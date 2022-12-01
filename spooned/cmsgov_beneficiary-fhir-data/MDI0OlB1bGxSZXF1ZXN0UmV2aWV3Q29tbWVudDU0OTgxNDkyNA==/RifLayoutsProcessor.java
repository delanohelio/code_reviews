[CompilationUnitImpl][CtPackageDeclarationImpl]package gov.cms.bfd.model.codegen;
[CtImportImpl]import javax.tools.StandardLocation;
[CtImportImpl]import javax.lang.model.element.ElementKind;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.net.MalformedURLException;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.squareup.javapoet.ClassName;
[CtImportImpl]import java.util.LinkedHashMap;
[CtImportImpl]import java.io.UncheckedIOException;
[CtImportImpl]import java.time.LocalDate;
[CtUnresolvedImport]import org.apache.poi.xssf.usermodel.XSSFWorkbook;
[CtUnresolvedImport]import javax.persistence.SequenceGenerator;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import javax.tools.FileObject;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.util.LinkedList;
[CtUnresolvedImport]import javax.persistence.Entity;
[CtUnresolvedImport]import javax.persistence.ManyToOne;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtUnresolvedImport]import com.squareup.javapoet.ParameterSpec;
[CtImportImpl]import java.time.Instant;
[CtUnresolvedImport]import javax.persistence.CascadeType;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import javax.persistence.ForeignKey;
[CtImportImpl]import javax.lang.model.SourceVersion;
[CtUnresolvedImport]import com.squareup.javapoet.JavaFile;
[CtUnresolvedImport]import javax.persistence.Column;
[CtUnresolvedImport]import javax.persistence.GenerationType;
[CtUnresolvedImport]import javax.persistence.GeneratedValue;
[CtUnresolvedImport]import com.squareup.javapoet.TypeName;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.io.Serializable;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import javax.annotation.processing.Processor;
[CtImportImpl]import javax.lang.model.element.Element;
[CtUnresolvedImport]import javax.persistence.IdClass;
[CtImportImpl]import javax.tools.Diagnostic;
[CtUnresolvedImport]import com.squareup.javapoet.FieldSpec;
[CtImportImpl]import java.net.URL;
[CtImportImpl]import javax.lang.model.element.Modifier;
[CtUnresolvedImport]import javax.persistence.Table;
[CtImportImpl]import java.io.StringWriter;
[CtUnresolvedImport]import javax.persistence.OrderBy;
[CtUnresolvedImport]import com.squareup.javapoet.CodeBlock;
[CtImportImpl]import java.io.PrintWriter;
[CtUnresolvedImport]import javax.persistence.Id;
[CtUnresolvedImport]import com.squareup.javapoet.MethodSpec;
[CtUnresolvedImport]import javax.persistence.TemporalType;
[CtImportImpl]import javax.lang.model.element.TypeElement;
[CtUnresolvedImport]import javax.persistence.JoinColumn;
[CtUnresolvedImport]import gov.cms.bfd.model.codegen.annotations.RifLayoutsGenerator;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import javax.persistence.OneToMany;
[CtUnresolvedImport]import com.squareup.javapoet.ParameterizedTypeName;
[CtImportImpl]import javax.annotation.processing.AbstractProcessor;
[CtUnresolvedImport]import com.squareup.javapoet.TypeSpec;
[CtImportImpl]import java.math.BigDecimal;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import org.apache.poi.ss.usermodel.Workbook;
[CtImportImpl]import javax.annotation.processing.RoundEnvironment;
[CtUnresolvedImport]import gov.cms.bfd.model.codegen.RifLayout.RifColumnType;
[CtUnresolvedImport]import javax.persistence.Temporal;
[CtUnresolvedImport]import javax.persistence.Transient;
[CtUnresolvedImport]import com.google.auto.service.AutoService;
[CtUnresolvedImport]import com.squareup.javapoet.AnnotationSpec;
[CtUnresolvedImport]import javax.persistence.FetchType;
[CtImportImpl]import java.io.Writer;
[CtImportImpl]import javax.lang.model.element.PackageElement;
[CtUnresolvedImport]import com.squareup.javapoet.ArrayTypeName;
[CtUnresolvedImport]import gov.cms.bfd.model.codegen.RifLayout.RifField;
[CtClassImpl][CtJavaDocImpl]/**
 * This <code>javac</code> annotation {@link Processor} reads in an Excel file that details a RIF
 * field layout, and then generates the Java code required to work with that layout.
 */
[CtAnnotationImpl]@com.google.auto.service.AutoService([CtFieldReadImpl]javax.annotation.processing.Processor.class)
public final class RifLayoutsProcessor extends [CtTypeReferenceImpl]javax.annotation.processing.AbstractProcessor {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Both Maven and Eclipse hide compiler messages, so setting this constant to <code>true</code>
     * will also log messages out to a new source file.
     */
    private static final [CtTypeReferenceImpl]boolean DEBUG = [CtLiteralImpl]true;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DATA_DICTIONARY_LINK = [CtLiteralImpl]"https://bluebutton.cms.gov/resources/variables/";

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> logMessages = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see javax.annotation.processing.AbstractProcessor#getSupportedAnnotationTypes()
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> getSupportedAnnotationTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.of([CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.model.codegen.annotations.RifLayoutsGenerator.class.getName());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see javax.annotation.processing.AbstractProcessor#getSupportedSourceVersion()
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]javax.lang.model.SourceVersion getSupportedSourceVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]javax.lang.model.SourceVersion.latestSupported();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set,
    javax.annotation.processing.RoundEnvironment)
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean process([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]javax.lang.model.element.TypeElement> annotations, [CtParameterImpl][CtTypeReferenceImpl]javax.annotation.processing.RoundEnvironment roundEnv) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]logNote([CtLiteralImpl]"Processing triggered for '%s' on root elements '%s'.", [CtVariableReadImpl]annotations, [CtInvocationImpl][CtVariableReadImpl]roundEnv.getRootElements());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]javax.lang.model.element.Element> annotatedElements = [CtInvocationImpl][CtVariableReadImpl]roundEnv.getElementsAnnotatedWith([CtFieldReadImpl]gov.cms.bfd.model.codegen.annotations.RifLayoutsGenerator.class);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]javax.lang.model.element.Element annotatedElement : [CtVariableReadImpl]annotatedElements) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]annotatedElement.getKind() != [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.ElementKind.[CtFieldReferenceImpl]PACKAGE)[CtBlockImpl]
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayoutProcessingException([CtVariableReadImpl]annotatedElement, [CtLiteralImpl]"The %s annotation is only valid on packages (i.e. in package-info.java).", [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.model.codegen.annotations.RifLayoutsGenerator.class.getName());

                [CtInvocationImpl]process([CtVariableReadImpl](([CtTypeReferenceImpl]javax.lang.model.element.PackageElement) (annotatedElement)));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayoutProcessingException e) [CtBlockImpl]{
            [CtInvocationImpl]log([CtFieldReadImpl][CtTypeAccessImpl]javax.tools.Diagnostic.Kind.[CtFieldReferenceImpl]ERROR, [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtInvocationImpl][CtVariableReadImpl]e.getElement());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* Don't allow exceptions of any type to propagate to the compiler.
            Log a warning and return, instead.
             */
            [CtTypeReferenceImpl]java.io.StringWriter writer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]writer));
            [CtInvocationImpl]log([CtFieldReadImpl][CtTypeAccessImpl]javax.tools.Diagnostic.Kind.[CtFieldReferenceImpl]ERROR, [CtBinaryOperatorImpl][CtLiteralImpl]"FATAL ERROR: " + [CtInvocationImpl][CtVariableReadImpl]writer.toString());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]roundEnv.processingOver())[CtBlockImpl]
            [CtInvocationImpl]writeDebugLogMessages();

        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param annotatedPackage
     * 		the {@link PackageElement} to process that has been annotated with
     * 		{@link RifLayoutsGenerator}
     * @throws IOException
     * 		An {@link IOException} may be thrown if errors are encountered trying to
     * 		generate source files.
     */
    private [CtTypeReferenceImpl]void process([CtParameterImpl][CtTypeReferenceImpl]javax.lang.model.element.PackageElement annotatedPackage) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.annotations.RifLayoutsGenerator annotation = [CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getAnnotation([CtFieldReadImpl]gov.cms.bfd.model.codegen.annotations.RifLayoutsGenerator.class);
        [CtInvocationImpl]logNote([CtVariableReadImpl]annotatedPackage, [CtLiteralImpl]"Processing package annotated with: '%s'.", [CtVariableReadImpl]annotation);
        [CtLocalVariableImpl][CtCommentImpl]/* Find the spreadsheet referenced by the annotation. It will define the
        RIF layouts.
         */
        [CtTypeReferenceImpl]javax.tools.FileObject spreadsheetResource;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]spreadsheetResource = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler().getResource([CtFieldReadImpl][CtTypeAccessImpl]javax.tools.StandardLocation.[CtFieldReferenceImpl]SOURCE_PATH, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString(), [CtInvocationImpl][CtVariableReadImpl]annotation.spreadsheetResource());
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayoutProcessingException([CtVariableReadImpl]annotatedPackage, [CtLiteralImpl]"Unable to find or open specified spreadsheet: '%s'.", [CtInvocationImpl][CtVariableReadImpl]annotation.spreadsheetResource());
        }
        [CtInvocationImpl]logNote([CtVariableReadImpl]annotatedPackage, [CtLiteralImpl]"Found spreadsheet: '%s'.", [CtInvocationImpl][CtVariableReadImpl]annotation.spreadsheetResource());
        [CtLocalVariableImpl][CtCommentImpl]/* Parse the spreadsheet, extracting the layouts from it. Also: define
        the layouts that we expect to parse and generate code for.
         */
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec> mappingSpecs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.poi.ss.usermodel.Workbook spreadsheetWorkbook = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]spreadsheetWorkbook = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.poi.xssf.usermodel.XSSFWorkbook([CtInvocationImpl][CtVariableReadImpl]spreadsheetResource.openInputStream());
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.beneficiarySheet())).setHeaderEntity([CtLiteralImpl]"Beneficiary").setHeaderTable([CtLiteralImpl]"Beneficiaries").setHeaderEntityIdField([CtLiteralImpl]"beneficiaryId").setHeaderEntityAdditionalDatabaseFields([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createDetailsForAdditionalDatabaseFields([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"hicnUnhashed", [CtLiteralImpl]"mbiHash"))).setInnerJoinRelationship([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.InnerJoinRelationship([CtLiteralImpl]"beneficiaryId", [CtLiteralImpl]null, [CtLiteralImpl]"BeneficiaryHistory", [CtLiteralImpl]"beneficiaryHistories"), [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.InnerJoinRelationship([CtLiteralImpl]"beneficiaryId", [CtLiteralImpl]null, [CtLiteralImpl]"MedicareBeneficiaryIdHistory", [CtLiteralImpl]"medicareBeneficiaryIdHistories"))).setHasLines([CtLiteralImpl]false).setHasBeneficiaryMonthly([CtLiteralImpl]true));
            [CtInvocationImpl][CtCommentImpl]/* FIXME Many BeneficiaryHistory fields are marked transient (i.e. not saved to
            DB), as they won't ever have changed data. We should change the RIF layout to
            exclude them, but this was implemented in a bit of a rush, and there wasn't
            time to fix that.
             */
            [CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.beneficiaryHistorySheet())).setHeaderEntity([CtLiteralImpl]"BeneficiaryHistory").setHeaderTable([CtLiteralImpl]"BeneficiariesHistory").setHeaderEntityGeneratedIdField([CtLiteralImpl]"beneficiaryHistoryId").setHeaderEntityTransientFields([CtLiteralImpl]"stateCode", [CtLiteralImpl]"countyCode", [CtLiteralImpl]"postalCode", [CtLiteralImpl]"race", [CtLiteralImpl]"entitlementCodeOriginal", [CtLiteralImpl]"entitlementCodeCurrent", [CtLiteralImpl]"endStageRenalDiseaseCode", [CtLiteralImpl]"medicareEnrollmentStatusCode", [CtLiteralImpl]"partATerminationCode", [CtLiteralImpl]"partBTerminationCode", [CtLiteralImpl]"nameSurname", [CtLiteralImpl]"nameGiven", [CtLiteralImpl]"nameMiddleInitial").setHeaderEntityAdditionalDatabaseFields([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createDetailsForAdditionalDatabaseFields([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"hicnUnhashed", [CtLiteralImpl]"mbiHash"))).setHasLines([CtLiteralImpl]false).setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.medicareBeneficiaryIdSheet())).setHeaderEntity([CtLiteralImpl]"MedicareBeneficiaryIdHistory").setHeaderTable([CtLiteralImpl]"MedicareBeneficiaryIdHistory").setHeaderEntityIdField([CtLiteralImpl]"medicareBeneficiaryIdKey").setHasLines([CtLiteralImpl]false).setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.pdeSheet())).setHeaderEntity([CtLiteralImpl]"PartDEvent").setHeaderTable([CtLiteralImpl]"PartDEvents").setHeaderEntityIdField([CtLiteralImpl]"eventId").setHasLines([CtLiteralImpl]false).setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.carrierSheet())).setHeaderEntity([CtLiteralImpl]"CarrierClaim").setHeaderTable([CtLiteralImpl]"CarrierClaims").setHeaderEntityIdField([CtLiteralImpl]"claimId").setHasLines([CtLiteralImpl]true).setLineTable([CtLiteralImpl]"CarrierClaimLines").setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.inpatientSheet())).setHeaderEntity([CtLiteralImpl]"InpatientClaim").setHeaderTable([CtLiteralImpl]"InpatientClaims").setHeaderEntityIdField([CtLiteralImpl]"claimId").setHasLines([CtLiteralImpl]true).setLineTable([CtLiteralImpl]"InpatientClaimLines").setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.outpatientSheet())).setHeaderEntity([CtLiteralImpl]"OutpatientClaim").setHeaderTable([CtLiteralImpl]"OutpatientClaims").setHeaderEntityIdField([CtLiteralImpl]"claimId").setHasLines([CtLiteralImpl]true).setLineTable([CtLiteralImpl]"OutpatientClaimLines").setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.hhaSheet())).setHeaderEntity([CtLiteralImpl]"HHAClaim").setHeaderTable([CtLiteralImpl]"HHAClaims").setHeaderEntityIdField([CtLiteralImpl]"claimId").setHasLines([CtLiteralImpl]true).setLineTable([CtLiteralImpl]"HHAClaimLines").setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.dmeSheet())).setHeaderEntity([CtLiteralImpl]"DMEClaim").setHeaderTable([CtLiteralImpl]"DMEClaims").setHeaderEntityIdField([CtLiteralImpl]"claimId").setHasLines([CtLiteralImpl]true).setLineTable([CtLiteralImpl]"DMEClaimLines").setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.hospiceSheet())).setHeaderEntity([CtLiteralImpl]"HospiceClaim").setHeaderTable([CtLiteralImpl]"HospiceClaims").setHeaderEntityIdField([CtLiteralImpl]"claimId").setHasLines([CtLiteralImpl]true).setLineTable([CtLiteralImpl]"HospiceClaimLines").setHasBeneficiaryMonthly([CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]mappingSpecs.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotatedPackage.getQualifiedName().toString()).setRifLayout([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.model.codegen.RifLayout.parse([CtVariableReadImpl]spreadsheetWorkbook, [CtInvocationImpl][CtVariableReadImpl]annotation.snfSheet())).setHeaderEntity([CtLiteralImpl]"SNFClaim").setHeaderTable([CtLiteralImpl]"SNFClaims").setHeaderEntityIdField([CtLiteralImpl]"claimId").setHasLines([CtLiteralImpl]true).setLineTable([CtLiteralImpl]"SNFClaimLines").setHasBeneficiaryMonthly([CtLiteralImpl]false));
        } finally [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spreadsheetWorkbook != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]spreadsheetWorkbook.close();

        }
        [CtInvocationImpl]logNote([CtVariableReadImpl]annotatedPackage, [CtLiteralImpl]"Generated mapping specification: '%s'.", [CtVariableReadImpl]mappingSpecs);
        [CtForEachImpl][CtCommentImpl]/* Generate the code for each layout. */
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec : [CtVariableReadImpl]mappingSpecs)[CtBlockImpl]
            [CtInvocationImpl]generateCode([CtVariableReadImpl]mappingSpec);

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates the code for the specified {@link RifLayout}.
     *
     * @param mappingSpec
     * 		the {@link MappingSpec} to generate code for
     * @throws IOException
     * 		An {@link IOException} may be thrown if errors are encountered trying to
     * 		generate source files.
     */
    private [CtTypeReferenceImpl]void generateCode([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl]logNote([CtLiteralImpl]"Generated code for %s", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout().getName());
        [CtLocalVariableImpl][CtCommentImpl]/* First, create the Java enum for the RIF columns. */
        [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec columnEnum = [CtInvocationImpl]generateColumnEnum([CtVariableReadImpl]mappingSpec);
        [CtLocalVariableImpl][CtCommentImpl]/* Then, create the JPA Entity for the "line" fields, containing: fields
        and accessors.
         */
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec> lineEntity = [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]lineEntity = [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl]generateLineEntity([CtVariableReadImpl]mappingSpec));
        }
        [CtLocalVariableImpl][CtCommentImpl]/* Then, create the JPA Entity for the "grouped" fields, containing:
        fields, accessors, and a RIF-to-JPA-Entity parser.
         */
        [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec headerEntity = [CtInvocationImpl]generateHeaderEntity([CtVariableReadImpl]mappingSpec);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasBeneficiaryMonthly()) [CtBlockImpl]{
            [CtInvocationImpl]generateBeneficiaryMonthlyEntity([CtVariableReadImpl]mappingSpec);
        }
        [CtInvocationImpl][CtCommentImpl]/* Then, create code that can be used to parse incoming RIF rows into
        instances of those entities.
         */
        generateParser([CtVariableReadImpl]mappingSpec, [CtVariableReadImpl]columnEnum, [CtVariableReadImpl]headerEntity, [CtVariableReadImpl]lineEntity);
        [CtInvocationImpl][CtCommentImpl]/* Then, create code that can be used to write the JPA Entity out to CSV
        files, for use with PostgreSQL's copy APIs.
         */
        generateCsvWriter([CtVariableReadImpl]mappingSpec, [CtVariableReadImpl]headerEntity, [CtVariableReadImpl]lineEntity);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates a Java {@link Enum} with entries for each {@link RifField} in the specified {@link MappingSpec}.
     *
     * @param mappingSpec
     * 		the {@link MappingSpec} of the layout to generate code for
     * @return the Java {@link Enum} that was generated
     * @throws IOException
     * 		An {@link IOException} may be thrown if errors are encountered trying to
     * 		generate source files.
     */
    private [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec generateColumnEnum([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder columnEnum = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.enumBuilder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getColumnEnum()).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int fieldIndex = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]fieldIndex < [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout().getRifFields().size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]fieldIndex++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout().getRifFields().get([CtVariableReadImpl]fieldIndex);
            [CtInvocationImpl][CtVariableReadImpl]columnEnum.addEnumConstant([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnName());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec columnEnumFinal = [CtInvocationImpl][CtVariableReadImpl]columnEnum.build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.JavaFile columnsEnumFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.JavaFile.builder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getPackageName(), [CtVariableReadImpl]columnEnumFinal).build();
        [CtInvocationImpl][CtVariableReadImpl]columnsEnumFile.writeTo([CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler());
        [CtReturnImpl]return [CtVariableReadImpl]columnEnumFinal;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates a Java {@link Entity} for the line {@link RifField}s in the specified {@link MappingSpec}.
     *
     * @param mappingSpec
     * 		the {@link MappingSpec} of the layout to generate code for
     * @return the Java {@link Entity} that was generated
     * @throws IOException
     * 		An {@link IOException} may be thrown if errors are encountered trying to
     * 		generate source files.
     */
    private [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec generateLineEntity([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout rifLayout = [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout();
        [CtLocalVariableImpl][CtCommentImpl]// Create the Entity class.
        [CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec entityAnnotation = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Entity.class).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec tableAnnotation = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Table.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"`" + [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineTable()) + [CtLiteralImpl]"`").build();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder lineEntity = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.classBuilder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntity()).addAnnotation([CtVariableReadImpl]entityAnnotation).addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.IdClass.class).addMember([CtLiteralImpl]"value", [CtLiteralImpl]"$T.class", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityIdClass()).build()).addAnnotation([CtVariableReadImpl]tableAnnotation).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC);
        [CtLocalVariableImpl][CtCommentImpl]// Create the @IdClass needed for the composite primary key.
        [CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder lineIdClass = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.classBuilder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityIdClass()).addSuperinterface([CtFieldReadImpl]java.io.Serializable.class).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]STATIC);
        [CtInvocationImpl][CtVariableReadImpl]lineIdClass.addField([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtFieldReadImpl]long.class, [CtLiteralImpl]"serialVersionUID", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]STATIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]FINAL).initializer([CtLiteralImpl]"$L", [CtLiteralImpl]1L).build());
        [CtLocalVariableImpl][CtCommentImpl]// Add a field to that @IdClass for the parent claim's ID.
        [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField parentClaimRifField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifLayout.getRifFields().stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityIdField().equals([CtInvocationImpl][CtVariableReadImpl]f.getJavaFieldName())).findAny().get();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeName parentClaimIdFieldType = [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtInvocationImpl][CtVariableReadImpl]parentClaimRifField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]parentClaimRifField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]parentClaimRifField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]parentClaimRifField.getRifColumnScale());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder parentIdField = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtVariableReadImpl]parentClaimIdFieldType, [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField(), [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE);
        [CtInvocationImpl][CtVariableReadImpl]lineIdClass.addField([CtInvocationImpl][CtVariableReadImpl]parentIdField.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder parentGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"getParentClaim").addStatement([CtLiteralImpl]"return $N", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField()).returns([CtVariableReadImpl]parentClaimIdFieldType);
        [CtInvocationImpl][CtVariableReadImpl]lineIdClass.addMethod([CtInvocationImpl][CtVariableReadImpl]parentGetter.build());
        [CtLocalVariableImpl][CtCommentImpl]// Add a field to that @IdClass class for the line number.
        [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifLineNumberField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifLayout.getRifFields().stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getJavaFieldName().equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityLineNumberField())).findFirst().get();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeName lineNumberFieldType = [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtInvocationImpl][CtVariableReadImpl]rifLineNumberField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]rifLineNumberField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]rifLineNumberField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]rifLineNumberField.getRifColumnScale());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder lineNumberIdField = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtVariableReadImpl]lineNumberFieldType, [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityLineNumberField(), [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE);
        [CtInvocationImpl][CtVariableReadImpl]lineIdClass.addField([CtInvocationImpl][CtVariableReadImpl]lineNumberIdField.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder lineNumberGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtBinaryOperatorImpl][CtLiteralImpl]"get" + [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.capitalize([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityLineNumberField())).addStatement([CtLiteralImpl]"return $N", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityLineNumberField()).returns([CtVariableReadImpl]lineNumberFieldType);
        [CtInvocationImpl][CtVariableReadImpl]lineIdClass.addMethod([CtInvocationImpl][CtVariableReadImpl]lineNumberGetter.build());
        [CtInvocationImpl][CtCommentImpl]// Add hashCode() and equals(...) to that @IdClass.
        [CtVariableReadImpl]lineIdClass.addMethod([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.generateHashCodeMethod([CtInvocationImpl][CtVariableReadImpl]parentIdField.build(), [CtInvocationImpl][CtVariableReadImpl]lineNumberIdField.build()));
        [CtInvocationImpl][CtVariableReadImpl]lineIdClass.addMethod([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.generateEqualsMethod([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntity(), [CtInvocationImpl][CtVariableReadImpl]parentIdField.build(), [CtInvocationImpl][CtVariableReadImpl]lineNumberIdField.build()));
        [CtInvocationImpl][CtCommentImpl]// Finalize the @IdClass and nest it inside the Entity class.
        [CtVariableReadImpl]lineEntity.addType([CtInvocationImpl][CtVariableReadImpl]lineIdClass.build());
        [CtLocalVariableImpl][CtCommentImpl]// Add a field and accessor to the "line" Entity for the parent.
        [CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec parentClaimField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity(), [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField(), [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).addAnnotation([CtFieldReadImpl]javax.persistence.Id.class).addAnnotation([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.ManyToOne.class).build()).addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.JoinColumn.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"`" + [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField()) + [CtLiteralImpl]"`").addMember([CtLiteralImpl]"foreignKey", [CtLiteralImpl]"@$T(name = $S)", [CtFieldReadImpl]javax.persistence.ForeignKey.class, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s_%s_to_%s", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineTable(), [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField(), [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderTable())).build()).build();
        [CtInvocationImpl][CtVariableReadImpl]lineEntity.addField([CtVariableReadImpl]parentClaimField);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec parentClaimGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]parentClaimField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addStatement([CtLiteralImpl]"return $N", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField()).returns([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity()).build();
        [CtInvocationImpl][CtVariableReadImpl]lineEntity.addMethod([CtVariableReadImpl]parentClaimGetter);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder parentClaimSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]parentClaimField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity(), [CtFieldReadImpl][CtVariableReadImpl]parentClaimField.name);
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtLiteralImpl]false, [CtVariableReadImpl]parentClaimField, [CtVariableReadImpl]parentClaimSetter);
        [CtInvocationImpl][CtVariableReadImpl]lineEntity.addMethod([CtInvocationImpl][CtVariableReadImpl]parentClaimSetter.build());
        [CtForImpl][CtCommentImpl]// For each "line" RIF field, create an Entity field with accessors.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int fieldIndex = [CtInvocationImpl][CtVariableReadImpl]mappingSpec.calculateFirstLineFieldIndex(); [CtBinaryOperatorImpl][CtVariableReadImpl]fieldIndex < [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifLayout.getRifFields().size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]fieldIndex++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifField = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifLayout.getRifFields().get([CtVariableReadImpl]fieldIndex);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec lineField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale()), [CtInvocationImpl][CtVariableReadImpl]rifField.getJavaFieldName(), [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).addAnnotations([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createAnnotations([CtVariableReadImpl]mappingSpec, [CtVariableReadImpl]rifField)).build();
            [CtInvocationImpl][CtVariableReadImpl]lineEntity.addField([CtVariableReadImpl]lineField);
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder lineFieldGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]lineField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale()));
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addGetterStatement([CtVariableReadImpl]rifField, [CtVariableReadImpl]lineField, [CtVariableReadImpl]lineFieldGetter);
            [CtInvocationImpl][CtVariableReadImpl]lineEntity.addMethod([CtInvocationImpl][CtVariableReadImpl]lineFieldGetter.build());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder lineFieldSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]lineField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale()), [CtFieldReadImpl][CtVariableReadImpl]lineField.name);
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtVariableReadImpl]rifField, [CtVariableReadImpl]lineField, [CtVariableReadImpl]lineFieldSetter);
            [CtInvocationImpl][CtVariableReadImpl]lineEntity.addMethod([CtInvocationImpl][CtVariableReadImpl]lineFieldSetter.build());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec lineEntityFinal = [CtInvocationImpl][CtVariableReadImpl]lineEntity.build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.JavaFile lineEntityClassFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.JavaFile.builder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getPackageName(), [CtVariableReadImpl]lineEntityFinal).build();
        [CtInvocationImpl][CtVariableReadImpl]lineEntityClassFile.writeTo([CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler());
        [CtReturnImpl]return [CtVariableReadImpl]lineEntityFinal;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec generateBeneficiaryMonthlyEntity([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create the Entity class.
        [CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec entityAnnotation = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Entity.class).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec tableAnnotation = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Table.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtLiteralImpl]"`BeneficiaryMonthly`").build();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder beneficiaryMonthlyEntity = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.classBuilder([CtLiteralImpl]"BeneficiaryMonthly").addAnnotation([CtVariableReadImpl]entityAnnotation).addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.IdClass.class).addMember([CtLiteralImpl]"value", [CtLiteralImpl]"$T.class", [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtLiteralImpl]"gov.cms.bfd.model.rif", [CtLiteralImpl]"BeneficiaryMonthly").nestedClass([CtLiteralImpl]"BeneficiaryMonthlyId")).build()).addAnnotation([CtVariableReadImpl]tableAnnotation).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC);
        [CtLocalVariableImpl][CtCommentImpl]// Create the @IdClass needed for the composite primary key.
        [CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder beneficiaryMonthlyIdClass = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.classBuilder([CtLiteralImpl]"BeneficiaryMonthlyId").addSuperinterface([CtFieldReadImpl]java.io.Serializable.class).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]STATIC);
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyIdClass.addField([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtFieldReadImpl]long.class, [CtLiteralImpl]"serialVersionUID", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]STATIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]FINAL).initializer([CtLiteralImpl]"$L", [CtLiteralImpl]1L).build());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeName parentBeneficiaryIdFieldType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder parentIdField = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtVariableReadImpl]parentBeneficiaryIdFieldType, [CtLiteralImpl]"parentBeneficiary", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE);
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyIdClass.addField([CtInvocationImpl][CtVariableReadImpl]parentIdField.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder parentGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"getParentBeneficiary").addStatement([CtLiteralImpl]"return $N", [CtLiteralImpl]"parentBeneficiary").returns([CtVariableReadImpl]parentBeneficiaryIdFieldType);
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyIdClass.addMethod([CtInvocationImpl][CtVariableReadImpl]parentGetter.build());
        [CtLocalVariableImpl][CtCommentImpl]// Add a field to that @IdClass class for the month.
        [CtTypeReferenceImpl]com.squareup.javapoet.TypeName yearMonthFieldType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.time.LocalDate.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder yearMonthIdField = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtVariableReadImpl]yearMonthFieldType, [CtLiteralImpl]"yearMonth", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE);
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyIdClass.addField([CtInvocationImpl][CtVariableReadImpl]yearMonthIdField.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder yearMonthGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtBinaryOperatorImpl][CtLiteralImpl]"get" + [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.capitalize([CtLiteralImpl]"yearMonth")).addStatement([CtLiteralImpl]"return $N", [CtLiteralImpl]"yearMonth").returns([CtVariableReadImpl]yearMonthFieldType);
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyIdClass.addMethod([CtInvocationImpl][CtVariableReadImpl]yearMonthGetter.build());
        [CtInvocationImpl][CtCommentImpl]// Add hashCode() and equals(...) to that @IdClass.
        [CtVariableReadImpl]beneficiaryMonthlyIdClass.addMethod([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.generateHashCodeMethod([CtInvocationImpl][CtVariableReadImpl]parentIdField.build(), [CtInvocationImpl][CtVariableReadImpl]yearMonthIdField.build()));
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyIdClass.addMethod([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.generateEqualsMethod([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getBeneficiaryMonthlyEntity(), [CtInvocationImpl][CtVariableReadImpl]parentIdField.build(), [CtInvocationImpl][CtVariableReadImpl]yearMonthIdField.build()));
        [CtInvocationImpl][CtCommentImpl]// Finalize the @IdClass and nest it inside the Entity class.
        [CtVariableReadImpl]beneficiaryMonthlyEntity.addType([CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyIdClass.build());
        [CtLocalVariableImpl][CtCommentImpl]// Add a field and accessor to the "line" Entity for the parent.
        [CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec parentBeneficiaryField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtLiteralImpl]"gov.cms.bfd.model.rif", [CtLiteralImpl]"Beneficiary"), [CtLiteralImpl]"parentBeneficiary", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).addAnnotation([CtFieldReadImpl]javax.persistence.Id.class).addAnnotation([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.ManyToOne.class).build()).addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.JoinColumn.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtLiteralImpl]"`parentBeneficiary`").addMember([CtLiteralImpl]"foreignKey", [CtLiteralImpl]"@$T(name = $S)", [CtFieldReadImpl]javax.persistence.ForeignKey.class, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s_%s_to_%s", [CtLiteralImpl]"BeneficiaryMonthly", [CtLiteralImpl]"parentBeneficiary", [CtLiteralImpl]"Beneficiary")).build()).build();
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyEntity.addField([CtVariableReadImpl]parentBeneficiaryField);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec parentBeneficiaryGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]parentBeneficiaryField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addStatement([CtLiteralImpl]"return $N", [CtLiteralImpl]"parentBeneficiary").returns([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtLiteralImpl]"gov.cms.bfd.model.rif", [CtLiteralImpl]"Beneficiary")).build();
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyEntity.addMethod([CtVariableReadImpl]parentBeneficiaryGetter);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder parentBeneficiarySetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]parentBeneficiaryField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtLiteralImpl]"gov.cms.bfd.model.rif", [CtLiteralImpl]"Beneficiary"), [CtFieldReadImpl][CtVariableReadImpl]parentBeneficiaryField.name);
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtLiteralImpl]false, [CtVariableReadImpl]parentBeneficiaryField, [CtVariableReadImpl]parentBeneficiarySetter);
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyEntity.addMethod([CtInvocationImpl][CtVariableReadImpl]parentBeneficiarySetter.build());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]true, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]"yearMonth", [CtTypeAccessImpl]RifColumnType.DATE, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]8), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"fipsStateCntyCode", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]5), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"medicareStatusCode", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]2), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"entitlementBuyInInd", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]1), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"hmoIndicatorInd", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]1), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partCContractNumberId", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]5), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partCPbpNumberId", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]3), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partCPlanTypeCode", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]3), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partDContractNumberId", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]5), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partDPbpNumberId", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]3), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partDSegmentNumberId", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]3), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partDRetireeDrugSubsidyInd", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]1), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"medicaidDualEligibilityCode", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]2), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyFields([CtVariableReadImpl]beneficiaryMonthlyEntity, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]"partDLowIncomeCostShareGroupCode", [CtTypeAccessImpl]RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]2), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec beneficiaryMonthlyEntityFinal = [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyEntity.build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.JavaFile beneficiaryMonthlyClassFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.JavaFile.builder([CtLiteralImpl]"gov.cms.bfd.model.rif", [CtVariableReadImpl]beneficiaryMonthlyEntityFinal).build();
        [CtInvocationImpl][CtVariableReadImpl]beneficiaryMonthlyClassFile.writeTo([CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler());
        [CtReturnImpl]return [CtVariableReadImpl]beneficiaryMonthlyEntityFinal;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates a Java {@link Entity} for the header {@link RifField}s in the specified {@link MappingSpec}.
     *
     * @param mappingSpec
     * 		the {@link MappingSpec} of the layout to generate code for
     * @return the Java {@link Entity} that was generated
     * @throws IOException
     * 		An {@link IOException} may be thrown if errors are encountered trying to
     * 		generate source files.
     */
    private [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec generateHeaderEntity([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create the Entity class.
        [CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec entityAnnotation = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Entity.class).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec tableAnnotation = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Table.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"`" + [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderTable()) + [CtLiteralImpl]"`").build();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder headerEntityClass = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.classBuilder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity()).addAnnotation([CtVariableReadImpl]entityAnnotation).addAnnotation([CtVariableReadImpl]tableAnnotation).addSuperinterface([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtLiteralImpl]"gov.cms.bfd.model.rif", [CtLiteralImpl]"RifRecordBase")).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC);
        [CtIfImpl][CtCommentImpl]// Create an Entity field with accessors for the generated-ID field (if any).
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityGeneratedIdField() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder idFieldBuilder = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtTypeAccessImpl]TypeName.LONG, [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityGeneratedIdField(), [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE);
            [CtInvocationImpl][CtVariableReadImpl]idFieldBuilder.addAnnotation([CtFieldReadImpl]javax.persistence.Id.class);
            [CtInvocationImpl][CtVariableReadImpl]idFieldBuilder.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Column.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"`%s`", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityGeneratedIdField())).addMember([CtLiteralImpl]"nullable", [CtLiteralImpl]"$L", [CtLiteralImpl]false).addMember([CtLiteralImpl]"updatable", [CtLiteralImpl]"$L", [CtLiteralImpl]false).build());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sequenceName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s_%s_seq", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity().simpleName(), [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityGeneratedIdField());
            [CtAssignmentImpl][CtCommentImpl]/* FIXME For consistency, sequence names should be mixed-case, but can't be, due
            to https://hibernate.atlassian.net/browse/HHH-9431.
             */
            [CtVariableWriteImpl]sequenceName = [CtInvocationImpl][CtVariableReadImpl]sequenceName.toLowerCase();
            [CtInvocationImpl][CtVariableReadImpl]idFieldBuilder.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.GeneratedValue.class).addMember([CtLiteralImpl]"strategy", [CtLiteralImpl]"$T.SEQUENCE", [CtFieldReadImpl]javax.persistence.GenerationType.class).addMember([CtLiteralImpl]"generator", [CtLiteralImpl]"$S", [CtVariableReadImpl]sequenceName).build());
            [CtInvocationImpl][CtVariableReadImpl]idFieldBuilder.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.SequenceGenerator.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtVariableReadImpl]sequenceName).addMember([CtLiteralImpl]"sequenceName", [CtLiteralImpl]"$S", [CtVariableReadImpl]sequenceName).addMember([CtLiteralImpl]"allocationSize", [CtLiteralImpl]"$L", [CtLiteralImpl]50).build());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec idField = [CtInvocationImpl][CtVariableReadImpl]idFieldBuilder.build();
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addField([CtVariableReadImpl]idField);
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder idFieldGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]idField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl][CtVariableReadImpl]idField.type);
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addGetterStatement([CtLiteralImpl]false, [CtVariableReadImpl]idField, [CtVariableReadImpl]idFieldGetter);
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtInvocationImpl][CtVariableReadImpl]idFieldGetter.build());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder idFieldSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]idField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtFieldReadImpl][CtVariableReadImpl]idField.type, [CtFieldReadImpl][CtVariableReadImpl]idField.name);
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtLiteralImpl]false, [CtVariableReadImpl]idField, [CtVariableReadImpl]idFieldSetter);
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtInvocationImpl][CtVariableReadImpl]idFieldSetter.build());
        }
        [CtForImpl][CtCommentImpl]// Create an Entity field with accessors for each RIF field.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int fieldIndex = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]fieldIndex <= [CtInvocationImpl][CtVariableReadImpl]mappingSpec.calculateLastHeaderFieldIndex(); [CtUnaryOperatorImpl][CtVariableWriteImpl]fieldIndex++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout().getRifFields().get([CtVariableReadImpl]fieldIndex);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec headerField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale()), [CtInvocationImpl][CtVariableReadImpl]rifField.getJavaFieldName(), [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).addAnnotations([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createAnnotations([CtVariableReadImpl]mappingSpec, [CtVariableReadImpl]rifField)).build();
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addField([CtVariableReadImpl]headerField);
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder headerFieldGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]headerField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale()));
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addGetterStatement([CtVariableReadImpl]rifField, [CtVariableReadImpl]headerField, [CtVariableReadImpl]headerFieldGetter);
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtInvocationImpl][CtVariableReadImpl]headerFieldGetter.build());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder headerFieldSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]headerField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale()), [CtFieldReadImpl][CtVariableReadImpl]headerField.name);
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtVariableReadImpl]rifField, [CtVariableReadImpl]headerField, [CtVariableReadImpl]headerFieldSetter);
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtInvocationImpl][CtVariableReadImpl]headerFieldSetter.build());
        }
        [CtForEachImpl][CtCommentImpl]/* Create an Entity field for additional database fields that we need to store
        data for whereas there isn't a corresponding RIF input field.
         */
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField addlDatabaseField : [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityAdditionalDatabaseFields()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec headerField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnScale()), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getJavaFieldName(), [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).addAnnotations([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createAnnotations([CtVariableReadImpl]mappingSpec, [CtVariableReadImpl]addlDatabaseField)).build();
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addField([CtVariableReadImpl]headerField);
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder headerFieldGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]headerField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnScale()));
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addGetterStatement([CtVariableReadImpl]addlDatabaseField, [CtVariableReadImpl]headerField, [CtVariableReadImpl]headerFieldGetter);
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtInvocationImpl][CtVariableReadImpl]headerFieldGetter.build());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder headerFieldSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]headerField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnType(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.isRifColumnOptional(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnLength(), [CtInvocationImpl][CtVariableReadImpl]addlDatabaseField.getRifColumnScale()), [CtFieldReadImpl][CtVariableReadImpl]headerField.name);
            [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtVariableReadImpl]addlDatabaseField, [CtVariableReadImpl]headerField, [CtVariableReadImpl]headerFieldSetter);
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtInvocationImpl][CtVariableReadImpl]headerFieldSetter.build());
        }
        [CtIfImpl][CtCommentImpl]// Add the parent-to-child join field and accessor, if appropriate.
        if ([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.ParameterizedTypeName childFieldType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterizedTypeName.get([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.util.List.class), [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntity());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder childField = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtVariableReadImpl]childFieldType, [CtLiteralImpl]"lines", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).initializer([CtLiteralImpl]"new $T<>()", [CtFieldReadImpl]java.util.LinkedList.class);
            [CtInvocationImpl][CtVariableReadImpl]childField.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.OneToMany.class).addMember([CtLiteralImpl]"mappedBy", [CtLiteralImpl]"$S", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField()).addMember([CtLiteralImpl]"orphanRemoval", [CtLiteralImpl]"$L", [CtLiteralImpl]true).addMember([CtLiteralImpl]"fetch", [CtLiteralImpl]"$T.LAZY", [CtFieldReadImpl]javax.persistence.FetchType.class).addMember([CtLiteralImpl]"cascade", [CtLiteralImpl]"$T.ALL", [CtFieldReadImpl]javax.persistence.CascadeType.class).build());
            [CtInvocationImpl][CtVariableReadImpl]childField.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.OrderBy.class).addMember([CtLiteralImpl]"value", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityLineNumberField() + [CtLiteralImpl]" ASC").build());
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addField([CtInvocationImpl][CtVariableReadImpl]childField.build());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec childGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"getLines").addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addStatement([CtLiteralImpl]"return $N", [CtLiteralImpl]"lines").returns([CtVariableReadImpl]childFieldType).build();
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtVariableReadImpl]childGetter);
        }
        [CtIfImpl][CtCommentImpl]// Add the parent-to-child join field and accessor, if appropriate.
        if ([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasBeneficiaryMonthly()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.ParameterizedTypeName childFieldType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterizedTypeName.get([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.util.List.class), [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getBeneficiaryMonthlyEntity());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder childField = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtVariableReadImpl]childFieldType, [CtLiteralImpl]"beneficiaryMonthlys", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).initializer([CtLiteralImpl]"new $T<>()", [CtFieldReadImpl]java.util.LinkedList.class);
            [CtInvocationImpl][CtVariableReadImpl]childField.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.OneToMany.class).addMember([CtLiteralImpl]"mappedBy", [CtLiteralImpl]"$S", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getBeneficiaryMonthlyEntityParentField()).addMember([CtLiteralImpl]"orphanRemoval", [CtLiteralImpl]"$L", [CtLiteralImpl]true).addMember([CtLiteralImpl]"fetch", [CtLiteralImpl]"$T.EAGER", [CtFieldReadImpl]javax.persistence.FetchType.class).addMember([CtLiteralImpl]"cascade", [CtLiteralImpl]"$T.ALL", [CtFieldReadImpl]javax.persistence.CascadeType.class).build());
            [CtInvocationImpl][CtVariableReadImpl]childField.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.OrderBy.class).addMember([CtLiteralImpl]"value", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getEntityBeneficiaryMonthlyField() + [CtLiteralImpl]" ASC").build());
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addField([CtInvocationImpl][CtVariableReadImpl]childField.build());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec childGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"getBeneficiaryMonthlys").addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addStatement([CtLiteralImpl]"return $N", [CtLiteralImpl]"beneficiaryMonthlys").returns([CtVariableReadImpl]childFieldType).build();
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtVariableReadImpl]childGetter);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec childSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"setBeneficiaryMonthlys").addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtVariableReadImpl]childFieldType, [CtLiteralImpl]"beneficiaryMonthlys").addStatement([CtLiteralImpl]"this.$N = ($T)$N", [CtLiteralImpl]"beneficiaryMonthlys", [CtVariableReadImpl]childFieldType, [CtLiteralImpl]"beneficiaryMonthlys").build();
            [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtVariableReadImpl]childSetter);
        }
        [CtIfImpl][CtCommentImpl]// Add the parent-to-child join field and accessor for an inner join
        [CtCommentImpl]// relationship
        if ([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasInnerJoinRelationship()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.InnerJoinRelationship relationship : [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getInnerJoinRelationship()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mappedBy = [CtInvocationImpl][CtVariableReadImpl]relationship.getMappedBy();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String orderBy = [CtInvocationImpl][CtVariableReadImpl]relationship.getOrderBy();
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.ClassName childEntity = [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getClassName([CtInvocationImpl][CtVariableReadImpl]relationship.getChildEntity());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String childFieldName = [CtInvocationImpl][CtVariableReadImpl]relationship.getChildField();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> fieldDeclaredType;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> fieldActualType;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]orderBy != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]fieldDeclaredType = [CtFieldReadImpl]java.util.List.class;
                    [CtAssignmentImpl][CtVariableWriteImpl]fieldActualType = [CtFieldReadImpl]java.util.LinkedList.class;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]fieldDeclaredType = [CtFieldReadImpl]java.util.Set.class;
                    [CtAssignmentImpl][CtVariableWriteImpl]fieldActualType = [CtFieldReadImpl]java.util.HashSet.class;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.ParameterizedTypeName childFieldType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterizedTypeName.get([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtVariableReadImpl]fieldDeclaredType), [CtVariableReadImpl]childEntity);
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec.Builder childField = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtVariableReadImpl]childFieldType, [CtVariableReadImpl]childFieldName, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).initializer([CtLiteralImpl]"new $T<>()", [CtVariableReadImpl]fieldActualType);
                [CtInvocationImpl][CtVariableReadImpl]childField.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.OneToMany.class).addMember([CtLiteralImpl]"mappedBy", [CtLiteralImpl]"$S", [CtVariableReadImpl]mappedBy).addMember([CtLiteralImpl]"orphanRemoval", [CtLiteralImpl]"$L", [CtLiteralImpl]false).addMember([CtLiteralImpl]"fetch", [CtLiteralImpl]"$T.LAZY", [CtFieldReadImpl]javax.persistence.FetchType.class).addMember([CtLiteralImpl]"cascade", [CtLiteralImpl]"$T.ALL", [CtFieldReadImpl]javax.persistence.CascadeType.class).build());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]orderBy != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]childField.addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.OrderBy.class).addMember([CtLiteralImpl]"value", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtVariableReadImpl]orderBy + [CtLiteralImpl]" ASC").build());

                [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addField([CtInvocationImpl][CtVariableReadImpl]childField.build());
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec childGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtBinaryOperatorImpl][CtLiteralImpl]"get" + [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.capitalize([CtVariableReadImpl]childFieldName)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addStatement([CtLiteralImpl]"return $N", [CtVariableReadImpl]childFieldName).returns([CtVariableReadImpl]childFieldType).build();
                [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtVariableReadImpl]childGetter);
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// Add a lastUpdated field.
        final [CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec lastUpdatedField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtFieldReadImpl]java.util.Date.class, [CtLiteralImpl]"lastUpdated", [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).addAnnotation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Temporal.class).addMember([CtLiteralImpl]"value", [CtLiteralImpl]"$T.TIMESTAMP", [CtFieldReadImpl]javax.persistence.TemporalType.class).build()).build();
        [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addField([CtVariableReadImpl]lastUpdatedField);
        [CtLocalVariableImpl][CtCommentImpl]// Getter method
        final [CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec lastUpdatedGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"getLastUpdated").addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addStatement([CtLiteralImpl]"return Optional.ofNullable(lastUpdated)").returns([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterizedTypeName.get([CtFieldReadImpl]java.util.Optional.class, [CtFieldReadImpl]java.util.Date.class)).build();
        [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtVariableReadImpl]lastUpdatedGetter);
        [CtLocalVariableImpl][CtCommentImpl]// Setter method which is useful for testing, but not needed in the main modules
        final [CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec lastUpdatedSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"setLastUpdated").addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addParameter([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterSpec.builder([CtFieldReadImpl]java.util.Date.class, [CtLiteralImpl]"lastUpdated").build()).addStatement([CtLiteralImpl]"this.lastUpdated = lastUpdated").returns([CtTypeAccessImpl]TypeName.VOID).build();
        [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.addMethod([CtVariableReadImpl]lastUpdatedSetter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec headerEntityFinal = [CtInvocationImpl][CtVariableReadImpl]headerEntityClass.build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.JavaFile headerEntityFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.JavaFile.builder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getPackageName(), [CtVariableReadImpl]headerEntityFinal).build();
        [CtInvocationImpl][CtVariableReadImpl]headerEntityFile.writeTo([CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler());
        [CtReturnImpl]return [CtVariableReadImpl]headerEntityFinal;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates a Java class that can handle RIF-to-Entity parsing.
     *
     * @param mappingSpec
     * 		the {@link MappingSpec} of the layout to generate code for
     * @param columnEnum
     * 		the RIF column {@link Enum} that was generated for the layout
     * @param headerEntity
     * 		the Java {@link Entity} that was generated for the header fields
     * @param lineEntity
     * 		the Java {@link Entity} that was generated for the line fields, if any
     * @return the Java parsing class that was generated
     * @throws IOException
     * 		An {@link IOException} may be thrown if errors are encountered trying to
     * 		generate source files.
     */
    private [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec generateParser([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec columnEnum, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec headerEntity, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec> lineEntity) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder parsingClass = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.classBuilder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getParserClass()).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]FINAL);
        [CtLocalVariableImpl][CtCommentImpl]// Grab some common types we'll need.
        [CtTypeReferenceImpl]com.squareup.javapoet.ClassName csvRecordType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtLiteralImpl]"org.apache.commons.csv", [CtLiteralImpl]"CSVRecord");
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.ClassName parseUtilsType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtLiteralImpl]"gov.cms.bfd.model.rif.parse", [CtLiteralImpl]"RifParsingUtils");
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder parseMethod = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"parseRif").addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]STATIC).returns([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity()).addParameter([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterizedTypeName.get([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.util.List.class), [CtVariableReadImpl]csvRecordType), [CtLiteralImpl]"csvRecords");
        [CtInvocationImpl][CtVariableReadImpl]parseMethod.addComment([CtLiteralImpl]"Verify the inputs.");
        [CtInvocationImpl][CtVariableReadImpl]parseMethod.addStatement([CtLiteralImpl]"$T.requireNonNull(csvRecords)", [CtFieldReadImpl]java.util.Objects.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parseMethod.beginControlFlow([CtLiteralImpl]"if (csvRecords.size() < 1)").addStatement([CtLiteralImpl]"throw new $T()", [CtFieldReadImpl]java.lang.IllegalArgumentException.class).endControlFlow();
        [CtInvocationImpl][CtVariableReadImpl]parseMethod.addCode([CtLiteralImpl]"\n$1T header = new $1T();\n", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity());
        [CtForImpl][CtCommentImpl]// Loop over each field and generate the code needed to parse it.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int fieldIndex = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]fieldIndex < [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout().getRifFields().size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]fieldIndex++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout().getRifFields().get([CtVariableReadImpl]fieldIndex);
            [CtLocalVariableImpl][CtCommentImpl]// Find the Entity field for the RifField.
            [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec> entitiesFieldsStream = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines()) ? [CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.concat([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]headerEntity.fieldSpecs.stream(), [CtInvocationImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]lineEntity.get().fieldSpecs.stream()) : [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]headerEntity.fieldSpecs.stream();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec entityField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entitiesFieldsStream.filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]rifField.getJavaFieldName())).findAny().get();
            [CtIfImpl][CtCommentImpl]// Are we starting the header parsing?
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]fieldIndex == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]parseMethod.addCode([CtLiteralImpl]"\n// Parse the header fields.\n");
                [CtInvocationImpl][CtVariableReadImpl]parseMethod.addCode([CtLiteralImpl]"$T headerRecord = csvRecords.get(0);\n", [CtVariableReadImpl]csvRecordType);
            }
            [CtIfImpl][CtCommentImpl]// Are we starting the line parsing?
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines() && [CtBinaryOperatorImpl]([CtVariableReadImpl]fieldIndex == [CtInvocationImpl][CtVariableReadImpl]mappingSpec.calculateFirstLineFieldIndex())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]parseMethod.addCode([CtLiteralImpl]"\n// Parse the line fields.\n");
                [CtInvocationImpl][CtVariableReadImpl]parseMethod.beginControlFlow([CtLiteralImpl]"for (int lineIndex = 0; lineIndex < csvRecords.size(); lineIndex++)");
                [CtInvocationImpl][CtVariableReadImpl]parseMethod.addStatement([CtLiteralImpl]"$T lineRecord = csvRecords.get(lineIndex)", [CtVariableReadImpl]csvRecordType);
                [CtInvocationImpl][CtVariableReadImpl]parseMethod.addStatement([CtLiteralImpl]"$1T line = new $1T()", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntity());
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec lineEntityParentField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]lineEntity.get().fieldSpecs.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField())).findAny().get();
                [CtInvocationImpl][CtVariableReadImpl]parseMethod.addCode([CtLiteralImpl]"line.$L(header);\n\n", [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]lineEntityParentField));
            }
            [CtLocalVariableImpl][CtCommentImpl]// Determine which variables to use in assignment statement.
            [CtTypeReferenceImpl]java.lang.String entityName;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String recordName;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines() && [CtBinaryOperatorImpl]([CtVariableReadImpl]fieldIndex >= [CtInvocationImpl][CtVariableReadImpl]mappingSpec.calculateFirstLineFieldIndex())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]entityName = [CtLiteralImpl]"line";
                [CtAssignmentImpl][CtVariableWriteImpl]recordName = [CtLiteralImpl]"lineRecord";
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]entityName = [CtLiteralImpl]"header";
                [CtAssignmentImpl][CtVariableWriteImpl]recordName = [CtLiteralImpl]"headerRecord";
            }
            [CtLocalVariableImpl][CtCommentImpl]// Determine which parsing utility method to use.
            [CtTypeReferenceImpl]java.lang.String parseUtilsMethodName;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) > [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Handle a String field.
                [CtVariableWriteImpl]parseUtilsMethodName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional()) ? [CtLiteralImpl]"parseOptionalString" : [CtLiteralImpl]"parseString";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) == [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Handle a Character field.
                [CtVariableWriteImpl]parseUtilsMethodName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional()) ? [CtLiteralImpl]"parseOptionalCharacter" : [CtLiteralImpl]"parseCharacter";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.NUM) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale().orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) == [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Handle an Integer field.
                [CtVariableWriteImpl]parseUtilsMethodName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional()) ? [CtLiteralImpl]"parseOptionalInteger" : [CtLiteralImpl]"parseInteger";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.NUM) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale().orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Handle a Decimal field.
                [CtVariableWriteImpl]parseUtilsMethodName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional()) ? [CtLiteralImpl]"parseOptionalDecimal" : [CtLiteralImpl]"parseDecimal";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.DATE) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Handle a LocalDate field.
                [CtVariableWriteImpl]parseUtilsMethodName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional()) ? [CtLiteralImpl]"parseOptionalDate" : [CtLiteralImpl]"parseDate";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.TIMESTAMP) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Handle an Instant field.
                [CtVariableWriteImpl]parseUtilsMethodName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional()) ? [CtLiteralImpl]"parseOptionalTimestamp" : [CtLiteralImpl]"parseTimestamp";
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> valueAssignmentArgs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]valueAssignmentArgs.put([CtLiteralImpl]"entity", [CtVariableReadImpl]entityName);
            [CtInvocationImpl][CtVariableReadImpl]valueAssignmentArgs.put([CtLiteralImpl]"entitySetter", [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]entityField));
            [CtInvocationImpl][CtVariableReadImpl]valueAssignmentArgs.put([CtLiteralImpl]"record", [CtVariableReadImpl]recordName);
            [CtInvocationImpl][CtVariableReadImpl]valueAssignmentArgs.put([CtLiteralImpl]"parseUtilsType", [CtVariableReadImpl]parseUtilsType);
            [CtInvocationImpl][CtVariableReadImpl]valueAssignmentArgs.put([CtLiteralImpl]"parseUtilsMethod", [CtVariableReadImpl]parseUtilsMethodName);
            [CtInvocationImpl][CtVariableReadImpl]valueAssignmentArgs.put([CtLiteralImpl]"columnEnumType", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getColumnEnum());
            [CtInvocationImpl][CtVariableReadImpl]valueAssignmentArgs.put([CtLiteralImpl]"columnEnumConstant", [CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnName());
            [CtInvocationImpl][CtVariableReadImpl]parseMethod.addCode([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.CodeBlock.builder().addNamed([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"$entity:L.$entitySetter:L(" + [CtLiteralImpl]"$parseUtilsType:T.$parseUtilsMethod:L(") + [CtLiteralImpl]"$record:L.get(") + [CtLiteralImpl]"$columnEnumType:T.$columnEnumConstant:L)));\n", [CtVariableReadImpl]valueAssignmentArgs).build());
        }
        [CtIfImpl][CtCommentImpl]// Did we just finish line parsing?
        if ([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec linesField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]headerEntity.fieldSpecs.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityLinesField())).findAny().get();
            [CtInvocationImpl][CtVariableReadImpl]parseMethod.addStatement([CtLiteralImpl]"header.$L().add(line)", [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]linesField));
            [CtInvocationImpl][CtVariableReadImpl]parseMethod.endControlFlow();
        }
        [CtInvocationImpl][CtVariableReadImpl]parseMethod.addStatement([CtLiteralImpl]"return header");
        [CtInvocationImpl][CtVariableReadImpl]parsingClass.addMethod([CtInvocationImpl][CtVariableReadImpl]parseMethod.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec parsingClassFinal = [CtInvocationImpl][CtVariableReadImpl]parsingClass.build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.JavaFile parsingClassFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.JavaFile.builder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getPackageName(), [CtVariableReadImpl]parsingClassFinal).build();
        [CtInvocationImpl][CtVariableReadImpl]parsingClassFile.writeTo([CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler());
        [CtReturnImpl]return [CtVariableReadImpl]parsingClassFinal;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates a Java class that can be used to write the JPA Entity out to CSV files, for use with
     * PostgreSQL's copy APIs.
     *
     * @param mappingSpec
     * 		the {@link MappingSpec} of the layout to generate code for
     * @param headerEntity
     * 		the Java {@link Entity} that was generated for the header fields
     * @param lineEntity
     * 		the Java {@link Entity} that was generated for the line fields, if any
     * @return the Java CSV writing class that was generated
     * @throws IOException
     * 		An {@link IOException} may be thrown if errors are encountered trying to
     * 		generate source files.
     */
    private [CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec generateCsvWriter([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec headerEntity, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec> lineEntity) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder csvWriterClass = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.TypeSpec.classBuilder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getCsvWriterClass()).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]FINAL);
        [CtLocalVariableImpl][CtCommentImpl]// Grab some common types we'll need.
        [CtTypeReferenceImpl]com.squareup.javapoet.ArrayTypeName recordType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ArrayTypeName.of([CtFieldReadImpl]java.lang.Object.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.ArrayTypeName recordsListType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ArrayTypeName.of([CtVariableReadImpl]recordType);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.ParameterizedTypeName returnType = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterizedTypeName.get([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.util.Map.class), [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.lang.String.class), [CtVariableReadImpl]recordsListType);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder csvWriterMethod = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"toCsvRecordsByTable").addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]STATIC).returns([CtVariableReadImpl]returnType).addParameter([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntity(), [CtLiteralImpl]"entity");
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addComment([CtLiteralImpl]"Verify the input.");
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"$T.requireNonNull(entity)", [CtFieldReadImpl]java.util.Objects.class);
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addCode([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"$T csvRecordsByTable = new $T<>(2)", [CtVariableReadImpl]returnType, [CtFieldReadImpl]java.util.HashMap.class);
        [CtInvocationImpl][CtCommentImpl]// Generate the header conversion.
        [CtVariableReadImpl]csvWriterMethod.addCode([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addComment([CtLiteralImpl]"Convert the header fields.");
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"$T headerRecords = new $T[2][]", [CtVariableReadImpl]recordsListType, [CtFieldReadImpl]java.lang.Object.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String headerColumnsList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]headerEntity.fieldSpecs.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines() && [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityLinesField()))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]false;

            [CtReturnImpl]return [CtLiteralImpl]true;
        }).map([CtLambdaImpl]([CtParameterImpl] f) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"\"" + [CtVariableReadImpl]f.name) + [CtLiteralImpl]"\"").collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", "));
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"headerRecords[0] = new $1T{ $2L }", [CtVariableReadImpl]recordType, [CtVariableReadImpl]headerColumnsList);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String headerGettersList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]headerEntity.fieldSpecs.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines() && [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityLinesField()))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]false;

            [CtReturnImpl]return [CtLiteralImpl]true;
        }).map([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl]calculateFieldToCsvValueCode([CtLiteralImpl]"entity", [CtVariableReadImpl]f, [CtVariableReadImpl]mappingSpec, [CtLiteralImpl]null, [CtLiteralImpl]null)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", "));
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"$1T headerRecord = new $1T{ $2L }", [CtVariableReadImpl]recordType, [CtVariableReadImpl]headerGettersList);
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"headerRecords[1] = headerRecord");
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"csvRecordsByTable.put($S, headerRecords)", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderTable());
        [CtIfImpl][CtCommentImpl]// Generate the line conversion.
        if ([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec linesField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]headerEntity.fieldSpecs.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityLinesField())).findAny().get();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String linesFieldGetter = [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]linesField);
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addCode([CtLiteralImpl]"\n");
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addComment([CtLiteralImpl]"Convert the line fields.");
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"$T lineRecords = new $T[entity.$L().size() + 1][]", [CtVariableReadImpl]recordsListType, [CtFieldReadImpl]java.lang.Object.class, [CtVariableReadImpl]linesFieldGetter);
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"csvRecordsByTable.put($S, lineRecords)", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineTable());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lineColumnsList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]lineEntity.get().fieldSpecs.stream().map([CtLambdaImpl]([CtParameterImpl] f) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"\"" + [CtVariableReadImpl]f.name) + [CtLiteralImpl]"\"").collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", "));
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"lineRecords[0] = new $1T{ $2L }", [CtVariableReadImpl]recordType, [CtVariableReadImpl]lineColumnsList);
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.beginControlFlow([CtLiteralImpl]"for (int lineIndex = 0; lineIndex < entity.$L().size();lineIndex++)", [CtVariableReadImpl]linesFieldGetter);
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"$T lineEntity = entity.$L().get(lineIndex)", [CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntity(), [CtVariableReadImpl]linesFieldGetter);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec parentField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]lineEntity.get().fieldSpecs.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityParentField())).findAny().get();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec headerIdField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]headerEntity.fieldSpecs.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtVariableReadImpl]f.name.equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityIdField())).findAny().get();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lineGettersList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]lineEntity.get().fieldSpecs.stream().map([CtLambdaImpl]([CtParameterImpl] f) -> [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]calculateFieldToCsvValueCode([CtLiteralImpl]"lineEntity", [CtVariableReadImpl]f, [CtVariableReadImpl]mappingSpec, [CtVariableReadImpl]parentField, [CtVariableReadImpl]headerIdField);
            }).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", "));
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"$1T lineRecord = new $1T{ $2L }", [CtVariableReadImpl]recordType, [CtVariableReadImpl]lineGettersList);
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"lineRecords[lineIndex + 1] = lineRecord");
            [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.endControlFlow();
        }
        [CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.addStatement([CtLiteralImpl]"return csvRecordsByTable");
        [CtInvocationImpl][CtVariableReadImpl]csvWriterClass.addMethod([CtInvocationImpl][CtVariableReadImpl]csvWriterMethod.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec parsingClassFinal = [CtInvocationImpl][CtVariableReadImpl]csvWriterClass.build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.JavaFile parsingClassFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.JavaFile.builder([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getPackageName(), [CtVariableReadImpl]parsingClassFinal).build();
        [CtInvocationImpl][CtVariableReadImpl]parsingClassFile.writeTo([CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler());
        [CtReturnImpl]return [CtVariableReadImpl]parsingClassFinal;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used in {@link #generateCsvWriter(MappingSpec, TypeSpec, Optional)} and generates the
     * field-to-CSV-value conversion code for the specified field.
     *
     * @param instanceName
     * 		the name of the object that the value will be pulled from
     * @param field
     * 		the field to generate conversion code for
     * @param mappingSpec
     * 		the {@link MappingSpec} of the field to generate conversion code for
     * @param parentField
     * 		the {@link MappingSpec#getLineEntityParentField()} field, or <code>null
     * 		</code> if this is a header field
     * @param headerIdField
     * 		the {@link MappingSpec#getHeaderEntityIdField()} field, or <code>null
     * 		</code> if this is a header field
     * @return the field-to-CSV-value conversion code for the specified field
     */
    private [CtTypeReferenceImpl]java.lang.String calculateFieldToCsvValueCode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String instanceName, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec field, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec parentField, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec headerIdField) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder code = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]code.append([CtVariableReadImpl]instanceName);
        [CtInvocationImpl][CtVariableReadImpl]code.append([CtLiteralImpl]".");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField> rifField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getRifLayout().getRifFields().stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtVariableReadImpl]field.name.equals([CtInvocationImpl][CtVariableReadImpl]f.getJavaFieldName())).findAny();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]field == [CtVariableReadImpl]parentField) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This is the line-level "parent" field.
            [CtVariableReadImpl]code.append([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]parentField));
            [CtInvocationImpl][CtVariableReadImpl]code.append([CtLiteralImpl]"().");
            [CtInvocationImpl][CtVariableReadImpl]code.append([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]headerIdField));
            [CtInvocationImpl][CtVariableReadImpl]code.append([CtLiteralImpl]"()");
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rifField.isPresent() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.get().isRifColumnOptional()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]code.append([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]field));
            [CtInvocationImpl][CtVariableReadImpl]code.append([CtLiteralImpl]"().orElse(null)");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]code.append([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]field));
            [CtInvocationImpl][CtVariableReadImpl]code.append([CtLiteralImpl]"()");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]code.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param fields
     * 		the fields that should be hashed
     * @return a new <code>hashCode()</code> implementation that uses the specified fields
     */
    private static [CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec generateHashCodeMethod([CtParameterImpl]com.squareup.javapoet.FieldSpec... fields) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder hashCodeMethod = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"hashCode").addAnnotation([CtFieldReadImpl]java.lang.Override.class).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]int.class).addStatement([CtLiteralImpl]"return $T.hash($L)", [CtFieldReadImpl]java.util.Objects.class, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]fields).map([CtLambdaImpl]([CtParameterImpl] f) -> [CtVariableReadImpl]f.name).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", ")));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param typeName
     * 		the {@link TypeName} of the class to add this method for
     * @param fields
     * 		the fields that should be compared
     * @return a new <code>equals(...)</code> implementation that uses the specified fields
     */
    private static [CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec generateEqualsMethod([CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeName typeName, [CtParameterImpl]com.squareup.javapoet.FieldSpec... fields) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder hashCodeMethod = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtLiteralImpl]"equals").addAnnotation([CtFieldReadImpl]java.lang.Override.class).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).addParameter([CtFieldReadImpl]java.lang.Object.class, [CtLiteralImpl]"obj").returns([CtFieldReadImpl]boolean.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.beginControlFlow([CtLiteralImpl]"if (this == obj)").addStatement([CtLiteralImpl]"return true").endControlFlow();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.beginControlFlow([CtLiteralImpl]"if (obj == null)").addStatement([CtLiteralImpl]"return false").endControlFlow();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.beginControlFlow([CtLiteralImpl]"if (getClass() != obj.getClass())").addStatement([CtLiteralImpl]"return false").endControlFlow();
        [CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.addStatement([CtLiteralImpl]"$T other = ($T) obj", [CtVariableReadImpl]typeName, [CtVariableReadImpl]typeName);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec field : [CtVariableReadImpl]fields) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.beginControlFlow([CtLiteralImpl]"if ($T.deepEquals($N, other.$N))", [CtFieldReadImpl]java.util.Objects.class, [CtVariableReadImpl]field, [CtVariableReadImpl]field).addStatement([CtLiteralImpl]"return false").endControlFlow();
        }
        [CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.addStatement([CtLiteralImpl]"return true");
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hashCodeMethod.build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param mappingSpec
     * 		the {@link MappingSpec} for the specified {@link RifField}
     * @param rifField
     * 		the {@link RifField} to create the corresponding {@link AnnotationSpec}s for
     * @return an ordered {@link List} of {@link AnnotationSpec}s representing the JPA, etc.
    annotations that should be applied to the specified {@link RifField}
     */
    private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec> createAnnotations([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.MappingSpec mappingSpec, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifField) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec> annotations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtIfImpl][CtCommentImpl]// Add an @Id annotation, if appropriate.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getJavaFieldName().equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityIdField()) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHasLines() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getJavaFieldName().equals([CtInvocationImpl][CtVariableReadImpl]mappingSpec.getLineEntityLineNumberField()))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec.Builder idAnnotation = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Id.class);
            [CtInvocationImpl][CtVariableReadImpl]annotations.add([CtInvocationImpl][CtVariableReadImpl]idAnnotation.build());
        }
        [CtLocalVariableImpl][CtCommentImpl]// Add an @Column annotation to every non-transient column.
        [CtTypeReferenceImpl]boolean isTransient = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mappingSpec.getHeaderEntityTransientFields().contains([CtInvocationImpl][CtVariableReadImpl]rifField.getJavaFieldName());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isTransient) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec.Builder columnAnnotation = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Column.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"`" + [CtInvocationImpl][CtVariableReadImpl]rifField.getJavaFieldName()) + [CtLiteralImpl]"`").addMember([CtLiteralImpl]"nullable", [CtLiteralImpl]"$L", [CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().isPresent()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"length", [CtLiteralImpl]"$L", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().get());
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnType() == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.NUM) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]/* In SQL, the precision is the number of digits in the unscaled value, e.g.
                "123.45" has a precision of 5. The scale is the number of digits to the right
                of the decimal point, e.g. "123.45" has a scale of 2.
                 */
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().isPresent() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale().isPresent()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"precision", [CtLiteralImpl]"$L", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().get());
                    [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"scale", [CtLiteralImpl]"$L", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale().get());
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]/* Unfortunately, Hibernate's SQL schema generation (HBM2DDL) doesn't correctly
                    handle SQL numeric datatypes that don't have a defined precision and scale.
                    What it _should_ do is represent those types in PostgreSQL as a "NUMERIC",
                    but what it does instead is insert a default precision and scale as
                    "NUMBER(19, 2)". The only way to force the correct behavior is to specify a
                    columnDefinition, so we do that. This leads to incorrect behavior with HSQL
                    (for different reasons), but fortunately that doesn't happen to cause
                    problems with our tests.
                     */
                    [CtTypeReferenceImpl]java.lang.StringBuilder columnDefinition = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
                    [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]"numeric");
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().isPresent() || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale().isPresent()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]'(');
                        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().isPresent()) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnLength().get());
                        }
                        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale().isPresent()) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]", ");
                            [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rifField.getRifColumnScale().get());
                        }
                        [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]')');
                    }
                    [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"columnDefinition", [CtLiteralImpl]"$S", [CtInvocationImpl][CtVariableReadImpl]columnDefinition.toString());
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]annotations.add([CtInvocationImpl][CtVariableReadImpl]columnAnnotation.build());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]annotations.add([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Transient.class).build());
        }
        [CtReturnImpl]return [CtVariableReadImpl]annotations;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param List<String>
     * 		the {@link RifField} to create an additional Annotated database field for
     * @return an ordered {@link List} of {@link RifField}s representing the additional fields that
    need to be stored to the database via JPA
     * @throws MalformedURLException
     */
    private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField> createDetailsForAdditionalDatabaseFields([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> additionalDatabaseFields) throws [CtTypeReferenceImpl]java.net.MalformedURLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField> addlDatabaseFields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String additionalDatabaseField : [CtVariableReadImpl]additionalDatabaseFields) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]additionalDatabaseField.contentEquals([CtLiteralImpl]"hicnUnhashed")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField hicnUnhashed = [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField([CtLiteralImpl]"BENE_CRNT_HIC_NUM", [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]64), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]0), [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtBinaryOperatorImpl][CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.DATA_DICTIONARY_LINK + [CtLiteralImpl]"benecrnthicnum"), [CtLiteralImpl]"BENE_CRNT_HIC_NUM", [CtLiteralImpl]"hicnUnhashed");
                [CtInvocationImpl][CtVariableReadImpl]addlDatabaseFields.add([CtVariableReadImpl]hicnUnhashed);
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]additionalDatabaseField.contentEquals([CtLiteralImpl]"mbiHash")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField mbiHash = [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField([CtLiteralImpl]"MBI_NUM", [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]64), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtLiteralImpl]0), [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtBinaryOperatorImpl][CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.DATA_DICTIONARY_LINK + [CtLiteralImpl]"mbiHash"), [CtLiteralImpl]"MBI_NUM", [CtLiteralImpl]"mbiHash");
                [CtInvocationImpl][CtVariableReadImpl]addlDatabaseFields.add([CtVariableReadImpl]mbiHash);
                [CtContinueImpl]continue;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]addlDatabaseFields;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param entityField
     * 		the JPA entity {@link FieldSpec} for the field that the desired getter will
     * 		wrap
     * @return the name of the Java "getter" for the specified {@link FieldSpec}
     */
    private static [CtTypeReferenceImpl]java.lang.String calculateGetterName([CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec entityField) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]entityField.type.equals([CtTypeAccessImpl]TypeName.BOOLEAN) || [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]entityField.type.equals([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.lang.Boolean.class)))[CtBlockImpl]
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"is" + [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.capitalize([CtFieldReadImpl][CtVariableReadImpl]entityField.name);
        else[CtBlockImpl]
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"get" + [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.capitalize([CtFieldReadImpl][CtVariableReadImpl]entityField.name);

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rifField
     * 		the {@link RifField} to generate the "getter" statement for
     * @param entityField
     * 		the {@link FieldSpec} for the field being wrapped by the "getter"
     * @param entityGetter
     * 		the "getter" method to generate the statement in
     */
    private static [CtTypeReferenceImpl]void addGetterStatement([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifField, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec entityField, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder entityGetter) [CtBlockImpl]{
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addGetterStatement([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtVariableReadImpl]entityField, [CtVariableReadImpl]entityGetter);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param optional
     * 		<code>true</code> if the property is an {@link Optional} one, <code>false
     * 		</code> otherwise
     * @param entityField
     * 		the {@link FieldSpec} for the field being wrapped by the "getter"
     * @param entityGetter
     * 		the "getter" method to generate the statement in
     */
    private static [CtTypeReferenceImpl]void addGetterStatement([CtParameterImpl][CtTypeReferenceImpl]boolean optional, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec entityField, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder entityGetter) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]optional)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]entityGetter.addStatement([CtLiteralImpl]"return $N", [CtVariableReadImpl]entityField);
        else[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]entityGetter.addStatement([CtLiteralImpl]"return $T.ofNullable($N)", [CtFieldReadImpl]java.util.Optional.class, [CtVariableReadImpl]entityField);

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param entityField
     * 		the JPA entity {@link FieldSpec} for the field that the desired setter will
     * 		wrap
     * @return the name of the Java "setter" for the specified {@link FieldSpec}
     */
    private static [CtTypeReferenceImpl]java.lang.String calculateSetterName([CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec entityField) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"set" + [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.capitalize([CtFieldReadImpl][CtVariableReadImpl]entityField.name);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rifField
     * 		the {@link RifField} to generate the "setter" statement for
     * @param entityField
     * 		the {@link FieldSpec} for the field being wrapped by the "setter"
     * @param entitySetter
     * 		the "setter" method to generate the statement in
     */
    private static [CtTypeReferenceImpl]void addSetterStatement([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifField rifField, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec entityField, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder entitySetter) [CtBlockImpl]{
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtInvocationImpl][CtVariableReadImpl]rifField.isRifColumnOptional(), [CtVariableReadImpl]entityField, [CtVariableReadImpl]entitySetter);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rifField
     * 		<code>true</code> if the property is an {@link Optional} one, <code>false
     * 		</code> otherwise
     * @param entityField
     * 		the {@link FieldSpec} for the field being wrapped by the "setter"
     * @param entitySetter
     * 		the "setter" method to generate the statement in
     */
    private static [CtTypeReferenceImpl]void addSetterStatement([CtParameterImpl][CtTypeReferenceImpl]boolean optional, [CtParameterImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec entityField, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder entitySetter) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]optional)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]entitySetter.addStatement([CtLiteralImpl]"this.$N = $N", [CtVariableReadImpl]entityField, [CtVariableReadImpl]entityField);
        else[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]entitySetter.addStatement([CtLiteralImpl]"this.$N = $N.orElse(null)", [CtVariableReadImpl]entityField, [CtVariableReadImpl]entityField);

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param name
     * 		the {@link String} to capitalize the first letter of
     * @return a capitalized {@link String}
     */
    private static [CtTypeReferenceImpl]java.lang.String capitalize([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]char first = [CtInvocationImpl][CtVariableReadImpl]name.charAt([CtLiteralImpl]0);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s%s", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.toUpperCase([CtVariableReadImpl]first), [CtInvocationImpl][CtVariableReadImpl]name.substring([CtLiteralImpl]1));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reports the specified log message.
     *
     * @param logEntryKind
     * 		the {@link Diagnostic.Kind} of log entry to add
     * @param associatedElement
     * 		the Java AST {@link Element} that the log entry should be associated
     * 		with, or <code>null</code>
     * @param messageFormat
     * 		the log message format {@link String}
     * @param messageArguments
     * 		the log message format arguments
     */
    private [CtTypeReferenceImpl]void log([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]javax.tools.Diagnostic.Kind logEntryKind, [CtParameterImpl][CtTypeReferenceImpl]javax.lang.model.element.Element associatedElement, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageFormat, [CtParameterImpl]java.lang.Object... messageArguments) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String logMessage = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]messageFormat, [CtVariableReadImpl]messageArguments);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]processingEnv.getMessager().printMessage([CtVariableReadImpl]logEntryKind, [CtVariableReadImpl]logMessage, [CtVariableReadImpl]associatedElement);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String logMessageFull;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]associatedElement != [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]logMessageFull = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"[%s] at '%s': %s", [CtVariableReadImpl]logEntryKind, [CtVariableReadImpl]associatedElement, [CtVariableReadImpl]logMessage);
        else[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]logMessageFull = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"[%s]: %s", [CtVariableReadImpl]logEntryKind, [CtVariableReadImpl]logMessage);

        [CtInvocationImpl][CtFieldReadImpl]logMessages.add([CtVariableReadImpl]logMessageFull);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reports the specified log message.
     *
     * @param logEntryKind
     * 		the {@link Diagnostic.Kind} of log entry to add
     * @param messageFormat
     * 		the log message format {@link String}
     * @param messageArguments
     * 		the log message format arguments
     */
    private [CtTypeReferenceImpl]void log([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]javax.tools.Diagnostic.Kind logEntryKind, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageFormat, [CtParameterImpl]java.lang.Object... messageArguments) [CtBlockImpl]{
        [CtInvocationImpl]log([CtVariableReadImpl]logEntryKind, [CtLiteralImpl]null, [CtVariableReadImpl]messageFormat, [CtVariableReadImpl]messageArguments);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reports the specified log message.
     *
     * @param associatedElement
     * 		the Java AST {@link Element} that the log entry should be associated
     * 		with, or <code>null</code>
     * @param messageFormat
     * 		the log message format {@link String}
     * @param messageArguments
     * 		the log message format arguments
     */
    private [CtTypeReferenceImpl]void logNote([CtParameterImpl][CtTypeReferenceImpl]javax.lang.model.element.Element associatedElement, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageFormat, [CtParameterImpl]java.lang.Object... messageArguments) [CtBlockImpl]{
        [CtInvocationImpl]log([CtFieldReadImpl][CtTypeAccessImpl]javax.tools.Diagnostic.Kind.[CtFieldReferenceImpl]NOTE, [CtVariableReadImpl]associatedElement, [CtVariableReadImpl]messageFormat, [CtVariableReadImpl]messageArguments);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reports the specified log message.
     *
     * @param associatedElement
     * 		the Java AST {@link Element} that the log entry should be associated
     * 		with, or <code>null</code>
     * @param messageFormat
     * 		the log message format {@link String}
     * @param messageArguments
     * 		the log message format arguments
     */
    private [CtTypeReferenceImpl]void logNote([CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageFormat, [CtParameterImpl]java.lang.Object... messageArguments) [CtBlockImpl]{
        [CtInvocationImpl]log([CtFieldReadImpl][CtTypeAccessImpl]javax.tools.Diagnostic.Kind.[CtFieldReferenceImpl]NOTE, [CtLiteralImpl]null, [CtVariableReadImpl]messageFormat, [CtVariableReadImpl]messageArguments);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Writes out all of the messages in {@link #logMessages} to a log file in the
     * annotation-generated source directory.
     */
    private [CtTypeReferenceImpl]void writeDebugLogMessages() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.DEBUG)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.tools.FileObject logResource = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]processingEnv.getFiler().createResource([CtFieldReadImpl][CtTypeAccessImpl]javax.tools.StandardLocation.[CtFieldReferenceImpl]SOURCE_OUTPUT, [CtLiteralImpl]"", [CtLiteralImpl]"rif-layout-processor-log.txt");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Writer logWriter = [CtInvocationImpl][CtVariableReadImpl]logResource.openWriter();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String logMessage : [CtFieldReadImpl]logMessages) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]logWriter.write([CtVariableReadImpl]logMessage);
                [CtInvocationImpl][CtVariableReadImpl]logWriter.write([CtLiteralImpl]'\n');
            }
            [CtInvocationImpl][CtVariableReadImpl]logWriter.flush();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates the fields for the BeneficiaryMonthly class in the model rif
     *
     * @param lineEntity
     * 		helps build the entity {@link TypeSpec.Builder}
     * @param isId
     * 		determines if the field is an id field
     * @param isTransient
     * 		determines if the field is transient {@link boolean}
     * @param isColumnOptional
     * 		determines if the field is optional {@link boolean}
     * @param fieldName
     * 		specifies the fieldname {@link String}
     * @param type
     * 		specifies the field type {@link RifColumnType}
     * @param columnLength
     * 		specifies the column length {@link Optional<Integer>}
     * @param columnScale
     * 		specifies the column scale {@link Optional<Integer>}
     */
    private static [CtTypeReferenceImpl]void createBeneficiaryMonthlyFields([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.TypeSpec.Builder lineEntity, [CtParameterImpl][CtTypeReferenceImpl]boolean isId, [CtParameterImpl][CtTypeReferenceImpl]boolean isTransient, [CtParameterImpl][CtTypeReferenceImpl]boolean isColumnOptional, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType type, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnLength, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnScale) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.squareup.javapoet.FieldSpec lineField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.FieldSpec.builder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtVariableReadImpl]type, [CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]columnLength, [CtVariableReadImpl]columnScale), [CtVariableReadImpl]fieldName, [CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PRIVATE).addAnnotations([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.createBeneficiaryMonthlyAnnotations([CtVariableReadImpl]isId, [CtVariableReadImpl]isTransient, [CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]fieldName, [CtVariableReadImpl]type, [CtVariableReadImpl]columnLength, [CtVariableReadImpl]columnScale)).build();
        [CtInvocationImpl][CtVariableReadImpl]lineEntity.addField([CtVariableReadImpl]lineField);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder lineFieldGetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateGetterName([CtVariableReadImpl]lineField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtVariableReadImpl]type, [CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]columnLength, [CtVariableReadImpl]columnScale));
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addGetterStatement([CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]lineField, [CtVariableReadImpl]lineFieldGetter);
        [CtInvocationImpl][CtVariableReadImpl]lineEntity.addMethod([CtInvocationImpl][CtVariableReadImpl]lineFieldGetter.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.MethodSpec.Builder lineFieldSetter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.MethodSpec.methodBuilder([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.calculateSetterName([CtVariableReadImpl]lineField)).addModifiers([CtFieldReadImpl][CtTypeAccessImpl]javax.lang.model.element.Modifier.[CtFieldReferenceImpl]PUBLIC).returns([CtFieldReadImpl]void.class).addParameter([CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaPropertyType([CtVariableReadImpl]type, [CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]columnLength, [CtVariableReadImpl]columnScale), [CtFieldReadImpl][CtVariableReadImpl]lineField.name);
        [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.addSetterStatement([CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]lineField, [CtVariableReadImpl]lineFieldSetter);
        [CtInvocationImpl][CtVariableReadImpl]lineEntity.addMethod([CtInvocationImpl][CtVariableReadImpl]lineFieldSetter.build());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates the fields for the BeneficiaryMonthly annotations in the model rif
     *
     * @param isId
     * 		determines if the field is an id field
     * @param isTransient
     * 		determines if the field is transient {@link boolean}
     * @param isColumnOptional
     * 		determines if the field is optional {@link boolean}
     * @param fieldName
     * 		specifies the fieldname {@link String}
     * @param type
     * 		specifies the field type {@link RifColumnType}
     * @param columnLength
     * 		specifies the column length {@link Optional<Integer>}
     * @param columnScale
     * 		specifies the column scale {@link Optional<Integer>}
     */
    private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec> createBeneficiaryMonthlyAnnotations([CtParameterImpl][CtTypeReferenceImpl]boolean isId, [CtParameterImpl][CtTypeReferenceImpl]boolean isTransient, [CtParameterImpl][CtTypeReferenceImpl]boolean isColumnOptional, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType type, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnLength, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnScale) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec> annotations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtIfImpl][CtCommentImpl]// Add an @Id annotation, if appropriate.
        if ([CtVariableReadImpl]isId) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec.Builder idAnnotation = [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Id.class);
            [CtInvocationImpl][CtVariableReadImpl]annotations.add([CtInvocationImpl][CtVariableReadImpl]idAnnotation.build());
        }
        [CtIfImpl][CtCommentImpl]// Add an @Column annotation to every non-transient column.
        if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isTransient) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.squareup.javapoet.AnnotationSpec.Builder columnAnnotation = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Column.class).addMember([CtLiteralImpl]"name", [CtLiteralImpl]"$S", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"`" + [CtVariableReadImpl]fieldName) + [CtLiteralImpl]"`").addMember([CtLiteralImpl]"nullable", [CtLiteralImpl]"$L", [CtVariableReadImpl]isColumnOptional);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR) && [CtInvocationImpl][CtVariableReadImpl]columnLength.isPresent()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"length", [CtLiteralImpl]"$L", [CtInvocationImpl][CtVariableReadImpl]columnLength.get());
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.NUM) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]/* In SQL, the precision is the number of digits in the unscaled value, e.g.
                "123.45" has a precision of 5. The scale is the number of digits to the right
                of the decimal point, e.g. "123.45" has a scale of 2.
                 */
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]columnLength.isPresent() && [CtInvocationImpl][CtVariableReadImpl]columnScale.isPresent()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"precision", [CtLiteralImpl]"$L", [CtInvocationImpl][CtVariableReadImpl]columnLength.get());
                    [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"scale", [CtLiteralImpl]"$L", [CtInvocationImpl][CtVariableReadImpl]columnScale.get());
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]/* Unfortunately, Hibernate's SQL schema generation (HBM2DDL) doesn't correctly
                    handle SQL numeric datatypes that don't have a defined precision and scale.
                    What it _should_ do is represent those types in PostgreSQL as a "NUMERIC",
                    but what it does instead is insert a default precision and scale as
                    "NUMBER(19, 2)". The only way to force the correct behavior is to specify a
                    columnDefinition, so we do that. This leads to incorrect behavior with HSQL
                    (for different reasons), but fortunately that doesn't happen to cause
                    problems with our tests.
                     */
                    [CtTypeReferenceImpl]java.lang.StringBuilder columnDefinition = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
                    [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]"numeric");
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]columnLength.isPresent() || [CtInvocationImpl][CtVariableReadImpl]columnScale.isPresent()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]'(');
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]columnLength.isPresent()) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtInvocationImpl][CtVariableReadImpl]columnLength.get());
                        }
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]columnScale.isPresent()) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]", ");
                            [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtInvocationImpl][CtVariableReadImpl]columnScale.get());
                        }
                        [CtInvocationImpl][CtVariableReadImpl]columnDefinition.append([CtLiteralImpl]')');
                    }
                    [CtInvocationImpl][CtVariableReadImpl]columnAnnotation.addMember([CtLiteralImpl]"columnDefinition", [CtLiteralImpl]"$S", [CtInvocationImpl][CtVariableReadImpl]columnDefinition.toString());
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]annotations.add([CtInvocationImpl][CtVariableReadImpl]columnAnnotation.build());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]annotations.add([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.AnnotationSpec.builder([CtFieldReadImpl]javax.persistence.Transient.class).build());
        }
        [CtReturnImpl]return [CtVariableReadImpl]annotations;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Selects the java field type
     *
     * @param type
     * 		specifies the field type {@link RifColumnType}
     * @param isColumnOptional
     * 		determines if the field is optional {@link boolean}
     * @param columnLength
     * 		specifies the column length {@link Optional<Integer>}
     * @param columnScale
     * 		specifies the column scale {@link Optional<Integer>}
     */
    private static [CtTypeReferenceImpl]com.squareup.javapoet.TypeName selectJavaFieldType([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType type, [CtParameterImpl][CtTypeReferenceImpl]boolean isColumnOptional, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnLength, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnScale) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]columnLength.orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) == [CtLiteralImpl]1)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]isColumnOptional))[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]com.squareup.javapoet.TypeName.CHAR;
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]columnLength.orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) == [CtLiteralImpl]1)) && [CtVariableReadImpl]isColumnOptional)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.lang.Character.class);
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.CHAR)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.lang.String.class);
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.DATE) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]columnLength.orElse([CtLiteralImpl]0) == [CtLiteralImpl]8))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.time.LocalDate.class);
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.TIMESTAMP) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]columnLength.orElse([CtLiteralImpl]0) == [CtLiteralImpl]20))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.time.Instant.class);
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.NUM) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]columnScale.orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) > [CtLiteralImpl]0))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.math.BigDecimal.class);
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.NUM) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]columnScale.orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) == [CtLiteralImpl]0)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]isColumnOptional))[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]com.squareup.javapoet.TypeName.INT;
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType.NUM) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]columnScale.orElse([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE) == [CtLiteralImpl]0)) && [CtVariableReadImpl]isColumnOptional)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.lang.Integer.class);
        else[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Unhandled field type: " + [CtInvocationImpl][CtVariableReadImpl]type.name());

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Selects the java property type
     *
     * @param type
     * 		specifies the field type {@link RifColumnType}
     * @param isColumnOptional
     * 		determines if the field is optional {@link boolean}
     * @param columnLength
     * 		specifies the column length {@link Optional<Integer>}
     * @param columnScale
     * 		specifies the column scale {@link Optional<Integer>}
     */
    private static [CtTypeReferenceImpl]com.squareup.javapoet.TypeName selectJavaPropertyType([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codegen.RifLayout.RifColumnType type, [CtParameterImpl][CtTypeReferenceImpl]boolean isColumnOptional, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnLength, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> columnScale) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isColumnOptional)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtVariableReadImpl]type, [CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]columnLength, [CtVariableReadImpl]columnScale);
        else[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ParameterizedTypeName.get([CtInvocationImpl][CtTypeAccessImpl]com.squareup.javapoet.ClassName.get([CtFieldReadImpl]java.util.Optional.class), [CtInvocationImpl]gov.cms.bfd.model.codegen.RifLayoutsProcessor.selectJavaFieldType([CtVariableReadImpl]type, [CtVariableReadImpl]isColumnOptional, [CtVariableReadImpl]columnLength, [CtVariableReadImpl]columnScale));

    }
}
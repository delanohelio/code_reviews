[CompilationUnitImpl][CtPackageDeclarationImpl]package gov.cms.bfd.server.war.stu3.providers;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.CarrierClaimColumn;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Identifier;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import ca.uhn.fhir.rest.api.Constants;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Coding;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.InpatientClaim;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.CodeableConcept;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.DomainResource;
[CtUnresolvedImport]import org.hl7.fhir.instance.model.api.IBaseExtension;
[CtImportImpl]import java.io.UncheckedIOException;
[CtImportImpl]import java.time.LocalDate;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.SNFClaimColumn;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.SNFClaimLine;
[CtImportImpl]import java.util.function.Consumer;
[CtUnresolvedImport]import org.hl7.fhir.instance.model.api.IBaseResource;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.CarrierClaimLine;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ProcedureComponent;
[CtUnresolvedImport]import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
[CtUnresolvedImport]import gov.cms.bfd.model.codebook.model.Value;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.OutpatientClaim;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Coverage;
[CtUnresolvedImport]import ca.uhn.fhir.model.primitive.IdDt;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Reference;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.DiagnosisComponent;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.InpatientClaimColumn;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ReferralRequest.ReferralRequestStatus;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ReferralRequest.ReferralRequestRequesterComponent;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.CarrierClaim;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.HHAClaimLine;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Money;
[CtUnresolvedImport]import gov.cms.bfd.server.war.stu3.providers.Diagnosis.DiagnosisLabel;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ReferralRequest;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent;
[CtUnresolvedImport]import org.hl7.fhir.instance.model.api.IAnyResource;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import ca.uhn.fhir.rest.api.server.RequestDetails;
[CtUnresolvedImport]import gov.cms.bfd.model.codebook.data.CcwCodebookVariable;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Period;
[CtUnresolvedImport]import gov.cms.bfd.server.war.stu3.providers.BeneficiaryTransformer.CurrencyIdentifier;
[CtImportImpl]import java.net.URLEncoder;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Practitioner;
[CtUnresolvedImport]import com.codahale.metrics.MetricRegistry;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.SimpleQuantity;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Observation;
[CtUnresolvedImport]import gov.cms.bfd.server.war.FDADrugDataUtilityApp;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.DMEClaimColumn;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Patient;
[CtImportImpl]import java.io.BufferedReader;
[CtImportImpl]import java.text.ParseException;
[CtUnresolvedImport]import org.hl7.fhir.instance.model.api.IBaseHasExtensions;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Bundle;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Extension;
[CtImportImpl]import org.slf4j.MDC;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.codesystems.ClaimCareteamrole;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Organization;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.SNFClaim;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.HHAClaimColumn;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitBalanceComponent;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.util.LinkedList;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.io.UnsupportedEncodingException;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Quantity;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.UnsignedIntType;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.Beneficiary;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.CareTeamComponent;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.DateType;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.OutpatientClaimColumn;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.HospiceClaimLine;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.InpatientClaimLine;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.OutpatientClaimLine;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ExplanationOfBenefitStatus;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.HHAClaim;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Observation.ObservationStatus;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.parse.InvalidRifValueException;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.DMEClaim;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.DMEClaimLine;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.AdjudicationComponent;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.codesystems.BenefitCategory;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.Resource;
[CtUnresolvedImport]import gov.cms.bfd.model.codebook.model.Variable;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.HospiceClaim;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.io.InputStreamReader;
[CtImportImpl]import java.time.ZoneId;
[CtImportImpl]import java.math.BigDecimal;
[CtImportImpl]import java.util.Date;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import com.justdavis.karl.misc.exceptions.BadCodeMonkeyException;
[CtClassImpl][CtJavaDocImpl]/**
 * Contains shared methods used to transform CCW JPA entities (e.g. {@link Beneficiary}) into FHIR
 * resources (e.g. {@link Patient}).
 */
public final class TransformerUtils {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.class);

    [CtFieldImpl][CtJavaDocImpl]/**
     * Tracks the {@link CcwCodebookVariable}s that have already had code lookup failures due to
     * missing {@link Value} matches. Why track this? To ensure that we don't spam log events for
     * failed lookups over and over and over. This was needed to fix CBBF-162, where those log events
     * were flooding our logs and filling up the drive.
     *
     * @see #calculateCodingDisplay(IAnyResource, CcwCodebookVariable, String)
     */
    private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable> codebookLookupMissingFailures = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Tracks the {@link CcwCodebookVariable}s that have already had code lookup failures due to
     * duplicate {@link Value} matches. Why track this? To ensure that we don't spam log events for
     * failed lookups over and over and over. This was needed to fix CBBF-162, where those log events
     * were flooding our logs and filling up the drive.
     *
     * @see #calculateCodingDisplay(IAnyResource, CcwCodebookVariable, String)
     */
    private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable> codebookLookupDuplicateFailures = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Stores the PRODUCTNDC and SUBSTANCENAME from the downloaded NDC file.
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> ndcProductMap = [CtLiteralImpl]null;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Tracks the national drug codes that have already had code lookup failures.
     */
    private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> drugCodeLookupMissingFailures = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Stores the diagnosis ICD codes and their display values
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> icdMap = [CtLiteralImpl]null;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Stores the procedure codes and their display values
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> procedureMap = [CtLiteralImpl]null;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Tracks the procedure codes that have already had code lookup failures.
     */
    private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> procedureLookupMissingFailures = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Stores the NPI codes and their display values
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> npiMap = [CtLiteralImpl]null;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Tracks the NPI codes that have already had code lookup failures.
     */
    private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> npiCodeLookupMissingFailures = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that the adjudication total should be part of
     * @param categoryVariable
     * 		the {@link CcwCodebookVariable} to map to the adjudication's <code>
     * 		category</code>
     * @param amountValue
     * 		the {@link Money#getValue()} for the adjudication total
     * @return the new {@link BenefitBalanceComponent}, which will have already been added to the
    appropriate {@link ExplanationOfBenefit#getBenefitBalance()} entry
     */
    static [CtTypeReferenceImpl]void addAdjudicationTotal([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable categoryVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Number> amountValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* TODO Once we switch to STU4 (expected >= Q3 2018), remap these to the new
        `ExplanationOfBenefit.total` field. In anticipation of that, the
        CcwCodebookVariable param here is named `category`: right now it's used for
        the `Extension.url` but can be changed to
        `ExplanationOfBenefit.total.category` once this mapping is moved to STU4.
         */
        [CtTypeReferenceImpl]java.lang.String extensionUrl = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]categoryVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Money adjudicationTotalAmount = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]amountValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension adjudicationTotalEextension = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension([CtVariableReadImpl]extensionUrl, [CtVariableReadImpl]adjudicationTotalAmount);
        [CtInvocationImpl][CtVariableReadImpl]eob.addExtension([CtVariableReadImpl]adjudicationTotalEextension);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that the adjudication total should be part of
     * @param categoryVariable
     * 		the {@link CcwCodebookVariable} to map to the adjudication's <code>
     * 		category</code>
     * @param totalAmountValue
     * 		the {@link Money#getValue()} for the adjudication total
     * @return the new {@link BenefitBalanceComponent}, which will have already been added to the
    appropriate {@link ExplanationOfBenefit#getBenefitBalance()} entry
     */
    static [CtTypeReferenceImpl]void addAdjudicationTotal([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable categoryVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Number totalAmountValue) [CtBlockImpl]{
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtVariableReadImpl]categoryVariable, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]totalAmountValue));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param amountValue
     * 		the value to use for {@link Money#getValue()}
     * @return a new {@link Money} instance, with the specified {@link Money#getValue()}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Money createMoney([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Number> amountValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]amountValue.isPresent())[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Money money = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Money();
        [CtInvocationImpl][CtVariableReadImpl]money.setSystem([CtTypeAccessImpl]TransformerConstants.CODING_MONEY);
        [CtInvocationImpl][CtVariableReadImpl]money.setCode([CtTypeAccessImpl]TransformerConstants.CODED_MONEY_USD);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]amountValue.get() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.math.BigDecimal)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]money.setValue([CtInvocationImpl](([CtTypeReferenceImpl]java.math.BigDecimal) ([CtVariableReadImpl]amountValue.get())));
        else[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException();

        [CtReturnImpl]return [CtVariableReadImpl]money;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param amountValue
     * 		the value to use for {@link Money#getValue()}
     * @return a new {@link Money} instance, with the specified {@link Money#getValue()}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Money createMoney([CtParameterImpl][CtTypeReferenceImpl]java.lang.Number amountValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]amountValue));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that the {@link BenefitComponent} should be part of
     * @param benefitCategory
     * 		the {@link BenefitCategory} (see {@link BenefitBalanceComponent#getCategory()}) for the {@link BenefitBalanceComponent} that the
     * 		new {@link BenefitComponent} should be part of
     * @param financialType
     * 		the {@link CcwCodebookVariable} to map to {@link BenefitComponent#getType()}
     * @return the new {@link BenefitBalanceComponent}, which will have already been added to the
    appropriate {@link ExplanationOfBenefit#getBenefitBalance()} entry
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent addBenefitBalanceFinancial([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.codesystems.BenefitCategory benefitCategory, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable financialType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitBalanceComponent eobPrimaryBenefitBalance = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.findOrAddBenefitBalance([CtVariableReadImpl]eob, [CtVariableReadImpl]benefitCategory);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept financialTypeConcept = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtTypeAccessImpl]TransformerConstants.CODING_BBAPI_BENEFIT_BALANCE_TYPE, [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]financialType));
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]financialTypeConcept.getCodingFirstRep().setDisplay([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]financialType.getVariable().getLabel());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent financialEntry = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent([CtVariableReadImpl]financialTypeConcept);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eobPrimaryBenefitBalance.getFinancial().add([CtVariableReadImpl]financialEntry);
        [CtReturnImpl]return [CtVariableReadImpl]financialEntry;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that the {@link BenefitComponent} should be part of
     * @param benefitCategory
     * 		the {@link BenefitCategory} to map to {@link BenefitBalanceComponent#getCategory()}
     * @return the already-existing {@link BenefitBalanceComponent} that matches the specified
    parameters, or a new one
     */
    private static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitBalanceComponent findOrAddBenefitBalance([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.codesystems.BenefitCategory benefitCategory) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitBalanceComponent> matchingBenefitBalance = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getBenefitBalance().stream().filter([CtLambdaImpl]([CtParameterImpl] bb) -> [CtInvocationImpl]isCodeInConcept([CtInvocationImpl][CtVariableReadImpl]bb.getCategory(), [CtInvocationImpl][CtVariableReadImpl]benefitCategory.getSystem(), [CtInvocationImpl][CtVariableReadImpl]benefitCategory.toCode())).findAny();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]matchingBenefitBalance.isPresent())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]matchingBenefitBalance.get();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept benefitCategoryConcept = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]benefitCategoryConcept.addCoding().setSystem([CtInvocationImpl][CtVariableReadImpl]benefitCategory.getSystem()).setCode([CtInvocationImpl][CtVariableReadImpl]benefitCategory.toCode()).setDisplay([CtInvocationImpl][CtVariableReadImpl]benefitCategory.getDisplay());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitBalanceComponent newBenefitBalance = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitBalanceComponent([CtVariableReadImpl]benefitCategoryConcept);
        [CtInvocationImpl][CtVariableReadImpl]eob.addBenefitBalance([CtVariableReadImpl]newBenefitBalance);
        [CtReturnImpl]return [CtVariableReadImpl]newBenefitBalance;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Ensures that the specified {@link ExplanationOfBenefit} has the specified {@link CareTeamComponent}, and links the specified {@link ItemComponent} to that {@link CareTeamComponent} (via {@link ItemComponent#addCareTeamLinkId(int)}).
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that the {@link CareTeamComponent} should be part
     * 		of
     * @param eobItem
     * 		the {@link ItemComponent} that should be linked to the {@link CareTeamComponent}
     * @param practitionerIdSystem
     * 		the {@link Identifier#getSystem()} of the practitioner to reference
     * 		in {@link CareTeamComponent#getProvider()}
     * @param practitionerIdValue
     * 		the {@link Identifier#getValue()} of the practitioner to reference
     * 		in {@link CareTeamComponent#getProvider()}
     * @param careTeamRole
     * 		the {@link ClaimCareteamrole} to use for the {@link CareTeamComponent#getRole()}
     * @return the {@link CareTeamComponent} that was created/linked
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.CareTeamComponent addCareTeamPractitioner([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent eobItem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String practitionerIdSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String practitionerIdValue, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.codesystems.ClaimCareteamrole careTeamRole) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Try to find a matching pre-existing entry.
        [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.CareTeamComponent careTeamEntry = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getCareTeam().stream().filter([CtLambdaImpl]([CtParameterImpl] ctc) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctc.getProvider().hasIdentifier()).filter([CtLambdaImpl]([CtParameterImpl] ctc) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]practitionerIdSystem.equals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctc.getProvider().getIdentifier().getSystem()) && [CtInvocationImpl][CtVariableReadImpl]practitionerIdValue.equals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctc.getProvider().getIdentifier().getValue())).filter([CtLambdaImpl]([CtParameterImpl] ctc) -> [CtInvocationImpl][CtVariableReadImpl]ctc.hasRole()).filter([CtLambdaImpl]([CtParameterImpl] ctc) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]careTeamRole.toCode().equals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctc.getRole().getCodingFirstRep().getCode()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]careTeamRole.getSystem().equals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctc.getRole().getCodingFirstRep().getSystem())).findAny().orElse([CtLiteralImpl]null);
        [CtIfImpl][CtCommentImpl]// If no match was found, add one to the EOB.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]careTeamEntry == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]careTeamEntry = [CtInvocationImpl][CtVariableReadImpl]eob.addCareTeam();
            [CtInvocationImpl][CtVariableReadImpl]careTeamEntry.setSequence([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getCareTeam().size() + [CtLiteralImpl]1);
            [CtInvocationImpl][CtVariableReadImpl]careTeamEntry.setProvider([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifierReference([CtVariableReadImpl]practitionerIdSystem, [CtVariableReadImpl]practitionerIdValue));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept careTeamRoleConcept = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtInvocationImpl][CtTypeAccessImpl]ClaimCareteamrole.OTHER.getSystem(), [CtInvocationImpl][CtVariableReadImpl]careTeamRole.toCode());
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]careTeamRoleConcept.getCodingFirstRep().setDisplay([CtInvocationImpl][CtVariableReadImpl]careTeamRole.getDisplay());
            [CtInvocationImpl][CtVariableReadImpl]careTeamEntry.setRole([CtVariableReadImpl]careTeamRoleConcept);
        }
        [CtIfImpl][CtCommentImpl]// care team entry is at eob level so no need to create item link id
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]eobItem == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]careTeamEntry;
        }
        [CtIfImpl][CtCommentImpl]// Link the EOB.item to the care team entry (if it isn't already).
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eobItem.getCareTeamLinkId().contains([CtInvocationImpl][CtVariableReadImpl]careTeamEntry.getSequence())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]eobItem.addCareTeamLinkId([CtInvocationImpl][CtVariableReadImpl]careTeamEntry.getSequence());
        }
        [CtReturnImpl]return [CtVariableReadImpl]careTeamEntry;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to (possibly) modify
     * @param diagnosis
     * 		the {@link Diagnosis} to add, if it's not already present
     * @return the {@link DiagnosisComponent#getSequence()} of the existing or newly-added entry
     */
    static [CtTypeReferenceImpl]int addDiagnosisCode([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis diagnosis) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.DiagnosisComponent> existingDiagnosis = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getDiagnosis().stream().filter([CtLambdaImpl]([CtParameterImpl] d) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]d.getDiagnosis() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept).filter([CtLambdaImpl]([CtParameterImpl] d) -> [CtInvocationImpl][CtVariableReadImpl]diagnosis.isContainedIn([CtInvocationImpl](([CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept) ([CtVariableReadImpl]d.getDiagnosis())))).findAny();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]existingDiagnosis.isPresent())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]existingDiagnosis.get().getSequenceElement().getValue();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.DiagnosisComponent diagnosisComponent = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.DiagnosisComponent().setSequence([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getDiagnosis().size() + [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]diagnosisComponent.setDiagnosis([CtInvocationImpl][CtVariableReadImpl]diagnosis.toCodeableConcept());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.DiagnosisLabel diagnosisLabel : [CtInvocationImpl][CtVariableReadImpl]diagnosis.getLabels()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept diagnosisTypeConcept = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtInvocationImpl][CtVariableReadImpl]diagnosisLabel.getSystem(), [CtInvocationImpl][CtVariableReadImpl]diagnosisLabel.toCode());
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]diagnosisTypeConcept.getCodingFirstRep().setDisplay([CtInvocationImpl][CtVariableReadImpl]diagnosisLabel.getDisplay());
            [CtInvocationImpl][CtVariableReadImpl]diagnosisComponent.addType([CtVariableReadImpl]diagnosisTypeConcept);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]diagnosis.getPresentOnAdmission().isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]diagnosisComponent.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_POA_IND_SW1, [CtInvocationImpl][CtVariableReadImpl]diagnosis.getPresentOnAdmission()));
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getDiagnosis().add([CtVariableReadImpl]diagnosisComponent);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]diagnosisComponent.getSequenceElement().getValue();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that the specified {@link ItemComponent} is a child
     * 		of
     * @param item
     * 		the {@link ItemComponent} to add an {@link ItemComponent#getDiagnosisLinkId()}
     * 		entry to
     * @param diagnosis
     * 		the {@link Diagnosis} to add a link for
     */
    static [CtTypeReferenceImpl]void addDiagnosisLink([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent item, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis diagnosis) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int diagnosisSequence = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addDiagnosisCode([CtVariableReadImpl]eob, [CtVariableReadImpl]diagnosis);
        [CtInvocationImpl][CtVariableReadImpl]item.addDiagnosisLinkId([CtVariableReadImpl]diagnosisSequence);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds an {@link Extension} to the specified {@link DomainResource}. {@link Extension#getValue()}
     * will be set to a {@link CodeableConcept} containing a single {@link Coding}, with the specified
     * system and code.
     *
     * <p>Data Architecture Note: The {@link CodeableConcept} might seem extraneous -- why not just
     * add the {@link Coding} directly to the {@link Extension}? The main reason for doing it this way
     * is consistency: this is what FHIR seems to do everywhere.
     *
     * @param fhirElement
     * 		the FHIR element to add the {@link Extension} to
     * @param extensionUrl
     * 		the {@link Extension#getUrl()} to use
     * @param codingSystem
     * 		the {@link Coding#getSystem()} to use
     * @param codingDisplay
     * 		the {@link Coding#getDisplay()} to use
     * @param codingCode
     * 		the {@link Coding#getCode()} to use
     */
    static [CtTypeReferenceImpl]void addExtensionCoding([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseHasExtensions fhirElement, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String extensionUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingDisplay, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingCode) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseExtension<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> extension = [CtInvocationImpl][CtVariableReadImpl]fhirElement.addExtension();
        [CtInvocationImpl][CtVariableReadImpl]extension.setUrl([CtVariableReadImpl]extensionUrl);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]codingDisplay == [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]extension.setValue([CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding().setSystem([CtVariableReadImpl]codingSystem).setCode([CtVariableReadImpl]codingCode));
        else[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]extension.setValue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding().setSystem([CtVariableReadImpl]codingSystem).setCode([CtVariableReadImpl]codingCode).setDisplay([CtVariableReadImpl]codingDisplay));

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds an {@link Extension} to the specified {@link DomainResource}. {@link Extension#getValue()}
     * will be set to a {@link Quantity} with the specified system and value.
     *
     * @param fhirElement
     * 		the FHIR element to add the {@link Extension} to
     * @param extensionUrl
     * 		the {@link Extension#getUrl()} to use
     * @param quantitySystem
     * 		the {@link Quantity#getSystem()} to use
     * @param quantityValue
     * 		the {@link Quantity#getValue()} to use
     */
    static [CtTypeReferenceImpl]void addExtensionValueQuantity([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseHasExtensions fhirElement, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String extensionUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String quantitySystem, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal quantityValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseExtension<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> extension = [CtInvocationImpl][CtVariableReadImpl]fhirElement.addExtension();
        [CtInvocationImpl][CtVariableReadImpl]extension.setUrl([CtVariableReadImpl]extensionUrl);
        [CtInvocationImpl][CtVariableReadImpl]extension.setValue([CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity().setSystem([CtVariableReadImpl]extensionUrl).setValue([CtVariableReadImpl]quantityValue));
        [CtCommentImpl]// CodeableConcept codeableConcept = new CodeableConcept();
        [CtCommentImpl]// extension.setValue(codeableConcept);
        [CtCommentImpl]// 
        [CtCommentImpl]// Coding coding = codeableConcept.addCoding();
        [CtCommentImpl]// coding.setSystem(codingSystem).setCode(codingCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds an {@link Extension} to the specified {@link DomainResource}. {@link Extension#getValue()}
     * will be set to a {@link Identifier} with the specified url, system, and value.
     *
     * @param fhirElement
     * 		the FHIR element to add the {@link Extension} to
     * @param extensionUrl
     * 		the {@link Extension#getUrl()} to use
     * @param extensionSystem
     * 		the {@link Identifier#getSystem()} to use
     * @param extensionValue
     * 		the {@link Identifier#getValue()} to use
     */
    static [CtTypeReferenceImpl]void addExtensionValueIdentifier([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseHasExtensions fhirElement, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String extensionUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String extensionSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String extensionValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseExtension<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> extension = [CtInvocationImpl][CtVariableReadImpl]fhirElement.addExtension();
        [CtInvocationImpl][CtVariableReadImpl]extension.setUrl([CtVariableReadImpl]extensionUrl);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier valueIdentifier = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]valueIdentifier.setSystem([CtVariableReadImpl]extensionSystem).setValue([CtVariableReadImpl]extensionValue);
        [CtInvocationImpl][CtVariableReadImpl]extension.setValue([CtVariableReadImpl]valueIdentifier);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new {@link SupportingInformationComponent} that has been added to the specified
     * {@link ExplanationOfBenefit}.
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param categoryVariable
     * 		{@link CcwCodebookVariable} to map to {@link SupportingInformationComponent#getCategory()}
     * @return the newly-added {@link SupportingInformationComponent} entry
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent addInformation([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable categoryVariable) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxSequence = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getInformation().stream().mapToInt([CtLambdaImpl]([CtParameterImpl] i) -> [CtInvocationImpl][CtVariableReadImpl]i.getSequence()).max().orElse([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent infoComponent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent();
        [CtInvocationImpl][CtVariableReadImpl]infoComponent.setSequence([CtBinaryOperatorImpl][CtVariableReadImpl]maxSequence + [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]infoComponent.setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConceptForFieldId([CtVariableReadImpl]eob, [CtTypeAccessImpl]TransformerConstants.CODING_BBAPI_INFORMATION_CATEGORY, [CtVariableReadImpl]categoryVariable));
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getInformation().add([CtVariableReadImpl]infoComponent);
        [CtReturnImpl]return [CtVariableReadImpl]infoComponent;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new {@link SupportingInformationComponent} that has been added to the specified
     * {@link ExplanationOfBenefit}. Unlike {@link #addInformation(ExplanationOfBenefit,
     * CcwCodebookVariable)}, this also sets the {@link SupportingInformationComponent#getCode()}
     * based on the values provided.
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param categoryVariable
     * 		{@link CcwCodebookVariable} to map to {@link SupportingInformationComponent#getCategory()}
     * @param codeSystemVariable
     * 		the {@link CcwCodebookVariable} to map to the {@link Coding#getSystem()} used in the {@link SupportingInformationComponent#getCode()}
     * @param codeValue
     * 		the value to map to the {@link Coding#getCode()} used in the {@link SupportingInformationComponent#getCode()}
     * @return the newly-added {@link SupportingInformationComponent} entry
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent addInformationWithCode([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable categoryVariable, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable codeSystemVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?> codeValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent infoComponent = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformation([CtVariableReadImpl]eob, [CtVariableReadImpl]categoryVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept infoCode = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept().addCoding([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCoding([CtVariableReadImpl]eob, [CtVariableReadImpl]codeSystemVariable, [CtVariableReadImpl]codeValue));
        [CtInvocationImpl][CtVariableReadImpl]infoComponent.setCode([CtVariableReadImpl]infoCode);
        [CtReturnImpl]return [CtVariableReadImpl]infoComponent;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new {@link SupportingInformationComponent} that has been added to the specified
     * {@link ExplanationOfBenefit}. Unlike {@link #addInformation(ExplanationOfBenefit,
     * CcwCodebookVariable)}, this also sets the {@link SupportingInformationComponent#getCode()}
     * based on the values provided.
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param categoryVariable
     * 		{@link CcwCodebookVariable} to map to {@link SupportingInformationComponent#getCategory()}
     * @param codeSystemVariable
     * 		the {@link CcwCodebookVariable} to map to the {@link Coding#getSystem()} used in the {@link SupportingInformationComponent#getCode()}
     * @param codeValue
     * 		the value to map to the {@link Coding#getCode()} used in the {@link SupportingInformationComponent#getCode()}
     * @return the newly-added {@link SupportingInformationComponent} entry
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent addInformationWithCode([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable categoryVariable, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable codeSystemVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object codeValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformationWithCode([CtVariableReadImpl]eob, [CtVariableReadImpl]categoryVariable, [CtVariableReadImpl]codeSystemVariable, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]codeValue));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to (possibly) modify
     * @param diagnosis
     * 		the {@link Diagnosis} to add, if it's not already present
     * @return the {@link ProcedureComponent#getSequence()} of the existing or newly-added entry
     */
    static [CtTypeReferenceImpl]int addProcedureCode([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure procedure) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ProcedureComponent> existingProcedure = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getProcedure().stream().filter([CtLambdaImpl]([CtParameterImpl] pc) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pc.getProcedure() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept).filter([CtLambdaImpl]([CtParameterImpl] pc) -> [CtInvocationImpl]isCodeInConcept([CtInvocationImpl](([CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept) ([CtVariableReadImpl]pc.getProcedure())), [CtInvocationImpl][CtVariableReadImpl]procedure.getFhirSystem(), [CtInvocationImpl][CtVariableReadImpl]procedure.getCode())).findAny();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]existingProcedure.isPresent())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]existingProcedure.get().getSequenceElement().getValue();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ProcedureComponent procedureComponent = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ProcedureComponent().setSequence([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getProcedure().size() + [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]procedureComponent.setProcedure([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtInvocationImpl][CtVariableReadImpl]procedure.getFhirSystem(), [CtLiteralImpl]null, [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.retrieveProcedureCodeDisplay([CtInvocationImpl][CtVariableReadImpl]procedure.getCode()), [CtInvocationImpl][CtVariableReadImpl]procedure.getCode()));
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]procedure.getProcedureDate().isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]procedureComponent.setDate([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]procedure.getProcedureDate().get()));
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getProcedure().add([CtVariableReadImpl]procedureComponent);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]procedureComponent.getSequenceElement().getValue();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param claimType
     * 		the {@link ClaimType} to compute an {@link ExplanationOfBenefit#getId()} for
     * @param claimId
     * 		the <code>claimId</code> field value (e.g. from {@link CarrierClaim#getClaimId()}) to compute an {@link ExplanationOfBenefit#getId()} for
     * @return the {@link ExplanationOfBenefit#getId()} value to use for the specified <code>claimId
    </code> value
     */
    public static [CtTypeReferenceImpl]java.lang.String buildEobId([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType claimType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String claimId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s-%s", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]claimType.name().toLowerCase(), [CtVariableReadImpl]claimId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to extract the id from
     * @return the <code>claimId</code> field value (e.g. from {@link CarrierClaim#getClaimId()})
     */
    static [CtTypeReferenceImpl]java.lang.String getUnprefixedClaimId([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier i : [CtInvocationImpl][CtVariableReadImpl]eob.getIdentifier()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]i.getSystem().contains([CtLiteralImpl]"clm_id") || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]i.getSystem().contains([CtLiteralImpl]"pde_id")) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]i.getValue();
            }
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException([CtLiteralImpl]"A claim ID was expected but none was found.");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to extract the claim type from
     * @return the {@link ClaimType}
     */
    static [CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType getClaimType([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String type = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getType().getCoding().stream().filter([CtLambdaImpl]([CtParameterImpl] c) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]c.getSystem().equals([CtVariableReadImpl]TransformerConstants.CODING_SYSTEM_BBAPI_EOB_TYPE)).findFirst().get().getCode();
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType.valueOf([CtVariableReadImpl]type);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param beneficiary
     * 		the {@link Beneficiary} to calculate the {@link Patient#getId()} value for
     * @return the {@link Patient#getId()} value that will be used for the specified {@link Beneficiary}
     */
    public static [CtTypeReferenceImpl]ca.uhn.fhir.model.primitive.IdDt buildPatientId([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.rif.Beneficiary beneficiary) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.buildPatientId([CtInvocationImpl][CtVariableReadImpl]beneficiary.getBeneficiaryId());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param beneficiaryId
     * 		the {@link Beneficiary#getBeneficiaryId()} to calculate the {@link Patient#getId()} value for
     * @return the {@link Patient#getId()} value that will be used for the specified {@link Beneficiary}
     */
    public static [CtTypeReferenceImpl]ca.uhn.fhir.model.primitive.IdDt buildPatientId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String beneficiaryId) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ca.uhn.fhir.model.primitive.IdDt([CtInvocationImpl][CtFieldReadImpl]org.hl7.fhir.dstu3.model.Patient.class.getSimpleName(), [CtVariableReadImpl]beneficiaryId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param medicareSegment
     * 		the {@link MedicareSegment} to compute a {@link Coverage#getId()} for
     * @param beneficiary
     * 		the {@link Beneficiary} to compute a {@link Coverage#getId()} for
     * @return the {@link Coverage#getId()} value to use for the specified values
     */
    public static [CtTypeReferenceImpl]ca.uhn.fhir.model.primitive.IdDt buildCoverageId([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.MedicareSegment medicareSegment, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.rif.Beneficiary beneficiary) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.buildCoverageId([CtVariableReadImpl]medicareSegment, [CtInvocationImpl][CtVariableReadImpl]beneficiary.getBeneficiaryId());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param medicareSegment
     * 		the {@link MedicareSegment} to compute a {@link Coverage#getId()} for
     * @param beneficiaryId
     * 		the {@link Beneficiary#getBeneficiaryId()} value to compute a {@link Coverage#getId()} for
     * @return the {@link Coverage#getId()} value to use for the specified values
     */
    public static [CtTypeReferenceImpl]ca.uhn.fhir.model.primitive.IdDt buildCoverageId([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.MedicareSegment medicareSegment, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String beneficiaryId) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ca.uhn.fhir.model.primitive.IdDt([CtInvocationImpl][CtFieldReadImpl]org.hl7.fhir.dstu3.model.Coverage.class.getSimpleName(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s-%s", [CtInvocationImpl][CtVariableReadImpl]medicareSegment.getUrlPrefix(), [CtVariableReadImpl]beneficiaryId));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param localDate
     * 		the {@link LocalDate} to convert
     * @return a {@link Date} version of the specified {@link LocalDate}
     */
    static [CtTypeReferenceImpl]java.util.Date convertToDate([CtParameterImpl][CtTypeReferenceImpl]java.time.LocalDate localDate) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]/* We use the system TZ here to ensure that the date doesn't shift at all, as
        FHIR will just use this as an unzoned Date (I think, and if not, it's almost
        certainly using the same TZ as this system).
         */
        return [CtInvocationImpl][CtTypeAccessImpl]java.util.Date.from([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]localDate.atStartOfDay([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault()).toInstant());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param codingSystem
     * 		the {@link Coding#getSystem()} to use
     * @param codingCode
     * 		the {@link Coding#getCode()} to use
     * @return a {@link CodeableConcept} with the specified {@link Coding}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept createCodeableConcept([CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingCode) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]codingSystem, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]codingCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param codingSystem
     * 		the {@link Coding#getSystem()} to use
     * @param codingVersion
     * 		the {@link Coding#getVersion()} to use
     * @param codingDisplay
     * 		the {@link Coding#getDisplay()} to use
     * @param codingCode
     * 		the {@link Coding#getCode()} to use
     * @return a {@link CodeableConcept} with the specified {@link Coding}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept createCodeableConcept([CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingVersion, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingDisplay, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingCode) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept codeableConcept = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding coding = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]codeableConcept.addCoding().setSystem([CtVariableReadImpl]codingSystem).setCode([CtVariableReadImpl]codingCode);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]codingVersion != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]coding.setVersion([CtVariableReadImpl]codingVersion);

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]codingDisplay != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]coding.setDisplay([CtVariableReadImpl]codingDisplay);

        [CtReturnImpl]return [CtVariableReadImpl]codeableConcept;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param identifierSystem
     * 		the {@link Identifier#getSystem()} to use in {@link Reference#getIdentifier()}
     * @param identifierValue
     * 		the {@link Identifier#getValue()} to use in {@link Reference#getIdentifier()}
     * @param providerIdentifiers
     * 		the {@link Identifier#getServiceProviderIdentifiers()} to use in
     * 		{@link Reference#getIdentifier()}
     * @return a {@link Reference} with the specified {@link Identifier}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference createIdentifierReference([CtParameterImpl][CtTypeReferenceImpl]java.lang.String identifierSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String identifierValue, [CtParameterImpl]gov.cms.bfd.server.war.stu3.providers.ServiceProviderIdentifiers... providerIdentifiers) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference reference = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]providerIdentifiers.length > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding coding = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding().setSystem([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]providerIdentifiers[[CtLiteralImpl]0].bySystem()).setCode([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]providerIdentifiers[[CtLiteralImpl]0].byCode()).setDisplay([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]providerIdentifiers[[CtLiteralImpl]0].byDisplay());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding> codingList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding>();
            [CtInvocationImpl][CtVariableReadImpl]codingList.add([CtVariableReadImpl]coding);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept codeableConcept = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept().setCoding([CtVariableReadImpl]codingList);
            [CtInvocationImpl][CtVariableReadImpl]reference.setIdentifier([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier().setSystem([CtVariableReadImpl]identifierSystem).setValue([CtVariableReadImpl]identifierValue).setType([CtVariableReadImpl]codeableConcept));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]reference.setIdentifier([CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier().setSystem([CtVariableReadImpl]identifierSystem).setValue([CtVariableReadImpl]identifierValue));
        }
        [CtInvocationImpl][CtVariableReadImpl]reference.setDisplay([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.retrieveNpiCodeDisplay([CtVariableReadImpl]identifierValue));
        [CtReturnImpl]return [CtVariableReadImpl]reference;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return a Reference to the {@link Organization} for CMS, which will only be valid if {@link #upsertSharedData()} has been run
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference createReferenceToCms() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference([CtBinaryOperatorImpl][CtLiteralImpl]"Organization?name=" + [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.urlEncode([CtTypeAccessImpl]TransformerConstants.COVERAGE_ISSUER));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param concept
     * 		the {@link CodeableConcept} to check
     * @param codingSystem
     * 		the {@link Coding#getSystem()} to match
     * @param codingCode
     * 		the {@link Coding#getCode()} to match
     * @return <code>true</code> if the specified {@link CodeableConcept} contains the specified
    {@link Coding}, <code>false</code> if it does not
     */
    static [CtTypeReferenceImpl]boolean isCodeInConcept([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept concept, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingCode) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.isCodeInConcept([CtVariableReadImpl]concept, [CtVariableReadImpl]codingSystem, [CtLiteralImpl]null, [CtVariableReadImpl]codingCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param concept
     * 		the {@link CodeableConcept} to check
     * @param codingSystem
     * 		the {@link Coding#getSystem()} to match
     * @param codingSystem
     * 		the {@link Coding#getVersion()} to match
     * @param codingCode
     * 		the {@link Coding#getCode()} to match
     * @return <code>true</code> if the specified {@link CodeableConcept} contains the specified
    {@link Coding}, <code>false</code> if it does not
     */
    static [CtTypeReferenceImpl]boolean isCodeInConcept([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept concept, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingVersion, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingCode) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]concept.getCoding().stream().anyMatch([CtLambdaImpl]([CtParameterImpl] c) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]codingSystem.equals([CtInvocationImpl][CtVariableReadImpl]c.getSystem()))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]false;

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]codingVersion != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]codingVersion.equals([CtInvocationImpl][CtVariableReadImpl]c.getVersion())))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]false;

            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]codingCode.equals([CtInvocationImpl][CtVariableReadImpl]c.getCode()))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]false;

            [CtReturnImpl]return [CtLiteralImpl]true;
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @param identifierValue
     * 		the value to use for {@link Identifier#getValue()} for the resulting
     * 		{@link Identifier}
     * @return the output {@link Extension}, with {@link Extension#getValue()} set to represent the
    specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createExtensionIdentifier([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> identifierValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]identifierValue.isPresent())[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier identifier = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifier([CtVariableReadImpl]ccwVariable, [CtInvocationImpl][CtVariableReadImpl]identifierValue.get());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String extensionUrl = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension extension = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension([CtVariableReadImpl]extensionUrl, [CtVariableReadImpl]identifier);
        [CtReturnImpl]return [CtVariableReadImpl]extension;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @param identifierValue
     * 		the value to use for {@link Identifier#getValue()} for the resulting
     * 		{@link Identifier}
     * @return the output {@link Extension}, with {@link Extension#getValue()} set to represent the
    specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createExtensionIdentifier([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String identifierValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionIdentifier([CtVariableReadImpl]ccwVariable, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]identifierValue));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @param identifierValue
     * 		the value to use for {@link Identifier#getValue()} for the resulting
     * 		{@link Identifier}
     * @return the output {@link Identifier}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier createIdentifier([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String identifierValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]identifierValue == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier identifier = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Identifier().setSystem([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable)).setValue([CtVariableReadImpl]identifierValue);
        [CtReturnImpl]return [CtVariableReadImpl]identifier;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @param dateYear
     * 		the value to use for {@link Coding#getCode()} for the resulting {@link Coding}
     * @return the output {@link Extension}, with {@link Extension#getValue()} set to represent the
    specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createExtensionDate([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> dateYear) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension extension = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stringDate = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateYear.get().toString() + [CtLiteralImpl]"-01-01";
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date date1 = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd").parse([CtVariableReadImpl]stringDate);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.DateType dateYearValue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.DateType([CtVariableReadImpl]date1, [CtFieldReadImpl]ca.uhn.fhir.model.api.TemporalPrecisionEnum.YEAR);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String extensionUrl = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
            [CtAssignmentImpl][CtVariableWriteImpl]extension = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension([CtVariableReadImpl]extensionUrl, [CtVariableReadImpl]dateYearValue);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.text.ParseException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.model.rif.parse.InvalidRifValueException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to parse reference year: '%s'.", [CtInvocationImpl][CtVariableReadImpl]dateYear.get()), [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]extension;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @param quantityValue
     * 		the value to use for {@link Coding#getCode()} for the resulting {@link Coding}
     * @return the output {@link Extension}, with {@link Extension#getValue()} set to represent the
    specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createExtensionQuantity([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Number> quantityValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]quantityValue.isPresent())[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity quantity;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]quantityValue.get() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.math.BigDecimal)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]quantity = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity().setValue([CtInvocationImpl](([CtTypeReferenceImpl]java.math.BigDecimal) ([CtVariableReadImpl]quantityValue.get())));
        else[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String extensionUrl = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension extension = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension([CtVariableReadImpl]extensionUrl, [CtVariableReadImpl]quantity);
        [CtReturnImpl]return [CtVariableReadImpl]extension;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @param quantityValue
     * 		the value to use for {@link Coding#getCode()} for the resulting {@link Coding}
     * @return the output {@link Extension}, with {@link Extension#getValue()} set to represent the
    specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createExtensionQuantity([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Number quantityValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionQuantity([CtVariableReadImpl]ccwVariable, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]quantityValue));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the {@link Quantity} fields related to the unit for the amount: {@link Quantity#getSystem()}, {@link Quantity#getCode()}, and {@link Quantity#getUnit()}.
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} for the unit coding
     * @param unitCode
     * 		the value to use for {@link Quantity#getCode()}
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that is being mapped
     * @param quantity
     * 		the {@link Quantity} to modify
     */
    static [CtTypeReferenceImpl]void setQuantityUnitInfo([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?> unitCode, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity quantity) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]unitCode.isPresent())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtVariableReadImpl]quantity.setSystem([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String unitCodeString;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unitCode.get() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.String)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]unitCodeString = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]unitCode.get()));
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unitCode.get() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Character)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]unitCodeString = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Character) ([CtVariableReadImpl]unitCode.get())).toString();
        else[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtInvocationImpl][CtVariableReadImpl]quantity.setCode([CtVariableReadImpl]unitCodeString);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> unit = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateCodingDisplay([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtVariableReadImpl]unitCodeString);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unit.isPresent())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]quantity.setUnit([CtInvocationImpl][CtVariableReadImpl]unit.get());

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link Extension}
     * 		will be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param code
     * 		the value to use for {@link Coding#getCode()} for the resulting {@link Coding}
     * @return the output {@link Extension}, with {@link Extension#getValue()} set to a new {@link Coding} to represent the specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createExtensionCoding([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?> code) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]code.isPresent())[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding coding = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCoding([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtInvocationImpl][CtVariableReadImpl]code.get());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String extensionUrl = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension extension = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension([CtVariableReadImpl]extensionUrl, [CtVariableReadImpl]coding);
        [CtReturnImpl]return [CtVariableReadImpl]extension;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link Extension}
     * 		will be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param code
     * 		the value to use for {@link Coding#getCode()} for the resulting {@link Coding}
     * @return the output {@link Extension}, with {@link Extension#getValue()} set to a new {@link Coding} to represent the specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createExtensionCoding([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object code) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Jumping through hoops to cope with overloaded method:
        [CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?> codeOptional = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]code instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.Optional) ? [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?>) (code)) : [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]code);
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtVariableReadImpl]codeOptional);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link CodeableConcept} will be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param code
     * 		the value to use for {@link Coding#getCode()} for the resulting (single) {@link Coding}, wrapped within the resulting {@link CodeableConcept}
     * @return the output {@link CodeableConcept} for the specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept createCodeableConcept([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?> code) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]code.isPresent())[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding coding = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCoding([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtInvocationImpl][CtVariableReadImpl]code.get());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept concept = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept();
        [CtInvocationImpl][CtVariableReadImpl]concept.addCoding([CtVariableReadImpl]coding);
        [CtReturnImpl]return [CtVariableReadImpl]concept;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link CodeableConcept} will be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param code
     * 		the value to use for {@link Coding#getCode()} for the resulting (single) {@link Coding}, wrapped within the resulting {@link CodeableConcept}
     * @return the output {@link CodeableConcept} for the specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept createCodeableConcept([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object code) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Jumping through hoops to cope with overloaded method:
        [CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?> codeOptional = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]code instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.Optional) ? [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?>) (code)) : [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]code);
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtVariableReadImpl]codeOptional);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Unlike {@link #createCodeableConcept(IAnyResource, CcwCodebookVariable, Optional)}, this method
     * creates a {@link CodeableConcept} that's intended for use as a field ID/discriminator: the
     * {@link Variable#getId()} will be used for the {@link Coding#getCode()}, rather than the {@link Coding#getSystem()}.
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link CodeableConcept} will be contained in
     * @param codingSystem
     * 		the {@link Coding#getSystem()} to use
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @return the output {@link CodeableConcept} for the specified input values
     */
    private static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept createCodeableConceptForFieldId([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codingSystem, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String code = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding coding = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding([CtVariableReadImpl]codingSystem, [CtVariableReadImpl]code, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ccwVariable.getVariable().getLabel());
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept().addCoding([CtVariableReadImpl]coding);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link Coding} will
     * 		be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param code
     * 		the value to use for {@link Coding#getCode()}
     * @return the output {@link Coding} for the specified input values
     */
    private static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding createCoding([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object code) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* The code parameter is an Object to avoid needing multiple copies of this and
        related methods. This if-else block is the price to be paid for that, though.
         */
        [CtTypeReferenceImpl]java.lang.String codeString;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]code instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Character)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]codeString = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Character) (code)).toString();
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]code instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.String)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]codeString = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]code.toString().trim();
        else[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException([CtBinaryOperatorImpl][CtLiteralImpl]"Unsupported: " + [CtVariableReadImpl]code);

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String system = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String display;
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ccwVariable.getVariable().getValueGroups().isPresent())[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]display = [CtInvocationImpl][CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateCodingDisplay([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtVariableReadImpl]codeString).orElse([CtLiteralImpl]null);
        else[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]display = [CtLiteralImpl]null;

        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding([CtVariableReadImpl]system, [CtVariableReadImpl]codeString, [CtVariableReadImpl]display);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link Coding} will
     * 		be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param code
     * 		the value to use for {@link Coding#getCode()}
     * @return the output {@link Coding} for the specified input values
     */
    private static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding createCoding([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtWildcardReferenceImpl]?> code) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCoding([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtInvocationImpl][CtVariableReadImpl]code.get());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @return the public URL at which documentation for the specified {@link CcwCodebookVariable} is
    published
     */
    static [CtTypeReferenceImpl]java.lang.String calculateVariableReferenceUrl([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s/%s", [CtTypeAccessImpl]TransformerConstants.BASE_URL_CCW_VARIABLES, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ccwVariable.getVariable().getId().toLowerCase());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being mapped
     * @return the {@link AdjudicationComponent#getCategory()} {@link CodeableConcept} to use for the
    specified {@link CcwCodebookVariable}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept createAdjudicationCategory([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* Adjudication.category is mapped a bit differently than other
        Codings/CodeableConcepts: they all share the same Coding.system and use the
        CcwCodebookVariable reference URL as their Coding.code. This looks weird, but
        makes it easy for API developers to find more information about what the
        specific adjudication they're looking at means.
         */
        [CtTypeReferenceImpl]java.lang.String conceptCode = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept categoryConcept = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtTypeAccessImpl]TransformerConstants.CODING_CCW_ADJUDICATION_CATEGORY, [CtVariableReadImpl]conceptCode);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]categoryConcept.getCodingFirstRep().setDisplay([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ccwVariable.getVariable().getLabel());
        [CtReturnImpl]return [CtVariableReadImpl]categoryConcept;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link AdjudicationComponent} will be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param reasonCode
     * 		the value to use for the {@link AdjudicationComponent#getReason()}'s {@link Coding#getCode()} for the resulting {@link Coding}
     * @return the output {@link AdjudicationComponent} for the specified input values
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.AdjudicationComponent createAdjudicationWithReason([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object reasonCode) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Cheating here, since they use the same URL.
        [CtTypeReferenceImpl]java.lang.String categoryConceptCode = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtVariableReadImpl]ccwVariable);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept category = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtTypeAccessImpl]TransformerConstants.CODING_CCW_ADJUDICATION_CATEGORY, [CtVariableReadImpl]categoryConceptCode);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]category.getCodingFirstRep().setDisplay([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ccwVariable.getVariable().getLabel());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.AdjudicationComponent adjudication = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.AdjudicationComponent([CtVariableReadImpl]category);
        [CtInvocationImpl][CtVariableReadImpl]adjudication.setReason([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]rootResource, [CtVariableReadImpl]ccwVariable, [CtVariableReadImpl]reasonCode));
        [CtReturnImpl]return [CtVariableReadImpl]adjudication;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rootResource
     * 		the root FHIR {@link IAnyResource} that the resultant {@link Coding} will
     * 		be contained in
     * @param ccwVariable
     * 		the {@link CcwCodebookVariable} being coded
     * @param code
     * 		the FHIR {@link Coding#getCode()} value to determine a corresponding {@link Coding#getDisplay()} value for
     * @return the {@link Coding#getDisplay()} value to use for the specified {@link CcwCodebookVariable} and {@link Coding#getCode()}, or {@link Optional#empty()} if no
    matching display value could be determined
     */
    private static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> calculateCodingDisplay([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource rootResource, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable ccwVariable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String code) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rootResource == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ccwVariable == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]code == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ccwVariable.getVariable().getValueGroups().isPresent())[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException([CtBinaryOperatorImpl][CtLiteralImpl]"No display values for Variable: " + [CtVariableReadImpl]ccwVariable);

        [CtLocalVariableImpl][CtCommentImpl]/* We know that the specified CCW Variable is coded, but there's no guarantee
        that the Coding's code matches one of the known/allowed Variable values: data
        is messy. When that happens, we log the event and return normally. The log
        event will at least allow for further investigation, if warranted. Also,
        there's a chance that the CCW Variable data itself is messy, and that the
        Coding's code matches more than one value -- we just log those events, too.
         */
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.codebook.model.Value> matchingVariableValues = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ccwVariable.getVariable().getValueGroups().get().stream().flatMap([CtLambdaImpl]([CtParameterImpl] g) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]g.getValues().stream()).filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getCode().equals([CtVariableReadImpl]code)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]matchingVariableValues.size() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]matchingVariableValues.get([CtLiteralImpl]0).getDescription());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]matchingVariableValues.isEmpty()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.codebookLookupMissingFailures.contains([CtVariableReadImpl]ccwVariable)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Note: The race condition here (from concurrent requests) is harmless.
                [CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.codebookLookupMissingFailures.add([CtVariableReadImpl]ccwVariable);
                [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.LOGGER.info([CtLiteralImpl]"No display value match found for {}.{} in resource '{}/{}'.", [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable.class.getSimpleName(), [CtInvocationImpl][CtVariableReadImpl]ccwVariable.name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rootResource.getClass().getSimpleName(), [CtInvocationImpl][CtVariableReadImpl]rootResource.getId());
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]matchingVariableValues.size() > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.codebookLookupDuplicateFailures.contains([CtVariableReadImpl]ccwVariable)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Note: The race condition here (from concurrent requests) is harmless.
                [CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.codebookLookupDuplicateFailures.add([CtVariableReadImpl]ccwVariable);
                [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.LOGGER.info([CtLiteralImpl]"Multiple display value matches found for {}.{} in resource '{}/{}'.", [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.model.codebook.data.CcwCodebookVariable.class.getSimpleName(), [CtInvocationImpl][CtVariableReadImpl]ccwVariable.name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rootResource.getClass().getSimpleName(), [CtInvocationImpl][CtVariableReadImpl]rootResource.getId());
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param beneficiaryPatientId
     * 		the {@link #TransformerConstants.CODING_SYSTEM_CCW_BENE_ID} ID
     * 		value for the {@link Coverage#getBeneficiary()} value to match
     * @param coverageType
     * 		the {@link MedicareSegment} value to match
     * @return a {@link Reference} to the {@link Coverage} resource where {@link Coverage#getPlan()}
    matches {@link #COVERAGE_PLAN} and the other parameters specified also match
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference referenceCoverage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String beneficiaryPatientId, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.MedicareSegment coverageType) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.buildCoverageId([CtVariableReadImpl]coverageType, [CtVariableReadImpl]beneficiaryPatientId));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param patientId
     * 		the {@link #TransformerConstants.CODING_SYSTEM_CCW_BENE_ID} ID value for the
     * 		beneficiary to match
     * @return a {@link Reference} to the {@link Patient} resource that matches the specified
    parameters
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference referencePatient([CtParameterImpl][CtTypeReferenceImpl]java.lang.String patientId) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Patient/%s", [CtVariableReadImpl]patientId));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param beneficiary
     * 		the {@link Beneficiary} to generate a {@link Patient} {@link Reference} for
     * @return a {@link Reference} to the {@link Patient} resource for the specified {@link Beneficiary}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference referencePatient([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.model.rif.Beneficiary beneficiary) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.referencePatient([CtInvocationImpl][CtVariableReadImpl]beneficiary.getBeneficiaryId());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param practitionerNpi
     * 		the {@link Practitioner#getIdentifier()} value to match (where {@link Identifier#getSystem()} is {@value #TransformerConstants.CODING_SYSTEM_NPI_US})
     * @return a {@link Reference} to the {@link Practitioner} resource that matches the specified
    parameters
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference referencePractitioner([CtParameterImpl][CtTypeReferenceImpl]java.lang.String practitionerNpi) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifierReference([CtTypeAccessImpl]TransformerConstants.CODING_NPI_US, [CtVariableReadImpl]practitionerNpi);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param period
     * 		the {@link Period} to adjust
     * @param date
     * 		the {@link LocalDate} to set the {@link Period#getEnd()} value with/to
     */
    static [CtTypeReferenceImpl]void setPeriodEnd([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Period period, [CtParameterImpl][CtTypeReferenceImpl]java.time.LocalDate date) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]period.setEnd([CtInvocationImpl][CtTypeAccessImpl]java.util.Date.from([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]date.atStartOfDay([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault()).toInstant()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param period
     * 		the {@link Period} to adjust
     * @param date
     * 		the {@link LocalDate} to set the {@link Period#getStart()} value with/to
     */
    static [CtTypeReferenceImpl]void setPeriodStart([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Period period, [CtParameterImpl][CtTypeReferenceImpl]java.time.LocalDate date) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]period.setStart([CtInvocationImpl][CtTypeAccessImpl]java.util.Date.from([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]date.atStartOfDay([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault()).toInstant()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param urlText
     * 		the URL or URL portion to be encoded
     * @return a URL-encoded version of the specified text
     */
    static [CtTypeReferenceImpl]java.lang.String urlEncode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String urlText) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.net.URLEncoder.encode([CtVariableReadImpl]urlText, [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8.name());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.UnsupportedEncodingException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * validate the from/thru dates to ensure the from date is before or the same as the thru date
     *
     * @param dateFrom
     * 		start date {@link LocalDate}
     * @param dateThrough
     * 		through date {@link LocalDate} to verify
     */
    static [CtTypeReferenceImpl]void validatePeriodDates([CtParameterImpl][CtTypeReferenceImpl]java.time.LocalDate dateFrom, [CtParameterImpl][CtTypeReferenceImpl]java.time.LocalDate dateThrough) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dateFrom == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dateThrough == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl][CtCommentImpl]// FIXME see CBBD-236 (ETL service fails on some Hospice claims "From
        [CtCommentImpl]// date is after the Through Date")
        [CtCommentImpl]// We are seeing this scenario in production where the from date is
        [CtCommentImpl]// after the through date so we are just logging the error for now.
        if ([CtInvocationImpl][CtVariableReadImpl]dateFrom.isAfter([CtVariableReadImpl]dateThrough))[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.LOGGER.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Error - From Date '%s' is after the Through Date '%s'", [CtVariableReadImpl]dateFrom, [CtVariableReadImpl]dateThrough));

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * validate the <Optional>from/<Optional>thru dates to ensure the from date is before or the same
     * as the thru date
     *
     * @param <Optional>dateFrom
     * 		start date {@link <Optional>LocalDate}
     * @param <Optional>dateThrough
     * 		through date {@link <Optional>LocalDate} to verify
     */
    static [CtTypeReferenceImpl]void validatePeriodDates([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> dateFrom, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> dateThrough) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]dateFrom.isPresent())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]dateThrough.isPresent())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.validatePeriodDates([CtInvocationImpl][CtVariableReadImpl]dateFrom.get(), [CtInvocationImpl][CtVariableReadImpl]dateThrough.get());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds field values to the benefit balance component that are common between the Inpatient and
     * SNF claim types.
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to map the fields into
     * @param coinsuranceDayCount
     * 		BENE_TOT_COINSRNC_DAYS_CNT: a {@link BigDecimal} shared field
     * 		representing the coinsurance day count for the claim
     * @param nonUtilizationDayCount
     * 		CLM_NON_UTLZTN_DAYS_CNT: a {@link BigDecimal} shared field
     * 		representing the non-utilization day count for the claim
     * @param deductibleAmount
     * 		NCH_BENE_IP_DDCTBL_AMT: a {@link BigDecimal} shared field representing
     * 		the deductible amount for the claim
     * @param partACoinsuranceLiabilityAmount
     * 		NCH_BENE_PTA_COINSRNC_LBLTY_AM: a {@link BigDecimal}
     * 		shared field representing the part A coinsurance amount for the claim
     * @param bloodPintsFurnishedQty
     * 		NCH_BLOOD_PNTS_FRNSHD_QTY: a {@link BigDecimal} shared field
     * 		representing the blood pints furnished quantity for the claim
     * @param noncoveredCharge
     * 		NCH_IP_NCVRD_CHRG_AMT: a {@link BigDecimal} shared field representing
     * 		the non-covered charge for the claim
     * @param totalDeductionAmount
     * 		NCH_IP_TOT_DDCTN_AMT: a {@link BigDecimal} shared field
     * 		representing the total deduction amount for the claim
     * @param claimPPSCapitalDisproportionateShareAmt
     * 		CLM_PPS_CPTL_DSPRPRTNT_SHR_AMT: an {@link Optional}&lt;{@link BigDecimal}&gt; shared field representing the claim PPS capital
     * 		disproportionate share amount for the claim
     * @param claimPPSCapitalExceptionAmount
     * 		CLM_PPS_CPTL_EXCPTN_AMT: an {@link Optional}&lt;{@link BigDecimal}&gt; shared field representing the claim PPS capital exception amount for the
     * 		claim
     * @param claimPPSCapitalFSPAmount
     * 		CLM_PPS_CPTL_FSP_AMT: an {@link Optional}&lt;{@link BigDecimal}&gt; shared field representing the claim PPS capital FSP amount for the claim
     * @param claimPPSCapitalIMEAmount
     * 		CLM_PPS_CPTL_IME_AMT: an {@link Optional}&lt;{@link BigDecimal}&gt; shared field representing the claim PPS capital IME amount for the claim
     * @param claimPPSCapitalOutlierAmount
     * 		CLM_PPS_CPTL_OUTLIER_AMT: an {@link Optional}&lt;{@link BigDecimal}&gt; shared field representing the claim PPS capital outlier amount for the
     * 		claim
     * @param claimPPSOldCapitalHoldHarmlessAmount
     * 		CLM_PPS_OLD_CPTL_HLD_HRMLS_AMT: an {@link Optional}&lt;{@link BigDecimal}&gt; shared field representing the claim PPS old capital
     * 		hold harmless amount for the claim
     */
    static [CtTypeReferenceImpl]void addCommonGroupInpatientSNF([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal coinsuranceDayCount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal nonUtilizationDayCount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal deductibleAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal partACoinsuranceLiabilityAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal bloodPintsFurnishedQty, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal noncoveredCharge, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal totalDeductionAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> claimPPSCapitalDisproportionateShareAmt, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> claimPPSCapitalExceptionAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> claimPPSCapitalFSPAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> claimPPSCapitalIMEAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> claimPPSCapitalOutlierAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> claimPPSOldCapitalHoldHarmlessAmount) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent beneTotCoinsrncDaysCntFinancial = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addBenefitBalanceFinancial([CtVariableReadImpl]eob, [CtTypeAccessImpl]BenefitCategory.MEDICAL, [CtTypeAccessImpl]CcwCodebookVariable.BENE_TOT_COINSRNC_DAYS_CNT);
        [CtInvocationImpl][CtVariableReadImpl]beneTotCoinsrncDaysCntFinancial.setUsed([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.UnsignedIntType([CtInvocationImpl][CtVariableReadImpl]coinsuranceDayCount.intValueExact()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent clmNonUtlztnDaysCntFinancial = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addBenefitBalanceFinancial([CtVariableReadImpl]eob, [CtTypeAccessImpl]BenefitCategory.MEDICAL, [CtTypeAccessImpl]CcwCodebookVariable.CLM_NON_UTLZTN_DAYS_CNT);
        [CtInvocationImpl][CtVariableReadImpl]clmNonUtlztnDaysCntFinancial.setUsed([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.UnsignedIntType([CtInvocationImpl][CtVariableReadImpl]nonUtilizationDayCount.intValueExact()));
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_BENE_IP_DDCTBL_AMT, [CtVariableReadImpl]deductibleAmount);
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_BENE_PTA_COINSRNC_LBLTY_AMT, [CtVariableReadImpl]partACoinsuranceLiabilityAmount);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent nchBloodPntsFrnshdQtyInfo = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformation([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_BLOOD_PNTS_FRNSHD_QTY);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity bloodPintsQuantity = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity();
        [CtInvocationImpl][CtVariableReadImpl]bloodPintsQuantity.setValue([CtVariableReadImpl]bloodPintsFurnishedQty);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bloodPintsQuantity.setSystem([CtTypeAccessImpl]TransformerConstants.CODING_SYSTEM_UCUM).setCode([CtTypeAccessImpl]TransformerConstants.CODING_SYSTEM_UCUM_PINT_CODE).setUnit([CtTypeAccessImpl]TransformerConstants.CODING_SYSTEM_UCUM_PINT_DISPLAY);
        [CtInvocationImpl][CtVariableReadImpl]nchBloodPntsFrnshdQtyInfo.setValue([CtVariableReadImpl]bloodPintsQuantity);
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_IP_NCVRD_CHRG_AMT, [CtVariableReadImpl]noncoveredCharge);
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_IP_TOT_DDCTN_AMT, [CtVariableReadImpl]totalDeductionAmount);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimPPSCapitalDisproportionateShareAmt.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_PPS_CPTL_DSPRPRTNT_SHR_AMT, [CtVariableReadImpl]claimPPSCapitalDisproportionateShareAmt);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimPPSCapitalExceptionAmount.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_PPS_CPTL_EXCPTN_AMT, [CtVariableReadImpl]claimPPSCapitalExceptionAmount);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimPPSCapitalFSPAmount.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_PPS_CPTL_FSP_AMT, [CtVariableReadImpl]claimPPSCapitalFSPAmount);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimPPSCapitalIMEAmount.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_PPS_CPTL_IME_AMT, [CtVariableReadImpl]claimPPSCapitalIMEAmount);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimPPSCapitalOutlierAmount.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_PPS_CPTL_OUTLIER_AMT, [CtVariableReadImpl]claimPPSCapitalOutlierAmount);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimPPSOldCapitalHoldHarmlessAmount.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_PPS_OLD_CPTL_HLD_HRMLS_AMT, [CtVariableReadImpl]claimPPSOldCapitalHoldHarmlessAmount);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds EOB information to fields that are common between the Inpatient and SNF claim types.
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that fields will be added to by this method
     * @param admissionTypeCd
     * 		CLM_IP_ADMSN_TYPE_CD: a {@link Character} shared field representing the
     * 		admission type cd for the claim
     * @param sourceAdmissionCd
     * 		CLM_SRC_IP_ADMSN_CD: an {@link Optional}&lt;{@link Character}&gt;
     * 		shared field representing the source admission cd for the claim
     * @param noncoveredStayFromDate
     * 		NCH_VRFD_NCVRD_STAY_FROM_DT: an {@link Optional}&lt;{@link LocalDate}&gt; shared field representing the non-covered stay from date for the claim
     * @param noncoveredStayThroughDate
     * 		NCH_VRFD_NCVRD_STAY_THRU_DT: an {@link Optional}&lt;{@link LocalDate}&gt; shared field representing the non-covered stay through date for the claim
     * @param coveredCareThroughDate
     * 		NCH_ACTV_OR_CVRD_LVL_CARE_THRU: an {@link Optional}&lt;{@link LocalDate}&gt; shared field representing the covered stay through date for the claim
     * @param medicareBenefitsExhaustedDate
     * 		NCH_BENE_MDCR_BNFTS_EXHTD_DT_I: an {@link Optional}&lt;{@link LocalDate}&gt; shared field representing the medicare benefits
     * 		exhausted date for the claim
     * @param diagnosisRelatedGroupCd
     * 		CLM_DRG_CD: an {@link Optional}&lt;{@link String}&gt; shared
     * 		field representing the non-covered stay from date for the claim
     */
    static [CtTypeReferenceImpl]void addCommonEobInformationInpatientSNF([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Character admissionTypeCd, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> sourceAdmissionCd, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> noncoveredStayFromDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> noncoveredStayThroughDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> coveredCareThroughDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> medicareBenefitsExhaustedDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisRelatedGroupCd) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// admissionTypeCd
        gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformationWithCode([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_IP_ADMSN_TYPE_CD, [CtTypeAccessImpl]CcwCodebookVariable.CLM_IP_ADMSN_TYPE_CD, [CtVariableReadImpl]admissionTypeCd);
        [CtIfImpl][CtCommentImpl]// sourceAdmissionCd
        if ([CtInvocationImpl][CtVariableReadImpl]sourceAdmissionCd.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformationWithCode([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_SRC_IP_ADMSN_CD, [CtTypeAccessImpl]CcwCodebookVariable.CLM_SRC_IP_ADMSN_CD, [CtVariableReadImpl]sourceAdmissionCd);
        }
        [CtIfImpl][CtCommentImpl]// noncoveredStayFromDate & noncoveredStayThroughDate
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]noncoveredStayFromDate.isPresent() || [CtInvocationImpl][CtVariableReadImpl]noncoveredStayThroughDate.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.validatePeriodDates([CtVariableReadImpl]noncoveredStayFromDate, [CtVariableReadImpl]noncoveredStayThroughDate);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent nchVrfdNcvrdStayInfo = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformation([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_VRFD_NCVRD_STAY_FROM_DT);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Period nchVrfdNcvrdStayPeriod = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Period();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]noncoveredStayFromDate.isPresent())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]nchVrfdNcvrdStayPeriod.setStart([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]noncoveredStayFromDate.get()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY);

            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]noncoveredStayThroughDate.isPresent())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]nchVrfdNcvrdStayPeriod.setEnd([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]noncoveredStayThroughDate.get()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY);

            [CtInvocationImpl][CtVariableReadImpl]nchVrfdNcvrdStayInfo.setTiming([CtVariableReadImpl]nchVrfdNcvrdStayPeriod);
        }
        [CtIfImpl][CtCommentImpl]// coveredCareThroughDate
        if ([CtInvocationImpl][CtVariableReadImpl]coveredCareThroughDate.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent nchActvOrCvrdLvlCareThruInfo = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformation([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_ACTV_OR_CVRD_LVL_CARE_THRU);
            [CtInvocationImpl][CtVariableReadImpl]nchActvOrCvrdLvlCareThruInfo.setTiming([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.DateType([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]coveredCareThroughDate.get())));
        }
        [CtIfImpl][CtCommentImpl]// medicareBenefitsExhaustedDate
        if ([CtInvocationImpl][CtVariableReadImpl]medicareBenefitsExhaustedDate.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.SupportingInformationComponent nchBeneMdcrBnftsExhtdDtIInfo = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformation([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_BENE_MDCR_BNFTS_EXHTD_DT_I);
            [CtInvocationImpl][CtVariableReadImpl]nchBeneMdcrBnftsExhtdDtIInfo.setTiming([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.DateType([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]medicareBenefitsExhaustedDate.get())));
        }
        [CtIfImpl][CtCommentImpl]// diagnosisRelatedGroupCd
        if ([CtInvocationImpl][CtVariableReadImpl]diagnosisRelatedGroupCd.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]/* FIXME This is an invalid DiagnosisComponent, since it's missing a (required)
            ICD code. Instead, stick the DRG on the claim's primary/first diagnosis. SamhsaMatcher uses this field so if this is updated you'll need to update that as well.
             */
            [CtInvocationImpl][CtVariableReadImpl]eob.addDiagnosis().setPackageCode([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_DRG_CD, [CtVariableReadImpl]diagnosisRelatedGroupCd));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * maps a blue button claim type to a FHIR claim type
     *
     * @param eobType
     * 		the {@link CodeableConcept} that will get remapped
     * @param blueButtonClaimType
     * 		the blue button {@link ClaimType} we are mapping from
     * @param ccwNearLineRecordIdCode
     * 		if present, the blue button near line id code {@link Optional}&lt;{@link Character}&gt; gets remapped to a ccw record id code
     * @param ccwClaimTypeCode
     * 		if present, the blue button claim type code {@link Optional}&lt;{@link String}&gt; gets remapped to a nch claim type code
     */
    static [CtTypeReferenceImpl]void mapEobType([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType blueButtonClaimType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> ccwNearLineRecordIdCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> ccwClaimTypeCode) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// map blue button claim type code into a nch claim type
        if ([CtInvocationImpl][CtVariableReadImpl]ccwClaimTypeCode.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getType().addCoding([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_CLM_TYPE_CD, [CtVariableReadImpl]ccwClaimTypeCode));
        }
        [CtInvocationImpl][CtCommentImpl]// This Coding MUST always be present as it's the only one we can definitely map
        [CtCommentImpl]// for all 8 of our claim types.
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getType().addCoding().setSystem([CtTypeAccessImpl]TransformerConstants.CODING_SYSTEM_BBAPI_EOB_TYPE).setCode([CtInvocationImpl][CtVariableReadImpl]blueButtonClaimType.name());
        [CtLocalVariableImpl][CtCommentImpl]// Map a Coding for FHIR's ClaimType coding system, if we can.
        [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.codesystems.ClaimType fhirClaimType;
        [CtSwitchImpl]switch ([CtVariableReadImpl]blueButtonClaimType) {
            [CtCaseImpl]case [CtFieldReadImpl]CARRIER :
            [CtCaseImpl]case [CtFieldReadImpl]OUTPATIENT :
                [CtAssignmentImpl][CtVariableWriteImpl]fhirClaimType = [CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType;
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]INPATIENT :
            [CtCaseImpl]case [CtFieldReadImpl]HOSPICE :
            [CtCaseImpl]case [CtFieldReadImpl]SNF :
                [CtAssignmentImpl][CtVariableWriteImpl]fhirClaimType = [CtFieldReadImpl]org.hl7.fhir.dstu3.model.codesystems.ClaimType.INSTITUTIONAL;
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]PDE :
                [CtAssignmentImpl][CtVariableWriteImpl]fhirClaimType = [CtFieldReadImpl]org.hl7.fhir.dstu3.model.codesystems.ClaimType.PHARMACY;
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]HHA :
            [CtCaseImpl]case [CtFieldReadImpl]DME :
                [CtAssignmentImpl][CtVariableWriteImpl]fhirClaimType = [CtLiteralImpl]null;
                [CtBreakImpl][CtCommentImpl]// FUTURE these blue button claim types currently have no equivalent
                [CtCommentImpl]// CODING_FHIR_CLAIM_TYPE mapping
                break;
            [CtCaseImpl]default :
                [CtThrowImpl][CtCommentImpl]// unknown claim type
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fhirClaimType != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getType().addCoding([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding([CtInvocationImpl][CtVariableReadImpl]fhirClaimType.getSystem(), [CtInvocationImpl][CtVariableReadImpl]fhirClaimType.toCode(), [CtInvocationImpl][CtVariableReadImpl]fhirClaimType.getDisplay()));

        [CtIfImpl][CtCommentImpl]// map blue button near line record id to a ccw record id code
        if ([CtInvocationImpl][CtVariableReadImpl]ccwNearLineRecordIdCode.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getType().addCoding([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_NEAR_LINE_REC_IDENT_CD, [CtVariableReadImpl]ccwNearLineRecordIdCode));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common group level header fields between all claim types
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param claimId
     * 		CLM_ID
     * @param beneficiaryId
     * 		BENE_ID
     * @param claimType
     * 		{@link ClaimType} to process
     * @param claimGroupId
     * 		CLM_GRP_ID
     * @param coverageType
     * 		{@link MedicareSegment}
     * @param dateFrom
     * 		CLM_FROM_DT
     * @param dateThrough
     * 		CLM_THRU_DT
     * @param paymentAmount
     * 		CLM_PMT_AMT
     * @param finalAction
     * 		FINAL_ACTION
     */
    static [CtTypeReferenceImpl]void mapEobCommonClaimHeaderData([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String claimId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String beneficiaryId, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType claimType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String claimGroupId, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.MedicareSegment coverageType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> dateFrom, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> dateThrough, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> paymentAmount, [CtParameterImpl][CtTypeReferenceImpl]char finalAction) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]eob.setId([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.buildEobId([CtVariableReadImpl]claimType, [CtVariableReadImpl]claimId));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimType.equals([CtTypeAccessImpl]ClaimType.PDE))[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]eob.addIdentifier([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifier([CtTypeAccessImpl]CcwCodebookVariable.PDE_ID, [CtVariableReadImpl]claimId));
        else[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]eob.addIdentifier([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifier([CtTypeAccessImpl]CcwCodebookVariable.CLM_ID, [CtVariableReadImpl]claimId));

        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.addIdentifier().setSystem([CtTypeAccessImpl]TransformerConstants.IDENTIFIER_SYSTEM_BBAPI_CLAIM_GROUP_ID).setValue([CtVariableReadImpl]claimGroupId);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getInsurance().setCoverage([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.referenceCoverage([CtVariableReadImpl]beneficiaryId, [CtVariableReadImpl]coverageType));
        [CtInvocationImpl][CtVariableReadImpl]eob.setPatient([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.referencePatient([CtVariableReadImpl]beneficiaryId));
        [CtSwitchImpl]switch ([CtVariableReadImpl]finalAction) {
            [CtCaseImpl]case [CtLiteralImpl]'F' :
                [CtInvocationImpl][CtVariableReadImpl]eob.setStatus([CtTypeAccessImpl]ExplanationOfBenefitStatus.ACTIVE);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtLiteralImpl]'N' :
                [CtInvocationImpl][CtVariableReadImpl]eob.setStatus([CtTypeAccessImpl]ExplanationOfBenefitStatus.CANCELLED);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtThrowImpl][CtCommentImpl]// unknown final action value
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dateFrom.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.validatePeriodDates([CtVariableReadImpl]dateFrom, [CtVariableReadImpl]dateThrough);
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.setPeriodStart([CtInvocationImpl][CtVariableReadImpl]eob.getBillablePeriod(), [CtInvocationImpl][CtVariableReadImpl]dateFrom.get());
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.setPeriodEnd([CtInvocationImpl][CtVariableReadImpl]eob.getBillablePeriod(), [CtInvocationImpl][CtVariableReadImpl]dateThrough.get());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]paymentAmount.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getPayment().setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]paymentAmount));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common group level data elements between the {@link CarrierClaim} and {@link DMEClaim} claim types to FHIR. The method parameter fields from {@link CarrierClaim} and {@link DMEClaim} are listed below and their corresponding RIF CCW fields (denoted in all CAPS below
     * from {@link CarrierClaimColumn} and {@link DMEClaimColumn}).
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param benficiaryId
     * 		BEME_ID, *
     * @param carrierNumber
     * 		CARR_NUM,
     * @param clinicalTrialNumber
     * 		CLM_CLNCL_TRIL_NUM,
     * @param beneficiaryPartBDeductAmount
     * 		CARR_CLM_CASH_DDCTBL_APLD_AMT,
     * @param paymentDenialCode
     * 		CARR_CLM_PMT_DNL_CD,
     * @param referringPhysicianNpi
     * 		RFR_PHYSN_NPI
     * @param providerAssignmentIndicator
     * 		CARR_CLM_PRVDR_ASGNMT_IND_SW,
     * @param providerPaymentAmount
     * 		NCH_CLM_PRVDR_PMT_AMT,
     * @param beneficiaryPaymentAmount
     * 		NCH_CLM_BENE_PMT_AMT,
     * @param submittedChargeAmount
     * 		NCH_CARR_CLM_SBMTD_CHRG_AMT,
     * @param allowedChargeAmount
     * 		NCH_CARR_CLM_ALOWD_AMT,
     */
    static [CtTypeReferenceImpl]void mapEobCommonGroupCarrierDME([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String beneficiaryId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String carrierNumber, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> clinicalTrialNumber, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal beneficiaryPartBDeductAmount, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String paymentDenialCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> referringPhysicianNpi, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> providerAssignmentIndicator, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal providerPaymentAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal beneficiaryPaymentAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal submittedChargeAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal allowedChargeAmount) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]eob.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionIdentifier([CtTypeAccessImpl]CcwCodebookVariable.CARR_NUM, [CtVariableReadImpl]carrierNumber));
        [CtInvocationImpl][CtVariableReadImpl]eob.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CARR_CLM_PMT_DNL_CD, [CtVariableReadImpl]paymentDenialCode));
        [CtIfImpl][CtCommentImpl]/* Referrals are represented as contained resources, since they share the
        lifecycle and identity of their containing EOB.
         */
        if ([CtInvocationImpl][CtVariableReadImpl]referringPhysicianNpi.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ReferralRequest referral = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ReferralRequest();
            [CtInvocationImpl][CtVariableReadImpl]referral.setStatus([CtTypeAccessImpl]ReferralRequestStatus.COMPLETED);
            [CtInvocationImpl][CtVariableReadImpl]referral.setSubject([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.referencePatient([CtVariableReadImpl]beneficiaryId));
            [CtInvocationImpl][CtVariableReadImpl]referral.setRequester([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ReferralRequest.ReferralRequestRequesterComponent([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.referencePractitioner([CtInvocationImpl][CtVariableReadImpl]referringPhysicianNpi.get())));
            [CtInvocationImpl][CtVariableReadImpl]referral.addRecipient([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.referencePractitioner([CtInvocationImpl][CtVariableReadImpl]referringPhysicianNpi.get()));
            [CtInvocationImpl][CtCommentImpl]// Set the ReferralRequest as a contained resource in the EOB:
            [CtVariableReadImpl]eob.setReferral([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference([CtVariableReadImpl]referral));
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]providerAssignmentIndicator.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]eob.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.ASGMNTCD, [CtVariableReadImpl]providerAssignmentIndicator));
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]clinicalTrialNumber.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]eob.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionIdentifier([CtTypeAccessImpl]CcwCodebookVariable.CLM_CLNCL_TRIL_NUM, [CtVariableReadImpl]clinicalTrialNumber));
        }
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CARR_CLM_CASH_DDCTBL_APLD_AMT, [CtVariableReadImpl]beneficiaryPartBDeductAmount);
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_CLM_PRVDR_PMT_AMT, [CtVariableReadImpl]providerPaymentAmount);
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_CLM_BENE_PMT_AMT, [CtVariableReadImpl]beneficiaryPaymentAmount);
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_CARR_CLM_SBMTD_CHRG_AMT, [CtVariableReadImpl]submittedChargeAmount);
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_CARR_CLM_ALOWD_AMT, [CtVariableReadImpl]allowedChargeAmount);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common item level data elements between the {@link CarrierClaimLine} and {@link DMEClaimLine} claim types to FHIR. The method parameter fields from {@link CarrierClaimLine}
     * and {@link DMEClaimLine} are listed below and their corresponding RIF CCW fields (denoted in
     * all CAPS below from {@link CarrierClaimColumn} and {@link DMEClaimColumn}).
     *
     * @param item
     * 		the {@ ItemComponent} to modify
     * @param eob
     * 		the {@ ExplanationOfBenefit} to modify
     * @param claimId
     * 		CLM_ID,
     * @param serviceCount
     * 		LINE_SRVC_CNT,
     * @param placeOfServiceCode
     * 		LINE_PLACE_OF_SRVC_CD,
     * @param firstExpenseDate
     * 		LINE_1ST_EXPNS_DT,
     * @param lastExpenseDate
     * 		LINE_LAST_EXPNS_DT,
     * @param beneficiaryPaymentAmount
     * 		LINE_BENE_PMT_AMT,
     * @param providerPaymentAmount
     * 		LINE_PRVDR_PMT_AMT,
     * @param beneficiaryPartBDeductAmount
     * 		LINE_BENE_PTB_DDCTBL_AMT,
     * @param primaryPayerCode
     * 		LINE_BENE_PRMRY_PYR_CD,
     * @param primaryPayerPaidAmount
     * 		LINE_BENE_PRMRY_PYR_PD_AMT,
     * @param betosCode
     * 		BETOS_CD,
     * @param paymentAmount
     * 		LINE_NCH_PMT_AMT,
     * @param paymentCode
     * 		LINE_PMT_80_100_CD,
     * @param coinsuranceAmount
     * 		LINE_COINSRNC_AMT,
     * @param submittedChargeAmount
     * 		LINE_SBMTD_CHRG_AMT,
     * @param allowedChargeAmount
     * 		LINE_ALOWD_CHRG_AMT,
     * @param processingIndicatorCode
     * 		LINE_PRCSG_IND_CD,
     * @param serviceDeductibleCode
     * 		LINE_SERVICE_DEDUCTIBLE,
     * @param diagnosisCode
     * 		LINE_ICD_DGNS_CD,
     * @param diagnosisCodeVersion
     * 		LINE_ICD_DGNS_VRSN_CD,
     * @param hctHgbTestTypeCode
     * 		LINE_HCT_HGB_TYPE_CD
     * @param hctHgbTestResult
     * 		LINE_HCT_HGB_RSLT_NUM,
     * @param cmsServiceTypeCode
     * 		LINE_CMS_TYPE_SRVC_CD,
     * @param nationalDrugCode
     * 		LINE_NDC_CD,
     * @param beneficiaryId
     * 		BENE_ID,
     * @param referringPhysicianNpi
     * 		RFR_PHYSN_NPI
     * @return the {@link ItemComponent}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent mapEobCommonItemCarrierDME([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent item, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String claimId, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal serviceCount, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String placeOfServiceCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> firstExpenseDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> lastExpenseDate, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal beneficiaryPaymentAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal providerPaymentAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal beneficiaryPartBDeductAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> primaryPayerCode, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal primaryPayerPaidAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> betosCode, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal paymentAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> paymentCode, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal coinsuranceAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal submittedChargeAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal allowedChargeAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> processingIndicatorCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> serviceDeductibleCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisCodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> hctHgbTestTypeCode, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal hctHgbTestResult, [CtParameterImpl][CtTypeReferenceImpl]char cmsServiceTypeCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> nationalDrugCode) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.SimpleQuantity serviceCnt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.SimpleQuantity();
        [CtInvocationImpl][CtVariableReadImpl]serviceCnt.setValue([CtVariableReadImpl]serviceCount);
        [CtInvocationImpl][CtVariableReadImpl]item.setQuantity([CtVariableReadImpl]serviceCnt);
        [CtInvocationImpl][CtVariableReadImpl]item.setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.LINE_CMS_TYPE_SRVC_CD, [CtVariableReadImpl]cmsServiceTypeCode));
        [CtInvocationImpl][CtVariableReadImpl]item.setLocation([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.LINE_PLACE_OF_SRVC_CD, [CtVariableReadImpl]placeOfServiceCode));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]betosCode.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]item.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.BETOS_CD, [CtVariableReadImpl]betosCode));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]firstExpenseDate.isPresent() && [CtInvocationImpl][CtVariableReadImpl]lastExpenseDate.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.validatePeriodDates([CtVariableReadImpl]firstExpenseDate, [CtVariableReadImpl]lastExpenseDate);
            [CtInvocationImpl][CtVariableReadImpl]item.setServiced([CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Period().setStart([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]firstExpenseDate.get()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY).setEnd([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]lastExpenseDate.get()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.AdjudicationComponent adjudicationForPayment = [CtInvocationImpl][CtVariableReadImpl]item.addAdjudication();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]adjudicationForPayment.setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_NCH_PMT_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]paymentAmount));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]paymentCode.isPresent())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]adjudicationForPayment.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.LINE_PMT_80_100_CD, [CtVariableReadImpl]paymentCode));

        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_BENE_PMT_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]beneficiaryPaymentAmount));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_PRVDR_PMT_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]providerPaymentAmount));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_BENE_PTB_DDCTBL_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]beneficiaryPartBDeductAmount));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]primaryPayerCode.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]item.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.LINE_BENE_PRMRY_PYR_CD, [CtVariableReadImpl]primaryPayerCode));
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_BENE_PRMRY_PYR_PD_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]primaryPayerPaidAmount));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_COINSRNC_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]coinsuranceAmount));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_SBMTD_CHRG_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]submittedChargeAmount));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.LINE_ALOWD_CHRG_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]allowedChargeAmount));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]processingIndicatorCode.isPresent())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]item.addAdjudication([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationWithReason([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.LINE_PRCSG_IND_CD, [CtVariableReadImpl]processingIndicatorCode));

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]serviceDeductibleCode.isPresent())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]item.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.LINE_SERVICE_DEDUCTIBLE, [CtVariableReadImpl]serviceDeductibleCode));

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis> lineDiagnosis = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisCode, [CtVariableReadImpl]diagnosisCodeVersion);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]lineDiagnosis.isPresent())[CtBlockImpl]
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addDiagnosisLink([CtVariableReadImpl]eob, [CtVariableReadImpl]item, [CtInvocationImpl][CtVariableReadImpl]lineDiagnosis.get());

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]hctHgbTestTypeCode.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Observation hctHgbObservation = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Observation();
            [CtInvocationImpl][CtVariableReadImpl]hctHgbObservation.setStatus([CtTypeAccessImpl]ObservationStatus.UNKNOWN);
            [CtInvocationImpl][CtVariableReadImpl]hctHgbObservation.setCode([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.LINE_HCT_HGB_TYPE_CD, [CtVariableReadImpl]hctHgbTestTypeCode));
            [CtInvocationImpl][CtVariableReadImpl]hctHgbObservation.setValue([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity().setValue([CtVariableReadImpl]hctHgbTestResult));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension hctHgbObservationReference = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.calculateVariableReferenceUrl([CtTypeAccessImpl]CcwCodebookVariable.LINE_HCT_HGB_RSLT_NUM), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference([CtVariableReadImpl]hctHgbObservation));
            [CtInvocationImpl][CtVariableReadImpl]item.addExtension([CtVariableReadImpl]hctHgbObservationReference);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nationalDrugCode.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addExtensionCoding([CtVariableReadImpl]item, [CtTypeAccessImpl]TransformerConstants.CODING_NDC, [CtTypeAccessImpl]TransformerConstants.CODING_NDC, [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.retrieveFDADrugCodeDisplay([CtInvocationImpl][CtVariableReadImpl]nationalDrugCode.get()), [CtInvocationImpl][CtVariableReadImpl]nationalDrugCode.get());
        }
        [CtReturnImpl]return [CtVariableReadImpl]item;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common item level data elements between the {@link InpatientClaimLine} {@link OutpatientClaimLine} {@link HospiceClaimLine} {@link HHAClaimLine}and {@link SNFClaimLine}
     * claim types to FHIR. The method parameter fields from {@link InpatientClaimLine} {@link OutpatientClaimLine} {@link HospiceClaimLine} {@link HHAClaimLine}and {@link SNFClaimLine} are
     * listed below and their corresponding RIF CCW fields (denoted in all CAPS below from {@link InpatientClaimColumn} {@link OutpatientClaimColumn} {@link HopsiceClaimColumn} {@link HHAClaimColumn} and {@link SNFClaimColumn}).
     *
     * @param item
     * 		the {@ ItemComponent} to modify
     * @param eob
     * 		the {@ ExplanationOfBenefit} to modify
     * @param revenueCenterCode
     * 		REV_CNTR,
     * @param rateAmount
     * 		REV_CNTR_RATE_AMT,
     * @param totalChargeAmount
     * 		REV_CNTR_TOT_CHRG_AMT,
     * @param nonCoveredChargeAmount
     * 		REV_CNTR_NCVRD_CHRG_AMT,
     * @param unitCount
     * 		REV_CNTR_UNIT_CNT,
     * @param nationalDrugCodeQuantity
     * 		REV_CNTR_NDC_QTY,
     * @param nationalDrugCodeQualifierCode
     * 		REV_CNTR_NDC_QTY_QLFR_CD,
     * @param revenueCenterRenderingPhysicianNPI
     * 		RNDRNG_PHYSN_NPI
     * @return the {@link ItemComponent}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent mapEobCommonItemRevenue([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent item, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String revenueCenterCode, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal rateAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal totalChargeAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal nonCoveredChargeAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal unitCount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> nationalDrugCodeQuantity, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> nationalDrugCodeQualifierCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> revenueCenterRenderingPhysicianNPI) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]item.setRevenue([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR, [CtVariableReadImpl]revenueCenterCode));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR_RATE_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]rateAmount));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR_TOT_CHRG_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]totalChargeAmount));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR_NCVRD_CHRG_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]nonCoveredChargeAmount));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.SimpleQuantity qty = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.SimpleQuantity();
        [CtInvocationImpl][CtVariableReadImpl]qty.setValue([CtVariableReadImpl]unitCount);
        [CtInvocationImpl][CtVariableReadImpl]item.setQuantity([CtVariableReadImpl]qty);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nationalDrugCodeQualifierCode.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* TODO: Is NDC count only ever present when line quantity isn't set? Depending
            on that, it may be that we should stop using this as an extension and instead
            set the code & system on the FHIR quantity field.
             */
            [CtCommentImpl]// TODO Shouldn't this be part of the same Extension with the NDC code itself?
            [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension drugQuantityExtension = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionQuantity([CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR_NDC_QTY, [CtVariableReadImpl]nationalDrugCodeQuantity);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity drugQuantity = [CtInvocationImpl](([CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Quantity) ([CtVariableReadImpl]drugQuantityExtension.getValue()));
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.setQuantityUnitInfo([CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR_NDC_QTY_QLFR_CD, [CtVariableReadImpl]nationalDrugCodeQualifierCode, [CtVariableReadImpl]eob, [CtVariableReadImpl]drugQuantity);
            [CtInvocationImpl][CtVariableReadImpl]item.addExtension([CtVariableReadImpl]drugQuantityExtension);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]revenueCenterRenderingPhysicianNPI.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addCareTeamPractitioner([CtVariableReadImpl]eob, [CtVariableReadImpl]item, [CtTypeAccessImpl]TransformerConstants.CODING_NPI_US, [CtInvocationImpl][CtVariableReadImpl]revenueCenterRenderingPhysicianNPI.get(), [CtTypeAccessImpl]ClaimCareteamrole.PRIMARY);
        }
        [CtReturnImpl]return [CtVariableReadImpl]item;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common item level data elements between the {@link OutpatientClaimLine} {@link HospiceClaimLine} and {@link HHAClaimLine} claim types to FHIR. The method parameter fields
     * from {@link OutpatientClaimLine} {@link HospiceClaimLine} and {@link HHAClaimLine} are listed
     * below and their corresponding RIF CCW fields (denoted in all CAPS below from {@link OutpatientClaimColumn} {@link HopsiceClaimColumn} and {@link HHAClaimColumn}.
     *
     * @param item
     * 		the {@ ItemComponent} to modify
     * @param revenueCenterDate
     * 		REV_CNTR_DT,
     * @param paymentAmount
     * 		REV_CNTR_PMT_AMT_AMT
     */
    static [CtTypeReferenceImpl]void mapEobCommonItemRevenueOutHHAHospice([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent item, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> revenueCenterDate, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal paymentAmount) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]revenueCenterDate.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]item.setServiced([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.DateType().setValue([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]revenueCenterDate.get())));
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.addAdjudication().setCategory([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createAdjudicationCategory([CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR_PMT_AMT_AMT)).setAmount([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]paymentAmount));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common group level data elements between the {@link InpatientClaim}, {@link OutpatientClaim} and {@link SNFClaim} claim types to FHIR. The method parameter fields from
     * {@link InpatientClaim}, {@link OutpatientClaim} and {@link SNFClaim} are listed below and their
     * corresponding RIF CCW fields (denoted in all CAPS below from {@link InpatientClaimColumn}
     * {@link OutpatientClaimColumn}and {@link SNFClaimColumn}).
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param beneficiaryId
     * 		BENE_ID, *
     * @param carrierNumber
     * 		CARR_NUM,
     * @param clinicalTrialNumber
     * 		CLM_CLNCL_TRIL_NUM,
     * @param beneficiaryPartBDeductAmount
     * 		CARR_CLM_CASH_DDCTBL_APLD_AMT,
     * @param paymentDenialCode
     * 		CARR_CLM_PMT_DNL_CD,
     * @param referringPhysicianNpi
     * 		RFR_PHYSN_NPI
     * @param providerAssignmentIndicator
     * 		CARR_CLM_PRVDR_ASGNMT_IND_SW,
     * @param providerPaymentAmount
     * 		NCH_CLM_PRVDR_PMT_AMT,
     * @param beneficiaryPaymentAmount
     * 		NCH_CLM_BENE_PMT_AMT,
     * @param submittedChargeAmount
     * 		NCH_CARR_CLM_SBMTD_CHRG_AMT,
     * @param allowedChargeAmount
     * 		NCH_CARR_CLM_ALOWD_AMT,
     */
    static [CtTypeReferenceImpl]void mapEobCommonGroupInpOutSNF([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal bloodDeductibleLiabilityAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> operatingPhysicianNpi, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> otherPhysicianNpi, [CtParameterImpl][CtTypeReferenceImpl]char claimQueryCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> mcoPaidSw) [CtBlockImpl]{
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_BENE_BLOOD_DDCTBL_LBLTY_AM, [CtVariableReadImpl]bloodDeductibleLiabilityAmount);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]operatingPhysicianNpi.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addCareTeamPractitioner([CtVariableReadImpl]eob, [CtLiteralImpl]null, [CtTypeAccessImpl]TransformerConstants.CODING_NPI_US, [CtInvocationImpl][CtVariableReadImpl]operatingPhysicianNpi.get(), [CtTypeAccessImpl]ClaimCareteamrole.ASSIST);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]otherPhysicianNpi.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addCareTeamPractitioner([CtVariableReadImpl]eob, [CtLiteralImpl]null, [CtTypeAccessImpl]TransformerConstants.CODING_NPI_US, [CtInvocationImpl][CtVariableReadImpl]otherPhysicianNpi.get(), [CtTypeAccessImpl]ClaimCareteamrole.OTHER);
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getBillablePeriod().addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLAIM_QUERY_CD, [CtVariableReadImpl]claimQueryCode));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]mcoPaidSw.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformationWithCode([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_MCO_PD_SW, [CtTypeAccessImpl]CcwCodebookVariable.CLM_MCO_PD_SW, [CtVariableReadImpl]mcoPaidSw);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common group level data elements between the {@link InpatientClaimLine} {@link OutpatientClaimLine} {@link HospiceClaimLine} {@link HHAClaimLine}and {@link SNFClaimLine}
     * claim types to FHIR. The method parameter fields from {@link InpatientClaimLine} {@link OutpatientClaimLine} {@link HospiceClaimLine} {@link HHAClaimLine}and {@link SNFClaimLine} are
     * listed below and their corresponding RIF CCW fields (denoted in all CAPS below from {@link InpatientClaimColumn} {@link OutpatientClaimColumn} {@link HopsiceClaimColumn} {@link HHAClaimColumn} and {@link SNFClaimColumn}).
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param organizationNpi
     * 		ORG_NPI_NUM,
     * @param claimFacilityTypeCode
     * 		CLM_FAC_TYPE_CD,
     * @param claimFrequencyCode
     * 		CLM_FREQ_CD,
     * @param claimNonPaymentReasonCode
     * 		CLM_MDCR_NON_PMT_RSN_CD,
     * @param patientDischargeStatusCode
     * 		PTNT_DSCHRG_STUS_CD,
     * @param claimServiceClassificationTypeCode
     * 		CLM_SRVC_CLSFCTN_TYPE_CD,
     * @param claimPrimaryPayerCode
     * 		NCH_PRMRY_PYR_CD,
     * @param attendingPhysicianNpi
     * 		AT_PHYSN_NPI,
     * @param totalChargeAmount
     * 		CLM_TOT_CHRG_AMT,
     * @param primaryPayerPaidAmount
     * 		NCH_PRMRY_PYR_CLM_PD_AMT,
     * @param fiscalIntermediaryNumber
     * 		FI_NUM
     */
    static [CtTypeReferenceImpl]void mapEobCommonGroupInpOutHHAHospiceSNF([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> organizationNpi, [CtParameterImpl][CtTypeReferenceImpl]char claimFacilityTypeCode, [CtParameterImpl][CtTypeReferenceImpl]char claimFrequencyCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> claimNonPaymentReasonCode, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String patientDischargeStatusCode, [CtParameterImpl][CtTypeReferenceImpl]char claimServiceClassificationTypeCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> claimPrimaryPayerCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> attendingPhysicianNpi, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal totalChargeAmount, [CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal primaryPayerPaidAmount, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> fiscalIntermediaryNumber) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]organizationNpi.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]eob.setOrganization([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifierReference([CtTypeAccessImpl]TransformerConstants.CODING_NPI_US, [CtInvocationImpl][CtVariableReadImpl]organizationNpi.get()));
            [CtInvocationImpl][CtVariableReadImpl]eob.setFacility([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifierReference([CtTypeAccessImpl]TransformerConstants.CODING_NPI_US, [CtInvocationImpl][CtVariableReadImpl]organizationNpi.get()));
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eob.getFacility().addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_FAC_TYPE_CD, [CtVariableReadImpl]claimFacilityTypeCode));
        [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformationWithCode([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_FREQ_CD, [CtTypeAccessImpl]CcwCodebookVariable.CLM_FREQ_CD, [CtVariableReadImpl]claimFrequencyCode);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimNonPaymentReasonCode.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]eob.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_MDCR_NON_PMT_RSN_CD, [CtVariableReadImpl]claimNonPaymentReasonCode));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]patientDischargeStatusCode.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformationWithCode([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.PTNT_DSCHRG_STUS_CD, [CtTypeAccessImpl]CcwCodebookVariable.PTNT_DSCHRG_STUS_CD, [CtVariableReadImpl]patientDischargeStatusCode);
        }
        [CtInvocationImpl][CtCommentImpl]// FIXME move into the mapType(...) method
        [CtInvocationImpl][CtVariableReadImpl]eob.getType().addCoding([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.CLM_SRVC_CLSFCTN_TYPE_CD, [CtVariableReadImpl]claimServiceClassificationTypeCode));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimPrimaryPayerCode.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addInformationWithCode([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.NCH_PRMRY_PYR_CD, [CtTypeAccessImpl]CcwCodebookVariable.NCH_PRMRY_PYR_CD, [CtInvocationImpl][CtVariableReadImpl]claimPrimaryPayerCode.get());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]attendingPhysicianNpi.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addCareTeamPractitioner([CtVariableReadImpl]eob, [CtLiteralImpl]null, [CtTypeAccessImpl]TransformerConstants.CODING_NPI_US, [CtInvocationImpl][CtVariableReadImpl]attendingPhysicianNpi.get(), [CtTypeAccessImpl]ClaimCareteamrole.PRIMARY);
        }
        [CtInvocationImpl][CtVariableReadImpl]eob.setTotalCost([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createMoney([CtVariableReadImpl]totalChargeAmount));
        [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addAdjudicationTotal([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.PRPAYAMT, [CtVariableReadImpl]primaryPayerPaidAmount);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fiscalIntermediaryNumber.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]eob.addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionIdentifier([CtTypeAccessImpl]CcwCodebookVariable.FI_NUM, [CtVariableReadImpl]fiscalIntermediaryNumber));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common group level data elements between the {@link InpatientClaim} {@link HHAClaim} {@link HospiceClaim} and {@link SNFClaim} claim types to FHIR. The method parameter
     * fields from {@link InpatientClaim} {@link HHAClaim} {@link HospiceClaim} and {@link SNFClaim}
     * are listed below and their corresponding RIF CCW fields (denoted in all CAPS below from {@link InpatientClaimColumn} {@link HHAClaimColumn} {@link HospiceColumn} and {@link SNFClaimColumn}).
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} to modify
     * @param claimAdmissionDate
     * 		CLM_ADMSN_DT,
     * @param benficiaryDischargeDate,
     * @param utilizedDays
     * 		CLM_UTLZTN_CNT,
     * @return the {@link ExplanationOfBenefit}
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit mapEobCommonGroupInpHHAHospiceSNF([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> claimAdmissionDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> beneficiaryDischargeDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.math.BigDecimal> utilizedDays) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]claimAdmissionDate.isPresent() || [CtInvocationImpl][CtVariableReadImpl]beneficiaryDischargeDate.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.validatePeriodDates([CtVariableReadImpl]claimAdmissionDate, [CtVariableReadImpl]beneficiaryDischargeDate);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Period period = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Period();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimAdmissionDate.isPresent()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]period.setStart([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]claimAdmissionDate.get()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]beneficiaryDischargeDate.isPresent()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]period.setEnd([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.convertToDate([CtInvocationImpl][CtVariableReadImpl]beneficiaryDischargeDate.get()), [CtTypeAccessImpl]TemporalPrecisionEnum.DAY);
            }
            [CtInvocationImpl][CtVariableReadImpl]eob.setHospitalization([CtVariableReadImpl]period);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]utilizedDays.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent clmUtlztnDayCntFinancial = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addBenefitBalanceFinancial([CtVariableReadImpl]eob, [CtTypeAccessImpl]BenefitCategory.MEDICAL, [CtTypeAccessImpl]CcwCodebookVariable.CLM_UTLZTN_DAY_CNT);
            [CtInvocationImpl][CtVariableReadImpl]clmUtlztnDayCntFinancial.setUsed([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.UnsignedIntType([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]utilizedDays.get().intValue()));
        }
        [CtReturnImpl]return [CtVariableReadImpl]eob;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transforms the common group level data elements between the {@link InpatientClaim} {@link HHAClaim} {@link HospiceClaim} and {@link SNFClaim} claim types to FHIR. The method parameter
     * fields from {@link InpatientClaim} {@link HHAClaim} {@link HospiceClaim} and {@link SNFClaim}
     * are listed below and their corresponding RIF CCW fields (denoted in all CAPS below from {@link InpatientClaimColumn} {@link HHAClaimColumn} {@link HospiceColumn} and {@link SNFClaimColumn}).
     *
     * @param eob
     * 		the root {@link ExplanationOfBenefit} that the {@link ItemComponent} is part of
     * @param item
     * 		the {@link ItemComponent} to modify
     * @param deductibleCoinsruanceCd
     * 		REV_CNTR_DDCTBL_COINSRNC_CD
     */
    static [CtTypeReferenceImpl]void mapEobCommonGroupInpHHAHospiceSNFCoinsurance([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent item, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> deductibleCoinsuranceCd) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]deductibleCoinsuranceCd.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// FIXME should this be an adjudication?
            [CtInvocationImpl][CtVariableReadImpl]item.getRevenue().addExtension([CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createExtensionCoding([CtVariableReadImpl]eob, [CtTypeAccessImpl]CcwCodebookVariable.REV_CNTR_DDCTBL_COINSRNC_CD, [CtVariableReadImpl]deductibleCoinsuranceCd));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extract the Diagnosis values for codes 1-12
     *
     * @param diagnosisPrincipalCode
     * @param diagnosisPrincipalCodeVersion
     * @param diagnosis1Code
     * 		through diagnosis12Code
     * @param diagnosis1CodeVersion
     * 		through diagnosis12CodeVersion
     * @return the {@link Diagnosis}es that can be extracted from the specified
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis> extractDiagnoses1Thru12([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisPrincipalCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisPrincipalCodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis1Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis1CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis2Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis2CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis3Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis3CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis4Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis4CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis5Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis5CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis6Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis6CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis7Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis7CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis8Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis8CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis9Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis9CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis10Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis10CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis11Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis11CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis12Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis12CodeVersion) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis> diagnoses = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtCommentImpl]/* Seems silly, but allows the block below to be simple one-liners, rather than
        requiring if-blocks.
         */
        [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis>> diagnosisAdder = [CtLambdaImpl]([CtParameterImpl]java.util.Optional<gov.cms.bfd.server.war.stu3.providers.Diagnosis> d) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.isPresent())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]diagnoses.add([CtInvocationImpl][CtVariableReadImpl]d.get());

        };
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisPrincipalCode, [CtVariableReadImpl]diagnosisPrincipalCodeVersion, [CtTypeAccessImpl]DiagnosisLabel.PRINCIPAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis1Code, [CtVariableReadImpl]diagnosis1CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.PRINCIPAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis2Code, [CtVariableReadImpl]diagnosis2CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis3Code, [CtVariableReadImpl]diagnosis3CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis4Code, [CtVariableReadImpl]diagnosis4CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis5Code, [CtVariableReadImpl]diagnosis5CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis6Code, [CtVariableReadImpl]diagnosis6CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis7Code, [CtVariableReadImpl]diagnosis7CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis8Code, [CtVariableReadImpl]diagnosis8CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis9Code, [CtVariableReadImpl]diagnosis9CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis10Code, [CtVariableReadImpl]diagnosis10CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis11Code, [CtVariableReadImpl]diagnosis11CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis12Code, [CtVariableReadImpl]diagnosis12CodeVersion));
        [CtReturnImpl]return [CtVariableReadImpl]diagnoses;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extract the Diagnosis values for codes 13-25
     *
     * @param diagnosis13Code
     * 		through diagnosis25Code
     * @param diagnosis13CodeVersion
     * 		through diagnosis25CodeVersion
     * @return the {@link Diagnosis}es that can be extracted from the specified
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis> extractDiagnoses13Thru25([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis13Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis13CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis14Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis14CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis15Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis15CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis16Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis16CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis17Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis17CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis18Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis18CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis19Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis19CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis20Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis20CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis21Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis21CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis22Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis22CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis23Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis23CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis24Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis24CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosis25Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosis25CodeVersion) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis> diagnoses = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtCommentImpl]/* Seems silly, but allows the block below to be simple one-liners, rather than
        requiring if-blocks.
         */
        [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis>> diagnosisAdder = [CtLambdaImpl]([CtParameterImpl]java.util.Optional<gov.cms.bfd.server.war.stu3.providers.Diagnosis> d) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.isPresent())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]diagnoses.add([CtInvocationImpl][CtVariableReadImpl]d.get());

        };
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis13Code, [CtVariableReadImpl]diagnosis13CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis14Code, [CtVariableReadImpl]diagnosis14CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis15Code, [CtVariableReadImpl]diagnosis15CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis16Code, [CtVariableReadImpl]diagnosis16CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis17Code, [CtVariableReadImpl]diagnosis17CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis18Code, [CtVariableReadImpl]diagnosis18CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis19Code, [CtVariableReadImpl]diagnosis19CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis20Code, [CtVariableReadImpl]diagnosis20CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis21Code, [CtVariableReadImpl]diagnosis21CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis22Code, [CtVariableReadImpl]diagnosis22CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis23Code, [CtVariableReadImpl]diagnosis23CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis24Code, [CtVariableReadImpl]diagnosis24CodeVersion));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosis25Code, [CtVariableReadImpl]diagnosis25CodeVersion));
        [CtReturnImpl]return [CtVariableReadImpl]diagnoses;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extract the External Diagnosis values for codes 1-12
     *
     * @param diagnosisExternalFirstCode
     * @param diagnosisExternalFirstCodeVersion
     * @param diagnosisExternal1Code
     * 		through diagnosisExternal12Code
     * @param diagnosisExternal1CodeVersion
     * 		through diagnosisExternal12CodeVersion
     * @return the {@link Diagnosis}es that can be extracted from the specified
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis> extractExternalDiagnoses1Thru12([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternalFirstCode, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternalFirstCodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal1Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal1CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal2Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal2CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal3Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal3CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal4Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal4CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal5Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal5CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal6Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal6CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal7Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal7CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal8Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal8CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal9Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal9CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal10Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal10CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal11Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal11CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> diagnosisExternal12Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> diagnosisExternal12CodeVersion) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis> diagnoses = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtCommentImpl]/* Seems silly, but allows the block below to be simple one-liners, rather than
        requiring if-blocks.
         */
        [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis>> diagnosisAdder = [CtLambdaImpl]([CtParameterImpl]java.util.Optional<gov.cms.bfd.server.war.stu3.providers.Diagnosis> d) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.isPresent())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]diagnoses.add([CtInvocationImpl][CtVariableReadImpl]d.get());

        };
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternalFirstCode, [CtVariableReadImpl]diagnosisExternalFirstCodeVersion, [CtTypeAccessImpl]DiagnosisLabel.FIRSTEXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal1Code, [CtVariableReadImpl]diagnosisExternal1CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.FIRSTEXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal2Code, [CtVariableReadImpl]diagnosisExternal2CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal3Code, [CtVariableReadImpl]diagnosisExternal3CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal4Code, [CtVariableReadImpl]diagnosisExternal4CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal5Code, [CtVariableReadImpl]diagnosisExternal5CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal6Code, [CtVariableReadImpl]diagnosisExternal6CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal7Code, [CtVariableReadImpl]diagnosisExternal7CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal8Code, [CtVariableReadImpl]diagnosisExternal8CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal9Code, [CtVariableReadImpl]diagnosisExternal9CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal10Code, [CtVariableReadImpl]diagnosisExternal10CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal11Code, [CtVariableReadImpl]diagnosisExternal11CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtInvocationImpl][CtVariableReadImpl]diagnosisAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.Diagnosis.from([CtVariableReadImpl]diagnosisExternal12Code, [CtVariableReadImpl]diagnosisExternal12CodeVersion, [CtTypeAccessImpl]DiagnosisLabel.EXTERNAL));
        [CtReturnImpl]return [CtVariableReadImpl]diagnoses;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extract the Procedure values for codes 1-25
     *
     * @param procedure1Code
     * 		through procedure25Code,
     * @param procedure1CodeVersion
     * 		through procedure25CodeVersion
     * @param procedure1Date
     * 		through procedure25Date
     * @return the {@link CCWProcedure}es that can be extracted from the specified claim types
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure> extractCCWProcedures([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure1Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure1CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure1Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure2Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure2CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure2Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure3Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure3CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure3Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure4Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure4CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure4Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure5Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure5CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure5Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure6Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure6CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure6Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure7Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure7CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure7Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure8Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure8CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure8Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure9Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure9CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure9Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure10Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure10CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure10Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure11Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure11CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure11Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure12Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure12CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure12Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure13Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure13CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure13Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure14Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure14CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure14Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure15Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure15CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure15Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure16Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure16CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure16Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure17Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure17CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure17Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure18Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure18CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure18Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure19Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure19CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure19Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure20Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure20CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure20Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure21Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure21CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure21Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure22Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure22CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure22Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure23Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure23CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure23Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure24Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure24CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure24Date, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> procedure25Code, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> procedure25CodeVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.time.LocalDate> procedure25Date) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure> ccwProcedures = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtCommentImpl]/* Seems silly, but allows the block below to be simple one-liners, rather than
        requiring if-blocks.
         */
        [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure>> ccwProcedureAdder = [CtLambdaImpl]([CtParameterImpl]java.util.Optional<gov.cms.bfd.server.war.stu3.providers.CCWProcedure> p) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isPresent())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]ccwProcedures.add([CtInvocationImpl][CtVariableReadImpl]p.get());

        };
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure1Code, [CtVariableReadImpl]procedure1CodeVersion, [CtVariableReadImpl]procedure1Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure2Code, [CtVariableReadImpl]procedure2CodeVersion, [CtVariableReadImpl]procedure2Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure3Code, [CtVariableReadImpl]procedure3CodeVersion, [CtVariableReadImpl]procedure3Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure4Code, [CtVariableReadImpl]procedure4CodeVersion, [CtVariableReadImpl]procedure4Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure5Code, [CtVariableReadImpl]procedure5CodeVersion, [CtVariableReadImpl]procedure5Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure6Code, [CtVariableReadImpl]procedure6CodeVersion, [CtVariableReadImpl]procedure6Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure7Code, [CtVariableReadImpl]procedure7CodeVersion, [CtVariableReadImpl]procedure7Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure8Code, [CtVariableReadImpl]procedure8CodeVersion, [CtVariableReadImpl]procedure8Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure9Code, [CtVariableReadImpl]procedure9CodeVersion, [CtVariableReadImpl]procedure9Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure10Code, [CtVariableReadImpl]procedure10CodeVersion, [CtVariableReadImpl]procedure10Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure11Code, [CtVariableReadImpl]procedure11CodeVersion, [CtVariableReadImpl]procedure11Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure12Code, [CtVariableReadImpl]procedure12CodeVersion, [CtVariableReadImpl]procedure12Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure13Code, [CtVariableReadImpl]procedure13CodeVersion, [CtVariableReadImpl]procedure13Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure14Code, [CtVariableReadImpl]procedure14CodeVersion, [CtVariableReadImpl]procedure14Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure15Code, [CtVariableReadImpl]procedure15CodeVersion, [CtVariableReadImpl]procedure15Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure16Code, [CtVariableReadImpl]procedure16CodeVersion, [CtVariableReadImpl]procedure16Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure17Code, [CtVariableReadImpl]procedure17CodeVersion, [CtVariableReadImpl]procedure17Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure18Code, [CtVariableReadImpl]procedure18CodeVersion, [CtVariableReadImpl]procedure18Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure19Code, [CtVariableReadImpl]procedure19CodeVersion, [CtVariableReadImpl]procedure19Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure20Code, [CtVariableReadImpl]procedure20CodeVersion, [CtVariableReadImpl]procedure20Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure21Code, [CtVariableReadImpl]procedure21CodeVersion, [CtVariableReadImpl]procedure21Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure22Code, [CtVariableReadImpl]procedure22CodeVersion, [CtVariableReadImpl]procedure22Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure23Code, [CtVariableReadImpl]procedure23CodeVersion, [CtVariableReadImpl]procedure23Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure24Code, [CtVariableReadImpl]procedure24CodeVersion, [CtVariableReadImpl]procedure24Date));
        [CtInvocationImpl][CtVariableReadImpl]ccwProcedureAdder.accept([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.CCWProcedure.from([CtVariableReadImpl]procedure25Code, [CtVariableReadImpl]procedure25CodeVersion, [CtVariableReadImpl]procedure25Date));
        [CtReturnImpl]return [CtVariableReadImpl]ccwProcedures;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the provider number field which is common among these claim types: Inpatient, Outpatient,
     * Hospice, HHA and SNF.
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} this method will modify
     * @param providerNumber
     * 		a {@link String} PRVDR_NUM: representing the provider number for the
     * 		claim
     */
    static [CtTypeReferenceImpl]void setProviderNumber([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String providerNumber) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]eob.setProvider([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Reference().setIdentifier([CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createIdentifier([CtTypeAccessImpl]CcwCodebookVariable.PRVDR_NUM, [CtVariableReadImpl]providerNumber)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param eob
     * 		the {@link ExplanationOfBenefit} that the HCPCS code is being mapped into
     * @param item
     * 		the {@link ItemComponent} that the HCPCS code is being mapped into
     * @param hcpcsYear
     * 		the {@link CcwCodebookVariable#CARR_CLM_HCPCS_YR_CD} identifying the HCPCS
     * 		code version in use
     * @param hcpcs
     * 		the {@link CcwCodebookVariable#HCPCS_CD} to be mapped
     * @param hcpcsModifiers
     * 		the {@link CcwCodebookVariable#HCPCS_1ST_MDFR_CD}, etc. values to be
     * 		mapped (if any)
     */
    static [CtTypeReferenceImpl]void mapHcpcs([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit eob, [CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent item, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Character> hcpcsYear, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> hcpcs, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String>> hcpcsModifiers) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create and map all of the possible CodeableConcepts.
        [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept hcpcsConcept = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]hcpcs.isPresent()) ? [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtTypeAccessImpl]TransformerConstants.CODING_SYSTEM_HCPCS, [CtInvocationImpl][CtVariableReadImpl]hcpcs.get()) : [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hcpcsConcept != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]item.setService([CtVariableReadImpl]hcpcsConcept);

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept> hcpcsModifierConcepts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtLiteralImpl]4);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> hcpcsModifier : [CtVariableReadImpl]hcpcsModifiers) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]hcpcsModifier.isPresent())[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.CodeableConcept hcpcsModifierConcept = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.createCodeableConcept([CtTypeAccessImpl]TransformerConstants.CODING_SYSTEM_HCPCS, [CtInvocationImpl][CtVariableReadImpl]hcpcsModifier.get());
            [CtInvocationImpl][CtVariableReadImpl]hcpcsModifierConcepts.add([CtVariableReadImpl]hcpcsModifierConcept);
            [CtInvocationImpl][CtVariableReadImpl]item.addModifier([CtVariableReadImpl]hcpcsModifierConcept);
        }
        [CtInvocationImpl][CtCommentImpl]// Set Coding.version for all of the mappings, if it's available.
        [CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.concat([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]hcpcsConcept).stream(), [CtInvocationImpl][CtVariableReadImpl]hcpcsModifierConcepts.stream()).forEach([CtLambdaImpl]([CtParameterImpl] concept) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]concept == [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return;

            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]hcpcsYear.isPresent())[CtBlockImpl]
                [CtReturnImpl]return;

            [CtInvocationImpl][CtCommentImpl]// Note: Only CARRIER and DME claims have the year/version field.
            [CtInvocationImpl][CtVariableReadImpl]concept.getCodingFirstRep().setVersion([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hcpcsYear.get().toString());
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves the Diagnosis display value from a Diagnosis code look up file
     *
     * @param icdCode
     * 		- Diagnosis code
     */
    public static [CtTypeReferenceImpl]java.lang.String retrieveIcdCodeDisplay([CtParameterImpl][CtTypeReferenceImpl]java.lang.String icdCode) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]icdCode.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl][CtCommentImpl]/* There's a race condition here: we may initialize this static field more than
        once if multiple requests come in at the same time. However, the assignment
        is atomic, so the race and reinitialization is harmless other than maybe
        wasting a bit of time.
         */
        [CtCommentImpl]// read the entire ICD file the first time and put in a Map
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.icdMap == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.icdMap = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.readIcdCodeFile();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.icdMap.containsKey([CtInvocationImpl][CtVariableReadImpl]icdCode.toUpperCase())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String icdCodeDisplay = [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.icdMap.get([CtVariableReadImpl]icdCode);
            [CtReturnImpl]return [CtVariableReadImpl]icdCodeDisplay;
        }
        [CtIfImpl][CtCommentImpl]// log which NDC codes we couldn't find a match for in our downloaded NDC file
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.drugCodeLookupMissingFailures.contains([CtVariableReadImpl]icdCode)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.drugCodeLookupMissingFailures.add([CtVariableReadImpl]icdCode);
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.LOGGER.info([CtLiteralImpl]"No ICD code display value match found for ICD code {} in resource {}.", [CtVariableReadImpl]icdCode, [CtLiteralImpl]"DGNS_CD.txt");
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads ALL the ICD codes and display values from the DGNS_CD.txt file. Refer to the README file
     * in the src/main/resources directory
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> readIcdCodeFile() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> icdDiagnosisMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.InputStream icdCodeDisplayStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader().getResourceAsStream([CtLiteralImpl]"DGNS_CD.txt");[CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.BufferedReader icdCodesIn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtVariableReadImpl]icdCodeDisplayStream))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* We want to extract the ICD Diagnosis codes and display values and put in a
            map for easy retrieval to get the display value icdColumns[1] is
            DGNS_DESC(i.e. 7840 code is HEADACHE description)
             */
            [CtTypeReferenceImpl]java.lang.String line = [CtLiteralImpl]"";
            [CtInvocationImpl][CtVariableReadImpl]icdCodesIn.readLine();
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]icdCodesIn.readLine()) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String icdColumns[] = [CtInvocationImpl][CtVariableReadImpl]line.split([CtLiteralImpl]"\t");
                [CtInvocationImpl][CtVariableReadImpl]icdDiagnosisMap.put([CtArrayReadImpl][CtVariableReadImpl]icdColumns[[CtLiteralImpl]0], [CtArrayReadImpl][CtVariableReadImpl]icdColumns[[CtLiteralImpl]1]);
            } 
            [CtInvocationImpl][CtVariableReadImpl]icdCodesIn.close();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtLiteralImpl]"Unable to read ICD code data.", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]icdDiagnosisMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves the NPI display value from an NPI code look up file
     *
     * @param npiCode
     * 		- NPI code
     */
    public static [CtTypeReferenceImpl]java.lang.String retrieveNpiCodeDisplay([CtParameterImpl][CtTypeReferenceImpl]java.lang.String npiCode) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]npiCode.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl][CtCommentImpl]/* There's a race condition here: we may initialize this static field more than
        once if multiple requests come in at the same time. However, the assignment
        is atomic, so the race and reinitialization is harmless other than maybe
        wasting a bit of time.
         */
        [CtCommentImpl]// read the entire NPI file the first time and put in a Map
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.npiMap == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.npiMap = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.readNpiCodeFile();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.npiMap.containsKey([CtInvocationImpl][CtVariableReadImpl]npiCode.toUpperCase())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String npiCodeDisplay = [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.npiMap.get([CtVariableReadImpl]npiCode);
            [CtReturnImpl]return [CtVariableReadImpl]npiCodeDisplay;
        }
        [CtIfImpl][CtCommentImpl]// log which NPI codes we couldn't find a match for in our downloaded NPI file
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.npiCodeLookupMissingFailures.contains([CtVariableReadImpl]npiCode)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.npiCodeLookupMissingFailures.add([CtVariableReadImpl]npiCode);
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.LOGGER.info([CtLiteralImpl]"No NPI code display value match found for NPI code {} in resource {}.", [CtVariableReadImpl]npiCode, [CtLiteralImpl]"NPI_Coded_Display_Values_Tab.txt");
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads ALL the NPI codes and display values from the NPI_Coded_Display_Values_Tab.txt file.
     * Refer to the README file in the src/main/resources directory
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> readNpiCodeFile() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> npiCodeMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.InputStream npiCodeDisplayStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader().getResourceAsStream([CtLiteralImpl]"NPI_Coded_Display_Values_Tab.txt");[CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.BufferedReader npiCodesIn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtVariableReadImpl]npiCodeDisplayStream))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* We want to extract the NPI codes and display values and put in a map for easy
            retrieval to get the display value-- npiColumns[0] is the NPI Code,
            npiColumns[4] is the NPI Organization Code, npiColumns[8] is the NPI provider
            name prefix, npiColumns[6] is the NPI provider first name, npiColumns[7] is
            the NPI provider middle name, npiColumns[5] is the NPI provider last name,
            npiColumns[9] is the NPI provider suffix name, npiColumns[10] is the NPI
            provider credential.
             */
            [CtTypeReferenceImpl]java.lang.String line = [CtLiteralImpl]"";
            [CtInvocationImpl][CtVariableReadImpl]npiCodesIn.readLine();
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]npiCodesIn.readLine()) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String npiColumns[] = [CtInvocationImpl][CtVariableReadImpl]line.split([CtLiteralImpl]"\t");
                [CtIfImpl]if ([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]4].isEmpty()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String npiDisplayName = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]8].trim() + [CtLiteralImpl]" ") + [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]6].trim()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]7].trim()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]5].trim()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]9].trim()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]10].trim();
                    [CtInvocationImpl][CtVariableReadImpl]npiCodeMap.put([CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]0], [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]npiDisplayName.replace([CtLiteralImpl]"  ", [CtLiteralImpl]" ").trim());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]npiCodeMap.put([CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]0], [CtInvocationImpl][CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]npiColumns[[CtLiteralImpl]4].replace([CtLiteralImpl]"\"", [CtLiteralImpl]"").trim());
                }
            } 
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtLiteralImpl]"Unable to read NPI code data.", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]npiCodeMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves the Procedure code and display value from a Procedure code look up file
     *
     * @param procedureCode
     * 		- Procedure code
     */
    public static [CtTypeReferenceImpl]java.lang.String retrieveProcedureCodeDisplay([CtParameterImpl][CtTypeReferenceImpl]java.lang.String procedureCode) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]procedureCode.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl][CtCommentImpl]/* There's a race condition here: we may initialize this static field more than
        once if multiple requests come in at the same time. However, the assignment
        is atomic, so the race and reinitialization is harmless other than maybe
        wasting a bit of time.
         */
        [CtCommentImpl]// read the entire Procedure code file the first time and put in a Map
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.procedureMap == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.procedureMap = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.readProcedureCodeFile();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.procedureMap.containsKey([CtInvocationImpl][CtVariableReadImpl]procedureCode.toUpperCase())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String procedureCodeDisplay = [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.procedureMap.get([CtVariableReadImpl]procedureCode);
            [CtReturnImpl]return [CtVariableReadImpl]procedureCodeDisplay;
        }
        [CtIfImpl][CtCommentImpl]// log which Procedure codes we couldn't find a match for in our procedure codes
        [CtCommentImpl]// file
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.procedureLookupMissingFailures.contains([CtVariableReadImpl]procedureCode)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.procedureLookupMissingFailures.add([CtVariableReadImpl]procedureCode);
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.LOGGER.info([CtLiteralImpl]"No procedure code display value match found for procedure code {} in resource {}.", [CtVariableReadImpl]procedureCode, [CtLiteralImpl]"PRCDR_CD.txt");
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads all the procedure codes and display values from the PRCDR_CD.txt file Refer to the README
     * file in the src/main/resources directory
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> readProcedureCodeFile() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> procedureCodeMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.InputStream procedureCodeDisplayStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader().getResourceAsStream([CtLiteralImpl]"PRCDR_CD.txt");[CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.BufferedReader procedureCodesIn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtVariableReadImpl]procedureCodeDisplayStream))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* We want to extract the procedure codes and display values and put in a map
            for easy retrieval to get the display value icdColumns[0] is PRCDR_CD;
            icdColumns[1] is PRCDR_DESC(i.e. 8295 is INJECT TENDON OF HAND description)
             */
            [CtTypeReferenceImpl]java.lang.String line = [CtLiteralImpl]"";
            [CtInvocationImpl][CtVariableReadImpl]procedureCodesIn.readLine();
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]procedureCodesIn.readLine()) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String icdColumns[] = [CtInvocationImpl][CtVariableReadImpl]line.split([CtLiteralImpl]"\t");
                [CtInvocationImpl][CtVariableReadImpl]procedureCodeMap.put([CtArrayReadImpl][CtVariableReadImpl]icdColumns[[CtLiteralImpl]0], [CtArrayReadImpl][CtVariableReadImpl]icdColumns[[CtLiteralImpl]1]);
            } 
            [CtInvocationImpl][CtVariableReadImpl]procedureCodesIn.close();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtLiteralImpl]"Unable to read Procedure code data.", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]procedureCodeMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves the PRODUCTNDC and SUBSTANCENAME from the FDA NDC Products file which was downloaded
     * during the build process
     *
     * @param claimDrugCode
     * 		- NDC value in claim records
     */
    public static [CtTypeReferenceImpl]java.lang.String retrieveFDADrugCodeDisplay([CtParameterImpl][CtTypeReferenceImpl]java.lang.String claimDrugCode) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]/* Handle bad data (e.g. our random test data) if drug code is empty or
        length is less than 9 characters
         */
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]claimDrugCode.isEmpty() || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]claimDrugCode.length() < [CtLiteralImpl]9))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl][CtCommentImpl]/* There's a race condition here: we may initialize this static field more than
        once if multiple requests come in at the same time. However, the assignment
        is atomic, so the race and reinitialization is harmless other than maybe
        wasting a bit of time.
         */
        [CtCommentImpl]// read the entire NDC file the first time and put in a Map
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.ndcProductMap == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.ndcProductMap = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.readFDADrugCodeFile();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String claimDrugCodeReformatted = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtVariableWriteImpl]claimDrugCodeReformatted = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]claimDrugCode.substring([CtLiteralImpl]0, [CtLiteralImpl]5) + [CtLiteralImpl]"-") + [CtInvocationImpl][CtVariableReadImpl]claimDrugCode.substring([CtLiteralImpl]5, [CtLiteralImpl]9);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.ndcProductMap.containsKey([CtVariableReadImpl]claimDrugCodeReformatted)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ndcSubstanceName = [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.ndcProductMap.get([CtVariableReadImpl]claimDrugCodeReformatted);
            [CtReturnImpl]return [CtVariableReadImpl]ndcSubstanceName;
        }
        [CtIfImpl][CtCommentImpl]// log which NDC codes we couldn't find a match for in our downloaded NDC file
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.drugCodeLookupMissingFailures.contains([CtVariableReadImpl]claimDrugCode)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.drugCodeLookupMissingFailures.add([CtVariableReadImpl]claimDrugCode);
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.LOGGER.info([CtLiteralImpl]"No national drug code value (PRODUCTNDC column) match found for drug code {} in resource {}.", [CtVariableReadImpl]claimDrugCode, [CtLiteralImpl]"fda_products_utf8.tsv");
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads all the <code>PRODUCTNDC</code> and <code>SUBSTANCENAME</code> fields from the FDA NDC
     * Products file which was downloaded during the build process.
     *
     * <p>See {@link FDADrugDataUtilityApp} for details.
     */
    public static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> readFDADrugCodeFile() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> ndcProductHashMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.InputStream ndcProductStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader().getResourceAsStream([CtTypeAccessImpl]FDADrugDataUtilityApp.FDA_PRODUCTS_RESOURCE);[CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.BufferedReader ndcProductsIn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtVariableReadImpl]ndcProductStream))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* We want to extract the PRODUCTNDC and PROPRIETARYNAME/SUBSTANCENAME from the
            FDA Products file (fda_products_utf8.tsv is in /target/classes directory) and
            put in a Map for easy retrieval to get the display value which is a
            combination of PROPRIETARYNAME & SUBSTANCENAME
             */
            [CtTypeReferenceImpl]java.lang.String line = [CtLiteralImpl]"";
            [CtInvocationImpl][CtVariableReadImpl]ndcProductsIn.readLine();
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]ndcProductsIn.readLine()) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String ndcProductColumns[] = [CtInvocationImpl][CtVariableReadImpl]line.split([CtLiteralImpl]"\t");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nationalDrugCodeManufacturer = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.leftPad([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]ndcProductColumns[[CtLiteralImpl]1].substring([CtLiteralImpl]0, [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]ndcProductColumns[[CtLiteralImpl]1].indexOf([CtLiteralImpl]"-")), [CtLiteralImpl]5, [CtLiteralImpl]'0');
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nationalDrugCodeIngredient = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.leftPad([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]ndcProductColumns[[CtLiteralImpl]1].substring([CtBinaryOperatorImpl][CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]ndcProductColumns[[CtLiteralImpl]1].indexOf([CtLiteralImpl]"-") + [CtLiteralImpl]1, [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]ndcProductColumns[[CtLiteralImpl]1].length()), [CtLiteralImpl]4, [CtLiteralImpl]'0');
                [CtInvocationImpl][CtCommentImpl]// ndcProductColumns[3] - Proprietary Name
                [CtCommentImpl]// ndcProductColumns[13] - Substance Name
                [CtVariableReadImpl]ndcProductHashMap.put([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s-%s", [CtVariableReadImpl]nationalDrugCodeManufacturer, [CtVariableReadImpl]nationalDrugCodeIngredient), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtArrayReadImpl][CtVariableReadImpl]ndcProductColumns[[CtLiteralImpl]3] + [CtLiteralImpl]" - ") + [CtArrayReadImpl][CtVariableReadImpl]ndcProductColumns[[CtLiteralImpl]13]);
            } 
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtLiteralImpl]"Unable to read NDC code data.", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]ndcProductHashMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param metricRegistry
     * 		the {@link MetricRegistry} to use
     * @param rifRecord
     * 		the RIF record (e.g. a {@link CarrierClaim} instance) to transform
     * @return the transformed {@link ExplanationOfBenefit} for the specified RIF record
     */
    static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.ExplanationOfBenefit transformRifRecordToEob([CtParameterImpl][CtTypeReferenceImpl]com.codahale.metrics.MetricRegistry metricRegistry, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object rifRecord) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rifRecord == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType claimType : [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]claimType.getEntityClass().isInstance([CtVariableReadImpl]rifRecord)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]claimType.getTransformer().apply([CtVariableReadImpl]metricRegistry, [CtVariableReadImpl]rifRecord);
            }
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.justdavis.karl.misc.exceptions.BadCodeMonkeyException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unhandled %s: %s", [CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.ClaimType.class, [CtInvocationImpl][CtVariableReadImpl]rifRecord.getClass()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a bundle from the entire search result
     *
     * @param paging
     * 		contains the {@link OffsetLinkBuilder} information
     * @param resources
     * 		a list of {@link ExplanationOfBenefit}s, {@link Coverage}s, or {@link Patient}s, of which a portion or all will be added to the bundle based on the paging values
     * @param transactionTime
     * 		date for the bundle
     * @return Returns a {@link Bundle} of either {@link ExplanationOfBenefit}s, {@link Coverage}s, or
    {@link Patient}s, which may contain multiple matching resources, or may also be empty.
     */
    public static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle createBundle([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.OffsetLinkBuilder paging, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseResource> resources, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date transactionTime) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle bundle = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]paging.isPagingRequested()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* FIXME: Due to a bug in HAPI-FHIR described here
            https://github.com/jamesagnew/hapi-fhir/issues/1074 paging for count=0 is not
            working correctly.
             */
            [CtTypeReferenceImpl]int endIndex = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]paging.getStartIndex() + [CtInvocationImpl][CtVariableReadImpl]paging.getPageSize(), [CtInvocationImpl][CtVariableReadImpl]resources.size());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseResource> resourcesSubList = [CtInvocationImpl][CtVariableReadImpl]resources.subList([CtInvocationImpl][CtVariableReadImpl]paging.getStartIndex(), [CtVariableReadImpl]endIndex);
            [CtAssignmentImpl][CtVariableWriteImpl]bundle = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addResourcesToBundle([CtVariableReadImpl]bundle, [CtVariableReadImpl]resourcesSubList);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]paging.setTotal([CtInvocationImpl][CtVariableReadImpl]resources.size()).addLinks([CtVariableReadImpl]bundle);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]bundle = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addResourcesToBundle([CtVariableReadImpl]bundle, [CtVariableReadImpl]resources);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* Dev Note: the Bundle's lastUpdated timestamp is the known last update time for the whole database.
        Because the filterManager's tracking of this timestamp is lazily updated for performance reason,
        the resources of the bundle may be after the filter manager's version of the timestamp.
         */
        [CtTypeReferenceImpl]java.util.Date maxBundleDate = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resources.stream().map([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMeta().getLastUpdated()).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).max([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Date::compareTo).orElse([CtVariableReadImpl]transactionTime);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundle.getMeta().setLastUpdated([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]transactionTime.after([CtVariableReadImpl]maxBundleDate) ? [CtVariableReadImpl]transactionTime : [CtVariableReadImpl]maxBundleDate);
        [CtInvocationImpl][CtVariableReadImpl]bundle.setTotal([CtInvocationImpl][CtVariableReadImpl]resources.size());
        [CtReturnImpl]return [CtVariableReadImpl]bundle;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a bundle from the entire search result
     *
     * @param resources
     * 		a list of {@link ExplanationOfBenefit}s, {@link Coverage}s, or {@link Patient}s, all of which will be added to the bundle
     * @param paging
     * 		contains the {@link LinkBuilder} information to add to the bundle
     * @param transactionTime
     * 		date for the bundle
     * @return Returns a {@link Bundle} of either {@link ExplanationOfBenefit}s, {@link Coverage}s, or
    {@link Patient}s, which may contain multiple matching resources, or may also be empty.
     */
    public static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle createBundle([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseResource> resources, [CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LinkBuilder paging, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date transactionTime) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle bundle = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle();
        [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.TransformerUtils.addResourcesToBundle([CtVariableReadImpl]bundle, [CtVariableReadImpl]resources);
        [CtInvocationImpl][CtVariableReadImpl]paging.addLinks([CtVariableReadImpl]bundle);
        [CtInvocationImpl][CtVariableReadImpl]bundle.setTotalElement([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]paging.isPagingRequested() ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.UnsignedIntType() : [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.UnsignedIntType([CtInvocationImpl][CtVariableReadImpl]resources.size()));
        [CtLocalVariableImpl][CtCommentImpl]/* Dev Note: the Bundle's lastUpdated timestamp is the known last update time for the whole database.
        Because the filterManager's tracking of this timestamp is lazily updated for performance reason,
        the resources of the bundle may be after the filter manager's version of the timestamp.
         */
        [CtTypeReferenceImpl]java.util.Date maxBundleDate = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resources.stream().map([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMeta().getLastUpdated()).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).max([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Date::compareTo).orElse([CtVariableReadImpl]transactionTime);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundle.getMeta().setLastUpdated([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]transactionTime.after([CtVariableReadImpl]maxBundleDate) ? [CtVariableReadImpl]transactionTime : [CtVariableReadImpl]maxBundleDate);
        [CtReturnImpl]return [CtVariableReadImpl]bundle;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param bundle
     * 		a {@link Bundle} to add the list of {@link ExplanationOfBenefit} resources to.
     * @param resources
     * 		a list of either {@link ExplanationOfBenefit}s, {@link Coverage}s, or {@link Patient}s, of which a portion will be added to the bundle based on the paging values
     * @return Returns a {@link Bundle} of {@link ExplanationOfBenefit}s, {@link Coverage}s, or {@link Patient}s, which may contain multiple matching resources, or may also be empty.
     */
    public static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle addResourcesToBundle([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle bundle, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseResource> resources) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IBaseResource res : [CtVariableReadImpl]resources) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent entry = [CtInvocationImpl][CtVariableReadImpl]bundle.addEntry();
            [CtInvocationImpl][CtVariableReadImpl]entry.setResource([CtVariableReadImpl](([CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Resource) (res)));
        }
        [CtReturnImpl]return [CtVariableReadImpl]bundle;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param currencyIdentifier
     * 		the {@link CurrencyIdentifier} indicating the currency of an {@link Identifier}.
     * @return Returns an {@link Extension} describing the currency of an {@link Identifier}.
     */
    public static [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension createIdentifierCurrencyExtension([CtParameterImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.BeneficiaryTransformer.CurrencyIdentifier currencyIdentifier) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String system = [CtFieldReadImpl]TransformerConstants.CODING_SYSTEM_IDENTIFIER_CURRENCY;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String code = [CtLiteralImpl]"historic";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String display = [CtLiteralImpl]"Historic";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]currencyIdentifier.equals([CtTypeAccessImpl]CurrencyIdentifier.CURRENT)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]code = [CtLiteralImpl]"current";
            [CtAssignmentImpl][CtVariableWriteImpl]display = [CtLiteralImpl]"Current";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding currentValueCoding = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Coding([CtVariableReadImpl]system, [CtVariableReadImpl]code, [CtVariableReadImpl]display);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension currencyIdentifierExtension = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.model.Extension([CtFieldReadImpl]TransformerConstants.CODING_SYSTEM_IDENTIFIER_CURRENCY, [CtVariableReadImpl]currentValueCoding);
        [CtReturnImpl]return [CtVariableReadImpl]currencyIdentifierExtension;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Records the JPA query details in {@link MDC}.
     *
     * @param queryId
     * 		an ID that identifies the type of JPA query being run, e.g. "bene_by_id"
     * @param queryDurationNanoseconds
     * 		the JPA query's duration, in nanoseconds
     * @param recordCount
     * 		the number of top-level records (e.g. JPA entities) returned by the query
     */
    public static [CtTypeReferenceImpl]void recordQueryInMdc([CtParameterImpl][CtTypeReferenceImpl]java.lang.String queryId, [CtParameterImpl][CtTypeReferenceImpl]long queryDurationNanoseconds, [CtParameterImpl][CtTypeReferenceImpl]long recordCount) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyPrefix = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"jpa_query.%s", [CtVariableReadImpl]queryId);
        [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s.duration_nanoseconds", [CtVariableReadImpl]keyPrefix), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtVariableReadImpl]queryDurationNanoseconds));
        [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s.duration_milliseconds", [CtVariableReadImpl]keyPrefix), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtBinaryOperatorImpl][CtVariableReadImpl]queryDurationNanoseconds / [CtLiteralImpl]1000000));
        [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s.record_count", [CtVariableReadImpl]keyPrefix), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtVariableReadImpl]recordCount));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the lastUpdated value in the resource.
     *
     * @param resource
     * 		is the FHIR resource to set lastUpdate
     * @param lastUpdated
     * 		is the lastUpdated value set. If not present, set the fallback lastUdpated.
     */
    public static [CtTypeReferenceImpl]void setLastUpdated([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource resource, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Date> lastUpdated) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getMeta().setLastUpdated([CtInvocationImpl][CtVariableReadImpl]lastUpdated.orElse([CtTypeAccessImpl]TransformerConstants.FALLBACK_LAST_UPDATED));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the lastUpdated value in the resource if the passed in value is later than the current
     * value.
     *
     * @param resource
     * 		is the FHIR resource to update
     * @param lastUpdated
     * 		is the lastUpdated value from the entity
     */
    public static [CtTypeReferenceImpl]void updateMaxLastUpdated([CtParameterImpl][CtTypeReferenceImpl]org.hl7.fhir.instance.model.api.IAnyResource resource, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Date> lastUpdated) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]lastUpdated.ifPresent([CtLambdaImpl]([CtParameterImpl]java.util.Date newDate) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date currentDate = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getMeta().getLastUpdated();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]currentDate != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]newDate.after([CtVariableReadImpl]currentDate)) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getMeta().setLastUpdated([CtVariableReadImpl]newDate);
            }
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Work around for https://github.com/jamesagnew/hapi-fhir/issues/1585. HAPI will fill in the
     * resource count as a total value when a Bundle has no total value.
     *
     * @param requestDetails
     * 		of a resource provider
     */
    public static [CtTypeReferenceImpl]void workAroundHAPIIssue1585([CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.api.server.RequestDetails requestDetails) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// The hack is to remove the _count parameter from theDetails so that total is not modified.
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]>([CtInvocationImpl][CtVariableReadImpl]requestDetails.getParameters());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]params.remove([CtTypeAccessImpl]Constants.PARAM_COUNT) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Remove _count parameter from the current request details
            [CtVariableReadImpl]requestDetails.setParameters([CtVariableReadImpl]params);
        }
    }
}
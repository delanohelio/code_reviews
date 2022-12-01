[CompilationUnitImpl][CtPackageDeclarationImpl]package org.broadinstitute.hellbender.tools.walkers.sv;
[CtUnresolvedImport]import htsjdk.variant.vcf.VCFHeaderLine;
[CtUnresolvedImport]import org.broadinstitute.barclay.argparser.CommandLineProgramProperties;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import htsjdk.variant.vcf.VCFStandardHeaderLines;
[CtUnresolvedImport]import htsjdk.variant.vcf.VCFHeader;
[CtUnresolvedImport]import org.broadinstitute.hellbender.cmdline.programgroups.StructuralVariantDiscoveryProgramGroup;
[CtUnresolvedImport]import org.broadinstitute.barclay.argparser.Argument;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.samples.Sex;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.sv.SVClusterEngine;
[CtUnresolvedImport]import org.broadinstitute.hellbender.cmdline.StandardArgumentDefinitions;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.samples.SampleDB;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.variant.*;
[CtUnresolvedImport]import htsjdk.samtools.reference.ReferenceSequenceFile;
[CtImportImpl]import org.apache.commons.lang3.tuple.MutablePair;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.copynumber.gcnv.GermlineCNVSegmentVariantComposer;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.spark.sv.utils.GATKSVVCFHeaderLines;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.logging.OneShotLogger;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.*;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.reference.ReferenceUtils;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.samples.SampleDBBuilder;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.sv.SVDepthOnlyCallDefragmenter;
[CtUnresolvedImport]import org.broadinstitute.hellbender.engine.ReferenceContext;
[CtUnresolvedImport]import htsjdk.variant.vcf.VCFConstants;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import htsjdk.samtools.SAMSequenceDictionary;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.sv.SVCallRecordWithEvidence;
[CtUnresolvedImport]import htsjdk.variant.variantcontext.*;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.sv.SVCallRecord;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.copynumber.PostprocessGermlineCNVCalls;
[CtUnresolvedImport]import htsjdk.variant.variantcontext.writer.VariantContextWriter;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.samples.PedigreeValidationType;
[CtUnresolvedImport]import org.broadinstitute.hellbender.engine.MultiVariantWalkerGroupedOnStart;
[CtUnresolvedImport]import org.broadinstitute.hellbender.utils.genotyper.IndexedSampleList;
[CtUnresolvedImport]import org.broadinstitute.hellbender.engine.ReadsContext;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.broadinstitute.barclay.argparser.BetaFeature;
[CtUnresolvedImport]import org.broadinstitute.hellbender.tools.spark.sv.utils.GATKSVVCFConstants;
[CtClassImpl][CtAnnotationImpl]@org.broadinstitute.barclay.argparser.BetaFeature
[CtAnnotationImpl]@org.broadinstitute.barclay.argparser.CommandLineProgramProperties(summary = [CtLiteralImpl]"Gathers single-sample segmented gCNV VCFs, harmonizes breakpoints, and outputs a cohort VCF with genotypes.", oneLineSummary = [CtLiteralImpl]"Combined single-sample segmented gCNV VCFs.", programGroup = [CtFieldReadImpl]org.broadinstitute.hellbender.cmdline.programgroups.StructuralVariantDiscoveryProgramGroup.class)
public class JointGermlineCNVSegmentation extends [CtTypeReferenceImpl]org.broadinstitute.hellbender.engine.MultiVariantWalkerGroupedOnStart {
    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.SortedSet<[CtTypeReferenceImpl]java.lang.String> samples;

    [CtFieldImpl]private [CtTypeReferenceImpl]htsjdk.variant.variantcontext.writer.VariantContextWriter vcfWriter;

    [CtFieldImpl]private [CtTypeReferenceImpl]htsjdk.samtools.SAMSequenceDictionary dictionary;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVDepthOnlyCallDefragmenter defragmenter;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVClusterEngine clusterEngine;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]GenomeLoc> callIntervals;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String currentContig;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.samples.SampleDB sampleDB;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean doDefragmentation;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.logging.OneShotLogger oneShotLogger = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.logging.OneShotLogger([CtFieldReadImpl]logger);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String MIN_QUALITY_LONG_NAME = [CtLiteralImpl]"minimum-qs-score";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String MODEL_CALL_INTERVALS = [CtLiteralImpl]"model-call-intervals";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String BREAKPOINT_SUMMARY_STRATEGY = [CtLiteralImpl]"breakpoint-summary-strategy";

    [CtFieldImpl][CtAnnotationImpl]@org.broadinstitute.barclay.argparser.Argument(fullName = [CtFieldReadImpl]org.broadinstitute.hellbender.tools.walkers.sv.JointGermlineCNVSegmentation.MIN_QUALITY_LONG_NAME, doc = [CtLiteralImpl]"Minimum QS score to combine a variant segment")
    private [CtTypeReferenceImpl]int minQS = [CtLiteralImpl]20;

    [CtFieldImpl][CtAnnotationImpl]@org.broadinstitute.barclay.argparser.Argument(fullName = [CtFieldReadImpl]org.broadinstitute.hellbender.tools.walkers.sv.JointGermlineCNVSegmentation.MODEL_CALL_INTERVALS, doc = [CtLiteralImpl]"Intervals used for gCNV calls.  Should be preprocessed and filtered to line up with model calls. Required for exomes.")
    private [CtTypeReferenceImpl]java.io.File modelCallIntervalList;

    [CtFieldImpl][CtAnnotationImpl]@org.broadinstitute.barclay.argparser.Argument(fullName = [CtFieldReadImpl]org.broadinstitute.hellbender.tools.walkers.sv.JointGermlineCNVSegmentation.BREAKPOINT_SUMMARY_STRATEGY, doc = [CtLiteralImpl]"Strategy to use for choosing a representative value for a breakpoint cluster.")
    private [CtTypeReferenceImpl]SVClusterEngine.BreakpointSummaryStrategy breakpointSummaryStrategy = [CtFieldReadImpl]SVClusterEngine.BreakpointSummaryStrategy.MEDIAN_START_MEDIAN_END;

    [CtFieldImpl][CtAnnotationImpl]@org.broadinstitute.barclay.argparser.Argument(fullName = [CtFieldReadImpl]org.broadinstitute.hellbender.cmdline.StandardArgumentDefinitions.OUTPUT_LONG_NAME, shortName = [CtFieldReadImpl]org.broadinstitute.hellbender.cmdline.StandardArgumentDefinitions.OUTPUT_SHORT_NAME, doc = [CtLiteralImpl]"The combined output file", optional = [CtLiteralImpl]false)
    private [CtTypeReferenceImpl]java.io.File outputFile;

    [CtFieldImpl][CtAnnotationImpl]@org.broadinstitute.barclay.argparser.Argument(doc = [CtLiteralImpl]"Reference copy-number on autosomal intervals.", fullName = [CtFieldReadImpl]org.broadinstitute.hellbender.tools.copynumber.PostprocessGermlineCNVCalls.AUTOSOMAL_REF_COPY_NUMBER_LONG_NAME, minValue = [CtLiteralImpl]0, optional = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]int refAutosomalCopyNumber = [CtLiteralImpl]2;

    [CtFieldImpl][CtAnnotationImpl]@org.broadinstitute.barclay.argparser.Argument(doc = [CtBinaryOperatorImpl][CtLiteralImpl]"Contigs to treat as allosomal (i.e. choose their reference copy-number allele according to " + [CtLiteralImpl]"the sample karyotype).", fullName = [CtFieldReadImpl]org.broadinstitute.hellbender.tools.copynumber.PostprocessGermlineCNVCalls.ALLOSOMAL_CONTIG_LONG_NAME, optional = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> allosomalContigList = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"X", [CtLiteralImpl]"Y", [CtLiteralImpl]"chrX", [CtLiteralImpl]"chrY");

    [CtFieldImpl][CtJavaDocImpl]/**
     * See https://software.broadinstitute.org/gatk/documentation/article.php?id=7696 for more details on the PED
     * format. Note that each -ped argument can be tagged with NO_FAMILY_ID, NO_PARENTS, NO_SEX, NO_PHENOTYPE to
     * tell the GATK PED parser that the corresponding fields are missing from the ped file.
     */
    [CtAnnotationImpl]@org.broadinstitute.barclay.argparser.Argument(fullName = [CtFieldReadImpl]org.broadinstitute.hellbender.cmdline.StandardArgumentDefinitions.PEDIGREE_FILE_LONG_NAME, shortName = [CtFieldReadImpl]org.broadinstitute.hellbender.cmdline.StandardArgumentDefinitions.PEDIGREE_FILE_SHORT_NAME, doc = [CtLiteralImpl]"Pedigree file for samples", optional = [CtLiteralImpl]false)
    private [CtTypeReferenceImpl]java.io.File pedigreeFile = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doDictionaryCrossValidation() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtCommentImpl]// require a reference to do dictionary validation since there may be too many samples for cross-validating
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean requiresReference() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onTraversalStart() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]sampleDB = [CtInvocationImpl]initializeSampleDB();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]sampleDB == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtLiteralImpl]"No pedigree file supplied for sex genotype calls. Will attempt to infer ploidy from segments VCF genotype ploidy.");[CtCommentImpl]// note that this only works if there are called variants on the sex chromosomes

        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> samples = [CtInvocationImpl]getSamplesForVariants();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]samples != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sample : [CtVariableReadImpl]samples) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sampleDB.getSample([CtVariableReadImpl]sample) == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Sample " + [CtVariableReadImpl]sample) + [CtLiteralImpl]" is missing from the supplied pedigree file. Will attempt to infer ploidy from segments VCF genotype ploidy.");[CtCommentImpl]// note that this only works if there are called variants on the sex chromosomes

                }
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]dictionary = [CtInvocationImpl]getBestAvailableSequenceDictionary();
        [CtLocalVariableImpl][CtCommentImpl]// dictionary will not be null because this tool requiresReference()
        final [CtTypeReferenceImpl]GenomeLocParser parser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]GenomeLocParser([CtFieldReadImpl][CtThisAccessImpl]this.dictionary);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]modelCallIntervalList == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]callIntervals = [CtLiteralImpl]null;
        } else [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]GenomeLoc> inputCoverageIntervals = [CtInvocationImpl][CtTypeAccessImpl]IntervalUtils.featureFileToIntervals([CtVariableReadImpl]parser, [CtInvocationImpl][CtFieldReadImpl]modelCallIntervalList.getAbsolutePath());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]GenomeLoc> inputTraversalIntervals = [CtInvocationImpl][CtTypeAccessImpl]IntervalUtils.genomeLocsFromLocatables([CtVariableReadImpl]parser, [CtInvocationImpl]getTraversalIntervals());
            [CtAssignmentImpl][CtFieldWriteImpl]callIntervals = [CtInvocationImpl][CtTypeAccessImpl]IntervalUtils.mergeListsBySetOperator([CtVariableReadImpl]inputCoverageIntervals, [CtVariableReadImpl]inputTraversalIntervals, [CtTypeAccessImpl]IntervalSetRule.INTERSECTION);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]defragmenter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVDepthOnlyCallDefragmenter([CtFieldReadImpl]dictionary, [CtLiteralImpl]0.8, [CtFieldReadImpl]callIntervals);
        [CtAssignmentImpl][CtFieldWriteImpl]clusterEngine = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVClusterEngine([CtFieldReadImpl]dictionary, [CtLiteralImpl]true, [CtFieldReadImpl]breakpointSummaryStrategy);
        [CtAssignmentImpl][CtFieldWriteImpl]vcfWriter = [CtInvocationImpl]getVCFWriter();
    }

    [CtMethodImpl][CtCommentImpl]// TODO: this is the third copy of this method
    [CtJavaDocImpl]/**
     * Entry-point function to initialize the samples database from input data
     */
    private [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.samples.SampleDB initializeSampleDB() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.samples.SampleDBBuilder sampleDBBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.samples.SampleDBBuilder([CtFieldReadImpl]org.broadinstitute.hellbender.utils.samples.PedigreeValidationType.STRICT);[CtCommentImpl]// strict will warn about missing samples

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]pedigreeFile != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sampleDBBuilder.addSamplesFromPedigreeFiles([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtFieldReadImpl]pedigreeFile));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sampleDBBuilder.getFinalSampleDB();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]htsjdk.variant.variantcontext.writer.VariantContextWriter getVCFWriter() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]samples = [CtInvocationImpl]getSamplesForVariants();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]htsjdk.variant.vcf.VCFHeader inputVCFHeader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]htsjdk.variant.vcf.VCFHeader([CtInvocationImpl][CtInvocationImpl]getHeaderForVariants().getMetaDataInInputOrder(), [CtFieldReadImpl]samples);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]htsjdk.variant.vcf.VCFHeaderLine> headerLines = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>([CtInvocationImpl][CtVariableReadImpl]inputVCFHeader.getMetaDataInInputOrder());
        [CtInvocationImpl][CtVariableReadImpl]headerLines.addAll([CtInvocationImpl]getDefaultToolVCFHeaderLines());
        [CtInvocationImpl][CtVariableReadImpl]headerLines.add([CtInvocationImpl][CtTypeAccessImpl]org.broadinstitute.hellbender.tools.spark.sv.utils.GATKSVVCFHeaderLines.getInfoLine([CtTypeAccessImpl]GATKSVVCFConstants.SVLEN));
        [CtInvocationImpl][CtVariableReadImpl]headerLines.add([CtInvocationImpl][CtTypeAccessImpl]org.broadinstitute.hellbender.tools.spark.sv.utils.GATKSVVCFHeaderLines.getInfoLine([CtTypeAccessImpl]GATKSVVCFConstants.SVTYPE));
        [CtInvocationImpl][CtVariableReadImpl]headerLines.add([CtInvocationImpl][CtTypeAccessImpl]htsjdk.variant.vcf.VCFStandardHeaderLines.getInfoLine([CtTypeAccessImpl]VCFConstants.ALLELE_FREQUENCY_KEY));
        [CtInvocationImpl][CtVariableReadImpl]headerLines.add([CtInvocationImpl][CtTypeAccessImpl]htsjdk.variant.vcf.VCFStandardHeaderLines.getInfoLine([CtTypeAccessImpl]VCFConstants.ALLELE_COUNT_KEY));
        [CtInvocationImpl][CtVariableReadImpl]headerLines.add([CtInvocationImpl][CtTypeAccessImpl]htsjdk.variant.vcf.VCFStandardHeaderLines.getInfoLine([CtTypeAccessImpl]VCFConstants.ALLELE_NUMBER_KEY));
        [CtLocalVariableImpl][CtTypeReferenceImpl]htsjdk.variant.variantcontext.writer.VariantContextWriter writer = [CtInvocationImpl]createVCFWriter([CtFieldReadImpl]outputFile);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> sampleNameSet = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.genotyper.IndexedSampleList([CtFieldReadImpl]samples).asSetOfSamples();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]htsjdk.variant.vcf.VCFHeader vcfHeader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]htsjdk.variant.vcf.VCFHeader([CtVariableReadImpl]headerLines, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtVariableReadImpl]sampleNameSet));
        [CtInvocationImpl][CtVariableReadImpl]writer.writeHeader([CtVariableReadImpl]vcfHeader);
        [CtReturnImpl]return [CtVariableReadImpl]writer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param variantContexts
     * 		VariantContexts from driving variants with matching start position
     * 		NOTE: This will never be empty
     * @param referenceContext
     * 		ReferenceContext object covering the reference of the longest spanning VariantContext
     * @param readsContexts
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void apply([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariantContext> variantContexts, [CtParameterImpl][CtTypeReferenceImpl]org.broadinstitute.hellbender.engine.ReferenceContext referenceContext, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.broadinstitute.hellbender.engine.ReadsContext> readsContexts) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]currentContig == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]currentContig = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]variantContexts.get([CtLiteralImpl]0).getContig();[CtCommentImpl]// variantContexts should have identical start, so choose 0th arbitrarily

        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]variantContexts.get([CtLiteralImpl]0).getContig().equals([CtFieldReadImpl]currentContig)) [CtBlockImpl]{
            [CtInvocationImpl]processClusters();
            [CtAssignmentImpl][CtFieldWriteImpl]currentContig = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]variantContexts.get([CtLiteralImpl]0).getContig();
        }
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]VariantContext vc : [CtVariableReadImpl]variantContexts) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]vc.getGenotypes().size() != [CtLiteralImpl]1) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]oneShotLogger.warn([CtLiteralImpl]"Multi-sample VCFs found, which are assumed to be pre-clustered. Skipping defragmentation.");
                [CtAssignmentImpl][CtFieldWriteImpl]doDefragmentation = [CtLiteralImpl]false;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]doDefragmentation = [CtLiteralImpl]true;
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecord record = [CtInvocationImpl][CtTypeAccessImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecord.createDepthOnlyFromGCNV([CtVariableReadImpl]vc, [CtFieldReadImpl]minQS);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]record != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtFieldReadImpl]doDefragmentation) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]defragmenter.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecordWithEvidence([CtVariableReadImpl]record));
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]clusterEngine.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecordWithEvidence([CtVariableReadImpl]record));
                }
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object onTraversalSuccess() [CtBlockImpl]{
        [CtInvocationImpl]processClusters();
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processClusters() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]defragmenter.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecordWithEvidence> defragmentedCalls = [CtInvocationImpl][CtFieldReadImpl]defragmenter.getOutput();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]defragmentedCalls.stream().forEachOrdered([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]clusterEngine::add);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Jack and Isaac cluster first and then defragment
        final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecordWithEvidence> clusteredCalls = [CtInvocationImpl][CtFieldReadImpl]clusterEngine.getOutput();
        [CtInvocationImpl]write([CtVariableReadImpl]clusteredCalls);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void write([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecordWithEvidence> calls) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]htsjdk.samtools.reference.ReferenceSequenceFile reference = [CtInvocationImpl][CtTypeAccessImpl]org.broadinstitute.hellbender.utils.reference.ReferenceUtils.createReferenceReader([CtInvocationImpl][CtFieldReadImpl]referenceArguments.getReferenceSpecifier());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariantContext> sortedCalls = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]calls.stream().sorted([CtInvocationImpl][CtCommentImpl]// VCs have to be sorted by end as well
        [CtTypeAccessImpl]java.util.Comparator.comparing([CtLambdaImpl]([CtParameterImpl] c) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]SimpleInterval([CtInvocationImpl][CtVariableReadImpl]c.getContig(), [CtInvocationImpl][CtVariableReadImpl]c.getStart(), [CtInvocationImpl][CtVariableReadImpl]c.getEnd()), [CtInvocationImpl][CtTypeAccessImpl]IntervalUtils.getDictionaryOrderComparator([CtFieldReadImpl]dictionary))).map([CtLambdaImpl]([CtParameterImpl] record) -> [CtInvocationImpl]buildVariantContext([CtVariableReadImpl]record, [CtVariableReadImpl]reference)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]VariantContext> it = [CtInvocationImpl][CtVariableReadImpl]sortedCalls.iterator();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]VariantContext> overlappingVCs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]VariantContext prev = [CtInvocationImpl][CtVariableReadImpl]it.next();
        [CtInvocationImpl][CtVariableReadImpl]overlappingVCs.add([CtVariableReadImpl]prev);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int clusterEnd = [CtInvocationImpl][CtVariableReadImpl]prev.getEnd();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clusterContig = [CtInvocationImpl][CtVariableReadImpl]prev.getContig();
        [CtWhileImpl][CtCommentImpl]// gather groups of overlapping VCs and update the genotype copy numbers appropriately
        while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]VariantContext curr = [CtInvocationImpl][CtVariableReadImpl]it.next();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]curr.getStart() < [CtVariableReadImpl]clusterEnd) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]curr.getContig().equals([CtVariableReadImpl]clusterContig)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]overlappingVCs.add([CtVariableReadImpl]curr);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]curr.getEnd() > [CtVariableReadImpl]clusterEnd) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]clusterEnd = [CtInvocationImpl][CtVariableReadImpl]curr.getEnd();
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariantContext> resolvedVCs = [CtInvocationImpl]resolveVariantContexts([CtVariableReadImpl]overlappingVCs);
                [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]VariantContext vc : [CtVariableReadImpl]resolvedVCs) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]vcfWriter.add([CtVariableReadImpl]vc);
                }
                [CtAssignmentImpl][CtVariableWriteImpl]overlappingVCs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtInvocationImpl][CtVariableReadImpl]overlappingVCs.add([CtVariableReadImpl]curr);
                [CtAssignmentImpl][CtVariableWriteImpl]clusterEnd = [CtInvocationImpl][CtVariableReadImpl]curr.getEnd();
                [CtAssignmentImpl][CtVariableWriteImpl]clusterContig = [CtInvocationImpl][CtVariableReadImpl]curr.getContig();
            }
        } 
        [CtLocalVariableImpl][CtCommentImpl]// write out the last set of overlapping VCs
        final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariantContext> resolvedVCs = [CtInvocationImpl]resolveVariantContexts([CtVariableReadImpl]overlappingVCs);
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]VariantContext vc : [CtVariableReadImpl]resolvedVCs) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]vcfWriter.add([CtVariableReadImpl]vc);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Ensure genotype calls are consistent for overlapping variant contexts
     * Note that we assume that a sample will not occur twice with the same copy number because it should have been defragmented
     *
     * @param overlappingVCs
     * @return  */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariantContext> resolveVariantContexts([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariantContext> overlappingVCs) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]Utils.nonNull([CtVariableReadImpl]overlappingVCs);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariantContext> resolvedVCs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]VariantContext> it = [CtInvocationImpl][CtVariableReadImpl]overlappingVCs.iterator();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.commons.lang3.tuple.MutablePair<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Integer>> sampleCopyNumbers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();[CtCommentImpl]// sampleName, copyNumber, endPos -- it's safe to just use position because if the VCs overlap then they must be on the same contig

        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]VariantContext curr = [CtInvocationImpl][CtVariableReadImpl]it.next();
            [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]Genotype g : [CtInvocationImpl][CtVariableReadImpl]curr.getGenotypes()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// if this sample is in the table and we have a new variant for this sample, update the table
                final [CtTypeReferenceImpl]java.lang.String s = [CtInvocationImpl][CtVariableReadImpl]g.getSampleName();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.containsKey([CtVariableReadImpl]s) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.get([CtVariableReadImpl]s).getLeft() != [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]g.getExtendedAttribute([CtTypeAccessImpl]GATKSVVCFConstants.COPY_NUMBER_FORMAT).toString()))) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.get([CtVariableReadImpl]s).setRight([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]g.getExtendedAttribute([CtTypeAccessImpl]GATKSVVCFConstants.COPY_NUMBER_FORMAT).toString()));
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.get([CtVariableReadImpl]s).setLeft([CtInvocationImpl][CtVariableReadImpl]curr.getEnd());
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]resolvedVCs.add([CtInvocationImpl]updateGenotypes([CtVariableReadImpl]curr, [CtVariableReadImpl]sampleCopyNumbers));
            [CtForEachImpl][CtCommentImpl]// update copy number table for subsequent VCs using variants genotypes from input VCs
            for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]Genotype g : [CtInvocationImpl][CtVariableReadImpl]curr.getGenotypes()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]g.hasAnyAttribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.put([CtInvocationImpl][CtVariableReadImpl]g.getSampleName(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.lang3.tuple.MutablePair<>([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]g.getExtendedAttribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN).toString()), [CtInvocationImpl][CtVariableReadImpl]curr.getAttributeAsInt([CtTypeAccessImpl]VCFConstants.END_KEY, [CtInvocationImpl][CtVariableReadImpl]curr.getStart())));
                }
            }
        } 
        [CtReturnImpl]return [CtVariableReadImpl]resolvedVCs;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param vc
     * 		VariantContext with just variant samples
     * @param sampleCopyNumbers
     * 		may be modified to remove terminated variants
     * @return new VariantContext with AC and AF
     */
    private [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.walkers.sv.VariantContext updateGenotypes([CtParameterImpl]final [CtTypeReferenceImpl]VariantContext vc, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.commons.lang3.tuple.MutablePair<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Integer>> sampleCopyNumbers) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]VariantContextBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]VariantContextBuilder([CtVariableReadImpl]vc);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Genotype> newGenotypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]Allele vcRefAllele = [CtInvocationImpl][CtVariableReadImpl]vc.getReference();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]Allele, [CtTypeReferenceImpl]java.lang.Long> alleleCountMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.put([CtTypeAccessImpl]GATKSVVCFConstants.DEL_ALLELE, [CtLiteralImpl]0L);
        [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.put([CtTypeAccessImpl]GATKSVVCFConstants.DUP_ALLELE, [CtLiteralImpl]0L);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int alleleNumber = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String sample : [CtFieldReadImpl]samples) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int samplePloidy;
            [CtIfImpl][CtCommentImpl]// "square off" the genotype matrix by adding homRef calls
            if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.containsKey([CtVariableReadImpl]sample)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]vc.hasGenotype([CtVariableReadImpl]sample))) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]GenotypeBuilder genotypeBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]GenotypeBuilder([CtVariableReadImpl]sample);
                [CtAssignmentImpl][CtVariableWriteImpl]samplePloidy = [CtInvocationImpl]getSamplePloidy([CtVariableReadImpl]sample, [CtInvocationImpl][CtVariableReadImpl]vc.getContig(), [CtLiteralImpl]null);
                [CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.alleles([CtInvocationImpl][CtTypeAccessImpl]GATKVariantContextUtils.makePloidyLengthAlleleList([CtVariableReadImpl]samplePloidy, [CtVariableReadImpl]vcRefAllele));
                [CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.attribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN, [CtVariableReadImpl]samplePloidy);
                [CtInvocationImpl][CtVariableReadImpl]newGenotypes.add([CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.make());
            } else [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]GenotypeBuilder genotypeBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]GenotypeBuilder([CtVariableReadImpl]sample);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]Genotype g = [CtInvocationImpl][CtVariableReadImpl]vc.getGenotype([CtVariableReadImpl]sample);[CtCommentImpl]// may be null

                [CtAssignmentImpl][CtVariableWriteImpl]samplePloidy = [CtInvocationImpl]getSamplePloidy([CtVariableReadImpl]sample, [CtInvocationImpl][CtVariableReadImpl]vc.getContig(), [CtVariableReadImpl]g);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int copyNumber = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]g != [CtLiteralImpl]null) ? [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]g.getExtendedAttribute([CtTypeAccessImpl]GATKSVVCFConstants.COPY_NUMBER_FORMAT, [CtVariableReadImpl]samplePloidy).toString()) : [CtVariableReadImpl]samplePloidy;
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Allele> alleles;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]doDefragmentation || [CtBinaryOperatorImpl]([CtVariableReadImpl]g == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// if it's a multi-sample VCF we can trust the input genotypes
                    [CtVariableWriteImpl]alleles = [CtInvocationImpl][CtTypeAccessImpl]GATKSVVariantContextUtils.makeGenotypeAlleles([CtVariableReadImpl]copyNumber, [CtVariableReadImpl]samplePloidy, [CtVariableReadImpl]vcRefAllele);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]alleles = [CtInvocationImpl][CtVariableReadImpl]g.getAlleles();
                }
                [CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.alleles([CtVariableReadImpl]alleles);
                [CtIfImpl][CtCommentImpl]// check for genotype in VC because we don't want to count overlapping events (in sampleCopyNumbers map) towards AC
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]vc.hasGenotype([CtVariableReadImpl]sample) && [CtInvocationImpl][CtVariableReadImpl]alleles.contains([CtTypeAccessImpl]GATKSVVCFConstants.DEL_ALLELE)) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Long temp = [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.get([CtTypeAccessImpl]GATKSVVCFConstants.DEL_ALLELE);
                    [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.put([CtTypeAccessImpl]GATKSVVCFConstants.DEL_ALLELE, [CtBinaryOperatorImpl][CtVariableReadImpl]temp + [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]alleles.stream().filter([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Allele::isNonReference).count());
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]vc.hasGenotype([CtVariableReadImpl]sample) && [CtBinaryOperatorImpl]([CtVariableReadImpl]copyNumber > [CtVariableReadImpl]samplePloidy)) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Long temp = [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.get([CtTypeAccessImpl]GATKSVVCFConstants.DUP_ALLELE);
                    [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.put([CtTypeAccessImpl]GATKSVVCFConstants.DUP_ALLELE, [CtBinaryOperatorImpl][CtVariableReadImpl]temp + [CtLiteralImpl]1);[CtCommentImpl]// best we can do for dupes is carrier frequency

                }
                [CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.attribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN, [CtVariableReadImpl]copyNumber);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.containsKey([CtVariableReadImpl]sample)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.get([CtVariableReadImpl]sample).getRight() > [CtInvocationImpl][CtVariableReadImpl]vc.getStart()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.attribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sampleCopyNumbers.get([CtVariableReadImpl]sample).getLeft());
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]newGenotypes.add([CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.make());
            }
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]alleleNumber += [CtVariableReadImpl]samplePloidy;
        }
        [CtInvocationImpl][CtVariableReadImpl]builder.genotypes([CtVariableReadImpl]newGenotypes);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]alleleNumber > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]vc.getAlternateAlleles().size() == [CtLiteralImpl]1) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]long AC;
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]vc.getAlternateAllele([CtLiteralImpl]0).equals([CtTypeAccessImpl]GATKSVVCFConstants.DUP_ALLELE)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]AC = [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.get([CtTypeAccessImpl]GATKSVVCFConstants.DUP_ALLELE);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]AC = [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.get([CtTypeAccessImpl]GATKSVVCFConstants.DEL_ALLELE);
                }
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.attribute([CtTypeAccessImpl]VCFConstants.ALLELE_COUNT_KEY, [CtVariableReadImpl]AC).attribute([CtTypeAccessImpl]VCFConstants.ALLELE_FREQUENCY_KEY, [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.valueOf([CtVariableReadImpl]AC) / [CtVariableReadImpl]alleleNumber).attribute([CtTypeAccessImpl]VCFConstants.ALLELE_NUMBER_KEY, [CtVariableReadImpl]alleleNumber);
            } else [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> alleleCounts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Double> alleleFreqs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtForEachImpl][CtCommentImpl]// if we merged and del and a dupe from different callsets, then make sure the VC has both alleles
                [CtCommentImpl]// if (alleleCountMap.get(GATKSVVCFConstants.DEL_ALLELE) > 0 && alleleCountMap.get(GATKSVVCFConstants.DUP_ALLELE) > 0) {
                [CtCommentImpl]// builder.alleles(vc.getReference().getDisplayString(), GATKSVVCFConstants.DUP_ALLELE.getDisplayString(), GATKSVVCFConstants.DEL_ALLELE.getDisplayString());
                [CtCommentImpl]// }
                for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]Allele a : [CtInvocationImpl][CtVariableReadImpl]builder.getAlleles()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]a.isReference()) [CtBlockImpl]{
                        [CtContinueImpl]continue;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]alleleCounts.add([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]alleleCountMap.containsKey([CtVariableReadImpl]a) ? [CtInvocationImpl][CtVariableReadImpl]alleleCountMap.get([CtVariableReadImpl]a) : [CtLiteralImpl]0L);
                    [CtInvocationImpl][CtVariableReadImpl]alleleFreqs.add([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]alleleCountMap.containsKey([CtVariableReadImpl]a) ? [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.valueOf([CtInvocationImpl][CtVariableReadImpl]alleleCountMap.get([CtVariableReadImpl]a)) / [CtVariableReadImpl]alleleNumber : [CtLiteralImpl]0L);
                }
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.attribute([CtTypeAccessImpl]VCFConstants.ALLELE_COUNT_KEY, [CtVariableReadImpl]alleleCounts).attribute([CtTypeAccessImpl]VCFConstants.ALLELE_FREQUENCY_KEY, [CtVariableReadImpl]alleleFreqs).attribute([CtTypeAccessImpl]VCFConstants.ALLELE_NUMBER_KEY, [CtVariableReadImpl]alleleNumber);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.make();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param sampleName
     * @param contig
     * @param g
     * 		may be null
     * @return  */
    private [CtTypeReferenceImpl]int getSamplePloidy([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String sampleName, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String contig, [CtParameterImpl]final [CtTypeReferenceImpl]Genotype g) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]allosomalContigList.contains([CtVariableReadImpl]contig)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]refAutosomalCopyNumber;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int samplePloidy = [CtLiteralImpl]1;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]sampleDB == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]sampleDB.getSample([CtVariableReadImpl]sampleName) == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]g != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]samplePloidy = [CtInvocationImpl][CtVariableReadImpl]g.getPloidy();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]oneShotLogger.warn([CtLiteralImpl]"Samples missing from pedigree assumed to have ploidy 1 on allosomes.");
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]sampleDB != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]sampleDB.getSample([CtVariableReadImpl]sampleName) != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.broadinstitute.hellbender.utils.samples.Sex sampleSex = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]sampleDB.getSample([CtVariableReadImpl]sampleName).getSex();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contig.equals([CtLiteralImpl]"X") || [CtInvocationImpl][CtVariableReadImpl]contig.equals([CtLiteralImpl]"chrX")) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]sampleSex.equals([CtTypeAccessImpl]Sex.FEMALE)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]samplePloidy = [CtLiteralImpl]2;
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]sampleSex.equals([CtTypeAccessImpl]Sex.MALE)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]samplePloidy = [CtLiteralImpl]1;
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contig.equals([CtLiteralImpl]"Y") || [CtInvocationImpl][CtVariableReadImpl]contig.equals([CtLiteralImpl]"chrY")) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]sampleSex.equals([CtTypeAccessImpl]Sex.FEMALE)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]samplePloidy = [CtLiteralImpl]0;
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]sampleSex.equals([CtTypeAccessImpl]Sex.MALE)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]samplePloidy = [CtLiteralImpl]1;
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]samplePloidy;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.walkers.sv.VariantContext buildVariantContext([CtParameterImpl]final [CtTypeReferenceImpl]org.broadinstitute.hellbender.tools.sv.SVCallRecordWithEvidence call, [CtParameterImpl]final [CtTypeReferenceImpl]htsjdk.samtools.reference.ReferenceSequenceFile reference) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]Utils.nonNull([CtVariableReadImpl]call);
        [CtInvocationImpl][CtTypeAccessImpl]Utils.nonNull([CtVariableReadImpl]reference);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Allele> outputAlleles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]Allele refAllele = [CtInvocationImpl][CtTypeAccessImpl]Allele.create([CtInvocationImpl][CtTypeAccessImpl]org.broadinstitute.hellbender.utils.reference.ReferenceUtils.getRefBaseAtPosition([CtVariableReadImpl]reference, [CtInvocationImpl][CtVariableReadImpl]call.getContig(), [CtInvocationImpl][CtVariableReadImpl]call.getStart()), [CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]outputAlleles.add([CtVariableReadImpl]refAllele);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]call.getType().equals([CtTypeAccessImpl]StructuralVariantType.CNV)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]outputAlleles.add([CtInvocationImpl][CtTypeAccessImpl]Allele.create([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]call.getType().name()) + [CtLiteralImpl]">", [CtLiteralImpl]false));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]outputAlleles.add([CtTypeAccessImpl]GATKSVVCFConstants.DEL_ALLELE);
            [CtInvocationImpl][CtVariableReadImpl]outputAlleles.add([CtTypeAccessImpl]GATKSVVCFConstants.DUP_ALLELE);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]VariantContextBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]VariantContextBuilder([CtLiteralImpl]"", [CtInvocationImpl][CtVariableReadImpl]call.getContig(), [CtInvocationImpl][CtVariableReadImpl]call.getStart(), [CtInvocationImpl][CtVariableReadImpl]call.getEnd(), [CtVariableReadImpl]outputAlleles);
        [CtInvocationImpl][CtVariableReadImpl]builder.attribute([CtTypeAccessImpl]VCFConstants.END_KEY, [CtInvocationImpl][CtVariableReadImpl]call.getEnd());
        [CtInvocationImpl][CtVariableReadImpl]builder.attribute([CtTypeAccessImpl]GATKSVVCFConstants.SVLEN, [CtInvocationImpl][CtVariableReadImpl]call.getLength());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]call.getType().equals([CtTypeAccessImpl]StructuralVariantType.CNV)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.attribute([CtTypeAccessImpl]VCFConstants.SVTYPE, [CtLiteralImpl]"MCNV");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.attribute([CtTypeAccessImpl]VCFConstants.SVTYPE, [CtInvocationImpl][CtVariableReadImpl]call.getType());
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Genotype> genotypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]Genotype g : [CtInvocationImpl][CtVariableReadImpl]call.getGenotypes()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]GenotypeBuilder genotypeBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]GenotypeBuilder([CtVariableReadImpl]g);
            [CtLocalVariableImpl][CtCommentImpl]// update reference alleles
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Allele> newGenotypeAlleles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Allele a : [CtInvocationImpl][CtVariableReadImpl]g.getAlleles()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]a.isReference()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]newGenotypeAlleles.add([CtVariableReadImpl]refAllele);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]newGenotypeAlleles.add([CtVariableReadImpl]a);
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.alleles([CtVariableReadImpl]newGenotypeAlleles);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]g.hasAnyAttribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.attribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN, [CtInvocationImpl][CtVariableReadImpl]g.getExtendedAttribute([CtTypeAccessImpl]GermlineCNVSegmentVariantComposer.CN));
            }
            [CtInvocationImpl][CtVariableReadImpl]genotypes.add([CtInvocationImpl][CtVariableReadImpl]genotypeBuilder.make());
        }
        [CtInvocationImpl][CtVariableReadImpl]builder.genotypes([CtVariableReadImpl]genotypes);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.make();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void closeTool() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]vcfWriter != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]vcfWriter.close();
        }
    }
}
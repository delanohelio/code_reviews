[CompilationUnitImpl][CtPackageDeclarationImpl]package org.kframework.backend.java.symbolic;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import org.kframework.attributes.Att;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.Formatter;
[CtImportImpl]import java.util.LinkedHashSet;
[CtUnresolvedImport]import org.kframework.backend.java.kil.JavaSymbolicObject;
[CtUnresolvedImport]import org.kframework.backend.java.kil.Sort;
[CtUnresolvedImport]import org.kframework.backend.java.kil.BuiltinMap;
[CtUnresolvedImport]import org.kframework.backend.java.kil.KList;
[CtUnresolvedImport]import org.kframework.backend.java.kil.KLabelConstant;
[CtImportImpl]import java.util.LinkedHashMap;
[CtUnresolvedImport]import org.kframework.krun.KRunOptions;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import org.kframework.backend.java.builtins.BitVector;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.kframework.backend.java.builtins.IntToken;
[CtImportImpl]import org.apache.commons.lang3.tuple.Pair;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import org.kframework.backend.java.kil.Variable;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtUnresolvedImport]import org.kframework.backend.java.kil.BuiltinList;
[CtUnresolvedImport]import org.kframework.backend.java.builtins.UninterpretedToken;
[CtUnresolvedImport]import org.kframework.backend.java.builtins.FloatToken;
[CtUnresolvedImport]import org.kframework.backend.java.kil.Rule;
[CtUnresolvedImport]import org.kframework.backend.java.kil.SortSignature;
[CtUnresolvedImport]import org.kframework.builtin.Sorts;
[CtUnresolvedImport]import com.google.common.collect.Sets;
[CtImportImpl]import java.util.Stack;
[CtUnresolvedImport]import org.kframework.utils.errorsystem.KEMException;
[CtUnresolvedImport]import org.kframework.backend.java.kil.SMTLibTerm;
[CtUnresolvedImport]import org.kframework.kore.KORE;
[CtUnresolvedImport]import org.kframework.backend.java.kil.Definition;
[CtImportImpl]import java.math.BigInteger;
[CtUnresolvedImport]import org.kframework.backend.java.kil.Term;
[CtUnresolvedImport]import org.kframework.backend.java.kil.GlobalContext;
[CtUnresolvedImport]import org.kframework.backend.java.kil.KItem;
[CtUnresolvedImport]import com.google.common.base.Joiner;
[CtUnresolvedImport]import org.kframework.backend.java.builtins.BoolToken;
[CtClassImpl]public class KILtoSMTLib extends [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.CopyOnWriteTransformer {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]com.google.common.collect.ImmutableSet<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Sort> SMTLIB_BUILTIN_SORTS = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.of([CtTypeAccessImpl]Sort.BOOL, [CtTypeAccessImpl]Sort.INT, [CtTypeAccessImpl]Sort.BIT_VECTOR, [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.builtin.Sorts.Float()), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.builtin.Sorts.String()), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"IntSet")), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"MIntSet")), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"FloatSet")), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"StringSet")), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"IntSeq")), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"MIntSeq")), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"FloatSeq")), [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"StringSeq")));

    [CtFieldImpl]public static final [CtTypeReferenceImpl]com.google.common.collect.ImmutableSet<[CtTypeReferenceImpl]java.lang.String> SMTLIB_BUILTIN_FUNCTIONS = [CtInvocationImpl][CtCommentImpl]/* array theory */
    [CtCommentImpl]/* core theory */
    [CtCommentImpl]/* int theory */
    [CtCommentImpl]/* extra int theory */
    [CtCommentImpl]/* extra float theory */
    [CtCommentImpl]/* bit vector theory */
    [CtCommentImpl]/* z3-specific bit vector theory */
    [CtCommentImpl]/* bit vector extras */
    [CtCommentImpl]/* string theory */
    [CtCommentImpl]/* set theory */
    [CtCommentImpl]/* float set theory */
    [CtCommentImpl]/* string set theory */
    [CtCommentImpl]/* associative sequence theory */
    [CtCommentImpl]/* bool2int */
    [CtTypeAccessImpl]com.google.common.collect.ImmutableSet.of([CtLiteralImpl]"forall", [CtLiteralImpl]"exists", [CtLiteralImpl]"select", [CtLiteralImpl]"store", [CtLiteralImpl]"not", [CtLiteralImpl]"and", [CtLiteralImpl]"or", [CtLiteralImpl]"xor", [CtLiteralImpl]"=>", [CtLiteralImpl]"=", [CtLiteralImpl]"distinct", [CtLiteralImpl]"ite", [CtLiteralImpl]"+", [CtLiteralImpl]"-", [CtLiteralImpl]"*", [CtLiteralImpl]"div", [CtLiteralImpl]"mod", [CtLiteralImpl]"abs", [CtLiteralImpl]"<=", [CtLiteralImpl]"<", [CtLiteralImpl]">=", [CtLiteralImpl]">", [CtLiteralImpl]"^", [CtLiteralImpl]"int_max", [CtLiteralImpl]"int_min", [CtLiteralImpl]"int_abs", [CtLiteralImpl]"remainder", [CtLiteralImpl]"min", [CtLiteralImpl]"max", [CtLiteralImpl]"==", [CtLiteralImpl]"concat", [CtLiteralImpl]"extract", [CtLiteralImpl]"bvnot", [CtLiteralImpl]"bvneg", [CtLiteralImpl]"bvand", [CtLiteralImpl]"bvor", [CtLiteralImpl]"bvadd", [CtLiteralImpl]"bvmul", [CtLiteralImpl]"bvudiv", [CtLiteralImpl]"bvurem", [CtLiteralImpl]"bvshl", [CtLiteralImpl]"bvlshr", [CtLiteralImpl]"bvult", [CtLiteralImpl]"bvsub", [CtLiteralImpl]"bvxor", [CtLiteralImpl]"bvslt", [CtLiteralImpl]"bvule", [CtLiteralImpl]"bvsle", [CtLiteralImpl]"bvugt", [CtLiteralImpl]"bvsgt", [CtLiteralImpl]"bvuge", [CtLiteralImpl]"bvsge", [CtLiteralImpl]"bv2int", [CtLiteralImpl]"mint_signed_of_unsigned", [CtLiteralImpl]"string_lt", [CtLiteralImpl]"string_le", [CtLiteralImpl]"string_gt", [CtLiteralImpl]"string_ge", [CtLiteralImpl]"smt_set_mem", [CtLiteralImpl]"smt_miset_mem", [CtLiteralImpl]"smt_set_add", [CtLiteralImpl]"smt_miset_add", [CtLiteralImpl]"smt_set_emp", [CtLiteralImpl]"smt_miset_emp", [CtLiteralImpl]"smt_set_cup", [CtLiteralImpl]"smt_miset_cup", [CtLiteralImpl]"smt_set_cap", [CtLiteralImpl]"smt_miset_cap", [CtLiteralImpl]"smt_set_com", [CtLiteralImpl]"smt_miset_com", [CtLiteralImpl]"smt_set_ele", [CtLiteralImpl]"smt_miset_ele", [CtLiteralImpl]"smt_set_dif", [CtLiteralImpl]"smt_miset_dif", [CtLiteralImpl]"smt_set_sub", [CtLiteralImpl]"smt_miset_sub", [CtLiteralImpl]"smt_set_lt", [CtLiteralImpl]"smt_miset_lt", [CtLiteralImpl]"smt_set_le", [CtLiteralImpl]"smt_miset_le", [CtLiteralImpl]"float_set_mem", [CtLiteralImpl]"float_set_add", [CtLiteralImpl]"float_set_emp", [CtLiteralImpl]"float_set_cup", [CtLiteralImpl]"float_set_cap", [CtLiteralImpl]"float_set_com", [CtLiteralImpl]"float_set_ele", [CtLiteralImpl]"float_set_dif", [CtLiteralImpl]"float_set_sub", [CtLiteralImpl]"float_set_lt", [CtLiteralImpl]"float_set_le", [CtLiteralImpl]"string_set_mem", [CtLiteralImpl]"string_set_add", [CtLiteralImpl]"string_set_emp", [CtLiteralImpl]"string_set_cup", [CtLiteralImpl]"string_set_cap", [CtLiteralImpl]"string_set_com", [CtLiteralImpl]"string_set_ele", [CtLiteralImpl]"string_set_dif", [CtLiteralImpl]"string_set_sub", [CtLiteralImpl]"string_set_lt", [CtLiteralImpl]"string_set_le", [CtLiteralImpl]"smt_seq_concat", [CtLiteralImpl]"smt_seq_elem", [CtLiteralImpl]"smt_seq_nil", [CtLiteralImpl]"smt_seq_len", [CtLiteralImpl]"smt_seq_sum", [CtLiteralImpl]"smt_seq2set", [CtLiteralImpl]"smt_seq_sorted", [CtLiteralImpl]"smt_seq_filter", [CtLiteralImpl]"smt_bool2int");

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.CharSequence translateConstraint([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.ConjunctiveFormula constraint) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib kil2SMT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib([CtLiteralImpl]true, [CtInvocationImpl][CtVariableReadImpl]constraint.globalContext());
        [CtLocalVariableImpl][CtCommentImpl]// this line has side effects used later
        [CtTypeReferenceImpl]java.lang.CharSequence expression = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kil2SMT.translate([CtVariableReadImpl]constraint).expression();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]1024);
        [CtInvocationImpl][CtVariableReadImpl]kil2SMT.appendSortAndFunctionDeclarations([CtVariableReadImpl]sb, [CtInvocationImpl][CtVariableReadImpl]kil2SMT.variables());
        [CtInvocationImpl][CtVariableReadImpl]kil2SMT.appendAxioms([CtVariableReadImpl]sb);
        [CtInvocationImpl][CtVariableReadImpl]kil2SMT.appendConstantDeclarations([CtVariableReadImpl]sb, [CtInvocationImpl][CtVariableReadImpl]kil2SMT.variables());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(assert ").append([CtVariableReadImpl]expression).append([CtLiteralImpl]")");
        [CtReturnImpl]return [CtVariableReadImpl]sb;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates the z3 query for "left /\ !right".
     * left -> right <==> !(left /\ !right)
     * => this query should be unsat for implication to be proven.
     */
    public static [CtTypeReferenceImpl]java.lang.CharSequence translateImplication([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.ConjunctiveFormula leftHandSide, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.ConjunctiveFormula rightHandSide, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> existentialQuantVars) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib leftTransformer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib([CtLiteralImpl]true, [CtInvocationImpl][CtVariableReadImpl]leftHandSide.globalContext());
        [CtLocalVariableImpl][CtCommentImpl]// termAbstractionMap is shared between transformers
        [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib rightTransformer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib([CtLiteralImpl]false, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rightHandSide.globalContext().getDefinition(), [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]rightHandSide.globalContext().krunOptions, [CtInvocationImpl][CtVariableReadImpl]rightHandSide.globalContext(), [CtFieldReadImpl][CtVariableReadImpl]leftTransformer.termAbstractionMap);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence leftExpression = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]leftTransformer.translate([CtVariableReadImpl]leftHandSide).expression();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String rightExpression = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rightTransformer.translate([CtVariableReadImpl]rightHandSide).expression().toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]1024);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.Sets.SetView<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> allVars = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.union([CtInvocationImpl][CtVariableReadImpl]leftTransformer.variables(), [CtInvocationImpl][CtVariableReadImpl]rightTransformer.variables());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> usedExistentialQuantVars = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.intersection([CtVariableReadImpl]existentialQuantVars, [CtInvocationImpl][CtVariableReadImpl]rightTransformer.variables());
        [CtInvocationImpl][CtVariableReadImpl]leftTransformer.appendSortAndFunctionDeclarations([CtVariableReadImpl]sb, [CtVariableReadImpl]allVars);
        [CtInvocationImpl][CtVariableReadImpl]leftTransformer.appendAxioms([CtVariableReadImpl]sb);
        [CtInvocationImpl][CtVariableReadImpl]leftTransformer.appendConstantDeclarations([CtVariableReadImpl]sb, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.difference([CtVariableReadImpl]allVars, [CtVariableReadImpl]usedExistentialQuantVars));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(assert (and\n  ");
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]leftExpression);
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n  (not ");
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]usedExistentialQuantVars.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(exists (");
            [CtInvocationImpl][CtVariableReadImpl]leftTransformer.appendQuantifiedVariables([CtVariableReadImpl]sb, [CtVariableReadImpl]usedExistentialQuantVars);
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]") ");
        }
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]rightExpression);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]usedExistentialQuantVars.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
        }
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")\n))");
        [CtReturnImpl]return [CtVariableReadImpl]sb;
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.kframework.backend.java.kil.Definition definition;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.kframework.backend.java.kil.GlobalContext globalContext;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.kframework.krun.KRunOptions krunOptions;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Flag indicating whether KItem terms and equalities that cannot be translated can be abstracted away into
     * fresh variables. If the flag is false and untranslatable term is encountered, an exception will be thrown instead.
     */
    private final [CtTypeReferenceImpl]boolean allowNewVars;

    [CtFieldImpl][CtCommentImpl]// All sets/maps are LinkedHashXXX, to avoid non-determinism when iterated and produce consistent logs.
    private final [CtTypeReferenceImpl]java.util.LinkedHashSet<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> variables;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Term, [CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> termAbstractionMap;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]org.kframework.backend.java.builtins.UninterpretedToken, [CtTypeReferenceImpl]java.lang.Integer> tokenEncoding;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Stack<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Term> binders;

    [CtConstructorImpl]private KILtoSMTLib([CtParameterImpl][CtTypeReferenceImpl]boolean allowNewVars, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.GlobalContext global) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]allowNewVars, [CtInvocationImpl][CtVariableReadImpl]global.getDefinition(), [CtFieldReadImpl][CtVariableReadImpl]global.krunOptions, [CtVariableReadImpl]global, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>());
    }

    [CtConstructorImpl]private KILtoSMTLib([CtParameterImpl][CtTypeReferenceImpl]boolean allowNewVars, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Definition definition, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.krun.KRunOptions krunOptions, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.GlobalContext global) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]allowNewVars, [CtVariableReadImpl]definition, [CtVariableReadImpl]krunOptions, [CtVariableReadImpl]global, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>());
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     *
     * @param allowNewVars
     * 		If true, untranslatable terms not present in {@code termAbstractionMap} will be
     * 		substituted with fresh vars. If false, only terms already present in the map will be
     * 		substituted. Also, if true, substitutions will be translated into Z3 as equalities.
     */
    private KILtoSMTLib([CtParameterImpl][CtTypeReferenceImpl]boolean allowNewVars, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Definition definition, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.krun.KRunOptions krunOptions, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.GlobalContext global, [CtParameterImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Term, [CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> termAbstractionMap) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.allowNewVars = [CtVariableReadImpl]allowNewVars;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.definition = [CtVariableReadImpl]definition;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.krunOptions = [CtVariableReadImpl]krunOptions;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.globalContext = [CtVariableReadImpl]global;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.termAbstractionMap = [CtVariableReadImpl]termAbstractionMap;
        [CtAssignmentImpl][CtFieldWriteImpl]variables = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtAssignmentImpl][CtFieldWriteImpl]tokenEncoding = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();
        [CtAssignmentImpl][CtFieldWriteImpl]binders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Stack<>();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm translate([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.JavaSymbolicObject object) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.JavaSymbolicObject astNode = [CtInvocationImpl][CtVariableReadImpl]object.accept([CtThisAccessImpl]this);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]astNode instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm) (astNode));
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.SMTTranslationFailure([CtBinaryOperatorImpl][CtLiteralImpl]"Attempting to translate an unsupported term of type " + [CtInvocationImpl][CtVariableReadImpl]object.getClass());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.StringBuilder appendSortAndFunctionDeclarations([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> variables) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Sort> sorts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kframework.backend.java.kil.KLabelConstant> functions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KLabelConstant kLabel : [CtInvocationImpl][CtFieldReadImpl]definition.kLabels()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String smtlib = [CtInvocationImpl][CtVariableReadImpl]kLabel.getAttr([CtInvocationImpl][CtTypeAccessImpl]org.kframework.attributes.Att.SMTLIB());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean inSmtPrelude = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]kLabel.getAttr([CtInvocationImpl][CtTypeAccessImpl]org.kframework.attributes.Att.SMT_PRELUDE()) != [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]smtlib != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtVariableReadImpl]inSmtPrelude)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]org.kframework.backend.java.symbolic.KILtoSMTLib.SMTLIB_BUILTIN_FUNCTIONS.contains([CtVariableReadImpl]smtlib))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]smtlib.startsWith([CtLiteralImpl]"("))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]functions.add([CtVariableReadImpl]kLabel);
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kLabel.signatures().size() == [CtLiteralImpl]1;
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.SortSignature signature = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kLabel.signatures().iterator().next();
                [CtInvocationImpl][CtVariableReadImpl]sorts.add([CtInvocationImpl]renameSort([CtInvocationImpl][CtVariableReadImpl]signature.result()));
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]signature.parameters().stream().map([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::renameSort).forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]sorts::add);
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable variable : [CtVariableReadImpl]variables) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sorts.add([CtInvocationImpl]renameSort([CtInvocationImpl][CtVariableReadImpl]variable.sort()));
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Sort sort : [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.difference([CtVariableReadImpl]sorts, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.union([CtFieldReadImpl]org.kframework.backend.java.symbolic.KILtoSMTLib.SMTLIB_BUILTIN_SORTS, [CtInvocationImpl][CtFieldReadImpl]definition.smtPreludeSorts()))) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sort.equals([CtTypeAccessImpl]Sort.MAP) && [CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]krunOptions.experimental.smt.mapAsIntArray) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(define-sort Map () (Array Int Int))");
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(declare-sort ");
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtInvocationImpl]renameSort([CtVariableReadImpl]sort).name());
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")\n");
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KLabelConstant kLabel : [CtVariableReadImpl]functions) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(declare-fun ");
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtVariableReadImpl]kLabel.getAttr([CtInvocationImpl][CtTypeAccessImpl]org.kframework.attributes.Att.SMTLIB()));
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" (");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> childrenSorts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Sort sort : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kLabel.signatures().iterator().next().parameters()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]childrenSorts.add([CtInvocationImpl][CtInvocationImpl]renameSort([CtVariableReadImpl]sort).name());
            }
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Joiner.on([CtLiteralImpl]" ").appendTo([CtVariableReadImpl]sb, [CtVariableReadImpl]childrenSorts);
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]") ");
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtInvocationImpl]renameSort([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kLabel.signatures().iterator().next().result()).name());
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")\n");
        }
        [CtReturnImpl]return [CtVariableReadImpl]sb;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.CharSequence appendAxioms([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Rule rule : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]definition.functionRules().values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rule.att().contains([CtInvocationImpl][CtTypeAccessImpl]org.kframework.attributes.Att.SMT_LEMMA())) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib kil2SMT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.KILtoSMTLib([CtLiteralImpl]false, [CtFieldReadImpl]globalContext);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence leftExpression = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kil2SMT.translate([CtInvocationImpl][CtVariableReadImpl]rule.leftHandSide()).expression();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence rightExpression = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kil2SMT.translate([CtInvocationImpl][CtVariableReadImpl]rule.rightHandSide()).expression();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.CharSequence> requiresExpressions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Term term : [CtInvocationImpl][CtVariableReadImpl]rule.requires()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]requiresExpressions.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kil2SMT.translate([CtVariableReadImpl]term).expression());
                    }
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(assert ");
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kil2SMT.variables().isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(forall (");
                        [CtInvocationImpl][CtVariableReadImpl]kil2SMT.appendQuantifiedVariables([CtVariableReadImpl]sb, [CtInvocationImpl][CtVariableReadImpl]kil2SMT.variables());
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]") ");
                        [CtCommentImpl]// sb.append(") (! ");
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]requiresExpressions.isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(=> ");
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(and");
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence cs : [CtVariableReadImpl]requiresExpressions) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ");
                            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]cs);
                        }
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]") ");
                    }
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(= ");
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]leftExpression);
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ");
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]rightExpression);
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
                    [CtIfImpl][CtCommentImpl]// sb.append(" :pattern(");
                    [CtCommentImpl]// sb.append(leftExpression);
                    [CtCommentImpl]// sb.append(")");
                    if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]requiresExpressions.isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kil2SMT.variables().isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
                    }
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")\n");
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.UnsupportedOperationException e) [CtBlockImpl]{
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]sb;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.CharSequence appendConstantDeclarations([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> variables) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable variable : [CtVariableReadImpl]variables) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(declare-fun ");
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"|").append([CtInvocationImpl][CtVariableReadImpl]variable.longName()).append([CtLiteralImpl]"|");
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" () ");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sortName;
            [CtAssignmentImpl][CtVariableWriteImpl]sortName = [CtInvocationImpl]getSortName([CtVariableReadImpl]variable);
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]sortName);
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")\n");
        }
        [CtReturnImpl]return [CtVariableReadImpl]sb;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.CharSequence appendQuantifiedVariables([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> variables) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable variable : [CtVariableReadImpl]variables) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(");
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"|").append([CtInvocationImpl][CtVariableReadImpl]variable.longName()).append([CtLiteralImpl]"|");
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sortName;
            [CtAssignmentImpl][CtVariableWriteImpl]sortName = [CtInvocationImpl]getSortName([CtVariableReadImpl]variable);
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]sortName);
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
        }
        [CtReturnImpl]return [CtVariableReadImpl]sb;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getSortName([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable variable) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getParametricSortName([CtInvocationImpl][CtVariableReadImpl]variable.att(), [CtInvocationImpl][CtVariableReadImpl]variable.sort());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getParametricSortName([CtParameterImpl][CtTypeReferenceImpl]org.kframework.attributes.Att att, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Sort s) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl]renameSort([CtVariableReadImpl]s);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s == [CtFieldReadImpl]org.kframework.backend.java.kil.Sort.BIT_VECTOR) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"(_ BitVec " + [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.builtins.BitVector.getBitwidthOrDie([CtVariableReadImpl]att)) + [CtLiteralImpl]")";
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]s == [CtFieldReadImpl]org.kframework.backend.java.kil.Sort.FLOAT) && [CtUnaryOperatorImpl](![CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]krunOptions.experimental.smt.floatsAsPO)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.lang3.tuple.Pair<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Integer> pair = [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.builtins.FloatToken.getExponentAndSignificandOrDie([CtVariableReadImpl]att);
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"(_ FP " + [CtInvocationImpl][CtVariableReadImpl]pair.getLeft()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]pair.getRight()) + [CtLiteralImpl]")";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]s.name();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.kframework.backend.java.kil.Sort renameSort([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Sort sort) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]sort = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]definition.smtSortFlattening().getOrDefault([CtVariableReadImpl]sort, [CtVariableReadImpl]sort);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sort == [CtFieldReadImpl]org.kframework.backend.java.kil.Sort.LIST) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.kore.KORE.Sort([CtLiteralImpl]"IntSeq"));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sort == [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Sort.of([CtInvocationImpl][CtTypeAccessImpl]org.kframework.builtin.Sorts.Id())) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.kframework.backend.java.kil.Sort.INT;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]sort;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns an unmodifiable view of the set of variables occurring during the translation.
     */
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable> variables() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.union([CtFieldReadImpl]variables, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtInvocationImpl][CtFieldReadImpl]termAbstractionMap.values()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Translates the equalities of the given symbolic constraint into SMTLib format.
     * Ignores the substitution of the symbolic constraint.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.ConjunctiveFormula constraint) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constraint.disjunctions().isEmpty()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.kframework.utils.errorsystem.KEMException.criticalError([CtBinaryOperatorImpl][CtLiteralImpl]"disjunctions are not supported by SMT translation for:\n" + [CtInvocationImpl][CtVariableReadImpl]constraint.toStringMultiline());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.kframework.backend.java.symbolic.Equality> equalities = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.newLinkedHashSet([CtInvocationImpl][CtVariableReadImpl]constraint.equalities());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]allowNewVars) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constraint.substitution().entrySet().stream().map([CtLambdaImpl]([CtParameterImpl] entry) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.Equality([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtInvocationImpl][CtVariableReadImpl]constraint.globalContext())).forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]equalities::add);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]equalities.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.toString());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(and");
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isEmptyAdd = [CtLiteralImpl]true;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.symbolic.Equality equality : [CtVariableReadImpl]equalities) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence left = [CtInvocationImpl]translateTerm([CtInvocationImpl][CtVariableReadImpl]equality.leftHandSide());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence right = [CtInvocationImpl]translateTerm([CtInvocationImpl][CtVariableReadImpl]equality.rightHandSide());
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n\t(= ");
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]left);
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ");
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]right);
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
                [CtAssignmentImpl][CtVariableWriteImpl]isEmptyAdd = [CtLiteralImpl]false;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.UnsupportedOperationException e) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// TODO(AndreiS): fix this translation and the exceptions
                if ([CtFieldReadImpl]allowNewVars) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]/* it is sound to skip the equalities that cannot be translated */
                    [CtVariableReadImpl]e.printStackTrace();
                } else [CtBlockImpl]{
                    [CtThrowImpl]throw [CtVariableReadImpl]e;
                }
            }
        }
        [CtIfImpl]if ([CtVariableReadImpl]isEmptyAdd) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" true");
        }
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtVariableReadImpl]sb);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.CharSequence translateTerm([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Term term) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]translate([CtVariableReadImpl]term).expression();
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.kframework.backend.java.symbolic.SMTTranslationFailure | [CtTypeReferenceImpl]java.lang.UnsupportedOperationException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]abstractThroughAnonVariable([CtVariableReadImpl]term, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String abstractThroughAnonVariable([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Term term, [CtParameterImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable variable = [CtInvocationImpl][CtFieldReadImpl]termAbstractionMap.get([CtVariableReadImpl]term);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]variable == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl]allowNewVars) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]variable = [CtInvocationImpl][CtTypeAccessImpl]org.kframework.backend.java.kil.Variable.getAnonVariable([CtInvocationImpl][CtVariableReadImpl]term.sort());
                [CtInvocationImpl][CtFieldReadImpl]termAbstractionMap.put([CtVariableReadImpl]term, [CtVariableReadImpl]variable);
                [CtIfImpl]if ([CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]globalContext.javaExecutionOptions.debugZ3Queries) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]globalContext.log().format([CtLiteralImpl]"\t%s ::= %s\n", [CtInvocationImpl][CtVariableReadImpl]variable.longName(), [CtVariableReadImpl]term);
                }
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]e;
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]variable.longName();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean recordBinders([CtParameterImpl][CtTypeReferenceImpl]java.lang.String smtlib, [CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KList args) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// if `smtlib` is in the form of `(forall|exists ((#N <sort>)) <term>)`, put `args[N-1]` in `binders`.
        [CtTypeReferenceImpl]boolean hasBinder = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher matcher = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"^\\((forall|exists) \\(\\(#([0-9]+) [^)]+\\)\\)").matcher([CtVariableReadImpl]smtlib);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]matcher.find()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]hasBinder = [CtLiteralImpl]true;
            [CtInvocationImpl][CtFieldReadImpl]binders.push([CtInvocationImpl][CtVariableReadImpl]args.get([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]matcher.group([CtLiteralImpl]2)) - [CtLiteralImpl]1));
        }
        [CtReturnImpl]return [CtVariableReadImpl]hasBinder;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KItem kItem) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]kItem.kLabel() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KLabelConstant)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KLabelConstant kLabel = [CtInvocationImpl](([CtTypeReferenceImpl]org.kframework.backend.java.kil.KLabelConstant) ([CtVariableReadImpl]kItem.kLabel()));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]kItem.kList() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KList)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.KList kList = [CtInvocationImpl](([CtTypeReferenceImpl]org.kframework.backend.java.kil.KList) ([CtVariableReadImpl]kItem.kList()));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]kList.hasFrame()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String label = [CtInvocationImpl][CtVariableReadImpl]kLabel.smtlib();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kLabel.label().equals([CtLiteralImpl]"Map:lookup") && [CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]krunOptions.experimental.smt.mapAsIntArray) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"select";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kLabel.label().equals([CtLiteralImpl]"_[_<-_]") && [CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]krunOptions.experimental.smt.mapAsIntArray) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"store";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]label == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtInvocationImpl]abstractThroughAnonVariable([CtVariableReadImpl]kItem, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.SMTTranslationFailure([CtBinaryOperatorImpl][CtLiteralImpl]"missing SMTLib translation for " + [CtVariableReadImpl]kLabel)));
        }
        [CtIfImpl]if ([CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]krunOptions.experimental.smt.floatsAsPO) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]kLabel.label()) {
                [CtCaseImpl]case [CtLiteralImpl]"_<Float_" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"float_lt";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"_<=Float_" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"float_le";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"_>Float_" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"float_gt";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"_>=Float_" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"float_ge";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"maxFloat" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"float_max";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"minFloat" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"float_min";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"_==Float_" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"=";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"_=/=Float_" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"(not (= #1 #2))";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"isNaN" :
                    [CtAssignmentImpl][CtVariableWriteImpl]label = [CtLiteralImpl]"(= #1 float_nan)";
                    [CtBreakImpl]break;
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]label.startsWith([CtLiteralImpl]"(")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// smtlib expression instead of operator
            [CtTypeReferenceImpl]java.lang.String expression = [CtVariableReadImpl]label;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasBinder = [CtInvocationImpl]recordBinders([CtVariableReadImpl]label, [CtVariableReadImpl]kList);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kList.getContents().size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]expression = [CtInvocationImpl][CtVariableReadImpl]expression.replaceAll([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"#" + [CtBinaryOperatorImpl]([CtVariableReadImpl]i + [CtLiteralImpl]1)) + [CtLiteralImpl]"(?![0-9])", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]translate([CtInvocationImpl][CtVariableReadImpl]kList.get([CtVariableReadImpl]i)).expression().toString());
            }
            [CtIfImpl]if ([CtVariableReadImpl]hasBinder) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]binders.pop();
            }
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtVariableReadImpl]expression);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kframework.backend.java.kil.Term> arguments;
        [CtSwitchImpl]switch ([CtVariableReadImpl]label) {
            [CtCaseImpl]case [CtLiteralImpl]"exists" :
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable variable = [CtInvocationImpl](([CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable) ([CtVariableReadImpl]kList.get([CtLiteralImpl]0)));
                [CtAssignmentImpl][CtVariableWriteImpl]label = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"exists ((" + [CtInvocationImpl][CtVariableReadImpl]variable.longName()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]variable.sort()) + [CtLiteralImpl]")) ";
                [CtAssignmentImpl][CtVariableWriteImpl]arguments = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtInvocationImpl][CtVariableReadImpl]kList.get([CtLiteralImpl]1));
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtLiteralImpl]"extract" :
                [CtLocalVariableImpl][CtTypeReferenceImpl]int beginIndex = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.kframework.backend.java.builtins.IntToken) ([CtVariableReadImpl]kList.get([CtLiteralImpl]1))).intValue();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int endIndex = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.kframework.backend.java.builtins.IntToken) ([CtVariableReadImpl]kList.get([CtLiteralImpl]2))).intValue() - [CtLiteralImpl]1;
                [CtAssignmentImpl][CtVariableWriteImpl]label = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"(_ extract " + [CtVariableReadImpl]endIndex) + [CtLiteralImpl]" ") + [CtVariableReadImpl]beginIndex) + [CtLiteralImpl]")";
                [CtAssignmentImpl][CtVariableWriteImpl]arguments = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtInvocationImpl][CtVariableReadImpl]kList.get([CtLiteralImpl]0));
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtAssignmentImpl][CtVariableWriteImpl]arguments = [CtInvocationImpl][CtVariableReadImpl]kList.getContents();
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]arguments.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(");
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]label);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Term argument : [CtVariableReadImpl]arguments) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ");
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtInvocationImpl]translate([CtVariableReadImpl]argument).expression());
            }
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtVariableReadImpl]sb);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtVariableReadImpl]label);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.JavaSymbolicObject transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.BuiltinMap builtinMap) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtInvocationImpl]abstractThroughAnonVariable([CtVariableReadImpl]builtinMap, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.SMTTranslationFailure([CtLiteralImpl]"BuiltinMap can be translated to Z3 only through fresh var")));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.builtins.BoolToken boolToken) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.toString([CtInvocationImpl][CtVariableReadImpl]boolToken.booleanValue()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.builtins.IntToken intToken) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtInvocationImpl][CtVariableReadImpl]intToken.javaBackendValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.builtins.FloatToken floatToken) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]krunOptions.experimental.smt.floatsAsPO && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]floatToken.bigFloatValue().isPositiveZero() || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]floatToken.bigFloatValue().isNegativeZero())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtLiteralImpl]"float_zero");
        }
        [CtAssertImpl]assert [CtUnaryOperatorImpl]![CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]krunOptions.experimental.smt.floatsAsPO;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Formatter([CtVariableReadImpl]sb).format([CtLiteralImpl]"((_ asFloat %d %d) roundNearestTiesToEven %s 0)", [CtInvocationImpl][CtVariableReadImpl]floatToken.exponent(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]floatToken.bigFloatValue().precision(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]floatToken.bigFloatValue().toString([CtLiteralImpl]"%f"));
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtVariableReadImpl]sb);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.builtins.BitVector bitVector) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"#b");
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]bitVector.bitwidth() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl]--[CtVariableWriteImpl]i) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.math.BigInteger value = [CtInvocationImpl][CtVariableReadImpl]bitVector.unsignedValue();
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]value.testBit([CtVariableReadImpl]i) ? [CtLiteralImpl]"1" : [CtLiteralImpl]"0");
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtVariableReadImpl]sb);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.builtins.UninterpretedToken uninterpretedToken) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]uninterpretedToken.sort() == [CtFieldReadImpl]org.kframework.backend.java.kil.Sort.KVARIABLE) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]binders.search([CtVariableReadImpl]uninterpretedToken) != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtInvocationImpl][CtVariableReadImpl]uninterpretedToken.javaBackendValue());
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.symbolic.SMTTranslationFailure([CtBinaryOperatorImpl][CtLiteralImpl]"unbounded K variable: " + [CtVariableReadImpl]uninterpretedToken);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]tokenEncoding.get([CtVariableReadImpl]uninterpretedToken) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]tokenEncoding.put([CtVariableReadImpl]uninterpretedToken, [CtInvocationImpl][CtFieldReadImpl]tokenEncoding.size());
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtInvocationImpl][CtFieldReadImpl]tokenEncoding.get([CtVariableReadImpl]uninterpretedToken)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.JavaSymbolicObject transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.BuiltinList builtinList) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builtinList.toKore().accept([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm transform([CtParameterImpl][CtTypeReferenceImpl]org.kframework.backend.java.kil.Variable variable) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]variables.add([CtVariableReadImpl]variable);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kframework.backend.java.kil.SMTLibTerm([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"|" + [CtInvocationImpl][CtVariableReadImpl]variable.longName()) + [CtLiteralImpl]"|");
    }
}
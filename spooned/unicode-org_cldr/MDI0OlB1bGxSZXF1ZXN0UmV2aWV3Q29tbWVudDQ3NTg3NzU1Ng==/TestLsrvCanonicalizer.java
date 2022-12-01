[CompilationUnitImpl][CtCommentImpl]// if (!rule.regular) {
[CtCommentImpl]// System.out.println(
[CtCommentImpl]// rule.lstrType + "\t➕\t" + rule.typeParts + "\t⇒\t"+rule.replacementParts + "\t➕\t" + rule.reason + "\t" + diff(rule.typeParts, rule.replacementParts));
[CtCommentImpl]// }
[CtCommentImpl]// }
[CtCommentImpl]// }
[CtCommentImpl]// private static String diff(XLanguageTag tagParts, XLanguageTag replacementParts) {
[CtCommentImpl]// StringBuilder result = new StringBuilder();
[CtCommentImpl]// for (LstrType type : LocaleCanonicalizer.LSTR) {
[CtCommentImpl]// Collection<String> t = tagParts.get(type);
[CtCommentImpl]// Collection<String> r = replacementParts.get(type);
[CtCommentImpl]// if (t.isEmpty() && r.isEmpty()) {
[CtCommentImpl]// //
[CtCommentImpl]// } else {
[CtCommentImpl]// String first = t.isEmpty() ? "∅" : Joiner.on("|").join(t);
[CtCommentImpl]// String second = r.isEmpty() ? "∅" : Joiner.on("|").join(r);
[CtCommentImpl]// result.append(first).append(" → ").append(second);
[CtCommentImpl]// }
[CtCommentImpl]// result.append("\t");
[CtCommentImpl]// }
[CtCommentImpl]// return result.toString();
[CtCommentImpl]// }
[CtPackageDeclarationImpl]package org.unicode.cldr.unittest;
[CtUnresolvedImport]import org.unicode.cldr.util.StandardCodes.LstrType;
[CtUnresolvedImport]import org.unicode.cldr.util.LsrvCanonicalizer.TestDataTypes;
[CtImportImpl]import java.util.Map.Entry;
[CtUnresolvedImport]import org.unicode.cldr.util.LsrvCanonicalizer.XLanguageTag;
[CtUnresolvedImport]import org.unicode.cldr.util.LsrvCanonicalizer;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.ibm.icu.dev.test.TestFmwk;
[CtUnresolvedImport]import org.unicode.cldr.util.LsrvCanonicalizer.ReplacementRule;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.google.common.base.Objects;
[CtUnresolvedImport]import com.google.common.base.Joiner;
[CtClassImpl]public class TestLsrvCanonicalizer extends [CtTypeReferenceImpl]com.ibm.icu.dev.test.TestFmwk {
    [CtFieldImpl]static final [CtTypeReferenceImpl]org.unicode.cldr.util.LsrvCanonicalizer rrs = [CtInvocationImpl][CtTypeAccessImpl]org.unicode.cldr.util.LsrvCanonicalizer.getInstance();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]boolean DEBUG = [CtLiteralImpl]false;

    [CtMethodImpl]public static [CtTypeReferenceImpl]void main([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] args) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.unicode.cldr.unittest.TestLsrvCanonicalizer testLocaleCanonicalizer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.unicode.cldr.unittest.TestLsrvCanonicalizer();
        [CtInvocationImpl][CtVariableReadImpl]testLocaleCanonicalizer.run([CtVariableReadImpl]args);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void TestCases() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]org.unicode.cldr.unittest.TestLsrvCanonicalizer.DEBUG) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// System.out.println(Joiner.on('\n').join(rrs.filter(LstrType.variant, null)));
            [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Joiner.on([CtLiteralImpl]'\n').join([CtInvocationImpl][CtFieldReadImpl]org.unicode.cldr.unittest.TestLsrvCanonicalizer.rrs.filter([CtTypeAccessImpl]LstrType.language, [CtLiteralImpl]"no")));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.unicode.cldr.util.LsrvCanonicalizer.ReplacementRule> rules = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.unicode.cldr.util.LsrvCanonicalizer.TestDataTypes, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>> mainEntry : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.unicode.cldr.unittest.TestLsrvCanonicalizer.rrs.getTestData([CtLiteralImpl]null).entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.unicode.cldr.util.LsrvCanonicalizer.TestDataTypes type = [CtInvocationImpl][CtVariableReadImpl]mainEntry.getKey();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> entry : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mainEntry.getValue().entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String toTest = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String expected = [CtInvocationImpl][CtVariableReadImpl]entry.getValue();
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.unicode.cldr.util.LsrvCanonicalizer.XLanguageTag source2 = [CtInvocationImpl][CtTypeAccessImpl]org.unicode.cldr.util.LsrvCanonicalizer.XLanguageTag.fromTag([CtTypeAccessImpl]LstrType.language, [CtVariableReadImpl]toTest);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.unicode.cldr.util.LsrvCanonicalizer.XLanguageTag newTag = [CtInvocationImpl][CtFieldReadImpl]org.unicode.cldr.unittest.TestLsrvCanonicalizer.rrs.canonicalizeToX([CtVariableReadImpl]source2, [CtVariableReadImpl]rules);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String actual = [CtInvocationImpl][CtVariableReadImpl]newTag.toLocaleString();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.unicode.cldr.unittest.TestLsrvCanonicalizer.DEBUG && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]rules.size() > [CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"source: " + [CtVariableReadImpl]toTest) + [CtLiteralImpl]", expected: ") + [CtVariableReadImpl]expected) + [CtLiteralImpl]", actual: ") + [CtVariableReadImpl]actual) + [CtLiteralImpl]", rules: ") + [CtVariableReadImpl]rules);
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Objects.equal([CtVariableReadImpl]expected, [CtVariableReadImpl]actual)) [CtBlockImpl]{
                    [CtInvocationImpl]errln([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error: " + [CtLiteralImpl]"source: ") + [CtVariableReadImpl]toTest) + [CtLiteralImpl]", expected: ") + [CtVariableReadImpl]expected) + [CtLiteralImpl]", actual: ") + [CtVariableReadImpl]actual) + [CtLiteralImpl]", rules: ") + [CtVariableReadImpl]rules);
                }
            }
        }
    }
}
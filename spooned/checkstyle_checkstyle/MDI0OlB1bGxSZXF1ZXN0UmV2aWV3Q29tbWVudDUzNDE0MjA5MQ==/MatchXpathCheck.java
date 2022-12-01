[CompilationUnitImpl][CtPackageDeclarationImpl]package com.puppycrawl.tools.checkstyle.checks.coding;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
[CtUnresolvedImport]import com.puppycrawl.tools.checkstyle.StatelessCheck;
[CtUnresolvedImport]import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
[CtUnresolvedImport]import net.sf.saxon.Configuration;
[CtUnresolvedImport]import com.puppycrawl.tools.checkstyle.xpath.RootNode;
[CtUnresolvedImport]import net.sf.saxon.om.Item;
[CtUnresolvedImport]import net.sf.saxon.sxpath.XPathDynamicContext;
[CtUnresolvedImport]import com.puppycrawl.tools.checkstyle.xpath.AbstractNode;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.puppycrawl.tools.checkstyle.api.DetailAST;
[CtUnresolvedImport]import net.sf.saxon.trans.XPathException;
[CtUnresolvedImport]import net.sf.saxon.sxpath.XPathEvaluator;
[CtUnresolvedImport]import net.sf.saxon.sxpath.XPathExpression;
[CtClassImpl][CtJavaDocImpl]/**
 * <p>
 * Evaluates Xpath query and report violation on all matching AST nodes. Current check allows
 * user to implement custom checks using Xpath. If Xpath query is not specified explicitly,
 * then check does nothing.
 * </p>
 * <p>
 * Please read more about Xpath syntax at <a href="https://www.w3schools.com/xml/xpath_syntax.asp">
 * W3Schools Xpath Syntax</a>. Information regarding Xpath functions can be found at
 * <a href="https://developer.mozilla.org/en-US/docs/Web/XPath/Functions">XSLT/XPath Reference</a>.
 * Note, that <b>@text</b> attribute can used only with token types that are listed in
 * <a href="https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/utils/XpathUtil.java#L101">
 *     XpathUtil</a>.
 * </p>
 * <ul>
 * <li>
 * Property {@code query} - Xpath query.
 * Type is {@code java.lang.String}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * Checkstyle provides <a href="https://checkstyle.org/cmdline.html">command line tool</a>
 * and <a href="https://checkstyle.org/writingchecks.html#The_Checkstyle_SDK_Gui">GUI
 * application</a> with options to show AST and to ease usage of Xpath queries.
 * </p>
 * <p><b>-T</b> option prints AST tree of the checked file.</p>
 * <pre>
 * $ java -jar checkstyle-X.XX-all.jar -T Main.java
 * CLASS_DEF -&gt; CLASS_DEF [1:0]
 * |--MODIFIERS -&gt; MODIFIERS [1:0]
 * |   `--LITERAL_PUBLIC -&gt; public [1:0]
 * |--LITERAL_CLASS -&gt; class [1:7]
 * |--IDENT -&gt; Main [1:13]
 * `--OBJBLOCK -&gt; OBJBLOCK [1:18]
 * |--LCURLY -&gt; { [1:18]
 * |--METHOD_DEF -&gt; METHOD_DEF [2:4]
 * |   |--MODIFIERS -&gt; MODIFIERS [2:4]
 * |   |   `--LITERAL_PUBLIC -&gt; public [2:4]
 * |   |--TYPE -&gt; TYPE [2:11]
 * |   |   `--IDENT -&gt; String [2:11]
 * |   |--IDENT -&gt; sayHello [2:18]
 * |   |--LPAREN -&gt; ( [2:26]
 * |   |--PARAMETERS -&gt; PARAMETERS [2:27]
 * |   |   `--PARAMETER_DEF -&gt; PARAMETER_DEF [2:27]
 * |   |       |--MODIFIERS -&gt; MODIFIERS [2:27]
 * |   |       |--TYPE -&gt; TYPE [2:27]
 * |   |       |   `--IDENT -&gt; String [2:27]
 * |   |       `--IDENT -&gt; name [2:34]
 * |   |--RPAREN -&gt; ) [2:38]
 * |   `--SLIST -&gt; { [2:40]
 * |       |--LITERAL_RETURN -&gt; return [3:8]
 * |       |   |--EXPR -&gt; EXPR [3:25]
 * |       |   |   `--PLUS -&gt; + [3:25]
 * |       |   |       |--STRING_LITERAL -&gt; "Hello, " [3:15]
 * |       |   |       `--IDENT -&gt; name [3:27]
 * |       |   `--SEMI -&gt; ; [3:31]
 * |       `--RCURLY -&gt; } [4:4]
 * `--RCURLY -&gt; } [5:0]
 * </pre>
 * <p><b>-b</b> option shows AST nodes that match given Xpath query. This command can be used to
 * validate accuracy of Xpath query against given file.</p>
 * <pre>
 * $ java -jar checkstyle-X.XX-all.jar Main.java -b "//METHOD_DEF[./IDENT[@text='sayHello']]"
 * CLASS_DEF -&gt; CLASS_DEF [1:0]
 * `--OBJBLOCK -&gt; OBJBLOCK [1:18]
 * |--METHOD_DEF -&gt; METHOD_DEF [2:4]
 * </pre>
 * <p>
 * The following example demonstrates validation of methods order, so that public methods should
 * come before the private ones:
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt;
 *  &lt;property name="query" value="//METHOD_DEF[.//LITERAL_PRIVATE and
 *  following-sibling::METHOD_DEF[.//LITERAL_PUBLIC]]"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *  public void method1() { }
 *  private void method2() { } // violation
 *  public void method3() { }
 *  private void method4() { } // violation
 *  public void method5() { }
 *  private void method6() { } // ok
 * }
 * </pre>
 * <p>
 * To violate if there are any parametrized constructors
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt;
 *  &lt;property name="query" value="//CTOR_DEF[count(./PARAMETERS/*) &gt; 0]"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *  public Test(Object c) { } // violation
 *  public Test(int a, HashMap&lt;String, Integer&gt; b) { } // violation
 *  public Test() { } // ok
 * }
 * </pre>
 * <p>
 * To violate if method name is 'test' or 'foo'
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt;
 *  &lt;property name="query" value="//METHOD_DEF[./IDENT[@text='test' or @text='foo']]"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *  public void test() {} // violation
 *  public void getName() {} // ok
 *  public void foo() {} // violation
 *  public void sayHello() {} // ok
 * }
 * </pre>
 * <p>
 * To violate if new instance creation was done without <b>var</b> type
 * </p>
 * <pre>
 * &lt;module name=&quot;MatchXpath&quot;&gt;
 *  &lt;property name=&quot;query&quot; value=&quot;//VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW
 *  and not(./TYPE/IDENT[@text='var'])]&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *   public void foo() {
 *     SomeObject a = new SomeObject(); // violation
 *     var b = new SomeObject(); // OK
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code matchxpath.match}
 * </li>
 * </ul>
 *
 * @since 8.39
 */
[CtAnnotationImpl]@com.puppycrawl.tools.checkstyle.StatelessCheck
public class MatchXpathCheck extends [CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.api.AbstractCheck {
    [CtFieldImpl][CtJavaDocImpl]/**
     * A key is pointing to the warning message text provided by user.
     */
    public static final [CtTypeReferenceImpl]java.lang.String MSG_KEY = [CtLiteralImpl]"matchxpath.match";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Xpath query.
     */
    private [CtTypeReferenceImpl]java.lang.String query = [CtLiteralImpl]"";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Xpath expression.
     */
    private [CtTypeReferenceImpl]net.sf.saxon.sxpath.XPathExpression xpathExpression;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Setter to xpath query.
     *
     * @param query
     * 		xpath query.
     */
    public [CtTypeReferenceImpl]void setQuery([CtParameterImpl][CtTypeReferenceImpl]java.lang.String query) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.query = [CtVariableReadImpl]query;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]query.isEmpty()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]net.sf.saxon.sxpath.XPathEvaluator xpathEvaluator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.sf.saxon.sxpath.XPathEvaluator([CtInvocationImpl][CtTypeAccessImpl]net.sf.saxon.Configuration.newConfiguration());
                [CtAssignmentImpl][CtFieldWriteImpl]xpathExpression = [CtInvocationImpl][CtVariableReadImpl]xpathEvaluator.createExpression([CtVariableReadImpl]query);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]net.sf.saxon.trans.XPathException ex) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Creating Xpath expression failed: " + [CtVariableReadImpl]query, [CtVariableReadImpl]ex);
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]int[] getDefaultTokens() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getRequiredTokens();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]int[] getAcceptableTokens() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getRequiredTokens();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]int[] getRequiredTokens() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_INT_ARRAY;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void beginTree([CtParameterImpl][CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.api.DetailAST rootAST) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]xpathExpression != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.api.DetailAST> matchingNodes = [CtInvocationImpl]findMatchingNodesByXpathQuery([CtVariableReadImpl]rootAST);
            [CtInvocationImpl][CtVariableReadImpl]matchingNodes.forEach([CtLambdaImpl]([CtParameterImpl] node) -> [CtInvocationImpl]log([CtVariableReadImpl]node, [CtFieldReadImpl][CtFieldReferenceImpl]MSG_KEY));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.api.DetailAST> findMatchingNodesByXpathQuery([CtParameterImpl][CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.api.DetailAST rootAST) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.xpath.RootNode rootNode = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.xpath.RootNode([CtVariableReadImpl]rootAST);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]net.sf.saxon.sxpath.XPathDynamicContext xpathDynamicContext = [CtInvocationImpl][CtFieldReadImpl]xpathExpression.createDynamicContext([CtVariableReadImpl]rootNode);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.sf.saxon.om.Item> matchingItems = [CtInvocationImpl][CtFieldReadImpl]xpathExpression.evaluate([CtVariableReadImpl]xpathDynamicContext);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]matchingItems.stream().map([CtLambdaImpl]([CtParameterImpl] item) -> [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.puppycrawl.tools.checkstyle.xpath.AbstractNode) (item)).getUnderlyingNode()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]net.sf.saxon.trans.XPathException ex) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Evaluation of Xpath query failed: " + [CtFieldReadImpl]query, [CtVariableReadImpl]ex);
        }
    }
}
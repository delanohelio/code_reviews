[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.netbeans.modules.xsl.grammar;
[CtUnresolvedImport]import org.openide.util.Lookup;
[CtImportImpl]import org.xml.sax.InputSource;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import org.w3c.dom.*;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import org.xml.sax.EntityResolver;
[CtUnresolvedImport]import org.netbeans.modules.xml.api.model.*;
[CtImportImpl]import org.xml.sax.SAXException;
[CtUnresolvedImport]import org.openide.loaders.DataObject;
[CtUnresolvedImport]import org.netbeans.api.xml.services.UserCatalog;
[CtImportImpl]import javax.swing.Icon;
[CtUnresolvedImport]import org.openide.nodes.PropertySupport;
[CtUnresolvedImport]import org.openide.util.lookup.Lookups;
[CtUnresolvedImport]import org.netbeans.modules.xml.spi.dom.*;
[CtUnresolvedImport]import org.netbeans.modules.xsl.api.XSLCustomizer;
[CtUnresolvedImport]import org.openide.util.NbBundle;
[CtClassImpl][CtJavaDocImpl]/**
 * This class implements code completion for XSL transformation files.
 * XSL elements in the completion are hardcoded from the XSLT spec, but the
 * result elements are gathered from the "doctype-public" and "doctype-system"
 * attributes of the xsl:output element.
 *
 * @author asgeir@dimonsoftware.com
 */
public final class XSLGrammarQuery implements [CtTypeReferenceImpl]GrammarQuery {
    [CtFieldImpl]private [CtTypeReferenceImpl]org.openide.loaders.DataObject dataObject;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Contains a mapping from XSL namespace element names to set of names of
     * allowed XSL children. Neither the element name keys nor the names in the
     * value set should contain the namespace prefix.
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String>> elementDecls;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Contains a mapping from XSL namespace element names to set of names of
     * allowed XSL attributes for that element.  The element name keys should
     * not contain the namespace prefix.
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String>> attrDecls;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A Set of XSL attributes which should be allowd for result elements
     */
    private static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> resultElementAttr;

    [CtFieldImpl][CtJavaDocImpl]/**
     * An object which indicates that result element should be allowed in a element Set
     */
    private static [CtTypeReferenceImpl]java.lang.String resultElements = [CtLiteralImpl]"RESULT_ELEMENTS_DUMMY_STRING";[CtCommentImpl]// NOI18N


    [CtFieldImpl][CtJavaDocImpl]/**
     * A Set of elements which should be allowed at template level in XSL stylesheet
     */
    private static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> template;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Contains a mapping from XSL namespace element names to an attribute name which
     * should contain XPath expression.  The element name keys should
     * not contain the namespace prefix.
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> exprAttributes;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A set containing all functions allowed in XSLT
     */
    private static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> xslFunctions;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A set containing XPath axes
     */
    private static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> xpathAxes;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A list of prefixes using the "http://www.w3.org/1999/XSL/Transform" namespace
     * defined in the context XSL document.  The first prefix in the list is the actual XSL
     * transformation prefix, which is normally defined on the xsl:stylesheet element.
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> prefixList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * A GrammarQuery for the result elements created for the doctype-public" and
     * "doctype-system" attributes of the xsl:output element.
     */
    private [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.GrammarQuery resultGrammarQuery;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The value of the system identifier of the DTD which was used when
     * resultGrammarQuery was previously created
     */
    private [CtTypeReferenceImpl]java.lang.String lastDoctypeSystem;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The value of the public identifier of the DTD which was used when
     * resultGrammarQuery was previously created
     */
    private [CtTypeReferenceImpl]java.lang.String lastDoctypePublic;

    [CtFieldImpl][CtCommentImpl]// we cannot parse SGML DTD for HTML, let emulate it by XHTML DTD
    private static final [CtTypeReferenceImpl]java.lang.String XHTML_PUBLIC_ID = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"netbeans.xsl.html.public", [CtLiteralImpl]"-//W3C//DTD XHTML 1.0 Transitional//EN");[CtCommentImpl]// NOI18N


    [CtFieldImpl][CtCommentImpl]// we cannot parse SGML DTD for HTML, let emulate it by XHTML DTD
    private static final [CtTypeReferenceImpl]java.lang.String XHTML_SYSTEM_ID = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"netbeans.xsl.html.system", [CtLiteralImpl]"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");[CtCommentImpl]// NOI18N


    [CtFieldImpl][CtCommentImpl]// namespace that this grammar supports
    public static final [CtTypeReferenceImpl]java.lang.String XSLT_NAMESPACE_URI = [CtLiteralImpl]"http://www.w3.org/1999/XSL/Transform";[CtCommentImpl]// NOI18N


    [CtFieldImpl][CtJavaDocImpl]/**
     * Folder which stores instances of custom external XSL customizers
     */
    private static final [CtTypeReferenceImpl]java.lang.String CUSTOMIZER_FOLDER = [CtLiteralImpl]"Plugins/XML/XSLCustomizer";[CtCommentImpl]// NOI18N


    [CtFieldImpl]private [CtTypeReferenceImpl]org.netbeans.modules.xsl.api.XSLCustomizer customizer = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.ResourceBundle bundle = [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.NbBundle.getBundle([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.class);

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Creates a new instance of XSLGrammarQuery
     */
    public XSLGrammarQuery([CtParameterImpl][CtTypeReferenceImpl]org.openide.loaders.DataObject dataObject) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dataObject = [CtVariableReadImpl]dataObject;
    }

    [CtMethodImpl][CtCommentImpl]// ////////////////////////////////////////7
    [CtCommentImpl]// Getters for the static members
    private static [CtTypeReferenceImpl]java.util.Map getElementDecls() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtAssignmentImpl][CtFieldWriteImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtLocalVariableImpl][CtCommentImpl]// Commonly used variables
            [CtTypeReferenceImpl]java.util.Set emptySet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String spaceAtt = [CtLiteralImpl]"xml:space";[CtCommentImpl]// NOI18N

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> tmpSet;
            [CtLocalVariableImpl][CtCommentImpl]// //////////////////////////////////////////////
            [CtCommentImpl]// Initialize common sets
            [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> charInstructions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.<[CtTypeReferenceImpl]java.lang.String>asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"apply-templates"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"call-template", [CtLiteralImpl]"apply-imports", [CtLiteralImpl]"for-each", [CtLiteralImpl]"value-of"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"copy-of", [CtLiteralImpl]"number", [CtLiteralImpl]"choose", [CtLiteralImpl]"if", [CtLiteralImpl]"text", [CtLiteralImpl]"copy"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"variable", [CtLiteralImpl]"message", [CtLiteralImpl]"fallback" }));[CtCommentImpl]// NOI18N

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> instructions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtVariableReadImpl]charInstructions);
            [CtInvocationImpl][CtVariableReadImpl]instructions.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.<[CtTypeReferenceImpl]java.lang.String>asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"processing-instruction"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"comment", [CtLiteralImpl]"element", [CtLiteralImpl]"attribute" }));[CtCommentImpl]// NOI18N

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> charTemplate = [CtVariableReadImpl]charInstructions;[CtCommentImpl]// We don't care about PCDATA

            [CtAssignmentImpl][CtFieldWriteImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtVariableReadImpl]instructions);
            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template.add([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElements);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> topLevel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.<[CtTypeReferenceImpl]java.lang.String>asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"import", [CtLiteralImpl]"include", [CtLiteralImpl]"strip-space"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"preserve-space", [CtLiteralImpl]"output", [CtLiteralImpl]"key", [CtLiteralImpl]"decimal-format", [CtLiteralImpl]"attribute-set"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"variable", [CtLiteralImpl]"param", [CtLiteralImpl]"template", [CtLiteralImpl]"namespace-alias" }));[CtCommentImpl]// NOI18N

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> topLevelAttr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.<[CtTypeReferenceImpl]java.lang.String>asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"extension-element-prefixes"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"exclude-result-prefixes", [CtLiteralImpl]"id", [CtLiteralImpl]"version", [CtVariableReadImpl]spaceAtt }));[CtCommentImpl]// NOI18N

            [CtAssignmentImpl][CtFieldWriteImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElementAttr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.<[CtTypeReferenceImpl]java.lang.String>asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"extension-element-prefixes"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"exclude-result-prefixes", [CtLiteralImpl]"use-attribute-sets", [CtLiteralImpl]"version" }));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// //////////////////////////////////////////////
            [CtCommentImpl]// Add items to elementDecls and attrDecls maps
            [CtCommentImpl]// xsl:stylesheet
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"stylesheet", [CtVariableReadImpl]topLevel);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"stylesheet", [CtVariableReadImpl]topLevelAttr);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:transform
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"transform", [CtVariableReadImpl]topLevel);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"transform", [CtVariableReadImpl]topLevelAttr);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:import
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"import", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"import", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"href" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xxsl:include
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"include", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"include", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"href" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:strip-space
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"strip-space", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"strip-space", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"elements" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:preserve-space
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"preserve-space", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"preserve-space", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"elements" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:output
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"output", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"output", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"method"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"version", [CtLiteralImpl]"encoding", [CtLiteralImpl]"omit-xml-declaration", [CtLiteralImpl]"standalone", [CtLiteralImpl]"doctype-public"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"doctype-system", [CtLiteralImpl]"cdata-section-elements", [CtLiteralImpl]"indent", [CtLiteralImpl]"media-type" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:key
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"key", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"key", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"name", [CtLiteralImpl]"match", [CtLiteralImpl]"use" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:decimal-format
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"decimal-format", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"decimal-format", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"name"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"decimal-separator", [CtLiteralImpl]"grouping-separator", [CtLiteralImpl]"infinity", [CtLiteralImpl]"minus-sign", [CtLiteralImpl]"NaN"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"percent", [CtLiteralImpl]"per-mille", [CtLiteralImpl]"zero-digit", [CtLiteralImpl]"digit", [CtLiteralImpl]"pattern-separator" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:namespace-alias
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"namespace-alias", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"namespace-alias", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "stylesheet-prefix", [CtLiteralImpl]"result-prefix" })));[CtCommentImpl]// NOI18N

            [CtAssignmentImpl][CtCommentImpl]// xsl:template
            [CtVariableWriteImpl]tmpSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtVariableReadImpl]instructions);
            [CtInvocationImpl][CtVariableReadImpl]tmpSet.add([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElements);
            [CtInvocationImpl][CtVariableReadImpl]tmpSet.add([CtLiteralImpl]"param");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"template", [CtVariableReadImpl]tmpSet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"template", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "match", [CtLiteralImpl]"name", [CtLiteralImpl]"priority", [CtLiteralImpl]"mode", [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:value-of
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"value-of", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"value-of", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "select", [CtLiteralImpl]"disable-output-escaping" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:copy-of
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"copy-of", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"copy-of", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"select" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:number
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"number", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"number", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "level", [CtLiteralImpl]"count", [CtLiteralImpl]"from", [CtLiteralImpl]"value", [CtLiteralImpl]"format", [CtLiteralImpl]"lang", [CtLiteralImpl]"letter-value"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"grouping-separator", [CtLiteralImpl]"grouping-size" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:apply-templates
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"apply-templates", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "sort", [CtLiteralImpl]"with-param" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"apply-templates", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "select", [CtLiteralImpl]"mode" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:apply-imports
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"apply-imports", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"apply-imports", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtAssignmentImpl][CtCommentImpl]// xsl:for-each
            [CtVariableWriteImpl]tmpSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtVariableReadImpl]instructions);
            [CtInvocationImpl][CtVariableReadImpl]tmpSet.add([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElements);
            [CtInvocationImpl][CtVariableReadImpl]tmpSet.add([CtLiteralImpl]"sort");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"for-each", [CtVariableReadImpl]tmpSet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"for-each", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "select", [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:sort
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"sort", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"sort", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "select", [CtLiteralImpl]"lang", [CtLiteralImpl]"data-type", [CtLiteralImpl]"order", [CtLiteralImpl]"case-order" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:if
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"if", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"if", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"test", [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:choose
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"choose", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "when", [CtLiteralImpl]"otherwise" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"choose", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:when
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"when", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"when", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "test", [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:otherwise
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"otherwise", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"otherwise", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:attribute-set
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"sort", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"attribute" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"attribute-set", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "name", [CtLiteralImpl]"use-attribute-sets" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:call-template
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"call-template", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"with-param" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"call-template", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"name" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:with-param
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"with-param", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"with-param", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "name", [CtLiteralImpl]"select" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:variable
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"variable", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"variable", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "name", [CtLiteralImpl]"select" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:param
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"param", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"param", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "name", [CtLiteralImpl]"select" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:text
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"text", [CtVariableReadImpl]emptySet);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"text", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "disable-output-escaping" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:processing-instruction
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"processing-instruction", [CtVariableReadImpl]charTemplate);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"processing-instruction", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "name", [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:element
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"element", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"element", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "name", [CtLiteralImpl]"namespace", [CtLiteralImpl]"use-attribute-sets", [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:attribute
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"attribute", [CtVariableReadImpl]charTemplate);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"attribute", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// NOI18N
            "name", [CtLiteralImpl]"namespace", [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:comment
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"comment", [CtVariableReadImpl]charTemplate);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"comment", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:copy
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"copy", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"copy", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl][CtCommentImpl]// NOI18N
            spaceAtt, [CtLiteralImpl]"use-attribute-sets" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:message
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"message", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"message", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl][CtCommentImpl]// NOI18N
            spaceAtt, [CtLiteralImpl]"terminate" })));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// xsl:fallback
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls.put([CtLiteralImpl]"fallback", [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template);[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls.put([CtLiteralImpl]"fallback", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]spaceAtt })));[CtCommentImpl]// NOI18N

        }
        [CtReturnImpl]return [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.elementDecls;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Map getAttrDecls() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getElementDecls();
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.attrDecls;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Set getResultElementAttr() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElementAttr == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getElementDecls();
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElementAttr;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Set getTemplate() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getElementDecls();
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.template;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Set getXslFunctions() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.xslFunctions == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.xslFunctions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.<[CtTypeReferenceImpl]java.lang.String>asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"boolean(", [CtLiteralImpl]"ceiling(", [CtLiteralImpl]"concat(", [CtLiteralImpl]"contains(", [CtLiteralImpl]"count(", [CtLiteralImpl]"current()", [CtLiteralImpl]"document("[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"false()", [CtLiteralImpl]"floor(", [CtLiteralImpl]"format-number(", [CtLiteralImpl]"generate-id("[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"id(", [CtLiteralImpl]"local-name(", [CtLiteralImpl]"key(", [CtLiteralImpl]"lang(", [CtLiteralImpl]"last()", [CtLiteralImpl]"name(", [CtLiteralImpl]"namespace-uri(", [CtLiteralImpl]"normalize-space("[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"not(", [CtLiteralImpl]"number(", [CtLiteralImpl]"position()", [CtLiteralImpl]"round(", [CtLiteralImpl]"starts-with(", [CtLiteralImpl]"string("[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"string-length(", [CtLiteralImpl]"substring(", [CtLiteralImpl]"substring-after(", [CtLiteralImpl]"substring-before(", [CtLiteralImpl]"sum("[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"system-property(", [CtLiteralImpl]"translate(", [CtLiteralImpl]"true()", [CtLiteralImpl]"unparsed-entity-uri(" }));[CtCommentImpl]// NOI18N

        }
        [CtReturnImpl]return [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.xslFunctions;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Set getXPathAxes() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.xpathAxes == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.xpathAxes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.<[CtTypeReferenceImpl]java.lang.String>asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"ancestor::", [CtLiteralImpl]"ancestor-or-self::"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"attribute::", [CtLiteralImpl]"child::", [CtLiteralImpl]"descendant::", [CtLiteralImpl]"descendant-or-self::", [CtLiteralImpl]"following::"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"following-sibling::", [CtLiteralImpl]"namespace::", [CtLiteralImpl]"parent::", [CtLiteralImpl]"preceding::"[CtCommentImpl]// NOI18N
            , [CtLiteralImpl]"preceding-sibling::", [CtLiteralImpl]"self::" }));[CtCommentImpl]// NOI18N

        }
        [CtReturnImpl]return [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.xpathAxes;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Map getExprAttributes() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"key", [CtLiteralImpl]"use");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"value-of", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"copy-of", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"number", [CtLiteralImpl]"value");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtCommentImpl]// ??? what about match one
            [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"apply-templates", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"for-each", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"sort", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"if", [CtLiteralImpl]"test");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"when", [CtLiteralImpl]"test");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"with-param", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"variable", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes.put([CtLiteralImpl]"param", [CtLiteralImpl]"select");[CtCommentImpl]// NOI18N

        }
        [CtReturnImpl]return [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.exprAttributes;
    }

    [CtMethodImpl][CtCommentImpl]// //////////////////////////////////////////////////////////////////////////////
    [CtCommentImpl]// GrammarQuery interface fulfillment
    [CtJavaDocImpl]/**
     * Support completions of elements defined by XSLT spec and by the <output>
     * doctype attribute (in result space).
     */
    public [CtTypeReferenceImpl]java.util.Enumeration queryElements([CtParameterImpl][CtTypeReferenceImpl]HintContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Node node = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.w3c.dom.Node) (ctx)).getParentNode();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix = [CtInvocationImpl][CtVariableReadImpl]ctx.getCurrentPrefix();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.w3c.dom.Element) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Element el = [CtVariableReadImpl](([CtTypeReferenceImpl]org.w3c.dom.Element) (node));
            [CtInvocationImpl]updateProperties([CtVariableReadImpl]el);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.size() == [CtLiteralImpl]0)[CtBlockImpl]
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String firstXslPrefixWithColon = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.get([CtLiteralImpl]0) + [CtLiteralImpl]":";[CtCommentImpl]// NOI18N

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set elements;
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]el.getTagName().startsWith([CtVariableReadImpl]firstXslPrefixWithColon)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String parentNCName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]el.getTagName().substring([CtInvocationImpl][CtVariableReadImpl]firstXslPrefixWithColon.length());
                [CtAssignmentImpl][CtVariableWriteImpl]elements = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.Set) ([CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getElementDecls().get([CtVariableReadImpl]parentNCName)));
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Children of result elements should always be the template set
                [CtVariableWriteImpl]elements = [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getTemplate();
            }
            [CtIfImpl][CtCommentImpl]// First we add the Result elements
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]elements != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]resultGrammarQuery != [CtLiteralImpl]null)) && [CtInvocationImpl][CtVariableReadImpl]elements.contains([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElements)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.ResultHintContext resultHintContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.ResultHintContext([CtVariableReadImpl]ctx, [CtVariableReadImpl]firstXslPrefixWithColon, [CtLiteralImpl]null);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration resultEnum = [CtInvocationImpl][CtFieldReadImpl]resultGrammarQuery.queryElements([CtVariableReadImpl]resultHintContext);
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]resultEnum.hasMoreElements()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]list.put([CtInvocationImpl][CtVariableReadImpl]resultEnum.nextElement());
                } 
            }
            [CtInvocationImpl][CtCommentImpl]// Then we add the XSLT elements of the first prefix (normally of the stylesheet node).
            org.netbeans.modules.xsl.grammar.XSLGrammarQuery.addXslElementsToEnum([CtVariableReadImpl]list, [CtVariableReadImpl]elements, [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.get([CtLiteralImpl]0) + [CtLiteralImpl]":", [CtVariableReadImpl]prefix);[CtCommentImpl]// NOI18N

            [CtForImpl][CtCommentImpl]// Finally we add xsl namespace elements with other prefixes than the first one
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]int prefixInd = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]prefixInd < [CtInvocationImpl][CtFieldReadImpl]prefixList.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]prefixInd++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String curPrefix = [CtBinaryOperatorImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]prefixList.get([CtVariableReadImpl]prefixInd))) + [CtLiteralImpl]":";[CtCommentImpl]// NOI18N

                [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Node curNode = [CtVariableReadImpl]el;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String curName = [CtLiteralImpl]null;
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curNode != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtAssignmentImpl]([CtVariableWriteImpl]curName = [CtInvocationImpl][CtVariableReadImpl]curNode.getNodeName()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]curName.startsWith([CtVariableReadImpl]curPrefix))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]curNode = [CtInvocationImpl][CtVariableReadImpl]curNode.getParentNode();
                } 
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]curName == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// This must be the document node
                    org.netbeans.modules.xsl.grammar.XSLGrammarQuery.addXslElementsToEnum([CtVariableReadImpl]list, [CtInvocationImpl][CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getElementDecls().keySet(), [CtVariableReadImpl]curPrefix, [CtVariableReadImpl]prefix);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String parentName = [CtInvocationImpl][CtVariableReadImpl]curName.substring([CtInvocationImpl][CtVariableReadImpl]curPrefix.length());
                    [CtAssignmentImpl][CtVariableWriteImpl]elements = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.Set) ([CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getElementDecls().get([CtVariableReadImpl]parentName)));
                    [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.addXslElementsToEnum([CtVariableReadImpl]list, [CtVariableReadImpl]elements, [CtVariableReadImpl]curPrefix, [CtVariableReadImpl]prefix);
                }
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.w3c.dom.Document) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// ??? it should be probably only root element name
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.size() == [CtLiteralImpl]0)[CtBlockImpl]
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();

            [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.addXslElementsToEnum([CtVariableReadImpl]list, [CtInvocationImpl][CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getElementDecls().keySet(), [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.get([CtLiteralImpl]0) + [CtLiteralImpl]":", [CtVariableReadImpl]prefix);[CtCommentImpl]// NOI18N

        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();
        }
        [CtReturnImpl]return [CtVariableReadImpl]list;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Enumeration queryAttributes([CtParameterImpl][CtTypeReferenceImpl]HintContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Element el = [CtLiteralImpl]null;
        [CtIfImpl][CtCommentImpl]// Support two versions of GrammarQuery contract
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getNodeType() == [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]ATTRIBUTE_NODE) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]el = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.w3c.dom.Attr) (ctx)).getOwnerElement();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getNodeType() == [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]ELEMENT_NODE) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]el = [CtVariableReadImpl](([CtTypeReferenceImpl]org.w3c.dom.Element) (ctx));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]el == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String elTagName = [CtInvocationImpl][CtVariableReadImpl]el.getTagName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.NamedNodeMap existingAttributes = [CtInvocationImpl][CtVariableReadImpl]el.getAttributes();
        [CtInvocationImpl]updateProperties([CtVariableReadImpl]el);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String curXslPrefix = [CtLiteralImpl]null;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int ind = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]ind < [CtInvocationImpl][CtFieldReadImpl]prefixList.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]ind++) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]elTagName.startsWith([CtBinaryOperatorImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]prefixList.get([CtVariableReadImpl]ind))) + [CtLiteralImpl]":")) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// NOI18N
                [CtVariableWriteImpl]curXslPrefix = [CtBinaryOperatorImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]prefixList.get([CtVariableReadImpl]ind))) + [CtLiteralImpl]":";[CtCommentImpl]// NOI18N

                [CtBreakImpl]break;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set possibleAttributes;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]curXslPrefix != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// Attributes of XSL element
            [CtVariableWriteImpl]possibleAttributes = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.Set) ([CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getAttrDecls().get([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]el.getTagName().substring([CtInvocationImpl][CtVariableReadImpl]curXslPrefix.length()))));
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// XSL Attributes of Result element
            [CtVariableWriteImpl]possibleAttributes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator it = [CtInvocationImpl][CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getResultElementAttr().iterator();
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]possibleAttributes.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]prefixList.get([CtLiteralImpl]0))) + [CtLiteralImpl]":") + [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]it.next())));[CtCommentImpl]// NOI18N

                } 
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]possibleAttributes == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix = [CtInvocationImpl][CtVariableReadImpl]ctx.getCurrentPrefix();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]resultGrammarQuery != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration enum2 = [CtInvocationImpl][CtFieldReadImpl]resultGrammarQuery.queryAttributes([CtVariableReadImpl]ctx);
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]enum2.hasMoreElements()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]GrammarResult resNode = [CtInvocationImpl](([CtTypeReferenceImpl]GrammarResult) ([CtVariableReadImpl]enum2.nextElement()));
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]possibleAttributes.contains([CtInvocationImpl][CtVariableReadImpl]resNode.getNodeName())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]list.put([CtVariableReadImpl]resNode);
                }
            } 
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator it = [CtInvocationImpl][CtVariableReadImpl]possibleAttributes.iterator();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String next = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]it.next()));
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]next.startsWith([CtVariableReadImpl]prefix)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]existingAttributes.getNamedItem([CtVariableReadImpl]next) == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]list.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyAttr([CtVariableReadImpl]next));
                }
            }
        } 
        [CtReturnImpl]return [CtVariableReadImpl]list;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Enumeration queryValues([CtParameterImpl][CtTypeReferenceImpl]HintContext ctx) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getNodeType() == [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]ATTRIBUTE_NODE) [CtBlockImpl]{
            [CtInvocationImpl]updateProperties([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.w3c.dom.Attr) (ctx)).getOwnerElement());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.size() == [CtLiteralImpl]0)[CtBlockImpl]
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String xslNamespacePrefix = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.get([CtLiteralImpl]0) + [CtLiteralImpl]":";[CtCommentImpl]// NOI18N

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix = [CtInvocationImpl][CtVariableReadImpl]ctx.getCurrentPrefix();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Attr attr = [CtVariableReadImpl](([CtTypeReferenceImpl]org.w3c.dom.Attr) (ctx));
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isXPath = [CtLiteralImpl]false;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String elName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attr.getOwnerElement().getNodeName();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]elName.startsWith([CtVariableReadImpl]xslNamespacePrefix)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtInvocationImpl][CtVariableReadImpl]elName.substring([CtInvocationImpl][CtVariableReadImpl]xslNamespacePrefix.length());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String xpathAttrName = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getExprAttributes().get([CtVariableReadImpl]key)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]xpathAttrName != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]xpathAttrName.equals([CtInvocationImpl][CtVariableReadImpl]attr.getNodeName())) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// This is an XSLT element which should contain XPathExpression
                    [CtVariableWriteImpl]isXPath = [CtLiteralImpl]true;
                }
                [CtIfImpl][CtCommentImpl]// consult awailable public IDs with users catalog
                if ([CtInvocationImpl][CtLiteralImpl]"output".equals([CtVariableReadImpl]key)) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// NOI18N
                    if ([CtInvocationImpl][CtLiteralImpl]"doctype-public".equals([CtInvocationImpl][CtVariableReadImpl]attr.getName())) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// NOI18N
                        [CtTypeReferenceImpl]org.netbeans.api.xml.services.UserCatalog catalog = [CtInvocationImpl][CtTypeAccessImpl]org.netbeans.api.xml.services.UserCatalog.getDefault();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]catalog == [CtLiteralImpl]null)[CtBlockImpl]
                            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();

                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration en = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator it = [CtInvocationImpl][CtVariableReadImpl]catalog.getPublicIDs();
                        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String next = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]it.next()));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]next != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]next.startsWith([CtVariableReadImpl]prefix)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]en.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyText([CtVariableReadImpl]next));
                            }
                        } 
                        [CtReturnImpl]return [CtVariableReadImpl]en;
                    }
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String preExpression = [CtLiteralImpl]"";[CtCommentImpl]// NOI18N

            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isXPath) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Check if we are inside { } for attribute value
                [CtTypeReferenceImpl]java.lang.String nodeValue = [CtInvocationImpl][CtVariableReadImpl]attr.getNodeValue();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int exprStart = [CtInvocationImpl][CtVariableReadImpl]nodeValue.lastIndexOf([CtLiteralImpl]'{', [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]prefix.length() - [CtLiteralImpl]1);[CtCommentImpl]// NOI18N

                [CtLocalVariableImpl][CtTypeReferenceImpl]int exprEnd = [CtInvocationImpl][CtVariableReadImpl]nodeValue.indexOf([CtLiteralImpl]'}', [CtInvocationImpl][CtVariableReadImpl]prefix.length());[CtCommentImpl]// NOI18N

                [CtIfImpl][CtCommentImpl]// Util.THIS.debug("exprStart: " + exprStart); // NOI18N
                [CtCommentImpl]// Util.THIS.debug("exprEnd: " + exprEnd); // NOI18N
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]exprStart != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]exprEnd != [CtUnaryOperatorImpl](-[CtLiteralImpl]1))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]isXPath = [CtLiteralImpl]true;
                    [CtAssignmentImpl][CtVariableWriteImpl]preExpression = [CtInvocationImpl][CtVariableReadImpl]prefix.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtVariableReadImpl]exprStart + [CtLiteralImpl]1);
                    [CtAssignmentImpl][CtVariableWriteImpl]prefix = [CtInvocationImpl][CtVariableReadImpl]prefix.substring([CtBinaryOperatorImpl][CtVariableReadImpl]exprStart + [CtLiteralImpl]1);
                }
            }
            [CtIfImpl]if ([CtVariableReadImpl]isXPath) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// This is an XPath expression
                [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int curIndex = [CtInvocationImpl][CtVariableReadImpl]prefix.length();
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]curIndex > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]curIndex--;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]char curChar = [CtInvocationImpl][CtVariableReadImpl]prefix.charAt([CtVariableReadImpl]curIndex);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curChar == [CtLiteralImpl]'(') || [CtBinaryOperatorImpl]([CtVariableReadImpl]curChar == [CtLiteralImpl]',')) || [CtBinaryOperatorImpl]([CtVariableReadImpl]curChar == [CtLiteralImpl]' ')) [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtCommentImpl]// NOI18N
                        [CtVariableWriteImpl]curIndex++;
                        [CtBreakImpl]break;
                    }
                } 
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]preExpression += [CtInvocationImpl][CtVariableReadImpl]prefix.substring([CtLiteralImpl]0, [CtVariableReadImpl]curIndex);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String subExpression = [CtInvocationImpl][CtVariableReadImpl]prefix.substring([CtVariableReadImpl]curIndex);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int lastDiv = [CtInvocationImpl][CtVariableReadImpl]subExpression.lastIndexOf([CtLiteralImpl]'/');[CtCommentImpl]// NOI18N

                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String subPre = [CtLiteralImpl]"";[CtCommentImpl]// NOI18N

                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String subRest = [CtLiteralImpl]"";[CtCommentImpl]// NOI18N

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lastDiv != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]subPre = [CtInvocationImpl][CtVariableReadImpl]subExpression.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtVariableReadImpl]lastDiv + [CtLiteralImpl]1);
                    [CtAssignmentImpl][CtVariableWriteImpl]subRest = [CtInvocationImpl][CtVariableReadImpl]subExpression.substring([CtBinaryOperatorImpl][CtVariableReadImpl]lastDiv + [CtLiteralImpl]1);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]subRest = [CtVariableReadImpl]subExpression;
                }
                [CtInvocationImpl][CtCommentImpl]// At this point we need to consult transformed document or
                [CtCommentImpl]// its grammar.
                [CtCommentImpl]// [93792] +
                [CtCommentImpl]// Object selScenarioObj = scenarioCookie.getModel().getSelectedItem();
                [CtCommentImpl]// [93792] -
                [CtCommentImpl]/* if (selScenarioObj instanceof XSLScenario) {
                XSLScenario scenario = (XSLScenario)selScenarioObj;
                Document doc = null;
                try {
                doc = scenario.getSourceDocument(dataObject, false);
                } catch(Exception e) {
                // We don't care, ignore
                }

                if (doc != null) {
                Element docElement = doc.getDocumentElement();

                Set childNodeNames = new TreeSet();

                String combinedXPath;
                if (subPre.startsWith("/")) { // NOI18N
                // This is an absolute XPath
                combinedXPath = subPre;
                } else {
                // This is a relative XPath

                // Traverse up the documents tree looking for xsl:for-each
                String xslForEachName = xslNamespacePrefix + "for-each"; // NOI18N
                List selectAttrs = new LinkedList();
                Node curNode = attr.getOwnerElement();
                if (curNode != null) {
                // We don't want to add select of our selfs
                curNode = curNode.getParentNode();
                }

                while (curNode != null && !(curNode instanceof Document)) {
                if (curNode.getNodeName().equals(xslForEachName)) {
                selectAttrs.add(0, ((Element)curNode).getAttribute("select")); // NOI18N
                }

                curNode = curNode.getParentNode();
                }

                combinedXPath = ""; // NOI18N
                for (int ind = 0; ind < selectAttrs.size(); ind++) {
                combinedXPath += selectAttrs.get(ind) + "/"; // NOI18N
                }
                combinedXPath += subPre;
                }

                try {
                NodeList nodeList = XPathAPI.selectNodeList(doc, combinedXPath + "child::*"); // NOI18N
                for (int ind = 0; ind < nodeList.getLength(); ind++) {
                Node curResNode = nodeList.item(ind);
                childNodeNames.add(curResNode.getNodeName());
                }

                nodeList = XPathAPI.selectNodeList(doc, combinedXPath + "@*"); // NOI18N
                for (int ind = 0; ind < nodeList.getLength(); ind++) {
                Node curResNode = nodeList.item(ind);
                childNodeNames.add("@" + curResNode.getNodeName()); // NOI18N
                }
                } catch (Exception e) {
                Util.THIS.debug("Ignored during XPathAPI operations", e); // NOI18N
                // We don't care, ignore
                }

                addItemsToEnum(list, childNodeNames, subRest, preExpression + subPre);
                }
                }
                 */
                org.netbeans.modules.xsl.grammar.XSLGrammarQuery.addItemsToEnum([CtVariableReadImpl]list, [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getXPathAxes(), [CtVariableReadImpl]subRest, [CtBinaryOperatorImpl][CtVariableReadImpl]preExpression + [CtVariableReadImpl]subPre);
                [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.addItemsToEnum([CtVariableReadImpl]list, [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.getXslFunctions(), [CtVariableReadImpl]subExpression, [CtVariableReadImpl]preExpression);
                [CtReturnImpl]return [CtVariableReadImpl]list;
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.GrammarResult queryDefault([CtParameterImpl][CtTypeReferenceImpl]HintContext ctx) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// ??? XSLT defaults are missing
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]resultGrammarQuery == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]resultGrammarQuery.queryDefault([CtVariableReadImpl]ctx);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isAllowed([CtParameterImpl][CtTypeReferenceImpl]java.util.Enumeration en) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;[CtCommentImpl]// !!! not implemented

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Enumeration queryEntities([CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration();
        [CtIfImpl][CtCommentImpl]// add well-know build-in entity names
        if ([CtInvocationImpl][CtLiteralImpl]"lt".startsWith([CtVariableReadImpl]prefix))[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]list.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyEntityReference([CtLiteralImpl]"lt"));
        [CtCommentImpl]// NOI18N

        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"gt".startsWith([CtVariableReadImpl]prefix))[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]list.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyEntityReference([CtLiteralImpl]"gt"));
        [CtCommentImpl]// NOI18N

        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"apos".startsWith([CtVariableReadImpl]prefix))[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]list.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyEntityReference([CtLiteralImpl]"apos"));
        [CtCommentImpl]// NOI18N

        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"quot".startsWith([CtVariableReadImpl]prefix))[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]list.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyEntityReference([CtLiteralImpl]"quot"));
        [CtCommentImpl]// NOI18N

        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"amp".startsWith([CtVariableReadImpl]prefix))[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]list.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyEntityReference([CtLiteralImpl]"amp"));
        [CtCommentImpl]// NOI18N

        [CtReturnImpl]return [CtVariableReadImpl]list;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Enumeration queryNotations([CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.Enumerations.empty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.awt.Component getCustomizer([CtParameterImpl][CtTypeReferenceImpl]HintContext ctx) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]customizer == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]customizer = [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.lookupCustomizerInstance();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]customizer == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]customizer.getCustomizer([CtVariableReadImpl]ctx, [CtFieldReadImpl]dataObject);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasCustomizer([CtParameterImpl][CtTypeReferenceImpl]HintContext ctx) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]customizer == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]customizer = [CtInvocationImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.lookupCustomizerInstance();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]customizer == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]customizer.hasCustomizer([CtVariableReadImpl]ctx);
    }

    [CtMethodImpl]public org.openide.nodes.Node.Property[] getProperties([CtParameterImpl]final [CtTypeReferenceImpl]HintContext ctx) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ctx.getNodeType() != [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]ATTRIBUTE_NODE) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ctx.getNodeValue() == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openide.nodes.PropertySupport attrNameProp = [CtNewClassImpl][CtCommentImpl]// NOI18N
        new [CtTypeReferenceImpl]org.openide.nodes.PropertySupport([CtLiteralImpl]"Attribute name", [CtFieldReadImpl]java.lang.String.class, [CtInvocationImpl][CtFieldReadImpl]bundle.getString([CtLiteralImpl]"BK0001"), [CtInvocationImpl][CtFieldReadImpl]bundle.getString([CtLiteralImpl]"BK0002"), [CtLiteralImpl]true, [CtLiteralImpl]false)[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void setValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
                [CtCommentImpl]// Dummy
            }

            [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Object getValue() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ctx.getNodeName();
            }
        };
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openide.nodes.PropertySupport attrValueProp = [CtNewClassImpl][CtCommentImpl]// NOI18N
        new [CtTypeReferenceImpl]org.openide.nodes.PropertySupport([CtLiteralImpl]"Attribute value", [CtFieldReadImpl]java.lang.String.class, [CtInvocationImpl][CtFieldReadImpl]bundle.getString([CtLiteralImpl]"BK0003"), [CtInvocationImpl][CtFieldReadImpl]bundle.getString([CtLiteralImpl]"BK0004"), [CtLiteralImpl]true, [CtLiteralImpl]true)[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void setValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ctx.setNodeValue([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)));
            }

            [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Object getValue() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ctx.getNodeValue();
            }
        };
        [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]org.openide.nodes.Node.Property[]{ [CtVariableReadImpl]attrNameProp, [CtVariableReadImpl]attrValueProp };
    }

    [CtMethodImpl][CtCommentImpl]// //////////////////////////////////////////////////////////////////////////////
    [CtCommentImpl]// Private helper methods
    [CtJavaDocImpl]/**
     * Looks up registered XSLCustomizer objects which will be used by this object
     */
    private static [CtTypeReferenceImpl]org.netbeans.modules.xsl.api.XSLCustomizer lookupCustomizerInstance() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.openide.util.Lookup.Template lookupTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.openide.util.Lookup.Template([CtFieldReadImpl]org.netbeans.modules.xsl.api.XSLCustomizer.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.openide.util.Lookup.Item lookupItem = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openide.util.lookup.Lookups.forPath([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.CUSTOMIZER_FOLDER).lookupItem([CtVariableReadImpl]lookupTemplate);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lookupItem == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.netbeans.modules.xsl.api.XSLCustomizer) ([CtVariableReadImpl]lookupItem.getInstance()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param enumX
     * 		the Enumeration which the element should be added to
     * @param elements
     * 		a set containing strings which should be added (with prefix) to the enum or <code>null</null>
     * @param namespacePrefix
     * 		a prefix at the form "xsl:" which should be added in front
     * 		of the names in the elements.
     * @param startWith
     * 		Elements should only be added to enum if they start with this string
     */
    private static [CtTypeReferenceImpl]void addXslElementsToEnum([CtParameterImpl][CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration enumX, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set elements, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String namespacePrefix, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String startWith) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elements == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]startWith.startsWith([CtVariableReadImpl]namespacePrefix) || [CtInvocationImpl][CtVariableReadImpl]namespacePrefix.startsWith([CtVariableReadImpl]startWith)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator it = [CtInvocationImpl][CtVariableReadImpl]elements.iterator();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object next = [CtInvocationImpl][CtVariableReadImpl]it.next();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]next != [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.resultElements) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nextText = [CtBinaryOperatorImpl][CtVariableReadImpl]namespacePrefix + [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (next));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nextText.startsWith([CtVariableReadImpl]startWith)) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// TODO pass true for empty elements
                        [CtVariableReadImpl]enumX.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyElement([CtVariableReadImpl]nextText, [CtLiteralImpl]false));
                    }
                }
            } 
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void addItemsToEnum([CtParameterImpl][CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.QueueEnumeration enumX, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set set, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String startWith, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator it = [CtInvocationImpl][CtVariableReadImpl]set.iterator();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nextText = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]it.next()));
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nextText.startsWith([CtVariableReadImpl]startWith)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]enumX.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.MyText([CtBinaryOperatorImpl][CtVariableReadImpl]prefix + [CtVariableReadImpl]nextText));
            }
        } 
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method traverses up the document tree, investigates it and updates
     * prefixList, resultGrammarQuery, lastDoctypeSystem or lastDoctypePublic
     * members if necessery.
     *
     * @param curNode
     * 		the node which from wich the traversing should start.
     */
    private [CtTypeReferenceImpl]void updateProperties([CtParameterImpl][CtTypeReferenceImpl]org.w3c.dom.Node curNode) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]prefixList.clear();
        [CtLocalVariableImpl][CtCommentImpl]// Traverse up the documents tree
        [CtTypeReferenceImpl]org.w3c.dom.Node rootNode = [CtVariableReadImpl]curNode;
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]curNode != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]curNode instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.w3c.dom.Document))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Update the xsl namespace prefix list
            [CtTypeReferenceImpl]org.w3c.dom.NamedNodeMap attributes = [CtInvocationImpl][CtVariableReadImpl]curNode.getAttributes();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int ind = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]ind < [CtInvocationImpl][CtVariableReadImpl]attributes.getLength(); [CtUnaryOperatorImpl][CtVariableWriteImpl]ind++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Attr attr = [CtInvocationImpl](([CtTypeReferenceImpl]org.w3c.dom.Attr) ([CtVariableReadImpl]attributes.item([CtVariableReadImpl]ind)));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String attrName = [CtInvocationImpl][CtVariableReadImpl]attr.getName();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]attrName != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]attrName.startsWith([CtLiteralImpl]"xmlns:")) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// NOI18N
                    if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attr.getValue().equals([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.XSLT_NAMESPACE_URI)) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]prefixList.add([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]attrName.substring([CtLiteralImpl]6));
                    }
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]rootNode = [CtVariableReadImpl]curNode;
            [CtAssignmentImpl][CtVariableWriteImpl]curNode = [CtInvocationImpl][CtVariableReadImpl]rootNode.getParentNode();
        } 
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean outputFound = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]prefixList.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String outputElName = [CtBinaryOperatorImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]prefixList.get([CtLiteralImpl]0))) + [CtLiteralImpl]":output";[CtCommentImpl]// NOI18N

            [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Node childOfRoot = [CtInvocationImpl][CtVariableReadImpl]rootNode.getFirstChild();
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]childOfRoot != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String childNodeName = [CtInvocationImpl][CtVariableReadImpl]childOfRoot.getNodeName();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]childNodeName != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]childNodeName.equals([CtVariableReadImpl]outputElName)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Element outputEl = [CtVariableReadImpl](([CtTypeReferenceImpl]org.w3c.dom.Element) (childOfRoot));
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String outputMethod = [CtInvocationImpl][CtVariableReadImpl]outputEl.getAttribute([CtLiteralImpl]"method");[CtCommentImpl]// NOI18N

                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String curDoctypePublic = [CtInvocationImpl][CtVariableReadImpl]outputEl.getAttribute([CtLiteralImpl]"doctype-public");[CtCommentImpl]// NOI18N

                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String curDoctypeSystem = [CtInvocationImpl][CtVariableReadImpl]outputEl.getAttribute([CtLiteralImpl]"doctype-system");[CtCommentImpl]// NOI18N

                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtLiteralImpl]"html".equals([CtVariableReadImpl]outputMethod)[CtCommentImpl]// NOI18N
                     && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curDoctypePublic == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]curDoctypePublic.length() == [CtLiteralImpl]0))) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curDoctypeSystem == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]curDoctypeSystem.length() == [CtLiteralImpl]0))) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// NOI18N
                        [CtCommentImpl]// html is special case that can be emulated using XHTML
                        [CtVariableWriteImpl]curDoctypePublic = [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.XHTML_PUBLIC_ID;
                        [CtAssignmentImpl][CtVariableWriteImpl]curDoctypeSystem = [CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.XHTML_SYSTEM_ID;
                    } else [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"text".equals([CtVariableReadImpl]outputMethod)) [CtBlockImpl]{
                        [CtBreakImpl][CtCommentImpl]// NOI18N
                        [CtCommentImpl]// user error, ignore
                        break;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curDoctypePublic != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]curDoctypePublic.equals([CtFieldReadImpl]lastDoctypePublic))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curDoctypePublic == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]lastDoctypePublic != [CtLiteralImpl]null))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curDoctypeSystem != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]curDoctypeSystem.equals([CtFieldReadImpl]lastDoctypeSystem)))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curDoctypeSystem == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]lastDoctypeSystem != [CtLiteralImpl]null))) [CtBlockImpl]{
                        [CtInvocationImpl]setOutputDoctype([CtVariableReadImpl]curDoctypePublic, [CtVariableReadImpl]curDoctypeSystem);
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]outputFound = [CtLiteralImpl]true;
                    [CtBreakImpl]break;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]childOfRoot = [CtInvocationImpl][CtVariableReadImpl]childOfRoot.getNextSibling();
            } 
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]outputFound) [CtBlockImpl]{
            [CtInvocationImpl]setOutputDoctype([CtLiteralImpl]null, [CtLiteralImpl]null);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates resultGrammarQuery by parsing the DTD specified by publicId and
     * systemId. lastDoctypeSystem and lastDoctypePublic are assigned to the new values.
     *
     * @param publicId
     * 		the public identifier of the DTD
     * @param publicId
     * 		the system identifier of the DTD
     */
    private [CtTypeReferenceImpl]void setOutputDoctype([CtParameterImpl][CtTypeReferenceImpl]java.lang.String publicId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String systemId) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]lastDoctypePublic = [CtVariableReadImpl]publicId;
        [CtAssignmentImpl][CtFieldWriteImpl]lastDoctypeSystem = [CtVariableReadImpl]systemId;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]publicId == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]systemId == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]resultGrammarQuery = [CtLiteralImpl]null;
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.xml.sax.InputSource inputSource = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.api.xml.services.UserCatalog catalog = [CtInvocationImpl][CtTypeAccessImpl]org.netbeans.api.xml.services.UserCatalog.getDefault();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]catalog != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.xml.sax.EntityResolver resolver = [CtInvocationImpl][CtVariableReadImpl]catalog.getEntityResolver();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resolver != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]inputSource = [CtInvocationImpl][CtVariableReadImpl]resolver.resolveEntity([CtVariableReadImpl]publicId, [CtVariableReadImpl]systemId);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.xml.sax.SAXException e) [CtBlockImpl]{
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                }[CtCommentImpl]// Will be handled below

            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inputSource == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL url = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtVariableReadImpl]systemId);
                [CtAssignmentImpl][CtVariableWriteImpl]inputSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.xml.sax.InputSource([CtInvocationImpl][CtVariableReadImpl]url.openStream());
                [CtInvocationImpl][CtVariableReadImpl]inputSource.setPublicId([CtVariableReadImpl]publicId);
                [CtInvocationImpl][CtVariableReadImpl]inputSource.setSystemId([CtVariableReadImpl]systemId);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]resultGrammarQuery = [CtLiteralImpl]null;
                [CtReturnImpl]return;
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]resultGrammarQuery = [CtInvocationImpl][CtTypeAccessImpl]DTDUtil.parseDTD([CtLiteralImpl]true, [CtVariableReadImpl]inputSource);
    }

    [CtClassImpl][CtCommentImpl]// //////////////////////////////////////////////////////////////////////////////
    [CtCommentImpl]// Private helper classes
    private class ResultHintContext extends [CtTypeReferenceImpl]ResultNode implements [CtTypeReferenceImpl]HintContext {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String currentPrefix;

        [CtConstructorImpl]public ResultHintContext([CtParameterImpl][CtTypeReferenceImpl]HintContext peer, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String ignorePrefix, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String onlyUsePrefix) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]peer, [CtVariableReadImpl]ignorePrefix, [CtVariableReadImpl]onlyUsePrefix);
            [CtAssignmentImpl][CtFieldWriteImpl]currentPrefix = [CtInvocationImpl][CtVariableReadImpl]peer.getCurrentPrefix();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCurrentPrefix() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]currentPrefix;
        }
    }

    [CtClassImpl][CtCommentImpl]// Result classes ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static abstract class AbstractResultNode extends [CtTypeReferenceImpl]AbstractNode implements [CtTypeReferenceImpl]GrammarResult {
        [CtMethodImpl]public [CtTypeReferenceImpl]javax.swing.Icon getIcon([CtParameterImpl][CtTypeReferenceImpl]int kind) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return provide additional information simplifiing decision
         */
        public [CtTypeReferenceImpl]java.lang.String getDescription() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openide.util.NbBundle.getMessage([CtFieldReadImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.class, [CtLiteralImpl]"BK0005");
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return text representing name of suitable entity
        //??? is it really needed
         */
        public [CtTypeReferenceImpl]java.lang.String getText() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getNodeName();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return name that is presented to user
         */
        public [CtTypeReferenceImpl]java.lang.String getDisplayName() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isEmptyElement() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtClassImpl]private static class MyEntityReference extends [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.AbstractResultNode implements [CtTypeReferenceImpl]org.w3c.dom.EntityReference {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String name;

        [CtConstructorImpl]MyEntityReference([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]short getNodeType() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]ENTITY_REFERENCE_NODE;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getNodeName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }
    }

    [CtClassImpl]private static class MyElement extends [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.AbstractResultNode implements [CtTypeReferenceImpl]org.w3c.dom.Element {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String name;

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean empty;

        [CtConstructorImpl]MyElement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]boolean empty) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.empty = [CtVariableReadImpl]empty;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]short getNodeType() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]ELEMENT_NODE;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getNodeName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getTagName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isEmptyElement() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]empty;
        }
    }

    [CtClassImpl]private static class MyAttr extends [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.AbstractResultNode implements [CtTypeReferenceImpl]org.w3c.dom.Attr {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String name;

        [CtConstructorImpl]MyAttr([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]short getNodeType() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]ATTRIBUTE_NODE;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getNodeName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;[CtCommentImpl]// ??? what spec says

        }
    }

    [CtClassImpl]private static class MyText extends [CtTypeReferenceImpl]org.netbeans.modules.xsl.grammar.XSLGrammarQuery.AbstractResultNode implements [CtTypeReferenceImpl]org.w3c.dom.Text {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String data;

        [CtConstructorImpl]MyText([CtParameterImpl][CtTypeReferenceImpl]java.lang.String data) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.data = [CtVariableReadImpl]data;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]short getNodeType() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]org.w3c.dom.Node.[CtFieldReferenceImpl]TEXT_NODE;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getNodeValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getData();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getData() throws [CtTypeReferenceImpl]org.w3c.dom.DOMException [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]data;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getDisplayName() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getData();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int getLength() [CtBlockImpl]{
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]data == [CtLiteralImpl]null ? [CtUnaryOperatorImpl]-[CtLiteralImpl]1 : [CtInvocationImpl][CtFieldReadImpl]data.length();
        }
    }

    [CtClassImpl]private static class QueueEnumeration implements [CtTypeReferenceImpl]java.util.Enumeration {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.LinkedList list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList();

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasMoreElements() [CtBlockImpl]{
            [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]list.isEmpty();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Object nextElement() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]list.removeFirst();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void put([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] arr) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]list.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]arr));
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void put([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]list.add([CtVariableReadImpl]o);
        }
    }
}
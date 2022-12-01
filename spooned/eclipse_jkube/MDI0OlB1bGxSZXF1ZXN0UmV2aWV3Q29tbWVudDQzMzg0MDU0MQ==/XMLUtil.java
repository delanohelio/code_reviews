[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2019 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at:
 *
 *     https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
[CtPackageDeclarationImpl]package org.eclipse.jkube.kit.common.util;
[CtImportImpl]import javax.xml.transform.Transformer;
[CtImportImpl]import javax.xml.transform.TransformerFactory;
[CtImportImpl]import javax.xml.transform.dom.DOMSource;
[CtImportImpl]import javax.xml.parsers.DocumentBuilderFactory;
[CtImportImpl]import javax.xml.xpath.XPath;
[CtImportImpl]import org.w3c.dom.Node;
[CtImportImpl]import javax.xml.transform.stream.StreamResult;
[CtImportImpl]import javax.xml.xpath.XPathExpressionException;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import org.w3c.dom.Document;
[CtImportImpl]import org.xml.sax.SAXException;
[CtImportImpl]import javax.xml.xpath.XPathConstants;
[CtImportImpl]import javax.xml.xpath.XPathFactory;
[CtImportImpl]import javax.xml.parsers.DocumentBuilder;
[CtImportImpl]import javax.xml.XMLConstants;
[CtImportImpl]import javax.xml.parsers.ParserConfigurationException;
[CtImportImpl]import java.io.File;
[CtImportImpl]import javax.xml.transform.TransformerException;
[CtClassImpl]public class XMLUtil {
    [CtConstructorImpl]private XMLUtil() [CtBlockImpl]{
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.w3c.dom.Document createNewDocument() throws [CtTypeReferenceImpl]javax.xml.parsers.ParserConfigurationException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = [CtInvocationImpl]org.eclipse.jkube.kit.common.util.XMLUtil.getDocumentBuilderFactory();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.newDocumentBuilder().newDocument();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.w3c.dom.Document readXML([CtParameterImpl][CtTypeReferenceImpl]java.io.File xmlFile) throws [CtTypeReferenceImpl]javax.xml.parsers.ParserConfigurationException, [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.xml.sax.SAXException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = [CtInvocationImpl]org.eclipse.jkube.kit.common.util.XMLUtil.getDocumentBuilderFactory();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilder documentBuilder = [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.newDocumentBuilder();
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]documentBuilder.parse([CtVariableReadImpl]xmlFile);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void writeXML([CtParameterImpl][CtTypeReferenceImpl]org.w3c.dom.Document document, [CtParameterImpl][CtTypeReferenceImpl]java.io.File xmlFile) throws [CtTypeReferenceImpl]javax.xml.transform.TransformerException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.transform.TransformerFactory transformerFactory = [CtInvocationImpl][CtTypeAccessImpl]javax.xml.transform.TransformerFactory.newInstance();
        [CtInvocationImpl][CtVariableReadImpl]transformerFactory.setFeature([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.XMLConstants.[CtFieldReferenceImpl]FEATURE_SECURE_PROCESSING, [CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]transformerFactory.setAttribute([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.XMLConstants.[CtFieldReferenceImpl]ACCESS_EXTERNAL_DTD, [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]transformerFactory.setAttribute([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.XMLConstants.[CtFieldReferenceImpl]ACCESS_EXTERNAL_STYLESHEET, [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.transform.Transformer transformer = [CtInvocationImpl][CtVariableReadImpl]transformerFactory.newTransformer();
        [CtInvocationImpl][CtVariableReadImpl]document.setXmlStandalone([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.transform.dom.DOMSource source = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.transform.dom.DOMSource([CtVariableReadImpl]document);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.transform.stream.StreamResult result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.transform.stream.StreamResult([CtVariableReadImpl]xmlFile);
        [CtInvocationImpl][CtVariableReadImpl]transformer.transform([CtVariableReadImpl]source, [CtVariableReadImpl]result);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.w3c.dom.Node getNodeFromDocument([CtParameterImpl][CtTypeReferenceImpl]org.w3c.dom.Document doc, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String xPathExpression) throws [CtTypeReferenceImpl]javax.xml.xpath.XPathExpressionException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.xpath.XPathFactory xPathfactory = [CtInvocationImpl][CtTypeAccessImpl]javax.xml.xpath.XPathFactory.newInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.xpath.XPath xpath = [CtInvocationImpl][CtVariableReadImpl]xPathfactory.newXPath();
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.w3c.dom.Node) ([CtInvocationImpl][CtVariableReadImpl]xpath.compile([CtVariableReadImpl]xPathExpression).evaluate([CtVariableReadImpl]doc, [CtFieldReadImpl][CtTypeAccessImpl]javax.xml.xpath.XPathConstants.[CtFieldReferenceImpl]NODE)));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getNodeValueFromDocument([CtParameterImpl][CtTypeReferenceImpl]org.w3c.dom.Document doc, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String xPathExpression) throws [CtTypeReferenceImpl]javax.xml.xpath.XPathExpressionException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.xpath.XPathFactory xPathfactory = [CtInvocationImpl][CtTypeAccessImpl]javax.xml.xpath.XPathFactory.newInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.xpath.XPath xpath = [CtInvocationImpl][CtVariableReadImpl]xPathfactory.newXPath();
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtVariableReadImpl]xpath.compile([CtVariableReadImpl]xPathExpression).evaluate([CtVariableReadImpl]doc, [CtFieldReadImpl][CtTypeAccessImpl]javax.xml.xpath.XPathConstants.[CtFieldReferenceImpl]STRING)));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilderFactory getDocumentBuilderFactory() throws [CtTypeReferenceImpl]javax.xml.parsers.ParserConfigurationException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = [CtInvocationImpl][CtTypeAccessImpl]javax.xml.parsers.DocumentBuilderFactory.newInstance();
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setAttribute([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.XMLConstants.[CtFieldReferenceImpl]FEATURE_SECURE_PROCESSING, [CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setAttribute([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.XMLConstants.[CtFieldReferenceImpl]ACCESS_EXTERNAL_DTD, [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setAttribute([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.XMLConstants.[CtFieldReferenceImpl]ACCESS_EXTERNAL_SCHEMA, [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setFeature([CtLiteralImpl]"http://apache.org/xml/features/nonvalidating/load-external-dtd", [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setFeature([CtLiteralImpl]"http://apache.org/xml/features/disallow-doctype-decl", [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setFeature([CtLiteralImpl]"http://xml.org/sax/features/external-general-entities", [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setFeature([CtLiteralImpl]"http://xml.org/sax/features/external-parameter-entities", [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setXIncludeAware([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]documentBuilderFactory.setExpandEntityReferences([CtLiteralImpl]false);
        [CtReturnImpl]return [CtVariableReadImpl]documentBuilderFactory;
    }
}
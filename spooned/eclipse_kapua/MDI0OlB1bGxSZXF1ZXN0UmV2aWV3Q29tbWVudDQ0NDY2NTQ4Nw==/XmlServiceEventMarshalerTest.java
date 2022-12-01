[CompilationUnitImpl][CtJavaDocImpl]/**
 * *****************************************************************************
 * Copyright (c) 2020 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 * *****************************************************************************
 */
[CtPackageDeclarationImpl]package org.eclipse.kapua.integration.misc;
[CtUnresolvedImport]import org.eclipse.kapua.qa.markers.junit.JUnitTests;
[CtUnresolvedImport]import org.eclipse.kapua.event.ServiceEventBusException;
[CtImportImpl]import java.io.StringWriter;
[CtUnresolvedImport]import org.eclipse.kapua.commons.util.xml.XmlUtil;
[CtUnresolvedImport]import org.eclipse.kapua.event.ServiceEvent;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.eclipse.kapua.commons.event.XmlServiceEventMarshaler;
[CtUnresolvedImport]import org.junit.Assert;
[CtUnresolvedImport]import org.junit.experimental.categories.Category;
[CtUnresolvedImport]import org.eclipse.kapua.KapuaException;
[CtUnresolvedImport]import org.eclipse.kapua.qa.common.TestJAXBContextProvider;
[CtUnresolvedImport]import org.junit.Before;
[CtClassImpl][CtAnnotationImpl]@org.junit.experimental.categories.Category([CtFieldReadImpl]org.eclipse.kapua.qa.markers.junit.JUnitTests.class)
public class XmlServiceEventMarshalerTest extends [CtTypeReferenceImpl]org.junit.Assert {
    [CtFieldImpl][CtTypeReferenceImpl]org.eclipse.kapua.event.ServiceEvent serviceEvent;

    [CtFieldImpl][CtTypeReferenceImpl]org.eclipse.kapua.commons.event.XmlServiceEventMarshaler xmlServiceEventMarshaler;

    [CtFieldImpl][CtTypeReferenceImpl]java.io.StringWriter stringWriter;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void createInstanceOfClasses() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]serviceEvent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.kapua.event.ServiceEvent();
        [CtAssignmentImpl][CtFieldWriteImpl]xmlServiceEventMarshaler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.kapua.commons.event.XmlServiceEventMarshaler();
        [CtAssignmentImpl][CtFieldWriteImpl]stringWriter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void marshalXmlWithoutContextTest() throws [CtTypeReferenceImpl]org.eclipse.kapua.event.ServiceEventBusException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]stringWriter.write([CtBinaryOperatorImpl][CtLiteralImpl]"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + [CtLiteralImpl]"<serviceEvent/>\n");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String expectedValues = [CtInvocationImpl][CtFieldReadImpl]stringWriter.toString();
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.kapua.commons.util.xml.XmlUtil.setContextProvider([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.kapua.qa.common.TestJAXBContextProvider());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"not_equals", [CtVariableReadImpl]expectedValues, [CtInvocationImpl][CtFieldReadImpl]xmlServiceEventMarshaler.marshal([CtFieldReadImpl]serviceEvent));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void marshalJsonWithContextTest() throws [CtTypeReferenceImpl]org.eclipse.kapua.event.ServiceEventBusException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]stringWriter.write([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + [CtLiteralImpl]"<serviceEvent>\n") + [CtLiteralImpl]"   <id>id</id>\n") + [CtLiteralImpl]"   <contextId>contextId</contextId>\n") + [CtLiteralImpl]"   <entityType>entityType</entityType>\n") + [CtLiteralImpl]"   <status>SENT</status>\n") + [CtLiteralImpl]"   <note>note</note>\n") + [CtLiteralImpl]"</serviceEvent>\n");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String expectedValues = [CtInvocationImpl][CtFieldReadImpl]stringWriter.toString();
        [CtInvocationImpl][CtFieldReadImpl]serviceEvent.setId([CtLiteralImpl]"id");
        [CtInvocationImpl][CtFieldReadImpl]serviceEvent.setContextId([CtLiteralImpl]"contextId");
        [CtInvocationImpl][CtFieldReadImpl]serviceEvent.setEntityType([CtLiteralImpl]"entityType");
        [CtInvocationImpl][CtFieldReadImpl]serviceEvent.setStatus([CtTypeAccessImpl]ServiceEvent.EventStatus.SENT);
        [CtInvocationImpl][CtFieldReadImpl]serviceEvent.setNote([CtLiteralImpl]"note");
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.kapua.commons.util.xml.XmlUtil.setContextProvider([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.kapua.qa.common.TestJAXBContextProvider());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"not_equals", [CtVariableReadImpl]expectedValues, [CtInvocationImpl][CtFieldReadImpl]xmlServiceEventMarshaler.marshal([CtFieldReadImpl]serviceEvent));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]org.eclipse.kapua.event.ServiceEventBusException.class)
    public [CtTypeReferenceImpl]void unmarshalXmlWithoutJAXBContextProviderTest() throws [CtTypeReferenceImpl]org.eclipse.kapua.KapuaException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]xmlServiceEventMarshaler.unmarshal([CtLiteralImpl]"message");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.lang.NullPointerException.class)
    public [CtTypeReferenceImpl]void unmarshalXmlWithNullContextTest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]xmlServiceEventMarshaler.unmarshal([CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void unmarshalXmlWithContextTest() throws [CtTypeReferenceImpl]org.eclipse.kapua.KapuaException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.kapua.commons.util.xml.XmlUtil.setContextProvider([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.kapua.qa.common.TestJAXBContextProvider());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.kapua.event.ServiceEvent elements = [CtInvocationImpl][CtFieldReadImpl]xmlServiceEventMarshaler.unmarshal([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + [CtLiteralImpl]"<serviceEvent>\n") + [CtLiteralImpl]"   <id>id</id>\n") + [CtLiteralImpl]"   <contextId>contextId</contextId>\n") + [CtLiteralImpl]"   <entityType>entityType</entityType>\n") + [CtLiteralImpl]"   <status>SENT</status>\n") + [CtLiteralImpl]"   <note>note</note>\n") + [CtLiteralImpl]"</serviceEvent>\n");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"not_equals", [CtLiteralImpl]"id", [CtInvocationImpl][CtVariableReadImpl]elements.getId());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"not_equals", [CtLiteralImpl]"contextId", [CtInvocationImpl][CtVariableReadImpl]elements.getContextId());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"not_equals", [CtLiteralImpl]"entityType", [CtInvocationImpl][CtVariableReadImpl]elements.getEntityType());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"not_equals", [CtTypeAccessImpl]ServiceEvent.EventStatus.SENT, [CtInvocationImpl][CtVariableReadImpl]elements.getStatus());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void getContentTypeTest() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"not_equals", [CtLiteralImpl]"application/xml", [CtInvocationImpl][CtFieldReadImpl]xmlServiceEventMarshaler.getContentType());
    }
}
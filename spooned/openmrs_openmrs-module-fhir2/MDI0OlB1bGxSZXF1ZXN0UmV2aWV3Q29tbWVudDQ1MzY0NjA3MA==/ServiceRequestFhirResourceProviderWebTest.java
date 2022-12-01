[CompilationUnitImpl][CtCommentImpl]/* This Source Code Form is subject to the terms of the Mozilla Public License,
v. 2.0. If a copy of the MPL was not distributed with this file, You can
obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
the terms of the Healthcare Disclaimer located at http://openmrs.org/license.

Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
graphic logo is a trademark of OpenMRS Inc.
 */
[CtPackageDeclarationImpl]package org.openmrs.module.fhir2.providers.r4;
[CtUnresolvedImport]import static org.mockito.ArgumentMatchers.any;
[CtUnresolvedImport]import lombok.Getter;
[CtUnresolvedImport]import javax.servlet.ServletException;
[CtUnresolvedImport]import org.mockito.Mock;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import static org.mockito.Mockito.when;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.openmrs.module.fhir2.api.FhirServiceRequestService;
[CtUnresolvedImport]import org.springframework.mock.web.MockHttpServletResponse;
[CtUnresolvedImport]import static org.hamcrest.Matchers.equalTo;
[CtUnresolvedImport]import org.hl7.fhir.r4.model.OperationOutcome;
[CtUnresolvedImport]import static org.hamcrest.Matchers.containsStringIgnoringCase;
[CtUnresolvedImport]import lombok.AccessLevel;
[CtImportImpl]import org.apache.commons.io.IOUtils;
[CtUnresolvedImport]import static org.mockito.ArgumentMatchers.anyString;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import static org.hamcrest.MatcherAssert.assertThat;
[CtUnresolvedImport]import org.mockito.junit.MockitoJUnitRunner;
[CtUnresolvedImport]import org.hl7.fhir.r4.model.ServiceRequest;
[CtUnresolvedImport]import org.junit.Before;
[CtClassImpl][CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.mockito.junit.MockitoJUnitRunner.class)
public class ServiceRequestFhirResourceProviderWebTest extends [CtTypeReferenceImpl]org.openmrs.module.fhir2.providers.r4.BaseFhirR4ResourceProviderWebTest<[CtTypeReferenceImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProvider, [CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SERVICE_REQUEST_UUID = [CtLiteralImpl]"7d13b03b-58c2-43f5-b34d-08750c51aea9";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String WRONG_SERVICE_REQUEST_UUID = [CtLiteralImpl]"92b04062-e57d-43aa-8c38-90a1ad70080c";

    [CtFieldImpl][CtAnnotationImpl]@lombok.Getter([CtFieldReadImpl]lombok.AccessLevel.PUBLIC)
    private [CtTypeReferenceImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProvider resourceProvider;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.openmrs.module.fhir2.api.FhirServiceRequestService service;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String JSON_CREATE_SERVICE_REQUEST_PATH = [CtLiteralImpl]"org/openmrs/module/fhir2/providers/ServiceRequestWebTest_create.json";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String JSON_UPDATE_SERVICE_REQUEST_PATH = [CtLiteralImpl]"org/openmrs/module/fhir2/providers/ServiceRequestWebTest_update.json";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String JSON_UPDATE_SERVICE_REQUEST_NO_ID_PATH = [CtLiteralImpl]"org/openmrs/module/fhir2/providers/ServiceRequestWebTest_UpdateWithoutId.json";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String JSON_UPDATE_SERVICE_REQUEST_WRONG_ID_PATH = [CtLiteralImpl]"org/openmrs/module/fhir2/providers/ServiceRequestWebTest_UpdateWithWrongId.json";

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setup() throws [CtTypeReferenceImpl]javax.servlet.ServletException [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]resourceProvider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProvider();
        [CtInvocationImpl][CtFieldReadImpl]resourceProvider.setServiceRequestService([CtFieldReadImpl]service);
        [CtInvocationImpl][CtSuperAccessImpl]super.setup();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void getServiceRequestById_shouldReturnServiceRequest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest serviceRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest();
        [CtInvocationImpl][CtVariableReadImpl]serviceRequest.setId([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]service.get([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID)).thenReturn([CtVariableReadImpl]serviceRequest);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]get([CtBinaryOperatorImpl][CtLiteralImpl]"/ServiceRequest/" + [CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID).accept([CtTypeAccessImpl]FhirMediaTypes.JSON).go();
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]response, [CtInvocationImpl]isOk());
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]response.getContentType(), [CtInvocationImpl]Matchers.equalTo([CtInvocationImpl][CtTypeAccessImpl]FhirMediaTypes.JSON.toString()));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]readResponse([CtVariableReadImpl]response).getIdElement().getIdPart(), [CtInvocationImpl]Matchers.equalTo([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void getEncounterByWrongUuid_shouldReturn404() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]get([CtBinaryOperatorImpl][CtLiteralImpl]"/ServiceRequest/" + [CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.WRONG_SERVICE_REQUEST_UUID).accept([CtTypeAccessImpl]FhirMediaTypes.JSON).go();
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]response, [CtInvocationImpl]isNotFound());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void createServiceRequest_shouldCreateServiceRequest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonServiceRequest;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getClassLoader().getResourceAsStream([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.JSON_CREATE_SERVICE_REQUEST_PATH)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]is);
            [CtAssignmentImpl][CtVariableWriteImpl]jsonServiceRequest = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.IOUtils.toString([CtVariableReadImpl]is, [CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest serviceRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest();
        [CtInvocationImpl][CtVariableReadImpl]serviceRequest.setId([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]service.create([CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]org.hl7.fhir.r4.model.ServiceRequest.class))).thenReturn([CtVariableReadImpl]serviceRequest);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]post([CtLiteralImpl]"/ServiceRequest").jsonContent([CtVariableReadImpl]jsonServiceRequest).accept([CtTypeAccessImpl]FhirMediaTypes.JSON).go();
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]response, [CtInvocationImpl]isCreated());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void updateServiceRequest_shouldUpdateExistingServiceRequest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonServiceRequest;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getClassLoader().getResourceAsStream([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.JSON_UPDATE_SERVICE_REQUEST_PATH)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]is);
            [CtAssignmentImpl][CtVariableWriteImpl]jsonServiceRequest = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.IOUtils.toString([CtVariableReadImpl]is, [CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest serviceRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest();
        [CtInvocationImpl][CtVariableReadImpl]serviceRequest.setId([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]service.update([CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]org.hl7.fhir.r4.model.ServiceRequest.class))).thenReturn([CtVariableReadImpl]serviceRequest);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]put([CtBinaryOperatorImpl][CtLiteralImpl]"/ServiceRequest/" + [CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID).jsonContent([CtVariableReadImpl]jsonServiceRequest).accept([CtTypeAccessImpl]FhirMediaTypes.JSON).go();
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]response, [CtInvocationImpl]isOk());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void updateServiceRequest_shouldErrorForNoId() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonServiceRequest;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getClassLoader().getResourceAsStream([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.JSON_UPDATE_SERVICE_REQUEST_NO_ID_PATH)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]is);
            [CtAssignmentImpl][CtVariableWriteImpl]jsonServiceRequest = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.IOUtils.toString([CtVariableReadImpl]is, [CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]put([CtBinaryOperatorImpl][CtLiteralImpl]"/ServiceRequest/" + [CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID).jsonContent([CtVariableReadImpl]jsonServiceRequest).accept([CtTypeAccessImpl]FhirMediaTypes.JSON).go();
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]response, [CtInvocationImpl]isBadRequest());
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]response.getContentAsString(), [CtInvocationImpl]Matchers.containsStringIgnoringCase([CtLiteralImpl]"body must contain an ID element for update"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void updateServiceRequest_shouldErrorForIdMissMatch() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonServiceRequest;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getClassLoader().getResourceAsStream([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.JSON_UPDATE_SERVICE_REQUEST_WRONG_ID_PATH)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]is);
            [CtAssignmentImpl][CtVariableWriteImpl]jsonServiceRequest = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.IOUtils.toString([CtVariableReadImpl]is, [CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]put([CtBinaryOperatorImpl][CtLiteralImpl]"/ServiceRequest/" + [CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.WRONG_SERVICE_REQUEST_UUID).jsonContent([CtVariableReadImpl]jsonServiceRequest).accept([CtTypeAccessImpl]FhirMediaTypes.JSON).go();
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]response, [CtInvocationImpl]isBadRequest());
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]response.getContentAsString(), [CtInvocationImpl]Matchers.containsStringIgnoringCase([CtLiteralImpl]"body must contain an ID element which matches the request URL"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteServiceRequest_shouldDeleteServiceRequest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.OperationOutcome retVal = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.r4.model.OperationOutcome();
        [CtInvocationImpl][CtVariableReadImpl]retVal.setId([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]retVal.getText().setDivAsString([CtLiteralImpl]"Deleted successfully");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest serviceRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.r4.model.ServiceRequest();
        [CtInvocationImpl][CtVariableReadImpl]serviceRequest.setId([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]service.delete([CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID)).thenReturn([CtVariableReadImpl]serviceRequest);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]delete([CtBinaryOperatorImpl][CtLiteralImpl]"/ServiceRequest/" + [CtFieldReadImpl]org.openmrs.module.fhir2.providers.r4.ServiceRequestFhirResourceProviderWebTest.SERVICE_REQUEST_UUID).accept([CtTypeAccessImpl]FhirMediaTypes.JSON).go();
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]response, [CtInvocationImpl]isOk());
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]response.getContentType(), [CtInvocationImpl]Matchers.equalTo([CtInvocationImpl][CtTypeAccessImpl]FhirMediaTypes.JSON.toString()));
    }
}
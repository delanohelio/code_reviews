[CompilationUnitImpl][CtJavaDocImpl]/**
 * Licensed to The Apereo Foundation under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 *
 * The Apereo Foundation licenses this file to you under the Educational
 * Community License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at:
 *
 *   http://opensource.org/licenses/ecl2.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
[CtPackageDeclarationImpl]package org.opencastproject.adopterstatistics.registration;
[CtUnresolvedImport]import static com.entwinemedia.fn.data.json.Jsons.f;
[CtUnresolvedImport]import org.opencastproject.util.doc.rest.RestQuery;
[CtUnresolvedImport]import com.google.gson.reflect.TypeToken;
[CtUnresolvedImport]import javax.ws.rs.Produces;
[CtUnresolvedImport]import javax.ws.rs.GET;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import javax.ws.rs.Path;
[CtUnresolvedImport]import org.opencastproject.index.service.util.RestUtils;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static com.entwinemedia.fn.data.json.Jsons.v;
[CtUnresolvedImport]import javax.ws.rs.core.MediaType;
[CtUnresolvedImport]import javax.ws.rs.Consumes;
[CtUnresolvedImport]import com.google.gson.Gson;
[CtUnresolvedImport]import org.opencastproject.util.doc.rest.RestService;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.opencastproject.util.doc.rest.RestResponse;
[CtUnresolvedImport]import javax.ws.rs.POST;
[CtUnresolvedImport]import com.entwinemedia.fn.data.json.JValue;
[CtUnresolvedImport]import com.entwinemedia.fn.data.json.Jsons;
[CtUnresolvedImport]import javax.servlet.http.HttpServletResponse;
[CtImportImpl]import java.util.Date;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtImportImpl]import java.lang.reflect.Type;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_OK;
[CtUnresolvedImport]import com.entwinemedia.fn.data.json.Field;
[CtUnresolvedImport]import static com.entwinemedia.fn.data.json.Jsons.obj;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
[CtClassImpl][CtJavaDocImpl]/**
 * The REST endpoint for the adopter statistics service
 */
[CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/")
[CtAnnotationImpl]@org.opencastproject.util.doc.rest.RestService(name = [CtLiteralImpl]"registrationController", title = [CtLiteralImpl]"Adopter Statistics Registration Service Endpoint", abstractText = [CtLiteralImpl]"Rest Endpoint for the registration form.", notes = [CtNewArrayImpl]{ [CtLiteralImpl]"Provides operations regarding the adopter registration form" })
public class Controller {
    [CtFieldImpl][CtJavaDocImpl]/**
     * The logger
     */
    private static final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.opencastproject.adopterstatistics.registration.Controller.class);

    [CtFieldImpl][CtJavaDocImpl]/**
     * The rest docs
     */
    protected [CtTypeReferenceImpl]java.lang.String docs;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The service that provides methods for the registration
     */
    protected [CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Service registrationService;

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRegistrationService([CtParameterImpl][CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Service registrationService) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.registrationService = [CtVariableReadImpl]registrationService;
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.reflect.Type stringMapType = [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gson.reflect.TypeToken<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {}.getType();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.google.gson.Gson gson = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.Gson();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.text.SimpleDateFormat jsonDateFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy/MM/dd");

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"registration")
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtFieldReadImpl]javax.ws.rs.core.MediaType.APPLICATION_JSON)
    [CtAnnotationImpl]@org.opencastproject.util.doc.rest.RestQuery(name = [CtLiteralImpl]"getregistrationform", description = [CtLiteralImpl]"GETs the form data for the current logged in user", reponses = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.opencastproject.util.doc.rest.RestResponse(description = [CtLiteralImpl]"Successful retrieved form data.", responseCode = [CtFieldReadImpl]javax.servlet.http.HttpServletResponse.SC_OK), [CtAnnotationImpl]@org.opencastproject.util.doc.rest.RestResponse(description = [CtLiteralImpl]"The underlying service could not output something.", responseCode = [CtFieldReadImpl]javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR) }, returnDescription = [CtLiteralImpl]"GETs the form data for a specific user.")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getRegistrationForm() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.opencastproject.adopterstatistics.registration.Controller.logger.info([CtLiteralImpl]"Retrieving statistics registration form for logged in user");
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.opencastproject.index.service.util.RestUtils.okJson([CtInvocationImpl]formToJson([CtInvocationImpl][CtFieldReadImpl]registrationService.retrieveFormData()));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"registration")
    [CtAnnotationImpl]@javax.ws.rs.Consumes([CtNewArrayImpl]{ [CtFieldReadImpl]javax.ws.rs.core.MediaType.TEXT_PLAIN, [CtFieldReadImpl]javax.ws.rs.core.MediaType.APPLICATION_JSON })
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtFieldReadImpl]javax.ws.rs.core.MediaType.APPLICATION_JSON)
    [CtAnnotationImpl]@org.opencastproject.util.doc.rest.RestQuery(name = [CtLiteralImpl]"saveregistrationform", description = [CtLiteralImpl]"Saves the adopter statistics registration form", returnDescription = [CtLiteralImpl]"Status", reponses = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.opencastproject.util.doc.rest.RestResponse(responseCode = [CtFieldReadImpl]SC_OK, description = [CtLiteralImpl]"Theme created"), [CtAnnotationImpl]@org.opencastproject.util.doc.rest.RestResponse(responseCode = [CtFieldReadImpl]SC_BAD_REQUEST, description = [CtLiteralImpl]"The theme references a non-existing file") })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response register([CtParameterImpl][CtTypeReferenceImpl]java.lang.String data) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> dataMap = [CtInvocationImpl][CtFieldReadImpl]org.opencastproject.adopterstatistics.registration.Controller.gson.fromJson([CtVariableReadImpl]data, [CtFieldReadImpl]org.opencastproject.adopterstatistics.registration.Controller.stringMapType);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Form f = [CtInvocationImpl]jsonToForm([CtVariableReadImpl]dataMap);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]registrationService.saveFormData([CtVariableReadImpl]f);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.serverError().build();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok().build();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtFieldReadImpl]javax.ws.rs.core.MediaType.TEXT_HTML)
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"docs")
    public [CtTypeReferenceImpl]java.lang.String getDocs() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]docs;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Constructs a form object from a map
     *
     * @param dataMap
     * 		Form data as map
     * @return The form object
     */
    private [CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Form jsonToForm([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> dataMap) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Form f = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Form();
        [CtInvocationImpl][CtVariableReadImpl]f.setOrganisationName([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"organisationName"));
        [CtInvocationImpl][CtVariableReadImpl]f.setDepartmentName([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"departmentName"));
        [CtInvocationImpl][CtVariableReadImpl]f.setFirstName([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"firstName"));
        [CtInvocationImpl][CtVariableReadImpl]f.setLastName([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"lastName"));
        [CtInvocationImpl][CtVariableReadImpl]f.setEmail([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"email"));
        [CtInvocationImpl][CtVariableReadImpl]f.setCountry([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"country"));
        [CtInvocationImpl][CtVariableReadImpl]f.setPostalCode([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"postalCode"));
        [CtInvocationImpl][CtVariableReadImpl]f.setCity([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"city"));
        [CtInvocationImpl][CtVariableReadImpl]f.setStreet([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"street"));
        [CtInvocationImpl][CtVariableReadImpl]f.setStreetNo([CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"streetNo"));
        [CtInvocationImpl][CtVariableReadImpl]f.setDateModified([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String contactMe = [CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"contactMe");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]contactMe != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]f.setContactMe([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtVariableReadImpl]contactMe));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String allowStatistics = [CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"allowsStatistics");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allowStatistics != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]f.setAllowsStatistics([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtVariableReadImpl]allowStatistics));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String allowErrorReports = [CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"allowsErrorReports");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allowErrorReports != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]f.setAllowsErrorReports([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtVariableReadImpl]allowErrorReports));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String allowTechData = [CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"allowsTechData");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allowTechData != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]f.setAllowsTechData([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtVariableReadImpl]allowTechData));
        }
        [CtReturnImpl]return [CtVariableReadImpl]f;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The JSON representation of the form.
     */
    private [CtTypeReferenceImpl]com.entwinemedia.fn.data.json.JValue formToJson([CtParameterImpl][CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.IForm iform) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Form form = [CtVariableReadImpl](([CtTypeReferenceImpl]org.opencastproject.adopterstatistics.registration.Form) (iform));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.entwinemedia.fn.data.json.Field> fields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.entwinemedia.fn.data.json.Field>();
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"organisationName", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getOrganisationName(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"departmentName", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getDepartmentName(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"firstName", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getFirstName(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"lastName", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getLastName(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"email", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getEmail(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"country", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getCountry(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"postalCode", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getPostalCode(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"city", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getCity(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"street", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getStreet(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"streetNo", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.getStreetNo(), [CtTypeAccessImpl]Jsons.BLANK)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"contactMe", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.isContactMe(), [CtTypeAccessImpl]Jsons.FALSE)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"allowsStatistics", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.isAllowsStatistics(), [CtTypeAccessImpl]Jsons.FALSE)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"allowsErrorReports", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.isAllowsErrorReports(), [CtTypeAccessImpl]Jsons.FALSE)));
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"allowsTechData", [CtInvocationImpl]v([CtInvocationImpl][CtVariableReadImpl]form.isAllowsTechData(), [CtTypeAccessImpl]Jsons.FALSE)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]form.getDateModified() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lastModified = [CtInvocationImpl][CtFieldReadImpl]org.opencastproject.adopterstatistics.registration.Controller.jsonDateFormat.format([CtInvocationImpl][CtVariableReadImpl]form.getDateModified());
            [CtInvocationImpl][CtVariableReadImpl]fields.add([CtInvocationImpl]f([CtLiteralImpl]"lastModified", [CtInvocationImpl]v([CtVariableReadImpl]lastModified, [CtTypeAccessImpl]Jsons.BLANK)));
        }
        [CtReturnImpl]return [CtInvocationImpl]obj([CtVariableReadImpl]fields);
    }
}
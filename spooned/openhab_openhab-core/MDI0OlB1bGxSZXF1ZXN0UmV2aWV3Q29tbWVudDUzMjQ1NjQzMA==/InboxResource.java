[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
[CtPackageDeclarationImpl]package org.openhab.core.io.rest.core.internal.discovery;
[CtUnresolvedImport]import org.openhab.core.config.discovery.dto.DiscoveryResultDTO;
[CtUnresolvedImport]import javax.ws.rs.PathParam;
[CtUnresolvedImport]import javax.ws.rs.Produces;
[CtUnresolvedImport]import javax.ws.rs.GET;
[CtUnresolvedImport]import org.openhab.core.io.rest.RESTResource;
[CtUnresolvedImport]import javax.ws.rs.Path;
[CtUnresolvedImport]import org.openhab.core.io.rest.RESTConstants;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import io.swagger.v3.oas.annotations.media.Content;
[CtUnresolvedImport]import javax.ws.rs.core.MediaType;
[CtUnresolvedImport]import org.openhab.core.thing.Thing;
[CtUnresolvedImport]import org.osgi.service.component.annotations.Component;
[CtUnresolvedImport]import io.swagger.v3.oas.annotations.Operation;
[CtUnresolvedImport]import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
[CtUnresolvedImport]import javax.ws.rs.Consumes;
[CtImportImpl]import javax.annotation.security.RolesAllowed;
[CtUnresolvedImport]import org.eclipse.jdt.annotation.Nullable;
[CtUnresolvedImport]import io.swagger.v3.oas.annotations.responses.ApiResponse;
[CtUnresolvedImport]import javax.ws.rs.HeaderParam;
[CtUnresolvedImport]import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
[CtUnresolvedImport]import org.osgi.service.component.annotations.Activate;
[CtUnresolvedImport]import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import javax.ws.rs.core.Response.Status;
[CtUnresolvedImport]import org.openhab.core.io.rest.JSONResponse;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import javax.ws.rs.DELETE;
[CtUnresolvedImport]import io.swagger.v3.oas.annotations.security.SecurityRequirement;
[CtUnresolvedImport]import io.swagger.v3.oas.annotations.media.Schema;
[CtUnresolvedImport]import javax.ws.rs.POST;
[CtUnresolvedImport]import org.eclipse.jdt.annotation.NonNullByDefault;
[CtUnresolvedImport]import org.openhab.core.thing.ThingUID;
[CtUnresolvedImport]import org.openhab.core.io.rest.Stream2JSONInputStream;
[CtUnresolvedImport]import io.swagger.v3.oas.annotations.Parameter;
[CtUnresolvedImport]import org.openhab.core.config.discovery.dto.DiscoveryResultDTOMapper;
[CtUnresolvedImport]import javax.ws.rs.core.HttpHeaders;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import org.openhab.core.auth.Role;
[CtUnresolvedImport]import org.openhab.core.config.discovery.DiscoveryResultFlag;
[CtUnresolvedImport]import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
[CtUnresolvedImport]import io.swagger.v3.oas.annotations.tags.Tag;
[CtUnresolvedImport]import org.openhab.core.config.discovery.inbox.Inbox;
[CtUnresolvedImport]import org.osgi.service.component.annotations.Reference;
[CtUnresolvedImport]import org.osgi.service.jaxrs.whiteboard.propertytypes.JSONRequired;
[CtClassImpl][CtJavaDocImpl]/**
 * This class acts as a REST resource for the inbox and is registered with the
 * Jersey servlet.
 *
 * @author Dennis Nobel - Initial contribution
 * @author Kai Kreuzer - refactored for using the OSGi JAX-RS connector and
removed ThingSetupManager
 * @author Yordan Zhelev - Added Swagger annotations
 * @author Chris Jackson - Updated to use JSONResponse. Fixed null response from
approve. Improved error reporting.
 * @author Franck Dechavanne - Added DTOs to ApiResponses
 * @author Markus Rathgeb - Migrated to JAX-RS Whiteboard Specification
 * @author Wouter Born - Migrated to OpenAPI annotations
 * @author Laurent Garnier - Added optional parameter newThingId to approve API
 */
[CtAnnotationImpl]@org.osgi.service.component.annotations.Component(service = [CtNewArrayImpl]{ [CtFieldReadImpl]org.openhab.core.io.rest.RESTResource.class, [CtFieldReadImpl]org.openhab.core.io.rest.core.internal.discovery.InboxResource.class })
[CtAnnotationImpl]@org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource
[CtAnnotationImpl]@org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName([CtFieldReadImpl][CtTypeAccessImpl]org.openhab.core.io.rest.core.internal.discovery.InboxResource.[CtFieldReferenceImpl]PATH_INBOX)
[CtAnnotationImpl]@org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"(" + [CtFieldReadImpl]org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_NAME) + [CtLiteralImpl]"=") + [CtFieldReadImpl]org.openhab.core.io.rest.RESTConstants.JAX_RS_NAME) + [CtLiteralImpl]")")
[CtAnnotationImpl]@org.osgi.service.jaxrs.whiteboard.propertytypes.JSONRequired
[CtAnnotationImpl]@javax.ws.rs.Path([CtFieldReadImpl][CtTypeAccessImpl]org.openhab.core.io.rest.core.internal.discovery.InboxResource.[CtFieldReferenceImpl]PATH_INBOX)
[CtAnnotationImpl]@javax.annotation.security.RolesAllowed([CtNewArrayImpl]{ [CtFieldReadImpl]org.openhab.core.auth.Role.ADMIN })
[CtAnnotationImpl]@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = [CtLiteralImpl]"oauth2", scopes = [CtNewArrayImpl]{ [CtLiteralImpl]"admin" })
[CtAnnotationImpl]@io.swagger.v3.oas.annotations.tags.Tag(name = [CtFieldReadImpl][CtTypeAccessImpl]org.openhab.core.io.rest.core.internal.discovery.InboxResource.[CtFieldReferenceImpl]PATH_INBOX)
[CtAnnotationImpl]@org.eclipse.jdt.annotation.NonNullByDefault
public class InboxResource implements [CtTypeReferenceImpl]org.openhab.core.io.rest.RESTResource {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.openhab.core.io.rest.core.internal.discovery.InboxResource.class);

    [CtFieldImpl][CtJavaDocImpl]/**
     * The URI path to this resource
     */
    public static final [CtTypeReferenceImpl]java.lang.String PATH_INBOX = [CtLiteralImpl]"inbox";

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.openhab.core.config.discovery.inbox.Inbox inbox;

    [CtConstructorImpl][CtAnnotationImpl]@org.osgi.service.component.annotations.Activate
    public InboxResource([CtParameterImpl][CtAnnotationImpl]@org.osgi.service.component.annotations.Reference
    final [CtTypeReferenceImpl]org.openhab.core.config.discovery.inbox.Inbox inbox) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inbox = [CtVariableReadImpl]inbox;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/{thingUID}/approve")
    [CtAnnotationImpl]@javax.ws.rs.Consumes([CtFieldReadImpl]javax.ws.rs.core.MediaType.TEXT_PLAIN)
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Operation(operationId = [CtLiteralImpl]"aproveInboxItemById", summary = [CtLiteralImpl]"Approves the discovery result by adding the thing to the registry.", responses = [CtNewArrayImpl]{ [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"200", description = [CtLiteralImpl]"OK"), [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"404", description = [CtLiteralImpl]"Thing unable to be approved."), [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"409", description = [CtLiteralImpl]"No binding found that supports this thing.") })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response approve([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.HeaderParam([CtFieldReadImpl]javax.ws.rs.core.HttpHeaders.ACCEPT_LANGUAGE)
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Parameter(description = [CtLiteralImpl]"language")
    [CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String language, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"thingUID")
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Parameter(description = [CtLiteralImpl]"thingUID")
    [CtTypeReferenceImpl]java.lang.String thingUID, [CtParameterImpl][CtAnnotationImpl]@io.swagger.v3.oas.annotations.Parameter(description = [CtLiteralImpl]"thing label")
    [CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String label, [CtParameterImpl][CtAnnotationImpl]@io.swagger.v3.oas.annotations.Parameter(description = [CtLiteralImpl]"new thing ID")
    [CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String newThingId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openhab.core.thing.ThingUID thingUIDObject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openhab.core.thing.ThingUID([CtVariableReadImpl]thingUID);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String notEmptyLabel = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]label != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]label.isEmpty())) ? [CtVariableReadImpl]label : [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String notEmptyNewThingId = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]newThingId != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]newThingId.isEmpty())) ? [CtVariableReadImpl]newThingId : [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openhab.core.thing.Thing thing = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]thing = [CtInvocationImpl][CtFieldReadImpl]inbox.approve([CtVariableReadImpl]thingUIDObject, [CtVariableReadImpl]notEmptyLabel, [CtVariableReadImpl]notEmptyNewThingId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.error([CtLiteralImpl]"Thing {} unable to be approved: {}", [CtVariableReadImpl]thingUID, [CtInvocationImpl][CtVariableReadImpl]e.getLocalizedMessage());
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openhab.core.io.rest.JSONResponse.createErrorResponse([CtTypeAccessImpl]Status.NOT_FOUND, [CtLiteralImpl]"Thing unable to be approved.");
        }
        [CtIfImpl][CtCommentImpl]// inbox.approve returns null if no handler is found that supports this thing
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]thing == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openhab.core.io.rest.JSONResponse.createErrorResponse([CtTypeAccessImpl]Status.CONFLICT, [CtLiteralImpl]"No binding found that can create the thing");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok([CtLiteralImpl]null, [CtTypeAccessImpl]MediaType.TEXT_PLAIN).build();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/{thingUID}")
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Operation(operationId = [CtLiteralImpl]"removeItemFromInbox", summary = [CtLiteralImpl]"Removes the discovery result from the inbox.", responses = [CtNewArrayImpl]{ [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"200", description = [CtLiteralImpl]"OK"), [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"404", description = [CtLiteralImpl]"Discovery result not found in the inbox.") })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response delete([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"thingUID")
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Parameter(description = [CtLiteralImpl]"thingUID")
    [CtTypeReferenceImpl]java.lang.String thingUID) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]inbox.remove([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openhab.core.thing.ThingUID([CtVariableReadImpl]thingUID))) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok([CtLiteralImpl]null, [CtTypeAccessImpl]MediaType.TEXT_PLAIN).build();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.openhab.core.io.rest.JSONResponse.createErrorResponse([CtTypeAccessImpl]Status.NOT_FOUND, [CtLiteralImpl]"Thing not found in inbox");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtNewArrayImpl]{ [CtFieldReadImpl]javax.ws.rs.core.MediaType.APPLICATION_JSON })
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Operation(operationId = [CtLiteralImpl]"getDiscoveredInboxItems", summary = [CtLiteralImpl]"Get all discovered things.", responses = [CtNewArrayImpl]{ [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"200", description = [CtLiteralImpl]"OK", content = [CtAnnotationImpl]@io.swagger.v3.oas.annotations.media.Content(schema = [CtAnnotationImpl]@io.swagger.v3.oas.annotations.media.Schema(implementation = [CtFieldReadImpl]org.openhab.core.config.discovery.dto.DiscoveryResultDTO.class))) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getAll() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.openhab.core.config.discovery.dto.DiscoveryResultDTO> discoveryStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]inbox.getAll().stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]DiscoveryResultDTOMapper::map);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openhab.core.io.rest.Stream2JSONInputStream([CtVariableReadImpl]discoveryStream)).build();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/{thingUID}/ignore")
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Operation(operationId = [CtLiteralImpl]"flagInboxItemAsIgnored", summary = [CtLiteralImpl]"Flags a discovery result as ignored for further processing.", responses = [CtNewArrayImpl]{ [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"200", description = [CtLiteralImpl]"OK") })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response ignore([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"thingUID")
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Parameter(description = [CtLiteralImpl]"thingUID")
    [CtTypeReferenceImpl]java.lang.String thingUID) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]inbox.setFlag([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openhab.core.thing.ThingUID([CtVariableReadImpl]thingUID), [CtTypeAccessImpl]DiscoveryResultFlag.IGNORED);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok([CtLiteralImpl]null, [CtTypeAccessImpl]MediaType.TEXT_PLAIN).build();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/{thingUID}/unignore")
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Operation(operationId = [CtLiteralImpl]"removeIgnoreFlagOnInboxItem", summary = [CtLiteralImpl]"Removes ignore flag from a discovery result.", responses = [CtNewArrayImpl]{ [CtAnnotationImpl]@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = [CtLiteralImpl]"200", description = [CtLiteralImpl]"OK") })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response unignore([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"thingUID")
    [CtAnnotationImpl]@io.swagger.v3.oas.annotations.Parameter(description = [CtLiteralImpl]"thingUID")
    [CtTypeReferenceImpl]java.lang.String thingUID) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]inbox.setFlag([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openhab.core.thing.ThingUID([CtVariableReadImpl]thingUID), [CtTypeAccessImpl]DiscoveryResultFlag.NEW);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok([CtLiteralImpl]null, [CtTypeAccessImpl]MediaType.TEXT_PLAIN).build();
    }
}
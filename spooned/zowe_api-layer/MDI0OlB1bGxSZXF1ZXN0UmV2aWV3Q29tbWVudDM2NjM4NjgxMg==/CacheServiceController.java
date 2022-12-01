[CompilationUnitImpl][CtCommentImpl]/* This program and the accompanying materials are made available under the terms of the
Eclipse Public License v2.0 which accompanies this distribution, and is available at
https://www.eclipse.org/legal/epl-v20.html

SPDX-License-Identifier: EPL-2.0

Copyright Contributors to the Zowe Project.
 */
[CtPackageDeclarationImpl]package com.ca.mfaas.gateway.controllers;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.PathVariable;
[CtUnresolvedImport]import com.ca.mfaas.gateway.security.service.ServiceCacheEvict;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.RequestMapping;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import lombok.AllArgsConstructor;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.DeleteMapping;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.RestController;
[CtClassImpl][CtJavaDocImpl]/**
 * This controller allows control the caches about services. The main purpose is to evict cached data
 * about services when a update happened in discovery service. Discovery service notifies about any
 * change to be sure that cache on gateway is still valid.
 */
[CtAnnotationImpl]@lombok.AllArgsConstructor
[CtAnnotationImpl]@org.springframework.web.bind.annotation.RestController
[CtAnnotationImpl]@org.springframework.web.bind.annotation.RequestMapping([CtLiteralImpl]"/cache/services")
public class CacheServiceController {
    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.ca.mfaas.gateway.security.service.ServiceCacheEvict> toEvict;

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.web.bind.annotation.DeleteMapping(path = [CtLiteralImpl]"")
    public [CtTypeReferenceImpl]void evictAll() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]toEvict.forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ServiceCacheEvict::evictCacheAllService);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.web.bind.annotation.DeleteMapping(path = [CtLiteralImpl]"/{serviceId}")
    public [CtTypeReferenceImpl]void evict([CtParameterImpl][CtAnnotationImpl]@org.springframework.web.bind.annotation.PathVariable([CtLiteralImpl]"serviceId")
    [CtTypeReferenceImpl]java.lang.String serviceId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]toEvict.forEach([CtLambdaImpl]([CtParameterImpl] s) -> [CtInvocationImpl][CtVariableReadImpl]s.evictCacheService([CtVariableReadImpl]serviceId));
    }
}
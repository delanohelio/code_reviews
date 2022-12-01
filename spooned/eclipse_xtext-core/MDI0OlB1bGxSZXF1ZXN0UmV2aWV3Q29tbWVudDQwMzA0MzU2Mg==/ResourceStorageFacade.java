[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2014, 2020 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
[CtPackageDeclarationImpl]package org.eclipse.xtext.resource.persistence;
[CtUnresolvedImport]import org.eclipse.xtext.generator.IContextualOutputConfigurationProvider;
[CtUnresolvedImport]import static org.eclipse.xtext.xbase.lib.IterableExtensions.*;
[CtUnresolvedImport]import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
[CtImportImpl]import java.io.OutputStream;
[CtUnresolvedImport]import org.eclipse.emf.common.util.URI;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import com.google.inject.Inject;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.io.ByteArrayOutputStream;
[CtUnresolvedImport]import org.eclipse.xtend.lib.annotations.Accessors;
[CtUnresolvedImport]import org.apache.log4j.Logger;
[CtUnresolvedImport]import com.google.inject.Provider;
[CtUnresolvedImport]import org.eclipse.xtext.util.RuntimeIOException;
[CtImportImpl]import java.io.ByteArrayInputStream;
[CtUnresolvedImport]import org.eclipse.xtext.generator.IFileSystemAccessExtension3;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.Pure;
[CtUnresolvedImport]import org.eclipse.xtext.generator.AbstractFileSystemAccess2;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Sven Efftinge - Initial contribution and API
 */
public class ResourceStorageFacade implements [CtTypeReferenceImpl]IResourceStorageFacade {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.log4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.apache.log4j.Logger.getLogger([CtFieldReadImpl]org.eclipse.xtext.resource.persistence.ResourceStorageFacade.class);

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.generator.IContextualOutputConfigurationProvider outputConfigurationProvider;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]org.eclipse.xtext.generator.AbstractFileSystemAccess2> fileSystemAccessProvider;

    [CtFieldImpl][CtAnnotationImpl]@org.eclipse.xtend.lib.annotations.Accessors
    private [CtTypeReferenceImpl]boolean storeNodeModel = [CtLiteralImpl]false;

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return whether the given resource should be loaded from stored resource state
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean shouldLoadFromStorage([CtParameterImpl][CtTypeReferenceImpl]StorageAwareResource resource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceLevelURIsAdapter adapter = [CtInvocationImpl][CtTypeAccessImpl]SourceLevelURIsAdapter.findInstalledAdapter([CtInvocationImpl][CtVariableReadImpl]resource.getResourceSet());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]adapter == [CtLiteralImpl]null) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]adapter.getSourceLevelURIs().contains([CtInvocationImpl][CtVariableReadImpl]resource.getURI()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtReturnImpl]return [CtInvocationImpl]doesStorageExist([CtVariableReadImpl]resource);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Finds or creates a ResourceStorageLoadable for the given resource. Clients should first call
     * shouldLoadFromStorage to check whether there exists a storage version of the given resource.
     *
     * @return an IResourceStorageLoadable
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.xtext.resource.persistence.ResourceStorageLoadable getOrCreateResourceStorageLoadable([CtParameterImpl][CtTypeReferenceImpl]StorageAwareResource resource) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]ResourceStorageProviderAdapter stateProvider = [CtInvocationImpl]head([CtInvocationImpl]filter([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getResourceSet().eAdapters(), [CtFieldReadImpl]org.eclipse.xtext.resource.persistence.ResourceStorageProviderAdapter.class));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stateProvider != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]ResourceStorageLoadable loadable = [CtInvocationImpl][CtVariableReadImpl]stateProvider.getResourceStorageLoadable([CtVariableReadImpl]resource);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]loadable != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtReturnImpl]return [CtVariableReadImpl]loadable;

            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getResourceSet().getURIConverter().exists([CtInvocationImpl]getBinaryStorageURI([CtInvocationImpl][CtVariableReadImpl]resource.getURI()), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap())) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]createResourceStorageLoadable([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getResourceSet().getURIConverter().createInputStream([CtInvocationImpl]getBinaryStorageURI([CtInvocationImpl][CtVariableReadImpl]resource.getURI())));
            }
            [CtReturnImpl]return [CtInvocationImpl]createResourceStorageLoadable([CtInvocationImpl][CtInvocationImpl]getFileSystemAccess([CtVariableReadImpl]resource).readBinaryFile([CtInvocationImpl]computeOutputPath([CtVariableReadImpl]resource)));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.util.RuntimeIOException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void saveResource([CtParameterImpl][CtTypeReferenceImpl]StorageAwareResource resource, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.generator.IFileSystemAccessExtension3 fsa) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.persistence.ResourceStorageFacade.MyByteArrayOutputStream bout = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.resource.persistence.ResourceStorageFacade.MyByteArrayOutputStream();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl]createResourceStorageWritable([CtVariableReadImpl]bout).writeResource([CtVariableReadImpl]resource);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.eclipse.xtext.resource.persistence.ResourceStorageFacade.[CtFieldReferenceImpl]LOG.warn([CtBinaryOperatorImpl][CtLiteralImpl]"Cannot write storage for " + [CtInvocationImpl][CtVariableReadImpl]resource.getURI(), [CtVariableReadImpl]e);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]fsa.generateFile([CtInvocationImpl]computeOutputPath([CtVariableReadImpl]resource), [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtVariableReadImpl]bout.toByteArray(), [CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]bout.length()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.xtext.resource.persistence.ResourceStorageLoadable createResourceStorageLoadable([CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream in) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ResourceStorageLoadable([CtVariableReadImpl]in, [CtInvocationImpl]isStoreNodeModel());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.xtext.resource.persistence.ResourceStorageWritable createResourceStorageWritable([CtParameterImpl][CtTypeReferenceImpl]java.io.OutputStream out) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ResourceStorageWritable([CtVariableReadImpl]out, [CtInvocationImpl]isStoreNodeModel());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return whether a stored resource state exists for the given resource
     */
    protected [CtTypeReferenceImpl]boolean doesStorageExist([CtParameterImpl][CtTypeReferenceImpl]StorageAwareResource resource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ResourceStorageProviderAdapter stateProvider = [CtInvocationImpl]head([CtInvocationImpl]filter([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getResourceSet().eAdapters(), [CtFieldReadImpl]org.eclipse.xtext.resource.persistence.ResourceStorageProviderAdapter.class));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]stateProvider != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]stateProvider.getResourceStorageLoadable([CtVariableReadImpl]resource) != [CtLiteralImpl]null))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getResourceSet().getURIConverter().exists([CtInvocationImpl]getBinaryStorageURI([CtInvocationImpl][CtVariableReadImpl]resource.getURI()), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getURI().isArchive())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri = [CtInvocationImpl][CtInvocationImpl]getFileSystemAccess([CtVariableReadImpl]resource).getURI([CtInvocationImpl]computeOutputPath([CtVariableReadImpl]resource));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]uri != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getResourceSet().getURIConverter().exists([CtVariableReadImpl]uri, [CtLiteralImpl]null);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.generator.AbstractFileSystemAccess2 getFileSystemAccess([CtParameterImpl][CtTypeReferenceImpl]StorageAwareResource resource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.generator.AbstractFileSystemAccess2 fsa = [CtInvocationImpl][CtFieldReadImpl]fileSystemAccessProvider.get();
        [CtInvocationImpl][CtVariableReadImpl]fsa.setContext([CtVariableReadImpl]resource);
        [CtInvocationImpl][CtVariableReadImpl]fsa.setOutputConfigurations([CtInvocationImpl]toMap([CtInvocationImpl][CtFieldReadImpl]outputConfigurationProvider.getOutputConfigurations([CtVariableReadImpl]resource), [CtLambdaImpl]([CtParameterImpl] it) -> [CtInvocationImpl][CtVariableReadImpl]it.getName()));
        [CtReturnImpl]return [CtVariableReadImpl]fsa;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String computeOutputPath([CtParameterImpl][CtTypeReferenceImpl]StorageAwareResource resource) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getBinaryStorageURI([CtInvocationImpl][CtVariableReadImpl]resource.getURI()).deresolve([CtInvocationImpl]getSourceContainerURI([CtVariableReadImpl]resource), [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]true).path();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.emf.common.util.URI getSourceContainerURI([CtParameterImpl][CtTypeReferenceImpl]StorageAwareResource resource) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getURI().trimSegments([CtLiteralImpl]1).appendSegment([CtLiteralImpl]"");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean hasStorageFor([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl().exists([CtInvocationImpl]getBinaryStorageURI([CtVariableReadImpl]uri), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.emf.common.util.URI getBinaryStorageURI([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI sourceURI) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceURI.trimSegments([CtLiteralImpl]1).appendSegment([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"." + [CtInvocationImpl][CtVariableReadImpl]sourceURI.lastSegment()) + [CtLiteralImpl]"bin");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.eclipse.xtext.xbase.lib.Pure
    public [CtTypeReferenceImpl]boolean isStoreNodeModel() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]storeNodeModel;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setStoreNodeModel([CtParameterImpl][CtTypeReferenceImpl]boolean storeNodeModel) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.storeNodeModel = [CtVariableReadImpl]storeNodeModel;
    }

    [CtClassImpl]private static class MyByteArrayOutputStream extends [CtTypeReferenceImpl]java.io.ByteArrayOutputStream {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtArrayTypeReferenceImpl]byte[] toByteArray() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]buf;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]int length() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]count;
        }
    }
}
[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2015, 2020 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
[CtPackageDeclarationImpl]package org.eclipse.xtext.junit4.build;
[CtUnresolvedImport]import com.google.common.collect.ArrayListMultimap;
[CtUnresolvedImport]import org.eclipse.xtext.build.IndexState;
[CtUnresolvedImport]import org.eclipse.xtext.resource.impl.ProjectDescription;
[CtUnresolvedImport]import com.google.common.collect.Iterables;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.Exceptions;
[CtImportImpl]import java.io.OutputStream;
[CtUnresolvedImport]import org.eclipse.emf.common.util.URI;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.eclipse.xtext.resource.IResourceServiceProvider;
[CtUnresolvedImport]import com.google.inject.Inject;
[CtUnresolvedImport]import org.eclipse.xtext.build.IncrementalBuilder;
[CtUnresolvedImport]import org.eclipse.xtext.generator.OutputConfigurationProvider;
[CtUnresolvedImport]import com.google.common.collect.Multimap;
[CtUnresolvedImport]import org.eclipse.xtext.generator.OutputConfiguration;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.junit.Before;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.Conversions;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
[CtUnresolvedImport]import com.google.common.collect.Sets;
[CtUnresolvedImport]import com.google.common.annotations.Beta;
[CtUnresolvedImport]import com.google.inject.Provider;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.CollectionLiterals;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.IterableExtensions;
[CtUnresolvedImport]import org.eclipse.xtext.validation.Issue;
[CtUnresolvedImport]import org.eclipse.xtext.Constants;
[CtUnresolvedImport]import org.eclipse.xtext.junit4.util.InMemoryURIHandler;
[CtUnresolvedImport]import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
[CtUnresolvedImport]import com.google.inject.name.Named;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.Pair;
[CtUnresolvedImport]import org.eclipse.xtext.build.BuildRequest;
[CtUnresolvedImport]import org.eclipse.xtext.resource.XtextResourceSet;
[CtUnresolvedImport]import org.eclipse.xtext.generator.OutputConfigurationAdapter;
[CtClassImpl][CtJavaDocImpl]/**
 * Abstract base class for testing languages in the incremental builder.
 *
 * @since 2.9
 * @noreference  * @noimplement  * @deprecated Use org.eclipse.xtext.testing.builder.AbstractIncrementalBuilderTest instead.
 */
[CtAnnotationImpl]@com.google.common.annotations.Beta
[CtAnnotationImpl]@java.lang.Deprecated
public abstract class AbstractIncrementalBuilderTest {
    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    protected [CtTypeReferenceImpl]org.eclipse.xtext.build.IncrementalBuilder incrementalBuilder;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    protected [CtTypeReferenceImpl]org.eclipse.xtext.build.IndexState indexState;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    protected [CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]org.eclipse.xtext.resource.XtextResourceSet> resourceSetProvider;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    [CtAnnotationImpl]@com.google.inject.name.Named([CtFieldReadImpl]org.eclipse.xtext.Constants.LANGUAGE_NAME)
    private [CtTypeReferenceImpl]java.lang.String languageName;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.generator.OutputConfigurationProvider configurationProvider;

    [CtFieldImpl]protected [CtTypeReferenceImpl]com.google.common.collect.Multimap<[CtTypeReferenceImpl]org.eclipse.emf.common.util.URI, [CtTypeReferenceImpl]org.eclipse.emf.common.util.URI> generated;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.emf.common.util.URI> deleted;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.xtext.validation.Issue> issues;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.junit4.util.InMemoryURIHandler inMemoryURIHandler;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setUp() [CtBlockImpl]{
        [CtInvocationImpl]clean();
        [CtAssignmentImpl][CtFieldWriteImpl]inMemoryURIHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.junit4.util.InMemoryURIHandler();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.xtext.validation.Issue> clean() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.xtext.validation.Issue> result = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]generated = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ArrayListMultimap.create();
        [CtAssignmentImpl][CtFieldWriteImpl]deleted = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtAssignmentImpl][CtFieldWriteImpl]issues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.build.IndexState build([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.build.BuildRequest buildRequest) [CtBlockImpl]{
        [CtInvocationImpl]clean();
        [CtAssignmentImpl][CtFieldWriteImpl]indexState = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]incrementalBuilder.build([CtVariableReadImpl]buildRequest, [CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI it) -> [CtInvocationImpl][CtInvocationImpl]getLanguages().getResourceServiceProvider([CtVariableReadImpl]it)).getIndexState();
        [CtReturnImpl]return [CtFieldReadImpl]indexState;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void withOutputConfig([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.build.BuildRequest it, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.lib.Procedures.Procedure1<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]org.eclipse.xtext.generator.OutputConfiguration> init) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.generator.OutputConfiguration config = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.getFirst([CtInvocationImpl][CtFieldReadImpl]configurationProvider.getOutputConfigurations(), [CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]init.apply([CtVariableReadImpl]config);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.generator.OutputConfigurationAdapter adapter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.generator.OutputConfigurationAdapter([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.CollectionLiterals.newHashMap([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.Pair.of([CtFieldReadImpl]languageName, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableSet([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.newHashSet([CtVariableReadImpl]config))))));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]it.getResourceSet().eAdapters().add([CtVariableReadImpl]adapter);
    }

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]IResourceServiceProvider.Registry getLanguages();

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.build.BuildRequest newBuildRequest([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.lib.Procedures.Procedure1<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]org.eclipse.xtext.build.BuildRequest> init) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.build.BuildRequest result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.build.BuildRequest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ResourceDescriptionsData newIndex = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]indexState.getResourceDescriptions().copy();
        [CtInvocationImpl][CtVariableReadImpl]result.setBaseDir([CtInvocationImpl]uri([CtLiteralImpl]""));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.XtextResourceSet rs = [CtInvocationImpl][CtFieldReadImpl]resourceSetProvider.get();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rs.getURIConverter().getURIHandlers().clear();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rs.getURIConverter().getURIHandlers().add([CtFieldReadImpl]inMemoryURIHandler);
        [CtInvocationImpl][CtVariableReadImpl]rs.setClasspathURIContext([CtInvocationImpl][CtFieldReadImpl]org.eclipse.xtext.junit4.build.AbstractIncrementalBuilderTest.class.getClassLoader());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription projectDescription = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription();
        [CtInvocationImpl][CtVariableReadImpl]projectDescription.setName([CtLiteralImpl]"test-project");
        [CtInvocationImpl][CtVariableReadImpl]projectDescription.attachToEmfObject([CtVariableReadImpl]rs);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions index = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap(), [CtVariableReadImpl]rs);
        [CtInvocationImpl][CtVariableReadImpl]index.setContainer([CtInvocationImpl][CtVariableReadImpl]projectDescription.getName(), [CtVariableReadImpl]newIndex);
        [CtInvocationImpl][CtVariableReadImpl]result.setResourceSet([CtVariableReadImpl]rs);
        [CtInvocationImpl][CtVariableReadImpl]result.setDirtyFiles([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>()));
        [CtInvocationImpl][CtVariableReadImpl]result.setDeletedFiles([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>()));
        [CtInvocationImpl][CtVariableReadImpl]result.setAfterValidate([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri,[CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]org.eclipse.xtext.validation.Issue> issues) -> [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.<[CtTypeReferenceImpl]org.eclipse.xtext.validation.Issue>addAll([CtFieldReadImpl][CtThisAccessImpl]this.issues, [CtVariableReadImpl]issues);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty([CtVariableReadImpl]issues);
        });
        [CtInvocationImpl][CtVariableReadImpl]result.setAfterDeleteFile([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri) -> [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]deleted.add([CtVariableReadImpl]uri);
        });
        [CtInvocationImpl][CtVariableReadImpl]result.setAfterGenerateFile([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI source,[CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI target) -> [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]generated.put([CtVariableReadImpl]source, [CtVariableReadImpl]target);
        });
        [CtInvocationImpl][CtVariableReadImpl]result.setState([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.build.IndexState([CtVariableReadImpl]newIndex, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]indexState.getFileMappings().copy()));
        [CtInvocationImpl][CtVariableReadImpl]init.apply([CtVariableReadImpl]result);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.emf.common.util.URI delete([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]inMemoryURIHandler.delete([CtVariableReadImpl]uri, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
            [CtReturnImpl]return [CtVariableReadImpl]uri;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.Exceptions.sneakyThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.emf.common.util.URI.createURI([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]org.eclipse.xtext.junit4.util.InMemoryURIHandler.SCHEME + [CtLiteralImpl]":/") + [CtVariableReadImpl]path);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.emf.common.util.URI operator_minus([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String content) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri = [CtInvocationImpl]uri([CtVariableReadImpl]path);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.OutputStream outputStream = [CtInvocationImpl][CtFieldReadImpl]inMemoryURIHandler.createOutputStream([CtVariableReadImpl]uri, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]outputStream.write([CtInvocationImpl][CtVariableReadImpl]content.getBytes());
                [CtInvocationImpl][CtVariableReadImpl]outputStream.close();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.Exceptions.sneakyThrow([CtVariableReadImpl]e);
            }
            [CtReturnImpl]return [CtVariableReadImpl]uri;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.Exceptions.sneakyThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean containsSuffix([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.emf.common.util.URI> uris, [CtParameterImpl]java.lang.String... suffixes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> iterable = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String>) ([CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.Conversions.doWrapArray([CtVariableReadImpl]suffixes)));
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.IterableExtensions.forall([CtVariableReadImpl]iterable, [CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.lang.String suffix) -> [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.xtext.xbase.lib.IterableExtensions.exists([CtVariableReadImpl]uris, [CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI uri) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]uri.toString().endsWith([CtVariableReadImpl]suffix)));
    }
}
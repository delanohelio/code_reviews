[CompilationUnitImpl][CtCommentImpl]/* Copyright 2000-2020 Vaadin Ltd.

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
 */
[CtPackageDeclarationImpl]package com.vaadin.flow.data.provider;
[CtUnresolvedImport]import com.vaadin.flow.component.ComponentEventListener;
[CtUnresolvedImport]import com.vaadin.flow.shared.Registration;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.io.Serializable;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * Base view interface for getting information on current
 * data set of a Component.
 *
 * @param <T>
 * 		data type
 * @since  */
public interface DataView<[CtTypeParameterImpl]T> extends [CtTypeReferenceImpl]java.io.Serializable {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the full data available to the component.
     * Data will use set filters and sorting.
     *
     * @return filtered and sorted data set
     */
    [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeParameterReferenceImpl]T> getItems();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the full data size with filters if any set.
     *
     * @return filtered data size
     */
    [CtTypeReferenceImpl]int getSize();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if item is in the current data.
     * Item may be filtered out or for lazy data not in the currently loaded
     * making it un-available.
     * <p>
     * By default, {@code equals} method implementation of the item is used
     * for identity check. If a custom data provider is used,
     * then the {@link DataProvider#getId(Object)} method is used instead.
     * Item's custom identity can be set up with a
     * {@link DataView#setIdentifierProvider(IdentifierProvider)}.
     *
     * @param item
     * 		item to search for
     * @return true if item is found in the available data
     * @see #setIdentifierProvider(IdentifierProvider)
     */
    [CtTypeReferenceImpl]boolean contains([CtParameterImpl][CtTypeParameterReferenceImpl]T item);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a size change listener that is fired when the data set size changes.
     * This can happen for instance when filtering the data set.
     * <p>
     * Size change listener is bound to the component and will be retained even
     * if the data changes by setting of a new items or {@link DataProvider} to
     * component.
     *
     * @param listener
     * 		size change listener to register
     * @return registration for removing the listener
     */
    [CtTypeReferenceImpl]com.vaadin.flow.shared.Registration addSizeChangeListener([CtParameterImpl][CtTypeReferenceImpl]com.vaadin.flow.component.ComponentEventListener<[CtTypeReferenceImpl]com.vaadin.flow.data.provider.SizeChangeEvent<[CtWildcardReferenceImpl]?>> listener);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets identity provider to be used for getting item identifier and
     * compare the items using that identifier.
     *
     * @param identifierProvider
     * 		function that returns the non-null identifier for a given item
     */
    [CtTypeReferenceImpl]void setIdentifierProvider([CtParameterImpl][CtTypeReferenceImpl]com.vaadin.flow.data.provider.IdentifierProvider<[CtTypeParameterReferenceImpl]T> identifierProvider);
}
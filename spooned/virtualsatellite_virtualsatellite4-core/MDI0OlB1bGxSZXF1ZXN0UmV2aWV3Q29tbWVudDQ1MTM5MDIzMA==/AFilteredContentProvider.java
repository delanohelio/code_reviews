[CompilationUnitImpl][CtJavaDocImpl]/**
 * *****************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * *****************************************************************************
 */
[CtPackageDeclarationImpl]package de.dlr.sc.virsat.project.ui.contentProvider;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashSet;
[CtClassImpl][CtJavaDocImpl]/**
 * Abstract class for filtering content.
 */
public abstract class AFilteredContentProvider {
    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> filterElementCategoryIds;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> filterElementStructuralElementIds;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Boolean>> filterElementFunctions;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Filters the objects for the desired ids
     *
     * @param objects
     * 		the objects to be filtered
     * @param categoryIdFilters
     * 		the ids by which we filter categories and properties by
     * @param structuralElementIdFilters
     * 		the ids by which we filter structural elements by
     * @return the objects after filtering them by id
     */
    protected abstract [CtArrayTypeReferenceImpl]java.lang.Object[] filterIds([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] objects, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> categoryIdFilters, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> structuralElementIdFilters);

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> filterElementClasses;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Default constructor
     */
    public AFilteredContentProvider() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]filterElementClasses = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtAssignmentImpl][CtFieldWriteImpl]filterElementCategoryIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtAssignmentImpl][CtFieldWriteImpl]filterElementStructuralElementIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtAssignmentImpl][CtFieldWriteImpl]filterElementFunctions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method adds a class filter to the GetElement Method
     *
     * @param filterClass
     * 		The class that should explicitly be returned by the getElement method
     * @return returns the current instance to easily concatenate various filters.
     */
    public [CtTypeReferenceImpl]de.dlr.sc.virsat.project.ui.contentProvider.AFilteredContentProvider addClassFilterToGetElement([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> filterClass) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]filterElementClasses.add([CtVariableReadImpl]filterClass);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Use this method to add an ID for a specific type id of the Category and Property Concept
     *
     * @param filterId
     * 		the ID of the object that should be returned by the GetElement method
     * @return returns the current instance to easily concatenate various filters.
     */
    public [CtTypeReferenceImpl]de.dlr.sc.virsat.project.ui.contentProvider.AFilteredContentProvider addCategoryIdFilterToGetElement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String filterId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]filterElementCategoryIds.add([CtVariableReadImpl]filterId);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Use this method to add an ID for a specific type id of the Structural Element Concept
     *
     * @param filterId
     * 		the ID of the object that should be returned by the GetElement method
     * @return returns the current instance to easily concatenate various filters.
     */
    public [CtTypeReferenceImpl]de.dlr.sc.virsat.project.ui.contentProvider.AFilteredContentProvider addStructuralElementIdFilterToGetElement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String filterId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]filterElementStructuralElementIds.add([CtVariableReadImpl]filterId);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Use this method to add a filtering function
     *
     * @param filter
     * 		function that the object that should be returned by the GetElement method should satisfy
     * @return returns the current instance to easily concatenate various filters.
     */
    public [CtTypeReferenceImpl]de.dlr.sc.virsat.project.ui.contentProvider.AFilteredContentProvider addFunctionFilterToGetElement([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Boolean> filter) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]filterElementFunctions.add([CtVariableReadImpl]filter);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Use this method to filter objects for a list of filtering functions
     * Only objects that satisfy the specified filters will be returned
     *
     * @param objects
     * 		Array of input objects
     * @param filterFunctions
     * 		A set of filtering functions. all objects will be returned if list is empty
     * @return the objects that passed the filter (that have been selected by the filter)
     */
    protected [CtArrayTypeReferenceImpl]java.lang.Object[] filterFunctions([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] objects, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Boolean>> filterFunctions) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Object> filteredObjects = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtIfImpl][CtCommentImpl]// In case there are no filters specified we return all objects
        if ([CtInvocationImpl][CtVariableReadImpl]filterFunctions.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]objects;
        }
        [CtForEachImpl][CtCommentImpl]// Now filter the
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object : [CtVariableReadImpl]objects) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Boolean> filter : [CtVariableReadImpl]filterFunctions) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]filter.apply([CtVariableReadImpl]object)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]filteredObjects.add([CtVariableReadImpl]object);
                    [CtBreakImpl][CtCommentImpl]// Object is accepted therefore go to the next
                    [CtCommentImpl]// evaluate the next object
                    break;
                }
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]filteredObjects.toArray();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Use this method to filter objects for a list of given classes.
     * Only the specified filtered classes will be returned
     *
     * @param objects
     * 		Array of input objects
     * @param classFilters
     * 		A set of classes that should be accepted. all objects will be returned if list is empty
     * @return the objects that passed the filter (that have been selected by the filter)
     */
    protected [CtArrayTypeReferenceImpl]java.lang.Object[] filterClasses([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] objects, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> classFilters) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Object> filteredObjects = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtIfImpl][CtCommentImpl]// In case there are no filters specified we return all objects
        if ([CtInvocationImpl][CtVariableReadImpl]classFilters.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]objects;
        }
        [CtForEachImpl][CtCommentImpl]// Now filter the
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object : [CtVariableReadImpl]objects) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> clazz : [CtVariableReadImpl]classFilters) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]clazz.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]object.getClass())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]filteredObjects.add([CtVariableReadImpl]object);
                    [CtBreakImpl][CtCommentImpl]// Object is accepted therefore go to the next
                    [CtCommentImpl]// evaluate the next object
                    break;
                }
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]filteredObjects.toArray();
    }
}
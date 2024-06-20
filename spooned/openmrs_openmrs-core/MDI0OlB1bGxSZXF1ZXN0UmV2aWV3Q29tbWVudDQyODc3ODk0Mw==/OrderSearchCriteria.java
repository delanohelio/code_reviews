[CompilationUnitImpl][CtJavaDocImpl]/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
[CtPackageDeclarationImpl]package org.openmrs.parameter;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Date;
[CtClassImpl][CtJavaDocImpl]/**
 * The search parameter object for orders. A convenience interface for building
 * instances is provided by {@link OrderSearchCriteriaBuilder}.
 *
 * @since 2.2
 * @see OrderSearchCriteriaBuilder
 */
public class OrderSearchCriteria {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.openmrs.parameter.Patient patient;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.openmrs.parameter.CareSetting careSetting;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]Concept> concepts;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]OrderType> orderTypes;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Accession Number to match on; performs an exact match
     */
    private [CtTypeReferenceImpl]java.lang.String accessionNumber;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Accession Number to match on; performs an exact match
     */
    private [CtTypeReferenceImpl]java.lang.String orderNumber;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Matches on dateActivated that is any time on this date or less
     */
    private final [CtTypeReferenceImpl]java.util.Date activatedOnOrBeforeDate;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Matches on dateActivated that is any time on this date or more
     */
    private final [CtTypeReferenceImpl]java.util.Date activatedOnOrAfterDate;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Matches on autoExpireDate that is any time on this date or less
     */
    private final [CtTypeReferenceImpl]java.util.Date autoExpireOnOrBeforeDate;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Matches on dateStopped that is any time on this date or less
     */
    private final [CtTypeReferenceImpl]boolean isStopped;

    [CtFieldImpl][CtJavaDocImpl]/**
     * All canceled or auto expired orders before date
     */
    private final [CtTypeReferenceImpl]java.util.Date canceledOrExpiredOnOrBeforeDate;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Matches on fulfillerStatus
     */
    private final [CtTypeReferenceImpl]Order.FulfillerStatus fulfillerStatus;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Matches on orders with fulfiller_status = null
     * This parameter could work in conjunction with fulfillerStatus.
     *  If fulfillerStatus is specified then includeNullFulfillerStatus=true would include
     *  all orders where fulfillerStatus=null OR fulfillerStatus = specified value
     */
    private final [CtTypeReferenceImpl]java.lang.Boolean includeNullFulfillerStatus;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Matches on action
     */
    private final [CtTypeReferenceImpl]Order.Action action;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean includeVoided;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean excludeCanceledAndExpired;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean excludeDiscontinueOrders;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Instead of calling this constructor directly, it is recommended to use {@link OrderSearchCriteriaBuilder}.
     *
     * @param patient
     * 		the patient the order is for
     * @param careSetting
     * 		the care setting to match on
     * @param concepts
     * 		the concepts to match on; if not specified, matches on all concepts
     * @param orderTypes
     * 		the order types to match on; if not specified, matches all order types
     * @param accessionNumber
     * 		to match on; performs exact match if specified
     * @param orderNumber
     * 		to match on; performs exact match if specifed
     * @param activatedOnOrBeforeDate
     * 		orders must have dateActivated on or before this date
     * @param activatedOnOrAfterDate
     * 		orders must have dateActivated on or after this date
     * @param includeVoided
     * 		whether to include the voided orders or not
     */
    public OrderSearchCriteria([CtParameterImpl][CtTypeReferenceImpl]Patient patient, [CtParameterImpl][CtTypeReferenceImpl]CareSetting careSetting, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]Concept> concepts, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]OrderType> orderTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessionNumber, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String orderNumber, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date activatedOnOrBeforeDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date activatedOnOrAfterDate, [CtParameterImpl][CtTypeReferenceImpl]boolean isStopped, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date autoExpireOnOrBeforeDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date canceledOrExpiredOnOrBeforeDate, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Order.Action action, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Order.FulfillerStatus fulfillerStatus, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean includeNullFulfillerStatus, [CtParameterImpl][CtTypeReferenceImpl]boolean excludeCanceledAndExpired, [CtParameterImpl][CtTypeReferenceImpl]boolean excludeDiscontinueOrders, [CtParameterImpl][CtTypeReferenceImpl]boolean includeVoided) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.patient = [CtVariableReadImpl]patient;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.careSetting = [CtVariableReadImpl]careSetting;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.concepts = [CtVariableReadImpl]concepts;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.orderTypes = [CtVariableReadImpl]orderTypes;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.accessionNumber = [CtVariableReadImpl]accessionNumber;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.orderNumber = [CtVariableReadImpl]orderNumber;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activatedOnOrBeforeDate = [CtVariableReadImpl]activatedOnOrBeforeDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activatedOnOrAfterDate = [CtVariableReadImpl]activatedOnOrAfterDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isStopped = [CtVariableReadImpl]isStopped;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.autoExpireOnOrBeforeDate = [CtVariableReadImpl]autoExpireOnOrBeforeDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.canceledOrExpiredOnOrBeforeDate = [CtVariableReadImpl]canceledOrExpiredOnOrBeforeDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.action = [CtVariableReadImpl]action;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fulfillerStatus = [CtVariableReadImpl]fulfillerStatus;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeNullFulfillerStatus = [CtVariableReadImpl]includeNullFulfillerStatus;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludeCanceledAndExpired = [CtVariableReadImpl]excludeCanceledAndExpired;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludeDiscontinueOrders = [CtVariableReadImpl]excludeDiscontinueOrders;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeVoided = [CtVariableReadImpl]includeVoided;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * (Legacy constructor, before addition of Order Number and Accession Number fields)
     * Instead of calling this constructor directly, it is recommended to use {@link OrderSearchCriteriaBuilder}.
     *
     * @param patient
     * 		the patient the order is for
     * @param careSetting
     * 		the care setting to match on
     * @param concepts
     * 		the concepts to match on; if not specified, matches on all concepts
     * @param orderTypes
     * 		the order types to match on; if not specified, matches all order types
     * @param activatedOnOrBeforeDate
     * 		orders must have dateActivated on or before this date
     * @param activatedOnOrAfterDate
     * 		orders must have dateActivated on or after this date
     * @param includeVoided
     * 		whether to include the voided orders or not
     */
    public OrderSearchCriteria([CtParameterImpl][CtTypeReferenceImpl]Patient patient, [CtParameterImpl][CtTypeReferenceImpl]CareSetting careSetting, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]Concept> concepts, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]OrderType> orderTypes, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date activatedOnOrBeforeDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date activatedOnOrAfterDate, [CtParameterImpl][CtTypeReferenceImpl]boolean isStopped, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date autoExpireOnOrBeforeDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date canceledOrExpiredOnOrBeforeDate, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Order.Action action, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Order.FulfillerStatus fulfillerStatus, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean includeNullFulfillerStatus, [CtParameterImpl][CtTypeReferenceImpl]boolean excludeCanceledAndExpired, [CtParameterImpl][CtTypeReferenceImpl]boolean excludeDiscontinueOrders, [CtParameterImpl][CtTypeReferenceImpl]boolean includeVoided) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.patient = [CtVariableReadImpl]patient;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.careSetting = [CtVariableReadImpl]careSetting;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.concepts = [CtVariableReadImpl]concepts;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.orderTypes = [CtVariableReadImpl]orderTypes;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activatedOnOrBeforeDate = [CtVariableReadImpl]activatedOnOrBeforeDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activatedOnOrAfterDate = [CtVariableReadImpl]activatedOnOrAfterDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isStopped = [CtVariableReadImpl]isStopped;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.autoExpireOnOrBeforeDate = [CtVariableReadImpl]autoExpireOnOrBeforeDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.canceledOrExpiredOnOrBeforeDate = [CtVariableReadImpl]canceledOrExpiredOnOrBeforeDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.action = [CtVariableReadImpl]action;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fulfillerStatus = [CtVariableReadImpl]fulfillerStatus;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeNullFulfillerStatus = [CtVariableReadImpl]includeNullFulfillerStatus;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludeCanceledAndExpired = [CtVariableReadImpl]excludeCanceledAndExpired;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludeDiscontinueOrders = [CtVariableReadImpl]excludeDiscontinueOrders;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeVoided = [CtVariableReadImpl]includeVoided;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the patient the order is for
     */
    public [CtTypeReferenceImpl]org.openmrs.parameter.Patient getPatient() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]patient;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the care setting to match on
     */
    public [CtTypeReferenceImpl]org.openmrs.parameter.CareSetting getCareSetting() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]careSetting;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the concepts defining the order must be in this collection
     */
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]Concept> getConcepts() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]concepts;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the order types to match on must be in this collection
     */
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]OrderType> getOrderTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]orderTypes;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getAccessionNumber() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]accessionNumber;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getOrderNumber() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]orderNumber;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return orders must have dateActivated on or before this date
     */
    public [CtTypeReferenceImpl]java.util.Date getActivatedOnOrBeforeDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]activatedOnOrBeforeDate;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return orders must have dateActivated on or after this date
     */
    public [CtTypeReferenceImpl]java.util.Date getActivatedOnOrAfterDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]activatedOnOrAfterDate;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return orders must have dateStopped on or before this date
     */
    public [CtTypeReferenceImpl]boolean isStopped() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]isStopped;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return orders must have autoExpireDate on or before this date
     */
    public [CtTypeReferenceImpl]java.util.Date getAutoExpireOnOrBeforeDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]autoExpireOnOrBeforeDate;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return orders that are canceled or have autoExpireDate on or before this date
     */
    public [CtTypeReferenceImpl]java.util.Date getCanceledOrExpiredOnOrBeforeDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]canceledOrExpiredOnOrBeforeDate;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return orders must match the action
     */
    public [CtTypeReferenceImpl]Order.Action getAction() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]action;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return orders must match the fulfillerstatus
     */
    public [CtTypeReferenceImpl]Order.FulfillerStatus getFulfillerStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]fulfillerStatus;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return include(OR) orders with fulfiller_status = null
     */
    public [CtTypeReferenceImpl]java.lang.Boolean getIncludeNullFulfillerStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]includeNullFulfillerStatus;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean getExcludeCanceledAndExpired() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]excludeCanceledAndExpired;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean getExcludeDiscontinueOrders() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]excludeDiscontinueOrders;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return whether to include the voided orders or not
     */
    public [CtTypeReferenceImpl]boolean getIncludeVoided() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]includeVoided;
    }
}
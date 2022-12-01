[CompilationUnitImpl][CtJavaDocImpl]/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
[CtPackageDeclarationImpl]package org.openmrs.api;
[CtImportImpl]import java.util.Locale;
[CtUnresolvedImport]import org.openmrs.test.TestUtil;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.LinkedHashSet;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertNotEquals;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import org.openmrs.Condition;
[CtUnresolvedImport]import org.openmrs.DrugOrder;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertFalse;
[CtImportImpl]import org.apache.commons.lang3.time.DateUtils;
[CtUnresolvedImport]import static org.hamcrest.Matchers.hasItems;
[CtUnresolvedImport]import org.junit.jupiter.api.Disabled;
[CtUnresolvedImport]import org.openmrs.Allergy;
[CtUnresolvedImport]import org.openmrs.parameter.OrderSearchCriteria;
[CtUnresolvedImport]import static org.openmrs.Order.Action.DISCONTINUE;
[CtUnresolvedImport]import static org.hamcrest.Matchers.nullValue;
[CtUnresolvedImport]import org.openmrs.OrderSet;
[CtUnresolvedImport]import org.openmrs.test.jupiter.BaseContextSensitiveTest;
[CtUnresolvedImport]import org.openmrs.OrderType;
[CtUnresolvedImport]import org.openmrs.orders.TimestampOrderNumberGenerator;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.hibernate.cfg.Configuration;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.hibernate.boot.registry.StandardServiceRegistry;
[CtUnresolvedImport]import javax.persistence.Entity;
[CtUnresolvedImport]import org.openmrs.Diagnosis;
[CtUnresolvedImport]import org.openmrs.parameter.OrderSearchCriteriaBuilder;
[CtUnresolvedImport]import org.openmrs.TestOrder;
[CtUnresolvedImport]import org.openmrs.api.db.hibernate.HibernateAdministrationDAO;
[CtImportImpl]import java.util.GregorianCalendar;
[CtUnresolvedImport]import org.openmrs.ConceptDatatype;
[CtUnresolvedImport]import org.openmrs.Drug;
[CtUnresolvedImport]import org.openmrs.FreeTextDosingInstructions;
[CtUnresolvedImport]import org.hibernate.boot.MetadataSources;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import static org.hamcrest.Matchers.containsInAnyOrder;
[CtUnresolvedImport]import org.openmrs.Provider;
[CtUnresolvedImport]import static org.openmrs.test.OpenmrsMatchers.hasId;
[CtUnresolvedImport]import org.openmrs.DosingInstructions;
[CtUnresolvedImport]import org.openmrs.OrderFrequency;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertTrue;
[CtUnresolvedImport]import static org.hamcrest.Matchers.is;
[CtUnresolvedImport]import static org.hamcrest.Matchers.containsString;
[CtUnresolvedImport]import org.openmrs.ConceptDescription;
[CtUnresolvedImport]import static org.openmrs.Order.FulfillerStatus.COMPLETED;
[CtUnresolvedImport]import org.openmrs.Patient;
[CtUnresolvedImport]import org.openmrs.order.OrderUtilTest;
[CtUnresolvedImport]import org.openmrs.util.OpenmrsConstants;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertNotNull;
[CtUnresolvedImport]import org.openmrs.messagesource.MessageSourceService;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.openmrs.api.impl.OrderServiceImpl;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertThrows;
[CtUnresolvedImport]import org.openmrs.ConceptClass;
[CtUnresolvedImport]import org.openmrs.Visit;
[CtUnresolvedImport]import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
[CtUnresolvedImport]import org.hibernate.boot.Metadata;
[CtUnresolvedImport]import org.openmrs.SimpleDosingInstructions;
[CtUnresolvedImport]import org.openmrs.util.PrivilegeConstants;
[CtUnresolvedImport]import org.openmrs.util.DateUtil;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertNull;
[CtUnresolvedImport]import static org.openmrs.test.TestUtil.containsId;
[CtImportImpl]import java.lang.reflect.Field;
[CtUnresolvedImport]import org.openmrs.api.db.hibernate.HibernateSessionFactoryBean;
[CtUnresolvedImport]import static org.hamcrest.MatcherAssert.assertThat;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertEquals;
[CtUnresolvedImport]import org.openmrs.Obs;
[CtUnresolvedImport]import javax.persistence.Id;
[CtUnresolvedImport]import static org.hamcrest.Matchers.empty;
[CtUnresolvedImport]import org.openmrs.Encounter;
[CtImportImpl]import java.util.Calendar;
[CtUnresolvedImport]import org.openmrs.GlobalProperty;
[CtUnresolvedImport]import org.openmrs.Order;
[CtUnresolvedImport]import org.openmrs.CareSetting;
[CtUnresolvedImport]import org.openmrs.ConceptName;
[CtUnresolvedImport]import org.openmrs.api.builder.OrderBuilder;
[CtImportImpl]import java.util.Date;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import org.openmrs.Concept;
[CtImportImpl]import java.text.DateFormat;
[CtUnresolvedImport]import org.openmrs.Order.Action;
[CtUnresolvedImport]import org.openmrs.OrderGroup;
[CtImportImpl]import java.text.ParseException;
[CtUnresolvedImport]import org.openmrs.api.context.Context;
[CtUnresolvedImport]import org.openmrs.order.OrderUtil;
[CtClassImpl][CtJavaDocImpl]/**
 * TODO clean up and test all methods in OrderService
 */
public class OrderServiceTest extends [CtTypeReferenceImpl]org.openmrs.test.jupiter.BaseContextSensitiveTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OTHER_ORDER_FREQUENCIES_XML = [CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrderFrequencies.xml";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String ORDER_SET = [CtLiteralImpl]"org/openmrs/api/include/OrderSetServiceTest-general.xml";

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.api.ConceptService conceptService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.api.OrderService orderService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.api.PatientService patientService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.api.EncounterService encounterService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.api.ProviderService providerService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.api.AdministrationService adminService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.api.OrderSetService orderSetService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.openmrs.messagesource.MessageSourceService messageSourceService;

    [CtClassImpl][CtAnnotationImpl]@javax.persistence.Entity
    public class SomeTestOrder extends [CtTypeReferenceImpl]org.openmrs.TestOrder {
        [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Id
        private [CtTypeReferenceImpl]java.lang.Integer orderId;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Patient patient;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.OrderType orderType;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Concept concept;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String instructions;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Date dateActivated;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Date autoExpireDate;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Encounter encounter;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Provider orderer;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Date dateStopped;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Concept orderReason;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String accessionNumber;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String orderReasonNonCoded;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.api.Urgency urgency = [CtFieldReadImpl]Urgency.ROUTINE;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String orderNumber;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String commentToFulfiller;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.CareSetting careSetting;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Date scheduledDate;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Double sortWeight;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Order previousOrder;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Order.Action action = [CtFieldReadImpl]org.openmrs.Order.Action.NEW;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.OrderGroup orderGroup;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.api.FulfillerStatus fulfillerStatus;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String fulfillerComment;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Double dose;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Concept doseUnits;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.OrderFrequency frequency;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Boolean asNeeded = [CtLiteralImpl]false;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Double quantity;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Concept quantityUnits;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Drug drug;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String asNeededCondition;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.openmrs.DosingInstructions> dosingType = [CtFieldReadImpl]org.openmrs.SimpleDosingInstructions.class;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Integer numRefills;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String dosingInstructions;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Integer duration;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Concept durationUnits;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.openmrs.Concept route;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String brandName;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Boolean dispenseAsWritten = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String drugNonCoded;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotSaveOrderIfOrderDoesntValidate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Order();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.APIException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.APIException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.containsString([CtLiteralImpl]"failed to validate with reason:"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderByUuid(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderByUuid_shouldFindObjectGivenValidUuid() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uuid = [CtLiteralImpl]"921de0a3-05c4-444a-be03-e01b4c4b9142";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByUuid([CtVariableReadImpl]uuid);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]order.getOrderId())));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderByUuid(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderByUuid_shouldReturnNullIfNoObjectFoundWithGivenUuid() [CtBlockImpl]{
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByUuid([CtLiteralImpl]"some invalid uuid"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#purgeOrder(org.openmrs.Order, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void purgeOrder_shouldDeleteAnyObsAssociatedToTheOrderWhenCascadeIsTrue() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-deleteObsThatReference.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String ordUuid = [CtLiteralImpl]"0c96f25c-4949-4f72-9931-d808fbcdb612";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String obsUuid = [CtLiteralImpl]"be3a4d7a-f9ab-47bb-aaad-bc0b452fcda4";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.ObsService os = [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getObsService();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Obs obs = [CtInvocationImpl][CtVariableReadImpl]os.getObsByUuid([CtVariableReadImpl]obsUuid);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]obs);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByUuid([CtVariableReadImpl]ordUuid);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]order);
        [CtInvocationImpl][CtCommentImpl]// sanity check to ensure that the obs and order are actually related
        assertEquals([CtVariableReadImpl]order, [CtInvocationImpl][CtVariableReadImpl]obs.getOrder());
        [CtInvocationImpl][CtCommentImpl]// Ensure that passing false does not delete the related obs
        [CtFieldReadImpl]orderService.purgeOrder([CtVariableReadImpl]order, [CtLiteralImpl]false);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]os.getObsByUuid([CtVariableReadImpl]obsUuid));
        [CtInvocationImpl][CtFieldReadImpl]orderService.purgeOrder([CtVariableReadImpl]order, [CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// Ensure that actually the order got purged
        assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByUuid([CtVariableReadImpl]ordUuid));
        [CtInvocationImpl][CtCommentImpl]// Ensure that the related obs got deleted
        assertNull([CtInvocationImpl][CtVariableReadImpl]os.getObsByUuid([CtVariableReadImpl]obsUuid));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#purgeOrder(org.openmrs.Order, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void purgeOrder_shouldDeleteOrderFromTheDatabase() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String uuid = [CtLiteralImpl]"9c21e407-697b-11e3-bd76-0800271c1b75";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByUuid([CtVariableReadImpl]uuid);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]order);
        [CtInvocationImpl][CtFieldReadImpl]orderService.purgeOrder([CtVariableReadImpl]order);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByUuid([CtVariableReadImpl]uuid));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @throws InterruptedException
     * @see OrderNumberGenerator#getNewOrderNumber(OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getNewOrderNumber_shouldAlwaysReturnUniqueOrderNumbersWhenCalledMultipleTimesWithoutSavingOrders() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int N = [CtLiteralImpl]50;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> uniqueOrderNumbers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtLiteralImpl]50);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Thread> threads = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]N; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]threads.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtLambdaImpl]() -> [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.openSession();
                    [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.addProxyPrivilege([CtTypeAccessImpl]PrivilegeConstants.ADD_ORDERS);
                    [CtInvocationImpl][CtVariableReadImpl]uniqueOrderNumbers.add([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]org.openmrs.api.OrderNumberGenerator) (orderService)).getNewOrderNumber([CtLiteralImpl]null));
                } finally [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.removeProxyPrivilege([CtTypeAccessImpl]PrivilegeConstants.ADD_ORDERS);
                    [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.closeSession();
                }
            }));
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]N; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]threads.get([CtVariableReadImpl]i).start();
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]N; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]threads.get([CtVariableReadImpl]i).join();
        }
        [CtInvocationImpl][CtCommentImpl]// since we used a set we should have the size as N indicating that there were no duplicates
        assertEquals([CtVariableReadImpl]N, [CtInvocationImpl][CtVariableReadImpl]uniqueOrderNumbers.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderByOrderNumber(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderByOrderNumber_shouldFindObjectGivenValidOrderNumber() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByOrderNumber([CtLiteralImpl]"1");
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]order);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]order.getOrderId())));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderByOrderNumber(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderByOrderNumber_shouldReturnNullIfNoObjectFoundWithGivenOrderNumber() [CtBlockImpl]{
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByOrderNumber([CtLiteralImpl]"some invalid order number"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderHistoryByConcept(Patient, Concept)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderHistoryByConcept_shouldReturnOrdersWithTheGivenConcept() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// We should have two orders with this concept.
        [CtTypeReferenceImpl]org.openmrs.Concept concept = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]88);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderHistoryByConcept([CtVariableReadImpl]patient, [CtVariableReadImpl]concept);
        [CtInvocationImpl][CtCommentImpl]// They must be sorted by dateActivated starting with the latest
        assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]444, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]0).getOrderId().intValue());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]44, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]1).getOrderId().intValue());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]2).getOrderId().intValue());
        [CtAssignmentImpl][CtVariableWriteImpl]concept = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]792);
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderHistoryByConcept([CtVariableReadImpl]patient, [CtVariableReadImpl]concept);
        [CtInvocationImpl][CtCommentImpl]// They must be sorted by dateActivated starting with the latest
        assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]0).getOrderId().intValue());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]222, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]1).getOrderId().intValue());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]22, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]2).getOrderId().intValue());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]3).getOrderId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderHistoryByConcept(Patient, Concept)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderHistoryByConcept_shouldReturnEmptyListForConceptWithoutOrders() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept concept = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]21);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderHistoryByConcept([CtVariableReadImpl]patient, [CtVariableReadImpl]concept);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderHistoryByConcept(org.openmrs.Patient, org.openmrs.Concept)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderHistoryByConcept_shouldRejectANullConcept() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getOrderHistoryByConcept([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Patient(), [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"patient and concept are required"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderHistoryByConcept(org.openmrs.Patient, org.openmrs.Concept)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderHistoryByConcept_shouldRejectANullPatient() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getOrderHistoryByConcept([CtLiteralImpl]null, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Concept()));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"patient and concept are required"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderHistoryByOrderNumber(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderHistoryByOrderNumber_shouldReturnAllOrderHistoryForGivenOrderNumber() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderHistoryByOrderNumber([CtLiteralImpl]"111");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]111, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]0).getOrderId().intValue());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]1).getOrderId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequency(Integer)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequency_shouldReturnTheOrderFrequencyThatMatchesTheSpecifiedId() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"28090760-7c38-11e3-baa7-0800200c9a66", [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]1).getUuid());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencyByUuid(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencyByUuid_shouldReturnTheOrderFrequencyThatMatchesTheSpecifiedUuid() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencyByUuid([CtLiteralImpl]"28090760-7c38-11e3-baa7-0800200c9a66").getOrderFrequencyId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencyByConcept(org.openmrs.Concept)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencyByConcept_shouldReturnTheOrderFrequencyThatMatchesTheSpecifiedConcept() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept concept = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]4);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencyByConcept([CtVariableReadImpl]concept).getOrderFrequencyId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldReturnOnlyNonRetiredOrderFrequenciesIfIncludeRetiredIsSetToFalse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderFrequency> orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]1));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]2));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldReturnAllTheOrderFrequenciesIfIncludeRetiredIsSetToTrue() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderFrequency> orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]true);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]1));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]2));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]3));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldReturnAllActiveOrdersForTheSpecifiedPatient() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]0), [CtLiteralImpl]null));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]1), [CtLiteralImpl]null));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]2), [CtLiteralImpl]null));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]3), [CtLiteralImpl]null));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]4), [CtLiteralImpl]null));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldReturnAllActiveOrdersForTheSpecifiedPatientAndCareSetting() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting careSetting = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtVariableReadImpl]careSetting, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldReturnAllActiveDrugOrdersForTheSpecifiedPatient() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1), [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldReturnAllActiveTestOrdersForTheSpecifiedPatient() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByName([CtLiteralImpl]"Test order"), [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]orders.get([CtLiteralImpl]0), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldFailIfPatientIsNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getActiveOrders([CtLiteralImpl]null, [CtLiteralImpl]null, [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getCareSetting([CtLiteralImpl]1), [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Patient is required when fetching active orders"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @throws ParseException
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldReturnActiveOrdersAsOfTheSpecifiedDate() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getAllOrdersByPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]12, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateFormat().parse([CtLiteralImpl]"10/12/2007");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]orders.contains([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]22)));[CtCommentImpl]// DC

        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]orders.contains([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]44)));[CtCommentImpl]// DC

        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]orders.contains([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]8)));[CtCommentImpl]// voided

        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]9) };
        [CtAssignmentImpl][CtVariableWriteImpl]asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateTimeFormat().parse([CtLiteralImpl]"10/12/2007 00:01:00");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders));
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders1 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]4), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222) };
        [CtAssignmentImpl][CtVariableWriteImpl]asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateFormat().parse([CtLiteralImpl]"10/04/2008");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders1));
        [CtAssignmentImpl][CtVariableWriteImpl]asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateTimeFormat().parse([CtLiteralImpl]"10/04/2008 00:01:00");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders2 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders2));
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders3 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]6) };
        [CtAssignmentImpl][CtVariableWriteImpl]asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateTimeFormat().parse([CtLiteralImpl]"26/09/2008 09:24:10");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders3));
        [CtAssignmentImpl][CtVariableWriteImpl]asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateTimeFormat().parse([CtLiteralImpl]"26/09/2008 09:25:10");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders4 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders4));
        [CtAssignmentImpl][CtVariableWriteImpl]asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateFormat().parse([CtLiteralImpl]"04/12/2008");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders5 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders5));
        [CtAssignmentImpl][CtVariableWriteImpl]asOfDate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getDateFormat().parse([CtLiteralImpl]"06/12/2008");
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]asOfDate);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders5));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldReturnAllOrdersIfNoOrderTypeIsSpecified() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrders = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]222), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrders));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getActiveOrders(org.openmrs.Patient, org.openmrs.OrderType,
    org.openmrs.CareSetting, java.util.Date)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getActiveOrders_shouldIncludeOrdersForSubTypesIfOrderTypeIsSpecified() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrders.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType testOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]testOrderType, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrder1 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]101), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]102), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]103), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]104) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrder1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType labTestOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]7);
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]labTestOrderType, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrder2 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]101), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]103), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]104) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrder2));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, String, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldPopulateCorrectAttributesOnTheDiscontinueAndDiscontinuedOrders() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByOrderNumber([CtLiteralImpl]"111");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Provider orderer = [CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date discontinueDate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String discontinueReasonNonCoded = [CtLiteralImpl]"Test if I can discontinue this";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinueOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.discontinueOrder([CtVariableReadImpl]order, [CtVariableReadImpl]discontinueReasonNonCoded, [CtVariableReadImpl]discontinueDate, [CtVariableReadImpl]orderer, [CtVariableReadImpl]encounter);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped(), [CtVariableReadImpl]discontinueDate);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]discontinueOrder);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getId());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getDateActivated(), [CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAutoExpireDate());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAction(), [CtTypeAccessImpl]Action.DISCONTINUE);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getOrderReasonNonCoded(), [CtVariableReadImpl]discontinueReasonNonCoded);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getPreviousOrder(), [CtVariableReadImpl]order);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(Order, String, Date, Provider, Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldPassForAnActiveOrderWhichIsScheduledAndNotStartedAsOfDiscontinueDate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Order();
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Action.NEW);
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1).getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]17));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addMonths([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtAssignmentImpl][CtVariableWriteImpl]order = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.isStarted());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Provider orderer = [CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date discontinueDate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String discontinueReasonNonCoded = [CtLiteralImpl]"Test if I can discontinue this";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinueOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.discontinueOrder([CtVariableReadImpl]order, [CtVariableReadImpl]discontinueReasonNonCoded, [CtVariableReadImpl]discontinueDate, [CtVariableReadImpl]orderer, [CtVariableReadImpl]encounter);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped(), [CtVariableReadImpl]discontinueDate);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]discontinueOrder);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getId());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getDateActivated(), [CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAutoExpireDate());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAction(), [CtTypeAccessImpl]Action.DISCONTINUE);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getOrderReasonNonCoded(), [CtVariableReadImpl]discontinueReasonNonCoded);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getPreviousOrder(), [CtVariableReadImpl]order);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, org.openmrs.Concept, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldSetCorrectAttributesOnTheDiscontinueAndDiscontinuedOrders() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-discontinueReason.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderByOrderNumber([CtLiteralImpl]"111");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Provider orderer = [CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date discontinueDate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept concept = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinueOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.discontinueOrder([CtVariableReadImpl]order, [CtVariableReadImpl]concept, [CtVariableReadImpl]discontinueDate, [CtVariableReadImpl]orderer, [CtVariableReadImpl]encounter);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped(), [CtVariableReadImpl]discontinueDate);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]discontinueOrder);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getId());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getDateActivated(), [CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAutoExpireDate());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAction(), [CtTypeAccessImpl]Action.DISCONTINUE);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getOrderReason(), [CtVariableReadImpl]concept);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getPreviousOrder(), [CtVariableReadImpl]order);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(Order, Concept, Date, Provider, Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldPassForAnActiveOrderWhichIsScheduledAndNotStartedAsOfDiscontinueDateWithParamConcept() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Order();
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Action.NEW);
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1).getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]17));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addMonths([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtAssignmentImpl][CtVariableWriteImpl]order = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.isStarted());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Provider orderer = [CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date discontinueDate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept concept = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinueOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.discontinueOrder([CtVariableReadImpl]order, [CtVariableReadImpl]concept, [CtVariableReadImpl]discontinueDate, [CtVariableReadImpl]orderer, [CtVariableReadImpl]encounter);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped(), [CtVariableReadImpl]discontinueDate);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]discontinueOrder);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getId());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getDateActivated(), [CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAutoExpireDate());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getAction(), [CtTypeAccessImpl]Action.DISCONTINUE);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getOrderReason(), [CtVariableReadImpl]concept);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueOrder.getPreviousOrder(), [CtVariableReadImpl]order);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, String, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldFailForADiscontinuationOrder() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-discontinuedOrder.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]26);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.DISCONTINUE, [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopDiscontinuationOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopDiscontinuationOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl]"Test if I can discontinue this", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.action.cannot.discontinue")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, org.openmrs.Concept, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldNotPassForADiscontinuationOrder() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-discontinuedOrder.xml");
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-discontinueReason.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]26);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.DISCONTINUE, [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopDiscontinuationOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopDiscontinuationOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl](([CtTypeReferenceImpl]org.openmrs.Concept) (null)), [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.action.cannot.discontinue")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, String, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldFailForADiscontinuedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]2);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.isActive());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getDateStopped());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl]"some reason", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, org.openmrs.Concept, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldNotPassForADiscontinuedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]2);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.isActive());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getDateStopped());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl](([CtTypeReferenceImpl]org.openmrs.Concept) (null)), [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldDiscontinueExistingActiveOrderIfNewOrderBeingSavedWithActionToDiscontinue() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Order.Action.DISCONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.SimpleDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setDose([CtLiteralImpl]500.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setDoseUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]50));
        [CtInvocationImpl][CtVariableReadImpl]order.setFrequency([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setRoute([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]22));
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtLiteralImpl]10);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtLiteralImpl]20.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtLocalVariableImpl][CtCommentImpl]// We are trying to discontinue order id 111 in standardTestDataset.xml
        [CtTypeReferenceImpl]org.openmrs.Order expectedPreviousOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]expectedPreviousOrder.getDateStopped());
        [CtAssignmentImpl][CtVariableWriteImpl]order = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null)));
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]expectedPreviousOrder.getDateStopped(), [CtLiteralImpl]"should populate dateStopped in previous order");
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getId(), [CtLiteralImpl]"should save discontinue order");
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expectedPreviousOrder, [CtInvocationImpl][CtVariableReadImpl]order.getPreviousOrder());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]expectedPreviousOrder.getDateStopped());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]order.getDateActivated(), [CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldDiscontinuePreviousOrderIfItIsNotAlreadyDiscontinued() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// We are trying to discontinue order id 111 in standardTestDataset.xml
        [CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Order.Action.DISCONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getProviderService().getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.SimpleDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setDose([CtLiteralImpl]500.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setDoseUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]50));
        [CtInvocationImpl][CtVariableReadImpl]order.setFrequency([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setRoute([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]22));
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtLiteralImpl]10);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtLiteralImpl]20.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]previousOrder, [CtLiteralImpl]null));
        [CtInvocationImpl][CtVariableReadImpl]order.setPreviousOrder([CtVariableReadImpl]previousOrder);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]order.getDateActivated(), [CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped(), [CtLiteralImpl]"previous order should be discontinued");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfConceptInPreviousOrderDoesNotMatchThisConcept() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]previousOrder, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForDiscontinuing();
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept newConcept = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5089);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]previousOrder.getConcept().equals([CtVariableReadImpl]newConcept));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtVariableReadImpl]newConcept);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"The orderable of the previous order and the new one order don't match"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, org.openmrs.Concept, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldRejectAFutureDiscontinueDate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar cal = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
        [CtInvocationImpl][CtVariableReadImpl]cal.add([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]HOUR_OF_DAY, [CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting careSetting = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order orderToDiscontinue = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtVariableReadImpl]careSetting, [CtLiteralImpl]null).get([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]orderToDiscontinue, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Concept(), [CtInvocationImpl][CtVariableReadImpl]cal.getTime(), [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Discontinue date cannot be in the future"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, String, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldFailIfDiscontinueDateIsInTheFuture() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar cal = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
        [CtInvocationImpl][CtVariableReadImpl]cal.add([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]HOUR_OF_DAY, [CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order orderToDiscontinue = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]2), [CtLiteralImpl]null, [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1), [CtLiteralImpl]null).get([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]orderToDiscontinue, [CtLiteralImpl]"Testing", [CtInvocationImpl][CtVariableReadImpl]cal.getTime(), [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Discontinue date cannot be in the future"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfTheExistingDrugOrderMatchesTheConceptAndDrugOfTheDCOrder() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.DrugOrder orderToDiscontinue = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]orderToDiscontinue, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDrug());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByName([CtLiteralImpl]"Drug order"));
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Order.Action.DISCONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getConcept());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.SimpleDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setDose([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDose());
        [CtInvocationImpl][CtVariableReadImpl]order.setDoseUnits([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDoseUnits());
        [CtInvocationImpl][CtVariableReadImpl]order.setRoute([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getRoute());
        [CtInvocationImpl][CtVariableReadImpl]order.setFrequency([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getFrequency());
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getQuantity());
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getQuantityUnits());
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getNumRefills());
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDateStopped(), [CtLiteralImpl]"previous order should be discontinued");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfTheExistingDrugOrderMatchesTheConceptAndNotDrugOfTheDCOrder() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.DrugOrder orderToDiscontinue = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]orderToDiscontinue, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtCommentImpl]// create a different test drug
        [CtTypeReferenceImpl]org.openmrs.Drug discontinuationOrderDrug = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Drug();
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrderDrug.setConcept([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getConcept());
        [CtAssignmentImpl][CtVariableWriteImpl]discontinuationOrderDrug = [CtInvocationImpl][CtFieldReadImpl]conceptService.saveDrug([CtVariableReadImpl]discontinuationOrderDrug);
        [CtInvocationImpl]assertNotEquals([CtVariableReadImpl]discontinuationOrderDrug, [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDrug());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDrug());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtVariableReadImpl]discontinuationOrderDrug);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"The orderable of the previous order and the new one order don't match"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * previous order
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfTheExistingDrugOrderMatchesTheConceptAndThereIsNoDrugOnThePreviousOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder orderToDiscontinue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setAction([CtTypeAccessImpl]Action.NEW);
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setPatient([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setConcept([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setOrderer([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1).getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]17));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setDrug([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setDosingInstructions([CtLiteralImpl]"instructions");
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setDosingInstructions([CtLiteralImpl]"2 for 5 days");
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setQuantity([CtLiteralImpl]10.0);
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.setNumRefills([CtLiteralImpl]2);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]orderToDiscontinue, [CtLiteralImpl]null);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]orderToDiscontinue, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.cloneForDiscontinuing();
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDateStopped(), [CtLiteralImpl]"previous order should be discontinued");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, org.openmrs.Concept, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldFailForAStoppedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order orderToDiscontinue = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDateStopped());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]orderToDiscontinue, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]1), [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, String, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldFailForAVoidedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order orderToDiscontinue = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]8);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getVoided());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]orderToDiscontinue, [CtLiteralImpl]"testing", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#discontinueOrder(org.openmrs.Order, org.openmrs.Concept, java.util.Date,
    org.openmrs.Provider, org.openmrs.Encounter)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void discontinueOrder_shouldFailForAnExpiredOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order orderToDiscontinue = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]6);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getAutoExpireDate());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getAutoExpireDate().before([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.discontinueOrder([CtVariableReadImpl]orderToDiscontinue, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]1), [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]encounter));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotAllowEditingAnExistingOrder() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.UnchangeableObjectException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.UnchangeableObjectException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.cannot.edit.existing"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getCareSettingByUuid(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getCareSettingByUuid_shouldReturnTheCareSettingWithTheSpecifiedUuid() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting cs = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSettingByUuid([CtLiteralImpl]"6f0c9a92-6f24-11e3-af88-005056821db0");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cs.getId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getCareSettingByName(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getCareSettingByName_shouldReturnTheCareSettingWithTheSpecifiedName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting cs = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSettingByName([CtLiteralImpl]"INPATIENT");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cs.getId().intValue());
        [CtAssignmentImpl][CtCommentImpl]// should also be case insensitive
        [CtVariableWriteImpl]cs = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSettingByName([CtLiteralImpl]"inpatient");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cs.getId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getCareSettings(boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getCareSettings_shouldReturnOnlyUnRetiredCareSettingsIfIncludeRetiredIsSetToFalse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.CareSetting> careSettings = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSettings([CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]careSettings.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]careSettings, [CtLiteralImpl]1));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]careSettings, [CtLiteralImpl]2));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getCareSettings(boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getCareSettings_shouldReturnRetiredCareSettingsIfIncludeRetiredIsSetToTrue() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting retiredCareSetting = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]3);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]retiredCareSetting.getRetired());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.CareSetting> careSettings = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSettings([CtLiteralImpl]true);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]careSettings.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]careSettings, [CtInvocationImpl][CtVariableReadImpl]retiredCareSetting.getCareSettingId()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotAllowRevisingAStoppedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]originalOrder.getDateStopped());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order revisedOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]4));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setInstructions([CtLiteralImpl]"Take after a meal");
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]revisedOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotAllowRevisingAVoidedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]8);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalOrder.getVoided());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order revisedOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setInstructions([CtLiteralImpl]"Take after a meal");
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]revisedOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotAllowRevisingAnExpiredOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]6);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]originalOrder.getAutoExpireDate());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]originalOrder.getAutoExpireDate().before([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order revisedOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setInstructions([CtLiteralImpl]"Take after a meal");
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setAutoExpireDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]revisedOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotAllowRevisingAnOrderWithNoPreviousOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order revisedOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setInstructions([CtLiteralImpl]"Take after a meal");
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setPreviousOrder([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.MissingRequiredPropertyException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.MissingRequiredPropertyException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]revisedOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.previous.required")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSaveARevisedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtVariableReadImpl]originalOrder.getPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> originalActiveOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int originalOrderCount = [CtInvocationImpl][CtVariableReadImpl]originalActiveOrders.size();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalActiveOrders.contains([CtVariableReadImpl]originalOrder));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order revisedOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setInstructions([CtLiteralImpl]"Take after a meal");
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revisedOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> activeOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]originalOrderCount, [CtInvocationImpl][CtVariableReadImpl]activeOrders.size());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]revisedOrder.getDateActivated(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addSeconds([CtInvocationImpl][CtVariableReadImpl]originalOrder.getDateStopped(), [CtLiteralImpl]1));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#updateOrderFulfillerStatus(org.openmrs.Order, Order.FulfillerStatus, String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void updateOrderFulfillerStatus_shouldEditFulfillerStatusInOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commentText = [CtLiteralImpl]"We got the new order";
        [CtInvocationImpl]assertNotEquals([CtInvocationImpl][CtVariableReadImpl]originalOrder.getFulfillerStatus(), [CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS);
        [CtInvocationImpl][CtFieldReadImpl]orderService.updateOrderFulfillerStatus([CtVariableReadImpl]originalOrder, [CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtVariableReadImpl]commentText);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order updatedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerStatus());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]commentText, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerComment());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#updateOrderFulfillerStatus(org.openmrs.Order,
    Order.FulfillerStatus, String, String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void updateOrderFulfillerStatus_shouldEditFulfillerStatusWithAccessionNumberInOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commentText = [CtLiteralImpl]"We got the new order";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String accessionNumber = [CtLiteralImpl]"12345";
        [CtInvocationImpl]assertNotEquals([CtInvocationImpl][CtVariableReadImpl]originalOrder.getAccessionNumber(), [CtVariableReadImpl]accessionNumber);
        [CtInvocationImpl][CtFieldReadImpl]orderService.updateOrderFulfillerStatus([CtVariableReadImpl]originalOrder, [CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtVariableReadImpl]commentText, [CtVariableReadImpl]accessionNumber);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order updatedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerStatus());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]commentText, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerComment());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]accessionNumber, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getAccessionNumber());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void updateOrderFulfillerStatus_shouldNotUpdateFulfillerStatusNullParameters() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// set up the test data
        [CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commentText = [CtLiteralImpl]"We got the new order";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String accessionNumber = [CtLiteralImpl]"12345";
        [CtInvocationImpl]assertNotEquals([CtInvocationImpl][CtVariableReadImpl]originalOrder.getAccessionNumber(), [CtVariableReadImpl]accessionNumber);
        [CtInvocationImpl][CtFieldReadImpl]orderService.updateOrderFulfillerStatus([CtVariableReadImpl]originalOrder, [CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtVariableReadImpl]commentText, [CtVariableReadImpl]accessionNumber);
        [CtInvocationImpl][CtCommentImpl]// now call again with all null
        [CtFieldReadImpl]orderService.updateOrderFulfillerStatus([CtVariableReadImpl]originalOrder, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order updatedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerStatus());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]commentText, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerComment());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]accessionNumber, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getAccessionNumber());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void updateOrderFulfillerStatus_shouldUpdateFulfillerStatusWithEmptyStrings() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// set up the test data
        [CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commentText = [CtLiteralImpl]"We got the new order";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String accessionNumber = [CtLiteralImpl]"12345";
        [CtInvocationImpl]assertNotEquals([CtInvocationImpl][CtVariableReadImpl]originalOrder.getAccessionNumber(), [CtVariableReadImpl]accessionNumber);
        [CtInvocationImpl][CtFieldReadImpl]orderService.updateOrderFulfillerStatus([CtVariableReadImpl]originalOrder, [CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtVariableReadImpl]commentText, [CtVariableReadImpl]accessionNumber);
        [CtInvocationImpl][CtCommentImpl]// now call again with all null
        [CtFieldReadImpl]orderService.updateOrderFulfillerStatus([CtVariableReadImpl]originalOrder, [CtLiteralImpl]null, [CtLiteralImpl]"", [CtLiteralImpl]"");
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order updatedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Order.FulfillerStatus.IN_PROGRESS, [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerStatus());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"", [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getFulfillerComment());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"", [CtInvocationImpl][CtVariableReadImpl]updatedOrder.getAccessionNumber());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSaveARevisedOrderForAScheduledOrderWhichIsNotStarted() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Order();
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setAction([CtTypeAccessImpl]Action.NEW);
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setPatient([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setConcept([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setOrderer([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1).getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]17));
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addMonths([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]originalOrder.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtAssignmentImpl][CtVariableWriteImpl]originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]originalOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtVariableReadImpl]originalOrder.getPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> originalActiveOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int originalOrderCount = [CtInvocationImpl][CtVariableReadImpl]originalActiveOrders.size();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalActiveOrders.contains([CtVariableReadImpl]originalOrder));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order revisedOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setInstructions([CtLiteralImpl]"Take after a meal");
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revisedOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> activeOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]originalOrderCount, [CtInvocationImpl][CtVariableReadImpl]activeOrders.size());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]revisedOrder.getDateActivated(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addSeconds([CtInvocationImpl][CtVariableReadImpl]originalOrder.getDateStopped(), [CtLiteralImpl]1));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]activeOrders.contains([CtVariableReadImpl]originalOrder));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(String, java.util.Locale, boolean, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldGetNonRetiredFrequenciesWithNamesMatchingThePhraseIfIncludeRetiredIsFalse() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrderFrequencies.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderFrequency> orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]"once", [CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]US, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]100));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]102));
        [CtAssignmentImpl][CtCommentImpl]// should match anywhere in the concept name
        [CtVariableWriteImpl]orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]"nce", [CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]US, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]100));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]102));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(String, java.util.Locale, boolean, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldIncludeRetiredFrequenciesIfIncludeRetiredIsSetToTrue() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrderFrequencies.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderFrequency> orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]"ce", [CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]US, [CtLiteralImpl]false, [CtLiteralImpl]true);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]100));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]101));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]102));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]103));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(String, java.util.Locale, boolean, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldGetFrequenciesWithNamesThatMatchThePhraseAndLocalesIfExactLocaleIsFalse() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrderFrequencies.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderFrequency> orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]"ce", [CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]US, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]100));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]101));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]102));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(String, java.util.Locale, boolean, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldGetFrequenciesWithNamesThatMatchThePhraseAndLocaleIfExactLocaleIsTrue() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrderFrequencies.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderFrequency> orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]"ce", [CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]US, [CtLiteralImpl]true, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]102, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orderFrequencies.get([CtLiteralImpl]0).getOrderFrequencyId().intValue());
        [CtAssignmentImpl][CtVariableWriteImpl]orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]"ce", [CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ENGLISH, [CtLiteralImpl]true, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]100));
        [CtInvocationImpl]assertTrue([CtInvocationImpl]TestUtil.containsId([CtVariableReadImpl]orderFrequencies, [CtLiteralImpl]101));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(String, java.util.Locale, boolean, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldReturnUniqueFrequencies() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrderFrequencies.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String searchPhrase = [CtLiteralImpl]"once";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Locale locale = [CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ENGLISH;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderFrequency> orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtVariableReadImpl]searchPhrase, [CtVariableReadImpl]locale, [CtLiteralImpl]true, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.OrderFrequency expectedOrderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]100);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expectedOrderFrequency, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.get([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtCommentImpl]// Add a new name to the frequency concept so that our search phrase matches on 2
        [CtCommentImpl]// concept names for the same frequency concept
        [CtTypeReferenceImpl]org.openmrs.Concept frequencyConcept = [CtInvocationImpl][CtVariableReadImpl]expectedOrderFrequency.getConcept();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String newConceptName = [CtBinaryOperatorImpl][CtVariableReadImpl]searchPhrase + [CtLiteralImpl]" A Day";
        [CtInvocationImpl][CtVariableReadImpl]frequencyConcept.addName([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.ConceptName([CtVariableReadImpl]newConceptName, [CtVariableReadImpl]locale));
        [CtInvocationImpl][CtVariableReadImpl]frequencyConcept.addDescription([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.ConceptDescription([CtLiteralImpl]"some description", [CtLiteralImpl]null));
        [CtInvocationImpl][CtFieldReadImpl]conceptService.saveConcept([CtVariableReadImpl]frequencyConcept);
        [CtAssignmentImpl][CtVariableWriteImpl]orderFrequencies = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtVariableReadImpl]searchPhrase, [CtVariableReadImpl]locale, [CtLiteralImpl]true, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.size());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expectedOrderFrequency, [CtInvocationImpl][CtVariableReadImpl]orderFrequencies.get([CtLiteralImpl]0));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderFrequencies(String, java.util.Locale, boolean, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderFrequencies_shouldRejectANullSearchPhrase() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getOrderFrequencies([CtLiteralImpl]null, [CtVariableReadImpl]Locale.ENGLISH, [CtLiteralImpl]false, [CtLiteralImpl]false));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"searchPhrase is required"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void retireOrderFrequency_shouldRetireGivenOrderFrequency() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderFrequency orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]1);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetired());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetireReason());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getDateRetired());
        [CtInvocationImpl][CtFieldReadImpl]orderService.retireOrderFrequency([CtVariableReadImpl]orderFrequency, [CtLiteralImpl]"retire reason");
        [CtAssignmentImpl][CtVariableWriteImpl]orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]1);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetired());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"retire reason", [CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetireReason());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getDateRetired());
        [CtInvocationImpl][CtCommentImpl]// Should not change the number of order frequencies.
        assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]true).size());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void unretireOrderFrequency_shouldUnretireGivenOrderFrequency() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrderFrequencies.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderFrequency orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]103);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetired());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetireReason());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getDateRetired());
        [CtInvocationImpl][CtFieldReadImpl]orderService.unretireOrderFrequency([CtVariableReadImpl]orderFrequency);
        [CtAssignmentImpl][CtVariableWriteImpl]orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]103);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetired());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getRetireReason());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getDateRetired());
        [CtInvocationImpl][CtCommentImpl]// Should not change the number of order frequencies.
        assertEquals([CtLiteralImpl]7, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]true).size());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void purgeOrderFrequency_shouldDeleteGivenOrderFrequency() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderFrequency orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]3);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl][CtFieldReadImpl]orderService.purgeOrderFrequency([CtVariableReadImpl]orderFrequency);
        [CtAssignmentImpl][CtVariableWriteImpl]orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]3);
        [CtInvocationImpl]assertNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl][CtCommentImpl]// Should reduce the existing number of order frequencies.
        assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]true).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrderFrequency(OrderFrequency)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrderFrequency_shouldAddANewOrderFrequencyToTheDatabase() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept concept = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Concept();
        [CtInvocationImpl][CtVariableReadImpl]concept.addName([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.ConceptName([CtLiteralImpl]"new name", [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getLocale()));
        [CtInvocationImpl][CtVariableReadImpl]concept.addDescription([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.ConceptDescription([CtLiteralImpl]"some description", [CtLiteralImpl]null));
        [CtInvocationImpl][CtVariableReadImpl]concept.setDatatype([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.ConceptDatatype([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]concept.setConceptClass([CtInvocationImpl][CtFieldReadImpl]conceptService.getConceptClassByName([CtLiteralImpl]"Frequency"));
        [CtAssignmentImpl][CtVariableWriteImpl]concept = [CtInvocationImpl][CtFieldReadImpl]conceptService.saveConcept([CtVariableReadImpl]concept);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer originalSize = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]true).size();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderFrequency orderFrequency = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.OrderFrequency();
        [CtInvocationImpl][CtVariableReadImpl]orderFrequency.setConcept([CtVariableReadImpl]concept);
        [CtInvocationImpl][CtVariableReadImpl]orderFrequency.setFrequencyPerDay([CtLiteralImpl]2.0);
        [CtAssignmentImpl][CtVariableWriteImpl]orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrderFrequency([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getId());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getUuid());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getCreator());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderFrequency.getDateCreated());
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtVariableReadImpl]originalSize + [CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequencies([CtLiteralImpl]true).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrderFrequency(OrderFrequency)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrderFrequency_shouldEditAnExistingOrderFrequencyThatIsNotInUse() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtFieldReadImpl]org.openmrs.api.OrderServiceTest.OTHER_ORDER_FREQUENCIES_XML);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderFrequency orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]100);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl][CtVariableReadImpl]orderFrequency.setFrequencyPerDay([CtLiteralImpl]4.0);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrderFrequency([CtVariableReadImpl]orderFrequency);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrderFrequency(OrderFrequency)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrderFrequency_shouldNotAllowEditingAnExistingOrderFrequencyThatIsInUse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderFrequency orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]1);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtInvocationImpl][CtVariableReadImpl]orderFrequency.setFrequencyPerDay([CtLiteralImpl]4.0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotUpdateObjectInUseException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotUpdateObjectInUseException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrderFrequency([CtVariableReadImpl]orderFrequency));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.frequency.cannot.edit"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#purgeOrderFrequency(OrderFrequency)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void purgeOrderFrequency_shouldNotAllowDeletingAnOrderFrequencyThatIsInUse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderFrequency orderFrequency = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]1);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderFrequency);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotDeleteObjectInUseException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotDeleteObjectInUseException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.purgeOrderFrequency([CtVariableReadImpl]orderFrequency));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.frequency.cannot.delete")));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrderWithScheduledDate_shouldAddANewOrderWithScheduledDateToTheDatabase() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date scheduledDate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Order();
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Action.NEW);
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getPatientService().getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1).getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setScheduledDate([CtVariableReadImpl]scheduledDate);
        [CtInvocationImpl][CtVariableReadImpl]order.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]17));
        [CtAssignmentImpl][CtVariableWriteImpl]order = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order newOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]order.getOrderId());
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]order);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.util.DateUtil.truncateToSeconds([CtVariableReadImpl]scheduledDate), [CtInvocationImpl][CtVariableReadImpl]order.getScheduledDate());
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]newOrder);
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.util.DateUtil.truncateToSeconds([CtVariableReadImpl]scheduledDate), [CtInvocationImpl][CtVariableReadImpl]newOrder.getScheduledDate());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetOrderNumberSpecifiedInTheContextIfSpecified() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.GlobalProperty gp = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_ORDER_NUMBER_GENERATOR_BEAN_ID, [CtLiteralImpl]"orderEntry.OrderNumberGenerator");
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getAdministrationService().saveGlobalProperty([CtVariableReadImpl]gp);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.OrderContext orderCtxt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.OrderContext();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String expectedOrderNumber = [CtLiteralImpl]"Testing";
        [CtInvocationImpl][CtVariableReadImpl]orderCtxt.setAttribute([CtTypeAccessImpl]TimestampOrderNumberGenerator.NEXT_ORDER_NUMBER, [CtVariableReadImpl]expectedOrderNumber);
        [CtAssignmentImpl][CtVariableWriteImpl]order = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtVariableReadImpl]orderCtxt);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expectedOrderNumber, [CtInvocationImpl][CtVariableReadImpl]order.getOrderNumber());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetTheOrderNumberReturnedByTheConfiguredGenerator() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.GlobalProperty gp = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_ORDER_NUMBER_GENERATOR_BEAN_ID, [CtLiteralImpl]"orderEntry.OrderNumberGenerator");
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getAdministrationService().saveGlobalProperty([CtVariableReadImpl]gp);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtAssignmentImpl][CtVariableWriteImpl]order = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]order.getOrderNumber().startsWith([CtTypeAccessImpl]TimestampOrderNumberGenerator.ORDER_NUMBER_PREFIX));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtAnnotationImpl]@org.junit.jupiter.api.Disabled([CtLiteralImpl]"Ignored because it fails after removal of deprecated methods TRUNK-4772")
    public [CtTypeReferenceImpl]void saveOrder_shouldFailForRevisionOrderIfAnActiveDrugOrderForTheSameConceptAndCareSettingsExists() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept aspirin = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]88);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder firstOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setConcept([CtVariableReadImpl]aspirin);
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setDrug([CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setAutoExpireDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtLiteralImpl]10));
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setDosingInstructions([CtLiteralImpl]"2 for 5 days");
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setQuantity([CtLiteralImpl]10.0);
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]firstOrder.setNumRefills([CtLiteralImpl]0);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]firstOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtCommentImpl]// New order in future for same concept and care setting
        [CtTypeReferenceImpl]org.openmrs.DrugOrder secondOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setPatient([CtInvocationImpl][CtVariableReadImpl]firstOrder.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setConcept([CtInvocationImpl][CtVariableReadImpl]firstOrder.getConcept());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setCareSetting([CtInvocationImpl][CtVariableReadImpl]firstOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setDrug([CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtInvocationImpl][CtVariableReadImpl]firstOrder.getEffectiveStopDate(), [CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setDosingInstructions([CtLiteralImpl]"2 for 5 days");
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setQuantity([CtLiteralImpl]10.0);
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setNumRefills([CtLiteralImpl]0);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]secondOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtCommentImpl]// Revise second order to have scheduled date overlapping with active order
        [CtTypeReferenceImpl]org.openmrs.DrugOrder revision = [CtInvocationImpl][CtVariableReadImpl]secondOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revision.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtInvocationImpl][CtVariableReadImpl]firstOrder.getEffectiveStartDate(), [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]revision.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]revision.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.APIException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.APIException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]revision, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.cannot.have.more.than.one"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * settings exists
     *
     * @see OrderService#saveOrder(Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtAnnotationImpl]@org.junit.jupiter.api.Disabled([CtLiteralImpl]"Ignored because it fails after removal of deprecated methods TRUNK-4772")
    public [CtTypeReferenceImpl]void saveOrder_shouldPassForRevisionOrderIfAnActiveTestOrderForTheSameConceptAndCareSettingsExists() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept cd4Count = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5497);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.TestOrder activeOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setConcept([CtVariableReadImpl]cd4Count);
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setAutoExpireDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtLiteralImpl]10));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]activeOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtCommentImpl]// New order in future for same concept
        [CtTypeReferenceImpl]org.openmrs.TestOrder secondOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setPatient([CtInvocationImpl][CtVariableReadImpl]activeOrder.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setConcept([CtInvocationImpl][CtVariableReadImpl]activeOrder.getConcept());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setCareSetting([CtInvocationImpl][CtVariableReadImpl]activeOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtInvocationImpl][CtVariableReadImpl]activeOrder.getEffectiveStopDate(), [CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]secondOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtCommentImpl]// Revise second order to have scheduled date overlapping with active order
        [CtTypeReferenceImpl]org.openmrs.TestOrder revision = [CtInvocationImpl][CtVariableReadImpl]secondOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revision.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtInvocationImpl][CtVariableReadImpl]activeOrder.getEffectiveStartDate(), [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]revision.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]revision.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order savedSecondOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revision, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]savedSecondOrder.getOrderId()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfAnActiveDrugOrderForTheSameConceptAndCareSettingExists() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept triomuneThirty = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]792);
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order for the same concept
        [CtTypeReferenceImpl]org.openmrs.DrugOrder duplicateOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]3)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.isActive());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]triomuneThirty, [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getConcept());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder drugOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setConcept([CtVariableReadImpl]triomuneThirty);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setCareSetting([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDrug([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getDrug());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDose([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getDose());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDoseUnits([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getDoseUnits());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setRoute([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getRoute());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setFrequency([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getFrequency());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setQuantity([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getQuantity());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setQuantityUnits([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getQuantityUnits());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setNumRefills([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getNumRefills());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.AmbiguousOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.AmbiguousOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]drugOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.cannot.have.more.than.one"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfAnActiveTestOrderForTheSameConceptAndCareSettingExists() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept cd4Count = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5497);
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order for the same concept
        [CtTypeReferenceImpl]org.openmrs.TestOrder duplicateOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.TestOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.isActive());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]cd4Count, [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getConcept());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtVariableReadImpl]cd4Count);
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getCareSetting());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order savedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]savedOrder.getOrderId()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtAnnotationImpl]@org.junit.jupiter.api.Disabled([CtLiteralImpl]"Ignored because it fails after removal of deprecated methods TRUNK-4772")
    public [CtTypeReferenceImpl]void saveOrder_shouldSaveRevisionOrderScheduledOnDateNotOverlappingWithAnActiveOrderForTheSameConceptAndCareSetting() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order
        final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept cd4Count = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5497);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.TestOrder activeOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setConcept([CtVariableReadImpl]cd4Count);
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]activeOrder.setAutoExpireDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtLiteralImpl]10));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]activeOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtCommentImpl]// New Drug order in future for same concept
        [CtTypeReferenceImpl]org.openmrs.TestOrder secondOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setPatient([CtInvocationImpl][CtVariableReadImpl]activeOrder.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setConcept([CtInvocationImpl][CtVariableReadImpl]activeOrder.getConcept());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setCareSetting([CtInvocationImpl][CtVariableReadImpl]activeOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtInvocationImpl][CtVariableReadImpl]activeOrder.getEffectiveStopDate(), [CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]secondOrder.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]secondOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtCommentImpl]// Revise Second Order to have scheduled date not overlapping with active order
        [CtTypeReferenceImpl]org.openmrs.TestOrder revision = [CtInvocationImpl][CtVariableReadImpl]secondOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revision.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtInvocationImpl][CtVariableReadImpl]activeOrder.getEffectiveStopDate(), [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]revision.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]revision.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order savedRevisionOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revision, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]savedRevisionOrder.getOrderId()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfAnActiveDrugOrderForTheSameConceptAndCareSettingButDifferentFormulationExists() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-drugOrdersWithSameConceptAndDifferentFormAndStrength.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order
        [CtTypeReferenceImpl]org.openmrs.DrugOrder existingOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1000)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]existingOrder.isActive());
        [CtLocalVariableImpl][CtCommentImpl]// New Drug order
        [CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtVariableReadImpl]existingOrder.getConcept());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtVariableReadImpl]existingOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]3001));
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingInstructions([CtLiteralImpl]"2 for 5 days");
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtLiteralImpl]10.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order savedDrugOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]savedDrugOrder.getOrderId()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldThrowAmbiguousOrderExceptionIfAnActiveDrugOrderForTheSameDrugFormulationExists() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-drugOrdersWithSameConceptAndDifferentFormAndStrength.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order for the same concept
        [CtTypeReferenceImpl]org.openmrs.DrugOrder existingOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1000)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]existingOrder.isActive());
        [CtLocalVariableImpl][CtCommentImpl]// New Drug order
        [CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtVariableReadImpl]existingOrder.getDrug());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtVariableReadImpl]existingOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingInstructions([CtLiteralImpl]"2 for 5 days");
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtLiteralImpl]10.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.AmbiguousOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.AmbiguousOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.cannot.have.more.than.one"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfAnActiveOrderForTheSameConceptExistsInADifferentCareSetting() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept cd4Count = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5497);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.TestOrder duplicateOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.TestOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7)));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.CareSetting inpatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2);
        [CtInvocationImpl]assertNotEquals([CtVariableReadImpl]inpatient, [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getCareSetting());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.isActive());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]cd4Count, [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getConcept());
        [CtLocalVariableImpl][CtTypeReferenceImpl]int initialActiveOrderCount = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null).size();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.TestOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtVariableReadImpl]cd4Count);
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtVariableReadImpl]inpatient);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> activeOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtUnaryOperatorImpl]++[CtVariableWriteImpl]initialActiveOrderCount, [CtInvocationImpl][CtVariableReadImpl]activeOrders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @throws ParseException
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldRollTheAutoExpireDateToTheEndOfTheDayIfItHasNoTimeComponent() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5089));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.DateFormat dateformat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"dd/MM/yyyy");
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtInvocationImpl][CtVariableReadImpl]dateformat.parse([CtLiteralImpl]"14/08/2014"));
        [CtInvocationImpl][CtVariableReadImpl]order.setAutoExpireDate([CtInvocationImpl][CtVariableReadImpl]dateformat.parse([CtLiteralImpl]"18/08/2014"));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtAssignmentImpl][CtVariableWriteImpl]dateformat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"dd/MM/yyyy HH:mm:ss.S");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]dateformat.parse([CtLiteralImpl]"18/08/2014 23:59:59.000"), [CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @throws ParseException
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotChangeTheAutoExpireDateIfItHasATimeComponent() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5089));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.DateFormat dateformat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"dd/MM/yyyy HH:mm:ss");
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtInvocationImpl][CtVariableReadImpl]dateformat.parse([CtLiteralImpl]"14/08/2014 10:00:00"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date autoExpireDate = [CtInvocationImpl][CtVariableReadImpl]dateformat.parse([CtLiteralImpl]"18/08/2014 10:00:00");
        [CtInvocationImpl][CtVariableReadImpl]order.setAutoExpireDate([CtVariableReadImpl]autoExpireDate);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]autoExpireDate, [CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfAnActiveDrugOrderForTheSameDrugFormulationExistsBeyondSchedule() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-DrugOrders.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder existingOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]2000)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int initialActiveOrderCount = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null).size();
        [CtLocalVariableImpl][CtCommentImpl]// New Drug order
        [CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtVariableReadImpl]existingOrder.getDrug());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtVariableReadImpl]existingOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingInstructions([CtLiteralImpl]"2 for 10 days");
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtLiteralImpl]10.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtLiteralImpl]2);
        [CtInvocationImpl][CtVariableReadImpl]order.setUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE);
        [CtInvocationImpl][CtVariableReadImpl]order.setScheduledDate([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtInvocationImpl][CtVariableReadImpl]existingOrder.getDateStopped(), [CtLiteralImpl]1));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> activeOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getActiveOrders([CtVariableReadImpl]patient, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtUnaryOperatorImpl]++[CtVariableWriteImpl]initialActiveOrderCount, [CtInvocationImpl][CtVariableReadImpl]activeOrders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderType(Integer)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderType_shouldFindOrderTypeObjectGivenValidId() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Drug order", [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1).getName());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderType(Integer)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderType_shouldReturnNullIfNoOrderTypeObjectFoundWithGivenId() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1000);
        [CtInvocationImpl]assertNull([CtVariableReadImpl]orderType);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderTypeByUuid(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderTypeByUuid_shouldFindOrderTypeObjectGivenValidUuid() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByUuid([CtLiteralImpl]"131168f4-15f5-102d-96e4-000c29c2a5d7");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Drug order", [CtInvocationImpl][CtVariableReadImpl]orderType.getName());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderTypeByUuid(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderTypeByUuid_shouldReturnNullIfNoOrderTypeObjectFoundWithGivenUuid() [CtBlockImpl]{
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByUuid([CtLiteralImpl]"some random uuid"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderTypes(boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderTypes_shouldGetAllOrderTypesIfIncludeRetiredIsSetToTrue() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtLiteralImpl]14, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypes([CtLiteralImpl]true).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderTypes(boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderTypes_shouldGetAllNonRetiredOrderTypesIfIncludeRetiredIsSetToFalse() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtLiteralImpl]11, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypes([CtLiteralImpl]false).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderTypeByName(String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderTypeByName_shouldReturnTheOrderTypeThatMatchesTheSpecifiedName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByName([CtLiteralImpl]"Drug order");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"131168f4-15f5-102d-96e4-000c29c2a5d7", [CtInvocationImpl][CtVariableReadImpl]orderType.getUuid());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrders(org.openmrs.Patient, org.openmrs.CareSetting,
    org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldFailIfPatientIsNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getOrders([CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]false));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Patient is required"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrders(org.openmrs.Patient, org.openmrs.CareSetting,
    org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldFailIfCareSettingIsNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getOrders([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Patient(), [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]false));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"CareSetting is required"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrders(org.openmrs.Patient, org.openmrs.CareSetting,
    org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetTheOrdersThatMatchAllTheArguments() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting outPatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType testOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> testOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]outPatient, [CtVariableReadImpl]testOrderType, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]testOrders.size());
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]testOrders, [CtLiteralImpl]6);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]testOrders, [CtLiteralImpl]7);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]testOrders, [CtLiteralImpl]9);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType drugOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> drugOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]outPatient, [CtVariableReadImpl]drugOrderType, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtVariableReadImpl]drugOrders.size());
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]drugOrders, [CtLiteralImpl]2);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]drugOrders, [CtLiteralImpl]3);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]drugOrders, [CtLiteralImpl]44);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]drugOrders, [CtLiteralImpl]444);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.containsId([CtVariableReadImpl]drugOrders, [CtLiteralImpl]5);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting inPatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> inPatientDrugOrders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]inPatient, [CtVariableReadImpl]drugOrderType, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]222, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inPatientDrugOrders.get([CtLiteralImpl]0).getOrderId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrders(org.openmrs.Patient, org.openmrs.CareSetting,
    org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetAllUnvoidedMatchesIfIncludeVoidedIsSetToFalse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting outPatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType testOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]outPatient, [CtVariableReadImpl]testOrderType, [CtLiteralImpl]false).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrders(org.openmrs.Patient, org.openmrs.CareSetting,
    org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldIncludeVoidedMatchesIfIncludeVoidedIsSetToTrue() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting outPatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType testOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]outPatient, [CtVariableReadImpl]testOrderType, [CtLiteralImpl]true).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrders(org.openmrs.Patient, org.openmrs.CareSetting,
    org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldIncludeOrdersForSubTypesIfOrderTypeIsSpecified() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-otherOrders.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType testOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting outPatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]outPatient, [CtVariableReadImpl]testOrderType, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]7, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrder1 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]6), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]9), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]101), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]102), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]103), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]104) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrder1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType labTestOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]7);
        [CtAssignmentImpl][CtVariableWriteImpl]orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]outPatient, [CtVariableReadImpl]labTestOrderType, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]patient, [CtVariableReadImpl]outPatient, [CtVariableReadImpl]labTestOrderType, [CtLiteralImpl]false).size());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.openmrs.Order[] expectedOrder2 = [CtNewArrayImpl]new org.openmrs.Order[]{ [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]101), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]103), [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]104) };
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]orders, [CtInvocationImpl]Matchers.hasItems([CtVariableReadImpl]expectedOrder2));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetOrdersByPatient() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setPatient([CtVariableReadImpl]patient).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]11, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetStoppedOrders() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setIsStopped([CtLiteralImpl]true).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldReturnOrdersAutoExpiredBeforeDate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date autoExpireOnOrBeforeDate = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.GregorianCalendar([CtLiteralImpl]2008, [CtLiteralImpl]9, [CtLiteralImpl]30).getTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setAutoExpireOnOrBeforeDate([CtVariableReadImpl]autoExpireOnOrBeforeDate).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate());
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]autoExpireOnOrBeforeDate.after([CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate()));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldReturnOnlyCanceledOrAutoExpiredOrdersBeforeDate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date canceledOrExpiredOnOrBeforeDate = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.GregorianCalendar([CtLiteralImpl]2008, [CtLiteralImpl]9, [CtLiteralImpl]30).getTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setCanceledOrExpiredOnOrBeforeDate([CtVariableReadImpl]canceledOrExpiredOnOrBeforeDate).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]7, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]order.getDateStopped().before([CtVariableReadImpl]canceledOrExpiredOnOrBeforeDate)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate().before([CtVariableReadImpl]canceledOrExpiredOnOrBeforeDate)));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldNotReturnCanceledOrAutoExpiredOrders() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date today = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance().getTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setExcludeCanceledAndExpired([CtLiteralImpl]true).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]6, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getDateStopped() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]order.getDateStopped().after([CtVariableReadImpl]today))) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]order.getAutoExpireDate().after([CtVariableReadImpl]today))));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldreturnOrdersWithFulfillerStatusCompleted() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setFulfillerStatus([CtInvocationImpl][CtTypeAccessImpl]Order.FulfillerStatus.valueOf([CtLiteralImpl]"COMPLETED")).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertEquals([CtTypeAccessImpl]FulfillerStatus.COMPLETED, [CtInvocationImpl][CtVariableReadImpl]order.getFulfillerStatus());
            [CtInvocationImpl]assertTrue([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]order.getFulfillerStatus() == [CtFieldReadImpl]Order.FulfillerStatus.COMPLETED);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldReturnOrdersWithFulfillerStatusReceivedOrNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setFulfillerStatus([CtInvocationImpl][CtTypeAccessImpl]Order.FulfillerStatus.valueOf([CtLiteralImpl]"RECEIVED")).setIncludeNullFulfillerStatus([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Boolean([CtLiteralImpl]true)).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]12, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getFulfillerStatus() == [CtFieldReadImpl]Order.FulfillerStatus.RECEIVED) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]order.getFulfillerStatus() == [CtLiteralImpl]null));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldReturnOrdersWithFulfillerStatusNotNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setIncludeNullFulfillerStatus([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Boolean([CtLiteralImpl]false)).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]order.getFulfillerStatus() != [CtLiteralImpl]null);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldReturnOrdersWithFulfillerStatusNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setIncludeNullFulfillerStatus([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Boolean([CtLiteralImpl]true)).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]10, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]order.getFulfillerStatus());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldreturnDiscontinuedOrders() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setAction([CtInvocationImpl][CtTypeAccessImpl]Order.Action.valueOf([CtLiteralImpl]"DISCONTINUE")).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.DISCONTINUE, [CtInvocationImpl][CtVariableReadImpl]order.getAction());
            [CtInvocationImpl]assertTrue([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]order.getAction() == [CtFieldReadImpl]org.openmrs.Order.Action.DISCONTINUE);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldNotReturnDiscontinuedOrders() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setExcludeDiscontinueOrders([CtLiteralImpl]true).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]11, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order : [CtVariableReadImpl]orders) [CtBlockImpl]{
            [CtInvocationImpl]assertNotEquals([CtInvocationImpl][CtVariableReadImpl]order.getAction(), [CtTypeAccessImpl]org.openmrs.Order.Action.DISCONTINUE);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetOrdersByCareSetting() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting outPatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setCareSetting([CtVariableReadImpl]outPatient).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]12, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetOrdersByConcepts() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Concept> concepts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]concepts.add([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]88));[CtCommentImpl]// aspirin

        [CtInvocationImpl][CtVariableReadImpl]concepts.add([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]3));[CtCommentImpl]// cough syrup

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setConcepts([CtVariableReadImpl]concepts).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]6, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetOrdersByOrderTypes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderType> orderTypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]orderTypes.add([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1));[CtCommentImpl]// drug order

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setOrderTypes([CtVariableReadImpl]orderTypes).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]10, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetOrdersByActivatedOnOrBeforeDate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// should get orders activated any time on this day
        [CtTypeReferenceImpl]java.util.Date activatedOnOrBeforeDate = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.GregorianCalendar([CtLiteralImpl]2008, [CtLiteralImpl]7, [CtLiteralImpl]19).getTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setActivatedOnOrBeforeDate([CtVariableReadImpl]activatedOnOrBeforeDate).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]11, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetOrdersByActivatedOnOrAfterDate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// hour and minute should be ignored by search
        [CtTypeReferenceImpl]java.util.Date activatedOnOrAfterDate = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.GregorianCalendar([CtLiteralImpl]2008, [CtLiteralImpl]7, [CtLiteralImpl]19, [CtLiteralImpl]12, [CtLiteralImpl]0).getTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setActivatedOnOrAfterDate([CtVariableReadImpl]activatedOnOrAfterDate).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetOrdersByIncludeVoided() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setIncludeVoided([CtLiteralImpl]true).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]14, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#(OrderSearchCriteria)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetTheOrdersByCareSettingAndOrderType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting outPatient = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderType> orderTypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]orderTypes.add([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2));[CtCommentImpl]// test order type

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setCareSetting([CtVariableReadImpl]outPatient).setOrderTypes([CtVariableReadImpl]orderTypes).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]orders.size());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetTheOrdersByOrderNumber() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setOrderNumber([CtLiteralImpl]"ORD-7").build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"2c96f25c-4949-4f72-9931-d808fbc226df", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.iterator().next().getUuid());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetTheOrdersByOrderNumberEvenIfCaseDoesNotMatch() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setOrderNumber([CtLiteralImpl]"ord-7").build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"2c96f25c-4949-4f72-9931-d808fbc226df", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.iterator().next().getUuid());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetTheOrdersByAccessionNumber() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setAccessionNumber([CtLiteralImpl]"ACC-123").build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"e1f95924-697a-11e3-bd76-0800271c1b75", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.iterator().next().getUuid());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrders_shouldGetTheOrdersByAccessionNumberEvenIfCaseDoesNotMatch() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteria orderSearchCriteria = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.parameter.OrderSearchCriteriaBuilder().setAccessionNumber([CtLiteralImpl]"acc-123").build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrders([CtVariableReadImpl]orderSearchCriteria);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]orders.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"e1f95924-697a-11e3-bd76-0800271c1b75", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orders.iterator().next().getUuid());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getAllOrdersByPatient(org.openmrs.Patient)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getAllOrdersByPatient_shouldFailIfPatientIsNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]java.lang.IllegalArgumentException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.getAllOrdersByPatient([CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Patient is required"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getAllOrdersByPatient(org.openmrs.Patient)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getAllOrdersByPatient_shouldGetAllTheOrdersForTheSpecifiedPatient() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtLiteralImpl]12, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getAllOrdersByPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2)).size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getAllOrdersByPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7)).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetOrderTypeIfNullButMappedToTheConceptClass() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.TestOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5497));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]order.getOrderType().getOrderTypeId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfOrderTypeIsNullAndNotMappedToTheConceptClass() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Order();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]9));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.OrderEntryException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.OrderEntryException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.type.cannot.determine"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrderType(org.openmrs.OrderType)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrderType_shouldAddANewOrderTypeToTheDatabase() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int orderTypeCount = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypes([CtLiteralImpl]true).size();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.OrderType();
        [CtInvocationImpl][CtVariableReadImpl]orderType.setName([CtLiteralImpl]"New Order");
        [CtInvocationImpl][CtVariableReadImpl]orderType.setJavaClassName([CtLiteralImpl]"org.openmrs.NewTestOrder");
        [CtInvocationImpl][CtVariableReadImpl]orderType.setDescription([CtLiteralImpl]"New order type for testing");
        [CtInvocationImpl][CtVariableReadImpl]orderType.setRetired([CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrderType([CtVariableReadImpl]orderType);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderType);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"New Order", [CtInvocationImpl][CtVariableReadImpl]orderType.getName());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getId());
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtVariableReadImpl]orderTypeCount + [CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypes([CtLiteralImpl]true).size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrderType(org.openmrs.OrderType)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrderType_shouldEditAnExistingOrderType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getDateChanged());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getChangedBy());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String newDescription = [CtLiteralImpl]"new";
        [CtInvocationImpl][CtVariableReadImpl]orderType.setDescription([CtVariableReadImpl]newDescription);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrderType([CtVariableReadImpl]orderType);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getDateChanged());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getChangedBy());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#purgeOrderType(org.openmrs.OrderType)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void purgeOrderType_shouldDeleteOrderTypeIfNotInUse() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Integer id = [CtLiteralImpl]13;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtVariableReadImpl]id);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderType);
        [CtInvocationImpl][CtFieldReadImpl]orderService.purgeOrderType([CtVariableReadImpl]orderType);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtVariableReadImpl]id));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#purgeOrderType(org.openmrs.OrderType)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void purgeOrderType_shouldNotAllowDeletingAnOrderTypeThatIsInUse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]1);
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderType);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotDeleteObjectInUseException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotDeleteObjectInUseException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.purgeOrderType([CtVariableReadImpl]orderType));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.type.cannot.delete")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#retireOrderType(org.openmrs.OrderType, String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void retireOrderType_shouldRetireOrderType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]15);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]orderType.getRetired());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetiredBy());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetireReason());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getDateRetired());
        [CtInvocationImpl][CtFieldReadImpl]orderService.retireOrderType([CtVariableReadImpl]orderType, [CtLiteralImpl]"Retire for testing purposes");
        [CtAssignmentImpl][CtVariableWriteImpl]orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]15);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]orderType.getRetired());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetiredBy());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetireReason());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getDateRetired());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#unretireOrderType(org.openmrs.OrderType)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void unretireOrderType_shouldUnretireOrderType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]16);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]orderType.getRetired());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetiredBy());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetireReason());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderType.getDateRetired());
        [CtInvocationImpl][CtFieldReadImpl]orderService.unretireOrderType([CtVariableReadImpl]orderType);
        [CtAssignmentImpl][CtVariableWriteImpl]orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]16);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]orderType.getRetired());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetiredBy());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getRetireReason());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]orderType.getDateRetired());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getSubtypes(org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderSubTypes_shouldGetAllSubOrderTypesWithRetiredOrderTypes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderType> orderTypeList = [CtInvocationImpl][CtFieldReadImpl]orderService.getSubtypes([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2), [CtLiteralImpl]true);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]7, [CtInvocationImpl][CtVariableReadImpl]orderTypeList.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getSubtypes(org.openmrs.OrderType, boolean)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderSubTypes_shouldGetAllSubOrderTypesWithoutRetiredOrderTypes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.OrderType> orderTypeList = [CtInvocationImpl][CtFieldReadImpl]orderService.getSubtypes([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2), [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]6, [CtInvocationImpl][CtVariableReadImpl]orderTypeList.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldDefaultToCareSettingAndOrderTypeDefinedInTheOrderContextIfNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept trimune30 = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]792);
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtVariableReadImpl]trimune30);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType expectedOrderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting expectedCareSetting = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.OrderContext orderContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.OrderContext();
        [CtInvocationImpl][CtVariableReadImpl]orderContext.setOrderType([CtVariableReadImpl]expectedOrderType);
        [CtInvocationImpl][CtVariableReadImpl]orderContext.setCareSetting([CtVariableReadImpl]expectedCareSetting);
        [CtAssignmentImpl][CtVariableWriteImpl]order = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtVariableReadImpl]orderContext);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]expectedOrderType.getConceptClasses().contains([CtInvocationImpl][CtVariableReadImpl]trimune30.getConceptClass()));
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expectedOrderType, [CtInvocationImpl][CtVariableReadImpl]order.getOrderType());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expectedCareSetting, [CtInvocationImpl][CtVariableReadImpl]order.getCareSetting());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDiscontinuationOrder(Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDiscontinuationOrder_shouldReturnDiscontinuationOrderIfOrderHasBeenDiscontinued() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.discontinueOrder([CtVariableReadImpl]order, [CtLiteralImpl]"no reason", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1), [CtInvocationImpl][CtVariableReadImpl]order.getEncounter());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order foundDiscontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getDiscontinuationOrder([CtVariableReadImpl]order);
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]foundDiscontinuationOrder, [CtInvocationImpl]Matchers.is([CtVariableReadImpl]discontinuationOrder));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDiscontinuationOrder(Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDiscontinuationOrder_shouldReturnNullIfOrderHasNotBeenDiscontinued() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getDiscontinuationOrder([CtVariableReadImpl]order);
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]discontinuationOrder, [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.nullValue()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderTypeByConceptClass(ConceptClass)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderTypeByConceptClass_shouldGetOrderTypeMappedToTheGivenConceptClass() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByConceptClass([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConceptClass([CtLiteralImpl]1));
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderType);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orderType.getOrderTypeId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getOrderTypeByConcept(Concept)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getOrderTypeByConcept_shouldGetOrderTypeMappedToTheGivenConcept() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByConcept([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getConceptService().getConcept([CtLiteralImpl]5089));
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]orderType);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]orderType.getOrderTypeId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfConceptInPreviousOrderDoesNotMatchThatOfTheRevisedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept newConcept = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5089);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]previousOrder.getConcept().equals([CtVariableReadImpl]newConcept));
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtVariableReadImpl]newConcept);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"The orderable of the previous order and the new one order don't match"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfTheExistingDrugOrderMatchesTheConceptAndNotDrugOfTheRevisedOrder() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.DrugOrder orderToDiscontinue = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]5)));
        [CtLocalVariableImpl][CtCommentImpl]// create a different test drug
        [CtTypeReferenceImpl]org.openmrs.Drug discontinuationOrderDrug = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.Drug();
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrderDrug.setConcept([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getConcept());
        [CtAssignmentImpl][CtVariableWriteImpl]discontinuationOrderDrug = [CtInvocationImpl][CtFieldReadImpl]conceptService.saveDrug([CtVariableReadImpl]discontinuationOrderDrug);
        [CtInvocationImpl]assertNotEquals([CtVariableReadImpl]discontinuationOrderDrug, [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDrug());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.getDrug());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtInvocationImpl][CtVariableReadImpl]orderToDiscontinue.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtVariableReadImpl]discontinuationOrderDrug);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"The orderable of the previous order and the new one order don't match"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfTheOrderTypeOfThePreviousOrderDoesNotMatch() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtVariableReadImpl]order.cloneForDiscontinuing();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]7);
        [CtInvocationImpl]assertNotEquals([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getOrderType(), [CtVariableReadImpl]orderType);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtil.isType([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getOrderType(), [CtVariableReadImpl]orderType));
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setOrderType([CtVariableReadImpl]orderType);
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setOrderer([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getProviderService().getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setEncounter([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getEncounterService().getEncounter([CtLiteralImpl]6));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.type.doesnot.match")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfTheJavaTypeOfThePreviousOrderDoesNotMatch() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.db.hibernate.HibernateSessionFactoryBean sessionFactoryBean = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.api.db.hibernate.HibernateSessionFactoryBean) ([CtFieldReadImpl]applicationContext.getBean([CtLiteralImpl]"&sessionFactory")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hibernate.cfg.Configuration configuration = [CtInvocationImpl][CtVariableReadImpl]sessionFactoryBean.getConfiguration();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.db.hibernate.HibernateAdministrationDAO adminDAO = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.api.db.hibernate.HibernateAdministrationDAO) ([CtFieldReadImpl]applicationContext.getBean([CtLiteralImpl]"adminDAO")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hibernate.boot.registry.StandardServiceRegistry standardRegistry = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hibernate.boot.registry.StandardServiceRegistryBuilder().configure().applySettings([CtInvocationImpl][CtVariableReadImpl]configuration.getProperties()).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hibernate.boot.Metadata metaData = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hibernate.boot.MetadataSources([CtVariableReadImpl]standardRegistry).addAnnotatedClass([CtFieldReadImpl]org.openmrs.Allergy.class).addAnnotatedClass([CtFieldReadImpl]org.openmrs.Encounter.class).addAnnotatedClass([CtFieldReadImpl]org.openmrs.api.OrderServiceTest.SomeTestOrder.class).addAnnotatedClass([CtFieldReadImpl]org.openmrs.Diagnosis.class).addAnnotatedClass([CtFieldReadImpl]org.openmrs.Condition.class).addAnnotatedClass([CtFieldReadImpl]org.openmrs.Visit.class).getMetadataBuilder().build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]adminDAO.getClass().getDeclaredField([CtLiteralImpl]"metadata");
        [CtInvocationImpl][CtVariableReadImpl]field.setAccessible([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]field.set([CtVariableReadImpl]adminDAO, [CtVariableReadImpl]metaData);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.OrderServiceTest.SomeTestOrder();
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setCareSetting([CtInvocationImpl][CtVariableReadImpl]order.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setConcept([CtInvocationImpl][CtVariableReadImpl]order.getConcept());
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setAction([CtTypeAccessImpl]Action.DISCONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setPreviousOrder([CtVariableReadImpl]order);
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setPatient([CtInvocationImpl][CtVariableReadImpl]order.getPatient());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]order.getOrderType().getJavaClass().isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getClass()));
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setOrderType([CtInvocationImpl][CtVariableReadImpl]order.getOrderType());
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setOrderer([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getProviderService().getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setEncounter([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getEncounterService().getEncounter([CtLiteralImpl]6));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.class.doesnot.match")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfTheCareSettingOfThePreviousOrderDoesNotMatch() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.order.OrderUtilTest.isActiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtVariableReadImpl]order.cloneForDiscontinuing();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting careSetting = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2);
        [CtInvocationImpl]assertNotEquals([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getCareSetting(), [CtVariableReadImpl]careSetting);
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setCareSetting([CtVariableReadImpl]careSetting);
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setOrderer([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getProviderService().getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setEncounter([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getEncounterService().getEncounter([CtLiteralImpl]6));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.care.setting.doesnot.match")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetConceptForDrugOrdersIfNull() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.CareSetting careSetting = [CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByName([CtLiteralImpl]"Drug order");
        [CtLocalVariableImpl][CtCommentImpl]// place drug order
        [CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtVariableReadImpl]encounter);
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtVariableReadImpl]careSetting);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getProviderService().getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtInvocationImpl][CtVariableReadImpl]encounter.getEncounterDatetime());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderType([CtVariableReadImpl]orderType);
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setInstructions([CtLiteralImpl]"None");
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingInstructions([CtLiteralImpl]"Test Instruction");
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getOrderId());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see org.openmrs.api.OrderService#getDrugRoutes()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDrugRoutes_shouldGetDrugRoutesAssociatedConceptPrividedInGlobalProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Concept> drugRoutesList = [CtInvocationImpl][CtFieldReadImpl]orderService.getDrugRoutes();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]drugRoutesList.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]22, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]drugRoutesList.get([CtLiteralImpl]0).getConceptId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#voidOrder(org.openmrs.Order, String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void voidOrder_shouldVoidAnOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]order.getDateVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidedBy());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidReason());
        [CtInvocationImpl][CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]order, [CtLiteralImpl]"None");
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getDateVoided());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidedBy());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidReason());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#voidOrder(org.openmrs.Order, String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void voidOrder_shouldUnsetDateStoppedOfThePreviousOrderIfTheSpecifiedOrderIsADiscontinuation() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]22);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.DISCONTINUE, [CtInvocationImpl][CtVariableReadImpl]order.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtVariableReadImpl]order.getPreviousOrder();
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl][CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]order, [CtLiteralImpl]"None");
        [CtInvocationImpl][CtCommentImpl]// Ensures order interceptor is okay with all the changes
        [CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#voidOrder(org.openmrs.Order, String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void voidOrder_shouldUnsetDateStoppedOfThePreviousOrderIfTheSpecifiedOrderIsARevision() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.REVISE, [CtInvocationImpl][CtVariableReadImpl]order.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtVariableReadImpl]order.getPreviousOrder();
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl][CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]order, [CtLiteralImpl]"None");
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#unvoidOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void unvoidOrder_shouldUnvoidAnOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]8);
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getDateVoided());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidedBy());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidReason());
        [CtInvocationImpl][CtFieldReadImpl]orderService.unvoidOrder([CtVariableReadImpl]order);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]order.getDateVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidedBy());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]order.getVoidReason());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#unvoidOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void unvoidOrder_shouldStopThePreviousOrderIfTheSpecifiedOrderIsADiscontinuation() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]22);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.DISCONTINUE, [CtInvocationImpl][CtVariableReadImpl]order.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtVariableReadImpl]order.getPreviousOrder();
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl][CtCommentImpl]// void the DC order for testing purposes so we can unvoid it later
        [CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]order, [CtLiteralImpl]"None");
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl][CtFieldReadImpl]orderService.unvoidOrder([CtVariableReadImpl]order);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#unvoidOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void unvoidOrder_shouldStopThePreviousOrderIfTheSpecifiedOrderIsARevision() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.REVISE, [CtInvocationImpl][CtVariableReadImpl]order.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtVariableReadImpl]order.getPreviousOrder();
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl][CtCommentImpl]// void the revise order for testing purposes so we can unvoid it later
        [CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]order, [CtLiteralImpl]"None");
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl][CtFieldReadImpl]orderService.unvoidOrder([CtVariableReadImpl]order);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @throws InterruptedException
     * @see OrderService#unvoidOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void unvoidOrder_shouldFailForADiscontinuationOrderIfThePreviousOrderIsInactive() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]22);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.DISCONTINUE, [CtInvocationImpl][CtVariableReadImpl]order.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtVariableReadImpl]order.getPreviousOrder();
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl][CtCommentImpl]// void the DC order for testing purposes so we can unvoid it later
        [CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]order, [CtLiteralImpl]"None");
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl][CtCommentImpl]// stop the order with a different DC order
        [CtFieldReadImpl]orderService.discontinueOrder([CtVariableReadImpl]previousOrder, [CtLiteralImpl]"Testing", [CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]previousOrder.getOrderer(), [CtInvocationImpl][CtVariableReadImpl]previousOrder.getEncounter());
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]10);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotUnvoidOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotUnvoidOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.unvoidOrder([CtVariableReadImpl]order));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.action.cannot.unvoid", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"discontinuation" }, [CtLiteralImpl]null)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @throws InterruptedException
     * @see OrderService#unvoidOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void unvoidOrder_shouldFailForAReviseOrderIfThePreviousOrderIsInactive() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]Action.REVISE, [CtInvocationImpl][CtVariableReadImpl]order.getAction());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtVariableReadImpl]order.getPreviousOrder();
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl][CtCommentImpl]// void the DC order for testing purposes so we can unvoid it later
        [CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]order, [CtLiteralImpl]"None");
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]order.getVoided());
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped());
        [CtLocalVariableImpl][CtCommentImpl]// stop the order with a different REVISE order
        [CtTypeReferenceImpl]org.openmrs.Order revise = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revise.setOrderer([CtInvocationImpl][CtVariableReadImpl]order.getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]revise.setEncounter([CtInvocationImpl][CtVariableReadImpl]order.getEncounter());
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revise, [CtLiteralImpl]null);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]10);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotUnvoidOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotUnvoidOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.unvoidOrder([CtVariableReadImpl]order));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.action.cannot.unvoid", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"revision" }, [CtLiteralImpl]null)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getRevisionOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getRevisionOrder_shouldReturnRevisionOrderIfOrderHasBeenRevised() [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111), [CtInvocationImpl][CtFieldReadImpl]orderService.getRevisionOrder([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getRevisionOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getRevisionOrder_shouldReturnNullIfOrderHasNotBeenRevised() [CtBlockImpl]{
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getRevisionOrder([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]444)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDiscontinuationOrder(Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDiscontinuationOrder_shouldReturnNullIfDcOrderIsVoided() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinueOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.discontinueOrder([CtVariableReadImpl]order, [CtLiteralImpl]"Some reason", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1), [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3));
        [CtInvocationImpl][CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]discontinueOrder, [CtLiteralImpl]"Invalid reason");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getDiscontinuationOrder([CtVariableReadImpl]order);
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]discontinuationOrder, [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.nullValue()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDrugDispensingUnits()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDrugDispensingUnits_shouldReturnTheUnionOfTheDosingAndDispensingUnits() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Concept> dispensingUnits = [CtInvocationImpl][CtFieldReadImpl]orderService.getDrugDispensingUnits();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]dispensingUnits.size());
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]dispensingUnits, [CtInvocationImpl]Matchers.containsInAnyOrder([CtInvocationImpl]OpenmrsMatchers.hasId([CtLiteralImpl]50), [CtInvocationImpl]OpenmrsMatchers.hasId([CtLiteralImpl]51)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDrugDispensingUnits()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDrugDispensingUnits_shouldReturnAnEmptyListIfNothingIsConfigured() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]adminService.saveGlobalProperty([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_DRUG_DISPENSING_UNITS_CONCEPT_UUID, [CtLiteralImpl]""));
        [CtInvocationImpl][CtFieldReadImpl]adminService.saveGlobalProperty([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_DRUG_DOSING_UNITS_CONCEPT_UUID, [CtLiteralImpl]""));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtFieldReadImpl]orderService.getDrugDispensingUnits(), [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.empty()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDrugDosingUnits()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDrugDosingUnits_shouldReturnAListIfGPIsSet() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Concept> dosingUnits = [CtInvocationImpl][CtFieldReadImpl]orderService.getDrugDosingUnits();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]dosingUnits.size());
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]dosingUnits, [CtInvocationImpl]Matchers.containsInAnyOrder([CtInvocationImpl]OpenmrsMatchers.hasId([CtLiteralImpl]50), [CtInvocationImpl]OpenmrsMatchers.hasId([CtLiteralImpl]51)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDrugDosingUnits()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDrugDosingUnits_shouldReturnAnEmptyListIfNothingIsConfigured() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]adminService.saveGlobalProperty([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_DRUG_DOSING_UNITS_CONCEPT_UUID, [CtLiteralImpl]""));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtFieldReadImpl]orderService.getDrugDosingUnits(), [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.empty()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDurationUnits()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDurationUnits_shouldReturnAListIfGPIsSet() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Concept> durationConcepts = [CtInvocationImpl][CtFieldReadImpl]orderService.getDurationUnits();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]durationConcepts.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]28, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]durationConcepts.get([CtLiteralImpl]0).getConceptId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getDurationUnits()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getDurationUnits_shouldReturnAnEmptyListIfNothingIsConfigured() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]adminService.saveGlobalProperty([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_DURATION_UNITS_CONCEPT_UUID, [CtLiteralImpl]""));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtFieldReadImpl]orderService.getDurationUnits(), [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.empty()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getRevisionOrder(org.openmrs.Order)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getRevisionOrder_shouldNotReturnAVoidedRevisionOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]7);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order revision1 = [CtInvocationImpl][CtVariableReadImpl]order.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revision1.setEncounter([CtInvocationImpl][CtVariableReadImpl]order.getEncounter());
        [CtInvocationImpl][CtVariableReadImpl]revision1.setOrderer([CtInvocationImpl][CtVariableReadImpl]order.getOrderer());
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revision1, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]revision1, [CtInvocationImpl][CtFieldReadImpl]orderService.getRevisionOrder([CtVariableReadImpl]order));
        [CtInvocationImpl][CtFieldReadImpl]orderService.voidOrder([CtVariableReadImpl]revision1, [CtLiteralImpl]"Testing");
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtFieldReadImpl]orderService.getRevisionOrder([CtVariableReadImpl]order), [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.nullValue()));
        [CtLocalVariableImpl][CtCommentImpl]// should return the new unvoided revision
        [CtTypeReferenceImpl]org.openmrs.Order revision2 = [CtInvocationImpl][CtVariableReadImpl]order.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revision2.setEncounter([CtInvocationImpl][CtVariableReadImpl]order.getEncounter());
        [CtInvocationImpl][CtVariableReadImpl]revision2.setOrderer([CtInvocationImpl][CtVariableReadImpl]order.getOrderer());
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revision2, [CtLiteralImpl]null);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]revision2, [CtInvocationImpl][CtFieldReadImpl]orderService.getRevisionOrder([CtVariableReadImpl]order));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassForADiscontinuationOrderWithNoPreviousOrder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.TestOrder dcOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]dcOrder.setAction([CtTypeAccessImpl]Action.DISCONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]dcOrder.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]dcOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]dcOrder.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]5089));
        [CtInvocationImpl][CtVariableReadImpl]dcOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]dcOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]dcOrder, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getTestSpecimenSources()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getTestSpecimenSources_shouldReturnAListIfGPIsSet() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openmrs.Concept> specimenSourceList = [CtInvocationImpl][CtFieldReadImpl]orderService.getTestSpecimenSources();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]specimenSourceList.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]22, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]specimenSourceList.get([CtLiteralImpl]0).getConceptId().intValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getTestSpecimenSources()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getTestSpecimenSources_shouldReturnAnEmptyListIfNothingIsConfigured() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]adminService.saveGlobalProperty([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_TEST_SPECIMEN_SOURCES_CONCEPT_UUID, [CtLiteralImpl]""));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtFieldReadImpl]orderService.getTestSpecimenSources(), [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.empty()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#retireOrderType(org.openmrs.OrderType, String)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void retireOrderType_shouldNotRetireIndependentField() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderType orderType = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrderType([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.ConceptClass conceptClass = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConceptClass([CtLiteralImpl]1);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]conceptClass.getRetired());
        [CtInvocationImpl][CtVariableReadImpl]orderType.addConceptClass([CtVariableReadImpl]conceptClass);
        [CtInvocationImpl][CtFieldReadImpl]orderService.retireOrderType([CtVariableReadImpl]orderType, [CtLiteralImpl]"test retire reason");
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]conceptClass.getRetired());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetOrderTypeOfDrugOrderToDrugOrderIfNotSetAndConceptNotMapped() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Drug drug = [CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept unmappedConcept = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]113);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByConcept([CtVariableReadImpl]unmappedConcept));
        [CtInvocationImpl][CtVariableReadImpl]drug.setConcept([CtVariableReadImpl]unmappedConcept);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder drugOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setEncounter([CtVariableReadImpl]encounter);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setOrderer([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getProviderService().getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDateActivated([CtInvocationImpl][CtVariableReadImpl]encounter.getEncounterDatetime());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDrug([CtVariableReadImpl]drug);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDosingType([CtFieldReadImpl]org.openmrs.SimpleDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDose([CtLiteralImpl]300.0);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDoseUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]50));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setQuantity([CtLiteralImpl]20.0);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setFrequency([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setRoute([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]22));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setNumRefills([CtLiteralImpl]10);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setOrderType([CtLiteralImpl]null);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]drugOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]drugOrder.getOrderType());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByUuid([CtTypeAccessImpl]OrderType.DRUG_ORDER_TYPE_UUID), [CtInvocationImpl][CtVariableReadImpl]drugOrder.getOrderType());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetOrderTypeOfTestOrderToTestOrderIfNotSetAndConceptNotMapped() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.TestOrder testOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.TestOrder();
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept unmappedConcept = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]113);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByConcept([CtVariableReadImpl]unmappedConcept));
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setConcept([CtVariableReadImpl]unmappedConcept);
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setEncounter([CtVariableReadImpl]encounter);
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setDateActivated([CtInvocationImpl][CtVariableReadImpl]encounter.getEncounterDatetime());
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setClinicalHistory([CtLiteralImpl]"Patient had a negative reaction to the test in the past");
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setFrequency([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setSpecimenSource([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]22));
        [CtInvocationImpl][CtVariableReadImpl]testOrder.setNumberOfRepeats([CtLiteralImpl]3);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]testOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]testOrder.getOrderType());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderTypeByUuid([CtTypeAccessImpl]OrderType.TEST_ORDER_TYPE_UUID), [CtInvocationImpl][CtVariableReadImpl]testOrder.getOrderType());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetAutoExpireDateOfDrugOrderIfAutoExpireDateIsNotSet() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-drugOrderAutoExpireDate.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Drug drug = [CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]3000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder drugOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setEncounter([CtVariableReadImpl]encounter);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setOrderer([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getProviderService().getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDrug([CtVariableReadImpl]drug);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDosingType([CtFieldReadImpl]org.openmrs.SimpleDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDose([CtLiteralImpl]300.0);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDoseUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]50));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setQuantity([CtLiteralImpl]20.0);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setFrequency([CtInvocationImpl][CtFieldReadImpl]orderService.getOrderFrequency([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setRoute([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]22));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setNumRefills([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setOrderType([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDateActivated([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.createDateTime([CtLiteralImpl]"2014-08-03"));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDuration([CtLiteralImpl]20);[CtCommentImpl]// 20 days

        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDurationUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]1001));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order savedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]drugOrder, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order loadedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]savedOrder.getId());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.openmrs.test.TestUtil.createDateTime([CtLiteralImpl]"2014-08-22 23:59:59"), [CtInvocationImpl][CtVariableReadImpl]loadedOrder.getAutoExpireDate());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldSetAutoExpireDateForReviseOrderWithSimpleDosingInstructions() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-drugOrderAutoExpireDate.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder originalOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]111)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder revisedOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setOrderer([CtInvocationImpl][CtVariableReadImpl]originalOrder.getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setEncounter([CtInvocationImpl][CtVariableReadImpl]originalOrder.getEncounter());
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setNumRefills([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setAutoExpireDate([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDuration([CtLiteralImpl]10);
        [CtInvocationImpl][CtVariableReadImpl]revisedOrder.setDurationUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]1001));
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]revisedOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]revisedOrder.getAutoExpireDate());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderServiceImpl#discontinueExistingOrdersIfNecessary()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldThrowAmbiguousOrderExceptionIfDisconnectingMultipleActiveOrdersForTheGivenConcepts() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-discontinueAmbiguousOrderByConcept.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Order.Action.DISCONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]88));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]9));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.AmbiguousOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderServiceImpl#discontinueExistingOrdersIfNecessary()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldThrowAmbiguousOrderExceptionIfDisconnectingMultipleActiveDrugOrdersWithTheSameDrug() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-ambiguousDrugOrders.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setAction([CtTypeAccessImpl]Order.Action.DISCONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderReasonNonCoded([CtLiteralImpl]"Discontinue this");
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtFieldReadImpl]conceptService.getDrug([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]9));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtFieldReadImpl]orderService.getCareSetting([CtLiteralImpl]1));
        [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.AmbiguousOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(org.openmrs.Order, OrderContext, org.openmrs.Order[])
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfAnKnownDrugOrderForTheSameDrugFormulationSpecified() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-drugOrdersWithSameConceptAndDifferentFormAndStrength.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order for the same concept
        [CtTypeReferenceImpl]org.openmrs.DrugOrder existingOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]1000)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]existingOrder.isActive());
        [CtLocalVariableImpl][CtCommentImpl]// New Drug order
        [CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtVariableReadImpl]existingOrder.getDrug());
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtVariableReadImpl]existingOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingType([CtFieldReadImpl]org.openmrs.FreeTextDosingInstructions.class);
        [CtInvocationImpl][CtVariableReadImpl]order.setDosingInstructions([CtLiteralImpl]"2 for 5 days");
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtLiteralImpl]10.0);
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]51));
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.OrderContext orderContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.OrderContext();
        [CtInvocationImpl][CtVariableReadImpl]orderContext.setAttribute([CtTypeAccessImpl]OrderService.PARALLEL_ORDERS, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtInvocationImpl][CtVariableReadImpl]existingOrder.getUuid() });
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtVariableReadImpl]orderContext);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]order.getOrderId()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getNonCodedDrugConcept()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getNonCodedDrugConcept_shouldReturnNullIfNothingIsConfigured() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]adminService.saveGlobalProperty([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.GlobalProperty([CtFieldReadImpl]org.openmrs.util.OpenmrsConstants.GP_DRUG_ORDER_DRUG_OTHER, [CtLiteralImpl]""));
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtFieldReadImpl]orderService.getNonCodedDrugConcept());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#getNonCodedDrugConcept()
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void getNonCodedDrugConcept_shouldReturnAConceptIfGPIsSet() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-nonCodedDrugs.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Concept nonCodedDrugConcept = [CtInvocationImpl][CtFieldReadImpl]orderService.getNonCodedDrugConcept();
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]nonCodedDrugConcept);
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]nonCodedDrugConcept.getConceptId(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]5584));
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nonCodedDrugConcept.getName().getName(), [CtLiteralImpl]"DRUG OTHER");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldPassIfAnActiveDrugOrderForTheSameConceptAndDifferentDrugNonCodedExists() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-nonCodedDrugs.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept nonCodedConcept = [CtInvocationImpl][CtFieldReadImpl]orderService.getNonCodedDrugConcept();
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order for the same concept
        [CtTypeReferenceImpl]org.openmrs.DrugOrder duplicateOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]584)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.isActive());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]nonCodedConcept, [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getConcept());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder drugOrder = [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.copy();
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDrugNonCoded([CtLiteralImpl]"non coded drug paracetemol");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order savedOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]drugOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]savedOrder.getOrderId()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see OrderService#saveOrder(Order, OrderContext)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfAnActiveDrugOrderForTheSameConceptAndDrugNonCodedAndCareSettingExists() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-nonCodedDrugs.xml");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept nonCodedConcept = [CtInvocationImpl][CtFieldReadImpl]orderService.getNonCodedDrugConcept();
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order for the same concept
        [CtTypeReferenceImpl]org.openmrs.DrugOrder duplicateOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]584)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.isActive());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]nonCodedConcept, [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getConcept());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder drugOrder = [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.copy();
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDrugNonCoded([CtLiteralImpl]"non coded drug crocine");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.AmbiguousOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.AmbiguousOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]drugOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.cannot.have.more.than.one"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldDiscontinuePreviousNonCodedOrderIfItIsNotAlreadyDiscontinued() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// We are trying to discontinue order id 584 in OrderServiceTest-nonCodedDrugs.xml
        executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-nonCodedDrugs.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder previousOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]584)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder drugOrder = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForDiscontinuing();
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setPreviousOrder([CtVariableReadImpl]previousOrder);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setOrderer([CtInvocationImpl][CtVariableReadImpl]previousOrder.getOrderer());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setEncounter([CtInvocationImpl][CtVariableReadImpl]previousOrder.getEncounter());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order saveOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]drugOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]previousOrder.getDateStopped(), [CtLiteralImpl]"previous order should be discontinued");
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]saveOrder.getOrderId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailDiscontinueNonCodedDrugOrderIfOrderableOfPreviousAndNewOrderDontMatch() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-nonCodedDrugs.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder previousOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]584)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder drugOrder = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForDiscontinuing();
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDrugNonCoded([CtLiteralImpl]"non coded drug citrigine");
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setPreviousOrder([CtVariableReadImpl]previousOrder);
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]drugOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]drugOrder, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"The orderable of the previous order and the new one order don't match"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldFailIfDrugNonCodedInPreviousDrugOrderDoesNotMatchThatOfTheRevisedDrugOrder() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-nonCodedDrugs.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder previousOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]584)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForRevision();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String drugNonCodedParacetemol = [CtLiteralImpl]"non coded aspirin";
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]previousOrder.getDrugNonCoded().equals([CtVariableReadImpl]drugNonCodedParacetemol));
        [CtInvocationImpl][CtVariableReadImpl]order.setDrugNonCoded([CtVariableReadImpl]drugNonCodedParacetemol);
        [CtInvocationImpl][CtVariableReadImpl]order.setPreviousOrder([CtVariableReadImpl]previousOrder);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.EditedOrderDoesNotMatchPreviousException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"The orderable of the previous order and the new one order don't match"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldRevisePreviousNonCodedOrderIfItIsAlreadyExisting() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// We are trying to discontinue order id 584 in OrderServiceTest-nonCodedDrugs.xml
        executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-nonCodedDrugs.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder previousOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]584)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]order.setAsNeeded([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]order.setPreviousOrder([CtVariableReadImpl]previousOrder);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder saveOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.saveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]saveOrder.getAsNeeded());
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtInvocationImpl][CtVariableReadImpl]saveOrder.getOrderId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveRetrospectiveOrder_shouldDiscontinueOrderInRetrospectiveEntry() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-ordersWithAutoExpireDate.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd hh:mm:ss.S");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date originalOrderDateActivated = [CtInvocationImpl][CtVariableReadImpl]dateFormat.parse([CtLiteralImpl]"2008-11-19 09:24:10.0");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date discontinuationOrderDate = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtVariableReadImpl]originalOrderDateActivated, [CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]201);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]originalOrder.getDateStopped());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]dateFormat.parse([CtLiteralImpl]"2008-11-23 09:24:09.0"), [CtInvocationImpl][CtVariableReadImpl]originalOrder.getAutoExpireDate());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive([CtVariableReadImpl]discontinuationOrderDate));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinueationOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForDiscontinuing();
        [CtInvocationImpl][CtVariableReadImpl]discontinueationOrder.setPreviousOrder([CtVariableReadImpl]originalOrder);
        [CtInvocationImpl][CtVariableReadImpl]discontinueationOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]17));
        [CtInvocationImpl][CtVariableReadImpl]discontinueationOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]discontinueationOrder.setDateActivated([CtVariableReadImpl]discontinuationOrderDate);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveRetrospectiveOrder([CtVariableReadImpl]discontinueationOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]originalOrder.getDateStopped());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinueationOrder.getAutoExpireDate(), [CtInvocationImpl][CtVariableReadImpl]discontinueationOrder.getDateActivated());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveRetrospectiveOrder_shouldDiscontinueAndStopActiveOrderInRetrospectiveEntry() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-ordersWithAutoExpireDate.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd hh:mm:ss.S");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date originalOrderDateActivated = [CtInvocationImpl][CtVariableReadImpl]dateFormat.parse([CtLiteralImpl]"2008-11-19 09:24:10.0");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date discontinuationOrderDate = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateUtils.addDays([CtVariableReadImpl]originalOrderDateActivated, [CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order originalOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]202);
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]originalOrder.getDateStopped());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]dateFormat.parse([CtLiteralImpl]"2008-11-23 09:24:09.0"), [CtInvocationImpl][CtVariableReadImpl]originalOrder.getAutoExpireDate());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]originalOrder.isActive([CtVariableReadImpl]discontinuationOrderDate));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order discontinuationOrder = [CtInvocationImpl][CtVariableReadImpl]originalOrder.cloneForDiscontinuing();
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setPreviousOrder([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]17));
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.setDateActivated([CtVariableReadImpl]discontinuationOrderDate);
        [CtInvocationImpl][CtFieldReadImpl]orderService.saveRetrospectiveOrder([CtVariableReadImpl]discontinuationOrder, [CtLiteralImpl]null);
        [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]originalOrder.getDateStopped());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getAutoExpireDate(), [CtInvocationImpl][CtVariableReadImpl]discontinuationOrder.getDateActivated());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveOrder_shouldNotRevisePreviousIfAlreadyStopped() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-ordersWithAutoExpireDate.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order previousOrder = [CtInvocationImpl][CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]203);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date dateActivated = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd hh:mm:ss").parse([CtLiteralImpl]"2008-10-19 13:00:00");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order order = [CtInvocationImpl][CtVariableReadImpl]previousOrder.cloneForRevision();
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtVariableReadImpl]dateActivated);
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]18));
        [CtInvocationImpl][CtVariableReadImpl]order.setPreviousOrder([CtVariableReadImpl]previousOrder);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.CannotStopInactiveOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.CannotStopInactiveOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveRetrospectiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtInvocationImpl][CtFieldReadImpl]messageSourceService.getMessage([CtLiteralImpl]"Order.cannot.discontinue.inactive")));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void saveRetrospectiveOrder_shouldFailIfAnActiveDrugOrderForTheSameConceptAndCareSettingExistsAtOrderDateActivated() throws [CtTypeReferenceImpl]java.text.ParseException [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtLiteralImpl]"org/openmrs/api/include/OrderServiceTest-ordersWithAutoExpireDate.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date newOrderDateActivated = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd hh:mm:ss").parse([CtLiteralImpl]"2008-11-19 13:00:10");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Patient patient = [CtInvocationImpl][CtFieldReadImpl]patientService.getPatient([CtLiteralImpl]12);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openmrs.Concept orderConcept = [CtInvocationImpl][CtFieldReadImpl]conceptService.getConcept([CtLiteralImpl]88);
        [CtLocalVariableImpl][CtCommentImpl]// sanity check that we have an active order for the same concept
        [CtTypeReferenceImpl]org.openmrs.DrugOrder duplicateOrder = [CtInvocationImpl](([CtTypeReferenceImpl]org.openmrs.DrugOrder) ([CtFieldReadImpl]orderService.getOrder([CtLiteralImpl]202)));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.isActive([CtVariableReadImpl]newOrderDateActivated));
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]orderConcept, [CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getConcept());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.DrugOrder order = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.DrugOrder();
        [CtInvocationImpl][CtVariableReadImpl]order.setPatient([CtVariableReadImpl]patient);
        [CtInvocationImpl][CtVariableReadImpl]order.setConcept([CtVariableReadImpl]orderConcept);
        [CtInvocationImpl][CtVariableReadImpl]order.setEncounter([CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]17));
        [CtInvocationImpl][CtVariableReadImpl]order.setOrderer([CtInvocationImpl][CtFieldReadImpl]providerService.getProvider([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]order.setCareSetting([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getCareSetting());
        [CtInvocationImpl][CtVariableReadImpl]order.setDateActivated([CtVariableReadImpl]newOrderDateActivated);
        [CtInvocationImpl][CtVariableReadImpl]order.setDrug([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getDrug());
        [CtInvocationImpl][CtVariableReadImpl]order.setDose([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getDose());
        [CtInvocationImpl][CtVariableReadImpl]order.setDoseUnits([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getDoseUnits());
        [CtInvocationImpl][CtVariableReadImpl]order.setRoute([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getRoute());
        [CtInvocationImpl][CtVariableReadImpl]order.setFrequency([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getFrequency());
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantity([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getQuantity());
        [CtInvocationImpl][CtVariableReadImpl]order.setQuantityUnits([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getQuantityUnits());
        [CtInvocationImpl][CtVariableReadImpl]order.setNumRefills([CtInvocationImpl][CtVariableReadImpl]duplicateOrder.getNumRefills());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.AmbiguousOrderException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.AmbiguousOrderException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]orderService.saveRetrospectiveOrder([CtVariableReadImpl]order, [CtLiteralImpl]null));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Order.cannot.have.more.than.one"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldSaveOrdersWithSortWeightWhenWithinAOrderGroup() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtFieldReadImpl]org.openmrs.api.OrderServiceTest.ORDER_SET);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderSet orderSet = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderSetService().getOrderSet([CtLiteralImpl]2000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup orderGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.OrderGroup();
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setOrderSet([CtVariableReadImpl]orderSet);
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setPatient([CtInvocationImpl][CtVariableReadImpl]encounter.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setEncounter([CtVariableReadImpl]encounter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order firstOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order secondOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1001).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order orderWithoutOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]firstOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]secondOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]orderWithoutOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]encounter.setOrders([CtVariableReadImpl]orders);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup og : [CtInvocationImpl][CtVariableReadImpl]encounter.getOrderGroups()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]og.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrderGroup([CtVariableReadImpl]og);
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order o : [CtInvocationImpl][CtVariableReadImpl]encounter.getOrdersWithoutOrderGroups()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]o.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrder([CtVariableReadImpl]o, [CtLiteralImpl]null);
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup savedOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderGroupByUuid([CtInvocationImpl][CtVariableReadImpl]orderGroup.getUuid());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order savedOrder = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderByUuid([CtInvocationImpl][CtVariableReadImpl]orderWithoutOrderGroup.getUuid());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]firstOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getUuid(), [CtLiteralImpl]"The first order in  savedOrderGroup is the same which is sent first in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]secondOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getUuid(), [CtLiteralImpl]"The second order in  savedOrderGroup is the same which is sent second in the List");
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]savedOrder.getSortWeight(), [CtLiteralImpl]"The order which doesn't belong to an orderGroup has no sortWeight");
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The first order has a lower sortWeight than the second", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getSortWeight()), [CtInvocationImpl]Matchers.is([CtUnaryOperatorImpl]-[CtLiteralImpl]1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldSetTheCorrectSortWeightWhenAddingAnOrderInOrderGroup() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtFieldReadImpl]org.openmrs.api.OrderServiceTest.ORDER_SET);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderSet orderSet = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderSetService().getOrderSet([CtLiteralImpl]2000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup orderGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.OrderGroup();
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setOrderSet([CtVariableReadImpl]orderSet);
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setPatient([CtInvocationImpl][CtVariableReadImpl]encounter.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setEncounter([CtVariableReadImpl]encounter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order firstOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order secondOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1001).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]firstOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]secondOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]encounter.setOrders([CtVariableReadImpl]orders);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup og : [CtInvocationImpl][CtVariableReadImpl]encounter.getOrderGroups()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]og.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrderGroup([CtVariableReadImpl]og);
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup savedOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderGroupByUuid([CtInvocationImpl][CtVariableReadImpl]orderGroup.getUuid());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]firstOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getUuid(), [CtLiteralImpl]"The first order in  savedOrderGroup is the same which is sent first in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]secondOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getUuid(), [CtLiteralImpl]"The second order in  savedOrderGroup is the same which is sent second in the List");
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The first order has a lower sortWeight than the second", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getSortWeight()), [CtInvocationImpl]Matchers.is([CtUnaryOperatorImpl]-[CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order newOrderWithoutAnyPosition = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]savedOrderGroup).build();
        [CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.addOrder([CtVariableReadImpl]newOrderWithoutAnyPosition);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrderGroup([CtVariableReadImpl]savedOrderGroup);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup secondSavedOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderGroupByUuid([CtInvocationImpl][CtVariableReadImpl]orderGroup.getUuid());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]firstOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getUuid(), [CtLiteralImpl]"The first order in  savedOrderGroup is the same which is sent first in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]secondOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getUuid(), [CtLiteralImpl]"The second order in  savedOrderGroup is the same which is sent second in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().get([CtLiteralImpl]2).getUuid(), [CtInvocationImpl][CtVariableReadImpl]newOrderWithoutAnyPosition.getUuid(), [CtLiteralImpl]"The third order in  savedOrderGroup is the same which is sent third in the List");
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The third order has a higher sortWeight than the second", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]2).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getSortWeight()), [CtInvocationImpl]Matchers.is([CtLiteralImpl]1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldSetTheCorrectSortWeightWhenAddingAnOrderAtAPosition() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtFieldReadImpl]org.openmrs.api.OrderServiceTest.ORDER_SET);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderSet orderSet = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderSetService().getOrderSet([CtLiteralImpl]2000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup orderGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.OrderGroup();
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setOrderSet([CtVariableReadImpl]orderSet);
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setPatient([CtInvocationImpl][CtVariableReadImpl]encounter.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setEncounter([CtVariableReadImpl]encounter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order firstOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order secondOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1001).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]firstOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]secondOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]encounter.setOrders([CtVariableReadImpl]orders);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup og : [CtInvocationImpl][CtVariableReadImpl]encounter.getOrderGroups()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]og.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrderGroup([CtVariableReadImpl]og);
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup savedOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderGroupByUuid([CtInvocationImpl][CtVariableReadImpl]orderGroup.getUuid());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]firstOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getUuid(), [CtLiteralImpl]"The first order in  savedOrderGroup is the same which is sent first in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]secondOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getUuid(), [CtLiteralImpl]"The second order in  savedOrderGroup is the same which is sent second in the List");
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The first order has a lower sortWeight than the second", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getSortWeight()), [CtInvocationImpl]Matchers.is([CtUnaryOperatorImpl]-[CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order newOrderAtPosition1 = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]savedOrderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order newOrderAtPosition2 = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]savedOrderGroup).build();
        [CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.addOrder([CtVariableReadImpl]newOrderAtPosition1, [CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.addOrder([CtVariableReadImpl]newOrderAtPosition2, [CtLiteralImpl]1);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrderGroup([CtVariableReadImpl]savedOrderGroup);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup secondSavedOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderGroupByUuid([CtInvocationImpl][CtVariableReadImpl]orderGroup.getUuid());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().size());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]newOrderAtPosition1.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().get([CtLiteralImpl]0).getUuid(), [CtLiteralImpl]"The first order in  savedOrderGroup is the same which is sent first in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]newOrderAtPosition2.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().get([CtLiteralImpl]1).getUuid(), [CtLiteralImpl]"The second order in  savedOrderGroup is the same which is sent second in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]firstOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]2).getUuid(), [CtLiteralImpl]"The third order in  savedOrderGroup is the same which is sent third in the List");
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]secondOrderWithOrderGroup.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]3).getUuid(), [CtLiteralImpl]"The fourth order in  savedOrderGroup is the same which is sent first in the List");
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The third order has a lower sortWeight than the fourth", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]2).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]3).getSortWeight()), [CtInvocationImpl]Matchers.is([CtUnaryOperatorImpl]-[CtLiteralImpl]1));
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The second order has a lower sortWeight than the third", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]2).getSortWeight()), [CtInvocationImpl]Matchers.is([CtUnaryOperatorImpl]-[CtLiteralImpl]1));
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The first order has a lower sortWeight than the second", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]0).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.getOrders().get([CtLiteralImpl]1).getSortWeight()), [CtInvocationImpl]Matchers.is([CtUnaryOperatorImpl]-[CtLiteralImpl]1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldSetTheCorrectSortWeightWhenAddingAnOrderWithANegativePosition() [CtBlockImpl]{
        [CtInvocationImpl]executeDataSet([CtFieldReadImpl]org.openmrs.api.OrderServiceTest.ORDER_SET);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Encounter encounter = [CtInvocationImpl][CtFieldReadImpl]encounterService.getEncounter([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderSet orderSet = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderSetService().getOrderSet([CtLiteralImpl]2000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup orderGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.OrderGroup();
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setOrderSet([CtVariableReadImpl]orderSet);
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setPatient([CtInvocationImpl][CtVariableReadImpl]encounter.getPatient());
        [CtInvocationImpl][CtVariableReadImpl]orderGroup.setEncounter([CtVariableReadImpl]encounter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order firstOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order secondOrderWithOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1001).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]orderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.openmrs.Order> orders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]firstOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]orders.add([CtVariableReadImpl]secondOrderWithOrderGroup);
        [CtInvocationImpl][CtVariableReadImpl]encounter.setOrders([CtVariableReadImpl]orders);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup og : [CtInvocationImpl][CtVariableReadImpl]encounter.getOrderGroups()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]og.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrderGroup([CtVariableReadImpl]og);
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup savedOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderGroupByUuid([CtInvocationImpl][CtVariableReadImpl]orderGroup.getUuid());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order newOrderWithNegativePosition = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]savedOrderGroup).build();
        [CtInvocationImpl][CtVariableReadImpl]savedOrderGroup.addOrder([CtVariableReadImpl]newOrderWithNegativePosition, [CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().saveOrderGroup([CtVariableReadImpl]savedOrderGroup);
        [CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.flushSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.OrderGroup secondSavedOrderGroup = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openmrs.api.context.Context.getOrderService().getOrderGroupByUuid([CtInvocationImpl][CtVariableReadImpl]orderGroup.getUuid());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().size());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]newOrderWithNegativePosition.getUuid(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().get([CtLiteralImpl]2).getUuid(), [CtLiteralImpl]"The new order gets added at the last position");
        [CtInvocationImpl]MatcherAssert.assertThat([CtLiteralImpl]"The new order has a higher sortWeight than the second", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().get([CtLiteralImpl]2).getSortWeight().compareTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().get([CtLiteralImpl]1).getSortWeight()), [CtInvocationImpl]Matchers.is([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.Order newOrderWithInvalidPosition = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openmrs.api.builder.OrderBuilder().withAction([CtTypeAccessImpl]Order.Action.NEW).withPatient([CtLiteralImpl]7).withConcept([CtLiteralImpl]1000).withCareSetting([CtLiteralImpl]1).withOrderer([CtLiteralImpl]1).withEncounter([CtLiteralImpl]3).withDateActivated([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderType([CtLiteralImpl]17).withUrgency([CtTypeAccessImpl]Order.Urgency.ON_SCHEDULED_DATE).withScheduledDate([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()).withOrderGroup([CtVariableReadImpl]savedOrderGroup).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openmrs.api.APIException exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]org.openmrs.api.APIException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.addOrder([CtVariableReadImpl]newOrderWithInvalidPosition, [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondSavedOrderGroup.getOrders().size() + [CtLiteralImpl]1));
        [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]exception.getMessage(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]"Cannot add a member which is out of range of the list"));
    }
}
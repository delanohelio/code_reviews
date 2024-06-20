[CompilationUnitImpl][CtJavaDocImpl]/**
 * *****************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2020 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2020 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 * *****************************************************************************
 */
[CtPackageDeclarationImpl]package org.opennms.netmgt.xml.event;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlAccessorType;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlAttribute;
[CtUnresolvedImport]import org.opennms.netmgt.events.api.model.IAlarmData;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import javax.validation.constraints.Min;
[CtUnresolvedImport]import javax.validation.constraints.NotNull;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlRootElement;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlAccessType;
[CtUnresolvedImport]import javax.validation.Valid;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.io.Serializable;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlElement;
[CtClassImpl][CtJavaDocImpl]/**
 * This element is used for converting events into alarms.
 */
[CtCommentImpl]// @ValidateUsing("event.xsd")
[CtAnnotationImpl]@javax.xml.bind.annotation.XmlRootElement(name = [CtLiteralImpl]"alarm-data")
[CtAnnotationImpl]@javax.xml.bind.annotation.XmlAccessorType([CtFieldReadImpl]javax.xml.bind.annotation.XmlAccessType.FIELD)
public class AlarmData implements [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]3681502418413339216L;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field _reductionKey.
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlAttribute(name = [CtLiteralImpl]"reduction-key", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@javax.validation.constraints.NotNull
    private [CtTypeReferenceImpl]java.lang.String _reductionKey;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field _alarmType.
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlAttribute(name = [CtLiteralImpl]"alarm-type", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@javax.validation.constraints.NotNull
    [CtAnnotationImpl]@javax.validation.constraints.Min([CtLiteralImpl]1)
    private [CtTypeReferenceImpl]java.lang.Integer _alarmType;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field _clearKey.
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlAttribute(name = [CtLiteralImpl]"clear-key")
    private [CtTypeReferenceImpl]java.lang.String _clearKey;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field _autoClean.
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlAttribute(name = [CtLiteralImpl]"auto-clean")
    private [CtTypeReferenceImpl]java.lang.Boolean _autoClean = [CtLiteralImpl]false;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field _x733AlarmType.
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlAttribute(name = [CtLiteralImpl]"x733-alarm-type")
    private [CtTypeReferenceImpl]java.lang.String _x733AlarmType;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field _x733ProbableCause.
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlAttribute(name = [CtLiteralImpl]"x733-probable-cause")
    private [CtTypeReferenceImpl]java.lang.Integer _x733ProbableCause;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field m_updateField
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlElement(name = [CtLiteralImpl]"update-field", required = [CtLiteralImpl]false)
    [CtAnnotationImpl]@javax.validation.Valid
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField> m_updateFieldList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Field m_managedObject
     */
    [CtAnnotationImpl]@javax.xml.bind.annotation.XmlElement(name = [CtLiteralImpl]"managed-object", required = [CtLiteralImpl]false)
    private [CtTypeReferenceImpl]org.opennms.netmgt.xml.event.ManagedObject m_managedObject;

    [CtConstructorImpl]public AlarmData() [CtBlockImpl]{
        [CtInvocationImpl]super();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.opennms.netmgt.xml.event.AlarmData copyFrom([CtParameterImpl][CtTypeReferenceImpl]org.opennms.netmgt.events.api.model.IAlarmData source) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]source == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opennms.netmgt.xml.event.AlarmData alarmData = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opennms.netmgt.xml.event.AlarmData();
        [CtInvocationImpl][CtVariableReadImpl]alarmData.setReductionKey([CtInvocationImpl][CtVariableReadImpl]source.getReductionKey());
        [CtInvocationImpl][CtVariableReadImpl]alarmData.setAlarmType([CtInvocationImpl][CtVariableReadImpl]source.getAlarmType());
        [CtInvocationImpl][CtVariableReadImpl]alarmData.setClearKey([CtInvocationImpl][CtVariableReadImpl]source.getClearKey());
        [CtInvocationImpl][CtVariableReadImpl]alarmData.setAutoClean([CtInvocationImpl][CtVariableReadImpl]source.getAutoClean());
        [CtInvocationImpl][CtVariableReadImpl]alarmData.setX733AlarmType([CtInvocationImpl][CtVariableReadImpl]source.getX733AlarmType());
        [CtInvocationImpl][CtVariableReadImpl]alarmData.setX733ProbableCause([CtInvocationImpl][CtVariableReadImpl]source.getX733ProbableCause());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]alarmData.getUpdateFieldList().addAll([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]source.getUpdateFieldList().stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]UpdateField::copyFrom).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList()));
        [CtInvocationImpl][CtVariableReadImpl]alarmData.setManagedObject([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]source.getManagedObject() == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]org.opennms.netmgt.xml.event.ManagedObject.copyFrom([CtInvocationImpl][CtVariableReadImpl]source.getManagedObject()));
        [CtReturnImpl]return [CtVariableReadImpl]alarmData;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteAlarmType() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._alarmType = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    public [CtTypeReferenceImpl]void deleteAutoClean() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._autoClean = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    public [CtTypeReferenceImpl]void deleteX733ProbableCause() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._x733ProbableCause = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of field 'alarmType'.
     *
     * @return the value of field 'AlarmType'.
     */
    public [CtTypeReferenceImpl]java.lang.Integer getAlarmType() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this._alarmType == [CtLiteralImpl]null ? [CtLiteralImpl]0 : [CtFieldReadImpl][CtThisAccessImpl]this._alarmType;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of field 'autoClean'.
     *
     * @return the value of field 'AutoClean'.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean getAutoClean() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this._autoClean == [CtLiteralImpl]null ? [CtLiteralImpl]false : [CtFieldReadImpl][CtThisAccessImpl]this._autoClean;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of field 'clearKey'.
     *
     * @return the value of field 'ClearKey'.
     */
    public [CtTypeReferenceImpl]java.lang.String getClearKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this._clearKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of field 'reductionKey'.
     *
     * @return the value of field 'ReductionKey'.
     */
    public [CtTypeReferenceImpl]java.lang.String getReductionKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this._reductionKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of field 'x733AlarmType'.
     *
     * @return the value of field 'X733AlarmType'.
     */
    public [CtTypeReferenceImpl]java.lang.String getX733AlarmType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this._x733AlarmType;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of field 'x733ProbableCause'.
     *
     * @return the value of field 'X733ProbableCause'.
     */
    public [CtTypeReferenceImpl]java.lang.Integer getX733ProbableCause() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this._x733ProbableCause == [CtLiteralImpl]null ? [CtLiteralImpl]0 : [CtFieldReadImpl][CtThisAccessImpl]this._x733ProbableCause;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Method hasAlarmType.
     *
     * @return true if at least one AlarmType has been added
     */
    public [CtTypeReferenceImpl]boolean hasAlarmType() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this._alarmType != [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Method hasAutoClean.
     *
     * @return true if at least one AutoClean has been added
     */
    public [CtTypeReferenceImpl]boolean hasAutoClean() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this._autoClean != [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Method hasX733ProbableCause.
     *
     * @return true if at least one X733ProbableCause has been added
     */
    public [CtTypeReferenceImpl]boolean hasX733ProbableCause() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this._x733ProbableCause != [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of field 'autoClean'.
     *
     * @return the value of field 'AutoClean'.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isAutoClean() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getAutoClean();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value of field 'alarmType'.
     *
     * @param alarmType
     * 		the value of field 'alarmType'.
     */
    public [CtTypeReferenceImpl]void setAlarmType([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Integer alarmType) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._alarmType = [CtVariableReadImpl]alarmType;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value of field 'autoClean'.
     *
     * @param autoClean
     * 		the value of field 'autoClean'.
     */
    public [CtTypeReferenceImpl]void setAutoClean([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Boolean autoClean) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._autoClean = [CtVariableReadImpl]autoClean;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value of field 'clearKey'.
     *
     * @param clearKey
     * 		the value of field 'clearKey'.
     */
    public [CtTypeReferenceImpl]void setClearKey([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String clearKey) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._clearKey = [CtVariableReadImpl]clearKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value of field 'reductionKey'.
     *
     * @param reductionKey
     * 		the value of field 'reductionKey'.
     */
    public [CtTypeReferenceImpl]void setReductionKey([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String reductionKey) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._reductionKey = [CtVariableReadImpl]reductionKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value of field 'x733AlarmType'.
     *
     * @param x733AlarmType
     * 		the value of field 'x733AlarmType'.
     */
    public [CtTypeReferenceImpl]void setX733AlarmType([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String x733AlarmType) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._x733AlarmType = [CtVariableReadImpl]x733AlarmType;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value of field 'x733ProbableCause'.
     *
     * @param x733ProbableCause
     * 		the value of field
     * 		'x733ProbableCause'.
     */
    public [CtTypeReferenceImpl]void setX733ProbableCause([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Integer x733ProbableCause) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this._x733ProbableCause = [CtVariableReadImpl]x733ProbableCause;
    }

    [CtMethodImpl]public [CtArrayTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField[] getUpdateField() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField[[CtLiteralImpl]0]);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField> getUpdateFieldCollection() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]m_updateFieldList;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField> getUpdateFieldList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]m_updateFieldList;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getUpdateFieldListCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.size();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Boolean hasUpdateFields() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean hasFields = [CtLiteralImpl]true;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]m_updateFieldList == [CtLiteralImpl]null) || [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]hasFields = [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtVariableReadImpl]hasFields;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setUpdateField([CtParameterImpl][CtArrayTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField[] fields) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.clear();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]fields.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.add([CtArrayReadImpl][CtVariableReadImpl]fields[[CtVariableReadImpl]i]);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setUpdateField([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField> fields) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]m_updateFieldList == [CtVariableReadImpl]fields)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.clear();
        [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.addAll([CtVariableReadImpl]fields);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setUpdateFieldCollection([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.opennms.netmgt.xml.event.UpdateField> fields) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]m_updateFieldList == [CtVariableReadImpl]fields)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.clear();
        [CtInvocationImpl][CtFieldReadImpl]m_updateFieldList.addAll([CtVariableReadImpl]fields);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.opennms.netmgt.xml.event.ManagedObject getManagedObject() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]m_managedObject;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setManagedObject([CtParameterImpl][CtTypeReferenceImpl]org.opennms.netmgt.xml.event.ManagedObject m_managedObject) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.m_managedObject = [CtVariableReadImpl]m_managedObject;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opennms.netmgt.xml.event.AlarmData alarmData = [CtVariableReadImpl](([CtTypeReferenceImpl]org.opennms.netmgt.xml.event.AlarmData) (o));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]_reductionKey, [CtFieldReadImpl][CtVariableReadImpl]alarmData._reductionKey) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]_alarmType, [CtFieldReadImpl][CtVariableReadImpl]alarmData._alarmType)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]_clearKey, [CtFieldReadImpl][CtVariableReadImpl]alarmData._clearKey)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]_autoClean, [CtFieldReadImpl][CtVariableReadImpl]alarmData._autoClean)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]_x733AlarmType, [CtFieldReadImpl][CtVariableReadImpl]alarmData._x733AlarmType)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]_x733ProbableCause, [CtFieldReadImpl][CtVariableReadImpl]alarmData._x733ProbableCause)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]m_updateFieldList, [CtFieldReadImpl][CtVariableReadImpl]alarmData.m_updateFieldList)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]m_managedObject, [CtFieldReadImpl][CtVariableReadImpl]alarmData.m_managedObject);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtFieldReadImpl]_reductionKey, [CtFieldReadImpl]_alarmType, [CtFieldReadImpl]_clearKey, [CtFieldReadImpl]_autoClean, [CtFieldReadImpl]_x733AlarmType, [CtFieldReadImpl]_x733ProbableCause, [CtFieldReadImpl]m_updateFieldList, [CtFieldReadImpl]m_managedObject);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opennms.netmgt.xml.event.OnmsStringBuilder([CtThisAccessImpl]this).toString();
    }
}
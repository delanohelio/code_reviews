[CompilationUnitImpl][CtCommentImpl]/* SPDX-License-Identifier: Apache 2.0 */
[CtPackageDeclarationImpl]package org.odpi.openmetadata.accessservices.assetconsumer.properties;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonAutoDetect;
[CtImportImpl]import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
[CtImportImpl]import java.util.*;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonInclude;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
[CtImportImpl]import java.io.Serializable;
[CtImportImpl]import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
[CtClassImpl][CtJavaDocImpl]/**
 * SecurityTags holds the list of labels and properties used by a security enforcement engine to control access
 * and visibility to the contents of the real-world object described by the Referenceable.
 */
[CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonAutoDetect(getterVisibility = [CtFieldReadImpl]com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY, setterVisibility = [CtFieldReadImpl]com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY, fieldVisibility = [CtFieldReadImpl]com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE)
[CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonInclude([CtFieldReadImpl][CtTypeAccessImpl]com.fasterxml.jackson.annotation.JsonInclude.Include.[CtFieldReferenceImpl]NON_NULL)
[CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = [CtLiteralImpl]true)
public class SecurityTagsProperties implements [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> securityLabels = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> securityProperties = [CtLiteralImpl]null;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Default constructor
     */
    public SecurityTagsProperties() [CtBlockImpl]{
        [CtInvocationImpl]super();
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Copy/clone constructor
     *
     * @param template
     * 		object to copy
     */
    public SecurityTagsProperties([CtParameterImpl][CtTypeReferenceImpl]org.odpi.openmetadata.accessservices.assetconsumer.properties.SecurityTagsProperties template) [CtBlockImpl]{
        [CtInvocationImpl]super();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]template != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.securityLabels = [CtInvocationImpl][CtVariableReadImpl]template.getSecurityLabels();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.securityProperties = [CtInvocationImpl][CtVariableReadImpl]template.getSecurityProperties();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the list of security labels attached to the element.
     *
     * @return list of label strings
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getSecurityLabels() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]securityLabels == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]securityLabels.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl]securityLabels);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set up the list of security labels for the element.
     *
     * @param securityLabels
     * 		list of label strings
     */
    public [CtTypeReferenceImpl]void setSecurityLabels([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> securityLabels) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.securityLabels = [CtVariableReadImpl]securityLabels;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the security properties associated with the element.  These are name-value pairs.
     *
     * @return map of properties
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> getSecurityProperties() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]securityProperties == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]securityProperties.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtFieldReadImpl]securityProperties);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set up the security properties associated with the element.  These are name-value pairs.
     *
     * @param securityProperties
     * 		map of properties
     */
    public [CtTypeReferenceImpl]void setSecurityProperties([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> securityProperties) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.securityProperties = [CtVariableReadImpl]securityProperties;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Standard toString method.
     *
     * @return print out of variables in a JSON-style
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SecurityTagsProperties{" + [CtLiteralImpl]"securityLabels=") + [CtFieldReadImpl]securityLabels) + [CtLiteralImpl]", securityProperties=") + [CtFieldReadImpl]securityProperties) + [CtLiteralImpl]'}';
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Compare the values of the supplied object with those stored in the current object.
     *
     * @param objectToCompare
     * 		supplied object
     * @return boolean result of comparison
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object objectToCompare) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]objectToCompare) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]objectToCompare == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]objectToCompare.getClass())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtSuperAccessImpl]super.equals([CtVariableReadImpl]objectToCompare)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.odpi.openmetadata.accessservices.assetconsumer.properties.SecurityTagsProperties that = [CtVariableReadImpl](([CtTypeReferenceImpl]org.odpi.openmetadata.accessservices.assetconsumer.properties.SecurityTagsProperties) (objectToCompare));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]securityLabels, [CtFieldReadImpl][CtVariableReadImpl]that.securityLabels) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]securityProperties, [CtFieldReadImpl][CtVariableReadImpl]that.securityProperties);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a hash code for this element type.
     *
     * @return int hash code
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtInvocationImpl][CtSuperAccessImpl]super.hashCode(), [CtFieldReadImpl]securityLabels, [CtFieldReadImpl]securityProperties);
    }
}
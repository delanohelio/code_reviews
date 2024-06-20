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
[CtPackageDeclarationImpl]package de.dlr.sc.virsat.graphiti.diagram;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyEnum;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyEReference;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.FloatProperty;
[CtUnresolvedImport]import org.eclipse.emf.ecore.EObject;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.util.PropertydefinitionsSwitch;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyString;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.BooleanProperty;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.EReferenceProperty;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.StringProperty;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.EnumProperty;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyBoolean;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.ATypeInstance;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.IntProperty;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.ResourceProperty;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyResource;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.dvlm.categories.ATypeDefinition;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.IBeanObject;
[CtUnresolvedImport]import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyInt;
[CtClassImpl][CtJavaDocImpl]/**
 * This factory class produces Bean Objects, wrapping certain BeanProperties for a given object.
 */
public class BeanPropertyFactory {
    [CtFieldImpl]private [CtTypeReferenceImpl]de.dlr.sc.virsat.graphiti.diagram.BeanPropertyFactory.BeanPropertyFactorySwitch bpfs;

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param object
     * 		Object that shall be wrapped into Bean Object
     * @return Bean Object wrapper for object
     */
    public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> getInstanceFor([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.ATypeInstance object) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.ATypeDefinition type = [CtInvocationImpl][CtVariableReadImpl]object.getType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> bean = [CtInvocationImpl][CtFieldReadImpl]bpfs.doSwitch([CtVariableReadImpl]type);
        [CtInvocationImpl][CtVariableReadImpl]bean.setATypeInstance([CtVariableReadImpl]object);
        [CtReturnImpl]return [CtVariableReadImpl]bean;
    }

    [CtConstructorImpl]public BeanPropertyFactory() [CtBlockImpl]{
        [CtInvocationImpl]super();
        [CtAssignmentImpl][CtFieldWriteImpl]bpfs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.graphiti.diagram.BeanPropertyFactory.BeanPropertyFactorySwitch();
    }

    [CtClassImpl]private class BeanPropertyFactorySwitch extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.util.PropertydefinitionsSwitch<[CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance>> {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> caseFloatProperty([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.FloatProperty object) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> caseIntProperty([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.IntProperty object) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.property.BeanPropertyInt();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> caseBooleanProperty([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.BooleanProperty object) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.property.BeanPropertyBoolean();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> caseEnumProperty([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.EnumProperty object) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.property.BeanPropertyEnum();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> caseResourceProperty([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.ResourceProperty object) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.property.BeanPropertyResource();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> caseStringProperty([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.StringProperty object) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.property.BeanPropertyString();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.IBeanObject<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance> caseEReferenceProperty([CtParameterImpl][CtTypeReferenceImpl]de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.EReferenceProperty object) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]de.dlr.sc.virsat.model.concept.types.property.BeanPropertyEReference<[CtTypeReferenceImpl]org.eclipse.emf.ecore.EObject>();
        }
    }
}
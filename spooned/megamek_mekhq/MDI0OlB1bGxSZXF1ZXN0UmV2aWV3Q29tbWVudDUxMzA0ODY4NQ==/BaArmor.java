[CompilationUnitImpl][CtCommentImpl]/* BaArmor.java

Copyright (c) 2009 Jay Lawson <jaylawson39 at yahoo.com>. All rights reserved.

This file is part of MekHQ.

MekHQ is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

MekHQ is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MekHQ.  If not, see <http://www.gnu.org/licenses/>.
 */
[CtPackageDeclarationImpl]package mekhq.campaign.parts;
[CtUnresolvedImport]import megamek.common.EquipmentType;
[CtUnresolvedImport]import mekhq.campaign.Campaign;
[CtUnresolvedImport]import mekhq.campaign.finances.Money;
[CtUnresolvedImport]import mekhq.campaign.work.IAcquisitionWork;
[CtImportImpl]import java.util.Objects;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Jay Lawson <jaylawson39 at yahoo.com>
 */
public class BaArmor extends [CtTypeReferenceImpl]mekhq.campaign.parts.Armor implements [CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]5275226057484468868L;

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canBeClan([CtParameterImpl][CtTypeReferenceImpl]int type) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STANDARD) || [CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STEALTH_BASIC)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STEALTH_IMP)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STEALTH)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_FIRE_RESIST);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canBeIs([CtParameterImpl][CtTypeReferenceImpl]int type) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]type != [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_FIRE_RESIST;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]double getPointsPerTon([CtParameterImpl][CtTypeReferenceImpl]int t, [CtParameterImpl][CtTypeReferenceImpl]boolean isClan) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]1.0 / [CtInvocationImpl][CtTypeAccessImpl]megamek.common.EquipmentType.getBaArmorWeightPerPoint([CtVariableReadImpl]t, [CtVariableReadImpl]isClan);
    }

    [CtConstructorImpl]public BaArmor() [CtBlockImpl]{
        [CtInvocationImpl]this([CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]false, [CtLiteralImpl]null);
    }

    [CtConstructorImpl]public BaArmor([CtParameterImpl][CtTypeReferenceImpl]int tonnage, [CtParameterImpl][CtTypeReferenceImpl]int points, [CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]int loc, [CtParameterImpl][CtTypeReferenceImpl]boolean clan, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign c) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Amount is used for armor quantity, not tonnage
        super([CtVariableReadImpl]tonnage, [CtVariableReadImpl]type, [CtVariableReadImpl]points, [CtVariableReadImpl]loc, [CtLiteralImpl]false, [CtVariableReadImpl]clan, [CtVariableReadImpl]c);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor clone() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor clone = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor([CtLiteralImpl]0, [CtFieldReadImpl]amount, [CtFieldReadImpl]type, [CtFieldReadImpl]location, [CtFieldReadImpl]clan, [CtFieldReadImpl]campaign);
        [CtInvocationImpl][CtVariableReadImpl]clone.copyBaseData([CtThisAccessImpl]this);
        [CtReturnImpl]return [CtVariableReadImpl]clone;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]double getTonnage() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.common.EquipmentType.getBaArmorWeightPerPoint([CtFieldReadImpl]type, [CtFieldReadImpl]clan) * [CtFieldReadImpl]amount;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.finances.Money getPointCost() [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtFieldReadImpl]type) {
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STANDARD_ADVANCED :
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtLiteralImpl]12500);
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_MIMETIC :
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STEALTH :
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtLiteralImpl]15000);
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STEALTH_BASIC :
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtLiteralImpl]12000);
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STEALTH_IMP :
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtLiteralImpl]20000);
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STEALTH_PROTOTYPE :
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtLiteralImpl]50000);
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_FIRE_RESIST :
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STANDARD_PROTOTYPE :
            [CtCaseImpl]case [CtFieldReadImpl]megamek.common.EquipmentType.T_ARMOR_BA_STANDARD :
            [CtCaseImpl]default :
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtLiteralImpl]10000);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]double getPointsPerTon() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]mekhq.campaign.parts.BaArmor.getPointsPerTon([CtFieldReadImpl]type, [CtFieldReadImpl]clan);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]type;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]mekhq.campaign.finances.Money getCurrentValue() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getPointCost().multipliedBy([CtFieldReadImpl]amount);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]double getTonnageNeeded() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]amountNeeded / [CtInvocationImpl]getPointsPerTon();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]mekhq.campaign.finances.Money getValueNeeded() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]adjustCostsForCampaignOptions([CtInvocationImpl][CtInvocationImpl]getPointCost().multipliedBy([CtFieldReadImpl]amountNeeded));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]mekhq.campaign.finances.Money getStickerPrice() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// always in 5-ton increments
        return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getPointCost().multipliedBy([CtLiteralImpl]5).multipliedBy([CtInvocationImpl]getPointsPerTon());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]mekhq.campaign.finances.Money getBuyCost() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getStickerPrice();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isSamePartType([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) && [CtBinaryOperatorImpl]([CtInvocationImpl]isClanTechBase() == [CtInvocationImpl][CtVariableReadImpl]part.isClanTechBase())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getRefitUnit(), [CtInvocationImpl][CtVariableReadImpl]part.getRefitUnit())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) (part)).getType() == [CtInvocationImpl]getType());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isSameStatus([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]hasParentPart()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]part.hasParentPart())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtThisAccessImpl]this.getDaysToArrival() == [CtInvocationImpl][CtVariableReadImpl]part.getDaysToArrival());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getArmorWeight([CtParameterImpl][CtTypeReferenceImpl]int points) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]points * [CtLiteralImpl]50) / [CtLiteralImpl]1000.0;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork getAcquisitionWork() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor([CtLiteralImpl]0, [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtBinaryOperatorImpl][CtLiteralImpl]5 * [CtInvocationImpl]getPointsPerTon()))), [CtFieldReadImpl]type, [CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtFieldReadImpl]clan, [CtFieldReadImpl]campaign);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.parts.Part getNewPart() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor([CtLiteralImpl]0, [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtBinaryOperatorImpl][CtLiteralImpl]5 * [CtInvocationImpl]getPointsPerTon()))), [CtFieldReadImpl]type, [CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtFieldReadImpl]clan, [CtFieldReadImpl]campaign);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getAmountAvailable() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor a = [CtInvocationImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) ([CtInvocationImpl][CtFieldReadImpl]campaign.getWarehouse().findSparePart([CtLambdaImpl]([CtParameterImpl] part) -> [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) && [CtInvocationImpl][CtVariableReadImpl]part.isPresent()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]part.isReservedForRefit())) && [CtBinaryOperatorImpl]([CtInvocationImpl]isClanTechBase() == [CtInvocationImpl][CtVariableReadImpl]part.isClanTechBase())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) (part)).getType() == [CtInvocationImpl]getType());
        })));
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]a != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]a.getAmount() : [CtLiteralImpl]0;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void changeAmountAvailable([CtParameterImpl][CtTypeReferenceImpl]int amount) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor a = [CtInvocationImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) ([CtInvocationImpl][CtFieldReadImpl]campaign.getWarehouse().findSparePart([CtLambdaImpl]([CtParameterImpl] part) -> [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]isSamePartType([CtVariableReadImpl]part) && [CtInvocationImpl][CtVariableReadImpl]part.isPresent();
        })));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]a) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setAmount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.getAmount() + [CtVariableReadImpl]amount);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.getAmount() <= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]campaign.removePart([CtVariableReadImpl]a);
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]amount > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]campaign.addPart([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor([CtInvocationImpl]getUnitTonnage(), [CtVariableReadImpl]amount, [CtFieldReadImpl]type, [CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtInvocationImpl]isClanTechBase(), [CtFieldReadImpl]campaign), [CtLiteralImpl]0);
        }
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package com.minecolonies.api.entity.citizen.happiness;
[CtImportImpl]import java.util.function.DoubleSupplier;
[CtUnresolvedImport]import static com.minecolonies.api.util.constant.NbtTagConstants.TAG_DAY;
[CtUnresolvedImport]import net.minecraft.nbt.CompoundNBT;
[CtClassImpl][CtJavaDocImpl]/**
 * The time based happiness modifier.
 */
public class ExpirationBasedHappinessModifier extends [CtTypeReferenceImpl]com.minecolonies.api.entity.citizen.happiness.StaticHappinessModifier {
    [CtFieldImpl][CtJavaDocImpl]/**
     * The number of passed days.
     */
    private [CtTypeReferenceImpl]int days = [CtLiteralImpl]0;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Period of time this modifier applies.
     */
    private final [CtTypeReferenceImpl]int period;

    [CtFieldImpl][CtJavaDocImpl]/**
     * If this should give a penalty if not active.
     */
    private [CtTypeReferenceImpl]boolean inverted;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create an instance of the happiness modifier.
     *
     * @param id
     * 		its string id.
     * @param weight
     * 		its weight.
     * @param period
     * 		the period.
     */
    public ExpirationBasedHappinessModifier([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl]final [CtTypeReferenceImpl]double weight, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.function.DoubleSupplier supplier, [CtParameterImpl]final [CtTypeReferenceImpl]int period) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]id, [CtVariableReadImpl]weight, [CtVariableReadImpl]supplier);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.period = [CtVariableReadImpl]period;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create an instance of the happiness modifier.
     *
     * @param id
     * 		its string id.
     * @param weight
     * 		its weight.
     * @param period
     * 		the period.
     * @param inverted
     * 		if inverted.
     */
    public ExpirationBasedHappinessModifier([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl]final [CtTypeReferenceImpl]double weight, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.function.DoubleSupplier supplier, [CtParameterImpl]final [CtTypeReferenceImpl]int period, [CtParameterImpl]final [CtTypeReferenceImpl]boolean inverted) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]id, [CtVariableReadImpl]weight, [CtVariableReadImpl]supplier, [CtVariableReadImpl]period);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inverted = [CtVariableReadImpl]inverted;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]double getFactor() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]inverted) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]days > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]1;
            }
            [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getFactor();
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]days > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getFactor();
            }
            [CtReturnImpl]return [CtLiteralImpl]1;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void reset() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.days = [CtFieldReadImpl]period;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void dayEnd() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.dayEnd();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]days > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtUnaryOperatorImpl][CtFieldWriteImpl]days--;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getDays() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]days;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void read([CtParameterImpl]final [CtTypeReferenceImpl]net.minecraft.nbt.CompoundNBT compoundNBT) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.read([CtVariableReadImpl]compoundNBT);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.days = [CtInvocationImpl][CtVariableReadImpl]compoundNBT.getInt([CtTypeAccessImpl]com.minecolonies.api.entity.citizen.happiness.TAG_DAY);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void write([CtParameterImpl]final [CtTypeReferenceImpl]net.minecraft.nbt.CompoundNBT compoundNBT) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.write([CtVariableReadImpl]compoundNBT);
        [CtInvocationImpl][CtVariableReadImpl]compoundNBT.putInt([CtTypeAccessImpl]com.minecolonies.api.entity.citizen.happiness.TAG_DAY, [CtFieldReadImpl]days);
    }
}
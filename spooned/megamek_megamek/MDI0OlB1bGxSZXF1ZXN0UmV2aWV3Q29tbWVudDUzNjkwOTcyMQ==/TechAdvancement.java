[CompilationUnitImpl][CtCommentImpl]/* MegaMek -
Copyright (C) 2017 The MegaMek Team

This program is free software; you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation; either version 2 of the License, or (at your option) any later
version.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
details.
 */
[CtPackageDeclarationImpl]package megamek.common;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import java.util.StringJoiner;
[CtClassImpl][CtJavaDocImpl]/**
 * Handles the progression of technology through prototype, production, extinction and reintroduction
 * phases. Calculates current rules level for IS or Clan.
 *
 * @author Neoancient
 */
public class TechAdvancement implements [CtTypeReferenceImpl]megamek.common.ITechnology {
    [CtFieldImpl][CtCommentImpl]// Dates that are approximate can be pushed this many years earlier (or later for extinctions).
    public static final [CtTypeReferenceImpl]int APPROXIMATE_MARGIN = [CtLiteralImpl]5;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int PROTOTYPE = [CtLiteralImpl]0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int PRODUCTION = [CtLiteralImpl]1;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int COMMON = [CtLiteralImpl]2;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int EXTINCT = [CtLiteralImpl]3;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int REINTRODUCED = [CtLiteralImpl]4;

    [CtFieldImpl]private [CtTypeReferenceImpl]int techBase = [CtFieldReadImpl]TECH_BASE_ALL;

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]int[] isAdvancement = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtLiteralImpl]5];

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]int[] clanAdvancement = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtLiteralImpl]5];

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]boolean[] isApproximate = [CtNewArrayImpl]new [CtTypeReferenceImpl]boolean[[CtLiteralImpl]5];

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]boolean[] clanApproximate = [CtNewArrayImpl]new [CtTypeReferenceImpl]boolean[[CtLiteralImpl]5];

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]int[] prototypeFactions = [CtNewArrayImpl]new int[]{  };

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]int[] productionFactions = [CtNewArrayImpl]new int[]{  };

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]int[] extinctionFactions = [CtNewArrayImpl]new int[]{  };

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]int[] reintroductionFactions = [CtNewArrayImpl]new int[]{  };

    [CtFieldImpl]private [CtTypeReferenceImpl]megamek.common.SimpleTechLevel staticTechLevel = [CtFieldReadImpl]SimpleTechLevel.STANDARD;

    [CtFieldImpl]private [CtTypeReferenceImpl]int techRating = [CtFieldReadImpl]RATING_C;

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]int[] availability = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtBinaryOperatorImpl][CtFieldReadImpl]ERA_DA + [CtLiteralImpl]1];

    [CtConstructorImpl]public TechAdvancement() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtFieldReadImpl]isAdvancement, [CtTypeAccessImpl]megamek.common.DATE_NONE);
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtFieldReadImpl]clanAdvancement, [CtTypeAccessImpl]megamek.common.DATE_NONE);
    }

    [CtConstructorImpl]public TechAdvancement([CtParameterImpl][CtTypeReferenceImpl]int techBase) [CtBlockImpl]{
        [CtInvocationImpl]this();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.techBase = [CtVariableReadImpl]techBase;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Copy constructor
     */
    public TechAdvancement([CtParameterImpl][CtTypeReferenceImpl]megamek.common.TechAdvancement ta) [CtBlockImpl]{
        [CtInvocationImpl]this([CtFieldReadImpl][CtVariableReadImpl]ta.techBase);
        [CtAssignmentImpl][CtFieldWriteImpl]isAdvancement = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.isAdvancement, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.isAdvancement.length);
        [CtAssignmentImpl][CtFieldWriteImpl]clanAdvancement = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.clanAdvancement, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.clanAdvancement.length);
        [CtAssignmentImpl][CtFieldWriteImpl]isApproximate = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.isApproximate, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.isApproximate.length);
        [CtAssignmentImpl][CtFieldWriteImpl]clanApproximate = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.clanApproximate, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.clanApproximate.length);
        [CtAssignmentImpl][CtFieldWriteImpl]prototypeFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.prototypeFactions, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.prototypeFactions.length);
        [CtAssignmentImpl][CtFieldWriteImpl]productionFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.productionFactions, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.productionFactions.length);
        [CtAssignmentImpl][CtFieldWriteImpl]extinctionFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.extinctionFactions, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.extinctionFactions.length);
        [CtAssignmentImpl][CtFieldWriteImpl]reintroductionFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl][CtVariableReadImpl]ta.reintroductionFactions, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.reintroductionFactions.length);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.staticTechLevel = [CtFieldReadImpl][CtVariableReadImpl]ta.staticTechLevel;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.techRating = [CtFieldReadImpl][CtVariableReadImpl]ta.techRating;
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtFieldReadImpl][CtVariableReadImpl]ta.availability, [CtLiteralImpl]0, [CtFieldReadImpl][CtThisAccessImpl]this.availability, [CtLiteralImpl]0, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]ta.availability.length);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.TechAdvancement setTechBase([CtParameterImpl][CtTypeReferenceImpl]int base) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]techBase = [CtVariableReadImpl]base;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTechBase() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]techBase;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Provide years for prototype, production, common, extinction, and reintroduction for IS factions.
     *
     * @param prog
     * 		Up to five tech progression years. Missing levels should be marked by DATE_NONE.
     * @return a reference to this object
     */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setISAdvancement([CtParameterImpl]int... prog) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtFieldReadImpl]isAdvancement, [CtTypeAccessImpl]megamek.common.DATE_NONE);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]prog, [CtLiteralImpl]0, [CtFieldReadImpl]isAdvancement, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]isAdvancement.length, [CtFieldReadImpl][CtVariableReadImpl]prog.length));
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Indicate whether the years for prototype, production, common, extinction, and reintroduction
     * for IS factions should be considered approximate.
     *
     * @param approx
     * 		Up to five tech progression years.
     * @return a reference to this object
     */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setISApproximate([CtParameterImpl]boolean... approx) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtFieldReadImpl]isApproximate, [CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]approx, [CtLiteralImpl]0, [CtFieldReadImpl]isApproximate, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]isApproximate.length, [CtFieldReadImpl][CtVariableReadImpl]approx.length));
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Provide years for prototype, production, common, extinction, and reintroduction for Clan factions.
     *
     * @param prog
     * 		Up to five tech progression years. Missing levels should be marked by DATE_NONE.
     * @return a reference to this object
     */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setClanAdvancement([CtParameterImpl]int... prog) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtFieldReadImpl]clanAdvancement, [CtTypeAccessImpl]megamek.common.DATE_NONE);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]prog, [CtLiteralImpl]0, [CtFieldReadImpl]clanAdvancement, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clanAdvancement.length, [CtFieldReadImpl][CtVariableReadImpl]prog.length));
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Indicate whether the years for prototype, production, common, extinction, and reintroduction
     * for Clan factions should be considered approximate.
     *
     * @param approx
     * 		Up to five tech progression years.
     * @return a reference to this object
     */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setClanApproximate([CtParameterImpl]boolean... approx) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtFieldReadImpl]clanApproximate, [CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]approx, [CtLiteralImpl]0, [CtFieldReadImpl]clanApproximate, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clanApproximate.length, [CtFieldReadImpl][CtVariableReadImpl]approx.length));
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * A convenience method that will set identical values for IS and Clan factions.
     *
     * @param prog
     * @return  */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setAdvancement([CtParameterImpl]int... prog) [CtBlockImpl]{
        [CtInvocationImpl]setISAdvancement([CtVariableReadImpl]prog);
        [CtInvocationImpl]setClanAdvancement([CtVariableReadImpl]prog);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * A convenience method that will set identical values for IS and Clan factions.
     *
     * @param approx
     * @return  */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setApproximate([CtParameterImpl]boolean... approx) [CtBlockImpl]{
        [CtInvocationImpl]setISApproximate([CtVariableReadImpl]approx);
        [CtInvocationImpl]setClanApproximate([CtVariableReadImpl]approx);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets which factions developed a prototype.
     *
     * @param factions
     * 		A list of F_* faction constants
     * @return  */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setPrototypeFactions([CtParameterImpl]int... factions) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]prototypeFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtVariableReadImpl]factions, [CtFieldReadImpl][CtVariableReadImpl]factions.length);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return A list of F_* constants that indicate which factions started prototype development.
     */
    public [CtArrayTypeReferenceImpl]int[] getPrototypeFactions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]prototypeFactions;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets which factions started production before the technology was commonly available.
     *
     * @param factions
     * 		A list of F_* faction constants
     * @return A reference to this object.
     */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setProductionFactions([CtParameterImpl]int... factions) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]productionFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtVariableReadImpl]factions, [CtFieldReadImpl][CtVariableReadImpl]factions.length);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return A list of F_* constants that indicate which factions started production
    before the technology was commonly available.
     */
    public [CtArrayTypeReferenceImpl]int[] getProductionFactions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]productionFactions;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the factions for which the technology became extinct.
     *
     * @param factions
     * 		A list of F_* faction constants
     * @return A reference to this object.
     */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setExtinctionFactions([CtParameterImpl]int... factions) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]extinctionFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtVariableReadImpl]factions, [CtFieldReadImpl][CtVariableReadImpl]factions.length);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return A list of F_* constants that indicate the factions for which the technology
    became extinct.
     */
    public [CtArrayTypeReferenceImpl]int[] getExtinctionFactions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]extinctionFactions;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the factions which reintroduced technology that had been extinct.
     *
     * @param factions
     * 		A list of F_* faction constants
     * @return A reference to this object.
     */
    public [CtTypeReferenceImpl]megamek.common.TechAdvancement setReintroductionFactions([CtParameterImpl]int... factions) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]reintroductionFactions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtVariableReadImpl]factions, [CtFieldReadImpl][CtVariableReadImpl]factions.length);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return A list of F_* constants that indicate the factions that reintroduced extinct technology.
    became extinct.
     */
    public [CtArrayTypeReferenceImpl]int[] getReintroductionFactions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]reintroductionFactions;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The prototype date for either Clan or IS factions. If the date is flagged as approximate,
     * the date returned will be earlier by the value of APPROXIMATE_MARGIN.
     */
    public [CtTypeReferenceImpl]int getPrototypeDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The prototype date for a particular faction. If there are prototype factions and the given faction
     * is not among them, the prototype date is DATE_NONE.
     *
     * @param clan
     * 		Whether to use Clan or IS progression dates
     * @faction The index of the faction (F_* constant). If < 0, the prototype factions are ignored.
     */
    public [CtTypeReferenceImpl]int getPrototypeDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan, [CtParameterImpl][CtTypeReferenceImpl]int faction) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtVariableReadImpl]clan) == [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]prototypeFactions.length > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]faction > [CtFieldReadImpl]F_NONE)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int f : [CtFieldReadImpl]prototypeFactions) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]faction == [CtVariableReadImpl]f) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_IS) && [CtUnaryOperatorImpl](![CtVariableReadImpl]clan))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_CLAN) && [CtVariableReadImpl]clan)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtVariableReadImpl]clan);
                }
            }
            [CtLocalVariableImpl][CtCommentImpl]// Per IO p. 34, tech with only a prototype date becomes available to
            [CtCommentImpl]// other factions after 3d6+5 years if it hasn't gone extinct by then.
            [CtCommentImpl]// Using the minimum value here.
            [CtTypeReferenceImpl]int date = [CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtVariableReadImpl]clan) + [CtLiteralImpl]8;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]clan) < [CtVariableReadImpl]date) || [CtBinaryOperatorImpl]([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtVariableReadImpl]clan) < [CtVariableReadImpl]date)) || [CtInvocationImpl]isExtinct([CtVariableReadImpl]date, [CtVariableReadImpl]clan)) [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
            }
            [CtReturnImpl]return [CtVariableReadImpl]date;
        }
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The production date for either Clan or IS factions. If the date is flagged as approximate,
     * the date returned will be earlier by the value of APPROXIMATE_MARGIN.
     */
    public [CtTypeReferenceImpl]int getProductionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The production date for a particular faction. If there are production factions and the given faction
     * is not among them, the production date is DATE_NONE.
     *
     * @param clan
     * 		Whether to use Clan or IS progression dates
     * @faction The index of the faction (F_* constant). If < 0, the production factions are ignored.
     */
    public [CtTypeReferenceImpl]int getProductionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan, [CtParameterImpl][CtTypeReferenceImpl]int faction) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]clan) == [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]productionFactions.length > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]faction > [CtFieldReadImpl]F_NONE)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int f : [CtFieldReadImpl]productionFactions) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]faction == [CtVariableReadImpl]f) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_IS) && [CtUnaryOperatorImpl](![CtVariableReadImpl]clan))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_CLAN) && [CtVariableReadImpl]clan)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]clan);
                }
            }
            [CtLocalVariableImpl][CtCommentImpl]// Per IO p. 34, tech with no common date becomes available to
            [CtCommentImpl]// other factions after 10 years if it hasn't gone extinct by then.
            [CtTypeReferenceImpl]int date = [CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]clan) + [CtLiteralImpl]10;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtVariableReadImpl]clan) <= [CtVariableReadImpl]date) || [CtInvocationImpl]isExtinct([CtVariableReadImpl]date, [CtVariableReadImpl]clan)) [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
            }
            [CtReturnImpl]return [CtVariableReadImpl]date;
        }
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The common date for either Clan or IS factions. If the date is flagged as approximate,
     * the date returned will be earlier by the value of APPROXIMATE_MARGIN.
     */
    public [CtTypeReferenceImpl]int getCommonDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The extinction date for either Clan or IS factions. If the date is flagged as approximate,
     * the date returned will be later by the value of APPROXIMATE_MARGIN.
     */
    public [CtTypeReferenceImpl]int getExtinctionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The extinction date for a particular faction. If there are extinction factions and the given faction
     * is not among them, the extinction date is DATE_NONE.
     *
     * @param clan
     * 		Whether to use Clan or IS progression dates
     * @faction The index of the faction (F_* constant). If < 0, the extinction factions are ignored.
     */
    public [CtTypeReferenceImpl]int getExtinctionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan, [CtParameterImpl][CtTypeReferenceImpl]int faction) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtVariableReadImpl]clan) == [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]extinctionFactions.length > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]faction > [CtFieldReadImpl]F_NONE)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int f : [CtFieldReadImpl]extinctionFactions) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]faction == [CtVariableReadImpl]f) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_IS) && [CtUnaryOperatorImpl](![CtVariableReadImpl]clan))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_CLAN) && [CtVariableReadImpl]clan)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtVariableReadImpl]clan);
                }
            }
            [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
        }
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The reintroduction date for either Clan or IS factions. If the date is flagged as approximate,
     * the date returned will be earlier by the value of APPROXIMATE_MARGIN.
     */
    public [CtTypeReferenceImpl]int getReintroductionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The reintroduction date for a particular faction. If there are reintroduction factions and the given faction
     * is not among them, the reintroduction date is DATE_NONE.
     *
     * @param clan
     * 		Whether to use Clan or IS progression dates
     * @faction The index of the faction (F_* constant). If < 0, the reintroduction factions are ignored.
     */
    public [CtTypeReferenceImpl]int getReintroductionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan, [CtParameterImpl][CtTypeReferenceImpl]int faction) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan) == [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]reintroductionFactions.length > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]faction > [CtFieldReadImpl]F_NONE)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int f : [CtFieldReadImpl]reintroductionFactions) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]faction == [CtVariableReadImpl]f) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_IS) && [CtUnaryOperatorImpl](![CtVariableReadImpl]clan))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]f == [CtFieldReadImpl]F_CLAN) && [CtVariableReadImpl]clan)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan);
                }
            }
            [CtIfImpl][CtCommentImpl]// If the production or common date is later than the reintroduction date, that is
            [CtCommentImpl]// when it becomes available to other factions. Otherwise we use reintro + 10 as with
            [CtCommentImpl]// production date.
            if ([CtBinaryOperatorImpl][CtInvocationImpl]getProductionDate([CtVariableReadImpl]clan, [CtVariableReadImpl]faction) > [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]getProductionDate([CtVariableReadImpl]clan, [CtVariableReadImpl]faction);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtVariableReadImpl]clan) > [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtVariableReadImpl]clan);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan) + [CtLiteralImpl]10;
            }
        }
        [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The year the technology first became available for Clan or IS factions, regardless
     * of production level, or APPROXIMATE_MARGIN years earlier if
     * marked as approximate.
     */
    public [CtTypeReferenceImpl]int getIntroductionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getPrototypeDate([CtVariableReadImpl]clan) > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getPrototypeDate([CtVariableReadImpl]clan);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getProductionDate([CtVariableReadImpl]clan) > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getProductionDate([CtVariableReadImpl]clan);
        }
        [CtReturnImpl]return [CtInvocationImpl]getCommonDate([CtVariableReadImpl]clan);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The year the technology first became available for the given faction, regardless
     * of production level, or APPROXIMATE_MARGIN years earlier if
     * marked as approximate.
     */
    public [CtTypeReferenceImpl]int getIntroductionDate([CtParameterImpl][CtTypeReferenceImpl]boolean clan, [CtParameterImpl][CtTypeReferenceImpl]int faction) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int date = [CtInvocationImpl]getReintroductionDate([CtVariableReadImpl]clan, [CtVariableReadImpl]faction);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getPrototypeDate([CtVariableReadImpl]clan, [CtVariableReadImpl]faction) > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]megamek.common.TechAdvancement.earliestDate([CtVariableReadImpl]date, [CtInvocationImpl]getPrototypeDate([CtVariableReadImpl]clan, [CtVariableReadImpl]faction));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getProductionDate([CtVariableReadImpl]clan, [CtVariableReadImpl]faction) > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]megamek.common.TechAdvancement.earliestDate([CtVariableReadImpl]date, [CtInvocationImpl]getProductionDate([CtVariableReadImpl]clan, [CtVariableReadImpl]faction));
        }
        [CtReturnImpl]return [CtInvocationImpl]megamek.common.TechAdvancement.earliestDate([CtVariableReadImpl]date, [CtInvocationImpl]getCommonDate([CtVariableReadImpl]clan));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Convenience method for calculating approximations.
     */
    private [CtTypeReferenceImpl]int getDate([CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]clan) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtFieldReadImpl]clanApproximate[[CtVariableReadImpl]index] && [CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtVariableReadImpl]index] > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtVariableReadImpl]index] + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]index == [CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT ? [CtLiteralImpl]5 : [CtUnaryOperatorImpl]-[CtLiteralImpl]5);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtVariableReadImpl]index];
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtFieldReadImpl]isApproximate[[CtVariableReadImpl]index] && [CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtVariableReadImpl]index] > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtVariableReadImpl]index] + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]index == [CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT ? [CtLiteralImpl]5 : [CtUnaryOperatorImpl]-[CtLiteralImpl]5);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtVariableReadImpl]index];
        }
    }

    [CtMethodImpl][CtCommentImpl]/* Methods which return universe-wide dates */
    public [CtTypeReferenceImpl]int getPrototypeDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]megamek.common.TechAdvancement.earliestDate([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtLiteralImpl]false), [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtLiteralImpl]true));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getProductionDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]megamek.common.TechAdvancement.earliestDate([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtLiteralImpl]false), [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtLiteralImpl]true));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getCommonDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]megamek.common.TechAdvancement.earliestDate([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtLiteralImpl]false), [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtLiteralImpl]true));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If the tech base is IS or Clan, returns the extinction date that matches the tech base. Otherwise
     * returns the later of the IS and Clan dates, or DATE_NONE if the tech has not gone extinct for both.
     *
     * @return Universe-wide extinction date.
     */
    public [CtTypeReferenceImpl]int getExtinctionDate() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getTechBase() != [CtFieldReadImpl]TECH_BASE_ALL) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtBinaryOperatorImpl][CtInvocationImpl]getTechBase() == [CtFieldReadImpl]TECH_BASE_CLAN);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT] == [CtFieldReadImpl]DATE_NONE) || [CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT] == [CtFieldReadImpl]DATE_NONE)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]DATE_NONE;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtLiteralImpl]false), [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtLiteralImpl]true));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getReintroductionDate() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getTechBase() != [CtFieldReadImpl]TECH_BASE_ALL) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtBinaryOperatorImpl][CtInvocationImpl]getTechBase() == [CtFieldReadImpl]TECH_BASE_CLAN);
        }
        [CtReturnImpl]return [CtInvocationImpl]megamek.common.TechAdvancement.earliestDate([CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtLiteralImpl]false), [CtInvocationImpl]getDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtLiteralImpl]true));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getIntroductionDate() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getPrototypeDate() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getPrototypeDate();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getProductionDate() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getProductionDate();
        }
        [CtReturnImpl]return [CtInvocationImpl]getCommonDate();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats the date at an index for display in a table, showing DATE_NONE as "-" and prepending
     * "~" to approximate dates.
     *
     * @param index
     * 		PROTOTYPE, PRODUCTION, COMMON, EXTINCT, or REINTRODUCED
     * @param clan
     * 		Use the Clan progression
     * @param factions
     * 		A list of factions to include in parentheses after the date.
     * @return  */
    private [CtTypeReferenceImpl]java.lang.String formatDate([CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl][CtTypeReferenceImpl]boolean clan, [CtParameterImpl][CtArrayTypeReferenceImpl]int[] factions) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int date = [CtConditionalImpl]([CtVariableReadImpl]clan) ? [CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtVariableReadImpl]index] : [CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtVariableReadImpl]index];
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]date == [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"-";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtIfImpl]if ([CtConditionalImpl][CtVariableReadImpl]clan ? [CtArrayReadImpl][CtFieldReadImpl]clanApproximate[[CtVariableReadImpl]index] : [CtArrayReadImpl][CtFieldReadImpl]isApproximate[[CtVariableReadImpl]index]) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"~");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]date == [CtFieldReadImpl]DATE_PS) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"PS");
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]date == [CtFieldReadImpl]DATE_ES) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"ES");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]date);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]factions != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]factions.length > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.StringJoiner sj = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.StringJoiner([CtLiteralImpl]",");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int f : [CtVariableReadImpl]factions) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clan && [CtBinaryOperatorImpl]([CtVariableReadImpl]f >= [CtFieldReadImpl]F_CLAN)) || [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtVariableReadImpl]clan) && [CtBinaryOperatorImpl]([CtVariableReadImpl]f < [CtFieldReadImpl]F_CLAN))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sj.add([CtArrayReadImpl][CtFieldReadImpl]IO_FACTION_CODES[[CtVariableReadImpl]f]);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sj.length() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(").append([CtInvocationImpl][CtVariableReadImpl]sj.toString()).append([CtLiteralImpl]")");
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats introduction date indicating approximate when appropriate, and prototype faction if any
     * for either IS or Clan use tech base.
     */
    public [CtTypeReferenceImpl]java.lang.String getIntroductionDateName() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getPrototypeDate() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getPrototypeDateName();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getProductionDate() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getProductionDateName();
        }
        [CtReturnImpl]return [CtInvocationImpl]getCommonDateName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats prototype date indicating approximate when appropriate, and prototype faction if any
     * for either IS or Clan use tech base.
     */
    public [CtTypeReferenceImpl]java.lang.String getPrototypeDateName([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtVariableReadImpl]clan, [CtFieldReadImpl]prototypeFactions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats earliest of Clan or IS prototype date indicating approximate when appropriate,
     * and prototype faction if any for mixed tech.
     */
    public [CtTypeReferenceImpl]java.lang.String getPrototypeDateName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean useClanDate = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE] == [CtFieldReadImpl]DATE_NONE) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE] != [CtFieldReadImpl]DATE_NONE) && [CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE] < [CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE]));
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.PROTOTYPE, [CtVariableReadImpl]useClanDate, [CtFieldReadImpl]prototypeFactions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats production date indicating approximate when appropriate, and production faction if any
     * for either IS or Clan use tech base.
     */
    public [CtTypeReferenceImpl]java.lang.String getProductionDateName([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]clan, [CtFieldReadImpl]productionFactions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats earliest of Clan or IS production date indicating approximate when appropriate,
     * and production faction if any for mixed tech.
     */
    public [CtTypeReferenceImpl]java.lang.String getProductionDateName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean useClanDate = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION] == [CtFieldReadImpl]DATE_NONE) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION] != [CtFieldReadImpl]DATE_NONE) && [CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION] < [CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION]));
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.PRODUCTION, [CtVariableReadImpl]useClanDate, [CtFieldReadImpl]productionFactions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats common date indicating approximate when appropriate.
     */
    public [CtTypeReferenceImpl]java.lang.String getCommonDateName([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtVariableReadImpl]clan, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats earliest of Clan or IS common date indicating approximate when appropriate for mixed tech.
     */
    public [CtTypeReferenceImpl]java.lang.String getCommonDateName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean useClanDate = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.COMMON] == [CtFieldReadImpl]DATE_NONE) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.COMMON] != [CtFieldReadImpl]DATE_NONE) && [CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.COMMON] < [CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.COMMON]));
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.COMMON, [CtVariableReadImpl]useClanDate, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats extinction date indicating approximate when appropriate, and extinction faction if any
     * for either IS or Clan use tech base.
     */
    public [CtTypeReferenceImpl]java.lang.String getExtinctionDateName([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtVariableReadImpl]clan, [CtFieldReadImpl]extinctionFactions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats latest of Clan or IS extinction date indicating approximate when appropriate,
     * and extinction faction if any for mixed tech.
     */
    public [CtTypeReferenceImpl]java.lang.String getExtinctionDateName() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]techBase == [CtFieldReadImpl]TECH_BASE_ALL) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT] == [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// If there is no IS date, choose the Clan date
                return [CtInvocationImpl]getExtinctionDateName([CtLiteralImpl]true);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT] == [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// If there is no Clan date, choose the IS date
                return [CtInvocationImpl]getExtinctionDateName([CtLiteralImpl]false);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT, [CtBinaryOperatorImpl][CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT] > [CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.EXTINCT], [CtFieldReadImpl]extinctionFactions);
            }
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getExtinctionDateName([CtBinaryOperatorImpl][CtFieldReadImpl]techBase == [CtFieldReadImpl]TECH_BASE_CLAN);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats reintroduction date indicating approximate when appropriate, and reintroduction faction if any
     * for either IS or Clan use tech base.
     */
    public [CtTypeReferenceImpl]java.lang.String getReintroductionDateName([CtParameterImpl][CtTypeReferenceImpl]boolean clan) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]clan, [CtFieldReadImpl]reintroductionFactions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Formats earliest of Clan or IS reintroduction date indicating approximate when appropriate,
     * and reintroduction faction if any for mixed tech.
     */
    public [CtTypeReferenceImpl]java.lang.String getReintroductionDateName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean useClanDate = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED] == [CtFieldReadImpl]DATE_NONE) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED] != [CtFieldReadImpl]DATE_NONE) && [CtBinaryOperatorImpl]([CtArrayReadImpl][CtFieldReadImpl]clanAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED] < [CtArrayReadImpl][CtFieldReadImpl]isAdvancement[[CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED]));
        [CtReturnImpl]return [CtInvocationImpl]formatDate([CtFieldReadImpl]megamek.common.TechAdvancement.REINTRODUCED, [CtVariableReadImpl]useClanDate, [CtFieldReadImpl]reintroductionFactions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Finds the earliest of two dates, ignoring DATE_NA unless both values are set to DATE_NA
     */
    public static [CtTypeReferenceImpl]int earliestDate([CtParameterImpl][CtTypeReferenceImpl]int d1, [CtParameterImpl][CtTypeReferenceImpl]int d2) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]d1 < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]d2;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]d2 < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]d1;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]d1, [CtVariableReadImpl]d2);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.TechAdvancement setIntroLevel([CtParameterImpl][CtTypeReferenceImpl]boolean intro) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]intro) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]staticTechLevel = [CtFieldReadImpl]SimpleTechLevel.INTRO;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]staticTechLevel == [CtFieldReadImpl]SimpleTechLevel.INTRO) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]staticTechLevel = [CtFieldReadImpl]SimpleTechLevel.STANDARD;
        }
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.TechAdvancement setUnofficial([CtParameterImpl][CtTypeReferenceImpl]boolean unofficial) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]unofficial) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]staticTechLevel = [CtFieldReadImpl]SimpleTechLevel.UNOFFICIAL;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]staticTechLevel == [CtFieldReadImpl]SimpleTechLevel.UNOFFICIAL) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]staticTechLevel = [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]megamek.common.SimpleTechLevel getStaticTechLevel() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]staticTechLevel;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.TechAdvancement setStaticTechLevel([CtParameterImpl][CtTypeReferenceImpl]megamek.common.SimpleTechLevel level) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]staticTechLevel = [CtVariableReadImpl]level;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.SimpleTechLevel guessStaticTechLevel([CtParameterImpl][CtTypeReferenceImpl]java.lang.String rulesRefs) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rulesRefs.contains([CtLiteralImpl]"TW") || [CtInvocationImpl][CtVariableReadImpl]rulesRefs.contains([CtLiteralImpl]"TM")) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]SimpleTechLevel.STANDARD;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getProductionDate() != [CtFieldReadImpl]DATE_NONE) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]SimpleTechLevel.ADVANCED;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]SimpleTechLevel.EXPERIMENTAL;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.TechAdvancement setTechRating([CtParameterImpl][CtTypeReferenceImpl]int rating) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]techRating = [CtVariableReadImpl]rating;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTechRating() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]techRating;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.TechAdvancement setAvailability([CtParameterImpl]int... av) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]av, [CtLiteralImpl]0, [CtFieldReadImpl]availability, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtFieldReadImpl][CtVariableReadImpl]av.length, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]availability.length));
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.TechAdvancement setAvailability([CtParameterImpl][CtTypeReferenceImpl]int era, [CtParameterImpl][CtTypeReferenceImpl]int av) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]era > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]era < [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]availability.length)) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtFieldReadImpl]availability[[CtVariableReadImpl]era] = [CtVariableReadImpl]av;
        }
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getBaseAvailability([CtParameterImpl][CtTypeReferenceImpl]int era) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]era < [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]era >= [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]availability.length)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]RATING_X;
        }
        [CtReturnImpl]return [CtArrayReadImpl][CtFieldReadImpl]availability[[CtVariableReadImpl]era];
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isClan() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]techBase == [CtFieldReadImpl]TECH_BASE_CLAN;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isMixedTech() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]techBase == [CtFieldReadImpl]TECH_BASE_ALL;
    }
}
[CompilationUnitImpl][CtJavaDocImpl]/**
 * This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter Güttinger and contributors
 */
[CtPackageDeclarationImpl]package ch.njol.skript.conditions;
[CtUnresolvedImport]import ch.njol.skript.doc.Since;
[CtUnresolvedImport]import ch.njol.skript.doc.Examples;
[CtUnresolvedImport]import ch.njol.skript.doc.Name;
[CtUnresolvedImport]import ch.njol.skript.doc.Description;
[CtUnresolvedImport]import ch.njol.skript.aliases.ItemType;
[CtUnresolvedImport]import ch.njol.skript.conditions.base.PropertyCondition;
[CtClassImpl][CtAnnotationImpl]@ch.njol.skript.doc.Name([CtLiteralImpl]"Is Occluding")
[CtAnnotationImpl]@ch.njol.skript.doc.Description([CtLiteralImpl]"Checks whether an item is a block and completely blocks vision.")
[CtAnnotationImpl]@ch.njol.skript.doc.Examples([CtLiteralImpl]"player's tool is occluding")
[CtAnnotationImpl]@ch.njol.skript.doc.Since([CtLiteralImpl]"INSERT VERSION")
public class CondIsOccluding extends [CtTypeReferenceImpl]ch.njol.skript.conditions.base.PropertyCondition<[CtTypeReferenceImpl]ch.njol.skript.aliases.ItemType> {
    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtInvocationImpl]register([CtFieldReadImpl]ch.njol.skript.conditions.CondIsOccluding.class, [CtLiteralImpl]"occluding", [CtLiteralImpl]"itemtypes");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean check([CtParameterImpl][CtTypeReferenceImpl]ch.njol.skript.aliases.ItemType item) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.getMaterial().isOccluding();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getPropertyName() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"occluding";
    }
}
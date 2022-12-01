[CompilationUnitImpl][CtCommentImpl]/* This file is part of Sponge, licensed under the MIT License (MIT).

Copyright (c) SpongePowered <https://www.spongepowered.org>
Copyright (c) contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
[CtPackageDeclarationImpl]package org.spongepowered.common.config.category;
[CtUnresolvedImport]import ninja.leaping.configurate.objectmapping.Setting;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
[CtImportImpl]import java.util.Map;
[CtClassImpl][CtAnnotationImpl]@ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable
public class TileEntityActivationModCategory extends [CtTypeReferenceImpl]org.spongepowered.common.config.category.ConfigCategory {
    [CtFieldImpl][CtAnnotationImpl]@ninja.leaping.configurate.objectmapping.Setting(value = [CtLiteralImpl]"enabled", comment = [CtLiteralImpl]"If 'false', tileentity activation rules for this mod will be ignored and always tick.")
    private [CtTypeReferenceImpl]boolean isEnabled = [CtLiteralImpl]true;

    [CtFieldImpl][CtAnnotationImpl]@ninja.leaping.configurate.objectmapping.Setting(value = [CtLiteralImpl]"default-block-range", comment = [CtLiteralImpl]"???")
    private [CtTypeReferenceImpl]java.lang.Integer defaultBlockRange;

    [CtFieldImpl][CtAnnotationImpl]@ninja.leaping.configurate.objectmapping.Setting(value = [CtLiteralImpl]"default-tick-rate", comment = [CtLiteralImpl]"???")
    private [CtTypeReferenceImpl]java.lang.Integer defaultTickRate;

    [CtFieldImpl][CtAnnotationImpl]@ninja.leaping.configurate.objectmapping.Setting(value = [CtLiteralImpl]"block-range", comment = [CtLiteralImpl]"???")
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> tileEntityRangeList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl][CtAnnotationImpl]@ninja.leaping.configurate.objectmapping.Setting(value = [CtLiteralImpl]"tick-rate", comment = [CtLiteralImpl]"???")
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> tileEntityTickRateList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtConstructorImpl]public TileEntityActivationModCategory() [CtBlockImpl]{
    }

    [CtConstructorImpl]public TileEntityActivationModCategory([CtParameterImpl][CtTypeReferenceImpl]java.lang.String modId) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]modId.equalsIgnoreCase([CtLiteralImpl]"computercraft")) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList.put([CtLiteralImpl]"advanced_modem", [CtLiteralImpl]0);
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList.put([CtLiteralImpl]"ccprinter", [CtLiteralImpl]0);
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList.put([CtLiteralImpl]"diskdrive", [CtLiteralImpl]0);
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList.put([CtLiteralImpl]"turtleex", [CtLiteralImpl]0);
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList.put([CtLiteralImpl]"wiredmodem", [CtLiteralImpl]0);
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList.put([CtLiteralImpl]"wirelessmodem", [CtLiteralImpl]0);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]modId.equalsIgnoreCase([CtLiteralImpl]"plethora-core")) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList.put([CtLiteralImpl]"plethora:manipulator", [CtLiteralImpl]0);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.isEnabled;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Integer getDefaultBlockRange() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.defaultBlockRange;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Integer getDefaultTickRate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.defaultTickRate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> getTileEntityRangeList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.tileEntityRangeList;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> getTileEntityTickRateList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.tileEntityTickRateList;
    }
}
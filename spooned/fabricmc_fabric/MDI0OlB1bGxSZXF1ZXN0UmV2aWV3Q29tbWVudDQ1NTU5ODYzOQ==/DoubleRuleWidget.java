[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2016, 2017, 2018, 2019 FabricMC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package net.fabricmc.fabric.impl.gamerule.widget;
[CtUnresolvedImport]import net.minecraft.client.gui.widget.TextFieldWidget;
[CtUnresolvedImport]import net.fabricmc.fabric.mixin.gamerule.client.EditGameRulesScreenAccessor;
[CtUnresolvedImport]import net.minecraft.client.util.math.MatrixStack;
[CtUnresolvedImport]import net.fabricmc.api.Environment;
[CtUnresolvedImport]import net.fabricmc.api.EnvType;
[CtUnresolvedImport]import net.minecraft.client.MinecraftClient;
[CtUnresolvedImport]import net.minecraft.text.Text;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
[CtUnresolvedImport]import net.minecraft.text.StringRenderable;
[CtUnresolvedImport]import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
[CtClassImpl][CtAnnotationImpl]@net.fabricmc.api.Environment([CtFieldReadImpl]net.fabricmc.api.EnvType.CLIENT)
public final class DoubleRuleWidget extends [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraft.client.gui.screen.world.EditGameRulesScreen.NamedRuleWidget {
    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraft.client.gui.widget.TextFieldWidget textFieldWidget;

    [CtConstructorImpl]public DoubleRuleWidget([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.client.gui.screen.world.EditGameRulesScreen gameRuleScreen, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.text.Text name, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraft.text.StringRenderable> description, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String ruleName, [CtParameterImpl][CtTypeReferenceImpl]net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule rule) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]gameRuleScreen.super([CtVariableReadImpl]description, [CtVariableReadImpl]name);
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.fabricmc.fabric.mixin.gamerule.client.EditGameRulesScreenAccessor accessor = [CtVariableReadImpl](([CtTypeReferenceImpl]net.fabricmc.fabric.mixin.gamerule.client.EditGameRulesScreenAccessor) (gameRuleScreen));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.textFieldWidget = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraft.client.gui.widget.TextFieldWidget([CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]net.minecraft.client.MinecraftClient.getInstance().textRenderer, [CtLiteralImpl]10, [CtLiteralImpl]5, [CtLiteralImpl]42, [CtLiteralImpl]20, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]name.shallowCopy().append([CtLiteralImpl]"\n").append([CtVariableReadImpl]ruleName).append([CtLiteralImpl]"\n"));
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.toString([CtInvocationImpl][CtVariableReadImpl]rule.get()));
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget.setChangedListener([CtLambdaImpl]([CtParameterImpl] value) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rule.validate([CtVariableReadImpl]value)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget.setEditableColor([CtLiteralImpl]14737632);
                [CtInvocationImpl][CtVariableReadImpl]accessor.callMarkValid([CtThisAccessImpl]this);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget.setEditableColor([CtLiteralImpl]16711680);
                [CtInvocationImpl][CtVariableReadImpl]accessor.callMarkInvalid([CtThisAccessImpl]this);
            }
        });
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.children.add([CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void render([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.client.util.math.MatrixStack matrices, [CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl][CtTypeReferenceImpl]int y, [CtParameterImpl][CtTypeReferenceImpl]int x, [CtParameterImpl][CtTypeReferenceImpl]int entryWidth, [CtParameterImpl][CtTypeReferenceImpl]int entryHeight, [CtParameterImpl][CtTypeReferenceImpl]int mouseX, [CtParameterImpl][CtTypeReferenceImpl]int mouseY, [CtParameterImpl][CtTypeReferenceImpl]boolean hovered, [CtParameterImpl][CtTypeReferenceImpl]float tickDelta) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// FIXME: Param names nightmare
        [CtThisAccessImpl]this.drawName([CtVariableReadImpl]matrices, [CtVariableReadImpl]y, [CtVariableReadImpl]x);
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget.x = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]x + [CtVariableReadImpl]entryWidth) - [CtLiteralImpl]44;
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget.y = [CtVariableReadImpl]y;
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.textFieldWidget.render([CtVariableReadImpl]matrices, [CtVariableReadImpl]mouseX, [CtVariableReadImpl]mouseY, [CtVariableReadImpl]tickDelta);
    }
}
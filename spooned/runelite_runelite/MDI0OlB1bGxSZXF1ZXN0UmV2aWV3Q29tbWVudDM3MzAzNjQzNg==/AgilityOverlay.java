[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2018, Adam <Adam@sigterm.info>
Copyright (c) 2018, Cas <https://github.com/casvandongen>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
[CtPackageDeclarationImpl]package net.runelite.client.plugins.agility;
[CtImportImpl]import java.awt.Color;
[CtImportImpl]import java.awt.Shape;
[CtUnresolvedImport]import net.runelite.api.coords.LocalPoint;
[CtUnresolvedImport]import net.runelite.client.ui.overlay.OverlayLayer;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import net.runelite.client.ui.overlay.OverlayUtil;
[CtUnresolvedImport]import net.runelite.client.game.AgilityShortcut;
[CtImportImpl]import java.awt.Dimension;
[CtUnresolvedImport]import net.runelite.api.Point;
[CtImportImpl]import java.awt.Graphics2D;
[CtUnresolvedImport]import net.runelite.api.Client;
[CtUnresolvedImport]import net.runelite.client.ui.overlay.Overlay;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import net.runelite.client.ui.overlay.OverlayPosition;
[CtImportImpl]import java.awt.Polygon;
[CtUnresolvedImport]import net.runelite.api.Tile;
[CtClassImpl]class AgilityOverlay extends [CtTypeReferenceImpl]net.runelite.client.ui.overlay.Overlay {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MAX_DISTANCE = [CtLiteralImpl]2350;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.awt.Color SHORTCUT_HIGH_LEVEL_COLOR = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]ORANGE;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.runelite.api.Client client;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.runelite.client.plugins.agility.AgilityPlugin plugin;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.runelite.client.plugins.agility.AgilityConfig config;

    [CtConstructorImpl][CtAnnotationImpl]@javax.inject.Inject
    private AgilityOverlay([CtParameterImpl][CtTypeReferenceImpl]net.runelite.api.Client client, [CtParameterImpl][CtTypeReferenceImpl]net.runelite.client.plugins.agility.AgilityPlugin plugin, [CtParameterImpl][CtTypeReferenceImpl]net.runelite.client.plugins.agility.AgilityConfig config) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]plugin);
        [CtInvocationImpl]setPosition([CtTypeAccessImpl]OverlayPosition.DYNAMIC);
        [CtInvocationImpl]setLayer([CtTypeAccessImpl]OverlayLayer.ABOVE_SCENE);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.client = [CtVariableReadImpl]client;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.plugin = [CtVariableReadImpl]plugin;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.config = [CtVariableReadImpl]config;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.awt.Dimension render([CtParameterImpl][CtTypeReferenceImpl]java.awt.Graphics2D graphics) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.api.coords.LocalPoint playerLocation = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.getLocalPlayer().getLocalLocation();
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.api.Point mousePosition = [CtInvocationImpl][CtFieldReadImpl]client.getMouseCanvasPosition();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.runelite.api.Tile> marksOfGrace = [CtInvocationImpl][CtFieldReadImpl]plugin.getMarksOfGrace();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]net.runelite.api.Tile stickTile = [CtInvocationImpl][CtFieldReadImpl]plugin.getStickTile();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]plugin.getObstacles().forEach([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] obstacle) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]Obstacles.SHORTCUT_OBSTACLE_IDS.containsKey([CtInvocationImpl][CtVariableReadImpl]object.getId()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.highlightShortcuts())) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]Obstacles.TRAP_OBSTACLE_IDS.contains([CtInvocationImpl][CtVariableReadImpl]object.getId()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.showTrapOverlay()))) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]Obstacles.COURSE_OBSTACLE_IDS.contains([CtInvocationImpl][CtVariableReadImpl]object.getId()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.showClickboxes()))) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.api.Tile tile = [CtInvocationImpl][CtVariableReadImpl]obstacle.getTile();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tile.getPlane() == [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]client.getPlane()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]object.getLocalLocation().distanceTo([CtVariableReadImpl]playerLocation) < [CtFieldReadImpl][CtFieldReferenceImpl]MAX_DISTANCE)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// This assumes that the obstacle is not clickable.
                if ([CtInvocationImpl][CtVariableReadImpl]Obstacles.TRAP_OBSTACLE_IDS.contains([CtInvocationImpl][CtVariableReadImpl]object.getId())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Polygon polygon = [CtInvocationImpl][CtVariableReadImpl]object.getCanvasTilePoly();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]polygon != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.ui.overlay.OverlayUtil.renderPolygon([CtVariableReadImpl]graphics, [CtVariableReadImpl]polygon, [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.getTrapColor());
                    }
                    [CtReturnImpl]return;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Shape objectClickbox = [CtInvocationImpl][CtVariableReadImpl]object.getClickbox();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]objectClickbox != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.game.AgilityShortcut agilityShortcut = [CtInvocationImpl][CtVariableReadImpl]obstacle.getShortcut();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color configColor = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]agilityShortcut == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]agilityShortcut.getLevel() <= [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]plugin.getAgilityLevel())) ? [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.getOverlayColor() : [CtFieldReadImpl][CtFieldReferenceImpl]SHORTCUT_HIGH_LEVEL_COLOR;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.highlightMarks() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]marksOfGrace.isEmpty())) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]configColor = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.getMarkColor();
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]objectClickbox.contains([CtInvocationImpl][CtVariableReadImpl]mousePosition.getX(), [CtInvocationImpl][CtVariableReadImpl]mousePosition.getY())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]graphics.setColor([CtInvocationImpl][CtVariableReadImpl]configColor.darker());
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]graphics.setColor([CtVariableReadImpl]configColor);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]graphics.draw([CtVariableReadImpl]objectClickbox);
                    [CtInvocationImpl][CtVariableReadImpl]graphics.setColor([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Color([CtInvocationImpl][CtVariableReadImpl]configColor.getRed(), [CtInvocationImpl][CtVariableReadImpl]configColor.getGreen(), [CtInvocationImpl][CtVariableReadImpl]configColor.getBlue(), [CtLiteralImpl]50));
                    [CtInvocationImpl][CtVariableReadImpl]graphics.fill([CtVariableReadImpl]objectClickbox);
                }
            }
        });
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]config.highlightMarks() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]marksOfGrace.isEmpty())) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.api.Tile markOfGraceTile : [CtVariableReadImpl]marksOfGrace) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]markOfGraceTile.getPlane() == [CtInvocationImpl][CtFieldReadImpl]client.getPlane()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]markOfGraceTile.getItemLayer() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]markOfGraceTile.getLocalLocation().distanceTo([CtVariableReadImpl]playerLocation) < [CtFieldReadImpl]net.runelite.client.plugins.agility.AgilityOverlay.MAX_DISTANCE)) [CtBlockImpl]{
                    [CtInvocationImpl]highlightTile([CtVariableReadImpl]graphics, [CtVariableReadImpl]playerLocation, [CtVariableReadImpl]markOfGraceTile, [CtInvocationImpl][CtFieldReadImpl]config.getMarkColor());
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]config.highlightStick() && [CtBinaryOperatorImpl]([CtVariableReadImpl]stickTile != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl]highlightTile([CtVariableReadImpl]graphics, [CtVariableReadImpl]playerLocation, [CtVariableReadImpl]stickTile, [CtInvocationImpl][CtFieldReadImpl]config.getStickColor());
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void highlightTile([CtParameterImpl][CtTypeReferenceImpl]java.awt.Graphics2D graphics, [CtParameterImpl][CtTypeReferenceImpl]net.runelite.api.coords.LocalPoint playerLocation, [CtParameterImpl][CtTypeReferenceImpl]net.runelite.api.Tile tile, [CtParameterImpl][CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tile.getPlane() == [CtInvocationImpl][CtFieldReadImpl]client.getPlane()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tile.getItemLayer() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tile.getLocalLocation().distanceTo([CtVariableReadImpl]playerLocation) < [CtFieldReadImpl]net.runelite.client.plugins.agility.AgilityOverlay.MAX_DISTANCE)) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.Polygon poly = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tile.getItemLayer().getCanvasTilePoly();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]poly != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.ui.overlay.OverlayUtil.renderPolygon([CtVariableReadImpl]graphics, [CtVariableReadImpl]poly, [CtVariableReadImpl]color);
            }
        }
    }
}
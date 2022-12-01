[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2018, Psikoi <https://github.com/psikoi>
Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
[CtPackageDeclarationImpl]package net.runelite.client.plugins.loottracker;
[CtImportImpl]import java.util.function.Predicate;
[CtImportImpl]import javax.swing.ImageIcon;
[CtImportImpl]import javax.swing.JPanel;
[CtUnresolvedImport]import net.runelite.client.ui.ColorScheme;
[CtImportImpl]import javax.swing.BoxLayout;
[CtImportImpl]import javax.swing.JRadioButton;
[CtUnresolvedImport]import net.runelite.client.util.QuantityFormatter;
[CtUnresolvedImport]import net.runelite.client.util.ColorUtil;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import javax.swing.JButton;
[CtImportImpl]import javax.swing.plaf.basic.BasicButtonUI;
[CtImportImpl]import java.awt.image.BufferedImage;
[CtUnresolvedImport]import net.runelite.client.ui.components.PluginErrorPanel;
[CtImportImpl]import java.awt.GridLayout;
[CtImportImpl]import java.awt.BorderLayout;
[CtImportImpl]import javax.swing.JToggleButton;
[CtUnresolvedImport]import static com.google.common.collect.Iterables.concat;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import net.runelite.http.api.loottracker.LootRecordType;
[CtImportImpl]import javax.swing.JMenuItem;
[CtUnresolvedImport]import net.runelite.client.game.ItemManager;
[CtImportImpl]import javax.swing.border.EmptyBorder;
[CtImportImpl]import java.awt.Color;
[CtImportImpl]import java.awt.event.MouseEvent;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import javax.swing.BorderFactory;
[CtImportImpl]import javax.swing.JOptionPane;
[CtImportImpl]import javax.swing.plaf.basic.BasicToggleButtonUI;
[CtUnresolvedImport]import net.runelite.http.api.loottracker.LootTrackerClient;
[CtImportImpl]import javax.swing.JLabel;
[CtUnresolvedImport]import net.runelite.client.util.ImageUtil;
[CtImportImpl]import java.awt.event.MouseAdapter;
[CtImportImpl]import java.awt.Dimension;
[CtImportImpl]import javax.swing.JPopupMenu;
[CtUnresolvedImport]import net.runelite.client.ui.PluginPanel;
[CtUnresolvedImport]import net.runelite.client.util.SwingUtil;
[CtImportImpl]import javax.swing.ButtonGroup;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import net.runelite.client.ui.FontManager;
[CtClassImpl]class LootTrackerPanel extends [CtTypeReferenceImpl]net.runelite.client.ui.PluginPanel {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MAX_LOOT_BOXES = [CtLiteralImpl]500;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon SINGLE_LOOT_VIEW;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon SINGLE_LOOT_VIEW_FADED;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon SINGLE_LOOT_VIEW_HOVER;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon GROUPED_LOOT_VIEW;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon GROUPED_LOOT_VIEW_FADED;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon GROUPED_LOOT_VIEW_HOVER;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon BACK_ARROW_ICON;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon BACK_ARROW_ICON_HOVER;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon VISIBLE_ICON;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon VISIBLE_ICON_HOVER;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon INVISIBLE_ICON;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon INVISIBLE_ICON_HOVER;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon COLLAPSE_ICON;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]javax.swing.ImageIcon EXPAND_ICON;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String HTML_LABEL_TEMPLATE = [CtLiteralImpl]"<html><body style='color:%s'>%s<span style='color:white'>%s</span></body></html>";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SYNC_RESET_ALL_WARNING_TEXT = [CtLiteralImpl]"This will permanently delete the current loot from both the client and the RuneLite website.";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NO_SYNC_RESET_ALL_WARNING_TEXT = [CtLiteralImpl]"This will permanently delete the current loot from the client.";

    [CtFieldImpl][CtCommentImpl]// When there is no loot, display this
    private final [CtTypeReferenceImpl]net.runelite.client.ui.components.PluginErrorPanel errorPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.runelite.client.ui.components.PluginErrorPanel();

    [CtFieldImpl][CtCommentImpl]// Handle loot boxes
    private final [CtTypeReferenceImpl]javax.swing.JPanel logsContainer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();

    [CtFieldImpl][CtCommentImpl]// Handle overall session data
    private final [CtTypeReferenceImpl]javax.swing.JPanel overallPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JLabel overallKillsLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JLabel overallGpLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JLabel overallIcon = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel();

    [CtFieldImpl][CtCommentImpl]// Details and navigation
    private final [CtTypeReferenceImpl]javax.swing.JPanel actionsContainer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JLabel detailsTitle = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JButton backBtn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JButton();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JToggleButton viewHiddenBtn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JToggleButton();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JRadioButton singleLootBtn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButton();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JRadioButton groupedLootBtn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButton();

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.swing.JButton collapseBtn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JButton();

    [CtFieldImpl][CtCommentImpl]// Aggregate of all kills
    private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord> aggregateRecords = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// Individual records for the individual kills this session
    private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord> sessionRecords = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerBox> boxes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.runelite.client.game.ItemManager itemManager;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin plugin;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerConfig config;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean groupLoot;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean hideIgnoredItems;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String currentView;

    [CtFieldImpl]private [CtTypeReferenceImpl]net.runelite.http.api.loottracker.LootRecordType currentType;

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.image.BufferedImage singleLootImg = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.getResourceStreamFromClass([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin.class, [CtLiteralImpl]"single_loot_icon.png");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.image.BufferedImage groupedLootImg = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.getResourceStreamFromClass([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin.class, [CtLiteralImpl]"grouped_loot_icon.png");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.image.BufferedImage backArrowImg = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.getResourceStreamFromClass([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin.class, [CtLiteralImpl]"back_icon.png");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.image.BufferedImage visibleImg = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.getResourceStreamFromClass([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin.class, [CtLiteralImpl]"visible_icon.png");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.image.BufferedImage invisibleImg = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.getResourceStreamFromClass([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin.class, [CtLiteralImpl]"invisible_icon.png");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.image.BufferedImage collapseImg = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.getResourceStreamFromClass([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin.class, [CtLiteralImpl]"collapsed.png");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.image.BufferedImage expandedImg = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.getResourceStreamFromClass([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin.class, [CtLiteralImpl]"expanded.png");
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.SINGLE_LOOT_VIEW = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]singleLootImg);
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.SINGLE_LOOT_VIEW_FADED = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.alphaOffset([CtVariableReadImpl]singleLootImg, [CtUnaryOperatorImpl]-[CtLiteralImpl]180));
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.SINGLE_LOOT_VIEW_HOVER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.alphaOffset([CtVariableReadImpl]singleLootImg, [CtUnaryOperatorImpl]-[CtLiteralImpl]220));
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.GROUPED_LOOT_VIEW = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]groupedLootImg);
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.GROUPED_LOOT_VIEW_FADED = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.alphaOffset([CtVariableReadImpl]groupedLootImg, [CtUnaryOperatorImpl]-[CtLiteralImpl]180));
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.GROUPED_LOOT_VIEW_HOVER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.alphaOffset([CtVariableReadImpl]groupedLootImg, [CtUnaryOperatorImpl]-[CtLiteralImpl]220));
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.BACK_ARROW_ICON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]backArrowImg);
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.BACK_ARROW_ICON_HOVER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.alphaOffset([CtVariableReadImpl]backArrowImg, [CtUnaryOperatorImpl]-[CtLiteralImpl]180));
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.VISIBLE_ICON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]visibleImg);
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.VISIBLE_ICON_HOVER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.alphaOffset([CtVariableReadImpl]visibleImg, [CtUnaryOperatorImpl]-[CtLiteralImpl]220));
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.INVISIBLE_ICON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]invisibleImg);
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.INVISIBLE_ICON_HOVER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ImageUtil.alphaOffset([CtVariableReadImpl]invisibleImg, [CtUnaryOperatorImpl]-[CtLiteralImpl]220));
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.COLLAPSE_ICON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]collapseImg);
        [CtAssignmentImpl][CtFieldWriteImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.EXPAND_ICON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]expandedImg);
    }

    [CtConstructorImpl]LootTrackerPanel([CtParameterImpl]final [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerPlugin plugin, [CtParameterImpl]final [CtTypeReferenceImpl]net.runelite.client.game.ItemManager itemManager, [CtParameterImpl]final [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerConfig config) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.itemManager = [CtVariableReadImpl]itemManager;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.plugin = [CtVariableReadImpl]plugin;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.config = [CtVariableReadImpl]config;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hideIgnoredItems = [CtLiteralImpl]true;
        [CtInvocationImpl]setBorder([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.border.EmptyBorder([CtLiteralImpl]6, [CtLiteralImpl]6, [CtLiteralImpl]6, [CtLiteralImpl]6));
        [CtInvocationImpl]setBackground([CtTypeAccessImpl]ColorScheme.DARK_GRAY_COLOR);
        [CtInvocationImpl]setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.BorderLayout());
        [CtLocalVariableImpl][CtCommentImpl]// Create layout panel for wrapping
        final [CtTypeReferenceImpl]javax.swing.JPanel layoutPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtVariableReadImpl]layoutPanel.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.BoxLayout([CtVariableReadImpl]layoutPanel, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]Y_AXIS));
        [CtInvocationImpl]add([CtVariableReadImpl]layoutPanel, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]NORTH);
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.BorderLayout());
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.setBackground([CtTypeAccessImpl]ColorScheme.DARKER_GRAY_COLOR);
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.setPreferredSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]30));
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.setBorder([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.border.EmptyBorder([CtLiteralImpl]5, [CtLiteralImpl]5, [CtLiteralImpl]5, [CtLiteralImpl]10));
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.setVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.swing.JPanel viewControls = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.GridLayout([CtLiteralImpl]1, [CtLiteralImpl]3, [CtLiteralImpl]10, [CtLiteralImpl]0));
        [CtInvocationImpl][CtVariableReadImpl]viewControls.setBackground([CtTypeAccessImpl]ColorScheme.DARKER_GRAY_COLOR);
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.removeButtonDecorations([CtFieldReadImpl]collapseBtn);
        [CtInvocationImpl][CtFieldReadImpl]collapseBtn.setIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.EXPAND_ICON);
        [CtInvocationImpl][CtFieldReadImpl]collapseBtn.setSelectedIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.COLLAPSE_ICON);
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.addModalTooltip([CtFieldReadImpl]collapseBtn, [CtLiteralImpl]"Collapse All", [CtLiteralImpl]"Un-Collapse All");
        [CtInvocationImpl][CtFieldReadImpl]collapseBtn.setBackground([CtTypeAccessImpl]ColorScheme.DARKER_GRAY_COLOR);
        [CtInvocationImpl][CtFieldReadImpl]collapseBtn.setUI([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.plaf.basic.BasicButtonUI());[CtCommentImpl]// substance breaks the layout

        [CtInvocationImpl][CtFieldReadImpl]collapseBtn.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent ev) -> [CtInvocationImpl]changeCollapse());
        [CtInvocationImpl][CtVariableReadImpl]viewControls.add([CtFieldReadImpl]collapseBtn);
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.removeButtonDecorations([CtFieldReadImpl]singleLootBtn);
        [CtInvocationImpl][CtFieldReadImpl]singleLootBtn.setIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.SINGLE_LOOT_VIEW_FADED);
        [CtInvocationImpl][CtFieldReadImpl]singleLootBtn.setRolloverIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.SINGLE_LOOT_VIEW_HOVER);
        [CtInvocationImpl][CtFieldReadImpl]singleLootBtn.setSelectedIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.SINGLE_LOOT_VIEW);
        [CtInvocationImpl][CtFieldReadImpl]singleLootBtn.setToolTipText([CtLiteralImpl]"Show each kill separately");
        [CtInvocationImpl][CtFieldReadImpl]singleLootBtn.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent e) -> [CtInvocationImpl]changeGrouping([CtLiteralImpl]false));
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.removeButtonDecorations([CtFieldReadImpl]groupedLootBtn);
        [CtInvocationImpl][CtFieldReadImpl]groupedLootBtn.setIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.GROUPED_LOOT_VIEW_FADED);
        [CtInvocationImpl][CtFieldReadImpl]groupedLootBtn.setRolloverIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.GROUPED_LOOT_VIEW_HOVER);
        [CtInvocationImpl][CtFieldReadImpl]groupedLootBtn.setSelectedIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.GROUPED_LOOT_VIEW);
        [CtInvocationImpl][CtFieldReadImpl]groupedLootBtn.setToolTipText([CtLiteralImpl]"Group loot by source");
        [CtInvocationImpl][CtFieldReadImpl]groupedLootBtn.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent e) -> [CtInvocationImpl]changeGrouping([CtLiteralImpl]true));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.ButtonGroup groupSingleGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ButtonGroup();
        [CtInvocationImpl][CtVariableReadImpl]groupSingleGroup.add([CtFieldReadImpl]singleLootBtn);
        [CtInvocationImpl][CtVariableReadImpl]groupSingleGroup.add([CtFieldReadImpl]groupedLootBtn);
        [CtInvocationImpl][CtVariableReadImpl]viewControls.add([CtFieldReadImpl]groupedLootBtn);
        [CtInvocationImpl][CtVariableReadImpl]viewControls.add([CtFieldReadImpl]singleLootBtn);
        [CtInvocationImpl]changeGrouping([CtLiteralImpl]true);
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.removeButtonDecorations([CtFieldReadImpl]viewHiddenBtn);
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setIconTextGap([CtLiteralImpl]0);
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.VISIBLE_ICON);
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setRolloverIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.INVISIBLE_ICON_HOVER);
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setSelectedIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.INVISIBLE_ICON);
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setRolloverSelectedIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.VISIBLE_ICON_HOVER);
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setBackground([CtTypeAccessImpl]ColorScheme.DARKER_GRAY_COLOR);
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setUI([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.plaf.basic.BasicToggleButtonUI());[CtCommentImpl]// substance breaks the layout and the pressed icon

        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent e) -> [CtInvocationImpl]changeItemHiding([CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.isSelected()));
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.addModalTooltip([CtFieldReadImpl]viewHiddenBtn, [CtLiteralImpl]"Show ignored items", [CtLiteralImpl]"Hide ignored items");
        [CtInvocationImpl]changeItemHiding([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]viewControls.add([CtFieldReadImpl]viewHiddenBtn);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.swing.JPanel leftTitleContainer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.BorderLayout([CtLiteralImpl]5, [CtLiteralImpl]0));
        [CtInvocationImpl][CtVariableReadImpl]leftTitleContainer.setBackground([CtTypeAccessImpl]ColorScheme.DARKER_GRAY_COLOR);
        [CtInvocationImpl][CtFieldReadImpl]detailsTitle.setForeground([CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]WHITE);
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.removeButtonDecorations([CtFieldReadImpl]backBtn);
        [CtInvocationImpl][CtFieldReadImpl]backBtn.setIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.BACK_ARROW_ICON);
        [CtInvocationImpl][CtFieldReadImpl]backBtn.setRolloverIcon([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.BACK_ARROW_ICON_HOVER);
        [CtInvocationImpl][CtFieldReadImpl]backBtn.setVisible([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]backBtn.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent ev) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]currentView = [CtLiteralImpl]null;
            [CtAssignmentImpl][CtFieldWriteImpl]currentType = [CtLiteralImpl]null;
            [CtInvocationImpl][CtFieldReadImpl]backBtn.setVisible([CtLiteralImpl]false);
            [CtInvocationImpl][CtFieldReadImpl]detailsTitle.setText([CtLiteralImpl]"");
            [CtInvocationImpl]rebuild();
        });
        [CtInvocationImpl][CtVariableReadImpl]leftTitleContainer.add([CtFieldReadImpl]backBtn, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]WEST);
        [CtInvocationImpl][CtVariableReadImpl]leftTitleContainer.add([CtFieldReadImpl]detailsTitle, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]CENTER);
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.add([CtVariableReadImpl]viewControls, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]EAST);
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.add([CtVariableReadImpl]leftTitleContainer, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]WEST);
        [CtInvocationImpl][CtCommentImpl]// Create panel that will contain overall data
        [CtFieldReadImpl]overallPanel.setBorder([CtInvocationImpl][CtTypeAccessImpl]javax.swing.BorderFactory.createCompoundBorder([CtInvocationImpl][CtTypeAccessImpl]javax.swing.BorderFactory.createMatteBorder([CtLiteralImpl]5, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtTypeAccessImpl]ColorScheme.DARK_GRAY_COLOR), [CtInvocationImpl][CtTypeAccessImpl]javax.swing.BorderFactory.createEmptyBorder([CtLiteralImpl]8, [CtLiteralImpl]10, [CtLiteralImpl]8, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtFieldReadImpl]overallPanel.setBackground([CtTypeAccessImpl]ColorScheme.DARKER_GRAY_COLOR);
        [CtInvocationImpl][CtFieldReadImpl]overallPanel.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.BorderLayout());
        [CtInvocationImpl][CtFieldReadImpl]overallPanel.setVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Add icon and contents
        final [CtTypeReferenceImpl]javax.swing.JPanel overallInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtVariableReadImpl]overallInfo.setBackground([CtTypeAccessImpl]ColorScheme.DARKER_GRAY_COLOR);
        [CtInvocationImpl][CtVariableReadImpl]overallInfo.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.GridLayout([CtLiteralImpl]2, [CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]overallInfo.setBorder([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.border.EmptyBorder([CtLiteralImpl]2, [CtLiteralImpl]10, [CtLiteralImpl]2, [CtLiteralImpl]0));
        [CtInvocationImpl][CtFieldReadImpl]overallKillsLabel.setFont([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.ui.FontManager.getRunescapeSmallFont());
        [CtInvocationImpl][CtFieldReadImpl]overallGpLabel.setFont([CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.ui.FontManager.getRunescapeSmallFont());
        [CtInvocationImpl][CtVariableReadImpl]overallInfo.add([CtFieldReadImpl]overallKillsLabel);
        [CtInvocationImpl][CtVariableReadImpl]overallInfo.add([CtFieldReadImpl]overallGpLabel);
        [CtInvocationImpl][CtFieldReadImpl]overallPanel.add([CtFieldReadImpl]overallIcon, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]WEST);
        [CtInvocationImpl][CtFieldReadImpl]overallPanel.add([CtVariableReadImpl]overallInfo, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]CENTER);
        [CtLocalVariableImpl][CtCommentImpl]// Create reset all menu
        final [CtTypeReferenceImpl]javax.swing.JMenuItem reset = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtLiteralImpl]"Reset All");
        [CtInvocationImpl][CtVariableReadImpl]reset.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent e) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]net.runelite.http.api.loottracker.LootTrackerClient client = [CtInvocationImpl][CtVariableReadImpl]plugin.getLootTrackerClient();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean syncLoot = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]client != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]config.syncPanel();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int result = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtFieldReadImpl]overallPanel, [CtConditionalImpl][CtVariableReadImpl]syncLoot ? [CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.SYNC_RESET_ALL_WARNING_TEXT : [CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.NO_SYNC_RESET_ALL_WARNING_TEXT, [CtLiteralImpl]"Are you sure?", [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]WARNING_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"Yes", [CtLiteralImpl]"No" }, [CtLiteralImpl]"No");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result != [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtCommentImpl]// If not in detailed view, remove all, otherwise only remove for the currently detailed title
            [CtFieldReadImpl]sessionRecords.removeIf([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtVariableReadImpl]r.matches([CtFieldReadImpl][CtFieldReferenceImpl]currentView, [CtFieldReadImpl][CtFieldReferenceImpl]currentType));
            [CtInvocationImpl][CtFieldReadImpl]aggregateRecords.removeIf([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtVariableReadImpl]r.matches([CtFieldReadImpl][CtFieldReferenceImpl]currentView, [CtFieldReadImpl][CtFieldReferenceImpl]currentType));
            [CtInvocationImpl][CtFieldReadImpl]boxes.removeIf([CtLambdaImpl]([CtParameterImpl] b) -> [CtInvocationImpl][CtVariableReadImpl]b.matches([CtFieldReadImpl][CtFieldReferenceImpl]currentView, [CtFieldReadImpl][CtFieldReferenceImpl]currentType));
            [CtInvocationImpl]updateOverall();
            [CtInvocationImpl][CtFieldReadImpl]logsContainer.removeAll();
            [CtInvocationImpl][CtFieldReadImpl]logsContainer.repaint();
            [CtIfImpl][CtCommentImpl]// Delete all loot, or loot matching the current view
            if ([CtVariableReadImpl]syncLoot) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]client.delete([CtFieldReadImpl]currentView);
            }
        });
        [CtLocalVariableImpl][CtCommentImpl]// Create popup menu
        final [CtTypeReferenceImpl]javax.swing.JPopupMenu popupMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPopupMenu();
        [CtInvocationImpl][CtVariableReadImpl]popupMenu.setBorder([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.border.EmptyBorder([CtLiteralImpl]5, [CtLiteralImpl]5, [CtLiteralImpl]5, [CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]popupMenu.add([CtVariableReadImpl]reset);
        [CtInvocationImpl][CtFieldReadImpl]overallPanel.setComponentPopupMenu([CtVariableReadImpl]popupMenu);
        [CtInvocationImpl][CtCommentImpl]// Create loot boxes wrapper
        [CtFieldReadImpl]logsContainer.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.BoxLayout([CtFieldReadImpl]logsContainer, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]Y_AXIS));
        [CtInvocationImpl][CtVariableReadImpl]layoutPanel.add([CtFieldReadImpl]actionsContainer);
        [CtInvocationImpl][CtVariableReadImpl]layoutPanel.add([CtFieldReadImpl]overallPanel);
        [CtInvocationImpl][CtVariableReadImpl]layoutPanel.add([CtFieldReadImpl]logsContainer);
        [CtInvocationImpl][CtCommentImpl]// Add error pane
        [CtFieldReadImpl]errorPanel.setContent([CtLiteralImpl]"Loot tracker", [CtLiteralImpl]"You have not received any loot yet.");
        [CtInvocationImpl]add([CtFieldReadImpl]errorPanel);
    }

    [CtMethodImpl][CtTypeReferenceImpl]void updateCollapseText() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]collapseBtn.setSelected([CtInvocationImpl]isAllCollapsed());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isAllCollapsed() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]boxes.stream().filter([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]LootTrackerBox::isCollapsed).count() == [CtInvocationImpl][CtFieldReadImpl]boxes.size();
    }

    [CtMethodImpl][CtTypeReferenceImpl]void loadHeaderIcon([CtParameterImpl][CtTypeReferenceImpl]java.awt.image.BufferedImage img) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]overallIcon.setIcon([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtVariableReadImpl]img));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds a new entry to the plugin.
     * Creates a subtitle, adds a new entry and then passes off to the render methods, that will decide
     * how to display this new data.
     */
    [CtTypeReferenceImpl]void add([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl]final [CtTypeReferenceImpl]net.runelite.http.api.loottracker.LootRecordType type, [CtParameterImpl]final [CtTypeReferenceImpl]int actorLevel, [CtParameterImpl][CtArrayTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerItem[] items) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String subTitle;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]net.runelite.http.api.loottracker.LootRecordType.PICKPOCKET) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]subTitle = [CtLiteralImpl]"(pickpocket)";
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]subTitle = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]actorLevel > [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) ? [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"(lvl-" + [CtVariableReadImpl]actorLevel) + [CtLiteralImpl]")" : [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord record = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord([CtVariableReadImpl]eventName, [CtVariableReadImpl]subTitle, [CtVariableReadImpl]type, [CtVariableReadImpl]items, [CtLiteralImpl]1);
        [CtInvocationImpl][CtFieldReadImpl]sessionRecords.add([CtVariableReadImpl]record);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]hideIgnoredItems && [CtInvocationImpl][CtFieldReadImpl]plugin.isEventIgnored([CtVariableReadImpl]eventName)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerBox box = [CtInvocationImpl]buildBox([CtVariableReadImpl]record);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]box != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]box.rebuild();
            [CtInvocationImpl]updateOverall();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds a Collection of records to the panel
     */
    [CtTypeReferenceImpl]void addRecords([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord> recs) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]aggregateRecords.addAll([CtVariableReadImpl]recs);
        [CtInvocationImpl]rebuild();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Changes grouping mode of panel
     *
     * @param group
     * 		if loot should be grouped or not
     */
    private [CtTypeReferenceImpl]void changeGrouping([CtParameterImpl][CtTypeReferenceImpl]boolean group) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]groupLoot = [CtVariableReadImpl]group;
        [CtInvocationImpl][CtConditionalImpl]([CtVariableReadImpl]group ? [CtFieldReadImpl]groupedLootBtn : [CtFieldReadImpl]singleLootBtn).setSelected([CtLiteralImpl]true);
        [CtInvocationImpl]rebuild();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Changes item hiding mode of panel
     *
     * @param hide
     * 		if ignored items should be hidden or not
     */
    private [CtTypeReferenceImpl]void changeItemHiding([CtParameterImpl][CtTypeReferenceImpl]boolean hide) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]hideIgnoredItems = [CtVariableReadImpl]hide;
        [CtInvocationImpl][CtFieldReadImpl]viewHiddenBtn.setSelected([CtVariableReadImpl]hide);
        [CtInvocationImpl]rebuild();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Changes the collapse status of loot entries
     */
    private [CtTypeReferenceImpl]void changeCollapse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isAllCollapsed = [CtInvocationImpl]isAllCollapsed();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerBox box : [CtFieldReadImpl]boxes) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]isAllCollapsed) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]box.expand();
            } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]box.isCollapsed()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]box.collapse();
            }
        }
        [CtInvocationImpl]updateCollapseText();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * After an item changed it's ignored state, iterate all the records and make
     * sure all items of the same name also get updated
     */
    [CtTypeReferenceImpl]void updateIgnoredRecords() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord record : [CtInvocationImpl]concat([CtFieldReadImpl]aggregateRecords, [CtFieldReadImpl]sessionRecords)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerItem item : [CtInvocationImpl][CtVariableReadImpl]record.getItems()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]item.setIgnored([CtInvocationImpl][CtFieldReadImpl]plugin.isIgnored([CtInvocationImpl][CtVariableReadImpl]item.getName()));
            }
        }
        [CtInvocationImpl]rebuild();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Rebuilds all the boxes from scratch using existing listed records, depending on the grouping mode.
     */
    private [CtTypeReferenceImpl]void rebuild() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.SwingUtil.fastRemoveAll([CtFieldReadImpl]logsContainer);
        [CtInvocationImpl][CtFieldReadImpl]boxes.clear();
        [CtIfImpl]if ([CtFieldReadImpl]groupLoot) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]aggregateRecords.forEach([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::buildBox);
            [CtInvocationImpl][CtFieldReadImpl]sessionRecords.forEach([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::buildBox);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Filter here instead of when building box for more accurate limiting
            final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord> session = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]sessionRecords.stream().filter([CtLambdaImpl]([CtParameterImpl] r) -> [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl][CtFieldReferenceImpl]hideIgnoredItems) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]plugin.isEventIgnored([CtInvocationImpl][CtVariableReadImpl]r.getTitle()))).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
            [CtLocalVariableImpl][CtTypeReferenceImpl]int start = [CtLiteralImpl]0;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.size() > [CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.MAX_LOOT_BOXES) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]start = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.size() - [CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.MAX_LOOT_BOXES;
            }
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtVariableReadImpl]start; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]session.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl]buildBox([CtInvocationImpl][CtVariableReadImpl]session.get([CtVariableReadImpl]i));
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]boxes.forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]LootTrackerBox::rebuild);
        [CtInvocationImpl]updateOverall();
        [CtInvocationImpl][CtFieldReadImpl]logsContainer.revalidate();
        [CtInvocationImpl][CtFieldReadImpl]logsContainer.repaint();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method decides what to do with a new record, if a similar log exists, it will
     * add its items to it, updating the log's overall price and kills. If not, a new log will be created
     * to hold this entry's information.
     */
    private [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerBox buildBox([CtParameterImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord record) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// If this record is not part of current view, return
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]record.matches([CtFieldReadImpl]currentView, [CtFieldReadImpl]currentType)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]hideIgnoredItems && [CtInvocationImpl][CtFieldReadImpl]plugin.isEventIgnored([CtInvocationImpl][CtVariableReadImpl]record.getTitle())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl][CtCommentImpl]// Group all similar loot together
        if ([CtFieldReadImpl]groupLoot) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerBox box : [CtFieldReadImpl]boxes) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]box.matches([CtVariableReadImpl]record)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]box.addKill([CtVariableReadImpl]record);
                    [CtReturnImpl]return [CtVariableReadImpl]box;
                }
            }
        }
        [CtInvocationImpl][CtCommentImpl]// Show main view
        remove([CtFieldReadImpl]errorPanel);
        [CtInvocationImpl][CtFieldReadImpl]actionsContainer.setVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]overallPanel.setVisible([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtCommentImpl]// Create box
        final [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerBox box = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerBox([CtFieldReadImpl]itemManager, [CtInvocationImpl][CtVariableReadImpl]record.getTitle(), [CtInvocationImpl][CtVariableReadImpl]record.getType(), [CtInvocationImpl][CtVariableReadImpl]record.getSubTitle(), [CtFieldReadImpl]hideIgnoredItems, [CtInvocationImpl][CtFieldReadImpl]config.priceType(), [CtInvocationImpl][CtFieldReadImpl]config.showPriceType(), [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]plugin::toggleItem, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]plugin::toggleEvent, [CtInvocationImpl][CtFieldReadImpl]plugin.isEventIgnored([CtInvocationImpl][CtVariableReadImpl]record.getTitle()));
        [CtInvocationImpl][CtVariableReadImpl]box.addKill([CtVariableReadImpl]record);
        [CtLocalVariableImpl][CtCommentImpl]// Use the existing popup menu or create a new one
        [CtTypeReferenceImpl]javax.swing.JPopupMenu popupMenu = [CtInvocationImpl][CtVariableReadImpl]box.getComponentPopupMenu();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]popupMenu == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]popupMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPopupMenu();
            [CtInvocationImpl][CtVariableReadImpl]popupMenu.setBorder([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.border.EmptyBorder([CtLiteralImpl]5, [CtLiteralImpl]5, [CtLiteralImpl]5, [CtLiteralImpl]5));
            [CtInvocationImpl][CtVariableReadImpl]box.setComponentPopupMenu([CtVariableReadImpl]popupMenu);
        }
        [CtInvocationImpl][CtCommentImpl]// Create collapse event
        [CtVariableReadImpl]box.addMouseListener([CtNewClassImpl]new [CtTypeReferenceImpl]java.awt.event.MouseAdapter()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void mouseClicked([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent e) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getButton() == [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.MouseEvent.[CtFieldReferenceImpl]BUTTON1) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]box.isCollapsed()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]box.expand();
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]box.collapse();
                    }
                    [CtInvocationImpl]updateCollapseText();
                }
            }
        });
        [CtLocalVariableImpl][CtCommentImpl]// Create reset menu
        final [CtTypeReferenceImpl]javax.swing.JMenuItem reset = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtLiteralImpl]"Reset");
        [CtInvocationImpl][CtVariableReadImpl]reset.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent e) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord> match = [CtConditionalImpl]([CtFieldReadImpl]groupLoot) ? [CtLambdaImpl][CtCommentImpl]// With grouped loot, remove any record with this title
            ([CtParameterImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord r) -> [CtInvocationImpl][CtVariableReadImpl]r.matches([CtInvocationImpl][CtVariableReadImpl]record.getTitle(), [CtInvocationImpl][CtVariableReadImpl]record.getType()) : [CtLambdaImpl][CtCommentImpl]// Otherwise remove specifically this entry
            ([CtParameterImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord r) -> [CtInvocationImpl][CtVariableReadImpl]r.equals([CtVariableReadImpl]record);
            [CtInvocationImpl][CtFieldReadImpl]sessionRecords.removeIf([CtVariableReadImpl]match);
            [CtInvocationImpl][CtFieldReadImpl]aggregateRecords.removeIf([CtVariableReadImpl]match);
            [CtInvocationImpl][CtFieldReadImpl]boxes.remove([CtVariableReadImpl]box);
            [CtInvocationImpl]updateOverall();
            [CtInvocationImpl][CtFieldReadImpl]logsContainer.remove([CtVariableReadImpl]box);
            [CtInvocationImpl][CtFieldReadImpl]logsContainer.repaint();
            [CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.http.api.loottracker.LootTrackerClient client = [CtInvocationImpl][CtFieldReadImpl]plugin.getLootTrackerClient();
            [CtIfImpl][CtCommentImpl]// Without loot being grouped we have no way to identify single kills to be deleted
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]client != [CtLiteralImpl]null) && [CtFieldReadImpl]groupLoot) && [CtInvocationImpl][CtFieldReadImpl]config.syncPanel()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]client.delete([CtInvocationImpl][CtVariableReadImpl]box.getId());
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]popupMenu.add([CtVariableReadImpl]reset);
        [CtLocalVariableImpl][CtCommentImpl]// Create details menu
        final [CtTypeReferenceImpl]javax.swing.JMenuItem details = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtLiteralImpl]"View details");
        [CtInvocationImpl][CtVariableReadImpl]details.addActionListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ActionEvent e) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]currentView = [CtInvocationImpl][CtVariableReadImpl]record.getTitle();
            [CtAssignmentImpl][CtFieldWriteImpl]currentType = [CtInvocationImpl][CtVariableReadImpl]record.getType();
            [CtInvocationImpl][CtFieldReadImpl]detailsTitle.setText([CtFieldReadImpl]currentView);
            [CtInvocationImpl][CtFieldReadImpl]backBtn.setVisible([CtLiteralImpl]true);
            [CtInvocationImpl]rebuild();
        });
        [CtInvocationImpl][CtVariableReadImpl]popupMenu.add([CtVariableReadImpl]details);
        [CtInvocationImpl][CtCommentImpl]// Add box to panel
        [CtFieldReadImpl]boxes.add([CtVariableReadImpl]box);
        [CtInvocationImpl][CtFieldReadImpl]logsContainer.add([CtVariableReadImpl]box, [CtLiteralImpl]0);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]groupLoot) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]boxes.size() > [CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.MAX_LOOT_BOXES)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logsContainer.remove([CtInvocationImpl][CtFieldReadImpl]boxes.remove([CtLiteralImpl]0));
        }
        [CtReturnImpl]return [CtVariableReadImpl]box;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateOverall() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long overallKills = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long overallGe = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long overallHa = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerRecord record : [CtInvocationImpl]concat([CtFieldReadImpl]aggregateRecords, [CtFieldReadImpl]sessionRecords)) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]record.matches([CtFieldReadImpl]currentView, [CtFieldReadImpl]currentType)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]hideIgnoredItems && [CtInvocationImpl][CtFieldReadImpl]plugin.isEventIgnored([CtInvocationImpl][CtVariableReadImpl]record.getTitle())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int present = [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]record.getItems().length;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.runelite.client.plugins.loottracker.LootTrackerItem item : [CtInvocationImpl][CtVariableReadImpl]record.getItems()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]hideIgnoredItems && [CtInvocationImpl][CtVariableReadImpl]item.isIgnored()) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]present--;
                    [CtContinueImpl]continue;
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]overallGe += [CtInvocationImpl][CtVariableReadImpl]item.getTotalGePrice();
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]overallHa += [CtInvocationImpl][CtVariableReadImpl]item.getTotalHaPrice();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]present > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]overallKills += [CtInvocationImpl][CtVariableReadImpl]record.getKills();
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String priceType = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]config.showPriceType()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]priceType = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]config.priceType() == [CtFieldReadImpl]LootTrackerPriceType.HIGH_ALCHEMY) ? [CtLiteralImpl]"HA " : [CtLiteralImpl]"GE ";
        }
        [CtInvocationImpl][CtFieldReadImpl]overallKillsLabel.setText([CtInvocationImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.htmlLabel([CtLiteralImpl]"Total count: ", [CtVariableReadImpl]overallKills));
        [CtInvocationImpl][CtFieldReadImpl]overallGpLabel.setText([CtInvocationImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.htmlLabel([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Total " + [CtVariableReadImpl]priceType) + [CtLiteralImpl]"value: ", [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]config.priceType() == [CtFieldReadImpl]LootTrackerPriceType.HIGH_ALCHEMY ? [CtVariableReadImpl]overallHa : [CtVariableReadImpl]overallGe));
        [CtInvocationImpl][CtFieldReadImpl]overallGpLabel.setToolTipText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<html>Total GE price: " + [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.QuantityFormatter.formatNumber([CtVariableReadImpl]overallGe)) + [CtLiteralImpl]"<br>Total HA price: ") + [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.QuantityFormatter.formatNumber([CtVariableReadImpl]overallHa)) + [CtLiteralImpl]"</html>");
        [CtInvocationImpl]updateCollapseText();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String htmlLabel([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]long value) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String valueStr = [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.QuantityFormatter.quantityToStackSize([CtVariableReadImpl]value);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]net.runelite.client.plugins.loottracker.LootTrackerPanel.HTML_LABEL_TEMPLATE, [CtInvocationImpl][CtTypeAccessImpl]net.runelite.client.util.ColorUtil.toHexColor([CtTypeAccessImpl]ColorScheme.LIGHT_GRAY_COLOR), [CtVariableReadImpl]key, [CtVariableReadImpl]valueStr);
    }
}
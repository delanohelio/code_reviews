[CompilationUnitImpl][CtCommentImpl]/* MegaMek - Copyright (C) 2003, 2004, 2005 Ben Mazur (bmazur@sev.org)

 This program is free software; you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation; either version 2 of the License, or (at your option)
 any later version.

 This program is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 for more details.
 */
[CtPackageDeclarationImpl]package megamek.client.ui.swing;
[CtImportImpl]import javax.swing.JPanel;
[CtImportImpl]import java.awt.GridBagConstraints;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.awt.event.ActionEvent;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import javax.swing.UIManager;
[CtImportImpl]import java.awt.event.FocusEvent;
[CtUnresolvedImport]import megamek.client.ui.Messages;
[CtImportImpl]import javax.swing.JFrame;
[CtImportImpl]import java.awt.BorderLayout;
[CtUnresolvedImport]import megamek.common.preference.PreferenceManager;
[CtImportImpl]import java.util.List;
[CtImportImpl]import javax.swing.JSlider;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import javax.swing.JScrollPane;
[CtImportImpl]import javax.swing.border.EmptyBorder;
[CtImportImpl]import javax.swing.JOptionPane;
[CtImportImpl]import javax.swing.UIManager.LookAndFeelInfo;
[CtImportImpl]import javax.swing.ToolTipManager;
[CtUnresolvedImport]import megamek.common.preference.IClientPreferences;
[CtUnresolvedImport]import megamek.client.ui.swing.util.KeyCommandBind;
[CtImportImpl]import javax.swing.Box;
[CtImportImpl]import java.io.FilenameFilter;
[CtImportImpl]import javax.swing.ListSelectionModel;
[CtImportImpl]import javax.swing.event.ListSelectionListener;
[CtUnresolvedImport]import megamek.client.ui.swing.widget.SkinXMLHandler;
[CtUnresolvedImport]import megamek.common.Configuration;
[CtImportImpl]import java.awt.event.MouseMotionAdapter;
[CtImportImpl]import javax.swing.JCheckBox;
[CtImportImpl]import java.awt.event.ActionListener;
[CtUnresolvedImport]import megamek.common.KeyBindParser;
[CtImportImpl]import javax.swing.SwingUtilities;
[CtImportImpl]import java.awt.event.FocusListener;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import megamek.common.Entity;
[CtImportImpl]import javax.swing.JList;
[CtImportImpl]import javax.swing.event.MouseInputAdapter;
[CtImportImpl]import java.awt.FlowLayout;
[CtImportImpl]import java.awt.Insets;
[CtImportImpl]import javax.swing.BoxLayout;
[CtImportImpl]import java.awt.GridBagLayout;
[CtImportImpl]import javax.swing.JTabbedPane;
[CtImportImpl]import javax.swing.DefaultListModel;
[CtImportImpl]import javax.swing.JButton;
[CtImportImpl]import javax.swing.JSeparator;
[CtImportImpl]import javax.swing.event.ListSelectionEvent;
[CtImportImpl]import javax.swing.JTextField;
[CtImportImpl]import java.awt.event.ItemEvent;
[CtImportImpl]import java.awt.Font;
[CtUnresolvedImport]import megamek.common.IGame;
[CtImportImpl]import java.awt.GridLayout;
[CtImportImpl]import java.awt.event.KeyEvent;
[CtImportImpl]import javax.swing.SwingConstants;
[CtImportImpl]import javax.swing.event.ChangeListener;
[CtImportImpl]import java.awt.Component;
[CtImportImpl]import java.awt.event.MouseEvent;
[CtImportImpl]import javax.swing.JLabel;
[CtImportImpl]import java.awt.Dimension;
[CtImportImpl]import javax.swing.JComboBox;
[CtUnresolvedImport]import megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand;
[CtImportImpl]import java.awt.event.ItemListener;
[CtImportImpl]import javax.swing.event.ChangeEvent;
[CtImportImpl]import java.awt.event.WindowAdapter;
[CtImportImpl]import java.awt.event.WindowEvent;
[CtImportImpl]import java.awt.event.KeyListener;
[CtClassImpl]public class CommonSettingsDialog extends [CtTypeReferenceImpl]megamek.client.ui.swing.ClientDialog implements [CtTypeReferenceImpl]java.awt.event.ActionListener , [CtTypeReferenceImpl]java.awt.event.ItemListener , [CtTypeReferenceImpl]java.awt.event.FocusListener , [CtTypeReferenceImpl]javax.swing.event.ListSelectionListener , [CtTypeReferenceImpl]javax.swing.event.ChangeListener {
    [CtClassImpl][CtJavaDocImpl]/**
     * A class for storing information about an GUIPreferences advanced option.
     *
     * @author arlith
     */
    private class AdvancedOptionData implements [CtTypeReferenceImpl]java.lang.Comparable<[CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.AdvancedOptionData> {
        [CtFieldImpl]public [CtTypeReferenceImpl]java.lang.String option;

        [CtConstructorImpl]public AdvancedOptionData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String option) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.option = [CtVariableReadImpl]option;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns true if this option has tooltip text.
         *
         * @return  */
        public [CtTypeReferenceImpl]boolean hasTooltipText() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.keyExists([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"AdvancedOptions." + [CtFieldReadImpl]option) + [CtLiteralImpl]".tooltip");
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns the tooltip text for this option.
         *
         * @return  */
        public [CtTypeReferenceImpl]java.lang.String getTooltipText() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"AdvancedOptions." + [CtFieldReadImpl]option) + [CtLiteralImpl]".tooltip");
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns a human-readable name for this advanced option.
         */
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.keyExists([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"AdvancedOptions." + [CtFieldReadImpl]option) + [CtLiteralImpl]".name")) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"AdvancedOptions." + [CtFieldReadImpl]option) + [CtLiteralImpl]".name");
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl]option;
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int compareTo([CtParameterImpl][CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.AdvancedOptionData other) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.toString().compareTo([CtInvocationImpl][CtVariableReadImpl]other.toString());
        }
    }

    [CtClassImpl]private class PhaseCommandListMouseAdapter extends [CtTypeReferenceImpl]javax.swing.event.MouseInputAdapter {
        [CtFieldImpl]private [CtTypeReferenceImpl]boolean mouseDragging = [CtLiteralImpl]false;

        [CtFieldImpl]private [CtTypeReferenceImpl]int dragSourceIndex;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void mousePressed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]javax.swing.SwingUtilities.isLeftMouseButton([CtVariableReadImpl]e)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object src = [CtInvocationImpl][CtVariableReadImpl]e.getSource();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]src instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.swing.JList) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]dragSourceIndex = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]javax.swing.JList<[CtWildcardReferenceImpl]?>) (src)).getSelectedIndex();
                    [CtAssignmentImpl][CtFieldWriteImpl]mouseDragging = [CtLiteralImpl]true;
                }
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void mouseReleased([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mouseDragging = [CtLiteralImpl]false;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void mouseDragged([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object src = [CtInvocationImpl][CtVariableReadImpl]e.getSource();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mouseDragging && [CtBinaryOperatorImpl]([CtVariableReadImpl]src instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.swing.JList)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JList<[CtWildcardReferenceImpl]?> srcList = [CtVariableReadImpl](([CtTypeReferenceImpl]javax.swing.JList<[CtWildcardReferenceImpl]?>) (src));
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtWildcardReferenceImpl]?> srcModel = [CtInvocationImpl](([CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtWildcardReferenceImpl]?>) ([CtVariableReadImpl]srcList.getModel()));
                [CtLocalVariableImpl][CtTypeReferenceImpl]int currentIndex = [CtInvocationImpl][CtVariableReadImpl]srcList.locationToIndex([CtInvocationImpl][CtVariableReadImpl]e.getPoint());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]currentIndex != [CtFieldReadImpl]dragSourceIndex) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int dragTargetIndex = [CtInvocationImpl][CtVariableReadImpl]srcList.getSelectedIndex();
                    [CtInvocationImpl]moveElement([CtVariableReadImpl]srcModel, [CtFieldReadImpl]dragSourceIndex, [CtVariableReadImpl]dragTargetIndex);
                    [CtAssignmentImpl][CtFieldWriteImpl]dragSourceIndex = [CtVariableReadImpl]currentIndex;
                }
            }
        }

        [CtMethodImpl]private <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]void moveElement([CtParameterImpl][CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeParameterReferenceImpl]T> srcModel, [CtParameterImpl][CtTypeReferenceImpl]int srcIndex, [CtParameterImpl][CtTypeReferenceImpl]int trgIndex) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]T dragElement = [CtInvocationImpl][CtVariableReadImpl]srcModel.get([CtVariableReadImpl]srcIndex);
            [CtInvocationImpl][CtVariableReadImpl]srcModel.remove([CtVariableReadImpl]srcIndex);
            [CtInvocationImpl][CtVariableReadImpl]srcModel.add([CtVariableReadImpl]trgIndex, [CtVariableReadImpl]dragElement);
        }
    }

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1535370193846895473L;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox autoEndFiring;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox autoDeclareSearchlight;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox nagForMASC;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox nagForPSR;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox nagForWiGELanding;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox nagForNoAction;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox animateMove;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showWrecks;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox soundMute;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showWpsinTT;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showArmorMiniVisTT;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showPilotPortraitTT;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox chkAntiAliasing;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String> defaultWeaponSortOrder;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField tooltipDelay;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField tooltipDismissDelay;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField tooltipDistSupression;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String> unitStartChar;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField maxPathfinderTime;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox getFocus;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox keepGameLog;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField gameLogFilename;

    [CtFieldImpl][CtCommentImpl]// private JTextField gameLogMaxSize;
    private [CtTypeReferenceImpl]javax.swing.JCheckBox stampFilenames;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField stampFormat;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox defaultAutoejectDisabled;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox useAverageSkills;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox generateNames;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showUnitId;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String> displayLocale;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showDamageLevel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showDamageDecal;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox showMapsheets;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox aOHexShadows;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox floatingIso;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox mmSymbol;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox entityOwnerColor;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox teamColoring;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox useSoftCenter;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox levelhighlight;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox shadowMap;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox hexInclines;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox mouseWheelZoom;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox mouseWheelZoomFlip;

    [CtFieldImpl][CtCommentImpl]// Tactical Overlay Options
    private [CtTypeReferenceImpl]javax.swing.JCheckBox fovInsideEnabled;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JSlider fovHighlightAlpha;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox fovOutsideEnabled;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JSlider fovDarkenAlpha;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JSlider numStripesSlider;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBox fovGrayscaleEnabled;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField fovHighlightRingsRadii;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField fovHighlightRingsColors;

    [CtFieldImpl][CtCommentImpl]// Labels (there to make it possible to disable them)
    private [CtTypeReferenceImpl]javax.swing.JLabel darkenAlphaLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JLabel numStripesLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JLabel fovHighlightRingsColorsLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JLabel fovHighlightRingsRadiiLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JLabel highlightAlphaLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JLabel stampFormatLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JLabel gameLogFilenameLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String> skinFiles;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]megamek.client.ui.swing.UITheme> uiThemes;

    [CtFieldImpl][CtCommentImpl]// Avanced Settings
    private [CtTypeReferenceImpl]javax.swing.JList<[CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.AdvancedOptionData> advancedKeys;

    [CtFieldImpl]private [CtTypeReferenceImpl]int advancedKeyIndex = [CtLiteralImpl]0;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JTextField advancedValue;

    [CtFieldImpl][CtCommentImpl]// Button order
    private [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand> movePhaseCommands;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand> deployPhaseCommands;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand> firingPhaseCommands;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand> physicalPhaseCommands;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand> targetingPhaseCommands;

    [CtFieldImpl]private [CtTypeReferenceImpl]StatusBarPhaseDisplay.CommandComparator cmdComp = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.CommandComparator();

    [CtFieldImpl]private [CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.PhaseCommandListMouseAdapter cmdMouseAdaptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.PhaseCommandListMouseAdapter();

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String> tileSetChoice;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.io.File> tileSets;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A Map that maps command strings to a JTextField for updating the modifier
     * for the command.
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]javax.swing.JTextField> cmdModifierMap;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A Map that maps command strings to a Integer for updating the key
     * for the command.
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> cmdKeyMap;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A Map that maps command strings to a JCheckBox for updating the
     * isRepeatable flag.
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]javax.swing.JCheckBox> cmdRepeatableMap;

    [CtFieldImpl]private [CtTypeReferenceImpl]megamek.client.ui.swing.ClientGUI clientgui = [CtLiteralImpl]null;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CANCEL = [CtLiteralImpl]"CANCEL";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String UPDATE = [CtLiteralImpl]"UPDATE";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]java.lang.String[] LOCALE_CHOICES = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"en", [CtLiteralImpl]"de", [CtLiteralImpl]"ru" };[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.awt.Dimension LABEL_SPACER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]5, [CtLiteralImpl]0);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.awt.Dimension DEPENDENT_INSET = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]25, [CtLiteralImpl]0);

    [CtFieldImpl][CtCommentImpl]// Save some values to restore them when the dialog is canceled
    private [CtTypeReferenceImpl]boolean savedFovHighlight;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedFovDarken;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedFovGrayscale;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedAOHexShadows;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedShadowMap;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedHexInclines;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedLevelhighlight;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedFloatingIso;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedMmSymbol;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedTeamColoring;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedUnitLabelBorder;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedShowDamageDecal;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean savedShowDamageLabel;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String savedFovHighlightRingsRadii;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String savedFovHighlightRingsColors;

    [CtFieldImpl]private [CtTypeReferenceImpl]int savedFovHighlightAlpha;

    [CtFieldImpl]private [CtTypeReferenceImpl]int savedFovDarkenAlpha;

    [CtFieldImpl]private [CtTypeReferenceImpl]int savedNumStripesSlider;

    [CtFieldImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> savedAdvancedOpt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Standard constructor. There is no default constructor for this class.
     *
     * @param owner
     * 		- the <code>Frame</code> that owns this dialog.
     */
    public CommonSettingsDialog([CtParameterImpl][CtTypeReferenceImpl]javax.swing.JFrame owner, [CtParameterImpl][CtTypeReferenceImpl]megamek.client.ui.swing.ClientGUI cg) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]owner);
        [CtAssignmentImpl][CtFieldWriteImpl]clientgui = [CtVariableReadImpl]cg;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Standard constructor. There is no default constructor for this class.
     *
     * @param owner
     * 		- the <code>Frame</code> that owns this dialog.
     */
    public CommonSettingsDialog([CtParameterImpl][CtTypeReferenceImpl]javax.swing.JFrame owner) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]owner, [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.title"), [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTabbedPane panTabs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTabbedPane();
        [CtInvocationImpl]setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.BorderLayout());
        [CtInvocationImpl][CtInvocationImpl]getContentPane().add([CtVariableReadImpl]panTabs, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]CENTER);
        [CtInvocationImpl][CtInvocationImpl]getContentPane().add([CtInvocationImpl]getButtonsPanel(), [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]PAGE_END);
        [CtInvocationImpl][CtCommentImpl]// Close this dialog when the window manager says to.
        addWindowListener([CtNewClassImpl]new [CtTypeReferenceImpl]java.awt.event.WindowAdapter()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void windowClosing([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.WindowEvent e) [CtBlockImpl]{
                [CtInvocationImpl]cancel();
            }
        });
        [CtLocalVariableImpl][CtCommentImpl]// Add the tabs
        [CtTypeReferenceImpl]javax.swing.JPanel settingsPanel = [CtInvocationImpl]getSettingsPanel();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollPane settingsPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtInvocationImpl]getSettingsPanel());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]settingsPane.getVerticalScrollBar().setUnitIncrement([CtLiteralImpl]16);
        [CtInvocationImpl][CtVariableReadImpl]panTabs.add([CtLiteralImpl]"Main", [CtVariableReadImpl]settingsPane);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollPane graphicsPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtInvocationImpl]getGraphicsPanel());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graphicsPane.getVerticalScrollBar().setUnitIncrement([CtLiteralImpl]16);
        [CtInvocationImpl][CtVariableReadImpl]panTabs.add([CtLiteralImpl]"Graphics", [CtVariableReadImpl]graphicsPane);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollPane keyBindPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtInvocationImpl]getKeyBindPanel());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]keyBindPane.getVerticalScrollBar().setUnitIncrement([CtLiteralImpl]16);
        [CtInvocationImpl][CtVariableReadImpl]panTabs.add([CtLiteralImpl]"Key Binds", [CtVariableReadImpl]keyBindPane);
        [CtInvocationImpl][CtVariableReadImpl]panTabs.add([CtLiteralImpl]"Button Order", [CtInvocationImpl]getButtonOrderPanel());
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollPane advancedSettingsPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtInvocationImpl]getAdvancedSettingsPanel());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]advancedSettingsPane.getVerticalScrollBar().setUnitIncrement([CtLiteralImpl]16);
        [CtInvocationImpl][CtVariableReadImpl]panTabs.add([CtLiteralImpl]"Advanced", [CtVariableReadImpl]advancedSettingsPane);
        [CtInvocationImpl]pack();
        [CtInvocationImpl]setLocationAndSize([CtFieldReadImpl][CtInvocationImpl]getPreferredSize().width, [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]settingsPanel.getPreferredSize().height);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JPanel getButtonsPanel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Add the dialog controls.
        [CtTypeReferenceImpl]javax.swing.JPanel buttons = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtVariableReadImpl]buttons.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.GridLayout([CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]20, [CtLiteralImpl]5));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JButton update = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JButton([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.Update"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtVariableReadImpl]update.setActionCommand([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.UPDATE);
        [CtInvocationImpl][CtVariableReadImpl]update.addActionListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]buttons.add([CtVariableReadImpl]update);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JButton cancel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JButton([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"Cancel"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtVariableReadImpl]cancel.setActionCommand([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.CANCEL);
        [CtInvocationImpl][CtVariableReadImpl]cancel.addActionListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]buttons.add([CtVariableReadImpl]cancel);
        [CtReturnImpl]return [CtVariableReadImpl]buttons;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JPanel getSettingsPanel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component>> comps = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component>>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component> row;
        [CtLocalVariableImpl][CtCommentImpl]// displayLocale settings
        [CtTypeReferenceImpl]javax.swing.JLabel displayLocaleLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.locale"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtFieldWriteImpl]displayLocale = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtFieldReadImpl]displayLocale.addItem([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.locale.English"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]displayLocale.addItem([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.locale.Deutsch"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]displayLocale.addItem([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.locale.Russian"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]displayLocale.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]150, [CtLiteralImpl]40));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]displayLocaleLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]displayLocale);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JSeparator Sep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]Sep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// --------------
        [CtFieldWriteImpl]showDamageLevel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showDamageLevel"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]showDamageLevel.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showDamageLevel);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]showDamageDecal = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showDamageDecal"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]showDamageDecal.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showDamageDecal);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]showUnitId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showUnitId"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]showUnitId.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showUnitId);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]entityOwnerColor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.entityOwnerColor"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]entityOwnerColor.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.entityOwnerColorTip"));
        [CtInvocationImpl][CtFieldReadImpl]entityOwnerColor.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]entityOwnerColor);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]teamColoring = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.teamColoring"));
        [CtInvocationImpl][CtFieldReadImpl]teamColoring.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.teamColoringTip"));
        [CtInvocationImpl][CtFieldReadImpl]teamColoring.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]teamColoring);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]useSoftCenter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.useSoftCenter"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]useSoftCenter.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.useSoftCenterTip"));
        [CtInvocationImpl][CtFieldReadImpl]useSoftCenter.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]useSoftCenter);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]Sep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]Sep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// --------------
        [CtCommentImpl]// Tooltip Stuff
        [CtCommentImpl]// 
        [CtCommentImpl]// Popup Delay and Dismiss Delay
        [CtFieldWriteImpl]tooltipDelay = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]4);
        [CtInvocationImpl][CtFieldReadImpl]tooltipDelay.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]150, [CtLiteralImpl]40));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel tooltipDelayLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tooltipDelay"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]tooltipDelayLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]tooltipDelay);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]tooltipDismissDelay = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]4);
        [CtInvocationImpl][CtFieldReadImpl]tooltipDismissDelay.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]150, [CtLiteralImpl]40));
        [CtInvocationImpl][CtFieldReadImpl]tooltipDismissDelay.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tooltipDismissDelayTooltip"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel tooltipDismissDelayLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tooltipDismissDelay"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtVariableReadImpl]tooltipDismissDelayLabel.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tooltipDismissDelayTooltip"));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]tooltipDismissDelayLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]tooltipDismissDelay);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]tooltipDistSupression = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]4);
        [CtInvocationImpl][CtFieldReadImpl]tooltipDistSupression.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]150, [CtLiteralImpl]40));
        [CtInvocationImpl][CtFieldReadImpl]tooltipDistSupression.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tooltipDistSuppressionTooltip"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel tooltipDistSupressionLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tooltipDistSuppression"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtVariableReadImpl]tooltipDistSupressionLabel.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tooltipDistSuppressionTooltip"));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]tooltipDistSupressionLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]tooltipDistSupression);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]showWpsinTT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showWpsinTT"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showWpsinTT);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// copied from showWpsinTT, kept comment as it looks like a relevant compiler/editor flag?
        [CtFieldWriteImpl]showArmorMiniVisTT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showArmorMiniVisTT"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showArmorMiniVisTT);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]showPilotPortraitTT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showPilotPortraitTT"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showPilotPortraitTT);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]Sep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]Sep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// --------------
        [CtFieldWriteImpl]soundMute = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.soundMute"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]soundMute);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel maxPathfinderTimeLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.pathFiderTimeLimit"));
        [CtAssignmentImpl][CtFieldWriteImpl]maxPathfinderTime = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]5);
        [CtInvocationImpl][CtFieldReadImpl]maxPathfinderTime.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]150, [CtLiteralImpl]40));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]maxPathfinderTimeLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]maxPathfinderTime);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]Sep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]Sep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// --------------
        [CtFieldWriteImpl]nagForMASC = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.nagForMASC"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]nagForMASC);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]nagForPSR = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.nagForPSR"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]nagForPSR);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]nagForWiGELanding = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.nagForWiGELanding"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]nagForWiGELanding);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]nagForNoAction = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.nagForNoAction"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]nagForNoAction);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]getFocus = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.getFocus"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]getFocus);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]mouseWheelZoom = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.mouseWheelZoom"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]mouseWheelZoom);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]mouseWheelZoomFlip = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.mouseWheelZoomFlip"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]mouseWheelZoomFlip);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]autoEndFiring = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.autoEndFiring"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]autoEndFiring);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]autoDeclareSearchlight = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.autoDeclareSearchlight"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]autoDeclareSearchlight);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel defaultSortOrderLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.defaultWeaponSortOrder"));[CtCommentImpl]// $NON-NLS-1$

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String toolTip = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.defaultWeaponSortOrderTooltip");
        [CtInvocationImpl][CtVariableReadImpl]defaultSortOrderLabel.setToolTipText([CtVariableReadImpl]toolTip);
        [CtAssignmentImpl][CtFieldWriteImpl]defaultWeaponSortOrder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JComboBox<>();
        [CtInvocationImpl][CtFieldReadImpl]defaultWeaponSortOrder.setToolTipText([CtVariableReadImpl]toolTip);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.common.Entity.WeaponSortOrder s : [CtInvocationImpl][CtTypeAccessImpl]Entity.WeaponSortOrder.values()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Skip custom: it doesn't make sense as a default.
            if ([CtInvocationImpl][CtVariableReadImpl]s.equals([CtTypeAccessImpl]Entity.WeaponSortOrder.CUSTOM)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String entry = [CtBinaryOperatorImpl][CtLiteralImpl]"MechDisplay.WeaponSortOrder." + [CtFieldReadImpl][CtVariableReadImpl]s.i18nEntry;
            [CtInvocationImpl][CtFieldReadImpl]defaultWeaponSortOrder.addItem([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtVariableReadImpl]entry));
        }
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]defaultSortOrderLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]defaultWeaponSortOrder);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]Sep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]Sep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtLocalVariableImpl][CtCommentImpl]// --------------
        [CtTypeReferenceImpl]javax.swing.JLabel unitStartCharLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.protoMechUnitCodes"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtFieldWriteImpl]unitStartChar = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtCommentImpl]// Add option for "A, B, C, D..."
        [CtFieldReadImpl]unitStartChar.addItem([CtLiteralImpl]"A, B, C, D...");[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtCommentImpl]// Add option for "ALPHA, BETA, GAMMA, DELTA..."
        [CtFieldReadImpl]unitStartChar.addItem([CtLiteralImpl]"Α, Β, Γ, Δ...");[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtCommentImpl]// Add option for "alpha, beta, gamma, delta..."
        [CtFieldReadImpl]unitStartChar.addItem([CtLiteralImpl]"α, β, γ, δ...");[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]unitStartChar.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]150, [CtLiteralImpl]40));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]unitStartCharLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]unitStartChar);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// player-specific settings
        [CtFieldWriteImpl]defaultAutoejectDisabled = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.defaultAutoejectDisabled"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]defaultAutoejectDisabled.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]defaultAutoejectDisabled);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]useAverageSkills = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.useAverageSkills"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]useAverageSkills.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]useAverageSkills);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]generateNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.generateNames"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]generateNames.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]generateNames);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]Sep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]Sep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// --------------
        [CtCommentImpl]// client-side gameLog settings
        [CtFieldWriteImpl]keepGameLog = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.keepGameLog"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]keepGameLog.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]keepGameLog);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]gameLogFilenameLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.logFileName"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtFieldWriteImpl]gameLogFilename = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]15);
        [CtInvocationImpl][CtFieldReadImpl]gameLogFilename.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]250, [CtLiteralImpl]40));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]gameLogFilenameLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]gameLogFilename);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]stampFilenames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.stampFilenames"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]stampFilenames.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]stampFilenames);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]stampFormatLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.stampFormat"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtFieldWriteImpl]stampFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]15);
        [CtInvocationImpl][CtFieldReadImpl]stampFormat.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtBinaryOperatorImpl][CtLiteralImpl]15 * [CtLiteralImpl]13, [CtLiteralImpl]40));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]stampFormatLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]stampFormat);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtReturnImpl]return [CtInvocationImpl]createSettingsPanel([CtVariableReadImpl]comps);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Display the current settings in this dialog. <p/> Overrides
     * <code>Dialog#setVisible(boolean)</code>.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setVisible([CtParameterImpl][CtTypeReferenceImpl]boolean visible) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Initialize the dialog when it's being shown
        if ([CtVariableReadImpl]visible) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.GUIPreferences gs = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance();
            [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.preference.IClientPreferences cs = [CtInvocationImpl][CtTypeAccessImpl]megamek.common.preference.PreferenceManager.getClientPreferences();
            [CtInvocationImpl][CtFieldReadImpl]autoEndFiring.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getAutoEndFiring());
            [CtInvocationImpl][CtFieldReadImpl]autoDeclareSearchlight.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getAutoDeclareSearchlight());
            [CtInvocationImpl][CtFieldReadImpl]nagForMASC.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getNagForMASC());
            [CtInvocationImpl][CtFieldReadImpl]nagForPSR.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getNagForPSR());
            [CtInvocationImpl][CtFieldReadImpl]nagForWiGELanding.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getNagForWiGELanding());
            [CtInvocationImpl][CtFieldReadImpl]nagForNoAction.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getNagForNoAction());
            [CtInvocationImpl][CtFieldReadImpl]animateMove.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getShowMoveStep());
            [CtInvocationImpl][CtFieldReadImpl]showWrecks.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getShowWrecks());
            [CtInvocationImpl][CtFieldReadImpl]soundMute.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getSoundMute());
            [CtInvocationImpl][CtFieldReadImpl]tooltipDelay.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtInvocationImpl][CtVariableReadImpl]gs.getTooltipDelay()));
            [CtInvocationImpl][CtFieldReadImpl]tooltipDismissDelay.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtInvocationImpl][CtVariableReadImpl]gs.getTooltipDismissDelay()));
            [CtInvocationImpl][CtFieldReadImpl]tooltipDistSupression.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtInvocationImpl][CtVariableReadImpl]gs.getTooltipDistSuppression()));
            [CtInvocationImpl][CtFieldReadImpl]showWpsinTT.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getShowWpsinTT());
            [CtInvocationImpl][CtFieldReadImpl]showArmorMiniVisTT.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getshowArmorMiniVisTT());
            [CtInvocationImpl][CtFieldReadImpl]showPilotPortraitTT.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getshowPilotPortraitTT());
            [CtInvocationImpl][CtFieldReadImpl]defaultWeaponSortOrder.setSelectedIndex([CtInvocationImpl][CtVariableReadImpl]gs.getDefaultWeaponSortOrder());
            [CtInvocationImpl][CtFieldReadImpl]mouseWheelZoom.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getMouseWheelZoom());
            [CtInvocationImpl][CtFieldReadImpl]mouseWheelZoomFlip.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getMouseWheelZoomFlip());
            [CtInvocationImpl][CtCommentImpl]// Select the correct char set (give a nice default to start).
            [CtFieldReadImpl]unitStartChar.setSelectedIndex([CtLiteralImpl]0);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int loop = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]loop < [CtInvocationImpl][CtFieldReadImpl]unitStartChar.getItemCount(); [CtUnaryOperatorImpl][CtVariableWriteImpl]loop++) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]unitStartChar.getItemAt([CtVariableReadImpl]loop).charAt([CtLiteralImpl]0) == [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.common.preference.PreferenceManager.getClientPreferences().getUnitStartChar()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]unitStartChar.setSelectedIndex([CtVariableReadImpl]loop);
                    [CtBreakImpl]break;
                }
            }
            [CtInvocationImpl][CtFieldReadImpl]maxPathfinderTime.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtInvocationImpl][CtVariableReadImpl]cs.getMaxPathfinderTime()));
            [CtInvocationImpl][CtFieldReadImpl]keepGameLog.setSelected([CtInvocationImpl][CtVariableReadImpl]cs.keepGameLog());
            [CtInvocationImpl][CtFieldReadImpl]gameLogFilename.setEnabled([CtInvocationImpl][CtFieldReadImpl]keepGameLog.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]gameLogFilename.setText([CtInvocationImpl][CtVariableReadImpl]cs.getGameLogFilename());
            [CtInvocationImpl][CtCommentImpl]// gameLogMaxSize.setEnabled(keepGameLog.isSelected());
            [CtCommentImpl]// gameLogMaxSize.setText( Integer.toString(cs.getGameLogMaxSize()) );
            [CtFieldReadImpl]stampFilenames.setSelected([CtInvocationImpl][CtVariableReadImpl]cs.stampFilenames());
            [CtInvocationImpl][CtFieldReadImpl]stampFormat.setEnabled([CtInvocationImpl][CtFieldReadImpl]stampFilenames.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]stampFormat.setText([CtInvocationImpl][CtVariableReadImpl]cs.getStampFormat());
            [CtInvocationImpl][CtFieldReadImpl]defaultAutoejectDisabled.setSelected([CtInvocationImpl][CtVariableReadImpl]cs.defaultAutoejectDisabled());
            [CtInvocationImpl][CtFieldReadImpl]useAverageSkills.setSelected([CtInvocationImpl][CtVariableReadImpl]cs.useAverageSkills());
            [CtInvocationImpl][CtFieldReadImpl]generateNames.setSelected([CtInvocationImpl][CtVariableReadImpl]cs.generateNames());
            [CtInvocationImpl][CtFieldReadImpl]showUnitId.setSelected([CtInvocationImpl][CtVariableReadImpl]cs.getShowUnitId());
            [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtLiteralImpl]0;
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cs.getLocaleString().startsWith([CtLiteralImpl]"de")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]index = [CtLiteralImpl]1;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cs.getLocaleString().startsWith([CtLiteralImpl]"ru")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]index = [CtLiteralImpl]2;
            }
            [CtInvocationImpl][CtFieldReadImpl]displayLocale.setSelectedIndex([CtVariableReadImpl]index);
            [CtInvocationImpl][CtFieldReadImpl]showMapsheets.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getShowMapsheets());
            [CtInvocationImpl][CtFieldReadImpl]chkAntiAliasing.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getAntiAliasing());
            [CtInvocationImpl][CtFieldReadImpl]showDamageLevel.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getShowDamageLevel());
            [CtInvocationImpl][CtFieldReadImpl]showDamageDecal.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getShowDamageDecal());
            [CtInvocationImpl][CtFieldReadImpl]aOHexShadows.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getAOHexShadows());
            [CtInvocationImpl][CtFieldReadImpl]floatingIso.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getFloatingIso());
            [CtInvocationImpl][CtFieldReadImpl]mmSymbol.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getMmSymbol());
            [CtInvocationImpl][CtFieldReadImpl]levelhighlight.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getLevelHighlight());
            [CtInvocationImpl][CtFieldReadImpl]shadowMap.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getShadowMap());
            [CtInvocationImpl][CtFieldReadImpl]hexInclines.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getHexInclines());
            [CtInvocationImpl][CtFieldReadImpl]useSoftCenter.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getBoolean([CtLiteralImpl]"SOFTCENTER"));
            [CtInvocationImpl][CtFieldReadImpl]entityOwnerColor.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getUnitLabelBorder());
            [CtInvocationImpl][CtFieldReadImpl]teamColoring.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getTeamColoring());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File dir = [CtInvocationImpl][CtTypeAccessImpl]megamek.common.Configuration.hexesDir();
            [CtAssignmentImpl][CtFieldWriteImpl]tileSets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]dir.listFiles([CtNewClassImpl]new [CtTypeReferenceImpl]java.io.FilenameFilter()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]boolean accept([CtParameterImpl][CtTypeReferenceImpl]java.io.File direc, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]name.endsWith([CtLiteralImpl]".tileset");
                }
            })));
            [CtAssignmentImpl][CtVariableWriteImpl]dir = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtTypeAccessImpl]megamek.common.Configuration.userdataDir(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.common.Configuration.hexesDir().toString());
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.io.File[] userDataTilesets = [CtInvocationImpl][CtVariableReadImpl]dir.listFiles([CtNewClassImpl]new [CtTypeReferenceImpl]java.io.FilenameFilter()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]boolean accept([CtParameterImpl][CtTypeReferenceImpl]java.io.File direc, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]name.endsWith([CtLiteralImpl]".tileset");
                }
            });
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userDataTilesets != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]tileSets.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]userDataTilesets));
            }
            [CtInvocationImpl][CtFieldReadImpl]tileSetChoice.removeAllItems();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]tileSets != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]tileSets.size()); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tileSets.get([CtVariableReadImpl]i).getName();
                [CtInvocationImpl][CtFieldReadImpl]tileSetChoice.addItem([CtInvocationImpl][CtVariableReadImpl]name.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]name.length() - [CtLiteralImpl]8));
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtInvocationImpl][CtVariableReadImpl]cs.getMapTileset())) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]tileSetChoice.setSelectedIndex([CtVariableReadImpl]i);
                }
            }
            [CtInvocationImpl][CtFieldReadImpl]skinFiles.removeAllItems();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> xmlFiles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.common.Configuration.skinsDir().list([CtNewClassImpl]new [CtTypeReferenceImpl]java.io.FilenameFilter()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]boolean accept([CtParameterImpl][CtTypeReferenceImpl]java.io.File directory, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fileName) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]fileName.endsWith([CtLiteralImpl]".xml");
                }
            })));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] files = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtTypeAccessImpl]megamek.common.Configuration.userdataDir(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.common.Configuration.skinsDir().toString()).list([CtNewClassImpl]new [CtTypeReferenceImpl]java.io.FilenameFilter()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]boolean accept([CtParameterImpl][CtTypeReferenceImpl]java.io.File directory, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fileName) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]fileName.endsWith([CtLiteralImpl]".xml");
                }
            });
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]files != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]xmlFiles.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]files));
            }
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.sort([CtVariableReadImpl]xmlFiles);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String file : [CtVariableReadImpl]xmlFiles) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.widget.SkinXMLHandler.validSkinSpecFile([CtVariableReadImpl]file)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]skinFiles.addItem([CtVariableReadImpl]file);
                }
            }
            [CtInvocationImpl][CtCommentImpl]// Select the default file first
            [CtFieldReadImpl]skinFiles.setSelectedItem([CtTypeAccessImpl]SkinXMLHandler.defaultSkinXML);
            [CtInvocationImpl][CtCommentImpl]// If this select fials, the default skin will be selected
            [CtFieldReadImpl]skinFiles.setSelectedItem([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance().getSkinFile());
            [CtInvocationImpl][CtFieldReadImpl]uiThemes.removeAllItems();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.UIManager.LookAndFeelInfo lafInfo : [CtInvocationImpl][CtTypeAccessImpl]javax.swing.UIManager.getInstalledLookAndFeels()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]uiThemes.addItem([CtConstructorCallImpl]new [CtTypeReferenceImpl]megamek.client.ui.swing.UITheme([CtInvocationImpl][CtVariableReadImpl]lafInfo.getClassName(), [CtInvocationImpl][CtVariableReadImpl]lafInfo.getName()));
            }
            [CtInvocationImpl][CtFieldReadImpl]uiThemes.setSelectedItem([CtConstructorCallImpl]new [CtTypeReferenceImpl]megamek.client.ui.swing.UITheme([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance().getUITheme()));
            [CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlight());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setValue([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtLiteralImpl]100.0 / [CtLiteralImpl]255.0) * [CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlightAlpha())));
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadii.setText([CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlightRingsRadii());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColors.setText([CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlightRingsColorsHsb());
            [CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getFovDarken());
            [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setValue([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtLiteralImpl]100.0 / [CtLiteralImpl]255.0) * [CtInvocationImpl][CtVariableReadImpl]gs.getFovDarkenAlpha())));
            [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setValue([CtInvocationImpl][CtVariableReadImpl]gs.getFovStripes());
            [CtInvocationImpl][CtFieldReadImpl]fovGrayscaleEnabled.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getFovGrayscale());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadii.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColors.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovGrayscaleEnabled.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]darkenAlphaLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]numStripesLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColorsLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadiiLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]highlightAlphaLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]stampFormatLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]stampFilenames.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]gameLogFilenameLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]keepGameLog.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]getFocus.setSelected([CtInvocationImpl][CtVariableReadImpl]gs.getFocus());
            [CtAssignmentImpl][CtFieldWriteImpl]savedFovHighlight = [CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlight();
            [CtAssignmentImpl][CtFieldWriteImpl]savedFovDarken = [CtInvocationImpl][CtVariableReadImpl]gs.getFovDarken();
            [CtAssignmentImpl][CtFieldWriteImpl]savedFovGrayscale = [CtInvocationImpl][CtVariableReadImpl]gs.getFovGrayscale();
            [CtAssignmentImpl][CtFieldWriteImpl]savedAOHexShadows = [CtInvocationImpl][CtVariableReadImpl]gs.getAOHexShadows();
            [CtAssignmentImpl][CtFieldWriteImpl]savedShadowMap = [CtInvocationImpl][CtVariableReadImpl]gs.getShadowMap();
            [CtAssignmentImpl][CtFieldWriteImpl]savedHexInclines = [CtInvocationImpl][CtVariableReadImpl]gs.getHexInclines();
            [CtAssignmentImpl][CtFieldWriteImpl]savedLevelhighlight = [CtInvocationImpl][CtVariableReadImpl]gs.getLevelHighlight();
            [CtAssignmentImpl][CtFieldWriteImpl]savedFloatingIso = [CtInvocationImpl][CtVariableReadImpl]gs.getFloatingIso();
            [CtAssignmentImpl][CtFieldWriteImpl]savedMmSymbol = [CtInvocationImpl][CtVariableReadImpl]gs.getMmSymbol();
            [CtAssignmentImpl][CtFieldWriteImpl]savedTeamColoring = [CtInvocationImpl][CtVariableReadImpl]gs.getTeamColoring();
            [CtAssignmentImpl][CtFieldWriteImpl]savedUnitLabelBorder = [CtInvocationImpl][CtVariableReadImpl]gs.getUnitLabelBorder();
            [CtAssignmentImpl][CtFieldWriteImpl]savedShowDamageDecal = [CtInvocationImpl][CtVariableReadImpl]gs.getShowDamageDecal();
            [CtAssignmentImpl][CtFieldWriteImpl]savedShowDamageLabel = [CtInvocationImpl][CtVariableReadImpl]gs.getShowDamageLevel();
            [CtAssignmentImpl][CtFieldWriteImpl]savedFovHighlightRingsRadii = [CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlightRingsRadii();
            [CtAssignmentImpl][CtFieldWriteImpl]savedFovHighlightRingsColors = [CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlightRingsColorsHsb();
            [CtAssignmentImpl][CtFieldWriteImpl]savedFovHighlightAlpha = [CtInvocationImpl][CtVariableReadImpl]gs.getFovHighlightAlpha();
            [CtAssignmentImpl][CtFieldWriteImpl]savedFovDarkenAlpha = [CtInvocationImpl][CtVariableReadImpl]gs.getFovDarkenAlpha();
            [CtAssignmentImpl][CtFieldWriteImpl]savedNumStripesSlider = [CtInvocationImpl][CtVariableReadImpl]gs.getFovStripes();
            [CtInvocationImpl][CtFieldReadImpl]savedAdvancedOpt.clear();
            [CtInvocationImpl][CtFieldReadImpl]advancedKeys.clearSelection();
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.setVisible([CtVariableReadImpl]visible);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cancel any updates made in this dialog and close it.
     */
    [CtTypeReferenceImpl]void cancel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Restore values that are immediately updated by player clicks
        [CtTypeReferenceImpl]megamek.client.ui.swing.GUIPreferences guip = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance();
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovHighlight([CtFieldReadImpl]savedFovHighlight);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovDarken([CtFieldReadImpl]savedFovDarken);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovGrayscale([CtFieldReadImpl]savedFovGrayscale);
        [CtInvocationImpl][CtVariableReadImpl]guip.setAOHexShadows([CtFieldReadImpl]savedAOHexShadows);
        [CtInvocationImpl][CtVariableReadImpl]guip.setShadowMap([CtFieldReadImpl]savedShadowMap);
        [CtInvocationImpl][CtVariableReadImpl]guip.setHexInclines([CtFieldReadImpl]savedHexInclines);
        [CtInvocationImpl][CtVariableReadImpl]guip.setLevelHighlight([CtFieldReadImpl]savedLevelhighlight);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFloatingIso([CtFieldReadImpl]savedFloatingIso);
        [CtInvocationImpl][CtVariableReadImpl]guip.setMmSymbol([CtFieldReadImpl]savedMmSymbol);
        [CtInvocationImpl][CtVariableReadImpl]guip.setTeamColoring([CtFieldReadImpl]savedTeamColoring);
        [CtInvocationImpl][CtVariableReadImpl]guip.setUnitLabelBorder([CtFieldReadImpl]savedUnitLabelBorder);
        [CtInvocationImpl][CtVariableReadImpl]guip.setShowDamageDecal([CtFieldReadImpl]savedShowDamageDecal);
        [CtInvocationImpl][CtVariableReadImpl]guip.setShowDamageLevel([CtFieldReadImpl]savedShowDamageLabel);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovHighlightRingsRadii([CtFieldReadImpl]savedFovHighlightRingsRadii);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovHighlightRingsColorsHsb([CtFieldReadImpl]savedFovHighlightRingsColors);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovHighlightAlpha([CtFieldReadImpl]savedFovHighlightAlpha);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovDarkenAlpha([CtFieldReadImpl]savedFovDarkenAlpha);
        [CtInvocationImpl][CtVariableReadImpl]guip.setFovStripes([CtFieldReadImpl]savedNumStripesSlider);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String option : [CtInvocationImpl][CtFieldReadImpl]savedAdvancedOpt.keySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance().setValue([CtVariableReadImpl]option, [CtInvocationImpl][CtFieldReadImpl]savedAdvancedOpt.get([CtVariableReadImpl]option));
        }
        [CtInvocationImpl]setVisible([CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Update the settings from this dialog's values, then closes it.
     */
    private [CtTypeReferenceImpl]void update() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.GUIPreferences gs = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.preference.IClientPreferences cs = [CtInvocationImpl][CtTypeAccessImpl]megamek.common.preference.PreferenceManager.getClientPreferences();
        [CtInvocationImpl][CtVariableReadImpl]gs.setShowDamageLevel([CtInvocationImpl][CtFieldReadImpl]showDamageLevel.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setShowDamageDecal([CtInvocationImpl][CtFieldReadImpl]showDamageDecal.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setUnitLabelBorder([CtInvocationImpl][CtFieldReadImpl]entityOwnerColor.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setTeamColoring([CtInvocationImpl][CtFieldReadImpl]teamColoring.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setAutoEndFiring([CtInvocationImpl][CtFieldReadImpl]autoEndFiring.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setAutoDeclareSearchlight([CtInvocationImpl][CtFieldReadImpl]autoDeclareSearchlight.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setDefaultWeaponSortOrder([CtInvocationImpl][CtFieldReadImpl]defaultWeaponSortOrder.getSelectedIndex());
        [CtInvocationImpl][CtVariableReadImpl]gs.setNagForMASC([CtInvocationImpl][CtFieldReadImpl]nagForMASC.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setNagForPSR([CtInvocationImpl][CtFieldReadImpl]nagForPSR.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setNagForWiGELanding([CtInvocationImpl][CtFieldReadImpl]nagForWiGELanding.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setNagForNoAction([CtInvocationImpl][CtFieldReadImpl]nagForNoAction.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setShowMoveStep([CtInvocationImpl][CtFieldReadImpl]animateMove.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setShowWrecks([CtInvocationImpl][CtFieldReadImpl]showWrecks.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setSoundMute([CtInvocationImpl][CtFieldReadImpl]soundMute.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setShowWpsinTT([CtInvocationImpl][CtFieldReadImpl]showWpsinTT.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setshowArmorMiniVisTT([CtInvocationImpl][CtFieldReadImpl]showArmorMiniVisTT.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setshowPilotPortraitTT([CtInvocationImpl][CtFieldReadImpl]showPilotPortraitTT.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setTooltipDelay([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtFieldReadImpl]tooltipDelay.getText()));
        [CtInvocationImpl][CtVariableReadImpl]gs.setTooltipDismissDelay([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtFieldReadImpl]tooltipDismissDelay.getText()));
        [CtInvocationImpl][CtVariableReadImpl]gs.setTooltipDistSuppression([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtFieldReadImpl]tooltipDistSupression.getText()));
        [CtInvocationImpl][CtVariableReadImpl]cs.setUnitStartChar([CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]unitStartChar.getSelectedItem())).charAt([CtLiteralImpl]0));
        [CtInvocationImpl][CtVariableReadImpl]gs.setMouseWheelZoom([CtInvocationImpl][CtFieldReadImpl]mouseWheelZoom.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setMouseWheelZoomFlip([CtInvocationImpl][CtFieldReadImpl]mouseWheelZoomFlip.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]cs.setMaxPathfinderTime([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtFieldReadImpl]maxPathfinderTime.getText()));
        [CtInvocationImpl][CtVariableReadImpl]gs.setGetFocus([CtInvocationImpl][CtFieldReadImpl]getFocus.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]cs.setKeepGameLog([CtInvocationImpl][CtFieldReadImpl]keepGameLog.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]cs.setGameLogFilename([CtInvocationImpl][CtFieldReadImpl]gameLogFilename.getText());
        [CtInvocationImpl][CtCommentImpl]// cs.setGameLogMaxSize(Integer.parseInt(gameLogMaxSize.getText()));
        [CtVariableReadImpl]cs.setStampFilenames([CtInvocationImpl][CtFieldReadImpl]stampFilenames.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]cs.setStampFormat([CtInvocationImpl][CtFieldReadImpl]stampFormat.getText());
        [CtInvocationImpl][CtVariableReadImpl]cs.setDefaultAutoejectDisabled([CtInvocationImpl][CtFieldReadImpl]defaultAutoejectDisabled.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]cs.setUseAverageSkills([CtInvocationImpl][CtFieldReadImpl]useAverageSkills.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]cs.setGenerateNames([CtInvocationImpl][CtFieldReadImpl]generateNames.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]cs.setShowUnitId([CtInvocationImpl][CtFieldReadImpl]showUnitId.isSelected());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.bv != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.bv.updateEntityLabels();
        }
        [CtInvocationImpl][CtVariableReadImpl]cs.setLocale([CtArrayReadImpl][CtFieldReadImpl][CtTypeAccessImpl]megamek.client.ui.swing.CommonSettingsDialog.[CtFieldReferenceImpl]LOCALE_CHOICES[[CtInvocationImpl][CtFieldReadImpl]displayLocale.getSelectedIndex()]);
        [CtInvocationImpl][CtVariableReadImpl]gs.setShowMapsheets([CtInvocationImpl][CtFieldReadImpl]showMapsheets.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setAOHexShadows([CtInvocationImpl][CtFieldReadImpl]aOHexShadows.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setFloatingIso([CtInvocationImpl][CtFieldReadImpl]floatingIso.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setMmSymbol([CtInvocationImpl][CtFieldReadImpl]mmSymbol.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setLevelHighlight([CtInvocationImpl][CtFieldReadImpl]levelhighlight.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setShadowMap([CtInvocationImpl][CtFieldReadImpl]shadowMap.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setHexInclines([CtInvocationImpl][CtFieldReadImpl]hexInclines.isSelected());
        [CtInvocationImpl][CtVariableReadImpl]gs.setValue([CtLiteralImpl]"SOFTCENTER", [CtInvocationImpl][CtFieldReadImpl]useSoftCenter.isSelected());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]gs.getAntiAliasing() != [CtInvocationImpl][CtFieldReadImpl]chkAntiAliasing.isSelected()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.bv != [CtLiteralImpl]null))) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.bv.clearHexImageCache();
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.bv.repaint();
        }
        [CtInvocationImpl][CtVariableReadImpl]gs.setAntiAliasing([CtInvocationImpl][CtFieldReadImpl]chkAntiAliasing.isSelected());
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.UITheme newUITheme = [CtInvocationImpl](([CtTypeReferenceImpl]megamek.client.ui.swing.UITheme) ([CtFieldReadImpl]uiThemes.getSelectedItem()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oldUITheme = [CtInvocationImpl][CtVariableReadImpl]gs.getUITheme();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]oldUITheme.equals([CtInvocationImpl][CtVariableReadImpl]newUITheme.getClassName())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]gs.setUITheme([CtInvocationImpl][CtVariableReadImpl]newUITheme.getClassName());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newSkinFile = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]skinFiles.getSelectedItem()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oldSkinFile = [CtInvocationImpl][CtVariableReadImpl]gs.getSkinFile();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]oldSkinFile.equals([CtVariableReadImpl]newSkinFile)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean success = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.widget.SkinXMLHandler.initSkinXMLHandler([CtVariableReadImpl]newSkinFile);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]success) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.widget.SkinXMLHandler.initSkinXMLHandler([CtVariableReadImpl]oldSkinFile);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String title = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.skinFileFail.title");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.skinFileFail.msg");
                [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtFieldReadImpl]owner, [CtVariableReadImpl]msg, [CtVariableReadImpl]title, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]gs.setSkinFile([CtVariableReadImpl]newSkinFile);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]tileSetChoice.getSelectedIndex() >= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tileSetFileName = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tileSets.get([CtInvocationImpl][CtFieldReadImpl]tileSetChoice.getSelectedIndex()).getName();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cs.getMapTileset().equals([CtVariableReadImpl]tileSetFileName)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.bv != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.bv.clearShadowMap();
            }
            [CtInvocationImpl][CtVariableReadImpl]cs.setMapTileset([CtVariableReadImpl]tileSetFileName);
        }
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.swing.ToolTipManager.sharedInstance().setInitialDelay([CtInvocationImpl][CtVariableReadImpl]gs.getTooltipDelay());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]gs.getTooltipDismissDelay() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.swing.ToolTipManager.sharedInstance().setDismissDelay([CtInvocationImpl][CtVariableReadImpl]gs.getTooltipDismissDelay());
        }
        [CtLocalVariableImpl][CtCommentImpl]// Lets iterate through all of the KeyCommandBinds and see if they've
        [CtCommentImpl]// changed
        [CtTypeReferenceImpl]boolean bindsChanged = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.util.KeyCommandBind kcb : [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.util.KeyCommandBind.values()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTextField txtModifiers = [CtInvocationImpl][CtFieldReadImpl]cmdModifierMap.get([CtFieldReadImpl][CtVariableReadImpl]kcb.cmd);
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JCheckBox repeatable = [CtInvocationImpl][CtFieldReadImpl]cmdRepeatableMap.get([CtFieldReadImpl][CtVariableReadImpl]kcb.cmd);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer keyCode = [CtInvocationImpl][CtFieldReadImpl]cmdKeyMap.get([CtFieldReadImpl][CtVariableReadImpl]kcb.cmd);
            [CtIfImpl][CtCommentImpl]// This shouldn't happen, but just to be safe...
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]txtModifiers == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtLiteralImpl]null)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]repeatable == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int modifiers = [CtLiteralImpl]0;
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]txtModifiers.getText().contains([CtInvocationImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.getKeyModifiersText([CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]SHIFT_MASK))) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]modifiers |= [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]SHIFT_MASK;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]txtModifiers.getText().contains([CtInvocationImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.getKeyModifiersText([CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]ALT_MASK))) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]modifiers |= [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]ALT_MASK;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]txtModifiers.getText().contains([CtInvocationImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.getKeyModifiersText([CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]CTRL_MASK))) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]modifiers |= [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]CTRL_MASK;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]kcb.modifiers != [CtVariableReadImpl]modifiers) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bindsChanged = [CtLiteralImpl]true;
                [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]kcb.modifiers = [CtVariableReadImpl]modifiers;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]kcb.key != [CtVariableReadImpl]keyCode) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bindsChanged = [CtLiteralImpl]true;
                [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]kcb.key = [CtVariableReadImpl]keyCode;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]kcb.isRepeatable != [CtInvocationImpl][CtVariableReadImpl]repeatable.isSelected()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bindsChanged = [CtLiteralImpl]true;
                [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]kcb.isRepeatable = [CtInvocationImpl][CtVariableReadImpl]repeatable.isSelected();
            }
        }
        [CtIfImpl]if ([CtVariableReadImpl]bindsChanged) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]megamek.common.KeyBindParser.writeKeyBindings();
        }
        [CtLocalVariableImpl][CtCommentImpl]// Button Order
        [CtCommentImpl]// Movement
        [CtTypeReferenceImpl]megamek.client.ui.swing.ButtonOrderPreferences bop = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.ButtonOrderPreferences.getInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean buttonOrderChanged = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]movePhaseCommands.getSize(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand cmd = [CtInvocationImpl][CtFieldReadImpl]movePhaseCommands.get([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getPriority() != [CtVariableReadImpl]i) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cmd.setPriority([CtVariableReadImpl]i);
                [CtInvocationImpl][CtVariableReadImpl]bop.setValue([CtInvocationImpl][CtVariableReadImpl]cmd.getCmd(), [CtVariableReadImpl]i);
                [CtAssignmentImpl][CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// Need to do stuff if the order changes.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]buttonOrderChanged && [CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clientgui.updateButtonPanel([CtTypeAccessImpl]IGame.Phase.PHASE_MOVEMENT);
        }
        [CtAssignmentImpl][CtCommentImpl]// Deploy
        [CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]deployPhaseCommands.getSize(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand cmd = [CtInvocationImpl][CtFieldReadImpl]deployPhaseCommands.get([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getPriority() != [CtVariableReadImpl]i) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cmd.setPriority([CtVariableReadImpl]i);
                [CtInvocationImpl][CtVariableReadImpl]bop.setValue([CtInvocationImpl][CtVariableReadImpl]cmd.getCmd(), [CtVariableReadImpl]i);
                [CtAssignmentImpl][CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// Need to do stuff if the order changes.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]buttonOrderChanged && [CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clientgui.updateButtonPanel([CtTypeAccessImpl]IGame.Phase.PHASE_DEPLOYMENT);
        }
        [CtAssignmentImpl][CtCommentImpl]// Firing
        [CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]firingPhaseCommands.getSize(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand cmd = [CtInvocationImpl][CtFieldReadImpl]firingPhaseCommands.get([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getPriority() != [CtVariableReadImpl]i) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cmd.setPriority([CtVariableReadImpl]i);
                [CtInvocationImpl][CtVariableReadImpl]bop.setValue([CtInvocationImpl][CtVariableReadImpl]cmd.getCmd(), [CtVariableReadImpl]i);
                [CtAssignmentImpl][CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// Need to do stuff if the order changes.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]buttonOrderChanged && [CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clientgui.updateButtonPanel([CtTypeAccessImpl]IGame.Phase.PHASE_FIRING);
        }
        [CtAssignmentImpl][CtCommentImpl]// Physical
        [CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]physicalPhaseCommands.getSize(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand cmd = [CtInvocationImpl][CtFieldReadImpl]physicalPhaseCommands.get([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getPriority() != [CtVariableReadImpl]i) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cmd.setPriority([CtVariableReadImpl]i);
                [CtInvocationImpl][CtVariableReadImpl]bop.setValue([CtInvocationImpl][CtVariableReadImpl]cmd.getCmd(), [CtVariableReadImpl]i);
                [CtAssignmentImpl][CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// Need to do stuff if the order changes.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]buttonOrderChanged && [CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clientgui.updateButtonPanel([CtTypeAccessImpl]IGame.Phase.PHASE_PHYSICAL);
        }
        [CtAssignmentImpl][CtCommentImpl]// Targeting
        [CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]targetingPhaseCommands.getSize(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand cmd = [CtInvocationImpl][CtFieldReadImpl]targetingPhaseCommands.get([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getPriority() != [CtVariableReadImpl]i) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cmd.setPriority([CtVariableReadImpl]i);
                [CtInvocationImpl][CtVariableReadImpl]bop.setValue([CtInvocationImpl][CtVariableReadImpl]cmd.getCmd(), [CtVariableReadImpl]i);
                [CtAssignmentImpl][CtVariableWriteImpl]buttonOrderChanged = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// Need to do stuff if the order changes.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]buttonOrderChanged && [CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clientgui.updateButtonPanel([CtTypeAccessImpl]IGame.Phase.PHASE_TARGETING);
        }
        [CtInvocationImpl]setVisible([CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle the player pressing the action buttons. <p/> Implements the
     * <code>ActionListener</code> interface.
     *
     * @param event
     * 		- the <code>ActionEvent</code> that initiated this call.
     */
    public [CtTypeReferenceImpl]void actionPerformed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String command = [CtInvocationImpl][CtVariableReadImpl]event.getActionCommand();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]command.equals([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.UPDATE)) [CtBlockImpl]{
            [CtInvocationImpl]update();
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]command.equals([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.CANCEL)) [CtBlockImpl]{
            [CtInvocationImpl]cancel();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle some setting changes that directly update e.g. the board.
     */
    public [CtTypeReferenceImpl]void itemStateChanged([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ItemEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object source = [CtInvocationImpl][CtVariableReadImpl]event.getItemSelectable();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.GUIPreferences guip = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]keepGameLog)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]gameLogFilename.setEnabled([CtInvocationImpl][CtFieldReadImpl]keepGameLog.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]stampFormatLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]stampFilenames.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]gameLogFilenameLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]keepGameLog.isSelected());
            [CtCommentImpl]// gameLogMaxSize.setEnabled(keepGameLog.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]stampFilenames)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]stampFormat.setEnabled([CtInvocationImpl][CtFieldReadImpl]stampFilenames.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]stampFormatLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]stampFilenames.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]fovInsideEnabled)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setFovHighlight([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadii.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColors.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColorsLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadiiLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]highlightAlphaLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]fovOutsideEnabled)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setFovDarken([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]darkenAlphaLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]numStripesLabel.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
            [CtInvocationImpl][CtFieldReadImpl]fovGrayscaleEnabled.setEnabled([CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]fovGrayscaleEnabled)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setFovGrayscale([CtInvocationImpl][CtFieldReadImpl]fovGrayscaleEnabled.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]aOHexShadows)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setAOHexShadows([CtInvocationImpl][CtFieldReadImpl]aOHexShadows.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]shadowMap)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setShadowMap([CtInvocationImpl][CtFieldReadImpl]shadowMap.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]hexInclines)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setHexInclines([CtInvocationImpl][CtFieldReadImpl]hexInclines.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]levelhighlight)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setLevelHighlight([CtInvocationImpl][CtFieldReadImpl]levelhighlight.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]floatingIso)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setFloatingIso([CtInvocationImpl][CtFieldReadImpl]floatingIso.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]mmSymbol)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setMmSymbol([CtInvocationImpl][CtFieldReadImpl]mmSymbol.isSelected());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.minimap != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.minimap.drawMap();
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]teamColoring)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setTeamColoring([CtInvocationImpl][CtFieldReadImpl]teamColoring.isSelected());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]clientgui != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.minimap != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]clientgui.minimap.drawMap();
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]entityOwnerColor)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setUnitLabelBorder([CtInvocationImpl][CtFieldReadImpl]entityOwnerColor.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]showDamageDecal)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setShowDamageDecal([CtInvocationImpl][CtFieldReadImpl]showDamageDecal.isSelected());
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]source.equals([CtFieldReadImpl]showDamageLevel)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setShowDamageLevel([CtInvocationImpl][CtFieldReadImpl]showDamageLevel.isSelected());
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void focusGained([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.FocusEvent e) [CtBlockImpl]{
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void focusLost([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.FocusEvent e) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object src = [CtInvocationImpl][CtVariableReadImpl]e.getSource();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.GUIPreferences guip = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]src.equals([CtFieldReadImpl]fovHighlightRingsRadii)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setFovHighlightRingsRadii([CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadii.getText());
            [CtReturnImpl]return;
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]src.equals([CtFieldReadImpl]fovHighlightRingsColors)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setFovHighlightRingsColorsHsb([CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColors.getText());
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// For Advanced options
        [CtTypeReferenceImpl]java.lang.String option = [CtBinaryOperatorImpl][CtLiteralImpl]"Advanced" + [CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]advancedKeys.getModel().getElementAt([CtFieldReadImpl]advancedKeyIndex).option;
        [CtInvocationImpl][CtFieldReadImpl]savedAdvancedOpt.put([CtVariableReadImpl]option, [CtInvocationImpl][CtVariableReadImpl]guip.getString([CtVariableReadImpl]option));
        [CtInvocationImpl][CtVariableReadImpl]guip.setValue([CtVariableReadImpl]option, [CtInvocationImpl][CtFieldReadImpl]advancedValue.getText());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The Graphics Tab
     */
    private [CtTypeReferenceImpl]javax.swing.JPanel getGraphicsPanel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component>> comps = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component>>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component> row;
        [CtAssignmentImpl][CtCommentImpl]// Anti-Aliasing
        [CtFieldWriteImpl]chkAntiAliasing = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.antiAliasing"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]chkAntiAliasing.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.antiAliasingToolTip"));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]chkAntiAliasing);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Animate Moves
        [CtFieldWriteImpl]animateMove = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.animateMove"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]animateMove);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Show Wrecks
        [CtFieldWriteImpl]showWrecks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showWrecks"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showWrecks);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Show Mapsheet borders
        [CtFieldWriteImpl]showMapsheets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.showMapsheets"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]showMapsheets);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Hill Base AO Shadows
        [CtFieldWriteImpl]aOHexShadows = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.AOHexSHadows"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]aOHexShadows.addItemListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]aOHexShadows);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Shadow Map = Terrain and Building shadows
        [CtFieldWriteImpl]shadowMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.useShadowMap"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]shadowMap.addItemListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]shadowMap);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Use Incline graphics (hex border highlights/shadows)
        [CtFieldWriteImpl]hexInclines = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.useInclines"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]hexInclines.addItemListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]hexInclines);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Level Highlight = borders around level changes
        [CtFieldWriteImpl]levelhighlight = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.levelHighlight"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]levelhighlight.addItemListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]levelhighlight);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Floating Isometric = do not draw hex sides
        [CtFieldWriteImpl]floatingIso = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.floatingIso"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]floatingIso.addItemListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]floatingIso);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Type of symbol used on the minimap
        [CtFieldWriteImpl]mmSymbol = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.mmSymbol"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]mmSymbol.addItemListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]mmSymbol);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// UI Theme
        [CtFieldWriteImpl]uiThemes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]megamek.client.ui.swing.UITheme>();
        [CtInvocationImpl][CtFieldReadImpl]uiThemes.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]400, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]uiThemes.getMaximumSize().height));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel uiThemesLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.uiTheme"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]uiThemesLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]uiThemes);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Skin
        [CtFieldWriteImpl]skinFiles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtFieldReadImpl]skinFiles.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]400, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]skinFiles.getMaximumSize().height));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel skinFileLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.skinFile"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]skinFileLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]skinFiles);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtLocalVariableImpl][CtCommentImpl]// Tileset
        [CtTypeReferenceImpl]javax.swing.JLabel tileSetChoiceLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"CommonSettingsDialog.tileset"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtFieldWriteImpl]tileSetChoice = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JComboBox<[CtTypeReferenceImpl]java.lang.String>();[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]tileSetChoice.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]400, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]tileSetChoice.getMaximumSize().height));
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]tileSetChoiceLabel);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createHorizontalStrut([CtLiteralImpl]15));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]tileSetChoice);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JSeparator highlightSep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]highlightSep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Highlighting Radius inside FoV
        [CtCommentImpl]// 
        [CtCommentImpl]// Highlight inside Check box
        [CtFieldWriteImpl]fovInsideEnabled = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovInsideEnabled"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]fovInsideEnabled.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovInsideEnabled);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Inside Opaqueness slider
        [CtFieldWriteImpl]fovHighlightAlpha = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSlider();
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setMajorTickSpacing([CtLiteralImpl]20);
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setMinorTickSpacing([CtLiteralImpl]5);
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setPaintTicks([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setPaintLabels([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]250, [CtLiteralImpl]100));
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.addChangeListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtCommentImpl]// Label
        [CtFieldWriteImpl]highlightAlphaLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovHighlightAlpha"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]highlightAlphaLabel);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovHighlightAlpha);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]fovHighlightRingsRadiiLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovHighlightRingsRadii"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovHighlightRingsRadiiLabel);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]fovHighlightRingsRadii = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]2 + [CtLiteralImpl]1) * [CtLiteralImpl]7);
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadii.addFocusListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadii.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]100, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsRadii.getPreferredSize().height));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovHighlightRingsRadii);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]fovHighlightRingsColorsLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovHighlightRingsColors"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovHighlightRingsColorsLabel);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]fovHighlightRingsColors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]50);[CtCommentImpl]// ((3+1)*3+1)*7);

        [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColors.addFocusListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColors.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]200, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]fovHighlightRingsColors.getPreferredSize().height));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovHighlightRingsColors);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createHorizontalGlue());
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Horizontal Line and Spacer
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JSeparator OutSideSep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtVariableReadImpl]OutSideSep);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]0, [CtLiteralImpl]5)));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Outside FoV Darkening
        [CtCommentImpl]// 
        [CtCommentImpl]// Activation Checkbox
        [CtFieldWriteImpl]fovOutsideEnabled = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovOutsideEnabled"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]fovOutsideEnabled.addItemListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovOutsideEnabled);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]fovDarkenAlpha = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSlider();
        [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setMajorTickSpacing([CtLiteralImpl]20);
        [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setMinorTickSpacing([CtLiteralImpl]5);
        [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setPaintTicks([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setPaintLabels([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]250, [CtLiteralImpl]100));
        [CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.addChangeListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]darkenAlphaLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovDarkenAlpha"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]4, [CtLiteralImpl]0)));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]darkenAlphaLabel);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovDarkenAlpha);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtCommentImpl]// Add some vertical spacing
        [CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]4));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtFieldWriteImpl]numStripesSlider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSlider([CtLiteralImpl]0, [CtLiteralImpl]50);
        [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setMajorTickSpacing([CtLiteralImpl]10);
        [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setMinorTickSpacing([CtLiteralImpl]5);
        [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setPaintTicks([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setPaintLabels([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]250, [CtLiteralImpl]100));
        [CtInvocationImpl][CtFieldReadImpl]numStripesSlider.addChangeListener([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]numStripesLabel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovStripes"));[CtCommentImpl]// $NON-NLS-1$

        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]4, [CtLiteralImpl]0)));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]numStripesLabel);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]4, [CtLiteralImpl]0)));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]numStripesSlider);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalStrut([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtAssignmentImpl][CtVariableWriteImpl]row = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]fovGrayscaleEnabled = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtLiteralImpl]"TacticalOverlaySettingsDialog.FovGrayscale"));[CtCommentImpl]// $NON-NLS-1$

        [CtInvocationImpl][CtFieldReadImpl]fovGrayscaleEnabled.addItemListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtLiteralImpl]4, [CtLiteralImpl]0)));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.DEPENDENT_INSET));
        [CtInvocationImpl][CtVariableReadImpl]row.add([CtFieldReadImpl]fovGrayscaleEnabled);
        [CtInvocationImpl][CtVariableReadImpl]comps.add([CtVariableReadImpl]row);
        [CtReturnImpl]return [CtInvocationImpl]createSettingsPanel([CtVariableReadImpl]comps);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a panel with a box for all of the commands that can be bound to
     * keys.
     *
     * @return  */
    private [CtTypeReferenceImpl]javax.swing.JPanel getKeyBindPanel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create the panel to hold all the components
        [CtCommentImpl]// We will have an N x 43 grid, the first column is for labels, the
        [CtCommentImpl]// second column will hold text fields for modifiers, the third
        [CtCommentImpl]// column holds text fields for keys, and the fourth has a checkbox for
        [CtCommentImpl]// isRepeatable.
        [CtTypeReferenceImpl]javax.swing.JPanel outer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtVariableReadImpl]outer.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.FlowLayout([CtFieldReadImpl][CtTypeAccessImpl]java.awt.FlowLayout.[CtFieldReferenceImpl]LEFT));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JPanel keyBinds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.GridBagLayout());
        [CtInvocationImpl][CtVariableReadImpl]outer.add([CtVariableReadImpl]keyBinds);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.GridBagConstraints gbc = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.GridBagConstraints();
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx = [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridy = [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.insets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Insets([CtLiteralImpl]0, [CtLiteralImpl]10, [CtLiteralImpl]5, [CtLiteralImpl]10);
        [CtLocalVariableImpl][CtCommentImpl]// Create header: labels for describing what each column does
        [CtTypeReferenceImpl]javax.swing.JLabel headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtLiteralImpl]"Name");
        [CtInvocationImpl][CtVariableReadImpl]headers.setToolTipText([CtLiteralImpl]"The name of the action");
        [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]headers, [CtVariableReadImpl]gbc);
        [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx++;
        [CtAssignmentImpl][CtVariableWriteImpl]headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtLiteralImpl]"Modifier");
        [CtInvocationImpl][CtVariableReadImpl]headers.setToolTipText([CtLiteralImpl]"The modifier key, like shift, ctrl, alt");
        [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]headers, [CtVariableReadImpl]gbc);
        [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx++;
        [CtAssignmentImpl][CtVariableWriteImpl]headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtLiteralImpl]"Key");
        [CtInvocationImpl][CtVariableReadImpl]headers.setToolTipText([CtLiteralImpl]"The key");
        [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]headers, [CtVariableReadImpl]gbc);
        [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx++;
        [CtAssignmentImpl][CtVariableWriteImpl]headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtLiteralImpl]"Repeatable?");
        [CtInvocationImpl][CtVariableReadImpl]headers.setToolTipText([CtBinaryOperatorImpl][CtLiteralImpl]"Should this action repeat rapidly " + [CtLiteralImpl]"when the key is held down?");
        [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]headers, [CtVariableReadImpl]gbc);
        [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridy++;
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx = [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridwidth = [CtLiteralImpl]4;
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.fill = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.GridBagConstraints.[CtFieldReferenceImpl]BOTH;
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JSeparator sep = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JSeparator([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.SwingConstants.[CtFieldReferenceImpl]HORIZONTAL);
        [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]sep, [CtVariableReadImpl]gbc);
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.fill = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.GridBagConstraints.[CtFieldReferenceImpl]NONE;
        [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridy++;
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridwidth = [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtCommentImpl]// Create maps to retrieve the text fields for saving
        [CtTypeReferenceImpl]int numBinds = [CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.util.KeyCommandBind.values().length;
        [CtAssignmentImpl][CtFieldWriteImpl]cmdModifierMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]javax.swing.JTextField>([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]numBinds * [CtLiteralImpl]1.26)));
        [CtAssignmentImpl][CtFieldWriteImpl]cmdKeyMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer>([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]numBinds * [CtLiteralImpl]1.26)));
        [CtAssignmentImpl][CtFieldWriteImpl]cmdRepeatableMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]javax.swing.JCheckBox>([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]numBinds * [CtLiteralImpl]1.26)));
        [CtForEachImpl][CtCommentImpl]// For each keyCommandBind, create a label and two text fields
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.util.KeyCommandBind kcb : [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.util.KeyCommandBind.values()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JLabel name = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JLabel([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtBinaryOperatorImpl][CtLiteralImpl]"KeyBinds.cmdNames." + [CtFieldReadImpl][CtVariableReadImpl]kcb.cmd));
            [CtInvocationImpl][CtVariableReadImpl]name.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.Messages.getString([CtBinaryOperatorImpl][CtLiteralImpl]"KeyBinds.cmdDesc." + [CtFieldReadImpl][CtVariableReadImpl]kcb.cmd));
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.anchor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.GridBagConstraints.[CtFieldReferenceImpl]EAST;
            [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]name, [CtVariableReadImpl]gbc);
            [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx++;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.anchor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.GridBagConstraints.[CtFieldReferenceImpl]CENTER;
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.swing.JTextField modifiers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]10);
            [CtInvocationImpl][CtVariableReadImpl]modifiers.setText([CtInvocationImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.getKeyModifiersText([CtFieldReadImpl][CtVariableReadImpl]kcb.modifiers));
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.event.KeyListener kl : [CtInvocationImpl][CtVariableReadImpl]modifiers.getKeyListeners()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]modifiers.removeKeyListener([CtVariableReadImpl]kl);
            }
            [CtInvocationImpl][CtCommentImpl]// Update how typing in the text field works
            [CtVariableReadImpl]modifiers.addKeyListener([CtNewClassImpl]new [CtTypeReferenceImpl]java.awt.event.KeyListener()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void keyPressed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.KeyEvent evt) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]modifiers.setText([CtInvocationImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.getKeyModifiersText([CtInvocationImpl][CtVariableReadImpl]evt.getModifiers()));
                    [CtInvocationImpl][CtVariableReadImpl]evt.consume();
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void keyReleased([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.KeyEvent evt) [CtBlockImpl]{
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void keyTyped([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.KeyEvent evt) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// This might be a bit hackish, but we want to deal with
                    [CtCommentImpl]// the key code, so the code to update the text is in
                    [CtCommentImpl]// keyPressed.  We've already done what we want with the
                    [CtCommentImpl]// typed key, and we don't want anything else acting upon
                    [CtCommentImpl]// the key typed event, so we consume it here.
                    [CtVariableReadImpl]evt.consume();
                }
            });
            [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]modifiers, [CtVariableReadImpl]gbc);
            [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx++;
            [CtInvocationImpl][CtFieldReadImpl]cmdModifierMap.put([CtFieldReadImpl][CtVariableReadImpl]kcb.cmd, [CtVariableReadImpl]modifiers);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.swing.JTextField key = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]10);
            [CtInvocationImpl][CtVariableReadImpl]key.setName([CtFieldReadImpl][CtVariableReadImpl]kcb.cmd);
            [CtInvocationImpl][CtVariableReadImpl]key.setText([CtInvocationImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.getKeyText([CtFieldReadImpl][CtVariableReadImpl]kcb.key));
            [CtLocalVariableImpl][CtCommentImpl]// Update how typing in the text field works
            final [CtTypeReferenceImpl]java.lang.String cmd = [CtFieldReadImpl][CtVariableReadImpl]kcb.cmd;
            [CtInvocationImpl][CtFieldReadImpl]cmdKeyMap.put([CtVariableReadImpl]cmd, [CtFieldReadImpl][CtVariableReadImpl]kcb.key);
            [CtInvocationImpl][CtVariableReadImpl]key.addKeyListener([CtNewClassImpl]new [CtTypeReferenceImpl]java.awt.event.KeyListener()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void keyPressed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.KeyEvent evt) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]key.setText([CtInvocationImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.getKeyText([CtInvocationImpl][CtVariableReadImpl]evt.getKeyCode()));
                    [CtInvocationImpl][CtFieldReadImpl]cmdKeyMap.put([CtVariableReadImpl]cmd, [CtInvocationImpl][CtVariableReadImpl]evt.getKeyCode());
                    [CtInvocationImpl][CtVariableReadImpl]evt.consume();
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void keyReleased([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.KeyEvent evt) [CtBlockImpl]{
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void keyTyped([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.KeyEvent evt) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// This might be a bit hackish, but we want to deal with
                    [CtCommentImpl]// the key code, so the code to update the text is in
                    [CtCommentImpl]// keyPressed.  We've already done what we want with the
                    [CtCommentImpl]// typed key, and we don't want anything else acting upon
                    [CtCommentImpl]// the key typed event, so we consume it here.
                    [CtVariableReadImpl]evt.consume();
                }
            });
            [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]key, [CtVariableReadImpl]gbc);
            [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx++;
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JCheckBox repeatable = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBox([CtLiteralImpl]"Repeatable?");
            [CtInvocationImpl][CtVariableReadImpl]repeatable.setSelected([CtFieldReadImpl][CtVariableReadImpl]kcb.isRepeatable);
            [CtInvocationImpl][CtFieldReadImpl]cmdRepeatableMap.put([CtFieldReadImpl][CtVariableReadImpl]kcb.cmd, [CtVariableReadImpl]repeatable);
            [CtInvocationImpl][CtVariableReadImpl]keyBinds.add([CtVariableReadImpl]repeatable, [CtVariableReadImpl]gbc);
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridx = [CtLiteralImpl]0;
            [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableReadImpl]gbc.gridy++;
            [CtInvocationImpl][CtCommentImpl]// deactivate TABbing through fields here so TAB can be caught as a keybind
            [CtVariableReadImpl]modifiers.setFocusTraversalKeysEnabled([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]key.setFocusTraversalKeysEnabled([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]repeatable.setFocusTraversalKeysEnabled([CtLiteralImpl]false);
        }
        [CtReturnImpl]return [CtVariableReadImpl]outer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a panel with a list boxes that allow the button order to be
     * changed.
     *
     * @return  */
    private [CtTypeReferenceImpl]javax.swing.JPanel getButtonOrderPanel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JPanel buttonOrderPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtVariableReadImpl]buttonOrderPanel.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.BoxLayout([CtVariableReadImpl]buttonOrderPanel, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]Y_AXIS));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTabbedPane phasePane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTabbedPane();
        [CtInvocationImpl][CtVariableReadImpl]buttonOrderPanel.add([CtVariableReadImpl]phasePane);
        [CtAssignmentImpl][CtCommentImpl]// MovementPhaseDisplay
        [CtFieldWriteImpl]movePhaseCommands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand>();
        [CtInvocationImpl][CtVariableReadImpl]phasePane.add([CtLiteralImpl]"Movement", [CtInvocationImpl]getButtonOrderPane([CtFieldReadImpl]movePhaseCommands, [CtInvocationImpl][CtTypeAccessImpl]MovementDisplay.MoveCommand.values()));
        [CtAssignmentImpl][CtCommentImpl]// DeploymentPhaseDisplay
        [CtFieldWriteImpl]deployPhaseCommands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand>();
        [CtInvocationImpl][CtVariableReadImpl]phasePane.add([CtLiteralImpl]"Deployment", [CtInvocationImpl]getButtonOrderPane([CtFieldReadImpl]deployPhaseCommands, [CtInvocationImpl][CtTypeAccessImpl]DeploymentDisplay.DeployCommand.values()));
        [CtAssignmentImpl][CtCommentImpl]// FiringPhaseDisplay
        [CtFieldWriteImpl]firingPhaseCommands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand>();
        [CtInvocationImpl][CtVariableReadImpl]phasePane.add([CtLiteralImpl]"Firing", [CtInvocationImpl]getButtonOrderPane([CtFieldReadImpl]firingPhaseCommands, [CtInvocationImpl][CtTypeAccessImpl]FiringDisplay.FiringCommand.values()));
        [CtAssignmentImpl][CtCommentImpl]// PhysicalPhaseDisplay
        [CtFieldWriteImpl]physicalPhaseCommands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand>();
        [CtInvocationImpl][CtVariableReadImpl]phasePane.add([CtLiteralImpl]"Physical", [CtInvocationImpl]getButtonOrderPane([CtFieldReadImpl]physicalPhaseCommands, [CtInvocationImpl][CtTypeAccessImpl]PhysicalDisplay.PhysicalCommand.values()));
        [CtAssignmentImpl][CtCommentImpl]// TargetingPhaseDisplay
        [CtFieldWriteImpl]targetingPhaseCommands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand>();
        [CtInvocationImpl][CtVariableReadImpl]phasePane.add([CtLiteralImpl]"Targeting", [CtInvocationImpl]getButtonOrderPane([CtFieldReadImpl]targetingPhaseCommands, [CtInvocationImpl][CtTypeAccessImpl]TargetingPhaseDisplay.TargetingCommand.values()));
        [CtReturnImpl]return [CtVariableReadImpl]buttonOrderPanel;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Constructs the button ordering panel for one phase.
     */
    private [CtTypeReferenceImpl]javax.swing.JScrollPane getButtonOrderPane([CtParameterImpl][CtTypeReferenceImpl]javax.swing.DefaultListModel<[CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand> list, [CtParameterImpl]megamek.client.ui.swing.StatusBarPhaseDisplay[] commands) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JPanel panel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.sort([CtVariableReadImpl]commands, [CtFieldReadImpl]cmdComp);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand cmd : [CtVariableReadImpl]commands) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]list.addElement([CtVariableReadImpl]cmd);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JList<[CtTypeReferenceImpl][CtTypeReferenceImpl]megamek.client.ui.swing.StatusBarPhaseDisplay.PhaseCommand> jlist = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JList<>([CtVariableReadImpl]list);
        [CtInvocationImpl][CtVariableReadImpl]jlist.setSelectionMode([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.ListSelectionModel.[CtFieldReferenceImpl]SINGLE_SELECTION);
        [CtInvocationImpl][CtVariableReadImpl]jlist.addMouseListener([CtFieldReadImpl]cmdMouseAdaptor);
        [CtInvocationImpl][CtVariableReadImpl]jlist.addMouseMotionListener([CtFieldReadImpl]cmdMouseAdaptor);
        [CtInvocationImpl][CtVariableReadImpl]panel.add([CtVariableReadImpl]jlist);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtVariableReadImpl]panel);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]scrollPane.getVerticalScrollBar().setUnitIncrement([CtLiteralImpl]16);
        [CtReturnImpl]return [CtVariableReadImpl]scrollPane;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JPanel createSettingsPanel([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component>> comps) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JPanel panel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtVariableReadImpl]panel.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.BorderLayout());
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.Box innerpanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.Box([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]PAGE_AXIS);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.awt.Component> cs : [CtVariableReadImpl]comps) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.Box subPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.Box([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]LINE_AXIS);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Component c : [CtVariableReadImpl]cs) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.swing.JLabel) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]subPanel.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.LABEL_SPACER));
                    [CtInvocationImpl][CtVariableReadImpl]subPanel.add([CtVariableReadImpl]c);
                    [CtInvocationImpl][CtVariableReadImpl]subPanel.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createRigidArea([CtFieldReadImpl]megamek.client.ui.swing.CommonSettingsDialog.LABEL_SPACER));
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]subPanel.add([CtVariableReadImpl]c);
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]subPanel.setAlignmentX([CtFieldReadImpl][CtTypeAccessImpl]java.awt.Component.[CtFieldReferenceImpl]LEFT_ALIGNMENT);
            [CtInvocationImpl][CtVariableReadImpl]innerpanel.add([CtVariableReadImpl]subPanel);
        }
        [CtInvocationImpl][CtVariableReadImpl]innerpanel.add([CtInvocationImpl][CtTypeAccessImpl]javax.swing.Box.createVerticalGlue());
        [CtInvocationImpl][CtVariableReadImpl]innerpanel.setBorder([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.border.EmptyBorder([CtLiteralImpl]10, [CtLiteralImpl]10, [CtLiteralImpl]10, [CtLiteralImpl]10));
        [CtInvocationImpl][CtVariableReadImpl]panel.add([CtVariableReadImpl]innerpanel, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.BorderLayout.[CtFieldReferenceImpl]PAGE_START);
        [CtReturnImpl]return [CtVariableReadImpl]panel;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JPanel getAdvancedSettingsPanel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JPanel p = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] s = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance().getAdvancedProperties();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.AdvancedOptionData[] opts = [CtNewArrayImpl]new [CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.AdvancedOptionData[[CtFieldReadImpl][CtVariableReadImpl]s.length];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]s.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]s[[CtVariableReadImpl]i] = [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]s[[CtVariableReadImpl]i].substring([CtBinaryOperatorImpl][CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]s[[CtVariableReadImpl]i].indexOf([CtLiteralImpl]"Advanced") + [CtLiteralImpl]8, [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]s[[CtVariableReadImpl]i].length());
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]opts[[CtVariableReadImpl]i] = [CtConstructorCallImpl]new [CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.AdvancedOptionData([CtArrayReadImpl][CtVariableReadImpl]s[[CtVariableReadImpl]i]);
        }
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.sort([CtVariableReadImpl]opts);
        [CtAssignmentImpl][CtFieldWriteImpl]advancedKeys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JList<>([CtVariableReadImpl]opts);
        [CtInvocationImpl][CtFieldReadImpl]advancedKeys.setSelectionMode([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.ListSelectionModel.[CtFieldReferenceImpl]SINGLE_SELECTION);
        [CtInvocationImpl][CtFieldReadImpl]advancedKeys.addListSelectionListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]advancedKeys.addMouseMotionListener([CtNewClassImpl]new [CtTypeReferenceImpl]java.awt.event.MouseMotionAdapter()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void mouseMoved([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent e) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtInvocationImpl][CtFieldReadImpl]advancedKeys.locationToIndex([CtInvocationImpl][CtVariableReadImpl]e.getPoint());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index > [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.CommonSettingsDialog.AdvancedOptionData dat = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]advancedKeys.getModel().getElementAt([CtVariableReadImpl]index);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dat.hasTooltipText()) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]advancedKeys.setToolTipText([CtInvocationImpl][CtVariableReadImpl]dat.getTooltipText());
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]advancedKeys.setToolTipText([CtLiteralImpl]null);
                    }
                }
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]p.add([CtFieldReadImpl]advancedKeys);
        [CtAssignmentImpl][CtFieldWriteImpl]advancedValue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextField([CtLiteralImpl]10);
        [CtInvocationImpl][CtFieldReadImpl]advancedValue.setFont([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Font([CtFieldReadImpl][CtTypeAccessImpl]java.awt.Font.[CtFieldReferenceImpl]SANS_SERIF, [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Font.[CtFieldReferenceImpl]PLAIN, [CtLiteralImpl]16));
        [CtInvocationImpl][CtFieldReadImpl]advancedValue.addFocusListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]p.add([CtFieldReadImpl]advancedValue);
        [CtReturnImpl]return [CtVariableReadImpl]p;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used to note which advanced setting is currently clicked.
     */
    public [CtTypeReferenceImpl]void valueChanged([CtParameterImpl][CtTypeReferenceImpl]javax.swing.event.ListSelectionEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]event.getValueIsAdjusting()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getSource().equals([CtFieldReadImpl]advancedKeys)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]advancedValue.setText([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance().getString([CtBinaryOperatorImpl][CtLiteralImpl]"Advanced" + [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]advancedKeys.getSelectedValue().option));
            [CtAssignmentImpl][CtFieldWriteImpl]advancedKeyIndex = [CtInvocationImpl][CtFieldReadImpl]advancedKeys.getSelectedIndex();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void stateChanged([CtParameterImpl][CtTypeReferenceImpl]javax.swing.event.ChangeEvent evt) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.client.ui.swing.GUIPreferences guip = [CtInvocationImpl][CtTypeAccessImpl]megamek.client.ui.swing.GUIPreferences.getInstance();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]evt.getSource().equals([CtFieldReadImpl]fovHighlightAlpha)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Need to convert from 0-100 to 0-255
            [CtVariableReadImpl]guip.setFovHighlightAlpha([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtFieldReadImpl]fovHighlightAlpha.getValue() * [CtLiteralImpl]2.55)));
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]evt.getSource().equals([CtFieldReadImpl]fovDarkenAlpha)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Need to convert from 0-100 to 0-255
            [CtVariableReadImpl]guip.setFovDarkenAlpha([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtFieldReadImpl]fovDarkenAlpha.getValue() * [CtLiteralImpl]2.55)));
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]evt.getSource().equals([CtFieldReadImpl]numStripesSlider)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]guip.setFovStripes([CtInvocationImpl][CtFieldReadImpl]numStripesSlider.getValue());
        }
    }
}
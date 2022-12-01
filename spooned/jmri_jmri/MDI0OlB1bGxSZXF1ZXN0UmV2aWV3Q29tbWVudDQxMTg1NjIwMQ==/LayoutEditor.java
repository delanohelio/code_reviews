[CompilationUnitImpl][CtPackageDeclarationImpl]package jmri.jmrit.display.layoutEditor;
[CtImportImpl]import java.util.*;
[CtImportImpl]import java.util.stream.*;
[CtUnresolvedImport]import jmri.jmrit.catalog.NamedIcon;
[CtImportImpl]import java.beans.*;
[CtImportImpl]import jmri.*;
[CtImportImpl]import javax.imageio.ImageIO;
[CtImportImpl]import java.awt.Cursor;
[CtImportImpl]import java.lang.reflect.Field;
[CtImportImpl]import java.awt.Container;
[CtImportImpl]import java.awt.Font;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtImportImpl]import java.awt.PointerInfo;
[CtImportImpl]import java.awt.Rectangle;
[CtUnresolvedImport]import jmri.util.*;
[CtUnresolvedImport]import javax.swing.event.*;
[CtUnresolvedImport]import jmri.jmrit.display.panelEditor.PanelEditor;
[CtImportImpl]import jmri.jmrit.display.*;
[CtImportImpl]import java.awt.Toolkit;
[CtUnresolvedImport]import jmri.jmrit.display.layoutEditor.LayoutEditorDialogs.*;
[CtImportImpl]import java.awt.Component;
[CtImportImpl]import java.awt.Color;
[CtUnresolvedImport]import javax.annotation.*;
[CtImportImpl]import java.awt.geom.*;
[CtImportImpl]import java.text.MessageFormat;
[CtUnresolvedImport]import jmri.util.swing.*;
[CtUnresolvedImport]import jmri.jmrit.dispatcher.*;
[CtUnresolvedImport]import jmri.swing.NamedBeanComboBox;
[CtImportImpl]import java.awt.Dimension;
[CtImportImpl]import java.awt.Point;
[CtImportImpl]import javax.swing.filechooser.FileNameExtensionFilter;
[CtUnresolvedImport]import javax.swing.*;
[CtImportImpl]import java.awt.MouseInfo;
[CtUnresolvedImport]import jmri.configurexml.StoreXmlUserAction;
[CtImportImpl]import java.awt.event.*;
[CtImportImpl]import java.awt.Graphics;
[CtUnresolvedImport]import jmri.jmrit.entryexit.AddEntryExitPairAction;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
[CtClassImpl][CtJavaDocImpl]/**
 * Provides a scrollable Layout Panel and editor toolbars (that can be hidden)
 * <p>
 * This module serves as a manager for the LayoutTurnout, Layout Block,
 * PositionablePoint, Track Segment, LayoutSlip and LevelXing objects which are
 * integral subparts of the LayoutEditor class.
 * <p>
 * All created objects are put on specific levels depending on their type
 * (higher levels are in front): Note that higher numbers appear behind lower
 * numbers.
 * <p>
 * The "contents" List keeps track of all text and icon label objects added to
 * the target frame for later manipulation. Other Lists keep track of drawn
 * items.
 * <p>
 * Based in part on PanelEditor.java (Bob Jacobsen (c) 2002, 2003). In
 * particular, text and icon label items are copied from Panel editor, as well
 * as some of the control design.
 *
 * @author Dave Duchamp Copyright: (c) 2004-2007
 * @author George Warner Copyright: (c) 2017-2019
 */
[CtCommentImpl]// no Serializable support at present
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"serial")
[CtAnnotationImpl]@edu.umd.cs.findbugs.annotations.SuppressFBWarnings([CtLiteralImpl]"SE_TRANSIENT_FIELD_NOT_RESTORED")
public class LayoutEditor extends [CtTypeReferenceImpl]jmri.jmrit.display.panelEditor.PanelEditor implements [CtTypeReferenceImpl]java.awt.event.MouseWheelListener {
    [CtEnumImpl][CtCommentImpl]// hit point types
    public enum HitPointType {

        [CtEnumValueImpl]NONE([CtLiteralImpl]0),
        [CtEnumValueImpl]POS_POINT([CtLiteralImpl]1),
        [CtEnumValueImpl]TURNOUT_A([CtLiteralImpl]2),
        [CtEnumValueImpl][CtCommentImpl]// throat for RH, LH, and WYE turnouts
        TURNOUT_B([CtLiteralImpl]3),
        [CtEnumValueImpl][CtCommentImpl]// continuing route for RH and LH turnouts
        TURNOUT_C([CtLiteralImpl]4),
        [CtEnumValueImpl][CtCommentImpl]// diverging route for RH and LH turnouts
        TURNOUT_D([CtLiteralImpl]5),
        [CtEnumValueImpl][CtCommentImpl]// 4th route for crossovers
        LEVEL_XING_A([CtLiteralImpl]6),
        [CtEnumValueImpl]LEVEL_XING_B([CtLiteralImpl]7),
        [CtEnumValueImpl]LEVEL_XING_C([CtLiteralImpl]8),
        [CtEnumValueImpl]LEVEL_XING_D([CtLiteralImpl]9),
        [CtEnumValueImpl]TRACK([CtLiteralImpl]10),
        [CtEnumValueImpl]TURNOUT_CENTER([CtLiteralImpl]11),
        [CtEnumValueImpl][CtCommentImpl]// non-connection points should be last
        LEVEL_XING_CENTER([CtLiteralImpl]12),
        [CtEnumValueImpl]TURNTABLE_CENTER([CtLiteralImpl]13),
        [CtEnumValueImpl]LAYOUT_POS_LABEL([CtLiteralImpl]14),
        [CtEnumValueImpl]LAYOUT_POS_JCOMP([CtLiteralImpl]15),
        [CtEnumValueImpl]MULTI_SENSOR([CtLiteralImpl]16),
        [CtEnumValueImpl]MARKER([CtLiteralImpl]17),
        [CtEnumValueImpl]TRACK_CIRCLE_CENTRE([CtLiteralImpl]18),
        [CtEnumValueImpl]UNUSED_19([CtLiteralImpl]19),
        [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
        [CtCommentImpl]// (use SLIP_LEFT & SLIP_RIGHT instead)
        SLIP_CENTER([CtLiteralImpl]20),
        [CtEnumValueImpl]SLIP_A([CtLiteralImpl]21),
        [CtEnumValueImpl]SLIP_B([CtLiteralImpl]22),
        [CtEnumValueImpl]SLIP_C([CtLiteralImpl]23),
        [CtEnumValueImpl]SLIP_D([CtLiteralImpl]24),
        [CtEnumValueImpl]SLIP_LEFT([CtLiteralImpl]25),
        [CtEnumValueImpl]SLIP_RIGHT([CtLiteralImpl]26),
        [CtEnumValueImpl]UNUSED_27([CtLiteralImpl]27),
        [CtEnumValueImpl]UNUSED_28([CtLiteralImpl]28),
        [CtEnumValueImpl]UNUSED_29([CtLiteralImpl]29),
        [CtEnumValueImpl]BEZIER_CONTROL_POINT_0([CtLiteralImpl]30),
        [CtEnumValueImpl][CtCommentImpl]// offset for TrackSegment Bezier control points (minimum)
        BEZIER_CONTROL_POINT_1([CtLiteralImpl]31),
        [CtEnumValueImpl][CtCommentImpl]// \
        BEZIER_CONTROL_POINT_2([CtLiteralImpl]32),
        [CtEnumValueImpl][CtCommentImpl]// \
        BEZIER_CONTROL_POINT_3([CtLiteralImpl]33),
        [CtEnumValueImpl][CtCommentImpl]// \
        BEZIER_CONTROL_POINT_4([CtLiteralImpl]34),
        [CtEnumValueImpl][CtCommentImpl]// } -- DON'T USE THESE; PLACEHOLDERS ONLY
        BEZIER_CONTROL_POINT_5([CtLiteralImpl]35),
        [CtEnumValueImpl][CtCommentImpl]// /
        BEZIER_CONTROL_POINT_6([CtLiteralImpl]36),
        [CtEnumValueImpl][CtCommentImpl]// /
        BEZIER_CONTROL_POINT_7([CtLiteralImpl]37),
        [CtEnumValueImpl][CtCommentImpl]// /
        BEZIER_CONTROL_POINT_8([CtLiteralImpl]38),
        [CtEnumValueImpl][CtCommentImpl]// offset for TrackSegment Bezier control points (maximum)
        SHAPE_CENTER([CtLiteralImpl]39),
        [CtEnumValueImpl]SHAPE_POINT_0([CtLiteralImpl]40),
        [CtEnumValueImpl][CtCommentImpl]// offset for Shape points (minimum)
        SHAPE_POINT_1([CtLiteralImpl]41),
        [CtEnumValueImpl][CtCommentImpl]// \
        SHAPE_POINT_2([CtLiteralImpl]42),
        [CtEnumValueImpl][CtCommentImpl]// \
        SHAPE_POINT_3([CtLiteralImpl]43),
        [CtEnumValueImpl][CtCommentImpl]// \
        SHAPE_POINT_4([CtLiteralImpl]44),
        [CtEnumValueImpl][CtCommentImpl]// \ __ DON'T USE THESE; PLACEHOLDERS ONLY
        SHAPE_POINT_5([CtLiteralImpl]45),
        [CtEnumValueImpl][CtCommentImpl]// /
        SHAPE_POINT_6([CtLiteralImpl]46),
        [CtEnumValueImpl][CtCommentImpl]// /
        SHAPE_POINT_7([CtLiteralImpl]47),
        [CtEnumValueImpl][CtCommentImpl]// /
        SHAPE_POINT_8([CtLiteralImpl]48),
        [CtEnumValueImpl][CtCommentImpl]// /
        SHAPE_POINT_9([CtLiteralImpl]49),
        [CtEnumValueImpl][CtCommentImpl]// offset for Shape points (maximum)
        TURNTABLE_RAY_0([CtLiteralImpl]50),
        [CtEnumValueImpl][CtCommentImpl]// offset for turntable connection points (minimum)
        TURNTABLE_RAY_1([CtLiteralImpl]51),
        [CtEnumValueImpl][CtCommentImpl]// \
        TURNTABLE_RAY_2([CtLiteralImpl]52),
        [CtEnumValueImpl][CtCommentImpl]// \
        TURNTABLE_RAY_3([CtLiteralImpl]53),
        [CtEnumValueImpl][CtCommentImpl]// \
        TURNTABLE_RAY_4([CtLiteralImpl]54),
        [CtEnumValueImpl][CtCommentImpl]// \
        TURNTABLE_RAY_5([CtLiteralImpl]55),
        [CtEnumValueImpl][CtCommentImpl]// \
        TURNTABLE_RAY_6([CtLiteralImpl]56),
        [CtEnumValueImpl][CtCommentImpl]// \
        TURNTABLE_RAY_7([CtLiteralImpl]57),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_8([CtLiteralImpl]58),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_9([CtLiteralImpl]59),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_10([CtLiteralImpl]60),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_11([CtLiteralImpl]61),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_12([CtLiteralImpl]62),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_13([CtLiteralImpl]63),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_14([CtLiteralImpl]64),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_15([CtLiteralImpl]65),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_16([CtLiteralImpl]66),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_17([CtLiteralImpl]67),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_18([CtLiteralImpl]68),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_19([CtLiteralImpl]69),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_20([CtLiteralImpl]70),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_21([CtLiteralImpl]71),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_22([CtLiteralImpl]72),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_23([CtLiteralImpl]73),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_24([CtLiteralImpl]74),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_25([CtLiteralImpl]75),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_26([CtLiteralImpl]76),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_27([CtLiteralImpl]77),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_28([CtLiteralImpl]78),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_29([CtLiteralImpl]79),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_30([CtLiteralImpl]80),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_31([CtLiteralImpl]81),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_32([CtLiteralImpl]82),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_33([CtLiteralImpl]83),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_34([CtLiteralImpl]84),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_35([CtLiteralImpl]85),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_36([CtLiteralImpl]86),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_37([CtLiteralImpl]87),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_38([CtLiteralImpl]88),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_39([CtLiteralImpl]89),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_40([CtLiteralImpl]90),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_41([CtLiteralImpl]91),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_42([CtLiteralImpl]92),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_43([CtLiteralImpl]93),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_44([CtLiteralImpl]94),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_45([CtLiteralImpl]95),
        [CtEnumValueImpl][CtCommentImpl]// | -- DON'T USE THESE; PLACEHOLDERS ONLY
        TURNTABLE_RAY_46([CtLiteralImpl]96),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_47([CtLiteralImpl]97),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_48([CtLiteralImpl]98),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_49([CtLiteralImpl]99),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_50([CtLiteralImpl]100),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_51([CtLiteralImpl]101),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_52([CtLiteralImpl]102),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_53([CtLiteralImpl]103),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_54([CtLiteralImpl]104),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_55([CtLiteralImpl]105),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_56([CtLiteralImpl]106),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_57([CtLiteralImpl]107),
        [CtEnumValueImpl][CtCommentImpl]// |
        TURNTABLE_RAY_58([CtLiteralImpl]108),
        [CtEnumValueImpl][CtCommentImpl]// /
        TURNTABLE_RAY_59([CtLiteralImpl]109),
        [CtEnumValueImpl][CtCommentImpl]// /
        TURNTABLE_RAY_60([CtLiteralImpl]110),
        [CtEnumValueImpl][CtCommentImpl]// /
        TURNTABLE_RAY_61([CtLiteralImpl]111),
        [CtEnumValueImpl][CtCommentImpl]// /
        TURNTABLE_RAY_62([CtLiteralImpl]112),
        [CtEnumValueImpl][CtCommentImpl]// /
        TURNTABLE_RAY_63([CtLiteralImpl]113);
        [CtFieldImpl][CtCommentImpl]// offset for turntable connection points (maximum)
        private final transient [CtTypeReferenceImpl]java.lang.Integer xmlValue;

        [CtConstructorImpl]HitPointType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer xmlValue) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.xmlValue = [CtVariableReadImpl]xmlValue;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType getValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer xmlValue) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType result = [CtLiteralImpl]null;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType instance : [CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.values()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]instance.xmlValue.equals([CtVariableReadImpl]xmlValue)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]instance;
                    [CtBreakImpl]break;
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType getValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType result = [CtLiteralImpl]null;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType instance : [CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.values()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]instance.name().equals([CtVariableReadImpl]name)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]instance;
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Integer getXmlValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]xmlValue;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param hitType
         * 		the hit point type
         * @return true if this is for a connection to a LayoutTrack
         */
        protected static [CtTypeReferenceImpl]boolean isConnectionHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;[CtCommentImpl]// assume failure (pessimist!)

            [CtSwitchImpl]switch ([CtVariableReadImpl]hitType) {
                [CtCaseImpl]case POS_POINT :
                [CtCaseImpl]case TURNOUT_A :
                [CtCaseImpl]case TURNOUT_B :
                [CtCaseImpl]case TURNOUT_C :
                [CtCaseImpl]case TURNOUT_D :
                [CtCaseImpl]case LEVEL_XING_A :
                [CtCaseImpl]case LEVEL_XING_B :
                [CtCaseImpl]case LEVEL_XING_C :
                [CtCaseImpl]case LEVEL_XING_D :
                [CtCaseImpl]case TRACK :
                [CtCaseImpl]case SLIP_A :
                [CtCaseImpl]case SLIP_B :
                [CtCaseImpl]case SLIP_C :
                [CtCaseImpl]case SLIP_D :
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;[CtCommentImpl]// these are all connection types

                    [CtBreakImpl]break;
                [CtCaseImpl]case NONE :
                [CtCaseImpl]case TURNOUT_CENTER :
                [CtCaseImpl]case LEVEL_XING_CENTER :
                [CtCaseImpl]case TURNTABLE_CENTER :
                [CtCaseImpl]case LAYOUT_POS_LABEL :
                [CtCaseImpl]case LAYOUT_POS_JCOMP :
                [CtCaseImpl]case MULTI_SENSOR :
                [CtCaseImpl]case MARKER :
                [CtCaseImpl]case TRACK_CIRCLE_CENTRE :
                [CtCaseImpl]case SLIP_CENTER :
                [CtCaseImpl]case SLIP_LEFT :
                [CtCaseImpl]case SLIP_RIGHT :
                [CtCaseImpl]default :
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;[CtCommentImpl]// these are not

                    [CtBreakImpl]break;
            }
            [CtIfImpl]if ([CtInvocationImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isBezierHitType([CtVariableReadImpl]hitType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;[CtCommentImpl]// these are not

            } else [CtIfImpl]if ([CtInvocationImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtVariableReadImpl]hitType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;[CtCommentImpl]// these are all connection types

            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }[CtCommentImpl]// isConnectionHitType


        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param hitType
         * 		the hit point type
         * @return true if this hit type is for a layout control
         */
        protected static [CtTypeReferenceImpl]boolean isControlHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;[CtCommentImpl]// assume failure (pessimist!)

            [CtSwitchImpl]switch ([CtVariableReadImpl]hitType) {
                [CtCaseImpl]case TURNOUT_CENTER :
                [CtCaseImpl]case SLIP_CENTER :
                [CtCaseImpl]case SLIP_LEFT :
                [CtCaseImpl]case SLIP_RIGHT :
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;[CtCommentImpl]// these are all control types

                    [CtBreakImpl]break;
                [CtCaseImpl]case POS_POINT :
                [CtCaseImpl]case TURNOUT_A :
                [CtCaseImpl]case TURNOUT_B :
                [CtCaseImpl]case TURNOUT_C :
                [CtCaseImpl]case TURNOUT_D :
                [CtCaseImpl]case LEVEL_XING_A :
                [CtCaseImpl]case LEVEL_XING_B :
                [CtCaseImpl]case LEVEL_XING_C :
                [CtCaseImpl]case LEVEL_XING_D :
                [CtCaseImpl]case TRACK :
                [CtCaseImpl]case SLIP_A :
                [CtCaseImpl]case SLIP_B :
                [CtCaseImpl]case SLIP_C :
                [CtCaseImpl]case SLIP_D :
                [CtCaseImpl]case NONE :
                [CtCaseImpl]case LEVEL_XING_CENTER :
                [CtCaseImpl]case TURNTABLE_CENTER :
                [CtCaseImpl]case LAYOUT_POS_LABEL :
                [CtCaseImpl]case LAYOUT_POS_JCOMP :
                [CtCaseImpl]case MULTI_SENSOR :
                [CtCaseImpl]case MARKER :
                [CtCaseImpl]case TRACK_CIRCLE_CENTRE :
                [CtCaseImpl]default :
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;[CtCommentImpl]// these are not

                    [CtBreakImpl]break;
            }
            [CtIfImpl]if ([CtInvocationImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isBezierHitType([CtVariableReadImpl]hitType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;[CtCommentImpl]// these are not control types

            } else [CtIfImpl]if ([CtInvocationImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtVariableReadImpl]hitType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;[CtCommentImpl]// these are all control types

            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }[CtCommentImpl]// isControlHitType


        [CtMethodImpl]protected static [CtTypeReferenceImpl]boolean isTurnoutHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_A) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_D) <= [CtLiteralImpl]0);
        }

        [CtMethodImpl]protected static [CtTypeReferenceImpl]boolean isSlipHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_A) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_RIGHT) <= [CtLiteralImpl]0);
        }

        [CtMethodImpl]protected static [CtTypeReferenceImpl]boolean isBezierHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]BEZIER_CONTROL_POINT_0) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]BEZIER_CONTROL_POINT_8) <= [CtLiteralImpl]0);
        }

        [CtMethodImpl]protected static [CtTypeReferenceImpl]boolean isLevelXingHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]LEVEL_XING_A) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]LEVEL_XING_D) <= [CtLiteralImpl]0);
        }

        [CtMethodImpl]protected static [CtTypeReferenceImpl]boolean isTurntableRayHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]hitType.compareTo([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_63) <= [CtLiteralImpl]0);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param hitType
         * 		the hit point type
         * @return true if this is for a popup menu
         */
        protected static [CtTypeReferenceImpl]boolean isPopupHitType([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType hitType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;[CtCommentImpl]// assume failure (pessimist!)

            [CtSwitchImpl]switch ([CtVariableReadImpl]hitType) {
                [CtCaseImpl]case LEVEL_XING_CENTER :
                [CtCaseImpl]case POS_POINT :
                [CtCaseImpl]case SLIP_CENTER :
                [CtCaseImpl]case SLIP_LEFT :
                [CtCaseImpl]case SLIP_RIGHT :
                [CtCaseImpl]case TRACK :
                [CtCaseImpl]case TRACK_CIRCLE_CENTRE :
                [CtCaseImpl]case TURNOUT_CENTER :
                [CtCaseImpl]case TURNTABLE_CENTER :
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;[CtCommentImpl]// these are all popup hit types

                    [CtBreakImpl]break;
                [CtCaseImpl]case LAYOUT_POS_JCOMP :
                [CtCaseImpl]case LAYOUT_POS_LABEL :
                [CtCaseImpl]case LEVEL_XING_A :
                [CtCaseImpl]case LEVEL_XING_B :
                [CtCaseImpl]case LEVEL_XING_C :
                [CtCaseImpl]case LEVEL_XING_D :
                [CtCaseImpl]case MARKER :
                [CtCaseImpl]case MULTI_SENSOR :
                [CtCaseImpl]case NONE :
                [CtCaseImpl]case SLIP_A :
                [CtCaseImpl]case SLIP_B :
                [CtCaseImpl]case SLIP_C :
                [CtCaseImpl]case SLIP_D :
                [CtCaseImpl]case TURNOUT_A :
                [CtCaseImpl]case TURNOUT_B :
                [CtCaseImpl]case TURNOUT_C :
                [CtCaseImpl]case TURNOUT_D :
                [CtCaseImpl]default :
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;[CtCommentImpl]// these are not

                    [CtBreakImpl]break;
            }
            [CtIfImpl]if ([CtInvocationImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isBezierHitType([CtVariableReadImpl]hitType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;[CtCommentImpl]// these are all popup hit types

            } else [CtIfImpl]if ([CtInvocationImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtVariableReadImpl]hitType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;[CtCommentImpl]// these are all popup hit types

            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }[CtCommentImpl]// isPopupHitType

    }

    [CtFieldImpl][CtCommentImpl]// Operational instance variables - not saved to disk
    private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.JmriJFrame floatingEditToolBoxFrame = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JScrollPane floatingEditContentScrollPane = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JPanel floatEditHelpPanel = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JPanel editToolBarContainerPanel = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JScrollPane editToolBarScrollPane = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JPanel helpBarPanel = [CtLiteralImpl]null;

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JPanel helpBar = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]boolean editorUseOldLocSize;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorToolBarPanel leToolBarPanel = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorToolBarPanel getLayoutEditorToolBarPanel() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]leToolBarPanel;
    }

    [CtFieldImpl][CtCommentImpl]// end of main panel controls
    private transient [CtTypeReferenceImpl]boolean delayedPopupTrigger = [CtLiteralImpl]false;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.awt.geom.Point2D currentPoint = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtLiteralImpl]100.0, [CtLiteralImpl]100.0);

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.awt.geom.Point2D dLoc = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtLiteralImpl]0.0, [CtLiteralImpl]0.0);

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int toolbarHeight = [CtLiteralImpl]100;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int toolbarWidth = [CtLiteralImpl]100;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.TrackSegment newTrack = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean panelChanged = [CtLiteralImpl]false;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int gridSize1st = [CtLiteralImpl]10;[CtCommentImpl]// grid size in pixels


    [CtFieldImpl]private transient [CtTypeReferenceImpl]int gridSize2nd = [CtLiteralImpl]10;[CtCommentImpl]// secondary grid


    [CtFieldImpl][CtCommentImpl]// size of point boxes
    protected static final [CtTypeReferenceImpl]double SIZE = [CtLiteralImpl]3.0;

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]double SIZE2 = [CtBinaryOperatorImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.SIZE * [CtLiteralImpl]2.0;[CtCommentImpl]// must be twice SIZE


    [CtFieldImpl]protected [CtTypeReferenceImpl]java.awt.Color turnoutCircleColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]black;[CtCommentImpl]// matches earlier versions


    [CtFieldImpl]protected [CtTypeReferenceImpl]java.awt.Color turnoutCircleThrownColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]black;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean turnoutFillControlCircles = [CtLiteralImpl]false;

    [CtFieldImpl]protected [CtTypeReferenceImpl]int turnoutCircleSize = [CtLiteralImpl]4;[CtCommentImpl]// matches earlier versions


    [CtFieldImpl][CtCommentImpl]// use turnoutCircleSize when you need an int and these when you need a double
    [CtCommentImpl]// note: these only change when setTurnoutCircleSize is called
    [CtCommentImpl]// using these avoids having to call getTurnoutCircleSize() and
    [CtCommentImpl]// the multiply (x2) and the int -> double conversion overhead
    protected transient [CtTypeReferenceImpl]double circleRadius = [CtBinaryOperatorImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.SIZE * [CtInvocationImpl]getTurnoutCircleSize();

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]double circleDiameter = [CtBinaryOperatorImpl][CtLiteralImpl]2.0 * [CtFieldReadImpl]circleRadius;

    [CtFieldImpl][CtCommentImpl]// selection variables
    protected transient [CtTypeReferenceImpl]boolean selectionActive = [CtLiteralImpl]false;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]double selectionX = [CtLiteralImpl]0.0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]double selectionY = [CtLiteralImpl]0.0;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]double selectionWidth = [CtLiteralImpl]0.0;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]double selectionHeight = [CtLiteralImpl]0.0;

    [CtFieldImpl][CtCommentImpl]// Option menu items
    private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem editModeCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem toolBarSideTopButton = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem toolBarSideLeftButton = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem toolBarSideBottomButton = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem toolBarSideRightButton = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem toolBarSideFloatButton = [CtLiteralImpl]null;

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem wideToolBarCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBarWide"));

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem positionableCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem controlCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem animationCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem showHelpCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem showGridCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem autoAssignBlocksCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JMenu scrollMenu = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem scrollBothMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem scrollNoneMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem scrollHorizontalMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem scrollVerticalMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JMenu tooltipMenu = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem tooltipAlwaysMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem tooltipNoneMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem tooltipInEditMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem tooltipNotInEditMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem snapToGridOnAddCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem snapToGridOnMoveCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem antialiasingOnCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem turnoutCirclesOnCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem turnoutDrawUnselectedLegCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem turnoutFillControlCirclesCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem hideTrackSegmentConstructionLinesCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem useDirectTurnoutControlCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]javax.swing.ButtonGroup turnoutCircleSizeButtonGroup = [CtLiteralImpl]null;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean turnoutDrawUnselectedLeg = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean autoAssignBlocks = [CtLiteralImpl]false;

    [CtFieldImpl][CtCommentImpl]// Tools menu items
    private final transient [CtTypeReferenceImpl]javax.swing.JMenu zoomMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuZoom"));

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom025Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 0.25");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom05Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 0.5");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom075Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 0.75");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem noZoomItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"NoZoom"));

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom15Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 1.5");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom20Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 2.0");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom30Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 3.0");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom40Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 4.0");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom50Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 5.0");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom60Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 6.0");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom70Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 7.0");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem zoom80Item = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtLiteralImpl]"x 8.0");

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JMenuItem undoTranslateSelectionMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"UndoTranslateSelection"));

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]javax.swing.JMenuItem assignBlockToSelectionMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AssignBlockToSelectionTitle") + [CtLiteralImpl]"...");

    [CtFieldImpl][CtCommentImpl]// Selected point information
    private final transient [CtTypeReferenceImpl]java.awt.geom.Point2D startDelta = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtLiteralImpl]0.0, [CtLiteralImpl]0.0);[CtCommentImpl]// starting delta coordinates


    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.lang.Object selectedObject = [CtLiteralImpl]null;

    [CtFieldImpl][CtCommentImpl]// selected object, null if nothing selected
    protected transient [CtTypeReferenceImpl]java.lang.Object prevSelectedObject = [CtLiteralImpl]null;[CtCommentImpl]// previous selected object, for undo


    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE;

    [CtFieldImpl][CtCommentImpl]// hit point type within the selected object
    protected transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutTrack foundTrack = [CtLiteralImpl]null;

    [CtFieldImpl][CtCommentImpl]// found object, null if nothing found
    protected transient [CtTypeReferenceImpl]java.awt.geom.Point2D foundLocation = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtLiteralImpl]0.0, [CtLiteralImpl]0.0);[CtCommentImpl]// location of found object


    [CtFieldImpl]protected transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType foundHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE;

    [CtFieldImpl][CtCommentImpl]// connection type within the found object
    [CtCommentImpl]// /private transient boolean foundNeedsConnect = false;    //true if found point needs a connection
    protected transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutTrack beginTrack = [CtLiteralImpl]null;

    [CtFieldImpl][CtCommentImpl]// begin track segment connection object, null if none
    protected transient [CtTypeReferenceImpl]java.awt.geom.Point2D beginLocation = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtLiteralImpl]0.0, [CtLiteralImpl]0.0);[CtCommentImpl]// location of begin object


    [CtFieldImpl]protected transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE;[CtCommentImpl]// connection type within begin connection object


    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.awt.geom.Point2D currentLocation = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtLiteralImpl]0.0, [CtLiteralImpl]0.0);[CtCommentImpl]// current location


    [CtFieldImpl][CtCommentImpl]// Lists of items that describe the Layout, and allow it to be drawn
    [CtCommentImpl]// Each of the items must be saved to disk over sessions
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]AnalogClock2Display> clocks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// fast clocks
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LocoIcon> markerImage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// marker images
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]MultiSensorIcon> multiSensors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// multi-sensor images
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]PositionableLabel> backgroundImage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();[CtCommentImpl]// background images


    [CtFieldImpl]public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]PositionableLabel> labelImage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// positionable label images
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]SensorIcon> sensorImage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// sensor images
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]SignalHeadIcon> signalHeadImage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// signal head images
    private final transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutTrack> layoutTrackList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// LayoutTrack list
    [CtCommentImpl]// PositionableLabel's
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]BlockContentsIcon> blockContentsLabelList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();[CtCommentImpl]// BlockContentsIcon Label List


    [CtFieldImpl]public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]MemoryIcon> memoryLabelList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// Memory Label List
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]SensorIcon> sensorList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// Sensor Icons
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]SignalHeadIcon> signalList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// Signal Head Icons
    public transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]SignalMastIcon> signalMastList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// Signal Mast Icons
    private final transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutShape> layoutShapes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtCommentImpl]// LayoutShap list
    [CtCommentImpl]// counts used to determine unique internal names
    private transient [CtTypeReferenceImpl]int numAnchors = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numEndBumpers = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numEdgeConnectors = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numTrackSegments = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numLevelXings = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numLayoutSlips = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numLayoutTurnouts = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numLayoutTurntables = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int numShapes = [CtLiteralImpl]0;

    [CtFieldImpl]public transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorFindItems finder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorFindItems([CtThisAccessImpl]this);

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorFindItems getFinder() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]finder;
    }

    [CtFieldImpl][CtCommentImpl]// persistent instance variables - saved to disk with Save Panel
    private transient [CtTypeReferenceImpl]int upperLeftX = [CtLiteralImpl]0;[CtCommentImpl]// Note: These are _WINDOW_ upper left x & y


    [CtFieldImpl]private transient [CtTypeReferenceImpl]int upperLeftY = [CtLiteralImpl]0;[CtCommentImpl]// (not panel)


    [CtFieldImpl]private transient [CtTypeReferenceImpl]int windowWidth = [CtLiteralImpl]0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]int windowHeight = [CtLiteralImpl]0;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]int panelWidth = [CtLiteralImpl]0;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]int panelHeight = [CtLiteralImpl]0;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]float mainlineTrackWidth = [CtLiteralImpl]4.0F;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]float sidelineTrackWidth = [CtLiteralImpl]2.0F;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.awt.Color mainlineTrackColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]DARK_GRAY;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.awt.Color sidelineTrackColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]DARK_GRAY;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.awt.Color defaultTrackColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]DARK_GRAY;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.awt.Color defaultOccupiedTrackColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]red;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.awt.Color defaultAlternativeTrackColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]white;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.awt.Color defaultTextColor = [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]black;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.String layoutName = [CtLiteralImpl]"";

    [CtFieldImpl]private transient [CtTypeReferenceImpl]double xScale = [CtLiteralImpl]1.0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]double yScale = [CtLiteralImpl]1.0;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean animatingLayout = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean showHelpBar = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean drawGrid = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean snapToGridOnAdd = [CtLiteralImpl]false;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean snapToGridOnMove = [CtLiteralImpl]false;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean snapToGridInvert = [CtLiteralImpl]false;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]boolean antialiasingOn = [CtLiteralImpl]false;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]boolean highlightSelectedBlockFlag = [CtLiteralImpl]false;

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]boolean turnoutCirclesWithoutEditMode = [CtLiteralImpl]false;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean tooltipsWithoutEditMode = [CtLiteralImpl]false;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean tooltipsInEditMode = [CtLiteralImpl]true;

    [CtFieldImpl][CtCommentImpl]// turnout size parameters - saved with panel
    private transient [CtTypeReferenceImpl]double turnoutBX = [CtFieldReadImpl]LayoutTurnout.turnoutBXDefault;[CtCommentImpl]// RH, LH, WYE


    [CtFieldImpl]private transient [CtTypeReferenceImpl]double turnoutCX = [CtFieldReadImpl]LayoutTurnout.turnoutCXDefault;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]double turnoutWid = [CtFieldReadImpl]LayoutTurnout.turnoutWidDefault;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]double xOverLong = [CtFieldReadImpl]LayoutTurnout.xOverLongDefault;[CtCommentImpl]// DOUBLE_XOVER, RH_XOVER, LH_XOVER


    [CtFieldImpl]private transient [CtTypeReferenceImpl]double xOverHWid = [CtFieldReadImpl]LayoutTurnout.xOverHWidDefault;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]double xOverShort = [CtFieldReadImpl]LayoutTurnout.xOverShortDefault;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean useDirectTurnoutControl = [CtLiteralImpl]false;[CtCommentImpl]// Uses Left click for closing points, Right click for throwing.


    [CtFieldImpl][CtCommentImpl]// saved state of options when panel was loaded or created
    private transient [CtTypeReferenceImpl]boolean savedEditMode = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean savedPositionable = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean savedControlLayout = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean savedAnimatingLayout = [CtLiteralImpl]true;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean savedShowHelpBar = [CtLiteralImpl]true;

    [CtFieldImpl][CtCommentImpl]// zoom
    private transient [CtTypeReferenceImpl]double minZoom = [CtLiteralImpl]0.25;

    [CtFieldImpl]private final transient [CtTypeReferenceImpl]double maxZoom = [CtLiteralImpl]8.0;

    [CtFieldImpl][CtCommentImpl]// A hash to store string -> KeyEvent constants, used to set keyboard shortcuts per locale
    protected transient [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> stringsToVTCodes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtEnumImpl]protected enum ToolBarSide {

        [CtEnumValueImpl]eTOP([CtLiteralImpl]"top"),
        [CtEnumValueImpl]eLEFT([CtLiteralImpl]"left"),
        [CtEnumValueImpl]eBOTTOM([CtLiteralImpl]"bottom"),
        [CtEnumValueImpl]eRIGHT([CtLiteralImpl]"right"),
        [CtEnumValueImpl]eFLOAT([CtLiteralImpl]"float");
        [CtFieldImpl]private final transient [CtTypeReferenceImpl]java.lang.String name;

        [CtFieldImpl]private static final transient [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide> ENUM_MAP;

        [CtConstructorImpl]ToolBarSide([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        }

        [CtAnonymousExecutableImpl][CtCommentImpl]// Build an immutable map of String name to enum pairs.
        static [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide> map = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide instance : [CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.values()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]map.put([CtInvocationImpl][CtVariableReadImpl]instance.getName(), [CtVariableReadImpl]instance);
            }
            [CtAssignmentImpl][CtFieldWriteImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.ENUM_MAP = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtVariableReadImpl]map);
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide getName([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
        [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.ENUM_MAP.get([CtVariableReadImpl]name);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide toolBarSide = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eTOP;

    [CtConstructorImpl]public LayoutEditor() [CtBlockImpl]{
        [CtInvocationImpl]this([CtLiteralImpl]"My Layout");
    }

    [CtConstructorImpl]public LayoutEditor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]name);
        [CtInvocationImpl]setSaveSize([CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]layoutName = [CtVariableReadImpl]name;
        [CtAssignmentImpl][CtFieldWriteImpl]editorUseOldLocSize = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]apps.gui.GuiLafPreferencesManager.class).isEditorUseOldLocSize();
        [CtInvocationImpl][CtCommentImpl]// initialise keycode map
        initStringsToVTCodes();
        [CtInvocationImpl]setupToolBar();
        [CtInvocationImpl]setupMenuBar();
        [CtInvocationImpl][CtSuperAccessImpl]super.setDefaultToolTip([CtConstructorCallImpl]new [CtTypeReferenceImpl]ToolTip([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Font([CtLiteralImpl]"SansSerif", [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Font.[CtFieldReferenceImpl]PLAIN, [CtLiteralImpl]12), [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]black, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Color([CtLiteralImpl]215, [CtLiteralImpl]225, [CtLiteralImpl]255), [CtFieldReadImpl][CtTypeAccessImpl]java.awt.Color.[CtFieldReferenceImpl]black));
        [CtInvocationImpl][CtCommentImpl]// setup help bar
        [CtFieldReadImpl]helpBar.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.BoxLayout([CtFieldReadImpl]helpBar, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]PAGE_AXIS));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTextArea helpTextArea1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextArea([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Help1"));
        [CtInvocationImpl][CtFieldReadImpl]helpBar.add([CtVariableReadImpl]helpTextArea1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTextArea helpTextArea2 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextArea([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Help2"));
        [CtInvocationImpl][CtFieldReadImpl]helpBar.add([CtVariableReadImpl]helpTextArea2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String helpText3 = [CtLiteralImpl]"";
        [CtSwitchImpl]switch ([CtInvocationImpl][CtTypeAccessImpl]SystemType.getType()) {
            [CtCaseImpl]case [CtFieldReadImpl]SystemType.MACOSX :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]helpText3 = [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Help3Mac");
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case [CtFieldReadImpl]SystemType.WINDOWS :
            [CtCaseImpl]case [CtFieldReadImpl]SystemType.LINUX :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]helpText3 = [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Help3Win");
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtAssignmentImpl][CtVariableWriteImpl]helpText3 = [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Help3");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTextArea helpTextArea3 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JTextArea([CtVariableReadImpl]helpText3);
        [CtInvocationImpl][CtFieldReadImpl]helpBar.add([CtVariableReadImpl]helpTextArea3);
        [CtLocalVariableImpl][CtCommentImpl]// set to full screen
        [CtTypeReferenceImpl]java.awt.Dimension screenDim = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        [CtAssignmentImpl][CtFieldWriteImpl]windowWidth = [CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]screenDim.width - [CtLiteralImpl]20;
        [CtAssignmentImpl][CtFieldWriteImpl]windowHeight = [CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]screenDim.height - [CtLiteralImpl]120;
        [CtInvocationImpl][CtCommentImpl]// Let Editor make target, and use this frame
        [CtSuperAccessImpl]super.setTargetPanel([CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl][CtSuperAccessImpl]super.setTargetPanelSize([CtFieldReadImpl]windowWidth, [CtFieldReadImpl]windowHeight);
        [CtInvocationImpl]setSize([CtFieldReadImpl][CtVariableReadImpl]screenDim.width, [CtFieldReadImpl][CtVariableReadImpl]screenDim.height);
        [CtLocalVariableImpl][CtCommentImpl]// register the resulting panel for later configuration
        [CtTypeReferenceImpl]ConfigureManager cm = [CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getNullableDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.ConfigureManager.class);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cm != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]cm.registerUser([CtThisAccessImpl]this);
        }
        [CtIfImpl][CtCommentImpl]// confirm that panel hasn't already been loaded
        if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.PanelMenu.class).isPanelNameUsed([CtVariableReadImpl]name)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtLiteralImpl]"File contains a panel with the same name ({}) as an existing panel", [CtVariableReadImpl]name);
        }
        [CtInvocationImpl]setFocusable([CtLiteralImpl]true);
        [CtInvocationImpl]addKeyListener([CtThisAccessImpl]this);
        [CtInvocationImpl]resetDirty();
        [CtAssignmentImpl][CtCommentImpl]// establish link to LayoutEditor Tools
        [CtFieldWriteImpl]auxTools = [CtInvocationImpl]getLEAuxTools();
        [CtInvocationImpl][CtTypeAccessImpl]javax.swing.SwingUtilities.invokeLater([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// initialize preferences
            [CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String windowFrameRef = [CtInvocationImpl]getWindowFrameRef();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object prefsProp = [CtInvocationImpl][CtVariableReadImpl]prefsMgr.getProperty([CtVariableReadImpl]windowFrameRef, [CtLiteralImpl]"toolBarSide");
                [CtIfImpl][CtCommentImpl]// log.debug("{}.toolBarSide is {}", windowFrameRef, prefsProp);
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]prefsProp != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]ToolBarSide newToolBarSide = [CtInvocationImpl][CtTypeAccessImpl]ToolBarSide.getName([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (prefsProp)));
                    [CtInvocationImpl]setToolBarSide([CtVariableReadImpl]newToolBarSide);
                }
                [CtLocalVariableImpl][CtCommentImpl]// Note: since prefs default to false and we want wide to be the default
                [CtCommentImpl]// we invert it and save it as thin
                [CtTypeReferenceImpl]boolean prefsToolBarIsWide = [CtInvocationImpl][CtVariableReadImpl]prefsMgr.getSimplePreferenceState([CtBinaryOperatorImpl][CtVariableReadImpl]windowFrameRef + [CtLiteralImpl]".toolBarThin");
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtLiteralImpl]"{}.toolBarThin is {}", [CtVariableReadImpl]windowFrameRef, [CtVariableReadImpl]prefsProp);
                [CtInvocationImpl]setToolBarWide([CtVariableReadImpl]prefsToolBarIsWide);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean prefsShowHelpBar = [CtInvocationImpl][CtVariableReadImpl]prefsMgr.getSimplePreferenceState([CtBinaryOperatorImpl][CtVariableReadImpl]windowFrameRef + [CtLiteralImpl]".showHelpBar");
                [CtInvocationImpl][CtCommentImpl]// log.debug("{}.showHelpBar is {}", windowFrameRef, prefsShowHelpBar);
                setShowHelpBar([CtVariableReadImpl]prefsShowHelpBar);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean prefsAntialiasingOn = [CtInvocationImpl][CtVariableReadImpl]prefsMgr.getSimplePreferenceState([CtBinaryOperatorImpl][CtVariableReadImpl]windowFrameRef + [CtLiteralImpl]".antialiasingOn");
                [CtInvocationImpl][CtCommentImpl]// log.debug("{}.antialiasingOn is {}", windowFrameRef, prefsAntialiasingOn);
                setAntialiasingOn([CtVariableReadImpl]prefsAntialiasingOn);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean prefsHighlightSelectedBlockFlag = [CtInvocationImpl][CtVariableReadImpl]prefsMgr.getSimplePreferenceState([CtBinaryOperatorImpl][CtVariableReadImpl]windowFrameRef + [CtLiteralImpl]".highlightSelectedBlock");
                [CtInvocationImpl][CtCommentImpl]// log.debug("{}.highlightSelectedBlock is {}", windowFrameRef, prefsHighlightSelectedBlockFlag);
                setHighlightSelectedBlock([CtVariableReadImpl]prefsHighlightSelectedBlockFlag);
            });[CtCommentImpl]// InstanceManager.getOptionalDefault(UserPreferencesManager.class).ifPresent((prefsMgr)

            [CtLocalVariableImpl][CtCommentImpl]// make sure that the layoutEditorComponent is in the _targetPanel components
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.awt.Component> componentList = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtFieldReadImpl]_targetPanel.getComponents());
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]componentList.contains([CtFieldReadImpl]layoutEditorComponent)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]_targetPanel.remove([CtFieldReadImpl]layoutEditorComponent);
                    [CtInvocationImpl][CtFieldReadImpl]_targetPanel.add([CtFieldReadImpl]layoutEditorComponent, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.valueOf([CtLiteralImpl]3));
                    [CtInvocationImpl][CtFieldReadImpl]_targetPanel.moveToFront([CtFieldReadImpl]layoutEditorComponent);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtLiteralImpl]"paintTargetPanelBefore: Exception {}", [CtVariableReadImpl]e);
                }
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setupMenuBar() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// initialize menu bar
        [CtTypeReferenceImpl]javax.swing.JMenuBar menuBar = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuBar();
        [CtLocalVariableImpl][CtCommentImpl]// set up File menu
        [CtTypeReferenceImpl]javax.swing.JMenu fileMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuFile"));
        [CtInvocationImpl][CtVariableReadImpl]fileMenu.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuFileMnemonic")));
        [CtInvocationImpl][CtVariableReadImpl]menuBar.add([CtVariableReadImpl]fileMenu);
        [CtLocalVariableImpl][CtTypeReferenceImpl]jmri.configurexml.StoreXmlUserAction store = [CtConstructorCallImpl]new [CtTypeReferenceImpl]jmri.configurexml.StoreXmlUserAction([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuItemStore"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int primary_modifier = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        [CtInvocationImpl][CtVariableReadImpl]store.putValue([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.Action.[CtFieldReferenceImpl]ACCELERATOR_KEY, [CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuItemStoreAccelerator")), [CtVariableReadImpl]primary_modifier));
        [CtInvocationImpl][CtVariableReadImpl]fileMenu.add([CtVariableReadImpl]store);
        [CtInvocationImpl][CtVariableReadImpl]fileMenu.addSeparator();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem deleteItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeletePanel"));
        [CtInvocationImpl][CtVariableReadImpl]fileMenu.add([CtVariableReadImpl]deleteItem);
        [CtInvocationImpl][CtVariableReadImpl]deleteItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]deletePanel()) [CtBlockImpl]{
                [CtInvocationImpl]dispose();
            }
        });
        [CtInvocationImpl]setJMenuBar([CtVariableReadImpl]menuBar);
        [CtInvocationImpl][CtCommentImpl]// setup Options menu
        setupOptionMenu([CtVariableReadImpl]menuBar);
        [CtInvocationImpl][CtCommentImpl]// setup Tools menu
        setupToolsMenu([CtVariableReadImpl]menuBar);
        [CtInvocationImpl][CtCommentImpl]// setup Zoom menu
        setupZoomMenu([CtVariableReadImpl]menuBar);
        [CtInvocationImpl][CtCommentImpl]// setup marker menu
        setupMarkerMenu([CtVariableReadImpl]menuBar);
        [CtInvocationImpl][CtCommentImpl]// Setup Dispatcher window
        setupDispatcherMenu([CtVariableReadImpl]menuBar);
        [CtInvocationImpl][CtCommentImpl]// setup Help menu
        addHelpMenu([CtLiteralImpl]"package.jmri.jmrit.display.LayoutEditor", [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void newPanelDefaults() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getLayoutTrackDrawingOptions().setMainRailWidth([CtLiteralImpl]2);
        [CtInvocationImpl][CtInvocationImpl]getLayoutTrackDrawingOptions().setSideRailWidth([CtLiteralImpl]1);
        [CtInvocationImpl]setBackgroundColor([CtFieldReadImpl]defaultBackgroundColor);
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtFieldReadImpl]defaultTrackColor);
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtFieldReadImpl]defaultOccupiedTrackColor);
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtFieldReadImpl]defaultAlternativeTrackColor);
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtFieldReadImpl]defaultBackgroundColor);
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtFieldReadImpl]defaultTextColor);
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorComponent layoutEditorComponent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorComponent([CtThisAccessImpl]this);

    [CtMethodImpl]private [CtTypeReferenceImpl]void setupToolBar() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Initial setup for both horizontal and vertical
        [CtTypeReferenceImpl]java.awt.Container contentPane = [CtInvocationImpl]getContentPane();
        [CtIfImpl][CtCommentImpl]// remove these (if present) so we can add them back (without duplicates)
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]editToolBarContainerPanel != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.setVisible([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]contentPane.remove([CtFieldReadImpl]editToolBarContainerPanel);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]helpBarPanel != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentPane.remove([CtFieldReadImpl]helpBarPanel);
        }
        [CtInvocationImpl]deletefloatingEditToolBoxFrame();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT)) [CtBlockImpl]{
            [CtInvocationImpl]createfloatingEditToolBoxFrame();
            [CtInvocationImpl]createFloatingHelpPanel();
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Dimension screenDim = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean toolBarIsVertical = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eRIGHT) || [CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eLEFT);
        [CtIfImpl]if ([CtVariableReadImpl]toolBarIsVertical) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]leToolBarPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorVerticalToolBarPanel([CtThisAccessImpl]this);
            [CtAssignmentImpl][CtFieldWriteImpl]editToolBarScrollPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtFieldReadImpl]leToolBarPanel);
            [CtAssignmentImpl][CtFieldWriteImpl]toolbarWidth = [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]editToolBarScrollPane.getPreferredSize().width;
            [CtAssignmentImpl][CtFieldWriteImpl]toolbarHeight = [CtFieldReadImpl][CtVariableReadImpl]screenDim.height;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]leToolBarPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorHorizontalToolBarPanel([CtThisAccessImpl]this);
            [CtAssignmentImpl][CtFieldWriteImpl]editToolBarScrollPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtFieldReadImpl]leToolBarPanel);
            [CtAssignmentImpl][CtFieldWriteImpl]toolbarWidth = [CtFieldReadImpl][CtVariableReadImpl]screenDim.width;
            [CtAssignmentImpl][CtFieldWriteImpl]toolbarHeight = [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]editToolBarScrollPane.getPreferredSize().height;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]editToolBarContainerPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.BoxLayout([CtFieldReadImpl]editToolBarContainerPanel, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]PAGE_AXIS));
        [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.add([CtFieldReadImpl]editToolBarScrollPane);
        [CtInvocationImpl][CtCommentImpl]// setup notification for when horizontal scrollbar changes visibility
        [CtCommentImpl]// editToolBarScroll.getViewport().addChangeListener(e -> {
        [CtCommentImpl]// log.warn("scrollbars visible: " + editToolBarScroll.getHorizontalScrollBar().isVisible());
        [CtCommentImpl]// });
        [CtFieldReadImpl]editToolBarContainerPanel.setMinimumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtFieldReadImpl]toolbarWidth, [CtFieldReadImpl]toolbarHeight));
        [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.setPreferredSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtFieldReadImpl]toolbarWidth, [CtFieldReadImpl]toolbarHeight));
        [CtAssignmentImpl][CtFieldWriteImpl]helpBarPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
        [CtInvocationImpl][CtFieldReadImpl]helpBarPanel.add([CtFieldReadImpl]helpBar);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Component c : [CtInvocationImpl][CtFieldReadImpl]helpBar.getComponents()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.swing.JTextArea) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTextArea j = [CtVariableReadImpl](([CtTypeReferenceImpl]javax.swing.JTextArea) (c));
                [CtInvocationImpl][CtVariableReadImpl]j.setSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtFieldReadImpl]toolbarWidth, [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]j.getSize().height));
                [CtInvocationImpl][CtVariableReadImpl]j.setLineWrap([CtVariableReadImpl]toolBarIsVertical);
                [CtInvocationImpl][CtVariableReadImpl]j.setWrapStyleWord([CtVariableReadImpl]toolBarIsVertical);
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]contentPane.setLayout([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.BoxLayout([CtVariableReadImpl]contentPane, [CtConditionalImpl][CtVariableReadImpl]toolBarIsVertical ? [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]LINE_AXIS : [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.BoxLayout.[CtFieldReferenceImpl]PAGE_AXIS));
        [CtSwitchImpl]switch ([CtFieldReadImpl]toolBarSide) {
            [CtCaseImpl]case eTOP :
            [CtCaseImpl]case eLEFT :
                [CtInvocationImpl][CtVariableReadImpl]contentPane.add([CtFieldReadImpl]editToolBarContainerPanel, [CtLiteralImpl]0);
                [CtBreakImpl]break;
            [CtCaseImpl]case eBOTTOM :
            [CtCaseImpl]case eRIGHT :
                [CtInvocationImpl][CtVariableReadImpl]contentPane.add([CtFieldReadImpl]editToolBarContainerPanel);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtBreakImpl][CtCommentImpl]// fall through
                break;
        }
        [CtIfImpl]if ([CtVariableReadImpl]toolBarIsVertical) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.add([CtFieldReadImpl]helpBarPanel);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentPane.add([CtFieldReadImpl]helpBarPanel);
        }
        [CtInvocationImpl][CtFieldReadImpl]helpBarPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtInvocationImpl]getShowHelpBar());
        [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.setVisible([CtInvocationImpl]isEditable());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createfloatingEditToolBoxFrame() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtBinaryOperatorImpl]([CtFieldReadImpl]floatingEditToolBoxFrame == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// Create a scroll pane to hold the window content.
            [CtFieldWriteImpl]leToolBarPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorFloatingToolBarPanel([CtThisAccessImpl]this);
            [CtAssignmentImpl][CtFieldWriteImpl]floatingEditContentScrollPane = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JScrollPane([CtFieldReadImpl]leToolBarPanel);
            [CtInvocationImpl][CtFieldReadImpl]floatingEditContentScrollPane.setHorizontalScrollBarPolicy([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JScrollPane.[CtFieldReferenceImpl]HORIZONTAL_SCROLLBAR_NEVER);
            [CtInvocationImpl][CtFieldReadImpl]floatingEditContentScrollPane.setVerticalScrollBarPolicy([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JScrollPane.[CtFieldReferenceImpl]VERTICAL_SCROLLBAR_AS_NEEDED);
            [CtAssignmentImpl][CtCommentImpl]// Create the window and add the toolbox content
            [CtFieldWriteImpl]floatingEditToolBoxFrame = [CtConstructorCallImpl]new [CtTypeReferenceImpl]JmriJFrame([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBox", [CtInvocationImpl]getLayoutName()));
            [CtInvocationImpl][CtFieldReadImpl]floatingEditToolBoxFrame.setDefaultCloseOperation([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JFrame.[CtFieldReferenceImpl]HIDE_ON_CLOSE);
            [CtInvocationImpl][CtFieldReadImpl]floatingEditToolBoxFrame.setContentPane([CtFieldReadImpl]floatingEditContentScrollPane);
            [CtInvocationImpl][CtFieldReadImpl]floatingEditToolBoxFrame.pack();
            [CtInvocationImpl][CtFieldReadImpl]floatingEditToolBoxFrame.setAlwaysOnTop([CtLiteralImpl]true);
            [CtInvocationImpl][CtFieldReadImpl]floatingEditToolBoxFrame.setVisible([CtLiteralImpl]true);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void deletefloatingEditToolBoxFrame() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]floatingEditContentScrollPane != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]floatingEditContentScrollPane.removeAll();
            [CtAssignmentImpl][CtFieldWriteImpl]floatingEditContentScrollPane = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]floatingEditToolBoxFrame != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]floatingEditToolBoxFrame.dispose();
            [CtAssignmentImpl][CtFieldWriteImpl]floatingEditToolBoxFrame = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createFloatingHelpPanel() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]leToolBarPanel instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutEditorFloatingToolBarPanel) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutEditorFloatingToolBarPanel leftbp = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutEditorFloatingToolBarPanel) (leToolBarPanel));
            [CtAssignmentImpl][CtFieldWriteImpl]floatEditHelpPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPanel();
            [CtInvocationImpl][CtFieldReadImpl]leToolBarPanel.add([CtFieldReadImpl]floatEditHelpPanel);
            [CtLocalVariableImpl][CtCommentImpl]// Notice: End tree structure indenting
            [CtCommentImpl]// Force the help panel width to the same as the tabs section
            [CtTypeReferenceImpl]int tabSectionWidth = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtVariableReadImpl]leftbp.getPreferredSize().getWidth()));
            [CtForEachImpl][CtCommentImpl]// Change the textarea settings
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Component c : [CtInvocationImpl][CtFieldReadImpl]helpBar.getComponents()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.swing.JTextArea) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JTextArea j = [CtVariableReadImpl](([CtTypeReferenceImpl]javax.swing.JTextArea) (c));
                    [CtInvocationImpl][CtVariableReadImpl]j.setSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtVariableReadImpl]tabSectionWidth, [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]j.getSize().height));
                    [CtInvocationImpl][CtVariableReadImpl]j.setLineWrap([CtLiteralImpl]true);
                    [CtInvocationImpl][CtVariableReadImpl]j.setWrapStyleWord([CtLiteralImpl]true);
                }
            }
            [CtInvocationImpl][CtCommentImpl]// Change the width of the help panel section
            [CtFieldReadImpl]floatEditHelpPanel.setMaximumSize([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Dimension([CtVariableReadImpl]tabSectionWidth, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE));
            [CtInvocationImpl][CtFieldReadImpl]floatEditHelpPanel.add([CtFieldReadImpl]helpBar);
            [CtInvocationImpl][CtFieldReadImpl]floatEditHelpPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtInvocationImpl]getShowHelpBar());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void init([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void initView() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]editModeCheckBoxMenuItem.setSelected([CtInvocationImpl]isEditable());
        [CtInvocationImpl][CtFieldReadImpl]positionableCheckBoxMenuItem.setSelected([CtInvocationImpl]allPositionable());
        [CtInvocationImpl][CtFieldReadImpl]controlCheckBoxMenuItem.setSelected([CtInvocationImpl]allControlling());
        [CtIfImpl]if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtInvocationImpl]setAllShowToolTip([CtFieldReadImpl]tooltipsInEditMode);
        } else [CtBlockImpl]{
            [CtInvocationImpl]setAllShowToolTip([CtFieldReadImpl]tooltipsWithoutEditMode);
        }
        [CtInvocationImpl][CtFieldReadImpl]scrollNoneMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_NONE);
        [CtInvocationImpl][CtFieldReadImpl]scrollBothMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_BOTH);
        [CtInvocationImpl][CtFieldReadImpl]scrollHorizontalMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_HORIZONTAL);
        [CtInvocationImpl][CtFieldReadImpl]scrollVerticalMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_VERTICAL);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setSize([CtParameterImpl][CtTypeReferenceImpl]int w, [CtParameterImpl][CtTypeReferenceImpl]int h) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.setSize([CtVariableReadImpl]w, [CtVariableReadImpl]h);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void targetWindowClosingEvent([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.WindowEvent e) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean save = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]isDirty() || [CtBinaryOperatorImpl]([CtFieldReadImpl]savedEditMode != [CtInvocationImpl]isEditable())) || [CtBinaryOperatorImpl]([CtFieldReadImpl]savedPositionable != [CtInvocationImpl]allPositionable())) || [CtBinaryOperatorImpl]([CtFieldReadImpl]savedControlLayout != [CtInvocationImpl]allControlling())) || [CtBinaryOperatorImpl]([CtFieldReadImpl]savedAnimatingLayout != [CtInvocationImpl]isAnimating())) || [CtBinaryOperatorImpl]([CtFieldReadImpl]savedShowHelpBar != [CtInvocationImpl]getShowHelpBar());
        [CtInvocationImpl]targetWindowClosing([CtVariableReadImpl]save);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set up NamedBeanComboBox
     *
     * @param inComboBox
     * 		the NamedBeanComboBox to set up
     * @param inValidateMode
     * 		true to validate typed inputs; false otherwise
     * @param inEnable
     * 		boolean to enable / disable the NamedBeanComboBox
     * @param inEditable
     * 		boolean to make the NamedBeanComboBox editable
     */
    public static [CtTypeReferenceImpl]void setupComboBox([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]jmri.swing.NamedBeanComboBox<[CtWildcardReferenceImpl]?> inComboBox, [CtParameterImpl][CtTypeReferenceImpl]boolean inValidateMode, [CtParameterImpl][CtTypeReferenceImpl]boolean inEnable, [CtParameterImpl][CtTypeReferenceImpl]boolean inEditable) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"LE setupComboBox called");
        [CtInvocationImpl][CtVariableReadImpl]inComboBox.setEnabled([CtVariableReadImpl]inEnable);
        [CtInvocationImpl][CtVariableReadImpl]inComboBox.setEditable([CtVariableReadImpl]inEditable);
        [CtInvocationImpl][CtVariableReadImpl]inComboBox.setValidatingInput([CtVariableReadImpl]inValidateMode);
        [CtInvocationImpl][CtVariableReadImpl]inComboBox.setSelectedIndex([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        [CtInvocationImpl][CtCommentImpl]// This has to be set before calling setupComboBoxMaxRows
        [CtCommentImpl]// (otherwise if inFirstBlank then the  number of rows will be wrong)
        [CtVariableReadImpl]inComboBox.setAllowNull([CtUnaryOperatorImpl]![CtVariableReadImpl]inValidateMode);
        [CtInvocationImpl][CtCommentImpl]// set the max number of rows that will fit onscreen
        [CtTypeAccessImpl]JComboBoxUtil.setupComboBoxMaxRows([CtVariableReadImpl]inComboBox);
        [CtInvocationImpl][CtVariableReadImpl]inComboBox.setSelectedIndex([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Grabs a subset of the possible KeyEvent constants and puts them into a
     * hash for fast lookups later. These lookups are used to enable bundles to
     * specify keyboard shortcuts on a per-locale basis.
     */
    private [CtTypeReferenceImpl]void initStringsToVTCodes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Field[] fields = [CtInvocationImpl][CtFieldReadImpl]java.awt.event.KeyEvent.class.getFields();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field : [CtVariableReadImpl]fields) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtVariableReadImpl]field.getName();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]name.startsWith([CtLiteralImpl]"VK")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int code = [CtLiteralImpl]0;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]code = [CtInvocationImpl][CtVariableReadImpl]field.getInt([CtLiteralImpl]null);
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                    [CtCommentImpl]// exceptions make me throw up...
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtInvocationImpl][CtVariableReadImpl]name.substring([CtLiteralImpl]3);
                [CtInvocationImpl][CtCommentImpl]// log.debug("VTCode[{}]:'{}'", key, code);
                [CtFieldReadImpl]stringsToVTCodes.put([CtVariableReadImpl]key, [CtVariableReadImpl]code);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set up the Option menu.
     *
     * @param menuBar
     * 		to add the option menu to
     * @return option menu that was added
     */
    protected [CtTypeReferenceImpl]javax.swing.JMenu setupOptionMenu([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenuBar menuBar) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenu optionMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuOptions"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"OptionsMnemonic")));
        [CtInvocationImpl][CtVariableReadImpl]menuBar.add([CtVariableReadImpl]optionMenu);
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// edit mode
        [CtCommentImpl]// 
        [CtFieldWriteImpl]editModeCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EditMode"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]editModeCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]editModeCheckBoxMenuItem.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EditModeMnemonic")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int primary_modifier = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        [CtInvocationImpl][CtFieldReadImpl]editModeCheckBoxMenuItem.setAccelerator([CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EditModeAccelerator")), [CtVariableReadImpl]primary_modifier));
        [CtInvocationImpl][CtFieldReadImpl]editModeCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl]setAllEditable([CtInvocationImpl][CtFieldReadImpl]editModeCheckBoxMenuItem.isSelected());
            [CtIfImpl][CtCommentImpl]// show/hide the help bar
            if ([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]floatEditHelpPanel != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]floatEditHelpPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtInvocationImpl]getShowHelpBar());
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]helpBarPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtInvocationImpl]getShowHelpBar());
            }
            [CtIfImpl]if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
                [CtInvocationImpl]setAllShowToolTip([CtFieldReadImpl]tooltipsInEditMode);
                [CtIfImpl][CtCommentImpl]// redo using the "Extra" color to highlight the selected block
                if ([CtFieldReadImpl]highlightSelectedBlockFlag) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]highlightBlockInComboBox([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox)) [CtBlockImpl]{
                        [CtInvocationImpl]highlightBlockInComboBox([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockContentsComboBox);
                    }
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl]setAllShowToolTip([CtFieldReadImpl]tooltipsWithoutEditMode);
                [CtIfImpl][CtCommentImpl]// undo using the "Extra" color to highlight the selected block
                if ([CtFieldReadImpl]highlightSelectedBlockFlag) [CtBlockImpl]{
                    [CtInvocationImpl]highlightBlock([CtLiteralImpl]null);
                }
            }
            [CtAssignmentImpl][CtFieldWriteImpl]awaitingIconChange = [CtLiteralImpl]false;
        });
        [CtInvocationImpl][CtFieldReadImpl]editModeCheckBoxMenuItem.setSelected([CtInvocationImpl]isEditable());
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// toolbar
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]javax.swing.JMenu toolBarMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBar"));[CtCommentImpl]// used for ToolBar SubMenu

        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]toolBarMenu);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenu toolBarSideMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBarSide"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.ButtonGroup toolBarSideGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ButtonGroup();
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// create toolbar side menu items: (top, left, bottom, right)
        [CtCommentImpl]// 
        [CtFieldWriteImpl]toolBarSideTopButton = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBarSideTop"));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideTopButton.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setToolBarSide([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eTOP));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideTopButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eTOP));
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideMenu.add([CtFieldReadImpl]toolBarSideTopButton);
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideGroup.add([CtFieldReadImpl]toolBarSideTopButton);
        [CtAssignmentImpl][CtFieldWriteImpl]toolBarSideLeftButton = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBarSideLeft"));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideLeftButton.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setToolBarSide([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eLEFT));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideLeftButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eLEFT));
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideMenu.add([CtFieldReadImpl]toolBarSideLeftButton);
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideGroup.add([CtFieldReadImpl]toolBarSideLeftButton);
        [CtAssignmentImpl][CtFieldWriteImpl]toolBarSideBottomButton = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBarSideBottom"));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideBottomButton.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setToolBarSide([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eBOTTOM));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideBottomButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eBOTTOM));
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideMenu.add([CtFieldReadImpl]toolBarSideBottomButton);
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideGroup.add([CtFieldReadImpl]toolBarSideBottomButton);
        [CtAssignmentImpl][CtFieldWriteImpl]toolBarSideRightButton = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBarSideRight"));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideRightButton.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setToolBarSide([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eRIGHT));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideRightButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eRIGHT));
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideMenu.add([CtFieldReadImpl]toolBarSideRightButton);
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideGroup.add([CtFieldReadImpl]toolBarSideRightButton);
        [CtAssignmentImpl][CtFieldWriteImpl]toolBarSideFloatButton = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ToolBarSideFloat"));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideFloatButton.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setToolBarSide([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT));
        [CtInvocationImpl][CtFieldReadImpl]toolBarSideFloatButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT));
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideMenu.add([CtFieldReadImpl]toolBarSideFloatButton);
        [CtInvocationImpl][CtVariableReadImpl]toolBarSideGroup.add([CtFieldReadImpl]toolBarSideFloatButton);
        [CtInvocationImpl][CtVariableReadImpl]toolBarMenu.add([CtVariableReadImpl]toolBarSideMenu);
        [CtInvocationImpl][CtCommentImpl]// 
        [CtCommentImpl]// toolbar wide menu
        [CtCommentImpl]// 
        [CtVariableReadImpl]toolBarMenu.add([CtFieldReadImpl]wideToolBarCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]wideToolBarCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setToolBarWide([CtInvocationImpl][CtFieldReadImpl]wideToolBarCheckBoxMenuItem.isSelected()));
        [CtInvocationImpl][CtFieldReadImpl]wideToolBarCheckBoxMenuItem.setSelected([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.toolBarIsWide);
        [CtInvocationImpl][CtFieldReadImpl]wideToolBarCheckBoxMenuItem.setEnabled([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eTOP) || [CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eBOTTOM));
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// Scroll Bars
        [CtCommentImpl]// 
        [CtFieldWriteImpl]scrollMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ComboBoxScrollable"));[CtCommentImpl]// used for ScrollBarsSubMenu

        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]scrollMenu);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.ButtonGroup scrollGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ButtonGroup();
        [CtAssignmentImpl][CtFieldWriteImpl]scrollBothMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ScrollBoth"));
        [CtInvocationImpl][CtVariableReadImpl]scrollGroup.add([CtFieldReadImpl]scrollBothMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollMenu.add([CtFieldReadImpl]scrollBothMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollBothMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_BOTH);
        [CtInvocationImpl][CtFieldReadImpl]scrollBothMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_scrollState = [CtFieldReadImpl]Editor.SCROLL_BOTH;
            [CtInvocationImpl]setScroll([CtFieldReadImpl]_scrollState);
            [CtInvocationImpl]redrawPanel();
        });
        [CtAssignmentImpl][CtFieldWriteImpl]scrollNoneMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ScrollNone"));
        [CtInvocationImpl][CtVariableReadImpl]scrollGroup.add([CtFieldReadImpl]scrollNoneMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollMenu.add([CtFieldReadImpl]scrollNoneMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollNoneMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_NONE);
        [CtInvocationImpl][CtFieldReadImpl]scrollNoneMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_scrollState = [CtFieldReadImpl]Editor.SCROLL_NONE;
            [CtInvocationImpl]setScroll([CtFieldReadImpl]_scrollState);
            [CtInvocationImpl]redrawPanel();
        });
        [CtAssignmentImpl][CtFieldWriteImpl]scrollHorizontalMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ScrollHorizontal"));
        [CtInvocationImpl][CtVariableReadImpl]scrollGroup.add([CtFieldReadImpl]scrollHorizontalMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollMenu.add([CtFieldReadImpl]scrollHorizontalMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollHorizontalMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_HORIZONTAL);
        [CtInvocationImpl][CtFieldReadImpl]scrollHorizontalMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_scrollState = [CtFieldReadImpl]Editor.SCROLL_HORIZONTAL;
            [CtInvocationImpl]setScroll([CtFieldReadImpl]_scrollState);
            [CtInvocationImpl]redrawPanel();
        });
        [CtAssignmentImpl][CtFieldWriteImpl]scrollVerticalMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ScrollVertical"));
        [CtInvocationImpl][CtVariableReadImpl]scrollGroup.add([CtFieldReadImpl]scrollVerticalMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollMenu.add([CtFieldReadImpl]scrollVerticalMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]scrollVerticalMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState == [CtFieldReadImpl]Editor.SCROLL_VERTICAL);
        [CtInvocationImpl][CtFieldReadImpl]scrollVerticalMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_scrollState = [CtFieldReadImpl]Editor.SCROLL_VERTICAL;
            [CtInvocationImpl]setScroll([CtFieldReadImpl]_scrollState);
            [CtInvocationImpl]redrawPanel();
        });
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// Tooltips
        [CtCommentImpl]// 
        [CtFieldWriteImpl]tooltipMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TooltipSubMenu"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]tooltipMenu);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.ButtonGroup tooltipGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ButtonGroup();
        [CtAssignmentImpl][CtFieldWriteImpl]tooltipNoneMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TooltipNone"));
        [CtInvocationImpl][CtVariableReadImpl]tooltipGroup.add([CtFieldReadImpl]tooltipNoneMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipMenu.add([CtFieldReadImpl]tooltipNoneMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipNoneMenuItem.setSelected([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsInEditMode) && [CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsWithoutEditMode));
        [CtInvocationImpl][CtFieldReadImpl]tooltipNoneMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsInEditMode = [CtLiteralImpl]false;
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsWithoutEditMode = [CtLiteralImpl]false;
            [CtInvocationImpl]setAllShowToolTip([CtLiteralImpl]false);
        });
        [CtAssignmentImpl][CtFieldWriteImpl]tooltipAlwaysMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TooltipAlways"));
        [CtInvocationImpl][CtVariableReadImpl]tooltipGroup.add([CtFieldReadImpl]tooltipAlwaysMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipMenu.add([CtFieldReadImpl]tooltipAlwaysMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipAlwaysMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]tooltipsInEditMode && [CtFieldReadImpl]tooltipsWithoutEditMode);
        [CtInvocationImpl][CtFieldReadImpl]tooltipAlwaysMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsInEditMode = [CtLiteralImpl]true;
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsWithoutEditMode = [CtLiteralImpl]true;
            [CtInvocationImpl]setAllShowToolTip([CtLiteralImpl]true);
        });
        [CtAssignmentImpl][CtFieldWriteImpl]tooltipInEditMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TooltipEdit"));
        [CtInvocationImpl][CtVariableReadImpl]tooltipGroup.add([CtFieldReadImpl]tooltipInEditMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipMenu.add([CtFieldReadImpl]tooltipInEditMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipInEditMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]tooltipsInEditMode && [CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsWithoutEditMode));
        [CtInvocationImpl][CtFieldReadImpl]tooltipInEditMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsInEditMode = [CtLiteralImpl]true;
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsWithoutEditMode = [CtLiteralImpl]false;
            [CtInvocationImpl]setAllShowToolTip([CtInvocationImpl]isEditable());
        });
        [CtAssignmentImpl][CtFieldWriteImpl]tooltipNotInEditMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TooltipNotEdit"));
        [CtInvocationImpl][CtVariableReadImpl]tooltipGroup.add([CtFieldReadImpl]tooltipNotInEditMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipMenu.add([CtFieldReadImpl]tooltipNotInEditMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]tooltipNotInEditMenuItem.setSelected([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsInEditMode) && [CtFieldReadImpl]tooltipsWithoutEditMode);
        [CtInvocationImpl][CtFieldReadImpl]tooltipNotInEditMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsInEditMode = [CtLiteralImpl]false;
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsWithoutEditMode = [CtLiteralImpl]true;
            [CtInvocationImpl]setAllShowToolTip([CtUnaryOperatorImpl]![CtInvocationImpl]isEditable());
        });
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// show edit help
        [CtCommentImpl]// 
        [CtFieldWriteImpl]showHelpCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ShowEditHelp"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]showHelpCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]showHelpCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean newShowHelpBar = [CtInvocationImpl][CtFieldReadImpl]showHelpCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]setShowHelpBar([CtVariableReadImpl]newShowHelpBar);
        });
        [CtInvocationImpl][CtFieldReadImpl]showHelpCheckBoxMenuItem.setSelected([CtInvocationImpl]getShowHelpBar());
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// Allow Repositioning
        [CtCommentImpl]// 
        [CtFieldWriteImpl]positionableCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AllowRepositioning"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]positionableCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]positionableCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setAllPositionable([CtInvocationImpl][CtFieldReadImpl]positionableCheckBoxMenuItem.isSelected()));
        [CtInvocationImpl][CtFieldReadImpl]positionableCheckBoxMenuItem.setSelected([CtInvocationImpl]allPositionable());
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// Allow Layout Control
        [CtCommentImpl]// 
        [CtFieldWriteImpl]controlCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AllowLayoutControl"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]controlCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]controlCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl]setAllControlling([CtInvocationImpl][CtFieldReadImpl]controlCheckBoxMenuItem.isSelected());
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]controlCheckBoxMenuItem.setSelected([CtInvocationImpl]allControlling());
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// use direct turnout control
        [CtCommentImpl]// 
        [CtFieldWriteImpl]useDirectTurnoutControlCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"UseDirectTurnoutControl"));[CtCommentImpl]// NOI18N

        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]useDirectTurnoutControlCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]useDirectTurnoutControlCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setDirectTurnoutControl([CtInvocationImpl][CtFieldReadImpl]useDirectTurnoutControlCheckBoxMenuItem.isSelected()));
        [CtInvocationImpl][CtFieldReadImpl]useDirectTurnoutControlCheckBoxMenuItem.setSelected([CtFieldReadImpl]useDirectTurnoutControl);
        [CtAssignmentImpl][CtCommentImpl]// 
        [CtCommentImpl]// antialiasing
        [CtCommentImpl]// 
        [CtFieldWriteImpl]antialiasingOnCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AntialiasingOn"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtFieldReadImpl]antialiasingOnCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]antialiasingOnCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]antialiasingOn = [CtInvocationImpl][CtFieldReadImpl]antialiasingOnCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]antialiasingOnCheckBoxMenuItem.setSelected([CtFieldReadImpl]antialiasingOn);
        [CtInvocationImpl][CtCommentImpl]// 
        [CtCommentImpl]// edit title
        [CtCommentImpl]// 
        [CtVariableReadImpl]optionMenu.addSeparator();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem titleItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EditTitle") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]titleItem);
        [CtInvocationImpl][CtVariableReadImpl]titleItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// prompt for name
            [CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtTypeAccessImpl]javax.swing.JOptionPane.showInputDialog([CtInvocationImpl]getTargetFrame(), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MakeLabel", [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EnterTitle")), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EditTitleMessageTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]PLAIN_MESSAGE, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtInvocationImpl]getLayoutName())));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]newName.equals([CtInvocationImpl]getLayoutName())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.PanelMenu.class).isPanelNameUsed([CtVariableReadImpl]newName)) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"CanNotRename"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"PanelExist"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
                    } else [CtBlockImpl]{
                        [CtInvocationImpl]setTitle([CtVariableReadImpl]newName);
                        [CtInvocationImpl]setLayoutName([CtVariableReadImpl]newName);
                        [CtInvocationImpl][CtInvocationImpl]getLayoutTrackDrawingOptions().setName([CtVariableReadImpl]newName);
                        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.PanelMenu.class).renameEditorPanel([CtThisAccessImpl]this);
                        [CtInvocationImpl]setDirty();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT) && [CtInvocationImpl]isEditable()) [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// Rebuild the toolbox after a name change.
                            deletefloatingEditToolBoxFrame();
                            [CtInvocationImpl]createfloatingEditToolBoxFrame();
                            [CtInvocationImpl]createFloatingHelpPanel();
                        }
                    }
                }
            }
        });
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// set background color
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]javax.swing.JMenuItem backgroundColorMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SetBackgroundColor", [CtLiteralImpl]"..."));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]backgroundColorMenuItem);
        [CtInvocationImpl][CtVariableReadImpl]backgroundColorMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color desiredColor = [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.showDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SetBackgroundColor", [CtLiteralImpl]""), [CtFieldReadImpl]defaultBackgroundColor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]desiredColor != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]defaultBackgroundColor.equals([CtVariableReadImpl]desiredColor))) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]defaultBackgroundColor = [CtVariableReadImpl]desiredColor;
                [CtInvocationImpl]setBackgroundColor([CtVariableReadImpl]desiredColor);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// set default text color
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]javax.swing.JMenuItem textColorMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultTextColor", [CtLiteralImpl]"..."));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]textColorMenuItem);
        [CtInvocationImpl][CtVariableReadImpl]textColorMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color desiredColor = [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.showDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultTextColor", [CtLiteralImpl]""), [CtFieldReadImpl]defaultTextColor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]desiredColor != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]defaultTextColor.equals([CtVariableReadImpl]desiredColor))) [CtBlockImpl]{
                [CtInvocationImpl]setDefaultTextColor([CtVariableReadImpl]desiredColor);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtIfImpl]if ([CtFieldReadImpl]editorUseOldLocSize) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// 
            [CtCommentImpl]// save location and size
            [CtCommentImpl]// 
            [CtTypeReferenceImpl]javax.swing.JMenuItem locationItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SetLocation"));
            [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]locationItem);
            [CtInvocationImpl][CtVariableReadImpl]locationItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
                [CtInvocationImpl]setCurrentPositionAndSize();
                [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"Bounds:{}, {}, {}, {}, {}, {}", [CtFieldReadImpl]upperLeftX, [CtFieldReadImpl]upperLeftY, [CtFieldReadImpl]windowWidth, [CtFieldReadImpl]windowHeight, [CtFieldReadImpl]panelWidth, [CtFieldReadImpl]panelHeight);
            });
        }
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// Add Options
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]javax.swing.JMenu optionsAddMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AddMenuTitle"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]optionsAddMenu);
        [CtLocalVariableImpl][CtCommentImpl]// add background image
        [CtTypeReferenceImpl]javax.swing.JMenuItem backgroundItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AddBackground") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]optionsAddMenu.add([CtVariableReadImpl]backgroundItem);
        [CtInvocationImpl][CtVariableReadImpl]backgroundItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl]addBackground();
            [CtInvocationImpl][CtCommentImpl]// note: panel resized in addBackground
            setDirty();
            [CtInvocationImpl]redrawPanel();
        });
        [CtLocalVariableImpl][CtCommentImpl]// add fast clock
        [CtTypeReferenceImpl]javax.swing.JMenuItem clockItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AddItem", [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"FastClock")));
        [CtInvocationImpl][CtVariableReadImpl]optionsAddMenu.add([CtVariableReadImpl]clockItem);
        [CtInvocationImpl][CtVariableReadImpl]clockItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]AnalogClock2Display c = [CtInvocationImpl]addClock();
            [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]c.getBounds());
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
        });
        [CtLocalVariableImpl][CtCommentImpl]// add turntable
        [CtTypeReferenceImpl]javax.swing.JMenuItem turntableItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AddTurntable"));
        [CtInvocationImpl][CtVariableReadImpl]optionsAddMenu.add([CtVariableReadImpl]turntableItem);
        [CtInvocationImpl][CtVariableReadImpl]turntableItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D pt = [CtInvocationImpl]windowCenter();
            [CtIfImpl]if ([CtFieldReadImpl]selectionActive) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]pt = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.midPoint([CtInvocationImpl]getSelectionRect());
            }
            [CtInvocationImpl]addTurntable([CtVariableReadImpl]pt);
            [CtInvocationImpl][CtCommentImpl]// note: panel resized in addTurntable
            setDirty();
            [CtInvocationImpl]redrawPanel();
        });
        [CtLocalVariableImpl][CtCommentImpl]// add reporter
        [CtTypeReferenceImpl]javax.swing.JMenuItem reporterItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AddReporter") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]optionsAddMenu.add([CtVariableReadImpl]reporterItem);
        [CtInvocationImpl][CtVariableReadImpl]reporterItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D pt = [CtInvocationImpl]windowCenter();
            [CtIfImpl]if ([CtFieldReadImpl]selectionActive) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]pt = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.midPoint([CtInvocationImpl]getSelectionRect());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]EnterReporterDialog d = [CtConstructorCallImpl]new [CtTypeReferenceImpl]EnterReporterDialog([CtThisAccessImpl]this);
            [CtInvocationImpl][CtVariableReadImpl]d.enterReporter([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]pt.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]pt.getY())));
            [CtInvocationImpl][CtCommentImpl]// note: panel resized in enterReporter
            setDirty();
            [CtInvocationImpl]redrawPanel();
        });
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// grid menu
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]javax.swing.JMenu gridMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"GridMenuTitle"));[CtCommentImpl]// used for Grid SubMenu

        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]gridMenu);
        [CtAssignmentImpl][CtCommentImpl]// show grid
        [CtFieldWriteImpl]showGridCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ShowEditGrid"));
        [CtInvocationImpl][CtFieldReadImpl]showGridCheckBoxMenuItem.setAccelerator([CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ShowEditGridAccelerator")), [CtVariableReadImpl]primary_modifier));
        [CtInvocationImpl][CtVariableReadImpl]gridMenu.add([CtFieldReadImpl]showGridCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]showGridCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]drawGrid = [CtInvocationImpl][CtFieldReadImpl]showGridCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]showGridCheckBoxMenuItem.setSelected([CtInvocationImpl]getDrawGrid());
        [CtAssignmentImpl][CtCommentImpl]// snap to grid on add
        [CtFieldWriteImpl]snapToGridOnAddCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SnapToGridOnAdd"));
        [CtInvocationImpl][CtFieldReadImpl]snapToGridOnAddCheckBoxMenuItem.setAccelerator([CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SnapToGridOnAddAccelerator")), [CtBinaryOperatorImpl][CtVariableReadImpl]primary_modifier | [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.ActionEvent.[CtFieldReferenceImpl]SHIFT_MASK));
        [CtInvocationImpl][CtVariableReadImpl]gridMenu.add([CtFieldReadImpl]snapToGridOnAddCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]snapToGridOnAddCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]snapToGridOnAdd = [CtInvocationImpl][CtFieldReadImpl]snapToGridOnAddCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]snapToGridOnAddCheckBoxMenuItem.setSelected([CtFieldReadImpl]snapToGridOnAdd);
        [CtAssignmentImpl][CtCommentImpl]// snap to grid on move
        [CtFieldWriteImpl]snapToGridOnMoveCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SnapToGridOnMove"));
        [CtInvocationImpl][CtFieldReadImpl]snapToGridOnMoveCheckBoxMenuItem.setAccelerator([CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SnapToGridOnMoveAccelerator")), [CtBinaryOperatorImpl][CtVariableReadImpl]primary_modifier | [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.ActionEvent.[CtFieldReferenceImpl]SHIFT_MASK));
        [CtInvocationImpl][CtVariableReadImpl]gridMenu.add([CtFieldReadImpl]snapToGridOnMoveCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]snapToGridOnMoveCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]snapToGridOnMove = [CtInvocationImpl][CtFieldReadImpl]snapToGridOnMoveCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]snapToGridOnMoveCheckBoxMenuItem.setSelected([CtFieldReadImpl]snapToGridOnMove);
        [CtLocalVariableImpl][CtCommentImpl]// specify grid square size
        [CtTypeReferenceImpl]javax.swing.JMenuItem gridSizeItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SetGridSizes") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]gridMenu.add([CtVariableReadImpl]gridSizeItem);
        [CtInvocationImpl][CtVariableReadImpl]gridSizeItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]EnterGridSizesDialog d = [CtConstructorCallImpl]new [CtTypeReferenceImpl]EnterGridSizesDialog([CtThisAccessImpl]this);
            [CtInvocationImpl][CtVariableReadImpl]d.enterGridSizes();
        });
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// track menu
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]javax.swing.JMenu trackMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TrackMenuTitle"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]trackMenu);
        [CtLocalVariableImpl][CtCommentImpl]// set track drawing options menu item
        [CtTypeReferenceImpl]javax.swing.JMenuItem jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SetTrackDrawingOptions"));
        [CtInvocationImpl][CtVariableReadImpl]trackMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SetTrackDrawingOptionsToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrackDrawingOptionsDialog ltdod = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutTrackDrawingOptionsDialog([CtThisAccessImpl]this, [CtLiteralImpl]true, [CtInvocationImpl]getLayoutTrackDrawingOptions());
            [CtInvocationImpl][CtVariableReadImpl]ltdod.setVisible([CtLiteralImpl]true);
        });
        [CtLocalVariableImpl][CtCommentImpl]// track colors item menu item
        [CtTypeReferenceImpl]javax.swing.JMenu trkColourMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TrackColorSubMenu"));
        [CtInvocationImpl][CtVariableReadImpl]trackMenu.add([CtVariableReadImpl]trkColourMenu);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem trackColorMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultTrackColor"));
        [CtInvocationImpl][CtVariableReadImpl]trkColourMenu.add([CtVariableReadImpl]trackColorMenuItem);
        [CtInvocationImpl][CtVariableReadImpl]trackColorMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color desiredColor = [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.showDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultTrackColor"), [CtFieldReadImpl]defaultTrackColor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]desiredColor != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]defaultTrackColor.equals([CtVariableReadImpl]desiredColor))) [CtBlockImpl]{
                [CtInvocationImpl]setDefaultTrackColor([CtVariableReadImpl]desiredColor);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem trackOccupiedColorMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultOccupiedTrackColor"));
        [CtInvocationImpl][CtVariableReadImpl]trkColourMenu.add([CtVariableReadImpl]trackOccupiedColorMenuItem);
        [CtInvocationImpl][CtVariableReadImpl]trackOccupiedColorMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color desiredColor = [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.showDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultOccupiedTrackColor"), [CtFieldReadImpl]defaultOccupiedTrackColor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]desiredColor != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]defaultOccupiedTrackColor.equals([CtVariableReadImpl]desiredColor))) [CtBlockImpl]{
                [CtInvocationImpl]setDefaultOccupiedTrackColor([CtVariableReadImpl]desiredColor);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem trackAlternativeColorMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultAlternativeTrackColor"));
        [CtInvocationImpl][CtVariableReadImpl]trkColourMenu.add([CtVariableReadImpl]trackAlternativeColorMenuItem);
        [CtInvocationImpl][CtVariableReadImpl]trackAlternativeColorMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color desiredColor = [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.showDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DefaultAlternativeTrackColor"), [CtFieldReadImpl]defaultAlternativeTrackColor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]desiredColor != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]defaultAlternativeTrackColor.equals([CtVariableReadImpl]desiredColor))) [CtBlockImpl]{
                [CtInvocationImpl]setDefaultAlternativeTrackColor([CtVariableReadImpl]desiredColor);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtLocalVariableImpl][CtCommentImpl]// Set All Tracks To Default Colors
        [CtTypeReferenceImpl]javax.swing.JMenuItem setAllTracksToDefaultColorsMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SetAllTracksToDefaultColors"));
        [CtInvocationImpl][CtVariableReadImpl]trkColourMenu.add([CtVariableReadImpl]setAllTracksToDefaultColorsMenuItem);
        [CtInvocationImpl][CtVariableReadImpl]setAllTracksToDefaultColorsMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]setAllTracksToDefaultColors() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtAssignmentImpl][CtCommentImpl]// Automatically Assign Blocks to Track
        [CtFieldWriteImpl]autoAssignBlocksCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AutoAssignBlock"));
        [CtInvocationImpl][CtVariableReadImpl]trackMenu.add([CtFieldReadImpl]autoAssignBlocksCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]autoAssignBlocksCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtAssignmentImpl][CtFieldWriteImpl]autoAssignBlocks = [CtInvocationImpl][CtFieldReadImpl]autoAssignBlocksCheckBoxMenuItem.isSelected());
        [CtInvocationImpl][CtFieldReadImpl]autoAssignBlocksCheckBoxMenuItem.setSelected([CtFieldReadImpl]autoAssignBlocks);
        [CtAssignmentImpl][CtCommentImpl]// add hideTrackSegmentConstructionLines menu item
        [CtFieldWriteImpl]hideTrackSegmentConstructionLinesCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"HideTrackConLines"));
        [CtInvocationImpl][CtVariableReadImpl]trackMenu.add([CtFieldReadImpl]hideTrackSegmentConstructionLinesCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]hideTrackSegmentConstructionLinesCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int show = [CtFieldReadImpl]TrackSegment.SHOWCON;
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]hideTrackSegmentConstructionLinesCheckBoxMenuItem.isSelected()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]show = [CtFieldReadImpl]TrackSegment.HIDECONALL;
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment ts : [CtInvocationImpl]getTrackSegments()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ts.hideConstructionLines([CtVariableReadImpl]show);
            }
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]hideTrackSegmentConstructionLinesCheckBoxMenuItem.setSelected([CtFieldReadImpl]autoAssignBlocks);
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// add turnout options submenu
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]javax.swing.JMenu turnoutOptionsMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutOptions"));
        [CtInvocationImpl][CtVariableReadImpl]optionMenu.add([CtVariableReadImpl]turnoutOptionsMenu);
        [CtAssignmentImpl][CtCommentImpl]// animation item
        [CtFieldWriteImpl]animationCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AllowTurnoutAnimation"));
        [CtInvocationImpl][CtVariableReadImpl]turnoutOptionsMenu.add([CtFieldReadImpl]animationCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]animationCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean mode = [CtInvocationImpl][CtFieldReadImpl]animationCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]setTurnoutAnimation([CtVariableReadImpl]mode);
        });
        [CtInvocationImpl][CtFieldReadImpl]animationCheckBoxMenuItem.setSelected([CtLiteralImpl]true);
        [CtAssignmentImpl][CtCommentImpl]// circle on Turnouts
        [CtFieldWriteImpl]turnoutCirclesOnCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutCirclesOn"));
        [CtInvocationImpl][CtVariableReadImpl]turnoutOptionsMenu.add([CtFieldReadImpl]turnoutCirclesOnCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]turnoutCirclesOnCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutCirclesWithoutEditMode = [CtInvocationImpl][CtFieldReadImpl]turnoutCirclesOnCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]turnoutCirclesOnCheckBoxMenuItem.setSelected([CtFieldReadImpl]turnoutCirclesWithoutEditMode);
        [CtLocalVariableImpl][CtCommentImpl]// select turnout circle color
        [CtTypeReferenceImpl]javax.swing.JMenuItem turnoutCircleColorMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutCircleColor"));
        [CtInvocationImpl][CtVariableReadImpl]turnoutCircleColorMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color desiredColor = [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.showDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutCircleColor"), [CtFieldReadImpl]turnoutCircleColor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]desiredColor != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]turnoutCircleColor.equals([CtVariableReadImpl]desiredColor))) [CtBlockImpl]{
                [CtInvocationImpl]setTurnoutCircleColor([CtVariableReadImpl]desiredColor);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]turnoutOptionsMenu.add([CtVariableReadImpl]turnoutCircleColorMenuItem);
        [CtLocalVariableImpl][CtCommentImpl]// select turnout circle thrown color
        [CtTypeReferenceImpl]javax.swing.JMenuItem turnoutCircleThrownColorMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutCircleThrownColor"));
        [CtInvocationImpl][CtVariableReadImpl]turnoutCircleThrownColorMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Color desiredColor = [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.showDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutCircleThrownColor"), [CtFieldReadImpl]turnoutCircleThrownColor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]desiredColor != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]turnoutCircleThrownColor.equals([CtVariableReadImpl]desiredColor))) [CtBlockImpl]{
                [CtInvocationImpl]setTurnoutCircleThrownColor([CtVariableReadImpl]desiredColor);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]turnoutOptionsMenu.add([CtVariableReadImpl]turnoutCircleThrownColorMenuItem);
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutFillControlCirclesCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutFillControlCircles"));
        [CtInvocationImpl][CtVariableReadImpl]turnoutOptionsMenu.add([CtFieldReadImpl]turnoutFillControlCirclesCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]turnoutFillControlCirclesCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutFillControlCircles = [CtInvocationImpl][CtFieldReadImpl]turnoutFillControlCirclesCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]turnoutFillControlCirclesCheckBoxMenuItem.setSelected([CtFieldReadImpl]turnoutFillControlCircles);
        [CtLocalVariableImpl][CtCommentImpl]// select turnout circle size
        [CtTypeReferenceImpl]javax.swing.JMenu turnoutCircleSizeMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutCircleSize"));
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutCircleSizeButtonGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ButtonGroup();
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"1", [CtLiteralImpl]1);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"2", [CtLiteralImpl]2);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"3", [CtLiteralImpl]3);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"4", [CtLiteralImpl]4);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"5", [CtLiteralImpl]5);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"6", [CtLiteralImpl]6);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"7", [CtLiteralImpl]7);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"8", [CtLiteralImpl]8);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"9", [CtLiteralImpl]9);
        [CtInvocationImpl]addTurnoutCircleSizeMenuEntry([CtVariableReadImpl]turnoutCircleSizeMenu, [CtLiteralImpl]"10", [CtLiteralImpl]10);
        [CtInvocationImpl][CtVariableReadImpl]turnoutOptionsMenu.add([CtVariableReadImpl]turnoutCircleSizeMenu);
        [CtAssignmentImpl][CtCommentImpl]// add "enable drawing of unselected leg " menu item (helps when diverging angle is small)
        [CtFieldWriteImpl]turnoutDrawUnselectedLegCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TurnoutDrawUnselectedLeg"));
        [CtInvocationImpl][CtVariableReadImpl]turnoutOptionsMenu.add([CtFieldReadImpl]turnoutDrawUnselectedLegCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]turnoutDrawUnselectedLegCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutDrawUnselectedLeg = [CtInvocationImpl][CtFieldReadImpl]turnoutDrawUnselectedLegCheckBoxMenuItem.isSelected();
            [CtInvocationImpl]redrawPanel();
        });
        [CtInvocationImpl][CtFieldReadImpl]turnoutDrawUnselectedLegCheckBoxMenuItem.setSelected([CtFieldReadImpl]turnoutDrawUnselectedLeg);
        [CtReturnImpl]return [CtVariableReadImpl]optionMenu;
    }

    [CtFieldImpl][CtCommentImpl]/* ============================================*\
    |* LayoutTrackDrawingOptions accessor methods *|
    \*============================================
     */
    private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutTrackDrawingOptions layoutTrackDrawingOptions = [CtLiteralImpl]null;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Getter Layout Track Drawing Options. since 4.15.6 split variable
     * defaultTrackColor and mainlineTrackColor/sidelineTrackColor <br>
     * blockDefaultColor, blockOccupiedColor and blockAlternativeColor added to
     * LayoutTrackDrawingOptions <br>
     *
     * @return LayoutTrackDrawingOptions object
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutTrackDrawingOptions getLayoutTrackDrawingOptions() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]layoutTrackDrawingOptions == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]layoutTrackDrawingOptions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutTrackDrawingOptions([CtInvocationImpl]getLayoutName());
            [CtInvocationImpl][CtCommentImpl]// integrate LayoutEditor drawing options with previous drawing options
            [CtFieldReadImpl]layoutTrackDrawingOptions.setMainBlockLineWidth([CtFieldReadImpl](([CtTypeReferenceImpl]int) (mainlineTrackWidth)));
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setSideBlockLineWidth([CtFieldReadImpl](([CtTypeReferenceImpl]int) (sidelineTrackWidth)));
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setMainRailWidth([CtFieldReadImpl](([CtTypeReferenceImpl]int) (mainlineTrackWidth)));
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setSideRailWidth([CtFieldReadImpl](([CtTypeReferenceImpl]int) (sidelineTrackWidth)));
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setMainRailColor([CtFieldReadImpl]mainlineTrackColor);
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setSideRailColor([CtFieldReadImpl]sidelineTrackColor);
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setBlockDefaultColor([CtFieldReadImpl]defaultTrackColor);
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setBlockOccupiedColor([CtFieldReadImpl]defaultOccupiedTrackColor);
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.setBlockAlternativeColor([CtFieldReadImpl]defaultAlternativeTrackColor);
        }
        [CtReturnImpl]return [CtFieldReadImpl]layoutTrackDrawingOptions;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * since 4.15.6 split variable defaultTrackColor and
     * mainlineTrackColor/sidelineTrackColor
     *
     * @param ltdo
     * 		LayoutTrackDrawingOptions object
     */
    public [CtTypeReferenceImpl]void setLayoutTrackDrawingOptions([CtParameterImpl][CtTypeReferenceImpl]LayoutTrackDrawingOptions ltdo) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]layoutTrackDrawingOptions = [CtVariableReadImpl]ltdo;
        [CtAssignmentImpl][CtCommentImpl]// integrate LayoutEditor drawing options with previous drawing options
        [CtFieldWriteImpl]mainlineTrackWidth = [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.getMainBlockLineWidth();
        [CtAssignmentImpl][CtFieldWriteImpl]sidelineTrackWidth = [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.getSideBlockLineWidth();
        [CtAssignmentImpl][CtFieldWriteImpl]mainlineTrackColor = [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.getMainRailColor();
        [CtAssignmentImpl][CtFieldWriteImpl]sidelineTrackColor = [CtInvocationImpl][CtFieldReadImpl]layoutTrackDrawingOptions.getSideRailColor();
        [CtInvocationImpl]redrawPanel();
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem skipTurnoutCheckBoxMenuItem = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]jmri.jmrit.entryexit.AddEntryExitPairAction addEntryExitPairAction = [CtLiteralImpl]null;

    [CtMethodImpl][CtJavaDocImpl]/**
     * setup the Layout Editor Tools menu
     *
     * @param menuBar
     * 		the menu bar to add the Tools menu to
     */
    protected [CtTypeReferenceImpl]void setupToolsMenu([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenuBar menuBar) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenu toolsMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuTools"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuToolsMnemonic")));
        [CtInvocationImpl][CtVariableReadImpl]menuBar.add([CtVariableReadImpl]toolsMenu);
        [CtInvocationImpl][CtCommentImpl]// setup checks menu
        [CtInvocationImpl]getLEChecks().setupChecksMenu([CtVariableReadImpl]toolsMenu);
        [CtInvocationImpl][CtCommentImpl]// assign blocks to selection
        [CtFieldReadImpl]assignBlockToSelectionMenuItem.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AssignBlockToSelectionToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtFieldReadImpl]assignBlockToSelectionMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]assignBlockToSelectionMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up scale track diagram dialog
            assignBlockToSelection();
        });
        [CtInvocationImpl][CtFieldReadImpl]assignBlockToSelectionMenuItem.setEnabled([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.size() > [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtCommentImpl]// scale track diagram
        [CtTypeReferenceImpl]javax.swing.JMenuItem jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ScaleTrackDiagram") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ScaleTrackDiagramToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// bring up scale track diagram dialog
            [CtTypeReferenceImpl]ScaleTrackDiagramDialog d = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ScaleTrackDiagramDialog([CtThisAccessImpl]this);
            [CtInvocationImpl][CtVariableReadImpl]d.scaleTrackDiagram();
        });
        [CtAssignmentImpl][CtCommentImpl]// translate selection
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TranslateSelection") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"TranslateSelectionToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// bring up translate selection dialog
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtFieldReadImpl]selectionActive) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectionWidth == [CtLiteralImpl]0.0)) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectionHeight == [CtLiteralImpl]0.0)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// no selection has been made - nothing to move
                [CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error12"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// bring up move selection dialog
                [CtTypeReferenceImpl]MoveSelectionDialog d = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MoveSelectionDialog([CtThisAccessImpl]this);
                [CtInvocationImpl][CtVariableReadImpl]d.moveSelection();
            }
        });
        [CtInvocationImpl][CtCommentImpl]// undo translate selection
        [CtFieldReadImpl]undoTranslateSelectionMenuItem.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"UndoTranslateSelectionToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtFieldReadImpl]undoTranslateSelectionMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]undoTranslateSelectionMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// undo previous move selection
            undoMoveSelection();
        });
        [CtInvocationImpl][CtFieldReadImpl]undoTranslateSelectionMenuItem.setEnabled([CtFieldReadImpl]canUndoMoveSelection);
        [CtAssignmentImpl][CtCommentImpl]// rotate selection
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"RotateSelection90MenuItemTitle"));
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"RotateSelection90MenuItemToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]rotateSelection90());
        [CtAssignmentImpl][CtCommentImpl]// rotate entire layout
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"RotateLayout90MenuItemTitle"));
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"RotateLayout90MenuItemToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]rotateLayout90());
        [CtAssignmentImpl][CtCommentImpl]// align layout to grid
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AlignLayoutToGridMenuItemTitle") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AlignLayoutToGridMenuItemToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]alignLayoutToGrid());
        [CtAssignmentImpl][CtCommentImpl]// align selection to grid
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AlignSelectionToGridMenuItemTitle") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AlignSelectionToGridMenuItemToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]alignSelectionToGrid());
        [CtAssignmentImpl][CtCommentImpl]// reset turnout size to program defaults
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ResetTurnoutSize"));
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ResetTurnoutSizeToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// undo previous move selection
            resetTurnoutSize();
        });
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.addSeparator();
        [CtAssignmentImpl][CtCommentImpl]// skip turnout
        [CtFieldWriteImpl]skipTurnoutCheckBoxMenuItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JCheckBoxMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SkipInternalTurnout"));
        [CtInvocationImpl][CtFieldReadImpl]skipTurnoutCheckBoxMenuItem.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SkipInternalTurnoutToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtFieldReadImpl]skipTurnoutCheckBoxMenuItem);
        [CtInvocationImpl][CtFieldReadImpl]skipTurnoutCheckBoxMenuItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setIncludedTurnoutSkipped([CtInvocationImpl][CtFieldReadImpl]skipTurnoutCheckBoxMenuItem.isSelected()));
        [CtInvocationImpl][CtFieldReadImpl]skipTurnoutCheckBoxMenuItem.setSelected([CtInvocationImpl]isIncludedTurnoutSkipped());
        [CtAssignmentImpl][CtCommentImpl]// set signals at turnout
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtTurnout") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtTurnoutToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up signals at turnout tool dialog
            [CtInvocationImpl]getLETools().setSignalsAtTurnout([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame);
        });
        [CtAssignmentImpl][CtCommentImpl]// set signals at block boundary
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtBoundary") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtBoundaryToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up signals at block boundary tool dialog
            [CtInvocationImpl]getLETools().setSignalsAtBlockBoundary([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame);
        });
        [CtAssignmentImpl][CtCommentImpl]// set signals at crossover turnout
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtXoverTurnout") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtXoverTurnoutToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up signals at crossover tool dialog
            [CtInvocationImpl]getLETools().setSignalsAtXoverTurnout([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame);
        });
        [CtAssignmentImpl][CtCommentImpl]// set signals at level crossing
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtLevelXing") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtLevelXingToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up signals at level crossing tool dialog
            [CtInvocationImpl]getLETools().setSignalsAtLevelXing([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame);
        });
        [CtAssignmentImpl][CtCommentImpl]// set signals at throat-to-throat turnouts
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtTToTTurnout") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtTToTTurnoutToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up signals at throat-to-throat turnouts tool dialog
            [CtInvocationImpl]getLETools().setSignalsAtThroatToThroatTurnouts([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame);
        });
        [CtAssignmentImpl][CtCommentImpl]// set signals at 3-way turnout
        [CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAt3WayTurnout") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAt3WayTurnoutToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up signals at 3-way turnout tool dialog
            [CtInvocationImpl]getLETools().setSignalsAt3WayTurnout([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame);
        });
        [CtAssignmentImpl][CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtSlip") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"SignalsAtSlipToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bring up signals at throat-to-throat turnouts tool dialog
            [CtInvocationImpl]getLETools().setSignalsAtSlip([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame);
        });
        [CtAssignmentImpl][CtVariableWriteImpl]jmi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EntryExitTitle") + [CtLiteralImpl]"...");
        [CtInvocationImpl][CtVariableReadImpl]jmi.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EntryExitToolTip"));
        [CtInvocationImpl][CtVariableReadImpl]toolsMenu.add([CtVariableReadImpl]jmi);
        [CtInvocationImpl][CtVariableReadImpl]jmi.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]addEntryExitPairAction == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]addEntryExitPairAction = [CtConstructorCallImpl]new [CtTypeReferenceImpl]jmri.jmrit.entryexit.AddEntryExitPairAction([CtLiteralImpl]"ENTRY EXIT", [CtThisAccessImpl]this);
            }
            [CtInvocationImpl][CtFieldReadImpl]addEntryExitPairAction.actionPerformed([CtVariableReadImpl]event);
        });
        [CtCommentImpl]// if (true) {   //TODO: disable for production
        [CtCommentImpl]// jmi = new JMenuItem("GEORGE");
        [CtCommentImpl]// toolsMenu.add(jmi);
        [CtCommentImpl]// jmi.addActionListener((ActionEvent event) -> {
        [CtCommentImpl]// //do GEORGE stuff here!
        [CtCommentImpl]// });
        [CtCommentImpl]// }
    }[CtCommentImpl]// setupToolsMenu


    [CtMethodImpl][CtJavaDocImpl]/**
     * get the toolbar side
     *
     * @return the side where to put the tool bar
     */
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide getToolBarSide() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]toolBarSide;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * set the tool bar side
     *
     * @param newToolBarSide
     * 		on which side to put the toolbar
     */
    public [CtTypeReferenceImpl]void setToolBarSide([CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide newToolBarSide) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// null if edit toolbar is not setup yet...
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]newToolBarSide.equals([CtFieldReadImpl]toolBarSide)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]toolBarSide = [CtVariableReadImpl]newToolBarSide;
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtInvocationImpl][CtVariableReadImpl]prefsMgr.setProperty([CtInvocationImpl]getWindowFrameRef(), [CtLiteralImpl]"toolBarSide", [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]toolBarSide.getName()));
            [CtInvocationImpl][CtFieldReadImpl]toolBarSideTopButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eTOP));
            [CtInvocationImpl][CtFieldReadImpl]toolBarSideLeftButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eLEFT));
            [CtInvocationImpl][CtFieldReadImpl]toolBarSideBottomButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eBOTTOM));
            [CtInvocationImpl][CtFieldReadImpl]toolBarSideRightButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eRIGHT));
            [CtInvocationImpl][CtFieldReadImpl]toolBarSideFloatButton.setSelected([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT));
            [CtInvocationImpl]setupToolBar();[CtCommentImpl]// re-layout all the toolbar items

            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]editToolBarContainerPanel != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.setVisible([CtLiteralImpl]false);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]floatEditHelpPanel != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]floatEditHelpPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtInvocationImpl]getShowHelpBar());
                }
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]floatingEditToolBoxFrame != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]deletefloatingEditToolBoxFrame();
                }
                [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.setVisible([CtInvocationImpl]isEditable());
                [CtIfImpl]if ([CtInvocationImpl]getShowHelpBar()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]helpBarPanel.setVisible([CtInvocationImpl]isEditable());
                    [CtLocalVariableImpl][CtCommentImpl]// not sure why... but this is the only way I could
                    [CtCommentImpl]// get everything to layout correctly
                    [CtCommentImpl]// when the helpbar is visible...
                    [CtTypeReferenceImpl]boolean editMode = [CtInvocationImpl]isEditable();
                    [CtInvocationImpl]setAllEditable([CtUnaryOperatorImpl]![CtVariableReadImpl]editMode);
                    [CtInvocationImpl]setAllEditable([CtVariableReadImpl]editMode);
                }
            }
            [CtInvocationImpl][CtFieldReadImpl]wideToolBarCheckBoxMenuItem.setEnabled([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eTOP) || [CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eBOTTOM));
        }
    }[CtCommentImpl]// setToolBarSide


    [CtMethodImpl][CtCommentImpl]// 
    [CtCommentImpl]// 
    [CtCommentImpl]// 
    private [CtTypeReferenceImpl]void setToolBarWide([CtParameterImpl][CtTypeReferenceImpl]boolean newToolBarIsWide) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// null if edit toolbar not setup yet...
        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.toolBarIsWide != [CtVariableReadImpl]newToolBarIsWide) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.toolBarIsWide = [CtVariableReadImpl]newToolBarIsWide;
            [CtInvocationImpl][CtFieldReadImpl]wideToolBarCheckBoxMenuItem.setSelected([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.toolBarIsWide);
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Note: since prefs default to false and we want wide to be the default
                [CtCommentImpl]// we invert it and save it as thin
                [CtVariableReadImpl]prefsMgr.setSimplePreferenceState([CtBinaryOperatorImpl][CtInvocationImpl]getWindowFrameRef() + [CtLiteralImpl]".toolBarThin", [CtUnaryOperatorImpl]![CtVariableReadImpl]leToolBarPanel.toolBarIsWide);
            });
            [CtInvocationImpl]setupToolBar();[CtCommentImpl]// re-layout all the toolbar items

            [CtIfImpl]if ([CtInvocationImpl]getShowHelpBar()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// not sure why, but this is the only way I could
                [CtCommentImpl]// get everything to layout correctly
                [CtCommentImpl]// when the helpbar is visible...
                [CtTypeReferenceImpl]boolean editMode = [CtInvocationImpl]isEditable();
                [CtInvocationImpl]setAllEditable([CtUnaryOperatorImpl]![CtVariableReadImpl]editMode);
                [CtInvocationImpl]setAllEditable([CtVariableReadImpl]editMode);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]helpBarPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtInvocationImpl]getShowHelpBar());
            }
        }
    }[CtCommentImpl]// setToolBarWide


    [CtMethodImpl][CtCommentImpl]// 
    [CtCommentImpl]// 
    [CtCommentImpl]// 
    private [CtTypeReferenceImpl]void setupZoomMenu([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenuBar menuBar) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuZoomMnemonic")));
        [CtInvocationImpl][CtVariableReadImpl]menuBar.add([CtFieldReadImpl]zoomMenu);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.ButtonGroup zoomButtonGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ButtonGroup();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int primary_modifier = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        [CtLocalVariableImpl][CtCommentImpl]// add zoom choices to menu
        [CtTypeReferenceImpl]javax.swing.JMenuItem zoomInItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ZoomIn"));
        [CtInvocationImpl][CtVariableReadImpl]zoomInItem.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"zoomInMnemonic")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String zoomInAccelerator = [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"zoomInAccelerator");
        [CtInvocationImpl][CtCommentImpl]// log.debug("zoomInAccelerator: " + zoomInAccelerator);
        [CtVariableReadImpl]zoomInItem.setAccelerator([CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtVariableReadImpl]zoomInAccelerator), [CtVariableReadImpl]primary_modifier));
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtVariableReadImpl]zoomInItem);
        [CtInvocationImpl][CtVariableReadImpl]zoomInItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtBinaryOperatorImpl][CtInvocationImpl]getZoom() * [CtLiteralImpl]1.1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem zoomOutItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ZoomOut"));
        [CtInvocationImpl][CtVariableReadImpl]zoomOutItem.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"zoomOutMnemonic")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String zoomOutAccelerator = [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"zoomOutAccelerator");
        [CtInvocationImpl][CtCommentImpl]// log.debug("zoomOutAccelerator: " + zoomOutAccelerator);
        [CtVariableReadImpl]zoomOutItem.setAccelerator([CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtVariableReadImpl]zoomOutAccelerator), [CtVariableReadImpl]primary_modifier));
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtVariableReadImpl]zoomOutItem);
        [CtInvocationImpl][CtVariableReadImpl]zoomOutItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtBinaryOperatorImpl][CtInvocationImpl]getZoom() / [CtLiteralImpl]1.1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem zoomFitItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ZoomToFit"));
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtVariableReadImpl]zoomFitItem);
        [CtInvocationImpl][CtVariableReadImpl]zoomFitItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]zoomToFit());
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.addSeparator();
        [CtInvocationImpl][CtCommentImpl]// add zoom choices to menu
        [CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom025Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom025Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]0.25));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom025Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom05Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom05Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]0.5));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom05Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom075Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom075Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]0.75));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom075Item);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String zoomNoneAccelerator = [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"zoomNoneAccelerator");
        [CtInvocationImpl][CtCommentImpl]// log.debug("zoomNoneAccelerator: " + zoomNoneAccelerator);
        [CtFieldReadImpl]noZoomItem.setAccelerator([CtInvocationImpl][CtTypeAccessImpl]javax.swing.KeyStroke.getKeyStroke([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtVariableReadImpl]zoomNoneAccelerator), [CtVariableReadImpl]primary_modifier));
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]noZoomItem);
        [CtInvocationImpl][CtFieldReadImpl]noZoomItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]1.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]noZoomItem);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom15Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom15Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]1.5));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom15Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom20Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom20Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]2.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom20Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom30Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom30Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]3.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom30Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom40Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom40Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]4.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom40Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom50Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom50Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]5.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom50Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom60Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom60Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]6.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom60Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom70Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom70Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]7.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom70Item);
        [CtInvocationImpl][CtFieldReadImpl]zoomMenu.add([CtFieldReadImpl]zoom80Item);
        [CtInvocationImpl][CtFieldReadImpl]zoom80Item.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtInvocationImpl]setZoom([CtLiteralImpl]8.0));
        [CtInvocationImpl][CtVariableReadImpl]zoomButtonGroup.add([CtFieldReadImpl]zoom80Item);
        [CtInvocationImpl][CtCommentImpl]// note: because this LayoutEditor object was just instantiated its
        [CtCommentImpl]// zoom attribute is 1.0; if it's being instantiated from an XML file
        [CtCommentImpl]// that has a zoom attribute for this object then setZoom will be
        [CtCommentImpl]// called after this method returns and we'll select the appropriate
        [CtCommentImpl]// menu item then.
        [CtFieldReadImpl]noZoomItem.setSelected([CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// Note: We have to invoke this stuff later because _targetPanel is not setup yet
        [CtTypeAccessImpl]javax.swing.SwingUtilities.invokeLater([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// get the window specific saved zoom user preference
            [CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object zoomProp = [CtInvocationImpl][CtVariableReadImpl]prefsMgr.getProperty([CtInvocationImpl]getWindowFrameRef(), [CtLiteralImpl]"zoom");
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtLiteralImpl]"{} zoom is {}", [CtInvocationImpl]getWindowFrameRef(), [CtVariableReadImpl]zoomProp);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]zoomProp != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]setZoom([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Double) (zoomProp)));
                }
            });
            [CtLocalVariableImpl][CtCommentImpl]// get the scroll bars from the scroll pane
            [CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtInvocationImpl]getPanelScrollPane();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]scrollPane != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollBar hsb = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getHorizontalScrollBar();
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollBar vsb = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getVerticalScrollBar();
                [CtInvocationImpl][CtCommentImpl]// Increase scroll bar unit increments!!!
                [CtVariableReadImpl]vsb.setUnitIncrement([CtFieldReadImpl]gridSize1st);
                [CtInvocationImpl][CtVariableReadImpl]hsb.setUnitIncrement([CtFieldReadImpl]gridSize1st);
                [CtInvocationImpl][CtCommentImpl]// add scroll bar adjustment listeners
                [CtVariableReadImpl]vsb.addAdjustmentListener([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::scrollBarAdjusted);
                [CtInvocationImpl][CtVariableReadImpl]hsb.addAdjustmentListener([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::scrollBarAdjusted);
                [CtAssignmentImpl][CtCommentImpl]// remove all mouse wheel listeners
                [CtFieldWriteImpl]mouseWheelListeners = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getMouseWheelListeners();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.event.MouseWheelListener mwl : [CtFieldReadImpl]mouseWheelListeners) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]scrollPane.removeMouseWheelListener([CtVariableReadImpl]mwl);
                }
                [CtInvocationImpl][CtCommentImpl]// add my mouse wheel listener
                [CtCommentImpl]// (so mouseWheelMoved (below) will be called)
                [CtVariableReadImpl]scrollPane.addMouseWheelListener([CtThisAccessImpl]this);
            }
        });
    }[CtCommentImpl]// setupZoomMenu


    [CtFieldImpl]private transient [CtArrayTypeReferenceImpl]java.awt.event.MouseWheelListener[] mouseWheelListeners;

    [CtMethodImpl][CtCommentImpl]// scroll bar listener to update x & y coordinates in toolbar on scroll
    public [CtTypeReferenceImpl]void scrollBarAdjusted([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.AdjustmentEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// log.warn("scrollBarAdjusted");
        if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// get the location of the mouse
            [CtTypeReferenceImpl]java.awt.PointerInfo mpi = [CtInvocationImpl][CtTypeAccessImpl]java.awt.MouseInfo.getPointerInfo();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Point mouseLoc = [CtInvocationImpl][CtVariableReadImpl]mpi.getLocation();
            [CtInvocationImpl][CtCommentImpl]// convert to target panel coordinates
            [CtTypeAccessImpl]javax.swing.SwingUtilities.convertPointFromScreen([CtVariableReadImpl]mouseLoc, [CtInvocationImpl]getTargetPanel());
            [CtLocalVariableImpl][CtCommentImpl]// correct for scaling...
            [CtTypeReferenceImpl]double theZoom = [CtInvocationImpl]getZoom();
            [CtAssignmentImpl][CtFieldWriteImpl]xLoc = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtVariableReadImpl]mouseLoc.getX() / [CtVariableReadImpl]theZoom));
            [CtAssignmentImpl][CtFieldWriteImpl]yLoc = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtVariableReadImpl]mouseLoc.getY() / [CtVariableReadImpl]theZoom));
            [CtInvocationImpl][CtFieldReadImpl]dLoc.setLocation([CtFieldReadImpl]xLoc, [CtFieldReadImpl]yLoc);
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.xLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]xLoc));
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.yLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]yLoc));
        }
        [CtInvocationImpl]adjustClip();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void adjustScrollBars() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// log.info("adjustScrollBars()");
        [CtCommentImpl]// This is the bounds of what's on the screen
        [CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtInvocationImpl]getPanelScrollPane();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Rectangle scrollBounds = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getViewportBorderBounds();
        [CtLocalVariableImpl][CtCommentImpl]// log.info("  getViewportBorderBounds: {}", MathUtil.rectangle2DToString(scrollBounds));
        [CtCommentImpl]// this is the size of the entire scaled layout panel
        [CtTypeReferenceImpl]java.awt.Dimension targetPanelSize = [CtInvocationImpl]getTargetPanelSize();
        [CtLocalVariableImpl][CtCommentImpl]// log.info("  getTargetPanelSize: {}", MathUtil.dimensionToString(targetPanelSize));
        [CtCommentImpl]// double scale = getZoom();
        [CtCommentImpl]// determine the relative position of the current horizontal scrollbar
        [CtTypeReferenceImpl]javax.swing.JScrollBar horScroll = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getHorizontalScrollBar();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double oldX = [CtInvocationImpl][CtVariableReadImpl]horScroll.getValue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double oldMaxX = [CtInvocationImpl][CtVariableReadImpl]horScroll.getMaximum();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double ratioX = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]oldMaxX < [CtLiteralImpl]1) ? [CtLiteralImpl]0 : [CtBinaryOperatorImpl][CtVariableReadImpl]oldX / [CtVariableReadImpl]oldMaxX;
        [CtLocalVariableImpl][CtCommentImpl]// calculate the new X maximum and value
        [CtTypeReferenceImpl]int panelWidth = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]targetPanelSize.getWidth()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int scrollWidth = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]scrollBounds.getWidth()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newMaxX = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]panelWidth - [CtVariableReadImpl]scrollWidth, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newX = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newMaxX * [CtVariableReadImpl]ratioX));
        [CtInvocationImpl][CtVariableReadImpl]horScroll.setMaximum([CtVariableReadImpl]newMaxX);
        [CtInvocationImpl][CtVariableReadImpl]horScroll.setValue([CtVariableReadImpl]newX);
        [CtLocalVariableImpl][CtCommentImpl]// determine the relative position of the current vertical scrollbar
        [CtTypeReferenceImpl]javax.swing.JScrollBar vertScroll = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getVerticalScrollBar();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double oldY = [CtInvocationImpl][CtVariableReadImpl]vertScroll.getValue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double oldMaxY = [CtInvocationImpl][CtVariableReadImpl]vertScroll.getMaximum();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double ratioY = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]oldMaxY < [CtLiteralImpl]1) ? [CtLiteralImpl]0 : [CtBinaryOperatorImpl][CtVariableReadImpl]oldY / [CtVariableReadImpl]oldMaxY;
        [CtLocalVariableImpl][CtCommentImpl]// calculate the new X maximum and value
        [CtTypeReferenceImpl]int panelHeight = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]targetPanelSize.getHeight()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int scrollHeight = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]scrollBounds.getHeight()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newMaxY = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]panelHeight - [CtVariableReadImpl]scrollHeight, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newY = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newMaxY * [CtVariableReadImpl]ratioY));
        [CtInvocationImpl][CtVariableReadImpl]vertScroll.setMaximum([CtVariableReadImpl]newMaxY);
        [CtInvocationImpl][CtVariableReadImpl]vertScroll.setValue([CtVariableReadImpl]newY);
        [CtInvocationImpl][CtCommentImpl]// log.info("w: {}, x: {}, h: {}, y: {}", "" + newMaxX, "" + newX, "" + newMaxY, "" + newY);
        adjustClip();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void adjustClip() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// log.info("adjustClip()");
        [CtCommentImpl]// This is the bounds of what's on the screen
        [CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtInvocationImpl]getPanelScrollPane();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Rectangle scrollBounds = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getViewportBorderBounds();
        [CtLocalVariableImpl][CtCommentImpl]// log.info("  ViewportBorderBounds: {}", MathUtil.rectangle2DToString(scrollBounds));
        [CtTypeReferenceImpl]javax.swing.JScrollBar horScroll = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getHorizontalScrollBar();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int scrollX = [CtInvocationImpl][CtVariableReadImpl]horScroll.getValue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollBar vertScroll = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getVerticalScrollBar();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int scrollY = [CtInvocationImpl][CtVariableReadImpl]vertScroll.getValue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D newClipRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.offset([CtVariableReadImpl]scrollBounds, [CtBinaryOperatorImpl][CtVariableReadImpl]scrollX - [CtInvocationImpl][CtVariableReadImpl]scrollBounds.getMinX(), [CtBinaryOperatorImpl][CtVariableReadImpl]scrollY - [CtInvocationImpl][CtVariableReadImpl]scrollBounds.getMinY());
        [CtAssignmentImpl][CtVariableWriteImpl]newClipRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.scale([CtVariableReadImpl]newClipRect, [CtBinaryOperatorImpl][CtLiteralImpl]1.0 / [CtInvocationImpl]getZoom());
        [CtAssignmentImpl][CtVariableWriteImpl]newClipRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtVariableReadImpl]newClipRect, [CtLiteralImpl]1.0);[CtCommentImpl]// round to nearest pixel

        [CtInvocationImpl][CtFieldReadImpl]layoutEditorComponent.setClip([CtVariableReadImpl]newClipRect);
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void mouseWheelMoved([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.MouseWheelEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// log.warn("mouseWheelMoved");
        if ([CtInvocationImpl][CtVariableReadImpl]event.isAltDown()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// get the mouse position from the event and convert to target panel coordinates
            [CtTypeReferenceImpl]java.awt.Component component = [CtInvocationImpl](([CtTypeReferenceImpl]java.awt.Component) ([CtVariableReadImpl]event.getSource()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Point eventPoint = [CtInvocationImpl][CtVariableReadImpl]event.getPoint();
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JComponent targetPanel = [CtInvocationImpl]getTargetPanel();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D mousePoint = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.SwingUtilities.convertPoint([CtVariableReadImpl]component, [CtVariableReadImpl]eventPoint, [CtVariableReadImpl]targetPanel);
            [CtLocalVariableImpl][CtCommentImpl]// get the old view port position
            [CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtInvocationImpl]getPanelScrollPane();
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JViewport viewPort = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getViewport();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D viewPosition = [CtInvocationImpl][CtVariableReadImpl]viewPort.getViewPosition();
            [CtLocalVariableImpl][CtCommentImpl]// convert from oldZoom (scaled) coordinates to image coordinates
            [CtTypeReferenceImpl]double zoom = [CtInvocationImpl]getZoom();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D imageMousePoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.divide([CtVariableReadImpl]mousePoint, [CtVariableReadImpl]zoom);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D imageViewPosition = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.divide([CtVariableReadImpl]viewPosition, [CtVariableReadImpl]zoom);
            [CtLocalVariableImpl][CtCommentImpl]// compute the delta (in image coordinates)
            [CtTypeReferenceImpl]java.awt.geom.Point2D imageDelta = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtVariableReadImpl]imageMousePoint, [CtVariableReadImpl]imageViewPosition);
            [CtLocalVariableImpl][CtCommentImpl]// compute how much to change zoom
            [CtTypeReferenceImpl]double amount = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.pow([CtLiteralImpl]1.1, [CtInvocationImpl][CtVariableReadImpl]event.getScrollAmount());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getWheelRotation() < [CtLiteralImpl]0.0) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// reciprocal for zoom out
                [CtVariableWriteImpl]amount = [CtBinaryOperatorImpl][CtLiteralImpl]1.0 / [CtVariableReadImpl]amount;
            }
            [CtLocalVariableImpl][CtCommentImpl]// set the new zoom
            [CtTypeReferenceImpl]double newZoom = [CtInvocationImpl]setZoom([CtBinaryOperatorImpl][CtVariableReadImpl]zoom * [CtVariableReadImpl]amount);
            [CtAssignmentImpl][CtCommentImpl]// recalulate the amount (in case setZoom didn't zoom as much as we wanted)
            [CtVariableWriteImpl]amount = [CtBinaryOperatorImpl][CtVariableReadImpl]newZoom / [CtVariableReadImpl]zoom;
            [CtLocalVariableImpl][CtCommentImpl]// convert the old delta to the new
            [CtTypeReferenceImpl]java.awt.geom.Point2D newImageDelta = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.divide([CtVariableReadImpl]imageDelta, [CtVariableReadImpl]amount);
            [CtLocalVariableImpl][CtCommentImpl]// calculate the new view position (in image coordinates)
            [CtTypeReferenceImpl]java.awt.geom.Point2D newImageViewPosition = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtVariableReadImpl]imageMousePoint, [CtVariableReadImpl]newImageDelta);
            [CtLocalVariableImpl][CtCommentImpl]// convert from image coordinates to newZoom (scaled) coordinates
            [CtTypeReferenceImpl]java.awt.geom.Point2D newViewPosition = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.multiply([CtVariableReadImpl]newImageViewPosition, [CtVariableReadImpl]newZoom);
            [CtAssignmentImpl][CtCommentImpl]// don't let origin go negative
            [CtVariableWriteImpl]newViewPosition = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]newViewPosition, [CtTypeAccessImpl]MathUtil.zeroPoint2D);
            [CtInvocationImpl][CtCommentImpl]// log.info("mouseWheelMoved: newViewPos2D: {}", newViewPosition);
            [CtCommentImpl]// set new view position
            [CtVariableReadImpl]viewPort.setViewPosition([CtInvocationImpl][CtTypeAccessImpl]MathUtil.point2DToPoint([CtVariableReadImpl]newViewPosition));
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtInvocationImpl]getPanelScrollPane();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]scrollPane != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]scrollPane.getVerticalScrollBar().isVisible()) [CtBlockImpl]{
                    [CtForEachImpl][CtCommentImpl]// Redispatch the event to the original MouseWheelListeners
                    for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.event.MouseWheelListener mwl : [CtFieldReadImpl]mouseWheelListeners) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]mwl.mouseWheelMoved([CtVariableReadImpl]event);
                    }
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// proprogate event to ancestor
                    [CtTypeReferenceImpl]java.awt.Component ancestor = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.SwingUtilities.getAncestorOfClass([CtFieldReadImpl]javax.swing.JScrollPane.class, [CtVariableReadImpl]scrollPane);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ancestor != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.event.MouseWheelEvent mwe = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.event.MouseWheelEvent([CtVariableReadImpl]ancestor, [CtInvocationImpl][CtVariableReadImpl]event.getID(), [CtInvocationImpl][CtVariableReadImpl]event.getWhen(), [CtInvocationImpl][CtVariableReadImpl]event.getModifiersEx(), [CtInvocationImpl][CtVariableReadImpl]event.getX(), [CtInvocationImpl][CtVariableReadImpl]event.getY(), [CtInvocationImpl][CtVariableReadImpl]event.getXOnScreen(), [CtInvocationImpl][CtVariableReadImpl]event.getYOnScreen(), [CtInvocationImpl][CtVariableReadImpl]event.getClickCount(), [CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger(), [CtInvocationImpl][CtVariableReadImpl]event.getScrollType(), [CtInvocationImpl][CtVariableReadImpl]event.getScrollAmount(), [CtInvocationImpl][CtVariableReadImpl]event.getWheelRotation());
                        [CtInvocationImpl][CtVariableReadImpl]ancestor.dispatchEvent([CtVariableReadImpl]mwe);
                    }
                }
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]// 
    [CtCommentImpl]// enable the apropreate zoom menu items based on the zoomFactor
    [CtCommentImpl]// 
    private [CtTypeReferenceImpl]void enableZoomMenuItem([CtParameterImpl][CtTypeReferenceImpl]double zoomFactor) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]zoom025Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]0.25);
        [CtInvocationImpl][CtFieldReadImpl]zoom05Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]0.5);
        [CtInvocationImpl][CtFieldReadImpl]zoom075Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]0.75);
        [CtInvocationImpl][CtFieldReadImpl]noZoomItem.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]1.0);
        [CtInvocationImpl][CtFieldReadImpl]zoom15Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]1.5);
        [CtInvocationImpl][CtFieldReadImpl]zoom20Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]2.0);
        [CtInvocationImpl][CtFieldReadImpl]zoom30Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]3.0);
        [CtInvocationImpl][CtFieldReadImpl]zoom40Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]4.0);
        [CtInvocationImpl][CtFieldReadImpl]zoom50Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]5.0);
        [CtInvocationImpl][CtFieldReadImpl]zoom60Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]6.0);
        [CtInvocationImpl][CtFieldReadImpl]zoom70Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]7.0);
        [CtInvocationImpl][CtFieldReadImpl]zoom80Item.setEnabled([CtBinaryOperatorImpl][CtVariableReadImpl]zoomFactor <= [CtLiteralImpl]8.0);
    }

    [CtMethodImpl][CtCommentImpl]// 
    [CtCommentImpl]// select the apropreate zoom menu item based on the zoomFactor
    [CtCommentImpl]// 
    private [CtTypeReferenceImpl]void selectZoomMenuItem([CtParameterImpl][CtTypeReferenceImpl]double zoomFactor) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// this will put zoomFactor on 100% increments
        [CtCommentImpl]// (so it will more likely match one of these values)
        [CtTypeReferenceImpl]int newZoomFactor = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]MathUtil.granulize([CtVariableReadImpl]zoomFactor, [CtLiteralImpl]100)));
        [CtInvocationImpl][CtCommentImpl]// int newZoomFactor = ((int) Math.round(zoomFactor)) * 100;
        [CtFieldReadImpl]noZoomItem.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]100);
        [CtInvocationImpl][CtFieldReadImpl]zoom20Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]200);
        [CtInvocationImpl][CtFieldReadImpl]zoom30Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]300);
        [CtInvocationImpl][CtFieldReadImpl]zoom40Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]400);
        [CtInvocationImpl][CtFieldReadImpl]zoom50Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]500);
        [CtInvocationImpl][CtFieldReadImpl]zoom60Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]600);
        [CtInvocationImpl][CtFieldReadImpl]zoom70Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]700);
        [CtInvocationImpl][CtFieldReadImpl]zoom80Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]800);
        [CtAssignmentImpl][CtCommentImpl]// this will put zoomFactor on 50% increments
        [CtCommentImpl]// (so it will more likely match one of these values)
        [CtCommentImpl]// newZoomFactor = ((int) (zoomFactor * 2)) * 50;
        [CtVariableWriteImpl]newZoomFactor = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]MathUtil.granulize([CtVariableReadImpl]zoomFactor, [CtLiteralImpl]50)));
        [CtInvocationImpl][CtFieldReadImpl]zoom05Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]50);
        [CtInvocationImpl][CtFieldReadImpl]zoom15Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]150);
        [CtAssignmentImpl][CtCommentImpl]// this will put zoomFactor on 25% increments
        [CtCommentImpl]// (so it will more likely match one of these values)
        [CtCommentImpl]// newZoomFactor = ((int) (zoomFactor * 4)) * 25;
        [CtVariableWriteImpl]newZoomFactor = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]MathUtil.granulize([CtVariableReadImpl]zoomFactor, [CtLiteralImpl]25)));
        [CtInvocationImpl][CtFieldReadImpl]zoom025Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]25);
        [CtInvocationImpl][CtFieldReadImpl]zoom075Item.setSelected([CtBinaryOperatorImpl][CtVariableReadImpl]newZoomFactor == [CtLiteralImpl]75);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * setZoom
     *
     * @param zoomFactor
     * 		the amount to scale
     * @return the new scale amount (not necessarily the same as zoomFactor)
     */
    public [CtTypeReferenceImpl]double setZoom([CtParameterImpl][CtTypeReferenceImpl]double zoomFactor) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// re-calculate minZoom (so panel never smaller than view)
        [CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtInvocationImpl]getPanelScrollPane();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D scrollBounds = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getViewportBorderBounds();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D panelBounds = [CtInvocationImpl]getPanelBounds();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Dimension panelSize = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.getSize([CtVariableReadImpl]panelBounds);
        [CtAssignmentImpl][CtFieldWriteImpl]minZoom = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]scrollBounds.getWidth() / [CtInvocationImpl][CtVariableReadImpl]panelSize.getWidth(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]scrollBounds.getHeight() / [CtInvocationImpl][CtVariableReadImpl]panelSize.getHeight());
        [CtInvocationImpl]enableZoomMenuItem([CtFieldReadImpl]minZoom);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double newZoom = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.pin([CtVariableReadImpl]zoomFactor, [CtFieldReadImpl]minZoom, [CtFieldReadImpl]maxZoom);
        [CtInvocationImpl]selectZoomMenuItem([CtVariableReadImpl]newZoom);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]MathUtil.equals([CtVariableReadImpl]newZoom, [CtInvocationImpl]getPaintScale())) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"zoom: {}", [CtVariableReadImpl]zoomFactor);
            [CtAssignmentImpl][CtCommentImpl]// setPaintScale(newZoom);   //<<== don't call; messes up scrollbars
            [CtFieldWriteImpl]_paintScale = [CtVariableReadImpl]newZoom;[CtCommentImpl]// just set paint scale directly

            [CtInvocationImpl]resetTargetSize();[CtCommentImpl]// calculate new target panel size

            [CtInvocationImpl]adjustScrollBars();[CtCommentImpl]// and adjust the scrollbars ourselves

            [CtInvocationImpl][CtCommentImpl]// adjustClip();
            [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.zoomLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"x%1$,.2f", [CtVariableReadImpl]newZoom));
            [CtInvocationImpl][CtCommentImpl]// save the window specific saved zoom user preference
            [CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtInvocationImpl][CtVariableReadImpl]prefsMgr.setProperty([CtInvocationImpl]getWindowFrameRef(), [CtLiteralImpl]"zoom", [CtVariableReadImpl]zoomFactor));
        }
        [CtReturnImpl]return [CtInvocationImpl]getPaintScale();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * getZoom
     *
     * @return the zooming scale
     */
    public [CtTypeReferenceImpl]double getZoom() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getPaintScale();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * getMinZoom
     *
     * @return the minimum zoom scale
     */
    public [CtTypeReferenceImpl]double getMinZoom() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]minZoom;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * getMaxZoom
     *
     * @return the maximum zoom scale
     */
    public [CtTypeReferenceImpl]double getMaxZoom() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maxZoom;
    }

    [CtMethodImpl][CtCommentImpl]// 
    [CtCommentImpl]// TODO: make this public? (might be useful!)
    [CtCommentImpl]// 
    private [CtTypeReferenceImpl]java.awt.geom.Rectangle2D calculateMinimumLayoutBounds() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// calculate a union of the bounds of everything on the layout
        [CtTypeReferenceImpl]java.awt.geom.Rectangle2D result = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D.Double();
        [CtLocalVariableImpl][CtCommentImpl]// combine all (onscreen) Components into a list of list of Components
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.awt.Component>> listOfListsOfComponents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]backgroundImage);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]sensorImage);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]signalHeadImage);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]markerImage);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]labelImage);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]clocks);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]multiSensors);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]signalList);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]memoryLabelList);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]blockContentsLabelList);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]sensorList);
        [CtInvocationImpl][CtVariableReadImpl]listOfListsOfComponents.add([CtFieldReadImpl]signalMastList);
        [CtForEachImpl][CtCommentImpl]// combine their bounds
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.awt.Component> listOfComponents : [CtVariableReadImpl]listOfListsOfComponents) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Component o : [CtVariableReadImpl]listOfComponents) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.isEmpty()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]o.getBounds();
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]result.createUnion([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
                }
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack o : [CtFieldReadImpl]layoutTrackList) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.isEmpty()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]o.getBounds();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]result.createUnion([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape o : [CtFieldReadImpl]layoutShapes) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.isEmpty()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]o.getBounds();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]result.createUnion([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
            }
        }
        [CtAssignmentImpl][CtCommentImpl]// put a grid size margin around it
        [CtVariableWriteImpl]result = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.inset([CtVariableReadImpl]result, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]gridSize1st * [CtFieldReadImpl]gridSize2nd) / [CtUnaryOperatorImpl](-[CtLiteralImpl]2.0));
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * resize panel bounds
     *
     * @param forceFlag
     * 		if false only grow bigger
     * @return the new (?) panel bounds
     */
    private [CtTypeReferenceImpl]java.awt.geom.Rectangle2D resizePanelBounds([CtParameterImpl][CtTypeReferenceImpl]boolean forceFlag) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D panelBounds = [CtInvocationImpl]getPanelBounds();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D layoutBounds = [CtInvocationImpl]calculateMinimumLayoutBounds();
        [CtInvocationImpl][CtCommentImpl]// make sure it includes the origin
        [CtVariableReadImpl]layoutBounds.add([CtTypeAccessImpl]MathUtil.zeroPoint2D);
        [CtIfImpl]if ([CtVariableReadImpl]forceFlag) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]panelBounds = [CtVariableReadImpl]layoutBounds;
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]panelBounds.add([CtVariableReadImpl]layoutBounds);
        }
        [CtAssignmentImpl][CtCommentImpl]// don't let origin go negative
        [CtVariableWriteImpl]panelBounds = [CtInvocationImpl][CtVariableReadImpl]panelBounds.createIntersection([CtTypeAccessImpl]MathUtil.zeroToInfinityRectangle2D);
        [CtInvocationImpl][CtCommentImpl]// log.info("resizePanelBounds: {}", MathUtil.rectangle2DToString(panelBounds));
        setPanelBounds([CtVariableReadImpl]panelBounds);
        [CtReturnImpl]return [CtVariableReadImpl]panelBounds;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]double zoomToFit() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D layoutBounds = [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtCommentImpl]// calculate the bounds for the scroll pane
        [CtTypeReferenceImpl]javax.swing.JScrollPane scrollPane = [CtInvocationImpl]getPanelScrollPane();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D scrollBounds = [CtInvocationImpl][CtVariableReadImpl]scrollPane.getViewportBorderBounds();
        [CtAssignmentImpl][CtCommentImpl]// don't let origin go negative
        [CtVariableWriteImpl]scrollBounds = [CtInvocationImpl][CtVariableReadImpl]scrollBounds.createIntersection([CtTypeAccessImpl]MathUtil.zeroToInfinityRectangle2D);
        [CtLocalVariableImpl][CtCommentImpl]// calculate the horzontial and vertical scales
        [CtTypeReferenceImpl]double scaleWidth = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]scrollPane.getWidth() / [CtInvocationImpl][CtVariableReadImpl]layoutBounds.getWidth();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double scaleHeight = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]scrollPane.getHeight() / [CtInvocationImpl][CtVariableReadImpl]layoutBounds.getHeight();
        [CtLocalVariableImpl][CtCommentImpl]// set the new zoom to the smallest of the two
        [CtTypeReferenceImpl]double result = [CtInvocationImpl]setZoom([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]scaleWidth, [CtVariableReadImpl]scaleHeight));
        [CtAssignmentImpl][CtCommentImpl]// set the new zoom (return value may be different)
        [CtVariableWriteImpl]result = [CtInvocationImpl]setZoom([CtVariableReadImpl]result);
        [CtAssignmentImpl][CtCommentImpl]// calculate new scroll bounds
        [CtVariableWriteImpl]scrollBounds = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.scale([CtVariableReadImpl]layoutBounds, [CtVariableReadImpl]result);
        [CtAssignmentImpl][CtCommentImpl]// don't let origin go negative
        [CtVariableWriteImpl]scrollBounds = [CtInvocationImpl][CtVariableReadImpl]scrollBounds.createIntersection([CtTypeAccessImpl]MathUtil.zeroToInfinityRectangle2D);
        [CtInvocationImpl][CtCommentImpl]// make sure it includes the origin
        [CtVariableReadImpl]scrollBounds.add([CtTypeAccessImpl]MathUtil.zeroPoint2D);
        [CtInvocationImpl][CtCommentImpl]// and scroll to it
        [CtVariableReadImpl]scrollPane.scrollRectToVisible([CtInvocationImpl][CtTypeAccessImpl]MathUtil.rectangle2DToRectangle([CtVariableReadImpl]scrollBounds));
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.awt.geom.Point2D windowCenter() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// Returns window's center coordinates converted to layout space
        [CtCommentImpl]// Used for initial setup of turntables and reporters
        return [CtInvocationImpl][CtTypeAccessImpl]MathUtil.point2DToPoint([CtInvocationImpl][CtTypeAccessImpl]MathUtil.divide([CtInvocationImpl][CtTypeAccessImpl]MathUtil.center([CtInvocationImpl]getBounds()), [CtInvocationImpl]getZoom()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setupMarkerMenu([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenuBar menuBar) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenu markerMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuMarker"));
        [CtInvocationImpl][CtVariableReadImpl]markerMenu.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuMarkerMnemonic")));
        [CtInvocationImpl][CtVariableReadImpl]menuBar.add([CtVariableReadImpl]markerMenu);
        [CtInvocationImpl][CtVariableReadImpl]markerMenu.add([CtNewClassImpl]new [CtTypeReferenceImpl]javax.swing.AbstractAction([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AddLoco") + [CtLiteralImpl]"...")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void actionPerformed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) [CtBlockImpl]{
                [CtInvocationImpl]locoMarkerFromInput();
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]markerMenu.add([CtNewClassImpl]new [CtTypeReferenceImpl]javax.swing.AbstractAction([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AddLocoRoster") + [CtLiteralImpl]"...")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void actionPerformed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) [CtBlockImpl]{
                [CtInvocationImpl]locoMarkerFromRoster();
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]markerMenu.add([CtNewClassImpl]new [CtTypeReferenceImpl]javax.swing.AbstractAction([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"RemoveMarkers"))[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void actionPerformed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) [CtBlockImpl]{
                [CtInvocationImpl]removeMarkers();
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setupDispatcherMenu([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenuBar menuBar) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenu dispMenu = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuDispatcher"));
        [CtInvocationImpl][CtVariableReadImpl]dispMenu.setMnemonic([CtInvocationImpl][CtFieldReadImpl]stringsToVTCodes.get([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuDispatcherMnemonic")));
        [CtInvocationImpl][CtVariableReadImpl]dispMenu.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtConstructorCallImpl]new [CtTypeReferenceImpl]DispatcherAction([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuItemOpen"))));
        [CtInvocationImpl][CtVariableReadImpl]menuBar.add([CtVariableReadImpl]dispMenu);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem newTrainItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenuItem([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"MenuItemNewTrain"));
        [CtInvocationImpl][CtVariableReadImpl]dispMenu.add([CtVariableReadImpl]newTrainItem);
        [CtInvocationImpl][CtVariableReadImpl]newTrainItem.addActionListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.TransitManager.class).getNamedBeanSet().size() <= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Inform the user that there are no Transits available, and don't open the window
                [CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.ResourceBundle.getBundle([CtLiteralImpl]"jmri.jmrit.dispatcher.DispatcherBundle").getString([CtLiteralImpl]"NoTransitsMessage"));
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]DispatcherFrame df = [CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.DispatcherFrame.class);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]df.getNewTrainActive()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]df.getActiveTrainFrame().initiateTrain([CtVariableReadImpl]event, [CtLiteralImpl]null, [CtLiteralImpl]null);
                    [CtInvocationImpl][CtVariableReadImpl]df.setNewTrainActive([CtLiteralImpl]true);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]df.getActiveTrainFrame().showActivateFrame([CtLiteralImpl]null);
                }
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]menuBar.add([CtVariableReadImpl]dispMenu);
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean includedTurnoutSkipped = [CtLiteralImpl]false;

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isIncludedTurnoutSkipped() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]includedTurnoutSkipped;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setIncludedTurnoutSkipped([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean boo) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]includedTurnoutSkipped = [CtVariableReadImpl]boo;
    }

    [CtFieldImpl][CtTypeReferenceImpl]boolean openDispatcherOnLoad = [CtLiteralImpl]false;

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isOpenDispatcherOnLoad()"
    public [CtTypeReferenceImpl]boolean getOpenDispatcherOnLoad() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]openDispatcherOnLoad;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setOpenDispatcherOnLoad([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean boo) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]openDispatcherOnLoad = [CtVariableReadImpl]boo;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove marker icons from panel
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void removeMarkers() [CtBlockImpl]{
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtInvocationImpl][CtFieldReadImpl]markerImage.size(); [CtBinaryOperatorImpl][CtVariableReadImpl]i > [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]LocoIcon il = [CtInvocationImpl][CtFieldReadImpl]markerImage.get([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]il != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]il.isActive()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]markerImage.remove([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1);
                [CtInvocationImpl][CtVariableReadImpl]il.remove();
                [CtInvocationImpl][CtVariableReadImpl]il.dispose();
                [CtInvocationImpl]setDirty();
            }
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.removeMarkers();
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Assign the block from the toolbar to all selected layout tracks
     */
    protected [CtTypeReferenceImpl]void assignBlockToSelection() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class).getByUserName([CtVariableReadImpl]newName);
        [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtInvocationImpl][CtVariableReadImpl]lt.setAllLayoutBlocks([CtVariableReadImpl]b));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean translateTrack([CtParameterImpl][CtTypeReferenceImpl]float xDel, [CtParameterImpl][CtTypeReferenceImpl]float yDel) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D delta = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtVariableReadImpl]xDel, [CtVariableReadImpl]yDel);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter(), [CtVariableReadImpl]delta)));
        [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]true);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * scale all LayoutTracks coordinates by the x and y factors
     *
     * @param xFactor
     * 		the amount to scale X coordinates
     * @param yFactor
     * 		the amount to scale Y coordinates
     */
    public [CtTypeReferenceImpl]boolean scaleTrack([CtParameterImpl][CtTypeReferenceImpl]float xFactor, [CtParameterImpl][CtTypeReferenceImpl]float yFactor) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtInvocationImpl][CtVariableReadImpl]lt.scaleCoords([CtVariableReadImpl]xFactor, [CtVariableReadImpl]yFactor));
        [CtOperatorAssignmentImpl][CtCommentImpl]// update the overall scale factors
        [CtFieldWriteImpl]xScale *= [CtVariableReadImpl]xFactor;
        [CtOperatorAssignmentImpl][CtFieldWriteImpl]yScale *= [CtVariableReadImpl]yFactor;
        [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]true);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * loop through all LayoutBlocks and set colors to the default colors from
     * this LayoutEditor
     *
     * @return count of changed blocks
     */
    public [CtTypeReferenceImpl]int setAllTracksToDefaultColors() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlockManager lbm = [CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.SortedSet<[CtTypeReferenceImpl]LayoutBlock> lBList = [CtInvocationImpl][CtVariableReadImpl]lbm.getNamedBeanSet();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int changed = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock lb : [CtVariableReadImpl]lBList) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lb.setBlockTrackColor([CtInvocationImpl][CtThisAccessImpl]this.getDefaultTrackColorColor());
            [CtInvocationImpl][CtVariableReadImpl]lb.setBlockOccupiedColor([CtInvocationImpl][CtThisAccessImpl]this.getDefaultOccupiedTrackColorColor());
            [CtInvocationImpl][CtVariableReadImpl]lb.setBlockExtraColor([CtInvocationImpl][CtThisAccessImpl]this.getDefaultAlternativeTrackColorColor());
            [CtUnaryOperatorImpl][CtVariableWriteImpl]changed++;
        }
        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.info([CtLiteralImpl]"Track Colors set to default values for {} layoutBlocks.", [CtVariableReadImpl]changed);
        [CtReturnImpl]return [CtVariableReadImpl]changed;
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.awt.geom.Rectangle2D undoRect;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean canUndoMoveSelection = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.awt.geom.Point2D undoDelta = [CtFieldReadImpl]MathUtil.zeroPoint2D;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Translate entire layout by x and y amounts.
     *
     * @param xTranslation
     * 		horizontal (X) translation value
     * @param yTranslation
     * 		vertical (Y) translation value
     */
    public [CtTypeReferenceImpl]void translate([CtParameterImpl][CtTypeReferenceImpl]float xTranslation, [CtParameterImpl][CtTypeReferenceImpl]float yTranslation) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// here when all numbers read in - translation if entered
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]xTranslation != [CtLiteralImpl]0.0F) || [CtBinaryOperatorImpl]([CtVariableReadImpl]yTranslation != [CtLiteralImpl]0.0F)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D delta = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtVariableReadImpl]xTranslation, [CtVariableReadImpl]yTranslation);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D selectionRect = [CtInvocationImpl]getSelectionRect();
            [CtAssignmentImpl][CtCommentImpl]// set up undo information
            [CtFieldWriteImpl]undoRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.offset([CtVariableReadImpl]selectionRect, [CtVariableReadImpl]delta);
            [CtAssignmentImpl][CtFieldWriteImpl]undoDelta = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtTypeAccessImpl]MathUtil.zeroPoint2D, [CtVariableReadImpl]delta);
            [CtAssignmentImpl][CtFieldWriteImpl]canUndoMoveSelection = [CtLiteralImpl]true;
            [CtInvocationImpl][CtFieldReadImpl]undoTranslateSelectionMenuItem.setEnabled([CtFieldReadImpl]canUndoMoveSelection);
            [CtForEachImpl][CtCommentImpl]// apply translation to icon items within the selection
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]Positionable c : [CtFieldReadImpl]_positionableSelection) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]c.getLocation(), [CtVariableReadImpl]delta);
                [CtInvocationImpl][CtVariableReadImpl]c.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newPoint.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newPoint.getY())));
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack lt : [CtFieldReadImpl]_layoutTrackSelection) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter(), [CtVariableReadImpl]delta));
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtFieldReadImpl]_layoutShapeSelection) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter(), [CtVariableReadImpl]delta));
            }
            [CtAssignmentImpl][CtFieldWriteImpl]selectionX = [CtInvocationImpl][CtFieldReadImpl]undoRect.getX();
            [CtAssignmentImpl][CtFieldWriteImpl]selectionY = [CtInvocationImpl][CtFieldReadImpl]undoRect.getY();
            [CtAssignmentImpl][CtFieldWriteImpl]selectionWidth = [CtInvocationImpl][CtFieldReadImpl]undoRect.getWidth();
            [CtAssignmentImpl][CtFieldWriteImpl]selectionHeight = [CtInvocationImpl][CtFieldReadImpl]undoRect.getHeight();
            [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]false);
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * undo the move selection
     */
    [CtTypeReferenceImpl]void undoMoveSelection() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]canUndoMoveSelection) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_positionableSelection.forEach([CtLambdaImpl]([CtParameterImpl] c) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Point2D newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]c.getLocation(), [CtFieldReadImpl][CtFieldReferenceImpl]undoDelta);
                [CtInvocationImpl][CtVariableReadImpl]c.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newPoint.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newPoint.getY())));
            });
            [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter(), [CtFieldReadImpl][CtFieldReferenceImpl]undoDelta)));
            [CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.forEach([CtLambdaImpl]([CtParameterImpl] ls) -> [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter(), [CtFieldReadImpl][CtFieldReferenceImpl]undoDelta)));
            [CtAssignmentImpl][CtFieldWriteImpl]undoRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.offset([CtFieldReadImpl]undoRect, [CtFieldReadImpl]undoDelta);
            [CtAssignmentImpl][CtFieldWriteImpl]selectionX = [CtInvocationImpl][CtFieldReadImpl]undoRect.getX();
            [CtAssignmentImpl][CtFieldWriteImpl]selectionY = [CtInvocationImpl][CtFieldReadImpl]undoRect.getY();
            [CtAssignmentImpl][CtFieldWriteImpl]selectionWidth = [CtInvocationImpl][CtFieldReadImpl]undoRect.getWidth();
            [CtAssignmentImpl][CtFieldWriteImpl]selectionHeight = [CtInvocationImpl][CtFieldReadImpl]undoRect.getHeight();
            [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]false);
            [CtInvocationImpl]redrawPanel();
            [CtAssignmentImpl][CtFieldWriteImpl]canUndoMoveSelection = [CtLiteralImpl]false;
            [CtInvocationImpl][CtFieldReadImpl]undoTranslateSelectionMenuItem.setEnabled([CtFieldReadImpl]canUndoMoveSelection);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Rotate selection by 90 degrees clockwise.
     */
    public [CtTypeReferenceImpl]void rotateSelection90() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D bounds = [CtInvocationImpl]getSelectionRect();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D center = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.midPoint([CtVariableReadImpl]bounds);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Positionable positionable : [CtFieldReadImpl]_positionableSelection) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D cBounds = [CtInvocationImpl][CtVariableReadImpl]positionable.getBounds([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Rectangle());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D oldBottomLeft = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtInvocationImpl][CtVariableReadImpl]cBounds.getMinX(), [CtInvocationImpl][CtVariableReadImpl]cBounds.getMaxY());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D newTopLeft = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.rotateDEG([CtVariableReadImpl]oldBottomLeft, [CtVariableReadImpl]center, [CtLiteralImpl]90);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean rotateFlag = [CtLiteralImpl]true;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]positionable instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]PositionableLabel) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel positionableLabel = [CtVariableReadImpl](([CtTypeReferenceImpl]PositionableLabel) (positionable));
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]positionableLabel.isBackground()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]rotateFlag = [CtLiteralImpl]false;
                }
            }
            [CtIfImpl]if ([CtVariableReadImpl]rotateFlag) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]positionable.rotate([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]positionable.getDegrees() + [CtLiteralImpl]90);
                [CtInvocationImpl][CtVariableReadImpl]positionable.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newTopLeft.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newTopLeft.getY())));
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack lt : [CtFieldReadImpl]_layoutTrackSelection) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.rotateDEG([CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter(), [CtVariableReadImpl]center, [CtLiteralImpl]90));
            [CtInvocationImpl][CtVariableReadImpl]lt.rotateCoords([CtLiteralImpl]90);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtFieldReadImpl]_layoutShapeSelection) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.rotateDEG([CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter(), [CtVariableReadImpl]center, [CtLiteralImpl]90));
            [CtInvocationImpl][CtVariableReadImpl]ls.rotateCoords([CtLiteralImpl]90);
        }
        [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]true);
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Rotate the entire layout by 90 degrees clockwise.
     */
    public [CtTypeReferenceImpl]void rotateLayout90() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> positionables = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl]_contents);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]backgroundImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]blockContentsLabelList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]labelImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]memoryLabelList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]sensorImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]sensorList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]signalHeadImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]signalList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]signalMastList);
        [CtAssignmentImpl][CtCommentImpl]// do this to remove duplicates that may be in more than one list
        [CtVariableWriteImpl]positionables = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]positionables.stream().distinct().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D bounds = [CtInvocationImpl]getPanelBounds();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D lowerLeft = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtInvocationImpl][CtVariableReadImpl]bounds.getMinX(), [CtInvocationImpl][CtVariableReadImpl]bounds.getMaxY());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Positionable positionable : [CtVariableReadImpl]positionables) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D cBounds = [CtInvocationImpl][CtVariableReadImpl]positionable.getBounds([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Rectangle());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D newTopLeft = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtTypeAccessImpl]MathUtil.rotateDEG([CtInvocationImpl][CtVariableReadImpl]positionable.getLocation(), [CtVariableReadImpl]lowerLeft, [CtLiteralImpl]90), [CtVariableReadImpl]lowerLeft);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean reLocateFlag = [CtLiteralImpl]true;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]positionable instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]PositionableLabel) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel positionableLabel = [CtVariableReadImpl](([CtTypeReferenceImpl]PositionableLabel) (positionable));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]positionableLabel.isBackground()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]reLocateFlag = [CtLiteralImpl]false;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]positionableLabel.rotate([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]positionableLabel.getDegrees() + [CtLiteralImpl]90);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException ex) [CtBlockImpl]{
                }
            }
            [CtIfImpl]if ([CtVariableReadImpl]reLocateFlag) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]positionable.setLocation([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtVariableReadImpl]newTopLeft.getX() - [CtInvocationImpl][CtVariableReadImpl]cBounds.getHeight())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newTopLeft.getY())));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException ex) [CtBlockImpl]{
                }
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack lt : [CtFieldReadImpl]layoutTrackList) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtTypeAccessImpl]MathUtil.rotateDEG([CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter(), [CtVariableReadImpl]lowerLeft, [CtLiteralImpl]90), [CtVariableReadImpl]lowerLeft);
                [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtVariableReadImpl]newPoint);
                [CtInvocationImpl][CtVariableReadImpl]lt.rotateCoords([CtLiteralImpl]90);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException ex) [CtBlockImpl]{
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtFieldReadImpl]layoutShapes) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtTypeAccessImpl]MathUtil.rotateDEG([CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter(), [CtVariableReadImpl]lowerLeft, [CtLiteralImpl]90), [CtVariableReadImpl]lowerLeft);
            [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtVariableReadImpl]newPoint);
            [CtInvocationImpl][CtVariableReadImpl]ls.rotateCoords([CtLiteralImpl]90);
        }
        [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]true);
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * align the layout to grid
     */
    public [CtTypeReferenceImpl]void alignLayoutToGrid() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// align to grid
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> positionables = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl]_contents);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]backgroundImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]blockContentsLabelList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]labelImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]memoryLabelList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]sensorImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]sensorList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]signalHeadImage);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]signalList);
        [CtInvocationImpl][CtVariableReadImpl]positionables.addAll([CtFieldReadImpl]signalMastList);
        [CtAssignmentImpl][CtCommentImpl]// do this to remove duplicates that may be in more than one list
        [CtVariableWriteImpl]positionables = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]positionables.stream().distinct().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl]alignToGrid([CtVariableReadImpl]positionables, [CtFieldReadImpl]layoutTrackList, [CtFieldReadImpl]layoutShapes);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * align selection to grid
     */
    public [CtTypeReferenceImpl]void alignSelectionToGrid() [CtBlockImpl]{
        [CtInvocationImpl]alignToGrid([CtFieldReadImpl]_positionableSelection, [CtFieldReadImpl]_layoutTrackSelection, [CtFieldReadImpl]_layoutShapeSelection);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void alignToGrid([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> positionables, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutTrack> tracks, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutShape> shapes) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Positionable positionable : [CtVariableReadImpl]positionables) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D newLocation = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtInvocationImpl][CtVariableReadImpl]positionable.getLocation(), [CtFieldReadImpl]gridSize1st);
            [CtInvocationImpl][CtVariableReadImpl]positionable.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newLocation.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newLocation.getY())));
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack lt : [CtVariableReadImpl]tracks) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter(), [CtFieldReadImpl]gridSize1st));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurntable) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable tt = [CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (lt));
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]LayoutTurntable.RayTrack rt : [CtInvocationImpl][CtVariableReadImpl]tt.getRayList()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int rayIndex = [CtInvocationImpl][CtVariableReadImpl]rt.getConnectionIndex();
                    [CtInvocationImpl][CtVariableReadImpl]tt.setRayCoordsIndexed([CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtInvocationImpl][CtVariableReadImpl]tt.getRayCoordsIndexed([CtVariableReadImpl]rayIndex), [CtFieldReadImpl]gridSize1st), [CtVariableReadImpl]rayIndex);
                }
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtVariableReadImpl]shapes) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter(), [CtFieldReadImpl]gridSize1st));
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtVariableReadImpl]ls.getNumberPoints(); [CtUnaryOperatorImpl][CtVariableWriteImpl]idx++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ls.setPoint([CtVariableReadImpl]idx, [CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtInvocationImpl][CtVariableReadImpl]ls.getPoint([CtVariableReadImpl]idx), [CtFieldReadImpl]gridSize1st));
            }
        }
        [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]true);
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setCurrentPositionAndSize() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// save current panel location and size
        [CtTypeReferenceImpl]java.awt.Dimension dim = [CtInvocationImpl]getSize();
        [CtAssignmentImpl][CtCommentImpl]// Compute window size based on LayoutEditor size
        [CtFieldWriteImpl]windowHeight = [CtFieldReadImpl][CtVariableReadImpl]dim.height;
        [CtAssignmentImpl][CtFieldWriteImpl]windowWidth = [CtFieldReadImpl][CtVariableReadImpl]dim.width;
        [CtAssignmentImpl][CtCommentImpl]// Compute layout size based on LayoutPane size
        [CtVariableWriteImpl]dim = [CtInvocationImpl]getTargetPanelSize();
        [CtAssignmentImpl][CtFieldWriteImpl]panelWidth = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl][CtVariableReadImpl]dim.width / [CtInvocationImpl]getZoom()));
        [CtAssignmentImpl][CtFieldWriteImpl]panelHeight = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl][CtVariableReadImpl]dim.height / [CtInvocationImpl]getZoom()));
        [CtInvocationImpl]adjustScrollBars();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Point pt = [CtInvocationImpl]getLocationOnScreen();
        [CtAssignmentImpl][CtFieldWriteImpl]upperLeftX = [CtFieldReadImpl][CtVariableReadImpl]pt.x;
        [CtAssignmentImpl][CtFieldWriteImpl]upperLeftY = [CtFieldReadImpl][CtVariableReadImpl]pt.y;
        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"setCurrentPositionAndSize Position - {},{} WindowSize - {},{} PanelSize - {},{}", [CtFieldReadImpl]upperLeftX, [CtFieldReadImpl]upperLeftY, [CtFieldReadImpl]windowWidth, [CtFieldReadImpl]windowHeight, [CtFieldReadImpl]panelWidth, [CtFieldReadImpl]panelHeight);
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem addButtonGroupMenuEntry([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenu inMenu, [CtParameterImpl][CtTypeReferenceImpl]javax.swing.ButtonGroup inButtonGroup, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String inName, [CtParameterImpl][CtTypeReferenceImpl]boolean inSelected, [CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionListener inActionListener) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JRadioButtonMenuItem([CtVariableReadImpl]inName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inActionListener != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.addActionListener([CtVariableReadImpl]inActionListener);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inButtonGroup != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]inButtonGroup.add([CtVariableReadImpl]result);
        }
        [CtInvocationImpl][CtVariableReadImpl]result.setSelected([CtVariableReadImpl]inSelected);
        [CtInvocationImpl][CtVariableReadImpl]inMenu.add([CtVariableReadImpl]result);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addTurnoutCircleSizeMenuEntry([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenu inMenu, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String inName, [CtParameterImpl]final [CtTypeReferenceImpl]int inSize) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.event.ActionListener a = [CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getTurnoutCircleSize() != [CtVariableReadImpl]inSize) [CtBlockImpl]{
                [CtInvocationImpl]setTurnoutCircleSize([CtVariableReadImpl]inSize);
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
        };
        [CtInvocationImpl]addButtonGroupMenuEntry([CtVariableReadImpl]inMenu, [CtFieldReadImpl]turnoutCircleSizeButtonGroup, [CtVariableReadImpl]inName, [CtBinaryOperatorImpl][CtInvocationImpl]getTurnoutCircleSize() == [CtVariableReadImpl]inSize, [CtVariableReadImpl]a);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void setOptionMenuTurnoutCircleSize() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tcs = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtInvocationImpl]getTurnoutCircleSize());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration<[CtTypeReferenceImpl]javax.swing.AbstractButton> e = [CtInvocationImpl][CtFieldReadImpl]turnoutCircleSizeButtonGroup.getElements();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.AbstractButton button = [CtInvocationImpl][CtVariableReadImpl]e.nextElement();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String buttonName = [CtInvocationImpl][CtVariableReadImpl]button.getText();
            [CtInvocationImpl][CtVariableReadImpl]button.setSelected([CtInvocationImpl][CtVariableReadImpl]buttonName.equals([CtVariableReadImpl]tcs));
        } 
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setScroll([CtParameterImpl][CtTypeReferenceImpl]int state) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// In edit mode the scroll bars are always displayed, however we will want to set the scroll for when we exit edit mode
            [CtSuperAccessImpl]super.setScroll([CtTypeAccessImpl]Editor.SCROLL_BOTH);
            [CtAssignmentImpl][CtFieldWriteImpl]_scrollState = [CtVariableReadImpl]state;
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.setScroll([CtVariableReadImpl]state);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a layout turntable at location specified
     *
     * @param pt
     * 		x,y placement for turntable
     */
    public [CtTypeReferenceImpl]void addTurntable([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D pt) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"TUR", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numLayoutTurntables);
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable lt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutTurntable([CtVariableReadImpl]name, [CtVariableReadImpl]pt, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtVariableReadImpl]lt);
        [CtInvocationImpl][CtVariableReadImpl]lt.addRay([CtLiteralImpl]0.0);
        [CtInvocationImpl][CtVariableReadImpl]lt.addRay([CtLiteralImpl]90.0);
        [CtInvocationImpl][CtVariableReadImpl]lt.addRay([CtLiteralImpl]180.0);
        [CtInvocationImpl][CtVariableReadImpl]lt.addRay([CtLiteralImpl]270.0);
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]lt.getBounds());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Allow external trigger of re-drawHidden
     */
    public [CtTypeReferenceImpl]void redrawPanel() [CtBlockImpl]{
        [CtInvocationImpl]repaint();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Allow external set/reset of awaitingIconChange
     */
    public [CtTypeReferenceImpl]void setAwaitingIconChange() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]awaitingIconChange = [CtLiteralImpl]true;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void resetAwaitingIconChange() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]awaitingIconChange = [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Allow external reset of dirty bit
     */
    public [CtTypeReferenceImpl]void resetDirty() [CtBlockImpl]{
        [CtInvocationImpl]setDirty([CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]savedEditMode = [CtInvocationImpl]isEditable();
        [CtAssignmentImpl][CtFieldWriteImpl]savedPositionable = [CtInvocationImpl]allPositionable();
        [CtAssignmentImpl][CtFieldWriteImpl]savedControlLayout = [CtInvocationImpl]allControlling();
        [CtAssignmentImpl][CtFieldWriteImpl]savedAnimatingLayout = [CtInvocationImpl]isAnimating();
        [CtAssignmentImpl][CtFieldWriteImpl]savedShowHelpBar = [CtInvocationImpl]getShowHelpBar();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Allow external set of dirty bit
     *
     * @param val
     * 		true/false for panelChanged
     */
    public [CtTypeReferenceImpl]void setDirty([CtParameterImpl][CtTypeReferenceImpl]boolean val) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]panelChanged = [CtVariableReadImpl]val;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDirty() [CtBlockImpl]{
        [CtInvocationImpl]setDirty([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check the dirty state
     *
     * @return true if panel has changed
     */
    public [CtTypeReferenceImpl]boolean isDirty() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]panelChanged;
    }

    [CtMethodImpl][CtCommentImpl]/* Get mouse coordinates and adjust for zoom.
    <p>
    Side effects on xLoc, yLoc and dLoc
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    private [CtTypeReferenceImpl]java.awt.geom.Point2D calcLocation([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent event, [CtParameterImpl][CtTypeReferenceImpl]int dX, [CtParameterImpl][CtTypeReferenceImpl]int dY) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]xLoc = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getX() + [CtVariableReadImpl]dX) / [CtInvocationImpl]getZoom()));
        [CtAssignmentImpl][CtFieldWriteImpl]yLoc = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getY() + [CtVariableReadImpl]dY) / [CtInvocationImpl]getZoom()));
        [CtInvocationImpl][CtFieldReadImpl]dLoc.setLocation([CtFieldReadImpl]xLoc, [CtFieldReadImpl]yLoc);
        [CtReturnImpl]return [CtFieldReadImpl]dLoc;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.awt.geom.Point2D calcLocation([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]calcLocation([CtVariableReadImpl]event, [CtLiteralImpl]0, [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle a mouse pressed event
     * <p>
     * Side-effects on _anchorX, _anchorY,_lastX, _lastY, xLoc, yLoc, dLoc,
     * selectionActive, xLabel, yLabel
     *
     * @param event
     * 		the MouseEvent
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void mousePressed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// initialize cursor position
        [CtFieldWriteImpl]_anchorX = [CtFieldReadImpl]xLoc;
        [CtAssignmentImpl][CtFieldWriteImpl]_anchorY = [CtFieldReadImpl]yLoc;
        [CtAssignmentImpl][CtFieldWriteImpl]_lastX = [CtFieldReadImpl]_anchorX;
        [CtAssignmentImpl][CtFieldWriteImpl]_lastY = [CtFieldReadImpl]_anchorY;
        [CtInvocationImpl]calcLocation([CtVariableReadImpl]event);
        [CtIfImpl][CtCommentImpl]// TODO: Add command-click on nothing to pan view?
        if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean prevSelectionActive = [CtFieldReadImpl]selectionActive;
            [CtAssignmentImpl][CtFieldWriteImpl]selectionActive = [CtLiteralImpl]false;
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.xLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]xLoc));
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.yLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]yLoc));
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isMetaDown([CtVariableReadImpl]event) || [CtInvocationImpl][CtVariableReadImpl]event.isAltDown()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// if requesting a popup and it might conflict with moving, delay the request to mouseReleased
                    [CtFieldWriteImpl]delayedPopupTrigger = [CtLiteralImpl]true;
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// no possible conflict with moving, display the popup now
                    showEditPopUps([CtVariableReadImpl]event);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isMetaDown([CtVariableReadImpl]event) || [CtInvocationImpl][CtVariableReadImpl]event.isAltDown()) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// if dragging an item, identify the item for mouseDragging
                [CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE;
                [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtFieldReadImpl]dLoc)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtFieldReadImpl]foundTrack;
                    [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl]foundHitPointType;
                    [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtFieldReadImpl]foundLocation, [CtFieldReadImpl]dLoc));
                    [CtAssignmentImpl][CtFieldWriteImpl]foundTrack = [CtLiteralImpl]null;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkMarkerPopUps([CtFieldReadImpl]dLoc);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]MARKER;
                        [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LocoIcon) (selectedObject)).getLocation(), [CtFieldReadImpl]dLoc));
                    } else [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkClockPopUps([CtFieldReadImpl]dLoc);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]LAYOUT_POS_JCOMP;
                            [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]PositionableJComponent) (selectedObject)).getLocation(), [CtFieldReadImpl]dLoc));
                        } else [CtBlockImpl]{
                            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkMultiSensorPopUps([CtFieldReadImpl]dLoc);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]MULTI_SENSOR;
                                [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]MultiSensorIcon) (selectedObject)).getLocation(), [CtFieldReadImpl]dLoc));
                            }
                        }
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkSensorIconPopUps([CtFieldReadImpl]dLoc);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkSignalHeadIconPopUps([CtFieldReadImpl]dLoc);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkLabelImagePopUps([CtFieldReadImpl]dLoc);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtLiteralImpl]null) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkSignalMastIconPopUps([CtFieldReadImpl]dLoc);
                                }
                            }
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]LAYOUT_POS_LABEL;
                            [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]PositionableLabel) (selectedObject)).getLocation(), [CtFieldReadImpl]dLoc));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]MemoryIcon) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]MemoryIcon pm = [CtFieldReadImpl](([CtTypeReferenceImpl]MemoryIcon) (selectedObject));
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pm.getPopupUtility().getFixedWidth() == [CtLiteralImpl]0) [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pm.getOriginalX() - [CtInvocationImpl][CtFieldReadImpl]dLoc.getX(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pm.getOriginalY() - [CtInvocationImpl][CtFieldReadImpl]dLoc.getY());
                                }
                            }
                        } else [CtBlockImpl]{
                            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkBackgroundPopUps([CtFieldReadImpl]dLoc);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]LAYOUT_POS_LABEL;
                                [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]PositionableLabel) (selectedObject)).getLocation(), [CtFieldReadImpl]dLoc));
                            } else [CtBlockImpl]{
                                [CtLocalVariableImpl][CtCommentImpl]// dragging a shape?
                                [CtTypeReferenceImpl]java.util.ListIterator<[CtTypeReferenceImpl]LayoutShape> listIterator = [CtInvocationImpl][CtFieldReadImpl]layoutShapes.listIterator([CtInvocationImpl][CtFieldReadImpl]layoutShapes.size());
                                [CtWhileImpl][CtCommentImpl]// hit test in front to back order (reverse order of list)
                                while ([CtInvocationImpl][CtVariableReadImpl]listIterator.hasPrevious()) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls = [CtInvocationImpl][CtVariableReadImpl]listIterator.previous();
                                    [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtInvocationImpl][CtVariableReadImpl]ls.findHitPointType([CtFieldReadImpl]dLoc, [CtLiteralImpl]true);
                                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]LayoutShape.isShapeHitPointType([CtFieldReadImpl]selectedHitPointType)) [CtBlockImpl]{
                                        [CtAssignmentImpl][CtCommentImpl]// log.warn("drag selectedObject: ", lt);
                                        [CtFieldWriteImpl]selectedObject = [CtVariableReadImpl]ls;[CtCommentImpl]// found one!

                                        [CtInvocationImpl][CtFieldReadImpl]beginLocation.setLocation([CtFieldReadImpl]dLoc);
                                        [CtInvocationImpl][CtFieldReadImpl]currentLocation.setLocation([CtFieldReadImpl]beginLocation);
                                        [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtTypeAccessImpl]MathUtil.zeroPoint2D);
                                        [CtBreakImpl]break;
                                    }
                                } 
                            }
                        }
                    }
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.isShiftDown() && [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.trackButton.isSelected()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// starting a Track Segment, check for free connection point
                [CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
                [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtFieldReadImpl]dLoc, [CtLiteralImpl]true)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// match to a free connection point
                    [CtFieldWriteImpl]beginTrack = [CtFieldReadImpl]foundTrack;
                    [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl]foundHitPointType;
                    [CtInvocationImpl][CtFieldReadImpl]beginLocation.setLocation([CtFieldReadImpl]foundLocation);
                    [CtInvocationImpl][CtCommentImpl]// BUGFIX: prevents initial drawTrackSegmentInProgress to {0, 0}
                    [CtFieldReadImpl]currentLocation.setLocation([CtFieldReadImpl]beginLocation);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// TODO: auto-add anchor point?
                    [CtFieldWriteImpl]beginTrack = [CtLiteralImpl]null;
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.isShiftDown() && [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.shapeButton.isSelected()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// adding or extending a shape
                [CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;[CtCommentImpl]// assume we're adding...

                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtFieldReadImpl]layoutShapes) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtInvocationImpl][CtVariableReadImpl]ls.findHitPointType([CtFieldReadImpl]dLoc, [CtLiteralImpl]true);
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]LayoutShape.isShapePointOffsetHitPointType([CtFieldReadImpl]selectedHitPointType)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// log.warn("extend selectedObject: ", lt);
                        [CtFieldWriteImpl]selectedObject = [CtVariableReadImpl]ls;[CtCommentImpl]// nope, we're extending

                        [CtInvocationImpl][CtFieldReadImpl]beginLocation.setLocation([CtFieldReadImpl]dLoc);
                        [CtInvocationImpl][CtFieldReadImpl]currentLocation.setLocation([CtFieldReadImpl]beginLocation);
                        [CtBreakImpl]break;
                    }
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// check if controlling a turnout in edit mode
                [CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
                [CtIfImpl]if ([CtInvocationImpl]allControlling()) [CtBlockImpl]{
                    [CtInvocationImpl]checkControls([CtLiteralImpl]false);
                }
                [CtAssignmentImpl][CtCommentImpl]// initialize starting selection - cancel any previous selection rectangle
                [CtFieldWriteImpl]selectionActive = [CtLiteralImpl]true;
                [CtAssignmentImpl][CtFieldWriteImpl]selectionX = [CtInvocationImpl][CtFieldReadImpl]dLoc.getX();
                [CtAssignmentImpl][CtFieldWriteImpl]selectionY = [CtInvocationImpl][CtFieldReadImpl]dLoc.getY();
                [CtAssignmentImpl][CtFieldWriteImpl]selectionWidth = [CtLiteralImpl]0.0;
                [CtAssignmentImpl][CtFieldWriteImpl]selectionHeight = [CtLiteralImpl]0.0;
            }
            [CtIfImpl]if ([CtVariableReadImpl]prevSelectionActive) [CtBlockImpl]{
                [CtInvocationImpl]redrawPanel();
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]allControlling() && [CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// not in edit mode - check if mouse is on a turnout (using wider search range)
            [CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
            [CtInvocationImpl]checkControls([CtLiteralImpl]true);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]isMetaDown([CtVariableReadImpl]event) || [CtInvocationImpl][CtVariableReadImpl]event.isAltDown()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// not in edit mode - check if moving a marker if there are any
            [CtFieldWriteImpl]selectedObject = [CtInvocationImpl]checkMarkerPopUps([CtFieldReadImpl]dLoc);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]MARKER;
                [CtInvocationImpl][CtFieldReadImpl]startDelta.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LocoIcon) (selectedObject)).getLocation(), [CtFieldReadImpl]dLoc));
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// not in edit mode - check if a marker popup menu is being requested
            [CtTypeReferenceImpl]LocoIcon lo = [CtInvocationImpl]checkMarkerPopUps([CtFieldReadImpl]dLoc);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lo != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]delayedPopupTrigger = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> selections = [CtInvocationImpl]getSelectedItems([CtVariableReadImpl]event);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selections.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]selections.get([CtLiteralImpl]0).doMousePressed([CtVariableReadImpl]event);
            }
        }
        [CtInvocationImpl]requestFocusInWindow();
    }[CtCommentImpl]// mousePressed


    [CtMethodImpl][CtCommentImpl]// this is a method to iterate over a list of lists of items
    [CtCommentImpl]// calling the predicate tester.test on each one
    [CtCommentImpl]// all matching items are then added to the resulting List
    [CtCommentImpl]// note: currently unused; commented out to avoid findbugs warning
    [CtCommentImpl]// private static List testEachItemInListOfLists(
    [CtCommentImpl]// @Nonnull List<List> listOfListsOfObjects,
    [CtCommentImpl]// @Nonnull Predicate<Object> tester) {
    [CtCommentImpl]// List result = new ArrayList<>();
    [CtCommentImpl]// for (List<Object> listOfObjects : listOfListsOfObjects) {
    [CtCommentImpl]// List<Object> l = listOfObjects.stream().filter(o -> tester.test(o)).collect(Collectors.toList());
    [CtCommentImpl]// result.addAll(l);
    [CtCommentImpl]// }
    [CtCommentImpl]// return result;
    [CtCommentImpl]// }
    [CtCommentImpl]// this is a method to iterate over a list of lists of items
    [CtCommentImpl]// calling the predicate tester.test on each one
    [CtCommentImpl]// and return the first one that matches
    [CtCommentImpl]// TODO: make this public? (it is useful! ;-)
    [CtCommentImpl]// note: currently unused; commented out to avoid findbugs warning
    [CtCommentImpl]// private static Object findFirstMatchingItemInListOfLists(
    [CtCommentImpl]// @Nonnull List<List> listOfListsOfObjects,
    [CtCommentImpl]// @Nonnull Predicate<Object> tester) {
    [CtCommentImpl]// Object result = null;
    [CtCommentImpl]// for (List listOfObjects : listOfListsOfObjects) {
    [CtCommentImpl]// Optional<Object> opt = listOfObjects.stream().filter(o -> tester.test(o)).findFirst();
    [CtCommentImpl]// if (opt.isPresent()) {
    [CtCommentImpl]// result = opt.get();
    [CtCommentImpl]// break;
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// return result;
    [CtCommentImpl]// }
    [CtJavaDocImpl]/**
     * Called by {@link #mousePressed} to determine if the mouse click was in a
     * turnout control location. If so, update selectedHitPointType and
     * selectedObject for use by {@link #mouseReleased}.
     * <p>
     * If there's no match, selectedObject is set to null and
     * selectedHitPointType is left referring to the results of the checking the
     * last track on the list.
     * <p>
     * Refers to the current value of {@link #layoutTrackList} and
     * {@link #dLoc}.
     *
     * @param useRectangles
     * 		set true to use rectangle; false for circles.
     */
    private [CtTypeReferenceImpl]void checkControls([CtParameterImpl][CtTypeReferenceImpl]boolean useRectangles) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;[CtCommentImpl]// deliberate side-effect

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack theTrack : [CtFieldReadImpl]layoutTrackList) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtInvocationImpl][CtVariableReadImpl]theTrack.findHitPointType([CtFieldReadImpl]dLoc, [CtVariableReadImpl]useRectangles);[CtCommentImpl]// deliberate side-effect

            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isControlHitType([CtFieldReadImpl]selectedHitPointType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtVariableReadImpl]theTrack;[CtCommentImpl]// deliberate side-effect

                [CtReturnImpl]return;
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]// optional parameter avoid
    private [CtTypeReferenceImpl]boolean findLayoutTracksHitPoint([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc, [CtParameterImpl][CtTypeReferenceImpl]boolean requireUnconnected) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]findLayoutTracksHitPoint([CtVariableReadImpl]loc, [CtVariableReadImpl]requireUnconnected, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtCommentImpl]// optional parameter requireUnconnected
    private [CtTypeReferenceImpl]boolean findLayoutTracksHitPoint([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]findLayoutTracksHitPoint([CtVariableReadImpl]loc, [CtLiteralImpl]false, [CtLiteralImpl]null);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean findLayoutTracksHitPoint([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc, [CtParameterImpl][CtTypeReferenceImpl]boolean requireUnconnected, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]LayoutTrack avoid) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;[CtCommentImpl]// assume failure (pessimist!)

        [CtAssignmentImpl][CtFieldWriteImpl]foundTrack = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]foundHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]LayoutTrack> opt = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]layoutTrackList.stream().filter([CtLambdaImpl]([CtParameterImpl] layoutTrack) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]layoutTrack != [CtVariableReadImpl]avoid) && [CtBinaryOperatorImpl]([CtVariableReadImpl]layoutTrack != [CtFieldReadImpl][CtFieldReferenceImpl]selectedObject)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]foundHitPointType = [CtInvocationImpl][CtVariableReadImpl]layoutTrack.findHitPointType([CtVariableReadImpl]loc, [CtLiteralImpl]false, [CtVariableReadImpl]requireUnconnected);
            }
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]HitPointType.NONE != [CtFieldReadImpl][CtFieldReferenceImpl]foundHitPointType;
        }).findFirst();
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack layoutTrack = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]opt.isPresent()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]layoutTrack = [CtInvocationImpl][CtVariableReadImpl]opt.get();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]layoutTrack != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]foundTrack = [CtVariableReadImpl]layoutTrack;
            [CtAssignmentImpl][CtFieldWriteImpl]foundLocation = [CtInvocationImpl][CtVariableReadImpl]layoutTrack.getCoordsForConnectionType([CtFieldReadImpl]foundHitPointType);
            [CtAssignmentImpl][CtCommentImpl]// /foundNeedsConnect = isDisconnected(foundHitPointType);
            [CtVariableWriteImpl]result = [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.TrackSegment checkTrackSegmentPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment result = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]// NOTE: Rather than calculate all the hit rectangles for all
        [CtCommentImpl]// the points below and test if this location is in any of those
        [CtCommentImpl]// rectangles just create a hit rectangle for the location and
        [CtCommentImpl]// see if any of the points below are in it instead...
        [CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl]layoutEditorControlCircleRectAt([CtVariableReadImpl]loc);
        [CtForEachImpl][CtCommentImpl]// check Track Segments, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment ts : [CtInvocationImpl]getTrackSegments()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtInvocationImpl][CtVariableReadImpl]ts.getCentreSeg())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]ts;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.PositionableLabel checkBackgroundPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel result = [CtLiteralImpl]null;
        [CtForImpl][CtCommentImpl]// check background images, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]backgroundImage.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel b = [CtInvocationImpl][CtFieldReadImpl]backgroundImage.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl][CtVariableReadImpl]b.getBounds();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]b;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.SensorIcon checkSensorIconPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SensorIcon result = [CtLiteralImpl]null;
        [CtForImpl][CtCommentImpl]// check sensor images, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sensorImage.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]SensorIcon s = [CtInvocationImpl][CtFieldReadImpl]sensorImage.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl][CtVariableReadImpl]s.getBounds();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]s;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.SignalHeadIcon checkSignalHeadIconPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalHeadIcon result = [CtLiteralImpl]null;
        [CtForImpl][CtCommentImpl]// check signal head images, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]signalHeadImage.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]SignalHeadIcon s = [CtInvocationImpl][CtFieldReadImpl]signalHeadImage.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl][CtVariableReadImpl]s.getBounds();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]s;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.SignalMastIcon checkSignalMastIconPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMastIcon result = [CtLiteralImpl]null;
        [CtForImpl][CtCommentImpl]// check signal head images, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]signalMastList.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMastIcon s = [CtInvocationImpl][CtFieldReadImpl]signalMastList.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl][CtVariableReadImpl]s.getBounds();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]s;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.PositionableLabel checkLabelImagePopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel result = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int level = [CtLiteralImpl]0;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]labelImage.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel s = [CtInvocationImpl][CtFieldReadImpl]labelImage.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]double x = [CtInvocationImpl][CtVariableReadImpl]s.getX();
            [CtLocalVariableImpl][CtTypeReferenceImpl]double y = [CtInvocationImpl][CtVariableReadImpl]s.getY();
            [CtLocalVariableImpl][CtTypeReferenceImpl]double w = [CtLiteralImpl]10.0;
            [CtLocalVariableImpl][CtTypeReferenceImpl]double h = [CtLiteralImpl]5.0;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]s.isIcon() || [CtInvocationImpl][CtVariableReadImpl]s.isRotated()) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.getPopupUtility().getOrientation() != [CtFieldReadImpl]PositionablePopupUtil.HORIZONTAL)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]w = [CtInvocationImpl][CtVariableReadImpl]s.maxWidth();
                [CtAssignmentImpl][CtVariableWriteImpl]h = [CtInvocationImpl][CtVariableReadImpl]s.maxHeight();
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]s.isText()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]h = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.getFont().getSize();
                [CtAssignmentImpl][CtVariableWriteImpl]w = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]h * [CtLiteralImpl]2) * [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.getText().length()) / [CtLiteralImpl]3;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D.Double([CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtVariableReadImpl]w, [CtVariableReadImpl]h);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]s.getDisplayLevel() >= [CtVariableReadImpl]level) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Check to make sure that we are returning the highest level label.
                    [CtVariableWriteImpl]result = [CtVariableReadImpl]s;
                    [CtAssignmentImpl][CtVariableWriteImpl]level = [CtInvocationImpl][CtVariableReadImpl]s.getDisplayLevel();
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.AnalogClock2Display checkClockPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]AnalogClock2Display result = [CtLiteralImpl]null;
        [CtForImpl][CtCommentImpl]// check clocks, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]clocks.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]AnalogClock2Display s = [CtInvocationImpl][CtFieldReadImpl]clocks.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl][CtVariableReadImpl]s.getBounds();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]s;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.MultiSensorIcon checkMultiSensorPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]MultiSensorIcon result = [CtLiteralImpl]null;
        [CtForImpl][CtCommentImpl]// check multi sensor icons, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]multiSensors.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]MultiSensorIcon s = [CtInvocationImpl][CtFieldReadImpl]multiSensors.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl][CtVariableReadImpl]s.getBounds();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]s;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LocoIcon checkMarkerPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LocoIcon result = [CtLiteralImpl]null;
        [CtForImpl][CtCommentImpl]// check marker icons, if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]markerImage.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]LocoIcon l = [CtInvocationImpl][CtFieldReadImpl]markerImage.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D r = [CtInvocationImpl][CtVariableReadImpl]l.getBounds();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.contains([CtVariableReadImpl]loc)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// mouse was pressed in marker icon
                [CtVariableWriteImpl]result = [CtVariableReadImpl]l;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutShape checkLayoutShapePopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape result = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtFieldReadImpl]layoutShapes) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtInvocationImpl][CtVariableReadImpl]ls.findHitPointType([CtVariableReadImpl]loc, [CtLiteralImpl]true);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]LayoutShape.isShapeHitPointType([CtFieldReadImpl]selectedHitPointType)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]ls;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * get the coordinates for the connection type of the specified object
     *
     * @param layoutTrack
     * 		the object (Layout track subclass)
     * @param connectionType
     * 		the type of connection
     * @return the coordinates for the connection type of the specified object
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public static [CtTypeReferenceImpl]java.awt.geom.Point2D getCoords([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTrack layoutTrack, [CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType connectionType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]layoutTrack.getCoordsForConnectionType([CtVariableReadImpl]connectionType);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void mouseReleased([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.setToolTip([CtLiteralImpl]null);
        [CtInvocationImpl][CtCommentImpl]// initialize mouse position
        calcLocation([CtVariableReadImpl]event);
        [CtAssignmentImpl][CtCommentImpl]// if alt modifier is down invert the snap to grid behaviour
        [CtFieldWriteImpl]snapToGridInvert = [CtInvocationImpl][CtVariableReadImpl]event.isAltDown();
        [CtIfImpl]if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.xLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]xLoc));
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.yLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]yLoc));
            [CtIfImpl][CtCommentImpl]// released the mouse with shift down... see what we're adding
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger()) && [CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event))) && [CtInvocationImpl][CtVariableReadImpl]event.isShiftDown()) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]currentPoint = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtFieldReadImpl]xLoc, [CtFieldReadImpl]yLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]snapToGridOnAdd != [CtFieldReadImpl]snapToGridInvert) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// this snaps the current point to the grid
                    [CtFieldWriteImpl]currentPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtFieldReadImpl]currentPoint, [CtFieldReadImpl]gridSize1st);
                    [CtAssignmentImpl][CtFieldWriteImpl]xLoc = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getX()));
                    [CtAssignmentImpl][CtFieldWriteImpl]yLoc = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getY()));
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.xLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]xLoc));
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.yLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]yLoc));
                }
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutRHButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutTurnout([CtTypeAccessImpl]LayoutTurnout.TurnoutType.RH_TURNOUT);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutLHButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutTurnout([CtTypeAccessImpl]LayoutTurnout.TurnoutType.LH_TURNOUT);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutWYEButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutTurnout([CtTypeAccessImpl]LayoutTurnout.TurnoutType.WYE_TURNOUT);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.doubleXoverButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutTurnout([CtTypeAccessImpl]LayoutTurnout.TurnoutType.DOUBLE_XOVER);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.rhXoverButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutTurnout([CtTypeAccessImpl]LayoutTurnout.TurnoutType.RH_XOVER);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.lhXoverButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutTurnout([CtTypeAccessImpl]LayoutTurnout.TurnoutType.LH_XOVER);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.levelXingButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLevelXing();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.layoutSingleSlipButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutSlip([CtTypeAccessImpl]LayoutSlip.TurnoutType.SINGLE_SLIP);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.layoutDoubleSlipButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLayoutSlip([CtTypeAccessImpl]LayoutSlip.TurnoutType.DOUBLE_SLIP);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.endBumperButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addEndBumper();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.anchorButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addAnchor();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.edgeButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addEdgeConnector();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.trackButton.isSelected()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]beginTrack != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]foundTrack != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]beginTrack != [CtFieldReadImpl]foundTrack)) [CtBlockImpl]{
                        [CtInvocationImpl]addTrackSegment();
                        [CtInvocationImpl]setCursor([CtInvocationImpl][CtTypeAccessImpl]java.awt.Cursor.getDefaultCursor());
                    }
                    [CtAssignmentImpl][CtFieldWriteImpl]beginTrack = [CtLiteralImpl]null;
                    [CtAssignmentImpl][CtFieldWriteImpl]foundTrack = [CtLiteralImpl]null;
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]startMultiSensor();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addSensor();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addSignalHead();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.textLabelButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addLabel();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.memoryButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addMemory();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockContentsButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addBlockContents();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.iconLabelButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addIcon();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.shapeButton.isSelected()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl]addLayoutShape([CtFieldReadImpl]currentPoint);
                        [CtInvocationImpl]setCursor([CtInvocationImpl][CtTypeAccessImpl]java.awt.Cursor.getDefaultCursor());
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutShape) (selectedObject));
                        [CtInvocationImpl][CtVariableReadImpl]ls.addPoint([CtFieldReadImpl]currentPoint, [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]selectedHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SHAPE_POINT_0.getXmlValue());
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalMastButton.isSelected()) [CtBlockImpl]{
                    [CtInvocationImpl]addSignalMast();
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtLiteralImpl]"No item selected in panel edit mode");
                }
                [CtAssignmentImpl][CtCommentImpl]// resizePanelBounds(false);
                [CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
                [CtInvocationImpl]redrawPanel();
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger() || [CtFieldReadImpl]delayedPopupTrigger) && [CtUnaryOperatorImpl](![CtFieldReadImpl]isDragging)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE;
                [CtAssignmentImpl][CtFieldWriteImpl]whenReleased = [CtInvocationImpl][CtVariableReadImpl]event.getWhen();
                [CtInvocationImpl]showEditPopUps([CtVariableReadImpl]event);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_CENTER)) && [CtInvocationImpl]allControlling()) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// controlling turnouts, in edit mode
                [CtTypeReferenceImpl]LayoutTurnout t = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject));
                [CtInvocationImpl][CtVariableReadImpl]t.toggleTurnout();
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_LEFT) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_RIGHT))) && [CtInvocationImpl]allControlling()) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// controlling slips, in edit mode
                [CtTypeReferenceImpl]LayoutSlip sl = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutSlip) (selectedObject));
                [CtInvocationImpl][CtVariableReadImpl]sl.toggleState([CtFieldReadImpl]selectedHitPointType);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtFieldReadImpl]selectedHitPointType)) && [CtInvocationImpl]allControlling()) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// controlling turntable, in edit mode
                [CtTypeReferenceImpl]LayoutTurntable t = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (selectedObject));
                [CtInvocationImpl][CtVariableReadImpl]t.setPosition([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]selectedHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0.getXmlValue());
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_CENTER) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_CENTER)) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_LEFT)) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_RIGHT))) && [CtInvocationImpl]allControlling()) && [CtBinaryOperatorImpl]([CtInvocationImpl]isMetaDown([CtVariableReadImpl]event) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) && [CtFieldReadImpl]isDragging) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// We just dropped a turnout (or slip)... see if it will connect to anything
                hitPointCheckLayoutTurnouts([CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject)));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]POS_POINT)) && [CtInvocationImpl]allControlling()) && [CtInvocationImpl]isMetaDown([CtVariableReadImpl]event)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) && [CtFieldReadImpl]isDragging) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// We just dropped a PositionablePoint... see if it will connect to anything
                [CtTypeReferenceImpl]PositionablePoint p = [CtFieldReadImpl](([CtTypeReferenceImpl]PositionablePoint) (selectedObject));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect1() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect2() == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtInvocationImpl]checkPointOfPositionable([CtVariableReadImpl]p);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.trackButton.isSelected() && [CtBinaryOperatorImpl]([CtFieldReadImpl]beginTrack != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]foundTrack != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// user let up shift key before releasing the mouse when creating a track segment
                setCursor([CtInvocationImpl][CtTypeAccessImpl]java.awt.Cursor.getDefaultCursor());
                [CtAssignmentImpl][CtFieldWriteImpl]beginTrack = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtFieldWriteImpl]foundTrack = [CtLiteralImpl]null;
                [CtInvocationImpl]redrawPanel();
            }
            [CtInvocationImpl]createSelectionGroups();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_CENTER)) && [CtInvocationImpl]allControlling()) && [CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtFieldReadImpl]delayedPopupTrigger)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// controlling turnout out of edit mode
            [CtTypeReferenceImpl]LayoutTurnout t = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject));
            [CtIfImpl]if ([CtFieldReadImpl]useDirectTurnoutControl) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]t.setState([CtTypeAccessImpl]Turnout.CLOSED);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]t.toggleTurnout();
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_LEFT) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_RIGHT))) && [CtInvocationImpl]allControlling()) && [CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtFieldReadImpl]delayedPopupTrigger)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// controlling slip out of edit mode
            [CtTypeReferenceImpl]LayoutSlip sl = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutSlip) (selectedObject));
            [CtInvocationImpl][CtVariableReadImpl]sl.toggleState([CtFieldReadImpl]selectedHitPointType);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtFieldReadImpl]selectedHitPointType)) && [CtInvocationImpl]allControlling()) && [CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtFieldReadImpl]delayedPopupTrigger)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// controlling turntable out of edit mode
            [CtTypeReferenceImpl]LayoutTurntable t = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (selectedObject));
            [CtInvocationImpl][CtVariableReadImpl]t.setPosition([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]selectedHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0.getXmlValue());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger() || [CtFieldReadImpl]delayedPopupTrigger) && [CtUnaryOperatorImpl](![CtFieldReadImpl]isDragging)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// requesting marker popup out of edit mode
            [CtTypeReferenceImpl]LocoIcon lo = [CtInvocationImpl]checkMarkerPopUps([CtFieldReadImpl]dLoc);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lo != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]showPopUp([CtVariableReadImpl]lo, [CtVariableReadImpl]event);
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtFieldReadImpl]dLoc)) [CtBlockImpl]{
                    [CtSwitchImpl][CtCommentImpl]// show popup menu
                    switch ([CtFieldReadImpl]foundHitPointType) {
                        [CtCaseImpl]case TURNOUT_CENTER :
                            [CtBlockImpl]{
                                [CtIfImpl]if ([CtFieldReadImpl]useDirectTurnoutControl) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout t = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (foundTrack));
                                    [CtInvocationImpl][CtVariableReadImpl]t.setState([CtTypeAccessImpl]Turnout.THROWN);
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]foundTrack.showPopup([CtVariableReadImpl]event);
                                }
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LEVEL_XING_CENTER :
                        [CtCaseImpl]case SLIP_RIGHT :
                        [CtCaseImpl]case SLIP_LEFT :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]foundTrack.showPopup([CtVariableReadImpl]event);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]default :
                            [CtBlockImpl]{
                                [CtBreakImpl]break;
                            }
                    }
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]AnalogClock2Display c = [CtInvocationImpl]checkClockPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]c, [CtVariableReadImpl]event);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMastIcon sm = [CtInvocationImpl]checkSignalMastIconPopUps([CtFieldReadImpl]dLoc);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl]showPopUp([CtVariableReadImpl]sm, [CtVariableReadImpl]event);
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel im = [CtInvocationImpl]checkLabelImagePopUps([CtFieldReadImpl]dLoc);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]im != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl]showPopUp([CtVariableReadImpl]im, [CtVariableReadImpl]event);
                        }
                    }
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger()) && [CtUnaryOperatorImpl](![CtFieldReadImpl]isDragging)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> selections = [CtInvocationImpl]getSelectedItems([CtVariableReadImpl]event);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selections.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]selections.get([CtLiteralImpl]0).doMouseReleased([CtVariableReadImpl]event);
                [CtAssignmentImpl][CtFieldWriteImpl]whenReleased = [CtInvocationImpl][CtVariableReadImpl]event.getWhen();
            }
        }
        [CtIfImpl][CtCommentImpl]// train icon needs to know when moved
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger() && [CtFieldReadImpl]isDragging) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> selections = [CtInvocationImpl]getSelectedItems([CtVariableReadImpl]event);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selections.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]selections.get([CtLiteralImpl]0).doMouseDragged([CtVariableReadImpl]event);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// An object was selected, deselect it
            [CtFieldWriteImpl]prevSelectedObject = [CtFieldReadImpl]selectedObject;
            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
        }
        [CtAssignmentImpl][CtCommentImpl]// clear these
        [CtFieldWriteImpl]beginTrack = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]foundTrack = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]delayedPopupTrigger = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtFieldReadImpl]isDragging) [CtBlockImpl]{
            [CtInvocationImpl]resizePanelBounds([CtLiteralImpl]true);
            [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]false;
        }
        [CtInvocationImpl]requestFocusInWindow();
    }[CtCommentImpl]// mouseReleased


    [CtMethodImpl]private [CtTypeReferenceImpl]void showEditPopUps([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtFieldReadImpl]dLoc)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isBezierHitType([CtFieldReadImpl]foundHitPointType)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]TrackSegment) (foundTrack)).showBezierPopUp([CtVariableReadImpl]event, [CtFieldReadImpl]foundHitPointType);
            } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtFieldReadImpl]foundHitPointType)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable t = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (foundTrack));
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]t.isTurnoutControlled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (foundTrack)).showRayPopUp([CtVariableReadImpl]event, [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]foundHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0.getXmlValue());
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isPopupHitType([CtFieldReadImpl]foundHitPointType)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]foundTrack.showPopup([CtVariableReadImpl]event);
            } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurnoutHitType([CtFieldReadImpl]foundHitPointType)) [CtBlockImpl]{
                [CtCommentImpl]// don't curently have edit popup for these
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown foundPointType:" + [CtFieldReadImpl]foundHitPointType);
            }
        } else [CtBlockImpl]{
            [CtDoImpl]do [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment ts = [CtInvocationImpl]checkTrackSegmentPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ts != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ts.showPopup([CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]SensorIcon s = [CtInvocationImpl]checkSensorIconPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]s, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]LocoIcon lo = [CtInvocationImpl]checkMarkerPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lo != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]lo, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]SignalHeadIcon sh = [CtInvocationImpl]checkSignalHeadIconPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sh != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]sh, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]AnalogClock2Display c = [CtInvocationImpl]checkClockPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]c, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]MultiSensorIcon ms = [CtInvocationImpl]checkMultiSensorPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ms != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]ms, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel lb = [CtInvocationImpl]checkLabelImagePopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lb != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]lb, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel b = [CtInvocationImpl]checkBackgroundPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]b, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMastIcon sm = [CtInvocationImpl]checkSignalMastIconPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]sm, [CtVariableReadImpl]event);
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls = [CtInvocationImpl]checkLayoutShapePopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ls != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ls.showShapePopUp([CtVariableReadImpl]event, [CtFieldReadImpl]selectedHitPointType);
                    [CtBreakImpl]break;
                }
            } while ([CtLiteralImpl]false );
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Select the menu items to display for the Positionable's popup.
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void showPopUp([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Positionable p, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.awt.Component) (p)).isVisible()) [CtBlockImpl]{
            [CtReturnImpl]return;[CtCommentImpl]// component must be showing on the screen to determine its location

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JPopupMenu popup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JPopupMenu();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isEditable()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenuItem jmi;
            [CtIfImpl]if ([CtInvocationImpl]showAlignPopup()) [CtBlockImpl]{
                [CtInvocationImpl]setShowAlignmentMenu([CtVariableReadImpl]popup);
                [CtInvocationImpl][CtVariableReadImpl]popup.add([CtNewClassImpl]new [CtTypeReferenceImpl]javax.swing.AbstractAction([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonDelete"))[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void actionPerformed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) [CtBlockImpl]{
                        [CtInvocationImpl]deleteSelectedItems();
                    }
                });
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.doViemMenu()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getClass().getName();
                    [CtAssignmentImpl][CtVariableWriteImpl]objectType = [CtInvocationImpl][CtVariableReadImpl]objectType.substring([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]objectType.lastIndexOf([CtLiteralImpl]'.') + [CtLiteralImpl]1);
                    [CtAssignmentImpl][CtVariableWriteImpl]jmi = [CtInvocationImpl][CtVariableReadImpl]popup.add([CtVariableReadImpl]objectType);
                    [CtInvocationImpl][CtVariableReadImpl]jmi.setEnabled([CtLiteralImpl]false);
                    [CtAssignmentImpl][CtVariableWriteImpl]jmi = [CtInvocationImpl][CtVariableReadImpl]popup.add([CtInvocationImpl][CtVariableReadImpl]p.getNameString());
                    [CtInvocationImpl][CtVariableReadImpl]jmi.setEnabled([CtLiteralImpl]false);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isPositionable()) [CtBlockImpl]{
                        [CtInvocationImpl]setShowCoordinatesMenu([CtVariableReadImpl]p, [CtVariableReadImpl]popup);
                    }
                    [CtInvocationImpl]setDisplayLevelMenu([CtVariableReadImpl]p, [CtVariableReadImpl]popup);
                    [CtInvocationImpl]setPositionableMenu([CtVariableReadImpl]p, [CtVariableReadImpl]popup);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean popupSet = [CtLiteralImpl]false;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]popupSet |= [CtInvocationImpl][CtVariableReadImpl]p.setRotateOrthogonalMenu([CtVariableReadImpl]popup);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]popupSet |= [CtInvocationImpl][CtVariableReadImpl]p.setRotateMenu([CtVariableReadImpl]popup);
                [CtIfImpl]if ([CtVariableReadImpl]popupSet) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]popup.addSeparator();
                    [CtAssignmentImpl][CtVariableWriteImpl]popupSet = [CtLiteralImpl]false;
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]popupSet |= [CtInvocationImpl][CtVariableReadImpl]p.setEditIconMenu([CtVariableReadImpl]popup);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]popupSet |= [CtInvocationImpl][CtVariableReadImpl]p.setTextEditMenu([CtVariableReadImpl]popup);
                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePopupUtil util = [CtInvocationImpl][CtVariableReadImpl]p.getPopupUtility();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]util != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]util.setFixedTextMenu([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]util.setTextMarginMenu([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]util.setTextBorderMenu([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]util.setTextFontMenu([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]util.setBackgroundMenu([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]util.setTextJustificationMenu([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]util.setTextOrientationMenu([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]popup.addSeparator();
                    [CtInvocationImpl][CtVariableReadImpl]util.propertyUtil([CtVariableReadImpl]popup);
                    [CtInvocationImpl][CtVariableReadImpl]util.setAdditionalEditPopUpMenu([CtVariableReadImpl]popup);
                    [CtAssignmentImpl][CtVariableWriteImpl]popupSet = [CtLiteralImpl]true;
                }
                [CtIfImpl]if ([CtVariableReadImpl]popupSet) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]popup.addSeparator();
                    [CtCommentImpl]// popupSet = false;
                }
                [CtInvocationImpl][CtVariableReadImpl]p.setDisableControlMenu([CtVariableReadImpl]popup);
                [CtInvocationImpl]setShowAlignmentMenu([CtVariableReadImpl]popup);
                [CtInvocationImpl][CtCommentImpl]// for Positionables with unique settings
                [CtVariableReadImpl]p.showPopUp([CtVariableReadImpl]popup);
                [CtInvocationImpl]setShowToolTipMenu([CtVariableReadImpl]p, [CtVariableReadImpl]popup);
                [CtInvocationImpl]setRemoveMenu([CtVariableReadImpl]p, [CtVariableReadImpl]popup);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.doViemMenu()) [CtBlockImpl]{
                    [CtInvocationImpl]setHiddenMenu([CtVariableReadImpl]p, [CtVariableReadImpl]popup);
                }
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]p.showPopUp([CtVariableReadImpl]popup);
            [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePopupUtil util = [CtInvocationImpl][CtVariableReadImpl]p.getPopupUtility();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]util != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]util.setAdditionalViewPopUpMenu([CtVariableReadImpl]popup);
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]popup.show([CtVariableReadImpl](([CtTypeReferenceImpl]java.awt.Component) (p)), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getWidth() / [CtLiteralImpl]2) + [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtInvocationImpl]getZoom() - [CtLiteralImpl]1.0) * [CtInvocationImpl][CtVariableReadImpl]p.getX())), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getHeight() / [CtLiteralImpl]2) + [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtInvocationImpl]getZoom() - [CtLiteralImpl]1.0) * [CtInvocationImpl][CtVariableReadImpl]p.getY())));
        [CtCommentImpl]/* popup.show((Component)pt, event.getX(), event.getY()); */
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]long whenReleased = [CtLiteralImpl]0;[CtCommentImpl]// used to identify event that was popup trigger


    [CtFieldImpl]private [CtTypeReferenceImpl]boolean awaitingIconChange = [CtLiteralImpl]false;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void mouseClicked([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// initialize mouse position
        calcLocation([CtVariableReadImpl]event);
        [CtAssignmentImpl][CtCommentImpl]// if alt modifier is down invert the snap to grid behaviour
        [CtFieldWriteImpl]snapToGridInvert = [CtInvocationImpl][CtVariableReadImpl]event.isAltDown();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isAltDown())) && [CtUnaryOperatorImpl](![CtFieldReadImpl]awaitingIconChange)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isControlDown())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> selections = [CtInvocationImpl]getSelectedItems([CtVariableReadImpl]event);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selections.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]selections.get([CtLiteralImpl]0).doMouseClicked([CtVariableReadImpl]event);
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger() && [CtBinaryOperatorImpl]([CtFieldReadImpl]whenReleased != [CtInvocationImpl][CtVariableReadImpl]event.getWhen())) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtFieldWriteImpl]selectedHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE;
                [CtInvocationImpl]showEditPopUps([CtVariableReadImpl]event);
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]LocoIcon lo = [CtInvocationImpl]checkMarkerPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lo != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]showPopUp([CtVariableReadImpl]lo, [CtVariableReadImpl]event);
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.isControlDown() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isPopupTrigger())) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtFieldReadImpl]dLoc)) [CtBlockImpl]{
                [CtSwitchImpl]switch ([CtFieldReadImpl]foundHitPointType) {
                    [CtCaseImpl]case POS_POINT :
                    [CtCaseImpl]case TURNOUT_CENTER :
                    [CtCaseImpl]case LEVEL_XING_CENTER :
                    [CtCaseImpl]case SLIP_LEFT :
                    [CtCaseImpl]case SLIP_RIGHT :
                    [CtCaseImpl]case TURNTABLE_CENTER :
                        [CtBlockImpl]{
                            [CtInvocationImpl]amendSelectionGroup([CtFieldReadImpl]foundTrack);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]default :
                        [CtBlockImpl]{
                            [CtBreakImpl]break;
                        }
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel s = [CtInvocationImpl]checkSensorIconPopUps([CtFieldReadImpl]dLoc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]amendSelectionGroup([CtVariableReadImpl]s);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel sh = [CtInvocationImpl]checkSignalHeadIconPopUps([CtFieldReadImpl]dLoc);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sh != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl]amendSelectionGroup([CtVariableReadImpl]sh);
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel ms = [CtInvocationImpl]checkMultiSensorPopUps([CtFieldReadImpl]dLoc);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ms != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl]amendSelectionGroup([CtVariableReadImpl]ms);
                        } else [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel lb = [CtInvocationImpl]checkLabelImagePopUps([CtFieldReadImpl]dLoc);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lb != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl]amendSelectionGroup([CtVariableReadImpl]lb);
                            } else [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel b = [CtInvocationImpl]checkBackgroundPopUps([CtFieldReadImpl]dLoc);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b != [CtLiteralImpl]null) [CtBlockImpl]{
                                    [CtInvocationImpl]amendSelectionGroup([CtVariableReadImpl]b);
                                } else [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel sm = [CtInvocationImpl]checkSignalMastIconPopUps([CtFieldReadImpl]dLoc);
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
                                        [CtInvocationImpl]amendSelectionGroup([CtVariableReadImpl]sm);
                                    } else [CtBlockImpl]{
                                        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls = [CtInvocationImpl]checkLayoutShapePopUps([CtFieldReadImpl]dLoc);
                                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ls != [CtLiteralImpl]null) [CtBlockImpl]{
                                            [CtInvocationImpl]amendSelectionGroup([CtVariableReadImpl]ls);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]selectionWidth == [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtFieldReadImpl]selectionHeight == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl]clearSelectionGroups();
        }
        [CtInvocationImpl]requestFocusInWindow();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkPointOfPositionable([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]PositionablePoint p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t = [CtInvocationImpl][CtVariableReadImpl]p.getConnect1();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtVariableReadImpl]p.getConnect2();
        }
        [CtIfImpl][CtCommentImpl]// Nothing connected to this bit of track so ignore
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]t == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]beginTrack = [CtVariableReadImpl]p;
        [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]POS_POINT;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D loc = [CtInvocationImpl][CtVariableReadImpl]p.getCoordsCenter();
        [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtVariableReadImpl]loc, [CtLiteralImpl]true, [CtVariableReadImpl]p)) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtFieldReadImpl]foundHitPointType) {
                [CtCaseImpl]case POS_POINT :
                    [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint p2 = [CtFieldReadImpl](([CtTypeReferenceImpl]PositionablePoint) (foundTrack));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p2.getType() == [CtFieldReadImpl]PositionablePoint.ANCHOR) && [CtInvocationImpl][CtVariableReadImpl]p2.setTrackConnection([CtVariableReadImpl]t)) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getConnect1() == [CtVariableReadImpl]p) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect1([CtVariableReadImpl]p2, [CtFieldReadImpl]foundHitPointType);
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect2([CtVariableReadImpl]p2, [CtFieldReadImpl]foundHitPointType);
                            }
                            [CtInvocationImpl][CtVariableReadImpl]p.removeTrackConnection([CtVariableReadImpl]t);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect1() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect2() == [CtLiteralImpl]null)) [CtBlockImpl]{
                                [CtInvocationImpl]removePositionablePoint([CtVariableReadImpl]p);
                            }
                        }
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case TURNOUT_A :
                [CtCaseImpl]case TURNOUT_B :
                [CtCaseImpl]case TURNOUT_C :
                [CtCaseImpl]case TURNOUT_D :
                [CtCaseImpl]case SLIP_A :
                [CtCaseImpl]case SLIP_B :
                [CtCaseImpl]case SLIP_C :
                [CtCaseImpl]case SLIP_D :
                [CtCaseImpl]case LEVEL_XING_A :
                [CtCaseImpl]case LEVEL_XING_B :
                [CtCaseImpl]case LEVEL_XING_C :
                [CtCaseImpl]case LEVEL_XING_D :
                    [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]foundTrack.getConnection([CtFieldReadImpl]foundHitPointType) == [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]foundTrack.setConnection([CtFieldReadImpl]foundHitPointType, [CtVariableReadImpl]t, [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TRACK);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getConnect1() == [CtVariableReadImpl]p) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect1([CtFieldReadImpl]foundTrack, [CtFieldReadImpl]foundHitPointType);
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect2([CtFieldReadImpl]foundTrack, [CtFieldReadImpl]foundHitPointType);
                                }
                                [CtInvocationImpl][CtVariableReadImpl]p.removeTrackConnection([CtVariableReadImpl]t);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect1() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect2() == [CtLiteralImpl]null)) [CtBlockImpl]{
                                    [CtInvocationImpl]removePositionablePoint([CtVariableReadImpl]p);
                                }
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]JmriException e) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"Unable to set location");
                        }
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]default :
                    [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtFieldReadImpl]foundHitPointType)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable tt = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (foundTrack));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int ray = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]foundHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0.getXmlValue();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tt.getRayConnectIndexed([CtVariableReadImpl]ray) == [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]tt.setRayConnect([CtVariableReadImpl]t, [CtVariableReadImpl]ray);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getConnect1() == [CtVariableReadImpl]p) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect1([CtVariableReadImpl]tt, [CtFieldReadImpl]foundHitPointType);
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect2([CtVariableReadImpl]tt, [CtFieldReadImpl]foundHitPointType);
                                }
                                [CtInvocationImpl][CtVariableReadImpl]p.removeTrackConnection([CtVariableReadImpl]t);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect1() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getConnect2() == [CtLiteralImpl]null)) [CtBlockImpl]{
                                    [CtInvocationImpl]removePositionablePoint([CtVariableReadImpl]p);
                                }
                            }
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"No valid point, so will quit");
                            [CtReturnImpl]return;
                        }
                        [CtBreakImpl]break;
                    }
            }
            [CtInvocationImpl]redrawPanel();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getLayoutBlock() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]getLEAuxTools().setBlockConnectivityChanged();
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]beginTrack = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtCommentImpl]// We just dropped a turnout... see if it will connect to anything
    private [CtTypeReferenceImpl]void hitPointCheckLayoutTurnouts([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTurnout lt) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]beginTrack = [CtVariableReadImpl]lt;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lt.getConnectA() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutSlip) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_A;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_A;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]dLoc = [CtInvocationImpl][CtVariableReadImpl]lt.getCoordsA();
            [CtInvocationImpl]hitPointCheckLayoutTurnoutSubs([CtFieldReadImpl]dLoc);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lt.getConnectB() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutSlip) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_B;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_B;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]dLoc = [CtInvocationImpl][CtVariableReadImpl]lt.getCoordsB();
            [CtInvocationImpl]hitPointCheckLayoutTurnoutSubs([CtFieldReadImpl]dLoc);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lt.getConnectC() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutSlip) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_C;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_C;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]dLoc = [CtInvocationImpl][CtVariableReadImpl]lt.getCoordsC();
            [CtInvocationImpl]hitPointCheckLayoutTurnoutSubs([CtFieldReadImpl]dLoc);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]lt.getConnectD() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]lt.isTurnoutTypeXover() || [CtInvocationImpl][CtVariableReadImpl]lt.isTurnoutTypeSlip())) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutSlip) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SLIP_D;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]beginHitPointType = [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_D;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]dLoc = [CtInvocationImpl][CtVariableReadImpl]lt.getCoordsD();
            [CtInvocationImpl]hitPointCheckLayoutTurnoutSubs([CtFieldReadImpl]dLoc);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]beginTrack = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]foundTrack = [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void hitPointCheckLayoutTurnoutSubs([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D dLoc) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtVariableReadImpl]dLoc, [CtLiteralImpl]true)) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtFieldReadImpl]foundHitPointType) {
                [CtCaseImpl]case POS_POINT :
                    [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint p2 = [CtFieldReadImpl](([CtTypeReferenceImpl]PositionablePoint) (foundTrack));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p2.getConnect1() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p2.getConnect2() != [CtLiteralImpl]null)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p2.getConnect1() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p2.getConnect2() == [CtLiteralImpl]null))) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t = [CtInvocationImpl][CtVariableReadImpl]p2.getConnect1();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t == [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtVariableReadImpl]p2.getConnect2();
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t == [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtReturnImpl]return;
                            }
                            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (beginTrack));
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lt.getConnection([CtFieldReadImpl]beginHitPointType) == [CtLiteralImpl]null) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]lt.setConnection([CtFieldReadImpl]beginHitPointType, [CtVariableReadImpl]t, [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TRACK);
                                    [CtInvocationImpl][CtVariableReadImpl]p2.removeTrackConnection([CtVariableReadImpl]t);
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getConnect1() == [CtVariableReadImpl]p2) [CtBlockImpl]{
                                        [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect1([CtVariableReadImpl]lt, [CtFieldReadImpl]beginHitPointType);
                                    } else [CtBlockImpl]{
                                        [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect2([CtVariableReadImpl]lt, [CtFieldReadImpl]beginHitPointType);
                                    }
                                    [CtInvocationImpl]removePositionablePoint([CtVariableReadImpl]p2);
                                }
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getLayoutBlock() != [CtLiteralImpl]null) [CtBlockImpl]{
                                    [CtInvocationImpl][CtInvocationImpl]getLEAuxTools().setBlockConnectivityChanged();
                                }
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]JmriException e) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"Unable to set location");
                            }
                        }
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case TURNOUT_A :
                [CtCaseImpl]case TURNOUT_B :
                [CtCaseImpl]case TURNOUT_C :
                [CtCaseImpl]case TURNOUT_D :
                [CtCaseImpl]case SLIP_A :
                [CtCaseImpl]case SLIP_B :
                [CtCaseImpl]case SLIP_C :
                [CtCaseImpl]case SLIP_D :
                    [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout ft = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (foundTrack));
                        [CtInvocationImpl]addTrackSegment();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ft.getTurnoutType() == [CtFieldReadImpl]LayoutTurnout.TurnoutType.RH_TURNOUT) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ft.getTurnoutType() == [CtFieldReadImpl]LayoutTurnout.TurnoutType.LH_TURNOUT)) [CtBlockImpl]{
                            [CtInvocationImpl]rotateTurnout([CtVariableReadImpl]ft);
                        }
                        [CtInvocationImpl][CtCommentImpl]// Assign a block to the new zero length track segment.
                        [CtVariableReadImpl]ft.setTrackSegmentBlock([CtFieldReadImpl]foundHitPointType, [CtLiteralImpl]true);
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]default :
                    [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtLiteralImpl]"Unexpected foundPointType {} in hitPointCheckLayoutTurnoutSubs", [CtFieldReadImpl]foundHitPointType);
                        [CtBreakImpl]break;
                    }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void rotateTurnout([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTurnout t) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout be = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (beginTrack));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]beginHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_A) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getConnectB() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getConnectC() != [CtLiteralImpl]null))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]beginHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_B) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getConnectA() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getConnectC() != [CtLiteralImpl]null)))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]beginHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_C) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getConnectB() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getConnectA() != [CtLiteralImpl]null)))) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getTurnoutType() != [CtFieldReadImpl]LayoutTurnout.TurnoutType.RH_TURNOUT) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]be.getTurnoutType() != [CtFieldReadImpl]LayoutTurnout.TurnoutType.LH_TURNOUT)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D c;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D diverg;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D xy2;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]foundHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_C) && [CtBinaryOperatorImpl]([CtFieldReadImpl]beginHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_C)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]c = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsA();
            [CtAssignmentImpl][CtVariableWriteImpl]diverg = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsB();
            [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtVariableReadImpl]c, [CtVariableReadImpl]diverg);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]foundHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_C) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]beginHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_A) || [CtBinaryOperatorImpl]([CtFieldReadImpl]beginHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_B))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]c = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsCenter();
            [CtAssignmentImpl][CtVariableWriteImpl]diverg = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsC();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]beginHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_A) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsB(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsA());
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsA(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsB());
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]foundHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_B) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]c = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsA();
            [CtAssignmentImpl][CtVariableWriteImpl]diverg = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsB();
            [CtSwitchImpl]switch ([CtFieldReadImpl]beginHitPointType) {
                [CtCaseImpl]case TURNOUT_B :
                    [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsA(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsB());
                    [CtBreakImpl]break;
                [CtCaseImpl]case TURNOUT_A :
                    [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsB(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsA());
                    [CtBreakImpl]break;
                [CtCaseImpl]case TURNOUT_C :
                [CtCaseImpl]default :
                    [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsCenter(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsC());
                    [CtBreakImpl]break;
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]foundHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_A) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]c = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsA();
            [CtAssignmentImpl][CtVariableWriteImpl]diverg = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsB();
            [CtSwitchImpl]switch ([CtFieldReadImpl]beginHitPointType) {
                [CtCaseImpl]case TURNOUT_A :
                    [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsA(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsB());
                    [CtBreakImpl]break;
                [CtCaseImpl]case TURNOUT_B :
                    [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsB(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsA());
                    [CtBreakImpl]break;
                [CtCaseImpl]case TURNOUT_C :
                [CtCaseImpl]default :
                    [CtAssignmentImpl][CtVariableWriteImpl]xy2 = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsC(), [CtInvocationImpl][CtVariableReadImpl]be.getCoordsCenter());
                    [CtBreakImpl]break;
            }
        } else [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D xy = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtVariableReadImpl]diverg, [CtVariableReadImpl]c);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double radius = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.toDegrees([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.atan2([CtInvocationImpl][CtVariableReadImpl]xy.getY(), [CtInvocationImpl][CtVariableReadImpl]xy.getX()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]double eRadius = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.toDegrees([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.atan2([CtInvocationImpl][CtVariableReadImpl]xy2.getY(), [CtInvocationImpl][CtVariableReadImpl]xy2.getX()));
        [CtInvocationImpl][CtVariableReadImpl]be.rotateCoords([CtBinaryOperatorImpl][CtVariableReadImpl]radius - [CtVariableReadImpl]eRadius);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D conCord = [CtInvocationImpl][CtVariableReadImpl]be.getCoordsA();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D tCord = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsC();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]foundHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_B) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tCord = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsB();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]foundHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNOUT_A) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tCord = [CtInvocationImpl][CtVariableReadImpl]t.getCoordsA();
        }
        [CtSwitchImpl]switch ([CtFieldReadImpl]beginHitPointType) {
            [CtCaseImpl]case TURNOUT_A :
                [CtAssignmentImpl][CtVariableWriteImpl]conCord = [CtInvocationImpl][CtVariableReadImpl]be.getCoordsA();
                [CtBreakImpl]break;
            [CtCaseImpl]case TURNOUT_B :
                [CtAssignmentImpl][CtVariableWriteImpl]conCord = [CtInvocationImpl][CtVariableReadImpl]be.getCoordsB();
                [CtBreakImpl]break;
            [CtCaseImpl]case TURNOUT_C :
                [CtAssignmentImpl][CtVariableWriteImpl]conCord = [CtInvocationImpl][CtVariableReadImpl]be.getCoordsC();
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtBreakImpl]break;
        }
        [CtAssignmentImpl][CtVariableWriteImpl]xy = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtVariableReadImpl]conCord, [CtVariableReadImpl]tCord);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D offset = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtInvocationImpl][CtVariableReadImpl]be.getCoordsCenter(), [CtVariableReadImpl]xy);
        [CtInvocationImpl][CtVariableReadImpl]be.setCoordsCenter([CtVariableReadImpl]offset);
    }

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> _positionableSelection = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutTrack> _layoutTrackSelection = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]protected transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutShape> _layoutShapeSelection = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtMethodImpl]protected [CtTypeReferenceImpl]void createSelectionGroups() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D selectionRect = [CtInvocationImpl]getSelectionRect();
        [CtInvocationImpl][CtInvocationImpl]getContents().forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]selectionRect.contains([CtInvocationImpl][CtVariableReadImpl]o.getLocation())) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_positionableSelection.contains([CtVariableReadImpl]o)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_positionableSelection.add([CtVariableReadImpl]o);
                }
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Point2D center = [CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]selectionRect.contains([CtVariableReadImpl]center)) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_layoutTrackSelection.contains([CtVariableReadImpl]lt)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_layoutTrackSelection.add([CtVariableReadImpl]lt);
                }
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]assignBlockToSelectionMenuItem.setEnabled([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.size() > [CtLiteralImpl]0);
        [CtInvocationImpl][CtFieldReadImpl]layoutShapes.forEach([CtLambdaImpl]([CtParameterImpl] ls) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]selectionRect.intersects([CtInvocationImpl][CtVariableReadImpl]ls.getBounds())) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_layoutShapeSelection.contains([CtVariableReadImpl]ls)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_layoutShapeSelection.add([CtVariableReadImpl]ls);
                }
            }
        });
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clearSelectionGroups() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]selectionActive = [CtLiteralImpl]false;
        [CtInvocationImpl][CtFieldReadImpl]_positionableSelection.clear();
        [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.clear();
        [CtInvocationImpl][CtFieldReadImpl]assignBlockToSelectionMenuItem.setEnabled([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.clear();
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean noWarnGlobalDelete = [CtLiteralImpl]false;

    [CtMethodImpl]private [CtTypeReferenceImpl]void deleteSelectedItems() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]noWarnGlobalDelete) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Question6"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYesPlus") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return;[CtCommentImpl]// return without creating if "No" response

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Suppress future warnings, and continue
                [CtFieldWriteImpl]noWarnGlobalDelete = [CtLiteralImpl]true;
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]_positionableSelection.forEach([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::remove);
        [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]PositionablePoint) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean oldWarning = [CtFieldReadImpl][CtFieldReferenceImpl]noWarnPositionablePoint;
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnPositionablePoint = [CtLiteralImpl]true;
                [CtInvocationImpl]removePositionablePoint([CtVariableReadImpl](([CtTypeReferenceImpl]PositionablePoint) (lt)));
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnPositionablePoint = [CtVariableReadImpl]oldWarning;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LevelXing) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean oldWarning = [CtFieldReadImpl][CtFieldReferenceImpl]noWarnLevelXing;
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnLevelXing = [CtLiteralImpl]true;
                [CtInvocationImpl]removeLevelXing([CtVariableReadImpl](([CtTypeReferenceImpl]LevelXing) (lt)));
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnLevelXing = [CtVariableReadImpl]oldWarning;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutSlip) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean oldWarning = [CtFieldReadImpl][CtFieldReferenceImpl]noWarnSlip;
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnSlip = [CtLiteralImpl]true;
                [CtInvocationImpl]removeLayoutSlip([CtVariableReadImpl](([CtTypeReferenceImpl]LayoutSlip) (lt)));
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnSlip = [CtVariableReadImpl]oldWarning;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurntable) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean oldWarning = [CtFieldReadImpl][CtFieldReferenceImpl]noWarnTurntable;
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnTurntable = [CtLiteralImpl]true;
                [CtInvocationImpl]removeTurntable([CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (lt)));
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnTurntable = [CtVariableReadImpl]oldWarning;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurnout) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// <== this includes LayoutSlips
                [CtTypeReferenceImpl]boolean oldWarning = [CtFieldReadImpl][CtFieldReferenceImpl]noWarnLayoutTurnout;
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnLayoutTurnout = [CtLiteralImpl]true;
                [CtInvocationImpl]removeLayoutTurnout([CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (lt)));
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]noWarnLayoutTurnout = [CtVariableReadImpl]oldWarning;
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]layoutShapes.removeAll([CtFieldReadImpl]_layoutShapeSelection);
        [CtInvocationImpl]clearSelectionGroups();
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void amendSelectionGroup([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Positionable p) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]_positionableSelection.contains([CtVariableReadImpl]p)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_positionableSelection.remove([CtVariableReadImpl]p);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_positionableSelection.add([CtVariableReadImpl]p);
        }
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void amendSelectionGroup([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTrack p) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.contains([CtVariableReadImpl]p)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.remove([CtVariableReadImpl]p);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.add([CtVariableReadImpl]p);
        }
        [CtInvocationImpl][CtFieldReadImpl]assignBlockToSelectionMenuItem.setEnabled([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.size() > [CtLiteralImpl]0);
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void amendSelectionGroup([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutShape ls) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.contains([CtVariableReadImpl]ls)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.remove([CtVariableReadImpl]ls);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.add([CtVariableReadImpl]ls);
        }
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void alignSelection([CtParameterImpl][CtTypeReferenceImpl]boolean alignX) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D minPoint = [CtFieldReadImpl]MathUtil.infinityPoint2D;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D maxPoint = [CtFieldReadImpl]MathUtil.zeroPoint2D;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D sumPoint = [CtFieldReadImpl]MathUtil.zeroPoint2D;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int cnt = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Positionable comp : [CtFieldReadImpl]_positionableSelection) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getFlag([CtTypeAccessImpl]Editor.OPTION_POSITION, [CtInvocationImpl][CtVariableReadImpl]comp.isPositionable())) [CtBlockImpl]{
                [CtContinueImpl]continue;[CtCommentImpl]// skip non-positionables

            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D p = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.pointToPoint2D([CtInvocationImpl][CtVariableReadImpl]comp.getLocation());
            [CtAssignmentImpl][CtVariableWriteImpl]minPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.min([CtVariableReadImpl]minPoint, [CtVariableReadImpl]p);
            [CtAssignmentImpl][CtVariableWriteImpl]maxPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]maxPoint, [CtVariableReadImpl]p);
            [CtAssignmentImpl][CtVariableWriteImpl]sumPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtVariableReadImpl]sumPoint, [CtVariableReadImpl]p);
            [CtUnaryOperatorImpl][CtVariableWriteImpl]cnt++;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack lt : [CtFieldReadImpl]_layoutTrackSelection) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D p = [CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter();
            [CtAssignmentImpl][CtVariableWriteImpl]minPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.min([CtVariableReadImpl]minPoint, [CtVariableReadImpl]p);
            [CtAssignmentImpl][CtVariableWriteImpl]maxPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]maxPoint, [CtVariableReadImpl]p);
            [CtAssignmentImpl][CtVariableWriteImpl]sumPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtVariableReadImpl]sumPoint, [CtVariableReadImpl]p);
            [CtUnaryOperatorImpl][CtVariableWriteImpl]cnt++;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtFieldReadImpl]_layoutShapeSelection) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D p = [CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter();
            [CtAssignmentImpl][CtVariableWriteImpl]minPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.min([CtVariableReadImpl]minPoint, [CtVariableReadImpl]p);
            [CtAssignmentImpl][CtVariableWriteImpl]maxPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]maxPoint, [CtVariableReadImpl]p);
            [CtAssignmentImpl][CtVariableWriteImpl]sumPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtVariableReadImpl]sumPoint, [CtVariableReadImpl]p);
            [CtUnaryOperatorImpl][CtVariableWriteImpl]cnt++;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D avePoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.divide([CtVariableReadImpl]sumPoint, [CtVariableReadImpl]cnt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int aveX = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]avePoint.getX()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int aveY = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]avePoint.getY()));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Positionable comp : [CtFieldReadImpl]_positionableSelection) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getFlag([CtTypeAccessImpl]Editor.OPTION_POSITION, [CtInvocationImpl][CtVariableReadImpl]comp.isPositionable())) [CtBlockImpl]{
                [CtContinueImpl]continue;[CtCommentImpl]// skip non-positionables

            }
            [CtIfImpl]if ([CtVariableReadImpl]alignX) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]comp.setLocation([CtVariableReadImpl]aveX, [CtInvocationImpl][CtVariableReadImpl]comp.getY());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]comp.setLocation([CtInvocationImpl][CtVariableReadImpl]comp.getX(), [CtVariableReadImpl]aveY);
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]alignX) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]Point2D.Double([CtVariableReadImpl]aveX, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter().getY()));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]Point2D.Double([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter().getX(), [CtVariableReadImpl]aveY));
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.forEach([CtLambdaImpl]([CtParameterImpl] ls) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]alignX) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]Point2D.Double([CtVariableReadImpl]aveX, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter().getY()));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]Point2D.Double([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter().getX(), [CtVariableReadImpl]aveY));
            }
        });
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean showAlignPopup() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]_positionableSelection.size() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.size() > [CtLiteralImpl]0)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.size() > [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Offer actions to align the selected Positionable items either
     * Horizontally (at average y coord) or Vertically (at average x coord).
     *
     * @param popup
     * 		the JPopupMenu to add alignment menu to
     * @return true if alignment menu added
     */
    public [CtTypeReferenceImpl]boolean setShowAlignmentMenu([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JPopupMenu popup) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]showAlignPopup()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JMenu edit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JMenu([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"EditAlignment"));
            [CtInvocationImpl][CtVariableReadImpl]edit.add([CtNewClassImpl]new [CtTypeReferenceImpl]javax.swing.AbstractAction([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AlignX"))[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void actionPerformed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) [CtBlockImpl]{
                    [CtInvocationImpl]alignSelection([CtLiteralImpl]true);
                }
            });
            [CtInvocationImpl][CtVariableReadImpl]edit.add([CtNewClassImpl]new [CtTypeReferenceImpl]javax.swing.AbstractAction([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"AlignY"))[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void actionPerformed([CtParameterImpl][CtTypeReferenceImpl]java.awt.event.ActionEvent event) [CtBlockImpl]{
                    [CtInvocationImpl]alignSelection([CtLiteralImpl]false);
                }
            });
            [CtInvocationImpl][CtVariableReadImpl]popup.add([CtVariableReadImpl]edit);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void keyPressed([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.KeyEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getKeyCode() == [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]VK_DELETE) [CtBlockImpl]{
            [CtInvocationImpl]deleteSelectedItems();
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]double deltaX = [CtInvocationImpl]returnDeltaPositionX([CtVariableReadImpl]event);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double deltaY = [CtInvocationImpl]returnDeltaPositionY([CtVariableReadImpl]event);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]deltaX != [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]deltaY != [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]selectionX += [CtVariableReadImpl]deltaX;
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]selectionY += [CtVariableReadImpl]deltaY;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D delta = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtVariableReadImpl]deltaX, [CtVariableReadImpl]deltaY);
            [CtInvocationImpl][CtFieldReadImpl]_positionableSelection.forEach([CtLambdaImpl]([CtParameterImpl] c) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Point2D newPoint = [CtInvocationImpl][CtVariableReadImpl]c.getLocation();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]MemoryIcon) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]c.getPopupUtility().getFixedWidth() == [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]MemoryIcon pm = [CtVariableReadImpl](([CtTypeReferenceImpl]MemoryIcon) (c));
                    [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]Point2D.Double([CtInvocationImpl][CtVariableReadImpl]pm.getOriginalX(), [CtInvocationImpl][CtVariableReadImpl]pm.getOriginalY());
                }
                [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtVariableReadImpl]newPoint, [CtVariableReadImpl]delta);
                [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]MathUtil.zeroPoint2D, [CtVariableReadImpl]newPoint);
                [CtInvocationImpl][CtVariableReadImpl]c.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.point2DToPoint([CtVariableReadImpl]newPoint));
            });
            [CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Point2D newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter(), [CtVariableReadImpl]delta);
                [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]MathUtil.zeroPoint2D, [CtVariableReadImpl]newPoint);
                [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtVariableReadImpl]newPoint);
            });
            [CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.forEach([CtLambdaImpl]([CtParameterImpl] ls) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Point2D newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter(), [CtVariableReadImpl]delta);
                [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]MathUtil.zeroPoint2D, [CtVariableReadImpl]newPoint);
                [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtVariableReadImpl]newPoint);
            });
            [CtInvocationImpl]redrawPanel();
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtInvocationImpl]getLayoutEditorToolBarPanel().keyPressed([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]double returnDeltaPositionX([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.KeyEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]double result = [CtLiteralImpl]0.0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double amount = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]event.isShiftDown()) ? [CtLiteralImpl]5.0 : [CtLiteralImpl]1.0;
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]event.getKeyCode()) {
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]VK_LEFT :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtUnaryOperatorImpl]-[CtVariableReadImpl]amount;
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]VK_RIGHT :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtUnaryOperatorImpl]+[CtVariableReadImpl]amount;
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]double returnDeltaPositionY([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.KeyEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]double result = [CtLiteralImpl]0.0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double amount = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]event.isShiftDown()) ? [CtLiteralImpl]5.0 : [CtLiteralImpl]1.0;
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]event.getKeyCode()) {
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]VK_UP :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtUnaryOperatorImpl]-[CtVariableReadImpl]amount;
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.KeyEvent.[CtFieldReferenceImpl]VK_DOWN :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtUnaryOperatorImpl]+[CtVariableReadImpl]amount;
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtFieldImpl][CtTypeReferenceImpl]int _prevNumSel = [CtLiteralImpl]0;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void mouseMoved([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// initialize mouse position
        calcLocation([CtVariableReadImpl]event);
        [CtAssignmentImpl][CtCommentImpl]// if alt modifier is down invert the snap to grid behaviour
        [CtFieldWriteImpl]snapToGridInvert = [CtInvocationImpl][CtVariableReadImpl]event.isAltDown();
        [CtIfImpl]if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.xLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]xLoc));
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.yLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]yLoc));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Positionable> selections = [CtInvocationImpl]getSelectedItems([CtVariableReadImpl]event);
        [CtLocalVariableImpl][CtTypeReferenceImpl]Positionable selection = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int numSel = [CtInvocationImpl][CtVariableReadImpl]selections.size();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]numSel > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]selection = [CtInvocationImpl][CtVariableReadImpl]selections.get([CtLiteralImpl]0);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]selection != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]selection.getDisplayLevel() > [CtFieldReadImpl]Editor.BKG)) && [CtInvocationImpl][CtVariableReadImpl]selection.showToolTip()) [CtBlockImpl]{
            [CtInvocationImpl]showToolTip([CtVariableReadImpl]selection, [CtVariableReadImpl]event);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.setToolTip([CtLiteralImpl]null);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]numSel != [CtFieldReadImpl]_prevNumSel) [CtBlockImpl]{
            [CtInvocationImpl]redrawPanel();
            [CtAssignmentImpl][CtFieldWriteImpl]_prevNumSel = [CtVariableReadImpl]numSel;
        }
    }[CtCommentImpl]// mouseMoved


    [CtFieldImpl]private [CtTypeReferenceImpl]boolean isDragging = [CtLiteralImpl]false;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void mouseDragged([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// initialize mouse position
        calcLocation([CtVariableReadImpl]event);
        [CtIfImpl][CtCommentImpl]// ignore this event if still at the original point
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtFieldReadImpl]isDragging) && [CtBinaryOperatorImpl]([CtFieldReadImpl]xLoc == [CtInvocationImpl]getAnchorX())) && [CtBinaryOperatorImpl]([CtFieldReadImpl]yLoc == [CtInvocationImpl]getAnchorY())) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtAssignmentImpl][CtCommentImpl]// if alt modifier is down invert the snap to grid behaviour
        [CtFieldWriteImpl]snapToGridInvert = [CtInvocationImpl][CtVariableReadImpl]event.isAltDown();
        [CtIfImpl][CtCommentImpl]// process this mouse dragged event
        if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.xLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]xLoc));
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.yLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]yLoc));
        }
        [CtAssignmentImpl][CtFieldWriteImpl]currentPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtFieldReadImpl]dLoc, [CtFieldReadImpl]startDelta);
        [CtAssignmentImpl][CtCommentImpl]// don't allow negative placement, objects could become unreachable
        [CtFieldWriteImpl]currentPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtFieldReadImpl]currentPoint, [CtTypeAccessImpl]MathUtil.zeroPoint2D);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl]isMetaDown([CtVariableReadImpl]event) || [CtInvocationImpl][CtVariableReadImpl]event.isAltDown())) && [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]MARKER)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// marker moves regardless of editMode or positionable
            [CtTypeReferenceImpl]PositionableLabel pl = [CtFieldReadImpl](([CtTypeReferenceImpl]PositionableLabel) (selectedObject));
            [CtInvocationImpl][CtVariableReadImpl]pl.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getY())));
            [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
            [CtInvocationImpl]redrawPanel();
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtInvocationImpl]isEditable()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null) && [CtInvocationImpl]isMetaDown([CtVariableReadImpl]event)) && [CtInvocationImpl]allPositionable()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]snapToGridOnMove != [CtFieldReadImpl]snapToGridInvert) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// this snaps currentPoint to the grid
                    [CtFieldWriteImpl]currentPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.granulize([CtFieldReadImpl]currentPoint, [CtFieldReadImpl]gridSize1st);
                    [CtAssignmentImpl][CtFieldWriteImpl]xLoc = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getX()));
                    [CtAssignmentImpl][CtFieldWriteImpl]yLoc = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getY()));
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.xLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]xLoc));
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.yLabel.setText([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]yLoc));
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]_positionableSelection.size() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]_layoutTrackSelection.size() > [CtLiteralImpl]0)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]_layoutShapeSelection.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D lastPoint = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtFieldReadImpl]_lastX, [CtFieldReadImpl]_lastY);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D offset = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.subtract([CtFieldReadImpl]currentPoint, [CtVariableReadImpl]lastPoint);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D newPoint;
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Positionable c : [CtFieldReadImpl]_positionableSelection) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]MemoryIcon) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]c.getPopupUtility().getFixedWidth() == [CtLiteralImpl]0)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]MemoryIcon pm = [CtVariableReadImpl](([CtTypeReferenceImpl]MemoryIcon) (c));
                            [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtInvocationImpl][CtVariableReadImpl]pm.getOriginalX(), [CtInvocationImpl][CtVariableReadImpl]pm.getOriginalY());
                        } else [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtVariableReadImpl]c.getLocation();
                        }
                        [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtVariableReadImpl]newPoint, [CtVariableReadImpl]offset);
                        [CtAssignmentImpl][CtCommentImpl]// don't allow negative placement, objects could become unreachable
                        [CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]newPoint, [CtTypeAccessImpl]MathUtil.zeroPoint2D);
                        [CtInvocationImpl][CtVariableReadImpl]c.setLocation([CtInvocationImpl][CtTypeAccessImpl]MathUtil.point2DToPoint([CtVariableReadImpl]newPoint));
                    }
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTrack lt : [CtFieldReadImpl]_layoutTrackSelection) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D center = [CtInvocationImpl][CtVariableReadImpl]lt.getCoordsCenter();
                        [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtVariableReadImpl]center, [CtVariableReadImpl]offset);
                        [CtAssignmentImpl][CtCommentImpl]// don't allow negative placement, objects could become unreachable
                        [CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]newPoint, [CtTypeAccessImpl]MathUtil.zeroPoint2D);
                        [CtInvocationImpl][CtVariableReadImpl]lt.setCoordsCenter([CtVariableReadImpl]newPoint);
                    }
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutShape ls : [CtFieldReadImpl]_layoutShapeSelection) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D center = [CtInvocationImpl][CtVariableReadImpl]ls.getCoordsCenter();
                        [CtAssignmentImpl][CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.add([CtVariableReadImpl]center, [CtVariableReadImpl]offset);
                        [CtAssignmentImpl][CtCommentImpl]// don't allow negative placement, objects could become unreachable
                        [CtVariableWriteImpl]newPoint = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.max([CtVariableReadImpl]newPoint, [CtTypeAccessImpl]MathUtil.zeroPoint2D);
                        [CtInvocationImpl][CtVariableReadImpl]ls.setCoordsCenter([CtVariableReadImpl]newPoint);
                    }
                    [CtAssignmentImpl][CtFieldWriteImpl]_lastX = [CtFieldReadImpl]xLoc;
                    [CtAssignmentImpl][CtFieldWriteImpl]_lastY = [CtFieldReadImpl]yLoc;
                } else [CtBlockImpl]{
                    [CtSwitchImpl]switch ([CtFieldReadImpl]selectedHitPointType) {
                        [CtCaseImpl]case POS_POINT :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]PositionablePoint) (selectedObject)).setCoordsCenter([CtFieldReadImpl]currentPoint);
                                [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case TURNOUT_CENTER :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject)).setCoordsCenter([CtFieldReadImpl]currentPoint);
                                [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case TURNOUT_A :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject)).setCoordsA([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case TURNOUT_B :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject)).setCoordsB([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case TURNOUT_C :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject)).setCoordsC([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case TURNOUT_D :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (selectedObject)).setCoordsD([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LEVEL_XING_CENTER :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LevelXing) (selectedObject)).setCoordsCenter([CtFieldReadImpl]currentPoint);
                                [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LEVEL_XING_A :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LevelXing) (selectedObject)).setCoordsA([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LEVEL_XING_B :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LevelXing) (selectedObject)).setCoordsB([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LEVEL_XING_C :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LevelXing) (selectedObject)).setCoordsC([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LEVEL_XING_D :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LevelXing) (selectedObject)).setCoordsD([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case SLIP_LEFT :
                        [CtCaseImpl]case SLIP_RIGHT :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutSlip) (selectedObject)).setCoordsCenter([CtFieldReadImpl]currentPoint);
                                [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case SLIP_A :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutSlip) (selectedObject)).setCoordsA([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case SLIP_B :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutSlip) (selectedObject)).setCoordsB([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case SLIP_C :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutSlip) (selectedObject)).setCoordsC([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case SLIP_D :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutSlip) (selectedObject)).setCoordsD([CtFieldReadImpl]currentPoint);
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case TURNTABLE_CENTER :
                            [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (selectedObject)).setCoordsCenter([CtFieldReadImpl]currentPoint);
                                [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LAYOUT_POS_LABEL :
                        [CtCaseImpl]case MULTI_SENSOR :
                            [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel pl = [CtFieldReadImpl](([CtTypeReferenceImpl]PositionableLabel) (selectedObject));
                                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]pl.isPositionable()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]pl.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getY())));
                                    [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
                                }
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case LAYOUT_POS_JCOMP :
                            [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableJComponent c = [CtFieldReadImpl](([CtTypeReferenceImpl]PositionableJComponent) (selectedObject));
                                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]c.isPositionable()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]c.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentPoint.getY())));
                                    [CtAssignmentImpl][CtFieldWriteImpl]isDragging = [CtLiteralImpl]true;
                                }
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]case TRACK_CIRCLE_CENTRE :
                            [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t = [CtFieldReadImpl](([CtTypeReferenceImpl]TrackSegment) (selectedObject));
                                [CtInvocationImpl][CtVariableReadImpl]t.reCalculateTrackSegmentAngle([CtInvocationImpl][CtFieldReadImpl]currentPoint.getX(), [CtInvocationImpl][CtFieldReadImpl]currentPoint.getY());
                                [CtBreakImpl]break;
                            }
                        [CtCaseImpl]default :
                            [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isBezierHitType([CtFieldReadImpl]foundHitPointType)) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]selectedHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]BEZIER_CONTROL_POINT_0.getXmlValue();
                                    [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]TrackSegment) (selectedObject)).setBezierControlPoint([CtFieldReadImpl]currentPoint, [CtVariableReadImpl]index);
                                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedHitPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SHAPE_CENTER) [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutShape) (selectedObject)).setCoordsCenter([CtFieldReadImpl]currentPoint);
                                } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]LayoutShape.isShapePointOffsetHitPointType([CtFieldReadImpl]selectedHitPointType)) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]selectedHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]SHAPE_POINT_0.getXmlValue();
                                    [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]LayoutShape) (selectedObject)).setPoint([CtVariableReadImpl]index, [CtFieldReadImpl]currentPoint);
                                } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtFieldReadImpl]selectedHitPointType)) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable turn = [CtFieldReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (selectedObject));
                                    [CtInvocationImpl][CtVariableReadImpl]turn.setRayCoordsIndexed([CtInvocationImpl][CtFieldReadImpl]currentPoint.getX(), [CtInvocationImpl][CtFieldReadImpl]currentPoint.getY(), [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]selectedHitPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0.getXmlValue());
                                }
                                [CtBreakImpl]break;
                            }
                    }
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]beginTrack != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]event.isShiftDown()) && [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.trackButton.isSelected()) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// dragging from first end of Track Segment
                [CtFieldReadImpl]currentLocation.setLocation([CtFieldReadImpl]xLoc, [CtFieldReadImpl]yLoc);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean needResetCursor = [CtBinaryOperatorImpl][CtFieldReadImpl]foundTrack != [CtLiteralImpl]null;
                [CtIfImpl]if ([CtInvocationImpl]findLayoutTracksHitPoint([CtFieldReadImpl]currentLocation, [CtLiteralImpl]true)) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// have match to free connection point, change cursor
                    setCursor([CtInvocationImpl][CtTypeAccessImpl]java.awt.Cursor.getPredefinedCursor([CtFieldReadImpl][CtTypeAccessImpl]java.awt.Cursor.[CtFieldReferenceImpl]CROSSHAIR_CURSOR));
                } else [CtIfImpl]if ([CtVariableReadImpl]needResetCursor) [CtBlockImpl]{
                    [CtInvocationImpl]setCursor([CtInvocationImpl][CtTypeAccessImpl]java.awt.Cursor.getDefaultCursor());
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.isShiftDown() && [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.shapeButton.isSelected()) && [CtBinaryOperatorImpl]([CtFieldReadImpl]selectedObject != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// dragging from end of shape
                [CtFieldReadImpl]currentLocation.setLocation([CtFieldReadImpl]xLoc, [CtFieldReadImpl]yLoc);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]selectionActive && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]event.isShiftDown())) && [CtUnaryOperatorImpl](![CtInvocationImpl]isMetaDown([CtVariableReadImpl]event))) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]selectionWidth = [CtBinaryOperatorImpl][CtFieldReadImpl]xLoc - [CtFieldReadImpl]selectionX;
                [CtAssignmentImpl][CtFieldWriteImpl]selectionHeight = [CtBinaryOperatorImpl][CtFieldReadImpl]yLoc - [CtFieldReadImpl]selectionY;
            }
            [CtInvocationImpl]redrawPanel();
        }[CtCommentImpl]// if (isEditable())
         else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Rectangle r = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Rectangle([CtInvocationImpl][CtVariableReadImpl]event.getX(), [CtInvocationImpl][CtVariableReadImpl]event.getY(), [CtLiteralImpl]1, [CtLiteralImpl]1);
            [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]javax.swing.JComponent) ([CtVariableReadImpl]event.getSource())).scrollRectToVisible([CtVariableReadImpl]r);
        }[CtCommentImpl]// if (isEditable())

    }[CtCommentImpl]// mouseDragged


    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an Anchor point.
     */
    public [CtTypeReferenceImpl]void addAnchor() [CtBlockImpl]{
        [CtInvocationImpl]addAnchor([CtFieldReadImpl]currentPoint);
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    protected [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.PositionablePoint addAnchor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"A", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numAnchors);
        [CtLocalVariableImpl][CtCommentImpl]// create object
        [CtTypeReferenceImpl]PositionablePoint o = [CtConstructorCallImpl]new [CtTypeReferenceImpl]PositionablePoint([CtVariableReadImpl]name, [CtFieldReadImpl]PositionablePoint.ANCHOR, [CtVariableReadImpl]p, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
        [CtReturnImpl]return [CtVariableReadImpl]o;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an End Bumper point.
     */
    public [CtTypeReferenceImpl]void addEndBumper() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"EB", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numEndBumpers);
        [CtLocalVariableImpl][CtCommentImpl]// create object
        [CtTypeReferenceImpl]PositionablePoint o = [CtConstructorCallImpl]new [CtTypeReferenceImpl]PositionablePoint([CtVariableReadImpl]name, [CtFieldReadImpl]PositionablePoint.END_BUMPER, [CtFieldReadImpl]currentPoint, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an Edge Connector point.
     */
    public [CtTypeReferenceImpl]void addEdgeConnector() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"EC", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numEdgeConnectors);
        [CtLocalVariableImpl][CtCommentImpl]// create object
        [CtTypeReferenceImpl]PositionablePoint o = [CtConstructorCallImpl]new [CtTypeReferenceImpl]PositionablePoint([CtVariableReadImpl]name, [CtFieldReadImpl]PositionablePoint.EDGE_CONNECTOR, [CtFieldReadImpl]currentPoint, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a Track Segment
     */
    public [CtTypeReferenceImpl]void addTrackSegment() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"T", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numTrackSegments);
        [CtAssignmentImpl][CtCommentImpl]// create object
        [CtFieldWriteImpl]newTrack = [CtConstructorCallImpl]new [CtTypeReferenceImpl]TrackSegment([CtVariableReadImpl]name, [CtFieldReadImpl]beginTrack, [CtFieldReadImpl]beginHitPointType, [CtFieldReadImpl]foundTrack, [CtFieldReadImpl]foundHitPointType, [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.dashedLine.isSelected(), [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.mainlineTrack.isSelected(), [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtFieldReadImpl]newTrack);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtFieldReadImpl]newTrack.getBounds());
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl][CtCommentImpl]// link to connected objects
        setLink([CtFieldReadImpl]beginTrack, [CtFieldReadImpl]beginHitPointType, [CtFieldReadImpl]newTrack, [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TRACK);
        [CtInvocationImpl]setLink([CtFieldReadImpl]foundTrack, [CtFieldReadImpl]foundHitPointType, [CtFieldReadImpl]newTrack, [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TRACK);
        [CtLocalVariableImpl][CtCommentImpl]// check on layout block
        [CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b = [CtInvocationImpl]provideLayoutBlock([CtVariableReadImpl]newName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]newTrack.setLayoutBlock([CtVariableReadImpl]b);
            [CtInvocationImpl][CtInvocationImpl]getLEAuxTools().setBlockConnectivityChanged();
            [CtLocalVariableImpl][CtCommentImpl]// check on occupancy sensor
            [CtTypeReferenceImpl]java.lang.String sensorName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.getSelectedItemDisplayName();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sensorName == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sensorName = [CtLiteralImpl]"";
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]sensorName.isEmpty()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]validateSensor([CtVariableReadImpl]sensorName, [CtVariableReadImpl]b, [CtThisAccessImpl]this)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]b.setOccupancySensorName([CtLiteralImpl]"");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]b.getOccupancySensor());
                }
            }
            [CtInvocationImpl][CtFieldReadImpl]newTrack.updateBlockInfo();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a Level Crossing
     */
    public [CtTypeReferenceImpl]void addLevelXing() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"X", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numLevelXings);
        [CtLocalVariableImpl][CtCommentImpl]// create object
        [CtTypeReferenceImpl]LevelXing o = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LevelXing([CtVariableReadImpl]name, [CtFieldReadImpl]currentPoint, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
        [CtLocalVariableImpl][CtCommentImpl]// check on layout block
        [CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b = [CtInvocationImpl]provideLayoutBlock([CtVariableReadImpl]newName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]o.setLayoutBlockAC([CtVariableReadImpl]b);
            [CtInvocationImpl][CtVariableReadImpl]o.setLayoutBlockBD([CtVariableReadImpl]b);
            [CtLocalVariableImpl][CtCommentImpl]// check on occupancy sensor
            [CtTypeReferenceImpl]java.lang.String sensorName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.getSelectedItemDisplayName();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sensorName == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sensorName = [CtLiteralImpl]"";
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]sensorName.isEmpty()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]validateSensor([CtVariableReadImpl]sensorName, [CtVariableReadImpl]b, [CtThisAccessImpl]this)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]b.setOccupancySensorName([CtLiteralImpl]"");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]b.getOccupancySensor());
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a LayoutSlip
     *
     * @param type
     * 		the slip type
     */
    public [CtTypeReferenceImpl]void addLayoutSlip([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]LayoutTurnout.TurnoutType type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get the rotation entry
        [CtTypeReferenceImpl]double rot = [CtLiteralImpl]0.0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.rotationComboBox.getEditor().getItem().toString().trim();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]s.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]rot = [CtLiteralImpl]0.0;
        } else [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]rot = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtVariableReadImpl]s);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error3") + [CtLiteralImpl]" ") + [CtVariableReadImpl]e, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
                [CtReturnImpl]return;
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"SL", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numLayoutSlips);
        [CtLocalVariableImpl][CtCommentImpl]// create object
        [CtTypeReferenceImpl]LayoutSlip o = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutSlip([CtVariableReadImpl]name, [CtFieldReadImpl]currentPoint, [CtVariableReadImpl]rot, [CtThisAccessImpl]this, [CtVariableReadImpl]type);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
        [CtLocalVariableImpl][CtCommentImpl]// check on layout block
        [CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b = [CtInvocationImpl]provideLayoutBlock([CtVariableReadImpl]newName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]o.setLayoutBlock([CtVariableReadImpl]b);
            [CtLocalVariableImpl][CtCommentImpl]// check on occupancy sensor
            [CtTypeReferenceImpl]java.lang.String sensorName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.getSelectedItemDisplayName();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sensorName == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sensorName = [CtLiteralImpl]"";
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]sensorName.isEmpty()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]validateSensor([CtVariableReadImpl]sensorName, [CtVariableReadImpl]b, [CtThisAccessImpl]this)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]b.setOccupancySensorName([CtLiteralImpl]"");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]b.getOccupancySensor());
                }
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String turnoutName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]turnoutName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]turnoutName = [CtLiteralImpl]"";
        }
        [CtIfImpl]if ([CtInvocationImpl]validatePhysicalTurnout([CtVariableReadImpl]turnoutName, [CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// turnout is valid and unique.
            [CtVariableReadImpl]o.setTurnout([CtVariableReadImpl]turnoutName);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]o.getTurnout().getSystemName().equals([CtVariableReadImpl]turnoutName)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]o.getTurnout());
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]o.setTurnout([CtLiteralImpl]"");
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.setSelectedItem([CtLiteralImpl]null);
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.setSelectedIndex([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]turnoutName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.extraTurnoutNameComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]turnoutName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]turnoutName = [CtLiteralImpl]"";
        }
        [CtIfImpl]if ([CtInvocationImpl]validatePhysicalTurnout([CtVariableReadImpl]turnoutName, [CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// turnout is valid and unique.
            [CtVariableReadImpl]o.setTurnoutB([CtVariableReadImpl]turnoutName);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]o.getTurnoutB().getSystemName().equals([CtVariableReadImpl]turnoutName)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.extraTurnoutNameComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]o.getTurnoutB());
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]o.setTurnoutB([CtLiteralImpl]"");
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.extraTurnoutNameComboBox.setSelectedItem([CtLiteralImpl]null);
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.extraTurnoutNameComboBox.setSelectedIndex([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a Layout Turnout
     *
     * @param type
     * 		the turnout type
     */
    public [CtTypeReferenceImpl]void addLayoutTurnout([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]LayoutTurnout.TurnoutType type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get the rotation entry
        [CtTypeReferenceImpl]double rot = [CtLiteralImpl]0.0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.rotationComboBox.getEditor().getItem().toString().trim();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]s.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]rot = [CtLiteralImpl]0.0;
        } else [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]rot = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtVariableReadImpl]s);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error3") + [CtLiteralImpl]" ") + [CtVariableReadImpl]e, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
                [CtReturnImpl]return;
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"TO", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numLayoutTurnouts);
        [CtLocalVariableImpl][CtCommentImpl]// create object
        [CtTypeReferenceImpl]LayoutTurnout o = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutTurnout([CtVariableReadImpl]name, [CtVariableReadImpl]type, [CtFieldReadImpl]currentPoint, [CtVariableReadImpl]rot, [CtFieldReadImpl]xScale, [CtFieldReadImpl]yScale, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
        [CtLocalVariableImpl][CtCommentImpl]// check on layout block
        [CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b = [CtInvocationImpl]provideLayoutBlock([CtVariableReadImpl]newName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]o.setLayoutBlock([CtVariableReadImpl]b);
            [CtLocalVariableImpl][CtCommentImpl]// check on occupancy sensor
            [CtTypeReferenceImpl]java.lang.String sensorName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.getSelectedItemDisplayName();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sensorName == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sensorName = [CtLiteralImpl]"";
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]sensorName.isEmpty()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]validateSensor([CtVariableReadImpl]sensorName, [CtVariableReadImpl]b, [CtThisAccessImpl]this)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]b.setOccupancySensorName([CtLiteralImpl]"");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockSensorComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]b.getOccupancySensor());
                }
            }
        }
        [CtInvocationImpl][CtCommentImpl]// set default continuing route Turnout State
        [CtVariableReadImpl]o.setContinuingSense([CtTypeAccessImpl]Turnout.CLOSED);
        [CtLocalVariableImpl][CtCommentImpl]// check on a physical turnout
        [CtTypeReferenceImpl]java.lang.String turnoutName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]turnoutName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]turnoutName = [CtLiteralImpl]"";
        }
        [CtIfImpl]if ([CtInvocationImpl]validatePhysicalTurnout([CtVariableReadImpl]turnoutName, [CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// turnout is valid and unique.
            [CtVariableReadImpl]o.setTurnout([CtVariableReadImpl]turnoutName);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]o.getTurnout().getSystemName().equals([CtVariableReadImpl]turnoutName)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]o.getTurnout());
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]o.setTurnout([CtLiteralImpl]"");
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.setSelectedItem([CtLiteralImpl]null);
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.turnoutNameComboBox.setSelectedIndex([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Validates that a physical turnout exists and is unique among Layout
     * Turnouts Returns true if valid turnout was entered, false otherwise
     *
     * @param inTurnoutName
     * 		the (system or user) name of the turnout
     * @param inOpenPane
     * 		the pane over which to show dialogs (null to
     * 		suppress dialogs)
     * @return true if valid
     */
    public [CtTypeReferenceImpl]boolean validatePhysicalTurnout([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String inTurnoutName, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]java.awt.Component inOpenPane) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// check if turnout name was entered
        if ([CtInvocationImpl][CtVariableReadImpl]inTurnoutName.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// no turnout entered
            return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtCommentImpl]// check that the unique turnout name corresponds to a defined physical turnout
        [CtTypeReferenceImpl]Turnout t = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.turnoutManagerInstance().getTurnout([CtVariableReadImpl]inTurnoutName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// There is no turnout corresponding to this name
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]inOpenPane != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtVariableReadImpl]inOpenPane, [CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error8"), [CtVariableReadImpl]inTurnoutName), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"validatePhysicalTurnout('{}')", [CtVariableReadImpl]inTurnoutName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]true;[CtCommentImpl]// assume success (optimist!)

        [CtForEachImpl][CtCommentImpl]// ensure that this turnout is unique among Layout Turnouts in this Layout
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt : [CtInvocationImpl]getLayoutTurnouts()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtVariableReadImpl]lt.getTurnout();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sname = [CtInvocationImpl][CtVariableReadImpl]t.getSystemName();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]t.getUserName();
                [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"{}: Turnout tested '{}' and '{}'.", [CtInvocationImpl][CtVariableReadImpl]lt.getName(), [CtVariableReadImpl]sname, [CtVariableReadImpl]uname);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sname.equals([CtVariableReadImpl]inTurnoutName) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]uname != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]uname.equals([CtVariableReadImpl]inTurnoutName))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;
                    [CtBreakImpl]break;
                }
            }
            [CtIfImpl][CtCommentImpl]// Only check for the second turnout if the type is a double cross over
            [CtCommentImpl]// otherwise the second turnout is used to throw an additional turnout at
            [CtCommentImpl]// the same time.
            if ([CtInvocationImpl][CtVariableReadImpl]lt.isTurnoutTypeXover()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtVariableReadImpl]lt.getSecondTurnout();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sname = [CtInvocationImpl][CtVariableReadImpl]t.getSystemName();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]t.getUserName();
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"{}: 2nd Turnout tested '{}' and '{}'.", [CtInvocationImpl][CtVariableReadImpl]lt.getName(), [CtVariableReadImpl]sname, [CtVariableReadImpl]uname);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sname.equals([CtVariableReadImpl]inTurnoutName) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]uname != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]uname.equals([CtVariableReadImpl]inTurnoutName))) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;
                        [CtBreakImpl]break;
                    }
                }
            }
        }
        [CtIfImpl]if ([CtVariableReadImpl]result) [CtBlockImpl]{
            [CtForEachImpl][CtCommentImpl]// only need to test slips if we haven't failed yet...
            [CtCommentImpl]// ensure that this turnout is unique among Layout slips in this Layout
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutSlip sl : [CtInvocationImpl]getLayoutSlips()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtVariableReadImpl]sl.getTurnout();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sname = [CtInvocationImpl][CtVariableReadImpl]t.getSystemName();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]t.getUserName();
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"{}: slip Turnout tested '{}' and '{}'.", [CtInvocationImpl][CtVariableReadImpl]sl.getName(), [CtVariableReadImpl]sname, [CtVariableReadImpl]uname);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sname.equals([CtVariableReadImpl]inTurnoutName) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]uname != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]uname.equals([CtVariableReadImpl]inTurnoutName))) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;
                        [CtBreakImpl]break;
                    }
                }
                [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtVariableReadImpl]sl.getTurnoutB();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sname = [CtInvocationImpl][CtVariableReadImpl]t.getSystemName();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]t.getUserName();
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"{}: slip Turnout B tested '{}' and '{}'.", [CtInvocationImpl][CtVariableReadImpl]sl.getName(), [CtVariableReadImpl]sname, [CtVariableReadImpl]uname);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sname.equals([CtVariableReadImpl]inTurnoutName) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]uname != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]uname.equals([CtVariableReadImpl]inTurnoutName))) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;
                        [CtBreakImpl]break;
                    }
                }
            }
        }
        [CtIfImpl]if ([CtVariableReadImpl]result) [CtBlockImpl]{
            [CtForEachImpl][CtCommentImpl]// only need to test Turntable turnouts if we haven't failed yet...
            [CtCommentImpl]// ensure that this turntable turnout is unique among turnouts in this Layout
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable tt : [CtInvocationImpl]getLayoutTurntables()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]LayoutTurntable.RayTrack ray : [CtInvocationImpl][CtVariableReadImpl]tt.getRayList()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtVariableReadImpl]ray.getTurnout();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sname = [CtInvocationImpl][CtVariableReadImpl]t.getSystemName();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]t.getUserName();
                        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"{}: Turntable turnout tested '{}' and '{}'.", [CtInvocationImpl][CtVariableReadImpl]ray.getTurnoutName(), [CtVariableReadImpl]sname, [CtVariableReadImpl]uname);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sname.equals([CtVariableReadImpl]inTurnoutName) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]uname != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]uname.equals([CtVariableReadImpl]inTurnoutName))) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]false;
                            [CtBreakImpl]break;
                        }
                    }
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]result) && [CtBinaryOperatorImpl]([CtVariableReadImpl]inOpenPane != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtVariableReadImpl]inOpenPane, [CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error4"), [CtVariableReadImpl]inTurnoutName), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * link the 'from' object and type to the 'to' object and type
     *
     * @param fromObject
     * 		the object to link from
     * @param fromPointType
     * 		the object type to link from
     * @param toObject
     * 		the object to link to
     * @param toPointType
     * 		the object type to link to
     */
    protected [CtTypeReferenceImpl]void setLink([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTrack fromObject, [CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType fromPointType, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTrack toObject, [CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType toPointType) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]fromPointType) {
            [CtCaseImpl]case POS_POINT :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]toPointType == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TRACK) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]PositionablePoint) (fromObject)).setTrackConnection([CtVariableReadImpl](([CtTypeReferenceImpl]TrackSegment) (toObject)));
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.error([CtLiteralImpl]"Attempt to link a non-TRACK connection ('{}')to a Positionable Point ('{}')", [CtInvocationImpl][CtVariableReadImpl]toObject.getName(), [CtInvocationImpl][CtVariableReadImpl]fromObject.getName());
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case TURNOUT_A :
            [CtCaseImpl]case TURNOUT_B :
            [CtCaseImpl]case TURNOUT_C :
            [CtCaseImpl]case TURNOUT_D :
            [CtCaseImpl]case SLIP_A :
            [CtCaseImpl]case SLIP_B :
            [CtCaseImpl]case SLIP_C :
            [CtCaseImpl]case SLIP_D :
            [CtCaseImpl]case LEVEL_XING_A :
            [CtCaseImpl]case LEVEL_XING_B :
            [CtCaseImpl]case LEVEL_XING_C :
            [CtCaseImpl]case LEVEL_XING_D :
                [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]fromObject.setConnection([CtVariableReadImpl]fromPointType, [CtVariableReadImpl]toObject, [CtVariableReadImpl]toPointType);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]JmriException e) [CtBlockImpl]{
                        [CtCommentImpl]// ignore (log.error in setConnection method)
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case TRACK :
                [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// should never happen, Track Segment links are set in ctor
                    [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.error([CtLiteralImpl]"Illegal request to set a Track Segment link");
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtVariableReadImpl]fromPointType)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (fromObject)).setRayConnect([CtVariableReadImpl](([CtTypeReferenceImpl]TrackSegment) (toObject)), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fromPointType.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0.getXmlValue());
                    }
                    [CtBreakImpl]break;
                }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return a layout block with the entered name, creating a new one if
     * needed. Note that the entered name becomes the user name of the
     * LayoutBlock, and a system name is automatically created by
     * LayoutBlockManager if needed.
     * <p>
     * If the block name is a system name, then the user will have to supply a
     * user name for the block.
     *
     * @param inBlockName
     * 		the entered name
     * @return the provided LayoutBlock
     */
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutBlock provideLayoutBlock([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String inBlockName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock result = [CtLiteralImpl]null;[CtCommentImpl]// assume failure (pessimist!)

        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock newBlk = [CtLiteralImpl]null;[CtCommentImpl]// assume failure (pessimist!)

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]inBlockName.isEmpty()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// nothing entered, try autoAssign
            if ([CtFieldReadImpl]autoAssignBlocks) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]newBlk = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class).createNewLayoutBlock();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]newBlk) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.error([CtLiteralImpl]"provideLayoutBlock: Failure to auto-assign LayoutBlock '{}'.", [CtVariableReadImpl]inBlockName);
                }
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// check if this Layout Block already exists
            [CtVariableWriteImpl]result = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class).getByUserName([CtVariableReadImpl]inBlockName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// (no)
                [CtCommentImpl]// The combo box name can be either a block system name or a block user name
                [CtTypeReferenceImpl]Block checkBlock = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.BlockManager.class).getBlock([CtVariableReadImpl]inBlockName);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]checkBlock == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.error([CtLiteralImpl]"provideLayoutBlock: The block name '{}' does not return a block.", [CtVariableReadImpl]inBlockName);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String checkUserName = [CtInvocationImpl][CtVariableReadImpl]checkBlock.getUserName();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]checkUserName != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]checkUserName.equals([CtVariableReadImpl]inBlockName)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// Go ahead and use the name for the layout block
                        [CtVariableWriteImpl]newBlk = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class).createNewLayoutBlock([CtLiteralImpl]null, [CtVariableReadImpl]inBlockName);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newBlk == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.error([CtLiteralImpl]"provideLayoutBlock: Failure to create new LayoutBlock '{}'.", [CtVariableReadImpl]inBlockName);
                        }
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// Appears to be a system name, request a user name
                        [CtTypeReferenceImpl]java.lang.String blkUserName = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showInputDialog([CtInvocationImpl]getTargetFrame(), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"BlkUserNameMsg"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"BlkUserNameTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]PLAIN_MESSAGE);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]blkUserName != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]blkUserName.isEmpty())) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtCommentImpl]// Verify the user name
                            [CtTypeReferenceImpl]Block checkDuplicate = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.BlockManager.class).getByUserName([CtVariableReadImpl]blkUserName);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]checkDuplicate != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtInvocationImpl]getTargetFrame(), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"BlkUserNameInUse", [CtVariableReadImpl]blkUserName), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtCommentImpl]// OK to use as a block user name
                                [CtVariableReadImpl]checkBlock.setUserName([CtVariableReadImpl]blkUserName);
                                [CtAssignmentImpl][CtVariableWriteImpl]newBlk = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class).createNewLayoutBlock([CtLiteralImpl]null, [CtVariableReadImpl]blkUserName);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newBlk == [CtLiteralImpl]null) [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.error([CtLiteralImpl]"provideLayoutBlock: Failure to create new LayoutBlock '{}' with a new user name.", [CtVariableReadImpl]blkUserName);
                                }
                            }
                        }
                    }
                }
            }
        }
        [CtIfImpl][CtCommentImpl]// if we created a new block
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]newBlk != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// initialize the new block
            [CtCommentImpl]// log.debug("provideLayoutBlock :: Init new block {}", inBlockName);
            [CtVariableReadImpl]newBlk.initializeLayoutBlock();
            [CtInvocationImpl][CtVariableReadImpl]newBlk.initializeLayoutBlockRouting();
            [CtInvocationImpl][CtVariableReadImpl]newBlk.setBlockTrackColor([CtFieldReadImpl]defaultTrackColor);
            [CtInvocationImpl][CtVariableReadImpl]newBlk.setBlockOccupiedColor([CtFieldReadImpl]defaultOccupiedTrackColor);
            [CtInvocationImpl][CtVariableReadImpl]newBlk.setBlockExtraColor([CtFieldReadImpl]defaultAlternativeTrackColor);
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]newBlk;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// set both new and previously existing block
            [CtVariableReadImpl]result.addLayoutEditor([CtThisAccessImpl]this);
            [CtInvocationImpl][CtVariableReadImpl]result.incrementUse();
            [CtInvocationImpl]setDirty();
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Validates that the supplied occupancy sensor name corresponds to an
     * existing sensor and is unique among all blocks. If valid, returns true
     * and sets the block sensor name in the block. Else returns false, and does
     * nothing to the block.
     *
     * @param sensorName
     * 		the sensor name to validate
     * @param blk
     * 		the LayoutBlock in which to set it
     * @param openFrame
     * 		the frame (Component) it is in
     * @return true if sensor is valid
     */
    public [CtTypeReferenceImpl]boolean validateSensor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String sensorName, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutBlock blk, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.Component openFrame) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;[CtCommentImpl]// assume failure (pessimist!)

        [CtIfImpl][CtCommentImpl]// check if anything entered
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]sensorName.isEmpty()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// get a validated sensor corresponding to this name and assigned to block
            if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]blk.getOccupancySensorName().equals([CtVariableReadImpl]sensorName)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Sensor s = [CtInvocationImpl][CtVariableReadImpl]blk.validateSensor([CtVariableReadImpl]sensorName, [CtVariableReadImpl]openFrame);
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtVariableReadImpl]s != [CtLiteralImpl]null;[CtCommentImpl]// if sensor returned result is true.

            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return a layout block with the given name if one exists. Registers this
     * LayoutEditor with the layout block. This method is designed to be used
     * when a panel is loaded. The calling method must handle whether the use
     * count should be incremented.
     *
     * @param blockID
     * 		the given name
     * @return null if blockID does not already exist
     */
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutBlock getLayoutBlock([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String blockID) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// check if this Layout Block already exists
        [CtTypeReferenceImpl]LayoutBlock blk = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class).getByUserName([CtVariableReadImpl]blockID);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]blk == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.error([CtLiteralImpl]"LayoutBlock '{}' not found when panel loaded", [CtVariableReadImpl]blockID);
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtInvocationImpl][CtVariableReadImpl]blk.addLayoutEditor([CtThisAccessImpl]this);
        [CtReturnImpl]return [CtVariableReadImpl]blk;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove object from all Layout Editor temporary lists of items not part of
     * track schematic
     *
     * @param s
     * 		the object to remove
     * @return true if found
     */
    protected [CtTypeReferenceImpl]boolean remove([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.Object s) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sensorImage.contains([CtVariableReadImpl]s) || [CtInvocationImpl][CtFieldReadImpl]sensorList.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]removeNxSensor([CtVariableReadImpl](([CtTypeReferenceImpl]SensorIcon) (s)))) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]sensorImage.remove([CtVariableReadImpl]s);
                [CtInvocationImpl][CtFieldReadImpl]sensorList.remove([CtVariableReadImpl]s);
                [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]backgroundImage.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]backgroundImage.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]memoryLabelList.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]memoryLabelList.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]blockContentsLabelList.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]blockContentsLabelList.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]signalList.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]signalList.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]multiSensors.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]multiSensors.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]clocks.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clocks.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]signalHeadImage.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]signalHeadImage.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]labelImage.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]labelImage.remove([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]signalMastList.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s == [CtInvocationImpl][CtFieldReadImpl]signalMastList.get([CtVariableReadImpl]i)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]removeSignalMast([CtVariableReadImpl](([CtTypeReferenceImpl]SignalMastIcon) (s)))) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]signalMastList.remove([CtVariableReadImpl]i);
                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                    [CtBreakImpl]break;
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.removeFromContents([CtVariableReadImpl](([CtTypeReferenceImpl]Positionable) (s)));
        [CtIfImpl]if ([CtVariableReadImpl]found) [CtBlockImpl]{
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
        }
        [CtReturnImpl]return [CtVariableReadImpl]found;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean removeFromContents([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Positionable l) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]remove([CtVariableReadImpl]l);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String findBeanUsage([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]NamedBean sm) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint pe;
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint pw;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LevelXing lx;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutSlip ls;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msgKey = [CtLiteralImpl]"DeleteReference";[CtCommentImpl]// NOI18N

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String beanKey = [CtLiteralImpl]"None";[CtCommentImpl]// NOI18N

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String beanValue = [CtInvocationImpl][CtVariableReadImpl]sm.getDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalMast) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]beanKey = [CtLiteralImpl]"BeanNameSignalMast";[CtCommentImpl]// NOI18N

            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalMastLogicManager.class).isSignalMastUsed([CtVariableReadImpl](([CtTypeReferenceImpl]SignalMast) (sm)))) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMastLogic sml = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalMastLogicManager.class).getSignalMastLogic([CtVariableReadImpl](([CtTypeReferenceImpl]SignalMast) (sm)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]sml != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]sml.useLayoutEditor([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sml.getDestinationList().get([CtLiteralImpl]0))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]msgKey = [CtLiteralImpl]"DeleteSmlReference";[CtCommentImpl]// NOI18N

                }
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Sensor) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]beanKey = [CtLiteralImpl]"BeanNameSensor";[CtCommentImpl]// NOI18N

        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalHead) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]beanKey = [CtLiteralImpl]"BeanNameSignalHead";[CtCommentImpl]// NOI18N

        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]beanKey.equals([CtLiteralImpl]"None")) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// NOI18N
            [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtVariableReadImpl]msgKey, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtVariableReadImpl]beanKey), [CtVariableReadImpl]beanValue));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]pw = [CtInvocationImpl][CtFieldReadImpl]finder.findPositionablePointByWestBoundBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t1 = [CtInvocationImpl][CtVariableReadImpl]pw.getConnect1();
            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t2 = [CtInvocationImpl][CtVariableReadImpl]pw.getConnect2();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t1 != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t2 != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtPoint1", [CtInvocationImpl][CtVariableReadImpl]t1.getBlockName()));[CtCommentImpl]// NOI18N

                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtPoint2", [CtInvocationImpl][CtVariableReadImpl]t2.getBlockName()));[CtCommentImpl]// NOI18N

                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtPoint1", [CtInvocationImpl][CtVariableReadImpl]t1.getBlockName()));[CtCommentImpl]// NOI18N

                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]pe = [CtInvocationImpl][CtFieldReadImpl]finder.findPositionablePointByEastBoundBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t1 = [CtInvocationImpl][CtVariableReadImpl]pe.getConnect1();
            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t2 = [CtInvocationImpl][CtVariableReadImpl]pe.getConnect2();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t1 != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t2 != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtPoint1", [CtInvocationImpl][CtVariableReadImpl]t1.getBlockName()));[CtCommentImpl]// NOI18N

                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtPoint2", [CtInvocationImpl][CtVariableReadImpl]t2.getBlockName()));[CtCommentImpl]// NOI18N

                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtPoint1", [CtInvocationImpl][CtVariableReadImpl]t1.getBlockName()));[CtCommentImpl]// NOI18N

                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]lt = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutTurnoutByBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtOther", [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"BeanNameTurnout"), [CtInvocationImpl][CtVariableReadImpl]lt.getTurnoutName()));[CtCommentImpl]// NOI18N

            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]lx = [CtInvocationImpl][CtFieldReadImpl]finder.findLevelXingByBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtOther", [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"LevelCrossing"), [CtInvocationImpl][CtVariableReadImpl]lx.getId()));[CtCommentImpl]// NOI18N

            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]ls = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutSlipByBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"DeleteAtOther", [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Slip"), [CtInvocationImpl][CtVariableReadImpl]ls.getTurnoutName()));[CtCommentImpl]// NOI18N

            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]found) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean removeSignalMast([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]SignalMastIcon si) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMast sm = [CtInvocationImpl][CtVariableReadImpl]si.getSignalMast();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String usage = [CtInvocationImpl]findBeanUsage([CtVariableReadImpl]sm);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]usage != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]usage = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"<html>%s</html>", [CtVariableReadImpl]usage);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtVariableReadImpl]usage, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonCancel") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;[CtCommentImpl]// return leaving the references in place but allow the icon to be deleted.

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// do not delete the item

            }
            [CtInvocationImpl]removeBeanRefs([CtVariableReadImpl]sm);
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean removeNxSensor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]SensorIcon si) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Sensor sn = [CtInvocationImpl][CtVariableReadImpl]si.getSensor();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String usage = [CtInvocationImpl]findBeanUsage([CtVariableReadImpl]sn);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]usage != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]usage = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"<html>%s</html>", [CtVariableReadImpl]usage);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtVariableReadImpl]usage, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonCancel") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;[CtCommentImpl]// return leaving the references in place but allow the icon to be deleted.

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// do not delete the item

            }
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getLETools().removeSensorAssignment([CtVariableReadImpl]sn);
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void removeBeanRefs([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]NamedBean sm) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint pe;
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint pw;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LevelXing lx;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutSlip ls;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]pw = [CtInvocationImpl][CtFieldReadImpl]finder.findPositionablePointByWestBoundBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]pw.removeBeanReference([CtVariableReadImpl]sm);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]pe = [CtInvocationImpl][CtFieldReadImpl]finder.findPositionablePointByEastBoundBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]pe.removeBeanReference([CtVariableReadImpl]sm);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]lt = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutTurnoutByBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lt.removeBeanReference([CtVariableReadImpl]sm);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]lx = [CtInvocationImpl][CtFieldReadImpl]finder.findLevelXingByBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lx.removeBeanReference([CtVariableReadImpl]sm);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]ls = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutSlipByBean([CtVariableReadImpl]sm)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ls.removeBeanReference([CtVariableReadImpl]sm);
        }
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean noWarnPositionablePoint = [CtLiteralImpl]false;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a PositionablePoint -- an Anchor or an End Bumper.
     *
     * @param o
     * 		the PositionablePoint to remove
     * @return true if removed
     */
    protected [CtTypeReferenceImpl]boolean removePositionablePoint([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]PositionablePoint o) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// First verify with the user that this is really wanted, only show message if there is a bit of track connected
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]o.getConnect1() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]o.getConnect2() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]noWarnPositionablePoint) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Question2"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYesPlus") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// return without creating if "No" response

                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Suppress future warnings, and continue
                    [CtFieldWriteImpl]noWarnPositionablePoint = [CtLiteralImpl]true;
                }
            }
            [CtIfImpl][CtCommentImpl]// remove from selection information
            if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]prevSelectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]prevSelectedObject = [CtLiteralImpl]null;
            }
            [CtLocalVariableImpl][CtCommentImpl]// remove connections if any
            [CtTypeReferenceImpl]TrackSegment t1 = [CtInvocationImpl][CtVariableReadImpl]o.getConnect1();
            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t2 = [CtInvocationImpl][CtVariableReadImpl]o.getConnect2();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t1 != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]removeTrackSegment([CtVariableReadImpl]t1);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t2 != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]removeTrackSegment([CtVariableReadImpl]t2);
            }
            [CtCommentImpl]// delete from array
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]layoutTrackList.contains([CtVariableReadImpl]o)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.remove([CtVariableReadImpl]o);
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean noWarnLayoutTurnout = [CtLiteralImpl]false;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a LayoutTurnout
     *
     * @param o
     * 		the LayoutTurnout to remove
     * @return true if removed
     */
    protected [CtTypeReferenceImpl]boolean removeLayoutTurnout([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTurnout o) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// First verify with the user that this is really wanted
        if ([CtUnaryOperatorImpl]![CtFieldReadImpl]noWarnLayoutTurnout) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Question1r"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYesPlus") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// return without removing if "No" response

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Suppress future warnings, and continue
                [CtFieldWriteImpl]noWarnLayoutTurnout = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// remove from selection information
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]prevSelectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]prevSelectedObject = [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtCommentImpl]// remove connections if any
        [CtTypeReferenceImpl]TrackSegment t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectA()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsA(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectB()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsB(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectC()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsC(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectD()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsD(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtLocalVariableImpl][CtCommentImpl]// decrement Block use count(s)
        [CtTypeReferenceImpl]LayoutBlock b = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlock();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]b.decrementUse();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]o.isTurnoutTypeXover() || [CtInvocationImpl][CtVariableReadImpl]o.isTurnoutTypeSlip()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b2 = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlockB();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]b2 != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]b2 != [CtVariableReadImpl]b)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]b2.decrementUse();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b3 = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlockC();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]b3 != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]b3 != [CtVariableReadImpl]b)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]b3 != [CtVariableReadImpl]b2)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]b3.decrementUse();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock b4 = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlockD();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]b4 != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]b4 != [CtVariableReadImpl]b)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]b4 != [CtVariableReadImpl]b2)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]b4 != [CtVariableReadImpl]b3)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]b4.decrementUse();
            }
        }
        [CtIfImpl][CtCommentImpl]// delete from array
        if ([CtInvocationImpl][CtFieldReadImpl]layoutTrackList.contains([CtVariableReadImpl]o)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.remove([CtVariableReadImpl]o);
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void substituteAnchor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D loc, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTrack o, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]TrackSegment t) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint p = [CtInvocationImpl]addAnchor([CtVariableReadImpl]loc);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getConnect1() == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect1([CtVariableReadImpl]p, [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]POS_POINT);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getConnect2() == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]t.setNewConnect2([CtVariableReadImpl]p, [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]POS_POINT);
        }
        [CtInvocationImpl][CtVariableReadImpl]p.setTrackConnection([CtVariableReadImpl]t);
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean noWarnLevelXing = [CtLiteralImpl]false;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a Level Crossing
     *
     * @param o
     * 		the LevelXing to remove
     * @return true if removed
     */
    protected [CtTypeReferenceImpl]boolean removeLevelXing([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LevelXing o) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// First verify with the user that this is really wanted
        if ([CtUnaryOperatorImpl]![CtFieldReadImpl]noWarnLevelXing) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Question3r"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYesPlus") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// return without creating if "No" response

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Suppress future warnings, and continue
                [CtFieldWriteImpl]noWarnLevelXing = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// remove from selection information
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]prevSelectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]prevSelectedObject = [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtCommentImpl]// remove connections if any
        [CtTypeReferenceImpl]TrackSegment t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectA()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsA(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectB()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsB(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectC()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsC(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectD()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsD(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtLocalVariableImpl][CtCommentImpl]// decrement block use count if any blocks in use
        [CtTypeReferenceImpl]LayoutBlock lb = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlockAC();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lb != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lb.decrementUse();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock lbx = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlockBD();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]lbx != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]lb != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]lbx != [CtVariableReadImpl]lb)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lb.decrementUse();
        }
        [CtIfImpl][CtCommentImpl]// delete from array
        if ([CtInvocationImpl][CtFieldReadImpl]layoutTrackList.contains([CtVariableReadImpl]o)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.remove([CtVariableReadImpl]o);
            [CtInvocationImpl][CtVariableReadImpl]o.remove();
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean noWarnSlip = [CtLiteralImpl]false;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a slip
     *
     * @param o
     * 		the LayoutSlip to remove
     * @return true if removed
     */
    protected [CtTypeReferenceImpl]boolean removeLayoutSlip([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTurnout o) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutSlip)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl][CtCommentImpl]// First verify with the user that this is really wanted
        if ([CtUnaryOperatorImpl]![CtFieldReadImpl]noWarnSlip) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Question5r"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYesPlus") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// return without creating if "No" response

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Suppress future warnings, and continue
                [CtFieldWriteImpl]noWarnSlip = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// remove from selection information
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]prevSelectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]prevSelectedObject = [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtCommentImpl]// remove connections if any
        [CtTypeReferenceImpl]TrackSegment t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectA()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsA(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectB()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsB(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectC()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsC(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl](([CtTypeReferenceImpl]TrackSegment) ([CtVariableReadImpl]o.getConnectD()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getCoordsD(), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
        }
        [CtLocalVariableImpl][CtCommentImpl]// decrement block use count if any blocks in use
        [CtTypeReferenceImpl]LayoutBlock lb = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlock();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lb != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]lb.decrementUse();
        }
        [CtIfImpl][CtCommentImpl]// delete from array
        if ([CtInvocationImpl][CtFieldReadImpl]layoutTrackList.contains([CtVariableReadImpl]o)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.remove([CtVariableReadImpl]o);
            [CtInvocationImpl][CtVariableReadImpl]o.remove();
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean noWarnTurntable = [CtLiteralImpl]false;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a Layout Turntable
     *
     * @param o
     * 		the LayoutTurntable to remove
     * @return true if removed
     */
    protected [CtTypeReferenceImpl]boolean removeTurntable([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTurntable o) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// First verify with the user that this is really wanted
        if ([CtUnaryOperatorImpl]![CtFieldReadImpl]noWarnTurntable) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int selectedValue = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Question4r"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"WarningTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE, [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYes"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonYesPlus") }, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ButtonNo"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// return without creating if "No" response

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectedValue == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]CANCEL_OPTION) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Suppress future warnings, and continue
                [CtFieldWriteImpl]noWarnTurntable = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl][CtCommentImpl]// remove from selection information
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]selectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]selectedObject = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]prevSelectedObject == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]prevSelectedObject = [CtLiteralImpl]null;
        }
        [CtForImpl][CtCommentImpl]// remove connections if any
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int j = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]j < [CtInvocationImpl][CtVariableReadImpl]o.getNumberRays(); [CtUnaryOperatorImpl][CtVariableWriteImpl]j++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment t = [CtInvocationImpl][CtVariableReadImpl]o.getRayConnectOrdered([CtVariableReadImpl]j);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]substituteAnchor([CtInvocationImpl][CtVariableReadImpl]o.getRayCoordsIndexed([CtVariableReadImpl]j), [CtVariableReadImpl]o, [CtVariableReadImpl]t);
            }
        }
        [CtIfImpl][CtCommentImpl]// delete from array
        if ([CtInvocationImpl][CtFieldReadImpl]layoutTrackList.contains([CtVariableReadImpl]o)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.remove([CtVariableReadImpl]o);
            [CtInvocationImpl][CtVariableReadImpl]o.remove();
            [CtInvocationImpl]setDirty();
            [CtInvocationImpl]redrawPanel();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a Track Segment
     *
     * @param o
     * 		the TrackSegment to remove
     */
    protected [CtTypeReferenceImpl]void removeTrackSegment([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]TrackSegment o) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// save affected blocks
        [CtTypeReferenceImpl]LayoutBlock block1 = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock block2 = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock block = [CtInvocationImpl][CtVariableReadImpl]o.getLayoutBlock();
        [CtLocalVariableImpl][CtCommentImpl]// remove any connections
        [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType type = [CtInvocationImpl][CtVariableReadImpl]o.getType1();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]POS_POINT) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint p = [CtInvocationImpl](([CtTypeReferenceImpl]PositionablePoint) ([CtVariableReadImpl]o.getConnect1()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]p.removeTrackConnection([CtVariableReadImpl]o);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect1() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]block1 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect1().getLayoutBlock();
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect2() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]block1 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect2().getLayoutBlock();
                }
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]block1 = [CtInvocationImpl]getAffectedBlock([CtInvocationImpl][CtVariableReadImpl]o.getConnect1(), [CtVariableReadImpl]type);
            [CtInvocationImpl]disconnect([CtInvocationImpl][CtVariableReadImpl]o.getConnect1(), [CtVariableReadImpl]type);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]type = [CtInvocationImpl][CtVariableReadImpl]o.getType2();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]POS_POINT) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint p = [CtInvocationImpl](([CtTypeReferenceImpl]PositionablePoint) ([CtVariableReadImpl]o.getConnect2()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]p.removeTrackConnection([CtVariableReadImpl]o);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect1() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]block2 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect1().getLayoutBlock();
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect2() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]block2 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getConnect2().getLayoutBlock();
                }
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]block2 = [CtInvocationImpl]getAffectedBlock([CtInvocationImpl][CtVariableReadImpl]o.getConnect2(), [CtVariableReadImpl]type);
            [CtInvocationImpl]disconnect([CtInvocationImpl][CtVariableReadImpl]o.getConnect2(), [CtVariableReadImpl]type);
        }
        [CtIfImpl][CtCommentImpl]// delete from array
        if ([CtInvocationImpl][CtFieldReadImpl]layoutTrackList.contains([CtVariableReadImpl]o)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.remove([CtVariableReadImpl]o);
        }
        [CtIfImpl][CtCommentImpl]// update affected blocks
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]block != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// decrement Block use count
            [CtVariableReadImpl]block.decrementUse();
            [CtInvocationImpl][CtInvocationImpl]getLEAuxTools().setBlockConnectivityChanged();
            [CtInvocationImpl][CtVariableReadImpl]block.updatePaths();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]block1 != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]block1 != [CtVariableReadImpl]block)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]block1.updatePaths();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]block2 != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]block2 != [CtVariableReadImpl]block)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]block2 != [CtVariableReadImpl]block1)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]block2.updatePaths();
        }
        [CtInvocationImpl][CtCommentImpl]// 
        setDirty();
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void disconnect([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTrack o, [CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType type) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
            [CtCaseImpl]case TURNOUT_A :
            [CtCaseImpl]case TURNOUT_B :
            [CtCaseImpl]case TURNOUT_C :
            [CtCaseImpl]case TURNOUT_D :
            [CtCaseImpl]case SLIP_A :
            [CtCaseImpl]case SLIP_B :
            [CtCaseImpl]case SLIP_C :
            [CtCaseImpl]case SLIP_D :
            [CtCaseImpl]case LEVEL_XING_A :
            [CtCaseImpl]case LEVEL_XING_B :
            [CtCaseImpl]case LEVEL_XING_C :
            [CtCaseImpl]case LEVEL_XING_D :
                [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]o.setConnection([CtVariableReadImpl]type, [CtLiteralImpl]null, [CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]NONE);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]JmriException e) [CtBlockImpl]{
                        [CtCommentImpl]// ignore (log.error in setConnection method)
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.isTurntableRayHitType([CtVariableReadImpl]type)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurntable) (o)).setRayConnect([CtLiteralImpl]null, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]type.getXmlValue() - [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType.[CtFieldReferenceImpl]TURNTABLE_RAY_0.getXmlValue());
                    }
                    [CtBreakImpl]break;
                }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutBlock getAffectedBlock([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutTrack o, [CtParameterImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.HitPointType type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock result = [CtLiteralImpl]null;
        [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
            [CtCaseImpl]case TURNOUT_A :
            [CtCaseImpl]case SLIP_A :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurnout) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (o));
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]lt.getLayoutBlock();
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case TURNOUT_B :
            [CtCaseImpl]case SLIP_B :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurnout) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (o));
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]lt.getLayoutBlockB();
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case TURNOUT_C :
            [CtCaseImpl]case SLIP_C :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurnout) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (o));
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]lt.getLayoutBlockC();
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case TURNOUT_D :
            [CtCaseImpl]case SLIP_D :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurnout) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtVariableReadImpl](([CtTypeReferenceImpl]LayoutTurnout) (o));
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]lt.getLayoutBlockD();
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case LEVEL_XING_A :
            [CtCaseImpl]case LEVEL_XING_C :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LevelXing) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LevelXing lx = [CtVariableReadImpl](([CtTypeReferenceImpl]LevelXing) (o));
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]lx.getLayoutBlockAC();
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case LEVEL_XING_B :
            [CtCaseImpl]case LEVEL_XING_D :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LevelXing) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]LevelXing lx = [CtVariableReadImpl](([CtTypeReferenceImpl]LevelXing) (o));
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]lx.getLayoutBlockBD();
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case TRACK :
                [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]TrackSegment) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment ts = [CtVariableReadImpl](([CtTypeReferenceImpl]TrackSegment) (o));
                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]ts.getLayoutBlock();
                    }
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtLiteralImpl]"Unhandled track type: {}", [CtVariableReadImpl]type);
                    [CtBreakImpl]break;
                }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a sensor indicator to the Draw Panel
     */
    [CtTypeReferenceImpl]void addSensor() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]newName.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error10"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]SensorIcon l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SensorIcon([CtConstructorCallImpl]new [CtTypeReferenceImpl]jmri.jmrit.catalog.NamedIcon([CtLiteralImpl]"resources/icons/smallschematics/tracksegments/circuit-error.gif", [CtLiteralImpl]"resources/icons/smallschematics/tracksegments/circuit-error.gif"), [CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SensorStateActive", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorIconEditor.getIcon([CtLiteralImpl]0));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SensorStateInactive", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorIconEditor.getIcon([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"BeanStateInconsistent", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorIconEditor.getIcon([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"BeanStateUnknown", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorIconEditor.getIcon([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]l.setSensor([CtVariableReadImpl]newName);
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.SENSORS);
        [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorComboBox.setSelectedItem([CtInvocationImpl][CtVariableReadImpl]l.getSensor());
        [CtInvocationImpl]setNextLocation([CtVariableReadImpl]l);
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void putSensor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]SensorIcon l) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]l.updateSize();
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.SENSORS);
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a signal head to the Panel
     */
    [CtTypeReferenceImpl]void addSignalHead() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// check for valid signal head entry
        [CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalHeadComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalHead mHead = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]newName.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mHead = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalHeadManager.class).getSignalHead([CtVariableReadImpl]newName);
            [CtInvocationImpl][CtCommentImpl]/* if (mHead == null)
            mHead = InstanceManager.getDefault(SignalHeadManager.class).getByUserName(newName);
            else
             */
            [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalHeadComboBox.setSelectedItem([CtVariableReadImpl]mHead);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mHead == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// There is no signal head corresponding to this name
            [CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error9"), [CtVariableReadImpl]newName), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// create and set up signal icon
        [CtTypeReferenceImpl]SignalHeadIcon l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SignalHeadIcon([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]l.setSignalHead([CtVariableReadImpl]newName);
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateRed", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]0));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateFlashingRed", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateYellow", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateFlashingYellow", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]3));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateGreen", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]4));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateFlashingGreen", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateDark", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]6));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateHeld", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]7));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateLunar", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]8));
        [CtInvocationImpl][CtVariableReadImpl]l.setIcon([CtLiteralImpl]"SignalHeadStateFlashingLunar", [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalIconEditor.getIcon([CtLiteralImpl]9));
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
        [CtInvocationImpl]setNextLocation([CtVariableReadImpl]l);
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl]putSignal([CtVariableReadImpl]l);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void putSignal([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]SignalHeadIcon l) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]l.updateSize();
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.SIGNALS);
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.SignalHead getSignalHead([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalHead sh = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalHeadManager.class).getBySystemName([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sh == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sh = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalHeadManager.class).getByUserName([CtVariableReadImpl]name);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sh == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtLiteralImpl]"did not find a SignalHead named {}", [CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtVariableReadImpl]sh;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean containsSignalHead([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]SignalHead head) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]head != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SignalHeadIcon h : [CtFieldReadImpl]signalList) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]h.getSignalHead() == [CtVariableReadImpl]head) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeSignalHead([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]SignalHead head) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]head != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SignalHeadIcon h : [CtFieldReadImpl]signalList) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]h.getSignalHead() == [CtVariableReadImpl]head) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]signalList.remove([CtVariableReadImpl]h);
                    [CtInvocationImpl][CtVariableReadImpl]h.remove();
                    [CtInvocationImpl][CtVariableReadImpl]h.dispose();
                    [CtInvocationImpl]setDirty();
                    [CtInvocationImpl]redrawPanel();
                    [CtBreakImpl]break;
                }
            }
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void addSignalMast() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// check for valid signal head entry
        [CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalMastComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMast mMast = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]newName.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mMast = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalMastManager.class).getSignalMast([CtVariableReadImpl]newName);
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalMastComboBox.setSelectedItem([CtVariableReadImpl]mMast);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mMast == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// There is no signal head corresponding to this name
            [CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error9"), [CtVariableReadImpl]newName), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// create and set up signal icon
        [CtTypeReferenceImpl]SignalMastIcon l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SignalMastIcon([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]l.setSignalMast([CtVariableReadImpl]newName);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
        [CtInvocationImpl]setNextLocation([CtVariableReadImpl]l);
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl]putSignalMast([CtVariableReadImpl]l);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void putSignalMast([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]SignalMastIcon l) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]l.updateSize();
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.SIGNALS);
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl][CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.SignalMast getSignalMast([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMast sh = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalMastManager.class).getBySystemName([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sh == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sh = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.SignalMastManager.class).getByUserName([CtVariableReadImpl]name);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sh == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.warn([CtLiteralImpl]"did not find a SignalMast named {}", [CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtVariableReadImpl]sh;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean containsSignalMast([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]SignalMast mast) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SignalMastIcon h : [CtFieldReadImpl]signalMastList) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]h.getSignalMast() == [CtVariableReadImpl]mast) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a label to the Draw Panel
     */
    [CtTypeReferenceImpl]void addLabel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String labelText = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.textLabelTextField.getText();
        [CtAssignmentImpl][CtVariableWriteImpl]labelText = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]labelText != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl]labelText.trim() : [CtLiteralImpl]"";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]labelText.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error11"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel l = [CtInvocationImpl][CtSuperAccessImpl]super.addLabel([CtVariableReadImpl]labelText);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
        [CtInvocationImpl]setDirty();
        [CtInvocationImpl][CtVariableReadImpl]l.setForeground([CtFieldReadImpl]defaultTextColor);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void putItem([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Positionable l) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.putItem([CtVariableReadImpl]l);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SensorIcon) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]sensorImage.add([CtVariableReadImpl](([CtTypeReferenceImpl]SensorIcon) (l)));
            [CtInvocationImpl][CtFieldReadImpl]sensorList.add([CtVariableReadImpl](([CtTypeReferenceImpl]SensorIcon) (l)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LocoIcon) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]markerImage.add([CtVariableReadImpl](([CtTypeReferenceImpl]LocoIcon) (l)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalHeadIcon) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]signalHeadImage.add([CtVariableReadImpl](([CtTypeReferenceImpl]SignalHeadIcon) (l)));
            [CtInvocationImpl][CtFieldReadImpl]signalList.add([CtVariableReadImpl](([CtTypeReferenceImpl]SignalHeadIcon) (l)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalMastIcon) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]signalMastList.add([CtVariableReadImpl](([CtTypeReferenceImpl]SignalMastIcon) (l)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]MemoryIcon) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]memoryLabelList.add([CtVariableReadImpl](([CtTypeReferenceImpl]MemoryIcon) (l)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BlockContentsIcon) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]blockContentsLabelList.add([CtVariableReadImpl](([CtTypeReferenceImpl]BlockContentsIcon) (l)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]AnalogClock2Display) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clocks.add([CtVariableReadImpl](([CtTypeReferenceImpl]AnalogClock2Display) (l)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]MultiSensorIcon) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]multiSensors.add([CtVariableReadImpl](([CtTypeReferenceImpl]MultiSensorIcon) (l)));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]PositionableLabel) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]PositionableLabel) (l)).isBackground()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]backgroundImage.add([CtVariableReadImpl](([CtTypeReferenceImpl]PositionableLabel) (l)));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]labelImage.add([CtVariableReadImpl](([CtTypeReferenceImpl]PositionableLabel) (l)));
            }
        }
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.Rectangle()));
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a memory label to the Draw Panel
     */
    [CtTypeReferenceImpl]void addMemory() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String memoryName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.textMemoryComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]memoryName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]memoryName = [CtLiteralImpl]"";
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]memoryName.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error11a"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]MemoryIcon l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MemoryIcon([CtLiteralImpl]" ", [CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]l.setMemory([CtVariableReadImpl]memoryName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]Memory xMemory = [CtInvocationImpl][CtVariableReadImpl]l.getMemory();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]xMemory != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]xMemory.getDisplayName();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]uname.equals([CtVariableReadImpl]memoryName)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// put the system name in the memory field
                [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.textMemoryComboBox.setSelectedItem([CtVariableReadImpl]xMemory);
            }
        }
        [CtInvocationImpl]setNextLocation([CtVariableReadImpl]l);
        [CtInvocationImpl][CtVariableReadImpl]l.setSize([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]l.getPreferredSize().width, [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]l.getPreferredSize().height);
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.LABELS);
        [CtInvocationImpl][CtVariableReadImpl]l.setForeground([CtFieldReadImpl]defaultTextColor);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl][CtTypeReferenceImpl]void addBlockContents() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockContentsComboBox.getSelectedItemDisplayName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newName = [CtLiteralImpl]"";
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]newName.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"Error11b"), [CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"ErrorTitle"), [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]BlockContentsIcon l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]BlockContentsIcon([CtLiteralImpl]" ", [CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]l.setBlock([CtVariableReadImpl]newName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]Block xMemory = [CtInvocationImpl][CtVariableReadImpl]l.getBlock();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]xMemory != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]xMemory.getDisplayName();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]uname.equals([CtVariableReadImpl]newName)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// put the system name in the memory field
                [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockContentsComboBox.setSelectedItem([CtVariableReadImpl]xMemory);
            }
        }
        [CtInvocationImpl]setNextLocation([CtVariableReadImpl]l);
        [CtInvocationImpl][CtVariableReadImpl]l.setSize([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]l.getPreferredSize().width, [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]l.getPreferredSize().height);
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.LABELS);
        [CtInvocationImpl][CtVariableReadImpl]l.setForeground([CtFieldReadImpl]defaultTextColor);
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a Reporter Icon to the panel
     */
    public [CtTypeReferenceImpl]void addReporter([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Reporter reporter, [CtParameterImpl][CtTypeReferenceImpl]int xx, [CtParameterImpl][CtTypeReferenceImpl]int yy) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ReporterIcon l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ReporterIcon([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]l.setReporter([CtVariableReadImpl]reporter);
        [CtInvocationImpl][CtVariableReadImpl]l.setLocation([CtVariableReadImpl]xx, [CtVariableReadImpl]yy);
        [CtInvocationImpl][CtVariableReadImpl]l.setSize([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]l.getPreferredSize().width, [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]l.getPreferredSize().height);
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.LABELS);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an icon to the target
     */
    [CtTypeReferenceImpl]void addIcon() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]PositionableLabel([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.iconEditor.getIcon([CtLiteralImpl]0), [CtThisAccessImpl]this);
        [CtInvocationImpl]setNextLocation([CtVariableReadImpl]l);
        [CtInvocationImpl][CtVariableReadImpl]l.setDisplayLevel([CtTypeAccessImpl]Editor.ICONS);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
        [CtInvocationImpl][CtVariableReadImpl]l.updateSize();
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a loco marker to the target
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LocoIcon addLocoIcon([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LocoIcon l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LocoIcon([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D pt = [CtInvocationImpl]windowCenter();
        [CtIfImpl]if ([CtFieldReadImpl]selectionActive) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]pt = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.midPoint([CtInvocationImpl]getSelectionRect());
        }
        [CtInvocationImpl][CtVariableReadImpl]l.setLocation([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]pt.getX())), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]pt.getY())));
        [CtInvocationImpl]putLocoIcon([CtVariableReadImpl]l, [CtVariableReadImpl]name);
        [CtInvocationImpl][CtVariableReadImpl]l.setPositionable([CtLiteralImpl]true);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
        [CtReturnImpl]return [CtVariableReadImpl]l;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void putLocoIcon([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LocoIcon l, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.putLocoIcon([CtVariableReadImpl]l, [CtVariableReadImpl]name);
        [CtInvocationImpl][CtFieldReadImpl]markerImage.add([CtVariableReadImpl]l);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]l.getBounds());
    }

    [CtFieldImpl][CtTypeReferenceImpl]javax.swing.JFileChooser inputFileChooser;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a background image
     */
    public [CtTypeReferenceImpl]void addBackground() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]inputFileChooser == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]inputFileChooser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JFileChooser([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s%sresources%sicons", [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.dir"), [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator, [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator));
            [CtIfImpl]if ([CtLiteralImpl]false) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// TODO: Discuss with jmri-developers
                [CtCommentImpl]// this filter will allow any images supported by the current
                [CtCommentImpl]// operating system. This may not be desirable because it will
                [CtCommentImpl]// allow images that may not be supported by operating systems
                [CtCommentImpl]// other than the current one.
                [CtFieldReadImpl]inputFileChooser.setFileFilter([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.filechooser.FileNameExtensionFilter([CtLiteralImpl]"Image files", [CtInvocationImpl][CtTypeAccessImpl]javax.imageio.ImageIO.getReaderFileSuffixes()));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// TODO: discuss with jmri-developers - support png image files?
                [CtFieldReadImpl]inputFileChooser.setFileFilter([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.filechooser.FileNameExtensionFilter([CtLiteralImpl]"Graphics Files", [CtLiteralImpl]"gif", [CtLiteralImpl]"jpg", [CtLiteralImpl]"png"));
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]inputFileChooser.rescanCurrentDirectory();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int retVal = [CtInvocationImpl][CtFieldReadImpl]inputFileChooser.showOpenDialog([CtThisAccessImpl]this);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]retVal != [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JFileChooser.[CtFieldReferenceImpl]APPROVE_OPTION) [CtBlockImpl]{
            [CtReturnImpl]return;[CtCommentImpl]// give up if no file selected

        }
        [CtLocalVariableImpl][CtCommentImpl]// NamedIcon icon = new NamedIcon(inputFileChooser.getSelectedFile().getPath(),
        [CtCommentImpl]// inputFileChooser.getSelectedFile().getPath());
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]inputFileChooser.getSelectedFile().getPath();
        [CtAssignmentImpl][CtCommentImpl]// convert to portable path
        [CtVariableWriteImpl]name = [CtInvocationImpl][CtTypeAccessImpl]FileUtil.getPortableFilename([CtVariableReadImpl]name);
        [CtLocalVariableImpl][CtCommentImpl]// setup icon
        [CtTypeReferenceImpl]PositionableLabel o = [CtInvocationImpl][CtSuperAccessImpl]super.setUpBackground([CtVariableReadImpl]name);
        [CtInvocationImpl][CtFieldReadImpl]backgroundImage.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a background image from the list of background images
     *
     * @param b
     * 		PositionableLabel to remove
     */
    protected [CtTypeReferenceImpl]void removeBackground([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]PositionableLabel b) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]backgroundImage.contains([CtVariableReadImpl]b)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]backgroundImage.remove([CtVariableReadImpl]b);
            [CtInvocationImpl]setDirty();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * add a layout shape to the list of layout shapes
     *
     * @param p
     * 		Point2D where the shape should be
     * @return the LayoutShape
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    protected [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutShape addLayoutShape([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get unique name
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtFieldReadImpl]finder.uniqueName([CtLiteralImpl]"S", [CtUnaryOperatorImpl]++[CtFieldWriteImpl]numShapes);
        [CtLocalVariableImpl][CtCommentImpl]// create object
        [CtTypeReferenceImpl]LayoutShape o = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutShape([CtVariableReadImpl]name, [CtVariableReadImpl]p, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]layoutShapes.add([CtVariableReadImpl]o);
        [CtInvocationImpl]unionToPanelBounds([CtInvocationImpl][CtVariableReadImpl]o.getBounds());
        [CtInvocationImpl]setDirty();
        [CtReturnImpl]return [CtVariableReadImpl]o;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a layout shape from the list of layout shapes
     *
     * @param s
     * 		the LayoutShape to add
     * @return true if added
     */
    protected [CtTypeReferenceImpl]boolean removeLayoutShape([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutShape s) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]layoutShapes.contains([CtVariableReadImpl]s)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]layoutShapes.remove([CtVariableReadImpl]s);
            [CtInvocationImpl]setDirty();
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;
            [CtInvocationImpl]redrawPanel();
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtFieldImpl][CtJavaDocImpl]/**
     * Invoke a window to allow you to add a MultiSensor indicator to the target
     */
    private [CtTypeReferenceImpl]int multiLocX;

    [CtFieldImpl]private [CtTypeReferenceImpl]int multiLocY;

    [CtMethodImpl][CtTypeReferenceImpl]void startMultiSensor() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]multiLocX = [CtFieldReadImpl]xLoc;
        [CtAssignmentImpl][CtFieldWriteImpl]multiLocY = [CtFieldReadImpl]yLoc;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorFrame == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// create a common edit frame
            [CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorFrame = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MultiSensorIconFrame([CtThisAccessImpl]this);
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorFrame.initComponents();
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorFrame.pack();
        }
        [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorFrame.setVisible([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtCommentImpl]// Invoked when window has new multi-sensor ready
    public [CtTypeReferenceImpl]void addMultiSensor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]MultiSensorIcon l) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]l.setLocation([CtFieldReadImpl]multiLocX, [CtFieldReadImpl]multiLocY);
        [CtInvocationImpl]putItem([CtVariableReadImpl]l);[CtCommentImpl]// note: this calls unionToPanelBounds & setDirty()

        [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorFrame.dispose();
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.multiSensorFrame = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set object location and size for icon and label object as it is created.
     * Size comes from the preferredSize; location comes from the fields where
     * the user can spec it.
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void setNextLocation([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Positionable obj) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]obj.setLocation([CtFieldReadImpl]xLoc, [CtFieldReadImpl]yLoc);
    }

    [CtFieldImpl][CtCommentImpl]// 
    [CtCommentImpl]// singleton (one per-LayoutEditor) accessors
    [CtCommentImpl]// 
    private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.ConnectivityUtil conTools = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.ConnectivityUtil getConnectivityUtil() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]conTools == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]conTools = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ConnectivityUtil([CtThisAccessImpl]this);
        }
        [CtReturnImpl]return [CtFieldReadImpl]conTools;
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorTools tools = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorTools getLETools() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]tools == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]tools = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorTools([CtThisAccessImpl]this);
        }
        [CtReturnImpl]return [CtFieldReadImpl]tools;
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorAuxTools auxTools = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorAuxTools getLEAuxTools() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]auxTools == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]auxTools = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorAuxTools([CtThisAccessImpl]this);
        }
        [CtReturnImpl]return [CtFieldReadImpl]auxTools;
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutTrackEditors layoutTrackEditors = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutTrackEditors getLayoutTrackEditors() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]layoutTrackEditors == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]layoutTrackEditors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutTrackEditors([CtThisAccessImpl]this);
        }
        [CtReturnImpl]return [CtFieldReadImpl]layoutTrackEditors;
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorChecks layoutEditorChecks = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditorChecks getLEChecks() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]layoutEditorChecks == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]layoutEditorChecks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LayoutEditorChecks([CtThisAccessImpl]this);
        }
        [CtReturnImpl]return [CtFieldReadImpl]layoutEditorChecks;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Invoked by DeletePanel menu item Validate user intent before deleting
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean deletePanel() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// verify deletion
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtSuperAccessImpl]super.deletePanel()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// return without deleting if "No" response

        }
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.clear();
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Control whether target panel items are editable. Does this by invoking
     * the {@link Editor#setAllEditable} function of the parent class. This also
     * controls the relevant pop-up menu items (which are the primary way that
     * items are edited).
     *
     * @param editable
     * 		true for editable.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setAllEditable([CtParameterImpl][CtTypeReferenceImpl]boolean editable) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int restoreScroll = [CtFieldReadImpl]_scrollState;
        [CtInvocationImpl][CtSuperAccessImpl]super.setAllEditable([CtVariableReadImpl]editable);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT)) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]editable) [CtBlockImpl]{
                [CtInvocationImpl]createfloatingEditToolBoxFrame();
                [CtInvocationImpl]createFloatingHelpPanel();
            } else [CtBlockImpl]{
                [CtInvocationImpl]deletefloatingEditToolBoxFrame();
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]editToolBarContainerPanel.setVisible([CtVariableReadImpl]editable);
        }
        [CtInvocationImpl]setShowHidden([CtVariableReadImpl]editable);
        [CtIfImpl]if ([CtVariableReadImpl]editable) [CtBlockImpl]{
            [CtInvocationImpl]setScroll([CtTypeAccessImpl]Editor.SCROLL_BOTH);
            [CtAssignmentImpl][CtFieldWriteImpl]_scrollState = [CtVariableReadImpl]restoreScroll;
        } else [CtBlockImpl]{
            [CtInvocationImpl]setScroll([CtFieldReadImpl]_scrollState);
        }
        [CtIfImpl][CtCommentImpl]// these may not be set up yet...
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]helpBarPanel != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]floatEditHelpPanel != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]floatEditHelpPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtInvocationImpl]getShowHelpBar());
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]helpBarPanel.setVisible([CtBinaryOperatorImpl][CtVariableReadImpl]editable && [CtInvocationImpl]getShowHelpBar());
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]awaitingIconChange = [CtLiteralImpl]false;
        [CtInvocationImpl][CtFieldReadImpl]editModeCheckBoxMenuItem.setSelected([CtVariableReadImpl]editable);
        [CtInvocationImpl]redrawPanel();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Control whether panel items are positionable. Markers are always
     * positionable.
     *
     * @param state
     * 		true for positionable.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setAllPositionable([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.setAllPositionable([CtVariableReadImpl]state);
        [CtInvocationImpl][CtFieldReadImpl]markerImage.forEach([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.setPositionable([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Control whether target panel items are controlling layout items. Does
     * this by invoke the {@link Positionable#setControlling} function of each
     * item on the target panel. This also controls the relevant pop-up menu
     * items.
     *
     * @param state
     * 		true for controlling.
     */
    public [CtTypeReferenceImpl]void setTurnoutAnimation([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]animationCheckBoxMenuItem.isSelected() != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]animationCheckBoxMenuItem.setSelected([CtVariableReadImpl]state);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]animatingLayout != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]animatingLayout = [CtVariableReadImpl]state;
            [CtInvocationImpl]redrawPanel();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isAnimating() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]animatingLayout;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getLayoutWidth() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]panelWidth;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getLayoutHeight() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]panelHeight;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getWindowWidth() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]windowWidth;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getWindowHeight() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]windowHeight;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getUpperLeftX() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]upperLeftX;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getUpperLeftY() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]upperLeftY;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean getScroll() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// deprecated but kept to allow opening files
        [CtCommentImpl]// on version 2.5.1 and earlier
        return [CtBinaryOperatorImpl][CtFieldReadImpl]_scrollState != [CtFieldReadImpl]Editor.SCROLL_NONE;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int setGridSize([CtParameterImpl][CtTypeReferenceImpl]int newSize) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]gridSize1st = [CtVariableReadImpl]newSize;
        [CtReturnImpl]return [CtFieldReadImpl]gridSize1st;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getGridSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]gridSize1st;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int setGridSize2nd([CtParameterImpl][CtTypeReferenceImpl]int newSize) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]gridSize2nd = [CtVariableReadImpl]newSize;
        [CtReturnImpl]return [CtFieldReadImpl]gridSize2nd;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getGridSize2nd() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]gridSize2nd;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMainlineTrackWidth() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl](([CtTypeReferenceImpl]int) (mainlineTrackWidth));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getSidelineTrackWidth() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl](([CtTypeReferenceImpl]int) (sidelineTrackWidth));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getXScale() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]xScale;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getYScale() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]yScale;
    }

    [CtMethodImpl][CtCommentImpl]// public Color getDefaultBackgroundColor() {
    [CtCommentImpl]// return defaultBackgroundColor;
    [CtCommentImpl]// }
    public [CtTypeReferenceImpl]java.lang.String getDefaultTrackColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]ColorUtil.colorToColorName([CtFieldReadImpl]defaultTrackColor);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Getter defaultTrackColor.
     *
     * @return block default color as Color
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.awt.Color getDefaultTrackColorColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]defaultTrackColor;
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.lang.String getDefaultOccupiedTrackColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]ColorUtil.colorToColorName([CtFieldReadImpl]defaultOccupiedTrackColor);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Getter defaultOccupiedTrackColor.
     *
     * @return block default occupied color as Color
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.awt.Color getDefaultOccupiedTrackColorColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]defaultOccupiedTrackColor;
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.lang.String getDefaultAlternativeTrackColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]ColorUtil.colorToColorName([CtFieldReadImpl]defaultAlternativeTrackColor);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Getter defaultAlternativeTrackColor.
     *
     * @return block default alternetive color as Color
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.awt.Color getDefaultAlternativeTrackColorColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]defaultAlternativeTrackColor;
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.lang.String getDefaultTextColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]ColorUtil.colorToColorName([CtFieldReadImpl]defaultTextColor);
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.lang.String getTurnoutCircleColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]ColorUtil.colorToColorName([CtFieldReadImpl]turnoutCircleColor);
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.lang.String getTurnoutCircleThrownColor() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]ColorUtil.colorToColorName([CtFieldReadImpl]turnoutCircleThrownColor);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isTurnoutFillControlCircles() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]turnoutFillControlCircles;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTurnoutCircleSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]turnoutCircleSize;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isTurnoutDrawUnselectedLeg() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]turnoutDrawUnselectedLeg;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getLayoutName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]layoutName;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getShowHelpBar() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]showHelpBar;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getDrawGrid() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]drawGrid;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getSnapOnAdd() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]snapToGridOnAdd;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getSnapOnMove() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]snapToGridOnMove;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getAntialiasingOn() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]antialiasingOn;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getHighlightSelectedBlock() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]highlightSelectedBlockFlag;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getTurnoutCircles() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]turnoutCirclesWithoutEditMode;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getTooltipsNotEdit() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]tooltipsWithoutEditMode;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getTooltipsInEdit() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]tooltipsInEditMode;
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getAutoBlockAssignment() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]autoAssignBlocks;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setLayoutDimensions([CtParameterImpl][CtTypeReferenceImpl]int windowWidth, [CtParameterImpl][CtTypeReferenceImpl]int windowHeight, [CtParameterImpl][CtTypeReferenceImpl]int windowX, [CtParameterImpl][CtTypeReferenceImpl]int windowY, [CtParameterImpl][CtTypeReferenceImpl]int panelWidth, [CtParameterImpl][CtTypeReferenceImpl]int panelHeight) [CtBlockImpl]{
        [CtInvocationImpl]setLayoutDimensions([CtVariableReadImpl]windowWidth, [CtVariableReadImpl]windowHeight, [CtVariableReadImpl]windowX, [CtVariableReadImpl]windowY, [CtVariableReadImpl]panelWidth, [CtVariableReadImpl]panelHeight, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setLayoutDimensions([CtParameterImpl][CtTypeReferenceImpl]int windowWidth, [CtParameterImpl][CtTypeReferenceImpl]int windowHeight, [CtParameterImpl][CtTypeReferenceImpl]int windowX, [CtParameterImpl][CtTypeReferenceImpl]int windowY, [CtParameterImpl][CtTypeReferenceImpl]int panelWidth, [CtParameterImpl][CtTypeReferenceImpl]int panelHeight, [CtParameterImpl][CtTypeReferenceImpl]boolean merge) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]upperLeftX = [CtVariableReadImpl]windowX;
        [CtAssignmentImpl][CtFieldWriteImpl]upperLeftY = [CtVariableReadImpl]windowY;
        [CtInvocationImpl]setLocation([CtFieldReadImpl]upperLeftX, [CtFieldReadImpl]upperLeftY);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.windowWidth = [CtVariableReadImpl]windowWidth;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.windowHeight = [CtVariableReadImpl]windowHeight;
        [CtInvocationImpl]setSize([CtVariableReadImpl]windowWidth, [CtVariableReadImpl]windowHeight);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D panelBounds = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D.Double([CtLiteralImpl]0.0, [CtLiteralImpl]0.0, [CtVariableReadImpl]panelWidth, [CtVariableReadImpl]panelHeight);
        [CtIfImpl]if ([CtVariableReadImpl]merge) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]panelBounds.add([CtInvocationImpl]calculateMinimumLayoutBounds());
        }
        [CtInvocationImpl]setPanelBounds([CtVariableReadImpl]panelBounds);
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.awt.geom.Rectangle2D getPanelBounds() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D.Double([CtLiteralImpl]0.0, [CtLiteralImpl]0.0, [CtFieldReadImpl]panelWidth, [CtFieldReadImpl]panelHeight);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setPanelBounds([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Rectangle2D newBounds) [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// don't let origin go negative
        [CtVariableWriteImpl]newBounds = [CtInvocationImpl][CtVariableReadImpl]newBounds.createIntersection([CtTypeAccessImpl]MathUtil.zeroToInfinityRectangle2D);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getPanelBounds().equals([CtVariableReadImpl]newBounds)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]panelWidth = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newBounds.getWidth()));
            [CtAssignmentImpl][CtFieldWriteImpl]panelHeight = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]newBounds.getHeight()));
            [CtInvocationImpl]resetTargetSize();
        }
        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"setPanelBounds(({})", [CtVariableReadImpl]newBounds);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void resetTargetSize() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newTargetWidth = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]panelWidth * [CtInvocationImpl]getZoom()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newTargetHeight = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]panelHeight * [CtInvocationImpl]getZoom()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Dimension targetPanelSize = [CtInvocationImpl]getTargetPanelSize();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int oldTargetWidth = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]targetPanelSize.getWidth()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int oldTargetHeight = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]targetPanelSize.getHeight()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]newTargetWidth != [CtVariableReadImpl]oldTargetWidth) || [CtBinaryOperatorImpl]([CtVariableReadImpl]newTargetHeight != [CtVariableReadImpl]oldTargetHeight)) [CtBlockImpl]{
            [CtInvocationImpl]setTargetPanelSize([CtVariableReadImpl]newTargetWidth, [CtVariableReadImpl]newTargetHeight);
            [CtInvocationImpl]adjustScrollBars();
        }
    }

    [CtMethodImpl][CtCommentImpl]// this will grow the panel bounds based on items added to the layout
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.awt.geom.Rectangle2D unionToPanelBounds([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Rectangle2D bounds) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D result = [CtInvocationImpl]getPanelBounds();
        [CtLocalVariableImpl][CtCommentImpl]// make room to expand
        [CtTypeReferenceImpl]java.awt.geom.Rectangle2D b = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.inset([CtVariableReadImpl]bounds, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]gridSize1st * [CtFieldReadImpl]gridSize2nd) / [CtUnaryOperatorImpl](-[CtLiteralImpl]2.0));
        [CtAssignmentImpl][CtCommentImpl]// don't let origin go negative
        [CtVariableWriteImpl]b = [CtInvocationImpl][CtVariableReadImpl]b.createIntersection([CtTypeAccessImpl]MathUtil.zeroToInfinityRectangle2D);
        [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]b);
        [CtInvocationImpl]setPanelBounds([CtVariableReadImpl]result);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setMainlineTrackWidth([CtParameterImpl][CtTypeReferenceImpl]int w) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mainlineTrackWidth = [CtVariableReadImpl]w;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setSidelineTrackWidth([CtParameterImpl][CtTypeReferenceImpl]int w) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]sidelineTrackWidth = [CtVariableReadImpl]w;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param color
     * 		value to set the default track color to.
     */
    public [CtTypeReferenceImpl]void setDefaultTrackColor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]defaultTrackColor = [CtVariableReadImpl]color;
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtVariableReadImpl]color);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param color
     * 		value to set the default occupied track color to.
     */
    public [CtTypeReferenceImpl]void setDefaultOccupiedTrackColor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]defaultOccupiedTrackColor = [CtVariableReadImpl]color;
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtVariableReadImpl]color);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param color
     * 		value to set the default alternate track color to.
     */
    public [CtTypeReferenceImpl]void setDefaultAlternativeTrackColor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]defaultAlternativeTrackColor = [CtVariableReadImpl]color;
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtVariableReadImpl]color);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param color
     * 		new color for turnout circle.
     */
    public [CtTypeReferenceImpl]void setTurnoutCircleColor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]color == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutCircleColor = [CtInvocationImpl]getDefaultTrackColorColor();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutCircleColor = [CtVariableReadImpl]color;
            [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtVariableReadImpl]color);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param color
     * 		new color for turnout circle.
     */
    public [CtTypeReferenceImpl]void setTurnoutCircleThrownColor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]color == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutCircleThrownColor = [CtInvocationImpl]getDefaultTrackColorColor();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutCircleThrownColor = [CtVariableReadImpl]color;
            [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtVariableReadImpl]color);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Should only be invoked on the GUI (Swing) thread
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.InvokeOnGuiThread
    public [CtTypeReferenceImpl]void setTurnoutFillControlCircles([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]turnoutFillControlCircles != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutFillControlCircles = [CtVariableReadImpl]state;
            [CtInvocationImpl][CtFieldReadImpl]turnoutFillControlCirclesCheckBoxMenuItem.setSelected([CtFieldReadImpl]turnoutFillControlCircles);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTurnoutCircleSize([CtParameterImpl][CtTypeReferenceImpl]int size) [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// this is an int
        [CtFieldWriteImpl]turnoutCircleSize = [CtVariableReadImpl]size;
        [CtAssignmentImpl][CtCommentImpl]// these are doubles
        [CtFieldWriteImpl]circleRadius = [CtBinaryOperatorImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.SIZE * [CtVariableReadImpl]size;
        [CtAssignmentImpl][CtFieldWriteImpl]circleDiameter = [CtBinaryOperatorImpl][CtLiteralImpl]2.0 * [CtFieldReadImpl]circleRadius;
        [CtInvocationImpl]setOptionMenuTurnoutCircleSize();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Should only be invoked on the GUI (Swing) thread
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.InvokeOnGuiThread
    public [CtTypeReferenceImpl]void setTurnoutDrawUnselectedLeg([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]turnoutDrawUnselectedLeg != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutDrawUnselectedLeg = [CtVariableReadImpl]state;
            [CtInvocationImpl][CtFieldReadImpl]turnoutDrawUnselectedLegCheckBoxMenuItem.setSelected([CtFieldReadImpl]turnoutDrawUnselectedLeg);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param color
     * 		value to set the default text color to.
     */
    public [CtTypeReferenceImpl]void setDefaultTextColor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]defaultTextColor = [CtVariableReadImpl]color;
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtVariableReadImpl]color);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param color
     * 		value to set the panel background to.
     */
    public [CtTypeReferenceImpl]void setDefaultBackgroundColor([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.Color color) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]defaultBackgroundColor = [CtVariableReadImpl]color;
        [CtInvocationImpl][CtTypeAccessImpl]JmriColorChooser.addRecentColor([CtVariableReadImpl]color);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setXScale([CtParameterImpl][CtTypeReferenceImpl]double xSc) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]xScale = [CtVariableReadImpl]xSc;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setYScale([CtParameterImpl][CtTypeReferenceImpl]double ySc) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]yScale = [CtVariableReadImpl]ySc;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setLayoutName([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]layoutName = [CtVariableReadImpl]name;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Should only be invoked on the GUI (Swing) thread
     */
    [CtCommentImpl]// due to the setSelected call on a possibly-visible item
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.InvokeOnGuiThread
    public [CtTypeReferenceImpl]void setShowHelpBar([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]showHelpBar != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]showHelpBar = [CtVariableReadImpl]state;
            [CtIfImpl][CtCommentImpl]// these may not be set up yet...
            if ([CtBinaryOperatorImpl][CtFieldReadImpl]showHelpCheckBoxMenuItem != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]showHelpCheckBoxMenuItem.setSelected([CtFieldReadImpl]showHelpBar);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]toolBarSide.equals([CtFieldReadImpl][CtTypeAccessImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.ToolBarSide.[CtFieldReferenceImpl]eFLOAT)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]floatEditHelpPanel != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]floatEditHelpPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtFieldReadImpl]showHelpBar);
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]helpBarPanel != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]helpBarPanel.setVisible([CtBinaryOperatorImpl][CtInvocationImpl]isEditable() && [CtFieldReadImpl]showHelpBar);
            }
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtInvocationImpl][CtVariableReadImpl]prefsMgr.setSimplePreferenceState([CtBinaryOperatorImpl][CtInvocationImpl]getWindowFrameRef() + [CtLiteralImpl]".showHelpBar", [CtFieldReadImpl][CtFieldReferenceImpl]showHelpBar));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Should only be invoked on the GUI (Swing) thread
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.InvokeOnGuiThread
    public [CtTypeReferenceImpl]void setDrawGrid([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]drawGrid != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]drawGrid = [CtVariableReadImpl]state;
            [CtInvocationImpl][CtFieldReadImpl]showGridCheckBoxMenuItem.setSelected([CtFieldReadImpl]drawGrid);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Should only be invoked on the GUI (Swing) thread
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.InvokeOnGuiThread
    public [CtTypeReferenceImpl]void setSnapOnAdd([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]snapToGridOnAdd != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]snapToGridOnAdd = [CtVariableReadImpl]state;
            [CtInvocationImpl][CtFieldReadImpl]snapToGridOnAddCheckBoxMenuItem.setSelected([CtFieldReadImpl]snapToGridOnAdd);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Should only be invoked on the GUI (Swing) thread
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.InvokeOnGuiThread
    public [CtTypeReferenceImpl]void setSnapOnMove([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]snapToGridOnMove != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]snapToGridOnMove = [CtVariableReadImpl]state;
            [CtInvocationImpl][CtFieldReadImpl]snapToGridOnMoveCheckBoxMenuItem.setSelected([CtFieldReadImpl]snapToGridOnMove);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Should only be invoked on the GUI (Swing) thread
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.InvokeOnGuiThread
    public [CtTypeReferenceImpl]void setAntialiasingOn([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]antialiasingOn != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]antialiasingOn = [CtVariableReadImpl]state;
            [CtIfImpl][CtCommentImpl]// this may not be set up yet...
            if ([CtBinaryOperatorImpl][CtFieldReadImpl]antialiasingOnCheckBoxMenuItem != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]antialiasingOnCheckBoxMenuItem.setSelected([CtFieldReadImpl]antialiasingOn);
            }
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtInvocationImpl][CtVariableReadImpl]prefsMgr.setSimplePreferenceState([CtBinaryOperatorImpl][CtInvocationImpl]getWindowFrameRef() + [CtLiteralImpl]".antialiasingOn", [CtFieldReadImpl][CtFieldReferenceImpl]antialiasingOn));
        }
    }

    [CtMethodImpl][CtCommentImpl]// enable/disable using the "Extra" color to highlight the selected block
    public [CtTypeReferenceImpl]void setHighlightSelectedBlock([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]highlightSelectedBlockFlag != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]highlightSelectedBlockFlag = [CtVariableReadImpl]state;
            [CtIfImpl][CtCommentImpl]// this may not be set up yet...
            if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.highlightBlockCheckBox != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.highlightBlockCheckBox.setSelected([CtFieldReadImpl]highlightSelectedBlockFlag);
            }
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getOptionalDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.UserPreferencesManager.class).ifPresent([CtLambdaImpl]([CtParameterImpl] prefsMgr) -> [CtInvocationImpl][CtVariableReadImpl]prefsMgr.setSimplePreferenceState([CtBinaryOperatorImpl][CtInvocationImpl]getWindowFrameRef() + [CtLiteralImpl]".highlightSelectedBlock", [CtFieldReadImpl][CtFieldReferenceImpl]highlightSelectedBlockFlag));
            [CtInvocationImpl][CtCommentImpl]// thread this so it won't break the AppVeyor checks
            [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtLambdaImpl]() -> [CtBlockImpl]{
                [CtIfImpl]if ([CtFieldReadImpl]highlightSelectedBlockFlag) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// use the "Extra" color to highlight the selected block
                    if ([CtUnaryOperatorImpl]![CtInvocationImpl]highlightBlockInComboBox([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox)) [CtBlockImpl]{
                        [CtInvocationImpl]highlightBlockInComboBox([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockContentsComboBox);
                    }
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// undo using the "Extra" color to highlight the selected block
                    [CtTypeReferenceImpl]Block block = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getSelectedItem();
                    [CtInvocationImpl]highlightBlock([CtLiteralImpl]null);
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.setSelectedItem([CtVariableReadImpl]block);
                }
            }).start();
        }
    }

    [CtMethodImpl][CtCommentImpl]// 
    [CtCommentImpl]// highlight the block selected by the specified combo Box
    [CtCommentImpl]// 
    protected [CtTypeReferenceImpl]boolean highlightBlockInComboBox([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]jmri.swing.NamedBeanComboBox<[CtTypeReferenceImpl]Block> inComboBox) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]highlightBlock([CtInvocationImpl][CtVariableReadImpl]inComboBox.getSelectedItem());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * highlight the specified block
     *
     * @param inBlock
     * 		the block
     * @return true if block was highlighted
     */
    public [CtTypeReferenceImpl]boolean highlightBlock([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.CheckForNull
    [CtTypeReferenceImpl]Block inBlock) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;[CtCommentImpl]// assume failure (pessimist!)

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getSelectedItem() != [CtVariableReadImpl]inBlock) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.setSelectedItem([CtVariableReadImpl]inBlock);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlockManager lbm = [CtInvocationImpl][CtTypeAccessImpl]InstanceManager.getDefault([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutBlockManager.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Block> l = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.blockIDComboBox.getManager().getNamedBeanSet();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Block b : [CtVariableReadImpl]l) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutBlock lb = [CtInvocationImpl][CtVariableReadImpl]lbm.getLayoutBlock([CtVariableReadImpl]b);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lb != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean enable = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]inBlock != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]b.equals([CtVariableReadImpl]inBlock);
                [CtInvocationImpl][CtVariableReadImpl]lb.setUseExtraColor([CtVariableReadImpl]enable);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]result |= [CtVariableReadImpl]enable;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * highlight the specified layout block
     *
     * @param inLayoutBlock
     * 		the layout block
     * @return true if layout block was highlighted
     */
    public [CtTypeReferenceImpl]boolean highlightLayoutBlock([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]LayoutBlock inLayoutBlock) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]highlightBlock([CtInvocationImpl][CtVariableReadImpl]inLayoutBlock.getBlock());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTurnoutCircles([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]turnoutCirclesWithoutEditMode != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]turnoutCirclesWithoutEditMode = [CtVariableReadImpl]state;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]turnoutCirclesOnCheckBoxMenuItem != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]turnoutCirclesOnCheckBoxMenuItem.setSelected([CtFieldReadImpl]turnoutCirclesWithoutEditMode);
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAutoBlockAssignment([CtParameterImpl][CtTypeReferenceImpl]boolean boo) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]autoAssignBlocks != [CtVariableReadImpl]boo) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]autoAssignBlocks = [CtVariableReadImpl]boo;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]autoAssignBlocksCheckBoxMenuItem != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]autoAssignBlocksCheckBoxMenuItem.setSelected([CtFieldReadImpl]autoAssignBlocks);
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTooltipsNotEdit([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]tooltipsWithoutEditMode != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsWithoutEditMode = [CtVariableReadImpl]state;
            [CtInvocationImpl]setTooltipSubMenu();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTooltipsInEdit([CtParameterImpl][CtTypeReferenceImpl]boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]tooltipsInEditMode != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]tooltipsInEditMode = [CtVariableReadImpl]state;
            [CtInvocationImpl]setTooltipSubMenu();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setTooltipSubMenu() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]tooltipNoneMenuItem != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]tooltipNoneMenuItem.setSelected([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsInEditMode) && [CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsWithoutEditMode));
            [CtInvocationImpl][CtFieldReadImpl]tooltipAlwaysMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]tooltipsInEditMode && [CtFieldReadImpl]tooltipsWithoutEditMode);
            [CtInvocationImpl][CtFieldReadImpl]tooltipInEditMenuItem.setSelected([CtBinaryOperatorImpl][CtFieldReadImpl]tooltipsInEditMode && [CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsWithoutEditMode));
            [CtInvocationImpl][CtFieldReadImpl]tooltipNotInEditMenuItem.setSelected([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]tooltipsInEditMode) && [CtFieldReadImpl]tooltipsWithoutEditMode);
        }
    }

    [CtMethodImpl][CtCommentImpl]// accessor routines for turnout size parameters
    public [CtTypeReferenceImpl]void setTurnoutBX([CtParameterImpl][CtTypeReferenceImpl]double bx) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutBX = [CtVariableReadImpl]bx;
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTurnoutBX() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]turnoutBX;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTurnoutCX([CtParameterImpl][CtTypeReferenceImpl]double cx) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutCX = [CtVariableReadImpl]cx;
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTurnoutCX() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]turnoutCX;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTurnoutWid([CtParameterImpl][CtTypeReferenceImpl]double wid) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutWid = [CtVariableReadImpl]wid;
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTurnoutWid() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]turnoutWid;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setXOverLong([CtParameterImpl][CtTypeReferenceImpl]double lg) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]xOverLong = [CtVariableReadImpl]lg;
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getXOverLong() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]xOverLong;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setXOverHWid([CtParameterImpl][CtTypeReferenceImpl]double hwid) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]xOverHWid = [CtVariableReadImpl]hwid;
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getXOverHWid() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]xOverHWid;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setXOverShort([CtParameterImpl][CtTypeReferenceImpl]double sh) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]xOverShort = [CtVariableReadImpl]sh;
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getXOverShort() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]xOverShort;
    }

    [CtMethodImpl][CtCommentImpl]// reset turnout sizes to program defaults
    protected [CtTypeReferenceImpl]void resetTurnoutSize() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutBX = [CtFieldReadImpl]LayoutTurnout.turnoutBXDefault;
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutCX = [CtFieldReadImpl]LayoutTurnout.turnoutCXDefault;
        [CtAssignmentImpl][CtFieldWriteImpl]turnoutWid = [CtFieldReadImpl]LayoutTurnout.turnoutWidDefault;
        [CtAssignmentImpl][CtFieldWriteImpl]xOverLong = [CtFieldReadImpl]LayoutTurnout.xOverLongDefault;
        [CtAssignmentImpl][CtFieldWriteImpl]xOverHWid = [CtFieldReadImpl]LayoutTurnout.xOverHWidDefault;
        [CtAssignmentImpl][CtFieldWriteImpl]xOverShort = [CtFieldReadImpl]LayoutTurnout.xOverShortDefault;
        [CtInvocationImpl]setDirty();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDirectTurnoutControl([CtParameterImpl][CtTypeReferenceImpl]boolean boo) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]useDirectTurnoutControl = [CtVariableReadImpl]boo;
        [CtInvocationImpl][CtFieldReadImpl]useDirectTurnoutControlCheckBoxMenuItem.setSelected([CtFieldReadImpl]useDirectTurnoutControl);
    }

    [CtMethodImpl][CtCommentImpl]// TODO: @Deprecated // Java standard pattern for boolean getters is "isShowHelpBar()"
    public [CtTypeReferenceImpl]boolean getDirectTurnoutControl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]useDirectTurnoutControl;
    }

    [CtMethodImpl][CtCommentImpl]// final initialization routine for loading a LayoutEditor
    public [CtTypeReferenceImpl]void setConnections() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.forEach([CtLambdaImpl]([CtParameterImpl] lt) -> [CtInvocationImpl][CtVariableReadImpl]lt.setObjects([CtThisAccessImpl]this));
        [CtInvocationImpl][CtInvocationImpl]getLEAuxTools().initializeBlockConnectivity();
        [CtInvocationImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"Initializing Block Connectivity for {}", [CtInvocationImpl]getLayoutName());
        [CtInvocationImpl][CtCommentImpl]// reset the panel changed bit
        resetDirty();
    }

    [CtMethodImpl][CtCommentImpl]// these are convenience methods to return rectangles
    [CtCommentImpl]// to use when (hit point-in-rect testing
    [CtCommentImpl]// 
    [CtCommentImpl]// compute the control point rect at inPoint
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.awt.geom.Rectangle2D layoutEditorControlRectAt([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D inPoint) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D.Double([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]inPoint.getX() - [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.SIZE, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]inPoint.getY() - [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.SIZE, [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.SIZE2, [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.SIZE2);
    }

    [CtMethodImpl][CtCommentImpl]// compute the turnout circle control rect at inPoint
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.awt.geom.Rectangle2D layoutEditorControlCircleRectAt([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Point2D inPoint) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D.Double([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]inPoint.getX() - [CtFieldReadImpl]circleRadius, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]inPoint.getY() - [CtFieldReadImpl]circleRadius, [CtFieldReadImpl]circleDiameter, [CtFieldReadImpl]circleDiameter);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Special internal class to allow drawing of layout to a JLayeredPane This
     * is the 'target' pane where the layout is displayed
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void paintTargetPanel([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.Graphics g) [CtBlockImpl]{
        [CtCommentImpl]// Nothing to do here
        [CtCommentImpl]// All drawing has been moved into LayoutEditorComponent
        [CtCommentImpl]// which calls draw.
        [CtCommentImpl]// This is so the layout is drawn at level three
        [CtCommentImpl]// (above or below the Positionables)
    }

    [CtMethodImpl][CtCommentImpl]// get selection rectangle
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    protected [CtTypeReferenceImpl]java.awt.geom.Rectangle2D getSelectionRect() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]double selX = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtFieldReadImpl]selectionX, [CtBinaryOperatorImpl][CtFieldReadImpl]selectionX + [CtFieldReadImpl]selectionWidth);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double selY = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtFieldReadImpl]selectionY, [CtBinaryOperatorImpl][CtFieldReadImpl]selectionY + [CtFieldReadImpl]selectionHeight);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Rectangle2D.Double([CtVariableReadImpl]selX, [CtVariableReadImpl]selY, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtFieldReadImpl]selectionWidth), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtFieldReadImpl]selectionHeight));
    }

    [CtMethodImpl][CtCommentImpl]// set selection rectangle
    public [CtTypeReferenceImpl]void setSelectionRect([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.geom.Rectangle2D selectionRect) [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// selectionRect = selectionRect.createIntersection(MathUtil.zeroToInfinityRectangle2D);
        [CtFieldWriteImpl]selectionX = [CtInvocationImpl][CtVariableReadImpl]selectionRect.getX();
        [CtAssignmentImpl][CtFieldWriteImpl]selectionY = [CtInvocationImpl][CtVariableReadImpl]selectionRect.getY();
        [CtAssignmentImpl][CtFieldWriteImpl]selectionWidth = [CtInvocationImpl][CtVariableReadImpl]selectionRect.getWidth();
        [CtAssignmentImpl][CtFieldWriteImpl]selectionHeight = [CtInvocationImpl][CtVariableReadImpl]selectionRect.getHeight();
        [CtAssignmentImpl][CtCommentImpl]// There's already code in the super class (Editor) to draw
        [CtCommentImpl]// the selection rect... We just have to set _selectRect
        [CtFieldWriteImpl]_selectRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.rectangle2DToRectangle([CtVariableReadImpl]selectionRect);
        [CtAssignmentImpl][CtVariableWriteImpl]selectionRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.scale([CtVariableReadImpl]selectionRect, [CtInvocationImpl]getZoom());
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.swing.JComponent targetPanel = [CtInvocationImpl]getTargetPanel();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Rectangle targetRect = [CtInvocationImpl][CtVariableReadImpl]targetPanel.getVisibleRect();
        [CtLocalVariableImpl][CtCommentImpl]// this will make it the size of the targetRect
        [CtCommentImpl]// (effectively centering it onscreen)
        [CtTypeReferenceImpl]java.awt.geom.Rectangle2D selRect2D = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.inset([CtVariableReadImpl]selectionRect, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]selectionRect.getWidth() - [CtInvocationImpl][CtVariableReadImpl]targetRect.getWidth()) / [CtLiteralImpl]2.0, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]selectionRect.getHeight() - [CtInvocationImpl][CtVariableReadImpl]targetRect.getHeight()) / [CtLiteralImpl]2.0);
        [CtAssignmentImpl][CtCommentImpl]// don't let the origin go negative
        [CtVariableWriteImpl]selRect2D = [CtInvocationImpl][CtVariableReadImpl]selRect2D.createIntersection([CtTypeAccessImpl]MathUtil.zeroToInfinityRectangle2D);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Rectangle selRect = [CtInvocationImpl][CtTypeAccessImpl]MathUtil.rectangle2DToRectangle([CtVariableReadImpl]selRect2D);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]targetRect.contains([CtVariableReadImpl]selRect)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]targetPanel.scrollRectToVisible([CtVariableReadImpl]selRect);
        }
        [CtInvocationImpl]clearSelectionGroups();
        [CtAssignmentImpl][CtFieldWriteImpl]selectionActive = [CtLiteralImpl]true;
        [CtInvocationImpl]createSelectionGroups();
        [CtCommentImpl]// redrawPanel(); // createSelectionGroups already calls this
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void setSelectRect([CtParameterImpl][CtTypeReferenceImpl]java.awt.Rectangle rectangle) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_selectRect = [CtVariableReadImpl]rectangle;
    }

    [CtMethodImpl][CtCommentImpl]/* //TODO: This compiles but I can't get the syntax correct to pass the (sub-)class
    public List<LayoutTrack> getLayoutTracksOfClass(@Nonnull Class<LayoutTrack> layoutTrackClass) {
    return layoutTrackList.stream()
    .filter(item -> item instanceof PositionablePoint)
    .filter(layoutTrackClass::isInstance)
    //.map(layoutTrackClass::cast)  // TODO: Do we need this? if not dead-code-strip
    .collect(Collectors.toList());
    }

    //TODO: This compiles but I can't get the syntax correct to pass the array of (sub-)classes
    public List<LayoutTrack> getLayoutTracksOfClasses(@Nonnull List<Class<? extends LayoutTrack>> layoutTrackClasses) {
    return layoutTrackList.stream()
    .filter(o -> layoutTrackClasses.contains(o.getClass()))
    .collect(Collectors.toList());
    }

    //TODO: This compiles but I can't get the syntax correct to pass the (sub-)class
    public List<LayoutTrack> getLayoutTracksOfClass(@Nonnull Class<? extends LayoutTrack> layoutTrackClass) {
    return getLayoutTracksOfClasses(new ArrayList<>(Arrays.asList(layoutTrackClass)));
    }

    public List<PositionablePoint> getPositionablePoints() {
    return getLayoutTracksOfClass(PositionablePoint);
    }
     */
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    private [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]LayoutTrack> getLayoutTracksOfClass([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]LayoutTrack> layoutTrackClass) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]layoutTrackList.stream().filter([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]layoutTrackClass::isInstance).map([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]layoutTrackClass::cast);
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]PositionablePoint> getPositionablePoints() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLayoutTracksOfClass([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.PositionablePoint.class).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.PositionablePoint.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutSlip> getLayoutSlips() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLayoutTracksOfClass([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutSlip.class).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutSlip.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]TrackSegment> getTrackSegments() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLayoutTracksOfClass([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.TrackSegment.class).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.TrackSegment.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutTurnout> getLayoutTurnouts() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// next line excludes LayoutSlips
        [CtInvocationImpl][CtFieldReadImpl]layoutTrackList.stream().filter([CtLambdaImpl]([CtParameterImpl] o) -> [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutSlip)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LayoutTurnout)).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutTurnout.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutTurntable> getLayoutTurntables() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLayoutTracksOfClass([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutTurntable.class).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutTurntable.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LevelXing> getLevelXings() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLayoutTracksOfClass([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LevelXing.class).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LevelXing.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutTrack> getLayoutTracks() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]layoutTrackList;
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutTurnout> getLayoutTurnoutsAndSlips() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLayoutTracksOfClass([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutTurnout.class).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutTurnout.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]LayoutShape> getLayoutShapes() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]layoutShapes;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sortLayoutShapesByLevel() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]layoutShapes.sort([CtLambdaImpl]([CtParameterImpl] lhs,[CtParameterImpl] rhs) -> [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// -1 == less than, 0 == equal, +1 == greater than
            return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.signum([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lhs.getLevel() - [CtInvocationImpl][CtVariableReadImpl]rhs.getLevel());
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]boolean showAlignPopup([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Positionable l) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void showToolTip([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]Positionable selection, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.awt.event.MouseEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ToolTip tip = [CtInvocationImpl][CtVariableReadImpl]selection.getToolTip();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String txt = [CtInvocationImpl][CtVariableReadImpl]tip.getText();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]txt != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]txt.isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]tip.setLocation([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selection.getX() + [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]selection.getWidth() / [CtLiteralImpl]2), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selection.getY() + [CtInvocationImpl][CtVariableReadImpl]selection.getHeight());
            [CtInvocationImpl]setToolTip([CtVariableReadImpl]tip);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addToPopUpMenu([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]NamedBean nb, [CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]javax.swing.JMenuItem item, [CtParameterImpl][CtTypeReferenceImpl]int menu) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]nb == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]item == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List theList = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Sensor) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]theList = [CtFieldReadImpl]sensorList;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalHead) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]theList = [CtFieldReadImpl]signalList;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalMast) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]theList = [CtFieldReadImpl]signalMastList;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Block) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]theList = [CtFieldReadImpl]blockContentsLabelList;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Memory) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]theList = [CtFieldReadImpl]memoryLabelList;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]theList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object o : [CtVariableReadImpl]theList) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionableLabel si = [CtVariableReadImpl](([CtTypeReferenceImpl]PositionableLabel) (o));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]si.getNamedBean() == [CtVariableReadImpl]nb) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]si.getPopupUtility() != [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu != [CtFieldReadImpl]Editor.VIEWPOPUPONLY) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]si.getPopupUtility().addEditPopUpMenu([CtVariableReadImpl]item);
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu != [CtFieldReadImpl]Editor.EDITPOPUPONLY) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]si.getPopupUtility().addViewPopUpMenu([CtVariableReadImpl]item);
                    }
                }
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Turnout) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt : [CtInvocationImpl]getLayoutTurnoutsAndSlips()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lt.getTurnout().equals([CtVariableReadImpl]nb)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu != [CtFieldReadImpl]Editor.VIEWPOPUPONLY) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]lt.addEditPopUpMenu([CtVariableReadImpl]item);
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu != [CtFieldReadImpl]Editor.EDITPOPUPONLY) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]lt.addViewPopUpMenu([CtVariableReadImpl]item);
                    }
                }
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"LayoutEditor: %s", [CtInvocationImpl]getLayoutName());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void vetoableChange([CtParameterImpl][CtAnnotationImpl]@jmri.jmrit.display.layoutEditor.Nonnull
    [CtTypeReferenceImpl]java.beans.PropertyChangeEvent evt) throws [CtTypeReferenceImpl]java.beans.PropertyVetoException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]NamedBean nb = [CtInvocationImpl](([CtTypeReferenceImpl]NamedBean) ([CtVariableReadImpl]evt.getOldValue()));
        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"CanDelete".equals([CtInvocationImpl][CtVariableReadImpl]evt.getPropertyName())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// NOI18N
            [CtTypeReferenceImpl]java.lang.StringBuilder message = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoInUseLayoutEditorHeader", [CtInvocationImpl]toString()));[CtCommentImpl]// NOI18N

            [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<ul>");
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalHead) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]containsSignalHead([CtVariableReadImpl](([CtTypeReferenceImpl]SignalHead) (nb)))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoSignalHeadIconFound"));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutTurnoutByBean([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoSignalHeadAssignedToTurnout", [CtInvocationImpl][CtVariableReadImpl]lt.getTurnoutName()));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint p = [CtInvocationImpl][CtFieldReadImpl]finder.findPositionablePointByBean([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtCommentImpl]// Need to expand to get the names of blocks
                    [CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoSignalHeadAssignedToPoint"));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]LevelXing lx = [CtInvocationImpl][CtFieldReadImpl]finder.findLevelXingByBean([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lx != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtCommentImpl]// Need to expand to get the names of blocks
                    [CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoSignalHeadAssignedToLevelXing"));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutSlip ls = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutSlipByBean([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ls != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoSignalHeadAssignedToLayoutSlip", [CtInvocationImpl][CtVariableReadImpl]ls.getTurnoutName()));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Turnout) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutTurnoutByBean([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoTurnoutIconFound"));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout t : [CtInvocationImpl]getLayoutTurnouts()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getLinkedTurnoutName() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uname = [CtInvocationImpl][CtVariableReadImpl]nb.getUserName();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nb.getSystemName().equals([CtInvocationImpl][CtVariableReadImpl]t.getLinkedTurnoutName()) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]uname != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]uname.equals([CtInvocationImpl][CtVariableReadImpl]t.getLinkedTurnoutName()))) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                            [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                            [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoLinkedTurnout", [CtInvocationImpl][CtVariableReadImpl]t.getTurnoutName()));
                            [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                        }
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]t.getSecondTurnout())) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                        [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                        [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoSecondTurnout", [CtInvocationImpl][CtVariableReadImpl]t.getTurnoutName()));
                        [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                    }
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutSlip ls = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutSlipByBean([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ls != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoSlipIconFound", [CtInvocationImpl][CtVariableReadImpl]ls.getDisplayName()));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable lx : [CtInvocationImpl]getLayoutTurntables()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]lx.isTurnoutControlled()) [CtBlockImpl]{
                        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]lx.getNumberRays(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]lx.getRayTurnout([CtVariableReadImpl]i))) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                                [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                                [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoRayTurntableControl", [CtInvocationImpl][CtVariableReadImpl]lx.getId()));
                                [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                                [CtBreakImpl]break;
                            }
                        }
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalMast) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]containsSignalMast([CtVariableReadImpl](([CtTypeReferenceImpl]SignalMast) (nb)))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"As an Icon");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String foundelsewhere = [CtInvocationImpl]findBeanUsage([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]foundelsewhere != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtVariableReadImpl]foundelsewhere);
                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Sensor) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtLiteralImpl]0;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SensorIcon si : [CtFieldReadImpl]sensorList) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]si.getNamedBean())) [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]count++;
                        [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]count > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"As an Icon %s times", [CtVariableReadImpl]count));
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String foundelsewhere = [CtInvocationImpl]findBeanUsage([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]foundelsewhere != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]message.append([CtVariableReadImpl]foundelsewhere);
                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Memory) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]MemoryIcon si : [CtFieldReadImpl]memoryLabelList) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]si.getMemory())) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                        [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"<li>");
                        [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoMemoryIconFound"));
                        [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</li>");
                    }
                }
            }
            [CtIfImpl]if ([CtVariableReadImpl]found) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]message.append([CtLiteralImpl]"</ul>");
                [CtInvocationImpl][CtVariableReadImpl]message.append([CtInvocationImpl][CtTypeAccessImpl]Bundle.getMessage([CtLiteralImpl]"VetoReferencesWillBeRemoved"));[CtCommentImpl]// NOI18N

                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.beans.PropertyVetoException([CtInvocationImpl][CtVariableReadImpl]message.toString(), [CtVariableReadImpl]evt);
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"DoDelete".equals([CtInvocationImpl][CtVariableReadImpl]evt.getPropertyName())) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// NOI18N
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalHead) [CtBlockImpl]{
                [CtInvocationImpl]removeSignalHead([CtVariableReadImpl](([CtTypeReferenceImpl]SignalHead) (nb)));
                [CtInvocationImpl]removeBeanRefs([CtVariableReadImpl]nb);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Turnout) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout lt = [CtInvocationImpl][CtFieldReadImpl]finder.findLayoutTurnoutByBean([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lt != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]lt.setTurnout([CtLiteralImpl]null);
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout t : [CtInvocationImpl]getLayoutTurnouts()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getLinkedTurnoutName() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]t.getLinkedTurnoutName().equals([CtInvocationImpl][CtVariableReadImpl]nb.getSystemName()) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]nb.getUserName() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]t.getLinkedTurnoutName().equals([CtInvocationImpl][CtVariableReadImpl]nb.getUserName()))) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]t.setLinkedTurnoutName([CtLiteralImpl]null);
                        }
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]t.getSecondTurnout())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]t.setSecondTurnout([CtLiteralImpl]null);
                    }
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutSlip sl : [CtInvocationImpl]getLayoutSlips()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]sl.getTurnout())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]sl.setTurnout([CtLiteralImpl]null);
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]sl.getTurnoutB())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]sl.setTurnoutB([CtLiteralImpl]null);
                    }
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurntable lx : [CtInvocationImpl]getLayoutTurntables()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]lx.isTurnoutControlled()) [CtBlockImpl]{
                        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]lx.getNumberRays(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]lx.getRayTurnout([CtVariableReadImpl]i))) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]lx.setRayTurnout([CtVariableReadImpl]i, [CtLiteralImpl]null, [CtTypeAccessImpl]NamedBean.UNKNOWN);
                            }
                        }
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SignalMast) [CtBlockImpl]{
                [CtInvocationImpl]removeBeanRefs([CtVariableReadImpl]nb);
                [CtIfImpl]if ([CtInvocationImpl]containsSignalMast([CtVariableReadImpl](([CtTypeReferenceImpl]SignalMast) (nb)))) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]SignalMastIcon> icon = [CtInvocationImpl][CtFieldReadImpl]signalMastList.iterator();
                    [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]icon.hasNext()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]SignalMastIcon i = [CtInvocationImpl][CtVariableReadImpl]icon.next();
                        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]i.getSignalMast().equals([CtVariableReadImpl]nb)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]icon.remove();
                            [CtInvocationImpl][CtSuperAccessImpl]super.removeFromContents([CtVariableReadImpl]i);
                        }
                    } 
                    [CtInvocationImpl]setDirty();
                    [CtInvocationImpl]redrawPanel();
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Sensor) [CtBlockImpl]{
                [CtInvocationImpl]removeBeanRefs([CtVariableReadImpl]nb);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]SensorIcon> icon = [CtInvocationImpl][CtFieldReadImpl]sensorImage.iterator();
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]icon.hasNext()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]SensorIcon i = [CtInvocationImpl][CtVariableReadImpl]icon.next();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]i.getSensor())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]icon.remove();
                        [CtInvocationImpl][CtSuperAccessImpl]super.removeFromContents([CtVariableReadImpl]i);
                    }
                } 
                [CtInvocationImpl]setDirty();
                [CtInvocationImpl]redrawPanel();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Memory) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]MemoryIcon> icon = [CtInvocationImpl][CtFieldReadImpl]memoryLabelList.iterator();
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]icon.hasNext()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]MemoryIcon i = [CtInvocationImpl][CtVariableReadImpl]icon.next();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nb.equals([CtInvocationImpl][CtVariableReadImpl]i.getMemory())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]icon.remove();
                        [CtInvocationImpl][CtSuperAccessImpl]super.removeFromContents([CtVariableReadImpl]i);
                    }
                } 
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]// protected void rename(String inFrom, String inTo) {
    [CtCommentImpl]// 
    [CtCommentImpl]// }
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void dispose() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorFrame != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorFrame.dispose();
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.sensorFrame = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame.dispose();
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.signalFrame = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.iconFrame != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.iconFrame.dispose();
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]leToolBarPanel.iconFrame = [CtLiteralImpl]null;
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.dispose();
    }

    [CtClassImpl][CtCommentImpl]// package protected
    class TurnoutComboBoxPopupMenuListener implements [CtTypeReferenceImpl]javax.swing.event.PopupMenuListener {
        [CtFieldImpl]private final [CtTypeReferenceImpl]jmri.swing.NamedBeanComboBox<[CtTypeReferenceImpl]Turnout> comboBox;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Turnout> currentTurnouts;

        [CtConstructorImpl]public TurnoutComboBoxPopupMenuListener([CtParameterImpl][CtTypeReferenceImpl]jmri.swing.NamedBeanComboBox<[CtTypeReferenceImpl]Turnout> comboBox, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Turnout> currentTurnouts) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.comboBox = [CtVariableReadImpl]comboBox;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentTurnouts = [CtVariableReadImpl]currentTurnouts;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void popupMenuWillBecomeVisible([CtParameterImpl][CtTypeReferenceImpl]javax.swing.event.PopupMenuEvent event) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This method is called before the popup menu becomes visible.
            [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"PopupMenuWillBecomeVisible");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Turnout> l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]comboBox.getManager().getNamedBeanSet().forEach([CtLambdaImpl]([CtParameterImpl] turnout) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]currentTurnouts.contains([CtVariableReadImpl]turnout)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]validatePhysicalTurnout([CtInvocationImpl][CtVariableReadImpl]turnout.getDisplayName(), [CtLiteralImpl]null)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]l.add([CtVariableReadImpl]turnout);
                    }
                }
            });
            [CtInvocationImpl][CtFieldReadImpl]comboBox.setExcludedItems([CtVariableReadImpl]l);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void popupMenuWillBecomeInvisible([CtParameterImpl][CtTypeReferenceImpl]javax.swing.event.PopupMenuEvent event) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This method is called before the popup menu becomes invisible
            [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"PopupMenuWillBecomeInvisible");
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void popupMenuCanceled([CtParameterImpl][CtTypeReferenceImpl]javax.swing.event.PopupMenuEvent event) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This method is called when the popup menu is canceled
            [CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.log.debug([CtLiteralImpl]"PopupMenuCanceled");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a listener that will exclude turnouts that are present in the
     * current panel.
     *
     * @param comboBox
     * 		The NamedBeanComboBox that contains the turnout list.
     * @return A PopupMenuListener
     */
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.TurnoutComboBoxPopupMenuListener newTurnoutComboBoxPopupMenuListener([CtParameterImpl][CtTypeReferenceImpl]jmri.swing.NamedBeanComboBox<[CtTypeReferenceImpl]Turnout> comboBox) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.TurnoutComboBoxPopupMenuListener([CtVariableReadImpl]comboBox, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a listener that will exclude turnouts that are present in the
     * current panel. The list of current turnouts are not excluded.
     *
     * @param comboBox
     * 		The NamedBeanComboBox that contains the turnout
     * 		list.
     * @param currentTurnouts
     * 		The turnouts to be left in the turnout list.
     * @return A PopupMenuListener
     */
    public [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.TurnoutComboBoxPopupMenuListener newTurnoutComboBoxPopupMenuListener([CtParameterImpl][CtTypeReferenceImpl]jmri.swing.NamedBeanComboBox<[CtTypeReferenceImpl]Turnout> comboBox, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Turnout> currentTurnouts) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.TurnoutComboBoxPopupMenuListener([CtVariableReadImpl]comboBox, [CtVariableReadImpl]currentTurnouts);
    }

    [CtFieldImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]NamedBeanUsageReport> usageReport;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]NamedBeanUsageReport> getUsageReport([CtParameterImpl][CtTypeReferenceImpl]NamedBean bean) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]usageReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bean != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]usageReport = [CtInvocationImpl][CtSuperAccessImpl]super.getUsageReport([CtVariableReadImpl]bean);
            [CtInvocationImpl][CtCommentImpl]// LE Specific checks
            [CtCommentImpl]// Turnouts
            findTurnoutUsage([CtVariableReadImpl]bean);
            [CtInvocationImpl][CtCommentImpl]// Check A, EB, EC for sensors, masts, heads
            findPositionalUsage([CtVariableReadImpl]bean);
            [CtInvocationImpl][CtCommentImpl]// Level Crossings
            findXingWhereUsed([CtVariableReadImpl]bean);
            [CtInvocationImpl][CtCommentImpl]// Track segments
            findSegmentWhereUsed([CtVariableReadImpl]bean);
        }
        [CtReturnImpl]return [CtFieldReadImpl]usageReport;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void findTurnoutUsage([CtParameterImpl][CtTypeReferenceImpl]NamedBean bean) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LayoutTurnout turnout : [CtInvocationImpl]getLayoutTurnoutsAndSlips()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String data = [CtInvocationImpl]getUsageData([CtVariableReadImpl]turnout);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getTurnout())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnout", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSecondTurnout())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnout2", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl]isLBLockUsed([CtVariableReadImpl]bean, [CtInvocationImpl][CtVariableReadImpl]turnout.getLayoutBlock())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutBlock", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]turnout.hasEnteringDoubleTrack()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]isLBLockUsed([CtVariableReadImpl]bean, [CtInvocationImpl][CtVariableReadImpl]turnout.getLayoutBlockB())) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutBlock", [CtVariableReadImpl]data));
                }
                [CtIfImpl]if ([CtInvocationImpl]isLBLockUsed([CtVariableReadImpl]bean, [CtInvocationImpl][CtVariableReadImpl]turnout.getLayoutBlockC())) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutBlock", [CtVariableReadImpl]data));
                }
                [CtIfImpl]if ([CtInvocationImpl]isLBLockUsed([CtVariableReadImpl]bean, [CtInvocationImpl][CtVariableReadImpl]turnout.getLayoutBlockD())) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutBlock", [CtVariableReadImpl]data));
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSensorA())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSensor", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSensorB())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSensor", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSensorC())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSensor", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSensorD())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSensor", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalAMast())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalMast", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalBMast())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalMast", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalCMast())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalMast", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalDMast())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalMast", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalA1())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalA2())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalA3())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalB1())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalB2())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalC1())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalC2())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalD1())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]turnout.getSignalD2())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorTurnoutSignalHead", [CtVariableReadImpl]data));
            }
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void findPositionalUsage([CtParameterImpl][CtTypeReferenceImpl]NamedBean bean) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]PositionablePoint point : [CtInvocationImpl]getPositionablePoints()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String data = [CtInvocationImpl]getUsageData([CtVariableReadImpl]point);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]point.getEastBoundSensor())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorPointSensor", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]point.getWestBoundSensor())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorPointSensor", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]point.getEastBoundSignalHead())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorPointSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]point.getWestBoundSignalHead())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorPointSignalHead", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]point.getEastBoundSignalMast())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorPointSignalMast", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]point.getWestBoundSignalMast())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorPointSignalMast", [CtVariableReadImpl]data));
            }
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void findSegmentWhereUsed([CtParameterImpl][CtTypeReferenceImpl]NamedBean bean) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment segment : [CtInvocationImpl]getTrackSegments()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isLBLockUsed([CtVariableReadImpl]bean, [CtInvocationImpl][CtVariableReadImpl]segment.getLayoutBlock())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String data = [CtInvocationImpl]getUsageData([CtVariableReadImpl]segment);
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorSegmentBlock", [CtVariableReadImpl]data));
            }
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void findXingWhereUsed([CtParameterImpl][CtTypeReferenceImpl]NamedBean bean) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LevelXing xing : [CtInvocationImpl]getLevelXings()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String data = [CtInvocationImpl]getUsageData([CtVariableReadImpl]xing);
            [CtIfImpl]if ([CtInvocationImpl]isLBLockUsed([CtVariableReadImpl]bean, [CtInvocationImpl][CtVariableReadImpl]xing.getLayoutBlockAC())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorXingBlock", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl]isLBLockUsed([CtVariableReadImpl]bean, [CtInvocationImpl][CtVariableReadImpl]xing.getLayoutBlockBD())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorXingBlock", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl]isUsedInXing([CtVariableReadImpl]bean, [CtVariableReadImpl]xing, [CtTypeAccessImpl]LevelXing.Geometry.POINTA)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorXingOther", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl]isUsedInXing([CtVariableReadImpl]bean, [CtVariableReadImpl]xing, [CtTypeAccessImpl]LevelXing.Geometry.POINTB)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorXingOther", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl]isUsedInXing([CtVariableReadImpl]bean, [CtVariableReadImpl]xing, [CtTypeAccessImpl]LevelXing.Geometry.POINTC)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorXingOther", [CtVariableReadImpl]data));
            }
            [CtIfImpl]if ([CtInvocationImpl]isUsedInXing([CtVariableReadImpl]bean, [CtVariableReadImpl]xing, [CtTypeAccessImpl]LevelXing.Geometry.POINTD)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]usageReport.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]NamedBeanUsageReport([CtLiteralImpl]"LayoutEditorXingOther", [CtVariableReadImpl]data));
            }
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String getUsageData([CtParameterImpl][CtTypeReferenceImpl]LayoutTrack track) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.geom.Point2D point = [CtInvocationImpl][CtVariableReadImpl]track.getCoordsCenter();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]track instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]TrackSegment) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]TrackSegment segment = [CtVariableReadImpl](([CtTypeReferenceImpl]TrackSegment) (track));
            [CtAssignmentImpl][CtVariableWriteImpl]point = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]java.awt.geom.Point2D.Double([CtInvocationImpl][CtVariableReadImpl]segment.getCentreSegX(), [CtInvocationImpl][CtVariableReadImpl]segment.getCentreSegY());
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s :: x=%d, y=%d", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]track.getClass().getSimpleName(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtVariableReadImpl]point.getX()), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtVariableReadImpl]point.getY()));
    }

    [CtMethodImpl][CtTypeReferenceImpl]boolean isLBLockUsed([CtParameterImpl][CtTypeReferenceImpl]NamedBean bean, [CtParameterImpl][CtTypeReferenceImpl]LayoutBlock lblock) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lblock != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]lblock.getBlock())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtTypeReferenceImpl]boolean isUsedInXing([CtParameterImpl][CtTypeReferenceImpl]NamedBean bean, [CtParameterImpl][CtTypeReferenceImpl]LevelXing xing, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]LevelXing.Geometry point) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]xing.getSensor([CtVariableReadImpl]point))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]xing.getSignalHead([CtVariableReadImpl]point))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bean.equals([CtInvocationImpl][CtVariableReadImpl]xing.getSignalMast([CtVariableReadImpl]point))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtFieldImpl][CtCommentImpl]// initialize logging
    private static final transient [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]jmri.jmrit.display.layoutEditor.LayoutEditor.class);
}
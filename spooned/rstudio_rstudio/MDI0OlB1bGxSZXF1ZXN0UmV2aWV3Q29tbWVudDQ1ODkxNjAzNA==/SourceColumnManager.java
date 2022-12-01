[CompilationUnitImpl][CtCommentImpl]/* SourceColumnManager.java

Copyright (C) 2020 by RStudio, PBC

Unless you have received this program directly from RStudio pursuant
to the terms of a commercial license agreement with RStudio, then
this program is licensed to you under the terms of version 3 of the
GNU Affero General Public License. This program is distributed WITHOUT
ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */
[CtPackageDeclarationImpl]package org.rstudio.studio.client.workbench.views.source;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.explorer.ObjectExplorerEditingTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range;
[CtUnresolvedImport]import com.google.gwt.json.client.JSONValue;
[CtUnresolvedImport]import com.google.inject.Inject;
[CtUnresolvedImport]import org.rstudio.studio.client.common.SimpleRequestCallback;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.*;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.commands.Commands;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.UnsavedChangesTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.EditingTargetSource;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdFrontMatter;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.*;
[CtUnresolvedImport]import org.rstudio.core.client.widget.ProgressIndicator;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdTemplateData;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.prefs.model.UserPrefs;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdOutputFormat;
[CtUnresolvedImport]import org.rstudio.studio.client.server.VoidServerRequestCallback;
[CtUnresolvedImport]import com.google.gwt.user.client.Command;
[CtUnresolvedImport]import org.rstudio.studio.client.common.FilePathUtils;
[CtUnresolvedImport]import org.rstudio.studio.client.common.synctex.Synctex;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.helper.JSObjectStateValue;
[CtUnresolvedImport]import org.rstudio.studio.client.events.GetEditorContextEvent;
[CtUnresolvedImport]import org.rstudio.core.client.js.JsObject;
[CtUnresolvedImport]import org.rstudio.studio.client.common.dependencies.DependencyManager;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.environment.events.DebugModeChangedEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Selection;
[CtUnresolvedImport]import com.google.gwt.json.client.JSONString;
[CtUnresolvedImport]import org.rstudio.studio.client.common.GlobalProgressDelayer;
[CtUnresolvedImport]import com.google.gwt.user.client.ui.Widget;
[CtImportImpl]import java.util.concurrent.atomic.AtomicInteger;
[CtUnresolvedImport]import org.rstudio.studio.client.application.events.EventBus;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetRMarkdownHelper;
[CtUnresolvedImport]import org.rstudio.core.client.command.Handler;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.output.find.events.FindInFilesEvent;
[CtUnresolvedImport]import org.rstudio.core.client.files.FileSystemItem;
[CtUnresolvedImport]import com.google.inject.Singleton;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.Session;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRMarkdownDialog;
[CtUnresolvedImport]import com.google.gwt.core.client.GWT;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.prefs.model.UserState;
[CtUnresolvedImport]import org.rstudio.core.client.command.AppCommand;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.ClientState;
[CtUnresolvedImport]import org.rstudio.core.client.js.JsUtil;
[CtUnresolvedImport]import org.rstudio.studio.client.common.GlobalDisplay;
[CtUnresolvedImport]import org.rstudio.core.client.widget.Operation;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.events.*;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.ui.PaneConfig;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog;
[CtUnresolvedImport]import org.rstudio.core.client.command.CommandBinder;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.events.EditingTargetSelectedEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.server.ServerRequestCallback;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.FileMRUList;
[CtUnresolvedImport]import com.google.gwt.core.client.JavaScriptObject;
[CtUnresolvedImport]import org.rstudio.core.client.widget.OperationWithInput;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.EditingTarget;
[CtUnresolvedImport]import com.google.gwt.core.client.JsArray;
[CtUnresolvedImport]import com.google.inject.Provider;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.explorer.model.ObjectExplorerHandle;
[CtUnresolvedImport]import org.rstudio.studio.client.palette.model.CommandPaletteEntrySource;
[CtUnresolvedImport]import org.rstudio.studio.client.palette.model.CommandPaletteItem;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdChosenTemplate;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.data.DataEditingTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor;
[CtUnresolvedImport]import org.rstudio.core.client.*;
[CtUnresolvedImport]import org.rstudio.studio.client.RStudioGinjector;
[CtUnresolvedImport]import com.google.gwt.core.client.JsArrayString;
[CtUnresolvedImport]import org.rstudio.studio.client.server.ServerError;
[CtClassImpl][CtAnnotationImpl]@com.google.inject.Singleton
public class SourceColumnManager implements [CtTypeReferenceImpl]org.rstudio.studio.client.palette.model.CommandPaletteEntrySource , [CtTypeReferenceImpl][CtTypeReferenceImpl]SourceExtendedTypeDetectedEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.environment.events.DebugModeChangedEvent.Handler {
    [CtInterfaceImpl]public interface CPSEditingTargetCommand {
        [CtMethodImpl][CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editingTarget, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation);
    }

    [CtClassImpl]public static class State extends [CtTypeReferenceImpl]com.google.gwt.core.client.JavaScriptObject {
        [CtMethodImpl][CtCommentImpl]/* -{
        return {
        names: names,
        activeColumn: activeColumn
        }
        }-
         */
        public static native [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State createState([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString names, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String activeColumn);

        [CtConstructorImpl]protected State() [CtBlockImpl]{
        }

        [CtMethodImpl]public final [CtArrayTypeReferenceImpl]java.lang.String[] getNames() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toStringArray([CtInvocationImpl]getNamesNative());
        }

        [CtMethodImpl][CtCommentImpl]/* -{
        if (this.activeColumn)
        return this.activeColumn;
        else
        return "";
        }-
         */
        public native final [CtTypeReferenceImpl]java.lang.String getActiveColumn();

        [CtMethodImpl][CtCommentImpl]/* -{
        return this.names;
        }-
         */
        private native [CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString getNamesNative();
    }

    [CtInterfaceImpl]interface Binder extends [CtTypeReferenceImpl]org.rstudio.core.client.command.CommandBinder<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.commands.Commands, [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager> {}

    [CtConstructorImpl]SourceColumnManager() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]RStudioGinjector.INSTANCE.injectMembers([CtThisAccessImpl]this);
    }

    [CtConstructorImpl][CtAnnotationImpl]@com.google.inject.Inject
    public SourceColumnManager([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.Binder binder, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Source.Display display, [CtParameterImpl][CtTypeReferenceImpl]SourceServerOperations server, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.GlobalDisplay globalDisplay, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.commands.Commands commands, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTargetSource editingTargetSource, [CtParameterImpl][CtTypeReferenceImpl]FileTypeRegistry fileTypeRegistry, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.application.events.EventBus events, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.dependencies.DependencyManager dependencyManager, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.synctex.Synctex synctex, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserPrefs userPrefs, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserState userState, [CtParameterImpl][CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.FileMRUList> pMruList, [CtParameterImpl][CtTypeReferenceImpl]SourceWindowManager windowManager) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]commands_ = [CtVariableReadImpl]commands;
        [CtInvocationImpl][CtVariableReadImpl]binder.bind([CtFieldReadImpl]commands_, [CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]server_ = [CtVariableReadImpl]server;
        [CtAssignmentImpl][CtFieldWriteImpl]globalDisplay_ = [CtVariableReadImpl]globalDisplay;
        [CtAssignmentImpl][CtFieldWriteImpl]editingTargetSource_ = [CtVariableReadImpl]editingTargetSource;
        [CtAssignmentImpl][CtFieldWriteImpl]fileTypeRegistry_ = [CtVariableReadImpl]fileTypeRegistry;
        [CtAssignmentImpl][CtFieldWriteImpl]events_ = [CtVariableReadImpl]events;
        [CtAssignmentImpl][CtFieldWriteImpl]dependencyManager_ = [CtVariableReadImpl]dependencyManager;
        [CtAssignmentImpl][CtFieldWriteImpl]session_ = [CtVariableReadImpl]session;
        [CtAssignmentImpl][CtFieldWriteImpl]synctex_ = [CtVariableReadImpl]synctex;
        [CtAssignmentImpl][CtFieldWriteImpl]userPrefs_ = [CtVariableReadImpl]userPrefs;
        [CtAssignmentImpl][CtFieldWriteImpl]userState_ = [CtVariableReadImpl]userState;
        [CtAssignmentImpl][CtFieldWriteImpl]pMruList_ = [CtVariableReadImpl]pMruList;
        [CtAssignmentImpl][CtFieldWriteImpl]windowManager_ = [CtVariableReadImpl]windowManager;
        [CtAssignmentImpl][CtFieldWriteImpl]rmarkdown_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetRMarkdownHelper();
        [CtAssignmentImpl][CtFieldWriteImpl]vimCommands_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SourceVimCommands();
        [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtLiteralImpl]null;
        [CtInvocationImpl]initDynamicCommands();
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SourceExtendedTypeDetectedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]DebugModeChangedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]EditingTargetSelectedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.EditingTargetSelectedEvent.Handler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onEditingTargetSelected([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.EditingTargetSelectedEvent event) [CtBlockImpl]{
                [CtInvocationImpl]setActive([CtInvocationImpl][CtVariableReadImpl]event.getTarget());
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SourceFileSavedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl]SourceFileSavedHandler()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onSourceFileSaved([CtParameterImpl][CtTypeReferenceImpl]SourceFileSavedEvent event) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().add([CtInvocationImpl][CtVariableReadImpl]event.getPath());
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]DocTabActivatedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]DocTabActivatedEvent.Handler()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onDocTabActivated([CtParameterImpl][CtTypeReferenceImpl]DocTabActivatedEvent event) [CtBlockImpl]{
                [CtInvocationImpl]setActiveDocId([CtInvocationImpl][CtVariableReadImpl]event.getId());
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SwitchToDocEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl]SwitchToDocHandler()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onSwitchToDoc([CtParameterImpl][CtTypeReferenceImpl]SwitchToDocEvent event) [CtBlockImpl]{
                [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setPhysicalTabIndex([CtInvocationImpl][CtVariableReadImpl]event.getSelectedIndex());
                [CtInvocationImpl][CtCommentImpl]// Fire an activation event just to ensure the activated
                [CtCommentImpl]// tab gets focus
                [CtInvocationImpl][CtFieldReadImpl]commands_.activateSource().execute();
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.addChangeHandler([CtLambdaImpl]([CtParameterImpl] event) -> [CtInvocationImpl]manageSourceNavigationCommands());
        [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.helper.JSObjectStateValue([CtLiteralImpl]"source-column-manager", [CtLiteralImpl]"column-info", [CtFieldReadImpl]org.rstudio.studio.client.workbench.model.ClientState.PERSISTENT, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().getClientState(), [CtLiteralImpl]false)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]void onInit([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject value) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.allowSourceColumns().getGlobalValue()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]columnList_.size() > [CtLiteralImpl]1) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.PaneConfig paneConfig = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.panes().getValue().cast();
                        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.panes().setGlobalValue([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.ui.PaneConfig.create([CtInvocationImpl][CtTypeAccessImpl]JsArrayUtil.copy([CtInvocationImpl][CtVariableReadImpl]paneConfig.getQuadrants()), [CtInvocationImpl][CtVariableReadImpl]paneConfig.getTabSet1(), [CtInvocationImpl][CtVariableReadImpl]paneConfig.getTabSet2(), [CtInvocationImpl][CtVariableReadImpl]paneConfig.getHiddenTabSet(), [CtInvocationImpl][CtVariableReadImpl]paneConfig.getConsoleLeftOnTop(), [CtInvocationImpl][CtVariableReadImpl]paneConfig.getConsoleRightOnTop(), [CtLiteralImpl]0).cast());
                        [CtInvocationImpl]consolidateColumns([CtLiteralImpl]1);
                    }
                    [CtReturnImpl]return;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State.createState([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toJsArrayString([CtInvocationImpl]getNames([CtLiteralImpl]false)), [CtInvocationImpl][CtInvocationImpl]getActive().getName());
                    [CtReturnImpl]return;
                }
                [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtInvocationImpl][CtVariableReadImpl]value.cast();
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]columnState_.getNames().length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtArrayReadImpl][CtInvocationImpl][CtFieldReadImpl]columnState_.getNames()[[CtVariableReadImpl]i];
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]StringUtil.equals([CtVariableReadImpl]name, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME))[CtBlockImpl]
                        [CtInvocationImpl]add([CtVariableReadImpl]name, [CtLiteralImpl]false);

                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject getValue() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]columnState_.cast();
            }
        };
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.GWT.create([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumn.class);
        [CtInvocationImpl][CtVariableReadImpl]column.loadDisplay([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME, [CtVariableReadImpl]display, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]columnList_.add([CtVariableReadImpl]column);
        [CtInvocationImpl]setActive([CtInvocationImpl][CtVariableReadImpl]column.getName());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String add() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Source.Display display = [CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.GWT.create([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourcePane.class);
        [CtReturnImpl]return [CtInvocationImpl]add([CtVariableReadImpl]display, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String add([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Source.Display display) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]add([CtVariableReadImpl]display, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String add([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]boolean updateState) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]add([CtVariableReadImpl]name, [CtLiteralImpl]false, [CtVariableReadImpl]updateState);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String add([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]boolean activate, [CtParameterImpl][CtTypeReferenceImpl]boolean updateState) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Source.Display display = [CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.GWT.create([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourcePane.class);
        [CtReturnImpl]return [CtInvocationImpl]add([CtVariableReadImpl]name, [CtVariableReadImpl]display, [CtVariableReadImpl]activate, [CtVariableReadImpl]updateState);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String add([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Source.Display display, [CtParameterImpl][CtTypeReferenceImpl]boolean activate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]add([CtVariableReadImpl]display, [CtVariableReadImpl]activate, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String add([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Source.Display display, [CtParameterImpl][CtTypeReferenceImpl]boolean activate, [CtParameterImpl][CtTypeReferenceImpl]boolean updateState) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]add([CtBinaryOperatorImpl][CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.COLUMN_PREFIX + [CtInvocationImpl][CtTypeAccessImpl]StringUtil.makeRandomId([CtLiteralImpl]12), [CtVariableReadImpl]display, [CtVariableReadImpl]activate, [CtVariableReadImpl]updateState);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String add([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Source.Display display, [CtParameterImpl][CtTypeReferenceImpl]boolean activate, [CtParameterImpl][CtTypeReferenceImpl]boolean updateState) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]contains([CtVariableReadImpl]name))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]"";

        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.GWT.create([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumn.class);
        [CtInvocationImpl][CtVariableReadImpl]column.loadDisplay([CtVariableReadImpl]name, [CtVariableReadImpl]display, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]columnList_.add([CtVariableReadImpl]column);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]activate || [CtBinaryOperatorImpl]([CtFieldReadImpl]activeColumn_ == [CtLiteralImpl]null))[CtBlockImpl]
            [CtInvocationImpl]setActive([CtVariableReadImpl]column);

        [CtIfImpl]if ([CtVariableReadImpl]updateState)[CtBlockImpl]
            [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State.createState([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toJsArrayString([CtInvocationImpl]getNames([CtLiteralImpl]false)), [CtInvocationImpl][CtInvocationImpl]getActive().getName());

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]column.getName();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void initialSelect([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn lastActive = [CtInvocationImpl]getByName([CtInvocationImpl][CtFieldReadImpl]columnState_.getActiveColumn());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lastActive != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl]setActive([CtInvocationImpl]getByName([CtInvocationImpl][CtFieldReadImpl]columnState_.getActiveColumn()));

        [CtInvocationImpl][CtInvocationImpl]getActive().initialSelect([CtVariableReadImpl]index);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setActive([CtParameterImpl][CtTypeReferenceImpl]int xpos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl]findByPosition([CtVariableReadImpl]xpos);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]setActive([CtVariableReadImpl]column);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setActive([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtVariableReadImpl]name) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeColumn_ != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setActiveEditor([CtLiteralImpl]"");

            [CtAssignmentImpl][CtFieldWriteImpl]activeColumn_ = [CtLiteralImpl]null;
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// If we can't find the column, use the main column. This may happen on start up.
        [CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl]getByName([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]column = [CtInvocationImpl]getByName([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME);

        [CtInvocationImpl]setActive([CtVariableReadImpl]column);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setActive([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
        [CtInvocationImpl]setActive([CtInvocationImpl]findByDocument([CtInvocationImpl][CtVariableReadImpl]target.getId()));
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setActiveEditor([CtVariableReadImpl]target);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setActive([CtParameterImpl][CtTypeReferenceImpl]SourceColumn column) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn prevColumn = [CtFieldReadImpl]activeColumn_;
        [CtAssignmentImpl][CtFieldWriteImpl]activeColumn_ = [CtVariableReadImpl]column;
        [CtIfImpl][CtCommentImpl]// If the active column changed, we need to update the active editor
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]prevColumn != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]prevColumn != [CtFieldReadImpl]activeColumn_)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]prevColumn.setActiveEditor([CtLiteralImpl]"");
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setActiveEditor();

            [CtInvocationImpl]manageCommands([CtLiteralImpl]true);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State.createState([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toJsArrayString([CtInvocationImpl]getNames([CtLiteralImpl]false)), [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]activeColumn_ == [CtLiteralImpl]null ? [CtLiteralImpl]"" : [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getName());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setActiveDocId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String docId) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtVariableReadImpl]docId))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtVariableReadImpl]column.setActiveEditor([CtVariableReadImpl]docId);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]setActive([CtVariableReadImpl]target);
                [CtReturnImpl]return;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDocsRestored() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]docsRestored_ = [CtLiteralImpl]true;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setOpeningForSourceNavigation([CtParameterImpl][CtTypeReferenceImpl]boolean value) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]openingForSourceNavigation_ = [CtVariableReadImpl]value;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void activateColumns([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command afterActivation) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasActiveEditor()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeColumn_ == [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl]setActive([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME);

            [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.R, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                    [CtInvocationImpl]setActive([CtVariableReadImpl]target);
                    [CtInvocationImpl]doActivateSource([CtVariableReadImpl]afterActivation);
                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl]doActivateSource([CtVariableReadImpl]afterActivation);
        }
    }

    [CtMethodImpl][CtCommentImpl]// This method sets activeColumn_ to the main column if it is null. It should be used in cases
    [CtCommentImpl]// where it is better for the column to be the main column than null.
    public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumn getActive() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeColumn_ != [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]activeColumn_;

        [CtInvocationImpl]setActive([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME);
        [CtReturnImpl]return [CtFieldReadImpl]activeColumn_;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getActiveDocId() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().getId();

        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getActiveDocPath() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().getPath();

        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasActiveEditor() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeColumn_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() != [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isActiveEditor([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editingTarget) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() == [CtVariableReadImpl]editingTarget);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean getDocsRestored() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]docsRestored_;
    }

    [CtMethodImpl][CtCommentImpl]// see if there are additional command palette items made available
    [CtCommentImpl]// by the active editor
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.rstudio.studio.client.palette.model.CommandPaletteItem> getCommandPaletteItems() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().getCommandPaletteItems();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTabCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getPhysicalTabIndex() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getActive() != [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getActive().getPhysicalTabIndex();
        else[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> getNames([CtParameterImpl][CtTypeReferenceImpl]boolean excludeMain) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]excludeMain) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]StringUtil.equals([CtInvocationImpl][CtVariableReadImpl]column.getName(), [CtFieldReadImpl][CtFieldReferenceImpl]MAIN_SOURCE_NAME)))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]result.add([CtInvocationImpl][CtVariableReadImpl]column.getName());

        });
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.google.gwt.user.client.ui.Widget> getWidgets([CtParameterImpl][CtTypeReferenceImpl]boolean excludeMain) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.google.gwt.user.client.ui.Widget> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]excludeMain) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]StringUtil.equals([CtInvocationImpl][CtVariableReadImpl]column.getName(), [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME)))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]result.add([CtInvocationImpl][CtVariableReadImpl]column.asWidget());

        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]SourceColumn> getColumnList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]columnList_;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.google.gwt.user.client.ui.Widget getWidget([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl]getByName([CtVariableReadImpl]name) == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtInvocationImpl]getByName([CtVariableReadImpl]name).asWidget();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session getSession() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]session_;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceNavigationHistory getSourceNavigationHistory() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sourceNavigationHistory_;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void recordCurrentNavigationHistoryPosition() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().recordCurrentNavigationPosition();

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getEditorPositionString() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().getCurrentStatus();

        [CtReturnImpl]return [CtLiteralImpl]"No document tabs open";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.common.synctex.Synctex getSynctex() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]synctex_;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserState getUserState() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]userState_;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]columnList_.size();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getUntitledNum([CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger max = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger();
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl][CtVariableReadImpl]max.set([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl][CtVariableReadImpl]max.get(), [CtInvocationImpl][CtVariableReadImpl]column.getUntitledNum([CtVariableReadImpl]prefix))));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]max.intValue();
    }

    [CtMethodImpl][CtCommentImpl]/* -{
    var match = (new RegExp("^" + prefix + "([0-9]{1,5})$")).exec(name);
    if (!match)
    return 0;
    return parseInt(match[1]);
    }-
     */
    public native final [CtTypeReferenceImpl]int getUntitledNum([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix);

    [CtMethodImpl]public [CtTypeReferenceImpl]void clearSourceNavigationHistory() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasDoc())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.clear();

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void manageCommands([CtParameterImpl][CtTypeReferenceImpl]boolean forceSync) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean saveAllEnabled = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]column.isInitialized() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]StringUtil.equals([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getName(), [CtInvocationImpl][CtVariableReadImpl]column.getName())))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]column.manageCommands([CtVariableReadImpl]forceSync, [CtFieldReadImpl]activeColumn_);

            [CtIfImpl][CtCommentImpl]// if one document is dirty then we are enabled
            if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]saveAllEnabled) && [CtInvocationImpl][CtVariableReadImpl]column.isSaveCommandActive())[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]saveAllEnabled = [CtLiteralImpl]true;

        }
        [CtIfImpl][CtCommentImpl]// the active column is always managed last because any column can disable a command, but
        [CtCommentImpl]// only the active one can enable a command
        if ([CtInvocationImpl][CtFieldReadImpl]activeColumn_.isInitialized())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]activeColumn_.manageCommands([CtVariableReadImpl]forceSync, [CtFieldReadImpl]activeColumn_);

        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().getAllowShell())[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.sendToTerminal().setVisible([CtLiteralImpl]false);

        [CtIfImpl][CtCommentImpl]// if source windows are open, managing state of the command becomes
        [CtCommentImpl]// complicated, so leave it enabled
        if ([CtInvocationImpl][CtFieldReadImpl]windowManager_.areSourceWindowsOpen())[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.saveAllSourceDocs().setEnabled([CtVariableReadImpl]saveAllEnabled);

        [CtInvocationImpl]manageSourceNavigationCommands();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageSourceNavigationCommands() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.sourceNavigateBack().setEnabled([CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.isBackEnabled());
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.sourceNavigateBack().setEnabled([CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.isBackEnabled());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget addTab([CtParameterImpl][CtTypeReferenceImpl]SourceDocument doc, [CtParameterImpl][CtTypeReferenceImpl]int mode, [CtParameterImpl][CtTypeReferenceImpl]SourceColumn column) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]column = [CtFieldReadImpl]activeColumn_;

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]column.addTab([CtVariableReadImpl]doc, [CtVariableReadImpl]mode);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget addTab([CtParameterImpl][CtTypeReferenceImpl]SourceDocument doc, [CtParameterImpl][CtTypeReferenceImpl]boolean atEnd, [CtParameterImpl][CtTypeReferenceImpl]int mode, [CtParameterImpl][CtTypeReferenceImpl]SourceColumn column) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]column = [CtFieldReadImpl]activeColumn_;

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]column.addTab([CtVariableReadImpl]doc, [CtVariableReadImpl]atEnd, [CtVariableReadImpl]mode);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget findEditor([CtParameterImpl][CtTypeReferenceImpl]java.lang.String docId) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtVariableReadImpl]column.getDoc([CtVariableReadImpl]docId);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]target;

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget findEditorByPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtVariableReadImpl]path))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtVariableReadImpl]column.getEditorWithPath([CtVariableReadImpl]path);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]target;

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumn findByDocument([CtParameterImpl][CtTypeReferenceImpl]java.lang.String docId) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]column.hasDoc([CtVariableReadImpl]docId))[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]column;

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumn findByPosition([CtParameterImpl][CtTypeReferenceImpl]int x) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.user.client.ui.Widget w = [CtInvocationImpl][CtVariableReadImpl]column.asWidget();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int left = [CtInvocationImpl][CtVariableReadImpl]w.getAbsoluteLeft();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int right = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]w.getAbsoluteLeft() + [CtInvocationImpl][CtVariableReadImpl]w.getOffsetWidth();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]x >= [CtVariableReadImpl]left) && [CtBinaryOperatorImpl]([CtVariableReadImpl]x <= [CtVariableReadImpl]right))[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]column;

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isEmpty([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getByName([CtVariableReadImpl]name) == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getByName([CtVariableReadImpl]name).getTabCount() == [CtLiteralImpl]0);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean attemptTextEditorActivate() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtInvocationImpl]hasActiveEditor() || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editingTarget = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
        [CtInvocationImpl][CtVariableReadImpl]editingTarget.ensureTextEditorActive([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtInvocationImpl]getEditorContext([CtInvocationImpl][CtVariableReadImpl]editingTarget.getId(), [CtInvocationImpl][CtVariableReadImpl]editingTarget.getPath(), [CtInvocationImpl][CtVariableReadImpl]editingTarget.getDocDisplay());
        });
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void activateCodeBrowser([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String codeBrowserPath, [CtParameterImpl][CtTypeReferenceImpl]boolean replaceIfActive, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> callback) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// first check to see if this request can be fulfilled with an existing
        [CtCommentImpl]// code browser tab
        [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]selectTabWithDocPath([CtVariableReadImpl]codeBrowserPath);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]callback.onSuccess([CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget) (target)));
            [CtReturnImpl]return;
        }
        [CtIfImpl][CtCommentImpl]// then check to see if the active editor is a code browser -- if it is,
        [CtCommentImpl]// we'll use it as is, replacing its contents
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]replaceIfActive && [CtInvocationImpl]hasActiveEditor()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]CodeBrowserCreatedEvent([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().getId(), [CtVariableReadImpl]codeBrowserPath));
            [CtInvocationImpl][CtVariableReadImpl]callback.onSuccess([CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor())));
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtCommentImpl]// create a new one
        newDoc([CtTypeAccessImpl]FileTypeRegistry.CODEBROWSER, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget arg) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]CodeBrowserCreatedEvent([CtInvocationImpl][CtVariableReadImpl]arg.getId(), [CtVariableReadImpl]codeBrowserPath));
                [CtInvocationImpl][CtVariableReadImpl]callback.onSuccess([CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget) (arg)));
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onFailure([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]callback.onFailure([CtVariableReadImpl]error);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onCancelled() [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]callback.onCancelled();
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void activateObjectExplorer([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.explorer.model.ObjectExplorerHandle handle) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtInvocationImpl][CtVariableReadImpl]column.getEditors()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// bail if this isn't an object explorer filetype
                [CtTypeReferenceImpl]FileType fileType = [CtInvocationImpl][CtVariableReadImpl]target.getFileType();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]fileType instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ObjectExplorerFileType))[CtBlockImpl]
                    [CtContinueImpl]continue;

                [CtIfImpl][CtCommentImpl]// check for identical titles
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]handle.getTitle() == [CtInvocationImpl][CtVariableReadImpl]target.getTitle()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.explorer.ObjectExplorerEditingTarget) (target)).update([CtVariableReadImpl]handle);
                    [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
                    [CtInvocationImpl][CtVariableReadImpl]column.selectTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget());
                    [CtReturnImpl]return;
                }
            }
        });
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]server_.newDocument([CtInvocationImpl][CtTypeAccessImpl]FileTypeRegistry.OBJECT_EXPLORER.getTypeId(), [CtLiteralImpl]null, [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject) ([CtVariableReadImpl]handle.cast())), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]SourceDocument>([CtLiteralImpl]"Show Object Explorer")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]SourceDocument response) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]activeColumn_.addTab([CtVariableReadImpl]response, [CtTypeAccessImpl]Source.OPEN_INTERACTIVE);
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void showOverflowPopout() [CtBlockImpl]{
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.showOverflowPopout();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void showDataItem([CtParameterImpl][CtTypeReferenceImpl]DataItem data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtInvocationImpl][CtVariableReadImpl]column.getEditors()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtVariableReadImpl]target.getPath();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]path != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]path.equals([CtInvocationImpl][CtVariableReadImpl]data.getURI())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.data.DataEditingTarget) (target)).updateData([CtVariableReadImpl]data);
                    [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
                    [CtInvocationImpl][CtVariableReadImpl]column.selectTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget());
                    [CtReturnImpl]return;
                }
            }
        });
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]server_.newDocument([CtInvocationImpl][CtTypeAccessImpl]FileTypeRegistry.DATAFRAME.getTypeId(), [CtLiteralImpl]null, [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject) ([CtVariableReadImpl]data.cast())), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]SourceDocument>([CtLiteralImpl]"Show Data Frame")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]SourceDocument response) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]activeColumn_.addTab([CtVariableReadImpl]response, [CtTypeAccessImpl]Source.OPEN_INTERACTIVE);
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void showUnsavedChangesDialog([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> dirtyTargets, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result> saveOperation, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCancelled) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.showUnsavedChangesDialog([CtVariableReadImpl]title, [CtVariableReadImpl]dirtyTargets, [CtVariableReadImpl]saveOperation, [CtVariableReadImpl]onCancelled);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean insertSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String code, [CtParameterImpl][CtTypeReferenceImpl]boolean isBlock) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]activeColumn_.insertCode([CtVariableReadImpl]code, [CtVariableReadImpl]isBlock);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabRight() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.moveTab([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getPhysicalTabIndex(), [CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabLeft() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.moveTab([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getPhysicalTabIndex(), [CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabToFirst() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.moveTab([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getPhysicalTabIndex(), [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getPhysicalTabIndex() * [CtUnaryOperatorImpl](-[CtLiteralImpl]1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabToLast() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.moveTab([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getPhysicalTabIndex(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() - [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getPhysicalTabIndex()) - [CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onSwitchToTab() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]showOverflowPopout();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onFirstTab() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() > [CtLiteralImpl]0)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setPhysicalTabIndex([CtLiteralImpl]0);

    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onPreviousTab() [CtBlockImpl]{
        [CtInvocationImpl]switchToTab([CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.wrapTabNavigation().getValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNextTab() [CtBlockImpl]{
        [CtInvocationImpl]switchToTab([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.wrapTabNavigation().getValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onLastTab() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.ensureVisible([CtLiteralImpl]false);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() > [CtLiteralImpl]0)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setPhysicalTabIndex([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() - [CtLiteralImpl]1);

    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onCloseSourceDoc() [CtBlockImpl]{
        [CtInvocationImpl]closeSourceDoc([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onFindInFiles() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchPattern = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget textEditor = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String selection = [CtInvocationImpl][CtVariableReadImpl]textEditor.getSelectedText();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean multiLineSelection = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selection.indexOf([CtLiteralImpl]'\n') != [CtUnaryOperatorImpl](-[CtLiteralImpl]1);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]selection.length() != [CtLiteralImpl]0) && [CtUnaryOperatorImpl](![CtVariableReadImpl]multiLineSelection))[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]searchPattern = [CtVariableReadImpl]selection;

        }
        [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.output.find.events.FindInFilesEvent([CtVariableReadImpl]searchPattern));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onDebugModeChanged([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.environment.events.DebugModeChangedEvent evt) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// when debugging ends, always disengage any active debug highlights
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]evt.debugging()) && [CtInvocationImpl]hasActiveEditor()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().endDebugHighlighting();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onSourceExtendedTypeDetected([CtParameterImpl][CtTypeReferenceImpl]SourceExtendedTypeDetectedEvent e) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// set the extended type of the specified source file
        [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]findEditor([CtInvocationImpl][CtVariableReadImpl]e.getDocId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]target.adaptToExtendedFileType([CtInvocationImpl][CtVariableReadImpl]e.getExtendedType());

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void nextTabWithWrap() [CtBlockImpl]{
        [CtInvocationImpl]switchToTab([CtLiteralImpl]1, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void prevTabWithWrap() [CtBlockImpl]{
        [CtInvocationImpl]switchToTab([CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]true);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void switchToTab([CtParameterImpl][CtTypeReferenceImpl]int delta, [CtParameterImpl][CtTypeReferenceImpl]boolean wrap) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getActive().getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.ensureVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int targetIndex = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getPhysicalTabIndex() + [CtVariableReadImpl]delta;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]targetIndex > [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() - [CtLiteralImpl]1)) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]wrap)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]targetIndex = [CtLiteralImpl]0;
            else[CtBlockImpl]
                [CtReturnImpl]return;

        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]targetIndex < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]wrap)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]targetIndex = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() - [CtLiteralImpl]1;
            else[CtBlockImpl]
                [CtReturnImpl]return;

        }
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setPhysicalTabIndex([CtVariableReadImpl]targetIndex);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doActivateSource([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command afterActivation) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.ensureVisible([CtLiteralImpl]false);
        [CtIfImpl]if ([CtInvocationImpl]hasActiveEditor()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().focus();
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().ensureCursorVisible();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]afterActivation != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]afterActivation.execute();

    }

    [CtMethodImpl][CtCommentImpl]// new doc functions
    public [CtTypeReferenceImpl]void newRMarkdownV1Doc() [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtLiteralImpl]"", [CtLiteralImpl]"v1.Rmd", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]3, [CtLiteralImpl]0));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void newRMarkdownV2Doc() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]rmarkdown_.showNewRMarkdownDialog([CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRMarkdownDialog.Result>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRMarkdownDialog.Result result) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// No document chosen, just create an empty one
                    newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtLiteralImpl]"", [CtLiteralImpl]"default.Rmd");
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.isNewDocument()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRMarkdownDialog.RmdNewDocument doc = [CtInvocationImpl][CtVariableReadImpl]result.getNewDocument();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String author = [CtInvocationImpl][CtVariableReadImpl]doc.getAuthor();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]author.length() > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.documentAuthor().setGlobalValue([CtVariableReadImpl]author);
                        [CtInvocationImpl][CtFieldReadImpl]userPrefs_.writeUserPrefs();
                    }
                    [CtInvocationImpl]newRMarkdownV2Doc([CtVariableReadImpl]doc);
                } else [CtBlockImpl]{
                    [CtInvocationImpl]newDocFromRmdTemplate([CtVariableReadImpl]result);
                }
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newDocFromRmdTemplate([CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRMarkdownDialog.Result result) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.rmarkdown.model.RmdChosenTemplate template = [CtInvocationImpl][CtVariableReadImpl]result.getFromTemplate();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]template.createDir()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]rmarkdown_.createDraftFromTemplate([CtVariableReadImpl]template);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]rmarkdown_.getTemplateContent([CtVariableReadImpl]template, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String content) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]content.length() == [CtLiteralImpl]0)[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Template Content Missing", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The template at " + [CtInvocationImpl][CtVariableReadImpl]template.getTemplatePath()) + [CtLiteralImpl]" is missing.");

                [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtVariableReadImpl]content, [CtLiteralImpl]null);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newRMarkdownV2Doc([CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRMarkdownDialog.RmdNewDocument doc) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]rmarkdown_.frontMatterToYAML([CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.rmarkdown.model.RmdFrontMatter) ([CtInvocationImpl][CtVariableReadImpl]doc.getJSOResult().cast())), [CtLiteralImpl]null, [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String yaml) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String template = [CtLiteralImpl]"";
                [CtIfImpl][CtCommentImpl]// select a template appropriate to the document type we're creating
                if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]doc.getTemplate().equals([CtTypeAccessImpl]RmdTemplateData.PRESENTATION_TEMPLATE))[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]template = [CtLiteralImpl]"presentation.Rmd";
                else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]doc.isShiny()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]doc.getFormat().endsWith([CtTypeAccessImpl]RmdOutputFormat.OUTPUT_PRESENTATION_SUFFIX))[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]template = [CtLiteralImpl]"shiny_presentation.Rmd";
                    else[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]template = [CtLiteralImpl]"shiny.Rmd";

                } else[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]template = [CtLiteralImpl]"document.Rmd";

                [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtLiteralImpl]"", [CtVariableReadImpl]template, [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]1, [CtLiteralImpl]0), [CtLiteralImpl]null, [CtNewClassImpl]new [CtTypeReferenceImpl]TransformerCommand<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.String transform([CtParameterImpl][CtTypeReferenceImpl]java.lang.String input) [CtBlockImpl]{
                        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]org.rstudio.studio.client.rmarkdown.model.RmdFrontMatter.FRONTMATTER_SEPARATOR + [CtVariableReadImpl]yaml) + [CtFieldReadImpl]org.rstudio.studio.client.rmarkdown.model.RmdFrontMatter.FRONTMATTER_SEPARATOR) + [CtLiteralImpl]"\n") + [CtVariableReadImpl]input;
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template) [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtVariableReadImpl]fileType, [CtVariableReadImpl]name, [CtVariableReadImpl]template, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position cursorPosition) [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtVariableReadImpl]fileType, [CtVariableReadImpl]name, [CtVariableReadImpl]template, [CtVariableReadImpl]cursorPosition, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position cursorPosition, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onSuccess) [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtVariableReadImpl]fileType, [CtVariableReadImpl]name, [CtVariableReadImpl]template, [CtVariableReadImpl]cursorPosition, [CtVariableReadImpl]onSuccess, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void startDebug() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setPendingDebugSelection();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget selectTabWithDocPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor = [CtInvocationImpl][CtVariableReadImpl]column.getEditorWithPath([CtVariableReadImpl]path);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]editor != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]column.selectTab([CtInvocationImpl][CtVariableReadImpl]editor.asWidget());
                [CtReturnImpl]return [CtVariableReadImpl]editor;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position cursorPosition, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onSuccess, [CtParameterImpl]final [CtTypeReferenceImpl]TransformerCommand<[CtTypeReferenceImpl]java.lang.String> contentTransformer) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.widget.ProgressIndicator indicator = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.GlobalProgressDelayer([CtFieldReadImpl]globalDisplay_, [CtLiteralImpl]500, [CtLiteralImpl]"Creating new document...").getIndicator();
        [CtInvocationImpl][CtFieldReadImpl]server_.getSourceTemplate([CtVariableReadImpl]name, [CtVariableReadImpl]template, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]java.lang.String templateContents) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]indicator.onCompleted();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]contentTransformer != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]templateContents = [CtInvocationImpl][CtVariableReadImpl]contentTransformer.transform([CtVariableReadImpl]templateContents);

                [CtInvocationImpl]newDoc([CtVariableReadImpl]fileType, [CtVariableReadImpl]templateContents, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cursorPosition != [CtLiteralImpl]null)[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]target.setCursorPosition([CtVariableReadImpl]cursorPosition);

                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]onSuccess != [CtLiteralImpl]null)[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]onSuccess.execute([CtVariableReadImpl]target);

                    }
                });
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]indicator.onError([CtInvocationImpl][CtVariableReadImpl]error.getUserMessage());
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void newDoc([CtParameterImpl][CtTypeReferenceImpl]EditableFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> callback) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getActive().newDoc([CtVariableReadImpl]fileType, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void newDoc([CtParameterImpl][CtTypeReferenceImpl]EditableFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String contents, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getActive().newDoc([CtVariableReadImpl]fileType, [CtVariableReadImpl]contents, [CtVariableReadImpl]resultCallback);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void disownDoc([CtParameterImpl][CtTypeReferenceImpl]java.lang.String docId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl]findByDocument([CtVariableReadImpl]docId);
        [CtInvocationImpl][CtVariableReadImpl]column.closeDoc([CtVariableReadImpl]docId);
    }

    [CtMethodImpl][CtCommentImpl]// When dragging between columns/windows, we need to be specific about which column we're
    [CtCommentImpl]// removing the document from as it may exist in more than one column. If the column is null,
    [CtCommentImpl]// it is assumed that we are a satellite window and do not have multiple displays.
    public [CtTypeReferenceImpl]void disownDocOnDrag([CtParameterImpl][CtTypeReferenceImpl]java.lang.String docId, [CtParameterImpl][CtTypeReferenceImpl]SourceColumn column) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getSize() > [CtLiteralImpl]1)[CtBlockImpl]
                [CtInvocationImpl][CtTypeAccessImpl]Debug.logWarning([CtLiteralImpl]"Warning: No column was provided to remove the doc from.");

            [CtAssignmentImpl][CtVariableWriteImpl]column = [CtInvocationImpl]getActive();
        }
        [CtInvocationImpl][CtVariableReadImpl]column.closeDoc([CtVariableReadImpl]docId);
        [CtInvocationImpl][CtVariableReadImpl]column.cancelTabDrag();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void selectTab([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl]findByDocument([CtInvocationImpl][CtVariableReadImpl]target.getId());
        [CtInvocationImpl][CtVariableReadImpl]column.ensureVisible([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]column.selectTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeTabs([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString ids) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ids != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl][CtVariableReadImpl]column.closeTabs([CtVariableReadImpl]ids));

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeTabWithPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl][CtTypeReferenceImpl]boolean interactive) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]findEditorByPath([CtVariableReadImpl]path);
        [CtInvocationImpl]closeTab([CtVariableReadImpl]target, [CtVariableReadImpl]interactive);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeTab([CtParameterImpl][CtTypeReferenceImpl]boolean interactive) [CtBlockImpl]{
        [CtInvocationImpl]closeTab([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor(), [CtVariableReadImpl]interactive);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeTab([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target, [CtParameterImpl][CtTypeReferenceImpl]boolean interactive) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]findByDocument([CtInvocationImpl][CtVariableReadImpl]target.getId()).closeTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget(), [CtVariableReadImpl]interactive, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeTab([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target, [CtParameterImpl][CtTypeReferenceImpl]boolean interactive, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onClosed) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]findByDocument([CtInvocationImpl][CtVariableReadImpl]target.getId()).closeTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget(), [CtVariableReadImpl]interactive, [CtVariableReadImpl]onClosed);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeAllTabs([CtParameterImpl][CtTypeReferenceImpl]boolean excludeActive, [CtParameterImpl][CtTypeReferenceImpl]boolean excludeMain) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl]closeAllTabs([CtVariableReadImpl]column, [CtVariableReadImpl]excludeActive, [CtVariableReadImpl]excludeMain));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeAllTabs([CtParameterImpl][CtTypeReferenceImpl]SourceColumn column, [CtParameterImpl][CtTypeReferenceImpl]boolean excludeActive, [CtParameterImpl][CtTypeReferenceImpl]boolean excludeMain) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]excludeMain) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]StringUtil.equals([CtInvocationImpl][CtVariableReadImpl]column.getName(), [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME))) [CtBlockImpl]{
            [CtInvocationImpl]cpsExecuteForEachEditor([CtInvocationImpl][CtVariableReadImpl]column.getEditors(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.CPSEditingTargetCommand()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]excludeActive && [CtBinaryOperatorImpl]([CtVariableReadImpl]target == [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                        [CtReturnImpl]return;
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]column.closeTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget(), [CtLiteralImpl]false, [CtVariableReadImpl]continuation);
                    }
                }
            });
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void closeSourceDoc([CtParameterImpl][CtTypeReferenceImpl]boolean interactive) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]closeTab([CtVariableReadImpl]interactive);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void saveAllSourceDocs() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl]cpsExecuteForEachEditor([CtInvocationImpl][CtVariableReadImpl]column.getEditors(), [CtLambdaImpl]([CtParameterImpl] editingTarget,[CtParameterImpl] continuation) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]editingTarget.dirtyState().getValue()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]editingTarget.save([CtVariableReadImpl]continuation);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
            }
        }));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void revertUnsavedTargets([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> unsavedTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl][CtVariableReadImpl]unsavedTargets.addAll([CtInvocationImpl][CtVariableReadImpl]column.getUnsavedEditors([CtVariableReadImpl]Source.TYPE_FILE_BACKED, [CtLiteralImpl]null)));
        [CtInvocationImpl][CtCommentImpl]// revert all of them
        [CtCommentImpl]// targets the user chose not to save
        [CtCommentImpl]// save each editor
        [CtCommentImpl]// onCompleted at the end
        cpsExecuteForEachEditor([CtVariableReadImpl]unsavedTargets, [CtLambdaImpl]([CtParameterImpl] saveTarget,[CtParameterImpl] continuation) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]saveTarget.getPath() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// file backed document -- revert it
                [CtVariableReadImpl]saveTarget.revertChanges([CtVariableReadImpl]continuation);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// untitled document -- just close the tab non-interactively
                closeTab([CtVariableReadImpl]saveTarget, [CtLiteralImpl]false, [CtVariableReadImpl]continuation);
            }
        }, [CtVariableReadImpl]onCompleted);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeAllLocalSourceDocs([CtParameterImpl][CtTypeReferenceImpl]java.lang.String caption, [CtParameterImpl][CtTypeReferenceImpl]SourceColumn sourceColumn, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted, [CtParameterImpl]final [CtTypeReferenceImpl]boolean excludeActive) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// save active editor for exclusion (it changes as we close tabs)
        final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget excludeEditor = [CtConditionalImpl]([CtVariableReadImpl]excludeActive) ? [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() : [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]// collect up a list of dirty documents
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> dirtyTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl][CtCommentImpl]// if sourceColumn is not provided, assume we are closing editors for every column
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]sourceColumn == [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl][CtVariableReadImpl]dirtyTargets.addAll([CtInvocationImpl][CtVariableReadImpl]column.getDirtyEditors([CtVariableReadImpl]excludeEditor)));
        else[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]dirtyTargets.addAll([CtInvocationImpl][CtVariableReadImpl]sourceColumn.getDirtyEditors([CtVariableReadImpl]excludeEditor));

        [CtLocalVariableImpl][CtCommentImpl]// create a command used to close all tabs
        final [CtTypeReferenceImpl]com.google.gwt.user.client.Command closeAllTabsCommand = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]sourceColumn == [CtLiteralImpl]null) ? [CtLambdaImpl]() -> [CtInvocationImpl]closeAllTabs([CtVariableReadImpl]excludeActive, [CtLiteralImpl]false) : [CtLambdaImpl]() -> [CtInvocationImpl]closeAllTabs([CtVariableReadImpl]sourceColumn, [CtVariableReadImpl]excludeActive, [CtLiteralImpl]false);
        [CtInvocationImpl]saveEditingTargetsWithPrompt([CtVariableReadImpl]caption, [CtVariableReadImpl]dirtyTargets, [CtInvocationImpl][CtTypeAccessImpl]CommandUtil.join([CtVariableReadImpl]closeAllTabsCommand, [CtVariableReadImpl]onCompleted), [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void consolidateColumns([CtParameterImpl][CtTypeReferenceImpl]int num) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]num >= [CtInvocationImpl][CtFieldReadImpl]columnList_.size()) || [CtBinaryOperatorImpl]([CtVariableReadImpl]num < [CtLiteralImpl]1))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]column.hasDoc()) [CtBlockImpl]{
                [CtInvocationImpl]closeColumn([CtInvocationImpl][CtVariableReadImpl]column.getName());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]num >= [CtInvocationImpl][CtFieldReadImpl]columnList_.size()) || [CtBinaryOperatorImpl]([CtVariableReadImpl]num == [CtLiteralImpl]1))[CtBlockImpl]
                    [CtBreakImpl]break;

            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// if we could not remove empty columns to get to the desired amount, consolidate editors
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]SourceDocument> moveEditors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn mainColumn = [CtInvocationImpl]getByName([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]num < [CtInvocationImpl][CtFieldReadImpl]columnList_.size()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.CPSEditingTargetCommand moveCommand = [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.CPSEditingTargetCommand()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editingTarget, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                }
            };
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]SourceColumn> moveColumns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl]columnList_);
            [CtInvocationImpl][CtVariableReadImpl]moveColumns.remove([CtVariableReadImpl]mainColumn);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int additionalColumnCount = [CtBinaryOperatorImpl][CtVariableReadImpl]num - [CtLiteralImpl]1;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]num > [CtLiteralImpl]1) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]moveColumns.size() != [CtVariableReadImpl]additionalColumnCount))[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]moveColumns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]moveColumns.subList([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]moveColumns.size() - [CtVariableReadImpl]additionalColumnCount));

            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtVariableReadImpl]moveColumns) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editors = [CtInvocationImpl][CtVariableReadImpl]column.getEditors();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtVariableReadImpl]editors) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]server_.getSourceDocument([CtInvocationImpl][CtVariableReadImpl]target.getId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]SourceDocument>()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl]final [CtTypeReferenceImpl]SourceDocument doc) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]mainColumn.addTab([CtVariableReadImpl]doc, [CtTypeAccessImpl]Source.OPEN_INTERACTIVE);
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Document Tab Move Failed", [CtBinaryOperatorImpl][CtLiteralImpl]"Couldn\'t move the tab to this window: \n" + [CtInvocationImpl][CtVariableReadImpl]error.getMessage());
                        }
                    });
                }
                [CtInvocationImpl]closeColumn([CtVariableReadImpl]column, [CtLiteralImpl]true);
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State.createState([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toJsArrayString([CtInvocationImpl]getNames([CtLiteralImpl]false)), [CtInvocationImpl][CtInvocationImpl]getActive().getName());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeColumn([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl]getByName([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]column.getTabCount() > [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtFieldReadImpl]activeColumn_)[CtBlockImpl]
            [CtInvocationImpl]setActive([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME);

        [CtInvocationImpl][CtFieldReadImpl]columnList_.remove([CtVariableReadImpl]column);
        [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State.createState([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toJsArrayString([CtInvocationImpl]getNames([CtLiteralImpl]false)), [CtInvocationImpl][CtInvocationImpl]getActive().getName());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeColumn([CtParameterImpl][CtTypeReferenceImpl]SourceColumn column, [CtParameterImpl][CtTypeReferenceImpl]boolean force) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]column.getTabCount() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]force)[CtBlockImpl]
                [CtReturnImpl]return;
            else [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtInvocationImpl][CtVariableReadImpl]column.getEditors())[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]column.closeDoc([CtInvocationImpl][CtVariableReadImpl]target.getId());

            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtFieldReadImpl]activeColumn_)[CtBlockImpl]
            [CtInvocationImpl]setActive([CtLiteralImpl]"");

        [CtInvocationImpl][CtFieldReadImpl]columnList_.remove([CtVariableReadImpl]column);
        [CtAssignmentImpl][CtFieldWriteImpl]columnState_ = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State.createState([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toJsArrayString([CtInvocationImpl]getNames([CtLiteralImpl]false)), [CtInvocationImpl][CtInvocationImpl]getActive().getName());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void ensureVisible([CtParameterImpl][CtTypeReferenceImpl]boolean newTabPending) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getActive() == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtInvocationImpl]getActive().ensureVisible([CtVariableReadImpl]newTabPending);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void openFile([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file) [CtBlockImpl]{
        [CtInvocationImpl]openFile([CtVariableReadImpl]file, [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.getTextTypeForFile([CtVariableReadImpl]file));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void openFile([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl][CtTypeReferenceImpl]TextFileType fileType) [CtBlockImpl]{
        [CtInvocationImpl]openFile([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget arg) [CtBlockImpl]{
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void openFile([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> executeOnSuccess) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// add this work to the queue
        [CtFieldReadImpl]openFileQueue_.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.OpenFileEntry([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtVariableReadImpl]executeOnSuccess));
        [CtIfImpl][CtCommentImpl]// begin queue processing if it's the only work in the queue
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]openFileQueue_.size() == [CtLiteralImpl]1)[CtBlockImpl]
            [CtInvocationImpl]processOpenFileQueue();

    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void editFile([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]server_.ensureFileExists([CtVariableReadImpl]path, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean success) [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]success) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtVariableReadImpl]path);
                    [CtInvocationImpl]openFile([CtVariableReadImpl]file);
                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void openProjectDocs([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session session, [CtParameterImpl][CtTypeReferenceImpl]boolean mainColumn) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mainColumn && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeColumn_ != [CtInvocationImpl]getByName([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME)))[CtBlockImpl]
            [CtInvocationImpl]setActive([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.MAIN_SOURCE_NAME);

        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString openDocs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.getSessionInfo().getProjectOpenDocs();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]openDocs.length() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// set new tab pending for the duration of the continuation
            [CtFieldReadImpl]activeColumn_.incrementNewTabPending();
            [CtLocalVariableImpl][CtCommentImpl]// create a continuation for opening the source docs
            [CtTypeReferenceImpl]SerializedCommandQueue openCommands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SerializedCommandQueue();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]openDocs.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String doc = [CtInvocationImpl][CtVariableReadImpl]openDocs.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem fsi = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtVariableReadImpl]doc);
                [CtInvocationImpl][CtVariableReadImpl]openCommands.addCommand([CtNewClassImpl]new [CtTypeReferenceImpl]SerializedCommand()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onExecute([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                        [CtInvocationImpl]openFile([CtVariableReadImpl]fsi, [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.getTextTypeForFile([CtVariableReadImpl]fsi), [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget arg) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                            }
                        });
                    }
                });
            }
            [CtInvocationImpl][CtCommentImpl]// decrement newTabPending and select first tab when done
            [CtVariableReadImpl]openCommands.addCommand([CtNewClassImpl]new [CtTypeReferenceImpl]SerializedCommand()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onExecute([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]activeColumn_.decrementNewTabPending();
                    [CtInvocationImpl]onFirstTab();
                    [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                }
            });
            [CtInvocationImpl][CtCommentImpl]// execute the continuation
            [CtVariableReadImpl]openCommands.run();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void fireDocTabsChanged() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.fireDocTabsChanged();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean hasDoc() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]column.hasDoc())[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]true;

        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void vimSetTabIndex([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tabCount = [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getTabCount();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index >= [CtVariableReadImpl]tabCount)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.setPhysicalTabIndex([CtVariableReadImpl]index);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processOpenFileQueue() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// no work to do
        if ([CtInvocationImpl][CtFieldReadImpl]openFileQueue_.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtCommentImpl]// find the first work unit
        final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.OpenFileEntry entry = [CtInvocationImpl][CtFieldReadImpl]openFileQueue_.peek();
        [CtLocalVariableImpl][CtCommentImpl]// define command to advance queue
        final [CtTypeReferenceImpl]com.google.gwt.user.client.Command processNextEntry = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]openFileQueue_.remove();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]openFileQueue_.isEmpty())[CtBlockImpl]
                    [CtInvocationImpl]processOpenFileQueue();

            }
        };
        [CtInvocationImpl]openFile([CtFieldReadImpl][CtVariableReadImpl]entry.file, [CtFieldReadImpl][CtVariableReadImpl]entry.fileType, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]processNextEntry.execute();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]entry.executeOnSuccess != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]entry.executeOnSuccess.execute([CtVariableReadImpl]target);

            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onCancelled() [CtBlockImpl]{
                [CtInvocationImpl][CtSuperAccessImpl]super.onCancelled();
                [CtInvocationImpl][CtVariableReadImpl]processNextEntry.execute();
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onFailure([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtInvocationImpl][CtVariableReadImpl]error.getUserMessage();
                [CtLocalVariableImpl][CtCommentImpl]// see if a special message was provided
                [CtTypeReferenceImpl]com.google.gwt.json.client.JSONValue errValue = [CtInvocationImpl][CtVariableReadImpl]error.getClientInfo();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]errValue != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.json.client.JSONString errMsg = [CtInvocationImpl][CtVariableReadImpl]errValue.isString();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]errMsg != [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]message = [CtInvocationImpl][CtVariableReadImpl]errMsg.stringValue();

                }
                [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showMessage([CtTypeAccessImpl]GlobalDisplay.MSG_ERROR, [CtLiteralImpl]"Error while opening file", [CtVariableReadImpl]message);
                [CtInvocationImpl][CtVariableReadImpl]processNextEntry.execute();
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void openFile([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtInvocationImpl]openFile([CtVariableReadImpl]file, [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.getTextTypeForFile([CtVariableReadImpl]file), [CtVariableReadImpl]resultCallback);
    }

    [CtMethodImpl][CtCommentImpl]// top-level wrapper for opening files. takes care of:
    [CtCommentImpl]// - making sure the view is visible
    [CtCommentImpl]// - checking whether it is already open and re-selecting its tab
    [CtCommentImpl]// - prohibit opening very large files (>500KB)
    [CtCommentImpl]// - confirmation of opening large files (>100KB)
    [CtCommentImpl]// - finally, actually opening the file from the server
    [CtCommentImpl]// via the call to the lower level openFile method
    public [CtTypeReferenceImpl]void openFile([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.ensureVisible([CtLiteralImpl]true);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fileType.isRNotebook()) [CtBlockImpl]{
            [CtInvocationImpl]openNotebook([CtVariableReadImpl]file, [CtVariableReadImpl]resultCallback);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]file == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]newDoc([CtVariableReadImpl]fileType, [CtVariableReadImpl]resultCallback);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtInvocationImpl]openFileAlreadyOpen([CtVariableReadImpl]file, [CtVariableReadImpl]resultCallback))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editingTargetSource_.getEditingTarget([CtVariableReadImpl]fileType);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]file.getLength() > [CtInvocationImpl][CtVariableReadImpl]target.getFileSizeLimit()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]resultCallback.onCancelled();

            [CtInvocationImpl]showFileTooLargeWarning([CtVariableReadImpl]file, [CtInvocationImpl][CtVariableReadImpl]target.getFileSizeLimit());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]file.getLength() > [CtInvocationImpl][CtVariableReadImpl]target.getLargeFileSize()) [CtBlockImpl]{
            [CtInvocationImpl]confirmOpenLargeFile([CtVariableReadImpl]file, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.Operation()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtInvocationImpl]openFileFromServer([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtVariableReadImpl]resultCallback);
                }
            }, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.Operation()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// user (wisely) cancelled
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                        [CtInvocationImpl][CtVariableReadImpl]resultCallback.onCancelled();

                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl]openFileFromServer([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtVariableReadImpl]resultCallback);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void openNotebook([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem rmdFile, [CtParameterImpl]final [CtTypeReferenceImpl]SourceDocumentResult doc, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]doc.getDocPath())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// this happens if we created the R Markdown file, or if the R Markdown
            [CtCommentImpl]// file on disk matched the one inside the notebook
            openFileFromServer([CtVariableReadImpl]rmdFile, [CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtVariableReadImpl]resultCallback);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]doc.getDocId())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// this happens when we have to open an untitled buffer for the the
            [CtCommentImpl]// notebook (usually because the of a conflict between the Rmd on disk
            [CtCommentImpl]// and the one in the .nb.html file)
            [CtFieldReadImpl]server_.getSourceDocument([CtInvocationImpl][CtVariableReadImpl]doc.getDocId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]SourceDocument>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]SourceDocument doc) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// create the editor
                    [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtInvocationImpl]getActive().addTab([CtVariableReadImpl]doc, [CtTypeAccessImpl]Source.OPEN_INTERACTIVE);
                    [CtIfImpl][CtCommentImpl]// show a warning bar
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]target instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (target)).showWarningMessage([CtBinaryOperatorImpl][CtLiteralImpl]"This notebook has the same name as an R Markdown " + [CtLiteralImpl]"file, but doesn't match it.");
                    }
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onSuccess([CtVariableReadImpl]target);
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Notebook Open Failed", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"This notebook could not be opened. " + [CtLiteralImpl]"If the error persists, try removing the ") + [CtLiteralImpl]"accompanying R Markdown file. \n\n") + [CtInvocationImpl][CtVariableReadImpl]error.getMessage());
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onFailure([CtVariableReadImpl]error);
                }
            });
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void beforeShow() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl][CtVariableReadImpl]column.onBeforeShow());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void beforeShow([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column = [CtInvocationImpl]getByName([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]column == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Debug.logWarning([CtBinaryOperatorImpl][CtLiteralImpl]"WARNING: Unknown column " + [CtVariableReadImpl]name);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]column.onBeforeShow();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void inEditorForId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onEditorLocated) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor = [CtInvocationImpl]findEditor([CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]editor != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]onEditorLocated.execute([CtVariableReadImpl]editor);

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void inEditorForPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onEditorLocated) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor = [CtInvocationImpl]findEditorByPath([CtVariableReadImpl]path);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]editor != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]onEditorLocated.execute([CtVariableReadImpl]editor);

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void withTarget([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget> command) [CtBlockImpl]{
        [CtInvocationImpl]withTarget([CtVariableReadImpl]id, [CtVariableReadImpl]command, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void withTarget([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget> command, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onFailure) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtVariableReadImpl]id)) ? [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() : [CtInvocationImpl]findEditor([CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]onFailure != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]onFailure.execute();

            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]target instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]onFailure != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]onFailure.execute();

            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]command.execute([CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (target)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> getDynamicCommands() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dynamicCommands_;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void getEditorContext([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay docDisplay) [CtBlockImpl]{
        [CtInvocationImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.getEditorContext([CtVariableReadImpl]id, [CtVariableReadImpl]path, [CtVariableReadImpl]docDisplay, [CtFieldReadImpl]server_);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void getEditorContext([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay docDisplay, [CtParameterImpl][CtTypeReferenceImpl]SourceServerOperations server) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor editor = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor) (docDisplay));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Selection selection = [CtInvocationImpl][CtVariableReadImpl]editor.getNativeSelection();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range[] ranges = [CtInvocationImpl][CtVariableReadImpl]selection.getAllRanges();
        [CtForEachImpl][CtCommentImpl]// clamp ranges to document boundaries
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range range : [CtVariableReadImpl]ranges) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position start = [CtInvocationImpl][CtVariableReadImpl]range.getStart();
            [CtInvocationImpl][CtVariableReadImpl]start.setRow([CtInvocationImpl][CtTypeAccessImpl]MathUtil.clamp([CtInvocationImpl][CtVariableReadImpl]start.getRow(), [CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]editor.getRowCount()));
            [CtInvocationImpl][CtVariableReadImpl]start.setColumn([CtInvocationImpl][CtTypeAccessImpl]MathUtil.clamp([CtInvocationImpl][CtVariableReadImpl]start.getColumn(), [CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]editor.getLine([CtInvocationImpl][CtVariableReadImpl]start.getRow()).length()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position end = [CtInvocationImpl][CtVariableReadImpl]range.getEnd();
            [CtInvocationImpl][CtVariableReadImpl]end.setRow([CtInvocationImpl][CtTypeAccessImpl]MathUtil.clamp([CtInvocationImpl][CtVariableReadImpl]end.getRow(), [CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]editor.getRowCount()));
            [CtInvocationImpl][CtVariableReadImpl]end.setColumn([CtInvocationImpl][CtTypeAccessImpl]MathUtil.clamp([CtInvocationImpl][CtVariableReadImpl]end.getColumn(), [CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]editor.getLine([CtInvocationImpl][CtVariableReadImpl]end.getRow()).length()));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArray<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.GetEditorContextEvent.DocumentSelection> docSelections = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.JavaScriptObject.createArray().cast();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range range : [CtVariableReadImpl]ranges) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]docSelections.push([CtInvocationImpl][CtTypeAccessImpl]GetEditorContextEvent.DocumentSelection.create([CtVariableReadImpl]range, [CtInvocationImpl][CtVariableReadImpl]editor.getTextForRange([CtVariableReadImpl]range)));
        }
        [CtAssignmentImpl][CtVariableWriteImpl]id = [CtInvocationImpl][CtTypeAccessImpl]StringUtil.notNull([CtVariableReadImpl]id);
        [CtAssignmentImpl][CtVariableWriteImpl]path = [CtInvocationImpl][CtTypeAccessImpl]StringUtil.notNull([CtVariableReadImpl]path);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.GetEditorContextEvent.SelectionData data = [CtInvocationImpl][CtTypeAccessImpl]GetEditorContextEvent.SelectionData.create([CtVariableReadImpl]id, [CtVariableReadImpl]path, [CtInvocationImpl][CtVariableReadImpl]editor.getCode(), [CtVariableReadImpl]docSelections);
        [CtInvocationImpl][CtVariableReadImpl]server.getEditorContextCompleted([CtVariableReadImpl]data, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void initDynamicCommands() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]dynamicCommands_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.saveSourceDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.reopenSourceDocWithEncoding());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.saveSourceDocAs());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.saveSourceDocWithEncoding());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.printSourceDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileLog());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileDiff());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileRevert());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeCodeWithoutFocus());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeAllCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeToCurrentLine());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeFromCurrentLine());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeCurrentFunction());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeCurrentSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeLastCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.insertChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.insertSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeSetupChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executePreviousChunks());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeSubsequentChunks());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeCurrentChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.executeNextChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.previewJS());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.previewSql());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.sourceActiveDocument());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.sourceActiveDocumentWithEcho());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.knitDocument());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.toggleRmdVisualMode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.enableProsemirrorDevTools());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.previewHTML());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.compilePDF());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.compileNotebook());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.synctexSearch());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.popoutDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.returnDocToMain());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.findReplace());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.findNext());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.findPrevious());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.findFromSelection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.replaceAndFind());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.extractFunction());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.extractLocalVariable());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.commentUncomment());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.reindent());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.reflowComment());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.jumpTo());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.jumpToMatching());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.goToHelp());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.goToDefinition());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.setWorkingDirToActiveDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.debugDumpContents());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.debugImportDump());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.goToLine());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.checkSpelling());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.wordCount());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.codeCompletion());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.findUsages());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.debugBreakpoint());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.vcsViewOnGitHub());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.vcsBlameOnGitHub());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.editRmdFormatOptions());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.reformatCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.showDiagnosticsActiveDocument());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.renameInScope());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.insertRoxygenSkeleton());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.expandSelection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.shrinkSelection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.toggleDocumentOutline());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.knitWithParameters());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.clearKnitrCache());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.goToNextSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.goToPrevSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.goToNextChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.goToPrevChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.profileCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.profileCodeWithoutFocus());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.saveProfileAs());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.restartRClearOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.restartRRunAllChunks());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.notebookCollapseAllOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.notebookExpandAllOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.notebookClearOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.notebookClearAllOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.notebookToggleExpansion());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.sendToTerminal());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.openNewTerminalAtEditorLocation());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.sendFilenameToTerminal());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.renameSourceDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.sourceAsLauncherJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.sourceAsJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.runSelectionAsJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.runSelectionAsLauncherJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtFieldReadImpl]commands_.toggleSoftWrapMode());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand command : [CtFieldReadImpl]dynamicCommands_) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]command.setVisible([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]command.setEnabled([CtLiteralImpl]false);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void initVimCommands() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.save([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.selectTabIndex([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.selectNextTab([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.selectPreviousTab([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.closeActiveTab([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.closeAllTabs([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.createNewDocument([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.saveAndCloseActiveTab([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.readFile([CtThisAccessImpl]this, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.defaultEncoding().getValue());
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.runRScript([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.reflowText([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.showVimHelp([CtInvocationImpl][CtTypeAccessImpl]RStudioGinjector.INSTANCE.getShortcutViewer());
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.showHelpAtCursor([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.reindent([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.expandShrinkSelection([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.openNextFile([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.openPreviousFile([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]vimCommands_.addStarRegister();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceAppCommand getSourceCommand([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand command, [CtParameterImpl][CtTypeReferenceImpl]SourceColumn column) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// check if we've already create a SourceAppCommand for this command
        [CtTypeReferenceImpl]java.lang.String key = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]command.getId() + [CtInvocationImpl][CtVariableReadImpl]column.getName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sourceAppCommands_.get([CtVariableReadImpl]key) != [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]sourceAppCommands_.get([CtVariableReadImpl]key);

        [CtLocalVariableImpl][CtCommentImpl]// if not found, create it
        [CtTypeReferenceImpl]SourceAppCommand sourceCommand = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SourceAppCommand([CtVariableReadImpl]command, [CtInvocationImpl][CtVariableReadImpl]column.getName(), [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]sourceAppCommands_.put([CtVariableReadImpl]key, [CtVariableReadImpl]sourceCommand);
        [CtReturnImpl]return [CtVariableReadImpl]sourceCommand;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openNotebook([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem rnbFile, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// construct path to .Rmd
        final [CtTypeReferenceImpl]java.lang.String rnbPath = [CtInvocationImpl][CtVariableReadImpl]rnbFile.getPath();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String rmdPath = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.common.FilePathUtils.filePathSansExtension([CtVariableReadImpl]rnbPath) + [CtLiteralImpl]".Rmd";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem rmdFile = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtVariableReadImpl]rmdPath);
        [CtIfImpl][CtCommentImpl]// if we already have associated .Rmd file open, then just edit it
        [CtCommentImpl]// TODO: should we perform conflict resolution here as well?
        if ([CtInvocationImpl]openFileAlreadyOpen([CtVariableReadImpl]rmdFile, [CtVariableReadImpl]resultCallback))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtCommentImpl]// ask the server to extract the .Rmd, then open that
        [CtTypeReferenceImpl]com.google.gwt.user.client.Command extractRmdCommand = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]server_.extractRmdFromNotebook([CtVariableReadImpl]rnbPath, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]SourceDocumentResult>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]SourceDocumentResult doc) [CtBlockImpl]{
                        [CtInvocationImpl]openNotebook([CtVariableReadImpl]rmdFile, [CtVariableReadImpl]doc, [CtVariableReadImpl]resultCallback);
                    }

                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Notebook Open Failed", [CtBinaryOperatorImpl][CtLiteralImpl]"This notebook could not be opened. \n\n" + [CtInvocationImpl][CtVariableReadImpl]error.getMessage());
                        [CtInvocationImpl][CtVariableReadImpl]resultCallback.onFailure([CtVariableReadImpl]error);
                    }
                });
            }
        };
        [CtInvocationImpl][CtFieldReadImpl]dependencyManager_.withRMarkdown([CtLiteralImpl]"R Notebook", [CtLiteralImpl]"Using R Notebooks", [CtVariableReadImpl]extractRmdCommand);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openFileFromServer([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command dismissProgress = [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showProgress([CtLiteralImpl]"Opening file...");
        [CtInvocationImpl][CtFieldReadImpl]server_.openDocument([CtInvocationImpl][CtVariableReadImpl]file.getPath(), [CtInvocationImpl][CtVariableReadImpl]fileType.getTypeId(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.defaultEncoding().getValue(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]SourceDocument>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]dismissProgress.execute();
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().remove([CtInvocationImpl][CtVariableReadImpl]file.getPath());
                [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onFailure([CtVariableReadImpl]error);

            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]SourceDocument document) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// apply (dynamic) doc property defaults
                [CtTypeAccessImpl]SourceColumn.applyDocPropertyDefaults([CtVariableReadImpl]document, [CtLiteralImpl]false, [CtFieldReadImpl]userPrefs_);
                [CtIfImpl][CtCommentImpl]// if we are opening for a source navigation then we
                [CtCommentImpl]// need to force Rmds into source mode
                if ([CtFieldReadImpl]openingForSourceNavigation_) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]document.getProperties().setString([CtTypeAccessImpl]TextEditingTarget.RMD_VISUAL_MODE, [CtTypeAccessImpl]DocUpdateSentinel.PROPERTY_FALSE);
                }
                [CtInvocationImpl][CtVariableReadImpl]dismissProgress.execute();
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().add([CtInvocationImpl][CtVariableReadImpl]document.getPath());
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtInvocationImpl]getActive().addTab([CtVariableReadImpl]document, [CtTypeAccessImpl]Source.OPEN_INTERACTIVE);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onSuccess([CtVariableReadImpl]target);

            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean openFileAlreadyOpen([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtForImpl][CtCommentImpl]// check to see if any local editors have the file open
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]column.getEditors().size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]column.getEditors().get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String thisPath = [CtInvocationImpl][CtVariableReadImpl]target.getPath();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]thisPath != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]thisPath.equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]file.getPath())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]column.selectTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget());
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().add([CtVariableReadImpl]thisPath);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                        [CtInvocationImpl][CtVariableReadImpl]resultCallback.onSuccess([CtVariableReadImpl]target);

                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void showFileTooLargeWarning([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl][CtTypeReferenceImpl]long sizeLimit) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The file '" + [CtInvocationImpl][CtVariableReadImpl]file.getName()) + [CtLiteralImpl]"' is too ");
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtLiteralImpl]"large to open in the source editor (the file is ");
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]StringUtil.formatFileSize([CtInvocationImpl][CtVariableReadImpl]file.getLength()) + [CtLiteralImpl]" and the ");
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtLiteralImpl]"maximum file size is ");
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]StringUtil.formatFileSize([CtVariableReadImpl]sizeLimit) + [CtLiteralImpl]")");
        [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showMessage([CtTypeAccessImpl]GlobalDisplay.MSG_WARNING, [CtLiteralImpl]"Selected File Too Large", [CtInvocationImpl][CtVariableReadImpl]msg.toString());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void confirmOpenLargeFile([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.Operation openOperation, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.Operation noOperation) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The source file '" + [CtInvocationImpl][CtVariableReadImpl]file.getName()) + [CtLiteralImpl]"' is large (");
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]StringUtil.formatFileSize([CtInvocationImpl][CtVariableReadImpl]file.getLength()) + [CtLiteralImpl]") ");
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtLiteralImpl]"and may take some time to open. ");
        [CtInvocationImpl][CtVariableReadImpl]msg.append([CtLiteralImpl]"Are you sure you want to continue opening it?");
        [CtInvocationImpl][CtCommentImpl]// Don't include cancel
        [CtFieldReadImpl]globalDisplay_.showYesNoMessage([CtTypeAccessImpl]GlobalDisplay.MSG_WARNING, [CtLiteralImpl]"Confirm Open", [CtInvocationImpl][CtVariableReadImpl]msg.toString(), [CtLiteralImpl]false, [CtVariableReadImpl]openOperation, [CtVariableReadImpl]noOperation, [CtLiteralImpl]false);[CtCommentImpl]// 'No' is default

    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveEditingTargetsWithPrompt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editingTargets, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCancelled) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// execute on completed right away if the list is empty
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]editingTargets.size() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]onCompleted.execute();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]editingTargets.size() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]editingTargets.get([CtLiteralImpl]0).saveWithPrompt([CtVariableReadImpl]onCompleted, [CtVariableReadImpl]onCancelled);
        } else [CtBlockImpl][CtCommentImpl]// otherwise use the multi save changes dialog
        {
            [CtLocalVariableImpl][CtCommentImpl]// convert to UnsavedChangesTarget collection
            [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> unsavedTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]editingTargets);
            [CtInvocationImpl][CtCommentImpl]// show dialog
            showUnsavedChangesDialog([CtVariableReadImpl]title, [CtVariableReadImpl]unsavedTargets, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result result) [CtBlockImpl]{
                    [CtInvocationImpl]saveChanges([CtInvocationImpl][CtVariableReadImpl]result.getSaveTargets(), [CtVariableReadImpl]onCompleted);
                }
            }, [CtVariableReadImpl]onCancelled);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> getUnsavedChanges([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ids) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> targets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]columnList_.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl][CtVariableReadImpl]targets.addAll([CtInvocationImpl][CtVariableReadImpl]column.getUnsavedEditors([CtVariableReadImpl]type, [CtVariableReadImpl]ids)));
        [CtReturnImpl]return [CtVariableReadImpl]targets;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void saveChanges([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> targets, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// convert back to editing targets
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> saveTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget target : [CtVariableReadImpl]targets) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget saveTarget = [CtInvocationImpl]findEditor([CtInvocationImpl][CtVariableReadImpl]target.getId());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]saveTarget != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]saveTargets.add([CtVariableReadImpl]saveTarget);

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.CPSEditingTargetCommand saveCommand = [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.CPSEditingTargetCommand()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget saveTarget, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]saveTarget.save([CtVariableReadImpl]continuation);
            }
        };
        [CtInvocationImpl][CtCommentImpl]// execute the save
        [CtCommentImpl]// targets the user chose to save
        [CtCommentImpl]// save each editor
        [CtCommentImpl]// onCompleted at the end
        cpsExecuteForEachEditor([CtVariableReadImpl]saveTargets, [CtVariableReadImpl]saveCommand, [CtVariableReadImpl]onCompleted);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void pasteFileContentsAtCursor([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String encoding) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeColumn_ == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget activeEditor = [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]activeEditor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget target = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor));
            [CtInvocationImpl][CtFieldReadImpl]server_.getFileContents([CtVariableReadImpl]path, [CtVariableReadImpl]encoding, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]java.lang.String content) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]target.insertCode([CtVariableReadImpl]content, [CtLiteralImpl]false);
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
                }
            });
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void pasteRCodeExecutionResult([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String code) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]server_.executeRCode([CtVariableReadImpl]code, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]java.lang.String output) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
                    [CtInvocationImpl][CtVariableReadImpl]editor.insertCode([CtVariableReadImpl]output, [CtLiteralImpl]false);
                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void reflowText() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
            [CtInvocationImpl][CtVariableReadImpl]editor.reflowText();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void reindent() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]editor.getDocDisplay().reindent();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveActiveSourceDoc() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget target = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
            [CtInvocationImpl][CtVariableReadImpl]target.save();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveAndCloseActiveSourceDoc() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget target = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
            [CtInvocationImpl][CtVariableReadImpl]target.save([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtInvocationImpl]onCloseSourceDoc();
                }
            });
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void revertActiveDocument() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasActiveEditor())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().getPath() != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().revertChanges([CtLiteralImpl]null);

        [CtInvocationImpl][CtCommentImpl]// Ensure that the document is in view
        [CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor().ensureCursorVisible();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void showHelpAtCursor() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasActiveEditor() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeColumn_.getActiveEditor() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) ([CtFieldReadImpl]activeColumn_.getActiveEditor()));
            [CtInvocationImpl][CtVariableReadImpl]editor.showHelpAtCursor();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Execute the given command for each editor, using continuation-passing
     * style. When executed, the CPSEditingTargetCommand needs to execute its
     * own Command parameter to continue the iteration.
     *
     * @param command
     * 		The command to run on each EditingTarget
     */
    private [CtTypeReferenceImpl]void cpsExecuteForEachEditor([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editors, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.CPSEditingTargetCommand command, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command completedCommand) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SerializedCommandQueue queue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SerializedCommandQueue();
        [CtForEachImpl][CtCommentImpl]// Clone editors_, since the original may be mutated during iteration
        for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor : [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]editors)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]queue.addCommand([CtNewClassImpl]new [CtTypeReferenceImpl]SerializedCommand()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onExecute([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]command.execute([CtVariableReadImpl]editor, [CtVariableReadImpl]continuation);
                }
            });
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]completedCommand != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]queue.addCommand([CtNewClassImpl]new [CtTypeReferenceImpl]SerializedCommand()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]void onExecute([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]completedCommand.execute();
                    [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                }
            });
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumn getByName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]StringUtil.equals([CtInvocationImpl][CtVariableReadImpl]column.getName(), [CtVariableReadImpl]name))[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]column;

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean contains([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SourceColumn column : [CtFieldReadImpl]columnList_) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]StringUtil.equals([CtInvocationImpl][CtVariableReadImpl]column.getName(), [CtVariableReadImpl]name))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]true;

        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void cpsExecuteForEachEditor([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editors, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.CPSEditingTargetCommand command) [CtBlockImpl]{
        [CtInvocationImpl]cpsExecuteForEachEditor([CtVariableReadImpl]editors, [CtVariableReadImpl]command, [CtLiteralImpl]null);
    }

    [CtClassImpl]private static class OpenFileEntry {
        [CtConstructorImpl]public OpenFileEntry([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem fileIn, [CtParameterImpl][CtTypeReferenceImpl]TextFileType fileTypeIn, [CtParameterImpl][CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> executeIn) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]file = [CtVariableReadImpl]fileIn;
            [CtAssignmentImpl][CtFieldWriteImpl]fileType = [CtVariableReadImpl]fileTypeIn;
            [CtAssignmentImpl][CtFieldWriteImpl]executeOnSuccess = [CtVariableReadImpl]executeIn;
        }

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file;

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.TextFileType fileType;

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> executeOnSuccess;
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.State columnState_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumn activeColumn_;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean openingForSourceNavigation_ = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean docsRestored_ = [CtLiteralImpl]false;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Queue<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.OpenFileEntry> openFileQueue_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]SourceColumn> columnList_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> dynamicCommands_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]SourceAppCommand> sourceAppCommands_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceVimCommands vimCommands_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.commands.Commands commands_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.application.events.EventBus events_;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.FileMRUList> pMruList_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceWindowManager windowManager_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session session_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.common.synctex.Synctex synctex_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserPrefs userPrefs_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserState userState_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.common.GlobalDisplay globalDisplay_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetRMarkdownHelper rmarkdown_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTargetSource editingTargetSource_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.FileTypeRegistry fileTypeRegistry_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceServerOperations server_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.common.dependencies.DependencyManager dependencyManager_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceNavigationHistory sourceNavigationHistory_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SourceNavigationHistory([CtLiteralImpl]30);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String COLUMN_PREFIX = [CtLiteralImpl]"Source";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String MAIN_SOURCE_NAME = [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceColumnManager.COLUMN_PREFIX;
}
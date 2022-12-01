[CompilationUnitImpl][CtCommentImpl]/* Source.java

Copyright (C) 2009-20 by RStudio, PBC

Unless you have received this program directly from RStudio pursuant
to the terms of a commercial license agreement with RStudio, then
this program is licensed to you under the terms of version 3 of the
GNU Affero General Public License. This program is distributed WITHOUT
ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */
[CtPackageDeclarationImpl]package org.rstudio.studio.client.workbench.views.source;
[CtUnresolvedImport]import org.rstudio.core.client.widget.ProgressOperationWithInput;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.DocTabDragParams;
[CtUnresolvedImport]import com.google.gwt.json.client.JSONValue;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetPresentationHelper;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.MainWindowObject;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.commands.Commands;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.UnsavedChangesTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.EditingTargetSource;
[CtUnresolvedImport]import org.rstudio.core.client.events.*;
[CtUnresolvedImport]import org.rstudio.studio.client.events.GetEditorContextEvent.DocumentSelection;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdFrontMatter;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.SessionInfo;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdTemplateData;
[CtUnresolvedImport]import com.google.gwt.event.shared.HandlerRegistration;
[CtUnresolvedImport]import org.rstudio.studio.client.application.AriaLiveService;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdOutputFormat;
[CtUnresolvedImport]import org.rstudio.studio.client.server.VoidServerRequestCallback;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.explorer.events.OpenObjectExplorerEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.snippets.model.SnippetsChangedEvent;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.CloseEvent;
[CtUnresolvedImport]import org.rstudio.core.client.dom.WindowEx;
[CtUnresolvedImport]import org.rstudio.studio.client.common.dependencies.DependencyManager;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.console.shell.editor.InputEditorDisplay;
[CtUnresolvedImport]import org.rstudio.studio.client.application.ApplicationAction;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.ConsoleEditorProvider;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.WorkbenchContext;
[CtUnresolvedImport]import com.google.gwt.user.client.Event.NativePreviewHandler;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.events.FileTypeChangedHandler;
[CtUnresolvedImport]import com.google.gwt.core.client.Scheduler.ScheduledCommand;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.FileType;
[CtUnresolvedImport]import org.rstudio.studio.client.common.GlobalProgressDelayer;
[CtUnresolvedImport]import com.google.gwt.user.client.Event.NativePreviewEvent;
[CtUnresolvedImport]import com.google.gwt.user.client.ui.*;
[CtUnresolvedImport]import org.rstudio.studio.client.application.events.EventBus;
[CtUnresolvedImport]import org.rstudio.core.client.command.Handler;
[CtUnresolvedImport]import com.google.gwt.event.dom.client.KeyCodes;
[CtUnresolvedImport]import org.rstudio.studio.client.application.events.AriaLiveStatusEvent.Severity;
[CtUnresolvedImport]import org.rstudio.studio.client.events.ReplaceRangesEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.profiler.OpenProfileEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.common.FileDialogs;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.ObjectExplorerFileType;
[CtUnresolvedImport]import com.google.inject.Singleton;
[CtUnresolvedImport]import com.google.gwt.event.dom.client.ChangeEvent;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.ValueChangeHandler;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.ValueChangeEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.application.events.AriaLiveStatusEvent.Timing;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.Session;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.SelectionEvent;
[CtUnresolvedImport]import org.rstudio.core.client.command.KeyCombination;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.HasSelectionHandlers;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.SourcePosition;
[CtUnresolvedImport]import com.google.gwt.event.dom.client.FocusEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.UnsavedChangesItem;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.prefs.model.UserState;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.SelectionHandler;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.events.SourceOnSaveChangedEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.application.events.CrossWindowEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.files.model.DirectoryListing;
[CtUnresolvedImport]import org.rstudio.studio.client.common.GlobalDisplay;
[CtUnresolvedImport]import com.google.gwt.user.client.Window;
[CtUnresolvedImport]import org.rstudio.core.client.widget.Operation;
[CtUnresolvedImport]import org.rstudio.studio.client.events.SetSelectionRangesEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.SourceServerOperations;
[CtImportImpl]import java.util.Queue;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.events.*;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog;
[CtUnresolvedImport]import org.rstudio.core.client.theme.DocTabSelectionEvent;
[CtUnresolvedImport]import org.rstudio.core.client.command.CommandBinder;
[CtUnresolvedImport]import org.rstudio.studio.client.server.ServerRequestCallback;
[CtUnresolvedImport]import org.rstudio.studio.client.server.model.RequestDocumentSaveEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.FileMRUList;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.SourceDocument;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.EditingTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.explorer.model.ObjectExplorerHandle;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.SourceNavigation;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.CloseHandler;
[CtUnresolvedImport]import org.rstudio.studio.client.rmarkdown.model.RmdChosenTemplate;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.data.DataEditingTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor;
[CtUnresolvedImport]import org.rstudio.core.client.*;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.events.ZoomPaneEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.DataItem;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.SourceWindowManager.NavigationResult;
[CtUnresolvedImport]import com.google.gwt.user.client.Timer;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.RdShellResult;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.explorer.ObjectExplorerEditingTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range;
[CtUnresolvedImport]import org.rstudio.core.client.command.KeySequence;
[CtUnresolvedImport]import org.rstudio.studio.client.common.satellite.Satellite;
[CtUnresolvedImport]import com.google.inject.Inject;
[CtUnresolvedImport]import org.rstudio.studio.client.common.SimpleRequestCallback;
[CtUnresolvedImport]import com.google.gwt.user.client.Event;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.helper.IntStateValue;
[CtUnresolvedImport]import org.rstudio.studio.client.common.rnw.RnwWeave;
[CtUnresolvedImport]import org.rstudio.core.client.widget.ProgressIndicator;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ace.AceEditorNative;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.profiler.model.ProfilerContents;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.prefs.model.UserPrefs;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.FileIcon;
[CtUnresolvedImport]import com.google.gwt.user.client.Command;
[CtUnresolvedImport]import org.rstudio.studio.client.common.FilePathUtils;
[CtUnresolvedImport]import org.rstudio.studio.client.common.synctex.Synctex;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.events.SourceOnSaveChangedHandler;
[CtUnresolvedImport]import org.rstudio.studio.client.events.GetEditorContextEvent;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.rstudio.core.client.js.JsObject;
[CtImportImpl]import java.util.LinkedList;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.environment.events.DebugModeChangedEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.application.ApplicationUtils;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.EditableFileType;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Selection;
[CtUnresolvedImport]import com.google.gwt.json.client.JSONString;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.ContentItem;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.SessionUtils;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.FileTypeRegistry;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.NewShinyWebApplication.Result;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.events.OpenPresentationSourceFileHandler;
[CtUnresolvedImport]import com.google.gwt.event.logical.shared.HasBeforeSelectionHandlers;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetRMarkdownHelper;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.model.NavigationMethods;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.output.find.events.FindInFilesEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.events.OpenPresentationSourceFileEvent;
[CtUnresolvedImport]import org.rstudio.core.client.files.FileSystemItem;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position;
[CtUnresolvedImport]import org.rstudio.studio.client.application.Desktop;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.RemoteFileSystemContext;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRMarkdownDialog;
[CtUnresolvedImport]import org.rstudio.core.client.command.ShortcutManager;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.TextFileType;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.SourceNavigationHistory;
[CtUnresolvedImport]import com.google.gwt.core.client.GWT;
[CtUnresolvedImport]import com.google.gwt.event.dom.client.ChangeHandler;
[CtUnresolvedImport]import org.rstudio.studio.client.server.model.RequestDocumentCloseEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.events.FileTypeChangedEvent;
[CtUnresolvedImport]import org.rstudio.core.client.command.AppCommand;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.model.ClientState;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.snippets.SnippetHelper;
[CtUnresolvedImport]import org.rstudio.core.client.js.JsUtil;
[CtUnresolvedImport]import org.rstudio.core.client.command.KeyboardShortcut;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.events.OpenSourceFileHandler;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRdDialog;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.events.NewWorkingCopyEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay;
[CtUnresolvedImport]import com.google.gwt.core.client.JavaScriptObject;
[CtUnresolvedImport]import org.rstudio.core.client.widget.OperationWithInput;
[CtUnresolvedImport]import org.rstudio.studio.client.common.rnw.RnwWeaveRegistry;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.codesearch.model.SearchPathFunctionDefinition;
[CtUnresolvedImport]import org.rstudio.studio.client.common.synctex.events.SynctexStatusChangedEvent;
[CtUnresolvedImport]import com.google.gwt.core.client.Scheduler;
[CtUnresolvedImport]import org.rstudio.studio.client.workbench.views.source.model.SourceDocumentResult;
[CtUnresolvedImport]import com.google.gwt.core.client.JsArray;
[CtUnresolvedImport]import com.google.inject.Provider;
[CtUnresolvedImport]import org.rstudio.studio.client.common.filetypes.events.OpenSourceFileEvent;
[CtUnresolvedImport]import org.rstudio.studio.client.events.ReplaceRangesEvent.ReplacementData;
[CtUnresolvedImport]import org.rstudio.studio.client.RStudioGinjector;
[CtUnresolvedImport]import com.google.gwt.core.client.JsArrayString;
[CtUnresolvedImport]import org.rstudio.studio.client.server.ServerError;
[CtClassImpl][CtAnnotationImpl]@com.google.inject.Singleton
public class Source implements [CtTypeReferenceImpl]InsertSourceHandler , [CtTypeReferenceImpl]IsWidget , [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.events.OpenSourceFileHandler , [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.events.OpenPresentationSourceFileHandler , [CtTypeReferenceImpl]TabClosingHandler , [CtTypeReferenceImpl]TabCloseHandler , [CtTypeReferenceImpl]TabReorderHandler , [CtTypeReferenceImpl]com.google.gwt.event.logical.shared.SelectionHandler<[CtTypeReferenceImpl]java.lang.Integer> , [CtTypeReferenceImpl]TabClosedHandler , [CtTypeReferenceImpl]FileEditHandler , [CtTypeReferenceImpl]ShowContentHandler , [CtTypeReferenceImpl]ShowDataHandler , [CtTypeReferenceImpl]CodeBrowserNavigationHandler , [CtTypeReferenceImpl]CodeBrowserFinishedHandler , [CtTypeReferenceImpl][CtTypeReferenceImpl]CodeBrowserHighlightEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]SourceExtendedTypeDetectedEvent.Handler , [CtTypeReferenceImpl]BeforeShowHandler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.snippets.model.SnippetsChangedEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]PopoutDocEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]DocWindowChangedEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]DocTabDragInitiatedEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]PopoutDocInitiatedEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.environment.events.DebugModeChangedEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.profiler.OpenProfileEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.explorer.events.OpenObjectExplorerEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.ReplaceRangesEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.SetSelectionRangesEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.GetEditorContextEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.model.RequestDocumentSaveEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.model.RequestDocumentCloseEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]EditPresentationSourceEvent.Handler , [CtTypeReferenceImpl][CtTypeReferenceImpl]NewDocumentWithCodeEvent.Handler {
    [CtInterfaceImpl]interface Binder extends [CtTypeReferenceImpl]org.rstudio.core.client.command.CommandBinder<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.commands.Commands, [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source> {}

    [CtInterfaceImpl]public interface Display extends [CtTypeReferenceImpl]IsWidget , [CtTypeReferenceImpl]HasTabClosingHandlers , [CtTypeReferenceImpl]HasTabCloseHandlers , [CtTypeReferenceImpl]HasTabClosedHandlers , [CtTypeReferenceImpl]HasTabReorderHandlers , [CtTypeReferenceImpl]com.google.gwt.event.logical.shared.HasBeforeSelectionHandlers<[CtTypeReferenceImpl]java.lang.Integer> , [CtTypeReferenceImpl]com.google.gwt.event.logical.shared.HasSelectionHandlers<[CtTypeReferenceImpl]java.lang.Integer> {
        [CtMethodImpl][CtTypeReferenceImpl]void addTab([CtParameterImpl][CtTypeReferenceImpl]Widget widget, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileIcon icon, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String docId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tooltip, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer position, [CtParameterImpl][CtTypeReferenceImpl]boolean switchToTab);

        [CtMethodImpl][CtTypeReferenceImpl]void selectTab([CtParameterImpl][CtTypeReferenceImpl]int tabIndex);

        [CtMethodImpl][CtTypeReferenceImpl]void selectTab([CtParameterImpl][CtTypeReferenceImpl]Widget widget);

        [CtMethodImpl][CtTypeReferenceImpl]int getTabCount();

        [CtMethodImpl][CtTypeReferenceImpl]int getActiveTabIndex();

        [CtMethodImpl][CtTypeReferenceImpl]void closeTab([CtParameterImpl][CtTypeReferenceImpl]Widget widget, [CtParameterImpl][CtTypeReferenceImpl]boolean interactive);

        [CtMethodImpl][CtTypeReferenceImpl]void closeTab([CtParameterImpl][CtTypeReferenceImpl]Widget widget, [CtParameterImpl][CtTypeReferenceImpl]boolean interactive, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onClosed);

        [CtMethodImpl][CtTypeReferenceImpl]void closeTab([CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl][CtTypeReferenceImpl]boolean interactive);

        [CtMethodImpl][CtTypeReferenceImpl]void closeTab([CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl][CtTypeReferenceImpl]boolean interactive, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onClosed);

        [CtMethodImpl][CtTypeReferenceImpl]void moveTab([CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl][CtTypeReferenceImpl]int delta);

        [CtMethodImpl][CtTypeReferenceImpl]void setDirty([CtParameterImpl][CtTypeReferenceImpl]Widget widget, [CtParameterImpl][CtTypeReferenceImpl]boolean dirty);

        [CtMethodImpl][CtTypeReferenceImpl]void manageChevronVisibility();

        [CtMethodImpl][CtTypeReferenceImpl]void showOverflowPopup();

        [CtMethodImpl][CtTypeReferenceImpl]void cancelTabDrag();

        [CtMethodImpl][CtTypeReferenceImpl]void showUnsavedChangesDialog([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> dirtyTargets, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result> saveOperation, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCancelled);

        [CtMethodImpl][CtTypeReferenceImpl]void ensureVisible();

        [CtMethodImpl][CtTypeReferenceImpl]void renameTab([CtParameterImpl][CtTypeReferenceImpl]Widget child, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileIcon icon, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tooltip);

        [CtMethodImpl][CtTypeReferenceImpl]com.google.gwt.event.shared.HandlerRegistration addBeforeShowHandler([CtParameterImpl][CtTypeReferenceImpl]BeforeShowHandler handler);
    }

    [CtInterfaceImpl]public interface CPSEditingTargetCommand {
        [CtMethodImpl][CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editingTarget, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation);
    }

    [CtConstructorImpl][CtAnnotationImpl]@com.google.inject.Inject
    public Source([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.commands.Commands commands, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.Binder binder, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.Display view, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceServerOperations server, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTargetSource editingTargetSource, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileTypeRegistry fileTypeRegistry, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.GlobalDisplay globalDisplay, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.FileDialogs fileDialogs, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.RemoteFileSystemContext fileContext, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.application.events.EventBus events, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.application.AriaLiveService ariaLive, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.synctex.Synctex synctex, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.WorkbenchContext workbenchContext, [CtParameterImpl][CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.FileMRUList> pMruList, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserState userState, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.satellite.Satellite satellite, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ConsoleEditorProvider consoleEditorProvider, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.rnw.RnwWeaveRegistry rnwWeaveRegistry, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.dependencies.DependencyManager dependencyManager, [CtParameterImpl][CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]SourceWindowManager> pWindowManager) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]commands_ = [CtVariableReadImpl]commands;
        [CtInvocationImpl][CtVariableReadImpl]binder.bind([CtVariableReadImpl]commands, [CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]view_ = [CtVariableReadImpl]view;
        [CtAssignmentImpl][CtFieldWriteImpl]server_ = [CtVariableReadImpl]server;
        [CtAssignmentImpl][CtFieldWriteImpl]editingTargetSource_ = [CtVariableReadImpl]editingTargetSource;
        [CtAssignmentImpl][CtFieldWriteImpl]fileTypeRegistry_ = [CtVariableReadImpl]fileTypeRegistry;
        [CtAssignmentImpl][CtFieldWriteImpl]globalDisplay_ = [CtVariableReadImpl]globalDisplay;
        [CtAssignmentImpl][CtFieldWriteImpl]fileDialogs_ = [CtVariableReadImpl]fileDialogs;
        [CtAssignmentImpl][CtFieldWriteImpl]fileContext_ = [CtVariableReadImpl]fileContext;
        [CtAssignmentImpl][CtFieldWriteImpl]rmarkdown_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetRMarkdownHelper();
        [CtAssignmentImpl][CtFieldWriteImpl]events_ = [CtVariableReadImpl]events;
        [CtAssignmentImpl][CtFieldWriteImpl]ariaLive_ = [CtVariableReadImpl]ariaLive;
        [CtAssignmentImpl][CtFieldWriteImpl]session_ = [CtVariableReadImpl]session;
        [CtAssignmentImpl][CtFieldWriteImpl]synctex_ = [CtVariableReadImpl]synctex;
        [CtAssignmentImpl][CtFieldWriteImpl]workbenchContext_ = [CtVariableReadImpl]workbenchContext;
        [CtAssignmentImpl][CtFieldWriteImpl]pMruList_ = [CtVariableReadImpl]pMruList;
        [CtAssignmentImpl][CtFieldWriteImpl]userState_ = [CtVariableReadImpl]userState;
        [CtAssignmentImpl][CtFieldWriteImpl]consoleEditorProvider_ = [CtVariableReadImpl]consoleEditorProvider;
        [CtAssignmentImpl][CtFieldWriteImpl]rnwWeaveRegistry_ = [CtVariableReadImpl]rnwWeaveRegistry;
        [CtAssignmentImpl][CtFieldWriteImpl]dependencyManager_ = [CtVariableReadImpl]dependencyManager;
        [CtAssignmentImpl][CtFieldWriteImpl]pWindowManager_ = [CtVariableReadImpl]pWindowManager;
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.newSourceDoc().setEnabled([CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]dynamicCommands_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand>();
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.saveSourceDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.reopenSourceDocWithEncoding());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.saveSourceDocAs());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.saveSourceDocWithEncoding());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.printSourceDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.vcsFileLog());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.vcsFileDiff());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.vcsFileRevert());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeCodeWithoutFocus());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeAllCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeToCurrentLine());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeFromCurrentLine());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeCurrentFunction());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeCurrentSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeLastCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.insertChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.insertSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeSetupChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executePreviousChunks());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeSubsequentChunks());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeCurrentChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.executeNextChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.previewJS());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.previewSql());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.sourceActiveDocument());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.sourceActiveDocumentWithEcho());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.knitDocument());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.toggleRmdVisualMode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.enableProsemirrorDevTools());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.previewHTML());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.compilePDF());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.compileNotebook());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.synctexSearch());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.popoutDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.returnDocToMain());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.findReplace());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.findNext());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.findPrevious());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.findFromSelection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.replaceAndFind());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.extractFunction());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.extractLocalVariable());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.commentUncomment());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.reindent());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.reflowComment());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.jumpTo());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.jumpToMatching());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.goToHelp());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.goToDefinition());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.setWorkingDirToActiveDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.debugDumpContents());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.debugImportDump());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.goToLine());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.checkSpelling());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.wordCount());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.codeCompletion());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.findUsages());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.debugBreakpoint());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.vcsViewOnGitHub());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.vcsBlameOnGitHub());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.editRmdFormatOptions());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.reformatCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.showDiagnosticsActiveDocument());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.renameInScope());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.insertRoxygenSkeleton());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.expandSelection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.shrinkSelection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.toggleDocumentOutline());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.knitWithParameters());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.clearKnitrCache());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.goToNextSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.goToPrevSection());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.goToNextChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.goToPrevChunk());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.profileCode());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.profileCodeWithoutFocus());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.saveProfileAs());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.restartRClearOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.restartRRunAllChunks());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.notebookCollapseAllOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.notebookExpandAllOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.notebookClearOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.notebookClearAllOutput());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.notebookToggleExpansion());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.sendToTerminal());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.openNewTerminalAtEditorLocation());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.sendFilenameToTerminal());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.renameSourceDoc());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.sourceAsLauncherJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.sourceAsJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.runSelectionAsJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.runSelectionAsLauncherJob());
        [CtInvocationImpl][CtFieldReadImpl]dynamicCommands_.add([CtInvocationImpl][CtVariableReadImpl]commands.toggleSoftWrapMode());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand command : [CtFieldReadImpl]dynamicCommands_) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]command.setVisible([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]command.setEnabled([CtLiteralImpl]false);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]vimCommands_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SourceVimCommands();
        [CtInvocationImpl][CtFieldReadImpl]view_.addTabClosingHandler([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]view_.addTabCloseHandler([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]view_.addTabClosedHandler([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]view_.addTabReorderHandler([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]view_.addSelectionHandler([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]view_.addBeforeShowHandler([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]EditPresentationSourceEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]FileEditEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]InsertSourceEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]ShowContentEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]ShowDataEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]OpenObjectExplorerEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]OpenPresentationSourceFileEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]OpenSourceFileEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]CodeBrowserNavigationEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]CodeBrowserFinishedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]CodeBrowserHighlightEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SnippetsChangedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]NewDocumentWithCodeEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]FileTypeChangedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.FileTypeChangedHandler()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onFileTypeChanged([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.FileTypeChangedEvent event) [CtBlockImpl]{
                [CtInvocationImpl]manageCommands();
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]NewDocumentWithCodeEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]NewDocumentWithCodeEvent.Handler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onNewDocumentWithCode([CtParameterImpl][CtTypeReferenceImpl]NewDocumentWithCodeEvent event) [CtBlockImpl]{
                [CtInvocationImpl]onNewDocumentWithCode([CtVariableReadImpl]event);
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SourceOnSaveChangedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.SourceOnSaveChangedHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSourceOnSaveChanged([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.SourceOnSaveChangedEvent event) [CtBlockImpl]{
                [CtInvocationImpl]manageSaveCommands();
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SwitchToDocEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl]SwitchToDocHandler()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onSwitchToDoc([CtParameterImpl][CtTypeReferenceImpl]SwitchToDocEvent event) [CtBlockImpl]{
                [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
                [CtInvocationImpl]setPhysicalTabIndex([CtInvocationImpl][CtVariableReadImpl]event.getSelectedIndex());
                [CtInvocationImpl][CtCommentImpl]// Fire an activation event just to ensure the activated
                [CtCommentImpl]// tab gets focus
                [CtInvocationImpl][CtFieldReadImpl]commands_.activateSource().execute();
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SourceFileSavedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl]SourceFileSavedHandler()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onSourceFileSaved([CtParameterImpl][CtTypeReferenceImpl]SourceFileSavedEvent event) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().add([CtInvocationImpl][CtVariableReadImpl]event.getPath());
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SourcePathChangedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]SourcePathChangedEvent.Handler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSourcePathChanged([CtParameterImpl]final [CtTypeReferenceImpl]SourcePathChangedEvent event) [CtBlockImpl]{
                [CtInvocationImpl]inEditorForPath([CtInvocationImpl][CtVariableReadImpl]event.getFrom(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget input) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem toPath = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtInvocationImpl][CtVariableReadImpl]event.getTo());
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]input instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// for text files, notify the editing surface so it can
                            [CtCommentImpl]// react to the new file type
                            [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (input)).setPath([CtVariableReadImpl]toPath);
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// for other files, just rename the tab
                            [CtInvocationImpl][CtVariableReadImpl]input.getName().setValue([CtInvocationImpl][CtVariableReadImpl]toPath.getName(), [CtLiteralImpl]true);
                        }
                        [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]SourceFileSavedEvent([CtInvocationImpl][CtVariableReadImpl]input.getId(), [CtInvocationImpl][CtVariableReadImpl]event.getTo()));
                    }
                });
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SourceNavigationEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl]SourceNavigationHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSourceNavigation([CtParameterImpl][CtTypeReferenceImpl]SourceNavigationEvent event) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]suspendSourceNavigationAdding_) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.add([CtInvocationImpl][CtVariableReadImpl]event.getNavigation());
                }
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SourceExtendedTypeDetectedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.addChangeHandler([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.event.dom.client.ChangeHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onChange([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.event.dom.client.ChangeEvent event) [CtBlockImpl]{
                [CtInvocationImpl]manageSourceNavigationCommands();
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SynctexStatusChangedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.synctex.events.SynctexStatusChangedEvent.Handler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSynctexStatusChanged([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.synctex.events.SynctexStatusChangedEvent event) [CtBlockImpl]{
                [CtInvocationImpl]manageSynctexCommands();
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]CollabEditStartedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]CollabEditStartedEvent.Handler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onCollabEditStarted([CtParameterImpl]final [CtTypeReferenceImpl]CollabEditStartedEvent collab) [CtBlockImpl]{
                [CtInvocationImpl]inEditorForPath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collab.getStartParams().getPath(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]editor.beginCollabSession([CtInvocationImpl][CtVariableReadImpl]collab.getStartParams());
                    }
                });
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]CollabEditEndedEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]CollabEditEndedEvent.Handler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onCollabEditEnded([CtParameterImpl]final [CtTypeReferenceImpl]CollabEditEndedEvent collab) [CtBlockImpl]{
                [CtInvocationImpl]inEditorForPath([CtInvocationImpl][CtVariableReadImpl]collab.getPath(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]editor.endCollabSession();
                    }
                });
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]NewWorkingCopyEvent.TYPE, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.NewWorkingCopyEvent.Handler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onNewWorkingCopy([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.events.NewWorkingCopyEvent event) [CtBlockImpl]{
                [CtInvocationImpl]newDoc([CtInvocationImpl][CtVariableReadImpl]event.getType(), [CtInvocationImpl][CtVariableReadImpl]event.getContents(), [CtLiteralImpl]null);
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]PopoutDocEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]DocWindowChangedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]DocTabDragInitiatedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]PopoutDocInitiatedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]DebugModeChangedEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]ReplaceRangesEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]GetEditorContextEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]SetSelectionRangesEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]OpenProfileEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]RequestDocumentSaveEvent.TYPE, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]events_.addHandler([CtTypeAccessImpl]RequestDocumentCloseEvent.TYPE, [CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]initialized_ = [CtLiteralImpl]true;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void loadFullSource() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor.preload();
        [CtAssignmentImpl][CtCommentImpl]// sync UI prefs with shortcut manager
        [CtFieldWriteImpl]userPrefs_ = [CtInvocationImpl][CtTypeAccessImpl]RStudioGinjector.INSTANCE.getUserPrefs();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.editorKeybindings().getValue() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.prefs.model.UserPrefs.EDITOR_KEYBINDINGS_VIM)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]ShortcutManager.INSTANCE.setEditorMode([CtTypeAccessImpl]KeyboardShortcut.MODE_VIM);
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.editorKeybindings().getValue() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.prefs.model.UserPrefs.EDITOR_KEYBINDINGS_EMACS)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]ShortcutManager.INSTANCE.setEditorMode([CtTypeAccessImpl]KeyboardShortcut.MODE_EMACS);
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.editorKeybindings().getValue() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.prefs.model.UserPrefs.EDITOR_KEYBINDINGS_SUBLIME)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]ShortcutManager.INSTANCE.setEditorMode([CtTypeAccessImpl]KeyboardShortcut.MODE_SUBLIME);
        else[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]ShortcutManager.INSTANCE.setEditorMode([CtTypeAccessImpl]KeyboardShortcut.MODE_DEFAULT);

        [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]DocTabsChangedEvent([CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0], [CtNewArrayImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileIcon[[CtLiteralImpl]0], [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0], [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0]));
        [CtInvocationImpl][CtCommentImpl]// fake shortcuts for commands_ which we handle at a lower level
        [CtInvocationImpl][CtFieldReadImpl]commands_.goToHelp().setShortcut([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.command.KeyboardShortcut([CtLiteralImpl]"F1", [CtFieldReadImpl]com.google.gwt.event.dom.client.KeyCodes.KEY_F1, [CtFieldReadImpl]org.rstudio.core.client.command.KeyboardShortcut.NONE));
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.goToDefinition().setShortcut([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.command.KeyboardShortcut([CtLiteralImpl]"F2", [CtFieldReadImpl]com.google.gwt.event.dom.client.KeyCodes.KEY_F2, [CtFieldReadImpl]org.rstudio.core.client.command.KeyboardShortcut.NONE));
        [CtIfImpl][CtCommentImpl]// If tab has been disabled for auto complete by the user, set the "shortcut" to ctrl-space instead.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.tabCompletion().getValue() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.tabKeyMoveFocus().getValue()))[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.codeCompletion().setShortcut([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.command.KeyboardShortcut([CtLiteralImpl]"Tab", [CtFieldReadImpl]com.google.gwt.event.dom.client.KeyCodes.KEY_TAB, [CtFieldReadImpl]org.rstudio.core.client.command.KeyboardShortcut.NONE));
        else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.KeySequence sequence = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.command.KeySequence();
            [CtInvocationImpl][CtVariableReadImpl]sequence.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.command.KeyCombination([CtLiteralImpl]"Ctrl+Space", [CtFieldReadImpl]com.google.gwt.event.dom.client.KeyCodes.KEY_SPACE, [CtFieldReadImpl]com.google.gwt.event.dom.client.KeyCodes.KEY_CTRL));
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.codeCompletion().setShortcut([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.command.KeyboardShortcut([CtVariableReadImpl]sequence));
        }
        [CtInvocationImpl][CtCommentImpl]// Suppress 'CTRL + ALT + SHIFT + click' to work around #2483 in Ace
        [CtTypeAccessImpl]com.google.gwt.user.client.Event.addNativePreviewHandler([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Event.NativePreviewHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onPreviewNativeEvent([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Event.NativePreviewEvent event) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int type = [CtInvocationImpl][CtVariableReadImpl]event.getTypeInt();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]com.google.gwt.user.client.Event.ONMOUSEDOWN) || [CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]com.google.gwt.user.client.Event.ONMOUSEUP)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int modifier = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.command.KeyboardShortcut.getModifierValue([CtInvocationImpl][CtVariableReadImpl]event.getNativeEvent());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]modifier == [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]org.rstudio.core.client.command.KeyboardShortcut.ALT | [CtFieldReadImpl]org.rstudio.core.client.command.KeyboardShortcut.CTRL) | [CtFieldReadImpl]org.rstudio.core.client.command.KeyboardShortcut.SHIFT)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]event.cancel();
                        [CtReturnImpl]return;
                    }
                }
            }
        });
        [CtIfImpl][CtCommentImpl]// on macOS, we need to aggressively re-sync commands when a new
        [CtCommentImpl]// window is selected (since the main menu applies to both main
        [CtCommentImpl]// window and satellites)
        if ([CtInvocationImpl][CtTypeAccessImpl]BrowseCap.isMacintoshDesktop()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.dom.WindowEx.addFocusHandler([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.event.dom.client.FocusEvent event) -> [CtBlockImpl]{
                [CtInvocationImpl]manageCommands([CtLiteralImpl]true);
            });
        }
        [CtInvocationImpl]restoreDocuments([CtFieldReadImpl]session_);
        [CtLocalVariableImpl][CtCommentImpl]// get the key to use for active tab persistence; use ordinal-based key
        [CtCommentImpl]// for source windows rather than their ID to avoid unbounded accumulation
        [CtTypeReferenceImpl]java.lang.String activeTabKey = [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.KEY_ACTIVETAB;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow())[CtBlockImpl]
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]activeTabKey += [CtBinaryOperatorImpl][CtLiteralImpl]"SourceWindow" + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().getSourceWindowOrdinal();

        [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.helper.IntStateValue([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.MODULE_SOURCE, [CtVariableReadImpl]activeTabKey, [CtFieldReadImpl]org.rstudio.studio.client.workbench.model.ClientState.PROJECT_PERSISTENT, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().getClientState())[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]void onInit([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer value) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]value >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() > [CtVariableReadImpl]value))[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]value);

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]view_.getActiveTabIndex() >= [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtInvocationImpl][CtFieldReadImpl]view_.getActiveTabIndex()).onInitiallyLoaded();
                }
                [CtInvocationImpl][CtCommentImpl]// clear the history manager
                [CtFieldReadImpl]sourceNavigationHistory_.clear();
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]java.lang.Integer getValue() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]getPhysicalTabIndex();
            }
        };
        [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.AceEditorNative.syncUiPrefs([CtFieldReadImpl]userPrefs_);
        [CtInvocationImpl][CtCommentImpl]// As tabs were added before, manageCommands() was suppressed due to
        [CtCommentImpl]// initialized_ being false, so we need to run it explicitly
        manageCommands();
        [CtInvocationImpl][CtCommentImpl]// Same with this event
        fireDocTabsChanged();
        [CtIfImpl][CtCommentImpl]// open project or edit_published docs (only for main source window)
        if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow()) [CtBlockImpl]{
            [CtInvocationImpl]openProjectDocs([CtFieldReadImpl]session_);
            [CtInvocationImpl]openEditPublishedDocs();
        }
        [CtInvocationImpl][CtCommentImpl]// add vim commands
        initVimCommands();
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void withSaveFilesBeforeCommand([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command command, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command cancelCommand, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String commandSource) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.saveFilesBeforeBuild().getValue()) [CtBlockImpl]{
            [CtInvocationImpl]saveUnsavedDocuments([CtVariableReadImpl]command);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String alwaysSaveOption = [CtConditionalImpl]([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.saveFilesBeforeBuild().getValue()) ? [CtLiteralImpl]"Always save files before build" : [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> unsavedSourceDocs = [CtInvocationImpl]getUnsavedChanges([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.TYPE_FILE_BACKED);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unsavedSourceDocs.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog([CtVariableReadImpl]commandSource, [CtVariableReadImpl]alwaysSaveOption, [CtVariableReadImpl]unsavedSourceDocs, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result>()[CtClassImpl] {
                    [CtMethodImpl]public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result result) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.getAlwaysSave()) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.saveFilesBeforeBuild().setGlobalValue([CtLiteralImpl]true);
                            [CtInvocationImpl][CtFieldReadImpl]userPrefs_.writeUserPrefs();
                        }
                        [CtInvocationImpl]handleUnsavedChangesBeforeExit([CtInvocationImpl][CtVariableReadImpl]result.getSaveTargets(), [CtVariableReadImpl]command);
                    }
                }, [CtVariableReadImpl]cancelCommand).showModal();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]command.execute();
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean consoleEditorHadFocusLast() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String id = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.MainWindowObject.lastFocusedEditorId().get();
        [CtReturnImpl]return [CtInvocationImpl][CtLiteralImpl]"rstudio_console_input".equals([CtVariableReadImpl]id);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void withTarget([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget> command, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onFailure) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtVariableReadImpl]id)) ? [CtFieldReadImpl]activeEditor_ : [CtInvocationImpl]getEditingTargetForId([CtVariableReadImpl]id);
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void getEditorContext([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay docDisplay) [CtBlockImpl]{
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
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArray<[CtTypeReferenceImpl]org.rstudio.studio.client.events.GetEditorContextEvent.DocumentSelection> docSelections = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.JavaScriptObject.createArray().cast();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]ranges.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]docSelections.push([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.events.GetEditorContextEvent.DocumentSelection.create([CtArrayReadImpl][CtVariableReadImpl]ranges[[CtVariableReadImpl]i], [CtInvocationImpl][CtVariableReadImpl]editor.getTextForRange([CtArrayReadImpl][CtVariableReadImpl]ranges[[CtVariableReadImpl]i])));
        }
        [CtAssignmentImpl][CtVariableWriteImpl]id = [CtInvocationImpl][CtTypeAccessImpl]StringUtil.notNull([CtVariableReadImpl]id);
        [CtAssignmentImpl][CtVariableWriteImpl]path = [CtInvocationImpl][CtTypeAccessImpl]StringUtil.notNull([CtVariableReadImpl]path);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.GetEditorContextEvent.SelectionData data = [CtInvocationImpl][CtTypeAccessImpl]GetEditorContextEvent.SelectionData.create([CtVariableReadImpl]id, [CtVariableReadImpl]path, [CtInvocationImpl][CtVariableReadImpl]editor.getCode(), [CtVariableReadImpl]docSelections);
        [CtInvocationImpl][CtFieldReadImpl]server_.getEditorContextCompleted([CtVariableReadImpl]data, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void withTarget([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget> command) [CtBlockImpl]{
        [CtInvocationImpl]withTarget([CtVariableReadImpl]id, [CtVariableReadImpl]command, [CtLiteralImpl]null);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void initVimCommands() [CtBlockImpl]{
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void vimSetTabIndex([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tabCount = [CtInvocationImpl][CtFieldReadImpl]view_.getTabCount();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index >= [CtVariableReadImpl]tabCount)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]setPhysicalTabIndex([CtVariableReadImpl]index);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void closeAllTabs([CtParameterImpl][CtTypeReferenceImpl]boolean interactive) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]interactive) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// call into the interactive tab closer
            onCloseAllSourceDocs();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// revert unsaved targets and close tabs
            revertUnsavedTargets([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// documents have been reverted; we can close
                    cpsExecuteForEachEditor([CtFieldReadImpl]editors_, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.CPSEditingTargetCommand()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editingTarget, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]view_.closeTab([CtInvocationImpl][CtVariableReadImpl]editingTarget.asWidget(), [CtLiteralImpl]false, [CtVariableReadImpl]continuation);
                        }
                    });
                }
            });
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveActiveSourceDoc() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget target = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
            [CtInvocationImpl][CtVariableReadImpl]target.save();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveAndCloseActiveSourceDoc() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget target = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
            [CtInvocationImpl][CtVariableReadImpl]target.save([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtInvocationImpl]onCloseSourceDoc();
                }
            });
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param isNewTabPending
     * 		True if a new tab is about to be created. (If
     * 		false and there are no tabs already, then a new source doc might
     * 		be created to make sure we don't end up with a source pane showing
     * 		with no tabs in it.)
     */
    private [CtTypeReferenceImpl]void ensureVisible([CtParameterImpl][CtTypeReferenceImpl]boolean isNewTabPending) [CtBlockImpl]{
        [CtUnaryOperatorImpl][CtFieldWriteImpl]newTabPending_++;
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]view_.ensureVisible();
        } finally [CtBlockImpl]{
            [CtUnaryOperatorImpl][CtFieldWriteImpl]newTabPending_--;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Widget asWidget() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]view_.asWidget();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void restoreDocuments([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session session) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.core.client.JsArray<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument> docs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.getSessionInfo().getSourceDocuments();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]docs.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// restore the docs assigned to this source window
            [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument doc = [CtInvocationImpl][CtVariableReadImpl]docs.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String docWindowId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]doc.getProperties().getString([CtTypeAccessImpl]SourceWindowManager.SOURCE_WINDOW_ID);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]docWindowId == [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]docWindowId = [CtLiteralImpl]"";

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String currentSourceWindowId = [CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.getSourceWindowId();
            [CtIfImpl][CtCommentImpl]// it belongs in this window if (a) it's assigned to it, or (b) this
            [CtCommentImpl]// is the main window, and the window it's assigned to isn't open.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]currentSourceWindowId == [CtVariableReadImpl]docWindowId) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().isSourceWindowOpen([CtVariableReadImpl]docWindowId)))) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// attempt to add a tab for the current doc; try/catch this since
                [CtCommentImpl]// we don't want to allow one failure to prevent all docs from
                [CtCommentImpl]// opening
                [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget sourceEditor = [CtLiteralImpl]null;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]sourceEditor = [CtInvocationImpl]addTab([CtVariableReadImpl]doc, [CtLiteralImpl]true, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_REPLAY);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Debug.logException([CtVariableReadImpl]e);
                }
                [CtIfImpl][CtCommentImpl]// if we couldn't add the tab for this doc, just continue to the
                [CtCommentImpl]// next one
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]sourceEditor == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtContinueImpl]continue;

            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openEditPublishedDocs() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// don't do this if we are switching projects (it
        [CtCommentImpl]// will be done after the switch)
        if ([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.ApplicationAction.isSwitchProject())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtCommentImpl]// check for edit_published url parameter
        final [CtTypeReferenceImpl]java.lang.String kEditPublished = [CtLiteralImpl]"edit_published";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String editPublished = [CtInvocationImpl][CtTypeAccessImpl]StringUtil.notNull([CtInvocationImpl][CtTypeAccessImpl]Window.Location.getParameter([CtVariableReadImpl]kEditPublished));
        [CtIfImpl][CtCommentImpl]// this is an appPath which we can call the server
        [CtCommentImpl]// to determine source files to edit
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]editPublished.length() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// remove it from the url
            [CtTypeAccessImpl]org.rstudio.studio.client.application.ApplicationUtils.removeQueryParam([CtVariableReadImpl]kEditPublished);
            [CtInvocationImpl][CtFieldReadImpl]server_.getEditPublishedDocs([CtVariableReadImpl]editPublished, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString docs) [CtBlockImpl]{
                    [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.SourceFilesOpener([CtVariableReadImpl]docs).run();
                }
            });
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openProjectDocs([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session session) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString openDocs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.getSessionInfo().getProjectOpenDocs();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]openDocs.length() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtUnaryOperatorImpl][CtCommentImpl]// set new tab pending for the duration of the continuation
            [CtFieldWriteImpl]newTabPending_++;
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
                    [CtUnaryOperatorImpl][CtFieldWriteImpl]newTabPending_--;
                    [CtInvocationImpl]onFirstTab();
                    [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                }
            });
            [CtInvocationImpl][CtCommentImpl]// execute the continuation
            [CtVariableReadImpl]openCommands.run();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onShowContent([CtParameterImpl][CtTypeReferenceImpl]ShowContentEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// ignore if we're a satellite
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.ContentItem content = [CtInvocationImpl][CtVariableReadImpl]event.getContent();
        [CtInvocationImpl][CtFieldReadImpl]server_.newDocument([CtInvocationImpl][CtTypeAccessImpl]FileTypeRegistry.URLCONTENT.getTypeId(), [CtLiteralImpl]null, [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject) ([CtVariableReadImpl]content.cast())), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>([CtLiteralImpl]"Show")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument response) [CtBlockImpl]{
                [CtInvocationImpl]addTab([CtVariableReadImpl]response, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onOpenObjectExplorerEvent([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.explorer.events.OpenObjectExplorerEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// ignore if we're a satellite
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.explorer.model.ObjectExplorerHandle handle = [CtInvocationImpl][CtVariableReadImpl]event.getHandle();
        [CtForImpl][CtCommentImpl]// attempt to open pre-existing tab
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtCommentImpl]// bail if this isn't an object explorer filetype
            [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileType fileType = [CtInvocationImpl][CtVariableReadImpl]target.getFileType();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]fileType instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.ObjectExplorerFileType))[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtIfImpl][CtCommentImpl]// check for identical titles
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]handle.getTitle() == [CtInvocationImpl][CtVariableReadImpl]target.getTitle()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.explorer.ObjectExplorerEditingTarget) ([CtFieldReadImpl]editors_.get([CtVariableReadImpl]i))).update([CtVariableReadImpl]handle);
                [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]i);
                [CtReturnImpl]return;
            }
        }
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]server_.newDocument([CtInvocationImpl][CtTypeAccessImpl]FileTypeRegistry.OBJECT_EXPLORER.getTypeId(), [CtLiteralImpl]null, [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject) ([CtVariableReadImpl]handle.cast())), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>([CtLiteralImpl]"Show Object Explorer")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument response) [CtBlockImpl]{
                [CtInvocationImpl]addTab([CtVariableReadImpl]response, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onShowData([CtParameterImpl][CtTypeReferenceImpl]ShowDataEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// ignore if we're a satellite
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.DataItem data = [CtInvocationImpl][CtVariableReadImpl]event.getData();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i).getPath();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]path != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]path.equals([CtInvocationImpl][CtVariableReadImpl]data.getURI())) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.data.DataEditingTarget) ([CtFieldReadImpl]editors_.get([CtVariableReadImpl]i))).updateData([CtVariableReadImpl]data);
                [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]i);
                [CtReturnImpl]return;
            }
        }
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]server_.newDocument([CtInvocationImpl][CtTypeAccessImpl]FileTypeRegistry.DATAFRAME.getTypeId(), [CtLiteralImpl]null, [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject) ([CtVariableReadImpl]data.cast())), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>([CtLiteralImpl]"Show Data Frame")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument response) [CtBlockImpl]{
                [CtInvocationImpl]addTab([CtVariableReadImpl]response, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onShowProfiler([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.profiler.OpenProfileEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String profilePath = [CtInvocationImpl][CtVariableReadImpl]event.getFilePath();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String htmlPath = [CtInvocationImpl][CtVariableReadImpl]event.getHtmlPath();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String htmlLocalPath = [CtInvocationImpl][CtVariableReadImpl]event.getHtmlLocalPath();
        [CtForImpl][CtCommentImpl]// first try to activate existing
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]idx++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]idx).getPath();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]path != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]path == [CtVariableReadImpl]profilePath)) [CtBlockImpl]{
                [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]idx);
                [CtReturnImpl]return;
            }
        }
        [CtInvocationImpl][CtCommentImpl]// create new profiler
        ensureVisible([CtLiteralImpl]true);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getDocId() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]server_.getSourceDocument([CtInvocationImpl][CtVariableReadImpl]event.getDocId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument response) [CtBlockImpl]{
                    [CtInvocationImpl]addTab([CtVariableReadImpl]response, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
                    [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Source Document Error", [CtInvocationImpl][CtVariableReadImpl]error.getUserMessage());
                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]server_.newDocument([CtInvocationImpl][CtTypeAccessImpl]FileTypeRegistry.PROFILER.getTypeId(), [CtLiteralImpl]null, [CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.core.client.js.JsObject) ([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.profiler.model.ProfilerContents.create([CtVariableReadImpl]profilePath, [CtVariableReadImpl]htmlPath, [CtVariableReadImpl]htmlLocalPath, [CtInvocationImpl][CtVariableReadImpl]event.getCreateProfile()).cast())), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>([CtLiteralImpl]"Show Profiler")[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument response) [CtBlockImpl]{
                    [CtInvocationImpl]addTab([CtVariableReadImpl]response, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
                    [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Source Document Error", [CtInvocationImpl][CtVariableReadImpl]error.getUserMessage());
                }
            });
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewSourceDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.R, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewTextDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.TEXT, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewRNotebook() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]dependencyManager_.withRMarkdown([CtLiteralImpl]"R Notebook", [CtLiteralImpl]"Create R Notebook", [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean succeeded) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]succeeded) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Notebook Creation Failed", [CtBinaryOperatorImpl][CtLiteralImpl]"One or more packages required for R Notebook " + [CtLiteralImpl]"creation were not installed.");
                    [CtReturnImpl]return;
                }
                [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtLiteralImpl]"", [CtLiteralImpl]"notebook.Rmd", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]3, [CtLiteralImpl]0));
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewCDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.C, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.verifyCppPrerequisites();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewCppDoc() [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.CPP, [CtLiteralImpl]"", [CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.useRcppTemplate().getValue() ? [CtLiteralImpl]"rcpp.cpp" : [CtLiteralImpl]"default.cpp", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]0, [CtLiteralImpl]0), [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.verifyCppPrerequisites();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewHeaderDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.H, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.verifyCppPrerequisites();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewMarkdownDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.MARKDOWN, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewPythonDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.PYTHON, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.verifyPythonPrerequisites();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewShellDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.SH, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewHtmlDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.HTML, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewJavaScriptDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.JS, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewCssDoc() [CtBlockImpl]{
        [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.CSS, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewStanDoc() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onStanInstalled = [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtInvocationImpl]newSourceDocWithTemplate([CtVariableReadImpl]FileTypeRegistry.STAN, [CtLiteralImpl]"", [CtLiteralImpl]"stan.stan", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]31, [CtLiteralImpl]0), [CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) -> [CtBlockImpl]{
            });
        };
        [CtInvocationImpl][CtFieldReadImpl]dependencyManager_.withStan([CtLiteralImpl]"Creating Stan script", [CtLiteralImpl]"Creating Stan scripts", [CtVariableReadImpl]onStanInstalled);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewD3Doc() [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.JS, [CtLiteralImpl]"", [CtLiteralImpl]"d3.js", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]5, [CtLiteralImpl]0), [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.verifyD3Prerequisites();
                [CtInvocationImpl][CtVariableReadImpl]target.setSourceOnSave([CtLiteralImpl]true);
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewSweaveDoc() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// set concordance value if we need to
        [CtTypeReferenceImpl]java.lang.String concordance = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.alwaysEnableRnwConcordance().getValue()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.rnw.RnwWeave activeWeave = [CtInvocationImpl][CtFieldReadImpl]rnwWeaveRegistry_.findTypeIgnoreCase([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.defaultSweaveEngine().getValue());
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]activeWeave.getInjectConcordance())[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]concordance = [CtLiteralImpl]"\\SweaveOpts{concordance=TRUE}\n";

        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String concordanceValue = [CtVariableReadImpl]concordance;
        [CtLocalVariableImpl][CtCommentImpl]// show progress
        final [CtTypeReferenceImpl]org.rstudio.core.client.widget.ProgressIndicator indicator = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.GlobalProgressDelayer([CtFieldReadImpl]globalDisplay_, [CtLiteralImpl]500, [CtLiteralImpl]"Creating new document...").getIndicator();
        [CtInvocationImpl][CtCommentImpl]// get the template
        [CtFieldReadImpl]server_.getSourceTemplate([CtLiteralImpl]"", [CtLiteralImpl]"sweave.Rnw", [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]java.lang.String templateContents) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]indicator.onCompleted();
                [CtLocalVariableImpl][CtCommentImpl]// add in concordance if necessary
                final [CtTypeReferenceImpl]boolean hasConcordance = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]concordanceValue.length() > [CtLiteralImpl]0;
                [CtIfImpl]if ([CtVariableReadImpl]hasConcordance) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String beginDoc = [CtLiteralImpl]"\\begin{document}\n";
                    [CtAssignmentImpl][CtVariableWriteImpl]templateContents = [CtInvocationImpl][CtVariableReadImpl]templateContents.replace([CtVariableReadImpl]beginDoc, [CtBinaryOperatorImpl][CtVariableReadImpl]beginDoc + [CtVariableReadImpl]concordanceValue);
                }
                [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.SWEAVE, [CtVariableReadImpl]templateContents, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int startRow = [CtBinaryOperatorImpl][CtLiteralImpl]4 + [CtConditionalImpl]([CtVariableReadImpl]hasConcordance ? [CtLiteralImpl]1 : [CtLiteralImpl]0);
                        [CtInvocationImpl][CtVariableReadImpl]target.setCursorPosition([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtVariableReadImpl]startRow, [CtLiteralImpl]0));
                    }
                });
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]indicator.onError([CtInvocationImpl][CtVariableReadImpl]error.getUserMessage());
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewRMarkdownDoc() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.SessionInfo sessionInfo = [CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean useRMarkdownV2 = [CtInvocationImpl][CtVariableReadImpl]sessionInfo.getRMarkdownPackageAvailable();
        [CtIfImpl]if ([CtVariableReadImpl]useRMarkdownV2)[CtBlockImpl]
            [CtInvocationImpl]newRMarkdownV2Doc();
        else[CtBlockImpl]
            [CtInvocationImpl]newRMarkdownV1Doc();

    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doNewRShinyApp([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]NewShinyWebApplication.Result result) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]server_.createShinyApp([CtInvocationImpl][CtVariableReadImpl]result.getAppName(), [CtInvocationImpl][CtVariableReadImpl]result.getAppType(), [CtInvocationImpl][CtVariableReadImpl]result.getAppDir(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString>([CtLiteralImpl]"Error Creating Shiny Application", [CtLiteralImpl]true)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString createdFiles) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Open and focus files that we created
                [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.SourceFilesOpener([CtVariableReadImpl]createdFiles).run();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewSqlDoc() [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.SQL, [CtLiteralImpl]"", [CtLiteralImpl]"query.sql", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]2, [CtLiteralImpl]0), [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.verifyNewSqlPrerequisites();
                [CtInvocationImpl][CtVariableReadImpl]target.setSourceOnSave([CtLiteralImpl]true);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doNewRPlumberAPI([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]NewPlumberAPI.Result result) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]server_.createPlumberAPI([CtInvocationImpl][CtVariableReadImpl]result.getAPIName(), [CtInvocationImpl][CtVariableReadImpl]result.getAPIDir(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString>([CtLiteralImpl]"Error Creating Plumber API", [CtLiteralImpl]true)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString createdFiles) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Open and focus files that we created
                [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.SourceFilesOpener([CtVariableReadImpl]createdFiles).run();
            }
        });
    }

    [CtClassImpl][CtCommentImpl]// open a list of source files then focus the first one within the list
    private class SourceFilesOpener extends [CtTypeReferenceImpl]SerializedCommandQueue {
        [CtConstructorImpl]public SourceFilesOpener([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString sourceFiles) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]sourceFiles.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String filePath = [CtInvocationImpl][CtVariableReadImpl]sourceFiles.get([CtVariableReadImpl]i);
                [CtInvocationImpl]addCommand([CtNewClassImpl]new [CtTypeReferenceImpl]SerializedCommand()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onExecute([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem path = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtVariableReadImpl]filePath);
                        [CtInvocationImpl]openFile([CtVariableReadImpl]path, [CtTypeAccessImpl]FileTypeRegistry.R, [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                                [CtIfImpl][CtCommentImpl]// record first target if necessary
                                if ([CtBinaryOperatorImpl][CtFieldReadImpl]firstTarget_ == [CtLiteralImpl]null)[CtBlockImpl]
                                    [CtAssignmentImpl][CtFieldWriteImpl]firstTarget_ = [CtVariableReadImpl]target;

                                [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                            }
                        });
                    }
                });
            }
            [CtInvocationImpl]addCommand([CtNewClassImpl]new [CtTypeReferenceImpl]SerializedCommand()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onExecute([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]firstTarget_ != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtInvocationImpl][CtFieldReadImpl]firstTarget_.asWidget());
                        [CtInvocationImpl][CtFieldReadImpl]firstTarget_.setCursorPosition([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]0, [CtLiteralImpl]0));
                    }
                    [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                }
            });
        }

        [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget firstTarget_ = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewRShinyApp() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]dependencyManager_.withShiny([CtLiteralImpl]"Creating Shiny applications", [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]NewShinyWebApplication widget = [CtConstructorCallImpl]new [CtTypeReferenceImpl]NewShinyWebApplication([CtLiteralImpl]"New Shiny Web Application", [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]NewShinyWebApplication.Result>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.NewShinyWebApplication.Result input) [CtBlockImpl]{
                        [CtInvocationImpl]doNewRShinyApp([CtVariableReadImpl]input);
                    }
                });
                [CtInvocationImpl][CtVariableReadImpl]widget.showModal();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewRHTMLDoc() [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.RHTML, [CtLiteralImpl]"", [CtLiteralImpl]"default.Rhtml");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewRDocumentationDoc() [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRdDialog([CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRdDialog.Result>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ui.NewRdDialog.Result result) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command createEmptyDoc = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                        [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.RD, [CtFieldReadImpl][CtVariableReadImpl]result.name, [CtLiteralImpl]"default.Rd", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]3, [CtLiteralImpl]7));
                    }
                };
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]result.type != [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.NewShinyWebApplication.Result.TYPE_NONE) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]server_.createRdShell([CtFieldReadImpl][CtVariableReadImpl]result.name, [CtFieldReadImpl][CtVariableReadImpl]result.type, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.RdShellResult>()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.RdShellResult result) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.getPath() != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.openFile([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtInvocationImpl][CtVariableReadImpl]result.getPath()));
                            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.getContents() != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.RD, [CtInvocationImpl][CtVariableReadImpl]result.getContents(), [CtLiteralImpl]null);
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]createEmptyDoc.execute();
                            }
                        }
                    });
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]createEmptyDoc.execute();
                }
            }
        }).showModal();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewRPresentationDoc() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]dependencyManager_.withRMarkdown([CtLiteralImpl]"Authoring R Presentations", [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]fileDialogs_.saveFile([CtLiteralImpl]"New R Presentation", [CtFieldReadImpl]fileContext_, [CtInvocationImpl][CtFieldReadImpl]workbenchContext_.getDefaultFileDialogDir(), [CtLiteralImpl]".Rpres", [CtLiteralImpl]true, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.ProgressOperationWithInput<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem input, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.widget.ProgressIndicator indicator) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]input == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]indicator.onCompleted();
                            [CtReturnImpl]return;
                        }
                        [CtInvocationImpl][CtVariableReadImpl]indicator.onProgress([CtLiteralImpl]"Creating Presentation...");
                        [CtInvocationImpl][CtFieldReadImpl]server_.createNewPresentation([CtInvocationImpl][CtVariableReadImpl]input.getPath(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback([CtVariableReadImpl]indicator)[CtClassImpl] {
                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                            public [CtTypeReferenceImpl]void onSuccess() [CtBlockImpl]{
                                [CtInvocationImpl]openFile([CtVariableReadImpl]input, [CtTypeAccessImpl]FileTypeRegistry.RPRESENTATION, [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
                                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget arg) [CtBlockImpl]{
                                        [CtInvocationImpl][CtFieldReadImpl]server_.showPresentationPane([CtInvocationImpl][CtVariableReadImpl]input.getPath(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newRMarkdownV1Doc() [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtLiteralImpl]"", [CtLiteralImpl]"v1.Rmd", [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position.create([CtLiteralImpl]3, [CtLiteralImpl]0));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newRMarkdownV2Doc() [CtBlockImpl]{
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template) [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtVariableReadImpl]fileType, [CtVariableReadImpl]name, [CtVariableReadImpl]template, [CtLiteralImpl]null);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position cursorPosition) [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtVariableReadImpl]fileType, [CtVariableReadImpl]name, [CtVariableReadImpl]template, [CtVariableReadImpl]cursorPosition, [CtLiteralImpl]null);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position cursorPosition, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onSuccess) [CtBlockImpl]{
        [CtInvocationImpl]newSourceDocWithTemplate([CtVariableReadImpl]fileType, [CtVariableReadImpl]name, [CtVariableReadImpl]template, [CtVariableReadImpl]cursorPosition, [CtVariableReadImpl]onSuccess, [CtLiteralImpl]null);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newSourceDocWithTemplate([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position cursorPosition, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onSuccess, [CtParameterImpl]final [CtTypeReferenceImpl]TransformerCommand<[CtTypeReferenceImpl]java.lang.String> contentTransformer) [CtBlockImpl]{
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void newDoc([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.EditableFileType fileType, [CtParameterImpl][CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> callback) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fileType instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// This is a text file, so see if the user has defined a template for it.
            [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType textType = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType) (fileType));
            [CtInvocationImpl][CtFieldReadImpl]server_.getSourceTemplate([CtLiteralImpl]"", [CtBinaryOperatorImpl][CtLiteralImpl]"default" + [CtInvocationImpl][CtVariableReadImpl]textType.getDefaultExtension(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]java.lang.String template) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Create a new document with the supplied template.
                    newDoc([CtVariableReadImpl]fileType, [CtVariableReadImpl]template, [CtVariableReadImpl]callback);
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Ignore errors; there's just not a template for this type.
                    newDoc([CtVariableReadImpl]fileType, [CtLiteralImpl]null, [CtVariableReadImpl]callback);
                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl]newDoc([CtVariableReadImpl]fileType, [CtLiteralImpl]null, [CtVariableReadImpl]callback);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void newDoc([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.EditableFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String contents, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]server_.newDocument([CtInvocationImpl][CtVariableReadImpl]fileType.getTypeId(), [CtVariableReadImpl]contents, [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsObject.createJsObject(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.SimpleRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>([CtLiteralImpl]"Error Creating New Document")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument newDoc) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]addTab([CtVariableReadImpl]newDoc, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]contents != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]target.forceSaveCommandActive();
                    [CtInvocationImpl]manageSaveCommands();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onSuccess([CtVariableReadImpl]target);

            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onFailure([CtVariableReadImpl]error);

            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onFindInFiles() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchPattern = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget textEditor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String selection = [CtInvocationImpl][CtVariableReadImpl]textEditor.getSelectedText();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean multiLineSelection = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]selection.indexOf([CtLiteralImpl]'\n') != [CtUnaryOperatorImpl](-[CtLiteralImpl]1);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]selection.length() != [CtLiteralImpl]0) && [CtUnaryOperatorImpl](![CtVariableReadImpl]multiLineSelection))[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]searchPattern = [CtVariableReadImpl]selection;

        }
        [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.output.find.events.FindInFilesEvent([CtVariableReadImpl]searchPattern));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onActivateSource() [CtBlockImpl]{
        [CtInvocationImpl]onActivateSource([CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onActivateSource([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command afterActivation) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// give the window manager a chance to activate the last source pane
        if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().activateLastFocusedSource())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.R, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]activeEditor_ = [CtVariableReadImpl]target;
                    [CtInvocationImpl]doActivateSource([CtVariableReadImpl]afterActivation);
                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl]doActivateSource([CtVariableReadImpl]afterActivation);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onLayoutZoomSource() [CtBlockImpl]{
        [CtInvocationImpl]onActivateSource([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.events.ZoomPaneEvent([CtLiteralImpl]"Source"));
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doActivateSource([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command afterActivation) [CtBlockImpl]{
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.focus();
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.ensureCursorVisible();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]afterActivation != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]afterActivation.execute();

    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onSwitchToTab() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]view_.showOverflowPopup();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onFirstTab() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() > [CtLiteralImpl]0)[CtBlockImpl]
            [CtInvocationImpl]setPhysicalTabIndex([CtLiteralImpl]0);

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
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() > [CtLiteralImpl]0)[CtBlockImpl]
            [CtInvocationImpl]setPhysicalTabIndex([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() - [CtLiteralImpl]1);

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void nextTabWithWrap() [CtBlockImpl]{
        [CtInvocationImpl]switchToTab([CtLiteralImpl]1, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void prevTabWithWrap() [CtBlockImpl]{
        [CtInvocationImpl]switchToTab([CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]true);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void switchToTab([CtParameterImpl][CtTypeReferenceImpl]int delta, [CtParameterImpl][CtTypeReferenceImpl]boolean wrap) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl]ensureVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int targetIndex = [CtBinaryOperatorImpl][CtInvocationImpl]getPhysicalTabIndex() + [CtVariableReadImpl]delta;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]targetIndex > [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() - [CtLiteralImpl]1)) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]wrap)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]targetIndex = [CtLiteralImpl]0;
            else[CtBlockImpl]
                [CtReturnImpl]return;

        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]targetIndex < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]wrap)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]targetIndex = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() - [CtLiteralImpl]1;
            else[CtBlockImpl]
                [CtReturnImpl]return;

        }
        [CtInvocationImpl]setPhysicalTabIndex([CtVariableReadImpl]targetIndex);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabRight() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]view_.moveTab([CtInvocationImpl]getPhysicalTabIndex(), [CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabLeft() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]view_.moveTab([CtInvocationImpl]getPhysicalTabIndex(), [CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabToFirst() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]view_.moveTab([CtInvocationImpl]getPhysicalTabIndex(), [CtBinaryOperatorImpl][CtInvocationImpl]getPhysicalTabIndex() * [CtUnaryOperatorImpl](-[CtLiteralImpl]1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onMoveTabToLast() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]view_.moveTab([CtInvocationImpl]getPhysicalTabIndex(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() - [CtInvocationImpl]getPhysicalTabIndex()) - [CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onPopoutDoc([CtParameterImpl]final [CtTypeReferenceImpl]PopoutDocEvent e) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// disowning the doc may cause the entire window to close, so defer it
        [CtCommentImpl]// to allow any other popout processing to occur
        [CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.Scheduler.get().scheduleDeferred([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.core.client.Scheduler.ScheduledCommand()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl]disownDoc([CtInvocationImpl][CtVariableReadImpl]e.getDocId());
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onDebugModeChanged([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.environment.events.DebugModeChangedEvent evt) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// when debugging ends, always disengage any active debug highlights
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]evt.debugging()) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.endDebugHighlighting();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onDocWindowChanged([CtParameterImpl]final [CtTypeReferenceImpl]DocWindowChangedEvent e) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getNewWindowId() == [CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.getSourceWindowId()) [CtBlockImpl]{
            [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
            [CtLocalVariableImpl][CtCommentImpl]// look for a collaborative editing session currently running inside
            [CtCommentImpl]// the document being transferred between windows--if we didn't know
            [CtCommentImpl]// about one with the event, try to look it up in the local cache of
            [CtCommentImpl]// source documents
            final [CtTypeReferenceImpl]CollabEditStartParams collabParams = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getCollabParams() == [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().getDocCollabParams([CtInvocationImpl][CtVariableReadImpl]e.getDocId()) : [CtInvocationImpl][CtVariableReadImpl]e.getCollabParams();
            [CtInvocationImpl][CtCommentImpl]// if we're the adopting window, add the doc
            [CtFieldReadImpl]server_.getSourceDocument([CtInvocationImpl][CtVariableReadImpl]e.getDocId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument doc) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]addTab([CtVariableReadImpl]doc, [CtInvocationImpl][CtVariableReadImpl]e.getPos());
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.Scheduler.get().scheduleDeferred([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.gwt.core.client.Scheduler.ScheduledCommand()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                            [CtIfImpl][CtCommentImpl]// if there was a collab session, resume it
                            if ([CtBinaryOperatorImpl][CtVariableReadImpl]collabParams != [CtLiteralImpl]null)[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]target.beginCollabSession([CtInvocationImpl][CtVariableReadImpl]e.getCollabParams());

                        }
                    });
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Document Tab Move Failed", [CtBinaryOperatorImpl][CtLiteralImpl]"Couldn\'t move the tab to this window: \n" + [CtInvocationImpl][CtVariableReadImpl]error.getMessage());
                }
            });
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getOldWindowId() == [CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.getSourceWindowId()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// cancel tab drag if it was occurring
            [CtFieldReadImpl]view_.cancelTabDrag();
            [CtInvocationImpl][CtCommentImpl]// disown this doc if it was our own
            disownDoc([CtInvocationImpl][CtVariableReadImpl]e.getDocId());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void disownDoc([CtParameterImpl][CtTypeReferenceImpl]java.lang.String docId) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]suspendDocumentClose_ = [CtLiteralImpl]true;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i).getId() == [CtVariableReadImpl]docId) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]view_.closeTab([CtVariableReadImpl]i, [CtLiteralImpl]false);
                [CtBreakImpl]break;
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]suspendDocumentClose_ = [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onDocTabDragInitiated([CtParameterImpl]final [CtTypeReferenceImpl]DocTabDragInitiatedEvent event) [CtBlockImpl]{
        [CtInvocationImpl]inEditorForId([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getDragParams().getDocId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.DocTabDragParams params = [CtInvocationImpl][CtVariableReadImpl]event.getDragParams();
                [CtInvocationImpl][CtVariableReadImpl]params.setSourcePosition([CtInvocationImpl][CtVariableReadImpl]editor.currentPosition());
                [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]DocTabDragStartedEvent([CtVariableReadImpl]params));
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onPopoutDocInitiated([CtParameterImpl]final [CtTypeReferenceImpl]PopoutDocInitiatedEvent event) [CtBlockImpl]{
        [CtInvocationImpl]inEditorForId([CtInvocationImpl][CtVariableReadImpl]event.getDocId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// if this is a text editor, ensure that its content is
                [CtCommentImpl]// synchronized with the server before we pop it out
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]editor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget textEditor = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (editor));
                    [CtInvocationImpl][CtVariableReadImpl]textEditor.withSavedDoc([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]textEditor.syncLocalSourceDb();
                            [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]PopoutDocEvent([CtVariableReadImpl]event, [CtInvocationImpl][CtVariableReadImpl]textEditor.currentPosition()));
                        }
                    });
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]PopoutDocEvent([CtVariableReadImpl]event, [CtInvocationImpl][CtVariableReadImpl]editor.currentPosition()));
                }
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onCloseSourceDoc() [CtBlockImpl]{
        [CtInvocationImpl]closeSourceDoc([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtTypeReferenceImpl]void closeSourceDoc([CtParameterImpl][CtTypeReferenceImpl]boolean interactive) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtFieldReadImpl]view_.closeTab([CtInvocationImpl][CtFieldReadImpl]view_.getActiveTabIndex(), [CtVariableReadImpl]interactive);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Execute the given command for each editor, using continuation-passing
     * style. When executed, the CPSEditingTargetCommand needs to execute its
     * own Command parameter to continue the iteration.
     *
     * @param command
     * 		The command to run on each EditingTarget
     */
    private [CtTypeReferenceImpl]void cpsExecuteForEachEditor([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editors, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.CPSEditingTargetCommand command, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command completedCommand) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]SerializedCommandQueue queue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]SerializedCommandQueue();
        [CtForEachImpl][CtCommentImpl]// Clone editors_, since the original may be mutated during iteration
        for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor : [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>([CtVariableReadImpl]editors)) [CtBlockImpl]{
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void cpsExecuteForEachEditor([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editors, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.CPSEditingTargetCommand command) [CtBlockImpl]{
        [CtInvocationImpl]cpsExecuteForEachEditor([CtVariableReadImpl]editors, [CtVariableReadImpl]command, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onSaveAllSourceDocs() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Save all documents in the main window
        cpsExecuteForEachEditor([CtFieldReadImpl]editors_, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.CPSEditingTargetCommand()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.dirtyState().getValue()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]target.save([CtVariableReadImpl]continuation);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                }
            }
        });
        [CtInvocationImpl][CtCommentImpl]// Save all documents in satellite windows
        [CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().saveUnsavedDocuments([CtLiteralImpl]null, [CtLiteralImpl]null);
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
            [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> unsavedTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget>();
            [CtInvocationImpl][CtVariableReadImpl]unsavedTargets.addAll([CtVariableReadImpl]editingTargets);
            [CtInvocationImpl][CtCommentImpl]// show dialog
            [CtFieldReadImpl]view_.showUnsavedChangesDialog([CtVariableReadImpl]title, [CtVariableReadImpl]unsavedTargets, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ui.unsaved.UnsavedChangesDialog.Result result) [CtBlockImpl]{
                    [CtInvocationImpl]saveChanges([CtInvocationImpl][CtVariableReadImpl]result.getSaveTargets(), [CtVariableReadImpl]onCompleted);
                }
            }, [CtVariableReadImpl]onCancelled);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveChanges([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> targets, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// convert back to editing targets
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> saveTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget target : [CtVariableReadImpl]targets) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget saveTarget = [CtInvocationImpl]getEditingTargetForId([CtInvocationImpl][CtVariableReadImpl]target.getId());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]saveTarget != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]saveTargets.add([CtVariableReadImpl]saveTarget);

        }
        [CtInvocationImpl][CtCommentImpl]// execute the save
        [CtCommentImpl]// targets the user chose to save
        [CtCommentImpl]// save each editor
        [CtCommentImpl]// onCompleted at the end
        cpsExecuteForEachEditor([CtVariableReadImpl]saveTargets, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.CPSEditingTargetCommand()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget saveTarget, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]saveTarget.save([CtVariableReadImpl]continuation);
            }
        }, [CtVariableReadImpl]onCompleted);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget getEditingTargetForId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtFieldReadImpl]editors_)[CtBlockImpl]
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]id == [CtInvocationImpl][CtVariableReadImpl]target.getId())[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]target;


        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onCloseAllSourceDocs() [CtBlockImpl]{
        [CtInvocationImpl]closeAllSourceDocs([CtLiteralImpl]"Close All", [CtLiteralImpl]null, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onCloseOtherSourceDocs() [CtBlockImpl]{
        [CtInvocationImpl]closeAllSourceDocs([CtLiteralImpl]"Close Other", [CtLiteralImpl]null, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeAllSourceDocs([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String caption, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted, [CtParameterImpl]final [CtTypeReferenceImpl]boolean excludeActive) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow() && [CtUnaryOperatorImpl](![CtVariableReadImpl]excludeActive)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// if this is the main window, close docs in the satellites first
            [CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().closeAllSatelliteDocs([CtVariableReadImpl]caption, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtInvocationImpl]closeAllLocalSourceDocs([CtVariableReadImpl]caption, [CtVariableReadImpl]onCompleted, [CtVariableReadImpl]excludeActive);
                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// this is a satellite (or we don't need to query satellites)--just
            [CtCommentImpl]// close our own tabs
            closeAllLocalSourceDocs([CtVariableReadImpl]caption, [CtVariableReadImpl]onCompleted, [CtVariableReadImpl]excludeActive);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void closeAllLocalSourceDocs([CtParameterImpl][CtTypeReferenceImpl]java.lang.String caption, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted, [CtParameterImpl]final [CtTypeReferenceImpl]boolean excludeActive) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// save active editor for exclusion (it changes as we close tabs)
        final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget activeEditor = [CtFieldReadImpl]activeEditor_;
        [CtLocalVariableImpl][CtCommentImpl]// collect up a list of dirty documents
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> dirtyTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtFieldReadImpl]editors_) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]excludeActive && [CtBinaryOperatorImpl]([CtVariableReadImpl]target == [CtVariableReadImpl]activeEditor))[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.dirtyState().getValue())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]dirtyTargets.add([CtVariableReadImpl]target);

        }
        [CtLocalVariableImpl][CtCommentImpl]// create a command used to close all tabs
        final [CtTypeReferenceImpl]com.google.gwt.user.client.Command closeAllTabsCommand = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl]cpsExecuteForEachEditor([CtFieldReadImpl]editors_, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.CPSEditingTargetCommand()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]excludeActive && [CtBinaryOperatorImpl]([CtVariableReadImpl]target == [CtVariableReadImpl]activeEditor)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]continuation.execute();
                            [CtReturnImpl]return;
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]view_.closeTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget(), [CtLiteralImpl]false, [CtVariableReadImpl]continuation);
                        }
                    }
                });
            }
        };
        [CtInvocationImpl][CtCommentImpl]// save targets
        saveEditingTargetsWithPrompt([CtVariableReadImpl]caption, [CtVariableReadImpl]dirtyTargets, [CtInvocationImpl][CtTypeAccessImpl]CommandUtil.join([CtVariableReadImpl]closeAllTabsCommand, [CtVariableReadImpl]onCompleted), [CtLiteralImpl]null);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isUnsavedTarget([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target, [CtParameterImpl][CtTypeReferenceImpl]int type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean fileBacked = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getPath() != [CtLiteralImpl]null;
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.dirtyState().getValue() && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.TYPE_FILE_BACKED) && [CtVariableReadImpl]fileBacked) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.TYPE_UNTITLED) && [CtUnaryOperatorImpl](![CtVariableReadImpl]fileBacked)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> getUnsavedChanges([CtParameterImpl][CtTypeReferenceImpl]int type) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getUnsavedChanges([CtVariableReadImpl]type, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> getUnsavedChanges([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ids) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> targets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget>();
        [CtIfImpl][CtCommentImpl]// if this is the main window, collect all unsaved changes from
        [CtCommentImpl]// the satellite windows as well
        if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]targets.addAll([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().getAllSatelliteUnsavedChanges([CtVariableReadImpl]type));
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtFieldReadImpl]editors_) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// no need to save targets which are up-to-date
            if ([CtUnaryOperatorImpl]![CtInvocationImpl]isUnsavedTarget([CtVariableReadImpl]target, [CtVariableReadImpl]type))[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtIfImpl][CtCommentImpl]// if we've requested the save of specific documents, screen
            [CtCommentImpl]// out documents not within the requested id set
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]ids != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]ids.contains([CtInvocationImpl][CtVariableReadImpl]target.getId())))[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtInvocationImpl][CtVariableReadImpl]targets.add([CtVariableReadImpl]target);
        }
        [CtReturnImpl]return [CtVariableReadImpl]targets;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void saveUnsavedDocuments([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtInvocationImpl]saveUnsavedDocuments([CtLiteralImpl]null, [CtVariableReadImpl]onCompleted);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void saveUnsavedDocuments([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ids, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command saveAllLocal = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl]saveChanges([CtInvocationImpl]getUnsavedChanges([CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.TYPE_FILE_BACKED, [CtVariableReadImpl]ids), [CtVariableReadImpl]onCompleted);
            }
        };
        [CtIfImpl][CtCommentImpl]// if this is the main source window, save all files in satellites first
        if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow())[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().saveUnsavedDocuments([CtVariableReadImpl]ids, [CtVariableReadImpl]saveAllLocal);
        else[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]saveAllLocal.execute();

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void saveWithPrompt([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget target, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCancelled) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().getWindowIdOfDocId([CtInvocationImpl][CtVariableReadImpl]target.getId()).isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// we are the main window, and we're being asked to save a document
            [CtCommentImpl]// that's in a different window; perform the save over there
            [CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().saveWithPrompt([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesItem.create([CtVariableReadImpl]target), [CtVariableReadImpl]onCompleted);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editingTarget = [CtInvocationImpl]getEditingTargetForId([CtInvocationImpl][CtVariableReadImpl]target.getId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]editingTarget != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]editingTarget.saveWithPrompt([CtVariableReadImpl]onCompleted, [CtVariableReadImpl]onCancelled);

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.google.gwt.user.client.Command revertUnsavedChangesBeforeExitCommand([CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl]handleUnsavedChangesBeforeExit([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget>(), [CtVariableReadImpl]onCompleted);
            }
        };
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void handleUnsavedChangesBeforeExit([CtParameterImpl]final [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.UnsavedChangesTarget> saveTargets, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// first handle saves, then revert unsaved, then callback on completed
        final [CtTypeReferenceImpl]com.google.gwt.user.client.Command completed = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// revert unsaved
                revertUnsavedTargets([CtVariableReadImpl]onCompleted);
            }
        };
        [CtIfImpl][CtCommentImpl]// if this is the main source window, let satellite windows save any
        [CtCommentImpl]// changes first
        if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().handleUnsavedChangesBeforeExit([CtVariableReadImpl]saveTargets, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtInvocationImpl]saveChanges([CtVariableReadImpl]saveTargets, [CtVariableReadImpl]completed);
                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl]saveChanges([CtVariableReadImpl]saveTargets, [CtVariableReadImpl]completed);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.Display getView() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]view_;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void revertActiveDocument() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath() != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.revertChanges([CtLiteralImpl]null);

        [CtInvocationImpl][CtCommentImpl]// Ensure that the document is in view
        [CtFieldReadImpl]activeEditor_.ensureCursorVisible();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void revertUnsavedTargets([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// collect up unsaved targets
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> unsavedTargets = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtFieldReadImpl]editors_)[CtBlockImpl]
            [CtIfImpl]if ([CtInvocationImpl]isUnsavedTarget([CtVariableReadImpl]target, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.TYPE_FILE_BACKED))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]unsavedTargets.add([CtVariableReadImpl]target);


        [CtInvocationImpl][CtCommentImpl]// revert all of them
        [CtCommentImpl]// targets the user chose not to save
        [CtCommentImpl]// save each editor
        [CtCommentImpl]// onCompleted at the end
        cpsExecuteForEachEditor([CtVariableReadImpl]unsavedTargets, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.CPSEditingTargetCommand()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget saveTarget, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command continuation) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]saveTarget.getPath() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// file backed document -- revert it
                    [CtVariableReadImpl]saveTarget.revertChanges([CtVariableReadImpl]continuation);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// untitled document -- just close the tab non-interactively
                    [CtFieldReadImpl]view_.closeTab([CtInvocationImpl][CtVariableReadImpl]saveTarget.asWidget(), [CtLiteralImpl]false, [CtVariableReadImpl]continuation);
                }
            }
        }, [CtVariableReadImpl]onCompleted);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onOpenSourceDoc() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]fileDialogs_.openFile([CtLiteralImpl]"Open File", [CtFieldReadImpl]fileContext_, [CtInvocationImpl][CtFieldReadImpl]workbenchContext_.getDefaultFileDialogDir(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.ProgressOperationWithInput<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem>()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void execute([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem input, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.ProgressIndicator indicator) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]input == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtInvocationImpl][CtFieldReadImpl]workbenchContext_.setDefaultFileDialogDir([CtInvocationImpl][CtVariableReadImpl]input.getParentPath());
                [CtInvocationImpl][CtVariableReadImpl]indicator.onCompleted();
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.Scheduler.get().scheduleDeferred([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.core.client.Scheduler.ScheduledCommand()[CtClassImpl] {
                    [CtMethodImpl]public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.openFile([CtVariableReadImpl]input);
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onNewDocumentWithCode([CtParameterImpl]final [CtTypeReferenceImpl]NewDocumentWithCodeEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// determine the type
        final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.EditableFileType docType;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getType() == [CtFieldReadImpl]NewDocumentWithCodeEvent.R_SCRIPT)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]docType = [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.FileTypeRegistry.R;
        else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getType() == [CtFieldReadImpl]NewDocumentWithCodeEvent.SQL)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]docType = [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.FileTypeRegistry.SQL;
        else[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]docType = [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.FileTypeRegistry.RMARKDOWN;

        [CtLocalVariableImpl][CtCommentImpl]// command to create and run the new doc
        [CtTypeReferenceImpl]com.google.gwt.user.client.Command newDocCommand = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl]newDoc([CtVariableReadImpl]docType, [CtInvocationImpl][CtVariableReadImpl]event.getCode(), [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                    [CtMethodImpl]public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget arg) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editingTarget = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (arg));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getCursorPosition() != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]editingTarget.navigateToPosition([CtInvocationImpl][CtVariableReadImpl]event.getCursorPosition(), [CtLiteralImpl]false);
                        }
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]event.getExecute()) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]docType.equals([CtTypeAccessImpl]FileTypeRegistry.R)) [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.executeToCurrentLine().execute();
                                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.activateSource().execute();
                            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]docType.equals([CtTypeAccessImpl]FileTypeRegistry.SQL)) [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.previewSql().execute();
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.executePreviousChunks().execute();
                            }
                        }
                    }
                });
            }
        };
        [CtIfImpl][CtCommentImpl]// do it
        if ([CtInvocationImpl][CtVariableReadImpl]docType.equals([CtTypeAccessImpl]FileTypeRegistry.R)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]newDocCommand.execute();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]dependencyManager_.withRMarkdown([CtLiteralImpl]"R Notebook", [CtLiteralImpl]"Create R Notebook", [CtVariableReadImpl]newDocCommand);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onNewRPlumberDoc() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]dependencyManager_.withRPlumber([CtLiteralImpl]"Creating R Plumber API", [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]NewPlumberAPI widget = [CtConstructorCallImpl]new [CtTypeReferenceImpl]NewPlumberAPI([CtLiteralImpl]"New Plumber API", [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl][CtTypeReferenceImpl]NewPlumberAPI.Result>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]NewPlumberAPI.Result input) [CtBlockImpl]{
                        [CtInvocationImpl]doNewRPlumberAPI([CtVariableReadImpl]input);
                    }
                });
                [CtInvocationImpl][CtVariableReadImpl]widget.showModal();
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onOpenSourceFile([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.events.OpenSourceFileEvent event) [CtBlockImpl]{
        [CtInvocationImpl]doOpenSourceFile([CtInvocationImpl][CtVariableReadImpl]event.getFile(), [CtInvocationImpl][CtVariableReadImpl]event.getFileType(), [CtInvocationImpl][CtVariableReadImpl]event.getPosition(), [CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]event.getNavigationMethod(), [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onOpenPresentationSourceFile([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.events.OpenPresentationSourceFileEvent event) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// don't do the navigation if the active document is a source
        [CtCommentImpl]// file from this presentation module
        doOpenSourceFile([CtInvocationImpl][CtVariableReadImpl]event.getFile(), [CtInvocationImpl][CtVariableReadImpl]event.getFileType(), [CtInvocationImpl][CtVariableReadImpl]event.getPosition(), [CtInvocationImpl][CtVariableReadImpl]event.getPattern(), [CtTypeAccessImpl]NavigationMethods.HIGHLIGHT_LINE, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onEditPresentationSource([CtParameterImpl]final [CtTypeReferenceImpl]EditPresentationSourceEvent event) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]openingForSourceNavigation_ = [CtLiteralImpl]true;
        [CtInvocationImpl]openFile([CtInvocationImpl][CtVariableReadImpl]event.getSourceFile(), [CtTypeAccessImpl]FileTypeRegistry.RPRESENTATION, [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]openingForSourceNavigation_ = [CtLiteralImpl]false;
                [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetPresentationHelper.navigateToSlide([CtVariableReadImpl]editor, [CtInvocationImpl][CtVariableReadImpl]event.getSlideIndex());
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void forceLoad() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor.preload();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCurrentDocId() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getActiveEditor() == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getActiveEditor().getId();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCurrentDocPath() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getActiveEditor() == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getActiveEditor().getPath();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doOpenSourceFile([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]FilePosition position, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String pattern, [CtParameterImpl]final [CtTypeReferenceImpl]int navMethod, [CtParameterImpl]final [CtTypeReferenceImpl]boolean forceHighlightMode) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// if the navigation should happen in another window, do that instead
        [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceWindowManager.NavigationResult navResult = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().navigateToFile([CtVariableReadImpl]file, [CtVariableReadImpl]position, [CtVariableReadImpl]navMethod);
        [CtIfImpl][CtCommentImpl]// we navigated externally, just skip this
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]navResult.getType() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceWindowManager.NavigationResult.RESULT_NAVIGATED)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl][CtCommentImpl]// we're about to open in this window--if it's the main window, focus it
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow() && [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.hasDesktopFrame())[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.getFrame().bringMainFrameToFront();

        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isDebugNavigation = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]navMethod == [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.DEBUG_STEP) || [CtBinaryOperatorImpl]([CtVariableReadImpl]navMethod == [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.DEBUG_END);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editingTargetAction = [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// the rstudioapi package can use the proxy (-1, -1) position to
                [CtCommentImpl]// indicate that source navigation should not occur; ie, we should
                [CtCommentImpl]// preserve whatever position was used in the document earlier
                [CtTypeReferenceImpl]boolean navigateToPosition = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]position != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]position.getLine() != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]position.getColumn() != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)));
                [CtIfImpl]if ([CtVariableReadImpl]navigateToPosition) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition endPosition = [CtLiteralImpl]null;
                    [CtIfImpl]if ([CtVariableReadImpl]isDebugNavigation) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]DebugFilePosition filePos = [CtInvocationImpl](([CtTypeReferenceImpl]DebugFilePosition) ([CtVariableReadImpl]position.cast()));
                        [CtAssignmentImpl][CtVariableWriteImpl]endPosition = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition.create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]filePos.getEndLine() - [CtLiteralImpl]1, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]filePos.getEndColumn() + [CtLiteralImpl]1);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.hasDesktopFrame() && [CtBinaryOperatorImpl]([CtVariableReadImpl]navMethod != [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.DEBUG_END))[CtBlockImpl]
                            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.getFrame().bringMainFrameToFront();

                    }
                    [CtInvocationImpl]navigate([CtVariableReadImpl]target, [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition.create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]position.getLine() - [CtLiteralImpl]1, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]position.getColumn() - [CtLiteralImpl]1), [CtVariableReadImpl]endPosition);
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pattern != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position pos = [CtInvocationImpl][CtVariableReadImpl]target.search([CtVariableReadImpl]pattern);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pos != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl]navigate([CtVariableReadImpl]target, [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition.create([CtInvocationImpl][CtVariableReadImpl]pos.getRow(), [CtLiteralImpl]0), [CtLiteralImpl]null);
                    }
                }
            }

            [CtMethodImpl]private [CtTypeReferenceImpl]void navigate([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition srcPosition, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition srcEndPosition) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.Scheduler.get().scheduleDeferred([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.core.client.Scheduler.ScheduledCommand()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]navMethod == [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.DEBUG_STEP) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]target.highlightDebugLocation([CtVariableReadImpl]srcPosition, [CtVariableReadImpl]srcEndPosition, [CtLiteralImpl]true);
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]navMethod == [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.DEBUG_END) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]target.endDebugHighlighting();
                        } else [CtBlockImpl]{
                            [CtIfImpl][CtCommentImpl]// force highlight mode if requested
                            if ([CtVariableReadImpl]forceHighlightMode)[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]target.forceLineHighlighting();

                            [CtLocalVariableImpl][CtCommentImpl]// now navigate to the new position
                            [CtTypeReferenceImpl]boolean highlight = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]navMethod == [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.HIGHLIGHT_LINE) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.highlightSelectedLine().getValue());
                            [CtInvocationImpl][CtVariableReadImpl]target.navigateToPosition([CtVariableReadImpl]srcPosition, [CtLiteralImpl]false, [CtVariableReadImpl]highlight);
                        }
                    }
                });
            }
        };
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]navResult.getType() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceWindowManager.NavigationResult.RESULT_RELOCATE) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]server_.getSourceDocument([CtInvocationImpl][CtVariableReadImpl]navResult.getDocId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument doc) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]editingTargetAction.execute([CtInvocationImpl]addTab([CtVariableReadImpl]doc, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_REPLAY));
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showErrorMessage([CtLiteralImpl]"Document Tab Move Failed", [CtBinaryOperatorImpl][CtLiteralImpl]"Couldn\'t move the tab to this window: \n" + [CtInvocationImpl][CtVariableReadImpl]error.getMessage());
                }
            });
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem> action = [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// set flag indicating we are opening for a source navigation
                [CtFieldWriteImpl]openingForSourceNavigation_ = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]position != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]pattern != [CtLiteralImpl]null);
                [CtInvocationImpl]openFile([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtLambdaImpl]([CtParameterImpl] target) -> [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReferenceImpl]openingForSourceNavigation_ = [CtLiteralImpl]false;
                    [CtInvocationImpl][CtVariableReadImpl]editingTargetAction.execute([CtVariableReadImpl]target);
                });
            }
        };
        [CtIfImpl][CtCommentImpl]// If this is a debug navigation, we only want to treat this as a full
        [CtCommentImpl]// file open if the file isn't already open; otherwise, we can just
        [CtCommentImpl]// highlight in place.
        if ([CtVariableReadImpl]isDebugNavigation) [CtBlockImpl]{
            [CtInvocationImpl]setPendingDebugSelection();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtVariableReadImpl]target.getPath();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]path != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]path.equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]file.getPath())) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// the file's open; just update its highlighting
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]navMethod == [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.DEBUG_END) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]target.endDebugHighlighting();
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]i);
                        [CtInvocationImpl][CtVariableReadImpl]editingTargetAction.execute([CtVariableReadImpl]target);
                    }
                    [CtReturnImpl]return;
                }
            }
            [CtIfImpl][CtCommentImpl]// If we're here, the target file wasn't open in an editor. Don't
            [CtCommentImpl]// open a file just to turn off debug highlighting in the file!
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]navMethod == [CtFieldReadImpl]org.rstudio.studio.client.common.filetypes.model.NavigationMethods.DEBUG_END)[CtBlockImpl]
                [CtReturnImpl]return;

        }
        [CtIfImpl][CtCommentImpl]// Warning: event.getFile() can be null (e.g. new Sweave document)
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]file != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]file.getLength() < [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]statQueue_.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.StatFileEntry([CtVariableReadImpl]file, [CtVariableReadImpl]action));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]statQueue_.size() == [CtLiteralImpl]1)[CtBlockImpl]
                [CtInvocationImpl]processStatQueue();

        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]action.execute([CtVariableReadImpl]file);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processStatQueue() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]statQueue_.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.StatFileEntry entry = [CtInvocationImpl][CtFieldReadImpl]statQueue_.peek();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command processNextEntry = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]statQueue_.remove();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]statQueue_.isEmpty())[CtBlockImpl]
                    [CtInvocationImpl]processStatQueue();

            }
        };
        [CtInvocationImpl][CtFieldReadImpl]server_.stat([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]entry.file.getPath(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem response) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]processNextEntry.execute();
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]entry.action.execute([CtVariableReadImpl]response);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]processNextEntry.execute();
                [CtInvocationImpl][CtCommentImpl]// Couldn't stat the file? Proceed anyway. If the file doesn't
                [CtCommentImpl]// exist, we'll let the downstream code be the one to show the
                [CtCommentImpl]// error.
                [CtFieldReadImpl][CtVariableReadImpl]entry.action.execute([CtFieldReadImpl][CtVariableReadImpl]entry.file);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openFile([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file) [CtBlockImpl]{
        [CtInvocationImpl]openFile([CtVariableReadImpl]file, [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.getTextTypeForFile([CtVariableReadImpl]file));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openFile([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType) [CtBlockImpl]{
        [CtInvocationImpl]openFile([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget arg) [CtBlockImpl]{
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openFile([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> executeOnSuccess) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// add this work to the queue
        [CtFieldReadImpl]openFileQueue_.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.OpenFileEntry([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtVariableReadImpl]executeOnSuccess));
        [CtIfImpl][CtCommentImpl]// begin queue processing if it's the only work in the queue
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]openFileQueue_.size() == [CtLiteralImpl]1)[CtBlockImpl]
            [CtInvocationImpl]processOpenFileQueue();

    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processOpenFileQueue() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// no work to do
        if ([CtInvocationImpl][CtFieldReadImpl]openFileQueue_.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtCommentImpl]// find the first work unit
        final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.OpenFileEntry entry = [CtInvocationImpl][CtFieldReadImpl]openFileQueue_.peek();
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void openNotebook([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem rmdFile, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocumentResult doc, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]doc.getDocPath())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// this happens if we created the R Markdown file, or if the R Markdown
            [CtCommentImpl]// file on disk matched the one inside the notebook
            openFileFromServer([CtVariableReadImpl]rmdFile, [CtTypeAccessImpl]FileTypeRegistry.RMARKDOWN, [CtVariableReadImpl]resultCallback);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]doc.getDocId())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// this happens when we have to open an untitled buffer for the the
            [CtCommentImpl]// notebook (usually because the of a conflict between the Rmd on disk
            [CtCommentImpl]// and the one in the .nb.html file)
            [CtFieldReadImpl]server_.getSourceDocument([CtInvocationImpl][CtVariableReadImpl]doc.getDocId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument doc) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// create the editor
                    [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]addTab([CtVariableReadImpl]doc, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void openNotebook([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem rnbFile, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
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
                [CtInvocationImpl][CtFieldReadImpl]server_.extractRmdFromNotebook([CtVariableReadImpl]rnbPath, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocumentResult>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocumentResult doc) [CtBlockImpl]{
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

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean openFileAlreadyOpen([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtForImpl][CtCommentImpl]// check to see if any local editors have the file open
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String thisPath = [CtInvocationImpl][CtVariableReadImpl]target.getPath();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]thisPath != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]thisPath.equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]file.getPath())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]i);
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().add([CtVariableReadImpl]thisPath);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onSuccess([CtVariableReadImpl]target);

                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtCommentImpl]// top-level wrapper for opening files. takes care of:
    [CtCommentImpl]// - making sure the view is visible
    [CtCommentImpl]// - checking whether it is already open and re-selecting its tab
    [CtCommentImpl]// - prohibit opening very large files (>500KB)
    [CtCommentImpl]// - confirmation of opening large files (>100KB)
    [CtCommentImpl]// - finally, actually opening the file from the server
    [CtCommentImpl]// via the call to the lower level openFile method
    private [CtTypeReferenceImpl]void openFile([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtInvocationImpl]ensureVisible([CtLiteralImpl]true);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fileType.isRNotebook()) [CtBlockImpl]{
            [CtInvocationImpl]openNotebook([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtVariableReadImpl]resultCallback);
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void openFileFromServer([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> resultCallback) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command dismissProgress = [CtInvocationImpl][CtFieldReadImpl]globalDisplay_.showProgress([CtLiteralImpl]"Opening file...");
        [CtInvocationImpl][CtFieldReadImpl]server_.openDocument([CtInvocationImpl][CtVariableReadImpl]file.getPath(), [CtInvocationImpl][CtVariableReadImpl]fileType.getTypeId(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userPrefs_.defaultEncoding().getValue(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]dismissProgress.execute();
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().remove([CtInvocationImpl][CtVariableReadImpl]file.getPath());
                [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onFailure([CtVariableReadImpl]error);

            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument document) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// if we are opening for a source navigation then we
                [CtCommentImpl]// need to force Rmds into source mode
                if ([CtFieldReadImpl]openingForSourceNavigation_) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]document.getProperties()._setBoolean([CtTypeAccessImpl]TextEditingTarget.RMD_VISUAL_MODE, [CtLiteralImpl]false);
                }
                [CtInvocationImpl][CtVariableReadImpl]dismissProgress.execute();
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pMruList_.get().add([CtInvocationImpl][CtVariableReadImpl]document.getPath());
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]addTab([CtVariableReadImpl]document, [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.Source.OPEN_INTERACTIVE);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCallback != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]resultCallback.onSuccess([CtVariableReadImpl]target);

            }
        });
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Widget createWidget([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]target.asWidget();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget addTab([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument doc, [CtParameterImpl][CtTypeReferenceImpl]int mode) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]addTab([CtVariableReadImpl]doc, [CtLiteralImpl]false, [CtVariableReadImpl]mode);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget addTab([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument doc, [CtParameterImpl][CtTypeReferenceImpl]boolean atEnd, [CtParameterImpl][CtTypeReferenceImpl]int mode) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// by default, add at the tab immediately after the current tab
        return [CtInvocationImpl]addTab([CtVariableReadImpl]doc, [CtConditionalImpl][CtVariableReadImpl]atEnd ? [CtLiteralImpl]null : [CtBinaryOperatorImpl][CtInvocationImpl]getPhysicalTabIndex() + [CtLiteralImpl]1, [CtVariableReadImpl]mode);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget addTab([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument doc, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer position, [CtParameterImpl][CtTypeReferenceImpl]int mode) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String defaultNamePrefix = [CtInvocationImpl][CtFieldReadImpl]editingTargetSource_.getDefaultNamePrefix([CtVariableReadImpl]doc);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editingTargetSource_.getEditingTarget([CtVariableReadImpl]doc, [CtFieldReadImpl]fileContext_, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String get() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]getNextDefaultName([CtVariableReadImpl]defaultNamePrefix);
            }
        });
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]Widget widget = [CtInvocationImpl]createWidget([CtVariableReadImpl]target);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]position == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]editors_.add([CtVariableReadImpl]target);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// we're inserting into an existing permuted tabset -- push aside
            [CtCommentImpl]// any tabs physically to the right of this tab
            [CtFieldReadImpl]editors_.add([CtVariableReadImpl]position, [CtVariableReadImpl]target);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtInvocationImpl][CtFieldReadImpl]tabOrder_.get([CtVariableReadImpl]i);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pos >= [CtVariableReadImpl]position)[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]tabOrder_.set([CtVariableReadImpl]i, [CtBinaryOperatorImpl][CtVariableReadImpl]pos + [CtLiteralImpl]1);

            }
            [CtInvocationImpl][CtCommentImpl]// add this tab in its "natural" position
            [CtFieldReadImpl]tabOrder_.add([CtVariableReadImpl]position, [CtVariableReadImpl]position);
        }
        [CtInvocationImpl][CtCommentImpl]// used as tooltip, if non-null
        [CtFieldReadImpl]view_.addTab([CtVariableReadImpl]widget, [CtInvocationImpl][CtVariableReadImpl]target.getIcon(), [CtInvocationImpl][CtVariableReadImpl]target.getId(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getName().getValue(), [CtInvocationImpl][CtVariableReadImpl]target.getTabTooltip(), [CtVariableReadImpl]position, [CtLiteralImpl]true);
        [CtInvocationImpl]fireDocTabsChanged();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getName().addValueChangeHandler([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.event.logical.shared.ValueChangeHandler<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onValueChange([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.event.logical.shared.ValueChangeEvent<[CtTypeReferenceImpl]java.lang.String> event) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]view_.renameTab([CtVariableReadImpl]widget, [CtInvocationImpl][CtVariableReadImpl]target.getIcon(), [CtInvocationImpl][CtVariableReadImpl]event.getValue(), [CtInvocationImpl][CtVariableReadImpl]target.getPath());
                [CtInvocationImpl]fireDocTabsChanged();
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]view_.setDirty([CtVariableReadImpl]widget, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.dirtyState().getValue());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.dirtyState().addValueChangeHandler([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.event.logical.shared.ValueChangeHandler<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onValueChange([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.event.logical.shared.ValueChangeEvent<[CtTypeReferenceImpl]java.lang.Boolean> event) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]view_.setDirty([CtVariableReadImpl]widget, [CtInvocationImpl][CtVariableReadImpl]event.getValue());
                [CtInvocationImpl]manageCommands();
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]target.addEnsureVisibleHandler([CtNewClassImpl]new [CtTypeReferenceImpl]EnsureVisibleHandler()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onEnsureVisible([CtParameterImpl][CtTypeReferenceImpl]EnsureVisibleEvent event) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]widget);
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]target.addCloseHandler([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.event.logical.shared.CloseHandler<[CtTypeReferenceImpl]java.lang.Void>()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]void onClose([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.event.logical.shared.CloseEvent<[CtTypeReferenceImpl]java.lang.Void> voidCloseEvent) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]view_.closeTab([CtVariableReadImpl]widget, [CtLiteralImpl]false);
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]SourceDocAddedEvent([CtVariableReadImpl]doc, [CtVariableReadImpl]mode));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]target instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) && [CtInvocationImpl][CtVariableReadImpl]doc.isReadOnly()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (target)).setIntendedAsReadOnly([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.toList([CtInvocationImpl][CtVariableReadImpl]doc.getReadOnlyAlternatives()));
        }
        [CtIfImpl][CtCommentImpl]// adding a tab may enable commands that are only available when
        [CtCommentImpl]// multiple documents are open; if this is the second document, go check
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]editors_.size() == [CtLiteralImpl]2)[CtBlockImpl]
            [CtInvocationImpl]manageMultiTabCommands();

        [CtIfImpl][CtCommentImpl]// if the target had an editing session active, attempt to resume it
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doc.getCollabParams() != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]target.beginCollabSession([CtInvocationImpl][CtVariableReadImpl]doc.getCollabParams());

        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getNextDefaultName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultNamePrefix) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtVariableReadImpl]defaultNamePrefix)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]defaultNamePrefix = [CtLiteralImpl]"Untitled";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int max = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtFieldReadImpl]editors_) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getName().getValue();
            [CtAssignmentImpl][CtVariableWriteImpl]max = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]max, [CtInvocationImpl]getUntitledNum([CtVariableReadImpl]name, [CtVariableReadImpl]defaultNamePrefix));
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]defaultNamePrefix + [CtBinaryOperatorImpl]([CtVariableReadImpl]max + [CtLiteralImpl]1);
    }

    [CtMethodImpl][CtCommentImpl]/* -{
    var match = (new RegExp("^" + prefix + "([0-9]{1,5})$")).exec(name);
    if (!match)
    return 0;
    return parseInt(match[1]);
    }-
     */
    private native final [CtTypeReferenceImpl]int getUntitledNum([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix);

    [CtMethodImpl]public [CtTypeReferenceImpl]void onInsertSource([CtParameterImpl]final [CtTypeReferenceImpl]InsertSourceEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.executeCode().isEnabled()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget textEditor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
            [CtInvocationImpl][CtVariableReadImpl]textEditor.insertCode([CtInvocationImpl][CtVariableReadImpl]event.getCode(), [CtInvocationImpl][CtVariableReadImpl]event.isBlock());
        } else [CtBlockImpl]{
            [CtInvocationImpl]newDoc([CtTypeAccessImpl]FileTypeRegistry.R, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget arg) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (arg)).insertCode([CtInvocationImpl][CtVariableReadImpl]event.getCode(), [CtInvocationImpl][CtVariableReadImpl]event.isBlock());
                }
            });
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onTabClosing([CtParameterImpl]final [CtTypeReferenceImpl]TabClosingEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editors_.get([CtInvocationImpl][CtVariableReadImpl]event.getTabIndex());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]target.onBeforeDismiss())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]event.cancel();

    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onTabClose([CtParameterImpl][CtTypeReferenceImpl]TabCloseEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// can't proceed if there is no active editor
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getTabIndex() >= [CtInvocationImpl][CtFieldReadImpl]editors_.size())[CtBlockImpl]
            [CtReturnImpl]return;
        [CtCommentImpl]// Seems like this should never happen...?

        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String activeEditorId = [CtInvocationImpl][CtFieldReadImpl]activeEditor_.getId();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtInvocationImpl][CtVariableReadImpl]event.getTabIndex()).getId() == [CtVariableReadImpl]activeEditorId) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// scan the source navigation history for an entry that can
            [CtCommentImpl]// be used as the next active tab (anything that doesn't have
            [CtCommentImpl]// the same document id as the currently active tab)
            [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigation srcNav = [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.scanBack([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigationHistory.Filter()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]boolean includeEntry([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigation navigation) [CtBlockImpl]{
                    [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]navigation.getDocumentId() != [CtVariableReadImpl]activeEditorId;
                }
            });
            [CtIfImpl][CtCommentImpl]// see if the source navigation we found corresponds to an active
            [CtCommentImpl]// tab -- if it does then set this on the event
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]srcNav != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]srcNav.getDocumentId() == [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i).getId()) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]i);
                        [CtBreakImpl]break;
                    }
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void closeTabIndex([CtParameterImpl][CtTypeReferenceImpl]int idx, [CtParameterImpl][CtTypeReferenceImpl]boolean closeDocument) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editors_.remove([CtVariableReadImpl]idx);
        [CtInvocationImpl][CtFieldReadImpl]tabOrder_.remove([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Integer([CtVariableReadImpl]idx));
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]tabOrder_.get([CtVariableReadImpl]i) > [CtVariableReadImpl]idx) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]tabOrder_.set([CtVariableReadImpl]i, [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]tabOrder_.get([CtVariableReadImpl]i) - [CtLiteralImpl]1);
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]target.onDismiss([CtConditionalImpl][CtVariableReadImpl]closeDocument ? [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget.DISMISS_TYPE_CLOSE : [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget.DISMISS_TYPE_MOVE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ == [CtVariableReadImpl]target) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.onDeactivate();
            [CtAssignmentImpl][CtFieldWriteImpl]activeEditor_ = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtVariableReadImpl]closeDocument) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]DocTabClosedEvent([CtInvocationImpl][CtVariableReadImpl]target.getId()));
            [CtInvocationImpl][CtFieldReadImpl]server_.closeDocument([CtInvocationImpl][CtVariableReadImpl]target.getId(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
        }
        [CtInvocationImpl]manageCommands();
        [CtInvocationImpl]fireDocTabsChanged();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.clear();
            [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]LastSourceDocClosedEvent());
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onTabClosed([CtParameterImpl][CtTypeReferenceImpl]TabClosedEvent event) [CtBlockImpl]{
        [CtInvocationImpl]closeTabIndex([CtInvocationImpl][CtVariableReadImpl]event.getTabIndex(), [CtUnaryOperatorImpl]![CtFieldReadImpl]suspendDocumentClose_);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onTabReorder([CtParameterImpl][CtTypeReferenceImpl]TabReorderEvent event) [CtBlockImpl]{
        [CtInvocationImpl]syncTabOrder();
        [CtIfImpl][CtCommentImpl]// sanity check: make sure we're moving from a valid location and to a
        [CtCommentImpl]// valid location
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getOldPos() < [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getOldPos() >= [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size())) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getNewPos() < [CtLiteralImpl]0)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getNewPos() >= [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size())) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// remove the tab from its old position
        [CtTypeReferenceImpl]int idx = [CtInvocationImpl][CtFieldReadImpl]tabOrder_.get([CtInvocationImpl][CtVariableReadImpl]event.getOldPos());
        [CtInvocationImpl][CtFieldReadImpl]tabOrder_.remove([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Integer([CtVariableReadImpl]idx));[CtCommentImpl]// force type box

        [CtInvocationImpl][CtCommentImpl]// add it to its new position
        [CtFieldReadImpl]tabOrder_.add([CtInvocationImpl][CtVariableReadImpl]event.getNewPos(), [CtVariableReadImpl]idx);
        [CtLocalVariableImpl][CtCommentImpl]// sort the document IDs and send to the server
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> ids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ids.add([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtInvocationImpl][CtFieldReadImpl]tabOrder_.get([CtVariableReadImpl]i)).getId());
        }
        [CtInvocationImpl][CtFieldReadImpl]server_.setDocOrder([CtVariableReadImpl]ids, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
        [CtInvocationImpl][CtCommentImpl]// activate the tab
        setPhysicalTabIndex([CtInvocationImpl][CtVariableReadImpl]event.getNewPos());
        [CtInvocationImpl]fireDocTabsChanged();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void syncTabOrder() [CtBlockImpl]{
        [CtForImpl][CtCommentImpl]// ensure the tab order is synced to the list of editors
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size(); [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]tabOrder_.add([CtVariableReadImpl]i);
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]tabOrder_.remove([CtVariableReadImpl]i);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void fireDocTabsChanged() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]initialized_)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtCommentImpl]// ensure we have a tab order (we want the popup list to match the order
        [CtCommentImpl]// of the tabs)
        syncTabOrder();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] ids = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtFieldReadImpl]editors_.size()];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileIcon[] icons = [CtNewArrayImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileIcon[[CtInvocationImpl][CtFieldReadImpl]editors_.size()];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] names = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtFieldReadImpl]editors_.size()];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] paths = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtFieldReadImpl]editors_.size()];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]ids.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl][CtFieldReadImpl]editors_.get([CtInvocationImpl][CtFieldReadImpl]tabOrder_.get([CtVariableReadImpl]i));
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]ids[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]target.getId();
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]icons[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]target.getIcon();
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]names[[CtVariableReadImpl]i] = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getName().getValue();
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]paths[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]target.getPath();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String activeId = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) ? [CtInvocationImpl][CtFieldReadImpl]activeEditor_.getId() : [CtLiteralImpl]null;
        [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]DocTabsChangedEvent([CtVariableReadImpl]activeId, [CtVariableReadImpl]ids, [CtVariableReadImpl]icons, [CtVariableReadImpl]names, [CtVariableReadImpl]paths));
        [CtInvocationImpl][CtFieldReadImpl]view_.manageChevronVisibility();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onSelection([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.event.logical.shared.SelectionEvent<[CtTypeReferenceImpl]java.lang.Integer> event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.onDeactivate();

        [CtAssignmentImpl][CtFieldWriteImpl]activeEditor_ = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getSelectedItem() >= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]activeEditor_ = [CtInvocationImpl][CtFieldReadImpl]editors_.get([CtInvocationImpl][CtVariableReadImpl]event.getSelectedItem());
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.onActivate();
            [CtInvocationImpl][CtCommentImpl]// let any listeners know this tab was activated
            [CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]DocTabActivatedEvent([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath(), [CtInvocationImpl][CtFieldReadImpl]activeEditor_.getId()));
            [CtIfImpl][CtCommentImpl]// don't send focus to the tab if we're expecting a debug selection
            [CtCommentImpl]// event
            if ([CtBinaryOperatorImpl][CtFieldReadImpl]initialized_ && [CtUnaryOperatorImpl](![CtInvocationImpl]isDebugSelectionPending())) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.Scheduler.get().scheduleDeferred([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.core.client.Scheduler.ScheduledCommand()[CtClassImpl] {
                    [CtMethodImpl]public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// presume that we will give focus to the tab
                        [CtTypeReferenceImpl]boolean focus = [CtLiteralImpl]true;
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]event instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.core.client.theme.DocTabSelectionEvent) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtCommentImpl]// however, if this event was generated from a doc tab
                            [CtCommentImpl]// selection that did not have focus, don't steal focus
                            [CtTypeReferenceImpl]org.rstudio.core.client.theme.DocTabSelectionEvent tabEvent = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.core.client.theme.DocTabSelectionEvent) (event));
                            [CtAssignmentImpl][CtVariableWriteImpl]focus = [CtInvocationImpl][CtVariableReadImpl]tabEvent.getFocus();
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]focus && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null))[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.focus();

                    }
                });
            } else [CtIfImpl]if ([CtInvocationImpl]isDebugSelectionPending()) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// we're debugging, so send focus to the console instead of the
                [CtCommentImpl]// editor
                [CtInvocationImpl][CtFieldReadImpl]commands_.activateConsole().execute();
                [CtInvocationImpl]clearPendingDebugSelection();
            }
        }
        [CtIfImpl]if ([CtFieldReadImpl]initialized_)[CtBlockImpl]
            [CtInvocationImpl]manageCommands();

    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageCommands() [CtBlockImpl]{
        [CtInvocationImpl]manageCommands([CtLiteralImpl]false);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageCommands([CtParameterImpl][CtTypeReferenceImpl]boolean forceSync) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasDocs = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]editors_.size() > [CtLiteralImpl]0;
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.newSourceDoc().setEnabled([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.closeSourceDoc().setEnabled([CtVariableReadImpl]hasDocs);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.closeAllSourceDocs().setEnabled([CtVariableReadImpl]hasDocs);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.nextTab().setEnabled([CtVariableReadImpl]hasDocs);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.previousTab().setEnabled([CtVariableReadImpl]hasDocs);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.firstTab().setEnabled([CtVariableReadImpl]hasDocs);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.lastTab().setEnabled([CtVariableReadImpl]hasDocs);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.switchToTab().setEnabled([CtVariableReadImpl]hasDocs);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.setWorkingDirToActiveDoc().setEnabled([CtVariableReadImpl]hasDocs);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> newCommands = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) ? [CtInvocationImpl][CtFieldReadImpl]activeEditor_.getSupportedCommands() : [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand>();
        [CtIfImpl]if ([CtVariableReadImpl]forceSync) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand command : [CtFieldReadImpl]activeCommands_) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]command.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtVariableReadImpl]command.setVisible([CtLiteralImpl]false);
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand command : [CtVariableReadImpl]newCommands) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]command.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]command.setVisible([CtLiteralImpl]true);
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> commandsToEnable = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand>([CtVariableReadImpl]newCommands);
            [CtInvocationImpl][CtVariableReadImpl]commandsToEnable.removeAll([CtFieldReadImpl]activeCommands_);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> commandsToDisable = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand>([CtFieldReadImpl]activeCommands_);
            [CtInvocationImpl][CtVariableReadImpl]commandsToDisable.removeAll([CtVariableReadImpl]newCommands);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand command : [CtVariableReadImpl]commandsToEnable) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]command.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]command.setVisible([CtLiteralImpl]true);
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand command : [CtVariableReadImpl]commandsToDisable) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]command.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtVariableReadImpl]command.setVisible([CtLiteralImpl]false);
            }
        }
        [CtInvocationImpl][CtCommentImpl]// commands which should always be visible even when disabled
        [CtInvocationImpl][CtFieldReadImpl]commands_.saveSourceDoc().setVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.saveSourceDocAs().setVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.printSourceDoc().setVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.setWorkingDirToActiveDoc().setVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.debugBreakpoint().setVisible([CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// manage synctex commands
        manageSynctexCommands();
        [CtInvocationImpl][CtCommentImpl]// manage vcs commands
        manageVcsCommands();
        [CtInvocationImpl][CtCommentImpl]// manage save and save all
        manageSaveCommands();
        [CtInvocationImpl][CtCommentImpl]// manage source navigation
        manageSourceNavigationCommands();
        [CtInvocationImpl][CtCommentImpl]// manage RSConnect commands
        manageRSConnectCommands();
        [CtInvocationImpl][CtCommentImpl]// manage R Markdown commands
        manageRMarkdownCommands();
        [CtInvocationImpl][CtCommentImpl]// manage multi-tab commands
        manageMultiTabCommands();
        [CtInvocationImpl]manageTerminalCommands();
        [CtAssignmentImpl][CtFieldWriteImpl]activeCommands_ = [CtVariableReadImpl]newCommands;
        [CtIfImpl][CtCommentImpl]// give the active editor a chance to manage commands
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]activeEditor_.manageCommands();

        [CtAssertImpl]assert [CtInvocationImpl]verifyNoUnsupportedCommands([CtVariableReadImpl]newCommands) : [CtLiteralImpl]"Unsupported commands detected (please add to Source.dynamicCommands_)";
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageMultiTabCommands() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasMultipleDocs = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]editors_.size() > [CtLiteralImpl]1;
        [CtIfImpl][CtCommentImpl]// special case--these editing targets always support popout, but it's
        [CtCommentImpl]// nonsensical to show it if it's the only tab in a satellite; hide it in
        [CtCommentImpl]// this case
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.popoutDoc().isEnabled() && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) || [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow())) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.popoutDoc().setVisible([CtVariableReadImpl]hasMultipleDocs);
        }
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.closeOtherSourceDocs().setEnabled([CtVariableReadImpl]hasMultipleDocs);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageSynctexCommands() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// synctex commands are enabled if we have synctex for the active editor
        [CtTypeReferenceImpl]boolean synctexAvailable = [CtInvocationImpl][CtFieldReadImpl]synctex_.isSynctexAvailable();
        [CtIfImpl]if ([CtVariableReadImpl]synctexAvailable) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtFieldReadImpl]activeEditor_.canCompilePdf()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]synctexAvailable = [CtInvocationImpl][CtFieldReadImpl]synctex_.isSynctexAvailable();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]synctexAvailable = [CtLiteralImpl]false;
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]synctex_.enableCommands([CtVariableReadImpl]synctexAvailable);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageVcsCommands() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// manage availablity of vcs commands
        [CtTypeReferenceImpl]boolean vcsCommandsEnabled = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().isVcsEnabled() && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath().startsWith([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().getActiveProjectDir().getPath());
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileLog().setVisible([CtVariableReadImpl]vcsCommandsEnabled);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileLog().setEnabled([CtVariableReadImpl]vcsCommandsEnabled);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileDiff().setVisible([CtVariableReadImpl]vcsCommandsEnabled);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileDiff().setEnabled([CtVariableReadImpl]vcsCommandsEnabled);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileRevert().setVisible([CtVariableReadImpl]vcsCommandsEnabled);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileRevert().setEnabled([CtVariableReadImpl]vcsCommandsEnabled);
        [CtIfImpl]if ([CtVariableReadImpl]vcsCommandsEnabled) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.getNameFromPath([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath());
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileDiff().setMenuLabel([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"_Diff \"" + [CtVariableReadImpl]name) + [CtLiteralImpl]"\"");
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileLog().setMenuLabel([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"_Log of \"" + [CtVariableReadImpl]name) + [CtLiteralImpl]"\"");
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsFileRevert().setMenuLabel([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"_Revert \"" + [CtVariableReadImpl]name) + [CtLiteralImpl]"\"...");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isGithubRepo = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().isGithubRepository();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]vcsCommandsEnabled && [CtVariableReadImpl]isGithubRepo) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.getNameFromPath([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath());
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsViewOnGitHub().setVisible([CtLiteralImpl]true);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsViewOnGitHub().setEnabled([CtLiteralImpl]true);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsViewOnGitHub().setMenuLabel([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"_View \"" + [CtVariableReadImpl]name) + [CtLiteralImpl]"\" on GitHub");
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsBlameOnGitHub().setVisible([CtLiteralImpl]true);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsBlameOnGitHub().setEnabled([CtLiteralImpl]true);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsBlameOnGitHub().setMenuLabel([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"_Blame \"" + [CtVariableReadImpl]name) + [CtLiteralImpl]"\" on GitHub");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsViewOnGitHub().setVisible([CtLiteralImpl]false);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsViewOnGitHub().setEnabled([CtLiteralImpl]false);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsBlameOnGitHub().setVisible([CtLiteralImpl]false);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.vcsBlameOnGitHub().setEnabled([CtLiteralImpl]false);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageRSConnectCommands() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean rsCommandsAvailable = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.model.SessionUtils.showPublishUi([CtFieldReadImpl]session_, [CtFieldReadImpl]userState_) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType().startsWith([CtTypeAccessImpl]SourceDocument.XT_SHINY_PREFIX)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument.XT_RMARKDOWN)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument.XT_PLUMBER_API));
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.rsconnectDeploy().setVisible([CtVariableReadImpl]rsCommandsAvailable);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String deployLabel = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType().startsWith([CtTypeAccessImpl]SourceDocument.XT_SHINY_PREFIX)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]deployLabel = [CtLiteralImpl]"Publish Application...";
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument.XT_PLUMBER_API) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]deployLabel = [CtLiteralImpl]"Publish Plumber API...";
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deployLabel == [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]deployLabel = [CtLiteralImpl]"Publish Document...";

            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.rsconnectDeploy().setLabel([CtVariableReadImpl]deployLabel);
        }
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.rsconnectConfigure().setVisible([CtVariableReadImpl]rsCommandsAvailable);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageRMarkdownCommands() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean rmdCommandsAvailable = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().getRMarkdownPackageAvailable() && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getExtendedFileType() == [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.model.SourceDocument.XT_RMARKDOWN);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.editRmdFormatOptions().setVisible([CtVariableReadImpl]rmdCommandsAvailable);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.editRmdFormatOptions().setEnabled([CtVariableReadImpl]rmdCommandsAvailable);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageSaveCommands() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean saveEnabled = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]activeEditor_.isSaveCommandActive();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.saveSourceDoc().setEnabled([CtVariableReadImpl]saveEnabled);
        [CtInvocationImpl]manageSaveAllCommand();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageSaveAllCommand() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// if source windows are open, managing state of the command becomes
        [CtCommentImpl]// complicated, so leave it enabled
        if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().areSourceWindowsOpen()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.saveAllSourceDocs().setEnabled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtForEachImpl][CtCommentImpl]// if one document is dirty then we are enabled
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtFieldReadImpl]editors_) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]target.isSaveCommandActive()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.saveAllSourceDocs().setEnabled([CtLiteralImpl]true);
                [CtReturnImpl]return;
            }
        }
        [CtInvocationImpl][CtCommentImpl]// not one was dirty, disabled
        [CtInvocationImpl][CtFieldReadImpl]commands_.saveAllSourceDocs().setEnabled([CtLiteralImpl]false);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageTerminalCommands() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]session_.getSessionInfo().getAllowShell())[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.sendToTerminal().setVisible([CtLiteralImpl]false);

    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean verifyNoUnsupportedCommands([CtParameterImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> commands) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> temp = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand>([CtVariableReadImpl]commands);
        [CtInvocationImpl][CtVariableReadImpl]temp.removeAll([CtFieldReadImpl]dynamicCommands_);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]temp.size() == [CtLiteralImpl]0;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void pasteFileContentsAtCursor([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String encoding) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget target = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
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
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
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
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
            [CtInvocationImpl][CtVariableReadImpl]editor.reflowText();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void reindent() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]editor.getDocDisplay().reindent();
        }
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

    [CtMethodImpl]private [CtTypeReferenceImpl]void showHelpAtCursor() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (activeEditor_));
            [CtInvocationImpl][CtVariableReadImpl]editor.showHelpAtCursor();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onFileEdit([CtParameterImpl][CtTypeReferenceImpl]FileEditEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.editFile([CtInvocationImpl][CtVariableReadImpl]event.getFile());
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onBeforeShow([CtParameterImpl][CtTypeReferenceImpl]BeforeShowEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]view_.getTabCount() == [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtFieldReadImpl]newTabPending_ == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Avoid scenarios where the Source tab comes up but no tabs are
            [CtCommentImpl]// in it. (But also avoid creating an extra source tab when there
            [CtCommentImpl]// were already new tabs about to be created!)
            onNewSourceDoc();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onSourceNavigateBack() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.isForwardEnabled()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]activeEditor_.recordCurrentNavigationPosition();

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigation navigation = [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.goBack();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]navigation != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl]attemptSourceNavigation([CtVariableReadImpl]navigation, [CtInvocationImpl][CtFieldReadImpl]commands_.sourceNavigateBack());

    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onSourceNavigateForward() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigation navigation = [CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.goForward();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]navigation != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl]attemptSourceNavigation([CtVariableReadImpl]navigation, [CtInvocationImpl][CtFieldReadImpl]commands_.sourceNavigateForward());

    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onOpenNextFileOnFilesystem() [CtBlockImpl]{
        [CtInvocationImpl]openAdjacentFile([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onOpenPreviousFileOnFilesystem() [CtBlockImpl]{
        [CtInvocationImpl]openAdjacentFile([CtLiteralImpl]false);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onSpeakEditorLocation() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String announcement;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]activeEditor_ == [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]announcement = [CtLiteralImpl]"No document tabs open";
        else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]announcement = [CtInvocationImpl][CtFieldReadImpl]activeEditor_.getCurrentStatus();
        }
        [CtInvocationImpl][CtFieldReadImpl]ariaLive_.announce([CtTypeAccessImpl]AriaLiveService.ON_DEMAND, [CtVariableReadImpl]announcement, [CtTypeAccessImpl]Timing.IMMEDIATE, [CtTypeAccessImpl]Severity.STATUS);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onZoomIn() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.hasDesktopFrame()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.getFrame().zoomIn();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onZoomOut() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.hasDesktopFrame()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.getFrame().zoomOut();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.rstudio.core.client.command.Handler
    public [CtTypeReferenceImpl]void onZoomActualSize() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.hasDesktopFrame()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.application.Desktop.getFrame().zoomActualSize();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openAdjacentFile([CtParameterImpl]final [CtTypeReferenceImpl]boolean forward) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// ensure we have an editor and a titled document is open
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ == [CtLiteralImpl]null) || [CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath()))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem activePath = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getPath());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem activeDir = [CtInvocationImpl][CtVariableReadImpl]activePath.getParentPath();
        [CtInvocationImpl][CtCommentImpl]// monitor result
        [CtCommentImpl]// show hidden
        [CtFieldReadImpl]server_.listFiles([CtVariableReadImpl]activeDir, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtNewClassImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerRequestCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.files.model.DirectoryListing>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponseReceived([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.files.model.DirectoryListing listing) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// read file listing (bail if there are no adjacent files)
                [CtTypeReferenceImpl]com.google.gwt.core.client.JsArray<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem> files = [CtInvocationImpl][CtVariableReadImpl]listing.getFiles();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int n = [CtInvocationImpl][CtVariableReadImpl]files.length();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]n < [CtLiteralImpl]2)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtLocalVariableImpl][CtCommentImpl]// find the index of the currently open file
                [CtTypeReferenceImpl]int index = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]n; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file = [CtInvocationImpl][CtVariableReadImpl]files.get([CtVariableReadImpl]i);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]file.equalTo([CtVariableReadImpl]activePath)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]index = [CtVariableReadImpl]i;
                        [CtBreakImpl]break;
                    }
                }
                [CtIfImpl][CtCommentImpl]// if this failed for some reason, bail
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]index == [CtUnaryOperatorImpl](-[CtLiteralImpl]1))[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtLocalVariableImpl][CtCommentImpl]// compute index of file to be opened (with wrap-around)
                [CtTypeReferenceImpl]int target = [CtConditionalImpl]([CtVariableReadImpl]forward) ? [CtBinaryOperatorImpl][CtVariableReadImpl]index + [CtLiteralImpl]1 : [CtBinaryOperatorImpl][CtVariableReadImpl]index - [CtLiteralImpl]1;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target < [CtLiteralImpl]0)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]target = [CtBinaryOperatorImpl][CtVariableReadImpl]n - [CtLiteralImpl]1;
                else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target >= [CtVariableReadImpl]n)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]target = [CtLiteralImpl]0;

                [CtLocalVariableImpl][CtCommentImpl]// extract the file and attempt to open
                [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem targetItem = [CtInvocationImpl][CtVariableReadImpl]files.get([CtVariableReadImpl]target);
                [CtInvocationImpl]openFile([CtVariableReadImpl]targetItem);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError error) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]Debug.logError([CtVariableReadImpl]error);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void attemptSourceNavigation([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigation navigation, [CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand retryCommand) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// see if we can navigate by id
        [CtTypeReferenceImpl]java.lang.String docId = [CtInvocationImpl][CtVariableReadImpl]navigation.getDocumentId();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtInvocationImpl]getEditingTargetForId([CtVariableReadImpl]docId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// check for navigation to the current position -- in this
            [CtCommentImpl]// case execute the retry command
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]target == [CtFieldReadImpl]activeEditor_) && [CtInvocationImpl][CtVariableReadImpl]target.isAtSourceRow([CtInvocationImpl][CtVariableReadImpl]navigation.getPosition())) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]retryCommand.isEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]retryCommand.execute();

            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]suspendSourceNavigationAdding_ = [CtLiteralImpl]true;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget());
                    [CtInvocationImpl][CtVariableReadImpl]target.restorePosition([CtInvocationImpl][CtVariableReadImpl]navigation.getPosition());
                } finally [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]suspendSourceNavigationAdding_ = [CtLiteralImpl]false;
                }
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]navigation.getPath() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]navigation.getPath().startsWith([CtTypeAccessImpl]CodeBrowserEditingTarget.PATH)) [CtBlockImpl]{
            [CtInvocationImpl]activateCodeBrowser([CtInvocationImpl][CtVariableReadImpl]navigation.getPath(), [CtLiteralImpl]false, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.SourceNavigationResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget>([CtInvocationImpl][CtVariableReadImpl]navigation.getPosition(), [CtVariableReadImpl]retryCommand));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]navigation.getPath() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]navigation.getPath().startsWith([CtTypeAccessImpl]DataItem.URI_PREFIX))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]navigation.getPath().startsWith([CtTypeAccessImpl]ObjectExplorerHandle.URI_PREFIX))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.files.FileSystemItem.createFile([CtInvocationImpl][CtVariableReadImpl]navigation.getPath());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType = [CtInvocationImpl][CtFieldReadImpl]fileTypeRegistry_.getTextTypeForFile([CtVariableReadImpl]file);
            [CtInvocationImpl][CtCommentImpl]// open the file and restore the position
            openFile([CtVariableReadImpl]file, [CtVariableReadImpl]fileType, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.SourceNavigationResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>([CtInvocationImpl][CtVariableReadImpl]navigation.getPosition(), [CtVariableReadImpl]retryCommand));
        } else [CtIfImpl][CtCommentImpl]// couldn't navigate to this item, retry
        if ([CtInvocationImpl][CtVariableReadImpl]retryCommand.isEnabled())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]retryCommand.execute();

    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void manageSourceNavigationCommands() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.sourceNavigateBack().setEnabled([CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.isBackEnabled());
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commands_.sourceNavigateForward().setEnabled([CtInvocationImpl][CtFieldReadImpl]sourceNavigationHistory_.isForwardEnabled());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onCodeBrowserNavigation([CtParameterImpl]final [CtTypeReferenceImpl]CodeBrowserNavigationEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// if this isn't the main source window, don't handle server-dispatched
        [CtCommentImpl]// code browser events
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.serverDispatched() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow())) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]tryExternalCodeBrowser([CtInvocationImpl][CtVariableReadImpl]event.getFunction(), [CtVariableReadImpl]event, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getDebugPosition() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]setPendingDebugSelection();
                }
                [CtInvocationImpl]activateCodeBrowser([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget.getCodeBrowserPath([CtInvocationImpl][CtVariableReadImpl]event.getFunction()), [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]event.serverDispatched(), [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget target) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]target.showFunction([CtInvocationImpl][CtVariableReadImpl]event.getFunction());
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getDebugPosition() != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl]highlightDebugBrowserPosition([CtVariableReadImpl]target, [CtInvocationImpl][CtVariableReadImpl]event.getDebugPosition(), [CtInvocationImpl][CtVariableReadImpl]event.getExecuting());
                        }
                    }
                });
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onCodeBrowserFinished([CtParameterImpl]final [CtTypeReferenceImpl]CodeBrowserFinishedEvent event) [CtBlockImpl]{
        [CtInvocationImpl]tryExternalCodeBrowser([CtInvocationImpl][CtVariableReadImpl]event.getFunction(), [CtVariableReadImpl]event, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget.getCodeBrowserPath([CtInvocationImpl][CtVariableReadImpl]event.getFunction());
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i).getPath() == [CtVariableReadImpl]path) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]view_.closeTab([CtVariableReadImpl]i, [CtLiteralImpl]false);
                        [CtReturnImpl]return;
                    }
                }
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onCodeBrowserHighlight([CtParameterImpl]final [CtTypeReferenceImpl]CodeBrowserHighlightEvent event) [CtBlockImpl]{
        [CtInvocationImpl]tryExternalCodeBrowser([CtInvocationImpl][CtVariableReadImpl]event.getFunction(), [CtVariableReadImpl]event, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl]setPendingDebugSelection();
                [CtInvocationImpl]activateCodeBrowser([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget.getCodeBrowserPath([CtInvocationImpl][CtVariableReadImpl]event.getFunction()), [CtLiteralImpl]false, [CtNewClassImpl]new [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget target) [CtBlockImpl]{
                        [CtIfImpl][CtCommentImpl]// if we just stole this code browser from another window,
                        [CtCommentImpl]// we may need to repopulate it
                        if ([CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]target.getContext()))[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]target.showFunction([CtInvocationImpl][CtVariableReadImpl]event.getFunction());

                        [CtInvocationImpl]highlightDebugBrowserPosition([CtVariableReadImpl]target, [CtInvocationImpl][CtVariableReadImpl]event.getDebugPosition(), [CtLiteralImpl]true);
                    }
                });
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void tryExternalCodeBrowser([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.codesearch.model.SearchPathFunctionDefinition func, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.application.events.CrossWindowEvent<[CtWildcardReferenceImpl]?> event, [CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command withLocalCodeBrowser) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget.getCodeBrowserPath([CtVariableReadImpl]func);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceWindowManager.NavigationResult result = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pWindowManager_.get().navigateToCodeBrowser([CtVariableReadImpl]path, [CtVariableReadImpl]event);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.getType() != [CtFieldReadImpl]org.rstudio.studio.client.workbench.views.source.SourceWindowManager.NavigationResult.RESULT_NAVIGATED) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]withLocalCodeBrowser.execute();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void highlightDebugBrowserPosition([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget target, [CtParameterImpl][CtTypeReferenceImpl]DebugFilePosition pos, [CtParameterImpl][CtTypeReferenceImpl]boolean executing) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]target.highlightDebugLocation([CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition.create([CtInvocationImpl][CtVariableReadImpl]pos.getLine(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pos.getColumn() - [CtLiteralImpl]1), [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition.create([CtInvocationImpl][CtVariableReadImpl]pos.getEndLine(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pos.getEndColumn() + [CtLiteralImpl]1), [CtVariableReadImpl]executing);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void activateCodeBrowser([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String codeBrowserPath, [CtParameterImpl][CtTypeReferenceImpl]boolean replaceIfActive, [CtParameterImpl]final [CtTypeReferenceImpl]ResultCallback<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> callback) [CtBlockImpl]{
        [CtForImpl][CtCommentImpl]// first check to see if this request can be fulfilled with an existing
        [CtCommentImpl]// code browser tab
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i).getPath() == [CtVariableReadImpl]codeBrowserPath) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// select the tab
                ensureVisible([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]i);
                [CtInvocationImpl][CtCommentImpl]// callback
                [CtVariableReadImpl]callback.onSuccess([CtInvocationImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget) ([CtFieldReadImpl]editors_.get([CtVariableReadImpl]i))));
                [CtReturnImpl][CtCommentImpl]// satisfied request
                return;
            }
        }
        [CtIfImpl][CtCommentImpl]// then check to see if the active editor is a code browser -- if it is,
        [CtCommentImpl]// we'll use it as is, replacing its contents
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]replaceIfActive && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]events_.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]CodeBrowserCreatedEvent([CtInvocationImpl][CtFieldReadImpl]activeEditor_.getId(), [CtVariableReadImpl]codeBrowserPath));
            [CtInvocationImpl][CtVariableReadImpl]callback.onSuccess([CtFieldReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.codebrowser.CodeBrowserEditingTarget) (activeEditor_)));
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

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isDebugSelectionPending() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]debugSelectionTimer_ != [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void clearPendingDebugSelection() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]debugSelectionTimer_ != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]debugSelectionTimer_.cancel();
            [CtAssignmentImpl][CtFieldWriteImpl]debugSelectionTimer_ = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setPendingDebugSelection() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isDebugSelectionPending()) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]debugSelectionTimer_ = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Timer()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]debugSelectionTimer_ = [CtLiteralImpl]null;
                }
            };
            [CtInvocationImpl][CtFieldReadImpl]debugSelectionTimer_.schedule([CtLiteralImpl]250);
        }
    }

    [CtClassImpl]private class SourceNavigationResultCallback<[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> extends [CtTypeReferenceImpl]ResultCallback<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError> {
        [CtConstructorImpl]public SourceNavigationResultCallback([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition restorePosition, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand retryCommand) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]suspendSourceNavigationAdding_ = [CtLiteralImpl]true;
            [CtAssignmentImpl][CtFieldWriteImpl]restorePosition_ = [CtVariableReadImpl]restorePosition;
            [CtAssignmentImpl][CtFieldWriteImpl]retryCommand_ = [CtVariableReadImpl]retryCommand;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl]final [CtTypeParameterReferenceImpl]T target) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.gwt.core.client.Scheduler.get().scheduleDeferred([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.core.client.Scheduler.ScheduledCommand()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]target.restorePosition([CtFieldReadImpl]restorePosition_);
                    } finally [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]suspendSourceNavigationAdding_ = [CtLiteralImpl]false;
                    }
                }
            });
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onFailure([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.ServerError info) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]suspendSourceNavigationAdding_ = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]retryCommand_.isEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]retryCommand_.execute();

        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onCancelled() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]suspendSourceNavigationAdding_ = [CtLiteralImpl]false;
        }

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourcePosition restorePosition_;

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand retryCommand_;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onSourceExtendedTypeDetected([CtParameterImpl][CtTypeReferenceImpl]SourceExtendedTypeDetectedEvent e) [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// set the extended type of the specified source file
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget editor : [CtFieldReadImpl]editors_) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]editor.getId() == [CtInvocationImpl][CtVariableReadImpl]e.getDocId()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]editor.adaptToExtendedFileType([CtInvocationImpl][CtVariableReadImpl]e.getExtendedType());
                [CtBreakImpl]break;
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onSnippetsChanged([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.snippets.model.SnippetsChangedEvent event) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.studio.client.workbench.snippets.SnippetHelper.onSnippetsChanged([CtVariableReadImpl]event);
    }

    [CtMethodImpl][CtCommentImpl]// when tabs have been reordered in the session, the physical layout of the
    [CtCommentImpl]// tabs doesn't match the logical order of editors_. it's occasionally
    [CtCommentImpl]// necessary to get or set the tabs by their physical order.
    public [CtTypeReferenceImpl]int getPhysicalTabIndex() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtInvocationImpl][CtFieldReadImpl]view_.getActiveTabIndex();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]idx = [CtInvocationImpl][CtFieldReadImpl]tabOrder_.indexOf([CtVariableReadImpl]idx);
        }
        [CtReturnImpl]return [CtVariableReadImpl]idx;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setPhysicalTabIndex([CtParameterImpl][CtTypeReferenceImpl]int idx) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtFieldReadImpl]tabOrder_.size()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]idx = [CtInvocationImpl][CtFieldReadImpl]tabOrder_.get([CtVariableReadImpl]idx);
        }
        [CtInvocationImpl][CtFieldReadImpl]view_.selectTab([CtVariableReadImpl]idx);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget getActiveEditor() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]activeEditor_;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onOpenProfileEvent([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.profiler.OpenProfileEvent event) [CtBlockImpl]{
        [CtInvocationImpl]onShowProfiler([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveDocumentIds([CtParameterImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString ids, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]java.lang.Boolean> onSaveCompleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// we use a timer that fires the document save completed event,
        [CtCommentImpl]// just to ensure the server receives a response even if something
        [CtCommentImpl]// goes wrong during save or some client-side code throws. unfortunately
        [CtCommentImpl]// the current save code is wired up in such a way that it's difficult
        [CtCommentImpl]// to distinguish a success from failure, so we just make sure the
        [CtCommentImpl]// server receives a response after 5s (defaulting to failure)
        final [CtTypeReferenceImpl]Mutable<[CtTypeReferenceImpl]java.lang.Boolean> savedSuccessfully = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Mutable<[CtTypeReferenceImpl]java.lang.Boolean>([CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Timer completedTimer = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Timer()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]onSaveCompleted.execute([CtInvocationImpl][CtVariableReadImpl]savedSuccessfully.get());
            }
        };
        [CtInvocationImpl][CtVariableReadImpl]completedTimer.schedule([CtLiteralImpl]5000);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gwt.user.client.Command onCompleted = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.gwt.user.client.Command()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]savedSuccessfully.set([CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]completedTimer.schedule([CtLiteralImpl]0);
            }
        };
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ids == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]saveUnsavedDocuments([CtVariableReadImpl]onCompleted);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> idSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String id : [CtInvocationImpl][CtTypeAccessImpl]org.rstudio.core.client.js.JsUtil.asIterable([CtVariableReadImpl]ids))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]idSet.add([CtVariableReadImpl]id);

            [CtInvocationImpl]saveUnsavedDocuments([CtVariableReadImpl]idSet, [CtVariableReadImpl]onCompleted);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onRequestDocumentSave([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.model.RequestDocumentSaveEvent event) [CtBlockImpl]{
        [CtInvocationImpl]saveDocumentIds([CtInvocationImpl][CtVariableReadImpl]event.getDocumentIds(), [CtLambdaImpl]([CtParameterImpl] success) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]server_.requestDocumentSaveCompleted([CtVariableReadImpl]success, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onRequestDocumentClose([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.server.model.RequestDocumentCloseEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArrayString ids = [CtInvocationImpl][CtVariableReadImpl]event.getDocumentIds();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.user.client.Command closeEditors = [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Close each of the requested tabs
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]ids != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target : [CtFieldReadImpl][CtFieldReferenceImpl]editors_) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]JsArrayUtil.jsArrayStringContains([CtVariableReadImpl]ids, [CtInvocationImpl][CtVariableReadImpl]target.getId())) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]/* non interactive */
                        [CtFieldReadImpl][CtFieldReferenceImpl]view_.closeTab([CtInvocationImpl][CtVariableReadImpl]target.asWidget(), [CtLiteralImpl]false);
                    }
                }
            }
            [CtIfImpl][CtCommentImpl]// Let the server know we've completed the task
            if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]server_.requestDocumentCloseCompleted([CtLiteralImpl]true, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
            }
        };
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]event.getSave()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Saving, so save unsaved documents before closing the tab(s).
            saveDocumentIds([CtVariableReadImpl]ids, [CtLambdaImpl]([CtParameterImpl] success) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]success) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// All unsaved changes saved; OK to close
                    [CtVariableReadImpl]closeEditors.execute();
                } else [CtIfImpl][CtCommentImpl]// We didn't save (or the user cancelled), so let the server know
                if ([CtInvocationImpl][CtTypeAccessImpl]SourceWindowManager.isMainSourceWindow()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]server_.requestDocumentCloseCompleted([CtLiteralImpl]false, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
                }
            });
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// If not saving, just close the windows immediately
            [CtVariableReadImpl]closeEditors.execute();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void inEditorForPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onEditorLocated) [CtBlockImpl]{
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String editorPath = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i).getPath();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]editorPath != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]editorPath.equals([CtVariableReadImpl]path)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]onEditorLocated.execute([CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i));
                [CtBreakImpl]break;
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void inEditorForId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.widget.OperationWithInput<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> onEditorLocated) [CtBlockImpl]{
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]editors_.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String editorId = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i).getId();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]editorId != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]editorId.equals([CtVariableReadImpl]id)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]onEditorLocated.execute([CtInvocationImpl][CtFieldReadImpl]editors_.get([CtVariableReadImpl]i));
                [CtBreakImpl]break;
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void dispatchEditorEvent([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl]final [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay> command) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.console.shell.editor.InputEditorDisplay console = [CtInvocationImpl][CtFieldReadImpl]consoleEditorProvider_.getConsoleEditor();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isConsoleEvent = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]console != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]isConsoleEvent = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]StringUtil.isNullOrEmpty([CtVariableReadImpl]id) && [CtInvocationImpl][CtVariableReadImpl]console.isFocused()) || [CtInvocationImpl][CtLiteralImpl]"#console".equals([CtVariableReadImpl]id);
        }
        [CtIfImpl]if ([CtVariableReadImpl]isConsoleEvent) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]command.execute([CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay) (console)));
        } else [CtBlockImpl]{
            [CtInvocationImpl]withTarget([CtVariableReadImpl]id, [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget target) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]command.execute([CtInvocationImpl][CtVariableReadImpl]target.getDocDisplay());
                }
            });
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onSetSelectionRanges([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.events.SetSelectionRangesEvent event) [CtBlockImpl]{
        [CtInvocationImpl]dispatchEditorEvent([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getData().getId(), [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay docDisplay) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArray<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range> ranges = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getData().getRanges();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ranges.length() == [CtLiteralImpl]0)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor editor = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor) (docDisplay));
                [CtInvocationImpl][CtVariableReadImpl]editor.setSelectionRanges([CtVariableReadImpl]ranges);
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onGetEditorContext([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.GetEditorContextEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.GetEditorContextEvent.Data data = [CtInvocationImpl][CtVariableReadImpl]event.getData();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int type = [CtInvocationImpl][CtVariableReadImpl]data.getType();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]org.rstudio.studio.client.events.GetEditorContextEvent.TYPE_ACTIVE_EDITOR) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]consoleEditorHadFocusLast() || [CtBinaryOperatorImpl]([CtFieldReadImpl]activeEditor_ == [CtLiteralImpl]null))[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]type = [CtFieldReadImpl]org.rstudio.studio.client.events.GetEditorContextEvent.TYPE_CONSOLE_EDITOR;
            else[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]type = [CtFieldReadImpl]org.rstudio.studio.client.events.GetEditorContextEvent.TYPE_SOURCE_EDITOR;

        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]org.rstudio.studio.client.events.GetEditorContextEvent.TYPE_CONSOLE_EDITOR) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.console.shell.editor.InputEditorDisplay editor = [CtInvocationImpl][CtFieldReadImpl]consoleEditorProvider_.getConsoleEditor();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]editor != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]editor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay)) [CtBlockImpl]{
                [CtInvocationImpl]getEditorContext([CtLiteralImpl]"#console", [CtLiteralImpl]"", [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay) (editor)));
                [CtReturnImpl]return;
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]org.rstudio.studio.client.events.GetEditorContextEvent.TYPE_SOURCE_EDITOR) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget target = [CtFieldReadImpl]activeEditor_;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]target != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]target instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget editingTarget = [CtVariableReadImpl](([CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget) (target));
                [CtInvocationImpl][CtVariableReadImpl]editingTarget.ensureTextEditorActive([CtLambdaImpl]() -> [CtBlockImpl]{
                    [CtInvocationImpl]getEditorContext([CtInvocationImpl][CtVariableReadImpl]editingTarget.getId(), [CtInvocationImpl][CtVariableReadImpl]editingTarget.getPath(), [CtInvocationImpl][CtVariableReadImpl]editingTarget.getDocDisplay());
                });
                [CtReturnImpl]return;
            }
        }
        [CtInvocationImpl][CtCommentImpl]// We need to ensure a 'getEditorContext' event is always
        [CtCommentImpl]// returned as we have a 'wait-for' event on the server side
        [CtFieldReadImpl]server_.getEditorContextCompleted([CtInvocationImpl][CtTypeAccessImpl]GetEditorContextEvent.SelectionData.create(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.server.VoidServerRequestCallback());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onReplaceRanges([CtParameterImpl]final [CtTypeReferenceImpl]org.rstudio.studio.client.events.ReplaceRangesEvent event) [CtBlockImpl]{
        [CtInvocationImpl]dispatchEditorEvent([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getData().getId(), [CtNewClassImpl]new [CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay docDisplay) [CtBlockImpl]{
                [CtInvocationImpl]doReplaceRanges([CtVariableReadImpl]event, [CtVariableReadImpl]docDisplay);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doReplaceRanges([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.ReplaceRangesEvent event, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay docDisplay) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gwt.core.client.JsArray<[CtTypeReferenceImpl]org.rstudio.studio.client.events.ReplaceRangesEvent.ReplacementData> data = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getData().getReplacementData();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int n = [CtInvocationImpl][CtVariableReadImpl]data.length();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]n; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.events.ReplaceRangesEvent.ReplacementData el = [CtInvocationImpl][CtVariableReadImpl]data.get([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]n - [CtVariableReadImpl]i) - [CtLiteralImpl]1);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range range = [CtInvocationImpl][CtVariableReadImpl]el.getRange();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String text = [CtInvocationImpl][CtVariableReadImpl]el.getText();
            [CtIfImpl][CtCommentImpl]// A null range at this point is a proxy to use the current selection
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]range == [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]range = [CtInvocationImpl][CtVariableReadImpl]docDisplay.getSelectionRange();

            [CtInvocationImpl][CtVariableReadImpl]docDisplay.replaceRange([CtVariableReadImpl]range, [CtVariableReadImpl]text);
        }
        [CtInvocationImpl][CtVariableReadImpl]docDisplay.focus();
    }

    [CtClassImpl]private class OpenFileEntry {
        [CtConstructorImpl]public OpenFileEntry([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem fileIn, [CtParameterImpl][CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileTypeIn, [CtParameterImpl][CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> executeIn) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]file = [CtVariableReadImpl]fileIn;
            [CtAssignmentImpl][CtFieldWriteImpl]fileType = [CtVariableReadImpl]fileTypeIn;
            [CtAssignmentImpl][CtFieldWriteImpl]executeOnSuccess = [CtVariableReadImpl]executeIn;
        }

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file;

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.TextFileType fileType;

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.CommandWithArg<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> executeOnSuccess;
    }

    [CtClassImpl]private class StatFileEntry {
        [CtConstructorImpl]public StatFileEntry([CtParameterImpl][CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem fileIn, [CtParameterImpl][CtTypeReferenceImpl]CommandWithArg<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem> actionIn) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]file = [CtVariableReadImpl]fileIn;
            [CtAssignmentImpl][CtFieldWriteImpl]action = [CtVariableReadImpl]actionIn;
        }

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem file;

        [CtFieldImpl]public final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.CommandWithArg<[CtTypeReferenceImpl]org.rstudio.core.client.files.FileSystemItem> action;
    }

    [CtFieldImpl]final [CtTypeReferenceImpl]java.util.Queue<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.StatFileEntry> statQueue_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.StatFileEntry>();

    [CtFieldImpl]final [CtTypeReferenceImpl]java.util.Queue<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.OpenFileEntry> openFileQueue_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.OpenFileEntry>();

    [CtFieldImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget> editors_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget>();

    [CtFieldImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Integer> tabOrder_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Integer>();

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTarget activeEditor_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.commands.Commands commands_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.Source.Display view_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceServerOperations server_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.EditingTargetSource editingTargetSource_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.common.filetypes.FileTypeRegistry fileTypeRegistry_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.common.GlobalDisplay globalDisplay_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.WorkbenchContext workbenchContext_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.common.FileDialogs fileDialogs_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.RemoteFileSystemContext fileContext_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetRMarkdownHelper rmarkdown_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.application.events.EventBus events_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.application.AriaLiveService ariaLive_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.model.Session session_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.common.synctex.Synctex synctex_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]org.rstudio.studio.client.workbench.FileMRUList> pMruList_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserPrefs userPrefs_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.prefs.model.UserState userState_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.ConsoleEditorProvider consoleEditorProvider_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.common.rnw.RnwWeaveRegistry rnwWeaveRegistry_;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> activeCommands_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.rstudio.core.client.command.AppCommand> dynamicCommands_;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigationHistory sourceNavigationHistory_ = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.model.SourceNavigationHistory([CtLiteralImpl]30);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.rstudio.studio.client.workbench.views.source.SourceVimCommands vimCommands_;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean suspendSourceNavigationAdding_;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean suspendDocumentClose_ = [CtLiteralImpl]false;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MODULE_SOURCE = [CtLiteralImpl]"source-pane";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String KEY_ACTIVETAB = [CtLiteralImpl]"activeTab";

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean initialized_;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.gwt.user.client.Timer debugSelectionTimer_ = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean openingForSourceNavigation_ = [CtLiteralImpl]false;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]SourceWindowManager> pWindowManager_;

    [CtFieldImpl][CtCommentImpl]// If positive, a new tab is about to be created
    private [CtTypeReferenceImpl]int newTabPending_;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.rstudio.studio.client.common.dependencies.DependencyManager dependencyManager_;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int TYPE_FILE_BACKED = [CtLiteralImpl]0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int TYPE_UNTITLED = [CtLiteralImpl]1;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int OPEN_INTERACTIVE = [CtLiteralImpl]0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int OPEN_REPLAY = [CtLiteralImpl]1;
}
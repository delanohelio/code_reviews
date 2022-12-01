[CompilationUnitImpl][CtPackageDeclarationImpl]package net.osmand.plus.mapsource;
[CtUnresolvedImport]import net.osmand.plus.SQLiteTileSource;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.widget.ImageButton;
[CtUnresolvedImport]import net.osmand.AndroidUtils;
[CtUnresolvedImport]import static net.osmand.plus.download.ui.LocalIndexesFragment.renameSQLiteFile;
[CtUnresolvedImport]import android.widget.LinearLayout;
[CtUnresolvedImport]import com.google.android.material.textfield.TextInputEditText;
[CtUnresolvedImport]import net.osmand.map.TileSourceManager;
[CtUnresolvedImport]import net.osmand.plus.mapsource.TileStorageFormatBottomSheet.OnTileStorageFormatSelectedListener;
[CtUnresolvedImport]import android.os.Bundle;
[CtUnresolvedImport]import android.view.KeyEvent;
[CtUnresolvedImport]import androidx.appcompat.app.AlertDialog;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtUnresolvedImport]import com.google.android.material.textfield.TextInputLayout;
[CtUnresolvedImport]import android.net.Uri;
[CtUnresolvedImport]import android.widget.FrameLayout;
[CtUnresolvedImport]import android.widget.ImageView;
[CtUnresolvedImport]import net.osmand.plus.R;
[CtUnresolvedImport]import android.app.Dialog;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import net.osmand.plus.base.BaseOsmAndDialogFragment;
[CtUnresolvedImport]import androidx.annotation.DrawableRes;
[CtUnresolvedImport]import android.graphics.drawable.Drawable;
[CtUnresolvedImport]import android.text.Editable;
[CtUnresolvedImport]import net.osmand.IndexConstants;
[CtUnresolvedImport]import androidx.fragment.app.Fragment;
[CtUnresolvedImport]import android.view.View;
[CtUnresolvedImport]import net.osmand.plus.UiUtilities;
[CtUnresolvedImport]import net.osmand.PlatformUtil;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import net.osmand.util.Algorithms;
[CtUnresolvedImport]import androidx.core.content.ContextCompat;
[CtUnresolvedImport]import android.content.DialogInterface;
[CtUnresolvedImport]import net.osmand.plus.mapsource.InputZoomLevelsBottomSheet.OnZoomSetListener;
[CtUnresolvedImport]import androidx.fragment.app.FragmentManager;
[CtUnresolvedImport]import android.view.LayoutInflater;
[CtUnresolvedImport]import android.view.ViewGroup;
[CtUnresolvedImport]import net.osmand.plus.OsmandApplication;
[CtUnresolvedImport]import androidx.annotation.StringRes;
[CtUnresolvedImport]import android.widget.TextView;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import net.osmand.plus.mapsource.MercatorProjectionBottomSheet.OnMercatorSelectedListener;
[CtUnresolvedImport]import net.osmand.plus.mapsource.ExpireTimeBottomSheet.OnExpireValueSetListener;
[CtUnresolvedImport]import androidx.appcompat.widget.Toolbar;
[CtImportImpl]import org.apache.commons.lang3.ArrayUtils;
[CtUnresolvedImport]import org.apache.commons.logging.Log;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import android.text.TextWatcher;
[CtClassImpl]public class EditMapSourceDialogFragment extends [CtTypeReferenceImpl]net.osmand.plus.base.BaseOsmAndDialogFragment implements [CtTypeReferenceImpl]net.osmand.plus.mapsource.InputZoomLevelsBottomSheet.OnZoomSetListener , [CtTypeReferenceImpl]net.osmand.plus.mapsource.ExpireTimeBottomSheet.OnExpireValueSetListener , [CtTypeReferenceImpl]net.osmand.plus.mapsource.MercatorProjectionBottomSheet.OnMercatorSelectedListener , [CtTypeReferenceImpl]net.osmand.plus.mapsource.TileStorageFormatBottomSheet.OnTileStorageFormatSelectedListener {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String TAG = [CtInvocationImpl][CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.class.getName();

    [CtFieldImpl]static final [CtTypeReferenceImpl]int EXPIRE_TIME_NEVER = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.commons.logging.Log LOG = [CtInvocationImpl][CtTypeAccessImpl]net.osmand.PlatformUtil.getLog([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MAPS_PLUGINS_URL = [CtLiteralImpl]"https://osmand.net/features/online-maps-plugin";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PNG_EXT = [CtLiteralImpl]"png";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MAX_ZOOM = [CtLiteralImpl]17;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MIN_ZOOM = [CtLiteralImpl]5;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int TILE_SIZE = [CtLiteralImpl]256;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int BIT_DENSITY = [CtLiteralImpl]16;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int AVG_SIZE = [CtLiteralImpl]32000;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EDIT_LAYER_NAME_KEY = [CtLiteralImpl]"edit_layer_name_key";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MIN_ZOOM_KEY = [CtLiteralImpl]"min_zoom_key";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MAX_ZOOM_KEY = [CtLiteralImpl]"max_zoom_key";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EXPIRE_TIME_KEY = [CtLiteralImpl]"expire_time_key";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ELLIPTIC_KEY = [CtLiteralImpl]"elliptic_key";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SQLITE_DB_KEY = [CtLiteralImpl]"sqlite_db_key";

    [CtFieldImpl]private [CtTypeReferenceImpl]net.osmand.plus.OsmandApplication app;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.android.material.textfield.TextInputEditText nameEditText;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.android.material.textfield.TextInputEditText urlEditText;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.LinearLayout contentContainer;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.FrameLayout saveBtn;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView saveBtnTitle;

    [CtFieldImpl]private [CtTypeReferenceImpl]TileSourceManager.TileSourceTemplate template;

    [CtFieldImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    private [CtTypeReferenceImpl]java.lang.String editedLayerName;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String urlToLoad = [CtLiteralImpl]"";

    [CtFieldImpl]private [CtTypeReferenceImpl]int minZoom = [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MIN_ZOOM;

    [CtFieldImpl]private [CtTypeReferenceImpl]int maxZoom = [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MAX_ZOOM;

    [CtFieldImpl]private [CtTypeReferenceImpl]int expireTimeMinutes = [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.EXPIRE_TIME_NEVER;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean elliptic = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean sqliteDB = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean nightMode;

    [CtMethodImpl]public static [CtTypeReferenceImpl]void showInstance([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]androidx.fragment.app.FragmentManager fm, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]androidx.fragment.app.Fragment targetFragment, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String editedLayerName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment fragment = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment();
        [CtInvocationImpl][CtVariableReadImpl]fragment.setTargetFragment([CtVariableReadImpl]targetFragment, [CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]fragment.setEditedLayerName([CtVariableReadImpl]editedLayerName);
        [CtInvocationImpl][CtVariableReadImpl]fragment.show([CtVariableReadImpl]fm, [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.TAG);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onCreate([CtParameterImpl][CtTypeReferenceImpl]android.os.Bundle savedInstanceState) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onCreate([CtVariableReadImpl]savedInstanceState);
        [CtAssignmentImpl][CtFieldWriteImpl]app = [CtInvocationImpl]getMyApplication();
        [CtAssignmentImpl][CtFieldWriteImpl]nightMode = [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]app.getSettings().isLightContent();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]android.view.View onCreateView([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]android.view.LayoutInflater inflater, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]android.view.ViewGroup container, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]android.os.Bundle savedInstanceState) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]savedInstanceState != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]editedLayerName = [CtInvocationImpl][CtVariableReadImpl]savedInstanceState.getString([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.EDIT_LAYER_NAME_KEY);
            [CtAssignmentImpl][CtFieldWriteImpl]minZoom = [CtInvocationImpl][CtVariableReadImpl]savedInstanceState.getInt([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MIN_ZOOM_KEY);
            [CtAssignmentImpl][CtFieldWriteImpl]maxZoom = [CtInvocationImpl][CtVariableReadImpl]savedInstanceState.getInt([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MAX_ZOOM_KEY);
            [CtAssignmentImpl][CtFieldWriteImpl]expireTimeMinutes = [CtInvocationImpl][CtVariableReadImpl]savedInstanceState.getInt([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.EXPIRE_TIME_KEY);
            [CtAssignmentImpl][CtFieldWriteImpl]elliptic = [CtInvocationImpl][CtVariableReadImpl]savedInstanceState.getBoolean([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ELLIPTIC_KEY);
            [CtAssignmentImpl][CtFieldWriteImpl]sqliteDB = [CtInvocationImpl][CtVariableReadImpl]savedInstanceState.getBoolean([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.SQLITE_DB_KEY);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.View root = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.UiUtilities.getMaterialInflater([CtFieldReadImpl]app, [CtFieldReadImpl]nightMode).inflate([CtTypeAccessImpl]R.layout.fragment_edit_map_source, [CtVariableReadImpl]container, [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.appcompat.widget.Toolbar toolbar = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.toolbar);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.ImageButton iconHelp = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.toolbar_action);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.graphics.drawable.Drawable closeDrawable = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]app.getUIUtilities().getIcon([CtInvocationImpl][CtTypeAccessImpl]net.osmand.AndroidUtils.getNavigationIconResId([CtFieldReadImpl]app), [CtConditionalImpl][CtFieldReadImpl]nightMode ? [CtFieldReadImpl]R.color.active_buttons_and_links_text_dark : [CtFieldReadImpl]R.color.active_buttons_and_links_text_light);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.graphics.drawable.Drawable helpDrawable = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]app.getUIUtilities().getIcon([CtTypeAccessImpl]R.drawable.ic_action_help, [CtConditionalImpl][CtFieldReadImpl]nightMode ? [CtFieldReadImpl]R.color.active_buttons_and_links_text_dark : [CtFieldReadImpl]R.color.active_buttons_and_links_text_light);
        [CtInvocationImpl][CtVariableReadImpl]iconHelp.setImageDrawable([CtVariableReadImpl]helpDrawable);
        [CtInvocationImpl][CtVariableReadImpl]iconHelp.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
                [CtInvocationImpl]onHelpClick();
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]toolbar.setNavigationIcon([CtVariableReadImpl]closeDrawable);
        [CtInvocationImpl][CtVariableReadImpl]toolbar.setNavigationContentDescription([CtTypeAccessImpl]R.string.shared_string_close);
        [CtInvocationImpl][CtVariableReadImpl]toolbar.setNavigationOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                [CtInvocationImpl]showExitDialog();
            }
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]int boxStrokeColor = [CtConditionalImpl]([CtFieldReadImpl]nightMode) ? [CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtFieldReadImpl]app, [CtTypeAccessImpl]R.color.app_bar_color_light) : [CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtFieldReadImpl]app, [CtTypeAccessImpl]R.color.active_buttons_and_links_bg_pressed_dark);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.android.material.textfield.TextInputLayout nameInputLayout = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.name_input_layout);
        [CtInvocationImpl][CtVariableReadImpl]nameInputLayout.setBoxStrokeColor([CtVariableReadImpl]boxStrokeColor);
        [CtAssignmentImpl][CtFieldWriteImpl]nameEditText = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.name_edit_text);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.android.material.textfield.TextInputLayout urlInputLayout = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.url_input_layout);
        [CtInvocationImpl][CtVariableReadImpl]urlInputLayout.setBoxStrokeColor([CtVariableReadImpl]boxStrokeColor);
        [CtAssignmentImpl][CtFieldWriteImpl]urlEditText = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.url_edit_text);
        [CtInvocationImpl][CtFieldReadImpl]nameEditText.addTextChangedListener([CtInvocationImpl]getTextWatcher());
        [CtInvocationImpl][CtFieldReadImpl]urlEditText.addTextChangedListener([CtInvocationImpl]getTextWatcher());
        [CtAssignmentImpl][CtFieldWriteImpl]contentContainer = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.content_container);
        [CtAssignmentImpl][CtFieldWriteImpl]saveBtn = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.save_button);
        [CtAssignmentImpl][CtFieldWriteImpl]saveBtnTitle = [CtInvocationImpl][CtVariableReadImpl]root.findViewById([CtTypeAccessImpl]R.id.save_button_title);
        [CtInvocationImpl][CtFieldReadImpl]saveBtn.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
                [CtInvocationImpl]saveTemplate();
                [CtInvocationImpl]dismiss();
            }
        });
        [CtAssignmentImpl][CtFieldWriteImpl]template = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.osmand.map.TileSourceManager.TileSourceTemplate([CtLiteralImpl]"", [CtLiteralImpl]"", [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.PNG_EXT, [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MAX_ZOOM, [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MIN_ZOOM, [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.TILE_SIZE, [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.BIT_DENSITY, [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.AVG_SIZE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]editedLayerName != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]editedLayerName.endsWith([CtTypeAccessImpl]IndexConstants.SQLITE_EXT)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File f = [CtInvocationImpl][CtFieldReadImpl]app.getAppPath([CtBinaryOperatorImpl][CtFieldReadImpl]net.osmand.IndexConstants.TILES_INDEX_DIR + [CtFieldReadImpl]editedLayerName);
                [CtAssignmentImpl][CtFieldWriteImpl]template = [CtInvocationImpl][CtTypeAccessImpl]net.osmand.map.TileSourceManager.createTileSourceTemplate([CtVariableReadImpl]f);
                [CtAssignmentImpl][CtFieldWriteImpl]sqliteDB = [CtLiteralImpl]false;
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]net.osmand.map.TileSourceManager.TileSourceTemplate> knownTemplates = [CtInvocationImpl][CtTypeAccessImpl]net.osmand.map.TileSourceManager.getKnownSourceTemplates();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File tPath = [CtInvocationImpl][CtFieldReadImpl]app.getAppPath([CtTypeAccessImpl]IndexConstants.TILES_INDEX_DIR);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File dir = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]tPath, [CtFieldReadImpl]editedLayerName);
                [CtLocalVariableImpl][CtTypeReferenceImpl]net.osmand.plus.SQLiteTileSource sqLiteTileSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.osmand.plus.SQLiteTileSource([CtFieldReadImpl]app, [CtVariableReadImpl]dir, [CtVariableReadImpl]knownTemplates);
                [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.couldBeDownloadedFromInternet();
                [CtAssignmentImpl][CtFieldWriteImpl]template = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.osmand.map.TileSourceManager.TileSourceTemplate([CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.getName(), [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.getUrlTemplate(), [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.PNG_EXT, [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.getMaximumZoomSupported(), [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.getMinimumZoomSupported(), [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.getTileSize(), [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.getBitDensity(), [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.AVG_SIZE);
                [CtInvocationImpl][CtFieldReadImpl]template.setExpirationTimeMinutes([CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.getExpirationTimeMinutes());
                [CtInvocationImpl][CtFieldReadImpl]template.setEllipticYTile([CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.isEllipticYTile());
                [CtAssignmentImpl][CtFieldWriteImpl]sqliteDB = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]savedInstanceState == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]urlToLoad = [CtInvocationImpl][CtFieldReadImpl]template.getUrlTemplate();
            [CtAssignmentImpl][CtFieldWriteImpl]expireTimeMinutes = [CtInvocationImpl][CtFieldReadImpl]template.getExpirationTimeMinutes();
            [CtAssignmentImpl][CtFieldWriteImpl]minZoom = [CtInvocationImpl][CtFieldReadImpl]template.getMinimumZoomSupported();
            [CtAssignmentImpl][CtFieldWriteImpl]maxZoom = [CtInvocationImpl][CtFieldReadImpl]template.getMaximumZoomSupported();
            [CtAssignmentImpl][CtFieldWriteImpl]elliptic = [CtInvocationImpl][CtFieldReadImpl]template.isEllipticYTile();
        }
        [CtInvocationImpl]updateUi();
        [CtReturnImpl]return [CtVariableReadImpl]root;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onSaveInstanceState([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]android.os.Bundle outState) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]outState.putString([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.EDIT_LAYER_NAME_KEY, [CtFieldReadImpl]editedLayerName);
        [CtInvocationImpl][CtVariableReadImpl]outState.putInt([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MIN_ZOOM_KEY, [CtFieldReadImpl]minZoom);
        [CtInvocationImpl][CtVariableReadImpl]outState.putInt([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MAX_ZOOM_KEY, [CtFieldReadImpl]maxZoom);
        [CtInvocationImpl][CtVariableReadImpl]outState.putInt([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.EXPIRE_TIME_KEY, [CtFieldReadImpl]expireTimeMinutes);
        [CtInvocationImpl][CtVariableReadImpl]outState.putBoolean([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ELLIPTIC_KEY, [CtFieldReadImpl]elliptic);
        [CtInvocationImpl][CtVariableReadImpl]outState.putBoolean([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.SQLITE_DB_KEY, [CtFieldReadImpl]sqliteDB);
        [CtInvocationImpl][CtSuperAccessImpl]super.onSaveInstanceState([CtVariableReadImpl]outState);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onResume() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onResume();
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.Dialog dialog = [CtInvocationImpl]getDialog();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dialog != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]dialog.setOnKeyListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.DialogInterface.OnKeyListener()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]boolean onKey([CtParameterImpl][CtTypeReferenceImpl]android.content.DialogInterface dialog, [CtParameterImpl][CtTypeReferenceImpl]int keyCode, [CtParameterImpl][CtTypeReferenceImpl]android.view.KeyEvent event) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_BACK) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getAction() == [CtFieldReadImpl]android.view.KeyEvent.ACTION_DOWN) [CtBlockImpl]{
                            [CtReturnImpl]return [CtLiteralImpl]true;
                        } else [CtBlockImpl]{
                            [CtInvocationImpl]showExitDialog();
                            [CtReturnImpl]return [CtLiteralImpl]true;
                        }
                    }
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            });
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onZoomSet([CtParameterImpl][CtTypeReferenceImpl]int min, [CtParameterImpl][CtTypeReferenceImpl]int max) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]minZoom = [CtVariableReadImpl]min;
        [CtAssignmentImpl][CtFieldWriteImpl]maxZoom = [CtVariableReadImpl]max;
        [CtInvocationImpl]updateDescription([CtFieldReadImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem.[CtFieldReferenceImpl]ZOOM_LEVELS);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onExpireValueSet([CtParameterImpl][CtTypeReferenceImpl]int expireValue) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]expireTimeMinutes = [CtVariableReadImpl]expireValue;
        [CtInvocationImpl]updateDescription([CtFieldReadImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem.[CtFieldReferenceImpl]EXPIRE_TIME);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onMercatorSelected([CtParameterImpl][CtTypeReferenceImpl]boolean elliptic) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isAdded()) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.elliptic = [CtVariableReadImpl]elliptic;
            [CtInvocationImpl]updateDescription([CtFieldReadImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem.[CtFieldReferenceImpl]MERCATOR_PROJECTION);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onStorageFormatSelected([CtParameterImpl][CtTypeReferenceImpl]boolean sqliteDb) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isAdded()) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sqliteDB = [CtVariableReadImpl]sqliteDb;
            [CtInvocationImpl]updateDescription([CtFieldReadImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem.[CtFieldReferenceImpl]STORAGE_FORMAT);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]android.text.TextWatcher getTextWatcher() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]android.text.TextWatcher()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void beforeTextChanged([CtParameterImpl][CtTypeReferenceImpl]java.lang.CharSequence charSequence, [CtParameterImpl][CtTypeReferenceImpl]int i, [CtParameterImpl][CtTypeReferenceImpl]int i1, [CtParameterImpl][CtTypeReferenceImpl]int i2) [CtBlockImpl]{
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onTextChanged([CtParameterImpl][CtTypeReferenceImpl]java.lang.CharSequence charSequence, [CtParameterImpl][CtTypeReferenceImpl]int i, [CtParameterImpl][CtTypeReferenceImpl]int i1, [CtParameterImpl][CtTypeReferenceImpl]int i2) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s = [CtInvocationImpl][CtVariableReadImpl]charSequence.toString();
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]net.osmand.util.Algorithms.isEmpty([CtVariableReadImpl]s)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]saveBtn.setEnabled([CtLiteralImpl]false);
                    [CtInvocationImpl][CtFieldReadImpl]saveBtnTitle.setEnabled([CtLiteralImpl]false);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]saveBtn.setEnabled([CtLiteralImpl]true);
                    [CtInvocationImpl][CtFieldReadImpl]saveBtnTitle.setEnabled([CtLiteralImpl]true);
                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void afterTextChanged([CtParameterImpl][CtTypeReferenceImpl]android.text.Editable editable) [CtBlockImpl]{
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveTemplate() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newName = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]nameEditText.getText().toString();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String urlToLoad = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]urlEditText.getText().toString();
            [CtInvocationImpl][CtFieldReadImpl]template.setName([CtVariableReadImpl]newName);
            [CtInvocationImpl][CtFieldReadImpl]template.setUrlToLoad([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]urlToLoad.isEmpty() ? [CtLiteralImpl]null : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]urlToLoad.replace([CtLiteralImpl]"{$x}", [CtLiteralImpl]"{1}").replace([CtLiteralImpl]"{$y}", [CtLiteralImpl]"{2}").replace([CtLiteralImpl]"{$z}", [CtLiteralImpl]"{0}"));
            [CtInvocationImpl][CtFieldReadImpl]template.setMinZoom([CtFieldReadImpl]minZoom);
            [CtInvocationImpl][CtFieldReadImpl]template.setMaxZoom([CtFieldReadImpl]maxZoom);
            [CtInvocationImpl][CtFieldReadImpl]template.setEllipticYTile([CtFieldReadImpl]elliptic);
            [CtInvocationImpl][CtFieldReadImpl]template.setExpirationTimeMinutes([CtFieldReadImpl]expireTimeMinutes);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File f = [CtInvocationImpl][CtFieldReadImpl]app.getAppPath([CtBinaryOperatorImpl][CtFieldReadImpl]net.osmand.IndexConstants.TILES_INDEX_DIR + [CtFieldReadImpl]editedLayerName);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]f.exists()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int extIndex = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getName().lastIndexOf([CtLiteralImpl]'.');
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ext = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]extIndex == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) ? [CtLiteralImpl]"" : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getName().substring([CtVariableReadImpl]extIndex);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String originalName = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]extIndex == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) ? [CtInvocationImpl][CtVariableReadImpl]f.getName() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getName().substring([CtLiteralImpl]0, [CtVariableReadImpl]extIndex);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]net.osmand.util.Algorithms.objectEquals([CtVariableReadImpl]newName, [CtVariableReadImpl]originalName)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]IndexConstants.SQLITE_EXT.equals([CtVariableReadImpl]ext) && [CtFieldReadImpl]sqliteDB) [CtBlockImpl]{
                        [CtInvocationImpl]renameSQLiteFile([CtFieldReadImpl]app, [CtVariableReadImpl]f, [CtVariableReadImpl]newName, [CtLiteralImpl]null);
                    } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]sqliteDB) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]f.renameTo([CtInvocationImpl][CtFieldReadImpl]app.getAppPath([CtBinaryOperatorImpl][CtFieldReadImpl]net.osmand.IndexConstants.TILES_INDEX_DIR + [CtVariableReadImpl]newName));
                    }
                }
            }
            [CtIfImpl]if ([CtFieldReadImpl]sqliteDB) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]f.exists()) || [CtInvocationImpl][CtVariableReadImpl]f.isDirectory()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]net.osmand.plus.SQLiteTileSource sqLiteTileSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.osmand.plus.SQLiteTileSource([CtFieldReadImpl]app, [CtVariableReadImpl]newName, [CtFieldReadImpl]minZoom, [CtFieldReadImpl]maxZoom, [CtVariableReadImpl]urlToLoad, [CtLiteralImpl]"0,1,2,3", [CtFieldReadImpl]elliptic, [CtLiteralImpl]false, [CtLiteralImpl]"", [CtBinaryOperatorImpl][CtFieldReadImpl]expireTimeMinutes > [CtLiteralImpl]0, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]expireTimeMinutes * [CtLiteralImpl]60) * [CtLiteralImpl]1000L, [CtLiteralImpl]false, [CtLiteralImpl]"");
                    [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.createDataBase();
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]net.osmand.map.TileSourceManager.TileSourceTemplate> knownTemplates = [CtInvocationImpl][CtTypeAccessImpl]net.osmand.map.TileSourceManager.getKnownSourceTemplates();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]net.osmand.plus.SQLiteTileSource sqLiteTileSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.osmand.plus.SQLiteTileSource([CtFieldReadImpl]app, [CtVariableReadImpl]f, [CtVariableReadImpl]knownTemplates);
                    [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.couldBeDownloadedFromInternet();
                    [CtInvocationImpl][CtVariableReadImpl]sqLiteTileSource.updateFromTileSourceTemplate([CtFieldReadImpl]template);
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]getSettings().installTileSource([CtFieldReadImpl]template);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.fragment.app.Fragment fragment = [CtInvocationImpl]getTargetFragment();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fragment instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.OnMapSourceUpdateListener) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.OnMapSourceUpdateListener) (fragment)).onMapSourceUpdated();
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.LOG.error([CtBinaryOperatorImpl][CtLiteralImpl]"Error on saving template " + [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateUi() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]nameEditText.setText([CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]editedLayerName != [CtLiteralImpl]null ? [CtInvocationImpl][CtFieldReadImpl]editedLayerName.replace([CtTypeAccessImpl]IndexConstants.SQLITE_EXT, [CtLiteralImpl]"") : [CtLiteralImpl]"");
        [CtInvocationImpl][CtFieldReadImpl]urlEditText.setText([CtFieldReadImpl]urlToLoad);
        [CtInvocationImpl]addConfigurationItems([CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem.values());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onHelpClick() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent i = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]android.content.Intent.ACTION_VIEW);
        [CtInvocationImpl][CtVariableReadImpl]i.setData([CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.MAPS_PLUGINS_URL));
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]net.osmand.AndroidUtils.isIntentSafe([CtFieldReadImpl]app, [CtVariableReadImpl]i)) [CtBlockImpl]{
            [CtInvocationImpl]startActivity([CtVariableReadImpl]i);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void showExitDialog() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Context themedContext = [CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.UiUtilities.getThemedContext([CtInvocationImpl]getActivity(), [CtFieldReadImpl]nightMode);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.appcompat.app.AlertDialog.Builder dismissDialog = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.appcompat.app.AlertDialog.Builder([CtVariableReadImpl]themedContext);
        [CtInvocationImpl][CtVariableReadImpl]dismissDialog.setTitle([CtInvocationImpl]getString([CtTypeAccessImpl]R.string.shared_string_dismiss));
        [CtInvocationImpl][CtVariableReadImpl]dismissDialog.setMessage([CtInvocationImpl]getString([CtTypeAccessImpl]R.string.exit_without_saving));
        [CtInvocationImpl][CtVariableReadImpl]dismissDialog.setNegativeButton([CtTypeAccessImpl]R.string.shared_string_cancel, [CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]dismissDialog.setPositiveButton([CtTypeAccessImpl]R.string.shared_string_exit, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.DialogInterface.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.content.DialogInterface dialog, [CtParameterImpl][CtTypeReferenceImpl]int which) [CtBlockImpl]{
                [CtInvocationImpl]dismiss();
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]dismissDialog.show();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getDescription([CtParameterImpl][CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem item) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]item) {
            [CtCaseImpl]case ZOOM_LEVELS :
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String min = [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.ltr_or_rtl_combine_via_space, [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.shared_string_min), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtFieldReadImpl]minZoom));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String max = [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.ltr_or_rtl_combine_via_space, [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.shared_string_max), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtFieldReadImpl]maxZoom));
                [CtReturnImpl]return [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.ltr_or_rtl_combine_via_bold_point, [CtVariableReadImpl]min, [CtVariableReadImpl]max);
            [CtCaseImpl]case EXPIRE_TIME :
                [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]expireTimeMinutes == [CtFieldReadImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.EXPIRE_TIME_NEVER ? [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.shared_string_never) : [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.ltr_or_rtl_combine_via_space, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtFieldReadImpl]expireTimeMinutes), [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.osmand_parking_minute));
            [CtCaseImpl]case MERCATOR_PROJECTION :
                [CtReturnImpl]return [CtConditionalImpl][CtFieldReadImpl]elliptic ? [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.edit_tilesource_elliptic_tile) : [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.pseudo_mercator_projection);
            [CtCaseImpl]case STORAGE_FORMAT :
                [CtReturnImpl]return [CtConditionalImpl][CtFieldReadImpl]sqliteDB ? [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.sqlite_db_file) : [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.one_image_per_tile);
            [CtCaseImpl]default :
                [CtReturnImpl]return [CtLiteralImpl]"";
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]View.OnClickListener getClickListener([CtParameterImpl]final [CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem item) [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.fragment.app.FragmentManager fm = [CtInvocationImpl]getFragmentManager();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]fm != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]fm.isStateSaved())) [CtBlockImpl]{
                    [CtSwitchImpl]switch ([CtVariableReadImpl]item) {
                        [CtCaseImpl]case ZOOM_LEVELS :
                            [CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.InputZoomLevelsBottomSheet.showInstance([CtVariableReadImpl]fm, [CtThisAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.this, [CtTypeAccessImpl]R.string.map_source_zoom_levels, [CtTypeAccessImpl]R.string.map_source_zoom_levels_descr, [CtFieldReadImpl]minZoom, [CtFieldReadImpl]maxZoom);
                            [CtBreakImpl]break;
                        [CtCaseImpl]case EXPIRE_TIME :
                            [CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.ExpireTimeBottomSheet.showInstance([CtVariableReadImpl]fm, [CtThisAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.this, [CtFieldReadImpl]expireTimeMinutes);
                            [CtBreakImpl]break;
                        [CtCaseImpl]case MERCATOR_PROJECTION :
                            [CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.MercatorProjectionBottomSheet.showInstance([CtVariableReadImpl]fm, [CtThisAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.this, [CtFieldReadImpl]elliptic);
                            [CtBreakImpl]break;
                        [CtCaseImpl]case STORAGE_FORMAT :
                            [CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.TileStorageFormatBottomSheet.showInstance([CtVariableReadImpl]fm, [CtThisAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.this, [CtFieldReadImpl]sqliteDB);
                            [CtBreakImpl]break;
                    }
                }
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addConfigurationItems([CtParameterImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem... items) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.LayoutInflater inflater = [CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.UiUtilities.getMaterialInflater([CtFieldReadImpl]app, [CtFieldReadImpl]nightMode);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem item : [CtVariableReadImpl]items) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.View view = [CtInvocationImpl][CtVariableReadImpl]inflater.inflate([CtTypeAccessImpl]R.layout.list_item_ui_customization, [CtLiteralImpl]null);
            [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]android.widget.ImageView) ([CtVariableReadImpl]view.findViewById([CtTypeAccessImpl]R.id.icon))).setImageDrawable([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]app.getUIUtilities().getIcon([CtFieldReadImpl][CtVariableReadImpl]item.iconRes, [CtFieldReadImpl]nightMode));
            [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) ([CtVariableReadImpl]view.findViewById([CtTypeAccessImpl]R.id.title))).setText([CtFieldReadImpl][CtVariableReadImpl]item.titleRes);
            [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) ([CtVariableReadImpl]view.findViewById([CtTypeAccessImpl]R.id.sub_title))).setText([CtInvocationImpl]getDescription([CtVariableReadImpl]item));
            [CtInvocationImpl][CtVariableReadImpl]view.setOnClickListener([CtInvocationImpl]getClickListener([CtVariableReadImpl]item));
            [CtInvocationImpl][CtFieldReadImpl]contentContainer.addView([CtVariableReadImpl]view);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateDescription([CtParameterImpl][CtTypeReferenceImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem item) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.View view = [CtInvocationImpl][CtFieldReadImpl]contentContainer.getChildAt([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.ArrayUtils.indexOf([CtInvocationImpl][CtTypeAccessImpl]net.osmand.plus.mapsource.EditMapSourceDialogFragment.ConfigurationItem.values(), [CtVariableReadImpl]item));
        [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) ([CtVariableReadImpl]view.findViewById([CtTypeAccessImpl]R.id.sub_title))).setText([CtInvocationImpl]getDescription([CtVariableReadImpl]item));
    }

    [CtEnumImpl]private enum ConfigurationItem {

        [CtEnumValueImpl]ZOOM_LEVELS([CtFieldReadImpl]R.drawable.ic_action_layers, [CtFieldReadImpl]R.string.shared_string_zoom_levels),
        [CtEnumValueImpl]EXPIRE_TIME([CtFieldReadImpl]R.drawable.ic_action_time_span, [CtFieldReadImpl]R.string.expire_time),
        [CtEnumValueImpl]MERCATOR_PROJECTION([CtFieldReadImpl]R.drawable.ic_world_globe_dark, [CtFieldReadImpl]R.string.mercator_projection),
        [CtEnumValueImpl]STORAGE_FORMAT([CtFieldReadImpl]R.drawable.ic_sdcard, [CtFieldReadImpl]R.string.storage_format);
        [CtFieldImpl][CtAnnotationImpl]@androidx.annotation.DrawableRes
        public [CtTypeReferenceImpl]int iconRes;

        [CtFieldImpl][CtAnnotationImpl]@androidx.annotation.StringRes
        public [CtTypeReferenceImpl]int titleRes;

        [CtConstructorImpl]ConfigurationItem([CtParameterImpl][CtTypeReferenceImpl]int iconRes, [CtParameterImpl][CtTypeReferenceImpl]int titleRes) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.titleRes = [CtVariableReadImpl]titleRes;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.iconRes = [CtVariableReadImpl]iconRes;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setEditedLayerName([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String editedLayerName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.editedLayerName = [CtVariableReadImpl]editedLayerName;
    }

    [CtInterfaceImpl]public interface OnMapSourceUpdateListener {
        [CtMethodImpl][CtTypeReferenceImpl]void onMapSourceUpdated();
    }
}
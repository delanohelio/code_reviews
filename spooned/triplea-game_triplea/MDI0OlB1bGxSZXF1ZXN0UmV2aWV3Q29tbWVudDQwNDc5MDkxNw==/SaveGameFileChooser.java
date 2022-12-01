[CompilationUnitImpl][CtPackageDeclarationImpl]package games.strategy.engine.framework.ui;
[CtUnresolvedImport]import games.strategy.engine.data.GameData;
[CtImportImpl]import java.awt.Frame;
[CtImportImpl]import java.time.ZonedDateTime;
[CtUnresolvedImport]import games.strategy.engine.framework.GameDataFileUtils;
[CtImportImpl]import javax.swing.JFileChooser;
[CtUnresolvedImport]import games.strategy.triplea.settings.ClientSetting;
[CtImportImpl]import java.awt.FileDialog;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.StringJoiner;
[CtClassImpl][CtJavaDocImpl]/**
 * A file chooser for save games. Defaults to the user's configured save game folder.
 */
public final class SaveGameFileChooser extends [CtTypeReferenceImpl]javax.swing.JFileChooser {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1548668790891292106L;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Displays a file chooser dialog for the user to select the file to which the current game should
     * be saved.
     *
     * @param frame
     * 		The owner of the file chooser dialog; may be {@code null}.
     * @return The file to which the current game should be saved or {@code null} if the user
    cancelled the operation.
     */
    public static [CtTypeReferenceImpl]java.io.File getSaveGameLocation([CtParameterImpl]final [CtTypeReferenceImpl]java.awt.Frame frame, [CtParameterImpl]final [CtTypeReferenceImpl]games.strategy.engine.data.GameData gameData) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.awt.FileDialog fileDialog = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.awt.FileDialog([CtVariableReadImpl]frame);
        [CtInvocationImpl][CtVariableReadImpl]fileDialog.setMode([CtFieldReadImpl][CtTypeAccessImpl]java.awt.FileDialog.[CtFieldReferenceImpl]SAVE);
        [CtInvocationImpl][CtVariableReadImpl]fileDialog.setDirectory([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]ClientSetting.saveGamesFolderPath.getValueOrThrow().toString());
        [CtInvocationImpl][CtVariableReadImpl]fileDialog.setFilenameFilter([CtLambdaImpl]([CtParameterImpl]java.io.File dir,[CtParameterImpl]java.lang.String name) -> [CtInvocationImpl][CtTypeAccessImpl]games.strategy.engine.framework.GameDataFileUtils.isCandidateFileName([CtVariableReadImpl]name));
        [CtInvocationImpl][CtVariableReadImpl]fileDialog.setFile([CtInvocationImpl]games.strategy.engine.framework.ui.SaveGameFileChooser.getSaveGameName([CtVariableReadImpl]gameData));
        [CtInvocationImpl][CtVariableReadImpl]fileDialog.setVisible([CtLiteralImpl]true);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String fileName = [CtInvocationImpl][CtVariableReadImpl]fileDialog.getFile();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fileName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl][CtCommentImpl]// If the user selects a filename that already exists,
        [CtCommentImpl]// the AWT Dialog will ask the user for confirmation
        return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtVariableReadImpl]fileDialog.getDirectory(), [CtInvocationImpl][CtTypeAccessImpl]games.strategy.engine.framework.GameDataFileUtils.addExtensionIfAbsent([CtVariableReadImpl]fileName));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getSaveGameName([CtParameterImpl]final [CtTypeReferenceImpl]games.strategy.engine.data.GameData gameData) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]gameData.getSaveGameFileName().orElse([CtInvocationImpl]games.strategy.engine.framework.ui.SaveGameFileChooser.formatGameName([CtInvocationImpl][CtVariableReadImpl]gameData.getGameName()));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String formatGameName([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String gameName) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.time.ZonedDateTime now = [CtInvocationImpl][CtTypeAccessImpl]java.time.ZonedDateTime.now();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.StringJoiner([CtLiteralImpl]"-").add([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]now.getYear())).add([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]now.getMonthValue())).add([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]now.getDayOfMonth())).add([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]gameName.replaceAll([CtLiteralImpl]" ", [CtLiteralImpl]"-") + [CtInvocationImpl][CtTypeAccessImpl]games.strategy.engine.framework.GameDataFileUtils.getExtension()).toString();
    }
}
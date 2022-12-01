[CompilationUnitImpl][CtPackageDeclarationImpl]package org.commcare.recovery.measures;
[CtUnresolvedImport]import org.javarosa.xml.ElementParser;
[CtUnresolvedImport]import android.os.Environment;
[CtUnresolvedImport]import org.javarosa.core.io.StreamsUtil;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.commcare.modern.util.Pair;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.commcare.tasks.templates.CommCareTask;
[CtUnresolvedImport]import org.javarosa.xml.util.UnfullfilledRequirementsException;
[CtUnresolvedImport]import org.commcare.preferences.HiddenPreferences;
[CtUnresolvedImport]import org.commcare.util.LogTypes;
[CtUnresolvedImport]import org.javarosa.xml.util.InvalidStructureException;
[CtUnresolvedImport]import org.xmlpull.v1.XmlPullParserException;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import org.commcare.CommCareApplication;
[CtUnresolvedImport]import org.javarosa.core.services.Logger;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtImportImpl]import java.util.zip.ZipFile;
[CtImportImpl]import java.io.File;
[CtClassImpl]public class ScanCczTask extends [CtTypeReferenceImpl]org.commcare.tasks.templates.CommCareTask<[CtTypeReferenceImpl]java.lang.Void, [CtTypeReferenceImpl]java.io.File, [CtTypeReferenceImpl]java.io.File, [CtTypeReferenceImpl]org.commcare.recovery.measures.ExecuteRecoveryMeasuresActivity> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CCZ_EXTENSION = [CtLiteralImpl]".ccz";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PROFILE_FILE_NAME = [CtLiteralImpl]"profile.ccpr";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MAX_SCAN_DEPTH = [CtLiteralImpl]10;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.io.File latestProfileFile = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.io.File doTaskBackground([CtParameterImpl]java.lang.Void... voids) [CtBlockImpl]{
        [CtInvocationImpl]locateAppCcz([CtInvocationImpl]getPathsToScan(), [CtLiteralImpl]0);
        [CtReturnImpl]return [CtFieldReadImpl]latestProfileFile;
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    private [CtTypeReferenceImpl]void locateAppCcz([CtParameterImpl][CtArrayTypeReferenceImpl]java.io.File[] files, [CtParameterImpl][CtTypeReferenceImpl]int depth) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int latestVersion = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File f : [CtVariableReadImpl]files) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]f.isDirectory() && [CtBinaryOperatorImpl]([CtVariableReadImpl]depth <= [CtFieldReadImpl]org.commcare.recovery.measures.ScanCczTask.MAX_SCAN_DEPTH)) [CtBlockImpl]{
                [CtInvocationImpl]locateAppCcz([CtInvocationImpl][CtVariableReadImpl]f.listFiles(), [CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]1);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]f.isFile() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getName().endsWith([CtFieldReadImpl]org.commcare.recovery.measures.ScanCczTask.CCZ_EXTENSION)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.commcare.modern.util.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> profileIdAndVersion = [CtInvocationImpl]getProfileIdAndVersionFromCcz([CtVariableReadImpl]f);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String currentAppId = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.commcare.CommCareApplication.instance().getCurrentApp().getUniqueId();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]currentAppId.contentEquals([CtFieldReadImpl][CtVariableReadImpl]profileIdAndVersion.first)) [CtBlockImpl]{
                        [CtIfImpl][CtCommentImpl]// We have a match, if it's the latest version till now, return the ccz in update
                        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]profileIdAndVersion.second > [CtVariableReadImpl]latestVersion) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]latestVersion = [CtFieldReadImpl][CtVariableReadImpl]profileIdAndVersion.second;
                            [CtAssignmentImpl][CtFieldWriteImpl]latestProfileFile = [CtVariableReadImpl]f;
                            [CtInvocationImpl]publishProgress([CtFieldReadImpl]latestProfileFile);
                        }
                    }
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.javarosa.xml.util.InvalidStructureException | [CtTypeReferenceImpl]org.xmlpull.v1.XmlPullParserException | [CtTypeReferenceImpl]org.javarosa.xml.util.UnfullfilledRequirementsException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
                }
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]// Unzip profile and parses id and version from it
    private [CtTypeReferenceImpl]org.commcare.modern.util.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> getProfileIdAndVersionFromCcz([CtParameterImpl][CtTypeReferenceImpl]java.io.File cczFile) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.javarosa.xml.util.UnfullfilledRequirementsException, [CtTypeReferenceImpl]org.xmlpull.v1.XmlPullParserException, [CtTypeReferenceImpl]org.javarosa.xml.util.InvalidStructureException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream profileStream = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.zip.ZipFile zipFile = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]zipFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.zip.ZipFile([CtVariableReadImpl]cczFile);
            [CtAssignmentImpl][CtVariableWriteImpl]profileStream = [CtInvocationImpl][CtVariableReadImpl]zipFile.getInputStream([CtInvocationImpl][CtVariableReadImpl]zipFile.getEntry([CtFieldReadImpl]org.commcare.recovery.measures.ScanCczTask.PROFILE_FILE_NAME));
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getProfileParser([CtVariableReadImpl]profileStream).parse();
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.javarosa.core.io.StreamsUtil.closeStream([CtVariableReadImpl]profileStream);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]zipFile != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]zipFile.close();
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.javarosa.xml.ElementParser<[CtTypeReferenceImpl]org.commcare.modern.util.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer>> getProfileParser([CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream profileStream) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]org.javarosa.xml.ElementParser<[CtTypeReferenceImpl]org.commcare.modern.util.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer>>([CtInvocationImpl][CtTypeAccessImpl]org.javarosa.xml.ElementParser.instantiateParser([CtVariableReadImpl]profileStream))[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.commcare.modern.util.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> parse() throws [CtTypeReferenceImpl]org.javarosa.xml.util.InvalidStructureException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int version = [CtInvocationImpl]parseInt([CtInvocationImpl][CtFieldReadImpl]parser.getAttributeValue([CtLiteralImpl]null, [CtLiteralImpl]"version"));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uniqueId = [CtInvocationImpl][CtFieldReadImpl]parser.getAttributeValue([CtLiteralImpl]null, [CtLiteralImpl]"uniqueid");
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.commcare.modern.util.Pair<>([CtVariableReadImpl]uniqueId, [CtVariableReadImpl]version);
            }
        };
    }

    [CtMethodImpl]private [CtArrayTypeReferenceImpl]java.io.File[] getPathsToScan() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lastKnownCczLocation = [CtInvocationImpl][CtTypeAccessImpl]org.commcare.preferences.HiddenPreferences.getLastKnownCczLocation();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.io.File> filePathsToScan = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtLiteralImpl]3);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isEmpty([CtVariableReadImpl]lastKnownCczLocation)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]filePathsToScan.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]lastKnownCczLocation));
        }
        [CtInvocationImpl][CtTypeAccessImpl]org.javarosa.core.services.Logger.log([CtTypeAccessImpl]LogTypes.SOFT_ASSERT, [CtLiteralImpl]"Access outside scoped storage in scanning cczs.");
        [CtInvocationImpl][CtVariableReadImpl]filePathsToScan.add([CtInvocationImpl][CtTypeAccessImpl]android.os.Environment.getExternalStoragePublicDirectory([CtTypeAccessImpl]Environment.DIRECTORY_DOWNLOADS));
        [CtInvocationImpl][CtVariableReadImpl]filePathsToScan.add([CtInvocationImpl][CtTypeAccessImpl]android.os.Environment.getExternalStoragePublicDirectory([CtTypeAccessImpl]Environment.DIRECTORY_DOCUMENTS));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]filePathsToScan.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.io.File[[CtInvocationImpl][CtVariableReadImpl]filePathsToScan.size()]);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void deliverResult([CtParameterImpl][CtTypeReferenceImpl]org.commcare.recovery.measures.ExecuteRecoveryMeasuresActivity activity, [CtParameterImpl][CtTypeReferenceImpl]java.io.File archive) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]activity.onCczScanComplete();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void deliverUpdate([CtParameterImpl][CtTypeReferenceImpl]org.commcare.recovery.measures.ExecuteRecoveryMeasuresActivity activity, [CtParameterImpl]java.io.File... archive) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]activity.updateCcz([CtArrayReadImpl][CtVariableReadImpl]archive[[CtLiteralImpl]0]);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void deliverError([CtParameterImpl][CtTypeReferenceImpl]org.commcare.recovery.measures.ExecuteRecoveryMeasuresActivity activity, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]activity.onCczScanFailed([CtVariableReadImpl]e);
    }
}
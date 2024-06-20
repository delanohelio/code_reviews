[CompilationUnitImpl][CtCommentImpl]/* Minecraft Forge
Copyright (c) 2016-2020.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation version 2.1
of the License.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
[CtPackageDeclarationImpl]package net.minecraftforge.fml.config;
[CtUnresolvedImport]import net.minecraftforge.fml.loading.FMLPaths;
[CtImportImpl]import java.nio.file.Path;
[CtUnresolvedImport]import com.electronwill.nightconfig.core.io.WritingMode;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.electronwill.nightconfig.core.file.CommentedFileConfig;
[CtUnresolvedImport]import com.electronwill.nightconfig.core.ConfigFormat;
[CtUnresolvedImport]import com.electronwill.nightconfig.core.file.FileWatcher;
[CtImportImpl]import org.apache.commons.io.FilenameUtils;
[CtUnresolvedImport]import com.electronwill.nightconfig.core.io.ParsingException;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import static net.minecraftforge.fml.config.ConfigTracker.CONFIG;
[CtUnresolvedImport]import org.apache.logging.log4j.Logger;
[CtImportImpl]import java.nio.file.Files;
[CtUnresolvedImport]import net.minecraftforge.fml.loading.FMLConfig;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.apache.logging.log4j.LogManager;
[CtClassImpl]public class ConfigFileTypeHandler {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.logging.log4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.LogManager.getLogger();

    [CtFieldImpl]static [CtTypeReferenceImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler TOML = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.nio.file.Path defaultConfigPath = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]FMLPaths.GAMEDIR.get().resolve([CtInvocationImpl][CtTypeAccessImpl]net.minecraftforge.fml.loading.FMLConfig.defaultConfigPath());

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]net.minecraftforge.fml.config.ModConfig, [CtTypeReferenceImpl]com.electronwill.nightconfig.core.file.CommentedFileConfig> reader([CtParameterImpl][CtTypeReferenceImpl]java.nio.file.Path configBasePath) [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]net.minecraftforge.fml.config.ModConfig c) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path configPath = [CtInvocationImpl][CtVariableReadImpl]configBasePath.resolve([CtInvocationImpl][CtVariableReadImpl]c.getFileName());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.electronwill.nightconfig.core.file.CommentedFileConfig configData = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.electronwill.nightconfig.core.file.CommentedFileConfig.builder([CtVariableReadImpl]configPath).sync().preserveInsertionOrder().autosave().onFileNotFound([CtLambdaImpl]([CtParameterImpl] newfile,[CtParameterImpl] configFormat) -> [CtInvocationImpl]setupConfigFile([CtVariableReadImpl]c, [CtVariableReadImpl]newfile, [CtVariableReadImpl]configFormat)).writingMode([CtTypeAccessImpl]WritingMode.REPLACE).build();
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.debug([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Built TOML config for {}", [CtInvocationImpl][CtVariableReadImpl]configPath.toString());
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]configData.load();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.electronwill.nightconfig.core.io.ParsingException ex) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.ConfigLoadingException([CtVariableReadImpl]c, [CtVariableReadImpl]ex);
            }
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.debug([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Loaded TOML config file {}", [CtInvocationImpl][CtVariableReadImpl]configPath.toString());
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.electronwill.nightconfig.core.file.FileWatcher.defaultInstance().addWatch([CtVariableReadImpl]configPath, [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.ConfigWatcher([CtVariableReadImpl]c, [CtVariableReadImpl]configData, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader()));
                [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.debug([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Watching TOML config file {} for changes", [CtInvocationImpl][CtVariableReadImpl]configPath.toString());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Couldn't watch config file", [CtVariableReadImpl]e);
            }
            [CtReturnImpl]return [CtVariableReadImpl]configData;
        };
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void unload([CtParameterImpl][CtTypeReferenceImpl]java.nio.file.Path configBasePath, [CtParameterImpl][CtTypeReferenceImpl]net.minecraftforge.fml.config.ModConfig config) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path configPath = [CtInvocationImpl][CtVariableReadImpl]configBasePath.resolve([CtInvocationImpl][CtVariableReadImpl]config.getFileName());
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.electronwill.nightconfig.core.file.FileWatcher.defaultInstance().removeWatch([CtInvocationImpl][CtVariableReadImpl]configBasePath.resolve([CtInvocationImpl][CtVariableReadImpl]config.getFileName()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.error([CtLiteralImpl]"Failed to remove config {} from tracker!", [CtInvocationImpl][CtVariableReadImpl]configPath.toString(), [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean setupConfigFile([CtParameterImpl]final [CtTypeReferenceImpl]net.minecraftforge.fml.config.ModConfig modConfig, [CtParameterImpl]final [CtTypeReferenceImpl]java.nio.file.Path file, [CtParameterImpl]final [CtTypeReferenceImpl]com.electronwill.nightconfig.core.ConfigFormat<[CtWildcardReferenceImpl]?> conf) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path p = [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.defaultConfigPath.resolve([CtInvocationImpl][CtVariableReadImpl]modConfig.getFileName());
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtVariableReadImpl]p)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.info([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Loading default config file from path {}", [CtVariableReadImpl]p);
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.copy([CtVariableReadImpl]p, [CtVariableReadImpl]file);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createFile([CtVariableReadImpl]file);
            [CtInvocationImpl][CtVariableReadImpl]conf.initEmptyFile([CtVariableReadImpl]file);
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void backUpConfig([CtParameterImpl]final [CtTypeReferenceImpl]com.electronwill.nightconfig.core.file.CommentedFileConfig commentedFileConfig) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxBackups = [CtLiteralImpl]5;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File bakFileLocation = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]commentedFileConfig.getFile().getParentFile();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String bakFileName = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.FilenameUtils.removeExtension([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]commentedFileConfig.getFile().getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String bakFileExtension = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.FilenameUtils.getExtension([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]commentedFileConfig.getFile().getName()) + [CtLiteralImpl]".bak";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File bakFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]bakFileLocation, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]bakFileName + [CtLiteralImpl]"-1") + [CtLiteralImpl]".") + [CtVariableReadImpl]bakFileExtension);
        [CtTryImpl]try [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtVariableReadImpl]maxBackups; [CtBinaryOperatorImpl][CtVariableReadImpl]i > [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File oldBak = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]bakFileLocation, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]bakFileName + [CtLiteralImpl]"-") + [CtVariableReadImpl]i) + [CtLiteralImpl]".") + [CtVariableReadImpl]bakFileExtension);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]oldBak.exists()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i == [CtVariableReadImpl]maxBackups) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]oldBak.delete())[CtBlockImpl]
                            [CtContinueImpl]continue;

                        [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.warn([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Failed to back up config file {} as the oldest backup, {}, could not be deleted", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]commentedFileConfig.getFile().getName(), [CtInvocationImpl][CtVariableReadImpl]oldBak.getAbsolutePath());
                        [CtReturnImpl]return;
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]oldBak.renameTo([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]bakFileLocation, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]bakFileName + [CtLiteralImpl]"-") + [CtBinaryOperatorImpl]([CtVariableReadImpl]i + [CtLiteralImpl]1)) + [CtLiteralImpl]".") + [CtVariableReadImpl]bakFileExtension)))[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.warn([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Failed to back up config file {} as an old backup, {}, could not be renamed", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]commentedFileConfig.getFile().getName(), [CtInvocationImpl][CtVariableReadImpl]oldBak.getAbsolutePath());

                }
            }
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.copy([CtInvocationImpl][CtVariableReadImpl]commentedFileConfig.getNioPath(), [CtInvocationImpl][CtVariableReadImpl]bakFile.toPath());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException exception) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.warn([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Failed to back up config file {}", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]commentedFileConfig.getFile().getName());
        }
    }

    [CtClassImpl]private static class ConfigWatcher implements [CtTypeReferenceImpl]java.lang.Runnable {
        [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraftforge.fml.config.ModConfig modConfig;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.electronwill.nightconfig.core.file.CommentedFileConfig commentedFileConfig;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.ClassLoader realClassLoader;

        [CtConstructorImpl]ConfigWatcher([CtParameterImpl]final [CtTypeReferenceImpl]net.minecraftforge.fml.config.ModConfig modConfig, [CtParameterImpl]final [CtTypeReferenceImpl]com.electronwill.nightconfig.core.file.CommentedFileConfig commentedFileConfig, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.ClassLoader classLoader) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.modConfig = [CtVariableReadImpl]modConfig;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.commentedFileConfig = [CtVariableReadImpl]commentedFileConfig;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.realClassLoader = [CtVariableReadImpl]classLoader;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Force the regular classloader onto the special thread
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().setContextClassLoader([CtFieldReadImpl]realClassLoader);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.modConfig.getSpec().isCorrecting()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.commentedFileConfig.load();
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.modConfig.getSpec().isCorrect([CtFieldReadImpl]commentedFileConfig)) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.warn([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Configuration file {} is not correct. Correcting", [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]commentedFileConfig.getFile().getAbsolutePath());
                        [CtInvocationImpl][CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.backUpConfig([CtFieldReadImpl]commentedFileConfig);
                        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.modConfig.getSpec().correct([CtFieldReadImpl]commentedFileConfig);
                        [CtInvocationImpl][CtFieldReadImpl]commentedFileConfig.save();
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.electronwill.nightconfig.core.io.ParsingException ex) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.ConfigLoadingException([CtFieldReadImpl]modConfig, [CtVariableReadImpl]ex);
                }
                [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.fml.config.ConfigFileTypeHandler.LOGGER.debug([CtTypeAccessImpl]net.minecraftforge.fml.config.ConfigTracker.CONFIG, [CtLiteralImpl]"Config file {} changed, sending notifies", [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.modConfig.getFileName());
                [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.modConfig.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.fml.config.ModConfig.Reloading([CtFieldReadImpl][CtThisAccessImpl]this.modConfig));
            }
        }
    }

    [CtClassImpl]private static class ConfigLoadingException extends [CtTypeReferenceImpl]java.lang.RuntimeException {
        [CtConstructorImpl]public ConfigLoadingException([CtParameterImpl][CtTypeReferenceImpl]net.minecraftforge.fml.config.ModConfig config, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception cause) [CtBlockImpl]{
            [CtInvocationImpl]super([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed loading config file " + [CtInvocationImpl][CtVariableReadImpl]config.getFileName()) + [CtLiteralImpl]" of type ") + [CtInvocationImpl][CtVariableReadImpl]config.getType()) + [CtLiteralImpl]" for modid ") + [CtInvocationImpl][CtVariableReadImpl]config.getModId(), [CtVariableReadImpl]cause);
        }
    }
}
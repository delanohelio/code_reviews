[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.

 WSO2 Inc. licenses this file to you under the Apache License,
 Version 2.0 (the "License"); you may not use this file except
 in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
[CtPackageDeclarationImpl]package org.ballerinalang.packerina.cmd;
[CtImportImpl]import java.util.regex.Pattern;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.ballerinalang.repository.CompilerInput;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.packaging.repo.RemoteRepo;
[CtImportImpl]import java.io.PrintStream;
[CtUnresolvedImport]import static org.ballerinalang.jvm.runtime.RuntimeConstants.SYSTEM_PROP_BAL_DEBUG;
[CtUnresolvedImport]import org.ballerinalang.tool.LauncherUtils;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.packaging.Patten;
[CtUnresolvedImport]import static org.ballerinalang.packerina.cmd.Constants.MODULE_NAME_REGEX;
[CtUnresolvedImport]import org.ballerinalang.model.elements.PackageID;
[CtImportImpl]import java.net.URI;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.util.Name;
[CtUnresolvedImport]import org.wso2.ballerinalang.util.RepoUtils;
[CtUnresolvedImport]import org.ballerinalang.tool.BLauncherCmd;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.packaging.converters.Converter;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.packaging.repo.Repo;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.util.Names;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import static org.ballerinalang.packerina.cmd.Constants.PULL_COMMAND;
[CtUnresolvedImport]import picocli.CommandLine;
[CtClassImpl][CtJavaDocImpl]/**
 * This class represents the "ballerina pull" command.
 *
 * @since 0.964
 */
[CtAnnotationImpl]@CommandLine.Command(name = [CtFieldReadImpl]org.ballerinalang.packerina.cmd.Constants.PULL_COMMAND, description = [CtLiteralImpl]"download the module source and binaries from a remote repository")
public class PullCommand implements [CtTypeReferenceImpl]org.ballerinalang.tool.BLauncherCmd {
    [CtFieldImpl]private static [CtTypeReferenceImpl]java.io.PrintStream outStream = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]err;

    [CtFieldImpl][CtAnnotationImpl]@picocli.CommandLine.Parameters
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> argList;

    [CtFieldImpl][CtAnnotationImpl]@CommandLine.Option(names = [CtNewArrayImpl]{ [CtLiteralImpl]"--help", [CtLiteralImpl]"-h" }, hidden = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]boolean helpFlag;

    [CtFieldImpl][CtAnnotationImpl]@CommandLine.Option(names = [CtLiteralImpl]"--debug", hidden = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]java.lang.String debugPort;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void execute() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]helpFlag) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commandUsageInfo = [CtInvocationImpl][CtTypeAccessImpl]org.ballerinalang.tool.BLauncherCmd.getCommandUsageInfo([CtTypeAccessImpl]org.ballerinalang.packerina.cmd.Constants.PULL_COMMAND);
            [CtInvocationImpl][CtFieldReadImpl]org.ballerinalang.packerina.cmd.PullCommand.outStream.println([CtVariableReadImpl]commandUsageInfo);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]argList == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]argList.size() == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.ballerinalang.tool.LauncherUtils.createUsageExceptionWithHelp([CtLiteralImpl]"no module given");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]argList.size() > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.ballerinalang.tool.LauncherUtils.createUsageExceptionWithHelp([CtLiteralImpl]"too many arguments");
        }
        [CtIfImpl][CtCommentImpl]// Enable remote debugging
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]debugPort) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtTypeAccessImpl]org.ballerinalang.packerina.cmd.SYSTEM_PROP_BAL_DEBUG, [CtFieldReadImpl]debugPort);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resourceName = [CtInvocationImpl][CtFieldReadImpl]argList.get([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String orgName;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String packageName;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String moduleName;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String version;
        [CtIfImpl][CtCommentImpl]// Get org-name
        if ([CtUnaryOperatorImpl]![CtInvocationImpl]validateModuleName([CtVariableReadImpl]resourceName)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.ballerinalang.packerina.cmd.CommandUtil.printError([CtFieldReadImpl]org.ballerinalang.packerina.cmd.PullCommand.outStream, [CtLiteralImpl]"invalid module name. Provide the module-name with the org-name ", [CtLiteralImpl]"ballerina pull {<org-name>/<module-name> | <org-name>/<module-name>:<version>}", [CtLiteralImpl]false);
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exit([CtLiteralImpl]1);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] moduleInfo = [CtInvocationImpl][CtVariableReadImpl]resourceName.split([CtLiteralImpl]"/");
        [CtAssignmentImpl][CtVariableWriteImpl]orgName = [CtArrayReadImpl][CtVariableReadImpl]moduleInfo[[CtLiteralImpl]0];
        [CtAssignmentImpl][CtVariableWriteImpl]packageName = [CtArrayReadImpl][CtVariableReadImpl]moduleInfo[[CtLiteralImpl]1];
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]orgName.equals([CtLiteralImpl]"ballerina")) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.ballerinalang.tool.LauncherUtils.createLauncherException([CtBinaryOperatorImpl][CtLiteralImpl]"`ballerina` is the builtin organization and its modules" + [CtLiteralImpl]" are included in the runtime.");
        }
        [CtLocalVariableImpl][CtCommentImpl]// Get module name
        [CtArrayTypeReferenceImpl]java.lang.String[] packageInfo = [CtInvocationImpl][CtVariableReadImpl]packageName.split([CtLiteralImpl]":");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]packageName.length() == [CtLiteralImpl]2) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// version is provided
            [CtVariableWriteImpl]moduleName = [CtArrayReadImpl][CtVariableReadImpl]packageInfo[[CtLiteralImpl]0];
            [CtAssignmentImpl][CtVariableWriteImpl]version = [CtArrayReadImpl][CtVariableReadImpl]packageInfo[[CtLiteralImpl]1];
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]moduleName = [CtVariableReadImpl]packageName;
            [CtAssignmentImpl][CtVariableWriteImpl]version = [CtInvocationImpl][CtTypeAccessImpl]Names.EMPTY.getValue();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI baseURI = [CtInvocationImpl][CtTypeAccessImpl]java.net.URI.create([CtInvocationImpl][CtTypeAccessImpl]org.wso2.ballerinalang.util.RepoUtils.getRemoteRepoURL());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.packaging.repo.Repo remoteRepo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.packaging.repo.RemoteRepo([CtVariableReadImpl]baseURI, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.ballerinalang.model.elements.PackageID moduleID = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.ballerinalang.model.elements.PackageID([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name([CtVariableReadImpl]orgName), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name([CtVariableReadImpl]moduleName), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name([CtVariableReadImpl]version));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.packaging.Patten patten = [CtInvocationImpl][CtVariableReadImpl]remoteRepo.calculate([CtVariableReadImpl]moduleID);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]patten != [CtFieldReadImpl]org.wso2.ballerinalang.compiler.packaging.Patten.NULL) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.packaging.converters.Converter converter = [CtInvocationImpl][CtVariableReadImpl]remoteRepo.getConverterInstance();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.ballerinalang.repository.CompilerInput> compilerInputs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]patten.convertToSources([CtVariableReadImpl]converter, [CtVariableReadImpl]moduleID).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]compilerInputs.size() == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Exit status, zero for OK, non-zero for error
                [CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exit([CtLiteralImpl]1);
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.ballerinalang.packerina.cmd.PullCommand.outStream.println([CtBinaryOperatorImpl][CtLiteralImpl]"couldn't find module " + [CtVariableReadImpl]patten);
            [CtInvocationImpl][CtCommentImpl]// Exit status, zero for OK, non-zero for error
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exit([CtLiteralImpl]1);
        }
        [CtInvocationImpl][CtCommentImpl]// Exit status, zero for OK, non-zero for error
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exit([CtLiteralImpl]0);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.ballerinalang.packerina.cmd.Constants.PULL_COMMAND;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void printLongDesc([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder out) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]out.append([CtLiteralImpl]"download modules to the user repository \n");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void printUsage([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder out) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]out.append([CtLiteralImpl]"  ballerina pull <module-name> \n");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setParentCmdParser([CtParameterImpl][CtTypeReferenceImpl]picocli.CommandLine parentCmdParser) [CtBlockImpl]{
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getPullCommandRegex() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.ballerinalang.packerina.cmd.Constants.MODULE_NAME_REGEX;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean validateModuleName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String str) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.matches([CtInvocationImpl]getPullCommandRegex(), [CtVariableReadImpl]str);
    }
}
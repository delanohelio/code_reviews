[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.storm.utils;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.apache.commons.lang.StringUtils;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import static org.junit.Assert.assertFalse;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.is;
[CtImportImpl]import java.lang.reflect.Field;
[CtImportImpl]import java.nio.file.Files;
[CtUnresolvedImport]import static org.junit.Assert.assertThat;
[CtImportImpl]import java.nio.file.Paths;
[CtUnresolvedImport]import static org.junit.Assert.fail;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.apache.commons.lang.RandomStringUtils;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import java.io.InputStreamReader;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.lang.reflect.Method;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.apache.storm.shade.org.apache.commons.lang.builder.ToStringBuilder;
[CtUnresolvedImport]import org.apache.storm.shade.org.apache.commons.lang.builder.ToStringStyle;
[CtImportImpl]import java.util.zip.ZipFile;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtImportImpl]import java.io.BufferedReader;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.apache.storm.testing.TmpPath;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl]public class ServerUtilsTest {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.class);

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testExtractZipFileDisallowsPathTraversal() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.testing.TmpPath path = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.testing.TmpPath()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path testRoot = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtInvocationImpl][CtVariableReadImpl]path.getPath());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path extractionDest = [CtInvocationImpl][CtVariableReadImpl]testRoot.resolve([CtLiteralImpl]"dest");
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtVariableReadImpl]extractionDest);
            [CtTryWithResourceImpl][CtCommentImpl]/* Contains good.txt and ../evil.txt. Evil.txt will path outside the target dir, and should not be extracted. */
            try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.zip.ZipFile zip = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.zip.ZipFile([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"src/test/resources/evil-path-traversal.jar").toFile())) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.extractZipFile([CtVariableReadImpl]zip, [CtInvocationImpl][CtVariableReadImpl]extractionDest.toFile(), [CtLiteralImpl]null);
            }
            [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtInvocationImpl][CtVariableReadImpl]extractionDest.resolve([CtLiteralImpl]"good.txt")), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]true));
            [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtInvocationImpl][CtVariableReadImpl]testRoot.resolve([CtLiteralImpl]"evil.txt")), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]false));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testExtractZipFileDisallowsPathTraversalWhenUsingPrefix() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.testing.TmpPath path = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.testing.TmpPath()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path testRoot = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtInvocationImpl][CtVariableReadImpl]path.getPath());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path destParent = [CtInvocationImpl][CtVariableReadImpl]testRoot.resolve([CtLiteralImpl]"outer");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path extractionDest = [CtInvocationImpl][CtVariableReadImpl]destParent.resolve([CtLiteralImpl]"resources");
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtVariableReadImpl]extractionDest);
            [CtTryWithResourceImpl][CtCommentImpl]/* Contains resources/good.txt and resources/../evil.txt. Evil.txt should not be extracted as it would end
            up outside the extraction dest.
             */
            try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.zip.ZipFile zip = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.zip.ZipFile([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"src/test/resources/evil-path-traversal-resources.jar").toFile())) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.extractZipFile([CtVariableReadImpl]zip, [CtInvocationImpl][CtVariableReadImpl]extractionDest.toFile(), [CtLiteralImpl]"resources");
            }
            [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtInvocationImpl][CtVariableReadImpl]extractionDest.resolve([CtLiteralImpl]"good.txt")), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]true));
            [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtInvocationImpl][CtVariableReadImpl]extractionDest.resolve([CtLiteralImpl]"evil.txt")), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]false));
            [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtInvocationImpl][CtVariableReadImpl]destParent.resolve([CtLiteralImpl]"evil.txt")), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]false));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get running processes for all or specified user.
     *
     * @param user
     * 		Null for all users, otherwise specify the user. e.g. "root"
     * @return List of ProcessIds for the user
     * @throws IOException
     */
    private [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Long> getRunningProcessIds([CtParameterImpl][CtTypeReferenceImpl]java.lang.String user) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get list of few running processes
        [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Long> pids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cmd = [CtConditionalImpl]([CtFieldReadImpl]ServerUtils.IS_ON_WINDOWS) ? [CtLiteralImpl]"tasklist" : [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]user == [CtLiteralImpl]null ? [CtLiteralImpl]"ps -e" : [CtBinaryOperatorImpl][CtLiteralImpl]"ps -U " + [CtVariableReadImpl]user;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Process p = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exec([CtVariableReadImpl]cmd);
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.BufferedReader input = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtInvocationImpl][CtVariableReadImpl]p.getInputStream()))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String line;
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]input.readLine()) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]line.trim();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]line.isEmpty() || [CtInvocationImpl][CtVariableReadImpl]line.startsWith([CtLiteralImpl]"PID")) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pidStr = [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]line.split([CtLiteralImpl]"\\s")[[CtLiteralImpl]0];
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]pidStr.equalsIgnoreCase([CtLiteralImpl]"pid")) [CtBlockImpl]{
                        [CtContinueImpl]continue;[CtCommentImpl]// header line

                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNumeric([CtVariableReadImpl]pidStr)) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.debug([CtLiteralImpl]"Ignoring line \"{}\" while looking for PIDs in output of \"{}\"", [CtVariableReadImpl]line, [CtVariableReadImpl]cmd);
                        [CtContinueImpl]continue;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]pids.add([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtVariableReadImpl]pidStr));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ex.printStackTrace();
                }
            } 
        }
        [CtReturnImpl]return [CtVariableReadImpl]pids;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testIsProcessAlive() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// specific selected process should not be alive for a randomly generated user
        [CtTypeReferenceImpl]java.lang.String randomUser = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.RandomStringUtils.randomAlphanumeric([CtLiteralImpl]12);
        [CtLocalVariableImpl][CtCommentImpl]// get list of few running processes
        [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Long> pids = [CtInvocationImpl]getRunningProcessIds([CtLiteralImpl]null);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]pids.isEmpty());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]long pid : [CtVariableReadImpl]pids) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean status = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isProcessAlive([CtVariableReadImpl]pid, [CtVariableReadImpl]randomUser);
            [CtInvocationImpl]Assert.assertFalse([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Random user " + [CtVariableReadImpl]randomUser) + [CtLiteralImpl]" is not expected to own any process", [CtVariableReadImpl]status);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean status = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String currentUser = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.name");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]long pid : [CtVariableReadImpl]pids) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// at least one pid will be owned by the current user (doing the testing)
            if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isProcessAlive([CtVariableReadImpl]pid, [CtVariableReadImpl]currentUser)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]status = [CtLiteralImpl]true;
                [CtBreakImpl]break;
            }
        }
        [CtInvocationImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Expecting user " + [CtVariableReadImpl]currentUser) + [CtLiteralImpl]" to own at least one process", [CtVariableReadImpl]status);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testIsAnyProcessAlive() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// no process should be alive for a randomly generated user
        [CtTypeReferenceImpl]java.lang.String randomUser = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.RandomStringUtils.randomAlphanumeric([CtLiteralImpl]12);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Long> pids = [CtInvocationImpl]getRunningProcessIds([CtLiteralImpl]null);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]pids.isEmpty());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean status = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isAnyProcessAlive([CtVariableReadImpl]pids, [CtVariableReadImpl]randomUser);
        [CtInvocationImpl]Assert.assertFalse([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Random user " + [CtVariableReadImpl]randomUser) + [CtLiteralImpl]" is not expected to own any process", [CtVariableReadImpl]status);
        [CtLocalVariableImpl][CtCommentImpl]// at least one pid will be owned by the current user (doing the testing)
        [CtTypeReferenceImpl]java.lang.String currentUser = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.name");
        [CtAssignmentImpl][CtVariableWriteImpl]status = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isAnyProcessAlive([CtVariableReadImpl]pids, [CtVariableReadImpl]currentUser);
        [CtInvocationImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Expecting user " + [CtVariableReadImpl]currentUser) + [CtLiteralImpl]" to own at least one process", [CtVariableReadImpl]status);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]ServerUtils.IS_ON_WINDOWS) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// userid test is valid only on Posix platforms
            [CtTypeReferenceImpl]int inValidUserId = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
            [CtAssignmentImpl][CtVariableWriteImpl]status = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isAnyProcessAlive([CtVariableReadImpl]pids, [CtVariableReadImpl]inValidUserId);
            [CtInvocationImpl]Assert.assertFalse([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid userId " + [CtVariableReadImpl]randomUser) + [CtLiteralImpl]" is not expected to own any process", [CtVariableReadImpl]status);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int currentUid = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.getUserId([CtLiteralImpl]null);
            [CtAssignmentImpl][CtVariableWriteImpl]status = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isAnyProcessAlive([CtVariableReadImpl]pids, [CtVariableReadImpl]currentUid);
            [CtInvocationImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Expecting uid " + [CtVariableReadImpl]currentUid) + [CtLiteralImpl]" to own at least one process", [CtVariableReadImpl]status);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testGetUserId() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]ServerUtils.IS_ON_WINDOWS) [CtBlockImpl]{
            [CtReturnImpl]return;[CtCommentImpl]// trivially succeed on Windows, since this test is not for Windows platform

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int uid1 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.getUserId([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path p = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createTempFile([CtLiteralImpl]"testGetUser", [CtLiteralImpl]".txt");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int uid2 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.getPathOwnerUid([CtInvocationImpl][CtVariableReadImpl]p.toString());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.toFile().delete()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.warn([CtLiteralImpl]"Could not delete tempoary file {}", [CtVariableReadImpl]p);
        }
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"User UID " + [CtVariableReadImpl]uid1) + [CtLiteralImpl]" is not same as file ") + [CtInvocationImpl][CtVariableReadImpl]p.toString()) + [CtLiteralImpl]" owner UID of ") + [CtVariableReadImpl]uid2, [CtVariableReadImpl]uid1, [CtVariableReadImpl]uid2);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testIsAnyProcessPosixProcessPidDirAlive() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String testName = [CtLiteralImpl]"testIsAnyProcessPosixProcessPidDirAlive";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> errors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxPidCnt = [CtLiteralImpl]5;
        [CtIfImpl]if ([CtFieldReadImpl]ServerUtils.IS_ON_WINDOWS) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"{}: test cannot be run on Windows. Marked as successful", [CtVariableReadImpl]testName);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path parentDir = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"/proc");
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parentDir.toFile().exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"{}: test cannot be run on system without process directory {}, os.name={}", [CtVariableReadImpl]testName, [CtVariableReadImpl]parentDir, [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"os.name"));
            [CtLocalVariableImpl][CtCommentImpl]// check if we can get process id on this Posix system - testing test code, useful on Mac
            [CtTypeReferenceImpl]java.lang.String cmd = [CtLiteralImpl]"/bin/sleep 10";
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getPidOfPosixProcess([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exec([CtVariableReadImpl]cmd), [CtVariableReadImpl]errors) < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl]Assert.fail([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s: Cannot obtain process id for executed command \"%s\"\n%s", [CtVariableReadImpl]testName, [CtVariableReadImpl]cmd, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.join([CtLiteralImpl]"\n\t", [CtVariableReadImpl]errors)));
            }
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Create processes and wait for their termination
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> observables = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]maxPidCnt; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cmd = [CtLiteralImpl]"sleep 20000";
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Process process = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exec([CtVariableReadImpl]cmd);
            [CtLocalVariableImpl][CtTypeReferenceImpl]long pid = [CtInvocationImpl]getPidOfPosixProcess([CtVariableReadImpl]process, [CtVariableReadImpl]errors);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"{}: ({}) ran process \"{}\" with pid={}", [CtVariableReadImpl]testName, [CtVariableReadImpl]i, [CtVariableReadImpl]cmd, [CtVariableReadImpl]pid);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pid < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String e = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s: (%d) Cannot obtain process id for executed command \"%s\"", [CtVariableReadImpl]testName, [CtVariableReadImpl]i, [CtVariableReadImpl]cmd);
                [CtInvocationImpl][CtVariableReadImpl]errors.add([CtVariableReadImpl]e);
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.error([CtVariableReadImpl]e);
                [CtContinueImpl]continue;
            }
            [CtInvocationImpl][CtVariableReadImpl]observables.add([CtVariableReadImpl]pid);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.name");
        [CtLocalVariableImpl][CtCommentImpl]// now kill processes one by one
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> pidList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]observables);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long processKillIntervalMs = [CtLiteralImpl]2000;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]pidList.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long pid = [CtInvocationImpl][CtVariableReadImpl]pidList.get([CtVariableReadImpl]i);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"{}: ({}) Sleeping for {} milliseconds before kill", [CtVariableReadImpl]testName, [CtVariableReadImpl]i, [CtVariableReadImpl]processKillIntervalMs);
            [CtIfImpl]if ([CtInvocationImpl]sleepInterrupted([CtVariableReadImpl]processKillIntervalMs)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().exec([CtBinaryOperatorImpl][CtLiteralImpl]"kill -9 " + [CtVariableReadImpl]pid);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"{}: ({}) Sleeping for {} milliseconds after kill", [CtVariableReadImpl]testName, [CtVariableReadImpl]i, [CtVariableReadImpl]processKillIntervalMs);
            [CtIfImpl]if ([CtInvocationImpl]sleepInterrupted([CtVariableReadImpl]processKillIntervalMs)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean pidDirsAvailable = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isAnyPosixProcessPidDirAlive([CtVariableReadImpl]observables, [CtVariableReadImpl]userName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]pidList.size() - [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]pidDirsAvailable) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"{}: ({}) Found existing process directories before killing last process", [CtVariableReadImpl]testName, [CtVariableReadImpl]i);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String e = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s: (%d) Found no existing process directories before killing last process", [CtVariableReadImpl]testName, [CtVariableReadImpl]i);
                    [CtInvocationImpl][CtVariableReadImpl]errors.add([CtVariableReadImpl]e);
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.error([CtVariableReadImpl]e);
                }
            } else [CtIfImpl]if ([CtVariableReadImpl]pidDirsAvailable) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String e = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s: (%d) Found existing process directories after killing last process", [CtVariableReadImpl]testName, [CtVariableReadImpl]i);
                [CtInvocationImpl][CtVariableReadImpl]errors.add([CtVariableReadImpl]e);
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.error([CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"{}: ({}) Found no existing process directories after killing last process", [CtVariableReadImpl]testName, [CtVariableReadImpl]i);
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]errors.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"There are %d failures in test:\n\t%s", [CtInvocationImpl][CtVariableReadImpl]errors.size(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.join([CtLiteralImpl]"\n\t", [CtVariableReadImpl]errors)));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Simulate the production scenario where the owner of the process directory is sometimes returned as the
     * UID instead of user. This scenario is simulated by calling
     * {@link ServerUtils#isAnyPosixProcessPidDirAlive(Collection, String, boolean)} with the last parameter
     * set to true as well as false.
     *
     * @throws Exception
     * 		on I/O exception
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testIsAnyPosixProcessPidDirAliveMockingFileOwnerUid() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File procDir = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtLiteralImpl]"/proc");
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]procDir.exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"Test testIsAnyPosixProcessPidDirAlive is designed to run on systems with /proc directory only, marking as success");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Long> allPids = [CtInvocationImpl]getRunningProcessIds([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Long> rootPids = [CtInvocationImpl]getRunningProcessIds([CtLiteralImpl]"root");
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]allPids.isEmpty());
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]rootPids.isEmpty());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String currentUser = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.name");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]boolean mockFileOwnerToUid : [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]true, [CtLiteralImpl]false)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// at least one pid will be owned by the current user (doing the testing)
            [CtTypeReferenceImpl]boolean status = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isAnyPosixProcessPidDirAlive([CtVariableReadImpl]allPids, [CtVariableReadImpl]currentUser, [CtVariableReadImpl]mockFileOwnerToUid);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String err = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"(mockFileOwnerToUid=%s) Expecting user %s to own at least one process", [CtVariableReadImpl]mockFileOwnerToUid, [CtVariableReadImpl]currentUser);
            [CtInvocationImpl]Assert.assertTrue([CtVariableReadImpl]err, [CtVariableReadImpl]status);
        }
        [CtForEachImpl][CtCommentImpl]// simulate reassignment of all process id to a different user (root)
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]boolean mockFileOwnerToUid : [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]true, [CtLiteralImpl]false)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean status = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ServerUtils.isAnyPosixProcessPidDirAlive([CtVariableReadImpl]rootPids, [CtVariableReadImpl]currentUser, [CtVariableReadImpl]mockFileOwnerToUid);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String err = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"(mockFileOwnerToUid=%s) Expecting user %s to own no process", [CtVariableReadImpl]mockFileOwnerToUid, [CtVariableReadImpl]currentUser);
            [CtInvocationImpl]Assert.assertFalse([CtVariableReadImpl]err, [CtVariableReadImpl]status);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Make the best effort to obtain the Process ID from the Process object. Thus staying entirely with the JVM.
     *
     * @param p
     * 		Process instance returned upon executing {@link Runtime#exec(String)}.
     * @param errors
     * 		Populate errors when PID is a negative number.
     * @return positive PID upon success, otherwise negative.
     */
    private synchronized [CtTypeReferenceImpl]long getPidOfPosixProcess([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process p, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> errors) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Process> pClass = [CtInvocationImpl][CtVariableReadImpl]p.getClass();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pObjStr = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.shade.org.apache.commons.lang.builder.ToStringBuilder.reflectionToString([CtVariableReadImpl]p, [CtTypeAccessImpl]ToStringStyle.SHORT_PREFIX_STYLE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pclassName = [CtInvocationImpl][CtVariableReadImpl]pClass.getName();
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]pclassName.equals([CtLiteralImpl]"java.lang.UNIXProcess")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field f = [CtInvocationImpl][CtVariableReadImpl]pClass.getDeclaredField([CtLiteralImpl]"pid");
                [CtInvocationImpl][CtVariableReadImpl]f.setAccessible([CtLiteralImpl]true);
                [CtLocalVariableImpl][CtTypeReferenceImpl]long pid = [CtInvocationImpl][CtVariableReadImpl]f.getLong([CtVariableReadImpl]p);
                [CtInvocationImpl][CtVariableReadImpl]f.setAccessible([CtLiteralImpl]false);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pid < [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]errors.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\t \"pid\" attribute in Process class " + [CtVariableReadImpl]pclassName) + [CtLiteralImpl]" returned -1, process=") + [CtVariableReadImpl]pObjStr);
                }
                [CtReturnImpl]return [CtVariableReadImpl]pid;
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field f : [CtInvocationImpl][CtVariableReadImpl]pClass.getDeclaredFields()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getName().equalsIgnoreCase([CtLiteralImpl]"pid")) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"ServerUtilsTest.getPidOfPosixProcess(): found attribute {}#{}", [CtVariableReadImpl]pclassName, [CtInvocationImpl][CtVariableReadImpl]f.getName());
                [CtInvocationImpl][CtVariableReadImpl]f.setAccessible([CtLiteralImpl]true);
                [CtLocalVariableImpl][CtTypeReferenceImpl]long pid = [CtInvocationImpl][CtVariableReadImpl]f.getLong([CtVariableReadImpl]p);
                [CtInvocationImpl][CtVariableReadImpl]f.setAccessible([CtLiteralImpl]false);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pid < [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]errors.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\t \"pid\" attribute in Process class " + [CtVariableReadImpl]pclassName) + [CtLiteralImpl]" returned -1, process=") + [CtVariableReadImpl]pObjStr);
                }
                [CtReturnImpl]return [CtVariableReadImpl]pid;
            }
            [CtTryImpl][CtCommentImpl]// post JDK 9 there should be getPid() - future JDK-11 compatibility only for the sake of Travis test in community
            try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method m = [CtInvocationImpl][CtVariableReadImpl]pClass.getDeclaredMethod([CtLiteralImpl]"getPid");
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.utils.ServerUtilsTest.LOG.info([CtLiteralImpl]"ServerUtilsTest.getPidOfPosixProcess(): found method {}#getPid()\n", [CtVariableReadImpl]pclassName);
                [CtLocalVariableImpl][CtTypeReferenceImpl]long pid = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Long) ([CtVariableReadImpl]m.invoke([CtVariableReadImpl]p)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pid < [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]errors.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\t \"getPid()\" method in Process class " + [CtVariableReadImpl]pclassName) + [CtLiteralImpl]" returned -1, process=") + [CtVariableReadImpl]pObjStr);
                }
                [CtReturnImpl]return [CtVariableReadImpl]pid;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.SecurityException e) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]errors.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\t getPid() method in Process class " + [CtVariableReadImpl]pclassName) + [CtLiteralImpl]" cannot be called: ") + [CtInvocationImpl][CtVariableReadImpl]e.getMessage()) + [CtLiteralImpl]", process=") + [CtVariableReadImpl]pObjStr);
                [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchMethodException e) [CtBlockImpl]{
                [CtCommentImpl]// ignore and try something else
            }
            [CtInvocationImpl][CtVariableReadImpl]errors.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\t Process class " + [CtVariableReadImpl]pclassName) + [CtLiteralImpl]" missing field \"pid\" and missing method \"getPid()\", process=") + [CtVariableReadImpl]pObjStr);
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]errors.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\t Exception in Process class " + [CtVariableReadImpl]pclassName) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]e.getMessage()) + [CtLiteralImpl]", process=") + [CtVariableReadImpl]pObjStr);
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sleep for specified milliseconds and return true if sleep was interrupted.
     *
     * @param milliSeconds
     * 		number of milliseconds to sleep
     * @return true if sleep was interrupted, false otherwise.
     */
    private [CtTypeReferenceImpl]boolean sleepInterrupted([CtParameterImpl][CtTypeReferenceImpl]long milliSeconds) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtVariableReadImpl]milliSeconds);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ex) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ex.printStackTrace();
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().interrupt();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }
}
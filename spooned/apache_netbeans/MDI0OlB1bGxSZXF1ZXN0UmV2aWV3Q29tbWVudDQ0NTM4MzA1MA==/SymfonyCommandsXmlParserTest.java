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
[CtPackageDeclarationImpl]package org.netbeans.modules.php.symfony2.commands;
[CtUnresolvedImport]import org.netbeans.junit.NbTestCase;
[CtImportImpl]import java.io.FileReader;
[CtImportImpl]import java.io.InputStreamReader;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.io.BufferedReader;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.io.Reader;
[CtImportImpl]import java.io.FileInputStream;
[CtClassImpl]public class SymfonyCommandsXmlParserTest extends [CtTypeReferenceImpl]org.netbeans.junit.NbTestCase {
    [CtConstructorImpl]public SymfonyCommandsXmlParserTest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]name);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void testParseCommands() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Reader reader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl]getDataDir(), [CtLiteralImpl]"symfony2-commands.xml")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO> commands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtTypeAccessImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandsXmlParser.parse([CtVariableReadImpl]reader, [CtVariableReadImpl]commands);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]commands.isEmpty());
        [CtInvocationImpl]assertSame([CtLiteralImpl]30, [CtInvocationImpl][CtVariableReadImpl]commands.size());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]0);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"help", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Displays help for a command", [CtInvocationImpl][CtVariableReadImpl]command.getDescription());
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<html>Usage:<br>" + [CtLiteralImpl]"<i>help [--xml] [command_name]</i><br>") + [CtLiteralImpl]"<br>") + [CtLiteralImpl]"The <i>help</i> command displays help for a given command:<br>") + [CtLiteralImpl]" <br>") + [CtLiteralImpl]"   <i>php app/console help list</i><br>") + [CtLiteralImpl]" <br>") + [CtLiteralImpl]" You can also output the help as XML by using the <i>--xml</i> option:<br>") + [CtLiteralImpl]" <br>") + [CtLiteralImpl]"   <i>php app/console help --xml list</i>", [CtInvocationImpl][CtVariableReadImpl]command.getHelp());
        [CtAssignmentImpl][CtVariableWriteImpl]command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]2);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"assetic:dump", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Dumps all assets to the filesystem", [CtInvocationImpl][CtVariableReadImpl]command.getDescription());
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<html>Usage:<br>" + [CtLiteralImpl]"<i>assetic:dump [--watch] [--force] [--period=\"...\"] [write_to]</i><br>") + [CtLiteralImpl]"<br>", [CtInvocationImpl][CtVariableReadImpl]command.getHelp());
        [CtAssignmentImpl][CtVariableWriteImpl]command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]5);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"cache:warmup", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Warms up an empty cache", [CtInvocationImpl][CtVariableReadImpl]command.getDescription());
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<html>Usage:<br>" + [CtLiteralImpl]"<i>cache:warmup</i><br>") + [CtLiteralImpl]"<br>") + [CtLiteralImpl]"The <i>cache:warmup</i> command warms up the cache.<br>") + [CtLiteralImpl]" <br>") + [CtLiteralImpl]" Before running this command, the cache must be empty.", [CtInvocationImpl][CtVariableReadImpl]command.getHelp());
        [CtAssignmentImpl][CtVariableWriteImpl]command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]29);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"swiftmailer:spool:send", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void testIssue223639() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Reader reader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl]getDataDir(), [CtLiteralImpl]"issue223639.xml")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO> commands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtTypeAccessImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandsXmlParser.parse([CtVariableReadImpl]reader, [CtVariableReadImpl]commands);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]commands.isEmpty());
        [CtInvocationImpl]assertSame([CtLiteralImpl]36, [CtInvocationImpl][CtVariableReadImpl]commands.size());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]0);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"help", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Displays help for a command", [CtInvocationImpl][CtVariableReadImpl]command.getDescription());
        [CtAssignmentImpl][CtVariableWriteImpl]command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]35);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"twig:lint", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void testIssue232490() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Reader reader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl]getDataDir(), [CtLiteralImpl]"issue232490.xml")), [CtLiteralImpl]"UTF-8"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO> commands = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtTypeAccessImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandsXmlParser.parse([CtVariableReadImpl]reader, [CtVariableReadImpl]commands);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]commands.isEmpty());
        [CtInvocationImpl]assertSame([CtLiteralImpl]30, [CtInvocationImpl][CtVariableReadImpl]commands.size());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]0);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"help", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Displays help for a command", [CtInvocationImpl][CtVariableReadImpl]command.getDescription());
        [CtAssignmentImpl][CtVariableWriteImpl]command = [CtInvocationImpl]findCommand([CtVariableReadImpl]commands, [CtLiteralImpl]"assetic:dump");
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]command);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"assetic:dump", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Zapíše všechny assety do souborů.", [CtInvocationImpl][CtVariableReadImpl]command.getDescription());
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<html>Usage:<br>" + [CtLiteralImpl]"<i>assetic:dump [--watch] [--force] [--period=\"...\"] [write_to]</i><br><br>") + [CtLiteralImpl]"Příliš žluťoučký kůň úpěl ďábelské ódy.", [CtInvocationImpl][CtVariableReadImpl]command.getHelp());
        [CtAssignmentImpl][CtVariableWriteImpl]command = [CtInvocationImpl][CtVariableReadImpl]commands.get([CtLiteralImpl]21);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"doctrine:query:sql", [CtInvocationImpl][CtVariableReadImpl]command.getCommand());
    }

    [CtMethodImpl][CtCommentImpl]// public void testIssue252901() throws Exception {
    [CtCommentImpl]// Reader reader = new BufferedReader(new FileReader(new File(getDataDir(), "issue252901.xml")));
    [CtCommentImpl]// 
    [CtCommentImpl]// List<SymfonyCommandVO> commands = new ArrayList<>();
    [CtCommentImpl]// SymfonyCommandsXmlParser.parse(reader, commands);
    [CtCommentImpl]// 
    [CtCommentImpl]// assertFalse(commands.isEmpty());
    [CtCommentImpl]// assertSame(50, commands.size());
    [CtCommentImpl]// 
    [CtCommentImpl]// SymfonyCommandVO command = commands.get(0);
    [CtCommentImpl]// assertEquals("help", command.getCommand());
    [CtCommentImpl]// assertEquals("Displays help for a command", command.getDescription());
    [CtCommentImpl]// 
    [CtCommentImpl]// command = commands.get(8);
    [CtCommentImpl]// assertEquals("debug:config", command.getCommand());
    [CtCommentImpl]// assertEquals("<html>Usage:<br>"
    [CtCommentImpl]// + "<i>debug:config [&lt;name&gt;]</i><br>"
    [CtCommentImpl]// + "<i>config:debug</i><br><br>"
    [CtCommentImpl]// + "The <i>debug:config</i> command dumps the current configuration for an<br> extension/bundle.<br><br>"
    [CtCommentImpl]// + " Either the extension alias or bundle name can be used:<br><br>"
    [CtCommentImpl]// + "   <i>php /home/gapon/NetBeansProjects/symfony2/app/console debug:config framework</i><br>"
    [CtCommentImpl]// + "   <i>php /home/gapon/NetBeansProjects/symfony2/app/console debug:config FrameworkBundle</i>", command.getHelp());
    [CtCommentImpl]// 
    [CtCommentImpl]// command = commands.get(49);
    [CtCommentImpl]// assertEquals("translation:update", command.getCommand());
    [CtCommentImpl]// }
    private [CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO findCommand([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO> commands, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String commandName) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.netbeans.modules.php.symfony2.commands.SymfonyCommandVO command : [CtVariableReadImpl]commands) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]command.getCommand().equals([CtVariableReadImpl]commandName)) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]command;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }
}
[CompilationUnitImpl][CtJavaDocImpl]/**
 * ****************************************************************************
 *  Copyright 2015 by OLTPBenchmark Project                                   *
 *                                                                            *
 *  Licensed under the Apache License, Version 2.0 (the "License");           *
 *  you may not use this file except in compliance with the License.          *
 *  You may obtain a copy of the License at                                   *
 *                                                                            *
 *    http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                            *
 *  Unless required by applicable law or agreed to in writing, software       *
 *  distributed under the License is distributed on an "AS IS" BASIS,         *
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 *  See the License for the specific language governing permissions and       *
 *  limitations under the License.                                            *
 * ****************************************************************************
 */
[CtPackageDeclarationImpl]package com.oltpbenchmark;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import com.oltpbenchmark.util.StringBoxUtil;
[CtUnresolvedImport]import com.oltpbenchmark.api.TransactionType;
[CtUnresolvedImport]import com.oltpbenchmark.util.TimeUtil;
[CtUnresolvedImport]import org.apache.commons.cli.Options;
[CtUnresolvedImport]import com.oltpbenchmark.types.DatabaseType;
[CtUnresolvedImport]import org.apache.commons.cli.HelpFormatter;
[CtUnresolvedImport]import com.oltpbenchmark.util.FileUtil;
[CtUnresolvedImport]import com.oltpbenchmark.util.StringUtil;
[CtUnresolvedImport]import org.apache.log4j.Logger;
[CtUnresolvedImport]import com.oltpbenchmark.api.BenchmarkModule;
[CtUnresolvedImport]import com.oltpbenchmark.util.QueueLimitException;
[CtUnresolvedImport]import org.apache.commons.cli.CommandLine;
[CtUnresolvedImport]import org.apache.commons.cli.PosixParser;
[CtUnresolvedImport]import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
[CtImportImpl]import java.io.PrintStream;
[CtUnresolvedImport]import com.oltpbenchmark.api.Worker;
[CtUnresolvedImport]import org.apache.commons.cli.CommandLineParser;
[CtUnresolvedImport]import org.apache.commons.collections15.map.ListOrderedMap;
[CtUnresolvedImport]import com.oltpbenchmark.api.TransactionTypes;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.oltpbenchmark.util.ResultUploader;
[CtUnresolvedImport]import com.oltpbenchmark.util.ClassUtil;
[CtUnresolvedImport]import org.apache.commons.configuration.XMLConfiguration;
[CtUnresolvedImport]import org.apache.commons.cli.ParseException;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.apache.commons.configuration.ConfigurationException;
[CtUnresolvedImport]import org.apache.commons.configuration.SubnodeConfiguration;
[CtClassImpl]public class DBWorkload {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.log4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.apache.log4j.Logger.getLogger([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SINGLE_LINE = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringUtil.repeat([CtLiteralImpl]"=", [CtLiteralImpl]70);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String RATE_DISABLED = [CtLiteralImpl]"disabled";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String RATE_UNLIMITED = [CtLiteralImpl]"unlimited";

    [CtFieldImpl]private static [CtTypeReferenceImpl]int newOrderTxnId = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private static [CtTypeReferenceImpl]int numWarehouses = [CtLiteralImpl]10;

    [CtFieldImpl]private static [CtTypeReferenceImpl]int startWarehouseId = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private static [CtTypeReferenceImpl]int totalWarehousesAcrossExecutions = [CtLiteralImpl]10;

    [CtFieldImpl]private static [CtTypeReferenceImpl]int time = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param args
     * @throws Exception
     */
    public static [CtTypeReferenceImpl]void main([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] args) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Initialize log4j
        [CtTypeReferenceImpl]java.lang.String log4jPath = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"log4j.configuration");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]log4jPath != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.log4j.PropertyConfigurator.configure([CtVariableReadImpl]log4jPath);
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Missing log4j.properties file");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.ClassUtil.isAssertsEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.warn([CtBinaryOperatorImpl][CtLiteralImpl]"\n" + [CtInvocationImpl]com.oltpbenchmark.DBWorkload.getAssertWarning());
        }
        [CtLocalVariableImpl][CtCommentImpl]// create the command line parser
        [CtTypeReferenceImpl]org.apache.commons.cli.CommandLineParser parser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.cli.PosixParser();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.configuration.XMLConfiguration pluginConfig = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]pluginConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.configuration.XMLConfiguration([CtLiteralImpl]"config/plugin.xml");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.commons.configuration.ConfigurationException e1) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtLiteralImpl]"Plugin configuration file config/plugin.xml is missing");
            [CtInvocationImpl][CtVariableReadImpl]e1.printStackTrace();
        }
        [CtInvocationImpl][CtVariableReadImpl]pluginConfig.setExpressionEngine([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.configuration.tree.xpath.XPathExpressionEngine());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.cli.Options options = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.cli.Options();
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"c", [CtLiteralImpl]"config", [CtLiteralImpl]true, [CtLiteralImpl]"Workload configuration file [default: config/workload_all.xml]");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"create", [CtLiteralImpl]true, [CtLiteralImpl]"Initialize the database for this benchmark");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"clear", [CtLiteralImpl]true, [CtLiteralImpl]"Clear all records in the database for this benchmark");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"load", [CtLiteralImpl]true, [CtLiteralImpl]"Load data using the benchmark's data loader");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"execute", [CtLiteralImpl]true, [CtLiteralImpl]"Execute the benchmark workload");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"runscript", [CtLiteralImpl]true, [CtLiteralImpl]"Run an SQL script");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"upload", [CtLiteralImpl]true, [CtLiteralImpl]"Upload the result");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"uploadHash", [CtLiteralImpl]true, [CtLiteralImpl]"git hash to be associated with the upload");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"v", [CtLiteralImpl]"verbose", [CtLiteralImpl]false, [CtLiteralImpl]"Display Messages");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"h", [CtLiteralImpl]"help", [CtLiteralImpl]false, [CtLiteralImpl]"Print this help");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"s", [CtLiteralImpl]"sample", [CtLiteralImpl]true, [CtLiteralImpl]"Sampling window");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"im", [CtLiteralImpl]"interval-monitor", [CtLiteralImpl]true, [CtLiteralImpl]"Throughput Monitoring Interval in milliseconds");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"ss", [CtLiteralImpl]false, [CtLiteralImpl]"Verbose Sampling per Transaction");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"o", [CtLiteralImpl]"output", [CtLiteralImpl]true, [CtLiteralImpl]"Output file (default System.out)");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"d", [CtLiteralImpl]"directory", [CtLiteralImpl]true, [CtLiteralImpl]"Base directory for the result files, default is current directory");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"t", [CtLiteralImpl]"timestamp", [CtLiteralImpl]false, [CtLiteralImpl]"Each result file is prepended with a timestamp for the beginning of the experiment");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]"ts", [CtLiteralImpl]"tracescript", [CtLiteralImpl]true, [CtLiteralImpl]"Script of transactions to execute");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"histograms", [CtLiteralImpl]false, [CtLiteralImpl]"Print txn histograms");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"dialects-export", [CtLiteralImpl]true, [CtLiteralImpl]"Export benchmark SQL to a dialects file");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"output-raw", [CtLiteralImpl]true, [CtLiteralImpl]"Output raw data");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"output-samples", [CtLiteralImpl]true, [CtLiteralImpl]"Output sample data");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"nodes", [CtLiteralImpl]true, [CtLiteralImpl]"comma separated list of nodes (default 127.0.0.1)");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"warehouses", [CtLiteralImpl]true, [CtLiteralImpl]"Number of warehouses (default 10)");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"startwarehouse", [CtLiteralImpl]true, [CtLiteralImpl]"Start warehouse id");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"totalwarehouses", [CtLiteralImpl]true, [CtLiteralImpl]"Total number of warehouses across all executions");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"loaderthreads", [CtLiteralImpl]true, [CtLiteralImpl]"Number of loader threads (default 10)");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtLiteralImpl]null, [CtLiteralImpl]"enableforeignkeys", [CtLiteralImpl]true, [CtLiteralImpl]"Whether to enable foregin keys");
        [CtLocalVariableImpl][CtCommentImpl]// parse the command line arguments
        [CtTypeReferenceImpl]org.apache.commons.cli.CommandLine argsLine = [CtInvocationImpl][CtVariableReadImpl]parser.parse([CtVariableReadImpl]options, [CtVariableReadImpl]args);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"h")) [CtBlockImpl]{
            [CtInvocationImpl]com.oltpbenchmark.DBWorkload.printUsage([CtVariableReadImpl]options);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Seconds
        [CtTypeReferenceImpl]int intervalMonitor = [CtLiteralImpl]0;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"im")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]intervalMonitor = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"im"));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String val = [CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"nodes");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> nodes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]nodes.add([CtLiteralImpl]"127.0.0.1");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"nodes")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]nodes = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]val.split([CtLiteralImpl]","));
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"warehouses")) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]com.oltpbenchmark.DBWorkload.numWarehouses = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"warehouses"));
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"startwarehouse")) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]com.oltpbenchmark.DBWorkload.startWarehouseId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"startwarehouse"));
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]com.oltpbenchmark.DBWorkload.startWarehouseId = [CtLiteralImpl]1;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"totalwarehouses")) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]com.oltpbenchmark.DBWorkload.totalWarehousesAcrossExecutions = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"totalwarehouses"));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int loaderThreads = [CtInvocationImpl]java.lang.Integer.min([CtLiteralImpl]10, [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.numWarehouses);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"loaderthreads")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]loaderThreads = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"loaderthreads"));
        }
        [CtLocalVariableImpl][CtCommentImpl]// -------------------------------------------------------------------
        [CtCommentImpl]// GET PLUGIN LIST
        [CtCommentImpl]// -------------------------------------------------------------------
        [CtTypeReferenceImpl]java.lang.String targetBenchmarks = [CtLiteralImpl]"tpcc";
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] targetList = [CtInvocationImpl][CtVariableReadImpl]targetBenchmarks.split([CtLiteralImpl]",");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule> benchList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule>();
        [CtLocalVariableImpl][CtCommentImpl]// Use this list for filtering of the output
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionType> activeTXTypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionType>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configFile = [CtLiteralImpl]"config/workload_all.xml";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"c")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]configFile = [CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"c");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.configuration.XMLConfiguration xmlConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.configuration.XMLConfiguration([CtVariableReadImpl]configFile);
        [CtInvocationImpl][CtVariableReadImpl]xmlConfig.setExpressionEngine([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.configuration.tree.xpath.XPathExpressionEngine());
        [CtLocalVariableImpl][CtCommentImpl]// Load the configuration for each benchmark
        [CtTypeReferenceImpl]int lastTxnId = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String plugin : [CtVariableReadImpl]targetList) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pluginTest = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"[@bench='" + [CtVariableReadImpl]plugin) + [CtLiteralImpl]"']";
            [CtLocalVariableImpl][CtCommentImpl]// ----------------------------------------------------------------
            [CtCommentImpl]// BEGIN LOADING WORKLOAD CONFIGURATION
            [CtCommentImpl]// ----------------------------------------------------------------
            [CtTypeReferenceImpl]WorkloadConfiguration wrkld = [CtConstructorCallImpl]new [CtTypeReferenceImpl]WorkloadConfiguration();
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setBenchmarkName([CtVariableReadImpl]plugin);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setXmlConfig([CtVariableReadImpl]xmlConfig);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean scriptRun = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"t")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]scriptRun = [CtLiteralImpl]true;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String traceFile = [CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"t");
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setTraceReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]TraceReader([CtVariableReadImpl]traceFile));
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]wrkld.getTraceReader().toString());

            }
            [CtInvocationImpl][CtCommentImpl]// Pull in database configuration
            [CtVariableReadImpl]wrkld.setDBType([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.types.DatabaseType.get([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtLiteralImpl]"dbtype")));
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setDBDriver([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtLiteralImpl]"driver"));
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setNodes([CtVariableReadImpl]nodes);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setDBName([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtLiteralImpl]"DBName"));
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setDBUsername([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtLiteralImpl]"username"));
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setDBPassword([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtLiteralImpl]"password"));
            [CtLocalVariableImpl][CtTypeReferenceImpl]int terminals = [CtInvocationImpl][CtVariableReadImpl]xmlConfig.getInt([CtLiteralImpl]"terminals[not(@bench)]", [CtBinaryOperatorImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.numWarehouses * [CtLiteralImpl]10);
            [CtAssignmentImpl][CtVariableWriteImpl]terminals = [CtInvocationImpl][CtVariableReadImpl]xmlConfig.getInt([CtBinaryOperatorImpl][CtLiteralImpl]"terminals" + [CtVariableReadImpl]pluginTest, [CtVariableReadImpl]terminals);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setTerminals([CtVariableReadImpl]terminals);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setLoaderThreads([CtVariableReadImpl]loaderThreads);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String isolationMode = [CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtLiteralImpl]"isolation[not(@bench)]", [CtLiteralImpl]"TRANSACTION_SERIALIZABLE");
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setIsolationMode([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtBinaryOperatorImpl][CtLiteralImpl]"isolation" + [CtVariableReadImpl]pluginTest, [CtVariableReadImpl]isolationMode));
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setScaleFactor([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.numWarehouses);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setStartWarehouseId([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.startWarehouseId);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setTotalWarehousesAcrossExecutions([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.totalWarehousesAcrossExecutions);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setRecordAbortMessages([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getBoolean([CtLiteralImpl]"recordabortmessages", [CtLiteralImpl]false));
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setDataDir([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtLiteralImpl]"datadir", [CtLiteralImpl]"."));
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"useKeyingTime")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setUseKeyingTime([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getBoolean([CtLiteralImpl]"useKeyingTime"));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"useThinkTime")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setUseKeyingTime([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getBoolean([CtLiteralImpl]"useThinkTime"));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"enableForeignKeysAfterLoad")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setEnableForeignKeysAfterLoad([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getBoolean([CtLiteralImpl]"enableForeignKeysAfterLoad"));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"startwarehouse")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setShouldEnableForeignKeys([CtLiteralImpl]false);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"batchSize")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setBatchSize([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getInt([CtLiteralImpl]"batchSize"));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"port")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setPort([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getInt([CtLiteralImpl]"port"));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"numDBConnections")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setNumDBConnections([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getInt([CtLiteralImpl]"numDBConnections"));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// We use a max of 200 connections per node so as to not overwhelm the DB cluster.
                [CtVariableReadImpl]wrkld.setNumDBConnections([CtInvocationImpl]java.lang.Integer.min([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.numWarehouses, [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]wrkld.getNodes().size() * [CtLiteralImpl]200));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"hikariConnectionTimeoutMs")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setHikariConnectionTimeout([CtInvocationImpl][CtVariableReadImpl]xmlConfig.getInt([CtLiteralImpl]"hikariConnectionTimeoutMs"));
            }
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Configuration -> nodes: " + [CtInvocationImpl][CtVariableReadImpl]wrkld.getNodes()) + [CtLiteralImpl]", port: ") + [CtInvocationImpl][CtVariableReadImpl]wrkld.getPort()) + [CtLiteralImpl]", warehouses: ") + [CtInvocationImpl][CtVariableReadImpl]wrkld.getScaleFactor()) + [CtLiteralImpl]", startWH: ") + [CtInvocationImpl][CtVariableReadImpl]wrkld.getStartWarehouseId()) + [CtLiteralImpl]", terminals: ") + [CtInvocationImpl][CtVariableReadImpl]wrkld.getTerminals()) + [CtLiteralImpl]", dbConnections: ") + [CtInvocationImpl][CtVariableReadImpl]wrkld.getNumDBConnections()) + [CtLiteralImpl]", loaderThreads: ") + [CtInvocationImpl][CtVariableReadImpl]wrkld.getLoaderThreads());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]wrkld.getNumDBConnections() <= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setNumDBConnections([CtInvocationImpl][CtVariableReadImpl]wrkld.getTerminals());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]wrkld.getNumDBConnections() < [CtInvocationImpl][CtVariableReadImpl]wrkld.getLoaderThreads()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setNumDBConnections([CtInvocationImpl][CtVariableReadImpl]wrkld.getLoaderThreads());
            }
            [CtIfImpl]if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"execute")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]wrkld.setNeedsExecution([CtLiteralImpl]true);
            }
            [CtLocalVariableImpl][CtCommentImpl]// ----------------------------------------------------------------
            [CtCommentImpl]// CREATE BENCHMARK MODULE
            [CtCommentImpl]// ----------------------------------------------------------------
            [CtTypeReferenceImpl]java.lang.String classname = [CtInvocationImpl][CtVariableReadImpl]pluginConfig.getString([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"/plugin[@name='" + [CtVariableReadImpl]plugin) + [CtLiteralImpl]"']");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]classname == [CtLiteralImpl]null)[CtBlockImpl]
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.cli.ParseException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Plugin " + [CtVariableReadImpl]plugin) + [CtLiteralImpl]" is undefined in config/plugin.xml");

            [CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule bench = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.ClassUtil.newInstance([CtVariableReadImpl]classname, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtVariableReadImpl]wrkld }, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[]{ [CtFieldReadImpl]com.oltpbenchmark.WorkloadConfiguration.class });
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> initDebug = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.collections15.map.ListOrderedMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
            [CtInvocationImpl][CtVariableReadImpl]initDebug.put([CtLiteralImpl]"Benchmark", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s {%s}", [CtInvocationImpl][CtVariableReadImpl]plugin.toUpperCase(), [CtVariableReadImpl]classname));
            [CtInvocationImpl][CtVariableReadImpl]initDebug.put([CtLiteralImpl]"Configuration", [CtVariableReadImpl]configFile);
            [CtInvocationImpl][CtVariableReadImpl]initDebug.put([CtLiteralImpl]"Type", [CtInvocationImpl][CtVariableReadImpl]wrkld.getDBType());
            [CtInvocationImpl][CtVariableReadImpl]initDebug.put([CtLiteralImpl]"Driver", [CtInvocationImpl][CtVariableReadImpl]wrkld.getDBDriver());
            [CtInvocationImpl][CtVariableReadImpl]initDebug.put([CtLiteralImpl]"URL", [CtInvocationImpl][CtVariableReadImpl]wrkld.getNodes());
            [CtInvocationImpl][CtVariableReadImpl]initDebug.put([CtLiteralImpl]"Isolation", [CtInvocationImpl][CtVariableReadImpl]wrkld.getIsolationString());
            [CtInvocationImpl][CtVariableReadImpl]initDebug.put([CtLiteralImpl]"Scale Factor", [CtInvocationImpl][CtVariableReadImpl]wrkld.getScaleFactor());
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE + [CtLiteralImpl]"\n\n") + [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringUtil.formatMaps([CtVariableReadImpl]initDebug));
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
            [CtLocalVariableImpl][CtCommentImpl]// ----------------------------------------------------------------
            [CtCommentImpl]// LOAD TRANSACTION DESCRIPTIONS
            [CtCommentImpl]// ----------------------------------------------------------------
            [CtTypeReferenceImpl]int numTxnTypes = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xmlConfig.configurationsAt([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"transactiontypes" + [CtVariableReadImpl]pluginTest) + [CtLiteralImpl]"/transactiontype").size();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]numTxnTypes == [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]targetList.length == [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// if it is a single workload run, <transactiontypes /> w/o attribute is used
                [CtVariableWriteImpl]pluginTest = [CtLiteralImpl]"[not(@bench)]";
                [CtAssignmentImpl][CtVariableWriteImpl]numTxnTypes = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xmlConfig.configurationsAt([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"transactiontypes" + [CtVariableReadImpl]pluginTest) + [CtLiteralImpl]"/transactiontype").size();
            }
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setNumTxnTypes([CtVariableReadImpl]numTxnTypes);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionType> ttypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionType>();
            [CtInvocationImpl][CtVariableReadImpl]ttypes.add([CtTypeAccessImpl]TransactionType.INVALID);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int txnIdOffset = [CtVariableReadImpl]lastTxnId;
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i <= [CtInvocationImpl][CtVariableReadImpl]wrkld.getNumTxnTypes(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"transactiontypes" + [CtVariableReadImpl]pluginTest) + [CtLiteralImpl]"/transactiontype[") + [CtVariableReadImpl]i) + [CtLiteralImpl]"]";
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String txnName = [CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtBinaryOperatorImpl][CtVariableReadImpl]key + [CtLiteralImpl]"/name");
                [CtLocalVariableImpl][CtCommentImpl]// Get ID if specified; else increment from last one.
                [CtTypeReferenceImpl]int txnId = [CtVariableReadImpl]i;
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtBinaryOperatorImpl][CtVariableReadImpl]key + [CtLiteralImpl]"/id")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]txnId = [CtInvocationImpl][CtVariableReadImpl]xmlConfig.getInt([CtBinaryOperatorImpl][CtVariableReadImpl]key + [CtLiteralImpl]"/id");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionType tmpType = [CtInvocationImpl][CtVariableReadImpl]bench.initTransactionType([CtVariableReadImpl]txnName, [CtBinaryOperatorImpl][CtVariableReadImpl]txnId + [CtVariableReadImpl]txnIdOffset);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]txnName.equals([CtLiteralImpl]"NewOrder")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]com.oltpbenchmark.DBWorkload.newOrderTxnId = [CtBinaryOperatorImpl][CtVariableReadImpl]txnId + [CtVariableReadImpl]txnIdOffset;
                }
                [CtInvocationImpl][CtCommentImpl]// Keep a reference for filtering
                [CtVariableReadImpl]activeTXTypes.add([CtVariableReadImpl]tmpType);
                [CtInvocationImpl][CtCommentImpl]// Add a ref for the active TTypes in this benchmark
                [CtVariableReadImpl]ttypes.add([CtVariableReadImpl]tmpType);
                [CtAssignmentImpl][CtVariableWriteImpl]lastTxnId = [CtVariableReadImpl]i;
            }[CtCommentImpl]// FOR

            [CtLocalVariableImpl][CtCommentImpl]// Wrap the list of transactions and save them
            [CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionTypes tt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionTypes([CtVariableReadImpl]ttypes);
            [CtInvocationImpl][CtVariableReadImpl]wrkld.setTransTypes([CtVariableReadImpl]tt);
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Using the following transaction types: " + [CtVariableReadImpl]tt);
            [CtLocalVariableImpl][CtCommentImpl]// Read in the groupings of transactions (if any) defined for this
            [CtCommentImpl]// benchmark
            [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> groupings = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numGroupings = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xmlConfig.configurationsAt([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"transactiontypes" + [CtVariableReadImpl]pluginTest) + [CtLiteralImpl]"/groupings/grouping").size();
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Num groupings: " + [CtVariableReadImpl]numGroupings);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtVariableReadImpl]numGroupings + [CtLiteralImpl]1); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"transactiontypes" + [CtVariableReadImpl]pluginTest) + [CtLiteralImpl]"/groupings/grouping[") + [CtVariableReadImpl]i) + [CtLiteralImpl]"]";
                [CtLocalVariableImpl][CtCommentImpl]// Get the name for the grouping and make sure it's valid.
                [CtTypeReferenceImpl]java.lang.String groupingName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xmlConfig.getString([CtBinaryOperatorImpl][CtVariableReadImpl]key + [CtLiteralImpl]"/name").toLowerCase();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]groupingName.matches([CtLiteralImpl]"^[a-z]\\w*$")) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Grouping name \"%s\" is invalid." + [CtLiteralImpl]" Must begin with a letter and contain only") + [CtLiteralImpl]" alphanumeric characters.", [CtVariableReadImpl]groupingName));
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]groupingName.equals([CtLiteralImpl]"all")) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtBinaryOperatorImpl][CtLiteralImpl]"Grouping name \"all\" is reserved." + [CtLiteralImpl]" Please pick a different name.");
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                }
                [CtLocalVariableImpl][CtCommentImpl]// Get the weights for this grouping and make sure that there
                [CtCommentImpl]// is an appropriate number of them.
                [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> groupingWeights = [CtInvocationImpl][CtVariableReadImpl]xmlConfig.getList([CtBinaryOperatorImpl][CtVariableReadImpl]key + [CtLiteralImpl]"/weights");
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]groupingWeights.size() != [CtVariableReadImpl]numTxnTypes) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Grouping \"%s\" has %d weights," + [CtLiteralImpl]" but there are %d transactions in this") + [CtLiteralImpl]" benchmark.", [CtVariableReadImpl]groupingName, [CtInvocationImpl][CtVariableReadImpl]groupingWeights.size(), [CtVariableReadImpl]numTxnTypes));
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                }
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Creating grouping with name, weights: " + [CtVariableReadImpl]groupingName) + [CtLiteralImpl]", ") + [CtVariableReadImpl]groupingWeights);
                [CtInvocationImpl][CtVariableReadImpl]groupings.put([CtVariableReadImpl]groupingName, [CtVariableReadImpl]groupingWeights);
            }
            [CtLocalVariableImpl][CtCommentImpl]// All benchmarks should also have an "all" grouping that gives
            [CtCommentImpl]// even weight to all transactions in the benchmark.
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> weightAll = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]numTxnTypes; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]weightAll.add([CtLiteralImpl]"1");

            [CtInvocationImpl][CtVariableReadImpl]groupings.put([CtLiteralImpl]"all", [CtVariableReadImpl]weightAll);
            [CtInvocationImpl][CtVariableReadImpl]benchList.add([CtVariableReadImpl]bench);
            [CtLocalVariableImpl][CtCommentImpl]// ----------------------------------------------------------------
            [CtCommentImpl]// WORKLOAD CONFIGURATION
            [CtCommentImpl]// ----------------------------------------------------------------
            [CtTypeReferenceImpl]int size = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xmlConfig.configurationsAt([CtLiteralImpl]"/works/work").size();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtVariableReadImpl]size + [CtLiteralImpl]1); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.configuration.SubnodeConfiguration work = [CtInvocationImpl][CtVariableReadImpl]xmlConfig.configurationAt([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"works/work[" + [CtVariableReadImpl]i) + [CtLiteralImpl]"]");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> weight_strings;
                [CtIfImpl][CtCommentImpl]// use a workaround if there multiple workloads or single
                [CtCommentImpl]// attributed workload
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]targetList.length > [CtLiteralImpl]1) || [CtInvocationImpl][CtVariableReadImpl]work.containsKey([CtLiteralImpl]"weights[@bench]")) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String weightKey = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]work.getString([CtBinaryOperatorImpl][CtLiteralImpl]"weights" + [CtVariableReadImpl]pluginTest).toLowerCase();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]groupings.containsKey([CtVariableReadImpl]weightKey))[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]weight_strings = [CtInvocationImpl][CtVariableReadImpl]groupings.get([CtVariableReadImpl]weightKey);
                    else[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]weight_strings = [CtInvocationImpl]com.oltpbenchmark.DBWorkload.getWeights([CtVariableReadImpl]plugin, [CtVariableReadImpl]work);

                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String weightKey = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]work.getString([CtLiteralImpl]"weights[not(@bench)]").toLowerCase();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]groupings.containsKey([CtVariableReadImpl]weightKey))[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]weight_strings = [CtInvocationImpl][CtVariableReadImpl]groupings.get([CtVariableReadImpl]weightKey);
                    else[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]weight_strings = [CtInvocationImpl][CtVariableReadImpl]work.getList([CtLiteralImpl]"weights[not(@bench)]");

                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]int rate = [CtLiteralImpl]1;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean rateLimited = [CtLiteralImpl]true;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean disabled = [CtLiteralImpl]false;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean serial = [CtLiteralImpl]false;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean timed = [CtLiteralImpl]false;
                [CtLocalVariableImpl][CtCommentImpl]// can be "disabled", "unlimited" or a number
                [CtTypeReferenceImpl]java.lang.String rate_string;
                [CtAssignmentImpl][CtVariableWriteImpl]rate_string = [CtInvocationImpl][CtVariableReadImpl]work.getString([CtLiteralImpl]"rate[not(@bench)]", [CtLiteralImpl]"");
                [CtAssignmentImpl][CtVariableWriteImpl]rate_string = [CtInvocationImpl][CtVariableReadImpl]work.getString([CtBinaryOperatorImpl][CtLiteralImpl]"rate" + [CtVariableReadImpl]pluginTest, [CtVariableReadImpl]rate_string);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rate_string.equals([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.RATE_DISABLED)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]disabled = [CtLiteralImpl]true;
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rate_string.equals([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.RATE_UNLIMITED)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]rateLimited = [CtLiteralImpl]false;
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rate_string.isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Please specify the rate for phase %d and workload %s", [CtVariableReadImpl]i, [CtVariableReadImpl]plugin));
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                } else [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]rate = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtVariableReadImpl]rate_string);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rate < [CtLiteralImpl]1) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtLiteralImpl]"Rate limit must be at least 1. Use unlimited or disabled values instead.");
                            [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                        }
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Rate string must be '%s', '%s' or a number", [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.RATE_DISABLED, [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.RATE_UNLIMITED));
                        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                    }
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Phase.Arrival arrival = [CtFieldReadImpl]Phase.Arrival.REGULAR;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String arrive = [CtInvocationImpl][CtVariableReadImpl]work.getString([CtLiteralImpl]"@arrival", [CtLiteralImpl]"regular");
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]arrive.toUpperCase().equals([CtLiteralImpl]"POISSON"))[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]arrival = [CtFieldReadImpl]Phase.Arrival.POISSON;

                [CtLocalVariableImpl][CtCommentImpl]// If serial is enabled then run all queries exactly once in serial (rather than
                [CtCommentImpl]// random) order
                [CtTypeReferenceImpl]java.lang.String serial_string;
                [CtAssignmentImpl][CtVariableWriteImpl]serial_string = [CtInvocationImpl][CtVariableReadImpl]work.getString([CtLiteralImpl]"serial[not(@bench)]", [CtLiteralImpl]"false");
                [CtAssignmentImpl][CtVariableWriteImpl]serial_string = [CtInvocationImpl][CtVariableReadImpl]work.getString([CtBinaryOperatorImpl][CtLiteralImpl]"serial" + [CtVariableReadImpl]pluginTest, [CtVariableReadImpl]serial_string);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]serial_string.equals([CtLiteralImpl]"true")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]serial = [CtLiteralImpl]true;
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]serial_string.equals([CtLiteralImpl]"false")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]serial = [CtLiteralImpl]false;
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Invalid string for serial: '%s'. Serial string must be 'true' or 'false'", [CtVariableReadImpl]serial_string));
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                }
                [CtAssignmentImpl][CtCommentImpl]// We're not actually serial if we're running a script, so make
                [CtCommentImpl]// sure to suppress the serial flag in this case.
                [CtVariableWriteImpl]serial = [CtBinaryOperatorImpl][CtVariableReadImpl]serial && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]wrkld.getTraceReader() == [CtLiteralImpl]null);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int activeTerminals;
                [CtAssignmentImpl][CtVariableWriteImpl]activeTerminals = [CtInvocationImpl][CtVariableReadImpl]work.getInt([CtLiteralImpl]"active_terminals[not(@bench)]", [CtVariableReadImpl]terminals);
                [CtAssignmentImpl][CtVariableWriteImpl]activeTerminals = [CtInvocationImpl][CtVariableReadImpl]work.getInt([CtBinaryOperatorImpl][CtLiteralImpl]"active_terminals" + [CtVariableReadImpl]pluginTest, [CtVariableReadImpl]activeTerminals);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]activeTerminals > [CtVariableReadImpl]terminals) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.error([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Configuration error in work %d: " + [CtLiteralImpl]"Number of active terminals is bigger than the total number of terminals", [CtVariableReadImpl]i));
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                }
                [CtAssignmentImpl][CtFieldWriteImpl]com.oltpbenchmark.DBWorkload.time = [CtInvocationImpl][CtVariableReadImpl]work.getInt([CtLiteralImpl]"/time", [CtLiteralImpl]0);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int warmup = [CtInvocationImpl][CtVariableReadImpl]work.getInt([CtLiteralImpl]"/warmup", [CtLiteralImpl]0);
                [CtAssignmentImpl][CtVariableWriteImpl]timed = [CtBinaryOperatorImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.time > [CtLiteralImpl]0;
                [CtIfImpl]if ([CtVariableReadImpl]scriptRun) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtLiteralImpl]"Running a script; ignoring timer, serial, and weight settings.");
                } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]timed) [CtBlockImpl]{
                    [CtIfImpl]if ([CtVariableReadImpl]serial) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]activeTerminals > [CtLiteralImpl]1) [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// For serial executions, we usually want only one terminal, but not always!
                            [CtCommentImpl]// (e.g. the CHBenCHmark)
                            [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.warn([CtBinaryOperatorImpl][CtLiteralImpl]"\n" + [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringBoxUtil.heavyBox([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"WARNING: Serial execution is enabled but the number of active terminals[=%d] > 1.\nIs this intentional??", [CtVariableReadImpl]activeTerminals)));
                        }
                        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Timer disabled for serial run; will execute" + [CtLiteralImpl]" all queries exactly once.");
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Must provide positive time bound for" + [CtLiteralImpl]" non-serial executions. Either provide") + [CtLiteralImpl]" a valid time or enable serial mode.");
                        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                    }
                } else [CtIfImpl]if ([CtVariableReadImpl]serial)[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Timer enabled for serial run; will run queries" + [CtLiteralImpl]" serially in a loop until the timer expires.");

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]warmup < [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtBinaryOperatorImpl][CtLiteralImpl]"Must provide nonnegative time bound for" + [CtLiteralImpl]" warmup.");
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                }
                [CtInvocationImpl][CtVariableReadImpl]wrkld.addWork([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.time, [CtVariableReadImpl]warmup, [CtVariableReadImpl]rate, [CtVariableReadImpl]weight_strings, [CtVariableReadImpl]rateLimited, [CtVariableReadImpl]disabled, [CtVariableReadImpl]serial, [CtVariableReadImpl]timed, [CtVariableReadImpl]activeTerminals, [CtVariableReadImpl]arrival);
            }[CtCommentImpl]// FOR

            [CtLocalVariableImpl][CtCommentImpl]// CHECKING INPUT PHASES
            [CtTypeReferenceImpl]int j = [CtLiteralImpl]0;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Phase p : [CtInvocationImpl][CtVariableReadImpl]wrkld.getAllPhases()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]j++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getWeightCount() != [CtInvocationImpl][CtVariableReadImpl]wrkld.getNumTxnTypes()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Configuration files is inconsistent, phase %d contains %d weights but you defined %d transaction types", [CtVariableReadImpl]j, [CtInvocationImpl][CtVariableReadImpl]p.getWeightCount(), [CtInvocationImpl][CtVariableReadImpl]wrkld.getNumTxnTypes()));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isSerial()) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.fatal([CtLiteralImpl]"However, note that since this a serial phase, the weights are irrelevant (but still must be included---sorry).");
                    }
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                }
            }[CtCommentImpl]// FOR

            [CtInvocationImpl][CtCommentImpl]// Generate the dialect map
            [CtVariableReadImpl]wrkld.init();
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]wrkld.getNumTxnTypes() >= [CtLiteralImpl]0;
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]xmlConfig != [CtLiteralImpl]null;
        }
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]benchList.isEmpty() == [CtLiteralImpl]false;
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]benchList.get([CtLiteralImpl]0) != [CtLiteralImpl]null;
        [CtIfImpl][CtCommentImpl]// Export StatementDialects
        if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"dialects-export")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule bench = [CtInvocationImpl][CtVariableReadImpl]benchList.get([CtLiteralImpl]0);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]bench.getStatementDialects() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Exporting StatementDialects for " + [CtVariableReadImpl]bench);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String xml = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bench.getStatementDialects().export([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bench.getWorkloadConfiguration().getDBType(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bench.getProcedures().values());
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtVariableReadImpl]xml);
                [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtLiteralImpl]0);
            }
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"No StatementDialects is available for " + [CtVariableReadImpl]bench);
        }
        [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.Deprecated
        [CtTypeReferenceImpl]boolean verbose = [CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"v");
        [CtIfImpl][CtCommentImpl]// Create the Benchmark's Database
        if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"create")) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule benchmark : [CtVariableReadImpl]benchList) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Creating new " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]benchmark.getBenchmarkName().toUpperCase()) + [CtLiteralImpl]" database...");
                [CtInvocationImpl]com.oltpbenchmark.DBWorkload.runCreator([CtVariableReadImpl]benchmark, [CtVariableReadImpl]verbose);
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtLiteralImpl]"Finished!");
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtLiteralImpl]"Skipping creating benchmark database tables");
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
        }
        [CtIfImpl][CtCommentImpl]// Clear the Benchmark's Database
        if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"clear")) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule benchmark : [CtVariableReadImpl]benchList) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Resetting " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]benchmark.getBenchmarkName().toUpperCase()) + [CtLiteralImpl]" database...");
                [CtInvocationImpl][CtVariableReadImpl]benchmark.clearDatabase();
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtLiteralImpl]"Finished!");
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtLiteralImpl]"Skipping creating benchmark database tables");
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
        }
        [CtIfImpl][CtCommentImpl]// Execute Loader
        if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"load")) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule benchmark : [CtVariableReadImpl]benchList) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Loading data into %s database with %d threads...", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]benchmark.getBenchmarkName().toUpperCase(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]benchmark.getWorkloadConfiguration().getLoaderThreads()));
                [CtInvocationImpl]com.oltpbenchmark.DBWorkload.runLoader([CtVariableReadImpl]benchmark, [CtVariableReadImpl]verbose);
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtLiteralImpl]"Finished!");
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtLiteralImpl]"Skipping loading benchmark database records");
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
        }
        [CtIfImpl]if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"enableforeignkeys")) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule benchmark : [CtVariableReadImpl]benchList) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]benchmark.enableForeignKeys();
            }
        }
        [CtIfImpl][CtCommentImpl]// Execute a Script
        if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"runscript")) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule benchmark : [CtVariableReadImpl]benchList) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String script = [CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"runscript");
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Running a SQL script: " + [CtVariableReadImpl]script);
                [CtInvocationImpl]com.oltpbenchmark.DBWorkload.runScript([CtVariableReadImpl]benchmark, [CtVariableReadImpl]script);
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtLiteralImpl]"Finished!");
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
            }
        }
        [CtIfImpl][CtCommentImpl]// Execute Workload
        if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"execute")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Bombs away!
            [CtTypeReferenceImpl]Results r = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]r = [CtInvocationImpl]com.oltpbenchmark.DBWorkload.runWorkload([CtVariableReadImpl]benchList, [CtVariableReadImpl]verbose, [CtVariableReadImpl]intervalMonitor);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable ex) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.error([CtLiteralImpl]"Unexpected error when running benchmarks.", [CtVariableReadImpl]ex);
                [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtLiteralImpl]1);
            }
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]r != [CtLiteralImpl]null;
            [CtInvocationImpl][CtCommentImpl]// WRITE OUTPUT
            com.oltpbenchmark.DBWorkload.writeOutputs([CtVariableReadImpl]r, [CtVariableReadImpl]activeTXTypes, [CtVariableReadImpl]argsLine, [CtVariableReadImpl]xmlConfig);
            [CtIfImpl][CtCommentImpl]// WRITE HISTOGRAMS
            if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"histograms")) [CtBlockImpl]{
                [CtInvocationImpl]com.oltpbenchmark.DBWorkload.writeHistograms([CtVariableReadImpl]r);
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtLiteralImpl]"Skipping benchmark workload execution");
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void writeHistograms([CtParameterImpl][CtTypeReferenceImpl]Results r) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringUtil.bold([CtLiteralImpl]"Completed Transactions:")).append([CtLiteralImpl]"\n").append([CtInvocationImpl][CtVariableReadImpl]r.getTransactionSuccessHistogram()).append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringUtil.bold([CtLiteralImpl]"Aborted Transactions:")).append([CtLiteralImpl]"\n").append([CtInvocationImpl][CtVariableReadImpl]r.getTransactionAbortHistogram()).append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringUtil.bold([CtLiteralImpl]"Rejected Transactions (Server Retry):")).append([CtLiteralImpl]"\n").append([CtInvocationImpl][CtVariableReadImpl]r.getTransactionRetryHistogram()).append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringUtil.bold([CtLiteralImpl]"Unexpected Errors:")).append([CtLiteralImpl]"\n").append([CtInvocationImpl][CtVariableReadImpl]r.getTransactionErrorHistogram());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getTransactionAbortMessageHistogram().isEmpty() == [CtLiteralImpl]false)[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n\n").append([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringUtil.bold([CtLiteralImpl]"User Aborts:")).append([CtLiteralImpl]"\n").append([CtInvocationImpl][CtVariableReadImpl]r.getTransactionAbortMessageHistogram());

        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Workload Histograms:\n" + [CtInvocationImpl][CtVariableReadImpl]sb.toString());
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Write out the results for a benchmark run to a bunch of files
     *
     * @param r
     * @param activeTXTypes
     * @param argsLine
     * @param xmlConfig
     * @throws Exception
     */
    private static [CtTypeReferenceImpl]void writeOutputs([CtParameterImpl][CtTypeReferenceImpl]Results r, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionType> activeTXTypes, [CtParameterImpl][CtTypeReferenceImpl]org.apache.commons.cli.CommandLine argsLine, [CtParameterImpl][CtTypeReferenceImpl]org.apache.commons.configuration.XMLConfiguration xmlConfig) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// If an output directory is used, store the information
        [CtTypeReferenceImpl]java.lang.String outputDirectory = [CtLiteralImpl]"results";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"d")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]outputDirectory = [CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"d");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String filePrefix = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"t")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]filePrefix = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.TimeUtil.getCurrentTime().getTime()) + [CtLiteralImpl]"_";
        }
        [CtLocalVariableImpl][CtCommentImpl]// Special result uploader
        [CtTypeReferenceImpl]com.oltpbenchmark.util.ResultUploader ru = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]xmlConfig.containsKey([CtLiteralImpl]"uploadUrl")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ru = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.oltpbenchmark.util.ResultUploader([CtVariableReadImpl]r, [CtVariableReadImpl]xmlConfig, [CtVariableReadImpl]argsLine);
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Upload Results URL: " + [CtVariableReadImpl]ru);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Output target
        [CtTypeReferenceImpl]java.io.PrintStream ps = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintStream rs = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String baseFileName = [CtLiteralImpl]"oltpbench";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"o")) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"o").equals([CtLiteralImpl]"-")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]ps = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out;
                [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out;
                [CtAssignmentImpl][CtVariableWriteImpl]baseFileName = [CtLiteralImpl]null;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]baseFileName = [CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"o");
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// Build the complex path
        [CtTypeReferenceImpl]java.lang.String baseFile = [CtVariableReadImpl]filePrefix;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nextName;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]baseFileName != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Check if directory needs to be created
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]outputDirectory.length() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.makeDirIfNotExists([CtInvocationImpl][CtVariableReadImpl]outputDirectory.split([CtLiteralImpl]"/"));
            }
            [CtAssignmentImpl][CtVariableWriteImpl]baseFile = [CtBinaryOperatorImpl][CtVariableReadImpl]filePrefix + [CtVariableReadImpl]baseFileName;
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"output-raw", [CtLiteralImpl]"true").equalsIgnoreCase([CtLiteralImpl]"true")) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// RAW OUTPUT
                [CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".csv"));
                [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Output Raw data into file: " + [CtVariableReadImpl]nextName);
                [CtInvocationImpl][CtVariableReadImpl]r.writeAllCSVAbsoluteTiming([CtVariableReadImpl]activeTXTypes, [CtVariableReadImpl]rs);
                [CtInvocationImpl][CtVariableReadImpl]rs.close();
            }
            [CtIfImpl]if ([CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"output-samples")) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Write samples using 1 second window
                [CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".samples"));
                [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Output samples into file: " + [CtVariableReadImpl]nextName);
                [CtInvocationImpl][CtVariableReadImpl]r.writeCSV2([CtVariableReadImpl]rs);
                [CtInvocationImpl][CtVariableReadImpl]rs.close();
            }
            [CtIfImpl][CtCommentImpl]// Result Uploader Files
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]ru != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Summary Data
                [CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".summary"));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintStream ss = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Output summary data into file: " + [CtVariableReadImpl]nextName);
                [CtInvocationImpl][CtVariableReadImpl]ru.writeSummary([CtVariableReadImpl]ss);
                [CtInvocationImpl][CtVariableReadImpl]ss.close();
                [CtAssignmentImpl][CtCommentImpl]// DBMS Parameters
                [CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".params"));
                [CtAssignmentImpl][CtVariableWriteImpl]ss = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Output DBMS parameters into file: " + [CtVariableReadImpl]nextName);
                [CtInvocationImpl][CtVariableReadImpl]ru.writeDBParameters([CtVariableReadImpl]ss);
                [CtInvocationImpl][CtVariableReadImpl]ss.close();
                [CtAssignmentImpl][CtCommentImpl]// DBMS Metrics
                [CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".metrics"));
                [CtAssignmentImpl][CtVariableWriteImpl]ss = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Output DBMS metrics into file: " + [CtVariableReadImpl]nextName);
                [CtInvocationImpl][CtVariableReadImpl]ru.writeDBMetrics([CtVariableReadImpl]ss);
                [CtInvocationImpl][CtVariableReadImpl]ss.close();
                [CtAssignmentImpl][CtCommentImpl]// Experiment Configuration
                [CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".expconfig"));
                [CtAssignmentImpl][CtVariableWriteImpl]ss = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
                [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Output experiment config into file: " + [CtVariableReadImpl]nextName);
                [CtInvocationImpl][CtVariableReadImpl]ru.writeBenchmarkConf([CtVariableReadImpl]ss);
                [CtInvocationImpl][CtVariableReadImpl]ss.close();
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtLiteralImpl]"No output file specified");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]com.oltpbenchmark.DBWorkload.isBooleanOptionSet([CtVariableReadImpl]argsLine, [CtLiteralImpl]"upload") && [CtBinaryOperatorImpl]([CtVariableReadImpl]ru != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ru.uploadResult([CtVariableReadImpl]activeTXTypes);
        }
        [CtIfImpl][CtCommentImpl]// SUMMARY FILE
        if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"s")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".res"));
            [CtAssignmentImpl][CtVariableWriteImpl]ps = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Output throughput samples into file: " + [CtVariableReadImpl]nextName);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int windowSize = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtLiteralImpl]"s"));
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Grouped into Buckets of " + [CtVariableReadImpl]windowSize) + [CtLiteralImpl]" seconds");
            [CtInvocationImpl][CtVariableReadImpl]r.writeCSV([CtVariableReadImpl]windowSize, [CtVariableReadImpl]ps);
            [CtIfImpl][CtCommentImpl]// Allow more detailed reporting by transaction to make it easier to check
            if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtLiteralImpl]"ss")) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.TransactionType t : [CtVariableReadImpl]activeTXTypes) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintStream ts = [CtVariableReadImpl]ps;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ts != [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// Get the actual filename for the output
                        [CtVariableWriteImpl]baseFile = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]filePrefix + [CtVariableReadImpl]baseFileName) + [CtLiteralImpl]"_") + [CtInvocationImpl][CtVariableReadImpl]t.getName();
                        [CtAssignmentImpl][CtVariableWriteImpl]nextName = [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.getNextFilename([CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.FileUtil.joinPath([CtVariableReadImpl]outputDirectory, [CtBinaryOperatorImpl][CtVariableReadImpl]baseFile + [CtLiteralImpl]".res"));
                        [CtAssignmentImpl][CtVariableWriteImpl]ts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]nextName));
                        [CtInvocationImpl][CtVariableReadImpl]r.writeCSV([CtVariableReadImpl]windowSize, [CtVariableReadImpl]ts, [CtVariableReadImpl]t);
                        [CtInvocationImpl][CtVariableReadImpl]ts.close();
                    }
                }
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.warn([CtLiteralImpl]"No bucket size specified");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ps != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]ps.close();

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rs != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]rs.close();

    }

    [CtMethodImpl][CtCommentImpl]/* buggy piece of shit of Java XPath implementation made me do it
    replaces good old [@bench="{plugin_name}", which doesn't work in Java XPath with lists
     */
    private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getWeights([CtParameterImpl][CtTypeReferenceImpl]java.lang.String plugin, [CtParameterImpl][CtTypeReferenceImpl]org.apache.commons.configuration.SubnodeConfiguration work) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> weight_strings = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]java.lang.String>();
        [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.commons.configuration.SubnodeConfiguration> weights = [CtInvocationImpl][CtVariableReadImpl]work.configurationsAt([CtLiteralImpl]"weights");
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean weights_started = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.configuration.SubnodeConfiguration weight : [CtVariableReadImpl]weights) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// stop if second attributed node encountered
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]weights_started && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]weight.getRootNode().getAttributeCount() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
            [CtIfImpl][CtCommentImpl]// start adding node values, if node with attribute equal to current
            [CtCommentImpl]// plugin encountered
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]weight.getRootNode().getAttributeCount() > [CtLiteralImpl]0) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]weight.getRootNode().getAttribute([CtLiteralImpl]0).getValue().equals([CtVariableReadImpl]plugin)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]weights_started = [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtVariableReadImpl]weights_started) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]weight_strings.add([CtInvocationImpl][CtVariableReadImpl]weight.getString([CtLiteralImpl]""));
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]weight_strings;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void runScript([CtParameterImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule bench, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String script) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Running %s", [CtVariableReadImpl]script));
        [CtInvocationImpl][CtVariableReadImpl]bench.runScript([CtVariableReadImpl]script);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void runCreator([CtParameterImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule bench, [CtParameterImpl][CtTypeReferenceImpl]boolean verbose) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Creating %s Database", [CtVariableReadImpl]bench));
        [CtInvocationImpl][CtVariableReadImpl]bench.createDatabase();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void runLoader([CtParameterImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule bench, [CtParameterImpl][CtTypeReferenceImpl]boolean verbose) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Loading %s Database", [CtVariableReadImpl]bench));
        [CtInvocationImpl][CtVariableReadImpl]bench.loadDatabase();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.oltpbenchmark.Results runWorkload([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule> benchList, [CtParameterImpl][CtTypeReferenceImpl]boolean verbose, [CtParameterImpl][CtTypeReferenceImpl]int intervalMonitor) throws [CtTypeReferenceImpl]com.oltpbenchmark.util.QueueLimitException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.oltpbenchmark.api.Worker<[CtWildcardReferenceImpl]?>> workers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.oltpbenchmark.api.Worker<[CtWildcardReferenceImpl]?>>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]WorkloadConfiguration> workConfs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]WorkloadConfiguration>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long start = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long end = [CtBinaryOperatorImpl][CtVariableReadImpl]start + [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.time) * [CtLiteralImpl]1000) * [CtLiteralImpl]1000) * [CtLiteralImpl]1000);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.BenchmarkModule bench : [CtVariableReadImpl]benchList) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Creating " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bench.getWorkloadConfiguration().getTerminals()) + [CtLiteralImpl]" virtual terminals...");
            [CtInvocationImpl][CtVariableReadImpl]workers.addAll([CtInvocationImpl][CtVariableReadImpl]bench.makeWorkers([CtVariableReadImpl]verbose));
            [CtLocalVariableImpl][CtCommentImpl]// LOG.info("done.");
            [CtTypeReferenceImpl]int num_phases = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bench.getWorkloadConfiguration().getNumberOfPhases();
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Launching the %s Benchmark with %s Phase%s...", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bench.getBenchmarkName().toUpperCase(), [CtVariableReadImpl]num_phases, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]num_phases > [CtLiteralImpl]1 ? [CtLiteralImpl]"s" : [CtLiteralImpl]""));
            [CtInvocationImpl][CtVariableReadImpl]workConfs.add([CtInvocationImpl][CtVariableReadImpl]bench.getWorkloadConfiguration());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Results r = [CtInvocationImpl][CtTypeAccessImpl]ThreadBench.runRateLimitedBenchmark([CtVariableReadImpl]workers, [CtVariableReadImpl]workConfs, [CtVariableReadImpl]intervalMonitor);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long numNewOrderTransactions = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oltpbenchmark.api.Worker<[CtWildcardReferenceImpl]?> w : [CtVariableReadImpl]workers) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]LatencyRecord.Sample sample : [CtInvocationImpl][CtVariableReadImpl]w.getLatencyRecords()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]sample.tranType == [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.newOrderTxnId) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]sample.startNs + [CtBinaryOperatorImpl]([CtLiteralImpl]1000L * [CtFieldReadImpl][CtVariableReadImpl]sample.latencyUs)) <= [CtVariableReadImpl]end)) [CtBlockImpl]{
                    [CtUnaryOperatorImpl]++[CtVariableWriteImpl]numNewOrderTransactions;
                }
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtFieldReadImpl]com.oltpbenchmark.DBWorkload.SINGLE_LINE);
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Rate limited reqs/s: " + [CtVariableReadImpl]r);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long tpmc = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]numNewOrderTransactions * [CtLiteralImpl]60) / [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.time;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double efficiency = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]1.0 * [CtVariableReadImpl]tpmc) * [CtLiteralImpl]100) / [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.numWarehouses) / [CtLiteralImpl]12.86;
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Num New Order transactions : " + [CtVariableReadImpl]numNewOrderTransactions) + [CtLiteralImpl]", time seconds: ") + [CtFieldReadImpl]com.oltpbenchmark.DBWorkload.time);
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"TPM-C: " + [CtVariableReadImpl]tpmc);
        [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Efficiency : " + [CtVariableReadImpl]efficiency);
        [CtReturnImpl]return [CtVariableReadImpl]r;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void printUsage([CtParameterImpl][CtTypeReferenceImpl]org.apache.commons.cli.Options options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.cli.HelpFormatter hlpfrmt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.cli.HelpFormatter();
        [CtInvocationImpl][CtVariableReadImpl]hlpfrmt.printHelp([CtLiteralImpl]"tpccbenchmark", [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the given key is in the CommandLine object and is set to
     * true.
     *
     * @param argsLine
     * @param key
     * @return  */
    private static [CtTypeReferenceImpl]boolean isBooleanOptionSet([CtParameterImpl][CtTypeReferenceImpl]org.apache.commons.cli.CommandLine argsLine, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]argsLine.hasOption([CtVariableReadImpl]key)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"CommandLine has option '" + [CtVariableReadImpl]key) + [CtLiteralImpl]"'. Checking whether set to true");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String val = [CtInvocationImpl][CtVariableReadImpl]argsLine.getOptionValue([CtVariableReadImpl]key);
            [CtInvocationImpl][CtFieldReadImpl]com.oltpbenchmark.DBWorkload.LOG.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"CommandLine %s => %s", [CtVariableReadImpl]key, [CtVariableReadImpl]val));
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]val != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]val.equalsIgnoreCase([CtLiteralImpl]"true") : [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getAssertWarning() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"!!! WARNING !!!\n" + [CtLiteralImpl]"OLTP-Bench is executing with JVM asserts enabled. This will degrade runtime performance.\n") + [CtLiteralImpl]"You can disable them by setting the config option 'assertions' to FALSE";
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.oltpbenchmark.util.StringBoxUtil.heavyBox([CtVariableReadImpl]msg);
    }
}
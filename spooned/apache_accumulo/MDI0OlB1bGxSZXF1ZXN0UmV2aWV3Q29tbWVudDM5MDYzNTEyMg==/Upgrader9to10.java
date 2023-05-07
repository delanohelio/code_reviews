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
[CtPackageDeclarationImpl]package org.apache.accumulo.master.upgrade;
[CtUnresolvedImport]import org.apache.accumulo.core.data.TableId;
[CtUnresolvedImport]import org.apache.accumulo.server.metadata.TabletMutatorBase;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.accumulo.server.metadata.RootGcCandidates;
[CtUnresolvedImport]import org.apache.hadoop.fs.FileSystem;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.accumulo.core.data.Mutation;
[CtUnresolvedImport]import org.apache.hadoop.fs.FileStatus;
[CtImportImpl]import java.io.UncheckedIOException;
[CtUnresolvedImport]import org.apache.accumulo.core.client.TableNotFoundException;
[CtUnresolvedImport]import static org.apache.accumulo.core.metadata.RootTable.ZROOT_TABLET;
[CtUnresolvedImport]import org.apache.accumulo.core.client.BatchWriterConfig;
[CtUnresolvedImport]import org.apache.accumulo.core.file.FileOperations;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.RootTable;
[CtUnresolvedImport]import org.apache.hadoop.fs.Path;
[CtUnresolvedImport]import org.apache.accumulo.core.data.Value;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.schema.MetadataTime;
[CtUnresolvedImport]import org.apache.accumulo.core.conf.Property;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.schema.TabletMetadata.LocationType;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.schema.Ample;
[CtUnresolvedImport]import org.apache.accumulo.server.fs.VolumeManager;
[CtUnresolvedImport]import org.apache.accumulo.core.client.MutationsRejectedException;
[CtUnresolvedImport]import org.apache.accumulo.core.file.FileSKVIterator;
[CtUnresolvedImport]import org.apache.accumulo.core.client.admin.TimeType;
[CtUnresolvedImport]import org.apache.accumulo.core.client.AccumuloClient;
[CtUnresolvedImport]import org.apache.accumulo.server.metadata.ServerAmpleImpl;
[CtUnresolvedImport]import org.apache.accumulo.fate.zookeeper.ZooUtil.NodeExistsPolicy;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.schema.MetadataSchema;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.apache.accumulo.server.master.state.TServerInstance;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.accumulo.core.client.Scanner;
[CtUnresolvedImport]import static org.apache.accumulo.core.metadata.schema.MetadataSchema.TabletsSection.ServerColumnFamily.DIRECTORY_COLUMN;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.schema.DataFileValue;
[CtUnresolvedImport]import org.apache.accumulo.core.util.HostAndPort;
[CtUnresolvedImport]import org.apache.accumulo.fate.zookeeper.ZooUtil;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static org.apache.accumulo.server.util.MetadataTableUtil.EMPTY_TEXT;
[CtImportImpl]import java.util.Map.Entry;
[CtImportImpl]import static java.nio.charset.StandardCharsets.UTF_8;
[CtImportImpl]import java.util.stream.StreamSupport;
[CtUnresolvedImport]import org.apache.accumulo.core.data.Key;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.TabletFile;
[CtUnresolvedImport]import org.apache.accumulo.fate.zookeeper.ZooReaderWriter;
[CtUnresolvedImport]import org.apache.accumulo.fate.zookeeper.ZooUtil.NodeMissingPolicy;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.apache.accumulo.server.gc.GcVolumeUtil;
[CtUnresolvedImport]import org.apache.accumulo.core.tabletserver.log.LogEntry;
[CtUnresolvedImport]import org.apache.zookeeper.KeeperException;
[CtUnresolvedImport]import org.apache.accumulo.server.ServerContext;
[CtUnresolvedImport]import org.apache.accumulo.core.dataImpl.KeyExtent;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.apache.accumulo.core.Constants;
[CtUnresolvedImport]import org.apache.accumulo.core.security.Authorizations;
[CtUnresolvedImport]import org.apache.accumulo.core.client.AccumuloException;
[CtUnresolvedImport]import org.apache.accumulo.core.data.Range;
[CtUnresolvedImport]import org.apache.zookeeper.KeeperException.NoNodeException;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.schema.RootTabletMetadata;
[CtUnresolvedImport]import static org.apache.accumulo.core.metadata.RootTable.ZROOT_TABLET_GC_CANDIDATES;
[CtUnresolvedImport]import com.google.common.base.Preconditions;
[CtUnresolvedImport]import org.apache.accumulo.core.client.BatchWriter;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtClassImpl][CtJavaDocImpl]/**
 * Handles upgrading from 2.0 to 2.1
 */
public class Upgrader9to10 implements [CtTypeReferenceImpl]org.apache.accumulo.master.upgrade.Upgrader {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.class);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ZROOT_TABLET_LOCATION = [CtBinaryOperatorImpl][CtFieldReadImpl]ZROOT_TABLET + [CtLiteralImpl]"/location";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ZROOT_TABLET_FUTURE_LOCATION = [CtBinaryOperatorImpl][CtFieldReadImpl]ZROOT_TABLET + [CtLiteralImpl]"/future_location";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ZROOT_TABLET_LAST_LOCATION = [CtBinaryOperatorImpl][CtFieldReadImpl]ZROOT_TABLET + [CtLiteralImpl]"/lastlocation";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ZROOT_TABLET_WALOGS = [CtBinaryOperatorImpl][CtFieldReadImpl]ZROOT_TABLET + [CtLiteralImpl]"/walogs";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ZROOT_TABLET_CURRENT_LOGS = [CtBinaryOperatorImpl][CtFieldReadImpl]ZROOT_TABLET + [CtLiteralImpl]"/current_logs";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ZROOT_TABLET_PATH = [CtBinaryOperatorImpl][CtFieldReadImpl]ZROOT_TABLET + [CtLiteralImpl]"/dir";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]org.apache.accumulo.core.data.Value UPGRADED = [CtFieldReadImpl]MetadataSchema.DeletesSection.SkewedKeyValue.NAME;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String OLD_DELETE_PREFIX = [CtLiteralImpl]"~del";

    [CtFieldImpl][CtCommentImpl]// effectively an 8MB batch size, since this number is the number of Chars
    public static final [CtTypeReferenceImpl]long CANDIDATE_BATCH_SIZE = [CtLiteralImpl]4000000;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void upgradeZookeeper([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx) [CtBlockImpl]{
        [CtInvocationImpl]upgradeRootTabletMetadata([CtVariableReadImpl]ctx);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void upgradeRoot([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx) [CtBlockImpl]{
        [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.upgradeRelativePaths([CtVariableReadImpl]ctx, [CtTypeAccessImpl]Ample.DataLevel.METADATA);
        [CtInvocationImpl]upgradeDirColumns([CtVariableReadImpl]ctx, [CtTypeAccessImpl]Ample.DataLevel.METADATA);
        [CtInvocationImpl]upgradeFileDeletes([CtVariableReadImpl]ctx, [CtTypeAccessImpl]Ample.DataLevel.METADATA);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void upgradeMetadata([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx) [CtBlockImpl]{
        [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.upgradeRelativePaths([CtVariableReadImpl]ctx, [CtTypeAccessImpl]Ample.DataLevel.USER);
        [CtInvocationImpl]upgradeDirColumns([CtVariableReadImpl]ctx, [CtTypeAccessImpl]Ample.DataLevel.USER);
        [CtInvocationImpl]upgradeFileDeletes([CtVariableReadImpl]ctx, [CtTypeAccessImpl]Ample.DataLevel.USER);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void upgradeRootTabletMetadata([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String rootMetaSer = [CtInvocationImpl]getFromZK([CtVariableReadImpl]ctx, [CtTypeAccessImpl]org.apache.accumulo.master.upgrade.ZROOT_TABLET);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]rootMetaSer == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]rootMetaSer.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dir = [CtInvocationImpl]getFromZK([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_PATH);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.accumulo.core.tabletserver.log.LogEntry> logs = [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.getRootLogEntries([CtVariableReadImpl]ctx);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.server.master.state.TServerInstance last = [CtInvocationImpl]getLocation([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_LAST_LOCATION);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.server.master.state.TServerInstance future = [CtInvocationImpl]getLocation([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_FUTURE_LOCATION);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.server.master.state.TServerInstance current = [CtInvocationImpl]getLocation([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_LOCATION);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.UpgradeMutator tabletMutator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.UpgradeMutator([CtVariableReadImpl]ctx);
            [CtInvocationImpl][CtVariableReadImpl]tabletMutator.putPrevEndRow([CtInvocationImpl][CtTypeAccessImpl]RootTable.EXTENT.getPrevEndRow());
            [CtInvocationImpl][CtVariableReadImpl]tabletMutator.putDirName([CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.upgradeDirColumn([CtVariableReadImpl]dir));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]last != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]tabletMutator.putLocation([CtVariableReadImpl]last, [CtTypeAccessImpl]LocationType.LAST);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]future != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]tabletMutator.putLocation([CtVariableReadImpl]future, [CtTypeAccessImpl]LocationType.FUTURE);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]current != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]tabletMutator.putLocation([CtVariableReadImpl]current, [CtTypeAccessImpl]LocationType.CURRENT);

            [CtInvocationImpl][CtVariableReadImpl]logs.forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]tabletMutator::putWal);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.DataFileValue> files = [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.cleanupRootTabletFiles([CtInvocationImpl][CtVariableReadImpl]ctx.getVolumeManager(), [CtVariableReadImpl]dir);
            [CtInvocationImpl][CtVariableReadImpl]files.forEach([CtLambdaImpl]([CtParameterImpl] path,[CtParameterImpl] dfv) -> [CtInvocationImpl][CtVariableReadImpl]tabletMutator.putFile([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.metadata.TabletFile([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]path)), [CtVariableReadImpl]dfv));
            [CtInvocationImpl][CtVariableReadImpl]tabletMutator.putTime([CtInvocationImpl]computeRootTabletTime([CtVariableReadImpl]ctx, [CtInvocationImpl][CtVariableReadImpl]files.keySet()));
            [CtInvocationImpl][CtVariableReadImpl]tabletMutator.mutate();
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getZooReaderWriter().putPersistentData([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getZooKeeperRoot() + [CtFieldReadImpl]ZROOT_TABLET_GC_CANDIDATES, [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.server.metadata.RootGcCandidates().toJson().getBytes([CtFieldReadImpl]java.nio.charset.StandardCharsets.UTF_8), [CtTypeAccessImpl]NodeExistsPolicy.SKIP);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.apache.zookeeper.KeeperException | [CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtCommentImpl]// this operation must be idempotent, so deleting after updating is very important
        delete([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_CURRENT_LOGS);
        [CtInvocationImpl]delete([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_FUTURE_LOCATION);
        [CtInvocationImpl]delete([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_LAST_LOCATION);
        [CtInvocationImpl]delete([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_LOCATION);
        [CtInvocationImpl]delete([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_WALOGS);
        [CtInvocationImpl]delete([CtVariableReadImpl]ctx, [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_PATH);
    }

    [CtClassImpl]private static class UpgradeMutator extends [CtTypeReferenceImpl]org.apache.accumulo.server.metadata.TabletMutatorBase {
        [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext context;

        [CtConstructorImpl]UpgradeMutator([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext context) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]context, [CtTypeAccessImpl]RootTable.EXTENT);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtVariableReadImpl]context;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void mutate() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation mutation = [CtInvocationImpl]getMutation();
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getZooReaderWriter().mutate([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]context.getZooKeeperRoot() + [CtFieldReadImpl]org.apache.accumulo.core.metadata.RootTable.ZROOT_TABLET, [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtLiteralImpl]0], [CtTypeAccessImpl]ZooUtil.PUBLIC, [CtLambdaImpl]([CtParameterImpl] currVal) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Earlier, it was checked that root tablet metadata did not exists. However the
                    [CtCommentImpl]// earlier check does handle race conditions. Race conditions are unexpected. This is
                    [CtCommentImpl]// a sanity check when making the update in ZK using compare and set. If this fails
                    [CtCommentImpl]// and its not a bug, then its likely some concurrency issue. For example two masters
                    [CtCommentImpl]// concurrently running upgrade could cause this to fail.
                    [CtTypeAccessImpl]com.google.common.base.Preconditions.checkState([CtBinaryOperatorImpl][CtVariableReadImpl]currVal.length == [CtLiteralImpl]0, [CtLiteralImpl]"Expected root tablet metadata to be empty!");
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.RootTabletMetadata rtm = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.RootTabletMetadata();
                    [CtInvocationImpl][CtVariableReadImpl]rtm.update([CtVariableReadImpl]mutation);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String json = [CtInvocationImpl][CtVariableReadImpl]rtm.toJson();
                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.master.upgrade.log.info([CtLiteralImpl]"Upgrading root tablet metadata, writing following to ZK : \n {}", [CtVariableReadImpl]json);
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]json.getBytes([CtFieldReadImpl][CtFieldReferenceImpl]java.nio.charset.StandardCharsets.UTF_8);
                });
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.apache.accumulo.server.master.state.TServerInstance getLocation([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relpath) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String str = [CtInvocationImpl]getFromZK([CtVariableReadImpl]ctx, [CtVariableReadImpl]relpath);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]str == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] parts = [CtInvocationImpl][CtVariableReadImpl]str.split([CtLiteralImpl]"[|]", [CtLiteralImpl]2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.util.HostAndPort address = [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.util.HostAndPort.fromString([CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]0]);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]parts.length > [CtLiteralImpl]1) && [CtBinaryOperatorImpl]([CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]1] != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]1].length() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.server.master.state.TServerInstance([CtVariableReadImpl]address, [CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]1]);
        } else [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// a 1.2 location specification: DO NOT WANT
            return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.accumulo.core.tabletserver.log.LogEntry> getRootLogEntries([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext context) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.apache.accumulo.core.tabletserver.log.LogEntry> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.fate.zookeeper.ZooReaderWriter zoo = [CtInvocationImpl][CtVariableReadImpl]context.getZooReaderWriter();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String root = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]context.getZooKeeperRoot() + [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.ZROOT_TABLET_WALOGS;
            [CtWhileImpl][CtCommentImpl]// there's a little race between getting the children and fetching
            [CtCommentImpl]// the data. The log can be removed in between.
            outer : while ([CtLiteralImpl]true) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]result.clear();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String child : [CtInvocationImpl][CtVariableReadImpl]zoo.getChildren([CtVariableReadImpl]root)) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.tabletserver.log.LogEntry e = [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.tabletserver.log.LogEntry.fromBytes([CtInvocationImpl][CtVariableReadImpl]zoo.getData([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]root + [CtLiteralImpl]"/") + [CtVariableReadImpl]child, [CtLiteralImpl]null));
                        [CtAssignmentImpl][CtCommentImpl]// upgrade from !0;!0<< -> +r<<
                        [CtVariableWriteImpl]e = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.tabletserver.log.LogEntry([CtFieldReadImpl]org.apache.accumulo.core.metadata.RootTable.EXTENT, [CtLiteralImpl]0, [CtFieldReadImpl][CtVariableReadImpl]e.server, [CtFieldReadImpl][CtVariableReadImpl]e.filename);
                        [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]e);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.zookeeper.KeeperException.NoNodeException ex) [CtBlockImpl]{
                        [CtContinueImpl][CtCommentImpl]// TODO I think this is a bug, probably meant to continue to while loop... was probably
                        [CtCommentImpl]// a bug in the original code.
                        continue outer;
                    }
                }
                [CtBreakImpl]break;
            } 
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.apache.zookeeper.KeeperException | [CtTypeReferenceImpl]java.lang.InterruptedException | [CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getFromZK([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relpath) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] data = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getZooReaderWriter().getData([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getZooKeeperRoot() + [CtVariableReadImpl]relpath, [CtLiteralImpl]null);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]data == [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]null;

            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtVariableReadImpl]data, [CtFieldReadImpl]java.nio.charset.StandardCharsets.UTF_8);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.zookeeper.KeeperException.NoNodeException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.apache.zookeeper.KeeperException | [CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void delete([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relpath) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getZooReaderWriter().recursiveDelete([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getZooKeeperRoot() + [CtVariableReadImpl]relpath, [CtTypeAccessImpl]NodeMissingPolicy.SKIP);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.apache.zookeeper.KeeperException | [CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.MetadataTime computeRootTabletTime([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext context, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> goodPaths) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]context.setupCrypto();
            [CtLocalVariableImpl][CtTypeReferenceImpl]long rtime = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Long.[CtFieldReferenceImpl]MIN_VALUE;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String good : [CtVariableReadImpl]goodPaths) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path path = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]good);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.FileSystem ns = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getVolumeManager().getFileSystemByPath([CtVariableReadImpl]path);
                [CtLocalVariableImpl][CtTypeReferenceImpl]long maxTime = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.file.FileSKVIterator reader = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.file.FileOperations.getInstance().newReaderBuilder().forFile([CtInvocationImpl][CtVariableReadImpl]path.toString(), [CtVariableReadImpl]ns, [CtInvocationImpl][CtVariableReadImpl]ns.getConf(), [CtInvocationImpl][CtVariableReadImpl]context.getCryptoService()).withTableConfiguration([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getServerConfFactory().getTableConfiguration([CtTypeAccessImpl]RootTable.ID)).seekToBeginning().build()) [CtBlockImpl]{
                    [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]reader.hasTop()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]maxTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]maxTime, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]reader.getTopKey().getTimestamp());
                        [CtInvocationImpl][CtVariableReadImpl]reader.next();
                    } 
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxTime > [CtVariableReadImpl]rtime) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]rtime = [CtVariableReadImpl]maxTime;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rtime < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Unexpected root tablet logical time " + [CtVariableReadImpl]rtime);
            }
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.MetadataTime([CtVariableReadImpl]rtime, [CtFieldReadImpl]org.apache.accumulo.core.client.admin.TimeType.LOGICAL);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.DataFileValue> cleanupRootTabletFiles([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.fs.VolumeManager fs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dir) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.hadoop.fs.FileStatus[] files = [CtInvocationImpl][CtVariableReadImpl]fs.listStatus([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]dir));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.DataFileValue> goodFiles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtFieldReadImpl][CtVariableReadImpl]files.length);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.FileStatus file : [CtVariableReadImpl]files) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]file.getPath().toString();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]file.getPath().toUri().getScheme() == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtThrowImpl][CtCommentImpl]// depending on the behavior of HDFS, if list status does not return fully qualified
                    [CtCommentImpl]// volumes
                    [CtCommentImpl]// then could switch to the default volume
                    throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Require fully qualified paths " + [CtInvocationImpl][CtVariableReadImpl]file.getPath());
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String filename = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]file.getPath().getName();
                [CtIfImpl][CtCommentImpl]// check for incomplete major compaction, this should only occur
                [CtCommentImpl]// for root tablet
                if ([CtInvocationImpl][CtVariableReadImpl]filename.startsWith([CtLiteralImpl]"delete+")) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String expectedCompactedFile = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]path.substring([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]path.lastIndexOf([CtLiteralImpl]"/delete+")) + [CtLiteralImpl]"/") + [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]filename.split([CtLiteralImpl]"\\+")[[CtLiteralImpl]1];
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fs.exists([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]expectedCompactedFile))) [CtBlockImpl]{
                        [CtIfImpl][CtCommentImpl]// compaction finished, but did not finish deleting compacted files.. so delete it
                        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]fs.deleteRecursively([CtInvocationImpl][CtVariableReadImpl]file.getPath()))[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.warn([CtLiteralImpl]"Delete of file: {} return false", [CtInvocationImpl][CtVariableReadImpl]file.getPath());

                        [CtContinueImpl]continue;
                    }
                    [CtAssignmentImpl][CtCommentImpl]// compaction did not finish, so put files back
                    [CtCommentImpl]// reset path and filename for rest of loop
                    [CtVariableWriteImpl]filename = [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]filename.split([CtLiteralImpl]"\\+", [CtLiteralImpl]3)[[CtLiteralImpl]2];
                    [CtAssignmentImpl][CtVariableWriteImpl]path = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]path.substring([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]path.lastIndexOf([CtLiteralImpl]"/delete+")) + [CtLiteralImpl]"/") + [CtVariableReadImpl]filename;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path src = [CtInvocationImpl][CtVariableReadImpl]file.getPath();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path dst = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]path);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]fs.rename([CtVariableReadImpl]src, [CtVariableReadImpl]dst)) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Rename " + [CtVariableReadImpl]src) + [CtLiteralImpl]" to ") + [CtVariableReadImpl]dst) + [CtLiteralImpl]" returned false ");
                    }
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]filename.endsWith([CtLiteralImpl]"_tmp")) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.warn([CtLiteralImpl]"cleaning up old tmp file: {}", [CtVariableReadImpl]path);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]fs.deleteRecursively([CtInvocationImpl][CtVariableReadImpl]file.getPath()))[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.warn([CtLiteralImpl]"Delete of tmp file: {} return false", [CtInvocationImpl][CtVariableReadImpl]file.getPath());

                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]filename.startsWith([CtBinaryOperatorImpl][CtFieldReadImpl]org.apache.accumulo.core.Constants.MAPFILE_EXTENSION + [CtLiteralImpl]"_")) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.file.FileOperations.getValidExtensions().contains([CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]filename.split([CtLiteralImpl]"\\.")[[CtLiteralImpl]1]))) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.error([CtLiteralImpl]"unknown file in tablet: {}", [CtVariableReadImpl]path);
                    [CtContinueImpl]continue;
                }
                [CtInvocationImpl][CtVariableReadImpl]goodFiles.put([CtVariableReadImpl]path, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.DataFileValue([CtInvocationImpl][CtVariableReadImpl]file.getLen(), [CtLiteralImpl]0));
            }
            [CtReturnImpl]return [CtVariableReadImpl]goodFiles;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void upgradeFileDeletes([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.Ample.DataLevel level) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtVariableReadImpl]level.metaTable();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.AccumuloClient c = [CtVariableReadImpl]ctx;
        [CtTryWithResourceImpl][CtCommentImpl]// find all deletes
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.BatchWriter writer = [CtInvocationImpl][CtVariableReadImpl]c.createBatchWriter([CtVariableReadImpl]tableName, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.client.BatchWriterConfig())) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.info([CtLiteralImpl]"looking for candidates in table {}", [CtVariableReadImpl]tableName);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> oldCandidates = [CtInvocationImpl]getOldCandidates([CtVariableReadImpl]ctx, [CtVariableReadImpl]tableName);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String upgradeProp = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getConfiguration().get([CtTypeAccessImpl]Property.INSTANCE_VOLUMES_UPGRADE_RELATIVE);
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]oldCandidates.hasNext()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> deletes = [CtInvocationImpl]readCandidatesInBatch([CtVariableReadImpl]oldCandidates);
                [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.info([CtLiteralImpl]"found {} deletes to upgrade", [CtInvocationImpl][CtVariableReadImpl]deletes.size());
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String olddelete : [CtVariableReadImpl]deletes) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// create new formatted delete
                    [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.trace([CtLiteralImpl]"upgrading delete entry for {}", [CtVariableReadImpl]olddelete);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path absolutePath = [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.resolveRelativeDelete([CtInvocationImpl][CtVariableReadImpl]ctx.getVolumeManager(), [CtVariableReadImpl]olddelete, [CtVariableReadImpl]upgradeProp);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String updatedDel = [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.switchToAllVolumes([CtVariableReadImpl]absolutePath);
                    [CtInvocationImpl][CtVariableReadImpl]writer.addMutation([CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.server.metadata.ServerAmpleImpl.createDeleteMutation([CtVariableReadImpl]updatedDel));
                }
                [CtInvocationImpl][CtVariableReadImpl]writer.flush();
                [CtInvocationImpl][CtCommentImpl]// if nothing thrown then we're good so mark all deleted
                [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.info([CtLiteralImpl]"upgrade processing completed so delete old entries");
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String olddelete : [CtVariableReadImpl]deletes) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.trace([CtLiteralImpl]"deleting old entry for {}", [CtVariableReadImpl]olddelete);
                    [CtInvocationImpl][CtVariableReadImpl]writer.addMutation([CtInvocationImpl]deleteOldDeleteMutation([CtVariableReadImpl]olddelete));
                }
                [CtInvocationImpl][CtVariableReadImpl]writer.flush();
            } 
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.apache.accumulo.core.client.TableNotFoundException | [CtTypeReferenceImpl]org.apache.accumulo.core.client.MutationsRejectedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If path of file to delete is a directory, change it to all volumes. See {@link GcVolumeUtil}.
     * For example: A directory "hdfs://localhost:9000/accumulo/tables/5a/t-0005" with depth = 4 will
     * be switched to "agcav:/tables/5a/t-0005". A file
     * "hdfs://localhost:9000/accumulo/tables/5a/t-0005/A0012.rf" with depth = 5 will be returned as
     * is.
     */
    [CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    static [CtTypeReferenceImpl]java.lang.String switchToAllVolumes([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path olddelete) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// for directory, change volume to all volumes
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]olddelete.depth() == [CtLiteralImpl]4) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]olddelete.getName().startsWith([CtTypeAccessImpl]Constants.BULK_PREFIX))) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.server.gc.GcVolumeUtil.getDeleteTabletOnAllVolumesUri([CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.data.TableId.of([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]olddelete.getParent().getName()), [CtInvocationImpl][CtVariableReadImpl]olddelete.getName());
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]olddelete.toString();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return path of the file from old delete markers
     */
    private [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> getOldCandidates([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName) throws [CtTypeReferenceImpl]org.apache.accumulo.core.client.TableNotFoundException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Range range = [CtInvocationImpl][CtTypeAccessImpl]MetadataSchema.DeletesSection.getRange();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.Scanner scanner = [CtInvocationImpl][CtVariableReadImpl]ctx.createScanner([CtVariableReadImpl]tableName, [CtTypeAccessImpl]Authorizations.EMPTY);
        [CtInvocationImpl][CtVariableReadImpl]scanner.setRange([CtVariableReadImpl]range);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.stream.StreamSupport.stream([CtInvocationImpl][CtVariableReadImpl]scanner.spliterator(), [CtLiteralImpl]false).filter([CtLambdaImpl]([CtParameterImpl] entry) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().equals([CtFieldReadImpl][CtFieldReferenceImpl]UPGRADED)).map([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getKey().getRow().toString().substring([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]OLD_DELETE_PREFIX.length())).iterator();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> readCandidatesInBatch([CtParameterImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> candidates) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long candidateLength = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]candidates.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String candidate = [CtInvocationImpl][CtVariableReadImpl]candidates.next();
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]candidateLength += [CtInvocationImpl][CtVariableReadImpl]candidate.length();
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]candidate);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]candidateLength > [CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.CANDIDATE_BATCH_SIZE) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.trace([CtBinaryOperatorImpl][CtLiteralImpl]"List of delete candidates has exceeded the batch size" + [CtLiteralImpl]" threshold. Attempting to delete what has been gathered so far.");
                [CtBreakImpl]break;
            }
        } 
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation deleteOldDeleteMutation([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String delete) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation([CtBinaryOperatorImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.OLD_DELETE_PREFIX + [CtVariableReadImpl]delete);
        [CtInvocationImpl][CtVariableReadImpl]m.putDelete([CtTypeAccessImpl]org.apache.accumulo.master.upgrade.EMPTY_TEXT, [CtTypeAccessImpl]org.apache.accumulo.master.upgrade.EMPTY_TEXT);
        [CtReturnImpl]return [CtVariableReadImpl]m;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void upgradeDirColumns([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.Ample.DataLevel level) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtVariableReadImpl]level.metaTable();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.AccumuloClient c = [CtVariableReadImpl]ctx;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.Scanner scanner = [CtInvocationImpl][CtVariableReadImpl]c.createScanner([CtVariableReadImpl]tableName, [CtTypeAccessImpl]Authorizations.EMPTY);[CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.BatchWriter writer = [CtInvocationImpl][CtVariableReadImpl]c.createBatchWriter([CtVariableReadImpl]tableName, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.client.BatchWriterConfig())) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.master.upgrade.DIRECTORY_COLUMN.fetch([CtVariableReadImpl]scanner);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.apache.accumulo.core.data.Key, [CtTypeReferenceImpl]org.apache.accumulo.core.data.Value> entry : [CtVariableReadImpl]scanner) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getKey().getRow());
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.master.upgrade.DIRECTORY_COLUMN.put([CtVariableReadImpl]m, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.data.Value([CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.upgradeDirColumn([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().toString())));
                [CtInvocationImpl][CtVariableReadImpl]writer.addMutation([CtVariableReadImpl]m);
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.apache.accumulo.core.client.TableNotFoundException | [CtTypeReferenceImpl]org.apache.accumulo.core.client.AccumuloException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String upgradeDirColumn([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dir) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]dir).getName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove all file entries containing relative paths and replace them with absolute URI paths.
     */
    public static [CtTypeReferenceImpl]void upgradeRelativePaths([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.ServerContext ctx, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.accumulo.core.metadata.schema.Ample.DataLevel level) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtVariableReadImpl]level.metaTable();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.AccumuloClient c = [CtVariableReadImpl]ctx;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.server.fs.VolumeManager fs = [CtInvocationImpl][CtVariableReadImpl]ctx.getVolumeManager();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String upgradeProp = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getConfiguration().get([CtTypeAccessImpl]Property.INSTANCE_VOLUMES_UPGRADE_RELATIVE);
        [CtIfImpl][CtCommentImpl]// first pass check for relative paths - if any, check existence of the file path
        [CtCommentImpl]// constructed from the upgrade property + relative path
        if ([CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.checkForRelativePaths([CtVariableReadImpl]c, [CtVariableReadImpl]fs, [CtVariableReadImpl]tableName, [CtVariableReadImpl]upgradeProp)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.info([CtLiteralImpl]"Relative Tablet File paths exist in {}, replacing with absolute using {}", [CtVariableReadImpl]tableName, [CtVariableReadImpl]upgradeProp);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.info([CtLiteralImpl]"No relative paths found in {} during upgrade.", [CtVariableReadImpl]tableName);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtCommentImpl]// second pass, create atomic mutations to replace the relative path
        org.apache.accumulo.master.upgrade.Upgrader9to10.replaceRelativePaths([CtVariableReadImpl]c, [CtVariableReadImpl]fs, [CtVariableReadImpl]tableName, [CtVariableReadImpl]upgradeProp);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Replace relative paths but only if the constructed absolute path exists on FileSystem
     */
    public static [CtTypeReferenceImpl]void replaceRelativePaths([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.AccumuloClient c, [CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.fs.VolumeManager fs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String upgradeProperty) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.Scanner scanner = [CtInvocationImpl][CtVariableReadImpl]c.createScanner([CtVariableReadImpl]tableName, [CtTypeAccessImpl]Authorizations.EMPTY);[CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.BatchWriter writer = [CtInvocationImpl][CtVariableReadImpl]c.createBatchWriter([CtVariableReadImpl]tableName)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]scanner.fetchColumnFamily([CtTypeAccessImpl]MetadataSchema.TabletsSection.DataFileColumnFamily.NAME);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.apache.accumulo.core.data.Key, [CtTypeReferenceImpl]org.apache.accumulo.core.data.Value> entry : [CtVariableReadImpl]scanner) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Key key = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String metaEntry = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]key.getColumnQualifier().toString();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]metaEntry.contains([CtLiteralImpl]":")) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// found relative paths so get the property used to build the absolute paths
                    if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]upgradeProperty == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]upgradeProperty.isBlank()) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Missing required property " + [CtInvocationImpl][CtTypeAccessImpl]Property.INSTANCE_VOLUMES_UPGRADE_RELATIVE.getKey());
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path relPath = [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.resolveRelativePath([CtVariableReadImpl]metaEntry, [CtVariableReadImpl]key);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path absPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]upgradeProperty, [CtVariableReadImpl]relPath);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fs.exists([CtVariableReadImpl]absPath)) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.debug([CtLiteralImpl]"Changing Tablet File path from {} to {}", [CtVariableReadImpl]metaEntry, [CtVariableReadImpl]absPath);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.accumulo.core.data.Mutation([CtInvocationImpl][CtVariableReadImpl]key.getRow());
                        [CtInvocationImpl][CtCommentImpl]// add the new path
                        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.at().family([CtInvocationImpl][CtVariableReadImpl]key.getColumnFamily()).qualifier([CtInvocationImpl][CtVariableReadImpl]absPath.toString()).visibility([CtInvocationImpl][CtVariableReadImpl]key.getColumnVisibility()).put([CtInvocationImpl][CtVariableReadImpl]entry.getValue());
                        [CtInvocationImpl][CtCommentImpl]// delete the old path
                        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.at().family([CtInvocationImpl][CtVariableReadImpl]key.getColumnFamily()).qualifier([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]key.getColumnQualifierData().toArray()).visibility([CtInvocationImpl][CtVariableReadImpl]key.getColumnVisibility()).delete();
                        [CtInvocationImpl][CtVariableReadImpl]writer.addMutation([CtVariableReadImpl]m);
                    } else [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Relative Tablet file " + [CtVariableReadImpl]relPath) + [CtLiteralImpl]" not found at ") + [CtVariableReadImpl]absPath);
                    }
                }
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.apache.accumulo.core.client.MutationsRejectedException | [CtTypeReferenceImpl]org.apache.accumulo.core.client.TableNotFoundException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException ioe) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]ioe);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if table has any relative paths, return false if none are found. When a relative path is
     * found, check existence of the file path constructed from the upgrade property + relative path
     */
    public static [CtTypeReferenceImpl]boolean checkForRelativePaths([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.AccumuloClient client, [CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.fs.VolumeManager fs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String upgradeProperty) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasRelatives = [CtLiteralImpl]false;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.Scanner scanner = [CtInvocationImpl][CtVariableReadImpl]client.createScanner([CtVariableReadImpl]tableName, [CtTypeAccessImpl]Authorizations.EMPTY)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.log.info([CtLiteralImpl]"Looking for relative paths in {}", [CtVariableReadImpl]tableName);
            [CtInvocationImpl][CtVariableReadImpl]scanner.fetchColumnFamily([CtTypeAccessImpl]MetadataSchema.TabletsSection.DataFileColumnFamily.NAME);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.apache.accumulo.core.data.Key, [CtTypeReferenceImpl]org.apache.accumulo.core.data.Value> entry : [CtVariableReadImpl]scanner) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Key key = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String metaEntry = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]key.getColumnQualifier().toString();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]metaEntry.contains([CtLiteralImpl]":")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// found relative paths so verify the property used to build the absolute paths
                    [CtVariableWriteImpl]hasRelatives = [CtLiteralImpl]true;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]upgradeProperty == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]upgradeProperty.isBlank()) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Missing required property " + [CtInvocationImpl][CtTypeAccessImpl]Property.INSTANCE_VOLUMES_UPGRADE_RELATIVE.getKey());
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path relPath = [CtInvocationImpl]org.apache.accumulo.master.upgrade.Upgrader9to10.resolveRelativePath([CtVariableReadImpl]metaEntry, [CtVariableReadImpl]key);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path absPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]upgradeProperty, [CtVariableReadImpl]relPath);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]fs.exists([CtVariableReadImpl]absPath)) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Tablet file " + [CtVariableReadImpl]relPath) + [CtLiteralImpl]" not found at ") + [CtVariableReadImpl]absPath) + [CtLiteralImpl]" using volume: ") + [CtVariableReadImpl]upgradeProperty);
                    }
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.client.TableNotFoundException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]hasRelatives;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Resolve old-style relative paths, returning Path of everything except volume and base
     */
    private static [CtTypeReferenceImpl]org.apache.hadoop.fs.Path resolveRelativePath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String metadataEntry, [CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.data.Key key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]VolumeManager.FileType.TABLE.getDirectory() + [CtLiteralImpl]"/";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]metadataEntry.startsWith([CtLiteralImpl]"../")) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// resolve style "../2a/t-0003/C0004.rf"
            return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtBinaryOperatorImpl][CtVariableReadImpl]prefix + [CtInvocationImpl][CtVariableReadImpl]metadataEntry.substring([CtLiteralImpl]3));
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// resolve style "/t-0003/C0004.rf"
            [CtTypeReferenceImpl]org.apache.accumulo.core.data.TableId tableId = [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.dataImpl.KeyExtent.tableOfMetadataRow([CtInvocationImpl][CtVariableReadImpl]key.getRow());
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]prefix + [CtInvocationImpl][CtVariableReadImpl]tableId.canonical()) + [CtVariableReadImpl]metadataEntry);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Resolve old relative delete markers of the form /tableId/tabletDir/[file] to
     * UpgradeVolume/tables/tableId/tabletDir/[file]
     */
    static [CtTypeReferenceImpl]org.apache.hadoop.fs.Path resolveRelativeDelete([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.server.fs.VolumeManager fs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String oldDelete, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String upgradeProperty) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path pathNoVolume = [CtInvocationImpl][CtTypeAccessImpl]VolumeManager.FileType.TABLE.removeVolume([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]oldDelete));
        [CtIfImpl][CtCommentImpl]// abs path won't be null so return
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]pathNoVolume != [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]pathNoVolume;

        [CtLocalVariableImpl][CtCommentImpl]// use Path to check the format is correct
        [CtTypeReferenceImpl]org.apache.hadoop.fs.Path pathToCheck = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]oldDelete);
        [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkState([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]oldDelete.startsWith([CtLiteralImpl]"/") && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]pathToCheck.depth() == [CtLiteralImpl]2) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]pathToCheck.depth() == [CtLiteralImpl]3)), [CtLiteralImpl]"Unrecognized relative delete marker {}", [CtVariableReadImpl]oldDelete);
        [CtIfImpl][CtCommentImpl]// found relative paths so verify the property used to build the absolute paths
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]upgradeProperty == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]upgradeProperty.isBlank()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Missing required property " + [CtInvocationImpl][CtTypeAccessImpl]Property.INSTANCE_VOLUMES_UPGRADE_RELATIVE.getKey());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path absPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]upgradeProperty, [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]VolumeManager.FileType.TABLE.getDirectory() + [CtVariableReadImpl]oldDelete);
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]fs.exists([CtVariableReadImpl]absPath)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"File not found at " + [CtVariableReadImpl]absPath) + [CtLiteralImpl]" for Delete marker ") + [CtVariableReadImpl]oldDelete) + [CtLiteralImpl]" using volume: ") + [CtVariableReadImpl]upgradeProperty);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]absPath;
    }
}
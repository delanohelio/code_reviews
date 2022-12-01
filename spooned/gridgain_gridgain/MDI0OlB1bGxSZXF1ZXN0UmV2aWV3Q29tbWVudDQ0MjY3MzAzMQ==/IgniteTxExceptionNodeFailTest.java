[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 GridGain Systems, Inc. and Contributors.

Licensed under the GridGain Community Edition License (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.gridgain.com/products/software/community-edition/gridgain-community-edition-license

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.ignite.internal.processors.cache.distributed.near;
[CtUnresolvedImport]import org.apache.ignite.cache.CacheAtomicityMode;
[CtUnresolvedImport]import org.apache.ignite.internal.IgnitionEx;
[CtUnresolvedImport]import org.apache.ignite.spi.IgniteSpiException;
[CtImportImpl]import org.apache.commons.io.FileUtils;
[CtUnresolvedImport]import org.apache.ignite.cache.affinity.Affinity;
[CtUnresolvedImport]import org.apache.ignite.transactions.Transaction;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.internal.U;
[CtUnresolvedImport]import org.apache.ignite.internal.IgniteEx;
[CtUnresolvedImport]import org.apache.ignite.transactions.TransactionHeuristicException;
[CtUnresolvedImport]import org.apache.ignite.cluster.ClusterNode;
[CtUnresolvedImport]import org.apache.ignite.cache.CacheWriteSynchronizationMode;
[CtUnresolvedImport]import org.apache.ignite.configuration.DataStorageConfiguration;
[CtUnresolvedImport]import org.apache.ignite.lang.IgniteInClosure;
[CtUnresolvedImport]import org.apache.ignite.testframework.junits.common.GridCommonAbstractTest;
[CtUnresolvedImport]import org.apache.ignite.IgniteException;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.apache.ignite.internal.managers.communication.GridIoMessage;
[CtUnresolvedImport]import static org.apache.ignite.cache.CacheWriteSynchronizationMode.FULL_SYNC;
[CtUnresolvedImport]import org.apache.ignite.testframework.GridTestUtils;
[CtUnresolvedImport]import org.apache.ignite.configuration.IgniteConfiguration;
[CtUnresolvedImport]import org.apache.ignite.configuration.CacheConfiguration;
[CtUnresolvedImport]import org.apache.ignite.plugin.extensions.communication.Message;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.apache.ignite.internal.TestRecordingCommunicationSpi;
[CtUnresolvedImport]import org.apache.ignite.configuration.DataRegionConfiguration;
[CtUnresolvedImport]import static org.apache.ignite.cache.CacheWriteSynchronizationMode.PRIMARY_SYNC;
[CtClassImpl][CtJavaDocImpl]/**
 * Tests check a result of commit when a node fail before
 * send {@link GridNearTxFinishResponse} to transaction coodinator
 */
public class IgniteTxExceptionNodeFailTest extends [CtTypeReferenceImpl]org.apache.ignite.testframework.junits.common.GridCommonAbstractTest {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Spi for node0
     */
    private [CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.near.IgniteTxExceptionNodeFailTest.SpecialSpi spi0;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Spi for node1
     */
    private [CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.near.IgniteTxExceptionNodeFailTest.SpecialSpi spi1;

    [CtFieldImpl][CtJavaDocImpl]/**
     * syncMode
     */
    private static [CtTypeReferenceImpl]org.apache.ignite.cache.CacheWriteSynchronizationMode syncMode;

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]org.apache.ignite.configuration.IgniteConfiguration getConfiguration([CtParameterImpl][CtTypeReferenceImpl]java.lang.String igniteInstanceName) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.configuration.IgniteConfiguration cfg = [CtInvocationImpl][CtSuperAccessImpl]super.getConfiguration([CtVariableReadImpl]igniteInstanceName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.configuration.DataStorageConfiguration dsConfig = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.configuration.DataStorageConfiguration().setDefaultDataRegionConfiguration([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.configuration.DataRegionConfiguration().setPersistenceEnabled([CtLiteralImpl]true));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.near.IgniteTxExceptionNodeFailTest.SpecialSpi spi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.near.IgniteTxExceptionNodeFailTest.SpecialSpi();
        [CtInvocationImpl][CtVariableReadImpl]cfg.setCommunicationSpi([CtVariableReadImpl]spi);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]igniteInstanceName.contains([CtLiteralImpl]"0"))[CtBlockImpl]
            [CtAssignmentImpl][CtFieldWriteImpl]spi0 = [CtVariableReadImpl]spi;

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]igniteInstanceName.contains([CtLiteralImpl]"1"))[CtBlockImpl]
            [CtAssignmentImpl][CtFieldWriteImpl]spi1 = [CtVariableReadImpl]spi;

        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cfg.setDataStorageConfiguration([CtVariableReadImpl]dsConfig).setCacheConfiguration([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.configuration.CacheConfiguration([CtLiteralImpl]"cache").setAtomicityMode([CtTypeAccessImpl]CacheAtomicityMode.TRANSACTIONAL).setWriteSynchronizationMode([CtFieldReadImpl]org.apache.ignite.internal.processors.cache.distributed.near.IgniteTxExceptionNodeFailTest.syncMode).setBackups([CtLiteralImpl]0));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void beforeTest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.beforeTest();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.FileUtils.deleteDirectory([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.defaultWorkDirectory()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void afterTest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.afterTest();
        [CtInvocationImpl]stopAllGrids();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Test with {@link CacheWriteSynchronizationMode#PRIMARY_SYNC}
     *
     * @throws Exception
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNodeFailWithPrimarySync() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]testNodeFail([CtTypeAccessImpl]CacheWriteSynchronizationMode.PRIMARY_SYNC);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Test with {@link CacheWriteSynchronizationMode#FULL_SYNC}
     *
     * @throws Exception
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNodeFailWithFullSync() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]testNodeFail([CtTypeAccessImpl]CacheWriteSynchronizationMode.FULL_SYNC);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * <ul>
     * <li>Start 2 nodes with transactional cache without backups
     * <li>Start transaction:
     *  <ul>
     *  <li>put a key to a partition on transaction coordinator
     *  <li>put a key to a partition on other node
     *  <li>try to commit the transaction
     *  </ul>
     * <li>Stop other node when it try to send GridNearTxFinishResponse
     * <li>Check that {@link Transaction#commit()} throw {@link TransactionHeuristicException}
     * </ul>
     *
     * @param testSyncMode
     * @throws Exception
     */
    private [CtTypeReferenceImpl]void testNodeFail([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.cache.CacheWriteSynchronizationMode testSyncMode) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]org.apache.ignite.internal.processors.cache.distributed.near.IgniteTxExceptionNodeFailTest.syncMode = [CtVariableReadImpl]testSyncMode;
        [CtInvocationImpl]startGrids([CtLiteralImpl]2);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]grid([CtLiteralImpl]0).cluster().active([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx grid0 = [CtInvocationImpl]grid([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx grid1 = [CtInvocationImpl]grid([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int key0 = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int key1 = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.cache.affinity.Affinity<[CtTypeReferenceImpl]java.lang.Object> aff = [CtInvocationImpl][CtVariableReadImpl]grid1.affinity([CtLiteralImpl]"cache");
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]1000; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]grid0.equals([CtInvocationImpl]grid([CtInvocationImpl][CtVariableReadImpl]aff.mapKeyToNode([CtVariableReadImpl]i)))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]key0 = [CtVariableReadImpl]i;
                [CtBreakImpl]break;
            }
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtVariableReadImpl]key0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]1000; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]grid1.equals([CtInvocationImpl]grid([CtInvocationImpl][CtVariableReadImpl]aff.mapKeyToNode([CtVariableReadImpl]i))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aff.mapKeyToNode([CtVariableReadImpl]key1).equals([CtInvocationImpl][CtVariableReadImpl]aff.mapKeyToNode([CtVariableReadImpl]i)))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]key1 = [CtVariableReadImpl]i;
                [CtBreakImpl]break;
            }
        }
        [CtAssertImpl]assert [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aff.mapKeyToNode([CtVariableReadImpl]key0).equals([CtInvocationImpl][CtVariableReadImpl]aff.mapKeyToNode([CtVariableReadImpl]key1));
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.transactions.Transaction tx = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]grid1.transactions().txStart()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]grid1.cache([CtLiteralImpl]"cache").put([CtVariableReadImpl]key0, [CtLiteralImpl]100);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]grid1.cache([CtLiteralImpl]"cache").put([CtVariableReadImpl]key1, [CtLiteralImpl]200);
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.testframework.GridTestUtils.assertThrows([CtLiteralImpl]null, [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]tx::commit, [CtFieldReadImpl]org.apache.ignite.transactions.TransactionHeuristicException.class, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Primary node [nodeId=" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]grid0.localNode().id()) + [CtLiteralImpl]", consistentId=") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]grid0.localNode().consistentId()) + [CtLiteralImpl]"] has left the grid and there are no backup nodes");
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * SPI wich block communication messages and stop a node.
     */
    private static class SpecialSpi extends [CtTypeReferenceImpl]org.apache.ignite.internal.TestRecordingCommunicationSpi {
        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void sendMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode node, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.plugin.extensions.communication.Message msg, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.lang.IgniteInClosure<[CtTypeReferenceImpl]org.apache.ignite.IgniteException> ackC) throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.managers.communication.GridIoMessage) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.plugin.extensions.communication.Message message = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.managers.communication.GridIoMessage) (msg)).message();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.near.GridNearTxFinishResponse) [CtBlockImpl]{
                    [CtInvocationImpl]blockMessages([CtLambdaImpl]([CtParameterImpl] node1,[CtParameterImpl] msg1) -> [CtLiteralImpl]true);
                    [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]ignite.log().info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Stopping node: [" + [CtInvocationImpl][CtFieldReadImpl]ignite.name()) + [CtLiteralImpl]"]");
                            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.IgnitionEx.stop([CtInvocationImpl][CtFieldReadImpl]ignite.name(), [CtLiteralImpl]true, [CtLiteralImpl]null, [CtLiteralImpl]true);
                        }
                    }, [CtLiteralImpl]"node-stopper").start();
                }
            }
            [CtInvocationImpl][CtSuperAccessImpl]super.sendMessage([CtVariableReadImpl]node, [CtVariableReadImpl]msg, [CtVariableReadImpl]ackC);
        }
    }
}
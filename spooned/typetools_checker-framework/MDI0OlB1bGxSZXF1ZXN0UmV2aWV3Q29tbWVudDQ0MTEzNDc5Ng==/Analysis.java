[CompilationUnitImpl][CtPackageDeclarationImpl]package org.checkerframework.dataflow.analysis;
[CtImportImpl]import java.util.IdentityHashMap;
[CtUnresolvedImport]import org.checkerframework.dataflow.cfg.ControlFlowGraph;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.checkerframework.dataflow.cfg.block.Block;
[CtUnresolvedImport]import org.checkerframework.checker.nullness.qual.Nullable;
[CtUnresolvedImport]import org.checkerframework.dataflow.cfg.node.Node;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * This interface defines a dataflow analysis, given a control flow graph and a transfer function. A
 * dataflow analysis has a direction, either forward or backward. The direction of corresponding
 * transfer function is consistent with the analysis, i.e. a forward analysis has a forward transfer
 * function, and a backward analysis has a backward transfer function.
 *
 * @param <V>
 * 		the abstract value type to be tracked by the analysis
 * @param <S>
 * 		the store type used in the analysis
 * @param <T>
 * 		the transfer function type that is used to approximated runtime behavior
 */
public interface Analysis<[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.AbstractValue<[CtTypeParameterReferenceImpl]V>, [CtTypeParameterImpl]S extends [CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.Store<[CtTypeParameterReferenceImpl]S>, [CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.TransferFunction<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]S>> {
    [CtEnumImpl][CtJavaDocImpl]/**
     * The direction of an analysis instance.
     */
    enum Direction {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The forward direction.
         */
        FORWARD,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The backward direction.
         */
        BACKWARD;}

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the direction of this analysis.
     *
     * @return the direction of this analysis
     */
    [CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.Analysis.Direction getDirection();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Is the analysis currently running?
     *
     * @return true if the analysis is running currently, else false
     */
    [CtTypeReferenceImpl]boolean isRunning();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Perform the actual analysis.
     *
     * @param cfg
     * 		the control flow graph
     */
    [CtTypeReferenceImpl]void performAnalysis([CtParameterImpl][CtTypeReferenceImpl]org.checkerframework.dataflow.cfg.ControlFlowGraph cfg);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Perform the actual analysis on one block.
     *
     * @param b
     * 		the block to analyze
     */
    [CtTypeReferenceImpl]void performAnalysisBlock([CtParameterImpl][CtTypeReferenceImpl]org.checkerframework.dataflow.cfg.block.Block b);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the analysis again within the block of {@code node} and returns the store at the
     * location of {@code node}. If {@code before} is true, then the store immediately before the
     * {@link Node} {@code node} is returned. Otherwise, the store after {@code node} is returned.
     * If {@code analysisCaches} is not null, this method uses a cache. {@code analysisCaches} is a
     * map of a block of node to the cached analysis result. If the cache for {@code transferInput}
     * is not in {@code analysisCaches}, this method create new cache and store it in {@code analysisCaches}. The cache is a map of a node to the analysis result of the node.
     *
     * @param node
     * 		the node to analyze
     * @param before
     * 		the boolean value to indicate which store to return
     * @param transferInput
     * 		the transfer input of the block of this node
     * @param nodeValues
     * 		abstract values of nodes
     * @param analysisCaches
     * 		caches of analysis results
     * @return the store at the location of node after running the analysis
     */
    [CtTypeParameterReferenceImpl]S runAnalysisFor([CtParameterImpl][CtTypeReferenceImpl]org.checkerframework.dataflow.cfg.node.Node node, [CtParameterImpl][CtTypeReferenceImpl]boolean before, [CtParameterImpl][CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.TransferInput<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]S> transferInput, [CtParameterImpl][CtTypeReferenceImpl]java.util.IdentityHashMap<[CtTypeReferenceImpl]org.checkerframework.dataflow.cfg.node.Node, [CtTypeParameterReferenceImpl]V> nodeValues, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.TransferInput<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]S>, [CtTypeReferenceImpl]java.util.IdentityHashMap<[CtTypeReferenceImpl]org.checkerframework.dataflow.cfg.node.Node, [CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.TransferResult<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]S>>> analysisCaches);

    [CtMethodImpl][CtJavaDocImpl]/**
     * The result of running the analysis. This is only available once the analysis finished
     * running.
     *
     * @return the result of running the analysis
     */
    [CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.AnalysisResult<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]S> getResult();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the transfer function of this analysis.
     *
     * @return the transfer function of this analysis
     */
    [CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
    [CtTypeParameterReferenceImpl]T getTransferFunction();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the transfer input of a given {@link Block} b.
     *
     * @param b
     * 		a given Block
     * @return the transfer input of this Block
     */
    [CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
    [CtTypeReferenceImpl]org.checkerframework.dataflow.analysis.TransferInput<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]S> getInput([CtParameterImpl][CtTypeReferenceImpl]org.checkerframework.dataflow.cfg.block.Block b);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the abstract value for {@link Node} {@code n}, or {@code null} if no information is
     * available. Note that if the analysis has not finished yet, this value might not represent the
     * final value for this node.
     *
     * @param n
     * 		n a node
     * @return the abstract value for node {@code n}, or {@code null} if no information is available
     */
    [CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
    [CtTypeParameterReferenceImpl]V getValue([CtParameterImpl][CtTypeReferenceImpl]org.checkerframework.dataflow.cfg.node.Node n);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the regular exit store, or {@code null}, if there is no such store (because the
     * method cannot exit through the regular exit block).
     *
     * @return the regular exit store, or {@code null}, if there is no such store (because the
    method cannot exit through the regular exit block)
     */
    [CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
    [CtTypeParameterReferenceImpl]S getRegularExitStore();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the exceptional exit store.
     *
     * @return the exceptional exit store
     */
    [CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
    [CtTypeParameterReferenceImpl]S getExceptionalExitStore();
}
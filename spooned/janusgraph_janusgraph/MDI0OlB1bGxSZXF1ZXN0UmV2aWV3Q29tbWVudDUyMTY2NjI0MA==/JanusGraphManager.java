[CompilationUnitImpl][CtPackageDeclarationImpl]package org.janusgraph.graphdb.management;
[CtImportImpl]import java.util.concurrent.ScheduledExecutorService;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.janusgraph.core.ConfiguredGraphFactory;
[CtUnresolvedImport]import org.janusgraph.core.JanusGraphFactory;
[CtUnresolvedImport]import org.janusgraph.graphdb.database.StandardJanusGraph;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.Settings;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.structure.Graph;
[CtImportImpl]import java.util.concurrent.Executors;
[CtUnresolvedImport]import org.janusgraph.graphdb.management.utils.JanusGraphManagerException;
[CtImportImpl]import javax.script.Bindings;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.GraphManager;
[CtImportImpl]import javax.script.SimpleBindings;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.process.traversal.TraversalSource;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtClassImpl][CtJavaDocImpl]/**
 * This class adheres to the TinkerPop graphManager specifications. It provides a coordinated
 * mechanism by which to instantiate graph references on a given JanusGraph node and a graph
 * reference tracker (or graph cache). Any graph created using the property \"graph.graphname\" and
 * any graph defined at server start, i.e. in the server's YAML file, will go through this
 * JanusGraphManager.
 */
public class JanusGraphManager implements [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.GraphManager {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.janusgraph.graphdb.management.JanusGraphManager.class);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String JANUS_GRAPH_MANAGER_EXPECTED_STATE_MSG = [CtLiteralImpl]"Gremlin Server must be configured to use the JanusGraphManager.";

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph> graphs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.process.traversal.TraversalSource> traversalSources = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Object instantiateGraphLock = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Object();

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor gremlinExecutor = [CtLiteralImpl]null;

    [CtFieldImpl]private static [CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager instance = [CtLiteralImpl]null;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CONFIGURATION_MANAGEMENT_GRAPH_KEY = [CtInvocationImpl][CtFieldReadImpl]org.janusgraph.graphdb.management.ConfigurationManagementGraph.class.getSimpleName();

    [CtConstructorImpl][CtJavaDocImpl]/**
     * This class adheres to the TinkerPop graphManager specifications. It provides a coordinated
     * mechanism by which to instantiate graph references on a given JanusGraph node and a graph
     * reference tracker (or graph cache). Any graph created using the property \"graph.graphname\" and
     * any graph defined at server start, i.e. in the server's YAML file, will go through this
     * JanusGraphManager.
     */
    public JanusGraphManager([CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings settings) [CtBlockImpl]{
        [CtInvocationImpl]initialize();
        [CtInvocationImpl][CtCommentImpl]// Open graphs defined at server start in settings.graphs
        [CtFieldReadImpl][CtVariableReadImpl]settings.graphs.forEach([CtLambdaImpl]([CtParameterImpl] key,[CtParameterImpl] value) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.janusgraph.graphdb.database.StandardJanusGraph graph = [CtInvocationImpl](([CtTypeReferenceImpl]org.janusgraph.graphdb.database.StandardJanusGraph) ([CtTypeAccessImpl]org.janusgraph.core.JanusGraphFactory.open([CtVariableReadImpl]value, [CtVariableReadImpl]key)));
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]key.toLowerCase().equals([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]CONFIGURATION_MANAGEMENT_GRAPH_KEY.toLowerCase())) [CtBlockImpl]{
                [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.graphdb.management.ConfigurationManagementGraph([CtVariableReadImpl]graph);
            }
        });
    }

    [CtMethodImpl]private synchronized [CtTypeReferenceImpl]void initialize() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]org.janusgraph.graphdb.management.JanusGraphManager.instance) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String errMsg = [CtLiteralImpl]"You may not instantiate a JanusGraphManager. The single instance should be handled by Tinkerpop's GremlinServer startup processes.";
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.graphdb.management.utils.JanusGraphManagerException([CtVariableReadImpl]errMsg);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]org.janusgraph.graphdb.management.JanusGraphManager.instance = [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager getInstance() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.janusgraph.graphdb.management.JanusGraphManager.instance;
    }

    [CtMethodImpl][CtCommentImpl]// To be used for testing purposes only, so we can run tests in parallel
    public static [CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager getInstance([CtParameterImpl][CtTypeReferenceImpl]boolean forceCreate) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]forceCreate) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings());
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.janusgraph.graphdb.management.JanusGraphManager.instance;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void configureGremlinExecutor([CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor gremlinExecutor) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.gremlinExecutor = [CtVariableReadImpl]gremlinExecutor;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.concurrent.ScheduledExecutorService bindExecutor = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.Executors.newScheduledThreadPool([CtLiteralImpl]1);
        [CtInvocationImpl][CtCommentImpl]// Dynamically created graphs created with the ConfiguredGraphFactory are
        [CtCommentImpl]// bound across all nodes in the cluster and in the face of server restarts
        [CtVariableReadImpl]bindExecutor.scheduleWithFixedDelay([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager.GremlinExecutorGraphBinder([CtThisAccessImpl]this, [CtFieldReadImpl][CtThisAccessImpl]this.gremlinExecutor), [CtLiteralImpl]0, [CtLiteralImpl]20L, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS);
    }

    [CtClassImpl]private class GremlinExecutorGraphBinder implements [CtTypeReferenceImpl]java.lang.Runnable {
        [CtFieldImpl]final [CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager graphManager;

        [CtFieldImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor gremlinExecutor;

        [CtConstructorImpl]public GremlinExecutorGraphBinder([CtParameterImpl][CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager graphManager, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor gremlinExecutor) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.graphManager = [CtVariableReadImpl]graphManager;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.gremlinExecutor = [CtVariableReadImpl]gremlinExecutor;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.janusgraph.core.ConfiguredGraphFactory.getGraphNames().forEach([CtLambdaImpl]([CtParameterImpl] it) -> [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph graph = [CtInvocationImpl][CtTypeAccessImpl]org.janusgraph.core.ConfiguredGraphFactory.open([CtVariableReadImpl]it);
                    [CtInvocationImpl]updateTraversalSource([CtVariableReadImpl]it, [CtVariableReadImpl]graph, [CtFieldReadImpl][CtThisAccessImpl]this.gremlinExecutor, [CtFieldReadImpl][CtThisAccessImpl]this.graphManager);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// cannot open graph, do nothing
                    [CtTypeAccessImpl]org.janusgraph.graphdb.management.log.error([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to open graph %s with the following error:\n %s.\n" + [CtLiteralImpl]"Thus, it and its traversal will not be bound on this server.", [CtVariableReadImpl]it, [CtInvocationImpl][CtTypeAccessImpl]org.janusgraph.graphdb.management.e.toString()));
                }
            });
        }
    }

    [CtMethodImpl][CtCommentImpl]// To be used for testing purposes
    protected static [CtTypeReferenceImpl]void shutdownJanusGraphManager() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]org.janusgraph.graphdb.management.JanusGraphManager.instance = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> getGraphNames() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]graphs.keySet();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph getGraph([CtParameterImpl][CtTypeReferenceImpl]java.lang.String gName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]graphs.get([CtVariableReadImpl]gName);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void putGraph([CtParameterImpl][CtTypeReferenceImpl]java.lang.String gName, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph g) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]graphs.put([CtVariableReadImpl]gName, [CtVariableReadImpl]g);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> getTraversalSourceNames() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]traversalSources.keySet();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.process.traversal.TraversalSource getTraversalSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tsName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]traversalSources.get([CtVariableReadImpl]tsName);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void putTraversalSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tsName, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.process.traversal.TraversalSource ts) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]traversalSources.put([CtVariableReadImpl]tsName, [CtVariableReadImpl]ts);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.process.traversal.TraversalSource removeTraversalSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tsName) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tsName == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtInvocationImpl][CtCommentImpl]// Remove the traversal source from the script engine bindings so that closed/dropped graph instances do not leak
        removeBinding([CtVariableReadImpl]tsName);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]traversalSources.remove([CtVariableReadImpl]tsName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the {@link Graph} and {@link TraversalSource} list as a set of bindings.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]javax.script.Bindings getAsBindings() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.script.Bindings bindings = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.script.SimpleBindings();
        [CtInvocationImpl][CtFieldReadImpl]graphs.forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]bindings::put);
        [CtInvocationImpl][CtFieldReadImpl]traversalSources.forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]bindings::put);
        [CtReturnImpl]return [CtVariableReadImpl]bindings;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void rollbackAll() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]graphs.forEach([CtLambdaImpl]([CtParameterImpl] key,[CtParameterImpl] graph) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graph.tx().isOpen()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graph.tx().rollback();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void rollback([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> graphSourceNamesToCloseTxOn) [CtBlockImpl]{
        [CtInvocationImpl]commitOrRollback([CtVariableReadImpl]graphSourceNamesToCloseTxOn, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void commitAll() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]graphs.forEach([CtLambdaImpl]([CtParameterImpl] key,[CtParameterImpl] graph) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graph.tx().isOpen())[CtBlockImpl]
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graph.tx().commit();

        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void commit([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> graphSourceNamesToCloseTxOn) [CtBlockImpl]{
        [CtInvocationImpl]commitOrRollback([CtVariableReadImpl]graphSourceNamesToCloseTxOn, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void commitOrRollback([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> graphSourceNamesToCloseTxOn, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean commit) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]graphSourceNamesToCloseTxOn.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String e) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph graph = [CtInvocationImpl]getGraph([CtVariableReadImpl]e);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]graph) [CtBlockImpl]{
                [CtInvocationImpl]closeTx([CtVariableReadImpl]graph, [CtVariableReadImpl]commit);
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeTx([CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph graph, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean commit) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graph.tx().isOpen()) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]commit) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graph.tx().commit();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]graph.tx().rollback();
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph openGraph([CtParameterImpl][CtTypeReferenceImpl]java.lang.String gName, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph> thunk) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph graph = [CtInvocationImpl][CtFieldReadImpl]graphs.get([CtVariableReadImpl]gName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]graph != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.janusgraph.graphdb.database.StandardJanusGraph) (graph)).isClosed())) [CtBlockImpl]{
            [CtInvocationImpl]updateTraversalSource([CtVariableReadImpl]gName, [CtVariableReadImpl]graph);
            [CtReturnImpl]return [CtVariableReadImpl]graph;
        } else [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]instantiateGraphLock) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]graph = [CtInvocationImpl][CtFieldReadImpl]graphs.get([CtVariableReadImpl]gName);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]graph == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.janusgraph.graphdb.database.StandardJanusGraph) (graph)).isClosed()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]graph = [CtInvocationImpl][CtVariableReadImpl]thunk.apply([CtVariableReadImpl]gName);
                    [CtInvocationImpl][CtFieldReadImpl]graphs.put([CtVariableReadImpl]gName, [CtVariableReadImpl]graph);
                }
            }
            [CtInvocationImpl]updateTraversalSource([CtVariableReadImpl]gName, [CtVariableReadImpl]graph);
            [CtReturnImpl]return [CtVariableReadImpl]graph;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph removeGraph([CtParameterImpl][CtTypeReferenceImpl]java.lang.String gName) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]gName == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtInvocationImpl][CtCommentImpl]// Remove the graph from the script engine bindings so that closed/dropped graph instances do not leak
        removeBinding([CtVariableReadImpl]gName);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]graphs.remove([CtVariableReadImpl]gName);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateTraversalSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String graphName, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph graph) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]gremlinExecutor) [CtBlockImpl]{
            [CtInvocationImpl]updateTraversalSource([CtVariableReadImpl]graphName, [CtVariableReadImpl]graph, [CtFieldReadImpl]gremlinExecutor, [CtThisAccessImpl]this);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateTraversalSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String graphName, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph graph, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor gremlinExecutor, [CtParameterImpl][CtTypeReferenceImpl]org.janusgraph.graphdb.management.JanusGraphManager graphManager) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]gremlinExecutor.getScriptEngineManager().put([CtVariableReadImpl]graphName, [CtVariableReadImpl]graph);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String traversalName = [CtBinaryOperatorImpl][CtVariableReadImpl]graphName + [CtLiteralImpl]"_traversal";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.process.traversal.TraversalSource traversalSource = [CtInvocationImpl][CtVariableReadImpl]graph.traversal();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]gremlinExecutor.getScriptEngineManager().put([CtVariableReadImpl]traversalName, [CtVariableReadImpl]traversalSource);
        [CtInvocationImpl][CtVariableReadImpl]graphManager.putTraversalSource([CtVariableReadImpl]traversalName, [CtVariableReadImpl]traversalSource);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void removeBinding([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]gremlinExecutor) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.script.Bindings bindings = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]gremlinExecutor.getScriptEngineManager().getBindings();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]bindings.get([CtVariableReadImpl]key) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]gremlinExecutor.getScriptEngineManager().getBindings().remove([CtVariableReadImpl]key);
            }
        }
    }
}
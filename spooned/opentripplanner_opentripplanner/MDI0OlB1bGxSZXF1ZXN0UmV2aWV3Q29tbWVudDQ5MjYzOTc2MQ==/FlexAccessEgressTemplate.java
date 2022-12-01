[CompilationUnitImpl][CtPackageDeclarationImpl]package org.opentripplanner.ext.flex.template;
[CtUnresolvedImport]import org.opentripplanner.routing.graphfinder.StopAtDistance;
[CtUnresolvedImport]import org.opentripplanner.ext.flex.FlexAccessEgress;
[CtUnresolvedImport]import org.opentripplanner.ext.flex.distancecalculator.DistanceCalculator;
[CtUnresolvedImport]import org.opentripplanner.routing.graph.Vertex;
[CtUnresolvedImport]import org.opentripplanner.model.Stop;
[CtUnresolvedImport]import org.opentripplanner.routing.vertextype.TransitStopVertex;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.opentripplanner.model.StopLocation;
[CtUnresolvedImport]import org.opentripplanner.model.calendar.ServiceDate;
[CtUnresolvedImport]import org.opentripplanner.ext.flex.FlexTripEdge;
[CtUnresolvedImport]import org.opentripplanner.routing.graph.Graph;
[CtUnresolvedImport]import org.opentripplanner.ext.flex.trip.FlexTrip;
[CtUnresolvedImport]import org.opentripplanner.model.SimpleTransfer;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.opentripplanner.routing.core.State;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.opentripplanner.routing.graph.Edge;
[CtClassImpl]public abstract class FlexAccessEgressTemplate {
    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.opentripplanner.routing.graphfinder.StopAtDistance accessEgress;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.opentripplanner.ext.flex.trip.FlexTrip trip;

    [CtFieldImpl]public final [CtTypeReferenceImpl]int fromStopIndex;

    [CtFieldImpl]public final [CtTypeReferenceImpl]int toStopIndex;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.opentripplanner.model.StopLocation transferStop;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]int differenceFromStartOfTime;

    [CtFieldImpl]public final [CtTypeReferenceImpl]org.opentripplanner.model.calendar.ServiceDate serviceDate;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.opentripplanner.ext.flex.distancecalculator.DistanceCalculator calculator;

    [CtConstructorImpl]FlexAccessEgressTemplate([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graphfinder.StopAtDistance accessEgress, [CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.ext.flex.trip.FlexTrip trip, [CtParameterImpl][CtTypeReferenceImpl]int fromStopIndex, [CtParameterImpl][CtTypeReferenceImpl]int toStopIndex, [CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.model.StopLocation transferStop, [CtParameterImpl][CtTypeReferenceImpl]int differenceFromStartOfTime, [CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.model.calendar.ServiceDate serviceDate, [CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.ext.flex.distancecalculator.DistanceCalculator calculator) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.accessEgress = [CtVariableReadImpl]accessEgress;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.trip = [CtVariableReadImpl]trip;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fromStopIndex = [CtVariableReadImpl]fromStopIndex;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.toStopIndex = [CtVariableReadImpl]toStopIndex;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transferStop = [CtVariableReadImpl]transferStop;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.differenceFromStartOfTime = [CtVariableReadImpl]differenceFromStartOfTime;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceDate = [CtVariableReadImpl]serviceDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.calculator = [CtVariableReadImpl]calculator;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.opentripplanner.model.StopLocation getTransferStop() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]transferStop;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.opentripplanner.ext.flex.trip.FlexTrip getFlexTrip() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]trip;
    }

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opentripplanner.routing.graph.Edge> getTransferEdges([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.model.SimpleTransfer simpleTransfer);

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]org.opentripplanner.model.StopLocation getFinalStop([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.model.SimpleTransfer simpleTransfer);

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.opentripplanner.model.SimpleTransfer> getTransfersFromTransferStop([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graph.Graph graph);

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]org.opentripplanner.routing.graph.Vertex getFlexVertex([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graph.Edge edge);

    [CtMethodImpl]protected abstract [CtArrayTypeReferenceImpl]int[] getFlexTimes([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.ext.flex.FlexTripEdge flexEdge, [CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.core.State state);

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]org.opentripplanner.ext.flex.FlexTripEdge getFlexEdge([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graph.Vertex flexFromVertex, [CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.model.StopLocation transferStop);

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]boolean isRouteable([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graph.Vertex flexVertex);

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.opentripplanner.ext.flex.FlexAccessEgress> getFlexAccessEgressStream([CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graph.Graph graph, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.opentripplanner.model.Stop, [CtTypeReferenceImpl]java.lang.Integer> indexByStop) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]transferStop instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.opentripplanner.model.Stop) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.opentripplanner.routing.vertextype.TransitStopVertex flexVertex = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]graph.index.getStopVertexForStop().get([CtFieldReadImpl]transferStop);
            [CtIfImpl]if ([CtInvocationImpl]isRouteable([CtVariableReadImpl]flexVertex)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.of([CtInvocationImpl]getFlexAccessEgress([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>(), [CtVariableReadImpl]flexVertex, [CtInvocationImpl][CtVariableReadImpl]indexByStop.get([CtFieldReadImpl]transferStop)));
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.empty();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getTransfersFromTransferStop([CtVariableReadImpl]graph).stream().filter([CtLambdaImpl]([CtParameterImpl] simpleTransfer) -> [CtBinaryOperatorImpl][CtInvocationImpl]getFinalStop([CtVariableReadImpl]simpleTransfer) instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.opentripplanner.model.Stop).filter([CtLambdaImpl]([CtParameterImpl] simpleTransfer) -> [CtInvocationImpl]isRouteable([CtInvocationImpl]getFlexVertex([CtInvocationImpl][CtInvocationImpl]getTransferEdges([CtVariableReadImpl]simpleTransfer).get([CtLiteralImpl]0)))).map([CtLambdaImpl]([CtParameterImpl] simpleTransfer) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]org.opentripplanner.routing.graph.Edge> edges = [CtInvocationImpl]getTransferEdges([CtVariableReadImpl]simpleTransfer);
                [CtReturnImpl]return [CtInvocationImpl]getFlexAccessEgress([CtVariableReadImpl]edges, [CtInvocationImpl]getFlexVertex([CtInvocationImpl][CtVariableReadImpl]edges.get([CtLiteralImpl]0)), [CtInvocationImpl][CtVariableReadImpl]indexByStop.get([CtInvocationImpl]getFinalStop([CtVariableReadImpl]simpleTransfer)));
            });
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.opentripplanner.ext.flex.FlexAccessEgress getFlexAccessEgress([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opentripplanner.routing.graph.Edge> transferEdges, [CtParameterImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graph.Vertex flexVertex, [CtParameterImpl][CtTypeReferenceImpl]int stopIndex) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opentripplanner.ext.flex.FlexTripEdge flexEdge = [CtInvocationImpl]getFlexEdge([CtVariableReadImpl]flexVertex, [CtFieldReadImpl]transferStop);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opentripplanner.routing.core.State state = [CtInvocationImpl][CtVariableReadImpl]flexEdge.traverse([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]accessEgress.state);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.opentripplanner.routing.graph.Edge e : [CtVariableReadImpl]transferEdges) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]state = [CtInvocationImpl][CtVariableReadImpl]e.traverse([CtVariableReadImpl]state);
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] times = [CtInvocationImpl]getFlexTimes([CtVariableReadImpl]flexEdge, [CtVariableReadImpl]state);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opentripplanner.ext.flex.FlexAccessEgress([CtVariableReadImpl]stopIndex, [CtArrayReadImpl][CtVariableReadImpl]times[[CtLiteralImpl]0], [CtArrayReadImpl][CtVariableReadImpl]times[[CtLiteralImpl]1], [CtArrayReadImpl][CtVariableReadImpl]times[[CtLiteralImpl]2], [CtFieldReadImpl]fromStopIndex, [CtFieldReadImpl]toStopIndex, [CtFieldReadImpl]differenceFromStartOfTime, [CtFieldReadImpl]trip, [CtVariableReadImpl]state, [CtInvocationImpl][CtVariableReadImpl]transferEdges.isEmpty());
    }
}
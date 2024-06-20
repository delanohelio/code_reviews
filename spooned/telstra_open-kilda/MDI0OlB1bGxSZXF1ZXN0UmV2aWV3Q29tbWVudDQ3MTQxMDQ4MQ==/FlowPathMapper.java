[CompilationUnitImpl][CtCommentImpl]/* Copyright 2018 Telstra Open Source

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
[CtPackageDeclarationImpl]package org.openkilda.wfm.share.mappers;
[CtUnresolvedImport]import org.openkilda.model.FlowEndpoint;
[CtUnresolvedImport]import org.openkilda.model.PathSegment;
[CtUnresolvedImport]import org.openkilda.model.FlowPath;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.openkilda.messaging.info.event.PathNode;
[CtUnresolvedImport]import org.openkilda.messaging.payload.flow.PathNodePayload;
[CtUnresolvedImport]import org.openkilda.messaging.info.event.PathInfoData;
[CtUnresolvedImport]import org.openkilda.model.Flow;
[CtUnresolvedImport]import org.mapstruct.Mapper;
[CtImportImpl]import java.util.Iterator;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.mapstruct.factory.Mappers;
[CtUnresolvedImport]import org.openkilda.adapter.FlowSideAdapter;
[CtClassImpl][CtJavaDocImpl]/**
 * Convert {@link FlowPath} to {@link PathInfoData} and back.
 */
[CtAnnotationImpl]@org.mapstruct.Mapper
public abstract class FlowPathMapper {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]org.openkilda.wfm.share.mappers.FlowPathMapper INSTANCE = [CtInvocationImpl][CtTypeAccessImpl]org.mapstruct.factory.Mappers.getMapper([CtFieldReadImpl]org.openkilda.wfm.share.mappers.FlowPathMapper.class);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Convert {@link FlowPath} to {@link PathInfoData}.
     */
    public [CtTypeReferenceImpl]org.openkilda.messaging.info.event.PathInfoData map([CtParameterImpl][CtTypeReferenceImpl]org.openkilda.model.FlowPath path) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.messaging.info.event.PathInfoData result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.info.event.PathInfoData();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]path != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.setLatency([CtInvocationImpl][CtVariableReadImpl]path.getLatency());
            [CtLocalVariableImpl][CtTypeReferenceImpl]int seqId = [CtLiteralImpl]0;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.messaging.info.event.PathNode> nodes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.PathSegment pathSegment : [CtInvocationImpl][CtVariableReadImpl]path.getSegments()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]nodes.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.info.event.PathNode([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pathSegment.getSrcSwitch().getSwitchId(), [CtInvocationImpl][CtVariableReadImpl]pathSegment.getSrcPort(), [CtUnaryOperatorImpl][CtVariableWriteImpl]seqId++, [CtInvocationImpl][CtVariableReadImpl]pathSegment.getLatency()));
                [CtInvocationImpl][CtVariableReadImpl]nodes.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.info.event.PathNode([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pathSegment.getDestSwitch().getSwitchId(), [CtInvocationImpl][CtVariableReadImpl]pathSegment.getDestPort(), [CtUnaryOperatorImpl][CtVariableWriteImpl]seqId++));
            }
            [CtInvocationImpl][CtVariableReadImpl]result.setPath([CtVariableReadImpl]nodes);
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Convert {@link FlowPath} to {@link PathNodePayload}.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload> mapToPathNodes([CtParameterImpl][CtTypeReferenceImpl]org.openkilda.model.FlowPath flowPath) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload> resultList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.Flow flow = [CtInvocationImpl][CtVariableReadImpl]flowPath.getFlow();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.FlowEndpoint ingress = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openkilda.adapter.FlowSideAdapter.makeIngressAdapter([CtVariableReadImpl]flow, [CtVariableReadImpl]flowPath).getEndpoint();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.FlowEndpoint egress = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openkilda.adapter.FlowSideAdapter.makeEgressAdapter([CtVariableReadImpl]flow, [CtVariableReadImpl]flowPath).getEndpoint();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.model.PathSegment> pathSegments = [CtInvocationImpl][CtVariableReadImpl]flowPath.getSegments();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.openkilda.model.PathSegment> leftIter = [CtInvocationImpl][CtVariableReadImpl]pathSegments.iterator();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.openkilda.model.PathSegment> rightIter = [CtInvocationImpl][CtVariableReadImpl]pathSegments.iterator();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]rightIter.hasNext()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]flowPath.getSrcSwitch().getSwitchId(), [CtInvocationImpl][CtVariableReadImpl]ingress.getPortNumber(), [CtInvocationImpl][CtVariableReadImpl]egress.getPortNumber()));
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.PathSegment left;
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.PathSegment right = [CtInvocationImpl][CtVariableReadImpl]rightIter.next();
            [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtVariableReadImpl]ingress.getSwitchId(), [CtInvocationImpl][CtVariableReadImpl]ingress.getPortNumber(), [CtInvocationImpl][CtVariableReadImpl]right.getSrcPort()));
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]rightIter.hasNext()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]left = [CtInvocationImpl][CtVariableReadImpl]leftIter.next();
                [CtAssignmentImpl][CtVariableWriteImpl]right = [CtInvocationImpl][CtVariableReadImpl]rightIter.next();
                [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]left.getDestSwitch().getSwitchId(), [CtInvocationImpl][CtVariableReadImpl]left.getDestPort(), [CtInvocationImpl][CtVariableReadImpl]right.getSrcPort()));
            } 
            [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtVariableReadImpl]egress.getSwitchId(), [CtInvocationImpl][CtVariableReadImpl]right.getDestPort(), [CtInvocationImpl][CtVariableReadImpl]egress.getPortNumber()));
        }
        [CtReturnImpl]return [CtVariableReadImpl]resultList;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Convert {@link FlowPath} to {@link PathNodePayload}.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload> mapToPathNodes([CtParameterImpl][CtTypeReferenceImpl]org.openkilda.model.Flow flow, [CtParameterImpl][CtTypeReferenceImpl]org.openkilda.model.FlowPath flowPath) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean forward = [CtInvocationImpl][CtVariableReadImpl]flow.isForward([CtVariableReadImpl]flowPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int inPort = [CtConditionalImpl]([CtVariableReadImpl]forward) ? [CtInvocationImpl][CtVariableReadImpl]flow.getSrcPort() : [CtInvocationImpl][CtVariableReadImpl]flow.getDestPort();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int outPort = [CtConditionalImpl]([CtVariableReadImpl]forward) ? [CtInvocationImpl][CtVariableReadImpl]flow.getDestPort() : [CtInvocationImpl][CtVariableReadImpl]flow.getSrcPort();
        [CtReturnImpl]return [CtInvocationImpl]mapToPathNodes([CtVariableReadImpl]flowPath, [CtVariableReadImpl]inPort, [CtVariableReadImpl]outPort);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Convert {@link FlowPath} to {@link PathNodePayload}.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload> mapToPathNodes([CtParameterImpl][CtTypeReferenceImpl]org.openkilda.model.FlowPath flowPath, [CtParameterImpl][CtTypeReferenceImpl]int inPort, [CtParameterImpl][CtTypeReferenceImpl]int outPort) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload> resultList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]flowPath.getSegments().isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]flowPath.getSrcSwitch().getSwitchId(), [CtVariableReadImpl]inPort, [CtVariableReadImpl]outPort));
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openkilda.model.PathSegment> pathSegments = [CtInvocationImpl][CtVariableReadImpl]flowPath.getSegments();
            [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]flowPath.getSrcSwitch().getSwitchId(), [CtVariableReadImpl]inPort, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pathSegments.get([CtLiteralImpl]0).getSrcPort()));
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]pathSegments.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.PathSegment inputNode = [CtInvocationImpl][CtVariableReadImpl]pathSegments.get([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.openkilda.model.PathSegment outputNode = [CtInvocationImpl][CtVariableReadImpl]pathSegments.get([CtVariableReadImpl]i);
                [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inputNode.getDestSwitch().getSwitchId(), [CtInvocationImpl][CtVariableReadImpl]inputNode.getDestPort(), [CtInvocationImpl][CtVariableReadImpl]outputNode.getSrcPort()));
            }
            [CtInvocationImpl][CtVariableReadImpl]resultList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openkilda.messaging.payload.flow.PathNodePayload([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]flowPath.getDestSwitch().getSwitchId(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pathSegments.get([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pathSegments.size() - [CtLiteralImpl]1).getDestPort(), [CtVariableReadImpl]outPort));
        }
        [CtReturnImpl]return [CtVariableReadImpl]resultList;
    }
}
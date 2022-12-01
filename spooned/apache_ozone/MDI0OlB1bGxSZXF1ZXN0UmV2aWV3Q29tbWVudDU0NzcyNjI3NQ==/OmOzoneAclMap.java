[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.hadoop.ozone.om.helpers;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import static org.apache.hadoop.ozone.OzoneAcl.ZERO_BITSET;
[CtUnresolvedImport]import static org.apache.hadoop.ozone.om.exceptions.OMException.ResultCodes.INVALID_REQUEST;
[CtUnresolvedImport]import static org.apache.hadoop.ozone.security.acl.IAccessAuthorizer.ACLType.NONE;
[CtImportImpl]import java.util.LinkedList;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.BitSet;
[CtUnresolvedImport]import org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType;
[CtUnresolvedImport]import com.google.protobuf.ByteString;
[CtUnresolvedImport]import org.apache.hadoop.ozone.om.exceptions.OMException;
[CtUnresolvedImport]import org.apache.hadoop.ozone.security.acl.IAccessAuthorizer.ACLType;
[CtUnresolvedImport]import org.apache.hadoop.security.UserGroupInformation;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclScope;
[CtUnresolvedImport]import org.apache.hadoop.ozone.OzoneAcl;
[CtUnresolvedImport]import static org.apache.hadoop.ozone.security.acl.IAccessAuthorizer.ACLType.ALL;
[CtUnresolvedImport]import org.apache.hadoop.ozone.security.acl.IAccessAuthorizer.ACLIdentityType;
[CtClassImpl][CtJavaDocImpl]/**
 * This helper class keeps a map of all user and their permissions.
 */
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"ProtocolBufferOrdinal")
public class OmOzoneAclMap {
    [CtFieldImpl][CtCommentImpl]// per Acl Type user:rights map
    private [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.BitSet>> accessAclMap;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo> defaultAclList;

    [CtConstructorImpl]OmOzoneAclMap() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]accessAclMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]defaultAclList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType aclType : [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]accessAclMap.add([CtInvocationImpl][CtVariableReadImpl]aclType.ordinal(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>());
        }
    }

    [CtConstructorImpl]OmOzoneAclMap([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo> defaultAclList, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.BitSet>> accessAclMap) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultAclList = [CtVariableReadImpl]defaultAclList;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.accessAclMap = [CtVariableReadImpl]accessAclMap;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.BitSet> getAccessAclMap([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType type) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]accessAclMap.get([CtInvocationImpl][CtVariableReadImpl]type.ordinal());
    }

    [CtMethodImpl][CtCommentImpl]// For a given acl type and user, get the stored acl
    private [CtTypeReferenceImpl]java.util.BitSet getAcl([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType type, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String user) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]type).get([CtVariableReadImpl]user);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl> getAcl() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl> acls = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]acls.addAll([CtInvocationImpl]getAccessAcls());
        [CtInvocationImpl][CtVariableReadImpl]acls.addAll([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]defaultAclList.stream().map([CtLambdaImpl]([CtParameterImpl] a) -> [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.fromProtobuf([CtVariableReadImpl]a)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList()));
        [CtReturnImpl]return [CtVariableReadImpl]acls;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Collection<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl> getAccessAcls() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl> acls = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType type : [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]accessAclMap.get([CtInvocationImpl][CtVariableReadImpl]type.ordinal()).entrySet().stream().forEach([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl][CtVariableReadImpl]acls.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.security.acl.IAccessAuthorizer.ACLIdentityType.valueOf([CtInvocationImpl][CtVariableReadImpl]type.name()), [CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtVariableReadImpl]OzoneAcl.AclScope.ACCESS)));
        }
        [CtReturnImpl]return [CtVariableReadImpl]acls;
    }

    [CtMethodImpl][CtCommentImpl]// Add a new acl to the map
    public [CtTypeReferenceImpl]void addAcl([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl acl) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]acl, [CtLiteralImpl]"Acl should not be null.");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType aclType = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getType().name());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getAclScope().equals([CtTypeAccessImpl]OzoneAcl.AclScope.DEFAULT)) [CtBlockImpl]{
            [CtInvocationImpl]addDefaultAcl([CtVariableReadImpl]acl);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]aclType).containsKey([CtInvocationImpl][CtVariableReadImpl]acl.getName())) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]aclType).put([CtInvocationImpl][CtVariableReadImpl]acl.getName(), [CtInvocationImpl][CtVariableReadImpl]acl.getAclBitSet());
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet currBitSet = [CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]aclType).get([CtInvocationImpl][CtVariableReadImpl]acl.getName());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet bitSet = [CtInvocationImpl]checkAndGet([CtVariableReadImpl]acl, [CtVariableReadImpl]currBitSet);
            [CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]aclType).replace([CtInvocationImpl][CtVariableReadImpl]acl.getName(), [CtVariableReadImpl]bitSet);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addDefaultAcl([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl acl) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo ozoneAclInfo = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.toProtobuf([CtVariableReadImpl]acl);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]defaultAclList.contains([CtVariableReadImpl]ozoneAclInfo)) [CtBlockImpl]{
            [CtInvocationImpl]aclExistsError([CtVariableReadImpl]acl);
        } else [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]defaultAclList.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo old = [CtInvocationImpl][CtFieldReadImpl]defaultAclList.get([CtVariableReadImpl]i);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]old.getType() == [CtInvocationImpl][CtVariableReadImpl]ozoneAclInfo.getType()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]old.getName().equals([CtInvocationImpl][CtVariableReadImpl]ozoneAclInfo.getName())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet currBitSet = [CtInvocationImpl][CtTypeAccessImpl]java.util.BitSet.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]old.getRights().toByteArray());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet bitSet = [CtInvocationImpl]checkAndGet([CtVariableReadImpl]acl, [CtVariableReadImpl]currBitSet);
                    [CtAssignmentImpl][CtVariableWriteImpl]ozoneAclInfo = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.newBuilder([CtVariableReadImpl]ozoneAclInfo).setRights([CtInvocationImpl][CtTypeAccessImpl]com.google.protobuf.ByteString.copyFrom([CtInvocationImpl][CtVariableReadImpl]bitSet.toByteArray())).build();
                    [CtInvocationImpl][CtFieldReadImpl]defaultAclList.remove([CtVariableReadImpl]i);
                    [CtInvocationImpl][CtFieldReadImpl]defaultAclList.add([CtVariableReadImpl]ozoneAclInfo);
                    [CtReturnImpl]return;
                }
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]defaultAclList.add([CtVariableReadImpl]ozoneAclInfo);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void aclExistsError([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl acl) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtThrowImpl][CtCommentImpl]// throw exception if acl is already added.
        throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Acl " + [CtVariableReadImpl]acl) + [CtLiteralImpl]" already exist.", [CtFieldReadImpl]INVALID_REQUEST);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.BitSet checkAndGet([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl acl, [CtParameterImpl][CtTypeReferenceImpl]java.util.BitSet currBitSet) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Check if we are adding new rights to existing acl.
        [CtTypeReferenceImpl]java.util.BitSet temp = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.BitSet) ([CtInvocationImpl][CtVariableReadImpl]acl.getAclBitSet().clone()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet curRights = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.BitSet) ([CtVariableReadImpl]currBitSet.clone()));
        [CtInvocationImpl][CtVariableReadImpl]temp.or([CtVariableReadImpl]curRights);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]temp.equals([CtVariableReadImpl]curRights)) [CtBlockImpl]{
            [CtInvocationImpl]aclExistsError([CtVariableReadImpl]acl);
        }
        [CtReturnImpl]return [CtVariableReadImpl]temp;
    }

    [CtMethodImpl][CtCommentImpl]// Add a new acl to the map
    public [CtTypeReferenceImpl]void setAcls([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl> acls) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]acls, [CtLiteralImpl]"Acls should not be null.");
        [CtForEachImpl][CtCommentImpl]// Remove all Acls.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType type : [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]accessAclMap.get([CtInvocationImpl][CtVariableReadImpl]type.ordinal()).clear();
        }
        [CtForEachImpl][CtCommentImpl]// Add acls.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl acl : [CtVariableReadImpl]acls) [CtBlockImpl]{
            [CtInvocationImpl]addAcl([CtVariableReadImpl]acl);
        }
    }

    [CtMethodImpl][CtCommentImpl]// Add a new acl to the map
    public [CtTypeReferenceImpl]void removeAcl([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl acl) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]acl, [CtLiteralImpl]"Acl should not be null.");
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getAclScope().equals([CtTypeAccessImpl]OzoneAcl.AclScope.DEFAULT)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]defaultAclList.remove([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.toProtobuf([CtVariableReadImpl]acl));
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType aclType = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getType().name());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]aclType).containsKey([CtInvocationImpl][CtVariableReadImpl]acl.getName())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet aclRights = [CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]aclType).get([CtInvocationImpl][CtVariableReadImpl]acl.getName());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet bits = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.BitSet) ([CtInvocationImpl][CtVariableReadImpl]acl.getAclBitSet().clone()));
            [CtInvocationImpl][CtVariableReadImpl]bits.and([CtVariableReadImpl]aclRights);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bits.equals([CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.ZERO_BITSET)) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// throw exception if acl doesn't exist.
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Acl [" + [CtVariableReadImpl]acl) + [CtLiteralImpl]"] doesn't exist.", [CtFieldReadImpl]INVALID_REQUEST);
            }
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getAclBitSet().and([CtVariableReadImpl]aclRights);
            [CtInvocationImpl][CtVariableReadImpl]aclRights.xor([CtInvocationImpl][CtVariableReadImpl]acl.getAclBitSet());
            [CtIfImpl][CtCommentImpl]// Remove the acl as all rights are already set to 0.
            if ([CtInvocationImpl][CtVariableReadImpl]aclRights.equals([CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.ZERO_BITSET)) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtVariableReadImpl]aclType).remove([CtInvocationImpl][CtVariableReadImpl]acl.getName());
            }
        } else [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// throw exception if acl doesn't exist.
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Acl [" + [CtVariableReadImpl]acl) + [CtLiteralImpl]"] doesn't exist.", [CtFieldReadImpl]INVALID_REQUEST);
        }
    }

    [CtMethodImpl][CtCommentImpl]// Add a new acl to the map
    public [CtTypeReferenceImpl]void addAcl([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo acl) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]acl, [CtLiteralImpl]"Acl should not be null.");
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getAclScope().equals([CtTypeAccessImpl]OzoneAclInfo.OzoneAclScope.DEFAULT)) [CtBlockImpl]{
            [CtInvocationImpl]addDefaultAcl([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.fromProtobuf([CtVariableReadImpl]acl));
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtInvocationImpl][CtVariableReadImpl]acl.getType()).containsKey([CtInvocationImpl][CtVariableReadImpl]acl.getName())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet acls = [CtInvocationImpl][CtTypeAccessImpl]java.util.BitSet.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getRights().toByteArray());
            [CtInvocationImpl][CtInvocationImpl]getAccessAclMap([CtInvocationImpl][CtVariableReadImpl]acl.getType()).put([CtInvocationImpl][CtVariableReadImpl]acl.getName(), [CtVariableReadImpl]acls);
        } else [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// throw exception if acl is already added.
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Acl " + [CtVariableReadImpl]acl) + [CtLiteralImpl]" already exist.", [CtFieldReadImpl]INVALID_REQUEST);
        }
    }

    [CtMethodImpl][CtCommentImpl]// for a given acl, check if the user has access rights
    public [CtTypeReferenceImpl]boolean hasAccess([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo acl) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]acl == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet aclBitSet = [CtInvocationImpl]getAcl([CtInvocationImpl][CtVariableReadImpl]acl.getType(), [CtInvocationImpl][CtVariableReadImpl]acl.getName());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]aclBitSet == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.BitSet result = [CtInvocationImpl][CtTypeAccessImpl]java.util.BitSet.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]acl.getRights().toByteArray());
        [CtInvocationImpl][CtVariableReadImpl]result.and([CtVariableReadImpl]aclBitSet);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]result.equals([CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.ZERO_BITSET)) || [CtInvocationImpl][CtVariableReadImpl]aclBitSet.get([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.om.helpers.ALL.ordinal())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]aclBitSet.get([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.om.helpers.NONE.ordinal()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * For a given acl, check if the user has access rights.
     * Acl's are checked in followoing order:
     * 1. Acls for USER.
     * 2. Acls for GROUPS.
     * 3. Acls for WORLD.
     * 4. Acls for ANONYMOUS.
     *
     * @param acl
     * @param ugi
     * @return true if given ugi has acl set, else false.
     */
    public [CtTypeReferenceImpl]boolean hasAccess([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.security.acl.IAccessAuthorizer.ACLType acl, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.security.UserGroupInformation ugi) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]acl == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ugi == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl][CtCommentImpl]// Check acls in user acl list.
        return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]checkAccessForOzoneAclType([CtTypeAccessImpl]OzoneAclType.USER, [CtVariableReadImpl]acl, [CtVariableReadImpl]ugi) || [CtInvocationImpl]checkAccessForOzoneAclType([CtTypeAccessImpl]OzoneAclType.GROUP, [CtVariableReadImpl]acl, [CtVariableReadImpl]ugi)) || [CtInvocationImpl]checkAccessForOzoneAclType([CtTypeAccessImpl]OzoneAclType.WORLD, [CtVariableReadImpl]acl, [CtVariableReadImpl]ugi)) || [CtInvocationImpl]checkAccessForOzoneAclType([CtTypeAccessImpl]OzoneAclType.ANONYMOUS, [CtVariableReadImpl]acl, [CtVariableReadImpl]ugi);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper function to check acl access for OzoneAclType.
     */
    private [CtTypeReferenceImpl]boolean checkAccessForOzoneAclType([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType identityType, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.security.acl.IAccessAuthorizer.ACLType acl, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.security.UserGroupInformation ugi) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]identityType) {
            [CtCaseImpl]case [CtFieldReadImpl]USER :
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.om.helpers.OzoneAclUtil.checkIfAclBitIsSet([CtVariableReadImpl]acl, [CtInvocationImpl]getAcl([CtVariableReadImpl]identityType, [CtInvocationImpl][CtVariableReadImpl]ugi.getUserName()));
            [CtCaseImpl]case [CtFieldReadImpl]GROUP :
                [CtForEachImpl][CtCommentImpl]// Check access for user groups.
                for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userGroup : [CtInvocationImpl][CtVariableReadImpl]ugi.getGroupNames()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.om.helpers.OzoneAclUtil.checkIfAclBitIsSet([CtVariableReadImpl]acl, [CtInvocationImpl]getAcl([CtVariableReadImpl]identityType, [CtVariableReadImpl]userGroup))) [CtBlockImpl]{
                        [CtReturnImpl][CtCommentImpl]// Return true if any user group has required permission.
                        return [CtLiteralImpl]true;
                    }
                }
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtIfImpl][CtCommentImpl]// For type WORLD and ANONYMOUS we set acl type as name.
                if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.om.helpers.OzoneAclUtil.checkIfAclBitIsSet([CtVariableReadImpl]acl, [CtInvocationImpl]getAcl([CtVariableReadImpl]identityType, [CtInvocationImpl][CtVariableReadImpl]identityType.name()))) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtCommentImpl]// Convert this map to OzoneAclInfo Protobuf List
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo> ozoneAclGetProtobuf() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo> aclList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType type : [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.values()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.BitSet> entry : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]accessAclMap.get([CtInvocationImpl][CtVariableReadImpl]type.ordinal()).entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.Builder builder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.newBuilder().setName([CtInvocationImpl][CtVariableReadImpl]entry.getKey()).setType([CtVariableReadImpl]type).setAclScope([CtTypeAccessImpl]OzoneAclScope.ACCESS).setRights([CtInvocationImpl][CtTypeAccessImpl]com.google.protobuf.ByteString.copyFrom([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().toByteArray()));
                [CtInvocationImpl][CtVariableReadImpl]aclList.add([CtInvocationImpl][CtVariableReadImpl]builder.build());
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]aclList.addAll([CtFieldReadImpl]defaultAclList);
        [CtReturnImpl]return [CtVariableReadImpl]aclList;
    }

    [CtMethodImpl][CtCommentImpl]// Create map from list of OzoneAclInfos
    public static [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.helpers.OmOzoneAclMap ozoneAclGetFromProtobuf([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo> aclList) throws [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.exceptions.OMException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.om.helpers.OmOzoneAclMap aclMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.helpers.OmOzoneAclMap();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo acl : [CtVariableReadImpl]aclList) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]aclMap.addAcl([CtVariableReadImpl]acl);
        }
        [CtReturnImpl]return [CtVariableReadImpl]aclMap;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Collection<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.apache.hadoop.ozone.OzoneAcl> getAclsByScope([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclScope scope) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]scope.equals([CtTypeAccessImpl]OzoneAclScope.DEFAULT)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]defaultAclList.stream().map([CtLambdaImpl]([CtParameterImpl] a) -> [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.OzoneAcl.fromProtobuf([CtVariableReadImpl]a)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getAcl();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo> getDefaultAclList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]defaultAclList;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return a new copy of the object.
     */
    public [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.helpers.OmOzoneAclMap copyObject() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.BitSet>> accessMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl][CtCommentImpl]// Initialize.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType aclType : [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]accessMap.add([CtInvocationImpl][CtVariableReadImpl]aclType.ordinal(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>());
        }
        [CtForEachImpl][CtCommentImpl]// Add from original accessAclMap to accessMap.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType aclType : [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo.OzoneAclType.values()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int ordinal = [CtInvocationImpl][CtVariableReadImpl]aclType.ordinal();
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]accessAclMap.get([CtVariableReadImpl]ordinal).forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String k,[CtParameterImpl]java.util.BitSet v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]accessMap.get([CtVariableReadImpl]ordinal).put([CtVariableReadImpl]k, [CtInvocationImpl](([CtTypeReferenceImpl]java.util.BitSet) ([CtVariableReadImpl]v.clone()))));
        }
        [CtLocalVariableImpl][CtCommentImpl]// We can do shallow copy here, as OzoneAclInfo is immutable structure.
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.OzoneAclInfo> defaultList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]defaultList.addAll([CtFieldReadImpl]defaultAclList);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.ozone.om.helpers.OmOzoneAclMap([CtVariableReadImpl]defaultList, [CtVariableReadImpl]accessMap);
    }
}
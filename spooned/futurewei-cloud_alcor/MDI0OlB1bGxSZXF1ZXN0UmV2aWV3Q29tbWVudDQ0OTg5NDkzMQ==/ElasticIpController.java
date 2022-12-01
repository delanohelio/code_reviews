[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 The Alcor Authors.

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
[CtPackageDeclarationImpl]package com.futurewei.alcor.elasticipmanager.controller;
[CtUnresolvedImport]import com.futurewei.alcor.common.utils.Ipv4AddrUtil;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeVersionException;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.ElasticIpProjectIdConflictException;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.futurewei.alcor.common.utils.Ipv6AddrUtil;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.ElasticIpNoProjectIdException;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeBadRangesException;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.springframework.http.HttpStatus;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.config.IpVersion;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.service.ElasticIpRangeService;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.service.ElasticIpService;
[CtUnresolvedImport]import com.futurewei.alcor.web.entity.elasticip.*;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.ElasticIpIdConfilictException;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.ElasticIpQueryFormatException;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeNoIdException;
[CtImportImpl]import java.math.BigInteger;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.*;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.futurewei.alcor.common.entity.ResponseId;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import com.futurewei.alcor.elasticipmanager.exception.elasticip.*;
[CtUnresolvedImport]import org.springframework.util.StringUtils;
[CtClassImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.RestController
public class ElasticIpController {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpController.class);

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.service.ElasticIpService elasticipService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.service.ElasticIpRangeService elasticIpRangeService;

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isIpAddressInvalid([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ipAddress, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer ipVersion) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isInvalid = [CtLiteralImpl]true;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ipVersion.equals([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion())) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]isInvalid = [CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.futurewei.alcor.common.utils.Ipv4AddrUtil.formatCheck([CtVariableReadImpl]ipAddress);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ipVersion.equals([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV6.getVersion())) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]isInvalid = [CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.futurewei.alcor.common.utils.Ipv6AddrUtil.formatCheck([CtVariableReadImpl]ipAddress);
        }
        [CtReturnImpl]return [CtVariableReadImpl]isInvalid;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isIpVersionInvalid([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer ipVersion) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]ipVersion.equals([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]ipVersion.equals([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV6.getVersion()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isAllocationRangesInvalid([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer ipVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]ElasticIpRange.AllocationRange> allocationRanges) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isInvalid = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allocationRanges != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ipVersion.equals([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion())) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]ElasticIpRange.AllocationRange range : [CtVariableReadImpl]allocationRanges) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]long start = [CtInvocationImpl][CtTypeAccessImpl]com.futurewei.alcor.common.utils.Ipv4AddrUtil.ipv4ToLong([CtInvocationImpl][CtVariableReadImpl]range.getStart());
                        [CtLocalVariableImpl][CtTypeReferenceImpl]long end = [CtInvocationImpl][CtTypeAccessImpl]com.futurewei.alcor.common.utils.Ipv4AddrUtil.ipv4ToLong([CtInvocationImpl][CtVariableReadImpl]range.getEnd());
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]start > [CtVariableReadImpl]end) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]isInvalid = [CtLiteralImpl]true;
                        }
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ipVersion.equals([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV6.getVersion())) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]ElasticIpRange.AllocationRange range : [CtVariableReadImpl]allocationRanges) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.math.BigInteger start = [CtInvocationImpl][CtTypeAccessImpl]com.futurewei.alcor.common.utils.Ipv6AddrUtil.ipv6ToBitInt([CtInvocationImpl][CtVariableReadImpl]range.getStart());
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.math.BigInteger end = [CtInvocationImpl][CtTypeAccessImpl]com.futurewei.alcor.common.utils.Ipv6AddrUtil.ipv6ToBitInt([CtInvocationImpl][CtVariableReadImpl]range.getEnd());
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]start.compareTo([CtVariableReadImpl]end) > [CtLiteralImpl]0) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]isInvalid = [CtLiteralImpl]true;
                        }
                    }
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]isInvalid = [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]isInvalid;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createElasticIpParameterProcess([CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl][CtTypeReferenceImpl]ElasticIpInfo elasticIpInfo) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elasticIpInfo == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpQueryFormatException();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]projectId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpNoProjectIdException();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getProjectId() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setProjectId([CtVariableReadImpl]projectId);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]projectId.equals([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getProjectId())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpProjectIdConflictException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIpVersion() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setElasticIpVersion([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion());
        } else [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isIpVersionInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIpVersion())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpEipVersionException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIp() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isIpAddressInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIp(), [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIpVersion())) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpEipAddressException();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPortId() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setPortId([CtLiteralImpl]"");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPortId().isEmpty()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIp() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion() != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpNoPortIdException();
            }
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion() != [CtLiteralImpl]null) && [CtInvocationImpl][CtThisAccessImpl]this.isIpVersionInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion())) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpPipVersionException();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIp() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion() == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setPrivateIpVersion([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion());
                }
                [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isIpAddressInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIp(), [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion())) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpPipAddressException();
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getName() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setName([CtLiteralImpl]"");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getDescription() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setDescription([CtLiteralImpl]"");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateElasticIpParameterProcess([CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String elasticIpId, [CtParameterImpl][CtTypeReferenceImpl]ElasticIpInfo elasticIpInfo) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elasticIpInfo == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpQueryFormatException();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]projectId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpNoProjectIdException();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getProjectId() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setProjectId([CtVariableReadImpl]projectId);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]projectId.equals([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getProjectId())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpProjectIdConflictException();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]elasticIpId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpNoIdException();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setId([CtVariableReadImpl]elasticIpId);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]elasticIpId.equals([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getId())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpIdConfilictException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIpVersion() != [CtLiteralImpl]null) && [CtInvocationImpl][CtThisAccessImpl]this.isIpVersionInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIpVersion())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpEipVersionException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIp() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIpVersion() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setElasticIpVersion([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isIpAddressInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIp(), [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getElasticIpVersion())) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpEipAddressException();
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPortId())) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIp() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion() != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpNoPortIdException();
            }
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion() != [CtLiteralImpl]null) && [CtInvocationImpl][CtThisAccessImpl]this.isIpVersionInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion())) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpPipVersionException();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIp() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIp() == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.setPrivateIpVersion([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion());
                }
                [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isIpAddressInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIp(), [CtInvocationImpl][CtVariableReadImpl]elasticIpInfo.getPrivateIpVersion())) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpPipAddressException();
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createElasticIpRangeParameterProcess([CtParameterImpl][CtTypeReferenceImpl]ElasticIpRangeInfo elasticIpRangeInfo) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elasticIpRangeInfo == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpQueryFormatException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getIpVersion() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.setIpVersion([CtInvocationImpl][CtTypeAccessImpl]IpVersion.IPV4.getVersion());
        } else [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isIpVersionInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getIpVersion())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeVersionException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]ElasticIpRange.AllocationRange> allocationRanges = [CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getAllocationRanges();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allocationRanges != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isAllocationRangesInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getIpVersion(), [CtVariableReadImpl]allocationRanges)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeBadRangesException();
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.setAllocationRanges([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getName() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.setName([CtLiteralImpl]"");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getDescription() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.setDescription([CtLiteralImpl]"");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateElasticIpRangeParameterProcess([CtParameterImpl][CtTypeReferenceImpl]java.lang.String elasticipRangeId, [CtParameterImpl][CtTypeReferenceImpl]ElasticIpRangeInfo elasticIpRangeInfo) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elasticIpRangeInfo == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpQueryFormatException();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]elasticipRangeId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeNoIdException();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.setId([CtVariableReadImpl]elasticipRangeId);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]elasticipRangeId.equals([CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getId())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpIdConfilictException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getIpVersion() != [CtLiteralImpl]null) && [CtInvocationImpl][CtThisAccessImpl]this.isIpVersionInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getIpVersion())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeVersionException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]ElasticIpRange.AllocationRange> allocationRanges = [CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getAllocationRanges();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allocationRanges != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isAllocationRangesInvalid([CtInvocationImpl][CtVariableReadImpl]elasticIpRangeInfo.getIpVersion(), [CtVariableReadImpl]allocationRanges)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.elasticiprange.ElasticIpRangeBadRangesException();
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create an elastic ip, and communicate with port and node services if the
     * elastic ip associated with a port.
     *
     * @param projectId
     * 		Project the elastic ip belongs to
     * @param request
     * 		Elastic ip configuration
     * @return ElasticIpInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PostMapping([CtLiteralImpl]"/project/{project_id}/elasticips")
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.ResponseBody
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.ResponseStatus([CtFieldReadImpl]org.springframework.http.HttpStatus.CREATED)
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpInfoWrapper createElasticIp([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable([CtLiteralImpl]"project_id")
    [CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.RequestBody
    [CtTypeReferenceImpl]ElasticIpInfoWrapper request) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpInfo requestInfo = [CtInvocationImpl][CtVariableReadImpl]request.getElasticip();
        [CtInvocationImpl][CtThisAccessImpl]this.createElasticIpParameterProcess([CtVariableReadImpl]projectId, [CtVariableReadImpl]requestInfo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpInfo result = [CtInvocationImpl][CtFieldReadImpl]elasticipService.createElasticIp([CtVariableReadImpl]requestInfo);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpInfoWrapper([CtVariableReadImpl]result);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Update an elastic ip, and communicate with port and node services if
     * the elastic ip's association state is changed.
     *
     * @param projectId
     * 		Project the elastic ip belongs to
     * @param elasticIpId
     * 		Uuid of the elastic ip
     * @param request
     * 		Elastic ip configuration
     * @return ElasticIpInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PutMapping([CtLiteralImpl]"/project/{project_id}/elasticips/{elasticip_id}")
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.ResponseBody
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpInfoWrapper updateElasticIp([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable([CtLiteralImpl]"project_id")
    [CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable([CtLiteralImpl]"elasticip_id")
    [CtTypeReferenceImpl]java.lang.String elasticIpId, [CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.RequestBody
    [CtTypeReferenceImpl]ElasticIpInfoWrapper request) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpInfo requestInfo = [CtInvocationImpl][CtVariableReadImpl]request.getElasticip();
        [CtInvocationImpl][CtThisAccessImpl]this.updateElasticIpParameterProcess([CtVariableReadImpl]projectId, [CtVariableReadImpl]elasticIpId, [CtVariableReadImpl]requestInfo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpInfo result = [CtInvocationImpl][CtFieldReadImpl]elasticipService.updateElasticIp([CtVariableReadImpl]requestInfo);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpInfoWrapper([CtVariableReadImpl]result);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Delete an elastic ip, and communicate with port and node services if the elastic ip
     * associated with a port.
     *
     * @param projectId
     * 		Project the elastic ip belongs to
     * @param elasticIpId
     * 		Uuid of the elastic ip
     * @return ResponseId
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.DeleteMapping([CtLiteralImpl]"/project/{project_id}/elasticips/{elasticip_id}")
    public [CtTypeReferenceImpl]com.futurewei.alcor.common.entity.ResponseId deleteElasticIp([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable([CtLiteralImpl]"project_id")
    [CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable([CtLiteralImpl]"elasticip_id")
    [CtTypeReferenceImpl]java.lang.String elasticIpId) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]projectId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpNoProjectIdException();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]elasticIpId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpNoIdException();
        }
        [CtInvocationImpl][CtFieldReadImpl]elasticipService.deleteElasticIp([CtVariableReadImpl]projectId, [CtVariableReadImpl]elasticIpId);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.common.entity.ResponseId([CtVariableReadImpl]elasticIpId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the information of an elastic ip.
     *
     * @param projectId
     * 		Project the elastic ip belongs to
     * @param elasticIpId
     * 		Uuid of the elastic ip
     * @return ElasticIpInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.GetMapping([CtNewArrayImpl]{ [CtLiteralImpl]"/project/{project_id}/elasticips/{elasticip_id}" })
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpInfoWrapper getElasticIp([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable
    [CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable
    [CtTypeReferenceImpl]java.lang.String elasticIpId) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]projectId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpNoProjectIdException();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]elasticIpId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpNoIdException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpInfo eip = [CtInvocationImpl][CtFieldReadImpl]elasticipService.getElasticIp([CtVariableReadImpl]projectId, [CtVariableReadImpl]elasticIpId);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpInfoWrapper([CtVariableReadImpl]eip);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a list of information of each elastic ip belongs to the project.
     *
     * @param projectId
     * 		Project the elastic ip belongs to
     * @return ElasticIpsInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.GetMapping([CtNewArrayImpl]{ [CtLiteralImpl]"/project/{project_id}/elasticips" })
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpsInfoWrapper getElasticIps([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable
    [CtTypeReferenceImpl]java.lang.String projectId) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]projectId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.exception.ElasticIpNoProjectIdException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ElasticIpInfo> eips = [CtInvocationImpl][CtFieldReadImpl]elasticipService.getElasticIps([CtVariableReadImpl]projectId);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpsInfoWrapper([CtVariableReadImpl]eips);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create an elastic ip range, of which a list of allocation address ranges
     * included for assigning addresses to elastic ips.
     *
     * @param request
     * 		Elastic ip range configuration
     * @return ElasticIpRangeInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PostMapping([CtLiteralImpl]"/elasticip-ranges")
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.ResponseBody
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.ResponseStatus([CtFieldReadImpl]org.springframework.http.HttpStatus.CREATED)
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpRangeInfoWrapper createElasticIpRange([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.RequestBody
    [CtTypeReferenceImpl]ElasticIpRangeInfoWrapper request) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpRangeInfo requestInfo = [CtInvocationImpl][CtVariableReadImpl]request.getElasticIpRange();
        [CtInvocationImpl][CtThisAccessImpl]this.createElasticIpRangeParameterProcess([CtVariableReadImpl]requestInfo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpRangeInfo result = [CtInvocationImpl][CtFieldReadImpl]elasticIpRangeService.createElasticIpRange([CtVariableReadImpl]requestInfo);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpRangeInfoWrapper([CtVariableReadImpl]result);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Update an elastic ip range.
     *
     * @param elasticIpRangeId
     * 		Uuid of the elastic ip range
     * @param request
     * 		Elastic ip range configuration
     * @return ElasticIpRangeInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PutMapping([CtLiteralImpl]"/elasticip-ranges/{elasticip_range_id}")
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.ResponseBody
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpRangeInfoWrapper updateElasticIpRange([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable([CtLiteralImpl]"elasticip_range_id")
    [CtTypeReferenceImpl]java.lang.String elasticIpRangeId, [CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.RequestBody
    [CtTypeReferenceImpl]ElasticIpRangeInfoWrapper request) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpRangeInfo requestInfo = [CtInvocationImpl][CtVariableReadImpl]request.getElasticIpRange();
        [CtInvocationImpl][CtThisAccessImpl]this.updateElasticIpRangeParameterProcess([CtVariableReadImpl]elasticIpRangeId, [CtVariableReadImpl]requestInfo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpRangeInfo result = [CtInvocationImpl][CtFieldReadImpl]elasticIpRangeService.createElasticIpRange([CtVariableReadImpl]requestInfo);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpRangeInfoWrapper([CtVariableReadImpl]result);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Delete an elastic ip range. It will throw a exception if there is any elastic ip
     * has assigned an address belongs to this elastic ip range.
     *
     * @param elasticIpRangeId
     * 		Uuid of the elastic ip range
     * @return ResponseId
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.DeleteMapping([CtLiteralImpl]"/elasticip-ranges/{elasticip_range_id}")
    public [CtTypeReferenceImpl]com.futurewei.alcor.common.entity.ResponseId deleteElasticIpRange([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable([CtLiteralImpl]"elasticip_range_id")
    [CtTypeReferenceImpl]java.lang.String elasticIpRangeId) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]elasticIpRangeId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpNoIdException();
        }
        [CtInvocationImpl][CtFieldReadImpl]elasticIpRangeService.deleteElasticIpRange([CtVariableReadImpl]elasticIpRangeId);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.futurewei.alcor.common.entity.ResponseId([CtVariableReadImpl]elasticIpRangeId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the information of an elastic ip range.
     *
     * @param elasticIpRangeId
     * 		Uuid of the elastic ip range
     * @return ElasticIpRangeInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.GetMapping([CtNewArrayImpl]{ [CtLiteralImpl]"/elasticip-ranges/{elasticip_range_id}" })
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpRangeInfoWrapper getElasticIpRange([CtParameterImpl][CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.PathVariable
    [CtTypeReferenceImpl]java.lang.String elasticIpRangeId) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]elasticIpRangeId)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpNoIdException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]ElasticIpRangeInfo eipRange = [CtInvocationImpl][CtFieldReadImpl]elasticIpRangeService.getElasticIpRange([CtVariableReadImpl]elasticIpRangeId);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpRangeInfoWrapper([CtVariableReadImpl]eipRange);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a list of information of each elastic ip range.
     *
     * @return ElasticIpRangesInfoWrapper
     * @throws Exception
     * 		Various exceptions that may occur during the create process
     */
    [CtAnnotationImpl]@com.futurewei.alcor.elasticipmanager.controller.GetMapping([CtNewArrayImpl]{ [CtLiteralImpl]"/elasticip-ranges" })
    public [CtTypeReferenceImpl]com.futurewei.alcor.elasticipmanager.controller.ElasticIpRangesInfoWrapper getElasticIpRanges() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ElasticIpRangeInfo> eipRanges = [CtInvocationImpl][CtFieldReadImpl]elasticIpRangeService.getElasticIpRanges();
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ElasticIpRangesInfoWrapper([CtVariableReadImpl]eipRanges);
    }
}
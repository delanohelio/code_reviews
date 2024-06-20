[CompilationUnitImpl][CtCommentImpl]/* Copyright 1999-2018 Alibaba Group Holding Ltd.

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
[CtPackageDeclarationImpl]package com.alibaba.nacos.config.server.controller;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.service.trace.ConfigTraceService;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import javax.servlet.ServletException;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.utils.event.EventDispatcher;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.constant.Constants;
[CtImportImpl]import java.sql.Timestamp;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.result.ResultBuilder;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.result.code.ResultCodeEnum;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import com.alibaba.nacos.api.exception.NacosException;
[CtImportImpl]import org.apache.commons.lang3.time.DateFormatUtils;
[CtUnresolvedImport]import com.alibaba.nacos.core.auth.ActionTypes;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.controller.parameters.SameNamespaceCloneConfigBean;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.service.PersistService;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.model.*;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.service.ConfigDataChangeEvent;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.utils.*;
[CtUnresolvedImport]import com.alibaba.nacos.core.auth.Secured;
[CtUnresolvedImport]import org.springframework.http.HttpHeaders;
[CtUnresolvedImport]import javax.servlet.http.HttpServletResponse;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.service.ConfigSubService;
[CtUnresolvedImport]import org.springframework.http.HttpStatus;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.auth.ConfigResourceParser;
[CtUnresolvedImport]import com.alibaba.nacos.config.server.service.AggrWhitelist;
[CtUnresolvedImport]import org.springframework.util.CollectionUtils;
[CtUnresolvedImport]import static com.alibaba.nacos.core.utils.SystemUtils.LOCAL_IP;
[CtUnresolvedImport]import org.springframework.web.multipart.MultipartFile;
[CtImportImpl]import java.net.URLDecoder;
[CtUnresolvedImport]import org.springframework.http.ResponseEntity;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.*;
[CtClassImpl][CtJavaDocImpl]/**
 * 软负载客户端发布数据专用控制器
 *
 * @author leiwen
 */
[CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RestController
[CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestMapping([CtFieldReadImpl]com.alibaba.nacos.config.server.constant.Constants.CONFIG_CONTROLLER_PATH)
public class ConfigController {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NAMESPACE_PUBLIC_KEY = [CtLiteralImpl]"public";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EXPORT_CONFIG_FILE_NAME = [CtLiteralImpl]"nacos_config_export_";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EXPORT_CONFIG_FILE_NAME_EXT = [CtLiteralImpl]".zip";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EXPORT_CONFIG_FILE_NAME_DATE_FORMAT = [CtLiteralImpl]"yyyyMMddHHmmss";

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.ConfigServletInner inner;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.PersistService persistService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigSubService configSubService;

    [CtConstructorImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    public ConfigController([CtParameterImpl][CtTypeReferenceImpl]ConfigServletInner configServletInner, [CtParameterImpl][CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.PersistService persistService, [CtParameterImpl][CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigSubService configSubService) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inner = [CtVariableReadImpl]configServletInner;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.persistService = [CtVariableReadImpl]persistService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configSubService = [CtVariableReadImpl]configSubService;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 增加或更新非聚合数据。
     *
     * @throws NacosException
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.PostMapping
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.WRITE, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]java.lang.Boolean publishConfig([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"content")
    [CtTypeReferenceImpl]java.lang.String content, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tag", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String tag, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"appName", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String appName, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"src_user", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String srcUser, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"config_tags", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String configTags, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"desc", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String desc, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"use", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String use, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"effect", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String effect, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"type", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"schema", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String schema) throws [CtTypeReferenceImpl]com.alibaba.nacos.api.exception.NacosException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String srcIp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getRemoteIp([CtVariableReadImpl]request);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requestIpApp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getAppName([CtVariableReadImpl]request);
        [CtInvocationImpl][CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtLiteralImpl]"datumId", [CtVariableReadImpl]content);
        [CtInvocationImpl][CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]tag);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configAdvanceInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>([CtLiteralImpl]10);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]configTags != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"config_tags", [CtVariableReadImpl]configTags);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]desc != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"desc", [CtVariableReadImpl]desc);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]use != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"use", [CtVariableReadImpl]use);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]effect != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"effect", [CtVariableReadImpl]effect);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"type", [CtVariableReadImpl]type);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"schema", [CtVariableReadImpl]schema);
        }
        [CtInvocationImpl][CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]configAdvanceInfo);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.service.AggrWhitelist.isAggrDataId([CtVariableReadImpl]dataId)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.log.warn([CtLiteralImpl]"[aggr-conflict] {} attemp to publish single data, {}, {}", [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getRemoteIp([CtVariableReadImpl]request), [CtVariableReadImpl]dataId, [CtVariableReadImpl]group);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.api.exception.NacosException([CtFieldReadImpl]com.alibaba.nacos.api.exception.NacosException.NO_RIGHT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"dataId:" + [CtVariableReadImpl]dataId) + [CtLiteralImpl]" is aggr");
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.sql.Timestamp time = [CtInvocationImpl][CtTypeAccessImpl]TimeUtils.getCurrentTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String betaIps = [CtInvocationImpl][CtVariableReadImpl]request.getHeader([CtLiteralImpl]"betaIps");
        [CtLocalVariableImpl][CtTypeReferenceImpl]ConfigInfo configInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ConfigInfo([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]appName, [CtVariableReadImpl]content);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isBlank([CtVariableReadImpl]betaIps)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isBlank([CtVariableReadImpl]tag)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]persistService.insertOrUpdate([CtVariableReadImpl]srcIp, [CtVariableReadImpl]srcUser, [CtVariableReadImpl]configInfo, [CtVariableReadImpl]time, [CtVariableReadImpl]configAdvanceInfo, [CtLiteralImpl]false);
                [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]false, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtInvocationImpl][CtVariableReadImpl]time.getTime()));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]persistService.insertOrUpdateTag([CtVariableReadImpl]configInfo, [CtVariableReadImpl]tag, [CtVariableReadImpl]srcIp, [CtVariableReadImpl]srcUser, [CtVariableReadImpl]time, [CtLiteralImpl]false);
                [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]false, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]tag, [CtInvocationImpl][CtVariableReadImpl]time.getTime()));
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// beta publish
            [CtFieldReadImpl]persistService.insertOrUpdateBeta([CtVariableReadImpl]configInfo, [CtVariableReadImpl]betaIps, [CtVariableReadImpl]srcIp, [CtVariableReadImpl]srcUser, [CtVariableReadImpl]time, [CtLiteralImpl]false);
            [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]true, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtInvocationImpl][CtVariableReadImpl]time.getTime()));
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.service.trace.ConfigTraceService.logPersistenceEvent([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]requestIpApp, [CtInvocationImpl][CtVariableReadImpl]time.getTime(), [CtTypeAccessImpl]LOCAL_IP, [CtTypeAccessImpl]ConfigTraceService.PERSISTENCE_EVENT_PUB, [CtVariableReadImpl]content);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 取数据
     *
     * @throws ServletException
     * @throws IOException
     * @throws NacosException
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]void getConfig([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tag", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String tag) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]javax.servlet.ServletException, [CtTypeReferenceImpl]com.alibaba.nacos.api.exception.NacosException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.NAMESPACE_PUBLIC_KEY.equalsIgnoreCase([CtVariableReadImpl]tenant)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tenant = [CtLiteralImpl]"";
        }
        [CtInvocationImpl][CtCommentImpl]// check params
        [CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtLiteralImpl]"datumId", [CtLiteralImpl]"content");
        [CtInvocationImpl][CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]tag);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String clientIp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getRemoteIp([CtVariableReadImpl]request);
        [CtInvocationImpl][CtFieldReadImpl]inner.doGetConfig([CtVariableReadImpl]request, [CtVariableReadImpl]response, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]tag, [CtVariableReadImpl]clientIp);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 取数据
     *
     * @throws NacosException
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping(params = [CtLiteralImpl]"show=all")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.ConfigAllInfo detailConfigInfo([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant) throws [CtTypeReferenceImpl]com.alibaba.nacos.api.exception.NacosException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// check params
        [CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtLiteralImpl]"datumId", [CtLiteralImpl]"content");
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]persistService.findConfigAllInfo([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 同步删除某个dataId下面所有的聚合前数据
     *
     * @throws NacosException
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.DeleteMapping
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.WRITE, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]java.lang.Boolean deleteConfig([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtCommentImpl]// 
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtCommentImpl]// 
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tag", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String tag) throws [CtTypeReferenceImpl]com.alibaba.nacos.api.exception.NacosException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtLiteralImpl]"datumId", [CtLiteralImpl]"rm");
        [CtInvocationImpl][CtTypeAccessImpl]ParamUtils.checkParam([CtVariableReadImpl]tag);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clientIp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getRemoteIp([CtVariableReadImpl]request);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isBlank([CtVariableReadImpl]tag)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]persistService.removeConfigInfo([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]clientIp, [CtLiteralImpl]null);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]persistService.removeConfigInfoTag([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]tag, [CtVariableReadImpl]clientIp, [CtLiteralImpl]null);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.sql.Timestamp time = [CtInvocationImpl][CtTypeAccessImpl]TimeUtils.getCurrentTime();
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.service.trace.ConfigTraceService.logPersistenceEvent([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]time.getTime(), [CtVariableReadImpl]clientIp, [CtTypeAccessImpl]ConfigTraceService.PERSISTENCE_EVENT_REMOVE, [CtLiteralImpl]null);
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]false, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]tag, [CtInvocationImpl][CtVariableReadImpl]time.getTime()));
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return java.lang.Boolean
     * @author klw
     * @Description: delete configuration based on multiple config ids
     * @Date 2019/7/5 10:26
     * @Param [request, response, dataId, group, tenant, tag]
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.DeleteMapping(params = [CtLiteralImpl]"delType=ids")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.WRITE, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.RestResult<[CtTypeReferenceImpl]java.lang.Boolean> deleteConfigs([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"ids")
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> ids) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clientIp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getRemoteIp([CtVariableReadImpl]request);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.sql.Timestamp time = [CtInvocationImpl][CtTypeAccessImpl]TimeUtils.getCurrentTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ConfigInfo> configInfoList = [CtInvocationImpl][CtFieldReadImpl]persistService.removeConfigInfoByIds([CtVariableReadImpl]ids, [CtVariableReadImpl]clientIp, [CtLiteralImpl]null);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]configInfoList)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]ConfigInfo configInfo : [CtVariableReadImpl]configInfoList) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.service.trace.ConfigTraceService.logPersistenceEvent([CtInvocationImpl][CtVariableReadImpl]configInfo.getDataId(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getGroup(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getTenant(), [CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]time.getTime(), [CtVariableReadImpl]clientIp, [CtTypeAccessImpl]ConfigTraceService.PERSISTENCE_EVENT_REMOVE, [CtLiteralImpl]null);
                [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]false, [CtInvocationImpl][CtVariableReadImpl]configInfo.getDataId(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getGroup(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getTenant(), [CtInvocationImpl][CtVariableReadImpl]time.getTime()));
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildSuccessResult([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping([CtLiteralImpl]"/catalog")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.RestResult<[CtTypeReferenceImpl]ConfigAdvanceInfo> getConfigAdvanceInfo([CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]RestResult<[CtTypeReferenceImpl]ConfigAdvanceInfo> rr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RestResult<[CtTypeReferenceImpl]ConfigAdvanceInfo>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]ConfigAdvanceInfo configInfo = [CtInvocationImpl][CtFieldReadImpl]persistService.findConfigAdvanceInfo([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant);
        [CtInvocationImpl][CtVariableReadImpl]rr.setCode([CtLiteralImpl]200);
        [CtInvocationImpl][CtVariableReadImpl]rr.setData([CtVariableReadImpl]configInfo);
        [CtReturnImpl]return [CtVariableReadImpl]rr;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 比较MD5
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.PostMapping([CtLiteralImpl]"/listener")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]void listener([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response) throws [CtTypeReferenceImpl]javax.servlet.ServletException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]request.setAttribute([CtLiteralImpl]"org.apache.catalina.ASYNC_SUPPORTED", [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String probeModify = [CtInvocationImpl][CtVariableReadImpl]request.getParameter([CtLiteralImpl]"Listening-Configs");
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isBlank([CtVariableReadImpl]probeModify)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"invalid probeModify");
        }
        [CtAssignmentImpl][CtVariableWriteImpl]probeModify = [CtInvocationImpl][CtTypeAccessImpl]java.net.URLDecoder.decode([CtVariableReadImpl]probeModify, [CtTypeAccessImpl]Constants.ENCODE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> clientMd5Map;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]clientMd5Map = [CtInvocationImpl][CtTypeAccessImpl]MD5Util.getClientMd5Map([CtVariableReadImpl]probeModify);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"invalid probeModify");
        }
        [CtInvocationImpl][CtCommentImpl]// do long-polling
        [CtFieldReadImpl]inner.doPollingConfig([CtVariableReadImpl]request, [CtVariableReadImpl]response, [CtVariableReadImpl]clientMd5Map, [CtInvocationImpl][CtVariableReadImpl]probeModify.length());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 订阅改配置的客户端信息
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping([CtLiteralImpl]"/listener")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.GroupkeyListenserStatus getListeners([CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"sampleTime", required = [CtLiteralImpl]false, defaultValue = [CtLiteralImpl]"1")
    [CtTypeReferenceImpl]int sampleTime) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]group = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isBlank([CtVariableReadImpl]group)) ? [CtFieldReadImpl]com.alibaba.nacos.config.server.constant.Constants.DEFAULT_GROUP : [CtVariableReadImpl]group;
        [CtLocalVariableImpl][CtTypeReferenceImpl]SampleResult collectSampleResult = [CtInvocationImpl][CtFieldReadImpl]configSubService.getCollectSampleResult([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]sampleTime);
        [CtLocalVariableImpl][CtTypeReferenceImpl]GroupkeyListenserStatus gls = [CtConstructorCallImpl]new [CtTypeReferenceImpl]GroupkeyListenserStatus();
        [CtInvocationImpl][CtVariableReadImpl]gls.setCollectStatus([CtLiteralImpl]200);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]collectSampleResult.getLisentersGroupkeyStatus() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]gls.setLisentersGroupkeyStatus([CtInvocationImpl][CtVariableReadImpl]collectSampleResult.getLisentersGroupkeyStatus());
        }
        [CtReturnImpl]return [CtVariableReadImpl]gls;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 查询配置信息，返回JSON格式。
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping(params = [CtLiteralImpl]"search=accurate")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.Page<[CtTypeReferenceImpl]ConfigInfo> searchConfig([CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"appName", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String appName, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"config_tags", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String configTags, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"pageNo")
    [CtTypeReferenceImpl]int pageNo, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"pageSize")
    [CtTypeReferenceImpl]int pageSize) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configAdvanceInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>([CtLiteralImpl]100);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]appName)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"appName", [CtVariableReadImpl]appName);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]configTags)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"config_tags", [CtVariableReadImpl]configTags);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]persistService.findConfigInfo4Page([CtVariableReadImpl]pageNo, [CtVariableReadImpl]pageSize, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]configAdvanceInfo);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"serialize page error, dataId=" + [CtVariableReadImpl]dataId) + [CtLiteralImpl]", group=") + [CtVariableReadImpl]group;
            [CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.log.error([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * 模糊查询配置信息。不允许只根据内容模糊查询，即dataId和group都为NULL，但content不是NULL。这种情况下，返回所有配置。
     */
    [CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping(params = [CtLiteralImpl]"search=blur")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.Page<[CtTypeReferenceImpl]ConfigInfo> fuzzySearchConfig([CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"appName", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String appName, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"config_tags", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String configTags, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"pageNo")
    [CtTypeReferenceImpl]int pageNo, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"pageSize")
    [CtTypeReferenceImpl]int pageSize) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configAdvanceInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>([CtLiteralImpl]50);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]appName)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"appName", [CtVariableReadImpl]appName);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]configTags)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]configAdvanceInfo.put([CtLiteralImpl]"config_tags", [CtVariableReadImpl]configTags);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]persistService.findConfigInfoLike4Page([CtVariableReadImpl]pageNo, [CtVariableReadImpl]pageSize, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]configAdvanceInfo);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"serialize page error, dataId=" + [CtVariableReadImpl]dataId) + [CtLiteralImpl]", group=") + [CtVariableReadImpl]group;
            [CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.log.error([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.DeleteMapping(params = [CtLiteralImpl]"beta=true")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.RestResult<[CtTypeReferenceImpl]java.lang.Boolean> stopBeta([CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]RestResult<[CtTypeReferenceImpl]java.lang.Boolean> rr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RestResult<[CtTypeReferenceImpl]java.lang.Boolean>();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]persistService.removeConfigInfo4Beta([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.log.error([CtLiteralImpl]"remove beta data error", [CtVariableReadImpl]e);
            [CtInvocationImpl][CtVariableReadImpl]rr.setCode([CtLiteralImpl]500);
            [CtInvocationImpl][CtVariableReadImpl]rr.setData([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]rr.setMessage([CtLiteralImpl]"remove beta data error");
            [CtReturnImpl]return [CtVariableReadImpl]rr;
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]true, [CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis()));
        [CtInvocationImpl][CtVariableReadImpl]rr.setCode([CtLiteralImpl]200);
        [CtInvocationImpl][CtVariableReadImpl]rr.setData([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]rr.setMessage([CtLiteralImpl]"stop beta ok");
        [CtReturnImpl]return [CtVariableReadImpl]rr;
    }

    [CtMethodImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping(params = [CtLiteralImpl]"beta=true")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.RestResult<[CtTypeReferenceImpl]ConfigInfo4Beta> queryBeta([CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"dataId")
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam([CtLiteralImpl]"group")
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]RestResult<[CtTypeReferenceImpl]ConfigInfo4Beta> rr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RestResult<[CtTypeReferenceImpl]ConfigInfo4Beta>();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]ConfigInfo4Beta ci = [CtInvocationImpl][CtFieldReadImpl]persistService.findConfigInfo4Beta([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant);
            [CtInvocationImpl][CtVariableReadImpl]rr.setCode([CtLiteralImpl]200);
            [CtInvocationImpl][CtVariableReadImpl]rr.setData([CtVariableReadImpl]ci);
            [CtInvocationImpl][CtVariableReadImpl]rr.setMessage([CtLiteralImpl]"stop beta ok");
            [CtReturnImpl]return [CtVariableReadImpl]rr;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.log.error([CtLiteralImpl]"remove beta data error", [CtVariableReadImpl]e);
            [CtInvocationImpl][CtVariableReadImpl]rr.setCode([CtLiteralImpl]500);
            [CtInvocationImpl][CtVariableReadImpl]rr.setMessage([CtLiteralImpl]"remove beta data error");
            [CtReturnImpl]return [CtVariableReadImpl]rr;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.GetMapping(params = [CtLiteralImpl]"export=true")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.READ, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtArrayTypeReferenceImpl]byte[]> exportConfig([CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"dataId", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String dataId, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"group", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String group, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"appName", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String appName, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]false, defaultValue = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.[CtFieldReferenceImpl]EMPTY)
    [CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"ids", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> ids) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]ids.removeAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singleton([CtLiteralImpl]null));
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.NAMESPACE_PUBLIC_KEY.equalsIgnoreCase([CtVariableReadImpl]tenant)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tenant = [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ConfigAllInfo> dataList = [CtInvocationImpl][CtFieldReadImpl]persistService.findAllConfigInfo4Export([CtVariableReadImpl]dataId, [CtVariableReadImpl]group, [CtVariableReadImpl]tenant, [CtVariableReadImpl]appName, [CtVariableReadImpl]ids);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]ZipUtils.ZipItem> zipItemList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder metaData = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]ConfigInfo ci : [CtVariableReadImpl]dataList) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtInvocationImpl][CtVariableReadImpl]ci.getAppName())) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Handle appName
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]metaData == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]metaData = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String metaDataId = [CtInvocationImpl][CtVariableReadImpl]ci.getDataId();
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]metaDataId.contains([CtLiteralImpl]".")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]metaDataId = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]metaDataId.substring([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]metaDataId.lastIndexOf([CtLiteralImpl]".")) + [CtLiteralImpl]"~") + [CtInvocationImpl][CtVariableReadImpl]metaDataId.substring([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]metaDataId.lastIndexOf([CtLiteralImpl]".") + [CtLiteralImpl]1);
                }
                [CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// Fixed use of "\r\n" here
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]metaData.append([CtInvocationImpl][CtVariableReadImpl]ci.getGroup()).append([CtLiteralImpl]".").append([CtVariableReadImpl]metaDataId).append([CtLiteralImpl]".app=").append([CtInvocationImpl][CtVariableReadImpl]ci.getAppName()).append([CtLiteralImpl]"\r\n");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String itemName = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ci.getGroup() + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]ci.getDataId();
            [CtInvocationImpl][CtVariableReadImpl]zipItemList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]ZipUtils.ZipItem([CtVariableReadImpl]itemName, [CtInvocationImpl][CtVariableReadImpl]ci.getContent()));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]metaData != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]zipItemList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]ZipUtils.ZipItem([CtLiteralImpl]".meta.yml", [CtInvocationImpl][CtVariableReadImpl]metaData.toString()));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.HttpHeaders headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpHeaders();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fileName = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.EXPORT_CONFIG_FILE_NAME + [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.time.DateFormatUtils.format([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.EXPORT_CONFIG_FILE_NAME_DATE_FORMAT)) + [CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.EXPORT_CONFIG_FILE_NAME_EXT;
        [CtInvocationImpl][CtVariableReadImpl]headers.add([CtLiteralImpl]"Content-Disposition", [CtBinaryOperatorImpl][CtLiteralImpl]"attachment;filename=" + [CtVariableReadImpl]fileName);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtArrayTypeReferenceImpl]byte[]>([CtInvocationImpl][CtTypeAccessImpl]ZipUtils.zip([CtVariableReadImpl]zipItemList), [CtVariableReadImpl]headers, [CtFieldReadImpl]org.springframework.http.HttpStatus.OK);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.PostMapping(params = [CtLiteralImpl]"import=true")
    [CtAnnotationImpl]@com.alibaba.nacos.core.auth.Secured(action = [CtFieldReadImpl]com.alibaba.nacos.core.auth.ActionTypes.WRITE, parser = [CtFieldReadImpl]com.alibaba.nacos.config.server.auth.ConfigResourceParser.class)
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.RestResult<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> importAndPublishConfig([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"src_user", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String srcUser, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"namespace", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String namespace, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"policy", defaultValue = [CtLiteralImpl]"ABORT")
    [CtTypeReferenceImpl]SameConfigPolicy policy, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.web.multipart.MultipartFile file) throws [CtTypeReferenceImpl]com.alibaba.nacos.api.exception.NacosException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> failedData = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]4);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]namespace)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]persistService.tenantInfoCountByTenantId([CtVariableReadImpl]namespace) <= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.NAMESPACE_NOT_EXIST, [CtVariableReadImpl]failedData);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ConfigAllInfo> configInfoList = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]ZipUtils.UnZipResult unziped = [CtInvocationImpl][CtTypeAccessImpl]ZipUtils.unzip([CtInvocationImpl][CtVariableReadImpl]file.getBytes());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]ZipUtils.ZipItem metaDataZipItem = [CtInvocationImpl][CtVariableReadImpl]unziped.getMetaDataItem();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> metaDataMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]16);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]metaDataZipItem != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String metaDataStr = [CtInvocationImpl][CtVariableReadImpl]metaDataZipItem.getItemData();
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] metaDataArr = [CtInvocationImpl][CtVariableReadImpl]metaDataStr.split([CtLiteralImpl]"\r\n");
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String metaDataItem : [CtVariableReadImpl]metaDataArr) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] metaDataItemArr = [CtInvocationImpl][CtVariableReadImpl]metaDataItem.split([CtLiteralImpl]"=");
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]metaDataItemArr.length != [CtLiteralImpl]2) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
                        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.METADATA_ILLEGAL, [CtVariableReadImpl]failedData);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]metaDataMap.put([CtArrayReadImpl][CtVariableReadImpl]metaDataItemArr[[CtLiteralImpl]0], [CtArrayReadImpl][CtVariableReadImpl]metaDataItemArr[[CtLiteralImpl]1]);
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]ZipUtils.ZipItem> itemList = [CtInvocationImpl][CtVariableReadImpl]unziped.getZipItemList();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]itemList != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]itemList.isEmpty())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]configInfoList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]itemList.size());
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]ZipUtils.ZipItem item : [CtVariableReadImpl]itemList) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] groupAdnDataId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.getItemName().split([CtLiteralImpl]"/");
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.getItemName().contains([CtLiteralImpl]"/")) || [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]groupAdnDataId.length != [CtLiteralImpl]2)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
                        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.DATA_VALIDATION_FAILED, [CtVariableReadImpl]failedData);
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String group = [CtArrayReadImpl][CtVariableReadImpl]groupAdnDataId[[CtLiteralImpl]0];
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dataId = [CtArrayReadImpl][CtVariableReadImpl]groupAdnDataId[[CtLiteralImpl]1];
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tempDataId = [CtVariableReadImpl]dataId;
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]tempDataId.contains([CtLiteralImpl]".")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]tempDataId = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tempDataId.substring([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]tempDataId.lastIndexOf([CtLiteralImpl]".")) + [CtLiteralImpl]"~") + [CtInvocationImpl][CtVariableReadImpl]tempDataId.substring([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tempDataId.lastIndexOf([CtLiteralImpl]".") + [CtLiteralImpl]1);
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String metaDataId = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]group + [CtLiteralImpl]".") + [CtVariableReadImpl]tempDataId) + [CtLiteralImpl]".app";
                    [CtLocalVariableImpl][CtTypeReferenceImpl]ConfigAllInfo ci = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ConfigAllInfo();
                    [CtInvocationImpl][CtVariableReadImpl]ci.setTenant([CtVariableReadImpl]namespace);
                    [CtInvocationImpl][CtVariableReadImpl]ci.setGroup([CtVariableReadImpl]group);
                    [CtInvocationImpl][CtVariableReadImpl]ci.setDataId([CtVariableReadImpl]dataId);
                    [CtInvocationImpl][CtVariableReadImpl]ci.setContent([CtInvocationImpl][CtVariableReadImpl]item.getItemData());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]metaDataMap.get([CtVariableReadImpl]metaDataId) != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]ci.setAppName([CtInvocationImpl][CtVariableReadImpl]metaDataMap.get([CtVariableReadImpl]metaDataId));
                    }
                    [CtInvocationImpl][CtVariableReadImpl]configInfoList.add([CtVariableReadImpl]ci);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
            [CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.log.error([CtLiteralImpl]"parsing data failed", [CtVariableReadImpl]e);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.PARSING_DATA_FAILED, [CtVariableReadImpl]failedData);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]configInfoList == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]configInfoList.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.DATA_EMPTY, [CtVariableReadImpl]failedData);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String srcIp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getRemoteIp([CtVariableReadImpl]request);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requestIpApp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getAppName([CtVariableReadImpl]request);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.sql.Timestamp time = [CtInvocationImpl][CtTypeAccessImpl]TimeUtils.getCurrentTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> saveResult = [CtInvocationImpl][CtFieldReadImpl]persistService.batchInsertOrUpdate([CtVariableReadImpl]configInfoList, [CtVariableReadImpl]srcUser, [CtVariableReadImpl]srcIp, [CtLiteralImpl]null, [CtVariableReadImpl]time, [CtLiteralImpl]false, [CtVariableReadImpl]policy);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]ConfigInfo configInfo : [CtVariableReadImpl]configInfoList) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]false, [CtInvocationImpl][CtVariableReadImpl]configInfo.getDataId(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getGroup(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getTenant(), [CtInvocationImpl][CtVariableReadImpl]time.getTime()));
            [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.service.trace.ConfigTraceService.logPersistenceEvent([CtInvocationImpl][CtVariableReadImpl]configInfo.getDataId(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getGroup(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getTenant(), [CtVariableReadImpl]requestIpApp, [CtInvocationImpl][CtVariableReadImpl]time.getTime(), [CtTypeAccessImpl]LOCAL_IP, [CtTypeAccessImpl]ConfigTraceService.PERSISTENCE_EVENT_PUB, [CtInvocationImpl][CtVariableReadImpl]configInfo.getContent());
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildSuccessResult([CtLiteralImpl]"导入成功", [CtVariableReadImpl]saveResult);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.PostMapping(params = [CtLiteralImpl]"clone=true")
    public [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.RestResult<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> cloneConfig([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"src_user", required = [CtLiteralImpl]false)
    [CtTypeReferenceImpl]java.lang.String srcUser, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"tenant", required = [CtLiteralImpl]true)
    [CtTypeReferenceImpl]java.lang.String namespace, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestBody(required = [CtLiteralImpl]true)
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.parameters.SameNamespaceCloneConfigBean> configBeansList, [CtParameterImpl][CtAnnotationImpl]@com.alibaba.nacos.config.server.controller.RequestParam(value = [CtLiteralImpl]"policy", defaultValue = [CtLiteralImpl]"ABORT")
    [CtTypeReferenceImpl]SameConfigPolicy policy) throws [CtTypeReferenceImpl]com.alibaba.nacos.api.exception.NacosException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> failedData = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]4);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]configBeansList)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.NO_SELECTED_CONFIG, [CtVariableReadImpl]failedData);
        }
        [CtInvocationImpl][CtVariableReadImpl]configBeansList.removeAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singleton([CtLiteralImpl]null));
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.alibaba.nacos.config.server.controller.ConfigController.NAMESPACE_PUBLIC_KEY.equalsIgnoreCase([CtVariableReadImpl]namespace)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]namespace = [CtLiteralImpl]"";
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]persistService.tenantInfoCountByTenantId([CtVariableReadImpl]namespace) <= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.NAMESPACE_NOT_EXIST, [CtVariableReadImpl]failedData);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> idList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]configBeansList.size());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.parameters.SameNamespaceCloneConfigBean> configBeansMap = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configBeansList.stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]SameNamespaceCloneConfigBean::getCfgId, [CtLambdaImpl]([CtParameterImpl] cfg) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idList.add([CtInvocationImpl][CtVariableReadImpl]cfg.getCfgId());
            [CtReturnImpl]return [CtVariableReadImpl]cfg;
        }, [CtLambdaImpl]([CtParameterImpl] k1,[CtParameterImpl] k2) -> [CtVariableReadImpl]k1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ConfigAllInfo> queryedDataList = [CtInvocationImpl][CtFieldReadImpl]persistService.findAllConfigInfo4Export([CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]idList);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]queryedDataList == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]queryedDataList.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.DATA_EMPTY, [CtVariableReadImpl]failedData);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ConfigAllInfo> configInfoList4Clone = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]queryedDataList.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]ConfigAllInfo ci : [CtVariableReadImpl]queryedDataList) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.alibaba.nacos.config.server.controller.parameters.SameNamespaceCloneConfigBean prarmBean = [CtInvocationImpl][CtVariableReadImpl]configBeansMap.get([CtInvocationImpl][CtVariableReadImpl]ci.getId());
            [CtLocalVariableImpl][CtTypeReferenceImpl]ConfigAllInfo ci4save = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ConfigAllInfo();
            [CtInvocationImpl][CtVariableReadImpl]ci4save.setTenant([CtVariableReadImpl]namespace);
            [CtInvocationImpl][CtVariableReadImpl]ci4save.setType([CtInvocationImpl][CtVariableReadImpl]ci.getType());
            [CtInvocationImpl][CtVariableReadImpl]ci4save.setGroup([CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]prarmBean != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtInvocationImpl][CtVariableReadImpl]prarmBean.getGroup()) ? [CtInvocationImpl][CtVariableReadImpl]prarmBean.getGroup() : [CtInvocationImpl][CtVariableReadImpl]ci.getGroup());
            [CtInvocationImpl][CtVariableReadImpl]ci4save.setDataId([CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]prarmBean != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtInvocationImpl][CtVariableReadImpl]prarmBean.getDataId()) ? [CtInvocationImpl][CtVariableReadImpl]prarmBean.getDataId() : [CtInvocationImpl][CtVariableReadImpl]ci.getDataId());
            [CtInvocationImpl][CtVariableReadImpl]ci4save.setContent([CtInvocationImpl][CtVariableReadImpl]ci.getContent());
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtInvocationImpl][CtVariableReadImpl]ci.getAppName())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ci4save.setAppName([CtInvocationImpl][CtVariableReadImpl]ci.getAppName());
            }
            [CtInvocationImpl][CtVariableReadImpl]configInfoList4Clone.add([CtVariableReadImpl]ci4save);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]configInfoList4Clone.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]failedData.put([CtLiteralImpl]"succCount", [CtLiteralImpl]0);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildResult([CtTypeAccessImpl]ResultCodeEnum.DATA_EMPTY, [CtVariableReadImpl]failedData);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String srcIp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getRemoteIp([CtVariableReadImpl]request);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requestIpApp = [CtInvocationImpl][CtTypeAccessImpl]RequestUtil.getAppName([CtVariableReadImpl]request);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.sql.Timestamp time = [CtInvocationImpl][CtTypeAccessImpl]TimeUtils.getCurrentTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> saveResult = [CtInvocationImpl][CtFieldReadImpl]persistService.batchInsertOrUpdate([CtVariableReadImpl]configInfoList4Clone, [CtVariableReadImpl]srcUser, [CtVariableReadImpl]srcIp, [CtLiteralImpl]null, [CtVariableReadImpl]time, [CtLiteralImpl]false, [CtVariableReadImpl]policy);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]ConfigInfo configInfo : [CtVariableReadImpl]configInfoList4Clone) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.utils.event.EventDispatcher.fireEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.nacos.config.server.service.ConfigDataChangeEvent([CtLiteralImpl]false, [CtInvocationImpl][CtVariableReadImpl]configInfo.getDataId(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getGroup(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getTenant(), [CtInvocationImpl][CtVariableReadImpl]time.getTime()));
            [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.service.trace.ConfigTraceService.logPersistenceEvent([CtInvocationImpl][CtVariableReadImpl]configInfo.getDataId(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getGroup(), [CtInvocationImpl][CtVariableReadImpl]configInfo.getTenant(), [CtVariableReadImpl]requestIpApp, [CtInvocationImpl][CtVariableReadImpl]time.getTime(), [CtTypeAccessImpl]LOCAL_IP, [CtTypeAccessImpl]ConfigTraceService.PERSISTENCE_EVENT_PUB, [CtInvocationImpl][CtVariableReadImpl]configInfo.getContent());
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.nacos.config.server.result.ResultBuilder.buildSuccessResult([CtLiteralImpl]"克隆成功", [CtVariableReadImpl]saveResult);
    }
}
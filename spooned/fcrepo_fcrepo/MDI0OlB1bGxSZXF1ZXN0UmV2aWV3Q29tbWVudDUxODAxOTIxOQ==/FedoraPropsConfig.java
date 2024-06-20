[CompilationUnitImpl][CtCommentImpl]/* Licensed to DuraSpace under one or more contributor license agreements.
See the NOTICE file distributed with this work for additional information
regarding copyright ownership.

DuraSpace licenses this file to you under the Apache License,
Version 2.0 (the "License"); you may not use this file except in
compliance with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.fcrepo.config;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Value;
[CtUnresolvedImport]import org.springframework.context.annotation.Configuration;
[CtImportImpl]import java.nio.file.Files;
[CtImportImpl]import java.nio.file.Path;
[CtUnresolvedImport]import javax.annotation.PostConstruct;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.springframework.context.annotation.PropertySource;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtClassImpl][CtJavaDocImpl]/**
 * General Fedora properties
 *
 * @author pwinckles
 * @since 6.0.0
 */
[CtAnnotationImpl]@org.springframework.context.annotation.Configuration
[CtAnnotationImpl]@org.springframework.context.annotation.PropertySource(value = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:${" + [CtFieldReadImpl][CtTypeAccessImpl]org.fcrepo.config.FedoraPropsConfig.[CtFieldReferenceImpl]FCREPO_CONFIG_FILE) + [CtLiteralImpl]"}", ignoreResourceNotFound = [CtLiteralImpl]true)
public class FedoraPropsConfig {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.class);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_CONFIG_FILE = [CtLiteralImpl]"fcrepo.config-file";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_HOME = [CtLiteralImpl]"fcrepo.home";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_JMS_HOST = [CtLiteralImpl]"fcrepo.jms.host";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_DYNAMIC_JMS_PORT = [CtLiteralImpl]"fcrepo.dynamic.jms.port";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_DYNAMIC_STOMP_PORT = [CtLiteralImpl]"fcrepo.dynamic.stomp.port";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_ACTIVEMQ_CONFIGURATION = [CtLiteralImpl]"fcrepo.activemq.configuration";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_NAMESPACE_REGISTRY = [CtLiteralImpl]"fcrepo.namespace.registry";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FCREPO_EXTERNAL_CONTENT_ALLOWED = [CtLiteralImpl]"fcrepo.external.content.allowed";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DATA_DIR = [CtLiteralImpl]"data";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ACTIVE_MQ_DIR = [CtLiteralImpl]"ActiveMQ/kahadb";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FCREPO_ACTIVEMQ_DIRECTORY = [CtLiteralImpl]"fcrepo.activemq.directory";

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_HOME) + [CtLiteralImpl]":fcrepo-home}")
    private [CtTypeReferenceImpl]java.nio.file.Path fedoraHome;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"#{fedoraPropsConfig.fedoraHome.resolve('" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.DATA_DIR) + [CtLiteralImpl]"')}")
    private [CtTypeReferenceImpl]java.nio.file.Path fedoraData;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_JMS_HOST) + [CtLiteralImpl]":localhost}")
    private [CtTypeReferenceImpl]java.lang.String jmsHost;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_DYNAMIC_JMS_PORT) + [CtLiteralImpl]":61616}")
    private [CtTypeReferenceImpl]java.lang.String jmsPort;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_DYNAMIC_STOMP_PORT) + [CtLiteralImpl]":61613}")
    private [CtTypeReferenceImpl]java.lang.String stompPort;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_ACTIVEMQ_CONFIGURATION) + [CtLiteralImpl]":classpath:/config/activemq.xml}")
    private [CtTypeReferenceImpl]java.lang.String activeMQConfiguration;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_ACTIVEMQ_DIRECTORY) + [CtLiteralImpl]":#{fedoraPropsConfig.fedoraData.resolve('") + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.ACTIVE_MQ_DIR) + [CtLiteralImpl]"')") + [CtLiteralImpl]".toAbsolutePath().toString()}}")
    private [CtTypeReferenceImpl]java.lang.String activeMqDirectory;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_NAMESPACE_REGISTRY) + [CtLiteralImpl]":classpath:/namespaces.yml}")
    private [CtTypeReferenceImpl]java.lang.String namespaceRegistry;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"${" + [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_EXTERNAL_CONTENT_ALLOWED) + [CtLiteralImpl]":#{null}}")
    private [CtTypeReferenceImpl]java.lang.String externalContentAllowed;

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.PostConstruct
    private [CtTypeReferenceImpl]void postConstruct() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.LOGGER.info([CtLiteralImpl]"Fedora home: {}", [CtFieldReadImpl]fedoraHome);
        [CtInvocationImpl][CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.LOGGER.debug([CtLiteralImpl]"Fedora home data: {}", [CtFieldReadImpl]fedoraData);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtFieldReadImpl]fedoraHome);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to create Fedora home directory at %s." + [CtLiteralImpl]" Fedora home can be configured by setting the %s property.", [CtFieldReadImpl]fedoraHome, [CtFieldReadImpl]org.fcrepo.config.FedoraPropsConfig.FCREPO_HOME), [CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtFieldReadImpl]fedoraData);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Path to Fedora home directory
     */
    public [CtTypeReferenceImpl]java.nio.file.Path getFedoraHome() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]fedoraHome;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the path to the Fedora home directory -- should only be used for testing purposes.
     *
     * @param fedoraHome
     * 		Path to Fedora home directory
     */
    public [CtTypeReferenceImpl]void setFedoraHome([CtParameterImpl]final [CtTypeReferenceImpl]java.nio.file.Path fedoraHome) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fedoraHome = [CtVariableReadImpl]fedoraHome;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Path to Fedora home data directory
     */
    public [CtTypeReferenceImpl]java.nio.file.Path getFedoraData() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]fedoraData;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the path to the Fedora home data directory -- should only be used for testing purposes.
     *
     * @param fedoraData
     * 		Path to Fedora home data directory
     */
    public [CtTypeReferenceImpl]void setFedoraData([CtParameterImpl]final [CtTypeReferenceImpl]java.nio.file.Path fedoraData) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fedoraData = [CtVariableReadImpl]fedoraData;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The JMS host
     */
    public [CtTypeReferenceImpl]java.lang.String getJmsHost() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]jmsHost;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The JMS/Open Wire port
     */
    public [CtTypeReferenceImpl]java.lang.String getJmsPort() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]jmsPort;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The STOMP protocol port
     */
    public [CtTypeReferenceImpl]java.lang.String getStompPort() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]stompPort;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The ActiveMQ data directory
     */
    public [CtTypeReferenceImpl]java.lang.String getActiveMqDirectory() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]activeMqDirectory;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The path to the ActiveMQ xml spring configuration.
     */
    public [CtTypeReferenceImpl]java.lang.String getActiveMQConfiguration() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]activeMQConfiguration;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The path to the allowed external content pattern definitions.
     */
    public [CtTypeReferenceImpl]java.lang.String getExternalContentAllowed() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]externalContentAllowed;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The path to the namespace registry file.
     */
    public [CtTypeReferenceImpl]java.lang.String getNamespaceRegistry() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]namespaceRegistry;
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Red Hat, Inc. and/or its affiliates.

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
[CtPackageDeclarationImpl]package org.kie.server.spring.boot.autoconfiguration.audit.replication;
[CtUnresolvedImport]import com.thoughtworks.xstream.XStream;
[CtUnresolvedImport]import org.jbpm.springboot.autoconfigure.EntityManagerFactoryHelper;
[CtUnresolvedImport]import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
[CtUnresolvedImport]import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.springframework.boot.autoconfigure.AutoConfigureAfter;
[CtUnresolvedImport]import org.kie.api.event.process.ProcessEventListener;
[CtUnresolvedImport]import org.kie.soup.xstream.XStreamUtils;
[CtUnresolvedImport]import org.springframework.boot.context.properties.EnableConfigurationProperties;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Qualifier;
[CtUnresolvedImport]import org.springframework.jms.core.JmsTemplate;
[CtUnresolvedImport]import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.kie.server.springboot.autoconfiguration.KieServerProperties;
[CtUnresolvedImport]import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
[CtImportImpl]import javax.sql.DataSource;
[CtUnresolvedImport]import org.springframework.context.ApplicationContext;
[CtUnresolvedImport]import org.springframework.context.annotation.Configuration;
[CtUnresolvedImport]import org.jbpm.springboot.autoconfigure.JBPMAutoConfiguration;
[CtUnresolvedImport]import org.kie.api.task.TaskLifeCycleEventListener;
[CtUnresolvedImport]import javax.persistence.EntityManagerFactory;
[CtUnresolvedImport]import org.springframework.core.env.Environment;
[CtUnresolvedImport]import org.springframework.context.annotation.Bean;
[CtUnresolvedImport]import org.kie.server.services.impl.KieServerImpl;
[CtUnresolvedImport]import javax.jms.ConnectionFactory;
[CtUnresolvedImport]import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
[CtClassImpl][CtAnnotationImpl]@org.springframework.context.annotation.Configuration
[CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnClass([CtNewArrayImpl]{ [CtFieldReadImpl]org.kie.server.services.impl.KieServerImpl.class })
[CtAnnotationImpl]@org.springframework.boot.autoconfigure.AutoConfigureAfter([CtNewArrayImpl]{ [CtFieldReadImpl]org.jbpm.springboot.autoconfigure.JBPMAutoConfiguration.class })
[CtAnnotationImpl]@org.springframework.boot.context.properties.EnableConfigurationProperties([CtFieldReadImpl]org.kie.server.springboot.autoconfiguration.KieServerProperties.class)
public class AuditDataReplicationKieServerAutoConfiguration {
    [CtFieldImpl]private static [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationKieServerAutoConfiguration.class);

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.springframework.context.ApplicationContext applicationContext;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.springframework.core.env.Environment env;

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"jmsSender")
    public [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.JMSSender createJMSQueueSender() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]env.containsProperty([CtLiteralImpl]"kieserver.audit-replication.queue")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String endpointName = [CtInvocationImpl][CtFieldReadImpl]env.getProperty([CtLiteralImpl]"kieserver.audit-replication.queue");
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.JMSSender([CtVariableReadImpl]endpointName);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String endpointName = [CtInvocationImpl][CtFieldReadImpl]env.getProperty([CtLiteralImpl]"kieserver.audit-replication.topic");
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.JMSSender([CtVariableReadImpl]endpointName);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"jmsTemplate")
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = [CtLiteralImpl]"kieserver.audit-replication.topic")
    public [CtTypeReferenceImpl]org.springframework.jms.core.JmsTemplate createJMSTopicTemplate([CtParameterImpl][CtTypeReferenceImpl]javax.jms.ConnectionFactory connectionFactory) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jms.core.JmsTemplate jmsTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jms.core.JmsTemplate([CtVariableReadImpl]connectionFactory);
        [CtInvocationImpl][CtVariableReadImpl]jmsTemplate.setPubSubDomain([CtLiteralImpl]true);
        [CtReturnImpl]return [CtVariableReadImpl]jmsTemplate;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"xstreamBean")
    public [CtTypeReferenceImpl]com.thoughtworks.xstream.XStream createXStream() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.thoughtworks.xstream.XStream xstream = [CtInvocationImpl][CtTypeAccessImpl]org.kie.soup.xstream.XStreamUtils.createTrustingXStream();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] voidDeny = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"void.class", [CtLiteralImpl]"Void.class" };
        [CtInvocationImpl][CtVariableReadImpl]xstream.denyTypes([CtVariableReadImpl]voidDeny);
        [CtReturnImpl]return [CtVariableReadImpl]xstream;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean(name = [CtLiteralImpl]"auditEntityManagerFactory")
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"auditEntityManagerFactory")
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = [CtLiteralImpl]"kieserver.audit-replication.consumer", havingValue = [CtLiteralImpl]"true")
    public [CtTypeReferenceImpl]org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean entityManagerFactory([CtParameterImpl][CtTypeReferenceImpl]javax.sql.DataSource dataSource, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.boot.autoconfigure.orm.jpa.JpaProperties jpaProperties) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.jbpm.springboot.autoconfigure.EntityManagerFactoryHelper.create([CtFieldReadImpl]applicationContext, [CtVariableReadImpl]dataSource, [CtVariableReadImpl]jpaProperties, [CtLiteralImpl]"org.jbpm.audit", [CtLiteralImpl]"classpath:/META-INF/jbpm-audit-persistence.xml");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"auditDataReplicationProcessEventListenerProducer")
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = [CtLiteralImpl]"kieserver.audit-replication.producer", havingValue = [CtLiteralImpl]"true")
    public [CtTypeReferenceImpl]org.kie.api.event.process.ProcessEventListener createrocessEventListenerProducer() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationKieServerAutoConfiguration.logger.info([CtLiteralImpl]"Adding AuditDataReplicationProcessEvent from data replication");
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationProcessEventProducer();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"auditDataReplicationTaskLifeCycleEventListenerProducer")
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = [CtLiteralImpl]"kieserver.audit-replication.producer", havingValue = [CtLiteralImpl]"true")
    public [CtTypeReferenceImpl]org.kie.api.task.TaskLifeCycleEventListener createTaskLifeCycleEventListener([CtParameterImpl][CtTypeReferenceImpl]javax.persistence.EntityManagerFactory emf) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationKieServerAutoConfiguration.logger.info([CtLiteralImpl]"Adding AuditDataReplicationTaskLifeCycleEventListenerProducer from data replication");
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationTaskLifeCycleEventListenerProducer();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"auditDataReplicationBAMTaskSumaryListenerProducer")
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = [CtLiteralImpl]"kieserver.audit-replication.producer", havingValue = [CtLiteralImpl]"true")
    public [CtTypeReferenceImpl]org.kie.api.task.TaskLifeCycleEventListener createBAMTaskSumaryListener([CtParameterImpl][CtTypeReferenceImpl]javax.persistence.EntityManagerFactory emf) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationKieServerAutoConfiguration.logger.info([CtLiteralImpl]"Adding AuditDataReplicationBAMTaskSumaryListenerProducer from data replication");
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationBAMTaskSumaryListenerProducer();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(name = [CtLiteralImpl]"auditDataReplicationQueueConsumer")
    [CtAnnotationImpl]@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = [CtLiteralImpl]"kieserver.audit-replication.consumer", havingValue = [CtLiteralImpl]"true")
    public [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AbstractAuditDataReplicationJMSConsumer createAuditDataReplicationQueueConsumer([CtParameterImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Qualifier([CtLiteralImpl]"auditEntityManagerFactory")
    [CtTypeReferenceImpl]javax.persistence.EntityManagerFactory emf) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationKieServerAutoConfiguration.logger.info([CtLiteralImpl]"Adding auditDataReplicationConsumer from data replication");
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]env.containsProperty([CtLiteralImpl]"kieserver.audit-replication.queue")) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationJMSQueueConsumer([CtVariableReadImpl]emf);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.server.spring.boot.autoconfiguration.audit.replication.AuditDataReplicationJMSTopicConsumer([CtVariableReadImpl]emf);
        }
    }
}
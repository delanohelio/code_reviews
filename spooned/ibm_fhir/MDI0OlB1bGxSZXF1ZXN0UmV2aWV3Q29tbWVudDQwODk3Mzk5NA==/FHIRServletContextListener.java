[CompilationUnitImpl][CtCommentImpl]/* (C) Copyright IBM Corp. 2016, 2020

SPDX-License-Identifier: Apache-2.0
 */
[CtPackageDeclarationImpl]package com.ibm.fhir.server.listener;
[CtUnresolvedImport]import com.ibm.fhir.search.util.SearchUtil;
[CtUnresolvedImport]import static com.ibm.fhir.config.FHIRConfiguration.PROPERTY_CHECK_REFERENCE_TYPES;
[CtUnresolvedImport]import com.ibm.fhir.config.FHIRConfigHelper;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import com.ibm.fhir.server.registry.ServerRegistryResourceProvider;
[CtUnresolvedImport]import static com.ibm.fhir.config.FHIRConfiguration.PROPERTY_WEBSOCKET_ENABLED;
[CtImportImpl]import java.util.logging.Logger;
[CtUnresolvedImport]import com.ibm.fhir.model.util.FHIRUtil;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.util.DerbyBootstrapper;
[CtUnresolvedImport]import org.owasp.encoder.Encode;
[CtUnresolvedImport]import static com.ibm.fhir.config.FHIRConfiguration.PROPERTY_JDBC_BOOTSTRAP_DB;
[CtUnresolvedImport]import com.ibm.fhir.persistence.helper.FHIRPersistenceHelper;
[CtUnresolvedImport]import com.ibm.fhir.notifications.kafka.impl.FHIRNotificationKafkaPublisher;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.ibm.fhir.database.utils.derby.DerbyServerPropertiesMgr;
[CtUnresolvedImport]import javax.servlet.ServletContextListener;
[CtUnresolvedImport]import com.ibm.fhir.config.FHIRRequestContext;
[CtImportImpl]import javax.naming.InitialContext;
[CtImportImpl]import javax.sql.DataSource;
[CtUnresolvedImport]import static com.ibm.fhir.config.FHIRConfiguration.PROPERTY_KAFKA_ENABLED;
[CtUnresolvedImport]import javax.servlet.annotation.WebListener;
[CtUnresolvedImport]import com.ibm.fhir.registry.FHIRRegistry;
[CtUnresolvedImport]import javax.servlet.ServletContextEvent;
[CtUnresolvedImport]import com.ibm.fhir.model.config.FHIRModelConfig;
[CtUnresolvedImport]import static com.ibm.fhir.config.FHIRConfiguration.PROPERTY_SERVER_REGISTRY_RESOURCE_PROVIDER_ENABLED;
[CtUnresolvedImport]import com.ibm.fhir.config.FHIRConfiguration;
[CtUnresolvedImport]import com.ibm.fhir.config.PropertyGroup;
[CtUnresolvedImport]import com.ibm.fhir.persistence.interceptor.impl.FHIRPersistenceInterceptorMgr;
[CtUnresolvedImport]import static com.ibm.fhir.config.FHIRConfiguration.PROPERTY_KAFKA_TOPICNAME;
[CtUnresolvedImport]import static com.ibm.fhir.config.FHIRConfiguration.PROPERTY_KAFKA_CONNECTIONPROPS;
[CtUnresolvedImport]import com.ibm.fhir.notification.websocket.impl.FHIRNotificationServiceEndpointConfig;
[CtImportImpl]import java.util.logging.Level;
[CtUnresolvedImport]import javax.websocket.server.ServerContainer;
[CtUnresolvedImport]import com.ibm.fhir.operation.registry.FHIROperationRegistry;
[CtUnresolvedImport]import com.ibm.fhir.config.PropertyGroup.PropertyEntry;
[CtClassImpl][CtAnnotationImpl]@javax.servlet.annotation.WebListener([CtLiteralImpl]"IBM FHIR Server Servlet Context Listener")
public class FHIRServletContextListener implements [CtTypeReferenceImpl]javax.servlet.ServletContextListener {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.logging.Logger log = [CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.class.getName());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ATTRNAME_WEBSOCKET_SERVERCONTAINER = [CtLiteralImpl]"javax.websocket.server.ServerContainer";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DEFAULT_KAFKA_TOPICNAME = [CtLiteralImpl]"fhirNotifications";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FHIR_SERVER_INIT_COMPLETE = [CtLiteralImpl]"com.ibm.fhir.webappInitComplete";

    [CtFieldImpl]private static [CtTypeReferenceImpl]com.ibm.fhir.notifications.kafka.impl.FHIRNotificationKafkaPublisher kafkaPublisher = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void contextInitialized([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.ServletContextEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINER)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.entering([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.class.getName(), [CtLiteralImpl]"contextInitialized");
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Initialize our "initComplete" flag to false.
            [CtInvocationImpl][CtVariableReadImpl]event.getServletContext().setAttribute([CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.FHIR_SERVER_INIT_COMPLETE, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.config.PropertyGroup fhirConfig = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.config.FHIRConfiguration.getInstance().loadConfiguration();
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.fine([CtBinaryOperatorImpl][CtLiteralImpl]"Current working directory: " + [CtInvocationImpl][CtTypeAccessImpl]org.owasp.encoder.Encode.forHtml([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.dir")));
            [CtInvocationImpl][CtCommentImpl]/* The following inits are intended to load the FHIRUtil and SearchUtil into the classloader.
            Subsequently, the code activates the static values (and maps).
             */
            [CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.fine([CtLiteralImpl]"Initializing FHIRUtil...");
            [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.model.util.FHIRUtil.init();
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.fine([CtLiteralImpl]"Initializing SearchUtil...");
            [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.search.util.SearchUtil.init();
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.fine([CtLiteralImpl]"Initializing FHIROperationRegistry...");
            [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.operation.registry.FHIROperationRegistry.getInstance();
            [CtLocalVariableImpl][CtCommentImpl]// For any singleton resources that need to be shared among our resource class instances,
            [CtCommentImpl]// we'll add them to our servlet context so that the resource class can easily retrieve them.
            [CtCommentImpl]// Set the shared FHIRPersistenceHelper.
            [CtTypeReferenceImpl]com.ibm.fhir.persistence.helper.FHIRPersistenceHelper persistenceHelper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.helper.FHIRPersistenceHelper();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getServletContext().setAttribute([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.helper.FHIRPersistenceHelper.class.getName(), [CtVariableReadImpl]persistenceHelper);
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.fine([CtLiteralImpl]"Set shared persistence helper on servlet context.");
            [CtLocalVariableImpl][CtCommentImpl]// If websocket notifications are enabled, then initialize the endpoint.
            [CtTypeReferenceImpl]java.lang.Boolean websocketEnabled = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getBooleanProperty([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_WEBSOCKET_ENABLED, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
            [CtIfImpl]if ([CtVariableReadImpl]websocketEnabled) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Initializing WebSocket notification publisher.");
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.websocket.server.ServerContainer container = [CtInvocationImpl](([CtTypeReferenceImpl]javax.websocket.server.ServerContainer) ([CtInvocationImpl][CtVariableReadImpl]event.getServletContext().getAttribute([CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.ATTRNAME_WEBSOCKET_SERVERCONTAINER)));
                [CtInvocationImpl][CtVariableReadImpl]container.addEndpoint([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.notification.websocket.impl.FHIRNotificationServiceEndpointConfig());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Bypassing WebSocket notification init.");
            }
            [CtLocalVariableImpl][CtCommentImpl]// If Kafka notifications are enabled, start up our Kafka notification publisher.
            [CtTypeReferenceImpl]java.lang.Boolean kafkaEnabled = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getBooleanProperty([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_KAFKA_ENABLED, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
            [CtIfImpl]if ([CtVariableReadImpl]kafkaEnabled) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Retrieve the topic name.
                [CtTypeReferenceImpl]java.lang.String topicName = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getStringProperty([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_KAFKA_TOPICNAME, [CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.DEFAULT_KAFKA_TOPICNAME);
                [CtLocalVariableImpl][CtCommentImpl]// Gather up the Kafka connection properties.
                [CtTypeReferenceImpl]java.util.Properties kafkaProps = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.config.PropertyGroup pg = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getPropertyGroup([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_KAFKA_CONNECTIONPROPS);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pg != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.ibm.fhir.config.PropertyGroup.PropertyEntry> connectionProps = [CtInvocationImpl][CtVariableReadImpl]pg.getProperties();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]connectionProps != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.config.PropertyGroup.PropertyEntry entry : [CtVariableReadImpl]connectionProps) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]kafkaProps.setProperty([CtInvocationImpl][CtVariableReadImpl]entry.getName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().toString());
                        }
                    }
                }
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Initializing Kafka notification publisher.");
                [CtAssignmentImpl][CtFieldWriteImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.kafkaPublisher = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.notifications.kafka.impl.FHIRNotificationKafkaPublisher([CtVariableReadImpl]topicName, [CtVariableReadImpl]kafkaProps);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Bypassing Kafka notification init.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean checkReferenceTypes = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getBooleanProperty([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_CHECK_REFERENCE_TYPES, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE);
            [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.model.config.FHIRModelConfig.setCheckReferenceTypes([CtVariableReadImpl]checkReferenceTypes);
            [CtInvocationImpl]bootstrapDerbyDatabases([CtVariableReadImpl]fhirConfig);
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.fine([CtLiteralImpl]"Initializing FHIRRegistry...");
            [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.registry.FHIRRegistry.getInstance();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean serverRegistryResourceProviderEnabled = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getBooleanProperty([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_SERVER_REGISTRY_RESOURCE_PROVIDER_ENABLED, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
            [CtIfImpl]if ([CtVariableReadImpl]serverRegistryResourceProviderEnabled) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Registering ServerRegistryResourceProvider...");
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.server.registry.ServerRegistryResourceProvider provider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.server.registry.ServerRegistryResourceProvider([CtVariableReadImpl]persistenceHelper);
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.registry.FHIRRegistry.getInstance().register([CtVariableReadImpl]provider);
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.persistence.interceptor.impl.FHIRPersistenceInterceptorMgr.getInstance().addInterceptor([CtVariableReadImpl]provider);
            }
            [CtInvocationImpl][CtCommentImpl]// Finally, set our "initComplete" flag to true.
            [CtInvocationImpl][CtVariableReadImpl]event.getServletContext().setAttribute([CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.FHIR_SERVER_INIT_COMPLETE, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtLiteralImpl]"Encountered an exception while initializing the servlet context.";
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtVariableReadImpl]msg, [CtVariableReadImpl]t);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]msg, [CtVariableReadImpl]t);
        } finally [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINER)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.exiting([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.class.getName(), [CtLiteralImpl]"contextInitialized");
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Bootstraps derby databases during server startup if requested.
     */
    private [CtTypeReferenceImpl]void bootstrapDerbyDatabases([CtParameterImpl][CtTypeReferenceImpl]com.ibm.fhir.config.PropertyGroup fhirConfig) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean performDbBootstrap = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getBooleanProperty([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_JDBC_BOOTSTRAP_DB, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
        [CtIfImpl]if ([CtVariableReadImpl]performDbBootstrap) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Performing Derby database bootstrapping...");
            [CtInvocationImpl][CtCommentImpl]// Start derby with debug off by default.
            [CtTypeAccessImpl]com.ibm.fhir.database.utils.derby.DerbyServerPropertiesMgr.setServerProperties([CtLiteralImpl]false);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String datasourceJndiName = [CtInvocationImpl][CtVariableReadImpl]fhirConfig.getStringProperty([CtTypeAccessImpl]FHIRConfiguration.PROPERTY_JDBC_DATASOURCE_JNDINAME, [CtLiteralImpl]"jdbc/fhirDB");
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.naming.InitialContext ctxt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.naming.InitialContext();
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.sql.DataSource ds = [CtInvocationImpl](([CtTypeReferenceImpl]javax.sql.DataSource) ([CtVariableReadImpl]ctxt.lookup([CtVariableReadImpl]datasourceJndiName)));
            [CtInvocationImpl]bootstrapDb([CtLiteralImpl]"default", [CtLiteralImpl]"default", [CtVariableReadImpl]ds);
            [CtInvocationImpl]bootstrapDb([CtLiteralImpl]"tenant1", [CtLiteralImpl]"profile", [CtVariableReadImpl]ds);
            [CtInvocationImpl]bootstrapDb([CtLiteralImpl]"tenant1", [CtLiteralImpl]"reference", [CtVariableReadImpl]ds);
            [CtInvocationImpl]bootstrapDb([CtLiteralImpl]"tenant1", [CtLiteralImpl]"study1", [CtVariableReadImpl]ds);
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Finished Derby database bootstrapping...");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtLiteralImpl]"Derby database bootstrapping is disabled.");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Bootstraps the database specified by tenantId and dsId, assuming the specified datastore definition can be
     * retrieved from the configuration.
     */
    private [CtTypeReferenceImpl]void bootstrapDb([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dsId, [CtParameterImpl][CtTypeReferenceImpl]javax.sql.DataSource ds) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.config.FHIRRequestContext.set([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.config.FHIRRequestContext([CtVariableReadImpl]tenantId, [CtVariableReadImpl]dsId));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.config.PropertyGroup pg = [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.config.FHIRConfigHelper.getPropertyGroup([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]com.ibm.fhir.config.FHIRConfiguration.PROPERTY_DATASOURCES + [CtLiteralImpl]"/") + [CtVariableReadImpl]dsId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pg != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String type = [CtInvocationImpl][CtVariableReadImpl]pg.getStringProperty([CtLiteralImpl]"type");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]type.isEmpty())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.toLowerCase().equals([CtLiteralImpl]"derby") || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.toLowerCase().equals([CtLiteralImpl]"derby_network_server"))) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Bootstrapping database for tenantId/dsId: " + [CtVariableReadImpl]tenantId) + [CtLiteralImpl]"/") + [CtVariableReadImpl]dsId);
                [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.persistence.jdbc.util.DerbyBootstrapper.bootstrapDb([CtVariableReadImpl]ds);
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Finished bootstrapping database for tenantId/dsId: " + [CtVariableReadImpl]tenantId) + [CtLiteralImpl]"/") + [CtVariableReadImpl]dsId);
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.config.FHIRRequestContext.remove();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void contextDestroyed([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.ServletContextEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINER)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.entering([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.class.getName(), [CtLiteralImpl]"contextDestroyed");
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Set our "initComplete" flag back to false.
            [CtInvocationImpl][CtVariableReadImpl]event.getServletContext().setAttribute([CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.FHIR_SERVER_INIT_COMPLETE, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
            [CtIfImpl][CtCommentImpl]// If we previously initialized the Kafka publisher, then shut it down now.
            if ([CtBinaryOperatorImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.kafkaPublisher != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.kafkaPublisher.shutdown();
                [CtAssignmentImpl][CtFieldWriteImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.kafkaPublisher = [CtLiteralImpl]null;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
        } finally [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINER)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.log.exiting([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.server.listener.FHIRServletContextListener.class.getName(), [CtLiteralImpl]"contextDestroyed");
            }
        }
    }
}
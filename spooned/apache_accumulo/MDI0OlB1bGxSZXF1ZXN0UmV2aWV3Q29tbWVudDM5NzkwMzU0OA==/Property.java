[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.accumulo.core.conf;
[CtImportImpl]import java.util.EnumSet;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.accumulo.start.classloader.vfs.AccumuloVFSClassLoader;
[CtUnresolvedImport]import org.apache.accumulo.core.metadata.MetadataTable;
[CtUnresolvedImport]import org.apache.accumulo.core.util.format.DefaultFormatter;
[CtUnresolvedImport]import org.apache.accumulo.core.Constants;
[CtUnresolvedImport]import org.apache.accumulo.start.classloader.AccumuloClassLoader;
[CtUnresolvedImport]import org.apache.accumulo.core.spi.scan.SimpleScanDispatcher;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.accumulo.core.iterators.IteratorUtil.IteratorScope;
[CtUnresolvedImport]import org.apache.accumulo.core.iteratorsImpl.system.DeletingIterator;
[CtImportImpl]import java.lang.annotation.Annotation;
[CtUnresolvedImport]import org.apache.accumulo.core.constraints.NoDeleteConstraint;
[CtUnresolvedImport]import org.apache.accumulo.core.client.security.tokens.PasswordToken;
[CtImportImpl]import java.util.Map.Entry;
[CtUnresolvedImport]import org.apache.accumulo.core.file.rfile.RFile;
[CtUnresolvedImport]import org.apache.accumulo.core.util.interpret.DefaultScanInterpreter;
[CtUnresolvedImport]import org.apache.accumulo.core.spi.scan.ScanDispatcher;
[CtUnresolvedImport]import org.apache.accumulo.core.spi.scan.ScanPrioritizer;
[CtUnresolvedImport]import com.google.common.base.Preconditions;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtEnumImpl]public enum Property {

    [CtEnumValueImpl][CtCommentImpl]// SSL properties local to each node (see also instance.ssl.enabled which must be consistent
    [CtCommentImpl]// across all nodes in an instance)
    RPC_PREFIX([CtLiteralImpl]"rpc.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtLiteralImpl]"Properties in this category related to the configuration of SSL keys for" + [CtLiteralImpl]" RPC. See also instance.ssl.enabled"),
    [CtEnumValueImpl]RPC_SSL_KEYSTORE_PATH([CtLiteralImpl]"rpc.javax.net.ssl.keyStore", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.PATH, [CtLiteralImpl]"Path of the keystore file for the server's private SSL key"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    RPC_SSL_KEYSTORE_PASSWORD([CtLiteralImpl]"rpc.javax.net.ssl.keyStorePassword", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"Password used to encrypt the SSL private keystore. " + [CtLiteralImpl]"Leave blank to use the Accumulo instance secret"),
    [CtEnumValueImpl]RPC_SSL_KEYSTORE_TYPE([CtLiteralImpl]"rpc.javax.net.ssl.keyStoreType", [CtLiteralImpl]"jks", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Type of SSL keystore"),
    [CtEnumValueImpl]RPC_SSL_TRUSTSTORE_PATH([CtLiteralImpl]"rpc.javax.net.ssl.trustStore", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.PATH, [CtLiteralImpl]"Path of the truststore file for the root cert"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    RPC_SSL_TRUSTSTORE_PASSWORD([CtLiteralImpl]"rpc.javax.net.ssl.trustStorePassword", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Password used to encrypt the SSL truststore. Leave blank to use no password"),
    [CtEnumValueImpl]RPC_SSL_TRUSTSTORE_TYPE([CtLiteralImpl]"rpc.javax.net.ssl.trustStoreType", [CtLiteralImpl]"jks", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Type of SSL truststore"),
    [CtEnumValueImpl]RPC_USE_JSSE([CtLiteralImpl]"rpc.useJsse", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Use JSSE system properties to configure SSL rather than the " + [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.RPC_PREFIX.getKey()) + [CtLiteralImpl]"javax.net.ssl.* Accumulo properties"),
    [CtEnumValueImpl]RPC_SSL_CIPHER_SUITES([CtLiteralImpl]"rpc.ssl.cipher.suites", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Comma separated list of cipher suites that can be used by accepted connections"),
    [CtEnumValueImpl]RPC_SSL_ENABLED_PROTOCOLS([CtLiteralImpl]"rpc.ssl.server.enabled.protocols", [CtLiteralImpl]"TLSv1.2", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Comma separated list of protocols that can be used to accept connections"),
    [CtEnumValueImpl]RPC_SSL_CLIENT_PROTOCOL([CtLiteralImpl]"rpc.ssl.client.protocol", [CtLiteralImpl]"TLSv1.2", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"The protocol used to connect to a secure server, must be in the list of enabled protocols " + [CtLiteralImpl]"on the server side (rpc.ssl.server.enabled.protocols)"),
    [CtEnumValueImpl][CtJavaDocImpl]/**
     *
     * @since 1.7.0
     */
    RPC_SASL_QOP([CtLiteralImpl]"rpc.sasl.qop", [CtLiteralImpl]"auth", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"The quality of protection to be used with SASL. Valid values are 'auth', 'auth-int'," + [CtLiteralImpl]" and 'auth-conf'"),
    [CtEnumValueImpl][CtCommentImpl]// instance properties (must be the same for every node in an instance)
    INSTANCE_PREFIX([CtLiteralImpl]"instance.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtLiteralImpl]"Properties in this category must be consistent throughout a cloud. " + [CtLiteralImpl]"This is enforced and servers won't be able to communicate if these differ."),
    [CtEnumValueImpl]INSTANCE_ZK_HOST([CtLiteralImpl]"instance.zookeeper.host", [CtLiteralImpl]"localhost:2181", [CtFieldReadImpl]PropertyType.HOSTLIST, [CtLiteralImpl]"Comma separated list of zookeeper servers"),
    [CtEnumValueImpl]INSTANCE_ZK_TIMEOUT([CtLiteralImpl]"instance.zookeeper.timeout", [CtLiteralImpl]"30s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Zookeeper session timeout; " + [CtLiteralImpl]"max value when represented as milliseconds should be no larger than ") + [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    INSTANCE_SECRET([CtLiteralImpl]"instance.secret", [CtLiteralImpl]"DEFAULT", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"A secret unique to a given instance that all servers must know in order" + [CtLiteralImpl]" to communicate with one another. It should be changed prior to the") + [CtLiteralImpl]" initialization of Accumulo. To change it after Accumulo has been") + [CtLiteralImpl]" initialized, use the ChangeSecret tool and then update accumulo.properties") + [CtLiteralImpl]" everywhere. Before using the ChangeSecret tool, make sure Accumulo is not") + [CtLiteralImpl]" running and you are logged in as the user that controls Accumulo files in") + [CtLiteralImpl]" HDFS. To use the ChangeSecret tool, run the command: ./bin/accumulo") + [CtLiteralImpl]" org.apache.accumulo.server.util.ChangeSecret"),
    [CtEnumValueImpl]INSTANCE_VOLUMES([CtLiteralImpl]"instance.volumes", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"A comma separated list of dfs uris to use. Files will be stored across" + [CtLiteralImpl]" these filesystems. If this is empty, then instance.dfs.uri will be used.") + [CtLiteralImpl]" After adding uris to this list, run 'accumulo init --add-volume' and then") + [CtLiteralImpl]" restart tservers. If entries are removed from this list then tservers") + [CtLiteralImpl]" will need to be restarted. After a uri is removed from the list Accumulo") + [CtLiteralImpl]" will not create new files in that location, however Accumulo can still") + [CtLiteralImpl]" reference files created at that location before the config change. To use") + [CtLiteralImpl]" a comma or other reserved characters in a URI use standard URI hex") + [CtLiteralImpl]" encoding. For example replace commas with %2C."),
    [CtEnumValueImpl]INSTANCE_VOLUMES_REPLACEMENTS([CtLiteralImpl]"instance.volumes.replacements", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Since accumulo stores absolute URIs changing the location of a namenode " + [CtLiteralImpl]"could prevent Accumulo from starting. The property helps deal with ") + [CtLiteralImpl]"that situation. Provide a comma separated list of uri replacement ") + [CtLiteralImpl]"pairs here if a namenode location changes. Each pair should be separated ") + [CtLiteralImpl]"with a space. For example, if hdfs://nn1 was replaced with ") + [CtLiteralImpl]"hdfs://nnA and hdfs://nn2 was replaced with hdfs://nnB, then set this ") + [CtLiteralImpl]"property to 'hdfs://nn1 hdfs://nnA,hdfs://nn2 hdfs://nnB' ") + [CtLiteralImpl]"Replacements must be configured for use. To see which volumes are ") + [CtLiteralImpl]"currently in use, run 'accumulo admin volumes -l'. To use a comma or ") + [CtLiteralImpl]"other reserved characters in a URI use standard URI hex encoding. For ") + [CtLiteralImpl]"example replace commas with %2C."),
    [CtEnumValueImpl]INSTANCE_VOLUMES_UPGRADE_RELATIVE([CtLiteralImpl]"instance.volumes.upgrade.relative", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The volume dfs uri containing relative tablet file paths. Relative paths may exist in the metadata from " + [CtLiteralImpl]"versions prior to 1.6. This property is only required if a relative path is detected ") + [CtLiteralImpl]"during the upgrade process and will only be used once."),
    [CtEnumValueImpl]INSTANCE_SECURITY_AUTHENTICATOR([CtLiteralImpl]"instance.security.authenticator", [CtLiteralImpl]"org.apache.accumulo.server.security.handler.ZKAuthenticator", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtLiteralImpl]"The authenticator class that accumulo will use to determine if a user " + [CtLiteralImpl]"has privilege to perform an action"),
    [CtEnumValueImpl]INSTANCE_SECURITY_AUTHORIZOR([CtLiteralImpl]"instance.security.authorizor", [CtLiteralImpl]"org.apache.accumulo.server.security.handler.ZKAuthorizor", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtLiteralImpl]"The authorizor class that accumulo will use to determine what labels a " + [CtLiteralImpl]"user has privilege to see"),
    [CtEnumValueImpl]INSTANCE_SECURITY_PERMISSION_HANDLER([CtLiteralImpl]"instance.security.permissionHandler", [CtLiteralImpl]"org.apache.accumulo.server.security.handler.ZKPermHandler", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtLiteralImpl]"The permission handler class that accumulo will use to determine if a " + [CtLiteralImpl]"user has privilege to perform an action"),
    [CtEnumValueImpl]INSTANCE_RPC_SSL_ENABLED([CtLiteralImpl]"instance.rpc.ssl.enabled", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtLiteralImpl]"Use SSL for socket connections from clients and among accumulo services. " + [CtLiteralImpl]"Mutually exclusive with SASL RPC configuration."),
    [CtEnumValueImpl]INSTANCE_RPC_SSL_CLIENT_AUTH([CtLiteralImpl]"instance.rpc.ssl.clientAuth", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Require clients to present certs signed by a trusted root"),
    [CtEnumValueImpl][CtJavaDocImpl]/**
     *
     * @since 1.7.0
     */
    INSTANCE_RPC_SASL_ENABLED([CtLiteralImpl]"instance.rpc.sasl.enabled", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtLiteralImpl]"Configures Thrift RPCs to require SASL with GSSAPI which supports " + [CtLiteralImpl]"Kerberos authentication. Mutually exclusive with SSL RPC configuration."),
    [CtEnumValueImpl]INSTANCE_RPC_SASL_ALLOWED_USER_IMPERSONATION([CtLiteralImpl]"instance.rpc.sasl.allowed.user.impersonation", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"One-line configuration property controlling what users are allowed to " + [CtLiteralImpl]"impersonate other users"),
    [CtEnumValueImpl]INSTANCE_RPC_SASL_ALLOWED_HOST_IMPERSONATION([CtLiteralImpl]"instance.rpc.sasl.allowed.host.impersonation", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"One-line configuration property controlling the network locations " + [CtLiteralImpl]"(hostnames) that are allowed to impersonate other users"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Experimental
    [CtCommentImpl]// Crypto-related properties
    INSTANCE_CRYPTO_PREFIX([CtLiteralImpl]"instance.crypto.opts.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties related to on-disk file encryption."),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Experimental
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    INSTANCE_CRYPTO_SENSITIVE_PREFIX([CtLiteralImpl]"instance.crypto.opts.sensitive.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Sensitive properties related to on-disk file encryption."),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Experimental
    INSTANCE_CRYPTO_SERVICE([CtLiteralImpl]"instance.crypto.service", [CtLiteralImpl]"org.apache.accumulo.core.cryptoImpl.NoCryptoService", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The class which executes on-disk file encryption. The default does nothing. To enable " + [CtLiteralImpl]"encryption, replace this classname with an implementation of the") + [CtLiteralImpl]"org.apache.accumulo.core.spi.crypto.CryptoService interface."),
    [CtEnumValueImpl][CtCommentImpl]// general properties
    GENERAL_PREFIX([CtLiteralImpl]"general.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtLiteralImpl]"Properties in this category affect the behavior of accumulo overall, but" + [CtLiteralImpl]" do not have to be consistent throughout a cloud."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    GENERAL_DYNAMIC_CLASSPATHS([CtFieldReadImpl]org.apache.accumulo.start.classloader.vfs.AccumuloVFSClassLoader.DYNAMIC_CLASSPATH_PROPERTY_NAME, [CtFieldReadImpl]org.apache.accumulo.start.classloader.vfs.AccumuloVFSClassLoader.DEFAULT_DYNAMIC_CLASSPATH_VALUE, [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"This property is deprecated since 2.0.0. A list of all of the places where changes " + [CtLiteralImpl]"in jars or classes will force a reload of the classloader. Built-in dynamic class ") + [CtLiteralImpl]"loading will be removed in a future version. If this is needed, consider overriding ") + [CtLiteralImpl]"the Java system class loader with one that has this feature ") + [CtLiteralImpl]"(https://docs.oracle.com/javase/8/docs/api/java/lang/ClassLoader.html#getSystemClassLoader--). ") + [CtLiteralImpl]"Additionally, this property no longer does property interpolation of environment ") + [CtLiteralImpl]"variables, such as '$ACCUMULO_HOME'. Use commons-configuration syntax,") + [CtLiteralImpl]"'${env:ACCUMULO_HOME}' instead."),
    [CtEnumValueImpl]GENERAL_RPC_TIMEOUT([CtLiteralImpl]"general.rpc.timeout", [CtLiteralImpl]"120s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Time to wait on I/O for simple, short RPC calls"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Experimental
    GENERAL_RPC_SERVER_TYPE([CtLiteralImpl]"general.rpc.server.type", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Type of Thrift server to instantiate, see " + [CtLiteralImpl]"org.apache.accumulo.server.rpc.ThriftServerType for more information. ") + [CtLiteralImpl]"Only useful for benchmarking thrift servers"),
    [CtEnumValueImpl]GENERAL_KERBEROS_KEYTAB([CtLiteralImpl]"general.kerberos.keytab", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.PATH, [CtLiteralImpl]"Path to the kerberos keytab to use. Leave blank if not using kerberoized hdfs"),
    [CtEnumValueImpl]GENERAL_KERBEROS_PRINCIPAL([CtLiteralImpl]"general.kerberos.principal", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Name of the kerberos principal to use. _HOST will automatically be " + [CtLiteralImpl]"replaced by the machines hostname in the hostname portion of the ") + [CtLiteralImpl]"principal. Leave blank if not using kerberoized hdfs"),
    [CtEnumValueImpl]GENERAL_KERBEROS_RENEWAL_PERIOD([CtLiteralImpl]"general.kerberos.renewal.period", [CtLiteralImpl]"30s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The amount of time between attempts to perform Kerberos ticket renewals." + [CtLiteralImpl]" This does not equate to how often tickets are actually renewed (which is") + [CtLiteralImpl]" performed at 80% of the ticket lifetime)."),
    [CtEnumValueImpl]GENERAL_MAX_MESSAGE_SIZE([CtLiteralImpl]"general.server.message.size.max", [CtLiteralImpl]"1G", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"The maximum size of a message that can be sent to a server."),
    [CtEnumValueImpl]GENERAL_SIMPLETIMER_THREADPOOL_SIZE([CtLiteralImpl]"general.server.simpletimer.threadpool.size", [CtLiteralImpl]"1", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of threads to use for " + [CtLiteralImpl]"server-internal scheduled tasks"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Experimental
    [CtCommentImpl]// If you update the default type, be sure to update the default used for initialization failures
    [CtCommentImpl]// in VolumeManagerImpl
    GENERAL_VOLUME_CHOOSER([CtLiteralImpl]"general.volume.chooser", [CtLiteralImpl]"org.apache.accumulo.server.fs.RandomVolumeChooser", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtLiteralImpl]"The class that will be used to select which volume will be used to create new files."),
    [CtEnumValueImpl]GENERAL_SECURITY_CREDENTIAL_PROVIDER_PATHS([CtLiteralImpl]"general.security.credential.provider.paths", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Comma-separated list of paths to CredentialProviders"),
    [CtEnumValueImpl]GENERAL_ARBITRARY_PROP_PREFIX([CtLiteralImpl]"general.custom.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Prefix to be used for user defined system-wide properties. This may be" + [CtLiteralImpl]" particularly useful for system-wide configuration for various") + [CtLiteralImpl]" user-implementations of pluggable Accumulo features, such as the balancer") + [CtLiteralImpl]" or volume chooser."),
    [CtEnumValueImpl]GENERAL_DELEGATION_TOKEN_LIFETIME([CtLiteralImpl]"general.delegation.token.lifetime", [CtLiteralImpl]"7d", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The length of time that delegation tokens and secret keys are valid"),
    [CtEnumValueImpl]GENERAL_DELEGATION_TOKEN_UPDATE_INTERVAL([CtLiteralImpl]"general.delegation.token.update.interval", [CtLiteralImpl]"1d", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The length of time between generation of new secret keys"),
    [CtEnumValueImpl]GENERAL_MAX_SCANNER_RETRY_PERIOD([CtLiteralImpl]"general.max.scanner.retry.period", [CtLiteralImpl]"5s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The maximum amount of time that a Scanner should wait before retrying a failed RPC"),
    [CtEnumValueImpl][CtCommentImpl]// properties that are specific to master server behavior
    MASTER_PREFIX([CtLiteralImpl]"master.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category affect the behavior of the master server"),
    [CtEnumValueImpl]MASTER_CLIENTPORT([CtLiteralImpl]"master.port.client", [CtLiteralImpl]"9999", [CtFieldReadImpl]PropertyType.PORT, [CtLiteralImpl]"The port used for handling client connections on the master"),
    [CtEnumValueImpl]MASTER_TABLET_BALANCER([CtLiteralImpl]"master.tablet.balancer", [CtLiteralImpl]"org.apache.accumulo.server.master.balancer.TableLoadBalancer", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtLiteralImpl]"The balancer class that accumulo will use to make tablet assignment and " + [CtLiteralImpl]"migration decisions."),
    [CtEnumValueImpl]MASTER_BULK_RETRIES([CtLiteralImpl]"master.bulk.retries", [CtLiteralImpl]"3", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of attempts to bulk import a RFile before giving up."),
    [CtEnumValueImpl]MASTER_BULK_THREADPOOL_SIZE([CtLiteralImpl]"master.bulk.threadpool.size", [CtLiteralImpl]"5", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of threads to use when coordinating a bulk import."),
    [CtEnumValueImpl]MASTER_BULK_TIMEOUT([CtLiteralImpl]"master.bulk.timeout", [CtLiteralImpl]"5m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The time to wait for a tablet server to process a bulk import request"),
    [CtEnumValueImpl]MASTER_RENAME_THREADS([CtLiteralImpl]"master.rename.threadpool.size", [CtLiteralImpl]"20", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of threads to use when renaming user files during table import or bulk ingest."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.MASTER_RENAME_THREADS)
    MASTER_BULK_RENAME_THREADS([CtLiteralImpl]"master.bulk.rename.threadpool.size", [CtLiteralImpl]"20", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of threads to use when moving user files to bulk ingest " + [CtLiteralImpl]"directories under accumulo control"),
    [CtEnumValueImpl]MASTER_IMPORTTABLE_RENAME_THREADS([CtLiteralImpl]"master.importtable.rename.threadpool.size", [CtLiteralImpl]"20", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of threads to use when renaming user files when importing a table."),
    [CtEnumValueImpl]MASTER_BULK_TSERVER_REGEX([CtLiteralImpl]"master.bulk.tserver.regex", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Regular expression that defines the set of Tablet Servers that will perform bulk imports"),
    [CtEnumValueImpl]MASTER_MINTHREADS([CtLiteralImpl]"master.server.threads.minimum", [CtLiteralImpl]"20", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The minimum number of threads to use to handle incoming requests."),
    [CtEnumValueImpl]MASTER_THREADCHECK([CtLiteralImpl]"master.server.threadcheck.time", [CtLiteralImpl]"1s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The time between adjustments of the server thread pool."),
    [CtEnumValueImpl]MASTER_RECOVERY_DELAY([CtLiteralImpl]"master.recovery.delay", [CtLiteralImpl]"10s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"When a tablet server's lock is deleted, it takes time for it to " + [CtLiteralImpl]"completely quit. This delay gives it time before log recoveries begin."),
    [CtEnumValueImpl]MASTER_LEASE_RECOVERY_WAITING_PERIOD([CtLiteralImpl]"master.lease.recovery.interval", [CtLiteralImpl]"5s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The amount of time to wait after requesting a write-ahead log to be recovered"),
    [CtEnumValueImpl]MASTER_WALOG_CLOSER_IMPLEMETATION([CtLiteralImpl]"master.walog.closer.implementation", [CtLiteralImpl]"org.apache.accumulo.server.master.recovery.HadoopLogCloser", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtLiteralImpl]"A class that implements a mechanism to steal write access to a write-ahead log"),
    [CtEnumValueImpl]MASTER_FATE_METRICS_ENABLED([CtLiteralImpl]"master.fate.metrics.enabled", [CtLiteralImpl]"true", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Enable reporting of FATE metrics in JMX (and logging with Hadoop Metrics2"),
    [CtEnumValueImpl]MASTER_FATE_METRICS_MIN_UPDATE_INTERVAL([CtLiteralImpl]"master.fate.metrics.min.update.interval", [CtLiteralImpl]"60s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Limit calls from metric sinks to zookeeper to update interval"),
    [CtEnumValueImpl]MASTER_FATE_THREADPOOL_SIZE([CtLiteralImpl]"master.fate.threadpool.size", [CtLiteralImpl]"4", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of threads used to run fault-tolerant executions (FATE)." + [CtLiteralImpl]" These are primarily table operations like merge."),
    [CtEnumValueImpl]MASTER_REPLICATION_SCAN_INTERVAL([CtLiteralImpl]"master.replication.status.scan.interval", [CtLiteralImpl]"30s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"Amount of time to sleep before scanning the status section of the " + [CtLiteralImpl]"replication table for new data"),
    [CtEnumValueImpl]MASTER_REPLICATION_COORDINATOR_PORT([CtLiteralImpl]"master.replication.coordinator.port", [CtLiteralImpl]"10001", [CtFieldReadImpl]PropertyType.PORT, [CtLiteralImpl]"Port for the replication coordinator service"),
    [CtEnumValueImpl]MASTER_REPLICATION_COORDINATOR_MINTHREADS([CtLiteralImpl]"master.replication.coordinator.minthreads", [CtLiteralImpl]"4", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"Minimum number of threads dedicated to answering coordinator requests"),
    [CtEnumValueImpl]MASTER_REPLICATION_COORDINATOR_THREADCHECK([CtLiteralImpl]"master.replication.coordinator.threadcheck.time", [CtLiteralImpl]"5s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The time between adjustments of the coordinator thread pool"),
    [CtEnumValueImpl]MASTER_STATUS_THREAD_POOL_SIZE([CtLiteralImpl]"master.status.threadpool.size", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of threads to use when fetching the tablet server status for balancing.  Zero " + [CtLiteralImpl]"indicates an unlimited number of threads will be used."),
    [CtEnumValueImpl]MASTER_METADATA_SUSPENDABLE([CtLiteralImpl]"master.metadata.suspendable", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Allow tablets for the " + [CtFieldReadImpl]org.apache.accumulo.core.metadata.MetadataTable.NAME) + [CtLiteralImpl]" table to be suspended via table.suspend.duration."),
    [CtEnumValueImpl]MASTER_STARTUP_TSERVER_AVAIL_MIN_COUNT([CtLiteralImpl]"master.startup.tserver.avail.min.count", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Minimum number of tservers that need to be registered before master will " + [CtLiteralImpl]"start tablet assignment - checked at master initialization, when master gets lock. ") + [CtLiteralImpl]" When set to 0 or less, no blocking occurs. Default is 0 (disabled) to keep original ") + [CtLiteralImpl]" behaviour. Added with version 1.10"),
    [CtEnumValueImpl]MASTER_STARTUP_TSERVER_AVAIL_MAX_WAIT([CtLiteralImpl]"master.startup.tserver.avail.max.wait", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Maximum time master will wait for tserver available threshold " + [CtLiteralImpl]"to be reached before continuing. When set to 0 or less, will block ") + [CtLiteralImpl]"indefinitely. Default is 0 to block indefinitely. Only valid when tserver available ") + [CtLiteralImpl]"threshold is set greater than 0. Added with version 1.10"),
    [CtEnumValueImpl][CtCommentImpl]// properties that are specific to tablet server behavior
    TSERV_PREFIX([CtLiteralImpl]"tserver.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category affect the behavior of the tablet servers"),
    [CtEnumValueImpl]TSERV_CLIENT_TIMEOUT([CtLiteralImpl]"tserver.client.timeout", [CtLiteralImpl]"3s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Time to wait for clients to continue scans before closing a session."),
    [CtEnumValueImpl]TSERV_DEFAULT_BLOCKSIZE([CtLiteralImpl]"tserver.default.blocksize", [CtLiteralImpl]"1M", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"Specifies a default blocksize for the tserver caches"),
    [CtEnumValueImpl]TSERV_CACHE_MANAGER_IMPL([CtLiteralImpl]"tserver.cache.manager.class", [CtLiteralImpl]"org.apache.accumulo.core.file.blockfile.cache.lru.LruBlockCacheManager", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Specifies the class name of the block cache factory implementation." + [CtLiteralImpl]" Alternative implementation is") + [CtLiteralImpl]" org.apache.accumulo.core.file.blockfile.cache.tinylfu.TinyLfuBlockCacheManager"),
    [CtEnumValueImpl]TSERV_DATACACHE_SIZE([CtLiteralImpl]"tserver.cache.data.size", [CtLiteralImpl]"10%", [CtFieldReadImpl]PropertyType.MEMORY, [CtLiteralImpl]"Specifies the size of the cache for RFile data blocks."),
    [CtEnumValueImpl]TSERV_INDEXCACHE_SIZE([CtLiteralImpl]"tserver.cache.index.size", [CtLiteralImpl]"25%", [CtFieldReadImpl]PropertyType.MEMORY, [CtLiteralImpl]"Specifies the size of the cache for RFile index blocks."),
    [CtEnumValueImpl]TSERV_SUMMARYCACHE_SIZE([CtLiteralImpl]"tserver.cache.summary.size", [CtLiteralImpl]"10%", [CtFieldReadImpl]PropertyType.MEMORY, [CtLiteralImpl]"Specifies the size of the cache for summary data on each tablet server."),
    [CtEnumValueImpl]TSERV_PORTSEARCH([CtLiteralImpl]"tserver.port.search", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"if the ports above are in use, search higher ports until one is available"),
    [CtEnumValueImpl]TSERV_CLIENTPORT([CtLiteralImpl]"tserver.port.client", [CtLiteralImpl]"9997", [CtFieldReadImpl]PropertyType.PORT, [CtLiteralImpl]"The port used for handling client connections on the tablet servers"),
    [CtEnumValueImpl]TSERV_TOTAL_MUTATION_QUEUE_MAX([CtLiteralImpl]"tserver.total.mutation.queue.max", [CtLiteralImpl]"5%", [CtFieldReadImpl]PropertyType.MEMORY, [CtLiteralImpl]"The amount of memory used to store write-ahead-log mutations before flushing them."),
    [CtEnumValueImpl]TSERV_TABLET_SPLIT_FINDMIDPOINT_MAXOPEN([CtLiteralImpl]"tserver.tablet.split.midpoint.files.max", [CtLiteralImpl]"300", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"To find a tablets split points, all RFiles are opened and their indexes" + [CtLiteralImpl]" are read. This setting determines how many RFiles can be opened at once.") + [CtLiteralImpl]" When there are more RFiles than this setting multiple passes must be") + [CtLiteralImpl]" made, which is slower. However opening too many RFiles at once can cause") + [CtLiteralImpl]" problems."),
    [CtEnumValueImpl]TSERV_WALOG_MAX_REFERENCED([CtLiteralImpl]"tserver.walog.max.referenced", [CtLiteralImpl]"3", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"When a tablet server has more than this many write ahead logs, any tablet referencing older " + [CtLiteralImpl]"logs over this threshold is minor compacted.  Also any tablet referencing this many ") + [CtLiteralImpl]"logs or more will be compacted."),
    [CtEnumValueImpl]TSERV_WALOG_MAX_SIZE([CtLiteralImpl]"tserver.walog.max.size", [CtLiteralImpl]"1g", [CtFieldReadImpl]PropertyType.BYTES, [CtBinaryOperatorImpl][CtLiteralImpl]"The maximum size for each write-ahead log. See comment for property" + [CtLiteralImpl]" tserver.memory.maps.max"),
    [CtEnumValueImpl]TSERV_WALOG_MAX_AGE([CtLiteralImpl]"tserver.walog.max.age", [CtLiteralImpl]"24h", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The maximum age for each write-ahead log."),
    [CtEnumValueImpl]TSERV_WALOG_TOLERATED_CREATION_FAILURES([CtLiteralImpl]"tserver.walog.tolerated.creation.failures", [CtLiteralImpl]"50", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The maximum number of failures tolerated when creating a new write-ahead" + [CtLiteralImpl]" log. Negative values will allow unlimited creation failures. Exceeding this") + [CtLiteralImpl]" number of failures consecutively trying to create a new write-ahead log") + [CtLiteralImpl]" causes the TabletServer to exit."),
    [CtEnumValueImpl]TSERV_WALOG_TOLERATED_WAIT_INCREMENT([CtLiteralImpl]"tserver.walog.tolerated.wait.increment", [CtLiteralImpl]"1000ms", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The amount of time to wait between failures to create or write a write-ahead log."),
    [CtEnumValueImpl][CtCommentImpl]// Never wait longer than 5 mins for a retry
    TSERV_WALOG_TOLERATED_MAXIMUM_WAIT_DURATION([CtLiteralImpl]"tserver.walog.maximum.wait.duration", [CtLiteralImpl]"5m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The maximum amount of time to wait after a failure to create or write a write-ahead log."),
    [CtEnumValueImpl]TSERV_MAJC_DELAY([CtLiteralImpl]"tserver.compaction.major.delay", [CtLiteralImpl]"30s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Time a tablet server will sleep between checking which tablets need compaction."),
    [CtEnumValueImpl]TSERV_MAJC_THREAD_MAXOPEN([CtLiteralImpl]"tserver.compaction.major.thread.files.open.max", [CtLiteralImpl]"10", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"Max number of RFiles a major compaction thread can open at once. "),
    [CtEnumValueImpl]TSERV_SCAN_MAX_OPENFILES([CtLiteralImpl]"tserver.scan.files.open.max", [CtLiteralImpl]"100", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"Maximum total RFiles that all tablets in a tablet server can open for scans. "),
    [CtEnumValueImpl]TSERV_MAX_IDLE([CtLiteralImpl]"tserver.files.open.idle", [CtLiteralImpl]"1m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Tablet servers leave previously used RFiles open for future queries." + [CtLiteralImpl]" This setting determines how much time an unused RFile should be kept open") + [CtLiteralImpl]" until it is closed."),
    [CtEnumValueImpl]TSERV_NATIVEMAP_ENABLED([CtLiteralImpl]"tserver.memory.maps.native.enabled", [CtLiteralImpl]"true", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"An in-memory data store for accumulo implemented in c++ that increases" + [CtLiteralImpl]" the amount of data accumulo can hold in memory and avoids Java GC") + [CtLiteralImpl]" pauses."),
    [CtEnumValueImpl]TSERV_MAXMEM([CtLiteralImpl]"tserver.memory.maps.max", [CtLiteralImpl]"33%", [CtFieldReadImpl]PropertyType.MEMORY, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Maximum amount of memory that can be used to buffer data written to a" + [CtLiteralImpl]" tablet server. There are two other properties that can effectively limit") + [CtLiteralImpl]" memory usage table.compaction.minor.logs.threshold and") + [CtLiteralImpl]" tserver.walog.max.size. Ensure that table.compaction.minor.logs.threshold") + [CtLiteralImpl]" * tserver.walog.max.size >= this property."),
    [CtEnumValueImpl]TSERV_MEM_MGMT([CtLiteralImpl]"tserver.memory.manager", [CtLiteralImpl]"org.apache.accumulo.server.tabletserver.LargestFirstMemoryManager", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtLiteralImpl]"An implementation of MemoryManger that accumulo will use."),
    [CtEnumValueImpl]TSERV_SESSION_MAXIDLE([CtLiteralImpl]"tserver.session.idle.max", [CtLiteralImpl]"1m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"When a tablet server's SimpleTimer thread triggers to check idle" + [CtLiteralImpl]" sessions, this configurable option will be used to evaluate scan sessions") + [CtLiteralImpl]" to determine if they can be closed due to inactivity"),
    [CtEnumValueImpl]TSERV_UPDATE_SESSION_MAXIDLE([CtLiteralImpl]"tserver.session.update.idle.max", [CtLiteralImpl]"1m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"When a tablet server's SimpleTimer thread triggers to check idle" + [CtLiteralImpl]" sessions, this configurable option will be used to evaluate update") + [CtLiteralImpl]" sessions to determine if they can be closed due to inactivity"),
    [CtEnumValueImpl]TSERV_SCAN_EXECUTORS_PREFIX([CtLiteralImpl]"tserver.scan.executors.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Prefix for defining executors to service scans. See " + [CtLiteralImpl]"[scan executors]({% durl administration/scan-executors %}) for an overview of why and") + [CtLiteralImpl]" how to use this property. For each executor the number of threads, thread priority, ") + [CtLiteralImpl]"and an optional prioritizer can be configured. To configure a new executor, set ") + [CtLiteralImpl]"`tserver.scan.executors.<name>.threads=<number>`.  Optionally, can also set ") + [CtLiteralImpl]"`tserver.scan.executors.<name>.priority=<number 1 to 10>`, ") + [CtLiteralImpl]"`tserver.scan.executors.<name>.prioritizer=<class name>`, and ") + [CtLiteralImpl]"`tserver.scan.executors.<name>.prioritizer.opts.<key>=<value>`"),
    [CtEnumValueImpl]TSERV_SCAN_EXECUTORS_DEFAULT_THREADS([CtLiteralImpl]"tserver.scan.executors.default.threads", [CtLiteralImpl]"16", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of threads for the scan executor that tables use by default."),
    [CtEnumValueImpl]TSERV_SCAN_EXECUTORS_DEFAULT_PRIORITIZER([CtLiteralImpl]"tserver.scan.executors.default.prioritizer", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Prioritizer for the default scan executor.  Defaults to none which " + [CtLiteralImpl]"results in FIFO priority.  Set to a class that implements ") + [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.spi.scan.ScanPrioritizer.class.getName()) + [CtLiteralImpl]" to configure one."),
    [CtEnumValueImpl]TSERV_SCAN_EXECUTORS_META_THREADS([CtLiteralImpl]"tserver.scan.executors.meta.threads", [CtLiteralImpl]"8", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of threads for the metadata table scan executor."),
    [CtEnumValueImpl]TSERV_MIGRATE_MAXCONCURRENT([CtLiteralImpl]"tserver.migrations.concurrent.max", [CtLiteralImpl]"1", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The maximum number of concurrent tablet migrations for a tablet server"),
    [CtEnumValueImpl]TSERV_MAJC_MAXCONCURRENT([CtLiteralImpl]"tserver.compaction.major.concurrent.max", [CtLiteralImpl]"3", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The maximum number of concurrent major compactions for a tablet server"),
    [CtEnumValueImpl]TSERV_MAJC_THROUGHPUT([CtLiteralImpl]"tserver.compaction.major.throughput", [CtLiteralImpl]"0B", [CtFieldReadImpl]PropertyType.BYTES, [CtBinaryOperatorImpl][CtLiteralImpl]"Maximum number of bytes to read or write per second over all major" + [CtLiteralImpl]" compactions on a TabletServer, or 0B for unlimited."),
    [CtEnumValueImpl]TSERV_MINC_MAXCONCURRENT([CtLiteralImpl]"tserver.compaction.minor.concurrent.max", [CtLiteralImpl]"4", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The maximum number of concurrent minor compactions for a tablet server"),
    [CtEnumValueImpl]TSERV_MAJC_TRACE_PERCENT([CtLiteralImpl]"tserver.compaction.major.trace.percent", [CtLiteralImpl]"0.1", [CtFieldReadImpl]PropertyType.FRACTION, [CtLiteralImpl]"The percent of major compactions to trace"),
    [CtEnumValueImpl]TSERV_MINC_TRACE_PERCENT([CtLiteralImpl]"tserver.compaction.minor.trace.percent", [CtLiteralImpl]"0.1", [CtFieldReadImpl]PropertyType.FRACTION, [CtLiteralImpl]"The percent of minor compactions to trace"),
    [CtEnumValueImpl]TSERV_COMPACTION_WARN_TIME([CtLiteralImpl]"tserver.compaction.warn.time", [CtLiteralImpl]"10m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"When a compaction has not made progress for this time period, a warning will be logged"),
    [CtEnumValueImpl]TSERV_BLOOM_LOAD_MAXCONCURRENT([CtLiteralImpl]"tserver.bloom.load.concurrent.max", [CtLiteralImpl]"4", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of concurrent threads that will load bloom filters in the background. " + [CtLiteralImpl]"Setting this to zero will make bloom filters load in the foreground."),
    [CtEnumValueImpl]TSERV_MONITOR_FS([CtLiteralImpl]"tserver.monitor.fs", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"When enabled the tserver will monitor file systems and kill itself when" + [CtLiteralImpl]" one switches from rw to ro. This is usually and indication that Linux has") + [CtLiteralImpl]" detected a bad disk."),
    [CtEnumValueImpl]TSERV_MEMDUMP_DIR([CtLiteralImpl]"tserver.dir.memdump", [CtLiteralImpl]"/tmp", [CtFieldReadImpl]PropertyType.PATH, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"A long running scan could possibly hold memory that has been minor" + [CtLiteralImpl]" compacted. To prevent this, the in memory map is dumped to a local file") + [CtLiteralImpl]" and the scan is switched to that local file. We can not switch to the") + [CtLiteralImpl]" minor compacted file because it may have been modified by iterators. The") + [CtLiteralImpl]" file dumped to the local dir is an exact copy of what was in memory."),
    [CtEnumValueImpl]TSERV_BULK_PROCESS_THREADS([CtLiteralImpl]"tserver.bulk.process.threads", [CtLiteralImpl]"1", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The master will task a tablet server with pre-processing a bulk import" + [CtLiteralImpl]" RFile prior to assigning it to the appropriate tablet servers. This") + [CtLiteralImpl]" configuration value controls the number of threads used to process the") + [CtLiteralImpl]" files."),
    [CtEnumValueImpl]TSERV_BULK_ASSIGNMENT_THREADS([CtLiteralImpl]"tserver.bulk.assign.threads", [CtLiteralImpl]"1", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The master delegates bulk import RFile processing and assignment to" + [CtLiteralImpl]" tablet servers. After file has been processed, the tablet server will") + [CtLiteralImpl]" assign the file to the appropriate tablets on all servers. This property") + [CtLiteralImpl]" controls the number of threads used to communicate to the other") + [CtLiteralImpl]" servers."),
    [CtEnumValueImpl]TSERV_BULK_RETRY([CtLiteralImpl]"tserver.bulk.retry.max", [CtLiteralImpl]"5", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of times the tablet server will attempt to assign a RFile to" + [CtLiteralImpl]" a tablet as it migrates and splits."),
    [CtEnumValueImpl]TSERV_BULK_TIMEOUT([CtLiteralImpl]"tserver.bulk.timeout", [CtLiteralImpl]"5m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The time to wait for a tablet server to process a bulk import request."),
    [CtEnumValueImpl]TSERV_MINTHREADS([CtLiteralImpl]"tserver.server.threads.minimum", [CtLiteralImpl]"20", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The minimum number of threads to use to handle incoming requests."),
    [CtEnumValueImpl]TSERV_THREADCHECK([CtLiteralImpl]"tserver.server.threadcheck.time", [CtLiteralImpl]"1s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The time between adjustments of the server thread pool."),
    [CtEnumValueImpl]TSERV_MAX_MESSAGE_SIZE([CtLiteralImpl]"tserver.server.message.size.max", [CtLiteralImpl]"1G", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"The maximum size of a message that can be sent to a tablet server."),
    [CtEnumValueImpl]TSERV_LOG_BUSY_TABLETS_COUNT([CtLiteralImpl]"tserver.log.busy.tablets.count", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"Number of busiest tablets to log. Logged at interval controlled by " + [CtLiteralImpl]"tserver.log.busy.tablets.interval. If <= 0, logging of busy tablets is disabled"),
    [CtEnumValueImpl]TSERV_LOG_BUSY_TABLETS_INTERVAL([CtLiteralImpl]"tserver.log.busy.tablets.interval", [CtLiteralImpl]"1h", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Time interval between logging out busy tablets information."),
    [CtEnumValueImpl]TSERV_HOLD_TIME_SUICIDE([CtLiteralImpl]"tserver.hold.time.max", [CtLiteralImpl]"5m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The maximum time for a tablet server to be in the \"memory full\" state." + [CtLiteralImpl]" If the tablet server cannot write out memory in this much time, it will") + [CtLiteralImpl]" assume there is some failure local to its node, and quit. A value of zero") + [CtLiteralImpl]" is equivalent to forever."),
    [CtEnumValueImpl]TSERV_WAL_BLOCKSIZE([CtLiteralImpl]"tserver.wal.blocksize", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.BYTES, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The size of the HDFS blocks used to write to the Write-Ahead log. If" + [CtLiteralImpl]" zero, it will be 110% of tserver.walog.max.size (that is, try to use just") + [CtLiteralImpl]" one block)"),
    [CtEnumValueImpl]TSERV_WAL_REPLICATION([CtLiteralImpl]"tserver.wal.replication", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The replication to use when writing the Write-Ahead log to HDFS. If" + [CtLiteralImpl]" zero, it will use the HDFS default replication setting."),
    [CtEnumValueImpl]TSERV_RECOVERY_MAX_CONCURRENT([CtLiteralImpl]"tserver.recovery.concurrent.max", [CtLiteralImpl]"2", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The maximum number of threads to use to sort logs during" + [CtLiteralImpl]" recovery"),
    [CtEnumValueImpl]TSERV_SORT_BUFFER_SIZE([CtLiteralImpl]"tserver.sort.buffer.size", [CtLiteralImpl]"10%", [CtFieldReadImpl]PropertyType.MEMORY, [CtLiteralImpl]"The amount of memory to use when sorting logs during recovery."),
    [CtEnumValueImpl]TSERV_WORKQ_THREADS([CtLiteralImpl]"tserver.workq.threads", [CtLiteralImpl]"2", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of threads for the distributed work queue. These threads are" + [CtLiteralImpl]" used for copying failed bulk import RFiles."),
    [CtEnumValueImpl]TSERV_WAL_SYNC([CtLiteralImpl]"tserver.wal.sync", [CtLiteralImpl]"true", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtLiteralImpl]"Use the SYNC_BLOCK create flag to sync WAL writes to disk. Prevents" + [CtLiteralImpl]" problems recovering from sudden system resets."),
    [CtEnumValueImpl]TSERV_ASSIGNMENT_DURATION_WARNING([CtLiteralImpl]"tserver.assignment.duration.warning", [CtLiteralImpl]"10m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The amount of time an assignment can run before the server will print a" + [CtLiteralImpl]" warning along with the current stack trace. Meant to help debug stuck") + [CtLiteralImpl]" assignments"),
    [CtEnumValueImpl]TSERV_REPLICATION_REPLAYERS([CtLiteralImpl]"tserver.replication.replayer.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Allows configuration of implementation used to apply replicated data"),
    [CtEnumValueImpl]TSERV_REPLICATION_DEFAULT_HANDLER([CtLiteralImpl]"tserver.replication.default.replayer", [CtLiteralImpl]"org.apache.accumulo.tserver.replication.BatchWriterReplicationReplayer", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtLiteralImpl]"Default AccumuloReplicationReplayer implementation"),
    [CtEnumValueImpl]TSERV_REPLICATION_BW_REPLAYER_MEMORY([CtLiteralImpl]"tserver.replication.batchwriter.replayer.memory", [CtLiteralImpl]"50M", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"Memory to provide to batchwriter to replay mutations for replication"),
    [CtEnumValueImpl]TSERV_ASSIGNMENT_MAXCONCURRENT([CtLiteralImpl]"tserver.assignment.concurrent.max", [CtLiteralImpl]"2", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of threads available to load tablets. Recoveries are still performed serially."),
    [CtEnumValueImpl]TSERV_SLOW_FLUSH_MILLIS([CtLiteralImpl]"tserver.slow.flush.time", [CtLiteralImpl]"100ms", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"If a flush to the write-ahead log takes longer than this period of time," + [CtLiteralImpl]" debugging information will written, and may result in a log rollover."),
    [CtEnumValueImpl]TSERV_SLOW_FILEPERMIT_MILLIS([CtLiteralImpl]"tserver.slow.filepermit.time", [CtLiteralImpl]"100ms", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"If a thread blocks more than this period of time waiting to get file permits," + [CtLiteralImpl]" debugging information will be written."),
    [CtEnumValueImpl]TSERV_SUMMARY_PARTITION_THREADS([CtLiteralImpl]"tserver.summary.partition.threads", [CtLiteralImpl]"10", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Summary data must be retrieved from RFiles. For a large number of" + [CtLiteralImpl]" RFiles, the files are broken into partitions of 100K files. This setting") + [CtLiteralImpl]" determines how many of these groups of 100K RFiles will be processed") + [CtLiteralImpl]" concurrently."),
    [CtEnumValueImpl]TSERV_SUMMARY_REMOTE_THREADS([CtLiteralImpl]"tserver.summary.remote.threads", [CtLiteralImpl]"128", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"For a partitioned group of 100K RFiles, those files are grouped by" + [CtLiteralImpl]" tablet server. Then a remote tablet server is asked to gather summary") + [CtLiteralImpl]" data. This setting determines how many concurrent request are made per") + [CtLiteralImpl]" partition."),
    [CtEnumValueImpl]TSERV_SUMMARY_RETRIEVAL_THREADS([CtLiteralImpl]"tserver.summary.retrieval.threads", [CtLiteralImpl]"10", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of threads on each tablet server available to retrieve" + [CtLiteralImpl]" summary data, that is not currently in cache, from RFiles."),
    [CtEnumValueImpl][CtCommentImpl]// accumulo garbage collector properties
    GC_PREFIX([CtLiteralImpl]"gc.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category affect the behavior of the accumulo garbage collector."),
    [CtEnumValueImpl]GC_CYCLE_START([CtLiteralImpl]"gc.cycle.start", [CtLiteralImpl]"30s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Time to wait before attempting to garbage collect any old RFiles or write-ahead logs."),
    [CtEnumValueImpl]GC_CYCLE_DELAY([CtLiteralImpl]"gc.cycle.delay", [CtLiteralImpl]"5m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"Time between garbage collection cycles. In each cycle, old RFiles or write-ahead logs " + [CtLiteralImpl]"no longer in use are removed from the filesystem."),
    [CtEnumValueImpl]GC_PORT([CtLiteralImpl]"gc.port.client", [CtLiteralImpl]"9998", [CtFieldReadImpl]PropertyType.PORT, [CtLiteralImpl]"The listening port for the garbage collector's monitor service"),
    [CtEnumValueImpl]GC_DELETE_THREADS([CtLiteralImpl]"gc.threads.delete", [CtLiteralImpl]"16", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"The number of threads used to delete RFiles and write-ahead logs"),
    [CtEnumValueImpl]GC_TRASH_IGNORE([CtLiteralImpl]"gc.trash.ignore", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Do not use the Trash, even if it is configured."),
    [CtEnumValueImpl]GC_TRACE_PERCENT([CtLiteralImpl]"gc.trace.percent", [CtLiteralImpl]"0.01", [CtFieldReadImpl]PropertyType.FRACTION, [CtLiteralImpl]"Percent of gc cycles to trace"),
    [CtEnumValueImpl]GC_SAFEMODE([CtLiteralImpl]"gc.safemode", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Provides listing of files to be deleted but does not delete any files"),
    [CtEnumValueImpl]GC_USE_FULL_COMPACTION([CtLiteralImpl]"gc.post.metadata.action", [CtLiteralImpl]"flush", [CtFieldReadImpl]PropertyType.GC_POST_ACTION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"When the gc runs it can make a lot of changes to the metadata, on completion, " + [CtLiteralImpl]" to force the changes to be written to disk, the metadata and root tables can be flushed") + [CtLiteralImpl]" and possibly compacted. Legal values are: compact - which both flushes and compacts the") + [CtLiteralImpl]" metadata; flush - which flushes only (compactions may be triggered if required); or none"),
    [CtEnumValueImpl]GC_METRICS_ENABLED([CtLiteralImpl]"gc.metrics.enabled", [CtLiteralImpl]"true", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Enable detailed gc metrics reporting with hadoop metrics."),
    [CtEnumValueImpl][CtCommentImpl]// properties that are specific to the monitor server behavior
    MONITOR_PREFIX([CtLiteralImpl]"monitor.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category affect the behavior of the monitor web server."),
    [CtEnumValueImpl]MONITOR_PORT([CtLiteralImpl]"monitor.port.client", [CtLiteralImpl]"9995", [CtFieldReadImpl]PropertyType.PORT, [CtLiteralImpl]"The listening port for the monitor's http service"),
    [CtEnumValueImpl]MONITOR_SSL_KEYSTORE([CtLiteralImpl]"monitor.ssl.keyStore", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.PATH, [CtLiteralImpl]"The keystore for enabling monitor SSL."),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    MONITOR_SSL_KEYSTOREPASS([CtLiteralImpl]"monitor.ssl.keyStorePassword", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The keystore password for enabling monitor SSL."),
    [CtEnumValueImpl]MONITOR_SSL_KEYSTORETYPE([CtLiteralImpl]"monitor.ssl.keyStoreType", [CtLiteralImpl]"jks", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Type of SSL keystore"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    MONITOR_SSL_KEYPASS([CtLiteralImpl]"monitor.ssl.keyPassword", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"Optional: the password for the private key in the keyStore. When not provided, this " + [CtLiteralImpl]"defaults to the keystore password."),
    [CtEnumValueImpl]MONITOR_SSL_TRUSTSTORE([CtLiteralImpl]"monitor.ssl.trustStore", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.PATH, [CtLiteralImpl]"The truststore for enabling monitor SSL."),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    MONITOR_SSL_TRUSTSTOREPASS([CtLiteralImpl]"monitor.ssl.trustStorePassword", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The truststore password for enabling monitor SSL."),
    [CtEnumValueImpl]MONITOR_SSL_TRUSTSTORETYPE([CtLiteralImpl]"monitor.ssl.trustStoreType", [CtLiteralImpl]"jks", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Type of SSL truststore"),
    [CtEnumValueImpl]MONITOR_SSL_INCLUDE_CIPHERS([CtLiteralImpl]"monitor.ssl.include.ciphers", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"A comma-separated list of allows SSL Ciphers, see" + [CtLiteralImpl]" monitor.ssl.exclude.ciphers to disallow ciphers"),
    [CtEnumValueImpl]MONITOR_SSL_EXCLUDE_CIPHERS([CtLiteralImpl]"monitor.ssl.exclude.ciphers", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"A comma-separated list of disallowed SSL Ciphers, see" + [CtLiteralImpl]" monitor.ssl.include.ciphers to allow ciphers"),
    [CtEnumValueImpl]MONITOR_SSL_INCLUDE_PROTOCOLS([CtLiteralImpl]"monitor.ssl.include.protocols", [CtLiteralImpl]"TLSv1.2", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"A comma-separate list of allowed SSL protocols"),
    [CtEnumValueImpl]MONITOR_LOCK_CHECK_INTERVAL([CtLiteralImpl]"monitor.lock.check.interval", [CtLiteralImpl]"5s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The amount of time to sleep between checking for the Montior ZooKeeper lock"),
    [CtEnumValueImpl]MONITOR_RESOURCES_EXTERNAL([CtLiteralImpl]"monitor.resources.external", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"A JSON Map of Strings. Each String should be an HTML tag of an external" + [CtLiteralImpl]" resource (JS or CSS) to be imported by the Monitor. Be sure to wrap") + [CtLiteralImpl]" with CDATA tags. If this value is set, all of the external resources") + [CtLiteralImpl]" in the `<head>` tag of the Monitor will be replaced with the tags set here.") + [CtLiteralImpl]" Be sure the jquery tag is first since other scripts will depend on it.") + [CtLiteralImpl]" The resources that are used by default can be seen in") + [CtLiteralImpl]" accumulo/server/monitor/src/main/resources/templates/default.ftl"),
    [CtEnumValueImpl]TRACE_PREFIX([CtLiteralImpl]"trace.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category affect the behavior of distributed tracing."),
    [CtEnumValueImpl]TRACE_SPAN_RECEIVERS([CtLiteralImpl]"trace.span.receivers", [CtLiteralImpl]"org.apache.accumulo.tracer.ZooTraceClient", [CtFieldReadImpl]PropertyType.CLASSNAMELIST, [CtLiteralImpl]"A list of span receiver classes to send trace spans"),
    [CtEnumValueImpl]TRACE_SPAN_RECEIVER_PREFIX([CtLiteralImpl]"trace.span.receiver.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Prefix for span receiver configuration properties"),
    [CtEnumValueImpl]TRACE_ZK_PATH([CtLiteralImpl]"trace.zookeeper.path", [CtFieldReadImpl]org.apache.accumulo.core.Constants.ZTRACERS, [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The zookeeper node where tracers are registered"),
    [CtEnumValueImpl]TRACE_PORT([CtLiteralImpl]"trace.port.client", [CtLiteralImpl]"12234", [CtFieldReadImpl]PropertyType.PORT, [CtLiteralImpl]"The listening port for the trace server"),
    [CtEnumValueImpl]TRACE_TABLE([CtLiteralImpl]"trace.table", [CtLiteralImpl]"trace", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The name of the table to store distributed traces"),
    [CtEnumValueImpl]TRACE_USER([CtLiteralImpl]"trace.user", [CtLiteralImpl]"root", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The name of the user to store distributed traces"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    TRACE_PASSWORD([CtLiteralImpl]"trace.password", [CtLiteralImpl]"secret", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The password for the user used to store distributed traces"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    TRACE_TOKEN_PROPERTY_PREFIX([CtLiteralImpl]"trace.token.property.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtLiteralImpl]"The prefix used to create a token for storing distributed traces. For" + [CtLiteralImpl]" each property required by trace.token.type, place this prefix in front of it."),
    [CtEnumValueImpl]TRACE_TOKEN_TYPE([CtLiteralImpl]"trace.token.type", [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.client.security.tokens.PasswordToken.class.getName(), [CtFieldReadImpl]PropertyType.CLASSNAME, [CtLiteralImpl]"An AuthenticationToken type supported by the authorizer"),
    [CtEnumValueImpl][CtCommentImpl]// per table properties
    TABLE_PREFIX([CtLiteralImpl]"table.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Properties in this category affect tablet server treatment of tablets," + [CtLiteralImpl]" but can be configured on a per-table basis. Setting these properties in") + [CtLiteralImpl]" accumulo.properties will override the default globally for all tables and not") + [CtLiteralImpl]" any specific table. However, both the default and the global setting can") + [CtLiteralImpl]" be overridden per table using the table operations API or in the shell,") + [CtLiteralImpl]" which sets the overridden value in zookeeper. Restarting accumulo tablet") + [CtLiteralImpl]" servers after setting these properties in accumulo.properties will cause the") + [CtLiteralImpl]" global setting to take effect. However, you must use the API or the shell") + [CtLiteralImpl]" to change properties in zookeeper that are set on a table."),
    [CtEnumValueImpl]TABLE_ARBITRARY_PROP_PREFIX([CtLiteralImpl]"table.custom.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Prefix to be used for user defined arbitrary properties."),
    [CtEnumValueImpl]TABLE_MAJC_RATIO([CtLiteralImpl]"table.compaction.major.ratio", [CtLiteralImpl]"3", [CtFieldReadImpl]PropertyType.FRACTION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Minimum ratio of total input size to maximum input RFile size for" + [CtLiteralImpl]" running a major compaction. When adjusting this property you may want to") + [CtLiteralImpl]" also adjust table.file.max. Want to avoid the situation where only") + [CtLiteralImpl]" merging minor compactions occur."),
    [CtEnumValueImpl]TABLE_MAJC_COMPACTALL_IDLETIME([CtLiteralImpl]"table.compaction.major.everything.idle", [CtLiteralImpl]"1h", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"After a tablet has been idle (no mutations) for this time period it may" + [CtLiteralImpl]" have all of its RFiles compacted into one. There is no guarantee an idle") + [CtLiteralImpl]" tablet will be compacted. Compactions of idle tablets are only started") + [CtLiteralImpl]" when regular compactions are not running. Idle compactions only take") + [CtLiteralImpl]" place for tablets that have one or more RFiles."),
    [CtEnumValueImpl]TABLE_SPLIT_THRESHOLD([CtLiteralImpl]"table.split.threshold", [CtLiteralImpl]"1G", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"A tablet is split when the combined size of RFiles exceeds this amount."),
    [CtEnumValueImpl]TABLE_MAX_END_ROW_SIZE([CtLiteralImpl]"table.split.endrow.size.max", [CtLiteralImpl]"10K", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"Maximum size of end row"),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TSERV_WALOG_MAX_REFERENCED)
    TABLE_MINC_LOGS_MAX([CtLiteralImpl]"table.compaction.minor.logs.threshold", [CtLiteralImpl]"3", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"This property is deprecated since 2.0.0."),
    [CtEnumValueImpl]TABLE_MINC_COMPACT_IDLETIME([CtLiteralImpl]"table.compaction.minor.idle", [CtLiteralImpl]"5m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"After a tablet has been idle (no mutations) for this time period it may have its " + [CtLiteralImpl]"in-memory map flushed to disk in a minor compaction. There is no guarantee an idle ") + [CtLiteralImpl]"tablet will be compacted."),
    [CtEnumValueImpl]TABLE_MINC_MAX_MERGE_FILE_SIZE([CtLiteralImpl]"table.compaction.minor.merge.file.size.max", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.BYTES, [CtBinaryOperatorImpl][CtLiteralImpl]"The max RFile size used for a merging minor compaction. The default" + [CtLiteralImpl]" value of 0 disables a max file size."),
    [CtEnumValueImpl]TABLE_SCAN_DISPATCHER([CtLiteralImpl]"table.scan.dispatcher", [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.spi.scan.SimpleScanDispatcher.class.getName(), [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"This class is used to dynamically dispatch scans to configured scan executors.  Configured " + [CtLiteralImpl]"classes must implement {% jlink ") + [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.spi.scan.ScanDispatcher.class.getName()) + [CtLiteralImpl]" %} See ") + [CtLiteralImpl]"[scan executors]({% durl administration/scan-executors %}) for an overview of why") + [CtLiteralImpl]" and how to use this property. This property is ignored for the root and metadata") + [CtLiteralImpl]" table.  The metadata table always dispatches to a scan executor named `meta`."),
    [CtEnumValueImpl]TABLE_SCAN_DISPATCHER_OPTS([CtLiteralImpl]"table.scan.dispatcher.opts.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Options for the table scan dispatcher"),
    [CtEnumValueImpl]TABLE_SCAN_MAXMEM([CtLiteralImpl]"table.scan.max.memory", [CtLiteralImpl]"512K", [CtFieldReadImpl]PropertyType.BYTES, [CtBinaryOperatorImpl][CtLiteralImpl]"The maximum amount of memory that will be used to cache results of a client query/scan. " + [CtLiteralImpl]"Once this limit is reached, the buffered data is sent to the client."),
    [CtEnumValueImpl]TABLE_FILE_TYPE([CtLiteralImpl]"table.file.type", [CtFieldReadImpl]org.apache.accumulo.core.file.rfile.RFile.EXTENSION, [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Change the type of file a table writes"),
    [CtEnumValueImpl]TABLE_LOAD_BALANCER([CtLiteralImpl]"table.balancer", [CtLiteralImpl]"org.apache.accumulo.server.master.balancer.DefaultLoadBalancer", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"This property can be set to allow the LoadBalanceByTable load balancer" + [CtLiteralImpl]" to change the called Load Balancer for this table"),
    [CtEnumValueImpl]TABLE_FILE_COMPRESSION_TYPE([CtLiteralImpl]"table.file.compress.type", [CtLiteralImpl]"gz", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"Compression algorithm used on index and data blocks before they are" + [CtLiteralImpl]" written. Possible values: zstd, gz, snappy, lzo, none"),
    [CtEnumValueImpl]TABLE_FILE_COMPRESSED_BLOCK_SIZE([CtLiteralImpl]"table.file.compress.blocksize", [CtLiteralImpl]"100K", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"The maximum size of data blocks in RFiles before they are compressed and written."),
    [CtEnumValueImpl]TABLE_FILE_COMPRESSED_BLOCK_SIZE_INDEX([CtLiteralImpl]"table.file.compress.blocksize.index", [CtLiteralImpl]"128K", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"The maximum size of index blocks in RFiles before they are compressed and written."),
    [CtEnumValueImpl]TABLE_FILE_BLOCK_SIZE([CtLiteralImpl]"table.file.blocksize", [CtLiteralImpl]"0B", [CtFieldReadImpl]PropertyType.BYTES, [CtBinaryOperatorImpl][CtLiteralImpl]"The HDFS block size used when writing RFiles. When set to 0B, the" + [CtLiteralImpl]" value/defaults of HDFS property 'dfs.block.size' will be used."),
    [CtEnumValueImpl]TABLE_FILE_REPLICATION([CtLiteralImpl]"table.file.replication", [CtLiteralImpl]"0", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"The number of replicas for a table's RFiles in HDFS. When set to 0, HDFS" + [CtLiteralImpl]" defaults are used."),
    [CtEnumValueImpl]TABLE_FILE_MAX([CtLiteralImpl]"table.file.max", [CtLiteralImpl]"15", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The maximum number of RFiles each tablet in a table can have. When" + [CtLiteralImpl]" adjusting this property you may want to consider adjusting") + [CtLiteralImpl]" table.compaction.major.ratio also. Setting this property to 0 will make") + [CtLiteralImpl]" it default to tserver.scan.files.open.max-1, this will prevent a tablet") + [CtLiteralImpl]" from having more RFiles than can be opened. Setting this property low may") + [CtLiteralImpl]" throttle ingest and increase query performance."),
    [CtEnumValueImpl]TABLE_FILE_SUMMARY_MAX_SIZE([CtLiteralImpl]"table.file.summary.maxSize", [CtLiteralImpl]"256K", [CtFieldReadImpl]PropertyType.BYTES, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The maximum size summary that will be stored. The number of RFiles that" + [CtLiteralImpl]" had summary data exceeding this threshold is reported by") + [CtLiteralImpl]" Summary.getFileStatistics().getLarge(). When adjusting this consider the") + [CtLiteralImpl]" expected number RFiles with summaries on each tablet server and the") + [CtLiteralImpl]" summary cache size."),
    [CtEnumValueImpl]TABLE_BLOOM_ENABLED([CtLiteralImpl]"table.bloom.enabled", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Use bloom filters on this table."),
    [CtEnumValueImpl]TABLE_BLOOM_LOAD_THRESHOLD([CtLiteralImpl]"table.bloom.load.threshold", [CtLiteralImpl]"1", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"This number of seeks that would actually use a bloom filter must occur" + [CtLiteralImpl]" before a RFile's bloom filter is loaded. Set this to zero to initiate") + [CtLiteralImpl]" loading of bloom filters when a RFile is opened."),
    [CtEnumValueImpl]TABLE_BLOOM_SIZE([CtLiteralImpl]"table.bloom.size", [CtLiteralImpl]"1048576", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"Bloom filter size, as number of keys."),
    [CtEnumValueImpl]TABLE_BLOOM_ERRORRATE([CtLiteralImpl]"table.bloom.error.rate", [CtLiteralImpl]"0.5%", [CtFieldReadImpl]PropertyType.FRACTION, [CtLiteralImpl]"Bloom filter error rate."),
    [CtEnumValueImpl]TABLE_BLOOM_KEY_FUNCTOR([CtLiteralImpl]"table.bloom.key.functor", [CtLiteralImpl]"org.apache.accumulo.core.file.keyfunctor.RowFunctor", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"A function that can transform the key prior to insertion and check of" + [CtLiteralImpl]" bloom filter. org.apache.accumulo.core.file.keyfunctor.RowFunctor,") + [CtLiteralImpl]" org.apache.accumulo.core.file.keyfunctor.ColumnFamilyFunctor, and") + [CtLiteralImpl]" org.apache.accumulo.core.file.keyfunctor.ColumnQualifierFunctor are") + [CtLiteralImpl]" allowable values. One can extend any of the above mentioned classes to") + [CtLiteralImpl]" perform specialized parsing of the key. "),
    [CtEnumValueImpl]TABLE_BLOOM_HASHTYPE([CtLiteralImpl]"table.bloom.hash.type", [CtLiteralImpl]"murmur", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The bloom filter hash type"),
    [CtEnumValueImpl]TABLE_DURABILITY([CtLiteralImpl]"table.durability", [CtLiteralImpl]"sync", [CtFieldReadImpl]PropertyType.DURABILITY, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The durability used to write to the write-ahead log. Legal values are:" + [CtLiteralImpl]" none, which skips the write-ahead log; log, which sends the data to the") + [CtLiteralImpl]" write-ahead log, but does nothing to make it durable; flush, which pushes") + [CtLiteralImpl]" data to the file system; and sync, which ensures the data is written to disk."),
    [CtEnumValueImpl]TABLE_FAILURES_IGNORE([CtLiteralImpl]"table.failures.ignore", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"If you want queries for your table to hang or fail when data is missing" + [CtLiteralImpl]" from the system, then set this to false. When this set to true missing") + [CtLiteralImpl]" data will be reported but queries will still run possibly returning a") + [CtLiteralImpl]" subset of the data."),
    [CtEnumValueImpl]TABLE_DEFAULT_SCANTIME_VISIBILITY([CtLiteralImpl]"table.security.scan.visibility.default", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The security label that will be assumed at scan time if an entry does" + [CtLiteralImpl]" not have a visibility expression.\n") + [CtLiteralImpl]"Note: An empty security label is displayed as []. The scan results") + [CtLiteralImpl]" will show an empty visibility even if the visibility from this") + [CtLiteralImpl]" setting is applied to the entry.\n") + [CtLiteralImpl]"CAUTION: If a particular key has an empty security label AND its") + [CtLiteralImpl]" table's default visibility is also empty, access will ALWAYS be") + [CtLiteralImpl]" granted for users with permission to that table. Additionally, if this") + [CtLiteralImpl]" field is changed, all existing data with an empty visibility label") + [CtLiteralImpl]" will be interpreted with the new label on the next scan."),
    [CtEnumValueImpl]TABLE_LOCALITY_GROUPS([CtLiteralImpl]"table.groups.enabled", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"A comma separated list of locality group names to enable for this table."),
    [CtEnumValueImpl]TABLE_CONSTRAINT_PREFIX([CtLiteralImpl]"table.constraint.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Properties in this category are per-table properties that add" + [CtLiteralImpl]" constraints to a table. These properties start with the category") + [CtLiteralImpl]" prefix, followed by a number, and their values correspond to a fully") + [CtLiteralImpl]" qualified Java class that implements the Constraint interface.\n") + [CtLiteralImpl]"For example:\n") + [CtLiteralImpl]"table.constraint.1 = org.apache.accumulo.core.constraints.MyCustomConstraint\n") + [CtLiteralImpl]"and:\n") + [CtLiteralImpl]" table.constraint.2 = my.package.constraints.MySecondConstraint"),
    [CtEnumValueImpl]TABLE_INDEXCACHE_ENABLED([CtLiteralImpl]"table.cache.index.enable", [CtLiteralImpl]"true", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Determines whether index block cache is enabled for a table."),
    [CtEnumValueImpl]TABLE_BLOCKCACHE_ENABLED([CtLiteralImpl]"table.cache.block.enable", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Determines whether data block cache is enabled for a table."),
    [CtEnumValueImpl]TABLE_ITERATOR_PREFIX([CtLiteralImpl]"table.iterator.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Properties in this category specify iterators that are applied at" + [CtLiteralImpl]" various stages (scopes) of interaction with a table. These properties") + [CtLiteralImpl]" start with the category prefix, followed by a scope (minc, majc, scan,") + [CtLiteralImpl]" etc.), followed by a period, followed by a name, as in") + [CtLiteralImpl]" table.iterator.scan.vers, or table.iterator.scan.custom. The values for") + [CtLiteralImpl]" these properties are a number indicating the ordering in which it is") + [CtLiteralImpl]" applied, and a class name such as:\n") + [CtLiteralImpl]"table.iterator.scan.vers = 10,org.apache.accumulo.core.iterators.VersioningIterator\n") + [CtLiteralImpl]"These iterators can take options if additional properties are set that") + [CtLiteralImpl]" look like this property, but are suffixed with a period, followed by 'opt'") + [CtLiteralImpl]" followed by another period, and a property name.\n") + [CtLiteralImpl]"For example, table.iterator.minc.vers.opt.maxVersions = 3"),
    [CtEnumValueImpl]TABLE_ITERATOR_SCAN_PREFIX([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_ITERATOR_PREFIX.getKey() + [CtInvocationImpl][CtTypeAccessImpl]IteratorScope.scan.name()) + [CtLiteralImpl]".", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Convenience prefix to find options for the scan iterator scope"),
    [CtEnumValueImpl]TABLE_ITERATOR_MINC_PREFIX([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_ITERATOR_PREFIX.getKey() + [CtInvocationImpl][CtTypeAccessImpl]IteratorScope.minc.name()) + [CtLiteralImpl]".", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Convenience prefix to find options for the minc iterator scope"),
    [CtEnumValueImpl]TABLE_ITERATOR_MAJC_PREFIX([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_ITERATOR_PREFIX.getKey() + [CtInvocationImpl][CtTypeAccessImpl]IteratorScope.majc.name()) + [CtLiteralImpl]".", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Convenience prefix to find options for the majc iterator scope"),
    [CtEnumValueImpl]TABLE_LOCALITY_GROUP_PREFIX([CtLiteralImpl]"table.group.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Properties in this category are per-table properties that define" + [CtLiteralImpl]" locality groups in a table. These properties start with the category") + [CtLiteralImpl]" prefix, followed by a name, followed by a period, and followed by a") + [CtLiteralImpl]" property for that group.\n") + [CtLiteralImpl]"For example table.group.group1=x,y,z sets the column families for a") + [CtLiteralImpl]" group called group1. Once configured, group1 can be enabled by adding") + [CtLiteralImpl]" it to the list of groups in the ") + [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_LOCALITY_GROUPS.getKey()) + [CtLiteralImpl]" property.\n") + [CtLiteralImpl]"Additional group options may be specified for a named group by setting") + [CtLiteralImpl]" `table.group.<name>.opt.<key>=<value>`."),
    [CtEnumValueImpl]TABLE_FORMATTER_CLASS([CtLiteralImpl]"table.formatter", [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.util.format.DefaultFormatter.class.getName(), [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The Formatter class to apply on results in the shell"),
    [CtEnumValueImpl]TABLE_INTERPRETER_CLASS([CtLiteralImpl]"table.interepreter", [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.util.interpret.DefaultScanInterpreter.class.getName(), [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"The ScanInterpreter class to apply on scan arguments in the shell"),
    [CtEnumValueImpl]TABLE_CLASSPATH([CtLiteralImpl]"table.classpath.context", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"Per table classpath context"),
    [CtEnumValueImpl]TABLE_COMPACTION_STRATEGY([CtLiteralImpl]"table.majc.compaction.strategy", [CtLiteralImpl]"org.apache.accumulo.tserver.compaction.DefaultCompactionStrategy", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtLiteralImpl]"A customizable major compaction strategy."),
    [CtEnumValueImpl]TABLE_COMPACTION_STRATEGY_PREFIX([CtLiteralImpl]"table.majc.compaction.strategy.opts.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category are used to configure the compaction strategy."),
    [CtEnumValueImpl]TABLE_REPLICATION([CtLiteralImpl]"table.replication", [CtLiteralImpl]"false", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"Is replication enabled for the given table"),
    [CtEnumValueImpl]TABLE_REPLICATION_TARGET([CtLiteralImpl]"table.replication.target.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Enumerate a mapping of other systems which this table should replicate" + [CtLiteralImpl]" their data to. The key suffix is the identifying cluster name and the") + [CtLiteralImpl]" value is an identifier for a location on the target system, e.g. the ID") + [CtLiteralImpl]" of the table on the target to replicate to"),
    [CtEnumValueImpl]TABLE_SAMPLER([CtLiteralImpl]"table.sampler", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The name of a class that implements org.apache.accumulo.core.Sampler." + [CtLiteralImpl]" Setting this option enables storing a sample of data which can be") + [CtLiteralImpl]" scanned. Always having a current sample can useful for query optimization") + [CtLiteralImpl]" and data comprehension. After enabling sampling for an existing table,") + [CtLiteralImpl]" a compaction is needed to compute the sample for existing data. The") + [CtLiteralImpl]" compact command in the shell has an option to only compact RFiles without") + [CtLiteralImpl]" sample data."),
    [CtEnumValueImpl]TABLE_SAMPLER_OPTS([CtLiteralImpl]"table.sampler.opt.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The property is used to set options for a sampler. If a sample had two" + [CtLiteralImpl]" options like hasher and modulous, then the two properties") + [CtLiteralImpl]" table.sampler.opt.hasher=${hash algorithm} and") + [CtLiteralImpl]" table.sampler.opt.modulous=${mod} would be set."),
    [CtEnumValueImpl]TABLE_SUSPEND_DURATION([CtLiteralImpl]"table.suspend.duration", [CtLiteralImpl]"0s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"For tablets belonging to this table: When a tablet server dies, allow" + [CtLiteralImpl]" the tablet server this duration to revive before reassigning its tablets") + [CtLiteralImpl]" to other tablet servers."),
    [CtEnumValueImpl]TABLE_SUMMARIZER_PREFIX([CtLiteralImpl]"table.summarizer.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Prefix for configuring summarizers for a table. Using this prefix" + [CtLiteralImpl]" multiple summarizers can be configured with options for each one. Each") + [CtLiteralImpl]" summarizer configured should have a unique id, this id can be anything.") + [CtLiteralImpl]" To add a summarizer set ") + [CtLiteralImpl]"`table.summarizer.<unique id>=<summarizer class name>.` If the summarizer has options") + [CtLiteralImpl]", then for each option set `table.summarizer.<unique id>.opt.<key>=<value>`."),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Experimental
    TABLE_DELETE_BEHAVIOR([CtLiteralImpl]"table.delete.behavior", [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]DeletingIterator.Behavior.PROCESS.name().toLowerCase(), [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"This determines what action to take when a delete marker is seen." + [CtLiteralImpl]" Valid values are `process` and `fail` with `process` being the default.  When set to ") + [CtLiteralImpl]"`process`, deletes will supress data.  When set to `fail`, any deletes seen will cause") + [CtLiteralImpl]" an exception. The purpose of `fail` is to support tables that never delete data and") + [CtLiteralImpl]" need fast seeks within the timestamp range of a column. When setting this to fail, ") + [CtLiteralImpl]"also consider configuring the `") + [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.constraints.NoDeleteConstraint.class.getName()) + [CtLiteralImpl]"` ") + [CtLiteralImpl]"constraint."),
    [CtEnumValueImpl][CtCommentImpl]// VFS ClassLoader properties
    [CtCommentImpl]// this property shouldn't be used directly; it exists solely to document the default value
    [CtCommentImpl]// defined by its use in AccumuloVFSClassLoader when generating the property documentation
    VFS_CLASSLOADER_SYSTEM_CLASSPATH_PROPERTY([CtFieldReadImpl]org.apache.accumulo.start.classloader.vfs.AccumuloVFSClassLoader.VFS_CLASSLOADER_SYSTEM_CLASSPATH_PROPERTY, [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"Configuration for a system level vfs classloader. Accumulo jar can be" + [CtLiteralImpl]" configured here and loaded out of HDFS."),
    [CtEnumValueImpl]VFS_CONTEXT_CLASSPATH_PROPERTY([CtFieldReadImpl]org.apache.accumulo.start.classloader.vfs.AccumuloVFSClassLoader.VFS_CONTEXT_CLASSPATH_PROPERTY, [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Properties in this category are define a classpath. These properties" + [CtLiteralImpl]" start  with the category prefix, followed by a context name. The value is") + [CtLiteralImpl]" a comma separated list of URIs. Supports full regex on filename alone.") + [CtLiteralImpl]" For example, general.vfs.context.classpath.cx1=hdfs://nn1:9902/mylibdir/*.jar.") + [CtLiteralImpl]" You can enable post delegation for a context, which will load classes from the") + [CtLiteralImpl]" context first instead of the parent first. Do this by setting") + [CtLiteralImpl]" `general.vfs.context.classpath.<name>.delegation=post`, where `<name>` is") + [CtLiteralImpl]" your context name. If delegation is not specified, it defaults to loading") + [CtLiteralImpl]" from parent classloader first."),
    [CtEnumValueImpl][CtCommentImpl]// this property shouldn't be used directly; it exists solely to document the default value
    [CtCommentImpl]// defined by its use in AccumuloVFSClassLoader when generating the property documentation
    VFS_CLASSLOADER_CACHE_DIR([CtFieldReadImpl]org.apache.accumulo.start.classloader.vfs.AccumuloVFSClassLoader.VFS_CACHE_DIR, [CtLiteralImpl]"${java.io.tmpdir}", [CtFieldReadImpl]PropertyType.ABSOLUTEPATH, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The base directory to use for the vfs cache. The actual cached files will be located" + [CtLiteralImpl]" in a subdirectory, `accumulo-vfs-cache-<jvmProcessName>-${user.name}`, where") + [CtLiteralImpl]" `<jvmProcessName>` is determined by the JVM's internal management engine.") + [CtLiteralImpl]" The cache will keep a soft reference to all of the classes loaded in the VM.") + [CtLiteralImpl]" This should be on local disk on each node with sufficient space."),
    [CtEnumValueImpl][CtCommentImpl]// General properties for configuring replication
    REPLICATION_PREFIX([CtLiteralImpl]"replication.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category affect the replication of data to other Accumulo instances."),
    [CtEnumValueImpl]REPLICATION_PEERS([CtLiteralImpl]"replication.peer.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"Properties in this category control what systems data can be replicated to"),
    [CtEnumValueImpl]REPLICATION_PEER_USER([CtLiteralImpl]"replication.peer.user.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"The username to provide when authenticating with the given peer"),
    [CtEnumValueImpl][CtAnnotationImpl]@org.apache.accumulo.core.conf.Sensitive
    REPLICATION_PEER_PASSWORD([CtLiteralImpl]"replication.peer.password.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"The password to provide when authenticating with the given peer"),
    [CtEnumValueImpl]REPLICATION_PEER_KEYTAB([CtLiteralImpl]"replication.peer.keytab.", [CtLiteralImpl]null, [CtFieldReadImpl]PropertyType.PREFIX, [CtLiteralImpl]"The keytab to use when authenticating with the given peer"),
    [CtEnumValueImpl]REPLICATION_NAME([CtLiteralImpl]"replication.name", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtLiteralImpl]"Name of this cluster with respect to replication. Used to identify this" + [CtLiteralImpl]" instance from other peers"),
    [CtEnumValueImpl]REPLICATION_MAX_WORK_QUEUE([CtLiteralImpl]"replication.max.work.queue", [CtLiteralImpl]"1000", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"Upper bound of the number of files queued for replication"),
    [CtEnumValueImpl]REPLICATION_WORK_ASSIGNMENT_SLEEP([CtLiteralImpl]"replication.work.assignment.sleep", [CtLiteralImpl]"30s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Amount of time to sleep between replication work assignment"),
    [CtEnumValueImpl]REPLICATION_WORKER_THREADS([CtLiteralImpl]"replication.worker.threads", [CtLiteralImpl]"4", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"Size of the threadpool that each tabletserver devotes to replicating data"),
    [CtEnumValueImpl]REPLICATION_RECEIPT_SERVICE_PORT([CtLiteralImpl]"replication.receipt.service.port", [CtLiteralImpl]"10002", [CtFieldReadImpl]PropertyType.PORT, [CtLiteralImpl]"Listen port used by thrift service in tserver listening for replication"),
    [CtEnumValueImpl]REPLICATION_WORK_ATTEMPTS([CtLiteralImpl]"replication.work.attempts", [CtLiteralImpl]"10", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"Number of attempts to try to replicate some data before giving up and" + [CtLiteralImpl]" letting it naturally be retried later"),
    [CtEnumValueImpl]REPLICATION_MIN_THREADS([CtLiteralImpl]"replication.receiver.min.threads", [CtLiteralImpl]"1", [CtFieldReadImpl]PropertyType.COUNT, [CtLiteralImpl]"Minimum number of threads for replication"),
    [CtEnumValueImpl]REPLICATION_THREADCHECK([CtLiteralImpl]"replication.receiver.threadcheck.time", [CtLiteralImpl]"30s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"The time between adjustments of the replication thread pool."),
    [CtEnumValueImpl]REPLICATION_MAX_UNIT_SIZE([CtLiteralImpl]"replication.max.unit.size", [CtLiteralImpl]"64M", [CtFieldReadImpl]PropertyType.BYTES, [CtLiteralImpl]"Maximum size of data to send in a replication message"),
    [CtEnumValueImpl]REPLICATION_WORK_ASSIGNER([CtLiteralImpl]"replication.work.assigner", [CtLiteralImpl]"org.apache.accumulo.master.replication.UnorderedWorkAssigner", [CtFieldReadImpl]PropertyType.CLASSNAME, [CtLiteralImpl]"Replication WorkAssigner implementation to use"),
    [CtEnumValueImpl]REPLICATION_DRIVER_DELAY([CtLiteralImpl]"replication.driver.delay", [CtLiteralImpl]"0s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtLiteralImpl]"Amount of time to wait before the replication work loop begins in the master."),
    [CtEnumValueImpl]REPLICATION_WORK_PROCESSOR_DELAY([CtLiteralImpl]"replication.work.processor.delay", [CtLiteralImpl]"0s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"Amount of time to wait before first checking for replication work, not" + [CtLiteralImpl]" useful outside of tests"),
    [CtEnumValueImpl]REPLICATION_WORK_PROCESSOR_PERIOD([CtLiteralImpl]"replication.work.processor.period", [CtLiteralImpl]"0s", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"Amount of time to wait before re-checking for replication work, not" + [CtLiteralImpl]" useful outside of tests"),
    [CtEnumValueImpl]REPLICATION_TRACE_PERCENT([CtLiteralImpl]"replication.trace.percent", [CtLiteralImpl]"0.1", [CtFieldReadImpl]PropertyType.FRACTION, [CtLiteralImpl]"The sampling percentage to use for replication traces"),
    [CtEnumValueImpl]REPLICATION_RPC_TIMEOUT([CtLiteralImpl]"replication.rpc.timeout", [CtLiteralImpl]"2m", [CtFieldReadImpl]PropertyType.TIMEDURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"Amount of time for a single replication RPC call to last before failing" + [CtLiteralImpl]" the attempt. See replication.work.attempts."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.INSTANCE_VOLUMES)
    [CtCommentImpl]// deprecated properties grouped at the end to reference property that replaces them
    INSTANCE_DFS_URI([CtLiteralImpl]"instance.dfs.uri", [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.URI, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"This property is deprecated since 1.6.0. " + [CtLiteralImpl]"A url accumulo should use to connect to DFS. If this is empty, accumulo") + [CtLiteralImpl]" will obtain this information from the hadoop configuration. This property") + [CtLiteralImpl]" will only be used when creating new files if instance.volumes is empty.") + [CtLiteralImpl]" After an upgrade to 1.6.0 Accumulo will start using absolute paths to") + [CtLiteralImpl]" reference files. Files created before a 1.6.0 upgrade are referenced via") + [CtLiteralImpl]" relative paths. Relative paths will always be resolved using this config") + [CtLiteralImpl]" (if empty using the hadoop config)."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.INSTANCE_VOLUMES)
    INSTANCE_DFS_DIR([CtLiteralImpl]"instance.dfs.dir", [CtLiteralImpl]"/accumulo", [CtFieldReadImpl]PropertyType.ABSOLUTEPATH, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"This property is deprecated since 1.6.0. " + [CtLiteralImpl]"HDFS directory in which accumulo instance will run. ") + [CtLiteralImpl]"Do not change after accumulo is initialized."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    GENERAL_CLASSPATHS([CtFieldReadImpl]org.apache.accumulo.start.classloader.AccumuloClassLoader.GENERAL_CLASSPATHS, [CtLiteralImpl]"", [CtFieldReadImpl]PropertyType.STRING, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"This property is deprecated since 2.0.0. The class path should instead be configured" + [CtLiteralImpl]" by the launch environment (for example, accumulo-env.sh). A list of all") + [CtLiteralImpl]" of the places to look for a class. Order does matter, as it will look for") + [CtLiteralImpl]" the jar starting in the first location to the last. Supports full regex") + [CtLiteralImpl]" on filename alone."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_DURABILITY)
    TSERV_WAL_SYNC_METHOD([CtLiteralImpl]"tserver.wal.sync.method", [CtLiteralImpl]"hsync", [CtFieldReadImpl]PropertyType.STRING, [CtLiteralImpl]"This property is deprecated since 1.7.0. Use table.durability instead."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_DURABILITY)
    TABLE_WALOG_ENABLED([CtLiteralImpl]"table.walog.enabled", [CtLiteralImpl]"true", [CtFieldReadImpl]PropertyType.BOOLEAN, [CtLiteralImpl]"This setting is deprecated since 1.7.0. Use table.durability=none instead."),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TSERV_SCAN_EXECUTORS_DEFAULT_THREADS)
    TSERV_READ_AHEAD_MAXCONCURRENT([CtLiteralImpl]"tserver.readahead.concurrent.max", [CtLiteralImpl]"16", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"This property is deprecated since 2.0.0, use tserver.scan.executors.default.threads " + [CtLiteralImpl]"instead. The maximum number of concurrent read ahead that will execute. This ") + [CtLiteralImpl]"effectively limits the number of long running scans that can run concurrently ") + [CtLiteralImpl]"per tserver.\""),
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtAnnotationImpl]@org.apache.accumulo.core.conf.ReplacedBy(property = [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TSERV_SCAN_EXECUTORS_META_THREADS)
    TSERV_METADATA_READ_AHEAD_MAXCONCURRENT([CtLiteralImpl]"tserver.metadata.readahead.concurrent.max", [CtLiteralImpl]"8", [CtFieldReadImpl]PropertyType.COUNT, [CtBinaryOperatorImpl][CtLiteralImpl]"This property is deprecated since 2.0.0, use tserver.scan.executors.meta.threads instead. " + [CtLiteralImpl]"The maximum number of concurrent metadata read ahead that will execute.");
    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String key;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String defaultValue;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String description;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean annotationsComputed = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean isSensitive;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean isDeprecated;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean isExperimental;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property replacedBy = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.accumulo.core.conf.PropertyType type;

    [CtConstructorImpl]private Property([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultValue, [CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.PropertyType type, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String description) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.key = [CtVariableReadImpl]name;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultValue = [CtVariableReadImpl]defaultValue;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.description = [CtVariableReadImpl]description;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtVariableReadImpl]type;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.key;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the key (string) for this property.
     *
     * @return key
     */
    public [CtTypeReferenceImpl]java.lang.String getKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.key;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the default value for this property. System properties are interpolated into the value if
     * necessary.
     *
     * @return default value
     */
    public [CtTypeReferenceImpl]java.lang.String getDefaultValue() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.defaultValue;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the type of this property.
     *
     * @return property type
     */
    public [CtTypeReferenceImpl]org.apache.accumulo.core.conf.PropertyType getType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.type;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the description of this property.
     *
     * @return description
     */
    public [CtTypeReferenceImpl]java.lang.String getDescription() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.description;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if this property is experimental.
     *
     * @return true if this property is experimental
     */
    public [CtTypeReferenceImpl]boolean isExperimental() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkState([CtFieldReadImpl]annotationsComputed, [CtLiteralImpl]"precomputeAnnotations() must be called before calling this method");
        [CtReturnImpl]return [CtFieldReadImpl]isExperimental;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if this property is deprecated.
     *
     * @return true if this property is deprecated
     */
    public [CtTypeReferenceImpl]boolean isDeprecated() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkState([CtFieldReadImpl]annotationsComputed, [CtLiteralImpl]"precomputeAnnotations() must be called before calling this method");
        [CtReturnImpl]return [CtFieldReadImpl]isDeprecated;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if this property is sensitive.
     *
     * @return true if this property is sensitive
     */
    public [CtTypeReferenceImpl]boolean isSensitive() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkState([CtFieldReadImpl]annotationsComputed, [CtLiteralImpl]"precomputeAnnotations() must be called before calling this method");
        [CtReturnImpl]return [CtFieldReadImpl]isSensitive;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property replacedBy() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkState([CtFieldReadImpl]annotationsComputed, [CtLiteralImpl]"precomputeAnnotations() must be called before calling this method");
        [CtReturnImpl]return [CtFieldReadImpl]replacedBy;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void precomputeAnnotations() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]isSensitive = [CtBinaryOperatorImpl][CtInvocationImpl]hasAnnotation([CtFieldReadImpl]org.apache.accumulo.core.conf.Sensitive.class) || [CtInvocationImpl]org.apache.accumulo.core.conf.Property.hasPrefixWithAnnotation([CtInvocationImpl]getKey(), [CtFieldReadImpl]org.apache.accumulo.core.conf.Sensitive.class);
        [CtAssignmentImpl][CtFieldWriteImpl]isDeprecated = [CtBinaryOperatorImpl][CtInvocationImpl]hasAnnotation([CtFieldReadImpl]java.lang.Deprecated.class) || [CtInvocationImpl]org.apache.accumulo.core.conf.Property.hasPrefixWithAnnotation([CtInvocationImpl]getKey(), [CtFieldReadImpl]java.lang.Deprecated.class);
        [CtAssignmentImpl][CtFieldWriteImpl]isExperimental = [CtBinaryOperatorImpl][CtInvocationImpl]hasAnnotation([CtFieldReadImpl]org.apache.accumulo.core.conf.Experimental.class) || [CtInvocationImpl]org.apache.accumulo.core.conf.Property.hasPrefixWithAnnotation([CtInvocationImpl]getKey(), [CtFieldReadImpl]org.apache.accumulo.core.conf.Experimental.class);
        [CtIfImpl]if ([CtInvocationImpl]hasAnnotation([CtFieldReadImpl]org.apache.accumulo.core.conf.ReplacedBy.class)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.ReplacedBy rb = [CtInvocationImpl]getAnnotation([CtFieldReadImpl]org.apache.accumulo.core.conf.ReplacedBy.class);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rb != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]replacedBy = [CtInvocationImpl][CtVariableReadImpl]rb.property();
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]annotationsComputed = [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if a property with the given key is sensitive. The key must be for a valid property, and
     * must either itself be annotated as sensitive or have a prefix annotated as sensitive.
     *
     * @param key
     * 		property key
     * @return true if property is sensitive
     */
    public static [CtTypeReferenceImpl]boolean isSensitive([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property prop = [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.propertiesByKey.get([CtVariableReadImpl]key);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]prop != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]prop.isSensitive();
        } else [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix : [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validPrefixes) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtVariableReadImpl]prefix)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.propertiesByKey.get([CtVariableReadImpl]prefix).isSensitive()) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    }
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtTypeReferenceImpl]boolean hasAnnotation([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> annotationType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl]getClass());
        [CtTryImpl]try [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.annotation.Annotation a : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getClass().getField([CtInvocationImpl]name()).getAnnotations()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]annotationType.isInstance([CtVariableReadImpl]a)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.SecurityException | [CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]log.error([CtLiteralImpl]"{}", [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtTypeParameterReferenceImpl]T getAnnotation([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> annotationType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl]getClass());
        [CtTryImpl]try [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.annotation.Annotation a : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getClass().getField([CtInvocationImpl]name()).getAnnotations()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]annotationType.isInstance([CtVariableReadImpl]a)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
                    [CtTypeParameterReferenceImpl]T uncheckedA = [CtVariableReadImpl](([CtTypeParameterReferenceImpl]T) (a));
                    [CtReturnImpl]return [CtVariableReadImpl]uncheckedA;
                }
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.SecurityException | [CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]log.error([CtLiteralImpl]"{}", [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private static <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtTypeReferenceImpl]boolean hasPrefixWithAnnotation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> annotationType) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix : [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validPrefixes) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtVariableReadImpl]prefix)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.propertiesByKey.get([CtVariableReadImpl]prefix).hasAnnotation([CtVariableReadImpl]annotationType)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String> validTableProperties = [CtLiteralImpl]null;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String> validProperties = [CtLiteralImpl]null;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String> validPrefixes = [CtLiteralImpl]null;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property> propertiesByKey = [CtLiteralImpl]null;

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isKeyValidlyPrefixed([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix : [CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validPrefixes) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtVariableReadImpl]prefix)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if the given property key is valid. A valid property key is either equal to the key of
     * some defined property or has a prefix matching some prefix defined in this class.
     *
     * @param key
     * 		property key
     * @return true if key is valid (recognized, or has a recognized prefix)
     */
    public static [CtTypeReferenceImpl]boolean isValidPropertyKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validProperties.contains([CtVariableReadImpl]key) || [CtInvocationImpl]org.apache.accumulo.core.conf.Property.isKeyValidlyPrefixed([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if the given property key is a valid property and is of type boolean.
     *
     * @param key
     * 		property key
     * @return true if key is valid and is of type boolean, false otherwise
     */
    public static [CtTypeReferenceImpl]boolean isValidBooleanPropertyKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validProperties.contains([CtVariableReadImpl]key) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]org.apache.accumulo.core.conf.Property.getPropertyByKey([CtVariableReadImpl]key).getType() == [CtFieldReadImpl]PropertyType.BOOLEAN);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if the given property key is for a valid table property. A valid table property key is
     * either equal to the key of some defined table property (which each start with
     * {@link #TABLE_PREFIX}) or has a prefix matching {@link #TABLE_CONSTRAINT_PREFIX},
     * {@link #TABLE_ITERATOR_PREFIX}, or {@link #TABLE_LOCALITY_GROUP_PREFIX}.
     *
     * @param key
     * 		property key
     * @return true if key is valid for a table property (recognized, or has a recognized prefix)
     */
    public static [CtTypeReferenceImpl]boolean isValidTablePropertyKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validTableProperties.contains([CtVariableReadImpl]key) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_PREFIX.getKey()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_CONSTRAINT_PREFIX.getKey()) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_ITERATOR_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_LOCALITY_GROUP_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_COMPACTION_STRATEGY_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_REPLICATION_TARGET.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_ARBITRARY_PROP_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_SAMPLER_OPTS.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_SUMMARIZER_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_SCAN_DISPATCHER_OPTS.getKey())));
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property> fixedProperties = [CtInvocationImpl][CtTypeAccessImpl]java.util.EnumSet.of([CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TSERV_CLIENTPORT, [CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TSERV_NATIVEMAP_ENABLED, [CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TSERV_SCAN_MAX_OPENFILES, [CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]MASTER_CLIENTPORT, [CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]GC_PORT);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if the given property may be changed via Zookeeper, but not recognized until the restart
     * of some relevant daemon.
     *
     * @param key
     * 		property key
     * @return true if property may be changed via Zookeeper but only heeded upon some restart
     */
    public static [CtTypeReferenceImpl]boolean isFixedZooPropertyKey([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.fixedProperties.contains([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if the given property key is valid for a property that may be changed via Zookeeper.
     *
     * @param key
     * 		property key
     * @return true if key's property may be changed via Zookeeper
     */
    public static [CtTypeReferenceImpl]boolean isValidZooPropertyKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// white list prefixes
        return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_PREFIX.getKey()) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TSERV_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]MASTER_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]GC_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]GENERAL_ARBITRARY_PROP_PREFIX.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.VFS_CONTEXT_CLASSPATH_PROPERTY.getKey())) || [CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.REPLICATION_PREFIX.getKey());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a {@link Property} instance with the given key.
     *
     * @param key
     * 		property key
     * @return property, or null if not found
     */
    public static [CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property getPropertyByKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.propertiesByKey.get([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if this property is expected to have a Java class as a value.
     *
     * @return true if this is property is a class property
     */
    public static [CtTypeReferenceImpl]boolean isClassProperty([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_CONSTRAINT_PREFIX.getKey()) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]key.substring([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_CONSTRAINT_PREFIX.getKey().length()).split([CtLiteralImpl]"\\.").length == [CtLiteralImpl]1)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_ITERATOR_PREFIX.getKey()) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]key.substring([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_ITERATOR_PREFIX.getKey().length()).split([CtLiteralImpl]"\\.").length == [CtLiteralImpl]2))) || [CtInvocationImpl][CtVariableReadImpl]key.equals([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_LOAD_BALANCER.getKey());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a new instance of a class specified in a configuration property. The table classpath
     * context is used if set.
     *
     * @param conf
     * 		configuration containing property
     * @param property
     * 		property specifying class name
     * @param base
     * 		base class of type
     * @param defaultInstance
     * 		instance to use if creation fails
     * @return new class instance, or default instance if creation failed
     * @see AccumuloVFSClassLoader
     */
    public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T createTableInstanceFromPropertyName([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.AccumuloConfiguration conf, [CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property property, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> base, [CtParameterImpl][CtTypeParameterReferenceImpl]T defaultInstance) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clazzName = [CtInvocationImpl][CtVariableReadImpl]conf.get([CtVariableReadImpl]property);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String context = [CtInvocationImpl][CtVariableReadImpl]conf.get([CtFieldReadImpl]org.apache.accumulo.core.conf.Property.TABLE_CLASSPATH);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.ConfigurationTypeHelper.getClassInstance([CtVariableReadImpl]context, [CtVariableReadImpl]clazzName, [CtVariableReadImpl]base, [CtVariableReadImpl]defaultInstance);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a new instance of a class specified in a configuration property.
     *
     * @param conf
     * 		configuration containing property
     * @param property
     * 		property specifying class name
     * @param base
     * 		base class of type
     * @param defaultInstance
     * 		instance to use if creation fails
     * @return new class instance, or default instance if creation failed
     * @see AccumuloVFSClassLoader
     */
    public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T createInstanceFromPropertyName([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.AccumuloConfiguration conf, [CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property property, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> base, [CtParameterImpl][CtTypeParameterReferenceImpl]T defaultInstance) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clazzName = [CtInvocationImpl][CtVariableReadImpl]conf.get([CtVariableReadImpl]property);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.ConfigurationTypeHelper.getClassInstance([CtLiteralImpl]null, [CtVariableReadImpl]clazzName, [CtVariableReadImpl]base, [CtVariableReadImpl]defaultInstance);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Collects together properties from the given configuration pertaining to compaction strategies.
     * The relevant properties all begin with the prefix in {@link #TABLE_COMPACTION_STRATEGY_PREFIX}.
     * In the returned map, the prefix is removed from each property's key.
     *
     * @param tableConf
     * 		configuration
     * @return map of compaction strategy property keys and values, with the detection prefix removed
    from each key
     */
    public static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getCompactionStrategyOptions([CtParameterImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.AccumuloConfiguration tableConf) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> longNames = [CtInvocationImpl][CtVariableReadImpl]tableConf.getAllPropertiesWithPrefix([CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_COMPACTION_STRATEGY_PREFIX);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> entry : [CtInvocationImpl][CtVariableReadImpl]longNames.entrySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.put([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getKey().substring([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_COMPACTION_STRATEGY_PREFIX.getKey().length()), [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// Precomputing information here avoids :
        [CtCommentImpl]// * Computing it each time a method is called
        [CtCommentImpl]// * Using synch to compute the first time a method is called
        [CtFieldWriteImpl]org.apache.accumulo.core.conf.Property.propertiesByKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtAssignmentImpl][CtFieldWriteImpl]org.apache.accumulo.core.conf.Property.validPrefixes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtAssignmentImpl][CtFieldWriteImpl]org.apache.accumulo.core.conf.Property.validProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property p : [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getType().equals([CtTypeAccessImpl]PropertyType.PREFIX)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validPrefixes.add([CtInvocationImpl][CtVariableReadImpl]p.getKey());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validProperties.add([CtInvocationImpl][CtVariableReadImpl]p.getKey());
            }
            [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.propertiesByKey.put([CtInvocationImpl][CtVariableReadImpl]p.getKey(), [CtVariableReadImpl]p);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]org.apache.accumulo.core.conf.Property.validTableProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property p : [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getType().equals([CtTypeAccessImpl]PropertyType.PREFIX)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getKey().startsWith([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.[CtFieldReferenceImpl]TABLE_PREFIX.getKey())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.conf.Property.validTableProperties.add([CtInvocationImpl][CtVariableReadImpl]p.getKey());
            }
        }
        [CtForEachImpl][CtCommentImpl]// order is very important here the following code relies on the maps and sets populated above
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.accumulo.core.conf.Property p : [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.conf.Property.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]p.precomputeAnnotations();
        }
    }
}
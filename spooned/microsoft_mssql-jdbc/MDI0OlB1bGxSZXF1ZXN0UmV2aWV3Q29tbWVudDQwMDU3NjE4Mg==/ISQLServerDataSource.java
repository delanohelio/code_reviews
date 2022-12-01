[CompilationUnitImpl][CtCommentImpl]/* Microsoft JDBC Driver for SQL Server Copyright(c) Microsoft Corporation All rights reserved. This program is made
available under the terms of the MIT License. See the LICENSE file in the project root for more information.
 */
[CtPackageDeclarationImpl]package com.microsoft.sqlserver.jdbc;
[CtImportImpl]import org.ietf.jgss.GSSCredential;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * Provides a factory to create connections to the data source represented by this object. This interface was added in
 * SQL Server JDBC Driver 3.0.
 *
 * This interface is implemented by {@link SQLServerDataSource} Class.
 */
public interface ISQLServerDataSource extends [CtTypeReferenceImpl]javax.sql.CommonDataSource {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the application intent.
     *
     * @param applicationIntent
     * 		A String that contains the application intent.
     */
    [CtTypeReferenceImpl]void setApplicationIntent([CtParameterImpl][CtTypeReferenceImpl]java.lang.String applicationIntent);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the application intent.
     *
     * @return A String that contains the application intent.
     */
    [CtTypeReferenceImpl]java.lang.String getApplicationIntent();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the application name.
     *
     * @param applicationName
     * 		A String that contains the name of the application.
     */
    [CtTypeReferenceImpl]void setApplicationName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String applicationName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the application name.
     *
     * @return A String that contains the application name, or "Microsoft JDBC Driver for SQL Server" if no value is
    set.
     */
    [CtTypeReferenceImpl]java.lang.String getApplicationName();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the database name to connect to.
     *
     * @param databaseName
     * 		A String that contains the database name.
     */
    [CtTypeReferenceImpl]void setDatabaseName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String databaseName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the database name.
     *
     * @return A String that contains the database name or null if no value is set.
     */
    [CtTypeReferenceImpl]java.lang.String getDatabaseName();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the SQL Server instance name.
     *
     * @param instanceName
     * 		A String that contains the instance name.
     */
    [CtTypeReferenceImpl]void setInstanceName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String instanceName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the SQL Server instance name.
     *
     * @return A String that contains the instance name, or null if no value is set.
     */
    [CtTypeReferenceImpl]java.lang.String getInstanceName();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a Boolean value that indicates if the integratedSecurity property is enabled.
     *
     * @param enable
     * 		true if integratedSecurity is enabled. Otherwise, false.
     */
    [CtTypeReferenceImpl]void setIntegratedSecurity([CtParameterImpl][CtTypeReferenceImpl]boolean enable);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a Boolean value that indicates if the lastUpdateCount property is enabled.
     *
     * @param lastUpdateCount
     * 		true if lastUpdateCount is enabled. Otherwise, false.
     */
    [CtTypeReferenceImpl]void setLastUpdateCount([CtParameterImpl][CtTypeReferenceImpl]boolean lastUpdateCount);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a Boolean value that indicates if the lastUpdateCount property is enabled.
     *
     * @return true if lastUpdateCount is enabled. Otherwise, false.
     */
    [CtTypeReferenceImpl]boolean getLastUpdateCount();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a Boolean value that indicates if the encrypt property is enabled.
     *
     * @param encrypt
     * 		true if the Secure Sockets Layer (SSL) encryption is enabled between the client and the SQL Server.
     * 		Otherwise, false.
     */
    [CtTypeReferenceImpl]void setEncrypt([CtParameterImpl][CtTypeReferenceImpl]boolean encrypt);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a Boolean value that indicates if the encrypt property is enabled.
     *
     * @return true if encrypt is enabled. Otherwise, false.
     */
    [CtTypeReferenceImpl]boolean getEncrypt();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value to enable/disable Transparent Netowrk IP Resolution (TNIR). Beginning in version 6.0 of the
     * Microsoft JDBC Driver for SQL Server, a new connection property transparentNetworkIPResolution (TNIR) is added
     * for transparent connection to Always On availability groups or to a server which has multiple IP addresses
     * associated. When transparentNetworkIPResolution is true, the driver attempts to connect to the first IP address
     * available. If the first attempt fails, the driver tries to connect to all IP addresses in parallel until the
     * timeout expires, discarding any pending connection attempts when one of them succeeds.
     * <p>
     * transparentNetworkIPResolution is ignored if multiSubnetFailover is true
     * <p>
     * transparentNetworkIPResolution is ignored if database mirroring is used
     * <p>
     * transparentNetworkIPResolution is ignored if there are more than 64 IP addresses
     *
     * @param tnir
     * 		if set to true, the driver attempts to connect to the first IP address available. It is true by default.
     */
    [CtTypeReferenceImpl]void setTransparentNetworkIPResolution([CtParameterImpl][CtTypeReferenceImpl]boolean tnir);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the TransparentNetworkIPResolution value.
     *
     * @return if enabled, returns true. Otherwise, false.
     */
    [CtTypeReferenceImpl]boolean getTransparentNetworkIPResolution();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a Boolean value that indicates if the trustServerCertificate property is enabled.
     *
     * @param e
     * 		true, if the server Secure Sockets Layer (SSL) certificate should be automatically trusted when the
     * 		communication layer is encrypted using SSL. Otherwise, false.
     */
    [CtTypeReferenceImpl]void setTrustServerCertificate([CtParameterImpl][CtTypeReferenceImpl]boolean e);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a Boolean value that indicates if the trustServerCertificate property is enabled.
     *
     * @return true if trustServerCertificate is enabled. Otherwise, false.
     */
    [CtTypeReferenceImpl]boolean getTrustServerCertificate();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the keystore type for the trustStore.
     *
     * @param trustStoreType
     * 		A String that contains the trust store type
     */
    [CtTypeReferenceImpl]void setTrustStoreType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String trustStoreType);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the keyStore Type for the trustStore.
     *
     * @return trustStoreType A String that contains the trust store type
     */
    [CtTypeReferenceImpl]java.lang.String getTrustStoreType();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the path (including file name) to the certificate trustStore file.
     *
     * @param trustStore
     * 		A String that contains the path (including file name) to the certificate trustStore file.
     */
    [CtTypeReferenceImpl]void setTrustStore([CtParameterImpl][CtTypeReferenceImpl]java.lang.String trustStore);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the path (including file name) to the certificate trustStore file.
     *
     * @return trustStore A String that contains the path (including file name) to the certificate trustStore file, or
    null if no value is set.
     */
    [CtTypeReferenceImpl]java.lang.String getTrustStore();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the password that is used to check the integrity of the trustStore data.
     *
     * @param trustStorePassword
     * 		A String that contains the password that is used to check the integrity of the trustStore data.
     */
    [CtTypeReferenceImpl]void setTrustStorePassword([CtParameterImpl][CtTypeReferenceImpl]java.lang.String trustStorePassword);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the host name to be used in validating the SQL Server Secure Sockets Layer (SSL) certificate.
     *
     * @param hostName
     * 		A String that contains the host name.
     */
    [CtTypeReferenceImpl]void setHostNameInCertificate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String hostName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the host name used in validating the SQL Server Secure Sockets Layer (SSL) certificate.
     *
     * @return A String that contains the host name, or null if no value is set.
     */
    [CtTypeReferenceImpl]java.lang.String getHostNameInCertificate();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets an int value that indicates the number of milliseconds to wait before the database reports a lock time out.
     *
     * @param lockTimeout
     * 		An int value that contains the number of milliseconds to wait.
     */
    [CtTypeReferenceImpl]void setLockTimeout([CtParameterImpl][CtTypeReferenceImpl]int lockTimeout);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns an int value that indicates the number of milliseconds that the database will wait before reporting a
     * lock time out.
     *
     * @return An int value that contains the number of milliseconds that the database will wait.
     */
    [CtTypeReferenceImpl]int getLockTimeout();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the password that will be used to connect to SQL Server.
     *
     * @param password
     * 		A String that contains the password.
     */
    [CtTypeReferenceImpl]void setPassword([CtParameterImpl][CtTypeReferenceImpl]java.lang.String password);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the port number to be used to communicate with SQL Server.
     *
     * @param portNumber
     * 		An int value that contains the port number.
     */
    [CtTypeReferenceImpl]void setPortNumber([CtParameterImpl][CtTypeReferenceImpl]int portNumber);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the current port number that is used to communicate with SQL Server.
     *
     * @return An int value that contains the current port number.
     */
    [CtTypeReferenceImpl]int getPortNumber();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the default cursor type that is used for all result sets that are created by using this SQLServerDataSource
     * object.
     *
     * @param selectMethod
     * 		A String value that contains the default cursor type.
     */
    [CtTypeReferenceImpl]void setSelectMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String selectMethod);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the default cursor type used for all result sets that are created by using this SQLServerDataSource
     * object.
     *
     * @return A String value that contains the default cursor type.
     */
    [CtTypeReferenceImpl]java.lang.String getSelectMethod();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the response buffering mode for connections created by using this SQLServerDataSource object.
     *
     * @param bufferingMode
     * 		A String that contains the buffering and streaming mode. The valid mode can be one of the following
     * 		case-insensitive Strings: full or adaptive.
     */
    [CtTypeReferenceImpl]void setResponseBuffering([CtParameterImpl][CtTypeReferenceImpl]java.lang.String bufferingMode);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the response buffering mode for this SQLServerDataSource object.
     *
     * @return A String that contains a lower-case full or adaptive.
     */
    [CtTypeReferenceImpl]java.lang.String getResponseBuffering();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value to enable/disable the sendTimeAsDatetime connection property.
     *
     * @param sendTimeAsDatetime
     * 		A Boolean value. When true, causes java.sql.Time values to be sent to the server as SQL Server datetime
     * 		types. When false, causes java.sql.Time values to be sent to the server as SQL Server time types.
     */
    [CtTypeReferenceImpl]void setSendTimeAsDatetime([CtParameterImpl][CtTypeReferenceImpl]boolean sendTimeAsDatetime);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of the sendTimeAsDatetime connection property. This method was added in SQL Server JDBC Driver
     * 3.0. Returns the setting of the sendTimeAsDatetime connection property.
     *
     * @return true if java.sql.Time values will be sent to the server as a SQL Server datetime type. false if
    java.sql.Time values will be sent to the server as a SQL Server time type.
     */
    [CtTypeReferenceImpl]boolean getSendTimeAsDatetime();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a boolean value that indicates if sending string parameters to the server in UNICODE format is enabled.
     *
     * @param sendStringParametersAsUnicode
     * 		true if string parameters are sent to the server in UNICODE format. Otherwise, false.
     */
    [CtTypeReferenceImpl]void setSendStringParametersAsUnicode([CtParameterImpl][CtTypeReferenceImpl]boolean sendStringParametersAsUnicode);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns whether sending string parameters to the server in UNICODE format is enabled.
     *
     * @return true if string parameters are sent to the server in UNICODE format. Otherwise, false.
     */
    [CtTypeReferenceImpl]boolean getSendStringParametersAsUnicode();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets whether the serverName will be translated from Unicode to ASCII Compatible Encoding (ACE).
     *
     * @param serverNameAsACE
     * 		if enabled the servername will be translated to ASCII Compatible Encoding (ACE)
     */
    [CtTypeReferenceImpl]void setServerNameAsACE([CtParameterImpl][CtTypeReferenceImpl]boolean serverNameAsACE);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns if the serverName should be translated from Unicode to ASCII Compatible Encoding (ACE).
     *
     * @return if enabled, will return true. Otherwise, false.
     */
    [CtTypeReferenceImpl]boolean getServerNameAsACE();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the name of the computer that is running SQL Server.
     *
     * @param serverName
     * 		A String that contains the server name.
     */
    [CtTypeReferenceImpl]void setServerName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String serverName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the name of the SQL Server instance.
     *
     * @return A String that contains the server name or null if no value is set.
     */
    [CtTypeReferenceImpl]java.lang.String getServerName();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the name of the failover server that is used in a database mirroring configuration.
     *
     * @param serverName
     * 		A String that contains the failover server name.
     */
    [CtTypeReferenceImpl]void setFailoverPartner([CtParameterImpl][CtTypeReferenceImpl]java.lang.String serverName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the name of the failover server that is used in a database mirroring configuration.
     *
     * @return A String that contains the name of the failover partner, or null if none is set.
     */
    [CtTypeReferenceImpl]java.lang.String getFailoverPartner();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value of the multiSubnetFailover connection property.
     *
     * @param multiSubnetFailover
     * 		The new value of the multiSubnetFailover connection property.
     */
    [CtTypeReferenceImpl]void setMultiSubnetFailover([CtParameterImpl][CtTypeReferenceImpl]boolean multiSubnetFailover);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of the multiSubnetFailover connection property.
     *
     * @return Returns true or false, depending on the current setting of the connection property.
     */
    [CtTypeReferenceImpl]boolean getMultiSubnetFailover();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the user name that is used to connect the data source.
     *
     * @param user
     * 		A String that contains the user name.
     */
    [CtTypeReferenceImpl]void setUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String user);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the user name that is used to connect the data source.
     *
     * @return A String that contains the user name.
     */
    [CtTypeReferenceImpl]java.lang.String getUser();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the name of the client computer name that is used to connect to the data source.
     *
     * @param workstationID
     * 		A String that contains the client computer name.
     */
    [CtTypeReferenceImpl]void setWorkstationID([CtParameterImpl][CtTypeReferenceImpl]java.lang.String workstationID);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the name of the client computer name that is used to connect to the data source.
     *
     * @return A String that contains the client computer name.
     */
    [CtTypeReferenceImpl]java.lang.String getWorkstationID();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets whether converting SQL states to XOPEN compliant states is enabled.
     *
     * @param xopenStates
     * 		true if converting SQL states to XOPEN compliant states is enabled. Otherwise, false.
     */
    [CtTypeReferenceImpl]void setXopenStates([CtParameterImpl][CtTypeReferenceImpl]boolean xopenStates);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value that indicates if converting SQL states to XOPEN compliant states is enabled.
     *
     * @return true if converting SQL states to XOPEN compliant states is enabled. Otherwise, false.
     */
    [CtTypeReferenceImpl]boolean getXopenStates();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the URL that is used to connect to the data source.
     *
     * @param url
     * 		A String that contains the URL.
     */
    [CtTypeReferenceImpl]void setURL([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the URL that is used to connect to the data source.
     *
     * @return A String that contains the URL.
     */
    [CtTypeReferenceImpl]java.lang.String getURL();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the description of the data source.
     *
     * @param description
     * 		A String that contains the description.
     */
    [CtTypeReferenceImpl]void setDescription([CtParameterImpl][CtTypeReferenceImpl]java.lang.String description);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a description of the data source.
     *
     * @return A String that contains the data source description or null if no value is set.
     */
    [CtTypeReferenceImpl]java.lang.String getDescription();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the current network packet size used to communicate with SQL Server, specified in bytes.
     *
     * @param packetSize
     * 		An int value containing the network packet size.
     */
    [CtTypeReferenceImpl]void setPacketSize([CtParameterImpl][CtTypeReferenceImpl]int packetSize);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the current network packet size used to communicate with SQL Server, specified in bytes.
     *
     * @return An int value containing the current network packet size.
     */
    [CtTypeReferenceImpl]int getPacketSize();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the kind of integrated security you want your application to use.
     *
     * @param authenticationScheme
     * 		Values are "JavaKerberos" and the default "NativeAuthentication".
     */
    [CtTypeReferenceImpl]void setAuthenticationScheme([CtParameterImpl][CtTypeReferenceImpl]java.lang.String authenticationScheme);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the authentication mode.
     *
     * @param authentication
     * 		the authentication mode
     */
    [CtTypeReferenceImpl]void setAuthentication([CtParameterImpl][CtTypeReferenceImpl]java.lang.String authentication);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the authentication mode.
     *
     * @return the authentication value
     */
    [CtTypeReferenceImpl]java.lang.String getAuthentication();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the server spn.
     *
     * @param serverSpn
     * 		A String that contains the server spn
     */
    [CtTypeReferenceImpl]void setServerSpn([CtParameterImpl][CtTypeReferenceImpl]java.lang.String serverSpn);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the server spn.
     *
     * @return A String that contains the server spn
     */
    [CtTypeReferenceImpl]java.lang.String getServerSpn();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the GSSCredential.
     *
     * @param userCredential
     * 		the credential
     */
    [CtTypeReferenceImpl]void setGSSCredentials([CtParameterImpl][CtTypeReferenceImpl]org.ietf.jgss.GSSCredential userCredential);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the GSSCredential.
     *
     * @return GSSCredential
     */
    [CtTypeReferenceImpl]org.ietf.jgss.GSSCredential getGSSCredentials();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the access token.
     *
     * @param accessToken
     * 		to be set in the string property.
     */
    [CtTypeReferenceImpl]void setAccessToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessToken);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the access token.
     *
     * @return the access token.
     */
    [CtTypeReferenceImpl]java.lang.String getAccessToken();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value to enable/disable Always Encrypted functionality for the data source object. The default is
     * Disabled.
     *
     * @param columnEncryptionSetting
     * 		Enables/disables Always Encrypted functionality for the data source object. The default is Disabled.
     */
    [CtTypeReferenceImpl]void setColumnEncryptionSetting([CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnEncryptionSetting);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the Always Encrypted functionality setting for the data source object.
     *
     * @return the Always Encrypted functionality setting for the data source object.
     */
    [CtTypeReferenceImpl]java.lang.String getColumnEncryptionSetting();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the name that identifies a key store. Only value supported is the "JavaKeyStorePassword" for identifying the
     * Java Key Store. The default is null.
     *
     * @param keyStoreAuthentication
     * 		the name that identifies a key store.
     */
    [CtTypeReferenceImpl]void setKeyStoreAuthentication([CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyStoreAuthentication);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of the keyStoreAuthentication setting for the data source object.
     *
     * @return the value of the keyStoreAuthentication setting for the data source object.
     */
    [CtTypeReferenceImpl]java.lang.String getKeyStoreAuthentication();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the password for the Java keystore. Note that, for Java Key Store provider the password for the keystore and
     * the key must be the same. Note that, keyStoreAuthentication must be set with "JavaKeyStorePassword".
     *
     * @param keyStoreSecret
     * 		the password to use for the keystore as well as for the key
     */
    [CtTypeReferenceImpl]void setKeyStoreSecret([CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyStoreSecret);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the location including the file name for the Java keystore. Note that, keyStoreAuthentication must be set
     * with "JavaKeyStorePassword".
     *
     * @param keyStoreLocation
     * 		the location including the file name for the Java keystore.
     */
    [CtTypeReferenceImpl]void setKeyStoreLocation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyStoreLocation);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the keyStoreLocation for the Java Key Store.
     *
     * @return the keyStoreLocation for the Java Key Store.
     */
    [CtTypeReferenceImpl]java.lang.String getKeyStoreLocation();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Setting the query timeout.
     *
     * @param queryTimeout
     * 		The number of seconds to wait before a timeout has occurred on a query. The default value is 0, which
     * 		means infinite timeout.
     */
    [CtTypeReferenceImpl]void setQueryTimeout([CtParameterImpl][CtTypeReferenceImpl]int queryTimeout);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the query timeout.
     *
     * @return The number of seconds to wait before a timeout has occurred on a query.
     */
    [CtTypeReferenceImpl]int getQueryTimeout();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the cancel timeout.
     *
     * @param cancelQueryTimeout
     * 		The number of seconds to wait before we wait for the query timeout to happen.
     */
    [CtTypeReferenceImpl]void setCancelQueryTimeout([CtParameterImpl][CtTypeReferenceImpl]int cancelQueryTimeout);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the cancel timeout.
     *
     * @return the number of seconds to wait before we wait for the query timeout to happen.
     */
    [CtTypeReferenceImpl]int getCancelQueryTimeout();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value that enables/disables whether the first execution of a prepared statement will call sp_executesql
     * and not prepare a statement. If this configuration is false the first execution of a prepared statement will call
     * sp_executesql and not prepare a statement, once the second execution happens it will call sp_prepexec and
     * actually setup a prepared statement handle. Following executions will call sp_execute. This relieves the need for
     * sp_unprepare on prepared statement close if the statement is only executed once.
     *
     * @param enablePrepareOnFirstPreparedStatementCall
     * 		Changes the setting per the description.
     */
    [CtTypeReferenceImpl]void setEnablePrepareOnFirstPreparedStatementCall([CtParameterImpl][CtTypeReferenceImpl]boolean enablePrepareOnFirstPreparedStatementCall);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value that indicates whether the first execution of a prepared statement will call sp_executesql and
     * not prepare a statement. If this configuration returns false the first execution of a prepared statement will
     * call sp_executesql and not prepare a statement, once the second execution happens it will call sp_prepexec and
     * actually setup a prepared statement handle. Following executions will call sp_execute. This relieves the need for
     * sp_unprepare on prepared statement close if the statement is only executed once.
     *
     * @return Returns the current setting per the description.
     */
    [CtTypeReferenceImpl]boolean getEnablePrepareOnFirstPreparedStatementCall();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value that controls how many outstanding prepared statement discard actions (sp_unprepare) can be
     * outstanding per connection before a call to clean-up the outstanding handles on the server is executed. If the
     * setting is {@literal <=} 1 unprepare actions will be executed immedietely on prepared statement close. If it is
     * set to {@literal >} 1 these calls will be batched together to avoid overhead of calling sp_unprepare too often.
     *
     * @param serverPreparedStatementDiscardThreshold
     * 		Changes the setting per the description.
     */
    [CtTypeReferenceImpl]void setServerPreparedStatementDiscardThreshold([CtParameterImpl][CtTypeReferenceImpl]int serverPreparedStatementDiscardThreshold);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of the setting that controls how many outstanding prepared statement discard actions
     * (sp_unprepare) can be outstanding per connection before a call to clean-up the outstanding handles on the server
     * is executed.
     *
     * @return Returns the current setting per the description.
     */
    [CtTypeReferenceImpl]int getServerPreparedStatementDiscardThreshold();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the size of the prepared statement cache for this connection. A value less than 1 means no cache.
     *
     * @param statementPoolingCacheSize
     * 		Changes the setting per the description.
     */
    [CtTypeReferenceImpl]void setStatementPoolingCacheSize([CtParameterImpl][CtTypeReferenceImpl]int statementPoolingCacheSize);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the size of the prepared statement cache for this connection. A value less than 1 means no cache.
     *
     * @return Returns the current setting per the description.
     */
    [CtTypeReferenceImpl]int getStatementPoolingCacheSize();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the value to disable/enable statement pooling.
     *
     * @param disableStatementPooling
     * 		true to disable statement pooling, false to enable it.
     */
    [CtTypeReferenceImpl]void setDisableStatementPooling([CtParameterImpl][CtTypeReferenceImpl]boolean disableStatementPooling);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns whether statement pooling is disabled.
     *
     * @return true if statement pooling is disabled, false if it is enabled.
     */
    [CtTypeReferenceImpl]boolean getDisableStatementPooling();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the socket timeout value.
     *
     * @param socketTimeout
     * 		The number of milliseconds to wait before a timeout is occurred on a socket read or accept. The default
     * 		value is 0, which means infinite timeout.
     */
    [CtTypeReferenceImpl]void setSocketTimeout([CtParameterImpl][CtTypeReferenceImpl]int socketTimeout);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the socket timeout value.
     *
     * @return The number of milliseconds to wait before a timeout is occurred on a socket read or accept.
     */
    [CtTypeReferenceImpl]int getSocketTimeout();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the login configuration file for Kerberos authentication. This overrides the default configuration <i>
     * SQLJDBCDriver </i>
     *
     * @param configurationName
     * 		the configuration name
     */
    [CtTypeReferenceImpl]void setJASSConfigurationName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String configurationName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the login configuration file for Kerberos authentication.
     *
     * @return login configuration file name
     */
    [CtTypeReferenceImpl]java.lang.String getJASSConfigurationName();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets whether Fips Mode should be enabled/disabled on the connection. For FIPS enabled JVM this property should be
     * true.
     *
     * @param fips
     * 		Boolean property to enable/disable fips
     */
    [CtTypeReferenceImpl]void setFIPS([CtParameterImpl][CtTypeReferenceImpl]boolean fips);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of connection property "fips". For FIPS enabled JVM this property should be true.
     *
     * @return fips boolean value
     */
    [CtTypeReferenceImpl]boolean getFIPS();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the sslProtocol property for connection Set this value to specify TLS protocol keyword.
     *
     * Acceptable values are: TLS, TLSv1, TLSv1.1, and TLSv1.2.
     *
     * @param sslProtocol
     * 		Value for SSL Protocol to be set.
     */
    [CtTypeReferenceImpl]void setSSLProtocol([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sslProtocol);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value of connection property 'sslProtocol'.
     *
     * @return sslProtocol property value
     */
    [CtTypeReferenceImpl]java.lang.String getSSLProtocol();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the connection property 'trustManagerClass' on the connection.
     *
     * @param trustManagerClass
     * 		The fully qualified class name of a custom javax.net.ssl.TrustManager.
     */
    [CtTypeReferenceImpl]void setTrustManagerClass([CtParameterImpl][CtTypeReferenceImpl]java.lang.String trustManagerClass);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value for the connection property 'trustManagerClass'.
     *
     * @return trustManagerClass property value
     */
    [CtTypeReferenceImpl]java.lang.String getTrustManagerClass();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets Constructor Arguments to be provided on constructor of 'trustManagerClass'.
     *
     * @param trustManagerConstructorArg
     * 		'trustManagerClass' constructor arguments
     */
    [CtTypeReferenceImpl]void setTrustManagerConstructorArg([CtParameterImpl][CtTypeReferenceImpl]java.lang.String trustManagerConstructorArg);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value for the connection property 'trustManagerConstructorArg'.
     *
     * @return trustManagerConstructorArg property value
     */
    [CtTypeReferenceImpl]java.lang.String getTrustManagerConstructorArg();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns whether the use Bulk Copy API is used for Batch Insert.
     *
     * @return whether the driver should use Bulk Copy API for Batch Insert operations.
     */
    [CtTypeReferenceImpl]boolean getUseBulkCopyForBatchInsert();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets whether the use Bulk Copy API should be used for Batch Insert.
     *
     * @param useBulkCopyForBatchInsert
     * 		indicates whether Bulk Copy API should be used for Batch Insert operations.
     */
    [CtTypeReferenceImpl]void setUseBulkCopyForBatchInsert([CtParameterImpl][CtTypeReferenceImpl]boolean useBulkCopyForBatchInsert);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the client id to be used to retrieve access token from MSI EndPoint.
     *
     * @param msiClientId
     * 		Client ID of User Assigned Managed Identity
     */
    [CtTypeReferenceImpl]void setMSIClientId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String msiClientId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value for the connection property 'msiClientId'.
     *
     * @return msiClientId property value
     */
    [CtTypeReferenceImpl]java.lang.String getMSIClientId();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the Azure Key Vault (AKV) Provider user principal id.
     *
     * @param keyVaultPrincipalId
     * 		principal Id of Azure Key Vault (AKV) Provider to be used for column encryption.
     */
    [CtTypeReferenceImpl]void setKeyStorePrincipalId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyVaultPrincipalId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value for the connection property 'keyVaultPrincipalId'.
     *
     * @return keyVaultPrincipalId
     */
    [CtTypeReferenceImpl]java.lang.String getKeyStorePrincipalId();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the Azure Key Vault (AKV) Provider Client Id to provided value to be used for column encryption.
     *
     * @param keyVaultProviderClientId
     * 		Client Id of Azure Key Vault (AKV) Provider to be used for column encryption.
     */
    [CtTypeReferenceImpl]void setKeyVaultProviderClientId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyVaultProviderClientId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value for the connection property 'keyVaultProviderClientId'.
     *
     * @return keyVaultProviderClientId
     */
    [CtTypeReferenceImpl]java.lang.String getKeyVaultProviderClientId();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the Azure Key Vault (AKV) Provider Client Key to provided value to be used for column encryption.
     *
     * @param keyVaultProviderClientKey
     * 		Client Key of Azure Key Vault (AKV) Provider to be used for column encryption.
     */
    [CtTypeReferenceImpl]void setKeyVaultProviderClientKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyVaultProviderClientKey);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the value for the connection property 'domain'.
     *
     * @return 'domain' property value
     */
    [CtTypeReferenceImpl]java.lang.String getDomain();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the 'domain' connection property used for NTLM Authentication.
     *
     * @param domain
     * 		Windows domain name
     */
    [CtTypeReferenceImpl]void setDomain([CtParameterImpl][CtTypeReferenceImpl]java.lang.String domain);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the current flag value for useFmtOnly.
     *
     * @return 'useFmtOnly' property value.
     */
    [CtTypeReferenceImpl]boolean getUseFmtOnly();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Specifies the flag to use FMTONLY for parameter metadata queries.
     *
     * @param useFmtOnly
     * 		boolean value for 'useFmtOnly'.
     */
    [CtTypeReferenceImpl]void setUseFmtOnly([CtParameterImpl][CtTypeReferenceImpl]boolean useFmtOnly);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the enclave attestation URL used in Always Encrypted with Secure Enclaves.
     *
     * @return enclave attestation URL.
     */
    [CtTypeReferenceImpl]java.lang.String getEnclaveAttestationUrl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the enclave attestation URL used in Always Encrypted with Secure Enclaves.
     *
     * @param url
     * 		Enclave attestation URL.
     */
    [CtTypeReferenceImpl]void setEnclaveAttestationUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the enclave attestation protocol used in Always Encrypted with Secure Enclaves.
     *
     * @return Enclave attestation protocol.
     */
    [CtTypeReferenceImpl]java.lang.String getEnclaveAttestationProtocol();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the enclave attestation protocol to be used in Always Encrypted with Secure Enclaves.
     *
     * @param protocol
     * 		Enclave attestation protocol.
     */
    [CtTypeReferenceImpl]void setEnclaveAttestationProtocol([CtParameterImpl][CtTypeReferenceImpl]java.lang.String protocol);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns client certificate path for client certificate authentication.
     *
     * @return Client certificate path.
     */
    [CtTypeReferenceImpl]java.lang.String getClientCertificate();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets client certificate path for client certificate authentication.
     *
     * @param certPath
     * 		Client certificate path.
     */
    [CtTypeReferenceImpl]void setClientCertificate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String certPath);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns Private key file path for client certificate authentication.
     *
     * @return Private key file path.
     */
    [CtTypeReferenceImpl]java.lang.String getClientKey();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets Private key file path for client certificate authentication.
     *
     * @param keyPath
     * 		Private key file path.
     */
    [CtTypeReferenceImpl]void setClientKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyPath);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the password to be used for Private key provided by the user for client certificate authentication.
     *
     * @param password
     * 		Private key password.
     */
    [CtTypeReferenceImpl]void setClientKeyPassword([CtParameterImpl][CtTypeReferenceImpl]java.lang.String password);
}
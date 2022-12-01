[CompilationUnitImpl][CtPackageDeclarationImpl]package com.amazon.opendistroforelasticsearch.security.auditlog.config;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonProperty;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtImportImpl]import java.util.EnumSet;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonCreator;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.security.compliance.ComplianceConfig;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.security.support.ConfigConstants;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory;
[CtUnresolvedImport]import org.apache.logging.log4j.Logger;
[CtUnresolvedImport]import org.elasticsearch.common.settings.Settings;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.security.support.WildcardMatcher;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtJavaDocImpl]/**
 * Class represents configuration for audit logging.
 * Expected class structure
 * {
 *   "enabled": true,
 *   "audit" : {
 *     "enable_rest" : true,
 *     "disabled_rest_categories" : [
 *       "GRANTED_PRIVILEGES",
 *       "SSL_EXCEPTION"
 *     ],
 *     "enable_transport" : true,
 *     "disabled_transport_categories" : [
 *       "GRANTED_PRIVILEGES",
 *       "AUTHENTICATED"
 *     ],
 *     "resolve_bulk_requests" : false,
 *     "log_request_body" : true,
 *     "resolve_indices" : true,
 *     "exclude_sensitive_headers" : true,
 *     "ignore_users" : [
 *       "kibanaserver"
 *     ],
 *     "ignore_requests" : [ ]
 *   },
 *   "compliance" : {
 *     "enabled": true,
 *     "internal_config" : true,
 *     "external_config" : true,
 *     "read_metadata_only" : true,
 *     "read_watched_fields" : { },
 *     "read_ignore_users" : [ ],
 *     "write_metadata_only" : true,
 *     "write_log_diffs" : false,
 *     "write_watched_indices" : [ ],
 *     "write_ignore_users" : [ ]
 *   }
 * }
 */
public class AuditConfig {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> DEFAULT_IGNORED_USERS = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"kibanaserver");

    [CtConstructorImpl]private AuditConfig() [CtBlockImpl]{
        [CtInvocationImpl]this([CtLiteralImpl]true, [CtLiteralImpl]null, [CtLiteralImpl]null);
    }

    [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"enabled")
    private final [CtTypeReferenceImpl]boolean auditLogEnabled;

    [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"audit")
    private final [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter filter;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.compliance.ComplianceConfig compliance;

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]auditLogEnabled;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter getFilter() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]filter;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.compliance.ComplianceConfig getCompliance() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]compliance;
    }

    [CtConstructorImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    public AuditConfig([CtParameterImpl]final [CtTypeReferenceImpl]boolean auditLogEnabled, [CtParameterImpl]final [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter filter, [CtParameterImpl]final [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.compliance.ComplianceConfig compliance) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.auditLogEnabled = [CtVariableReadImpl]auditLogEnabled;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.filter = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]filter != [CtLiteralImpl]null) ? [CtVariableReadImpl]filter : [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter.[CtFieldReferenceImpl]DEFAULT;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.compliance = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]compliance != [CtLiteralImpl]null) ? [CtVariableReadImpl]compliance : [CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.compliance.ComplianceConfig.DEFAULT;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig from([CtParameterImpl]final [CtTypeReferenceImpl]org.elasticsearch.common.settings.Settings settings) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig([CtLiteralImpl]true, [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter.from([CtVariableReadImpl]settings), [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.compliance.ComplianceConfig.from([CtVariableReadImpl]settings));
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Filter represents set of filtering configuration settings for audit logging.
     * Audit logger will use these settings to determine what audit logs are to be generated.
     */
    public static class Filter {
        [CtFieldImpl]public static final [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter DEFAULT = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter.from([CtTypeAccessImpl]Settings.EMPTY);

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isRestApiAuditEnabled;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isTransportApiAuditEnabled;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean resolveBulkRequests;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean logRequestBody;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean resolveIndices;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean excludeSensitiveHeaders;

        [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"ignore_users")
        private final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoredAuditUsers;

        [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"ignore_requests")
        private final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoredAuditRequests;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.support.WildcardMatcher ignoredAuditUsersMatcher;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.support.WildcardMatcher ignoredAuditRequestsMatcher;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledRestCategories;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledTransportCategories;

        [CtConstructorImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
        Filter([CtParameterImpl]final [CtTypeReferenceImpl]boolean isRestApiAuditEnabled, [CtParameterImpl]final [CtTypeReferenceImpl]boolean isTransportApiAuditEnabled, [CtParameterImpl]final [CtTypeReferenceImpl]boolean resolveBulkRequests, [CtParameterImpl]final [CtTypeReferenceImpl]boolean logRequestBody, [CtParameterImpl]final [CtTypeReferenceImpl]boolean resolveIndices, [CtParameterImpl]final [CtTypeReferenceImpl]boolean excludeSensitiveHeaders, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoredAuditUsers, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoredAuditRequests, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledRestCategories, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledTransportCategories) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isRestApiAuditEnabled = [CtVariableReadImpl]isRestApiAuditEnabled;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isTransportApiAuditEnabled = [CtVariableReadImpl]isTransportApiAuditEnabled;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.resolveBulkRequests = [CtVariableReadImpl]resolveBulkRequests;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.logRequestBody = [CtVariableReadImpl]logRequestBody;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.resolveIndices = [CtVariableReadImpl]resolveIndices;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludeSensitiveHeaders = [CtVariableReadImpl]excludeSensitiveHeaders;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoredAuditUsers = [CtVariableReadImpl]ignoredAuditUsers;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoredAuditUsersMatcher = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.support.WildcardMatcher.from([CtVariableReadImpl]ignoredAuditUsers);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoredAuditRequests = [CtVariableReadImpl]ignoredAuditRequests;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoredAuditRequestsMatcher = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.support.WildcardMatcher.from([CtVariableReadImpl]ignoredAuditRequests);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.disabledRestCategories = [CtVariableReadImpl]disabledRestCategories;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.disabledTransportCategories = [CtVariableReadImpl]disabledTransportCategories;
        }

        [CtMethodImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonCreator
        [CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
        public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter from([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> properties) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isRestApiAuditEnabled = [CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"enable_rest", [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isTransportAuditEnabled = [CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"enable_transport", [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean resolveBulkRequests = [CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"resolve_bulk_requests", [CtLiteralImpl]false);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean logRequestBody = [CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"log_request_body", [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean resolveIndices = [CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"resolve_indices", [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean excludeSensitiveHeaders = [CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"exclude_sensitive_headers", [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledRestCategories = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory.parse([CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"disabled_rest_categories", [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_DISABLED_CATEGORIES_DEFAULT));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledTransportCategories = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory.parse([CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"disabled_transport_categories", [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_DISABLED_CATEGORIES_DEFAULT));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoredAuditUsers = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"ignore_users", [CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.DEFAULT_IGNORED_USERS));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoreAuditRequests = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtInvocationImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.getOrDefault([CtVariableReadImpl]properties, [CtLiteralImpl]"ignore_requests", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList()));
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter([CtVariableReadImpl]isRestApiAuditEnabled, [CtVariableReadImpl]isTransportAuditEnabled, [CtVariableReadImpl]resolveBulkRequests, [CtVariableReadImpl]logRequestBody, [CtVariableReadImpl]resolveIndices, [CtVariableReadImpl]excludeSensitiveHeaders, [CtVariableReadImpl]ignoredAuditUsers, [CtVariableReadImpl]ignoreAuditRequests, [CtVariableReadImpl]disabledRestCategories, [CtVariableReadImpl]disabledTransportCategories);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Generate audit logging configuration from settings defined in elasticsearch.yml
         *
         * @param settings
         * 		settings
         * @return audit configuration filter
         */
        public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter from([CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.common.settings.Settings settings) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isRestApiAuditEnabled = [CtInvocationImpl][CtVariableReadImpl]settings.getAsBoolean([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_ENABLE_REST, [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isTransportAuditEnabled = [CtInvocationImpl][CtVariableReadImpl]settings.getAsBoolean([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_ENABLE_TRANSPORT, [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean resolveBulkRequests = [CtInvocationImpl][CtVariableReadImpl]settings.getAsBoolean([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_RESOLVE_BULK_REQUESTS, [CtLiteralImpl]false);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean logRequestBody = [CtInvocationImpl][CtVariableReadImpl]settings.getAsBoolean([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_LOG_REQUEST_BODY, [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean resolveIndices = [CtInvocationImpl][CtVariableReadImpl]settings.getAsBoolean([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_RESOLVE_INDICES, [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean excludeSensitiveHeaders = [CtInvocationImpl][CtVariableReadImpl]settings.getAsBoolean([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_EXCLUDE_SENSITIVE_HEADERS, [CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledRestCategories = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory.from([CtVariableReadImpl]settings, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_CONFIG_DISABLED_REST_CATEGORIES);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> disabledTransportCategories = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory.from([CtVariableReadImpl]settings, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_CONFIG_DISABLED_TRANSPORT_CATEGORIES);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoredAuditUsers = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.support.ConfigConstants.getSettingAsSet([CtVariableReadImpl]settings, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_IGNORE_USERS, [CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.DEFAULT_IGNORED_USERS, [CtLiteralImpl]false);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> ignoreAuditRequests = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtInvocationImpl][CtVariableReadImpl]settings.getAsList([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_IGNORE_REQUESTS, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList()));
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.Filter([CtVariableReadImpl]isRestApiAuditEnabled, [CtVariableReadImpl]isTransportAuditEnabled, [CtVariableReadImpl]resolveBulkRequests, [CtVariableReadImpl]logRequestBody, [CtVariableReadImpl]resolveIndices, [CtVariableReadImpl]excludeSensitiveHeaders, [CtVariableReadImpl]ignoredAuditUsers, [CtVariableReadImpl]ignoreAuditRequests, [CtVariableReadImpl]disabledRestCategories, [CtVariableReadImpl]disabledTransportCategories);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Checks if auditing for REST API is enabled or disabled
         *
         * @return true/false
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"enable_rest")
        public [CtTypeReferenceImpl]boolean isRestApiAuditEnabled() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]isRestApiAuditEnabled;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Checks if auditing for Transport API is enabled or disabled
         *
         * @return true/false
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"enable_transport")
        public [CtTypeReferenceImpl]boolean isTransportApiAuditEnabled() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]isTransportApiAuditEnabled;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Checks if bulk requests must be resolved during auditing
         *
         * @return true/false
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"resolve_bulk_requests")
        public [CtTypeReferenceImpl]boolean shouldResolveBulkRequests() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]resolveBulkRequests;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Checks if request body must be logged
         *
         * @return true/false
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"log_request_body")
        public [CtTypeReferenceImpl]boolean shouldLogRequestBody() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]logRequestBody;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Check if indices must be resolved during auditing
         *
         * @return true/false
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"resolve_indices")
        public [CtTypeReferenceImpl]boolean shouldResolveIndices() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]resolveIndices;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Checks if sensitive headers eg: Authorization must be excluded in log messages
         *
         * @return true/false
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"exclude_sensitive_headers")
        public [CtTypeReferenceImpl]boolean shouldExcludeSensitiveHeaders() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]excludeSensitiveHeaders;
        }

        [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
        [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.support.WildcardMatcher getIgnoredAuditUsersMatcher() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]ignoredAuditUsersMatcher;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Check if user is excluded from audit.
         *
         * @param user
         * @return true if user is excluded from audit logging
         */
        public [CtTypeReferenceImpl]boolean isAuditDisabled([CtParameterImpl][CtTypeReferenceImpl]java.lang.String user) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]ignoredAuditUsersMatcher.test([CtVariableReadImpl]user);
        }

        [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
        [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.support.WildcardMatcher getIgnoredAuditRequestsMatcher() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]ignoredAuditRequestsMatcher;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Check if request is excluded from audit
         *
         * @param action
         * @return true if request action is excluded from audit
         */
        public [CtTypeReferenceImpl]boolean isRequestAuditDisabled([CtParameterImpl][CtTypeReferenceImpl]java.lang.String action) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]ignoredAuditRequestsMatcher.test([CtVariableReadImpl]action);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Disabled categories for REST API auditing
         *
         * @return set of categories
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"disabled_rest_categories")
        public [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> getDisabledRestCategories() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]disabledRestCategories;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Disabled categories for Transport API auditing
         *
         * @return set of categories
         */
        [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"disabled_transport_categories")
        public [CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.security.auditlog.impl.AuditCategory> getDisabledTransportCategories() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]disabledTransportCategories;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void log([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.Logger logger) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"Auditing on REST API is {}.", [CtConditionalImpl][CtFieldReadImpl]isRestApiAuditEnabled ? [CtLiteralImpl]"enabled" : [CtLiteralImpl]"disabled");
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"{} are excluded from REST API auditing.", [CtFieldReadImpl]disabledRestCategories);
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"Auditing on Transport API is {}.", [CtConditionalImpl][CtFieldReadImpl]isTransportApiAuditEnabled ? [CtLiteralImpl]"enabled" : [CtLiteralImpl]"disabled");
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"{} are excluded from Transport API auditing.", [CtFieldReadImpl]disabledTransportCategories);
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"Auditing of request body is {}.", [CtConditionalImpl][CtFieldReadImpl]logRequestBody ? [CtLiteralImpl]"enabled" : [CtLiteralImpl]"disabled");
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"Bulk requests resolution is {} during request auditing.", [CtConditionalImpl][CtFieldReadImpl]resolveBulkRequests ? [CtLiteralImpl]"enabled" : [CtLiteralImpl]"disabled");
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"Index resolution is {} during request auditing.", [CtConditionalImpl][CtFieldReadImpl]resolveIndices ? [CtLiteralImpl]"enabled" : [CtLiteralImpl]"disabled");
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"Sensitive headers auditing is {}.", [CtConditionalImpl][CtFieldReadImpl]excludeSensitiveHeaders ? [CtLiteralImpl]"enabled" : [CtLiteralImpl]"disabled");
            [CtInvocationImpl][CtVariableReadImpl]logger.info([CtLiteralImpl]"Auditing requests from {} users is disabled.", [CtFieldReadImpl]ignoredAuditUsersMatcher);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Filter{" + [CtLiteralImpl]"isRestApiAuditEnabled=") + [CtFieldReadImpl]isRestApiAuditEnabled) + [CtLiteralImpl]", disabledRestCategories=") + [CtFieldReadImpl]disabledRestCategories) + [CtLiteralImpl]", isTransportApiAuditEnabled=") + [CtFieldReadImpl]isTransportApiAuditEnabled) + [CtLiteralImpl]", disabledTransportCategories=") + [CtFieldReadImpl]disabledTransportCategories) + [CtLiteralImpl]", resolveBulkRequests=") + [CtFieldReadImpl]resolveBulkRequests) + [CtLiteralImpl]", logRequestBody=") + [CtFieldReadImpl]logRequestBody) + [CtLiteralImpl]", resolveIndices=") + [CtFieldReadImpl]resolveIndices) + [CtLiteralImpl]", excludeSensitiveHeaders=") + [CtFieldReadImpl]excludeSensitiveHeaders) + [CtLiteralImpl]", ignoredAuditUsers=") + [CtFieldReadImpl]ignoredAuditUsersMatcher) + [CtLiteralImpl]", ignoreAuditRequests=") + [CtFieldReadImpl]ignoredAuditRequestsMatcher) + [CtLiteralImpl]'}';
        }
    }

    [CtFieldImpl][CtJavaDocImpl]/**
     * List of keys that are deprecated
     */
    public static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> DEPRECATED_KEYS = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_ENABLE_REST, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_CONFIG_DISABLED_REST_CATEGORIES, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_ENABLE_TRANSPORT, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_CONFIG_DISABLED_TRANSPORT_CATEGORIES, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_LOG_REQUEST_BODY, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_RESOLVE_INDICES, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_EXCLUDE_SENSITIVE_HEADERS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_RESOLVE_BULK_REQUESTS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_IGNORE_USERS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_AUDIT_IGNORE_REQUESTS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_INTERNAL_CONFIG_ENABLED, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_EXTERNAL_CONFIG_ENABLED, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_READ_METADATA_ONLY, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_READ_IGNORE_USERS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_READ_WATCHED_FIELDS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_WRITE_METADATA_ONLY, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_WRITE_LOG_DIFFS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_WRITE_IGNORE_USERS, [CtTypeAccessImpl]ConfigConstants.OPENDISTRO_SECURITY_COMPLIANCE_HISTORY_WRITE_WATCHED_INDICES);

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> getDeprecatedKeys([CtParameterImpl]final [CtTypeReferenceImpl]org.elasticsearch.common.settings.Settings settings) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.security.auditlog.config.AuditConfig.[CtFieldReferenceImpl]DEPRECATED_KEYS.stream().filter([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]settings::hasValue).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
    }
}
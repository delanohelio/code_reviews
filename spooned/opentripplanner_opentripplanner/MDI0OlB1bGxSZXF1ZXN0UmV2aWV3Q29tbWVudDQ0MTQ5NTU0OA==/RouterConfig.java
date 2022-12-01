[CompilationUnitImpl][CtPackageDeclarationImpl]package org.opentripplanner.standalone.config;
[CtUnresolvedImport]import org.opentripplanner.routing.algorithm.raptor.transit.TransitTuningParameters;
[CtUnresolvedImport]import static org.opentripplanner.standalone.config.RoutingRequestMapper.mapRoutingRequest;
[CtUnresolvedImport]import org.opentripplanner.transit.raptor.api.request.RaptorTuningParameters;
[CtImportImpl]import com.fasterxml.jackson.databind.JsonNode;
[CtImportImpl]import com.fasterxml.jackson.databind.node.MissingNode;
[CtUnresolvedImport]import org.opentripplanner.routing.api.request.RoutingRequest;
[CtImportImpl]import java.net.URI;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.io.Serializable;
[CtClassImpl][CtJavaDocImpl]/**
 * This class is an object representation of the 'router-config.json'.
 */
public class RouterConfig implements [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]double DEFAULT_STREET_ROUTING_TIMEOUT = [CtLiteralImpl]5.0;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.opentripplanner.standalone.config.RouterConfig.class);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]org.opentripplanner.standalone.config.RouterConfig DEFAULT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opentripplanner.standalone.config.RouterConfig([CtInvocationImpl][CtTypeAccessImpl]com.fasterxml.jackson.databind.node.MissingNode.getInstance(), [CtLiteralImpl]"DEFAULT", [CtLiteralImpl]false);

    [CtFieldImpl][CtJavaDocImpl]/**
     * The raw JsonNode three kept for reference and (de)serialization.
     */
    public final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode rawJson;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String requestLogFile;

    [CtFieldImpl]private final [CtTypeReferenceImpl]double streetRoutingTimeoutSeconds;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.net.URI bikeRentalServiceDirectoryUrl;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.opentripplanner.routing.api.request.RoutingRequest routingRequestDefaults;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.opentripplanner.standalone.config.TransitRoutingConfig transitConfig;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.opentripplanner.standalone.config.UpdaterConfig updaterConfig;

    [CtConstructorImpl]public RouterConfig([CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String source, [CtParameterImpl][CtTypeReferenceImpl]boolean logUnusedParams) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opentripplanner.standalone.config.NodeAdapter adapter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opentripplanner.standalone.config.NodeAdapter([CtVariableReadImpl]node, [CtVariableReadImpl]source);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rawJson = [CtVariableReadImpl]node;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestLogFile = [CtInvocationImpl][CtVariableReadImpl]adapter.asText([CtLiteralImpl]"requestLogFile", [CtLiteralImpl]null);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.streetRoutingTimeoutSeconds = [CtInvocationImpl][CtVariableReadImpl]adapter.asDouble([CtLiteralImpl]"streetRoutingTimeout", [CtFieldReadImpl]org.opentripplanner.standalone.config.RouterConfig.DEFAULT_STREET_ROUTING_TIMEOUT);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bikeRentalServiceDirectoryUrl = [CtInvocationImpl][CtVariableReadImpl]adapter.asUri([CtLiteralImpl]"bikeRentalServiceDirectoryUrl", [CtLiteralImpl]null);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transitConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opentripplanner.standalone.config.TransitRoutingConfig([CtInvocationImpl][CtVariableReadImpl]adapter.path([CtLiteralImpl]"transit"));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.routingRequestDefaults = [CtInvocationImpl]org.opentripplanner.standalone.config.RoutingRequestMapper.mapRoutingRequest([CtInvocationImpl][CtVariableReadImpl]adapter.path([CtLiteralImpl]"routingDefaults"));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.updaterConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opentripplanner.standalone.config.UpdaterConfig([CtInvocationImpl][CtVariableReadImpl]adapter.path([CtLiteralImpl]"updaters"));
        [CtIfImpl]if ([CtVariableReadImpl]logUnusedParams) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]adapter.logAllUnusedParameters([CtFieldReadImpl]org.opentripplanner.standalone.config.RouterConfig.LOG);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String requestLogFile() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]requestLogFile;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The preferred way to limit the search is to limit the distance for
     * each street mode(WALK, BIKE, CAR). So the default timeout for a
     * street search is set quite high. This is used to abort the search
     * if the max distance is not reached within the timeout.
     */
    public [CtTypeReferenceImpl]double streetRoutingTimeoutSeconds() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]streetRoutingTimeoutSeconds;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.net.URI getBikeRentalServiceDirectoryUrl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]bikeRentalServiceDirectoryUrl;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.opentripplanner.routing.api.request.RoutingRequest routingRequestDefaults() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]routingRequestDefaults;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.opentripplanner.transit.raptor.api.request.RaptorTuningParameters raptorTuningParameters() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]transitConfig;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.opentripplanner.routing.algorithm.raptor.transit.TransitTuningParameters transitTuningParameters() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]transitConfig;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.opentripplanner.standalone.config.UpdaterConfig updaterConfig() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]updaterConfig;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If {@code true} the config is loaded from file, in not the DEFAULT config is used.
     */
    public [CtTypeReferenceImpl]boolean isDefault() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.rawJson.isMissingNode();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String toJson() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtFieldReadImpl]rawJson.isMissingNode() ? [CtLiteralImpl]"" : [CtInvocationImpl][CtFieldReadImpl]rawJson.toString();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// Print ONLY the values set, not deafult values
        return [CtInvocationImpl][CtFieldReadImpl]rawJson.toPrettyString();
    }
}
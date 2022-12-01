[CompilationUnitImpl][CtJavaDocImpl]/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
[CtPackageDeclarationImpl]package org.graylog2.streams;
[CtUnresolvedImport]import org.graylog2.indexer.indexset.IndexSetConfig;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.graylog2.plugin.Tools;
[CtUnresolvedImport]import org.graylog2.alarmcallbacks.AlarmCallbackConfiguration;
[CtUnresolvedImport]import org.graylog2.alerts.AlertService;
[CtUnresolvedImport]import org.graylog2.plugin.streams.StreamRule;
[CtUnresolvedImport]import com.mongodb.BasicDBList;
[CtUnresolvedImport]import com.mongodb.DBObject;
[CtUnresolvedImport]import org.graylog2.plugin.database.EmbeddedPersistable;
[CtUnresolvedImport]import javax.ws.rs.BadRequestException;
[CtUnresolvedImport]import org.graylog2.indexer.IndexSet;
[CtUnresolvedImport]import org.graylog2.streams.events.StreamsChangedEvent;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.graylog2.database.PersistedServiceImpl;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.graylog2.notifications.NotificationService;
[CtUnresolvedImport]import org.graylog2.rest.resources.streams.requests.CreateStreamRequest;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtUnresolvedImport]import org.graylog2.events.ClusterEventBus;
[CtUnresolvedImport]import org.graylog2.indexer.indexset.IndexSetService;
[CtUnresolvedImport]import org.graylog2.plugin.streams.Stream;
[CtUnresolvedImport]import org.graylog2.plugin.alarms.AlertCondition;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.graylog2.database.MongoConnection;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.mongodb.QueryBuilder;
[CtUnresolvedImport]import org.graylog2.alarmcallbacks.EmailAlarmCallback;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static com.google.common.base.Strings.isNullOrEmpty;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import com.google.common.collect.Lists;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtImportImpl]import java.util.stream.StreamSupport;
[CtUnresolvedImport]import org.graylog2.indexer.MongoIndexSet;
[CtUnresolvedImport]import org.graylog2.alerts.Alert;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.graylog2.database.NotFoundException;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import org.graylog2.notifications.Notification;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import com.mongodb.BasicDBObject;
[CtUnresolvedImport]import org.graylog2.streams.events.StreamDeletedEvent;
[CtUnresolvedImport]import com.google.common.collect.Maps;
[CtUnresolvedImport]import org.graylog2.alarmcallbacks.AlarmCallbackConfigurationImpl;
[CtUnresolvedImport]import org.graylog2.alarmcallbacks.AlarmCallbackConfigurationService;
[CtUnresolvedImport]import com.mongodb.DBCursor;
[CtUnresolvedImport]import org.graylog2.plugin.streams.Output;
[CtUnresolvedImport]import org.graylog2.plugin.database.ValidationException;
[CtUnresolvedImport]import org.bson.types.ObjectId;
[CtClassImpl]public class StreamServiceImpl extends [CtTypeReferenceImpl]org.graylog2.database.PersistedServiceImpl implements [CtTypeReferenceImpl]org.graylog2.streams.StreamService {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.graylog2.streams.StreamServiceImpl.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.graylog2.streams.StreamRuleService streamRuleService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.graylog2.alerts.AlertService alertService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.graylog2.streams.OutputService outputService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.graylog2.indexer.indexset.IndexSetService indexSetService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]MongoIndexSet.Factory indexSetFactory;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.graylog2.notifications.NotificationService notificationService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.graylog2.events.ClusterEventBus clusterEventBus;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.graylog2.alarmcallbacks.AlarmCallbackConfigurationService alarmCallbackConfigurationService;

    [CtConstructorImpl][CtAnnotationImpl]@javax.inject.Inject
    public StreamServiceImpl([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.database.MongoConnection mongoConnection, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.streams.StreamRuleService streamRuleService, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.alerts.AlertService alertService, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.streams.OutputService outputService, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.indexer.indexset.IndexSetService indexSetService, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.graylog2.indexer.MongoIndexSet.Factory indexSetFactory, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.notifications.NotificationService notificationService, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.events.ClusterEventBus clusterEventBus, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.alarmcallbacks.AlarmCallbackConfigurationService alarmCallbackConfigurationService) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]mongoConnection);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.streamRuleService = [CtVariableReadImpl]streamRuleService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.alertService = [CtVariableReadImpl]alertService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.outputService = [CtVariableReadImpl]outputService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexSetService = [CtVariableReadImpl]indexSetService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexSetFactory = [CtVariableReadImpl]indexSetFactory;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.notificationService = [CtVariableReadImpl]notificationService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.clusterEventBus = [CtVariableReadImpl]clusterEventBus;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.alarmCallbackConfigurationService = [CtVariableReadImpl]alarmCallbackConfigurationService;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private [CtTypeReferenceImpl]org.graylog2.indexer.IndexSet getIndexSet([CtParameterImpl][CtTypeReferenceImpl]com.mongodb.DBObject dbObject) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getIndexSet([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]dbObject.get([CtTypeAccessImpl]StreamImpl.FIELD_INDEX_SET_ID))));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private [CtTypeReferenceImpl]org.graylog2.indexer.IndexSet getIndexSet([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isNullOrEmpty([CtVariableReadImpl]id)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.graylog2.indexer.indexset.IndexSetConfig> indexSetConfig = [CtInvocationImpl][CtFieldReadImpl]indexSetService.get([CtVariableReadImpl]id);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]indexSetConfig.flatMap([CtLambdaImpl]([CtParameterImpl] c) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]indexSetFactory.create([CtVariableReadImpl]c))).orElse([CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream load([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id) throws [CtTypeReferenceImpl]org.graylog2.database.NotFoundException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.mongodb.DBObject o = [CtInvocationImpl]get([CtFieldReadImpl]org.graylog2.streams.StreamImpl.class, [CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.graylog2.database.NotFoundException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Stream <" + [CtVariableReadImpl]id) + [CtLiteralImpl]"> not found!");
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.StreamRule> streamRules = [CtInvocationImpl][CtFieldReadImpl]streamRuleService.loadForStreamId([CtInvocationImpl][CtVariableReadImpl]id.toHexString());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Output> outputs = [CtInvocationImpl]loadOutputsForRawStream([CtVariableReadImpl]o);
        [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> fields = [CtInvocationImpl][CtVariableReadImpl]o.toMap();
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.graylog2.streams.StreamImpl([CtInvocationImpl](([CtTypeReferenceImpl]org.bson.types.ObjectId) ([CtVariableReadImpl]o.get([CtLiteralImpl]"_id"))), [CtVariableReadImpl]fields, [CtVariableReadImpl]streamRules, [CtVariableReadImpl]outputs, [CtInvocationImpl]getIndexSet([CtVariableReadImpl]o));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream create([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> fields) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.graylog2.streams.StreamImpl([CtVariableReadImpl]fields, [CtInvocationImpl]getIndexSet([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]fields.get([CtTypeAccessImpl]StreamImpl.FIELD_INDEX_SET_ID)))));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream create([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.rest.resources.streams.requests.CreateStreamRequest cr, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> streamData = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Maps.newHashMap();
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_TITLE, [CtInvocationImpl][CtVariableReadImpl]cr.title());
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_DESCRIPTION, [CtInvocationImpl][CtVariableReadImpl]cr.description());
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_CREATOR_USER_ID, [CtVariableReadImpl]userId);
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_CREATED_AT, [CtInvocationImpl][CtTypeAccessImpl]org.graylog2.plugin.Tools.nowUTC());
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_CONTENT_PACK, [CtInvocationImpl][CtVariableReadImpl]cr.contentPack());
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_MATCHING_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cr.matchingType().toString());
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_DISABLED, [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_REMOVE_MATCHES_FROM_DEFAULT_STREAM, [CtInvocationImpl][CtVariableReadImpl]cr.removeMatchesFromDefaultStream());
        [CtInvocationImpl][CtVariableReadImpl]streamData.put([CtTypeAccessImpl]StreamImpl.FIELD_INDEX_SET_ID, [CtInvocationImpl][CtVariableReadImpl]cr.indexSetId());
        [CtReturnImpl]return [CtInvocationImpl]create([CtVariableReadImpl]streamData);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream load([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) throws [CtTypeReferenceImpl]org.graylog2.database.NotFoundException [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]load([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.types.ObjectId([CtVariableReadImpl]id));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.graylog2.database.NotFoundException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Stream <" + [CtVariableReadImpl]id) + [CtLiteralImpl]"> not found!");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadAllEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]loadAllEnabled([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadAllEnabled([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> additionalQueryOpts) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]additionalQueryOpts.put([CtTypeAccessImpl]StreamImpl.FIELD_DISABLED, [CtLiteralImpl]false);
        [CtReturnImpl]return [CtInvocationImpl]loadAll([CtVariableReadImpl]additionalQueryOpts);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadAll() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]loadAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadAll([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> additionalQueryOpts) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.mongodb.DBObject query = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtVariableReadImpl]additionalQueryOpts);
        [CtReturnImpl]return [CtInvocationImpl]loadAll([CtVariableReadImpl]query);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadAll([CtParameterImpl][CtTypeReferenceImpl]com.mongodb.DBObject query) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.mongodb.DBObject> results = [CtInvocationImpl]query([CtFieldReadImpl]org.graylog2.streams.StreamImpl.class, [CtVariableReadImpl]query);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> streamIds = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]results.stream().map([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]o.get([CtLiteralImpl]"_id").toString()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.StreamRule>> allStreamRules = [CtInvocationImpl][CtFieldReadImpl]streamRuleService.loadForStreamIds([CtVariableReadImpl]streamIds);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableList.Builder<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> streams = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.builder();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.graylog2.indexer.IndexSet> indexSets = [CtInvocationImpl]indexSetsForStreams([CtVariableReadImpl]results);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> outputIds = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]results.stream().map([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::outputIdsForRawStream).flatMap([CtLambdaImpl]([CtParameterImpl] outputs) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]outputs.stream().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.bson.types.ObjectId::toHexString)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.graylog2.plugin.streams.Output> outputsById = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]outputService.loadByIds([CtVariableReadImpl]outputIds).stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Output::getId, [CtInvocationImpl][CtTypeAccessImpl]java.util.function.Function.identity()));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.mongodb.DBObject o : [CtVariableReadImpl]results) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.bson.types.ObjectId objectId = [CtInvocationImpl](([CtTypeReferenceImpl]org.bson.types.ObjectId) ([CtVariableReadImpl]o.get([CtLiteralImpl]"_id")));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String id = [CtInvocationImpl][CtVariableReadImpl]objectId.toHexString();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.StreamRule> streamRules = [CtInvocationImpl][CtVariableReadImpl]allStreamRules.getOrDefault([CtVariableReadImpl]id, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList());
            [CtInvocationImpl][CtFieldReadImpl]org.graylog2.streams.StreamServiceImpl.LOG.debug([CtLiteralImpl]"Found {} rules for stream <{}>", [CtInvocationImpl][CtVariableReadImpl]streamRules.size(), [CtVariableReadImpl]id);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Output> outputs = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]outputIdsForRawStream([CtVariableReadImpl]o).stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ObjectId::toHexString).map([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]outputsById::get).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
            [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> fields = [CtInvocationImpl][CtVariableReadImpl]o.toMap();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String indexSetId = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]fields.get([CtTypeAccessImpl]StreamImpl.FIELD_INDEX_SET_ID)));
            [CtInvocationImpl][CtVariableReadImpl]streams.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.graylog2.streams.StreamImpl([CtVariableReadImpl]objectId, [CtVariableReadImpl]fields, [CtVariableReadImpl]streamRules, [CtVariableReadImpl]outputs, [CtInvocationImpl][CtVariableReadImpl]indexSets.get([CtVariableReadImpl]indexSetId)));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]streams.build();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.bson.types.ObjectId> outputIdsForRawStream([CtParameterImpl][CtTypeReferenceImpl]com.mongodb.DBObject o) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.bson.types.ObjectId> objectIds = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.bson.types.ObjectId>) ([CtVariableReadImpl]o.get([CtTypeAccessImpl]StreamImpl.FIELD_OUTPUTS)));
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]objectIds == [CtLiteralImpl]null ? [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList() : [CtVariableReadImpl]objectIds;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.graylog2.indexer.IndexSet> indexSetsForStreams([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.mongodb.DBObject> streamsQuery) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> indexSetIds = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]streamsQuery.stream().map([CtLambdaImpl]([CtParameterImpl] stream) -> [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]stream.get([CtVariableReadImpl]StreamImpl.FIELD_INDEX_SET_ID)))).filter([CtLambdaImpl]([CtParameterImpl] s) -> [CtUnaryOperatorImpl]![CtInvocationImpl]isNullOrEmpty([CtVariableReadImpl]s)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]indexSetService.findByIds([CtVariableReadImpl]indexSetIds).stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]IndexSetConfig::id, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]indexSetFactory::create));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadByIds([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> streamIds) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.bson.types.ObjectId> objectIds = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]streamIds.stream().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.bson.types.ObjectId::new).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.mongodb.DBObject query = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.mongodb.QueryBuilder.start([CtLiteralImpl]"_id").in([CtVariableReadImpl]objectIds).get();
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtInvocationImpl]loadAll([CtVariableReadImpl]query));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadAllWithConfiguredAlertConditions() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.mongodb.DBObject query = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.mongodb.QueryBuilder.start().and([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.mongodb.QueryBuilder.start([CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS).exists([CtLiteralImpl]true).get(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.mongodb.QueryBuilder.start([CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS).not().size([CtLiteralImpl]0).get()).get();
        [CtReturnImpl]return [CtInvocationImpl]loadAll([CtVariableReadImpl]query);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Output> loadOutputsForRawStream([CtParameterImpl][CtTypeReferenceImpl]com.mongodb.DBObject stream) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.bson.types.ObjectId> outputIds = [CtInvocationImpl]outputIdsForRawStream([CtVariableReadImpl]stream);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Output> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]outputIds != [CtLiteralImpl]null)[CtBlockImpl]
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bson.types.ObjectId outputId : [CtVariableReadImpl]outputIds)[CtBlockImpl]
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]result.add([CtInvocationImpl][CtFieldReadImpl]outputService.load([CtInvocationImpl][CtVariableReadImpl]outputId.toHexString()));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.graylog2.database.NotFoundException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.graylog2.streams.StreamServiceImpl.LOG.warn([CtLiteralImpl]"Non-existing output <{}> referenced from stream <{}>!", [CtInvocationImpl][CtVariableReadImpl]outputId.toHexString(), [CtInvocationImpl][CtVariableReadImpl]stream.get([CtLiteralImpl]"_id"));
                }


        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long count() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]totalCount([CtFieldReadImpl]org.graylog2.streams.StreamImpl.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void destroy([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream) throws [CtTypeReferenceImpl]org.graylog2.database.NotFoundException [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.StreamRule streamRule : [CtInvocationImpl][CtFieldReadImpl]streamRuleService.loadForStream([CtVariableReadImpl]stream)) [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.destroy([CtVariableReadImpl]streamRule);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String streamId = [CtInvocationImpl][CtVariableReadImpl]stream.getId();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.graylog2.notifications.Notification notification : [CtInvocationImpl][CtFieldReadImpl]notificationService.all()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object rawValue = [CtInvocationImpl][CtVariableReadImpl]notification.getDetail([CtLiteralImpl]"stream_id");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]rawValue != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rawValue.toString().equals([CtVariableReadImpl]streamId)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.graylog2.streams.StreamServiceImpl.LOG.debug([CtLiteralImpl]"Removing notification that references stream: {}", [CtVariableReadImpl]notification);
                [CtInvocationImpl][CtFieldReadImpl]notificationService.destroy([CtVariableReadImpl]notification);
            }
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.destroy([CtVariableReadImpl]stream);
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtVariableReadImpl]streamId));
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamDeletedEvent.create([CtVariableReadImpl]streamId));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void update([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String title, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String description) throws [CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]title != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stream.getFields().put([CtTypeAccessImpl]StreamImpl.FIELD_TITLE, [CtVariableReadImpl]title);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]description != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stream.getFields().put([CtTypeAccessImpl]StreamImpl.FIELD_DESCRIPTION, [CtVariableReadImpl]description);
        }
        [CtInvocationImpl]save([CtVariableReadImpl]stream);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void pause([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream) throws [CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]stream.setDisabled([CtLiteralImpl]true);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String streamId = [CtInvocationImpl]save([CtVariableReadImpl]stream);
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtVariableReadImpl]streamId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void resume([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream) throws [CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]stream.setDisabled([CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String streamId = [CtInvocationImpl]save([CtVariableReadImpl]stream);
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtVariableReadImpl]streamId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.StreamRule> getStreamRules([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream) throws [CtTypeReferenceImpl]org.graylog2.database.NotFoundException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]streamRuleService.loadForStream([CtVariableReadImpl]stream);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.alarms.AlertCondition> getAlertConditions([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.alarms.AlertCondition> conditions = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stream.getFields().containsKey([CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.mongodb.BasicDBObject> alertConditions = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.mongodb.BasicDBObject>) ([CtInvocationImpl][CtVariableReadImpl]stream.getFields().get([CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS)));
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.mongodb.BasicDBObject conditionFields : [CtVariableReadImpl]alertConditions) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]conditions.add([CtInvocationImpl][CtFieldReadImpl]alertService.fromPersisted([CtVariableReadImpl]conditionFields, [CtVariableReadImpl]stream));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.graylog2.streams.StreamServiceImpl.LOG.error([CtLiteralImpl]"Skipping alert condition.", [CtVariableReadImpl]e);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]conditions;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.graylog2.plugin.alarms.AlertCondition getAlertCondition([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String conditionId) throws [CtTypeReferenceImpl]org.graylog2.database.NotFoundException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stream.getFields().containsKey([CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.mongodb.BasicDBObject> alertConditions = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.mongodb.BasicDBObject>) ([CtInvocationImpl][CtVariableReadImpl]stream.getFields().get([CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS)));
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.mongodb.BasicDBObject conditionFields : [CtVariableReadImpl]alertConditions) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conditionFields.get([CtLiteralImpl]"id").equals([CtVariableReadImpl]conditionId)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]alertService.fromPersisted([CtVariableReadImpl]conditionFields, [CtVariableReadImpl]stream);
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.graylog2.streams.StreamServiceImpl.LOG.error([CtLiteralImpl]"Skipping alert condition.", [CtVariableReadImpl]e);
                }
            }
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.graylog2.database.NotFoundException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Alert condition <" + [CtVariableReadImpl]conditionId) + [CtLiteralImpl]"> for stream <") + [CtInvocationImpl][CtVariableReadImpl]stream.getId()) + [CtLiteralImpl]"> not found");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addAlertCondition([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.alarms.AlertCondition condition) throws [CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException [CtBlockImpl]{
        [CtInvocationImpl]embed([CtVariableReadImpl]stream, [CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS, [CtVariableReadImpl](([CtTypeReferenceImpl]org.graylog2.plugin.database.EmbeddedPersistable) (condition)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void updateAlertCondition([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.alarms.AlertCondition condition) throws [CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException [CtBlockImpl]{
        [CtInvocationImpl]removeAlertCondition([CtVariableReadImpl]stream, [CtInvocationImpl][CtVariableReadImpl]condition.getId());
        [CtInvocationImpl]addAlertCondition([CtVariableReadImpl]stream, [CtVariableReadImpl]condition);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void removeAlertCondition([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String conditionId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Before deleting alert condition, resolve all its alerts.
        final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.alerts.Alert> alerts = [CtInvocationImpl][CtFieldReadImpl]alertService.listForStreamIds([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtInvocationImpl][CtVariableReadImpl]stream.getId()), [CtTypeAccessImpl]Alert.AlertState.UNRESOLVED, [CtLiteralImpl]0, [CtLiteralImpl]0);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]alerts.stream().filter([CtLambdaImpl]([CtParameterImpl] alert) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]alert.getConditionId().equals([CtVariableReadImpl]conditionId)).forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]alertService::resolveAlert);
        [CtInvocationImpl]removeEmbedded([CtVariableReadImpl]stream, [CtTypeAccessImpl]StreamImpl.EMBEDDED_ALERT_CONDITIONS, [CtVariableReadImpl]conditionId);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addAlertReceiver([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.alarmcallbacks.AlarmCallbackConfiguration> streamCallbacks = [CtInvocationImpl][CtFieldReadImpl]alarmCallbackConfigurationService.getForStream([CtVariableReadImpl]stream);
        [CtInvocationImpl]updateCallbackConfiguration([CtLiteralImpl]"add", [CtVariableReadImpl]type, [CtVariableReadImpl]name, [CtVariableReadImpl]streamCallbacks);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void removeAlertReceiver([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.alarmcallbacks.AlarmCallbackConfiguration> streamCallbacks = [CtInvocationImpl][CtFieldReadImpl]alarmCallbackConfigurationService.getForStream([CtVariableReadImpl]stream);
        [CtInvocationImpl]updateCallbackConfiguration([CtLiteralImpl]"remove", [CtVariableReadImpl]type, [CtVariableReadImpl]name, [CtVariableReadImpl]streamCallbacks);
    }

    [CtMethodImpl][CtCommentImpl]// I tried to be sorry, really. https://www.youtube.com/watch?v=3KVyRqloGmk
    private [CtTypeReferenceImpl]void updateCallbackConfiguration([CtParameterImpl][CtTypeReferenceImpl]java.lang.String action, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String entity, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.alarmcallbacks.AlarmCallbackConfiguration> streamCallbacks) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean ran = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]false);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]streamCallbacks.stream().filter([CtLambdaImpl]([CtParameterImpl] callback) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]callback.getType().equals([CtInvocationImpl][CtFieldReadImpl]org.graylog2.alarmcallbacks.EmailAlarmCallback.class.getCanonicalName())).forEach([CtLambdaImpl]([CtParameterImpl] callback) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ran.set([CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configuration = [CtInvocationImpl][CtVariableReadImpl]callback.getConfiguration();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key;
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"users".equals([CtVariableReadImpl]type)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]key = [CtVariableReadImpl]EmailAlarmCallback.CK_USER_RECEIVERS;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]key = [CtVariableReadImpl]EmailAlarmCallback.CK_EMAIL_RECEIVERS;
            }
            [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            final [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.String> recipients = [CtInvocationImpl](([CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.String>) ([CtVariableReadImpl]configuration.get([CtVariableReadImpl]key)));
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"add".equals([CtVariableReadImpl]action)) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]recipients.contains([CtVariableReadImpl]entity)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]recipients.add([CtVariableReadImpl]entity);
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]recipients.contains([CtVariableReadImpl]entity)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]recipients.remove([CtVariableReadImpl]entity);
            }
            [CtInvocationImpl][CtVariableReadImpl]configuration.put([CtVariableReadImpl]key, [CtVariableReadImpl]recipients);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.graylog2.alarmcallbacks.AlarmCallbackConfiguration updatedConfig = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.graylog2.alarmcallbacks.AlarmCallbackConfigurationImpl) (callback)).toBuilder().setConfiguration([CtVariableReadImpl]configuration).build();
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]alarmCallbackConfigurationService.save([CtVariableReadImpl]updatedConfig);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new <org.graylog2.streams.e>[CtTypeReferenceImpl]javax.ws.rs.BadRequestException([CtLiteralImpl]"Unable to save alarm callback configuration");
            }
        });
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]ran.get()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.ws.rs.BadRequestException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Unable to " + [CtVariableReadImpl]action) + [CtLiteralImpl]" receiver: Stream has no email alarm callback.");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addOutput([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Output output) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]collection([CtVariableReadImpl]stream).update([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"_id", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.types.ObjectId([CtInvocationImpl][CtVariableReadImpl]stream.getId())), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"$addToSet", [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtFieldReadImpl]StreamImpl.FIELD_OUTPUTS, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.types.ObjectId([CtInvocationImpl][CtVariableReadImpl]output.getId()))));
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtInvocationImpl][CtVariableReadImpl]stream.getId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addOutputs([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId streamId, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.bson.types.ObjectId> outputIds) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.mongodb.BasicDBList outputs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBList();
        [CtInvocationImpl][CtVariableReadImpl]outputs.addAll([CtVariableReadImpl]outputIds);
        [CtInvocationImpl][CtInvocationImpl]collection([CtFieldReadImpl]org.graylog2.streams.StreamImpl.class).update([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"_id", [CtVariableReadImpl]streamId), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"$addToSet", [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtFieldReadImpl]StreamImpl.FIELD_OUTPUTS, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"$each", [CtVariableReadImpl]outputs))));
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtInvocationImpl][CtVariableReadImpl]streamId.toHexString()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void removeOutput([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Output output) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]collection([CtVariableReadImpl]stream).update([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"_id", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.types.ObjectId([CtInvocationImpl][CtVariableReadImpl]stream.getId())), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"$pull", [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtFieldReadImpl]StreamImpl.FIELD_OUTPUTS, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.types.ObjectId([CtInvocationImpl][CtVariableReadImpl]output.getId()))));
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtInvocationImpl][CtVariableReadImpl]stream.getId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void removeOutputFromAllStreams([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Output output) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bson.types.ObjectId outputId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.types.ObjectId([CtInvocationImpl][CtVariableReadImpl]output.getId());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.mongodb.DBObject match = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtFieldReadImpl]StreamImpl.FIELD_OUTPUTS, [CtVariableReadImpl]outputId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.mongodb.DBObject modify = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtLiteralImpl]"$pull", [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtFieldReadImpl]StreamImpl.FIELD_OUTPUTS, [CtVariableReadImpl]outputId));
        [CtLocalVariableImpl][CtCommentImpl]// Collect streams that will change before updating them because we don't get the list of changed streams
        [CtCommentImpl]// from the upsert call.
        final [CtTypeReferenceImpl]com.google.common.collect.ImmutableSet<[CtTypeReferenceImpl]java.lang.String> updatedStreams;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl]final [CtTypeReferenceImpl]com.mongodb.DBCursor cursor = [CtInvocationImpl][CtInvocationImpl]collection([CtFieldReadImpl]org.graylog2.streams.StreamImpl.class).find([CtVariableReadImpl]match)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]updatedStreams = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.stream.StreamSupport.stream([CtInvocationImpl][CtVariableReadImpl]cursor.spliterator(), [CtLiteralImpl]false).map([CtLambdaImpl]([CtParameterImpl] stream) -> [CtInvocationImpl][CtVariableReadImpl]stream.get([CtLiteralImpl]"_id")).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).map([CtLambdaImpl]([CtParameterImpl] id) -> [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bson.types.ObjectId) (id)).toHexString()).collect([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.toImmutableSet());
        }
        [CtInvocationImpl][CtInvocationImpl]collection([CtFieldReadImpl]org.graylog2.streams.StreamImpl.class).update([CtVariableReadImpl]match, [CtVariableReadImpl]modify, [CtLiteralImpl]false, [CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtVariableReadImpl]updatedStreams));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream> loadAllWithIndexSet([CtParameterImpl][CtTypeReferenceImpl]java.lang.String indexSetId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> query = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.mongodb.BasicDBObject([CtFieldReadImpl]StreamImpl.FIELD_INDEX_SET_ID, [CtVariableReadImpl]indexSetId);
        [CtReturnImpl]return [CtInvocationImpl]loadAll([CtVariableReadImpl]query);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String save([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream) throws [CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String savedStreamId = [CtInvocationImpl][CtSuperAccessImpl]super.save([CtVariableReadImpl]stream);
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtVariableReadImpl]savedStreamId));
        [CtReturnImpl]return [CtVariableReadImpl]savedStreamId;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String saveWithRules([CtParameterImpl][CtTypeReferenceImpl]org.graylog2.plugin.streams.Stream stream, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.graylog2.plugin.streams.StreamRule> streamRules) throws [CtTypeReferenceImpl]org.graylog2.plugin.database.ValidationException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String savedStreamId = [CtInvocationImpl][CtSuperAccessImpl]super.save([CtVariableReadImpl]stream);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.graylog2.plugin.streams.StreamRule> rules = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]streamRules.stream().map([CtLambdaImpl]([CtParameterImpl] rule) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]streamRuleService.copy([CtVariableReadImpl]savedStreamId, [CtVariableReadImpl]rule)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl][CtFieldReadImpl]streamRuleService.save([CtVariableReadImpl]rules);
        [CtInvocationImpl][CtFieldReadImpl]clusterEventBus.post([CtInvocationImpl][CtTypeAccessImpl]org.graylog2.streams.events.StreamsChangedEvent.create([CtVariableReadImpl]savedStreamId));
        [CtReturnImpl]return [CtVariableReadImpl]savedStreamId;
    }
}
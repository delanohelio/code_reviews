[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2020, RTE (http://www.rte-france.com)

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
[CtPackageDeclarationImpl]package org.lfenergy.operatorfabric.users.controllers;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.springtools.error.model.ApiError;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.users.model.EntityData;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.users.model.Entity;
[CtUnresolvedImport]import javax.servlet.http.HttpServletResponse;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.RequestMapping;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.users.repositories.EntityRepository;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.RestController;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.springtools.configuration.oauth.UpdatedUserEvent;
[CtUnresolvedImport]import org.springframework.http.HttpStatus;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtUnresolvedImport]import org.springframework.cloud.bus.ServiceMatcher;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.users.repositories.UserRepository;
[CtUnresolvedImport]import org.springframework.context.ApplicationEventPublisher;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.users.model.UserData;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.springtools.error.model.ApiErrorException;
[CtClassImpl][CtJavaDocImpl]/**
 * GroupsController, documented at {@link EntitiesApi}
 */
[CtAnnotationImpl]@org.springframework.web.bind.annotation.RestController
[CtAnnotationImpl]@org.springframework.web.bind.annotation.RequestMapping([CtLiteralImpl]"/entities")
public class EntitiesController implements [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.controllers.EntitiesApi {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ENTITY_NOT_FOUND_MSG = [CtLiteralImpl]"Entity %s not found";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String USER_NOT_FOUND_MSG = [CtLiteralImpl]"User %s not found";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String BAD_USER_LIST_MSG = [CtLiteralImpl]"Bad user list : user %s not found";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String NO_MATCHING_ENTITY_ID_MSG = [CtLiteralImpl]"Payload Entity id does not match URL Entity id";

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.repositories.EntityRepository entityRepository;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.repositories.UserRepository userRepository;

    [CtFieldImpl][CtCommentImpl]/* These are Spring Cloud Bus beans used to fire an event (UpdatedUserEvent) every time a user is modified.
     Other services handle this event by clearing their user cache for the given user. See issue #64
     */
    [CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.springframework.cloud.bus.ServiceMatcher busServiceMatcher;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.springframework.context.ApplicationEventPublisher publisher;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Void addEntityUsers([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> users) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Only existing entities can be updated
        findEntityOrThrow([CtVariableReadImpl]id);
        [CtLocalVariableImpl][CtCommentImpl]// Retrieve users from repository for users list, throwing an error if a login is not found
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData> foundUsers = [CtInvocationImpl]retrieveUsers([CtVariableReadImpl]users);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData userData : [CtVariableReadImpl]foundUsers) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]userData.addEntity([CtVariableReadImpl]id);
            [CtInvocationImpl][CtFieldReadImpl]publisher.publishEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.configuration.oauth.UpdatedUserEvent([CtThisAccessImpl]this, [CtInvocationImpl][CtFieldReadImpl]busServiceMatcher.getServiceId(), [CtInvocationImpl][CtVariableReadImpl]userData.getLogin()));
        }
        [CtInvocationImpl][CtFieldReadImpl]userRepository.saveAll([CtVariableReadImpl]foundUsers);
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.Entity createEntity([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.Entity entity) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]entity.getId()).orElse([CtLiteralImpl]null) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]response.addHeader([CtLiteralImpl]"Location", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]request.getContextPath() + [CtLiteralImpl]"/entities/") + [CtInvocationImpl][CtVariableReadImpl]entity.getId());
            [CtInvocationImpl][CtVariableReadImpl]response.setStatus([CtLiteralImpl]201);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]entityRepository.save([CtVariableReadImpl](([CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.EntityData) (entity)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Void deleteEntityUsers([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Only existing entities can be updated
        findEntityOrThrow([CtVariableReadImpl]id);
        [CtLocalVariableImpl][CtCommentImpl]// Retrieve users from repository for users list, throwing an error if a login is not found
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData> foundUsers = [CtInvocationImpl][CtFieldReadImpl]userRepository.findByEntitiesContaining([CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]foundUsers != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData userData : [CtVariableReadImpl]foundUsers) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]userData.deleteEntity([CtVariableReadImpl]id);
                [CtInvocationImpl][CtFieldReadImpl]publisher.publishEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.configuration.oauth.UpdatedUserEvent([CtThisAccessImpl]this, [CtInvocationImpl][CtFieldReadImpl]busServiceMatcher.getServiceId(), [CtInvocationImpl][CtVariableReadImpl]userData.getLogin()));
            }
            [CtInvocationImpl][CtFieldReadImpl]userRepository.saveAll([CtVariableReadImpl]foundUsers);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Void deleteEntityUser([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String login) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Only existing entities can be updated
        findEntityOrThrow([CtVariableReadImpl]id);
        [CtLocalVariableImpl][CtCommentImpl]// Retrieve users from repository for users list, throwing an error if a login is not found
        [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData foundUser = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userRepository.findById([CtVariableReadImpl]login).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiErrorException([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiError.builder().status([CtVariableReadImpl]HttpStatus.NOT_FOUND).message([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl][CtFieldReferenceImpl]USER_NOT_FOUND_MSG, [CtVariableReadImpl]login)).build()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]foundUser != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]foundUser.deleteEntity([CtVariableReadImpl]id);
            [CtInvocationImpl][CtFieldReadImpl]publisher.publishEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.configuration.oauth.UpdatedUserEvent([CtThisAccessImpl]this, [CtInvocationImpl][CtFieldReadImpl]busServiceMatcher.getServiceId(), [CtInvocationImpl][CtVariableReadImpl]foundUser.getLogin()));
            [CtInvocationImpl][CtFieldReadImpl]userRepository.save([CtVariableReadImpl]foundUser);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.Entity> fetchEntities([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]entityRepository.findAll();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.Entity fetchEntity([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityRepository.findById([CtVariableReadImpl]id).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiErrorException([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiError.builder().status([CtVariableReadImpl]HttpStatus.NOT_FOUND).message([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl][CtFieldReferenceImpl]ENTITY_NOT_FOUND_MSG, [CtVariableReadImpl]id)).build()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.Entity updateEntity([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.Entity entity) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// id from entity body parameter should match id path parameter
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getId().equals([CtVariableReadImpl]id)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiErrorException([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiError.builder().status([CtTypeAccessImpl]HttpStatus.BAD_REQUEST).message([CtFieldReadImpl]org.lfenergy.operatorfabric.users.controllers.EntitiesController.NO_MATCHING_ENTITY_ID_MSG).build());
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]createEntity([CtVariableReadImpl]request, [CtVariableReadImpl]response, [CtVariableReadImpl]entity);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Void updateEntityUsers([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> users) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Only existing entities can be updated
        findEntityOrThrow([CtVariableReadImpl]id);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData> formerlyBelongs = [CtInvocationImpl][CtFieldReadImpl]userRepository.findByEntitiesContaining([CtVariableReadImpl]id);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> newUsersInEntity = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]users);
        [CtInvocationImpl][CtCommentImpl]// Make sure the intended updated users list only contains logins existing in the repository, throwing an error if this is not the case
        retrieveUsers([CtVariableReadImpl]users);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData> toUpdate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]formerlyBelongs.stream().filter([CtLambdaImpl]([CtParameterImpl] u) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]users.contains([CtInvocationImpl][CtVariableReadImpl]u.getLogin())).forEach([CtLambdaImpl]([CtParameterImpl] u) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]u.deleteEntity([CtVariableReadImpl]id);
            [CtInvocationImpl][CtVariableReadImpl]newUsersInEntity.remove([CtInvocationImpl][CtVariableReadImpl]u.getLogin());
            [CtInvocationImpl][CtVariableReadImpl]toUpdate.add([CtVariableReadImpl]u);
        });
        [CtForEachImpl][CtCommentImpl]// Fire an UpdatedUserEvent for all users that are updated because they're removed from the entity
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData userData : [CtVariableReadImpl]toUpdate) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]userData.addEntity([CtVariableReadImpl]id);
            [CtInvocationImpl][CtFieldReadImpl]publisher.publishEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.configuration.oauth.UpdatedUserEvent([CtThisAccessImpl]this, [CtInvocationImpl][CtFieldReadImpl]busServiceMatcher.getServiceId(), [CtInvocationImpl][CtVariableReadImpl]userData.getLogin()));
        }
        [CtInvocationImpl][CtFieldReadImpl]userRepository.saveAll([CtVariableReadImpl]toUpdate);
        [CtInvocationImpl]addEntityUsers([CtVariableReadImpl]request, [CtVariableReadImpl]response, [CtVariableReadImpl]id, [CtVariableReadImpl]newUsersInEntity);[CtCommentImpl]// For users that are added to the entity, the event will be published by addEntityUsers.

        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.EntityData findEntityOrThrow([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityRepository.findById([CtVariableReadImpl]id).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiErrorException([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiError.builder().status([CtVariableReadImpl]HttpStatus.NOT_FOUND).message([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl][CtFieldReferenceImpl]ENTITY_NOT_FOUND_MSG, [CtVariableReadImpl]id)).build()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieve users from repository for logins list, throwing an error if a login is not found
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData> retrieveUsers([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> logins) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData> foundUsers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String login : [CtVariableReadImpl]logins) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.lfenergy.operatorfabric.users.model.UserData foundUser = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userRepository.findById([CtVariableReadImpl]login).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiErrorException([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.lfenergy.operatorfabric.springtools.error.model.ApiError.builder().status([CtVariableReadImpl]HttpStatus.BAD_REQUEST).message([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl][CtFieldReferenceImpl]BAD_USER_LIST_MSG, [CtVariableReadImpl]login)).build()));
            [CtInvocationImpl][CtVariableReadImpl]foundUsers.add([CtVariableReadImpl]foundUser);
        }
        [CtReturnImpl]return [CtVariableReadImpl]foundUsers;
    }
}
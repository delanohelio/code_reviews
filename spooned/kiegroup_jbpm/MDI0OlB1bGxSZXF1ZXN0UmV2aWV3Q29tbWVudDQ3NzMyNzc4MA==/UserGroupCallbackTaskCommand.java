[CompilationUnitImpl][CtCommentImpl]/* Copyright 2017 Red Hat, Inc. and/or its affiliates.

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
[CtPackageDeclarationImpl]package org.jbpm.services.task.commands;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.jbpm.services.task.exception.CannotAddTaskException;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.Properties;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.kie.api.task.model.OrganizationalEntity;
[CtUnresolvedImport]import org.kie.internal.task.api.model.InternalTaskData;
[CtUnresolvedImport]import org.kie.internal.task.api.TaskContext;
[CtUnresolvedImport]import org.kie.internal.task.api.model.InternalPeopleAssignments;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.kie.api.task.model.User;
[CtUnresolvedImport]import org.kie.internal.task.api.model.Deadlines;
[CtUnresolvedImport]import org.kie.internal.task.api.model.Notification;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlAccessorType;
[CtUnresolvedImport]import org.kie.internal.task.api.model.Escalation;
[CtUnresolvedImport]import org.kie.internal.task.api.model.InternalOrganizationalEntity;
[CtUnresolvedImport]import org.kie.internal.task.api.model.InternalComment;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import org.kie.api.task.model.Attachment;
[CtUnresolvedImport]import org.kie.internal.task.api.TaskModelProvider;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlTransient;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlRootElement;
[CtUnresolvedImport]import javax.xml.bind.annotation.XmlAccessType;
[CtUnresolvedImport]import org.kie.api.task.model.Group;
[CtUnresolvedImport]import org.kie.internal.task.api.model.InternalAttachment;
[CtUnresolvedImport]import org.kie.api.runtime.Context;
[CtUnresolvedImport]import org.kie.api.task.model.Email;
[CtUnresolvedImport]import org.kie.api.task.model.Comment;
[CtUnresolvedImport]import org.kie.internal.task.api.model.Reassignment;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.kie.api.task.model.Status;
[CtUnresolvedImport]import org.kie.internal.task.api.TaskPersistenceContext;
[CtUnresolvedImport]import org.kie.internal.task.api.model.Deadline;
[CtUnresolvedImport]import org.drools.core.util.StringUtils;
[CtClassImpl][CtAnnotationImpl]@javax.xml.bind.annotation.XmlTransient
[CtAnnotationImpl]@javax.xml.bind.annotation.XmlRootElement(name = [CtLiteralImpl]"user-group-callback-task-command")
[CtAnnotationImpl]@javax.xml.bind.annotation.XmlAccessorType([CtFieldReadImpl]javax.xml.bind.annotation.XmlAccessType.NONE)
public class UserGroupCallbackTaskCommand<[CtTypeParameterImpl]T> extends [CtTypeReferenceImpl]org.jbpm.services.task.commands.TaskCommand<[CtTypeParameterReferenceImpl]T> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]2675686383800457244L;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.jbpm.services.task.commands.UserGroupCallbackTaskCommand.class);

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Boolean> userGroupsMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Boolean>();

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> restrictedGroups = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String>();

    [CtConstructorImpl]public UserGroupCallbackTaskCommand() [CtBlockImpl]{
    }

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream in = [CtInvocationImpl][CtFieldReadImpl]org.jbpm.services.task.commands.UserGroupCallbackTaskCommand.class.getResourceAsStream([CtLiteralImpl]"/restricted-groups.properties");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]in != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
                [CtInvocationImpl][CtVariableReadImpl]props.load([CtVariableReadImpl]in);
                [CtInvocationImpl][CtFieldReadImpl]org.jbpm.services.task.commands.UserGroupCallbackTaskCommand.restrictedGroups.addAll([CtInvocationImpl][CtVariableReadImpl]props.stringPropertyNames());
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.jbpm.services.task.commands.UserGroupCallbackTaskCommand.logger.warn([CtLiteralImpl]"Error when loading restricted groups for human task service {}", [CtInvocationImpl][CtVariableReadImpl]e.getMessage());
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> doUserGroupCallbackOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> groupIds, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]groupIds = [CtInvocationImpl]doCallbackGroupsOperation([CtVariableReadImpl]userId, [CtVariableReadImpl]groupIds, [CtVariableReadImpl]context);
        [CtReturnImpl]return [CtInvocationImpl]filterGroups([CtVariableReadImpl]groupIds);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean doCallbackUserOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]doCallbackUserOperation([CtVariableReadImpl]userId, [CtVariableReadImpl]context, [CtLiteralImpl]false);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean doCallbackEmailOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String emailId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]emailId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]addEmailFromCallbackOperation([CtVariableReadImpl]emailId, [CtVariableReadImpl]context);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean doCallbackUserOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context, [CtParameterImpl][CtTypeReferenceImpl]boolean throwExceptionWhenNotFound) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]userId != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback().existsUser([CtVariableReadImpl]userId)) [CtBlockImpl]{
            [CtInvocationImpl]addUserFromCallbackOperation([CtVariableReadImpl]userId, [CtVariableReadImpl]context);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtVariableReadImpl]throwExceptionWhenNotFound) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"User " + [CtVariableReadImpl]userId) + [CtLiteralImpl]" was not found in callback ") + [CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback());
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.kie.api.task.model.User doCallbackAndReturnUserOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]userId != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback().existsUser([CtVariableReadImpl]userId)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]addUserFromCallbackOperation([CtVariableReadImpl]userId, [CtVariableReadImpl]context);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean doCallbackGroupOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String groupId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]groupId != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback().existsGroup([CtVariableReadImpl]groupId)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]org.jbpm.services.task.commands.UserGroupCallbackTaskCommand.restrictedGroups.contains([CtVariableReadImpl]groupId))) [CtBlockImpl]{
            [CtInvocationImpl]addGroupFromCallbackOperation([CtVariableReadImpl]groupId, [CtVariableReadImpl]context);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.kie.api.task.model.User addUserFromCallbackOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.User user = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getPersistenceContext().findUser([CtVariableReadImpl]userId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtBinaryOperatorImpl][CtVariableReadImpl]user != [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]org.drools.core.util.StringUtils.isEmpty([CtVariableReadImpl]userId)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]userExists)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]user = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kie.internal.task.api.TaskModelProvider.getFactory().newUser();
            [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.kie.internal.task.api.model.InternalOrganizationalEntity) (user)).setId([CtVariableReadImpl]userId);
            [CtInvocationImpl]persistIfNotExists([CtVariableReadImpl]user, [CtVariableReadImpl]context);
        }
        [CtReturnImpl]return [CtVariableReadImpl]user;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void addEmailFromCallbackOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String emailId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.Email email = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getPersistenceContext().findEmail([CtVariableReadImpl]emailId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean emailExists = [CtBinaryOperatorImpl][CtVariableReadImpl]email != [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]org.drools.core.util.StringUtils.isEmpty([CtVariableReadImpl]emailId)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]emailExists)) [CtBlockImpl]{
            [CtInvocationImpl]persistIfNotExists([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kie.internal.task.api.TaskModelProvider.getFactory().newEmail([CtVariableReadImpl]emailId), [CtVariableReadImpl]context);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void persistIfNotExists([CtParameterImpl]final [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity entity, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskPersistenceContext tpc = [CtInvocationImpl][CtVariableReadImpl]context.getPersistenceContext();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity orgEntity = [CtInvocationImpl][CtVariableReadImpl]tpc.findOrgEntity([CtInvocationImpl][CtVariableReadImpl]entity.getId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]orgEntity == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]orgEntity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) && [CtBinaryOperatorImpl]([CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]orgEntity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) && [CtBinaryOperatorImpl]([CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]orgEntity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Email) && [CtBinaryOperatorImpl]([CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Email))) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]tpc.persistOrgEntity([CtVariableReadImpl]entity);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> doCallbackGroupsOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> groupIds, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]groupIds != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]groupIds.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> userGroups = [CtInvocationImpl]filterGroups([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback().getGroupsForUser([CtVariableReadImpl]userId));
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String groupId : [CtVariableReadImpl]groupIds) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback().existsGroup([CtVariableReadImpl]groupId) && [CtBinaryOperatorImpl]([CtVariableReadImpl]userGroups != [CtLiteralImpl]null)) && [CtInvocationImpl][CtVariableReadImpl]userGroups.contains([CtVariableReadImpl]groupId)) [CtBlockImpl]{
                        [CtInvocationImpl]addGroupFromCallbackOperation([CtVariableReadImpl]groupId, [CtVariableReadImpl]context);
                    }
                }
            } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]userGroupsMap.containsKey([CtVariableReadImpl]userId) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userGroupsMap.get([CtVariableReadImpl]userId).booleanValue())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> userGroups = [CtInvocationImpl]filterGroups([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback().getGroupsForUser([CtVariableReadImpl]userId));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]userGroups != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]userGroups.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String group : [CtVariableReadImpl]userGroups) [CtBlockImpl]{
                        [CtInvocationImpl]addGroupFromCallbackOperation([CtVariableReadImpl]group, [CtVariableReadImpl]context);
                    }
                    [CtInvocationImpl][CtFieldReadImpl]userGroupsMap.put([CtVariableReadImpl]userId, [CtLiteralImpl]true);
                    [CtAssignmentImpl][CtVariableWriteImpl]groupIds = [CtVariableReadImpl]userGroups;
                }
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]groupIds != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String groupId : [CtVariableReadImpl]groupIds) [CtBlockImpl]{
                [CtInvocationImpl]addGroupFromCallbackOperation([CtVariableReadImpl]groupId, [CtVariableReadImpl]context);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]groupIds;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void addGroupFromCallbackOperation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String groupId, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group group = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getPersistenceContext().findGroup([CtVariableReadImpl]groupId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean groupExists = [CtBinaryOperatorImpl][CtVariableReadImpl]group != [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]org.drools.core.util.StringUtils.isEmpty([CtVariableReadImpl]groupId)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]groupExists)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]group = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kie.internal.task.api.TaskModelProvider.getFactory().newGroup();
            [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.kie.internal.task.api.model.InternalOrganizationalEntity) (group)).setId([CtVariableReadImpl]groupId);
            [CtInvocationImpl]persistIfNotExists([CtVariableReadImpl]group, [CtVariableReadImpl]context);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void doCallbackOperationForTaskData([CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.model.InternalTaskData data, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]data.getActualOwner() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]data.getActualOwner().getId(), [CtVariableReadImpl]context);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// remove it from the task to avoid foreign key constraint exception
                [CtVariableReadImpl]data.setActualOwner([CtLiteralImpl]null);
                [CtInvocationImpl][CtVariableReadImpl]data.setStatus([CtTypeAccessImpl]Status.Ready);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]data.getCreatedBy() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]data.getCreatedBy().getId(), [CtVariableReadImpl]context);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// remove it from the task to avoid foreign key constraint exception
                [CtVariableReadImpl]data.setCreatedBy([CtLiteralImpl]null);
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void doCallbackOperationForPotentialOwners([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> potentialOwners, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> nonExistingEntities = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity orgEntity : [CtVariableReadImpl]potentialOwners) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]orgEntity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]orgEntity.getId(), [CtVariableReadImpl]context);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]orgEntity);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]orgEntity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean groupExists = [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]orgEntity.getId(), [CtVariableReadImpl]context);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]groupExists) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]orgEntity);
                }
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]potentialOwners.removeAll([CtVariableReadImpl]nonExistingEntities);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void doCallbackOperationForPeopleAssignments([CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.model.InternalPeopleAssignments assignments, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> nonExistingEntities = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]assignments != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> businessAdmins = [CtInvocationImpl][CtVariableReadImpl]assignments.getBusinessAdministrators();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]businessAdmins != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity admin : [CtVariableReadImpl]businessAdmins) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]admin instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]admin.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]admin);
                        }
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]admin instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean groupExists = [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]admin.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]groupExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]admin);
                        }
                    }
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]businessAdmins.removeAll([CtVariableReadImpl]nonExistingEntities);
                    [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.clear();
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]businessAdmins == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]businessAdmins.isEmpty()) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// throw an exception as it should not be allowed to create task without administrator
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jbpm.services.task.exception.CannotAddTaskException([CtLiteralImpl]"There are no known Business Administrators, task cannot be created according to WS-HT specification");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> potentialOwners = [CtInvocationImpl][CtVariableReadImpl]assignments.getPotentialOwners();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]potentialOwners != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity powner : [CtVariableReadImpl]potentialOwners) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]powner instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]powner.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]powner);
                        }
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]powner instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean groupExists = [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]powner.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]groupExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]powner);
                        }
                    }
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]potentialOwners.removeAll([CtVariableReadImpl]nonExistingEntities);
                    [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.clear();
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]assignments.getTaskInitiator() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]assignments.getTaskInitiator().getId() != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]assignments.getTaskInitiator().getId(), [CtVariableReadImpl]context);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> excludedOwners = [CtInvocationImpl][CtVariableReadImpl]assignments.getExcludedOwners();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]excludedOwners != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity exowner : [CtVariableReadImpl]excludedOwners) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exowner instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]exowner.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]exowner);
                        }
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exowner instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean groupExists = [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]exowner.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]groupExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]exowner);
                        }
                    }
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]excludedOwners.removeAll([CtVariableReadImpl]nonExistingEntities);
                    [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.clear();
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> recipients = [CtInvocationImpl][CtVariableReadImpl]assignments.getRecipients();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]recipients != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity recipient : [CtVariableReadImpl]recipients) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]recipient instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]recipient.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]recipient);
                        }
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]recipient instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean groupExists = [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]recipient.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]groupExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]recipient);
                        }
                    }
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]recipients.removeAll([CtVariableReadImpl]nonExistingEntities);
                    [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.clear();
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> stakeholders = [CtInvocationImpl][CtVariableReadImpl]assignments.getTaskStakeholders();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stakeholders != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity stakeholder : [CtVariableReadImpl]stakeholders) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stakeholder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean userExists = [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]stakeholder.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]userExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]stakeholder);
                        }
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stakeholder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean groupExists = [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]stakeholder.getId(), [CtVariableReadImpl]context);
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]groupExists) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.add([CtVariableReadImpl]stakeholder);
                        }
                    }
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]stakeholders.removeAll([CtVariableReadImpl]nonExistingEntities);
                    [CtInvocationImpl][CtVariableReadImpl]nonExistingEntities.clear();
                }
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void doCallbackOperationForTaskDeadlines([CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.model.Deadlines deadlines, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deadlines != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]deadlines.getStartDeadlines() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.internal.task.api.model.Deadline> startDeadlines = [CtInvocationImpl][CtVariableReadImpl]deadlines.getStartDeadlines();
                [CtInvocationImpl]doCallbackDeadlines([CtVariableReadImpl]startDeadlines, [CtVariableReadImpl]context);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]deadlines.getEndDeadlines() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.internal.task.api.model.Deadline> endDeadlines = [CtInvocationImpl][CtVariableReadImpl]deadlines.getEndDeadlines();
                [CtInvocationImpl]doCallbackDeadlines([CtVariableReadImpl]endDeadlines, [CtVariableReadImpl]context);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doCallbackDeadlines([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.internal.task.api.model.Deadline> deadlines, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.internal.task.api.model.Deadline endDeadline : [CtVariableReadImpl]deadlines) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.internal.task.api.model.Escalation> escalations = [CtInvocationImpl][CtVariableReadImpl]endDeadline.getEscalations();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]escalations != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.internal.task.api.model.Escalation escalation : [CtVariableReadImpl]escalations) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.internal.task.api.model.Notification> notifications = [CtInvocationImpl][CtVariableReadImpl]escalation.getNotifications();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]notifications != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.internal.task.api.model.Notification notification : [CtVariableReadImpl]notifications) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> recipients = [CtInvocationImpl][CtVariableReadImpl]notification.getRecipients();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]recipients != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity recipient : [CtVariableReadImpl]recipients) [CtBlockImpl]{
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]recipient instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                                        [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]recipient.getId(), [CtVariableReadImpl]context);
                                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]recipient instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                                        [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]recipient.getId(), [CtVariableReadImpl]context);
                                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]recipient instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Email) [CtBlockImpl]{
                                        [CtInvocationImpl]doCallbackEmailOperation([CtInvocationImpl][CtVariableReadImpl]recipient.getId(), [CtVariableReadImpl]context);
                                    }
                                }
                            }
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> administrators = [CtInvocationImpl][CtVariableReadImpl]notification.getBusinessAdministrators();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]administrators != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity administrator : [CtVariableReadImpl]administrators) [CtBlockImpl]{
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]administrator instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                                        [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]administrator.getId(), [CtVariableReadImpl]context);
                                    }
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]administrator instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                                        [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]administrator.getId(), [CtVariableReadImpl]context);
                                    }
                                }
                            }
                        }
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.internal.task.api.model.Reassignment> ressignments = [CtInvocationImpl][CtVariableReadImpl]escalation.getReassignments();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ressignments != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.internal.task.api.model.Reassignment reassignment : [CtVariableReadImpl]ressignments) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> potentialOwners = [CtInvocationImpl][CtVariableReadImpl]reassignment.getPotentialOwners();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]potentialOwners != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity potentialOwner : [CtVariableReadImpl]potentialOwners) [CtBlockImpl]{
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]potentialOwner instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.User) [CtBlockImpl]{
                                        [CtInvocationImpl]doCallbackUserOperation([CtInvocationImpl][CtVariableReadImpl]potentialOwner.getId(), [CtVariableReadImpl]context, [CtLiteralImpl]true);
                                    }
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]potentialOwner instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.kie.api.task.model.Group) [CtBlockImpl]{
                                        [CtInvocationImpl]doCallbackGroupOperation([CtInvocationImpl][CtVariableReadImpl]potentialOwner.getId(), [CtVariableReadImpl]context);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void doCallbackOperationForComment([CtParameterImpl][CtTypeReferenceImpl]org.kie.api.task.model.Comment comment, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]comment != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]comment.getAddedBy() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.User entity = [CtInvocationImpl]doCallbackAndReturnUserOperation([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]comment.getAddedBy().getId(), [CtVariableReadImpl]context);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.kie.internal.task.api.model.InternalComment) (comment)).setAddedBy([CtVariableReadImpl]entity);
                }
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void doCallbackOperationForAttachment([CtParameterImpl][CtTypeReferenceImpl]org.kie.api.task.model.Attachment attachment, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]attachment != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]attachment.getAttachedBy() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.api.task.model.User entity = [CtInvocationImpl]doCallbackAndReturnUserOperation([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attachment.getAttachedBy().getId(), [CtVariableReadImpl]context);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.kie.internal.task.api.model.InternalAttachment) (attachment)).setAttachedBy([CtVariableReadImpl]entity);
                }
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> filterGroups([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> groups) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]groups != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]groups.removeAll([CtFieldReadImpl]org.jbpm.services.task.commands.UserGroupCallbackTaskCommand.restrictedGroups);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]groups = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        }
        [CtReturnImpl]return [CtVariableReadImpl]groups;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean isBusinessAdmin([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kie.api.task.model.OrganizationalEntity> businessAdmins, [CtParameterImpl][CtTypeReferenceImpl]org.kie.internal.task.api.TaskContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> usersGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getUserGroupCallback().getGroupsForUser([CtVariableReadImpl]userId));
        [CtInvocationImpl][CtVariableReadImpl]usersGroup.add([CtVariableReadImpl]userId);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]businessAdmins.stream().anyMatch([CtLambdaImpl]([CtParameterImpl] oe) -> [CtInvocationImpl][CtVariableReadImpl]usersGroup.contains([CtInvocationImpl][CtVariableReadImpl]oe.getId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T execute([CtParameterImpl][CtTypeReferenceImpl]org.kie.api.runtime.Context context) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtLiteralImpl]"org.jbpm.services.task.commands.UserGroupCallbackTaskCommand.execute -> TODO");
    }
}
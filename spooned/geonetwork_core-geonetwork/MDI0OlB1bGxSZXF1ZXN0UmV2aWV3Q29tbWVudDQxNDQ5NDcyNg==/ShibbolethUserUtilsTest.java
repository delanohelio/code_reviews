[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2001-2016 Food and Agriculture Organization of the
United Nations (FAO-UN), United Nations World Food Programme (WFP)
and United Nations Environment Programme (UNEP)

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA

Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
Rome - Italy. email: geonetwork@osgeo.org
 */
[CtPackageDeclarationImpl]package org.fao.geonet.kernel.security.shibboleth;
[CtUnresolvedImport]import static org.junit.Assert.assertNotSame;
[CtUnresolvedImport]import static org.junit.Assert.assertNotNull;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.springframework.mock.web.MockHttpServletRequest;
[CtUnresolvedImport]import org.fao.geonet.AbstractCoreIntegrationTest;
[CtUnresolvedImport]import static org.junit.Assert.assertSame;
[CtUnresolvedImport]import org.fao.geonet.repository.GroupRepository;
[CtUnresolvedImport]import static org.junit.Assert.assertNull;
[CtUnresolvedImport]import org.junit.After;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import org.fao.geonet.repository.UserRepository;
[CtUnresolvedImport]import org.fao.geonet.domain.User;
[CtUnresolvedImport]import org.fao.geonet.repository.UserGroupRepository;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.fao.geonet.domain.UserGroup;
[CtUnresolvedImport]import org.fao.geonet.repository.specification.UserGroupSpecs;
[CtUnresolvedImport]import org.fao.geonet.domain.Group;
[CtUnresolvedImport]import org.fao.geonet.domain.Profile;
[CtUnresolvedImport]import org.junit.Before;
[CtClassImpl]public class ShibbolethUserUtilsTest extends [CtTypeReferenceImpl]org.fao.geonet.AbstractCoreIntegrationTest {
    [CtFieldImpl]private [CtTypeReferenceImpl]org.fao.geonet.kernel.security.shibboleth.ShibbolethUserUtils utils;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.fao.geonet.kernel.security.shibboleth.ShibbolethUserConfiguration config;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.fao.geonet.repository.UserRepository userRepo;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.fao.geonet.repository.GroupRepository groupRepo;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.fao.geonet.repository.UserGroupRepository userGroupRepo;

    [CtFieldImpl][CtCommentImpl]// Default values
    private [CtTypeReferenceImpl]java.lang.String surname = [CtLiteralImpl]"Sur Name";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String username = [CtLiteralImpl]"shibbolethtest";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String email = [CtLiteralImpl]"blabla@bleble.bli";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String firstname = [CtLiteralImpl]"First of her name";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String groupname = [CtLiteralImpl]"ShibTestGroup";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String organisation = [CtLiteralImpl]"Organisation";

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setUp() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]utils = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.fao.geonet.kernel.security.shibboleth.ShibbolethUserUtils();
        [CtAssignmentImpl][CtFieldWriteImpl]config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.fao.geonet.kernel.security.shibboleth.ShibbolethUserConfiguration();
        [CtInvocationImpl][CtFieldReadImpl]config.setArraySeparator([CtLiteralImpl]";");
        [CtInvocationImpl][CtFieldReadImpl]config.setDefaultGroup([CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"1");
        [CtInvocationImpl][CtFieldReadImpl]config.setEmailKey([CtLiteralImpl]"EMAIL_KEY");
        [CtInvocationImpl][CtFieldReadImpl]config.setFirstnameKey([CtLiteralImpl]"FIRSTNAME_KEY");
        [CtInvocationImpl][CtFieldReadImpl]config.setGroupKey([CtLiteralImpl]"GROUP_KEY");
        [CtInvocationImpl][CtFieldReadImpl]config.setProfileKey([CtLiteralImpl]"PROFILE_KEY");
        [CtInvocationImpl][CtFieldReadImpl]config.setSurnameKey([CtLiteralImpl]"SURNAME_KEY");
        [CtInvocationImpl][CtFieldReadImpl]config.setUpdateGroup([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]config.setUpdateProfile([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]config.setUsernameKey([CtLiteralImpl]"USERNAME_KEY");
        [CtInvocationImpl][CtFieldReadImpl]config.setOrganisationKey([CtLiteralImpl]"ORGANISATION_KEY");
        [CtInvocationImpl][CtFieldReadImpl]config.setRoleGroupSeparator([CtLiteralImpl]",");
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]5; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.Group group = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.fao.geonet.domain.Group();
            [CtInvocationImpl][CtVariableReadImpl]group.setName([CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtVariableReadImpl]i);
            [CtInvocationImpl][CtFieldReadImpl]groupRepo.save([CtVariableReadImpl]group);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.After
    public [CtTypeReferenceImpl]void cleanUp() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.User user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl][CtFieldReadImpl]userRepo.delete([CtInvocationImpl][CtVariableReadImpl]user.getId());
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]5; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.Group group = [CtInvocationImpl][CtFieldReadImpl]groupRepo.findByName([CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtVariableReadImpl]i);
            [CtInvocationImpl][CtFieldReadImpl]groupRepo.delete([CtVariableReadImpl]group);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void twoConsecutiveLogins() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.User user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNull([CtLiteralImpl]"User already exists", [CtVariableReadImpl]user);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String group = [CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"1";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String groups = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]group + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtVariableReadImpl]group;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String profile = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Profile.UserAdmin.name() + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtInvocationImpl][CtTypeAccessImpl]Profile.Administrator.name();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest();
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getEmailKey(), [CtFieldReadImpl]email);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getFirstnameKey(), [CtFieldReadImpl]firstname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getGroupKey(), [CtVariableReadImpl]groups);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getProfileKey(), [CtVariableReadImpl]profile);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getSurnameKey(), [CtFieldReadImpl]surname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getUsernameKey(), [CtFieldReadImpl]username);
        [CtInvocationImpl][CtFieldReadImpl]utils.setupUser([CtVariableReadImpl]request, [CtFieldReadImpl][CtThisAccessImpl]this.config);
        [CtAssignmentImpl][CtCommentImpl]// Checks
        [CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNotNull([CtLiteralImpl]"User was not created", [CtVariableReadImpl]user);
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"The profile should be the highest in the list", [CtTypeAccessImpl]Profile.Administrator, [CtInvocationImpl][CtVariableReadImpl]user.getProfile());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Integer> idGroups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findGroupIds([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"Groups size is wrong", [CtInvocationImpl][CtVariableReadImpl]idGroups.size(), [CtLiteralImpl]1);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The group assigned is wrong", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.valueOf([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]groupRepo.findByName([CtVariableReadImpl]group).getId()), [CtInvocationImpl][CtVariableReadImpl]idGroups.get([CtLiteralImpl]0));
        [CtAssignmentImpl][CtCommentImpl]// Second round, same user different authorization
        [CtVariableWriteImpl]group = [CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"3";
        [CtAssignmentImpl][CtVariableWriteImpl]groups = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]group + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtVariableReadImpl]group;
        [CtAssignmentImpl][CtVariableWriteImpl]profile = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Profile.Guest.name() + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtInvocationImpl][CtTypeAccessImpl]Profile.Editor.name();
        [CtAssignmentImpl][CtVariableWriteImpl]request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest();
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getEmailKey(), [CtFieldReadImpl]email);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getFirstnameKey(), [CtFieldReadImpl]firstname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getGroupKey(), [CtVariableReadImpl]groups);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getProfileKey(), [CtVariableReadImpl]profile);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getSurnameKey(), [CtFieldReadImpl]surname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getUsernameKey(), [CtFieldReadImpl]username);
        [CtInvocationImpl][CtFieldReadImpl]utils.setupUser([CtVariableReadImpl]request, [CtFieldReadImpl][CtThisAccessImpl]this.config);
        [CtAssignmentImpl][CtCommentImpl]// Checks
        [CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNotNull([CtLiteralImpl]"User was removed", [CtVariableReadImpl]user);
        [CtAssignmentImpl][CtVariableWriteImpl]idGroups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findGroupIds([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"The profile should be the highest in the list", [CtTypeAccessImpl]Profile.Editor, [CtInvocationImpl][CtVariableReadImpl]user.getProfile());
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"Groups size is wrong", [CtInvocationImpl][CtVariableReadImpl]idGroups.size(), [CtLiteralImpl]1);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The group assigned is wrong", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.valueOf([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]groupRepo.findByName([CtVariableReadImpl]group).getId()), [CtInvocationImpl][CtVariableReadImpl]idGroups.get([CtLiteralImpl]0));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void twoConsecutiveLoginsNoAuthorization() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]config.setUpdateGroup([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]config.setUpdateProfile([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.User user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNull([CtLiteralImpl]"User already exists", [CtVariableReadImpl]user);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String group = [CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"1";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String groups = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]group + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtVariableReadImpl]group;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String profile = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Profile.UserAdmin.name() + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtInvocationImpl][CtTypeAccessImpl]Profile.Administrator.name();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest();
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getEmailKey(), [CtFieldReadImpl]email);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getFirstnameKey(), [CtFieldReadImpl]firstname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getGroupKey(), [CtVariableReadImpl]groups);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getProfileKey(), [CtVariableReadImpl]profile);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getSurnameKey(), [CtFieldReadImpl]surname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getUsernameKey(), [CtFieldReadImpl]username);
        [CtInvocationImpl][CtFieldReadImpl]utils.setupUser([CtVariableReadImpl]request, [CtFieldReadImpl][CtThisAccessImpl]this.config);
        [CtAssignmentImpl][CtCommentImpl]// Checks
        [CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNotNull([CtLiteralImpl]"User was not created", [CtVariableReadImpl]user);
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"The profile should be the highest in the list", [CtTypeAccessImpl]Profile.Administrator, [CtInvocationImpl][CtVariableReadImpl]user.getProfile());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Integer> idGroups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findGroupIds([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"Groups size is wrong", [CtInvocationImpl][CtVariableReadImpl]idGroups.size(), [CtLiteralImpl]1);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The group assigned is wrong", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.valueOf([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]groupRepo.findByName([CtVariableReadImpl]group).getId()), [CtInvocationImpl][CtVariableReadImpl]idGroups.get([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtCommentImpl]// Second round, same user different authorization but the original
        [CtCommentImpl]// authorization should be kept (no updateProfile, updateGroups)
        [CtTypeReferenceImpl]java.lang.String groupNew = [CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"3";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String groupsgroupNew = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]groupNew + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtVariableReadImpl]groupNew;
        [CtAssignmentImpl][CtVariableWriteImpl]request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest();
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getEmailKey(), [CtFieldReadImpl]email);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getFirstnameKey(), [CtFieldReadImpl]firstname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getGroupKey(), [CtVariableReadImpl]groupsgroupNew);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getProfileKey(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Profile.Guest.name() + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtInvocationImpl][CtTypeAccessImpl]Profile.Editor.name());
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getSurnameKey(), [CtFieldReadImpl]surname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getUsernameKey(), [CtFieldReadImpl]username);
        [CtInvocationImpl][CtFieldReadImpl]utils.setupUser([CtVariableReadImpl]request, [CtFieldReadImpl][CtThisAccessImpl]this.config);
        [CtAssignmentImpl][CtCommentImpl]// Checks
        [CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNotNull([CtLiteralImpl]"User was removed", [CtVariableReadImpl]user);
        [CtAssignmentImpl][CtVariableWriteImpl]idGroups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findGroupIds([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"The profile should be the highest in the list", [CtTypeAccessImpl]Profile.Administrator, [CtInvocationImpl][CtVariableReadImpl]user.getProfile());
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"Groups size is wrong", [CtInvocationImpl][CtVariableReadImpl]idGroups.size(), [CtLiteralImpl]1);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The group assigned is wrong", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.valueOf([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]groupRepo.findByName([CtVariableReadImpl]group).getId()), [CtInvocationImpl][CtVariableReadImpl]idGroups.get([CtLiteralImpl]0));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void groupLengthNotMatchProfileLength() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.User user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNull([CtLiteralImpl]"User already exists", [CtVariableReadImpl]user);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String group = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]groupname + [CtLiteralImpl]"1") + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtFieldReadImpl]groupname) + [CtLiteralImpl]"2") + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtFieldReadImpl]groupname) + [CtLiteralImpl]"3";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String profile = [CtInvocationImpl][CtTypeAccessImpl]Profile.Editor.name();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest();
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getEmailKey(), [CtFieldReadImpl]email);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getFirstnameKey(), [CtFieldReadImpl]firstname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getGroupKey(), [CtVariableReadImpl]group);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getProfileKey(), [CtVariableReadImpl]profile);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getSurnameKey(), [CtFieldReadImpl]surname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getUsernameKey(), [CtFieldReadImpl]username);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getOrganisationKey(), [CtFieldReadImpl]organisation);
        [CtInvocationImpl][CtFieldReadImpl]utils.setupUser([CtVariableReadImpl]request, [CtFieldReadImpl][CtThisAccessImpl]this.config);
        [CtAssignmentImpl][CtCommentImpl]// Checks
        [CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNotNull([CtLiteralImpl]"User was not created", [CtVariableReadImpl]user);
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"The profile should be the highest in the list", [CtTypeAccessImpl]Profile.Editor, [CtInvocationImpl][CtVariableReadImpl]user.getProfile());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Integer> idGroups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findGroupIds([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"Groups size is wrong", [CtInvocationImpl][CtVariableReadImpl]idGroups.size(), [CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.fao.geonet.domain.UserGroup> groups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findAll([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.UserGroup ug : [CtVariableReadImpl]groups) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ug.getProfile().equals([CtTypeAccessImpl]Profile.Editor)) [CtBlockImpl]{
                [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ug.getGroup().getName().equalsIgnoreCase([CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"1"));
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ug.getProfile().equals([CtTypeAccessImpl]Profile.Guest)) [CtBlockImpl]{
                [CtInvocationImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ug.getGroup().getName().equalsIgnoreCase([CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"2") || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ug.getGroup().getName().equalsIgnoreCase([CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"3"));
            } else [CtBlockImpl]{
                [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"We have a usergroup we shouldn't have", [CtLiteralImpl]false);
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void severalGroups() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.User user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNull([CtLiteralImpl]"User already exists", [CtVariableReadImpl]user);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String group = [CtBinaryOperatorImpl][CtFieldReadImpl]groupname + [CtLiteralImpl]"1";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String profile = [CtInvocationImpl][CtTypeAccessImpl]Profile.Reviewer.name();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]2; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]5; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]group = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]group + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtFieldReadImpl]groupname) + [CtVariableReadImpl]i;
            [CtAssignmentImpl][CtVariableWriteImpl]profile = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]profile + [CtInvocationImpl][CtFieldReadImpl]config.getArraySeparator()) + [CtInvocationImpl][CtTypeAccessImpl]Profile.Editor.name();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.mock.web.MockHttpServletRequest();
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getEmailKey(), [CtFieldReadImpl]email);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getFirstnameKey(), [CtFieldReadImpl]firstname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getGroupKey(), [CtVariableReadImpl]group);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getProfileKey(), [CtVariableReadImpl]profile);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getSurnameKey(), [CtFieldReadImpl]surname);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.config.getUsernameKey(), [CtFieldReadImpl]username);
        [CtInvocationImpl][CtFieldReadImpl]utils.setupUser([CtVariableReadImpl]request, [CtFieldReadImpl][CtThisAccessImpl]this.config);
        [CtAssignmentImpl][CtCommentImpl]// Checks
        [CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]userRepo.findOneByUsername([CtFieldReadImpl]username);
        [CtInvocationImpl]Assert.assertNotNull([CtLiteralImpl]"User was not created", [CtVariableReadImpl]user);
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"The profile should be the highest in the list", [CtTypeAccessImpl]Profile.Reviewer, [CtInvocationImpl][CtVariableReadImpl]user.getProfile());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Integer> idGroups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findGroupIds([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtInvocationImpl]Assert.assertSame([CtLiteralImpl]"Groups size is wrong", [CtInvocationImpl][CtVariableReadImpl]idGroups.size(), [CtLiteralImpl]4);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.fao.geonet.domain.UserGroup> groups = [CtInvocationImpl][CtFieldReadImpl]userGroupRepo.findAll([CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.repository.specification.UserGroupSpecs.hasUserId([CtInvocationImpl][CtVariableReadImpl]user.getId()));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.fao.geonet.domain.UserGroup ug : [CtVariableReadImpl]groups) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertNotSame([CtLiteralImpl]"No profile can be guest as we have defined a role for all groups.", [CtTypeAccessImpl]Profile.Guest, [CtInvocationImpl][CtVariableReadImpl]ug.getProfile());
        }
    }
}
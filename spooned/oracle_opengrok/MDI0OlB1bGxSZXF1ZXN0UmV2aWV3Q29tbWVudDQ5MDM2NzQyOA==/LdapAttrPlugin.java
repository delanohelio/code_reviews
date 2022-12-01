[CompilationUnitImpl][CtCommentImpl]/* CDDL HEADER START

The contents of this file are subject to the terms of the
Common Development and Distribution License (the "License").
You may not use this file except in compliance with the License.

See LICENSE.txt included in this distribution for the specific
language governing permissions and limitations under the License.

When distributing Covered Code, include this CDDL HEADER in each
file and include the License file at LICENSE.txt.
If applicable, add the following below this CDDL HEADER, with the
fields enclosed by brackets "[]" replaced with your own identifying
information: Portions Copyright [yyyy] [name of copyright owner]

CDDL HEADER END
 */
[CtPackageDeclarationImpl]package opengrok.auth.plugin;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.opengrok.indexer.configuration.Group;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.opengrok.indexer.configuration.Project;
[CtUnresolvedImport]import org.opengrok.indexer.authorization.AuthorizationException;
[CtUnresolvedImport]import opengrok.auth.plugin.ldap.LdapException;
[CtImportImpl]import java.util.logging.Logger;
[CtUnresolvedImport]import opengrok.auth.entity.LdapUser;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtUnresolvedImport]import opengrok.auth.plugin.entity.User;
[CtImportImpl]import java.nio.file.Files;
[CtUnresolvedImport]import opengrok.auth.plugin.ldap.AbstractLdapProvider;
[CtImportImpl]import java.util.TreeSet;
[CtImportImpl]import java.nio.file.Paths;
[CtImportImpl]import java.util.logging.Level;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.util.Map;
[CtClassImpl][CtJavaDocImpl]/**
 * Authorization plug-in to check user's LDAP attribute against whitelist.
 *
 * This plugin heavily relies on the presence of the {@code LdapUserPlugin} in the stack above it,
 * since it is using the Distinguished Name of the {@code LdapUser} to perform the LDAP lookup.
 *
 * @author Krystof Tulinger
 */
public class LdapAttrPlugin extends [CtTypeReferenceImpl]opengrok.auth.plugin.AbstractLdapPlugin {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.logging.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.class.getName());

    [CtFieldImpl][CtJavaDocImpl]/**
     * List of configuration names.
     * <ul>
     * <li><code>attribute</code> is LDAP attribute to check (mandatory)</li>
     * <li><code>file</code> whitelist file (mandatory)</li>
     * <li><code>instance</code> is number of <code>LdapUserInstance</code> plugin to use (optional)</li>
     * </ul>
     */
    static final [CtTypeReferenceImpl]java.lang.String ATTR_PARAM = [CtLiteralImpl]"attribute";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String FILE_PARAM = [CtLiteralImpl]"file";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String INSTANCE_PARAM = [CtLiteralImpl]"instance";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SESSION_ALLOWED_PREFIX = [CtLiteralImpl]"opengrok-ldap-attr-plugin-allowed";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String sessionAllowed = [CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.SESSION_ALLOWED_PREFIX;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String ldapAttr;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> whitelist = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Integer ldapUserInstance;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String filePath;

    [CtConstructorImpl]public LdapAttrPlugin() [CtBlockImpl]{
        [CtOperatorAssignmentImpl][CtFieldWriteImpl]sessionAllowed += [CtBinaryOperatorImpl][CtLiteralImpl]"-" + [CtUnaryOperatorImpl]([CtFieldWriteImpl]nextId++);
    }

    [CtMethodImpl][CtCommentImpl]// for testing
    [CtTypeReferenceImpl]void load([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> parameters, [CtParameterImpl][CtTypeReferenceImpl]opengrok.auth.plugin.ldap.AbstractLdapProvider provider) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.load([CtVariableReadImpl]provider);
        [CtInvocationImpl]init([CtVariableReadImpl]parameters);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void load([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> parameters) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.load([CtVariableReadImpl]parameters);
        [CtInvocationImpl]init([CtVariableReadImpl]parameters);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void init([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> parameters) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtFieldWriteImpl]ldapAttr = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]parameters.get([CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.ATTR_PARAM)))) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Missing param [" + [CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.ATTR_PARAM) + [CtLiteralImpl]"] in the setup");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtFieldWriteImpl]filePath = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]parameters.get([CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.FILE_PARAM)))) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Missing param [" + [CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.FILE_PARAM) + [CtLiteralImpl]"] in the setup");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String instance = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]parameters.get([CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.INSTANCE_PARAM)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]instance != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]ldapUserInstance = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtVariableReadImpl]instance);
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]java.lang.String> stream = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.lines([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtFieldReadImpl]filePath))) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]stream.forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]whitelist::add);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to read the file \"%s\"", [CtFieldReadImpl]filePath), [CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINE, [CtLiteralImpl]"LdapAttrPlugin plugin loaded with attr={0}, whitelist={1}, instance={2}", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtFieldReadImpl]ldapAttr, [CtFieldReadImpl]filePath, [CtFieldReadImpl]ldapUserInstance });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]boolean sessionExists([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtSuperAccessImpl]super.sessionExists([CtVariableReadImpl]req) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.getSession().getAttribute([CtFieldReadImpl]sessionAllowed) != [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String getSessionAllowedAttrName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sessionAllowed;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void fillSession([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]opengrok.auth.plugin.entity.User user) [CtBlockImpl]{
        [CtInvocationImpl]updateSession([CtVariableReadImpl]req, [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]opengrok.auth.entity.LdapUser ldapUser = [CtInvocationImpl](([CtTypeReferenceImpl]opengrok.auth.entity.LdapUser) ([CtInvocationImpl][CtVariableReadImpl]req.getSession().getAttribute([CtInvocationImpl][CtTypeAccessImpl]opengrok.auth.plugin.LdapUserPlugin.getSessionAttrName([CtFieldReadImpl]ldapUserInstance))));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ldapUser == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]WARNING, [CtLiteralImpl]"cannot get {0} attribute from {1}", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtFieldReadImpl]LdapUserPlugin.SESSION_ATTR, [CtVariableReadImpl]user });
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Check attributes cached in LDAP user object first, then query LDAP server
        [CtCommentImpl]// (and if found, cache the result in the LDAP user object).
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> attributeValues = [CtInvocationImpl][CtVariableReadImpl]ldapUser.getAttribute([CtFieldReadImpl]ldapAttr);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]attributeValues == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String>> records = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]opengrok.auth.plugin.ldap.AbstractLdapProvider ldapProvider = [CtInvocationImpl]getLdapProvider();
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dn = [CtInvocationImpl][CtVariableReadImpl]ldapUser.getDn();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dn != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINEST, [CtLiteralImpl]"searching with dn={0} on {1}", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtVariableReadImpl]dn, [CtVariableReadImpl]ldapProvider });
                    [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]opengrok.auth.plugin.ldap.AbstractLdapProvider.LdapSearchResult<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String>>> res;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ldapProvider.lookupLdapContent([CtVariableReadImpl]dn, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtFieldReadImpl]ldapAttr })) == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]WARNING, [CtLiteralImpl]"cannot lookup attributes {0} for user {1} on {2})", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtFieldReadImpl]ldapAttr, [CtVariableReadImpl]ldapUser, [CtVariableReadImpl]ldapProvider });
                        [CtReturnImpl]return;
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]records = [CtInvocationImpl][CtVariableReadImpl]res.getAttrs();
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINE, [CtLiteralImpl]"no DN for LDAP user {0} on {1}", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtVariableReadImpl]ldapUser, [CtVariableReadImpl]ldapProvider });
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]opengrok.auth.plugin.ldap.LdapException ex) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opengrok.indexer.authorization.AuthorizationException([CtVariableReadImpl]ex);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]records == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]records.isEmpty()) || [CtBinaryOperatorImpl]([CtAssignmentImpl]([CtVariableWriteImpl]attributeValues = [CtInvocationImpl][CtVariableReadImpl]records.get([CtFieldReadImpl]ldapAttr)) == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]WARNING, [CtLiteralImpl]"empty records or attribute values {0} for user {1} on {2}", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtFieldReadImpl]ldapAttr, [CtVariableReadImpl]ldapUser, [CtVariableReadImpl]ldapProvider });
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtVariableReadImpl]ldapUser.setAttribute([CtFieldReadImpl]ldapAttr, [CtVariableReadImpl]attributeValues);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean sessionAllowed = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValues.stream().anyMatch([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]whitelist::contains);
        [CtInvocationImpl][CtFieldReadImpl]opengrok.auth.plugin.LdapAttrPlugin.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINEST, [CtLiteralImpl]"LDAP user {0} {1} against {2}", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtVariableReadImpl]ldapUser, [CtConditionalImpl][CtVariableReadImpl]sessionAllowed ? [CtLiteralImpl]"allowed" : [CtLiteralImpl]"denied", [CtFieldReadImpl]filePath });
        [CtInvocationImpl]updateSession([CtVariableReadImpl]req, [CtVariableReadImpl]sessionAllowed);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a new allowed value into the session.
     *
     * @param req
     * 		the request
     * @param allowed
     * 		the new value
     */
    private [CtTypeReferenceImpl]void updateSession([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]boolean allowed) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.getSession().setAttribute([CtFieldReadImpl]sessionAllowed, [CtVariableReadImpl]allowed);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean checkEntity([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]org.opengrok.indexer.configuration.Project project) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Boolean) ([CtInvocationImpl][CtVariableReadImpl]request.getSession().getAttribute([CtFieldReadImpl]sessionAllowed)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean checkEntity([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]org.opengrok.indexer.configuration.Group group) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Boolean) ([CtInvocationImpl][CtVariableReadImpl]request.getSession().getAttribute([CtFieldReadImpl]sessionAllowed)));
    }
}
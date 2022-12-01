[CompilationUnitImpl][CtCommentImpl]/* [INCLUDE-IF Sidecar18-SE] */
[CtCommentImpl]/* [IF Java12] */
[CtPackageDeclarationImpl]package java.lang;
[CtImportImpl]import java.security.ProtectionDomain;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.ibm.oti.reflect.TypeAnnotationParser;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import sun.reflect.generics.repository.ClassRepository;
[CtUnresolvedImport]import com.ibm.oti.vm.VM;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import sun.misc.Unsafe;
[CtImportImpl]import java.util.LinkedHashMap;
[CtUnresolvedImport]import sun.reflect.Reflection;
[CtImportImpl]import sun.reflect.annotation.AnnotationType;
[CtImportImpl]import sun.reflect.generics.scope.ClassScope;
[CtImportImpl]import java.lang.annotation.*;
[CtImportImpl]import java.net.URL;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtImportImpl]import java.security.AccessControlContext;
[CtImportImpl]import java.security.PrivilegedAction;
[CtImportImpl]import java.security.AllPermission;
[CtImportImpl]import java.lang.constant.Constable;
[CtImportImpl]import java.security.Permissions;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.lang.invoke.*;
[CtImportImpl]import java.lang.constant.ClassDesc;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import static com.ibm.oti.util.Util.doesClassLoaderDescendFrom;
[CtImportImpl]import java.lang.reflect.*;
[CtImportImpl]import java.lang.annotation.Repeatable;
[CtImportImpl]import java.security.PrivilegedActionException;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.security.PrivilegedExceptionAction;
[CtImportImpl]import sun.reflect.generics.factory.CoreReflectionFactory;
[CtImportImpl]import java.lang.ref.*;
[CtUnresolvedImport]import sun.reflect.CallerSensitive;
[CtImportImpl]import java.util.concurrent.atomic.AtomicInteger;
[CtImportImpl]import java.security.AccessController;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import sun.security.util.SecurityConstants;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import sun.reflect.ConstantPool;
[CtClassImpl][CtJavaDocImpl]/**
 * An instance of class Class is the in-image representation
 * of a Java class. There are three basic types of Classes
 * <dl>
 * <dt><em>Classes representing object types (classes or interfaces)</em></dt>
 * <dd>These are Classes which represent the class of a
 *     simple instance as found in the class hierarchy.
 *     The name of one of these Classes is simply the
 *     fully qualified class name of the class or interface
 *     that it represents. Its <em>signature</em> is
 *     the letter "L", followed by its name, followed
 *     by a semi-colon (";").</dd>
 * <dt><em>Classes representing base types</em></dt>
 * <dd>These Classes represent the standard Java base types.
 *     Although it is not possible to create new instances
 *     of these Classes, they are still useful for providing
 *     reflection information, and as the component type
 *     of array classes. There is one of these Classes for
 *     each base type, and their signatures are:
 *     <ul>
 *     <li><code>B</code> representing the <code>byte</code> base type</li>
 *     <li><code>S</code> representing the <code>short</code> base type</li>
 *     <li><code>I</code> representing the <code>int</code> base type</li>
 *     <li><code>J</code> representing the <code>long</code> base type</li>
 *     <li><code>F</code> representing the <code>float</code> base type</li>
 *     <li><code>D</code> representing the <code>double</code> base type</li>
 *     <li><code>C</code> representing the <code>char</code> base type</li>
 *     <li><code>Z</code> representing the <code>boolean</code> base type</li>
 *     <li><code>V</code> representing void function return values</li>
 *     </ul>
 *     The name of a Class representing a base type
 *     is the keyword which is used to represent the
 *     type in Java source code (i.e. "int" for the
 *     <code>int</code> base type.</dd>
 * <dt><em>Classes representing array classes</em></dt>
 * <dd>These are Classes which represent the classes of
 *     Java arrays. There is one such Class for all array
 *     instances of a given arity (number of dimensions)
 *     and leaf component type. In this case, the name of the
 *     class is one or more left square brackets (one per
 *     dimension in the array) followed by the signature ofP
 *     the class representing the leaf component type, which
 *     can be either an object type or a base type. The
 *     signature of a Class representing an array type
 *     is the same as its name.</dd>
 * </dl>
 *
 * @author OTI
 * @version initial
 */
[CtCommentImpl]/* [IF Java12] */
[CtCommentImpl]/* [ENDIF] */
[CtCommentImpl]/* [ENDIF] Java14 */
public final class Class<[CtTypeParameterImpl]T> implements [CtTypeReferenceImpl]java.io.Serializable , [CtTypeReferenceImpl]java.lang.reflect.GenericDeclaration , [CtTypeReferenceImpl]java.lang.reflect.Type , [CtTypeReferenceImpl]java.lang.constant.Constable , [CtTypeReferenceImpl]java.lang.invoke.TypeDescriptor , [CtTypeReferenceImpl][CtTypeReferenceImpl]java.lang.invoke.TypeDescriptor.OfField<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]3206093459760846163L;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.security.ProtectionDomain AllPermissionsPD;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int SYNTHETIC = [CtLiteralImpl]0x1000;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int ANNOTATION = [CtLiteralImpl]0x2000;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int ENUM = [CtLiteralImpl]0x4000;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MEMBER_INVALID_TYPE = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl][CtCommentImpl]/* [IF] */
    [CtJavaDocImpl]/**
     * It is important that these remain static final
     * because the VM peeks for them before running the <clinit>
     */
    [CtCommentImpl]/* [ENDIF] */
    static final [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] EmptyParameters = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[[CtLiteralImpl]0];

    [CtFieldImpl][CtCommentImpl]/* [PR VMDESIGN 485] */
    private transient [CtTypeReferenceImpl]long vmRef;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.ClassLoader classLoader;

    [CtFieldImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
    private transient [CtTypeReferenceImpl]java.lang.Module module;

    [CtFieldImpl][CtCommentImpl]/* [ENDIF] */
    [CtCommentImpl]/* [PR CMVC 125822] Move RAM class fields onto the heap to fix hotswap crash */
    private transient [CtTypeReferenceImpl]java.security.ProtectionDomain protectionDomain;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.String classNameString;

    [CtFieldImpl][CtCommentImpl]/* Cache filename on Class to avoid repeated lookups / allocations in stack traces */
    private transient [CtTypeReferenceImpl]java.lang.String fileNameString;

    [CtClassImpl]private static final class AnnotationVars {
        [CtConstructorImpl]AnnotationVars() [CtBlockImpl]{
        }

        [CtFieldImpl]static [CtTypeReferenceImpl]long annotationTypeOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtFieldImpl]static [CtTypeReferenceImpl]long valueMethodOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtFieldImpl][CtCommentImpl]/* [PR 66931] annotationType should be volatile because we use compare and swap */
        volatile [CtTypeReferenceImpl]sun.reflect.annotation.AnnotationType annotationType;

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.invoke.MethodHandle valueMethod;
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.Class.AnnotationVars annotationVars;

    [CtFieldImpl]private static [CtTypeReferenceImpl]long annotationVarsOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl][CtCommentImpl]/* [PR JAZZ 55717] add Java 8 new field: transient ClassValue.ClassValueMap classValueMap */
    [CtCommentImpl]/* [PR CMVC 200702] New field to support changes for RI defect 7030453 */
    transient [CtTypeReferenceImpl][CtTypeReferenceImpl]java.lang.ClassValue.ClassValueMap classValueMap;

    [CtClassImpl]private static final class EnumVars<[CtTypeParameterImpl]T> {
        [CtConstructorImpl]EnumVars() [CtBlockImpl]{
        }

        [CtFieldImpl]static [CtTypeReferenceImpl]long enumDirOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtFieldImpl]static [CtTypeReferenceImpl]long enumConstantsOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtFieldImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeParameterReferenceImpl]T> cachedEnumConstantDirectory;

        [CtFieldImpl][CtCommentImpl]/* [PR CMVC 188840] Perf: Class.getEnumConstants() is slow */
        [CtArrayTypeReferenceImpl]T[] cachedEnumConstants;
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.Class.EnumVars<[CtTypeParameterReferenceImpl]T> enumVars;

    [CtFieldImpl]private static [CtTypeReferenceImpl]long enumVarsOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]transient [CtTypeReferenceImpl]J9VMInternals.ClassInitializationLock initializationLock;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.Object methodHandleCache;

    [CtFieldImpl][CtCommentImpl]/* [PR Jazz 85476] Address locking contention on classRepository in getGeneric*() methods */
    private transient [CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder classRepoHolder;

    [CtClassImpl][CtCommentImpl]/* Helper class to hold the ClassRepository. We use a Class with a final 
    field to ensure that we have both safe initialization and safe publication.
     */
    private static final class ClassRepositoryHolder {
        [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder NullSingleton = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder([CtLiteralImpl]null);

        [CtFieldImpl]final [CtTypeReferenceImpl]sun.reflect.generics.repository.ClassRepository classRepository;

        [CtConstructorImpl]ClassRepositoryHolder([CtParameterImpl][CtTypeReferenceImpl]sun.reflect.generics.repository.ClassRepository classRepo) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]classRepository = [CtVariableReadImpl]classRepo;
        }
    }

    [CtClassImpl]private static final class AnnotationCache {
        [CtFieldImpl]final [CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> directAnnotationMap;

        [CtFieldImpl]final [CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> annotationMap;

        [CtConstructorImpl]AnnotationCache([CtParameterImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> directMap, [CtParameterImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> annMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]directAnnotationMap = [CtVariableReadImpl]directMap;
            [CtAssignmentImpl][CtFieldWriteImpl]annotationMap = [CtVariableReadImpl]annMap;
        }
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.Class.AnnotationCache annotationCache;

    [CtFieldImpl]private static [CtTypeReferenceImpl]long annotationCacheOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private static [CtTypeReferenceImpl]boolean reflectCacheEnabled;

    [CtFieldImpl]private static [CtTypeReferenceImpl]boolean reflectCacheDebug;

    [CtFieldImpl]private static [CtTypeReferenceImpl]boolean reflectCacheAppOnly = [CtLiteralImpl]true;

    [CtClassImpl][CtCommentImpl]/* This {@code ClassReflectNullPlaceHolder} class is created to indicate the cached class value is
    initialized to null rather than the default value null ;e.g. {@code cachedDeclaringClass}
    and {@code cachedEnclosingClass}. The reason default value has to be null is that
    j.l.Class instances are created by the VM only rather than Java, and
    any instance field with non-null default values have to be set by VM natives.
     */
    private static final class ClassReflectNullPlaceHolder {}

    [CtFieldImpl]private transient [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] cachedInterfaces;

    [CtFieldImpl]private static [CtTypeReferenceImpl]long cachedInterfacesOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cachedDeclaringClass;

    [CtFieldImpl]private static [CtTypeReferenceImpl]long cachedDeclaringClassOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cachedEnclosingClass;

    [CtFieldImpl]private static [CtTypeReferenceImpl]long cachedEnclosingClassOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private static [CtArrayTypeReferenceImpl]java.lang.annotation.Annotation[] EMPTY_ANNOTATION_ARRAY = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.annotation.Annotation[[CtLiteralImpl]0];

    [CtFieldImpl]static [CtTypeReferenceImpl][CtTypeReferenceImpl]java.lang.invoke.MethodHandles.Lookup implLookup;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]sun.misc.Unsafe unsafe = [CtInvocationImpl][CtTypeAccessImpl]sun.misc.Unsafe.getUnsafe();

    [CtMethodImpl]static [CtTypeReferenceImpl]sun.misc.Unsafe getUnsafe() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]java.lang.Class.unsafe;
    }

    [CtFieldImpl][CtCommentImpl]/* [IF Java11] */
    private [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> nestHost;

    [CtConstructorImpl][CtCommentImpl]/* [ENDIF] Java11 */
    [CtJavaDocImpl]/**
     * Prevents this class from being instantiated. Instances
     * created by the virtual machine only.
     */
    private Class() [CtBlockImpl]{
    }

    [CtMethodImpl][CtCommentImpl]/* Ensure the caller has the requested type of access.

    @param		security			the current SecurityManager
    @param		callerClassLoader	the ClassLoader of the caller of the original protected API
    @param		type				type of access, PUBLIC, DECLARED or INVALID
     */
    [CtTypeReferenceImpl]void checkMemberAccess([CtParameterImpl][CtTypeReferenceImpl]java.lang.SecurityManager security, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader, [CtParameterImpl][CtTypeReferenceImpl]int type) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]callerClassLoader != [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader loader = [CtInvocationImpl]getClassLoaderImpl();
            [CtIfImpl][CtCommentImpl]/* [PR CMVC 82311] Spec is incorrect before 1.5, RI has this behavior since 1.2 */
            [CtCommentImpl]/* [PR CMVC 201490] To remove CheckPackageAccess call from more Class methods */
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED) && [CtBinaryOperatorImpl]([CtVariableReadImpl]callerClassLoader != [CtVariableReadImpl]loader)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]security.checkPermission([CtFieldReadImpl][CtTypeAccessImpl]sun.security.util.SecurityConstants.[CtFieldReferenceImpl]CHECK_MEMBER_ACCESS_PERMISSION);
            }
            [CtIfImpl][CtCommentImpl]/* [PR CMVC 195558, 197433, 198986] Various fixes. */
            if ([CtInvocationImpl][CtTypeAccessImpl]sun.reflect.misc.ReflectUtil.needsPackageAccessCheck([CtVariableReadImpl]callerClassLoader, [CtVariableReadImpl]loader)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Proxy.isProxyClass([CtThisAccessImpl]this)) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]sun.reflect.misc.ReflectUtil.checkProxyPackageAccess([CtVariableReadImpl]callerClassLoader, [CtInvocationImpl][CtThisAccessImpl]this.getInterfaces());
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String packageName = [CtInvocationImpl][CtThisAccessImpl]this.getPackageName();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]packageName != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]packageName != [CtLiteralImpl]"")) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// $NON-NLS-1$
                        [CtVariableReadImpl]security.checkPackageAccess([CtVariableReadImpl]packageName);
                    }
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Ensure the caller has the requested type of access.
     *
     * This helper method is only called by getClasses, and skip security.checkPackageAccess()
     * when the class is a ProxyClass and the package name is sun.proxy.
     *
     * @param type			type
     * 		of access, PUBLIC or DECLARED
     */
    private [CtTypeReferenceImpl]void checkNonSunProxyMemberAccess([CtParameterImpl][CtTypeReferenceImpl]java.lang.SecurityManager security, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader, [CtParameterImpl][CtTypeReferenceImpl]int type) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]callerClassLoader != [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader loader = [CtInvocationImpl]getClassLoaderImpl();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED) && [CtBinaryOperatorImpl]([CtVariableReadImpl]callerClassLoader != [CtVariableReadImpl]loader)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]security.checkPermission([CtFieldReadImpl][CtTypeAccessImpl]sun.security.util.SecurityConstants.[CtFieldReferenceImpl]CHECK_MEMBER_ACCESS_PERMISSION);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String packageName = [CtInvocationImpl][CtThisAccessImpl]this.getPackageName();
            [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Proxy.isProxyClass([CtThisAccessImpl]this) && [CtInvocationImpl][CtVariableReadImpl]packageName.equals([CtFieldReadImpl][CtTypeAccessImpl]sun.reflect.misc.ReflectUtil.[CtFieldReferenceImpl]PROXY_PACKAGE))) && [CtBinaryOperatorImpl]([CtVariableReadImpl]packageName != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]packageName != [CtLiteralImpl]"")) && [CtInvocationImpl][CtTypeAccessImpl]sun.reflect.misc.ReflectUtil.needsPackageAccessCheck([CtVariableReadImpl]callerClassLoader, [CtVariableReadImpl]loader)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]security.checkPackageAccess([CtVariableReadImpl]packageName);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]long getFieldOffset([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.class.getDeclaredField([CtVariableReadImpl]fieldName);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]field);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This helper method atomically writes the given {@code fieldValue} to the
     * field specified by the {@code fieldOffset}
     */
    private [CtTypeReferenceImpl]void writeFieldValue([CtParameterImpl][CtTypeReferenceImpl]long fieldOffset, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object fieldValue) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
        [CtInvocationImpl]java.lang.Class.getUnsafe().putObjectRelease([CtThisAccessImpl]this, [CtVariableReadImpl]fieldOffset, [CtVariableReadImpl]fieldValue);
        [CtInvocationImpl][CtCommentImpl]/* [ELSE] */
        [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtThisAccessImpl]this, [CtVariableReadImpl]fieldOffset, [CtVariableReadImpl]fieldValue);
        [CtCommentImpl]/* [ENDIF] */
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void forNameAccessCheck([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.SecurityManager sm, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> callerClass, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> foundClass) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]callerClass) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.ProtectionDomain pd = [CtInvocationImpl][CtVariableReadImpl]callerClass.getPDImpl();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]pd) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedAction<[CtTypeReferenceImpl]java.lang.Object>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.Object run() [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]foundClass.checkMemberAccess([CtVariableReadImpl]sm, [CtInvocationImpl][CtVariableReadImpl]callerClass.getClassLoaderImpl(), [CtFieldReadImpl]java.lang.Class.MEMBER_INVALID_TYPE);
                        [CtReturnImpl]return [CtLiteralImpl]null;
                    }
                }, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.security.AccessControlContext([CtNewArrayImpl]new [CtTypeReferenceImpl]java.security.ProtectionDomain[]{ [CtVariableReadImpl]pd }));
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Class object which represents the class
     * named by the argument. The name should be the name
     * of a class as described in the class definition of
     * java.lang.Class, however Classes representing base
     * types can not be found using this method.
     *
     * @param className	The
     * 		name of the non-base type class to find
     * @return the named Class
     * @throws ClassNotFoundException
     * 		If the class could not be found
     * @see java.lang.Class
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public static [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> forName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String className) throws [CtTypeReferenceImpl]java.lang.ClassNotFoundException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtLiteralImpl]null;
        [CtIfImpl][CtJavaDocImpl]/**
         * Get the SecurityManager from System.  If the VM has not yet completed bootstrapping (i.e., J9VMInternals.initialized is still false)
         * sm is kept as null without referencing System in order to avoid loading System earlier than necessary.
         */
        if ([CtFieldReadImpl]J9VMInternals.initialized) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]sm) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.forNameImpl([CtVariableReadImpl]className, [CtLiteralImpl]true, [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.callerClassLoader());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> caller = [CtInvocationImpl]java.lang.Class.getStackClass([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]caller) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]callerClassLoader = [CtInvocationImpl][CtVariableReadImpl]caller.getClassLoaderImpl();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> c = [CtInvocationImpl]java.lang.Class.forNameImpl([CtVariableReadImpl]className, [CtLiteralImpl]false, [CtVariableReadImpl]callerClassLoader);
        [CtInvocationImpl]java.lang.Class.forNameAccessCheck([CtVariableReadImpl]sm, [CtVariableReadImpl]caller, [CtVariableReadImpl]c);
        [CtInvocationImpl][CtTypeAccessImpl]J9VMInternals.initialize([CtVariableReadImpl]c);
        [CtReturnImpl]return [CtVariableReadImpl]c;
    }

    [CtMethodImpl][CtTypeReferenceImpl]sun.reflect.annotation.AnnotationType getAnnotationType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.AnnotationVars localAnnotationVars = [CtInvocationImpl]getAnnotationVars();
        [CtReturnImpl]return [CtFieldReadImpl][CtVariableReadImpl]localAnnotationVars.annotationType;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void setAnnotationType([CtParameterImpl][CtTypeReferenceImpl]sun.reflect.annotation.AnnotationType t) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.AnnotationVars localAnnotationVars = [CtInvocationImpl]getAnnotationVars();
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]localAnnotationVars.annotationType = [CtVariableReadImpl]t;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Compare-And-Swap the AnnotationType instance corresponding to this class.
     * (This method only applies to annotation types.)
     */
    [CtTypeReferenceImpl]boolean casAnnotationType([CtParameterImpl][CtTypeReferenceImpl]sun.reflect.annotation.AnnotationType oldType, [CtParameterImpl][CtTypeReferenceImpl]sun.reflect.annotation.AnnotationType newType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.AnnotationVars localAnnotationVars = [CtInvocationImpl]getAnnotationVars();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long localTypeOffset = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.AnnotationVars.[CtFieldReferenceImpl]annotationTypeOffset;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](-[CtLiteralImpl]1) == [CtVariableReadImpl]localTypeOffset) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field = [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedAction<[CtTypeReferenceImpl]java.lang.reflect.Field>()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.reflect.Field run() [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.AnnotationVars.class.getDeclaredField([CtLiteralImpl]"annotationType");[CtCommentImpl]// $NON-NLS-1$

                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                    }
                }
            });
            [CtAssignmentImpl][CtVariableWriteImpl]localTypeOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]field);
            [CtAssignmentImpl][CtFieldWriteImpl][CtTypeAccessImpl]java.lang.Class.AnnotationVars.[CtFieldReferenceImpl]annotationTypeOffset = [CtVariableReadImpl]localTypeOffset;
        }
        [CtReturnImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
        return [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().compareAndSetObject([CtVariableReadImpl]localAnnotationVars, [CtVariableReadImpl]localTypeOffset, [CtVariableReadImpl]oldType, [CtVariableReadImpl]newType);
        [CtCommentImpl]/* [ELSE]
        return getUnsafe().compareAndSwapObject(localAnnotationVars, localTypeOffset, oldType, newType);
        /*[ENDIF]
         */
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Class object which represents the class
     * named by the argument. The name should be the name
     * of a class as described in the class definition of
     * java.lang.Class, however Classes representing base
     * types can not be found using this method.
     * Security rules will be obeyed.
     *
     * @param className			The
     * 		name of the non-base type class to find
     * @param initializeBoolean	A
     * 		boolean indicating whether the class should be
     * 		initialized
     * @param classLoader			The
     * 		classloader to use to load the class
     * @return the named class.
     * @throws ClassNotFoundException
     * 		If the class could not be found
     * @see java.lang.Class
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public static [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> forName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String className, [CtParameterImpl][CtTypeReferenceImpl]boolean initializeBoolean, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader classLoader) throws [CtTypeReferenceImpl]java.lang.ClassNotFoundException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtFieldReadImpl]J9VMInternals.initialized) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]sm) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.forNameImpl([CtVariableReadImpl]className, [CtVariableReadImpl]initializeBoolean, [CtVariableReadImpl]classLoader);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> caller = [CtInvocationImpl]java.lang.Class.getStackClass([CtLiteralImpl]1);
        [CtIfImpl][CtCommentImpl]/* perform security checks */
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]classLoader) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]caller) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtVariableReadImpl]caller.getClassLoaderImpl();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]callerClassLoader != [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]/* only allowed if caller has RuntimePermission("getClassLoader") permission */
                    [CtVariableReadImpl]sm.checkPermission([CtFieldReadImpl][CtTypeAccessImpl]sun.security.util.SecurityConstants.[CtFieldReferenceImpl]GET_CLASSLOADER_PERMISSION);
                }
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> c = [CtInvocationImpl]java.lang.Class.forNameImpl([CtVariableReadImpl]className, [CtLiteralImpl]false, [CtVariableReadImpl]classLoader);
        [CtInvocationImpl]java.lang.Class.forNameAccessCheck([CtVariableReadImpl]sm, [CtVariableReadImpl]caller, [CtVariableReadImpl]c);
        [CtIfImpl]if ([CtVariableReadImpl]initializeBoolean) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]J9VMInternals.initialize([CtVariableReadImpl]c);
        }
        [CtReturnImpl]return [CtVariableReadImpl]c;
    }

    [CtMethodImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtJavaDocImpl]/**
     * Answers a Class object which represents the class
     * with the given name in the given module.
     * The name should be the name of a class as described
     * in the class definition of java.lang.Class,
     * however Classes representing base
     * types can not be found using this method.
     * It does not invoke the class initializer.
     * Note that this method does not check whether the
     * requested class is accessible to its caller.
     * Security rules will be obeyed.
     *
     * @param module
     * 		The name of the module
     * @param name
     * 		The name of the non-base type class to find
     * @return The Class object representing the named class
     * @see java.lang.Class
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public static [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> forName([CtParameterImpl][CtTypeReferenceImpl]java.lang.Module module, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader classLoader;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> c;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]module) || [CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]name)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException();
        }
        [CtIfImpl]if ([CtFieldReadImpl]J9VMInternals.initialized) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> caller = [CtInvocationImpl]java.lang.Class.getStackClass([CtLiteralImpl]1);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]sm) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]/* If the caller is not the specified module and RuntimePermission("getClassLoader") permission is denied, throw SecurityException */
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]caller) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]caller.getModule() != [CtVariableReadImpl]module)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sm.checkPermission([CtFieldReadImpl][CtTypeAccessImpl]sun.security.util.SecurityConstants.[CtFieldReferenceImpl]GET_CLASSLOADER_PERMISSION);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]classLoader = [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedAction<[CtTypeReferenceImpl]java.lang.ClassLoader>()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.ClassLoader run() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]module.getClassLoader();
                }
            });
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]classLoader = [CtInvocationImpl][CtVariableReadImpl]module.getClassLoader();
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]classLoader == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]c = [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader.loadClass([CtVariableReadImpl]module, [CtVariableReadImpl]name);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]c = [CtInvocationImpl][CtVariableReadImpl]classLoader.loadClassHelper([CtVariableReadImpl]name, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtVariableReadImpl]module);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]/* This method returns null on failure rather than throwing a ClassNotFoundException */
            return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]c) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]/* If the class loader of the given module defines other modules and 
            the given name is a class defined in a different module, 
            this method returns null after the class is loaded.
             */
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]c.getModule() != [CtVariableReadImpl]module) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]c;
    }

    [CtMethodImpl][CtCommentImpl]/* [ENDIF] Sidecar19-SE */
    [CtJavaDocImpl]/**
     * Answers a Class object which represents the class
     * named by the argument. The name should be the name
     * of a class as described in the class definition of
     * java.lang.Class, however Classes representing base
     * types can not be found using this method.
     *
     * @param className			The
     * 		name of the non-base type class to find
     * @param initializeBoolean	A
     * 		boolean indicating whether the class should be
     * 		initialized
     * @param classLoader			The
     * 		classloader to use to load the class
     * @return the named class.
     * @throws ClassNotFoundException
     * 		If the class could not be found
     * @see java.lang.Class
     */
    private static native [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> forNameImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String className, [CtParameterImpl][CtTypeReferenceImpl]boolean initializeBoolean, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader classLoader) throws [CtTypeReferenceImpl]java.lang.ClassNotFoundException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing all public class members
     * of the class which the receiver represents and its
     * superclasses and interfaces
     *
     * @return the class' public class members
     * @throws SecurityException
     * 		If member access is not allowed
     * @see java.lang.Class
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getClasses() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 82311] Spec is incorrect before 1.5, RI has this behavior since 1.2 */
        [CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkNonSunProxyMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> publicClasses = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> current = [CtThisAccessImpl]this;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] classes;
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]current != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]/* [PR 97353] Call the native directly, as the security check in getDeclaredClasses() does nothing but check the caller classloader */
            [CtVariableWriteImpl]classes = [CtInvocationImpl][CtVariableReadImpl]current.getDeclaredClassesImpl();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]classes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++)[CtBlockImpl]
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isPublic([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]classes[[CtVariableReadImpl]i].getModifiers()))[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]publicClasses.addElement([CtArrayReadImpl][CtVariableReadImpl]classes[[CtVariableReadImpl]i]);


            [CtAssignmentImpl][CtVariableWriteImpl]current = [CtInvocationImpl][CtVariableReadImpl]current.getSuperclass();
        } 
        [CtAssignmentImpl][CtVariableWriteImpl]classes = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[[CtInvocationImpl][CtVariableReadImpl]publicClasses.size()];
        [CtInvocationImpl][CtVariableReadImpl]publicClasses.copyInto([CtVariableReadImpl]classes);
        [CtReturnImpl]return [CtVariableReadImpl]classes;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the classloader which was used to load the
     * class represented by the receiver. Answer null if the
     * class was loaded by the system class loader.
     *
     * @return the receiver's class loader or nil
     * @see java.lang.ClassLoader
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.ClassLoader getClassLoader() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]classLoader) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]classLoader == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]security) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callersClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.callerClassLoader();
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.needsClassLoaderPermissionCheck([CtVariableReadImpl]callersClassLoader, [CtFieldReadImpl]classLoader)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]security.checkPermission([CtFieldReadImpl][CtTypeAccessImpl]sun.security.util.SecurityConstants.[CtFieldReferenceImpl]GET_CLASSLOADER_PERMISSION);
                }
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]classLoader;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the classloader used to load the receiver's class.
     * Returns null if the class was loaded by the bootstrap (system) class loader.
     * This skips security checks.
     *
     * @return the receiver's class loader or null
     * @see java.lang.ClassLoader
     */
    [CtTypeReferenceImpl]java.lang.ClassLoader internalGetClassLoader() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]classLoader == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader ? [CtLiteralImpl]null : [CtFieldReadImpl]classLoader;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the ClassLoader which was used to load the
     * class represented by the receiver.
     *
     * @return the receiver's class loader
     * @see java.lang.ClassLoader
     */
    [CtTypeReferenceImpl]java.lang.ClassLoader getClassLoader0() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader loader = [CtInvocationImpl]getClassLoaderImpl();
        [CtReturnImpl]return [CtVariableReadImpl]loader;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the ClassLoader for this Class without doing any security
     * checks. The bootstrap ClassLoader is returned, unlike getClassLoader()
     * which returns null in place of the bootstrap ClassLoader.
     *
     * @return the ClassLoader
     * @see ClassLoader#isASystemClassLoader()
     */
    [CtTypeReferenceImpl]java.lang.ClassLoader getClassLoaderImpl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]classLoader;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Class object which represents the receiver's
     * component type if the receiver represents an array type.
     * Otherwise answers nil. The component type of an array
     * type is the type of the elements of the array.
     *
     * @return the component type of the receiver.
     * @see java.lang.Class
     */
    public native [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getComponentType();

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.NoSuchMethodException newNoSuchMethodException([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] types) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]error.append([CtInvocationImpl]getName()).append([CtLiteralImpl]'.').append([CtVariableReadImpl]name).append([CtLiteralImpl]'(');
        [CtForImpl][CtCommentImpl]/* [PR CMVC 80340] check for null types */
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]types.length; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i != [CtLiteralImpl]0)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]error.append([CtLiteralImpl]", ");
            [CtCommentImpl]// $NON-NLS-1$

            [CtInvocationImpl][CtVariableReadImpl]error.append([CtConditionalImpl][CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]types[[CtVariableReadImpl]i] == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]types[[CtVariableReadImpl]i].getName());
        }
        [CtInvocationImpl][CtVariableReadImpl]error.append([CtLiteralImpl]')');
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NoSuchMethodException([CtInvocationImpl][CtVariableReadImpl]error.toString());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a public Constructor object which represents the
     * constructor described by the arguments.
     *
     * @param parameterTypes	the
     * 		types of the arguments.
     * @return the constructor described by the arguments.
     * @throws NoSuchMethodException
     * 		if the constructor could not be found.
     * @throws SecurityException
     * 		if member access is not allowed
     * @see #getConstructors
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> getConstructor([CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException, [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtIfImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameterTypes == [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]parameterTypes = [CtFieldReadImpl]java.lang.Class.EmptyParameters;

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> cachedConstructor = [CtInvocationImpl]lookupCachedConstructor([CtVariableReadImpl]parameterTypes);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cachedConstructor != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isPublic([CtInvocationImpl][CtVariableReadImpl]cachedConstructor.getModifiers())) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedConstructor;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> rc;
        [CtIfImpl][CtCommentImpl]// Handle the default constructor case upfront
        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]rc = [CtInvocationImpl]getConstructorImpl([CtVariableReadImpl]parameterTypes, [CtLiteralImpl]"()V");[CtCommentImpl]// $NON-NLS-1$

        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]parameterTypes = [CtInvocationImpl][CtVariableReadImpl]parameterTypes.clone();
            [CtLocalVariableImpl][CtCommentImpl]// Build a signature for the requested method.
            [CtTypeReferenceImpl]java.lang.String signature = [CtInvocationImpl]getParameterTypesSignature([CtLiteralImpl]true, [CtLiteralImpl]"<init>", [CtVariableReadImpl]parameterTypes, [CtLiteralImpl]"V");[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

            [CtAssignmentImpl][CtVariableWriteImpl]rc = [CtInvocationImpl]getConstructorImpl([CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]signature);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rc != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]rc = [CtInvocationImpl]java.lang.Class.checkParameterTypes([CtVariableReadImpl]rc, [CtVariableReadImpl]parameterTypes);

        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rc == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtInvocationImpl]newNoSuchMethodException([CtLiteralImpl]"<init>", [CtVariableReadImpl]parameterTypes);
        [CtCommentImpl]// $NON-NLS-1$

        [CtReturnImpl]return [CtInvocationImpl]cacheConstructor([CtVariableReadImpl]rc);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a public Constructor object which represents the
     * constructor described by the arguments.
     *
     * @param parameterTypes	the
     * 		types of the arguments.
     * @param signature		the
     * 		signature of the method.
     * @return the constructor described by the arguments.
     * @see #getConstructors
     */
    private native [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> getConstructorImpl([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String signature);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Constructor objects describing
     * all constructors which are visible from the current execution
     * context.
     *
     * @return all visible constructors starting from the receiver.
     * @throws SecurityException
     * 		if member access is not allowed
     * @see #getMethods
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?>[] getConstructors() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] cachedConstructors = [CtInvocationImpl]lookupCachedConstructors([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]PublicConstructorsKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cachedConstructors != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedConstructors;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] ctors = [CtInvocationImpl]getConstructorsImpl();
        [CtReturnImpl]return [CtInvocationImpl]cacheConstructors([CtVariableReadImpl]ctors, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]PublicConstructorsKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Constructor objects describing
     * all constructors which are visible from the current execution
     * context.
     *
     * @return all visible constructors starting from the receiver.
     * @see #getMethods
     */
    private native [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] getConstructorsImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing all class members of the class
     * which the receiver represents. Note that some of the fields
     * which are returned may not be visible in the current
     * execution context.
     *
     * @return the class' class members
     * @throws SecurityException
     * 		if member access is not allowed
     * @see java.lang.Class
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getDeclaredClasses() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkNonSunProxyMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
        }
        [CtReturnImpl][CtCommentImpl]/* [PR 97353] getClasses() calls this native directly */
        return [CtInvocationImpl]getDeclaredClassesImpl();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing all class members of the class
     * which the receiver represents. Note that some of the fields
     * which are returned may not be visible in the current
     * execution context.
     *
     * @return the class' class members
     * @see java.lang.Class
     */
    private native [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getDeclaredClassesImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Constructor object which represents the
     * constructor described by the arguments.
     *
     * @param parameterTypes	the
     * 		types of the arguments.
     * @return the constructor described by the arguments.
     * @throws NoSuchMethodException
     * 		if the constructor could not be found.
     * @throws SecurityException
     * 		if member access is not allowed
     * @see #getConstructors
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> getDeclaredConstructor([CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException, [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
        }
        [CtIfImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameterTypes == [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]parameterTypes = [CtFieldReadImpl]java.lang.Class.EmptyParameters;

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> cachedConstructor = [CtInvocationImpl]lookupCachedConstructor([CtVariableReadImpl]parameterTypes);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cachedConstructor != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedConstructor;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> rc;
        [CtIfImpl][CtCommentImpl]// Handle the default constructor case upfront
        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]rc = [CtInvocationImpl]getDeclaredConstructorImpl([CtVariableReadImpl]parameterTypes, [CtLiteralImpl]"()V");[CtCommentImpl]// $NON-NLS-1$

        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]parameterTypes = [CtInvocationImpl][CtVariableReadImpl]parameterTypes.clone();
            [CtLocalVariableImpl][CtCommentImpl]// Build a signature for the requested method.
            [CtTypeReferenceImpl]java.lang.String signature = [CtInvocationImpl]getParameterTypesSignature([CtLiteralImpl]true, [CtLiteralImpl]"<init>", [CtVariableReadImpl]parameterTypes, [CtLiteralImpl]"V");[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

            [CtAssignmentImpl][CtVariableWriteImpl]rc = [CtInvocationImpl]getDeclaredConstructorImpl([CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]signature);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rc != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]rc = [CtInvocationImpl]java.lang.Class.checkParameterTypes([CtVariableReadImpl]rc, [CtVariableReadImpl]parameterTypes);

        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rc == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtInvocationImpl]newNoSuchMethodException([CtLiteralImpl]"<init>", [CtVariableReadImpl]parameterTypes);
        [CtCommentImpl]// $NON-NLS-1$

        [CtReturnImpl]return [CtInvocationImpl]cacheConstructor([CtVariableReadImpl]rc);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Constructor object which represents the
     * constructor described by the arguments.
     *
     * @param parameterTypes	the
     * 		types of the arguments.
     * @param signature		the
     * 		signature of the method.
     * @return the constructor described by the arguments.
     * @see #getConstructors
     */
    private native [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> getDeclaredConstructorImpl([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String signature);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Constructor objects describing
     * all constructor which are defined by the receiver. Note that
     * some of the fields which are returned may not be visible
     * in the current execution context.
     *
     * @return the receiver's constructors.
     * @throws SecurityException
     * 		if member access is not allowed
     * @see #getMethods
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?>[] getDeclaredConstructors() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] cachedConstructors = [CtInvocationImpl]lookupCachedConstructors([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]DeclaredConstructorsKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cachedConstructors != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedConstructors;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] ctors = [CtInvocationImpl]getDeclaredConstructorsImpl();
        [CtReturnImpl]return [CtInvocationImpl]cacheConstructors([CtVariableReadImpl]ctors, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]DeclaredConstructorsKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Constructor objects describing
     * all constructor which are defined by the receiver. Note that
     * some of the fields which are returned may not be visible
     * in the current execution context.
     *
     * @return the receiver's constructors.
     * @see #getMethods
     */
    private native [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] getDeclaredConstructorsImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Field object describing the field in the receiver
     * named by the argument. Note that the Constructor may not be
     * visible from the current execution context.
     *
     * @param name		The
     * 		name of the field to look for.
     * @return the field in the receiver named by the argument.
     * @throws NoSuchFieldException
     * 		if the requested field could not be found
     * @throws SecurityException
     * 		if member access is not allowed
     * @see #getDeclaredFields
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Field getDeclaredField([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) throws [CtTypeReferenceImpl]java.lang.NoSuchFieldException, [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtTypeReferenceImpl]java.lang.reflect.Field cachedField = [CtInvocationImpl]lookupCachedField([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cachedField != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]cachedField.getDeclaringClass() == [CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedField;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field = [CtInvocationImpl]getDeclaredFieldImpl([CtVariableReadImpl]name);
        [CtLocalVariableImpl][CtCommentImpl]/* [PR JAZZ 102876] IBM J9VM not using Reflection.filterFields API to hide the sensitive fields */
        [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] fields = [CtInvocationImpl][CtTypeAccessImpl]sun.reflect.Reflection.filterFields([CtThisAccessImpl]this, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Field[]{ [CtVariableReadImpl]field });
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]0 == [CtFieldReadImpl][CtVariableReadImpl]fields.length) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NoSuchFieldException([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtInvocationImpl]cacheField([CtArrayReadImpl][CtVariableReadImpl]fields[[CtLiteralImpl]0]);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Field object describing the field in the receiver
     * named by the argument. Note that the Constructor may not be
     * visible from the current execution context.
     *
     * @param name		The
     * 		name of the field to look for.
     * @return the field in the receiver named by the argument.
     * @throws NoSuchFieldException
     * 		If the given field does not exist
     * @see #getDeclaredFields
     */
    private native [CtTypeReferenceImpl]java.lang.reflect.Field getDeclaredFieldImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) throws [CtTypeReferenceImpl]java.lang.NoSuchFieldException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Field objects describing
     * all fields which are defined by the receiver. Note that
     * some of the fields which are returned may not be visible
     * in the current execution context.
     *
     * @return the receiver's fields.
     * @throws SecurityException
     * 		If member access is not allowed
     * @see #getFields
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] getDeclaredFields() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] cachedFields = [CtInvocationImpl]lookupCachedFields([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]DeclaredFieldsKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cachedFields != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedFields;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Field[] fields = [CtInvocationImpl]getDeclaredFieldsImpl();
        [CtReturnImpl]return [CtInvocationImpl]cacheFields([CtInvocationImpl][CtTypeAccessImpl]sun.reflect.Reflection.filterFields([CtThisAccessImpl]this, [CtVariableReadImpl]fields), [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]DeclaredFieldsKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Field objects describing
     * all fields which are defined by the receiver. Note that
     * some of the fields which are returned may not be visible
     * in the current execution context.
     *
     * @return the receiver's fields.
     * @see #getFields
     */
    private native [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] getDeclaredFieldsImpl();

    [CtMethodImpl][CtCommentImpl]/* [IF Sidecar19-SE]
    /**
    Answers a list of method objects which represent the public methods
    described by the arguments. Note that the associated method may not 
    be visible from the current execution context.
    An empty list is returned if the method can't be found.

    @param		name			the name of the method
    @param		parameterTypes	the types of the arguments.
    @return		a list of methods described by the arguments.

    @see			#getMethods
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method> getDeclaredPublicMethods([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey ck = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newDeclaredPublicMethodsKey([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method> methodList = [CtInvocationImpl]lookupCachedDeclaredPublicMethods([CtVariableReadImpl]ck);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]methodList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]methodList;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]methodList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtInvocationImpl]getMethodHelper([CtLiteralImpl]false, [CtLiteralImpl]true, [CtVariableReadImpl]methodList, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchMethodException e) [CtBlockImpl]{
            [CtCommentImpl]// no NoSuchMethodException expected
        }
        [CtReturnImpl]return [CtInvocationImpl]cacheDeclaredPublicMethods([CtVariableReadImpl]methodList, [CtVariableReadImpl]ck);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method> lookupCachedDeclaredPublicMethods([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"lookup DeclaredPublicMethods in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]peekReflectCache();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method> methods = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method>) ([CtVariableReadImpl]cache.find([CtVariableReadImpl]cacheKey)));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]methods != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// assuming internal caller won't change this method list content
                return [CtVariableReadImpl]methods;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@sun.reflect.CallerSensitive
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method> cacheDeclaredPublicMethods([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method> methods, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled) || [CtBinaryOperatorImpl]([CtFieldReadImpl]java.lang.Class.reflectCacheAppOnly && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]2) == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader))) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]methods;
        }
        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"cache DeclaredPublicMethods in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtInvocationImpl]acquireReflectCache();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]methods.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method method = [CtInvocationImpl][CtVariableReadImpl]methods.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newMethodKey([CtInvocationImpl][CtVariableReadImpl]method.getName(), [CtInvocationImpl]java.lang.Class.getParameterTypes([CtVariableReadImpl]method), [CtInvocationImpl][CtVariableReadImpl]method.getReturnType());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method methodPut = [CtInvocationImpl][CtVariableReadImpl]cache.insertIfAbsent([CtVariableReadImpl]key, [CtVariableReadImpl]method);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]method != [CtVariableReadImpl]methodPut) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]methods.set([CtVariableReadImpl]i, [CtVariableReadImpl]methodPut);
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]cache.insert([CtVariableReadImpl]cacheKey, [CtVariableReadImpl]methods);
        } finally [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cache.release();
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]methods;
    }

    [CtMethodImpl][CtCommentImpl]/* [ENDIF] */
    [CtJavaDocImpl]/**
     * A helper method for reflection debugging
     *
     * @param parameters
     * 		parameters[i].getName() to be appended
     * @param posInsert
     * 		parameters to be appended AFTER msgs[posInsert]
     * @param msgs
     * 		a message array
     */
    static [CtTypeReferenceImpl]void reflectCacheDebugHelper([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameters, [CtParameterImpl][CtTypeReferenceImpl]int posInsert, [CtParameterImpl]java.lang.String... msgs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder output = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]200);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]msgs.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]output.append([CtArrayReadImpl][CtVariableReadImpl]msgs[[CtVariableReadImpl]i]);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]parameters != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]i == [CtVariableReadImpl]posInsert)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]'(');
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int j = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]j < [CtFieldReadImpl][CtVariableReadImpl]parameters.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]j++) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]j != [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]", ");[CtCommentImpl]// $NON-NLS-1$

                    }
                    [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]parameters[[CtVariableReadImpl]i].getName());
                }
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]')');
            }
        }
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]err.println([CtVariableReadImpl]output);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Method object which represents the method
     * described by the arguments. Note that the associated
     * method may not be visible from the current execution
     * context.
     *
     * @param name			the
     * 		name of the method
     * @param parameterTypes	the
     * 		types of the arguments.
     * @return the method described by the arguments.
     * @throws NoSuchMethodException
     * 		if the method could not be found.
     * @throws SecurityException
     * 		If member access is not allowed
     * @see #getMethods
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Method getDeclaredMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException, [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
        }
        [CtReturnImpl]return [CtInvocationImpl]getMethodHelper([CtLiteralImpl]true, [CtLiteralImpl]true, [CtLiteralImpl]null, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This native iterates over methods matching the provided name and signature
     * in the receiver class. The startingPoint parameter is passed the last
     * method returned (or null on the first use), and the native returns the next
     * matching method or null if there are no more matches.
     * Note that the associated method may not be visible from the
     * current execution context.
     *
     * @param name				the
     * 		name of the method
     * @param parameterTypes		the
     * 		types of the arguments.
     * @param partialSignature	the
     * 		signature of the method, without return type.
     * @param startingPoint		the
     * 		method to start searching after, or null to start at the beginning
     * @return the next Method described by the arguments
     * @see #getMethods
     */
    private native [CtTypeReferenceImpl]java.lang.reflect.Method getDeclaredMethodImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String partialSignature, [CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method startingPoint);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Method objects describing
     * all methods which are defined by the receiver. Note that
     * some of the methods which are returned may not be visible
     * in the current execution context.
     *
     * @throws SecurityException	if
     * 		member access is not allowed
     * @return the receiver's methods.
     * @see #getMethods
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.reflect.Method[] getDeclaredMethods() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtArrayTypeReferenceImpl]java.lang.reflect.Method[] cachedMethods = [CtInvocationImpl]lookupCachedMethods([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]DeclaredMethodsKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cachedMethods != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedMethods;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtReturnImpl][CtCommentImpl]/* [PR CMVC 194301] do not allow reflect access to sun.misc.Unsafe.getUnsafe() */
        return [CtInvocationImpl]cacheMethods([CtInvocationImpl][CtTypeAccessImpl]sun.reflect.Reflection.filterMethods([CtThisAccessImpl]this, [CtInvocationImpl]getDeclaredMethodsImpl()), [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]DeclaredMethodsKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Method objects describing
     * all methods which are defined by the receiver. Note that
     * some of the methods which are returned may not be visible
     * in the current execution context.
     *
     * @return the receiver's methods.
     * @see #getMethods
     */
    private native [CtArrayTypeReferenceImpl]java.lang.reflect.Method[] getDeclaredMethodsImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the class which declared the class represented
     * by the receiver. This will return null if the receiver
     * is not a member of another class.
     *
     * @return the declaring class of the receiver.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getDeclaringClass() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.cachedDeclaringClassOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.cachedDeclaringClassOffset = [CtInvocationImpl]getFieldOffset([CtLiteralImpl]"cachedDeclaringClass");[CtCommentImpl]// $NON-NLS-1$

        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]cachedDeclaringClass == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> localDeclaringClass = [CtInvocationImpl]getDeclaringClassImpl();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localDeclaringClass == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]localDeclaringClass = [CtFieldReadImpl]java.lang.Class.ClassReflectNullPlaceHolder.class;
            }
            [CtInvocationImpl]writeFieldValue([CtFieldReadImpl]java.lang.Class.cachedDeclaringClassOffset, [CtVariableReadImpl]localDeclaringClass);
        }
        [CtLocalVariableImpl][CtJavaDocImpl]/**
         * ClassReflectNullPlaceHolder.class means the value of cachedDeclaringClass is null
         *
         * @see ClassReflectNullPlaceHolder.class
         */
        [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]cachedDeclaringClass == [CtFieldReadImpl]java.lang.Class.ClassReflectNullPlaceHolder.class) ? [CtLiteralImpl]null : [CtFieldReadImpl]cachedDeclaringClass;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]declaringClass == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]declaringClass;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]declaringClass.isClassADeclaredClass([CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
                [CtInvocationImpl][CtVariableReadImpl]declaringClass.checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl]java.lang.Class.MEMBER_INVALID_TYPE);
            }
            [CtReturnImpl]return [CtVariableReadImpl]declaringClass;
        }
        [CtThrowImpl][CtCommentImpl]/* [MSG "K0555", "incompatible InnerClasses attribute between \"{0}\" and \"{1}\""] */
        throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IncompatibleClassChangeError([CtInvocationImpl][CtTypeAccessImpl]com.ibm.oti.util.Msg.getString([CtLiteralImpl]"K0555", [CtInvocationImpl][CtThisAccessImpl]this.getName(), [CtInvocationImpl][CtVariableReadImpl]declaringClass.getName()));[CtCommentImpl]// $NON-NLS-1$

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the class passed in to the method is a declared class of
     * this class.
     *
     * @param aClass		The
     * 		class to validate
     * @return true if aClass a declared class of this class
    false otherwise.
     */
    private native [CtTypeReferenceImpl]boolean isClassADeclaredClass([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> aClass);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the class which declared the class represented
     * by the receiver. This will return null if the receiver
     * is a member of another class.
     *
     * @return the declaring class of the receiver.
     */
    private native [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getDeclaringClassImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Field object describing the field in the receiver
     * named by the argument which must be visible from the current
     * execution context.
     *
     * @param name		The
     * 		name of the field to look for.
     * @return the field in the receiver named by the argument.
     * @throws NoSuchFieldException
     * 		If the given field does not exist
     * @throws SecurityException
     * 		If access is denied
     * @see #getDeclaredFields
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Field getField([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) throws [CtTypeReferenceImpl]java.lang.NoSuchFieldException, [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtTypeReferenceImpl]java.lang.reflect.Field cachedField = [CtInvocationImpl]lookupCachedField([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cachedField != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isPublic([CtInvocationImpl][CtVariableReadImpl]cachedField.getModifiers())) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedField;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field = [CtInvocationImpl]getFieldImpl([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]0 == [CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]sun.reflect.Reflection.filterFields([CtThisAccessImpl]this, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Field[]{ [CtVariableReadImpl]field }).length) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NoSuchFieldException([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtInvocationImpl]cacheField([CtVariableReadImpl]field);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Field object describing the field in the receiver
     * named by the argument which must be visible from the current
     * execution context.
     *
     * @param name		The
     * 		name of the field to look for.
     * @return the field in the receiver named by the argument.
     * @throws NoSuchFieldException
     * 		If the given field does not exist
     * @see #getDeclaredFields
     */
    private native [CtTypeReferenceImpl]java.lang.reflect.Field getFieldImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) throws [CtTypeReferenceImpl]java.lang.NoSuchFieldException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Field objects describing
     * all fields which are visible from the current execution
     * context.
     *
     * @return all visible fields starting from the receiver.
     * @throws SecurityException
     * 		If member access is not allowed
     * @see #getDeclaredFields
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] getFields() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] cachedFields = [CtInvocationImpl]lookupCachedFields([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]PublicFieldsKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cachedFields != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]cachedFields;
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Field[] fields = [CtInvocationImpl]getFieldsImpl();
        [CtReturnImpl]return [CtInvocationImpl]cacheFields([CtInvocationImpl][CtTypeAccessImpl]sun.reflect.Reflection.filterFields([CtThisAccessImpl]this, [CtVariableReadImpl]fields), [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]PublicFieldsKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Field objects describing
     * all fields which are visible from the current execution
     * context.
     *
     * @return all visible fields starting from the receiver.
     * @see #getDeclaredFields
     */
    private native [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] getFieldsImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array of Class objects which match the interfaces
     * specified in the receiver classes <code>implements</code>
     * declaration
     *
     * @return {@code Class<?>[]}
    the interfaces the receiver claims to implement.
     */
    public [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getInterfaces() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.cachedInterfacesOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.cachedInterfacesOffset = [CtInvocationImpl]getFieldOffset([CtLiteralImpl]"cachedInterfaces");[CtCommentImpl]// $NON-NLS-1$

        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]cachedInterfaces == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]writeFieldValue([CtFieldReadImpl]java.lang.Class.cachedInterfacesOffset, [CtInvocationImpl][CtTypeAccessImpl]J9VMInternals.getInterfaces([CtThisAccessImpl]this));
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] newInterfaces = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]cachedInterfaces.length == [CtLiteralImpl]0) ? [CtFieldReadImpl]cachedInterfaces : [CtInvocationImpl][CtFieldReadImpl]cachedInterfaces.clone();
        [CtReturnImpl]return [CtVariableReadImpl]newInterfaces;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Method object which represents the method
     * described by the arguments.
     *
     * @param name
     * 		String
     * 		the name of the method
     * @param parameterTypes
     * 		{@code Class<?>[]}
     * 		the types of the arguments.
     * @return Method
    the method described by the arguments.
     * @throws NoSuchMethodException
     * 		if the method could not be found.
     * @throws SecurityException
     * 		if member access is not allowed
     * @see #getMethods
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Method getMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException, [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtReturnImpl]return [CtInvocationImpl]getMethodHelper([CtLiteralImpl]true, [CtLiteralImpl]false, [CtLiteralImpl]null, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method throws NoSuchMethodException when throwException is true, otherwise returns null.
     */
    private [CtTypeReferenceImpl]java.lang.reflect.Method throwExceptionOrReturnNull([CtParameterImpl][CtTypeReferenceImpl]boolean throwException, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]throwException) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]newNoSuchMethodException([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method for
     * 	public Method getDeclaredMethod(String name, Class<?>... parameterTypes)
     * 	public Method getMethod(String name, Class<?>... parameterTypes)
     * 	List<Method> getDeclaredPublicMethods(String name, Class<?>... parameterTypes)
     * without going thorough security checking
     *
     * @param throwException
     * 		boolean
     * 		true - throw exception in this helper;
     * 		false - return null instead without throwing NoSuchMethodException
     * @param forDeclaredMethod
     * 		boolean
     * 		true - for getDeclaredMethod(String name, Class<?>... parameterTypes)
     * 		& getDeclaredPublicMethods(String name, Class<?>... parameterTypes);
     * 		false - for getMethod(String name, Class<?>... parameterTypes);
     * @param name
     * 		String					the name of the method
     * @param parameterTypes
     * 		Class<?>[]	the types of the arguments
     * @param methodList
     * 		List<Method>		a list to store the methods described by the arguments
     * 		for getDeclaredPublicMethods()
     * 		or null for getDeclaredMethod() & getMethod()
     * @return Method						the method described by the arguments.
     * @throws NoSuchMethodException		if
     * 		the method could not be found.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    [CtTypeReferenceImpl]java.lang.reflect.Method getMethodHelper([CtParameterImpl][CtTypeReferenceImpl]boolean throwException, [CtParameterImpl][CtTypeReferenceImpl]boolean forDeclaredMethod, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Method> methodList, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method result;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method bestCandidate;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String strSig;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean candidateFromInterface = [CtLiteralImpl]false;
        [CtIfImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameterTypes == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]parameterTypes = [CtFieldReadImpl]java.lang.Class.EmptyParameters;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]methodList == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// getDeclaredPublicMethods() has to go through all methods anyway
            [CtTypeReferenceImpl]java.lang.reflect.Method cachedMethod = [CtInvocationImpl]lookupCachedMethod([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cachedMethod != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]forDeclaredMethod && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]cachedMethod.getDeclaringClass() == [CtThisAccessImpl]this)) || [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtVariableReadImpl]forDeclaredMethod) && [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isPublic([CtInvocationImpl][CtVariableReadImpl]cachedMethod.getModifiers())))) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]cachedMethod;
            }
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtIfImpl][CtCommentImpl]// Handle the no parameter case upfront
        [CtCommentImpl]/* [PR 103441] should throw NullPointerException when name is null */
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]strSig = [CtLiteralImpl]"()";[CtCommentImpl]// $NON-NLS-1$

            [CtAssignmentImpl][CtVariableWriteImpl]parameterTypes = [CtFieldReadImpl]java.lang.Class.EmptyParameters;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]parameterTypes = [CtInvocationImpl][CtVariableReadImpl]parameterTypes.clone();
            [CtAssignmentImpl][CtCommentImpl]// Build a signature for the requested method.
            [CtVariableWriteImpl]strSig = [CtInvocationImpl]getParameterTypesSignature([CtVariableReadImpl]throwException, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes, [CtLiteralImpl]"");[CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]strSig == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }
        [CtIfImpl]if ([CtVariableReadImpl]forDeclaredMethod) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl]getDeclaredMethodImpl([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]strSig, [CtLiteralImpl]null);
        } else [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isInterface()) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]/* if the result is not in the current class, all superinterfaces will need to be searched */
            [CtVariableWriteImpl]result = [CtInvocationImpl]getDeclaredMethodImpl([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]strSig, [CtLiteralImpl]null);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]result) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl]getMostSpecificMethodFromAllInterfacesOfCurrentClass([CtThisAccessImpl]this, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
                [CtAssignmentImpl][CtVariableWriteImpl]candidateFromInterface = [CtLiteralImpl]true;
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl]getMethodImpl([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]strSig);
            [CtIfImpl][CtCommentImpl]/* Retrieve the specified method implemented by the superclass from the top to the bottom. */
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]result != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.getDeclaringClass().isInterface()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo>> infoCache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]16);
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl]getMostSpecificMethodFromAllInterfacesOfAllSuperclasses([CtThisAccessImpl]this, [CtVariableReadImpl]infoCache, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
                [CtAssignmentImpl][CtVariableWriteImpl]candidateFromInterface = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]throwExceptionOrReturnNull([CtVariableReadImpl]throwException, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]0 == [CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]sun.reflect.Reflection.filterMethods([CtThisAccessImpl]this, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Method[]{ [CtVariableReadImpl]result }).length) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]throwExceptionOrReturnNull([CtVariableReadImpl]throwException, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
        }
        [CtIfImpl][CtCommentImpl]/* [PR 127079] Use declaring classloader for Methods */
        [CtCommentImpl]/* [PR CMVC 104523] ensure parameter types are visible in the receiver's class loader */
        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader loader = [CtConditionalImpl]([CtVariableReadImpl]forDeclaredMethod) ? [CtInvocationImpl]getClassLoaderImpl() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.getDeclaringClass().getClassLoaderImpl();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> parameterType = [CtArrayReadImpl][CtVariableReadImpl]parameterTypes[[CtVariableReadImpl]i];
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]parameterType.isPrimitive()) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtInvocationImpl][CtVariableReadImpl]parameterType.getName(), [CtLiteralImpl]false, [CtVariableReadImpl]loader) != [CtVariableReadImpl]parameterType) [CtBlockImpl]{
                            [CtReturnImpl]return [CtInvocationImpl]throwExceptionOrReturnNull([CtVariableReadImpl]throwException, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
                        }
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl]throwExceptionOrReturnNull([CtVariableReadImpl]throwException, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
                    }
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]methodList != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]result.getModifiers() & [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PUBLIC) != [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]methodList.add([CtVariableReadImpl]result);
        }
        [CtAssignmentImpl][CtCommentImpl]/* [PR 113003] The native is called repeatedly until it returns null,
        as each call returns another match if one exists. The first call uses
        getMethodImpl which searches across superclasses and interfaces, but
        since the spec requires that we only weigh multiple matches against
        each other if they are in the same class, on subsequent calls we call
        getDeclaredMethodImpl on the declaring class of the first hit.
        If more than one match is found, more specific method is selected.
        For methods with same signature (name, parameter types) but different return types,
        Method N with return type S is more specific than M with return type R if:
        S is the same as or a subtype of R.
        Otherwise, the result method is chosen arbitrarily from specific methods.
         */
        [CtVariableWriteImpl]bestCandidate = [CtVariableReadImpl]result;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]candidateFromInterface) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass = [CtConditionalImpl]([CtVariableReadImpl]forDeclaredMethod) ? [CtThisAccessImpl]this : [CtInvocationImpl][CtVariableReadImpl]result.getDeclaringClass();
            [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]declaringClass.getDeclaredMethodImpl([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]strSig, [CtVariableReadImpl]result);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean publicMethod = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]result.getModifiers() & [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PUBLIC) != [CtLiteralImpl]0;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]methodList != [CtLiteralImpl]null) && [CtVariableReadImpl]publicMethod) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]methodList.add([CtVariableReadImpl]result);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]forDeclaredMethod || [CtVariableReadImpl]publicMethod) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// bestCandidate and result have same declaringClass.
                    [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> candidateRetType = [CtInvocationImpl][CtVariableReadImpl]bestCandidate.getReturnType();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> resultRetType = [CtInvocationImpl][CtVariableReadImpl]result.getReturnType();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]candidateRetType != [CtVariableReadImpl]resultRetType) && [CtInvocationImpl][CtVariableReadImpl]candidateRetType.isAssignableFrom([CtVariableReadImpl]resultRetType)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]bestCandidate = [CtVariableReadImpl]result;
                    }
                }
            } 
        }
        [CtReturnImpl]return [CtInvocationImpl]cacheMethod([CtVariableReadImpl]bestCandidate);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method searches all interfaces implemented by superclasses from the top to the bottom
     * for the most specific method declared in one of these interfaces.
     *
     * @param currentClass
     * 		the class to be searched, including the current class and all superclasses
     * @param name
     * 		the specified method's name
     * @param parameterTypes
     * 		the types of the arguments of the specified method
     * @return the most specific method selected from all interfaces from each superclass of the current class;
    otherwise, return the method of the first interface from the top superclass
    if the return types of all specified methods are identical.
     */
    private [CtTypeReferenceImpl]java.lang.reflect.Method getMostSpecificMethodFromAllInterfacesOfAllSuperclasses([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> currentClass, [CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo>> infoCache, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method candidateMethod = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]currentClass != [CtFieldReadImpl]java.lang.Object.class) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]/* get to the top superclass first. if all return types end up being the same the interfaces from this superclass have priority. */
            [CtVariableWriteImpl]candidateMethod = [CtInvocationImpl]getMostSpecificMethodFromAllInterfacesOfAllSuperclasses([CtInvocationImpl][CtVariableReadImpl]currentClass.getSuperclass(), [CtVariableReadImpl]infoCache, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
            [CtAssignmentImpl][CtCommentImpl]/* search all interfaces of current class, comparing against result from previous superclass. */
            [CtVariableWriteImpl]candidateMethod = [CtInvocationImpl]getMostSpecificMethodFromAllInterfacesOfCurrentClass([CtVariableReadImpl]currentClass, [CtVariableReadImpl]infoCache, [CtVariableReadImpl]candidateMethod, [CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
        }
        [CtReturnImpl]return [CtVariableReadImpl]candidateMethod;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method searches all interfaces implemented by the current class or interface
     * for the most specific method declared in one of these interfaces.
     *
     * @param currentClass
     * 		the class or interface to be searched
     * @param potentialCandidate
     * 		potential candidate from superclass, null if currentClass is an interface
     * @param name
     * 		the specified method's name
     * @param parameterTypes
     * 		the types of the arguments of the specified method
     * @return the most specific method selected from all interfaces;
    otherwise if return types from all qualifying methods are identical, return an arbitrary method.
     */
    private [CtTypeReferenceImpl]java.lang.reflect.Method getMostSpecificMethodFromAllInterfacesOfCurrentClass([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> currentClass, [CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo>> infoCache, [CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method potentialCandidate, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]java.lang.Class<[CtWildcardReferenceImpl]?>... parameterTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method bestMethod = [CtVariableReadImpl]potentialCandidate;
        [CtIfImpl][CtCommentImpl]/* if infoCache is passed in, reuse from superclass */
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]infoCache) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]infoCache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]16);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> methodCandidates = [CtInvocationImpl]getMethodSet([CtVariableReadImpl]infoCache, [CtLiteralImpl]false, [CtLiteralImpl]true);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo mi : [CtInvocationImpl][CtVariableReadImpl]methodCandidates.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl][CtVariableReadImpl]mi.jlrMethods) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bestMethod = [CtInvocationImpl]java.lang.Class.getMostSpecificInterfaceMethod([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]bestMethod, [CtFieldReadImpl][CtVariableReadImpl]mi.me);
            } else [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method m : [CtFieldReadImpl][CtVariableReadImpl]mi.jlrMethods) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]bestMethod = [CtInvocationImpl]java.lang.Class.getMostSpecificInterfaceMethod([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]bestMethod, [CtVariableReadImpl]m);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]bestMethod;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.reflect.Method getMostSpecificInterfaceMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method bestMethod, [CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method candidateMethod) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]candidateMethod == [CtVariableReadImpl]bestMethod) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]bestMethod;
        }
        [CtIfImpl][CtCommentImpl]/* match name and parameters to user specification */
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]candidateMethod.getDeclaringClass().isInterface()) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]candidateMethod.getName().equals([CtVariableReadImpl]name))) || [CtUnaryOperatorImpl](![CtInvocationImpl]java.lang.Class.doParameterTypesMatch([CtInvocationImpl][CtVariableReadImpl]candidateMethod.getParameterTypes(), [CtVariableReadImpl]parameterTypes))) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]bestMethod;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]bestMethod) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]bestMethod = [CtVariableReadImpl]candidateMethod;
            [CtReturnImpl]return [CtVariableReadImpl]bestMethod;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> bestRetType = [CtInvocationImpl][CtVariableReadImpl]bestMethod.getReturnType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> candidateRetType = [CtInvocationImpl][CtVariableReadImpl]candidateMethod.getReturnType();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bestRetType == [CtVariableReadImpl]candidateRetType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int bestModifiers = [CtInvocationImpl][CtVariableReadImpl]bestMethod.getModifiers();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int candidateModifiers = [CtInvocationImpl][CtVariableReadImpl]candidateMethod.getModifiers();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> bestDeclaringClass = [CtInvocationImpl][CtVariableReadImpl]bestMethod.getDeclaringClass();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> candidateDeclaringClass = [CtInvocationImpl][CtVariableReadImpl]candidateMethod.getDeclaringClass();
            [CtIfImpl][CtCommentImpl]/* if all return types end up being the same, non-static methods take priority over static methods and sub-interfaces take
            priority over superinterface
             */
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isStatic([CtVariableReadImpl]bestModifiers) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isStatic([CtVariableReadImpl]candidateModifiers))) || [CtInvocationImpl]java.lang.Class.methodAOverridesMethodB([CtVariableReadImpl]candidateDeclaringClass, [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isAbstract([CtVariableReadImpl]candidateModifiers), [CtInvocationImpl][CtVariableReadImpl]candidateDeclaringClass.isInterface(), [CtVariableReadImpl]bestDeclaringClass, [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isAbstract([CtVariableReadImpl]bestModifiers), [CtInvocationImpl][CtVariableReadImpl]bestDeclaringClass.isInterface())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bestMethod = [CtVariableReadImpl]candidateMethod;
            }
        } else [CtIfImpl][CtCommentImpl]/* resulting method should have the most specific return type */
        if ([CtInvocationImpl][CtVariableReadImpl]bestRetType.isAssignableFrom([CtVariableReadImpl]candidateRetType)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]bestMethod = [CtVariableReadImpl]candidateMethod;
        }
        [CtReturnImpl]return [CtVariableReadImpl]bestMethod;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean doParameterTypesMatch([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] paramList1, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] paramList2) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]paramList1.length != [CtFieldReadImpl][CtVariableReadImpl]paramList2.length)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]index < [CtFieldReadImpl][CtVariableReadImpl]paramList1.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]index++) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]paramList1[[CtVariableReadImpl]index].equals([CtArrayReadImpl][CtVariableReadImpl]paramList2[[CtVariableReadImpl]index])) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Method object which represents the first method found matching
     * the arguments.
     *
     * @param name
     * 		String
     * 		the name of the method
     * @param parameterTypes
     * 		Class<?>[]
     * 		the types of the arguments.
     * @param partialSignature
     * 		String
     * 		the signature of the method, without return type.
     * @return Object
    the first Method found matching the arguments
     * @see #getMethods
     */
    private native [CtTypeReferenceImpl]java.lang.reflect.Method getMethodImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String partialSignature);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array containing Method objects describing
     * all methods which are visible from the current execution
     * context.
     *
     * @return Method[]
    all visible methods starting from the receiver.
     * @throws SecurityException
     * 		if member access is not allowed
     * @see #getDeclaredMethods
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.reflect.Method[] getMethods() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method[] methods;
        [CtAssignmentImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
        [CtVariableWriteImpl]methods = [CtInvocationImpl]lookupCachedMethods([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]PublicMethodsKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]methods != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]methods;
        }
        [CtIfImpl]if ([CtInvocationImpl]isPrimitive()) [CtBlockImpl]{
            [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Method[[CtLiteralImpl]0];
        }
        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 192714,194493] prepare the class before attempting to access members */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo>> infoCache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]16);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> myMethods = [CtInvocationImpl]getMethodSet([CtVariableReadImpl]infoCache, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.reflect.Method> myMethodList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtLiteralImpl]16);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo mi : [CtInvocationImpl][CtVariableReadImpl]myMethods.values()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]/* don't know how big this will be at the start */
            if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl][CtVariableReadImpl]mi.jlrMethods) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]myMethodList.add([CtFieldReadImpl][CtVariableReadImpl]mi.me);
            } else [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method m : [CtFieldReadImpl][CtVariableReadImpl]mi.jlrMethods) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]myMethodList.add([CtVariableReadImpl]m);
                }
            }
        }
        [CtAssignmentImpl][CtVariableWriteImpl]methods = [CtInvocationImpl][CtVariableReadImpl]myMethodList.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Method[[CtInvocationImpl][CtVariableReadImpl]myMethodList.size()]);
        [CtReturnImpl]return [CtInvocationImpl]cacheMethods([CtInvocationImpl][CtTypeAccessImpl]sun.reflect.Reflection.filterMethods([CtThisAccessImpl]this, [CtVariableReadImpl]methods), [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.[CtFieldReferenceImpl]PublicMethodsKey);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> getMethodSet([CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo>> infoCache, [CtParameterImpl][CtTypeReferenceImpl]boolean virtualOnly, [CtParameterImpl][CtTypeReferenceImpl]boolean localInterfacesOnly) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* virtualOnly must be false only for the bottom class of the hierarchy */
        [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> myMethods = [CtInvocationImpl][CtVariableReadImpl]infoCache.get([CtThisAccessImpl]this);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]myMethods) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]/* haven't visited this class.  Initialize with the methods from the VTable which take priority */
            [CtVariableWriteImpl]myMethods = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]16);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isInterface()) && [CtUnaryOperatorImpl](![CtVariableReadImpl]localInterfacesOnly)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int vCount = [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtTypeReferenceImpl]int sCount = [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method methods[] = [CtLiteralImpl]null;[CtCommentImpl]/* this includes the superclass's virtual and static methods. */

                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> methodFilter = [CtLiteralImpl]null;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean noHotswap = [CtLiteralImpl]true;
                [CtDoImpl]do [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]/* atomically get the list of methods, iterate if a hotswap occurred */
                    [CtVariableWriteImpl]vCount = [CtInvocationImpl]getVirtualMethodCountImpl();[CtCommentImpl]/* returns only public methods */

                    [CtAssignmentImpl][CtVariableWriteImpl]sCount = [CtInvocationImpl]getStaticMethodCountImpl();
                    [CtAssignmentImpl][CtVariableWriteImpl]methods = [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.reflect.Method[]) ([CtFieldReadImpl]java.lang.reflect.Method.class.allocateAndFillArray([CtBinaryOperatorImpl][CtVariableReadImpl]vCount + [CtVariableReadImpl]sCount)));
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]methods) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtLiteralImpl]"Error retrieving class methods");[CtCommentImpl]// $NON-NLS-1$

                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]noHotswap = [CtBinaryOperatorImpl][CtInvocationImpl]getVirtualMethodsImpl([CtVariableReadImpl]methods, [CtLiteralImpl]0, [CtVariableReadImpl]vCount) && [CtInvocationImpl]getStaticMethodsImpl([CtVariableReadImpl]methods, [CtVariableReadImpl]vCount, [CtVariableReadImpl]sCount);
                } while ([CtUnaryOperatorImpl]![CtVariableReadImpl]noHotswap );
                [CtLocalVariableImpl][CtCommentImpl]/* if we are here, this is the target class, so return static and virtual methods */
                [CtTypeReferenceImpl]boolean scanInterfaces = [CtLiteralImpl]false;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method m : [CtVariableReadImpl]methods) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> mDeclaringClass = [CtInvocationImpl][CtVariableReadImpl]m.getDeclaringClass();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo mi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MethodInfo([CtVariableReadImpl]m);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo prevMI = [CtInvocationImpl][CtVariableReadImpl]myMethods.put([CtVariableReadImpl]mi, [CtVariableReadImpl]mi);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]prevMI != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]/* As per Java spec:
                        For methods with same signature (name, parameter types) and return type,
                        only the most specific method should be selected. 
                        Method N is more specific than M if:
                        N is declared by a class and M is declared by an interface; or
                        N and M are both declared by either classes or interfaces and N's
                        declaring type is the same as or a subtype of M's declaring type.
                         */
                        [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> prevMIDeclaringClass = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]prevMI.me.getDeclaringClass();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]mDeclaringClass.isInterface() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]prevMIDeclaringClass.isInterface())) || [CtInvocationImpl][CtVariableReadImpl]mDeclaringClass.isAssignableFrom([CtVariableReadImpl]prevMIDeclaringClass)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]myMethods.put([CtVariableReadImpl]prevMI, [CtVariableReadImpl]prevMI);
                        }
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]mDeclaringClass.isInterface()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]scanInterfaces = [CtLiteralImpl]true;
                        [CtInvocationImpl][CtCommentImpl]/* Add all the interfaces at once to preserve ordering */
                        [CtVariableReadImpl]myMethods.remove([CtVariableReadImpl]mi, [CtVariableReadImpl]mi);
                    }
                }
                [CtIfImpl]if ([CtVariableReadImpl]scanInterfaces) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]/* methodFilter is guaranteed to be non-null at this point */
                    addInterfaceMethods([CtVariableReadImpl]infoCache, [CtVariableReadImpl]methodFilter, [CtVariableReadImpl]myMethods, [CtVariableReadImpl]localInterfacesOnly);
                }
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]localInterfacesOnly) || [CtInvocationImpl]isInterface()) [CtBlockImpl]{
                    [CtForEachImpl][CtCommentImpl]/* this is an interface and doesn't have a vTable, but may have static or private methods */
                    for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method m : [CtInvocationImpl]getDeclaredMethods()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int methodModifiers = [CtInvocationImpl][CtVariableReadImpl]m.getModifiers();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]virtualOnly && [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isStatic([CtVariableReadImpl]methodModifiers)) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isPublic([CtVariableReadImpl]methodModifiers))) [CtBlockImpl]{
                            [CtContinueImpl]continue;
                        }
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo mi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MethodInfo([CtVariableReadImpl]m);
                        [CtInvocationImpl][CtVariableReadImpl]myMethods.put([CtVariableReadImpl]mi, [CtVariableReadImpl]mi);
                    }
                }
                [CtInvocationImpl]addInterfaceMethods([CtVariableReadImpl]infoCache, [CtLiteralImpl]null, [CtVariableReadImpl]myMethods, [CtVariableReadImpl]localInterfacesOnly);
            }
            [CtInvocationImpl][CtVariableReadImpl]infoCache.put([CtThisAccessImpl]this, [CtVariableReadImpl]myMethods);[CtCommentImpl]/* save results for future use */

        }
        [CtReturnImpl]return [CtVariableReadImpl]myMethods;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add methods defined in this class's interfaces or those of superclasses
     *
     * @param infoCache
     * 		Cache of previously visited method lists
     * @param methodFilter
     * 		List of methods to include.  If null, include all
     * @param myMethods
     * 		non-null if you want to update an existing list
     * @return list of methods with their various declarations
     */
    private [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> addInterfaceMethods([CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo>> infoCache, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> methodFilter, [CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> myMethods, [CtParameterImpl][CtTypeReferenceImpl]boolean localInterfacesOnly) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean addToCache = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateList = [CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]myMethods;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]updateList) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]myMethods = [CtInvocationImpl][CtVariableReadImpl]infoCache.get([CtThisAccessImpl]this);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]myMethods) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]/* haven't visited this class */
            [CtVariableWriteImpl]myMethods = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtAssignmentImpl][CtVariableWriteImpl]addToCache = [CtLiteralImpl]true;
            [CtAssignmentImpl][CtVariableWriteImpl]updateList = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtVariableReadImpl]updateList) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class mySuperclass = [CtInvocationImpl]getSuperclass();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isInterface()) && [CtBinaryOperatorImpl]([CtFieldReadImpl]java.lang.Object.class != [CtVariableReadImpl]mySuperclass)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]/* some interface methods are visible via the superclass */
                [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> superclassMethods = [CtInvocationImpl][CtVariableReadImpl]mySuperclass.addInterfaceMethods([CtVariableReadImpl]infoCache, [CtVariableReadImpl]methodFilter, [CtLiteralImpl]null, [CtVariableReadImpl]localInterfacesOnly);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo otherInfo : [CtInvocationImpl][CtVariableReadImpl]superclassMethods.values()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]methodFilter) || [CtInvocationImpl][CtVariableReadImpl]methodFilter.contains([CtVariableReadImpl]otherInfo)) [CtBlockImpl]{
                        [CtInvocationImpl]addMethod([CtVariableReadImpl]myMethods, [CtVariableReadImpl]otherInfo);
                    }
                }
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class intf : [CtInvocationImpl]getInterfaces()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> intfMethods = [CtInvocationImpl][CtVariableReadImpl]intf.getMethodSet([CtVariableReadImpl]infoCache, [CtLiteralImpl]true, [CtVariableReadImpl]localInterfacesOnly);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo otherInfo : [CtInvocationImpl][CtVariableReadImpl]intfMethods.values()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]methodFilter) || [CtInvocationImpl][CtVariableReadImpl]methodFilter.contains([CtVariableReadImpl]otherInfo)) [CtBlockImpl]{
                        [CtInvocationImpl]addMethod([CtVariableReadImpl]myMethods, [CtVariableReadImpl]otherInfo);
                    }
                }
            }
        }
        [CtIfImpl]if ([CtVariableReadImpl]addToCache) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]infoCache.put([CtThisAccessImpl]this, [CtVariableReadImpl]myMethods);
            [CtCommentImpl]/* save results for future use */
        }
        [CtReturnImpl]return [CtVariableReadImpl]myMethods;
    }

    [CtMethodImpl][CtCommentImpl]/* this is called only to add methods from implemented interfaces of a class or superinterfaces of an interface */
    private [CtTypeReferenceImpl]void addMethod([CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo, [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo> myMethods, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo otherMi) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo oldMi = [CtInvocationImpl][CtVariableReadImpl]myMethods.get([CtVariableReadImpl]otherMi);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]oldMi) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]/* haven't seen this method's name & sig */
            [CtVariableWriteImpl]oldMi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MethodInfo([CtVariableReadImpl]otherMi);
            [CtInvocationImpl][CtCommentImpl]/* create a new MethodInfo object and add mi's Method objects to it */
            [CtVariableReadImpl]myMethods.put([CtVariableReadImpl]oldMi, [CtVariableReadImpl]oldMi);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]/* NB: the vTable has an abstract method for each method declared in the implemented interfaces */
            [CtVariableReadImpl]oldMi.update([CtVariableReadImpl]otherMi);[CtCommentImpl]/* add the new method as appropriate */

        }
    }

    [CtMethodImpl]private native [CtTypeReferenceImpl]int getVirtualMethodCountImpl();

    [CtMethodImpl]private native [CtTypeReferenceImpl]boolean getVirtualMethodsImpl([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method[] array, [CtParameterImpl][CtTypeReferenceImpl]int start, [CtParameterImpl][CtTypeReferenceImpl]int count);

    [CtMethodImpl]private native [CtTypeReferenceImpl]int getStaticMethodCountImpl();

    [CtMethodImpl]private native [CtTypeReferenceImpl]boolean getStaticMethodsImpl([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method[] array, [CtParameterImpl][CtTypeReferenceImpl]int start, [CtParameterImpl][CtTypeReferenceImpl]int count);

    [CtMethodImpl]private native [CtArrayTypeReferenceImpl]java.lang.Object[] allocateAndFillArray([CtParameterImpl][CtTypeReferenceImpl]int size);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an integer which which is the receiver's modifiers.
     * Note that the constants which describe the bits which are
     * returned are implemented in class java.lang.reflect.Modifier
     * which may not be available on the target.
     *
     * @return the receiver's modifiers
     */
    public [CtTypeReferenceImpl]int getModifiers() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 89071, 89373] Return SYNTHETIC, ANNOTATION, ENUM modifiers */
        [CtTypeReferenceImpl]int rawModifiers = [CtInvocationImpl]getModifiersImpl();
        [CtIfImpl]if ([CtInvocationImpl]isArray()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]rawModifiers &= [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PUBLIC | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PRIVATE) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PROTECTED) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]ABSTRACT) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]FINAL;
        } else [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]rawModifiers &= [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PUBLIC | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PRIVATE) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]PROTECTED) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]STATIC) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]FINAL) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]INTERFACE) | [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]ABSTRACT) | [CtFieldReadImpl]java.lang.Class.SYNTHETIC) | [CtFieldReadImpl]java.lang.Class.ENUM) | [CtFieldReadImpl]java.lang.Class.ANNOTATION;
        }
        [CtReturnImpl]return [CtVariableReadImpl]rawModifiers;
    }

    [CtMethodImpl]private native [CtTypeReferenceImpl]int getModifiersImpl();

    [CtMethodImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtJavaDocImpl]/**
     * Answers the module to which the receiver belongs.
     * If this class doesn't belong to a named module, the unnamedModule of the classloader
     * loaded this class is returned;
     * If this class represents an array type, the module for the element type is returned;
     * If this class represents a primitive type or void, module java.base is returned.
     *
     * @return the module to which the receiver belongs
     */
    public [CtTypeReferenceImpl]java.lang.Module getModule() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]module;
    }

    [CtMethodImpl][CtCommentImpl]/* [ENDIF] Sidecar19-SE */
    [CtJavaDocImpl]/**
     * Answers the name of the class which the receiver represents.
     * For a description of the format which is used, see the class
     * definition of java.lang.Class.
     *
     * @return the receiver's name.
     * @see java.lang.Class
     */
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 105714] Remove classNameMap (PR 115275) and always use getClassNameStringImpl() */
        [CtTypeReferenceImpl]java.lang.String name = [CtFieldReadImpl]classNameString;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]name;
        }
        [CtAssignmentImpl][CtCommentImpl]// must have been null to set it
        [CtVariableWriteImpl]name = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ibm.oti.vm.VM.getClassNameImpl([CtThisAccessImpl]this).intern();
        [CtAssignmentImpl][CtFieldWriteImpl]classNameString = [CtVariableReadImpl]name;
        [CtReturnImpl]return [CtVariableReadImpl]name;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the ProtectionDomain of the receiver.
     * <p>
     * Note: In order to conserve space in embedded targets, we allow this
     * method to answer null for classes in the system protection domain
     * (i.e. for system classes). System classes are always given full
     * permissions (i.e. AllPermission). This is not changeable via the
     * java.security.Policy.
     *
     * @return ProtectionDomain
    the receiver's ProtectionDomain.
     * @see java.lang.Class
     */
    public [CtTypeReferenceImpl]java.security.ProtectionDomain getProtectionDomain() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]security.checkPermission([CtFieldReadImpl][CtTypeAccessImpl]sun.security.util.SecurityConstants.[CtFieldReferenceImpl]GET_PD_PERMISSION);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.ProtectionDomain result = [CtInvocationImpl]getPDImpl();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result != [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]result;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.AllPermissionsPD == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]allocateAllPermissionsPD();
        }
        [CtReturnImpl]return [CtFieldReadImpl]java.lang.Class.AllPermissionsPD;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void allocateAllPermissionsPD() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.Permissions collection = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.security.Permissions();
        [CtInvocationImpl][CtVariableReadImpl]collection.add([CtFieldReadImpl][CtTypeAccessImpl]sun.security.util.SecurityConstants.[CtFieldReferenceImpl]ALL_PERMISSION);
        [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.AllPermissionsPD = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.security.ProtectionDomain([CtLiteralImpl]null, [CtVariableReadImpl]collection);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the ProtectionDomain of the receiver.
     * <p>
     * This method is for internal use only.
     *
     * @return ProtectionDomain
    the receiver's ProtectionDomain.
     * @see java.lang.Class
     */
    [CtTypeReferenceImpl]java.security.ProtectionDomain getPDImpl() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]/* [PR CMVC 125822] Move RAM class fields onto the heap to fix hotswap crash */
        return [CtFieldReadImpl]protectionDomain;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method to get the name of the package of incoming non-array class.
     * returns an empty string if the class is in an unnamed package.
     *
     * @param clz
     * 		a non-array class.
     * @return the package name of incoming non-array class.
     */
    private static [CtTypeReferenceImpl]java.lang.String getNonArrayClassPackageName([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> clz) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtVariableReadImpl]clz.getName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtInvocationImpl][CtVariableReadImpl]name.lastIndexOf([CtLiteralImpl]'.');
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index >= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]name.substring([CtLiteralImpl]0, [CtVariableReadImpl]index).intern();
        }
        [CtReturnImpl]return [CtLiteralImpl]"";[CtCommentImpl]// $NON-NLS-1$

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the name of the package to which the receiver belongs.
     * For example, Object.class.getPackageName() returns "java.lang".
     * Returns "java.lang" if this class represents a primitive type or void,
     * and the element type's package name in the case of an array type.
     *
     * @return String the receiver's package name
     * @see #getPackage
     */
    [CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtCommentImpl]/* [ENDIF] Sidecar19-SE */
    public [CtTypeReferenceImpl]java.lang.String getPackageName() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
        if ([CtInvocationImpl]isPrimitive()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"java.lang";[CtCommentImpl]// $NON-NLS-1$

        }
        [CtIfImpl]if ([CtInvocationImpl]isArray()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> componentType = [CtInvocationImpl]getComponentType();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]componentType.isArray()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]componentType = [CtInvocationImpl][CtVariableReadImpl]componentType.getComponentType();
            } 
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]componentType.isPrimitive()) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"java.lang";[CtCommentImpl]// $NON-NLS-1$

            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.getNonArrayClassPackageName([CtVariableReadImpl]componentType);
            }
        }
        [CtReturnImpl][CtCommentImpl]/* [ENDIF] Sidecar19-SE */
        return [CtInvocationImpl]java.lang.Class.getNonArrayClassPackageName([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a URL referring to the
     * resource specified by resName. The mapping between
     * the resource name and the URL is managed by the
     * class's class loader.
     *
     * @param resName
     * 		the name of the resource.
     * @return a stream on the resource.
     * @see java.lang.ClassLoader
     */
    [CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtCommentImpl]/* [ENDIF] Sidecar19-SE */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.net.URL getResource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader loader = [CtInvocationImpl][CtThisAccessImpl]this.getClassLoaderImpl();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String absoluteResName = [CtInvocationImpl][CtThisAccessImpl]this.toResourceName([CtVariableReadImpl]resName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL result = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
        [CtTypeReferenceImpl]java.lang.Module thisModule = [CtInvocationImpl]getModule();
        [CtIfImpl]if ([CtInvocationImpl]useModularSearch([CtVariableReadImpl]absoluteResName, [CtVariableReadImpl]thisModule, [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getCallerClass())) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]loader.findResource([CtInvocationImpl][CtVariableReadImpl]thisModule.getName(), [CtVariableReadImpl]absoluteResName);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]IOException e) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }
        [CtIfImpl][CtCommentImpl]/* [ENDIF] Sidecar19-SE */
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]result) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]loader == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getSystemResource([CtVariableReadImpl]absoluteResName);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]loader.getResource([CtVariableReadImpl]absoluteResName);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a read-only stream on the contents of the
     * resource specified by resName. The mapping between
     * the resource name and the stream is managed by the
     * class's class loader.
     *
     * @param resName		the
     * 		name of the resource.
     * @return a stream on the resource.
     * @see java.lang.ClassLoader
     */
    [CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtCommentImpl]/* [ENDIF] Sidecar19-SE */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.io.InputStream getResourceAsStream([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader loader = [CtInvocationImpl][CtThisAccessImpl]this.getClassLoaderImpl();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String absoluteResName = [CtInvocationImpl][CtThisAccessImpl]this.toResourceName([CtVariableReadImpl]resName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream result = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
        [CtTypeReferenceImpl]java.lang.Module thisModule = [CtInvocationImpl]getModule();
        [CtIfImpl]if ([CtInvocationImpl]useModularSearch([CtVariableReadImpl]absoluteResName, [CtVariableReadImpl]thisModule, [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getCallerClass())) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]thisModule.getResourceAsStream([CtVariableReadImpl]absoluteResName);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]IOException e) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }
        [CtIfImpl][CtCommentImpl]/* [ENDIF] Sidecar19-SE */
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]result) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]loader == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getSystemResourceAsStream([CtVariableReadImpl]absoluteResName);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]loader.getResourceAsStream([CtVariableReadImpl]absoluteResName);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtJavaDocImpl]/**
     * Indicate if the package should be looked up in a module or via the class path.
     * Look up the resource in the module if the module is named
     * and is the same module as the caller or the package is open to the caller.
     * The default package (i.e. resources at the root of the module) is considered open.
     *
     * @param absoluteResName
     * 		name of resource, including package
     * @param thisModule
     * 		module of the current class
     * @param callerClass
     * 		class of method calling getResource() or getResourceAsStream()
     * @return true if modular lookup should be used.
     */
    private [CtTypeReferenceImpl]boolean useModularSearch([CtParameterImpl][CtTypeReferenceImpl]java.lang.String absoluteResName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Module thisModule, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> callerClass) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean visible = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]thisModule.isNamed()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Module callerModule = [CtInvocationImpl][CtVariableReadImpl]callerClass.getModule();
            [CtAssignmentImpl][CtVariableWriteImpl]visible = [CtBinaryOperatorImpl][CtVariableReadImpl]thisModule == [CtVariableReadImpl]callerModule;
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]visible) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]visible = [CtInvocationImpl][CtVariableReadImpl]absoluteResName.endsWith([CtLiteralImpl]".class");[CtCommentImpl]// $NON-NLS-1$

                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]visible) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// extract the package name
                    [CtTypeReferenceImpl]int lastSlash = [CtInvocationImpl][CtVariableReadImpl]absoluteResName.lastIndexOf([CtLiteralImpl]'/');
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](-[CtLiteralImpl]1) == [CtVariableReadImpl]lastSlash) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// no package name
                        [CtVariableWriteImpl]visible = [CtLiteralImpl]true;
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String result = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]absoluteResName.substring([CtLiteralImpl]0, [CtVariableReadImpl]lastSlash).replace([CtLiteralImpl]'/', [CtLiteralImpl]'.');
                        [CtAssignmentImpl][CtVariableWriteImpl]visible = [CtInvocationImpl][CtVariableReadImpl]thisModule.isOpen([CtVariableReadImpl]result, [CtVariableReadImpl]callerModule);
                    }
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]visible;
    }

    [CtMethodImpl][CtCommentImpl]/* [ENDIF] Sidecar19-SE */
    [CtJavaDocImpl]/**
     * Answers a String object which represents the class's
     * signature, as described in the class definition of
     * java.lang.Class.
     *
     * @return the signature of the class.
     * @see java.lang.Class
     */
    private [CtTypeReferenceImpl]java.lang.String getSignature() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isArray())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl]getName();
        [CtCommentImpl]// Array classes are named with their signature

        [CtIfImpl]if ([CtInvocationImpl]isPrimitive()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Special cases for each base type.
            if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]void.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"V";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]boolean.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"Z";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]byte.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"B";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]char.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"C";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]short.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"S";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]int.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"I";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]long.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"J";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]float.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"F";
            [CtCommentImpl]// $NON-NLS-1$

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]double.class)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"D";
            [CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtCommentImpl]// General case.
        [CtCommentImpl]// Create a buffer of the correct size
        [CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]getName();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]name.length() + [CtLiteralImpl]2).append([CtLiteralImpl]'L').append([CtVariableReadImpl]name).append([CtLiteralImpl]';').toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the signers for the class represented by the
     * receiver, or null if there are no signers.
     *
     * @return the signers of the receiver.
     * @see #getMethods
     */
    public [CtArrayTypeReferenceImpl]java.lang.Object[] getSigners() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]/* [PR CMVC 93861] allow setSigners() for bootstrap classes */
        return [CtInvocationImpl][CtInvocationImpl]getClassLoaderImpl().getSigners([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the Class which represents the receiver's
     * superclass. For Classes which represent base types,
     * interfaces, and for java.lang.Object the method
     * answers null.
     *
     * @return the receiver's superclass.
     */
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> getSuperclass() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]J9VMInternals.getSuperclass([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers true if the receiver represents an array class.
     *
     * @return <code>true</code>
    if the receiver represents an array class
    <code>false</code>
    if it does not represent an array class
     */
    public native [CtTypeReferenceImpl]boolean isArray();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers true if the type represented by the argument
     * can be converted via an identity conversion or a widening
     * reference conversion (i.e. if either the receiver or the
     * argument represent primitive types, only the identity
     * conversion applies).
     *
     * @return <code>true</code>
    the argument can be assigned into the receiver
    <code>false</code>
    the argument cannot be assigned into the receiver
     * @param cls	Class
     * 		the class to test
     * @throws NullPointerException
     * 		if the parameter is null
     */
    public native [CtTypeReferenceImpl]boolean isAssignableFrom([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cls);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers true if the argument is non-null and can be
     * cast to the type of the receiver. This is the runtime
     * version of the <code>instanceof</code> operator.
     *
     * @return <code>true</code>
    the argument can be cast to the type of the receiver
    <code>false</code>
    the argument is null or cannot be cast to the
    type of the receiver
     * @param object
     * 		Object
     * 		the object to test
     */
    public native [CtTypeReferenceImpl]boolean isInstance([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object object);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers true if the receiver represents an interface.
     *
     * @return <code>true</code>
    if the receiver represents an interface
    <code>false</code>
    if it does not represent an interface
     */
    public [CtTypeReferenceImpl]boolean isInterface() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// This code has been inlined in toGenericString. toGenericString
        [CtCommentImpl]// must be modified to reflect any changes to this implementation.
        return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isArray()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getModifiersImpl() & [CtLiteralImpl]512[CtCommentImpl]/* AccInterface */
        ) != [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers true if the receiver represents a base type.
     *
     * @return <code>true</code>
    if the receiver represents a base type
    <code>false</code>
    if it does not represent a base type
     */
    public native [CtTypeReferenceImpl]boolean isPrimitive();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a new instance of the class represented by the
     * receiver, created by invoking the default (i.e. zero-argument)
     * constructor. If there is no such constructor, or if the
     * creation fails (either because of a lack of available memory or
     * because an exception is thrown by the constructor), an
     * InstantiationException is thrown. If the default constructor
     * exists, but is not accessible from the context where this
     * message is sent, an IllegalAccessException is thrown.
     *
     * @return a new instance of the class represented by the receiver.
     * @throws IllegalAccessException
     * 		if the constructor is not visible to the sender.
     * @throws InstantiationException
     * 		if the instance could not be created.
     */
    [CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtCommentImpl]/* [ENDIF] */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    [CtAnnotationImpl]@java.lang.Deprecated(forRemoval = [CtLiteralImpl]false, since = [CtLiteralImpl]"9")
    public [CtTypeParameterReferenceImpl]T newInstance() throws [CtTypeReferenceImpl]java.lang.IllegalAccessException, [CtTypeReferenceImpl]java.lang.InstantiationException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
            [CtInvocationImpl]checkNonSunProxyMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]PUBLIC);
        }
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtTypeAccessImpl]J9VMInternals.newInstanceImpl([CtThisAccessImpl]this)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used as a prototype for the jit.
     *
     * @param callerClass
     * @return the object
     * @throws InstantiationException
     */
    [CtCommentImpl]/* [PR CMVC 114139]InstantiationException has wrong detail message */
    private [CtTypeReferenceImpl]java.lang.Object newInstancePrototype([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> callerClass) throws [CtTypeReferenceImpl]java.lang.InstantiationException [CtBlockImpl]{
        [CtThrowImpl][CtCommentImpl]/* [PR 96623] */
        throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.InstantiationException([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a string describing a path to the receiver's appropriate
     * package specific subdirectory, with the argument appended if the
     * argument did not begin with a slash. If it did, answer just the
     * argument with the leading slash removed.
     *
     * @return String
    the path to the resource.
     * @param resName	String
     * 		the name of the resource.
     * @see #getResource
     * @see #getResourceAsStream
     */
    private [CtTypeReferenceImpl]java.lang.String toResourceName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resName) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Turn package name into a directory path
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]resName.length() > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]resName.charAt([CtLiteralImpl]0) == [CtLiteralImpl]'/'))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]resName.substring([CtLiteralImpl]1);

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> thisObject = [CtThisAccessImpl]this;
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]thisObject.isArray()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]thisObject = [CtInvocationImpl][CtVariableReadImpl]thisObject.getComponentType();
        } 
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String qualifiedClassName = [CtInvocationImpl][CtVariableReadImpl]thisObject.getName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int classIndex = [CtInvocationImpl][CtVariableReadImpl]qualifiedClassName.lastIndexOf([CtLiteralImpl]'.');
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]classIndex == [CtUnaryOperatorImpl](-[CtLiteralImpl]1))[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]resName;
        [CtCommentImpl]// from a default package

        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]qualifiedClassName.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtVariableReadImpl]classIndex + [CtLiteralImpl]1).replace([CtLiteralImpl]'.', [CtLiteralImpl]'/') + [CtVariableReadImpl]resName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return a printable representation for the receiver.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Note change from 1.1.7 to 1.2: For primitive types,
        [CtCommentImpl]// return just the type name.
        if ([CtInvocationImpl]isPrimitive())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl]getName();

        [CtReturnImpl]return [CtBinaryOperatorImpl][CtConditionalImpl]([CtInvocationImpl]isInterface() ? [CtLiteralImpl]"interface " : [CtLiteralImpl]"class ") + [CtInvocationImpl]getName();[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a formatted string describing this Class. The string has
     * the following format:
     * <i>modifier1 modifier2 ... kind name&lt;typeparam1, typeparam2, ...&gt;</i>.
     * kind is one of <code>class</code>, <code>enum</code>, <code>interface</code>,
     * <code>&#64;interface</code>, or
     * the empty string for primitive types. The type parameter list is
     * omitted if there are no type parameters.
     * /*[IF Sidecar19-SE]
     * For array classes, the string has the following format instead:
     * <i>name&lt;typeparam1, typeparam2, ...&gt;</i> followed by a number of
     * <code>[]</code> pairs, one pair for each dimension of the array.
     * /*[ENDIF]
     *
     * @return a formatted string describing this class
     * @since 1.8
     */
    public [CtTypeReferenceImpl]java.lang.String toGenericString() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isPrimitive())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl]getName();

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int modifiers = [CtInvocationImpl]getModifiers();
        [CtLocalVariableImpl][CtCommentImpl]// Checks for isInterface, isAnnotation and isEnum have been inlined
        [CtCommentImpl]// in order to avoid multiple calls to isArray and getModifiers
        [CtTypeReferenceImpl]boolean isArray = [CtInvocationImpl]isArray();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isInterface = [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]isArray) && [CtBinaryOperatorImpl]([CtLiteralImpl]0 != [CtBinaryOperatorImpl]([CtVariableReadImpl]modifiers & [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]INTERFACE));
        [CtLocalVariableImpl][CtCommentImpl]// Get kind of type before modifying the modifiers
        [CtTypeReferenceImpl]java.lang.String kindOfType;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]isArray) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]modifiers & [CtFieldReadImpl]java.lang.Class.ANNOTATION) != [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]kindOfType = [CtLiteralImpl]"@interface ";[CtCommentImpl]// $NON-NLS-1$

        } else [CtIfImpl]if ([CtVariableReadImpl]isInterface) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]kindOfType = [CtLiteralImpl]"interface ";[CtCommentImpl]// $NON-NLS-1$

        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtVariableReadImpl]isArray) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]modifiers & [CtFieldReadImpl]java.lang.Class.ENUM) != [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtInvocationImpl]getSuperclass() == [CtFieldReadImpl]java.lang.Enum.class)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]kindOfType = [CtLiteralImpl]"enum ";[CtCommentImpl]// $NON-NLS-1$

        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]kindOfType = [CtLiteralImpl]"class ";[CtCommentImpl]// $NON-NLS-1$

        }
        [CtIfImpl][CtCommentImpl]// Remove "interface" from modifiers (is included as kind of type)
        if ([CtVariableReadImpl]isInterface) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]modifiers -= [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.[CtFieldReferenceImpl]INTERFACE;
        }
        [CtIfImpl][CtCommentImpl]// Build generic string
        [CtCommentImpl]/* [IF Sidecar19-SE] */
        if ([CtVariableReadImpl]isArray) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int depth = [CtLiteralImpl]0;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class inner = [CtThisAccessImpl]this;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class component = [CtThisAccessImpl]this;
            [CtDoImpl]do [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]inner = [CtInvocationImpl][CtVariableReadImpl]inner.getComponentType();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inner != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]component = [CtVariableReadImpl]inner;
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]depth += [CtLiteralImpl]1;
                }
            } while ([CtBinaryOperatorImpl][CtVariableReadImpl]inner != [CtLiteralImpl]null );
            [CtInvocationImpl][CtVariableReadImpl]result.append([CtInvocationImpl][CtVariableReadImpl]component.getName());
            [CtInvocationImpl][CtVariableReadImpl]component.appendTypeParameters([CtVariableReadImpl]result);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]depth; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]'[').append([CtLiteralImpl]']');
            }
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.toString();
        }
        [CtInvocationImpl][CtCommentImpl]/* [ENDIF] */
        [CtVariableReadImpl]result.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.toString([CtVariableReadImpl]modifiers));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.length() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]' ');
        }
        [CtInvocationImpl][CtVariableReadImpl]result.append([CtVariableReadImpl]kindOfType);
        [CtInvocationImpl][CtVariableReadImpl]result.append([CtInvocationImpl]getName());
        [CtInvocationImpl]appendTypeParameters([CtVariableReadImpl]result);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.toString();
    }

    [CtMethodImpl][CtCommentImpl]// Add type parameters to stringbuilder if present
    private [CtTypeReferenceImpl]void appendTypeParameters([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder nameBuilder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.TypeVariable<[CtWildcardReferenceImpl]?>[] typeVariables = [CtInvocationImpl]getTypeParameters();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]0 != [CtFieldReadImpl][CtVariableReadImpl]typeVariables.length) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtLiteralImpl]'<');
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean comma = [CtLiteralImpl]false;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.TypeVariable<[CtWildcardReferenceImpl]?> t : [CtVariableReadImpl]typeVariables) [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]comma)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtLiteralImpl]',');

                [CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtVariableReadImpl]t);
                [CtAssignmentImpl][CtVariableWriteImpl]comma = [CtLiteralImpl]true;
                [CtLocalVariableImpl][CtCommentImpl]/* [IF Java12] */
                [CtArrayTypeReferenceImpl]java.lang.reflect.Type[] types = [CtInvocationImpl][CtVariableReadImpl]t.getBounds();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]types.length == [CtLiteralImpl]1) && [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]types[[CtLiteralImpl]0].equals([CtFieldReadImpl]java.lang.Object.class)) [CtBlockImpl]{
                    [CtCommentImpl]// skip in case the only bound is java.lang.Object
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix = [CtLiteralImpl]" extends ";[CtCommentImpl]// $NON-NLS-1$

                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Type type : [CtVariableReadImpl]types) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtVariableReadImpl]prefix).append([CtInvocationImpl][CtVariableReadImpl]type.getTypeName());
                        [CtAssignmentImpl][CtVariableWriteImpl]prefix = [CtLiteralImpl]" & ";[CtCommentImpl]// $NON-NLS-1$

                    }
                }
                [CtCommentImpl]/* [ENDIF] Java12 */
            }
            [CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtLiteralImpl]'>');
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the Package of which this class is a member.
     * A class has a Package iff it was loaded from a SecureClassLoader.
     *
     * @return Package the Package of which this class is a member
    or null in the case of primitive or array types
     */
    public [CtTypeReferenceImpl]java.lang.Package getPackage() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isArray() || [CtInvocationImpl]isPrimitive()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String packageName = [CtInvocationImpl]getPackageName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]packageName) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
            if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.classLoader == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]jdk.internal.loader.BootLoader.getDefinedPackage([CtVariableReadImpl]packageName);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getClassLoaderImpl().getDefinedPackage([CtVariableReadImpl]packageName);
            }
            [CtCommentImpl]/* [ELSE]
            return getClassLoaderImpl().getPackage(packageName);
            /*[ENDIF]
             */
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getPrimitiveClass([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"float"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]float[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"double"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"int"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"long"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]long[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"char"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]char[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"byte"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"boolean"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]boolean[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl][CtCommentImpl]// $NON-NLS-1$
        if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"short"))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtNewArrayImpl]new [CtTypeReferenceImpl]short[[CtLiteralImpl]0].getClass().getComponentType();

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtLiteralImpl]"void")) [CtBlockImpl]{
            [CtTryImpl][CtCommentImpl]// $NON-NLS-1$
            try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method method = [CtInvocationImpl][CtFieldReadImpl]java.lang.Runnable.class.getMethod([CtLiteralImpl]"run", [CtFieldReadImpl]java.lang.Class.EmptyParameters);[CtCommentImpl]// $NON-NLS-1$

                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]method.getReturnType();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.ibm.oti.vm.VM.dumpString([CtLiteralImpl]"Cannot initialize Void.TYPE\n");[CtCommentImpl]// $NON-NLS-1$

            }
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown primitive type: " + [CtVariableReadImpl]name);[CtCommentImpl]// $NON-NLS-1$

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the assertion status for this class.
     * Assertion is enabled/disabled based on
     * classloader default, package or class default at runtime
     *
     * @since 1.4
     * @return the assertion status for this class
     */
    public [CtTypeReferenceImpl]boolean desiredAssertionStatus() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader cldr = [CtInvocationImpl]getClassLoaderImpl();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cldr != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]/* [PR CMVC 80253] package assertion status not checked */
            return [CtInvocationImpl][CtVariableReadImpl]cldr.getClassAssertionStatus([CtInvocationImpl]getName());
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer the class at depth.
     *
     * Notes:
     * 	 1) This method operates on the defining classes of methods on stack.
     * 		NOT the classes of receivers.
     *
     * 	 2) The item at index zero describes the caller of this method.
     *
     * @param depth
     * @return the class at the given depth
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    static native final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getStackClass([CtParameterImpl][CtTypeReferenceImpl]int depth);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Walk the stack and answer an array containing the maxDepth
     * most recent classes on the stack of the calling thread.
     *
     * Starting with the caller of the caller of getStackClasses(), return an
     * array of not more than maxDepth Classes representing the classes of
     * running methods on the stack (including native methods).  Frames
     * representing the VM implementation of java.lang.reflect are not included
     * in the list.  If stopAtPrivileged is true, the walk will terminate at any
     * frame running one of the following methods:
     *
     * <code><ul>
     * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedAction;)Ljava/lang/Object;</li>
     * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;</li>
     * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;</li>
     * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedExceptionAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;</li>
     * </ul></code>
     *
     * If one of the doPrivileged methods is found, the walk terminate and that frame is NOT included in the returned array.
     *
     * Notes: <ul>
     * 	 <li> This method operates on the defining classes of methods on stack.
     * 		NOT the classes of receivers. </li>
     *
     * 	 <li> The item at index zero in the result array describes the caller of
     * 		the caller of this method. </li>
     * </ul>
     *
     * @param maxDepth			maximum
     * 		depth to walk the stack, -1 for the entire stack
     * @param stopAtPrivileged	stop
     * 		at privileged classes
     * @return the array of the most recent classes on the stack
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    static native final [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getStackClasses([CtParameterImpl][CtTypeReferenceImpl]int maxDepth, [CtParameterImpl][CtTypeReferenceImpl]boolean stopAtPrivileged);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called from JVM_ClassDepth.
     * Answers the index in the stack of the first method which
     * is contained in a class called <code>name</code>. If no
     * methods from this class are in the stack, return -1.
     *
     * @param name
     * 		String
     * 		the name of the class to look for.
     * @return int
    the depth in the stack of a the first
    method found.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    static [CtTypeReferenceImpl]int classDepth([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] classes = [CtInvocationImpl]java.lang.Class.getStackClasses([CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]false);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]classes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++)[CtBlockImpl]
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]classes[[CtVariableReadImpl]i].getName().equals([CtVariableReadImpl]name))[CtBlockImpl]
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1;


        [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called from JVM_ClassLoaderDepth.
     * Answers the index in the stack of the first class
     * whose class loader is not a system class loader.
     *
     * @return the frame index of the first method whose class was loaded by a non-system class loader.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    static [CtTypeReferenceImpl]int classLoaderDepth() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Now, check if there are any non-system class loaders in
        [CtCommentImpl]// the stack up to the first privileged method (or the end
        [CtCommentImpl]// of the stack.
        [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] classes = [CtInvocationImpl]java.lang.Class.getStackClasses([CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]true);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]classes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader cl = [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]classes[[CtVariableReadImpl]i].getClassLoaderImpl();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cl.isASystemClassLoader())[CtBlockImpl]
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1;

        }
        [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called from JVM_CurrentClassLoader.
     * Answers the class loader of the first class in the stack
     * whose class loader is not a system class loader.
     *
     * @return the most recent non-system class loader.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    static [CtTypeReferenceImpl]java.lang.ClassLoader currentClassLoader() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Now, check if there are any non-system class loaders in
        [CtCommentImpl]// the stack up to the first privileged method (or the end
        [CtCommentImpl]// of the stack.
        [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] classes = [CtInvocationImpl]java.lang.Class.getStackClasses([CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]true);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]classes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader cl = [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]classes[[CtVariableReadImpl]i].getClassLoaderImpl();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cl.isASystemClassLoader())[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]cl;

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called from JVM_CurrentLoadedClass.
     * Answers the first class in the stack which was loaded
     * by a class loader which is not a system class loader.
     *
     * @return the most recent class loaded by a non-system class loader.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    static [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> currentLoadedClass() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Now, check if there are any non-system class loaders in
        [CtCommentImpl]// the stack up to the first privileged method (or the end
        [CtCommentImpl]// of the stack.
        [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] classes = [CtInvocationImpl]java.lang.Class.getStackClasses([CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]true);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]classes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader cl = [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]classes[[CtVariableReadImpl]i].getClassLoaderImpl();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cl.isASystemClassLoader())[CtBlockImpl]
                [CtReturnImpl]return [CtArrayReadImpl][CtVariableReadImpl]classes[[CtVariableReadImpl]i];

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the specified Annotation for this Class. Inherited Annotations
     * are searched.
     *
     * @param annotation
     * 		the Annotation type
     * @return the specified Annotation or null
     * @since 1.5
     */
    public <[CtTypeParameterImpl]A extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtTypeParameterReferenceImpl]A getAnnotation([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]A> annotation) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> map = [CtFieldReadImpl][CtInvocationImpl]getAnnotationCache().annotationMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]map != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeParameterReferenceImpl]A) ([CtVariableReadImpl]map.get([CtVariableReadImpl]annotation)));
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the directly declared Annotations for this Class, including the Annotations
     * inherited from superclasses.
     * If an annotation type has been included before, then next occurrences will not be included.
     *
     * Repeated annotations are not included since they will be stored in their container annotation.
     * But container annotations are included. (If a container annotation is repeatable and it is repeated,
     * then these container annotations' container annotation is included. )
     *
     * @return an array of Annotation
     * @since 1.5
     */
    public [CtArrayTypeReferenceImpl]java.lang.annotation.Annotation[] getAnnotations() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> map = [CtFieldReadImpl][CtInvocationImpl]getAnnotationCache().annotationMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]map != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.annotation.Annotation> annotations = [CtInvocationImpl][CtVariableReadImpl]map.values();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]annotations.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.annotation.Annotation[[CtInvocationImpl][CtVariableReadImpl]annotations.size()]);
        }
        [CtReturnImpl]return [CtFieldReadImpl]java.lang.Class.EMPTY_ANNOTATION_ARRAY;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Looks through directly declared annotations for this class, not including Annotations inherited from superclasses.
     *
     * @param annotation
     * 		the Annotation to search for
     * @return directly declared annotation of specified annotation type.
     * @since 1.8
     */
    public <[CtTypeParameterImpl]A extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtTypeParameterReferenceImpl]A getDeclaredAnnotation([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]A> annotation) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException();

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> map = [CtFieldReadImpl][CtInvocationImpl]getAnnotationCache().directAnnotationMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]map != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeParameterReferenceImpl]A) ([CtVariableReadImpl]map.get([CtVariableReadImpl]annotation)));
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the annotated types for the implemented interfaces.
     *
     * @return array, possibly empty, of AnnotatedTypes
     */
    public [CtArrayTypeReferenceImpl]java.lang.reflect.AnnotatedType[] getAnnotatedInterfaces() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.ibm.oti.reflect.TypeAnnotationParser.buildAnnotatedInterfaces([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the annotated superclass of this class.
     *
     * @return null if this class is Object, an interface, a primitive type, or an array type.  Otherwise return (possibly empty) AnnotatedType.
     */
    public [CtTypeReferenceImpl]java.lang.reflect.AnnotatedType getAnnotatedSuperclass() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtThisAccessImpl]this.equals([CtFieldReadImpl]java.lang.Object.class) || [CtInvocationImpl][CtThisAccessImpl]this.isInterface()) || [CtInvocationImpl][CtThisAccessImpl]this.isPrimitive()) || [CtInvocationImpl][CtThisAccessImpl]this.isArray()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.ibm.oti.reflect.TypeAnnotationParser.buildAnnotatedSupertype([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the type name of the class which the receiver represents.
     *
     * @return the fully qualified type name, with brackets if an array class
     * @since 1.8
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getTypeName() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isArray()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder nameBuffer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]"[]");[CtCommentImpl]// $NON-NLS-1$

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> componentType = [CtInvocationImpl]getComponentType();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]componentType.isArray()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]nameBuffer.append([CtLiteralImpl]"[]");[CtCommentImpl]// $NON-NLS-1$

                [CtAssignmentImpl][CtVariableWriteImpl]componentType = [CtInvocationImpl][CtVariableReadImpl]componentType.getComponentType();
            } 
            [CtInvocationImpl][CtVariableReadImpl]nameBuffer.insert([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]componentType.getName());
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]nameBuffer.toString();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getName();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the annotations only for this Class, not including Annotations inherited from superclasses.
     * It includes all the directly declared annotations.
     * Repeated annotations are not included but their container annotation does.
     *
     * @return an array of declared annotations
     * @since 1.5
     */
    public [CtArrayTypeReferenceImpl]java.lang.annotation.Annotation[] getDeclaredAnnotations() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> map = [CtFieldReadImpl][CtInvocationImpl]getAnnotationCache().directAnnotationMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]map != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.annotation.Annotation> annotations = [CtInvocationImpl][CtVariableReadImpl]map.values();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]annotations.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.annotation.Annotation[[CtInvocationImpl][CtVariableReadImpl]annotations.size()]);
        }
        [CtReturnImpl]return [CtFieldReadImpl]java.lang.Class.EMPTY_ANNOTATION_ARRAY;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Terms used for annotations :
     * Repeatable Annotation :
     * 		An annotation which can be used more than once for the same class declaration.
     * 		Repeatable annotations are annotated with Repeatable annotation which tells the
     * 		container annotation for this repeatable annotation.
     * 		Example =
     *
     * @interface ContainerAnnotation {RepeatableAnn[] value();}
     * @Repeatable(ContainerAnnotation.class) Container Annotation:
    Container annotation stores the repeated annotations in its array-valued element.
    Using repeatable annotations more than once makes them stored in their container annotation.
    In this case, container annotation is visible directly on class declaration, but not the repeated annotations.
    Repeated Annotation:
    A repeatable annotation which is used more than once for the same class.
    Directly Declared Annotation :
    All non repeatable annotations are directly declared annotations.
    As for repeatable annotations, they can be directly declared annotation if and only if they are used once.
    Repeated annotations are not directly declared in class declaration, but their container annotation does.

    -------------------------------------------------------------------------------------------------------

    Gets the specified type annotations of this class.
    If the specified type is not repeatable annotation, then returned array size will be 0 or 1.
    If specified type is repeatable annotation, then all the annotations of that type will be returned. Array size might be 0, 1 or more.

    It does not search through super classes.
     * @param annotationClass
     * 		the annotation type to search for
     * @return array of declared annotations in the specified annotation type
     * @since 1.8
     */
    public <[CtTypeParameterImpl]A extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtArrayTypeReferenceImpl]A[] getDeclaredAnnotationsByType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]A> annotationClass) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeParameterReferenceImpl]A> annotationsList = [CtInvocationImpl]internalGetDeclaredAnnotationsByType([CtVariableReadImpl]annotationClass);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]annotationsList.toArray([CtInvocationImpl](([CtArrayTypeReferenceImpl]A[]) ([CtTypeAccessImpl]java.lang.reflect.Array.newInstance([CtVariableReadImpl]annotationClass, [CtInvocationImpl][CtVariableReadImpl]annotationsList.size()))));
    }

    [CtMethodImpl]private <[CtTypeParameterImpl]A extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeParameterReferenceImpl]A> internalGetDeclaredAnnotationsByType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]A> annotationClass) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.AnnotationCache currentAnnotationCache = [CtInvocationImpl]getAnnotationCache();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeParameterReferenceImpl]A> annotationsList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> map = [CtFieldReadImpl][CtVariableReadImpl]currentAnnotationCache.directAnnotationMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]map != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.annotation.Repeatable repeatable = [CtInvocationImpl][CtVariableReadImpl]annotationClass.getDeclaredAnnotation([CtFieldReadImpl]java.lang.annotation.Repeatable.class);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]repeatable == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeParameterReferenceImpl]A annotation = [CtInvocationImpl](([CtTypeParameterReferenceImpl]A) ([CtVariableReadImpl]map.get([CtVariableReadImpl]annotationClass)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]annotationsList.add([CtVariableReadImpl]annotation);
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> containerType = [CtInvocationImpl][CtVariableReadImpl]repeatable.value();
                [CtForEachImpl][CtCommentImpl]// if the annotation and its container are both present, the order must be maintained
                for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> entry : [CtInvocationImpl][CtVariableReadImpl]map.entrySet()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> annotationType = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotationType == [CtVariableReadImpl]annotationClass) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]annotationsList.add([CtInvocationImpl](([CtTypeParameterReferenceImpl]A) ([CtVariableReadImpl]entry.getValue())));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotationType == [CtVariableReadImpl]containerType) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]A[] containedAnnotations = [CtInvocationImpl](([CtArrayTypeReferenceImpl]A[]) (getAnnotationsArrayFromValue([CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtVariableReadImpl]containerType, [CtVariableReadImpl]annotationClass)));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]containedAnnotations != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]annotationsList.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]containedAnnotations));
                        }
                    }
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]annotationsList;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the specified type annotations of this class.
     * If the specified type is not repeatable annotation, then returned array size will be 0 or 1.
     * If specified type is repeatable annotation, then all the annotations of that type will be returned. Array size might be 0, 1 or more.
     *
     * It searches through superclasses until it finds the inherited specified annotationClass.
     *
     * @param annotationClass
     * 		the annotation type to search for
     * @return array of declared annotations in the specified annotation type
     * @since 1.8
     */
    public <[CtTypeParameterImpl]A extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> [CtArrayTypeReferenceImpl]A[] getAnnotationsByType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]A> annotationClass) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeParameterReferenceImpl]A> annotationsList = [CtInvocationImpl]internalGetDeclaredAnnotationsByType([CtVariableReadImpl]annotationClass);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]annotationClass.isInheritedAnnotationType()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> sc = [CtThisAccessImpl]this;
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtLiteralImpl]0 == [CtInvocationImpl][CtVariableReadImpl]annotationsList.size()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sc = [CtInvocationImpl][CtVariableReadImpl]sc.getSuperclass();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]sc)[CtBlockImpl]
                    [CtBreakImpl]break;

                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeParameterReferenceImpl]A> superAnnotations = [CtInvocationImpl][CtVariableReadImpl]sc.internalGetDeclaredAnnotationsByType([CtVariableReadImpl]annotationClass);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]superAnnotations != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]annotationsList.addAll([CtVariableReadImpl]superAnnotations);
                }
            } 
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]annotationsList.toArray([CtInvocationImpl](([CtArrayTypeReferenceImpl]A[]) ([CtTypeAccessImpl]java.lang.reflect.Array.newInstance([CtVariableReadImpl]annotationClass, [CtInvocationImpl][CtVariableReadImpl]annotationsList.size()))));
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.Class.AnnotationVars getAnnotationVars() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.AnnotationVars tempAnnotationVars = [CtFieldReadImpl]annotationVars;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tempAnnotationVars == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.annotationVarsOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field annotationVarsField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.class.getDeclaredField([CtLiteralImpl]"annotationVars");[CtCommentImpl]// $NON-NLS-1$

                    [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.annotationVarsOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]annotationVarsField);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]tempAnnotationVars = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.AnnotationVars();
            [CtSynchronizedImpl]synchronized([CtThisAccessImpl]this) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]annotationVars == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
                    [CtCommentImpl]// and flushed to memory before assigning to the annotationVars field.
                    [CtCommentImpl]/* [IF Sidecar19-SE]
                    getUnsafe().putObjectRelease(this, annotationVarsOffset, tempAnnotationVars);
                    /*[ELSE]
                     */
                    [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtThisAccessImpl]this, [CtFieldReadImpl]java.lang.Class.annotationVarsOffset, [CtVariableReadImpl]tempAnnotationVars);
                    [CtCommentImpl]/* [ENDIF] */
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]tempAnnotationVars = [CtFieldReadImpl]annotationVars;
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]tempAnnotationVars;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.invoke.MethodHandle getValueMethod([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> containedType) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Class.AnnotationVars localAnnotationVars = [CtInvocationImpl]getAnnotationVars();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.invoke.MethodHandle valueMethod = [CtFieldReadImpl][CtVariableReadImpl]localAnnotationVars.valueMethod;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]valueMethod == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.invoke.MethodType methodType = [CtInvocationImpl][CtTypeAccessImpl]java.lang.invoke.MethodType.methodType([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Array.newInstance([CtVariableReadImpl]containedType, [CtLiteralImpl]0).getClass());
            [CtAssignmentImpl][CtVariableWriteImpl]valueMethod = [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedAction<[CtTypeReferenceImpl]java.lang.invoke.MethodHandle>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.invoke.MethodHandle run() [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.lang.invoke.MethodHandles.Lookup localImplLookup = [CtFieldReadImpl]java.lang.Class.implLookup;
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localImplLookup == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field privilegedLookupField = [CtInvocationImpl][CtFieldReadImpl]java.lang.invoke.MethodHandles.Lookup.class.getDeclaredField([CtLiteralImpl]"IMPL_LOOKUP");[CtCommentImpl]// $NON-NLS-1$

                            [CtInvocationImpl][CtVariableReadImpl]privilegedLookupField.setAccessible([CtLiteralImpl]true);
                            [CtAssignmentImpl][CtVariableWriteImpl]localImplLookup = [CtInvocationImpl](([CtTypeReferenceImpl][CtTypeReferenceImpl]java.lang.invoke.MethodHandles.Lookup) ([CtVariableReadImpl]privilegedLookupField.get([CtFieldReadImpl]java.lang.invoke.MethodHandles.Lookup.class)));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field implLookupField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.class.getDeclaredField([CtLiteralImpl]"implLookup");[CtCommentImpl]// $NON-NLS-1$

                            [CtLocalVariableImpl][CtTypeReferenceImpl]long implLookupOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().staticFieldOffset([CtVariableReadImpl]implLookupField);
                            [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
                            [CtCommentImpl]// and flushed to memory before assigning to the implLookup field.
                            [CtCommentImpl]/* [IF Sidecar19-SE]
                            getUnsafe().putObjectRelease(Class.class, implLookupOffset, localImplLookup);
                            /*[ELSE]
                             */
                            [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtFieldReadImpl]java.lang.Class.class, [CtVariableReadImpl]implLookupOffset, [CtVariableReadImpl]localImplLookup);
                            [CtCommentImpl]/* [ENDIF] */
                        }
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.invoke.MethodHandle handle = [CtInvocationImpl][CtVariableReadImpl]localImplLookup.findVirtual([CtThisAccessImpl]java.lang.Class.this, [CtLiteralImpl]"value", [CtVariableReadImpl]methodType);[CtCommentImpl]// $NON-NLS-1$

                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.AnnotationVars.[CtFieldReferenceImpl]valueMethodOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field valueMethodField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.AnnotationVars.class.getDeclaredField([CtLiteralImpl]"valueMethod");[CtCommentImpl]// $NON-NLS-1$

                            [CtAssignmentImpl][CtFieldWriteImpl][CtTypeAccessImpl]java.lang.Class.AnnotationVars.[CtFieldReferenceImpl]valueMethodOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]valueMethodField);
                        }
                        [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
                        [CtCommentImpl]// and flushed to memory before assigning to the valueMethod field.
                        [CtCommentImpl]/* [IF Sidecar19-SE]
                        getUnsafe().putObjectRelease(localAnnotationVars, AnnotationVars.valueMethodOffset, handle);
                        /*[ELSE]
                         */
                        [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtVariableReadImpl]localAnnotationVars, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.AnnotationVars.[CtFieldReferenceImpl]valueMethodOffset, [CtVariableReadImpl]handle);
                        [CtReturnImpl][CtCommentImpl]/* [ENDIF] */
                        return [CtVariableReadImpl]handle;
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchMethodException e) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]null;
                    }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                    }
                }
            });
        }
        [CtReturnImpl]return [CtVariableReadImpl]valueMethod;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the array of containedType from the value() method.
     *
     * @param container
     * 		the annotation which is the container of the repeated annotation
     * @param containerType
     * 		the annotationType() of the container. This implements the value() method.
     * @param containedType
     * 		the annotationType() stored in the container
     * @return Annotation array if the given annotation has a value() method which returns an array of the containedType. Otherwise, return null.
     */
    private [CtArrayTypeReferenceImpl]java.lang.annotation.Annotation[] getAnnotationsArrayFromValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.annotation.Annotation container, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> containerType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> containedType) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.invoke.MethodHandle valueMethod = [CtInvocationImpl][CtVariableReadImpl]containerType.getValueMethod([CtVariableReadImpl]containedType);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]valueMethod != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object children = [CtInvocationImpl][CtVariableReadImpl]valueMethod.invoke([CtVariableReadImpl]container);
                [CtIfImpl][CtCommentImpl]/* Check whether value is Annotation array or not */
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]children instanceof [CtTypeAccessImpl][CtArrayTypeReferenceImpl]java.lang.annotation.Annotation[]) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl](([CtArrayTypeReferenceImpl]java.lang.annotation.Annotation[]) (children));
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.Error | [CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]t);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isInheritedAnnotationType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> map = [CtFieldReadImpl][CtInvocationImpl]getAnnotationCache().directAnnotationMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]map != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]map.get([CtFieldReadImpl]java.lang.annotation.Inherited.class) != [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> buildAnnotations([CtParameterImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> directAnnotationsMap) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> superClass = [CtInvocationImpl]getSuperclass();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]superClass == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]directAnnotationsMap;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> superAnnotations = [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]superClass.getAnnotationCache().annotationMap;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> annotationsMap = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]superAnnotations != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> entry : [CtInvocationImpl][CtVariableReadImpl]superAnnotations.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> annotationType = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                [CtIfImpl][CtCommentImpl]// if the annotation is Inherited store the annotation
                if ([CtInvocationImpl][CtVariableReadImpl]annotationType.isInheritedAnnotationType()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotationsMap == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]annotationsMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]superAnnotations.size() + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]directAnnotationsMap != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]directAnnotationsMap.size() : [CtLiteralImpl]0)) * [CtLiteralImpl]4) / [CtLiteralImpl]3);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]annotationsMap.put([CtVariableReadImpl]annotationType, [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotationsMap == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]directAnnotationsMap;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]directAnnotationsMap != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]annotationsMap.putAll([CtVariableReadImpl]directAnnotationsMap);
        }
        [CtReturnImpl]return [CtVariableReadImpl]annotationsMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets all the direct annotations.
     * It does not include repeated annotations for this class, it includes their container annotation(s).
     *
     * @return array of all the direct annotations.
     */
    private [CtTypeReferenceImpl]java.lang.Class.AnnotationCache getAnnotationCache() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.AnnotationCache annotationCacheResult = [CtFieldReadImpl]annotationCache;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotationCacheResult == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] annotationsData = [CtInvocationImpl]getDeclaredAnnotationsData();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotationsData == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]annotationCacheResult = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.AnnotationCache([CtLiteralImpl]null, [CtInvocationImpl]buildAnnotations([CtLiteralImpl]null));
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.annotation.Annotation[] directAnnotations = [CtInvocationImpl][CtTypeAccessImpl]sun.reflect.annotation.AnnotationParser.toArray([CtInvocationImpl][CtTypeAccessImpl]sun.reflect.annotation.AnnotationParser.parseAnnotations([CtVariableReadImpl]annotationsData, [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]Access().getConstantPool([CtThisAccessImpl]this), [CtThisAccessImpl]this));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> directAnnotationsMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]directAnnotations.length * [CtLiteralImpl]4) / [CtLiteralImpl]3);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.annotation.Annotation annotation : [CtVariableReadImpl]directAnnotations) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> annotationType = [CtInvocationImpl][CtVariableReadImpl]annotation.annotationType();
                    [CtInvocationImpl][CtVariableReadImpl]directAnnotationsMap.put([CtVariableReadImpl]annotationType, [CtVariableReadImpl]annotation);
                }
                [CtAssignmentImpl][CtVariableWriteImpl]annotationCacheResult = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.AnnotationCache([CtVariableReadImpl]directAnnotationsMap, [CtInvocationImpl]buildAnnotations([CtVariableReadImpl]directAnnotationsMap));
            }
            [CtLocalVariableImpl][CtCommentImpl]// Don't bother with synchronization. Since it is just a cache, it doesn't matter if it gets overwritten
            [CtCommentImpl]// because multiple threads create the cache at the same time
            [CtTypeReferenceImpl]long localAnnotationCacheOffset = [CtFieldReadImpl]java.lang.Class.annotationCacheOffset;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localAnnotationCacheOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field annotationCacheField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.class.getDeclaredField([CtLiteralImpl]"annotationCache");[CtCommentImpl]// $NON-NLS-1$

                    [CtAssignmentImpl][CtVariableWriteImpl]localAnnotationCacheOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]annotationCacheField);
                    [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.annotationCacheOffset = [CtVariableReadImpl]localAnnotationCacheOffset;
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                }
            }
            [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
            [CtCommentImpl]// and flushed to memory before assigning to the annotationCache field.
            [CtCommentImpl]/* [IF Sidecar19-SE] */
            [CtInvocationImpl]java.lang.Class.getUnsafe().putObjectRelease([CtThisAccessImpl]this, [CtVariableReadImpl]localAnnotationCacheOffset, [CtVariableReadImpl]annotationCacheResult);
            [CtInvocationImpl][CtCommentImpl]/* [ELSE] */
            [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtThisAccessImpl]this, [CtVariableReadImpl]localAnnotationCacheOffset, [CtVariableReadImpl]annotationCacheResult);
            [CtCommentImpl]/* [ENDIF] */
        }
        [CtReturnImpl]return [CtVariableReadImpl]annotationCacheResult;
    }

    [CtMethodImpl]private native [CtArrayTypeReferenceImpl]byte[] getDeclaredAnnotationsData();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer if this class is an Annotation.
     *
     * @return true if this class is an Annotation
     * @since 1.5
     */
    public [CtTypeReferenceImpl]boolean isAnnotation() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// This code has been inlined in toGenericString. toGenericString
        [CtCommentImpl]// must be modified to reflect any changes to this implementation.
        [CtCommentImpl]/* [PR CMVC 89373] Ensure Annotation subclass is not annotation */
        return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isArray()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getModifiersImpl() & [CtFieldReadImpl]java.lang.Class.ANNOTATION) != [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer if the specified Annotation exists for this Class. Inherited
     * Annotations are searched.
     *
     * @param annotation
     * 		the Annotation type
     * @return true if the specified Annotation exists
     * @since 1.5
     */
    public [CtTypeReferenceImpl]boolean isAnnotationPresent([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation> annotation) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException();

        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]getAnnotation([CtVariableReadImpl]annotation) != [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cast this Class to a subclass of the specified Class.
     *
     * @param <U>
     * 		the type for casting to
     * @param cls
     * 		the Class to cast to
     * @return this Class, cast to a subclass of the specified Class
     * @throws ClassCastException
     * 		if this Class is not the same or a subclass
     * 		of the specified Class
     * @since 1.5
     */
    public <[CtTypeParameterImpl]U> [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]U> asSubclass([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]U> cls) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cls.isAssignableFrom([CtThisAccessImpl]this))[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ClassCastException([CtInvocationImpl][CtThisAccessImpl]this.toString());

        [CtReturnImpl]return [CtThisAccessImpl](([CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]U>) (this));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cast the specified object to this Class.
     *
     * @param object
     * 		the object to cast
     * @return the specified object, cast to this Class
     * @throws ClassCastException
     * 		if the specified object cannot be cast
     * 		to this Class
     * @since 1.5
     */
    public [CtTypeParameterReferenceImpl]T cast([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object object) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]/* [MSG "K0336", "Cannot cast {0} to {1}"] */
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]object != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtThisAccessImpl]this.isInstance([CtVariableReadImpl]object)))[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ClassCastException([CtInvocationImpl][CtTypeAccessImpl]com.ibm.oti.util.Msg.getString([CtLiteralImpl]"K0336", [CtInvocationImpl][CtVariableReadImpl]object.getClass(), [CtThisAccessImpl]this));
        [CtCommentImpl]// $NON-NLS-1$

        [CtReturnImpl]return [CtVariableReadImpl](([CtTypeParameterReferenceImpl]T) (object));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer if this Class is an enum.
     *
     * @return true if this Class is an enum
     * @since 1.5
     */
    public [CtTypeReferenceImpl]boolean isEnum() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// This code has been inlined in toGenericString. toGenericString
        [CtCommentImpl]// must be modified to reflect any changes to this implementation.
        [CtCommentImpl]/* [PR CMVC 89071] Ensure class with enum access flag (modifier) !isEnum() */
        return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isArray()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getModifiersImpl() & [CtFieldReadImpl]java.lang.Class.ENUM) != [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtInvocationImpl]getSuperclass() == [CtFieldReadImpl]java.lang.Enum.class);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Class.EnumVars<[CtTypeParameterReferenceImpl]T> getEnumVars() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.EnumVars<[CtTypeParameterReferenceImpl]T> tempEnumVars = [CtFieldReadImpl]enumVars;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tempEnumVars == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long localEnumVarsOffset = [CtFieldReadImpl]java.lang.Class.enumVarsOffset;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localEnumVarsOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field enumVarsField;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]enumVarsField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.class.getDeclaredField([CtLiteralImpl]"enumVars");[CtCommentImpl]// $NON-NLS-1$

                    [CtAssignmentImpl][CtVariableWriteImpl]localEnumVarsOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]enumVarsField);
                    [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.enumVarsOffset = [CtVariableReadImpl]localEnumVarsOffset;
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                }
            }
            [CtAssignmentImpl][CtCommentImpl]// Don't bother with synchronization to determine if the field is already assigned. Since it is just a cache,
            [CtCommentImpl]// it doesn't matter if it gets overwritten because multiple threads create the cache at the same time
            [CtVariableWriteImpl]tempEnumVars = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.EnumVars<>();
            [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
            [CtCommentImpl]// and flushed to memory before assigning to the enumVars field.
            [CtCommentImpl]/* [IF Sidecar19-SE]
            getUnsafe().putObjectRelease(this, localEnumVarsOffset, tempEnumVars);
            /*[ELSE]
             */
            [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtThisAccessImpl]this, [CtVariableReadImpl]localEnumVarsOffset, [CtVariableReadImpl]tempEnumVars);
            [CtCommentImpl]/* [ENDIF] */
        }
        [CtReturnImpl]return [CtVariableReadImpl]tempEnumVars;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Map keyed by enum name, of uncloned and cached enum constants in this class
     */
    [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeParameterReferenceImpl]T> enumConstantDirectory() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.EnumVars<[CtTypeParameterReferenceImpl]T> localEnumVars = [CtInvocationImpl]getEnumVars();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeParameterReferenceImpl]T> map = [CtFieldReadImpl][CtVariableReadImpl]localEnumVars.cachedEnumConstantDirectory;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]map) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 189091] Perf: EnumSet.allOf() is slow */
            [CtArrayTypeReferenceImpl]T[] enums = [CtInvocationImpl]getEnumConstantsShared();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]enums == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]/* [PR CMVC 189257] Class#valueOf throws NPE instead of IllegalArgEx for nonEnum Classes */
                [CtCommentImpl]/* Class#valueOf() is the caller of this method,
                according to the spec it throws IllegalArgumentException if the class is not an Enum.
                 */
                [CtCommentImpl]/* [MSG "K0564", "{0} is not an Enum"] */
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]com.ibm.oti.util.Msg.getString([CtLiteralImpl]"K0564", [CtInvocationImpl]getName()));[CtCommentImpl]// $NON-NLS-1$

            }
            [CtAssignmentImpl][CtVariableWriteImpl]map = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]enums.length * [CtLiteralImpl]4) / [CtLiteralImpl]3);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]enums.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]map.put([CtInvocationImpl][CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.Enum<[CtWildcardReferenceImpl]?>) ([CtVariableReadImpl]enums[[CtVariableReadImpl]i])).name(), [CtArrayReadImpl][CtVariableReadImpl]enums[[CtVariableReadImpl]i]);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.EnumVars.[CtFieldReferenceImpl]enumDirOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field enumDirField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.EnumVars.class.getDeclaredField([CtLiteralImpl]"cachedEnumConstantDirectory");[CtCommentImpl]// $NON-NLS-1$

                    [CtAssignmentImpl][CtFieldWriteImpl][CtTypeAccessImpl]java.lang.Class.EnumVars.[CtFieldReferenceImpl]enumDirOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]enumDirField);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                }
            }
            [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
            [CtCommentImpl]// and flushed to memory before assigning to the cachedEnumConstantDirectory field.
            [CtCommentImpl]/* [IF Sidecar19-SE]
            getUnsafe().putObjectRelease(localEnumVars, EnumVars.enumDirOffset, map);
            /*[ELSE]
             */
            [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtVariableReadImpl]localEnumVars, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.EnumVars.[CtFieldReferenceImpl]enumDirOffset, [CtVariableReadImpl]map);
            [CtCommentImpl]/* [ENDIF] */
        }
        [CtReturnImpl]return [CtVariableReadImpl]map;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer the shared uncloned array of enum constants for this Class. Returns null if
     * this class is not an enum.
     *
     * @return the array of enum constants, or null
     * @since 1.5
     */
    [CtCommentImpl]/* [PR CMVC 189091] Perf: EnumSet.allOf() is slow */
    [CtArrayTypeReferenceImpl]T[] getEnumConstantsShared() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 188840] Perf: Class.getEnumConstants() is slow */
        [CtTypeReferenceImpl]java.lang.Class.EnumVars<[CtTypeParameterReferenceImpl]T> localEnumVars = [CtInvocationImpl]getEnumVars();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]T[] enums = [CtFieldReadImpl][CtVariableReadImpl]localEnumVars.cachedEnumConstants;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]enums) && [CtInvocationImpl]isEnum()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeReferenceImpl]java.lang.reflect.Method> privilegedAction = [CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeReferenceImpl]java.lang.reflect.Method>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.reflect.Method run() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method method = [CtInvocationImpl]getMethod([CtLiteralImpl]"values");[CtCommentImpl]// $NON-NLS-1$

                        [CtInvocationImpl][CtCommentImpl]/* [PR CMVC 83171] caused ClassCastException: <enum class> not an enum] */
                        [CtCommentImpl]// the enum class may not be visible
                        [CtVariableReadImpl]method.setAccessible([CtLiteralImpl]true);
                        [CtReturnImpl]return [CtVariableReadImpl]method;
                    }
                };
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method values = [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtVariableReadImpl]privilegedAction);
                [CtAssignmentImpl][CtVariableWriteImpl]enums = [CtInvocationImpl](([CtArrayTypeReferenceImpl]T[]) ([CtVariableReadImpl]values.invoke([CtThisAccessImpl]this)));
                [CtLocalVariableImpl][CtTypeReferenceImpl]long localEnumConstantsOffset = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.EnumVars.[CtFieldReferenceImpl]enumConstantsOffset;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localEnumConstantsOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field enumConstantsField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.EnumVars.class.getDeclaredField([CtLiteralImpl]"cachedEnumConstants");[CtCommentImpl]// $NON-NLS-1$

                        [CtAssignmentImpl][CtVariableWriteImpl]localEnumConstantsOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]enumConstantsField);
                        [CtAssignmentImpl][CtFieldWriteImpl][CtTypeAccessImpl]java.lang.Class.EnumVars.[CtFieldReferenceImpl]enumConstantsOffset = [CtVariableReadImpl]localEnumConstantsOffset;
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                    }
                }
                [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
                [CtCommentImpl]// and flushed to memory before assigning to the cachedEnumConstants field.
                [CtCommentImpl]/* [IF Sidecar19-SE]
                getUnsafe().putObjectRelease(localEnumVars, localEnumConstantsOffset, enums);
                /*[ELSE]
                 */
                [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtVariableReadImpl]localEnumVars, [CtVariableReadImpl]localEnumConstantsOffset, [CtVariableReadImpl]enums);
                [CtCommentImpl]/* [ENDIF] */
            }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException | [CtTypeReferenceImpl]java.security.PrivilegedActionException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]enums = [CtLiteralImpl]null;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]enums;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer the array of enum constants for this Class. Returns null if
     * this class is not an enum.
     *
     * @return the array of enum constants, or null
     * @since 1.5
     */
    public [CtArrayTypeReferenceImpl]T[] getEnumConstants() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]/* [PR CMVC 188840] Perf: Class.getEnumConstants() is slow */
        [CtCommentImpl]/* [PR CMVC 189091] Perf: EnumSet.allOf() is slow */
        [CtCommentImpl]/* [PR CMVC 192837] JAVA8:JCK: NPE at j.l.Class.getEnumConstants */
        [CtArrayTypeReferenceImpl]T[] enumConstants = [CtInvocationImpl]getEnumConstantsShared();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]enumConstants) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]enumConstants.clone();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer if this Class is synthetic. A synthetic Class is created by
     * the compiler.
     *
     * @return true if this Class is synthetic.
     * @since 1.5
     */
    public [CtTypeReferenceImpl]boolean isSynthetic() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isArray()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getModifiersImpl() & [CtFieldReadImpl]java.lang.Class.SYNTHETIC) != [CtLiteralImpl]0);
    }

    [CtMethodImpl]private native [CtTypeReferenceImpl]java.lang.String getGenericSignature();

    [CtMethodImpl]private [CtTypeReferenceImpl]sun.reflect.generics.factory.CoreReflectionFactory getFactory() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]sun.reflect.generics.factory.CoreReflectionFactory.make([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]sun.reflect.generics.scope.ClassScope.make([CtThisAccessImpl]this));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder getClassRepositoryHolder() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder localClassRepositoryHolder = [CtFieldReadImpl]classRepoHolder;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localClassRepositoryHolder == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtThisAccessImpl]this) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]localClassRepositoryHolder = [CtFieldReadImpl]classRepoHolder;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localClassRepositoryHolder == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String signature = [CtInvocationImpl]getGenericSignature();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]signature == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]localClassRepositoryHolder = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Class.ClassRepositoryHolder.[CtFieldReferenceImpl]NullSingleton;
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]sun.reflect.generics.repository.ClassRepository classRepo = [CtInvocationImpl][CtTypeAccessImpl]sun.reflect.generics.repository.ClassRepository.make([CtVariableReadImpl]signature, [CtInvocationImpl]getFactory());
                        [CtAssignmentImpl][CtVariableWriteImpl]localClassRepositoryHolder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder([CtVariableReadImpl]classRepo);
                    }
                    [CtAssignmentImpl][CtFieldWriteImpl]classRepoHolder = [CtVariableReadImpl]localClassRepositoryHolder;
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]localClassRepositoryHolder;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array of TypeVariable for the generic parameters declared
     * on this Class.
     *
     * @return the TypeVariable[] for the generic parameters
     * @since 1.5
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtArrayTypeReferenceImpl]java.lang.reflect.TypeVariable<[CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T>>[] getTypeParameters() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder holder = [CtInvocationImpl]getClassRepositoryHolder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]sun.reflect.generics.repository.ClassRepository repository = [CtFieldReadImpl][CtVariableReadImpl]holder.classRepository;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]repository == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.TypeVariable[[CtLiteralImpl]0];

        [CtReturnImpl]return [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.reflect.TypeVariable<[CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T>>[]) ([CtVariableReadImpl]repository.getTypeParameters()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers an array of Type for the Class objects which match the
     * interfaces specified in the receiver classes <code>implements</code>
     * declaration.
     *
     * @return Type[]
    the interfaces the receiver claims to implement.
     * @since 1.5
     */
    public [CtArrayTypeReferenceImpl]java.lang.reflect.Type[] getGenericInterfaces() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder holder = [CtInvocationImpl]getClassRepositoryHolder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]sun.reflect.generics.repository.ClassRepository repository = [CtFieldReadImpl][CtVariableReadImpl]holder.classRepository;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]repository == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl]getInterfaces();

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]repository.getSuperInterfaces();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the Type for the Class which represents the receiver's
     * superclass. For classes which represent base types,
     * interfaces, and for java.lang.Object the method
     * answers null.
     *
     * @return the Type for the receiver's superclass.
     * @since 1.5
     */
    public [CtTypeReferenceImpl]java.lang.reflect.Type getGenericSuperclass() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ClassRepositoryHolder holder = [CtInvocationImpl]getClassRepositoryHolder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]sun.reflect.generics.repository.ClassRepository repository = [CtFieldReadImpl][CtVariableReadImpl]holder.classRepository;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]repository == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl]getSuperclass();

        [CtIfImpl]if ([CtInvocationImpl]isInterface())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]repository.getSuperclass();
    }

    [CtMethodImpl]private native [CtTypeReferenceImpl]java.lang.Object getEnclosingObject();

    [CtMethodImpl][CtJavaDocImpl]/**
     * If this Class is defined inside a constructor, return the Constructor.
     *
     * @return the enclosing Constructor or null
     * @throws SecurityException
     * 		if declared member access or package access is not allowed
     * @since 1.5
     * @see #isAnonymousClass()
     * @see #isLocalClass()
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?> getEnclosingConstructor() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?> constructor = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object enclosing = [CtInvocationImpl]getEnclosingObject();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]enclosing instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<?>) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]constructor = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?>) (enclosing));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constructor.getDeclaringClass().checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
            }
            [CtCommentImpl]/* [PR CMVC 201439] To remove CheckPackageAccess call from getEnclosingMethod of J9 */
        }
        [CtReturnImpl]return [CtVariableReadImpl]constructor;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If this Class is defined inside a method, return the Method.
     *
     * @return the enclosing Method or null
     * @throws SecurityException
     * 		if declared member access or package access is not allowed
     * @since 1.5
     * @see #isAnonymousClass()
     * @see #isLocalClass()
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.reflect.Method getEnclosingMethod() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method method = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object enclosing = [CtInvocationImpl]getEnclosingObject();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]enclosing instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.reflect.Method) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]method = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.reflect.Method) (enclosing));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]method.getDeclaringClass().checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.reflect.Member.[CtFieldReferenceImpl]DECLARED);
            }
            [CtCommentImpl]/* [PR CMVC 201439] To remove CheckPackageAccess call from getEnclosingMethod of J9 */
        }
        [CtReturnImpl]return [CtVariableReadImpl]method;
    }

    [CtMethodImpl]private native [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getEnclosingObjectClass();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the enclosing Class of this Class. Unlike getDeclaringClass(),
     * this method works on any nested Class, not just classes nested directly
     * in other classes.
     *
     * @return the enclosing Class or null
     * @throws SecurityException
     * 		if package access is not allowed
     * @since 1.5
     * @see #getDeclaringClass()
     * @see #isAnonymousClass()
     * @see #isLocalClass()
     * @see #isMemberClass()
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getEnclosingClass() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> enclosingClass = [CtInvocationImpl]getDeclaringClass();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]enclosingClass == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.cachedEnclosingClassOffset == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.cachedEnclosingClassOffset = [CtInvocationImpl]getFieldOffset([CtLiteralImpl]"cachedEnclosingClass");[CtCommentImpl]// $NON-NLS-1$

            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]cachedEnclosingClass == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> localEnclosingClass = [CtInvocationImpl]getEnclosingObjectClass();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localEnclosingClass == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]localEnclosingClass = [CtFieldReadImpl]java.lang.Class.ClassReflectNullPlaceHolder.class;
                }
                [CtInvocationImpl]writeFieldValue([CtFieldReadImpl]java.lang.Class.cachedEnclosingClassOffset, [CtVariableReadImpl]localEnclosingClass);
            }
            [CtAssignmentImpl][CtJavaDocImpl]/**
             * ClassReflectNullPlaceHolder.class means the value of cachedEnclosingClass is null
             *
             * @see ClassReflectNullPlaceHolder.class
             */
            [CtVariableWriteImpl]enclosingClass = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]cachedEnclosingClass == [CtFieldReadImpl]java.lang.Class.ClassReflectNullPlaceHolder.class) ? [CtLiteralImpl]null : [CtFieldReadImpl]cachedEnclosingClass;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]enclosingClass != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager security = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]security != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]1);
                [CtInvocationImpl][CtVariableReadImpl]enclosingClass.checkMemberAccess([CtVariableReadImpl]security, [CtVariableReadImpl]callerClassLoader, [CtFieldReadImpl]java.lang.Class.MEMBER_INVALID_TYPE);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]enclosingClass;
    }

    [CtMethodImpl]private native [CtTypeReferenceImpl]java.lang.String getSimpleNameImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the simple name of this Class. The simple name does not include
     * the package or the name of the enclosing class. The simple name of an
     * anonymous class is "".
     *
     * @return the simple name
     * @since 1.5
     * @see #isAnonymousClass()
     */
    public [CtTypeReferenceImpl]java.lang.String getSimpleName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int arrayCount = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> baseType = [CtThisAccessImpl]this;
        [CtIfImpl]if ([CtInvocationImpl]isArray()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]arrayCount = [CtLiteralImpl]1;
            [CtWhileImpl]while ([CtInvocationImpl][CtAssignmentImpl]([CtVariableWriteImpl]baseType = [CtInvocationImpl][CtVariableReadImpl]baseType.getComponentType()).isArray()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]arrayCount++;
            } 
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String simpleName = [CtInvocationImpl][CtVariableReadImpl]baseType.getSimpleNameImpl();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fullName = [CtInvocationImpl][CtVariableReadImpl]baseType.getName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]simpleName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> parent = [CtInvocationImpl][CtVariableReadImpl]baseType.getEnclosingObjectClass();
            [CtIfImpl][CtCommentImpl]// either a base class, or anonymous class
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]parent != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]simpleName = [CtLiteralImpl]"";[CtCommentImpl]// $NON-NLS-1$

            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// remove the package name
                [CtTypeReferenceImpl]int index = [CtInvocationImpl][CtVariableReadImpl]fullName.lastIndexOf([CtLiteralImpl]'.');
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]simpleName = [CtInvocationImpl][CtVariableReadImpl]fullName.substring([CtBinaryOperatorImpl][CtVariableReadImpl]index + [CtLiteralImpl]1);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// no periods in fully qualified name, thus simple name is also the full name
                    [CtVariableWriteImpl]simpleName = [CtVariableReadImpl]fullName;
                }
            }
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]fullName.endsWith([CtVariableReadImpl]simpleName)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> parent = [CtInvocationImpl][CtVariableReadImpl]baseType.getEnclosingObjectClass();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fullName.lastIndexOf([CtLiteralImpl]'.') + [CtLiteralImpl]1;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]parent == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]parent = [CtInvocationImpl]getDeclaringClassImpl();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]parent != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]/* Nested classes have names which consist of the parent class name followed by a '$', followed by
                the simple name. Some nested classes have additional characters between the parent class name
                and the simple name of the nested class.
                 */
                [CtTypeReferenceImpl]java.lang.String parentName = [CtInvocationImpl][CtVariableReadImpl]parent.getName();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fullName.startsWith([CtVariableReadImpl]parentName) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]fullName.charAt([CtInvocationImpl][CtVariableReadImpl]parentName.length()) == [CtLiteralImpl]'$')) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]index = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fullName.lastIndexOf([CtLiteralImpl]'$') + [CtLiteralImpl]1;
                    [CtWhileImpl][CtCommentImpl]// a local class simple name is preceded by a sequence of digits
                    while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]index < [CtInvocationImpl][CtVariableReadImpl]fullName.length()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.isJavaIdentifierStart([CtInvocationImpl][CtVariableReadImpl]fullName.charAt([CtVariableReadImpl]index)))) [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]index++;
                    } 
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]simpleName = [CtInvocationImpl][CtVariableReadImpl]fullName.substring([CtVariableReadImpl]index);
            }
        }
        [CtIfImpl][CtCommentImpl]/* [ENDIF] !Sidecar19-SE */
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]arrayCount > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtVariableReadImpl]simpleName);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]arrayCount; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]"[]");[CtCommentImpl]// $NON-NLS-1$

            }
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.toString();
        }
        [CtReturnImpl]return [CtVariableReadImpl]simpleName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the canonical name of this Class. The canonical name is null
     * for a local or anonymous class. The canonical name includes the package
     * and the name of the enclosing class.
     *
     * @return the canonical name or null
     * @since 1.5
     * @see #isAnonymousClass()
     * @see #isLocalClass()
     */
    public [CtTypeReferenceImpl]java.lang.String getCanonicalName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int arrayCount = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> baseType = [CtThisAccessImpl]this;
        [CtIfImpl]if ([CtInvocationImpl]isArray()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]arrayCount = [CtLiteralImpl]1;
            [CtWhileImpl]while ([CtInvocationImpl][CtAssignmentImpl]([CtVariableWriteImpl]baseType = [CtInvocationImpl][CtVariableReadImpl]baseType.getComponentType()).isArray()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]arrayCount++;
            } 
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]baseType.getEnclosingObjectClass() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// local or anonymous class
            return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String canonicalName;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass = [CtInvocationImpl][CtVariableReadImpl]baseType.getDeclaringClass();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]declaringClass == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]canonicalName = [CtInvocationImpl][CtVariableReadImpl]baseType.getName();
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* [PR 119256] The canonical name of a member class of a local class should be null */
            [CtTypeReferenceImpl]java.lang.String declaringClassCanonicalName = [CtInvocationImpl][CtVariableReadImpl]declaringClass.getCanonicalName();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]declaringClassCanonicalName == [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]null;

            [CtLocalVariableImpl][CtCommentImpl]// remove the enclosingClass from the name, including the $
            [CtTypeReferenceImpl]java.lang.String simpleName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]baseType.getName().substring([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]declaringClass.getName().length() + [CtLiteralImpl]1);
            [CtAssignmentImpl][CtVariableWriteImpl]canonicalName = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]declaringClassCanonicalName + [CtLiteralImpl]'.') + [CtVariableReadImpl]simpleName;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]arrayCount > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtVariableReadImpl]canonicalName);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]arrayCount; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]"[]");[CtCommentImpl]// $NON-NLS-1$

            }
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.toString();
        }
        [CtReturnImpl]return [CtVariableReadImpl]canonicalName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer if this Class is anonymous. An unnamed Class defined
     * inside a method.
     *
     * @return true if this Class is anonymous.
     * @since 1.5
     * @see #isLocalClass()
     */
    public [CtTypeReferenceImpl]boolean isAnonymousClass() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getSimpleNameImpl() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl]getEnclosingObjectClass() != [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer if this Class is local. A named Class defined inside
     * a method.
     *
     * @return true if this Class is local.
     * @since 1.5
     * @see #isAnonymousClass()
     */
    public [CtTypeReferenceImpl]boolean isLocalClass() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getEnclosingObjectClass() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl]getSimpleNameImpl() != [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answer if this Class is a member Class. A Class defined inside another
     * Class.
     *
     * @return true if this Class is local.
     * @since 1.5
     * @see #isLocalClass()
     */
    public [CtTypeReferenceImpl]boolean isMemberClass() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getEnclosingObjectClass() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl]getDeclaringClass() != [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Compute the signature for get*Method()
     *
     * @param throwException
     * 		if NoSuchMethodException is thrown
     * @param name			the
     * 		name of the method
     * @param parameterTypes	the
     * 		types of the arguments
     * @return the signature string
     * @throws NoSuchMethodException
     * 		if one of the parameter types cannot be found in the local class loader
     * @see #getDeclaredMethod
     * @see #getMethod
     */
    private [CtTypeReferenceImpl]java.lang.String getParameterTypesSignature([CtParameterImpl][CtTypeReferenceImpl]boolean throwException, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String returnTypeSignature) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int total = [CtLiteralImpl]2;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] sigs = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> parameterType = [CtArrayReadImpl][CtVariableReadImpl]parameterTypes[[CtVariableReadImpl]i];
            [CtIfImpl][CtCommentImpl]/* [PR 103441] should throw NoSuchMethodException */
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameterType != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]sigs[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]parameterType.getSignature();
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]total += [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]sigs[[CtVariableReadImpl]i].length();
            } else [CtIfImpl]if ([CtVariableReadImpl]throwException) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl]newNoSuchMethodException([CtVariableReadImpl]name, [CtVariableReadImpl]parameterTypes);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]total += [CtInvocationImpl][CtVariableReadImpl]returnTypeSignature.length();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder signature = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtVariableReadImpl]total);
        [CtInvocationImpl][CtVariableReadImpl]signature.append([CtLiteralImpl]'(');
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]parameterTypes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]signature.append([CtArrayReadImpl][CtVariableReadImpl]sigs[[CtVariableReadImpl]i]);

        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]signature.append([CtLiteralImpl]')').append([CtVariableReadImpl]returnTypeSignature);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]signature.toString();
    }

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.reflect.Method copyMethod;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.reflect.Method copyField;

    [CtFieldImpl][CtCommentImpl]/* [PR CMVC 114820, CMVC 115873, CMVC 116166] add reflection cache */
    private static [CtTypeReferenceImpl]java.lang.reflect.Method copyConstructor;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.reflect.Field methodParameterTypesField;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.reflect.Field constructorParameterTypesField;

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]java.lang.Object[] NoArgs = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[[CtLiteralImpl]0];

    [CtMethodImpl][CtCommentImpl]/* [PR JAZZ 107786] constructorParameterTypesField should be initialized regardless of reflectCacheEnabled or not */
    static [CtTypeReferenceImpl]void initCacheIds([CtParameterImpl][CtTypeReferenceImpl]boolean cacheEnabled, [CtParameterImpl][CtTypeReferenceImpl]boolean cacheDebug) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.reflectCacheEnabled = [CtVariableReadImpl]cacheEnabled;
        [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.reflectCacheDebug = [CtVariableReadImpl]cacheDebug;
        [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedAction<[CtTypeReferenceImpl]java.lang.Void>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Void run() [CtBlockImpl]{
                [CtInvocationImpl]java.lang.Class.doInitCacheIds();
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        });
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]void setReflectCacheAppOnly([CtParameterImpl][CtTypeReferenceImpl]boolean cacheAppOnly) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.reflectCacheAppOnly = [CtVariableReadImpl]cacheAppOnly;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"nls")
    static [CtTypeReferenceImpl]void doInitCacheIds() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]/* We cannot just call getDeclaredField() because that method includes a call
        to Reflection.filterFields() which will remove the fields needed here.
        The remaining required behavior of getDeclaredField() is inlined here.
        The security checks are omitted (they would be redundant). Caching is
        not done (we're in the process of initializing the caching mechanisms).
        We must ensure the classes that own the fields of interest are prepared.
         */
        [CtTypeAccessImpl]J9VMInternals.prepare([CtFieldReadImpl]java.lang.reflect.Constructor.class);
        [CtInvocationImpl][CtTypeAccessImpl]J9VMInternals.prepare([CtFieldReadImpl]java.lang.reflect.Method.class);
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.constructorParameterTypesField = [CtInvocationImpl][CtFieldReadImpl]java.lang.reflect.Constructor.class.getDeclaredFieldImpl([CtLiteralImpl]"parameterTypes");
            [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.methodParameterTypesField = [CtInvocationImpl][CtFieldReadImpl]java.lang.reflect.Method.class.getDeclaredFieldImpl([CtLiteralImpl]"parameterTypes");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.constructorParameterTypesField.setAccessible([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.methodParameterTypesField.setAccessible([CtLiteralImpl]true);
        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheEnabled) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.copyConstructor = [CtInvocationImpl]java.lang.Class.getAccessibleMethod([CtFieldReadImpl]java.lang.reflect.Constructor.class, [CtLiteralImpl]"copy");
            [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.copyMethod = [CtInvocationImpl]java.lang.Class.getAccessibleMethod([CtFieldReadImpl]java.lang.reflect.Method.class, [CtLiteralImpl]"copy");
            [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.copyField = [CtInvocationImpl]java.lang.Class.getAccessibleMethod([CtFieldReadImpl]java.lang.reflect.Field.class, [CtLiteralImpl]"copy");
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.reflect.Method getAccessibleMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cls, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method method = [CtInvocationImpl][CtVariableReadImpl]cls.getDeclaredMethod([CtVariableReadImpl]name, [CtFieldReadImpl]java.lang.Class.EmptyParameters);
            [CtInvocationImpl][CtVariableReadImpl]method.setAccessible([CtLiteralImpl]true);
            [CtReturnImpl]return [CtVariableReadImpl]method;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchMethodException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtClassImpl][CtCommentImpl]/* [PR RTC 104994 redesign getMethods] */
    [CtJavaDocImpl]/**
     * represents all methods of a given name and signature visible from a given class or interface.
     */
    private class MethodInfo {
        [CtFieldImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.reflect.Method> jlrMethods;

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.reflect.Method me;

        [CtFieldImpl]private final [CtTypeReferenceImpl]int myHash;

        [CtFieldImpl]private [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] paramTypes;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> returnType;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String methodName;

        [CtConstructorImpl]public MethodInfo([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method myMethod) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]me = [CtVariableReadImpl]myMethod;
            [CtAssignmentImpl][CtFieldWriteImpl]methodName = [CtInvocationImpl][CtVariableReadImpl]myMethod.getName();
            [CtAssignmentImpl][CtFieldWriteImpl]myHash = [CtInvocationImpl][CtFieldReadImpl]methodName.hashCode();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.paramTypes = [CtLiteralImpl]null;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.returnType = [CtLiteralImpl]null;
            [CtAssignmentImpl][CtFieldWriteImpl]jlrMethods = [CtLiteralImpl]null;
        }

        [CtConstructorImpl]public MethodInfo([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo otherMi) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.me = [CtFieldReadImpl][CtVariableReadImpl]otherMi.me;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.methodName = [CtFieldReadImpl][CtVariableReadImpl]otherMi.methodName;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.paramTypes = [CtFieldReadImpl][CtVariableReadImpl]otherMi.paramTypes;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.returnType = [CtFieldReadImpl][CtVariableReadImpl]otherMi.returnType;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.myHash = [CtFieldReadImpl][CtVariableReadImpl]otherMi.myHash;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]otherMi.jlrMethods) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]jlrMethods = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.reflect.Method>) ([CtFieldReadImpl][CtVariableReadImpl]otherMi.jlrMethods.clone()));
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]jlrMethods = [CtLiteralImpl]null;
            }
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void initializeTypes() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]paramTypes = [CtInvocationImpl]java.lang.Class.getParameterTypes([CtFieldReadImpl]me);
            [CtAssignmentImpl][CtFieldWriteImpl]returnType = [CtInvocationImpl][CtFieldReadImpl]me.getReturnType();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * (non-Javadoc)
         *
         * @param that
         * 		another MethodInfo object
         * @return true if the methods have the same name and signature
         * @note does not compare the defining class, permissions, exceptions, etc.
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object that) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]that) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]that.getClass().equals([CtInvocationImpl][CtThisAccessImpl]this.getClass())) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            [CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo otherMethod = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo) (that));
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]methodName.equals([CtFieldReadImpl][CtVariableReadImpl]otherMethod.methodName)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl]returnType) [CtBlockImpl]{
                [CtInvocationImpl]initializeTypes();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl][CtVariableReadImpl]otherMethod.returnType) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]otherMethod.initializeTypes();
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]returnType.equals([CtFieldReadImpl][CtVariableReadImpl]otherMethod.returnType)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] m1Parms = [CtFieldReadImpl]paramTypes;
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] m2Parms = [CtFieldReadImpl][CtVariableReadImpl]otherMethod.paramTypes;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]m1Parms.length != [CtFieldReadImpl][CtVariableReadImpl]m2Parms.length) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]m1Parms.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]m1Parms[[CtVariableReadImpl]i] != [CtArrayReadImpl][CtVariableReadImpl]m2Parms[[CtVariableReadImpl]i]) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Add a method to the list.  newMethod may be discarded if it is masked by an incumbent method in the list.
         * Also, an incumbent method may be removed if newMethod masks it.
         * In general, a target class inherits a method from its direct superclass or directly implemented interfaces unless:
         * 	- the method is static or private and the declaring class is not the target class
         * 	- the target class declares the method (concrete or abstract)
         * 	- the method is default and a superclass of the target class contains a concrete implementation of the method
         * 	- a more specific implemented interface contains a concrete implementation
         *
         * @param newMethod
         * 		method to be added.
         */
        [CtTypeReferenceImpl]void update([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method newMethod) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int newModifiers = [CtInvocationImpl][CtVariableReadImpl]newMethod.getModifiers();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isPublic([CtVariableReadImpl]newModifiers)) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]/* can't see the method */
                return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> newMethodClass = [CtInvocationImpl][CtVariableReadImpl]newMethod.getDeclaringClass();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean newMethodIsAbstract = [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isAbstract([CtVariableReadImpl]newModifiers);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean newMethodClassIsInterface = [CtInvocationImpl][CtVariableReadImpl]newMethodClass.isInterface();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl]jlrMethods) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]/* handle the common case of a single declaration */
                if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]newMethod.equals([CtFieldReadImpl]me)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> incumbentMethodClass = [CtInvocationImpl][CtFieldReadImpl]me.getDeclaringClass();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]java.lang.Class.this != [CtVariableReadImpl]incumbentMethodClass) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean incumbentIsAbstract = [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isAbstract([CtInvocationImpl][CtFieldReadImpl]me.getModifiers());
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean incumbentClassIsInterface = [CtInvocationImpl][CtVariableReadImpl]incumbentMethodClass.isInterface();
                        [CtIfImpl]if ([CtInvocationImpl]java.lang.Class.methodAOverridesMethodB([CtVariableReadImpl]newMethodClass, [CtVariableReadImpl]newMethodIsAbstract, [CtVariableReadImpl]newMethodClassIsInterface, [CtVariableReadImpl]incumbentMethodClass, [CtVariableReadImpl]incumbentIsAbstract, [CtVariableReadImpl]incumbentClassIsInterface)) [CtBlockImpl]{
                            [CtAssignmentImpl][CtFieldWriteImpl]me = [CtVariableReadImpl]newMethod;
                        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]java.lang.Class.methodAOverridesMethodB([CtVariableReadImpl]incumbentMethodClass, [CtVariableReadImpl]incumbentIsAbstract, [CtVariableReadImpl]incumbentClassIsInterface, [CtVariableReadImpl]newMethodClass, [CtVariableReadImpl]newMethodIsAbstract, [CtVariableReadImpl]newMethodClassIsInterface)) [CtBlockImpl]{
                            [CtAssignmentImpl][CtCommentImpl]/* we need to store both */
                            [CtFieldWriteImpl]jlrMethods = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtLiteralImpl]2);
                            [CtInvocationImpl][CtFieldReadImpl]jlrMethods.add([CtFieldReadImpl]me);
                            [CtInvocationImpl][CtFieldReadImpl]jlrMethods.add([CtVariableReadImpl]newMethod);
                        }
                    }
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int methodCursor = [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean addMethod = [CtLiteralImpl]true;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean replacedMethod = [CtLiteralImpl]false;
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]methodCursor < [CtInvocationImpl][CtFieldReadImpl]jlrMethods.size()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int increment = [CtLiteralImpl]1;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method m = [CtInvocationImpl][CtFieldReadImpl]jlrMethods.get([CtVariableReadImpl]methodCursor);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]newMethod.equals([CtVariableReadImpl]m)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]/* already have this method */
                        [CtVariableWriteImpl]addMethod = [CtLiteralImpl]false;
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> incumbentMethodClass = [CtInvocationImpl][CtVariableReadImpl]m.getDeclaringClass();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]java.lang.Class.this == [CtVariableReadImpl]incumbentMethodClass) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]addMethod = [CtLiteralImpl]false;
                        } else [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean incumbentIsAbstract = [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isAbstract([CtInvocationImpl][CtVariableReadImpl]m.getModifiers());
                            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean incumbentClassIsInterface = [CtInvocationImpl][CtVariableReadImpl]incumbentMethodClass.isInterface();
                            [CtIfImpl]if ([CtInvocationImpl]java.lang.Class.methodAOverridesMethodB([CtVariableReadImpl]newMethodClass, [CtVariableReadImpl]newMethodIsAbstract, [CtVariableReadImpl]newMethodClassIsInterface, [CtVariableReadImpl]incumbentMethodClass, [CtVariableReadImpl]incumbentIsAbstract, [CtVariableReadImpl]incumbentClassIsInterface)) [CtBlockImpl]{
                                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]replacedMethod) [CtBlockImpl]{
                                    [CtInvocationImpl][CtCommentImpl]/* preserve ordering by removing old and appending new instead of directly replacing. */
                                    [CtFieldReadImpl]jlrMethods.remove([CtVariableReadImpl]methodCursor);
                                    [CtInvocationImpl][CtFieldReadImpl]jlrMethods.add([CtVariableReadImpl]newMethod);
                                    [CtAssignmentImpl][CtVariableWriteImpl]increment = [CtLiteralImpl]0;
                                    [CtAssignmentImpl][CtVariableWriteImpl]replacedMethod = [CtLiteralImpl]true;
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]jlrMethods.remove([CtVariableReadImpl]methodCursor);
                                    [CtAssignmentImpl][CtVariableWriteImpl]increment = [CtLiteralImpl]0;
                                    [CtCommentImpl]/* everything slid over one slot */
                                }
                                [CtAssignmentImpl][CtVariableWriteImpl]addMethod = [CtLiteralImpl]false;
                            } else [CtIfImpl]if ([CtInvocationImpl]java.lang.Class.methodAOverridesMethodB([CtVariableReadImpl]incumbentMethodClass, [CtVariableReadImpl]incumbentIsAbstract, [CtVariableReadImpl]incumbentClassIsInterface, [CtVariableReadImpl]newMethodClass, [CtVariableReadImpl]newMethodIsAbstract, [CtVariableReadImpl]newMethodClassIsInterface)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]addMethod = [CtLiteralImpl]false;
                            }
                        }
                    }
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]methodCursor += [CtVariableReadImpl]increment;
                } 
                [CtIfImpl]if ([CtVariableReadImpl]addMethod) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]jlrMethods.add([CtVariableReadImpl]newMethod);
                }
            }
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void update([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<T>.MethodInfo otherMi) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl][CtVariableReadImpl]otherMi.jlrMethods) [CtBlockImpl]{
                [CtInvocationImpl]update([CtFieldReadImpl][CtVariableReadImpl]otherMi.me);
            } else[CtBlockImpl]
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method m : [CtFieldReadImpl][CtVariableReadImpl]otherMi.jlrMethods) [CtBlockImpl]{
                    [CtInvocationImpl]update([CtVariableReadImpl]m);
                }

        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]myHash;
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean methodAOverridesMethodB([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> methodAClass, [CtParameterImpl][CtTypeReferenceImpl]boolean methodAIsAbstract, [CtParameterImpl][CtTypeReferenceImpl]boolean methodAClassIsInterface, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> methodBClass, [CtParameterImpl][CtTypeReferenceImpl]boolean methodBIsAbstract, [CtParameterImpl][CtTypeReferenceImpl]boolean methodBClassIsInterface) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]methodBIsAbstract && [CtVariableReadImpl]methodBClassIsInterface) && [CtUnaryOperatorImpl](![CtVariableReadImpl]methodAIsAbstract)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]methodAClassIsInterface)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]methodBClass.isAssignableFrom([CtVariableReadImpl]methodAClass) && [CtBinaryOperatorImpl][CtCommentImpl]/* [IF !Sidecar19-SE] */
        [CtCommentImpl]/* In Java 8, abstract methods in subinterfaces do not hide abstract methods in superinterfaces.
        This is fixed in Java 9.
         */
        ([CtUnaryOperatorImpl](![CtVariableReadImpl]methodAClassIsInterface) || [CtUnaryOperatorImpl](![CtVariableReadImpl]methodAIsAbstract)));
    }

    [CtClassImpl][CtCommentImpl]/* [PR 125873] Improve reflection cache */
    private static final class ReflectRef extends [CtTypeReferenceImpl]java.lang.ref.SoftReference<[CtTypeReferenceImpl]java.lang.Object> implements [CtTypeReferenceImpl]java.lang.Runnable {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.ref.ReferenceQueue<[CtTypeReferenceImpl]java.lang.Object> queue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ref.ReferenceQueue<>();

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Class.ReflectCache cache;

        [CtFieldImpl]final [CtTypeReferenceImpl]java.lang.Class.CacheKey key;

        [CtConstructorImpl]ReflectRef([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]value, [CtFieldReadImpl]java.lang.Class.ReflectRef.queue);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cache = [CtVariableReadImpl]cache;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.key = [CtVariableReadImpl]key;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]cache.handleCleared([CtThisAccessImpl]this);
        }
    }

    [CtClassImpl][CtCommentImpl]/* [IF] */
    [CtCommentImpl]/* Keys for constructors, fields and methods are all mutually distinct so we can
    distinguish them in a single map. The key for a field has parameterTypes == null
    while parameterTypes can't be null for constructors or methods. The key for a
    constructor has an empty name which is not legal in a class file (for any feature).
    The Public* and Declared* keys have names that can't collide with any other normal
    key (derived from a legal class).
     */
    [CtCommentImpl]/* [ENDIF] */
    private static final class CacheKey {
        [CtFieldImpl][CtCommentImpl]/* [PR CMVC 163440] java.lang.Class$CacheKey.PRIME should be static */
        private static final [CtTypeReferenceImpl]int PRIME = [CtLiteralImpl]31;

        [CtMethodImpl]private static [CtTypeReferenceImpl]int hashCombine([CtParameterImpl][CtTypeReferenceImpl]int partial, [CtParameterImpl][CtTypeReferenceImpl]int itemHash) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]partial * [CtFieldReadImpl]java.lang.Class.CacheKey.PRIME) + [CtVariableReadImpl]itemHash;
        }

        [CtMethodImpl]private static [CtTypeReferenceImpl]int hashCombine([CtParameterImpl][CtTypeReferenceImpl]int partial, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object item) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.CacheKey.hashCombine([CtVariableReadImpl]partial, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]item == [CtLiteralImpl]null ? [CtLiteralImpl]0 : [CtInvocationImpl][CtVariableReadImpl]item.hashCode());
        }

        [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.Class.CacheKey newConstructorKey([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtLiteralImpl]"", [CtVariableReadImpl]parameterTypes, [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$

        }

        [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.Class.CacheKey newFieldKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtVariableReadImpl]fieldName, [CtLiteralImpl]null, [CtVariableReadImpl]type);
        }

        [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.Class.CacheKey newMethodKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String methodName, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> returnType) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtVariableReadImpl]methodName, [CtVariableReadImpl]parameterTypes, [CtVariableReadImpl]returnType);
        }

        [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.Class.CacheKey newDeclaredPublicMethodsKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String methodName, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtBinaryOperatorImpl][CtLiteralImpl]"#m" + [CtVariableReadImpl]methodName, [CtVariableReadImpl]parameterTypes, [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$

        }

        [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Class.CacheKey PublicConstructorsKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtLiteralImpl]"/c", [CtFieldReadImpl]java.lang.Class.EmptyParameters, [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$


        [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Class.CacheKey PublicFieldsKey = [CtInvocationImpl]java.lang.Class.CacheKey.newFieldKey([CtLiteralImpl]"/f", [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$


        [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Class.CacheKey PublicMethodsKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtLiteralImpl]"/m", [CtFieldReadImpl]java.lang.Class.EmptyParameters, [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$


        [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Class.CacheKey DeclaredConstructorsKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtLiteralImpl]".c", [CtFieldReadImpl]java.lang.Class.EmptyParameters, [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$


        [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Class.CacheKey DeclaredFieldsKey = [CtInvocationImpl]java.lang.Class.CacheKey.newFieldKey([CtLiteralImpl]".f", [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$


        [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Class.CacheKey DeclaredMethodsKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.CacheKey([CtLiteralImpl]".m", [CtFieldReadImpl]java.lang.Class.EmptyParameters, [CtLiteralImpl]null);[CtCommentImpl]// $NON-NLS-1$


        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String name;

        [CtFieldImpl]private final [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> returnType;

        [CtFieldImpl]private final [CtTypeReferenceImpl]int hashCode;

        [CtConstructorImpl]private CacheKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> returnType) [CtBlockImpl]{
            [CtInvocationImpl]super();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int hash = [CtInvocationImpl]java.lang.Class.CacheKey.hashCombine([CtInvocationImpl][CtVariableReadImpl]name.hashCode(), [CtVariableReadImpl]returnType);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameterTypes != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> parameterType : [CtVariableReadImpl]parameterTypes) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]hash = [CtInvocationImpl]java.lang.Class.CacheKey.hashCombine([CtVariableReadImpl]hash, [CtVariableReadImpl]parameterType);
                }
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parameterTypes = [CtVariableReadImpl]parameterTypes;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.returnType = [CtVariableReadImpl]returnType;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hashCode = [CtVariableReadImpl]hash;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object obj) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]obj) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey that = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Class.CacheKey) (obj));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtThisAccessImpl]this.returnType == [CtFieldReadImpl][CtVariableReadImpl]that.returnType) && [CtInvocationImpl]java.lang.Class.sameTypes([CtFieldReadImpl][CtThisAccessImpl]this.parameterTypes, [CtFieldReadImpl][CtVariableReadImpl]that.parameterTypes)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.name.equals([CtFieldReadImpl][CtVariableReadImpl]that.name);
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]hashCode;
        }
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getParameterTypes([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?> constructor) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]java.lang.Class.constructorParameterTypesField) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[]) ([CtFieldReadImpl]java.lang.Class.constructorParameterTypesField.get([CtVariableReadImpl]constructor)));
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]constructor.getParameterTypes();
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]static [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getParameterTypes([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method method) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]java.lang.Class.methodParameterTypesField) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[]) ([CtFieldReadImpl]java.lang.Class.methodParameterTypesField.get([CtVariableReadImpl]method)));
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]method.getParameterTypes();
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtClassImpl][CtCommentImpl]/* [PR 125873] Improve reflection cache */
    private static final class ReflectCache extends [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.Class.CacheKey, [CtTypeReferenceImpl]java.lang.Class.ReflectRef> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]6551549321039776630L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> owner;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger useCount;

        [CtConstructorImpl]ReflectCache([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> owner) [CtBlockImpl]{
            [CtInvocationImpl]super();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.owner = [CtVariableReadImpl]owner;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.useCount = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger();
        }

        [CtMethodImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache acquire() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]useCount.incrementAndGet();
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtTypeReferenceImpl]void handleCleared([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.ReflectRef ref) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean removed = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]remove([CtFieldReadImpl][CtVariableReadImpl]ref.key, [CtVariableReadImpl]ref) && [CtInvocationImpl]isEmpty()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]useCount.get() == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]owner.setReflectCache([CtLiteralImpl]null);
                    [CtAssignmentImpl][CtVariableWriteImpl]removed = [CtLiteralImpl]true;
                }
            }
            [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]removed) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]err.println([CtBinaryOperatorImpl][CtLiteralImpl]"Removed reflect cache for: " + [CtThisAccessImpl]this);[CtCommentImpl]// $NON-NLS-1$

                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]err.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Retained reflect cache for: " + [CtThisAccessImpl]this) + [CtLiteralImpl]", size: ") + [CtInvocationImpl]size());[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

                }
            }
        }

        [CtMethodImpl][CtTypeReferenceImpl]java.lang.Object find([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectRef ref = [CtInvocationImpl]get([CtVariableReadImpl]key);
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]ref != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]ref.get() : [CtLiteralImpl]null;
        }

        [CtMethodImpl][CtTypeReferenceImpl]void insert([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
            [CtInvocationImpl]put([CtVariableReadImpl]key, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.ReflectRef([CtThisAccessImpl]this, [CtVariableReadImpl]key, [CtVariableReadImpl]value));
        }

        [CtMethodImpl]<[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T insertIfAbsent([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key, [CtParameterImpl][CtTypeParameterReferenceImpl]T value) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectRef newRef = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.ReflectRef([CtThisAccessImpl]this, [CtVariableReadImpl]key, [CtVariableReadImpl]value);
            [CtForImpl]for (; ;) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectRef oldRef = [CtInvocationImpl]putIfAbsent([CtVariableReadImpl]key, [CtVariableReadImpl]newRef);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oldRef == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]value;
                }
                [CtLocalVariableImpl][CtTypeParameterReferenceImpl]T oldValue = [CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtVariableReadImpl]oldRef.get()));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oldValue != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]oldValue;
                }
                [CtIfImpl][CtCommentImpl]// The entry addressed by key has been cleared, but not yet removed from this map.
                [CtCommentImpl]// One thread will successfully replace the entry; the value stored will be shared.
                if ([CtInvocationImpl]replace([CtVariableReadImpl]key, [CtVariableReadImpl]oldRef, [CtVariableReadImpl]newRef)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]value;
                }
            }
        }

        [CtMethodImpl][CtTypeReferenceImpl]void release() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]useCount.decrementAndGet();
        }
    }

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.Class.ReflectCache reflectCache;

    [CtFieldImpl]private static [CtTypeReferenceImpl]long reflectCacheOffset = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Class.ReflectCache acquireReflectCache() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtFieldReadImpl]reflectCache;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]sun.misc.Unsafe theUnsafe = [CtInvocationImpl]java.lang.Class.getUnsafe();
            [CtLocalVariableImpl][CtTypeReferenceImpl]long cacheOffset = [CtInvocationImpl]java.lang.Class.getReflectCacheOffset();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache newCache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Class.ReflectCache([CtThisAccessImpl]this);
            [CtDoImpl]do [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Some thread will insert this new cache making it available to all.
                [CtCommentImpl]/* [IF Sidecar19-SE] */
                if ([CtInvocationImpl][CtVariableReadImpl]theUnsafe.compareAndSetObject([CtThisAccessImpl]this, [CtVariableReadImpl]cacheOffset, [CtLiteralImpl]null, [CtVariableReadImpl]newCache)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]/* [ELSE]
                    if (theUnsafe.compareAndSwapObject(this, cacheOffset, null, newCache)) {
                    /*[ENDIF]
                     */
                    [CtVariableWriteImpl]cache = [CtVariableReadImpl]newCache;
                    [CtBreakImpl]break;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Class.ReflectCache) ([CtVariableReadImpl]theUnsafe.getObject([CtThisAccessImpl]this, [CtVariableReadImpl]cacheOffset)));
            } while ([CtBinaryOperatorImpl][CtVariableReadImpl]cache == [CtLiteralImpl]null );
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]cache.acquire();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]long getReflectCacheOffset() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long cacheOffset = [CtFieldReadImpl]java.lang.Class.reflectCacheOffset;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cacheOffset < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Bypass the reflection cache to avoid infinite recursion.
                [CtTypeReferenceImpl]java.lang.reflect.Field reflectCacheField = [CtInvocationImpl][CtFieldReadImpl]java.lang.Class.class.getDeclaredFieldImpl([CtLiteralImpl]"reflectCache");[CtCommentImpl]// $NON-NLS-1$

                [CtAssignmentImpl][CtVariableWriteImpl]cacheOffset = [CtInvocationImpl][CtInvocationImpl]java.lang.Class.getUnsafe().objectFieldOffset([CtVariableReadImpl]reflectCacheField);
                [CtAssignmentImpl][CtFieldWriteImpl]java.lang.Class.reflectCacheOffset = [CtVariableReadImpl]cacheOffset;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchFieldException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]cacheOffset;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void setReflectCache([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Lazy initialization of a non-volatile field. Ensure the Object is initialized
        [CtCommentImpl]// and flushed to memory before assigning to the annotationCache field.
        [CtCommentImpl]/* [IF Sidecar19-SE]
        getUnsafe().putObjectRelease(this, getReflectCacheOffset(), cache);
        /*[ELSE]
         */
        [CtInvocationImpl]java.lang.Class.getUnsafe().putOrderedObject([CtThisAccessImpl]this, [CtInvocationImpl]java.lang.Class.getReflectCacheOffset(), [CtVariableReadImpl]cache);
        [CtCommentImpl]/* [ENDIF] */
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Class.ReflectCache peekReflectCache() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]reflectCache;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.InternalError newInternalError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception cause) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.InternalError([CtVariableReadImpl]cause);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.reflect.Method lookupCachedMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String methodName, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameters) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"lookup Method: ", [CtInvocationImpl]getName(), [CtLiteralImpl]".", [CtVariableReadImpl]methodName);[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]peekReflectCache();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// use a null returnType to find the Method with the largest depth
            [CtTypeReferenceImpl]java.lang.reflect.Method method = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Method) ([CtVariableReadImpl]cache.find([CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newMethodKey([CtVariableReadImpl]methodName, [CtVariableReadImpl]parameters, [CtLiteralImpl]null))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]method != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] orgParams = [CtInvocationImpl]java.lang.Class.getParameterTypes([CtVariableReadImpl]method);
                    [CtIfImpl][CtCommentImpl]// ensure the parameter classes are identical
                    if ([CtInvocationImpl]java.lang.Class.sameTypes([CtVariableReadImpl]parameters, [CtVariableReadImpl]orgParams)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Method) ([CtFieldReadImpl]java.lang.Class.copyMethod.invoke([CtVariableReadImpl]method, [CtFieldReadImpl]java.lang.Class.NoArgs)));
                    }
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@sun.reflect.CallerSensitive
    private [CtTypeReferenceImpl]java.lang.reflect.Method cacheMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method method) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]method;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.reflectCacheAppOnly && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]2) == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]method;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.copyMethod == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]method;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"cache Method: ", [CtInvocationImpl]getName(), [CtLiteralImpl]".", [CtInvocationImpl][CtVariableReadImpl]method.getName());[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes = [CtInvocationImpl]java.lang.Class.getParameterTypes([CtVariableReadImpl]method);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newMethodKey([CtInvocationImpl][CtVariableReadImpl]method.getName(), [CtVariableReadImpl]parameterTypes, [CtInvocationImpl][CtVariableReadImpl]method.getReturnType());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass = [CtInvocationImpl][CtVariableReadImpl]method.getDeclaringClass();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl][CtVariableReadImpl]declaringClass.acquireReflectCache();
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]/* [PR CMVC 116493] store inherited methods in their declaringClass */
                [CtVariableWriteImpl]method = [CtInvocationImpl][CtVariableReadImpl]cache.insertIfAbsent([CtVariableReadImpl]key, [CtVariableReadImpl]method);
            } finally [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]declaringClass != [CtThisAccessImpl]this) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]cache.release();
                    [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtInvocationImpl]acquireReflectCache();
                }
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// cache the Method with the largest depth with a null returnType
                [CtTypeReferenceImpl]java.lang.Class.CacheKey lookupKey = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newMethodKey([CtInvocationImpl][CtVariableReadImpl]method.getName(), [CtVariableReadImpl]parameterTypes, [CtLiteralImpl]null);
                [CtInvocationImpl][CtVariableReadImpl]cache.insert([CtVariableReadImpl]lookupKey, [CtVariableReadImpl]method);
            } finally [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cache.release();
            }
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Method) ([CtFieldReadImpl]java.lang.Class.copyMethod.invoke([CtVariableReadImpl]method, [CtFieldReadImpl]java.lang.Class.NoArgs)));
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.reflect.Field lookupCachedField([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"lookup Field: ", [CtInvocationImpl]getName(), [CtLiteralImpl]".", [CtVariableReadImpl]fieldName);[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]peekReflectCache();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* [PR 124746] Field cache cannot handle same field name with multiple types */
            [CtTypeReferenceImpl]java.lang.reflect.Field field = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Field) ([CtVariableReadImpl]cache.find([CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newFieldKey([CtVariableReadImpl]fieldName, [CtLiteralImpl]null))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]field != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Field) ([CtFieldReadImpl]java.lang.Class.copyField.invoke([CtVariableReadImpl]field, [CtFieldReadImpl]java.lang.Class.NoArgs)));
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@sun.reflect.CallerSensitive
    private [CtTypeReferenceImpl]java.lang.reflect.Field cacheField([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Field field) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]field;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.reflectCacheAppOnly && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]2) == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]field;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.copyField == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]field;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"cache Field: ", [CtInvocationImpl]getName(), [CtLiteralImpl]".", [CtInvocationImpl][CtVariableReadImpl]field.getName());[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

        }
        [CtLocalVariableImpl][CtCommentImpl]/* [PR 124746] Field cache cannot handle same field name with multiple types */
        [CtTypeReferenceImpl]java.lang.Class.CacheKey typedKey = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newFieldKey([CtInvocationImpl][CtVariableReadImpl]field.getName(), [CtInvocationImpl][CtVariableReadImpl]field.getType());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass = [CtInvocationImpl][CtVariableReadImpl]field.getDeclaringClass();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl][CtVariableReadImpl]declaringClass.acquireReflectCache();
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]field = [CtInvocationImpl][CtVariableReadImpl]cache.insertIfAbsent([CtVariableReadImpl]typedKey, [CtVariableReadImpl]field);
            [CtIfImpl][CtCommentImpl]/* [PR 124746] Field cache cannot handle same field name with multiple types */
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]declaringClass == [CtThisAccessImpl]this) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// cache the Field returned from getField() with a null returnType
                [CtTypeReferenceImpl]java.lang.Class.CacheKey lookupKey = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newFieldKey([CtInvocationImpl][CtVariableReadImpl]field.getName(), [CtLiteralImpl]null);
                [CtInvocationImpl][CtVariableReadImpl]cache.insert([CtVariableReadImpl]lookupKey, [CtVariableReadImpl]field);
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]cache.release();
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Field) ([CtFieldReadImpl]java.lang.Class.copyField.invoke([CtVariableReadImpl]field, [CtFieldReadImpl]java.lang.Class.NoArgs)));
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> lookupCachedConstructor([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameters) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtVariableReadImpl]parameters, [CtLiteralImpl]1, [CtLiteralImpl]"lookup Constructor: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]peekReflectCache();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?> constructor = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtWildcardReferenceImpl]?>) ([CtVariableReadImpl]cache.find([CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newConstructorKey([CtVariableReadImpl]parameters))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]constructor != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] orgParams = [CtInvocationImpl]java.lang.Class.getParameterTypes([CtVariableReadImpl]constructor);
                [CtTryImpl]try [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// ensure the parameter classes are identical
                    if ([CtInvocationImpl]java.lang.Class.sameTypes([CtVariableReadImpl]orgParams, [CtVariableReadImpl]parameters)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>) ([CtFieldReadImpl]java.lang.Class.copyConstructor.invoke([CtVariableReadImpl]constructor, [CtFieldReadImpl]java.lang.Class.NoArgs)));
                    }
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@sun.reflect.CallerSensitive
    private [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> cacheConstructor([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> constructor) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]constructor;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.reflectCacheAppOnly && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]2) == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]constructor;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.copyConstructor == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]constructor;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtInvocationImpl][CtVariableReadImpl]constructor.getParameterTypes(), [CtLiteralImpl]1, [CtLiteralImpl]"cache Constructor: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]acquireReflectCache();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newConstructorKey([CtInvocationImpl]java.lang.Class.getParameterTypes([CtVariableReadImpl]constructor));
            [CtInvocationImpl][CtVariableReadImpl]cache.insert([CtVariableReadImpl]key, [CtVariableReadImpl]constructor);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]cache.release();
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>) ([CtFieldReadImpl]java.lang.Class.copyConstructor.invoke([CtVariableReadImpl]constructor, [CtFieldReadImpl]java.lang.Class.NoArgs)));
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]java.lang.reflect.Method[] copyMethods([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method[] methods) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method[] result = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Method[[CtFieldReadImpl][CtVariableReadImpl]methods.length];
        [CtTryImpl]try [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]methods.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtVariableReadImpl]i] = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Method) ([CtFieldReadImpl]java.lang.Class.copyMethod.invoke([CtArrayReadImpl][CtVariableReadImpl]methods[[CtVariableReadImpl]i], [CtFieldReadImpl]java.lang.Class.NoArgs)));
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtArrayTypeReferenceImpl]java.lang.reflect.Method[] lookupCachedMethods([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"lookup Methods in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]peekReflectCache();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method[] methods = [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.reflect.Method[]) ([CtVariableReadImpl]cache.find([CtVariableReadImpl]cacheKey)));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]methods != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.copyMethods([CtVariableReadImpl]methods);
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@sun.reflect.CallerSensitive
    private [CtArrayTypeReferenceImpl]java.lang.reflect.Method[] cacheMethods([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Method[] methods, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]methods;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.reflectCacheAppOnly && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]2) == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]methods;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.copyMethod == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]methods;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"cache Methods in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cacheOwner = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]methods.length; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method method = [CtArrayReadImpl][CtVariableReadImpl]methods[[CtVariableReadImpl]i];
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newMethodKey([CtInvocationImpl][CtVariableReadImpl]method.getName(), [CtInvocationImpl]java.lang.Class.getParameterTypes([CtVariableReadImpl]method), [CtInvocationImpl][CtVariableReadImpl]method.getReturnType());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass = [CtInvocationImpl][CtVariableReadImpl]method.getDeclaringClass();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cacheOwner != [CtVariableReadImpl]declaringClass) || [CtBinaryOperatorImpl]([CtVariableReadImpl]cache == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]cache.release();
                        [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtLiteralImpl]null;
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtInvocationImpl][CtVariableReadImpl]declaringClass.acquireReflectCache();
                    [CtAssignmentImpl][CtVariableWriteImpl]cacheOwner = [CtVariableReadImpl]declaringClass;
                }
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]methods[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]cache.insertIfAbsent([CtVariableReadImpl]key, [CtVariableReadImpl]method);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cache != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]cacheOwner != [CtThisAccessImpl]this)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cache.release();
                [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtLiteralImpl]null;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtInvocationImpl]acquireReflectCache();
            }
            [CtInvocationImpl][CtVariableReadImpl]cache.insert([CtVariableReadImpl]cacheKey, [CtVariableReadImpl]methods);
        } finally [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cache.release();
            }
        }
        [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.copyMethods([CtVariableReadImpl]methods);
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] copyFields([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Field[] fields) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Field[] result = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Field[[CtFieldReadImpl][CtVariableReadImpl]fields.length];
        [CtTryImpl]try [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]fields.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtVariableReadImpl]i] = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Field) ([CtFieldReadImpl]java.lang.Class.copyField.invoke([CtArrayReadImpl][CtVariableReadImpl]fields[[CtVariableReadImpl]i], [CtFieldReadImpl]java.lang.Class.NoArgs)));
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] lookupCachedFields([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"lookup Fields in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]peekReflectCache();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Field[] fields = [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.reflect.Field[]) ([CtVariableReadImpl]cache.find([CtVariableReadImpl]cacheKey)));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fields != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.copyFields([CtVariableReadImpl]fields);
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@sun.reflect.CallerSensitive
    private [CtArrayTypeReferenceImpl]java.lang.reflect.Field[] cacheFields([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Field[] fields, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]fields;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.reflectCacheAppOnly && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]2) == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]fields;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.copyField == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]fields;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"cache Fields in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cacheOwner = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]fields.length; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]/* [PR 124746] Field cache cannot handle same field name with multiple types */
                [CtTypeReferenceImpl]java.lang.reflect.Field field = [CtArrayReadImpl][CtVariableReadImpl]fields[[CtVariableReadImpl]i];
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass = [CtInvocationImpl][CtVariableReadImpl]field.getDeclaringClass();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cacheOwner != [CtVariableReadImpl]declaringClass) || [CtBinaryOperatorImpl]([CtVariableReadImpl]cache == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]cache.release();
                        [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtLiteralImpl]null;
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtInvocationImpl][CtVariableReadImpl]declaringClass.acquireReflectCache();
                    [CtAssignmentImpl][CtVariableWriteImpl]cacheOwner = [CtVariableReadImpl]declaringClass;
                }
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]fields[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]cache.insertIfAbsent([CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newFieldKey([CtInvocationImpl][CtVariableReadImpl]field.getName(), [CtInvocationImpl][CtVariableReadImpl]field.getType()), [CtVariableReadImpl]field);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cache != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]cacheOwner != [CtThisAccessImpl]this)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cache.release();
                [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtLiteralImpl]null;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtInvocationImpl]acquireReflectCache();
            }
            [CtInvocationImpl][CtVariableReadImpl]cache.insert([CtVariableReadImpl]cacheKey, [CtVariableReadImpl]fields);
        } finally [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cache.release();
            }
        }
        [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.copyFields([CtVariableReadImpl]fields);
    }

    [CtMethodImpl]private static <[CtTypeParameterImpl]T> [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] copyConstructors([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] constructors) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] result = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.reflect.Constructor[[CtFieldReadImpl][CtVariableReadImpl]constructors.length];
        [CtTryImpl]try [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]constructors.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtVariableReadImpl]i] = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>) ([CtFieldReadImpl]java.lang.Class.copyConstructor.invoke([CtArrayReadImpl][CtVariableReadImpl]constructors[[CtVariableReadImpl]i], [CtFieldReadImpl]java.lang.Class.NoArgs)));
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.IllegalAccessException | [CtTypeReferenceImpl]java.lang.IllegalArgumentException | [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]java.lang.Class.newInternalError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] lookupCachedConstructors([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"lookup Constructors in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]peekReflectCache();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] constructors = [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[]) ([CtVariableReadImpl]cache.find([CtVariableReadImpl]cacheKey)));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]constructors != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.copyConstructors([CtVariableReadImpl]constructors);
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@sun.reflect.CallerSensitive
    private [CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] cacheConstructors([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T>[] constructors, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey cacheKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]java.lang.Class.reflectCacheEnabled)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]constructors;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.reflectCacheAppOnly && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getStackClassLoader([CtLiteralImpl]2) == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.ClassLoader.[CtFieldReferenceImpl]bootstrapClassLoader)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]constructors;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]java.lang.Class.copyConstructor == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]constructors;

        [CtIfImpl]if ([CtFieldReadImpl]java.lang.Class.reflectCacheDebug) [CtBlockImpl]{
            [CtInvocationImpl]java.lang.Class.reflectCacheDebugHelper([CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]"cache Constructors in: ", [CtInvocationImpl]getName());[CtCommentImpl]// $NON-NLS-1$

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.ReflectCache cache = [CtInvocationImpl]acquireReflectCache();
        [CtTryImpl]try [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]constructors.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class.CacheKey key = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.CacheKey.newConstructorKey([CtInvocationImpl]java.lang.Class.getParameterTypes([CtArrayReadImpl][CtVariableReadImpl]constructors[[CtVariableReadImpl]i]));
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]constructors[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]cache.insertIfAbsent([CtVariableReadImpl]key, [CtArrayReadImpl][CtVariableReadImpl]constructors[[CtVariableReadImpl]i]);
            }
            [CtInvocationImpl][CtVariableReadImpl]cache.insert([CtVariableReadImpl]cacheKey, [CtVariableReadImpl]constructors);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]cache.release();
        }
        [CtReturnImpl]return [CtInvocationImpl]java.lang.Class.copyConstructors([CtVariableReadImpl]constructors);
    }

    [CtMethodImpl]private static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> checkParameterTypes([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeParameterReferenceImpl]T> constructor, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] parameterTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] constructorParameterTypes = [CtInvocationImpl]java.lang.Class.getParameterTypes([CtVariableReadImpl]constructor);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl]java.lang.Class.sameTypes([CtVariableReadImpl]constructorParameterTypes, [CtVariableReadImpl]parameterTypes) ? [CtVariableReadImpl]constructor : [CtLiteralImpl]null;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean sameTypes([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] aTypes, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] bTypes) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]aTypes == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bTypes == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bTypes != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int length = [CtFieldReadImpl][CtVariableReadImpl]aTypes.length;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]length == [CtFieldReadImpl][CtVariableReadImpl]bTypes.length) [CtBlockImpl]{
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]length; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]aTypes[[CtVariableReadImpl]i] != [CtArrayReadImpl][CtVariableReadImpl]bTypes[[CtVariableReadImpl]i]) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]false;
                    }
                }
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.Object getMethodHandleCache() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]methodHandleCache;
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.Object setMethodHandleCache([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object cache) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object result = [CtFieldReadImpl]methodHandleCache;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]result) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtThisAccessImpl]this) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtFieldReadImpl]methodHandleCache;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]result) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]methodHandleCache = [CtVariableReadImpl]cache;
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtVariableReadImpl]cache;
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtCommentImpl]/* [IF Sidecar19-SE] */
    [CtTypeReferenceImpl]sun.reflect.ConstantPool getConstantPool() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]Access().getConstantPool([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.annotation.Annotation>, [CtTypeReferenceImpl]java.lang.annotation.Annotation> getDeclaredAnnotationMap() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtLiteralImpl]"Class.getDeclaredAnnotationMap() unimplemented");[CtCommentImpl]// $NON-NLS-1$

    }

    [CtMethodImpl][CtArrayTypeReferenceImpl]byte[] getRawAnnotations() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtLiteralImpl]"Class.getRawAnnotations() unimplemented");[CtCommentImpl]// $NON-NLS-1$

    }

    [CtMethodImpl][CtArrayTypeReferenceImpl]byte[] getRawTypeAnnotations() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtLiteralImpl]"Class.getRawTypeAnnotations() unimplemented");[CtCommentImpl]// $NON-NLS-1$

    }

    [CtMethodImpl]static [CtArrayTypeReferenceImpl]byte[] getExecutableTypeAnnotationBytes([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Executable exec) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtLiteralImpl]"Class.getExecutableTypeAnnotationBytes() unimplemented");[CtCommentImpl]// $NON-NLS-1$

    }

    [CtMethodImpl][CtCommentImpl]/* [ENDIF] Sidecar19-SE */
    [CtCommentImpl]/* [IF Java11] */
    [CtJavaDocImpl]/**
     * Answers the host class of the receiver's nest.
     *
     * @return the host class of the receiver.
     */
    private native [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getNestHostImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the nest member classes of the receiver's nest host.
     *
     * @return the host class of the receiver.
     * @implNote This implementation does not remove duplicate nest members if they are present.
     */
    private native [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getNestMembersImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the host class of the receiver's nest.
     *
     * @throws SecurityException
     * 		if nestHost is not same as the current class, a security manager
     * 		is present, the classloader of the caller is not the same or an ancestor of nestHost
     * 		class, and checkPackageAccess() denies access
     * @return the host class of the receiver.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> getNestHost() throws [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]nestHost == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]nestHost = [CtInvocationImpl]getNestHostImpl();
        }
        [CtIfImpl][CtCommentImpl]/* The specification requires that if:
           - the returned class is not the current class
           - a security manager is present
           - the caller's class loader is not the same or an ancestor of the returned class
           - s.checkPackageAccess() disallows access to the package of the returned class
        then throw a SecurityException.
         */
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]nestHost != [CtThisAccessImpl]this) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager securityManager = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]securityManager != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getCallerClassLoader();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader nestHostClassLoader = [CtInvocationImpl][CtFieldReadImpl]nestHost.internalGetClassLoader();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]java.lang.Class.doesClassLoaderDescendFrom([CtVariableReadImpl]nestHostClassLoader, [CtVariableReadImpl]callerClassLoader)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nestHostPackageName = [CtInvocationImpl][CtFieldReadImpl]nestHost.getPackageName();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]nestHostPackageName != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]nestHostPackageName != [CtLiteralImpl]"")) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]securityManager.checkPackageAccess([CtVariableReadImpl]nestHostPackageName);
                    }
                }
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]nestHost;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the class passed has the same nest top as this class.
     *
     * @param that
     * 		The class to compare
     * @return true if class is a nestmate of this class; false otherwise.
     */
    public [CtTypeReferenceImpl]boolean isNestmateOf([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> that) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> thisNestHost = [CtFieldReadImpl][CtThisAccessImpl]this.nestHost;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]thisNestHost == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]thisNestHost = [CtInvocationImpl][CtThisAccessImpl]this.getNestHostImpl();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> thatNestHost = [CtFieldReadImpl][CtVariableReadImpl]that.nestHost;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]thatNestHost == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]thatNestHost = [CtInvocationImpl][CtVariableReadImpl]that.getNestHostImpl();
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]thisNestHost == [CtVariableReadImpl]thatNestHost;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers the nest member classes of the receiver's nest host.
     *
     * @throws SecurityException
     * 		if a SecurityManager is present and package access is not allowed
     * @throws LinkageError
     * 		if there is any problem loading or validating a nest member or the nest host
     * @throws SecurityException
     * 		if a returned class is not the current class, a security manager is enabled,
     * 		the caller's class loader is not the same or an ancestor of that returned class, and the
     * 		checkPackageAccess() denies access
     * @return the host class of the receiver.
     */
    [CtAnnotationImpl]@sun.reflect.CallerSensitive
    public [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] getNestMembers() throws [CtTypeReferenceImpl]java.lang.LinkageError, [CtTypeReferenceImpl]java.lang.SecurityException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isArray() || [CtInvocationImpl]isPrimitive()) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]/* By spec, Class objects representing array types or primitive types
            belong to the nest consisting only of itself.
             */
            return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[]{ [CtThisAccessImpl]this };
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] members = [CtInvocationImpl]getNestMembersImpl();
        [CtIfImpl][CtCommentImpl]/* Skip security check for the Class object that belongs to the nest consisting only of itself */
        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]members.length > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.SecurityManager securityManager = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]securityManager != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]/* All classes in a nest must be in the same runtime package and therefore same classloader */
                [CtTypeReferenceImpl]java.lang.ClassLoader nestMemberClassLoader = [CtInvocationImpl][CtThisAccessImpl]this.internalGetClassLoader();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader callerClassLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getCallerClassLoader();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]java.lang.Class.doesClassLoaderDescendFrom([CtVariableReadImpl]nestMemberClassLoader, [CtVariableReadImpl]callerClassLoader)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nestMemberPackageName = [CtInvocationImpl][CtThisAccessImpl]this.getPackageName();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]nestMemberPackageName != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]nestMemberPackageName != [CtLiteralImpl]"")) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// $NON-NLS-1$
                        [CtVariableReadImpl]securityManager.checkPackageAccess([CtVariableReadImpl]nestMemberPackageName);
                    }
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]members;
    }

    [CtMethodImpl][CtCommentImpl]/* [ENDIF] Java11 */
    [CtCommentImpl]/* [IF Java12] */
    [CtJavaDocImpl]/**
     * Create class of an array. The component type will be this Class instance.
     *
     * @return array class where the component type is this Class instance
     */
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> arrayType() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]void.class) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();
        }
        [CtReturnImpl]return [CtInvocationImpl]arrayTypeImpl();
    }

    [CtMethodImpl]private native [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> arrayTypeImpl();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Answers a Class object which represents the receiver's component type if the receiver
     * represents an array type. The component type of an array type is the type of the elements
     * of the array.
     *
     * @return the component type of the receiver. Returns null if the receiver does
    not represent an array.
     */
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> componentType() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getComponentType();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the nominal descriptor of this Class instance, or an empty Optional
     * if construction is not possible.
     *
     * @return Optional with a nominal descriptor of Class instance
     */
    public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.constant.ClassDesc> describeConstable() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.constant.ClassDesc classDescriptor = [CtInvocationImpl][CtTypeAccessImpl]java.lang.constant.ClassDesc.ofDescriptor([CtInvocationImpl][CtThisAccessImpl]this.descriptorString());
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]classDescriptor);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return field descriptor of Class instance.
     *
     * @return field descriptor of Class instance
     */
    public [CtTypeReferenceImpl]java.lang.String descriptorString() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]/* see MethodType.getBytecodeStringName */
        if ([CtInvocationImpl][CtThisAccessImpl]this.isPrimitive()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]int.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"I";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]long.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"J";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]byte.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"B";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]boolean.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"Z";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]void.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"V";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]char.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"C";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]double.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"D";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]float.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"F";[CtCommentImpl]// $NON-NLS-1$

            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]short.class) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"S";[CtCommentImpl]// $NON-NLS-1$

            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getName().replace([CtLiteralImpl]'.', [CtLiteralImpl]'/');
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isArray()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]name;
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"L" + [CtVariableReadImpl]name) + [CtLiteralImpl]";";[CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$

    }

    [CtMethodImpl][CtCommentImpl]/* [ENDIF] Java12 */
    [CtCommentImpl]/* [IF Java14] */
    [CtJavaDocImpl]/**
     * Returns true if the class instance is a record.
     *
     * @return true for a record class, false otherwise
     */
    public native [CtTypeReferenceImpl]boolean isRecord();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns an array of RecordComponent objects for a record class.
     *
     * @return array of RecordComponent objects, one for each component in the record.
    For a class that is not a record an empty array is returned.
    For a record with no components an empty array is returned.
     */
    public [CtArrayTypeReferenceImpl]java.lang.reflect.RecordComponent[] getRecordComponents() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.InternalError([CtLiteralImpl]"Compile stub invoked! For JEP 359 support see https://github.com/eclipse/openj9/pull/7946");[CtCommentImpl]// $NON-NLS-1$

    }
}
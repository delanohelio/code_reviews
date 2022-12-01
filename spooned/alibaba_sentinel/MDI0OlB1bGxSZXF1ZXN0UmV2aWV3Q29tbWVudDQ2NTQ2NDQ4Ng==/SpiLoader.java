[CompilationUnitImpl][CtCommentImpl]/* Copyright 1999-2018 Alibaba Group Holding Ltd.

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
[CtPackageDeclarationImpl]package com.alibaba.csp.sentinel.spi;
[CtUnresolvedImport]import com.alibaba.csp.sentinel.util.AssertUtil;
[CtUnresolvedImport]import com.alibaba.csp.sentinel.util.StringUtil;
[CtImportImpl]import java.net.URL;
[CtImportImpl]import java.lang.reflect.Modifier;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtImportImpl]import java.util.*;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import com.alibaba.csp.sentinel.log.RecordLog;
[CtUnresolvedImport]import com.alibaba.csp.sentinel.config.SentinelConfig;
[CtImportImpl]import java.io.*;
[CtClassImpl][CtJavaDocImpl]/**
 * A simple SPI loading facility.
 *
 * <p>SPI is short for Service Provider Interface.</p>
 *
 * <p>
 * Service is represented by a single type, that is, a single interface or an abstract class.
 * Provider is implementations of Service, that is, some classes which implement the interface or extends the abstract class.
 * </p>
 *
 * <p>
 * For Service type:
 * Must interface or abstract class.
 * </p>
 *
 * <p>
 * For Provider class:
 * Must have a zero-argument constructor so that they can be instantiated during loading.
 * </p>
 *
 * <p>
 * For Provider configuration file:
 * 1. The file contains a list of fully-qualified binary names of concrete provider classes, one per line.
 * 2. Space and tab characters surrounding each name, as well as blank lines, are ignored.
 * 3. The comment line character is #, all characters following it are ignored.
 * </p>
 *
 * <p>
 * Provide common functions, such as:
 * Load all Provider instance unsorted/sorted list.
 * Load highest/lowest order priority instance.
 * Load first-found or default instance.
 * Load instance by aliasname or provider class.
 * </p>
 *
 * @author Eric Zhao
 * @author cdfive
 * @since 1.4.0
 * @see com.alibaba.csp.sentinel.spi.Spi
 * @see java.util.ServiceLoader
 */
public final class SpiLoader<[CtTypeParameterImpl]S> {
    [CtFieldImpl][CtCommentImpl]// Default path for the folder of Provider configuration file
    private static final [CtTypeReferenceImpl]java.lang.String SPI_FILE_PREFIX = [CtLiteralImpl]"META-INF/services/";

    [CtFieldImpl][CtCommentImpl]// Cache the SpiLoader instances, key: classname of Service, value: SpiLoader instance
    private static final [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader> SPI_LOADER_MAP = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl][CtCommentImpl]// Cache the classes of Provider
    private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S>> classList = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.synchronizedList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S>>());

    [CtFieldImpl][CtCommentImpl]// Cache the sorted classes of Provider
    private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S>> sortedClassList = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.synchronizedList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S>>());

    [CtFieldImpl][CtJavaDocImpl]/**
     * Cache the classes of Provider, key: aliasName, value: class of Provider.
     * Note: aliasName is the value of {@link Spi} when the Provider class has {@link Spi} annotation and value is not empty,
     * otherwise use classname of the Provider.
     */
    private final [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S>> classMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl][CtCommentImpl]// Cache the singleton instance of Provider, key: classname of Provider, value: Provider instance
    private final [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeParameterReferenceImpl]S> singletonMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl][CtCommentImpl]// Whether this SpiLoader has beend loaded, that is, loaded the Provider configuration file
    private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean loaded = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]false);

    [CtFieldImpl][CtCommentImpl]// Default provider class
    private [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> defaultClass = [CtLiteralImpl]null;

    [CtFieldImpl][CtCommentImpl]// The Service class, must be interface or abstract class
    private [CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]S> service;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create SpiLoader instance via Service class
     * Cached by className, and load from cache first
     *
     * @param service
     * 		Service class
     * @param <T>
     * 		Service type
     * @return SpiLoader instance
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader<[CtTypeParameterReferenceImpl]T> of([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> service) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.util.AssertUtil.notNull([CtVariableReadImpl]service, [CtLiteralImpl]"SPI class cannot be null");
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.util.AssertUtil.isTrue([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]service.isInterface() || [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isAbstract([CtInvocationImpl][CtVariableReadImpl]service.getModifiers()), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"SPI class[" + [CtInvocationImpl][CtVariableReadImpl]service.getName()) + [CtLiteralImpl]"] must be interface or abstract class");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String className = [CtInvocationImpl][CtVariableReadImpl]service.getName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader<[CtTypeParameterReferenceImpl]T> spiLoader = [CtInvocationImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_LOADER_MAP.get([CtVariableReadImpl]className);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spiLoader == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.class) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]spiLoader = [CtInvocationImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_LOADER_MAP.get([CtVariableReadImpl]className);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spiLoader == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_LOADER_MAP.putIfAbsent([CtVariableReadImpl]className, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader<>([CtVariableReadImpl]service));
                    [CtAssignmentImpl][CtVariableWriteImpl]spiLoader = [CtInvocationImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_LOADER_MAP.get([CtVariableReadImpl]className);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]spiLoader;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create SpiLoader instance via Service class
     * Same as {@link SpiLoader#of} method
     *
     * @param service
     * 		Service class
     * @param <T>
     * 		Service type
     * @return SpiLoader instance
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader<[CtTypeParameterReferenceImpl]T> getSpiLoader([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> service) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.spi.SpiLoader.of([CtVariableReadImpl]service);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reset all SpiLoader instances
     */
    public static [CtTypeReferenceImpl]void resetAll() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader>> entries = [CtInvocationImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_LOADER_MAP.entrySet();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader> entry : [CtVariableReadImpl]entries) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.alibaba.csp.sentinel.spi.SpiLoader spiLoader = [CtInvocationImpl][CtVariableReadImpl]entry.getValue();
            [CtInvocationImpl][CtVariableReadImpl]spiLoader.reset();
        }
        [CtInvocationImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_LOADER_MAP.clear();
    }

    [CtConstructorImpl][CtCommentImpl]// Private access
    private SpiLoader([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]S> service) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.service = [CtVariableReadImpl]service;
    }

    [CtConstructorImpl][CtCommentImpl]// Private access
    private SpiLoader() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load all Provider instances of the specified Service
     *
     * @return Provider instances list
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]S> loadInstanceList() [CtBlockImpl]{
        [CtInvocationImpl]load();
        [CtReturnImpl]return [CtInvocationImpl]createInstanceList([CtFieldReadImpl]classList);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load all Provider instances of the specified Service, sorted by order value in class's {@link Spi} annotation
     *
     * @return Sorted Provider instances list
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]S> loadInstanceListSorted() [CtBlockImpl]{
        [CtInvocationImpl]load();
        [CtReturnImpl]return [CtInvocationImpl]createInstanceList([CtFieldReadImpl]sortedClassList);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load highest order priority instance, order value is defined in class's {@link Spi} annotation
     *
     * @return Provider instance of highest order priority
     */
    public [CtTypeParameterReferenceImpl]S loadHighestPriorityInstance() [CtBlockImpl]{
        [CtInvocationImpl]load();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sortedClassList.size() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> highestClass = [CtInvocationImpl][CtFieldReadImpl]sortedClassList.get([CtLiteralImpl]0);
        [CtReturnImpl]return [CtInvocationImpl]createInstance([CtVariableReadImpl]highestClass);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load lowest order priority instance, order value is defined in class's {@link Spi} annotation
     *
     * @return Provider instance of lowest order priority
     */
    public [CtTypeParameterReferenceImpl]S loadLowestPriorityInstance() [CtBlockImpl]{
        [CtInvocationImpl]load();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sortedClassList.size() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> lowestClass = [CtInvocationImpl][CtFieldReadImpl]sortedClassList.get([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sortedClassList.size() - [CtLiteralImpl]1);
        [CtReturnImpl]return [CtInvocationImpl]createInstance([CtVariableReadImpl]lowestClass);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load the first-found Provider instance
     *
     * @return Provider instance of first-found specific
     */
    public [CtTypeParameterReferenceImpl]S loadFirstInstance() [CtBlockImpl]{
        [CtInvocationImpl]load();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]classList.size() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> serviceClass = [CtInvocationImpl][CtFieldReadImpl]classList.get([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]S instance = [CtInvocationImpl]createInstance([CtVariableReadImpl]serviceClass);
        [CtReturnImpl]return [CtVariableReadImpl]instance;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load the first-found Provider instance,if not found, return default Provider instance
     *
     * @return Provider instance
     */
    public [CtTypeParameterReferenceImpl]S loadFirstInstanceOrDefault() [CtBlockImpl]{
        [CtInvocationImpl]load();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> clazz : [CtFieldReadImpl]classList) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]defaultClass == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]clazz != [CtFieldReadImpl]defaultClass)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]createInstance([CtVariableReadImpl]clazz);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl]loadDefaultInstance();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load default Provider instance
     * Provider class with @Spi(isDefault = true)
     *
     * @return default Provider instance
     */
    public [CtTypeParameterReferenceImpl]S loadDefaultInstance() [CtBlockImpl]{
        [CtInvocationImpl]load();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]defaultClass == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl]createInstance([CtFieldReadImpl]defaultClass);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load instance by specific class type
     *
     * @param clazz
     * 		class type
     * @return Provider instance
     */
    public [CtTypeParameterReferenceImpl]S loadInstance([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> clazz) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.util.AssertUtil.notNull([CtVariableReadImpl]clazz, [CtLiteralImpl]"SPI class cannot be null");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]clazz.equals([CtFieldReadImpl]service)) [CtBlockImpl]{
            [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]clazz.getName() + [CtLiteralImpl]" is not subtype of ") + [CtInvocationImpl][CtFieldReadImpl]service.getName());
        }
        [CtInvocationImpl]load();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]classMap.containsValue([CtVariableReadImpl]clazz)) [CtBlockImpl]{
            [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]clazz.getName() + [CtLiteralImpl]" is not Provider class of ") + [CtInvocationImpl][CtFieldReadImpl]service.getName()) + [CtLiteralImpl]",check if it is in the SPI file?");
        }
        [CtReturnImpl]return [CtInvocationImpl]createInstance([CtVariableReadImpl]clazz);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load instance by aliasName of Provider class
     *
     * @param aliasName
     * 		aliasName of Provider class
     * @return Provider instance
     */
    public [CtTypeParameterReferenceImpl]S loadInstance([CtParameterImpl][CtTypeReferenceImpl]java.lang.String aliasName) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.util.AssertUtil.notEmpty([CtVariableReadImpl]aliasName, [CtLiteralImpl]"aliasName cannot be empty");
        [CtInvocationImpl]load();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> clazz = [CtInvocationImpl][CtFieldReadImpl]classMap.get([CtVariableReadImpl]aliasName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clazz == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtLiteralImpl]"no Provider class's aliasName is " + [CtVariableReadImpl]aliasName);
        }
        [CtReturnImpl]return [CtInvocationImpl]createInstance([CtVariableReadImpl]clazz);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reset all fields of current SpiLoader instance
     */
    public synchronized [CtTypeReferenceImpl]void reset() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_LOADER_MAP.remove([CtInvocationImpl][CtFieldReadImpl]service.getName());
        [CtInvocationImpl][CtFieldReadImpl]classList.clear();
        [CtInvocationImpl][CtFieldReadImpl]sortedClassList.clear();
        [CtInvocationImpl][CtFieldReadImpl]classMap.clear();
        [CtInvocationImpl][CtFieldReadImpl]singletonMap.clear();
        [CtAssignmentImpl][CtFieldWriteImpl]defaultClass = [CtLiteralImpl]null;
        [CtInvocationImpl][CtFieldReadImpl]loaded.set([CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Load the Provider class from Provider configuration file
     */
    public [CtTypeReferenceImpl]void load() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]loaded.compareAndSet([CtLiteralImpl]false, [CtLiteralImpl]true)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fullFileName = [CtBinaryOperatorImpl][CtFieldReadImpl]com.alibaba.csp.sentinel.spi.SpiLoader.SPI_FILE_PREFIX + [CtInvocationImpl][CtFieldReadImpl]service.getName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader classLoader;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.config.SentinelConfig.shouldUseContextClassloader()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]classLoader = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]classLoader = [CtInvocationImpl][CtFieldReadImpl]service.getClassLoader();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]classLoader == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]classLoader = [CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getSystemClassLoader();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration<[CtTypeReferenceImpl]java.net.URL> urls = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]urls = [CtInvocationImpl][CtVariableReadImpl]classLoader.getResources([CtVariableReadImpl]fullFileName);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error locating SPI file,filename=" + [CtVariableReadImpl]fullFileName) + [CtLiteralImpl]",classloader=") + [CtVariableReadImpl]classLoader, [CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]urls == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]urls.hasMoreElements())) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.log.RecordLog.warn([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"No SPI file,filename=" + [CtVariableReadImpl]fullFileName) + [CtLiteralImpl]",classloader=") + [CtVariableReadImpl]classLoader);
            [CtReturnImpl]return;
        }
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]urls.hasMoreElements()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL url = [CtInvocationImpl][CtVariableReadImpl]urls.nextElement();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream in = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.BufferedReader br = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]in = [CtInvocationImpl][CtVariableReadImpl]url.openStream();
                [CtAssignmentImpl][CtVariableWriteImpl]br = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtVariableReadImpl]in, [CtLiteralImpl]"utf-8"));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String line;
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]br.readLine()) != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.util.StringUtil.isBlank([CtVariableReadImpl]line)) [CtBlockImpl]{
                        [CtContinueImpl][CtCommentImpl]// Skip blank line
                        continue;
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]line.trim();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int commentIndex = [CtInvocationImpl][CtVariableReadImpl]line.indexOf([CtLiteralImpl]"#");
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]commentIndex == [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtContinueImpl][CtCommentImpl]// Skip comment line
                        continue;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]commentIndex > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]line.substring([CtLiteralImpl]0, [CtVariableReadImpl]commentIndex);
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]line = [CtInvocationImpl][CtVariableReadImpl]line.trim();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]S> clazz = [CtLiteralImpl]null;
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]clazz = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]S>) ([CtTypeAccessImpl]java.lang.Class.forName([CtVariableReadImpl]line, [CtLiteralImpl]false, [CtVariableReadImpl]classLoader)));
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
                        [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"class " + [CtVariableReadImpl]line) + [CtLiteralImpl]" not found", [CtVariableReadImpl]e);
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]service.isAssignableFrom([CtVariableReadImpl]clazz)) [CtBlockImpl]{
                        [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"class " + [CtInvocationImpl][CtVariableReadImpl]clazz.getName()) + [CtLiteralImpl]"is not subtype of ") + [CtInvocationImpl][CtFieldReadImpl]service.getName()) + [CtLiteralImpl]",SPI filename=") + [CtVariableReadImpl]fullFileName);
                    }
                    [CtInvocationImpl][CtFieldReadImpl]classList.add([CtVariableReadImpl]clazz);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Spi spi = [CtInvocationImpl][CtVariableReadImpl]clazz.getAnnotation([CtFieldReadImpl]com.alibaba.csp.sentinel.spi.Spi.class);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String aliasName = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]spi == [CtLiteralImpl]null) || [CtInvocationImpl][CtLiteralImpl]"".equals([CtInvocationImpl][CtVariableReadImpl]spi.value())) ? [CtInvocationImpl][CtVariableReadImpl]clazz.getName() : [CtInvocationImpl][CtVariableReadImpl]spi.value();
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]classMap.containsKey([CtVariableReadImpl]aliasName)) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> existClass = [CtInvocationImpl][CtFieldReadImpl]classMap.get([CtVariableReadImpl]aliasName);
                        [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Found repeat aliasname for " + [CtInvocationImpl][CtVariableReadImpl]clazz.getName()) + [CtLiteralImpl]" and ") + [CtInvocationImpl][CtVariableReadImpl]existClass.getName()) + [CtLiteralImpl]",SPI filename=") + [CtVariableReadImpl]fullFileName);
                    }
                    [CtInvocationImpl][CtFieldReadImpl]classMap.put([CtVariableReadImpl]aliasName, [CtVariableReadImpl]clazz);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]spi != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]spi.isDefault()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]defaultClass != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtLiteralImpl]"Found more than one default Provider,SPI filename=" + [CtVariableReadImpl]fullFileName);
                        }
                        [CtAssignmentImpl][CtFieldWriteImpl]defaultClass = [CtVariableReadImpl]clazz;
                    }
                    [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.log.RecordLog.info([CtLiteralImpl]"[SpiLoader]Found SPI,Service={},Provider={},aliasname={},isSingleton={},isDefault={},order={}", [CtInvocationImpl][CtFieldReadImpl]service.getName(), [CtVariableReadImpl]line, [CtVariableReadImpl]aliasName, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]spi == [CtLiteralImpl]null ? [CtLiteralImpl]true : [CtInvocationImpl][CtVariableReadImpl]spi.isSingleton(), [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]spi == [CtLiteralImpl]null ? [CtLiteralImpl]false : [CtInvocationImpl][CtVariableReadImpl]spi.isDefault(), [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]spi == [CtLiteralImpl]null ? [CtLiteralImpl]0 : [CtInvocationImpl][CtVariableReadImpl]spi.order());
                } 
                [CtInvocationImpl][CtFieldReadImpl]sortedClassList.addAll([CtFieldReadImpl]classList);
                [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.sort([CtFieldReadImpl]sortedClassList, [CtNewClassImpl]new [CtTypeReferenceImpl]java.util.Comparator<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S>>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]int compare([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> o1, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> o2) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]Spi spi1 = [CtInvocationImpl][CtVariableReadImpl]o1.getAnnotation([CtFieldReadImpl]com.alibaba.csp.sentinel.spi.Spi.class);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int order1 = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]spi1 == [CtLiteralImpl]null) ? [CtLiteralImpl]0 : [CtInvocationImpl][CtVariableReadImpl]spi1.order();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]Spi spi2 = [CtInvocationImpl][CtVariableReadImpl]o2.getAnnotation([CtFieldReadImpl]com.alibaba.csp.sentinel.spi.Spi.class);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int order2 = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]spi2 == [CtLiteralImpl]null) ? [CtLiteralImpl]0 : [CtInvocationImpl][CtVariableReadImpl]spi2.order();
                        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.compare([CtVariableReadImpl]order1, [CtVariableReadImpl]order2);
                    }
                });
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtInvocationImpl]fail([CtLiteralImpl]"error reading SPI file", [CtVariableReadImpl]e);
            } finally [CtBlockImpl]{
                [CtInvocationImpl]closeResources([CtVariableReadImpl]in, [CtVariableReadImpl]br);
            }
        } 
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"com.alibaba.csp.sentinel.spi.SpiLoader[" + [CtInvocationImpl][CtFieldReadImpl]service.getName()) + [CtLiteralImpl]"]";
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create Provider instance list
     *
     * @param clazzList
     * 		class types of Providers
     * @return Provider instance list
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]S> createInstanceList([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S>> clazzList) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clazzList == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]clazzList.size() == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]S> instances = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]clazzList.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> clazz : [CtVariableReadImpl]clazzList) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]S instance = [CtInvocationImpl]createInstance([CtVariableReadImpl]clazz);
            [CtInvocationImpl][CtVariableReadImpl]instances.add([CtVariableReadImpl]instance);
        }
        [CtReturnImpl]return [CtVariableReadImpl]instances;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create Provider instance
     *
     * @param clazz
     * 		class type of Provider
     * @return Provider class
     */
    private [CtTypeParameterReferenceImpl]S createInstance([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> clazz) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Spi spi = [CtInvocationImpl][CtVariableReadImpl]clazz.getAnnotation([CtFieldReadImpl]com.alibaba.csp.sentinel.spi.Spi.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean singleton = [CtLiteralImpl]true;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spi != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]singleton = [CtInvocationImpl][CtVariableReadImpl]spi.isSingleton();
        }
        [CtReturnImpl]return [CtInvocationImpl]createInstance([CtVariableReadImpl]clazz, [CtVariableReadImpl]singleton);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create Provider instance
     *
     * @param clazz
     * 		class type of Provider
     * @param singleton
     * 		if instance is singleton or prototype
     * @return Provider instance
     */
    private [CtTypeParameterReferenceImpl]S createInstance([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]S> clazz, [CtParameterImpl][CtTypeReferenceImpl]boolean singleton) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]S instance = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]singleton) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]instance = [CtInvocationImpl][CtFieldReadImpl]singletonMap.get([CtInvocationImpl][CtVariableReadImpl]clazz.getName());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]instance == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtSynchronizedImpl]synchronized([CtThisAccessImpl]this) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]instance = [CtInvocationImpl][CtFieldReadImpl]service.cast([CtInvocationImpl][CtVariableReadImpl]clazz.newInstance());
                        [CtInvocationImpl][CtFieldReadImpl]singletonMap.put([CtInvocationImpl][CtVariableReadImpl]clazz.getName(), [CtVariableReadImpl]instance);
                    }
                }
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]instance = [CtInvocationImpl][CtFieldReadImpl]service.cast([CtInvocationImpl][CtVariableReadImpl]clazz.newInstance());
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtInvocationImpl]fail([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]clazz.getName() + [CtLiteralImpl]" could not be instantiated");
        }
        [CtReturnImpl]return [CtVariableReadImpl]instance;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Close all resources
     *
     * @param closeables
     * 		{@link Closeable} resources
     */
    private [CtTypeReferenceImpl]void closeResources([CtParameterImpl]java.io.Closeable... closeables) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]closeables == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Closeable closeable : [CtVariableReadImpl]closeables) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]closeable.close();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl]fail([CtLiteralImpl]"error closing SPI file", [CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Throw {@link SpiLoaderException} with message
     *
     * @param msg
     * 		error message
     */
    private [CtTypeReferenceImpl]void fail([CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.log.RecordLog.error([CtVariableReadImpl]msg);
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]SpiLoaderException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"[" + [CtInvocationImpl][CtFieldReadImpl]service.getName()) + [CtLiteralImpl]"]") + [CtVariableReadImpl]msg);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Throw {@link SpiLoaderException} with message and Throwable
     *
     * @param msg
     * 		error message
     */
    private [CtTypeReferenceImpl]void fail([CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.alibaba.csp.sentinel.log.RecordLog.error([CtVariableReadImpl]msg, [CtVariableReadImpl]e);
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]SpiLoaderException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"[" + [CtInvocationImpl][CtFieldReadImpl]service.getName()) + [CtLiteralImpl]"]") + [CtVariableReadImpl]msg, [CtVariableReadImpl]e);
    }
}
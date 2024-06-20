[CompilationUnitImpl][CtCommentImpl]/* Copyright 2013 MovingBlocks

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
[CtPackageDeclarationImpl]package org.terasology.engine;
[CtUnresolvedImport]import com.google.common.base.Charsets;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import java.nio.charset.Charset;
[CtImportImpl]import java.nio.file.Paths;
[CtUnresolvedImport]import org.terasology.naming.Name;
[CtClassImpl][CtJavaDocImpl]/**
 * This class contains various important constants used by the Terasology engine.
 */
public final class TerasologyConstants {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Name and extension of an entities data file. (Not currently in use) Used to contain state data on world entities
     * for loading/saving. See AbstractStorageManager for details.
     */
    public static final [CtTypeReferenceImpl]java.lang.String ENTITY_DATA_FILE = [CtLiteralImpl]"entity.dat";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Terasology's default server port.
     */
    public static final [CtTypeReferenceImpl]int DEFAULT_PORT = [CtLiteralImpl]25777;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Name and extension of a world data file.
     */
    public static final [CtTypeReferenceImpl]java.lang.String WORLD_DATA_FILE = [CtLiteralImpl]"world.dat";

    [CtFieldImpl][CtJavaDocImpl]/**
     * The name of the default world.
     */
    public static final [CtTypeReferenceImpl]java.lang.String MAIN_WORLD = [CtLiteralImpl]"main";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Default charset used by Terasology.
     */
    public static final [CtTypeReferenceImpl]java.nio.charset.Charset CHARSET = [CtFieldReadImpl]com.google.common.base.Charsets.UTF_8;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Name of the engine module. The engine module contains engine features, without any gameplay elements.
     * Examples of specific features include the entity system, noise functions, core blocks, and utilities.
     */
    public static final [CtTypeReferenceImpl]org.terasology.naming.Name ENGINE_MODULE = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.terasology.naming.Name([CtLiteralImpl]"engine");

    [CtFieldImpl][CtJavaDocImpl]/**
     * Name of the core gameplay module. The core gameplay module adds a minimal environment for some basic gameplay.
     */
    [CtCommentImpl]// TODO: CSG moved out of the engine repo, so this should either be removed or reference the Builder Sample Gameplay
    [CtCommentImpl]// which still lives in the engine repo.
    public static final [CtTypeReferenceImpl]org.terasology.naming.Name CORE_GAMEPLAY_MODULE = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.terasology.naming.Name([CtLiteralImpl]"coresamplegameplay");

    [CtFieldImpl][CtJavaDocImpl]/**
     * Name of a module's assets directory.
     */
    public static final [CtTypeReferenceImpl]java.lang.String ASSETS_SUBDIRECTORY = [CtLiteralImpl]"assets";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Name of a module's overrides directory.
     * Overrides replace an entire existing prefab.
     * More info on Overrides/Deltas: <a href="https://github.com/Terasology/TutorialAssetSystem/wiki/Deltas-and-Overrides">Deltas-and-Overrides</>
     */
    public static final [CtTypeReferenceImpl]java.lang.String OVERRIDES_SUBDIRECTORY = [CtLiteralImpl]"overrides";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Name of a module's deltas directory.
     * Deltas of a prefab replace certain fields in an existing version of that prefab with their own fields.
     * More info on Overrides/Deltas: <a href="https://github.com/Terasology/TutorialAssetSystem/wiki/Deltas-and-Overrides">Deltas-and-Overrides</>
     */
    public static final [CtTypeReferenceImpl]java.lang.String DELTAS_SUBDIRECTORY = [CtLiteralImpl]"deltas";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Path to a module's info file.
     */
    public static final [CtTypeReferenceImpl]java.nio.file.Path MODULE_INFO_FILENAME = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"module.txt");

    [CtConstructorImpl]private TerasologyConstants() [CtBlockImpl]{
    }
}
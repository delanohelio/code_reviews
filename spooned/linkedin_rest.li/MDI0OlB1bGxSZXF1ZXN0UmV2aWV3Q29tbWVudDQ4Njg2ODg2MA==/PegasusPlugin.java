[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2019 LinkedIn Corp.

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
[CtPackageDeclarationImpl]package com.linkedin.pegasus.gradle;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.PublishRestModelTask;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.ValidateExtensionSchemaTask;
[CtUnresolvedImport]import org.gradle.api.plugins.JavaPlugin;
[CtUnresolvedImport]import org.gradle.plugins.ide.idea.IdeaPlugin;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.CheckSnapshotTask;
[CtUnresolvedImport]import org.gradle.plugins.ide.eclipse.EclipsePlugin;
[CtUnresolvedImport]import org.gradle.api.Task;
[CtUnresolvedImport]import org.gradle.api.tasks.javadoc.Javadoc;
[CtUnresolvedImport]import org.gradle.api.plugins.JavaBasePlugin;
[CtUnresolvedImport]import org.gradle.api.artifacts.ConfigurationContainer;
[CtImportImpl]import java.util.TreeSet;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.TranslateSchemasTask;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.CheckPegasusSnapshotTask;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.gradle.plugins.ide.idea.model.IdeaModule;
[CtUnresolvedImport]import org.gradle.api.tasks.Delete;
[CtUnresolvedImport]import org.gradle.api.Project;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.ChangedFileReportTask;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.GeneratePegasusSnapshotTask;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.PegasusOptions.IdlOptions;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.GenerateRestClientTask;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.GenerateRestModelTask;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import org.gradle.api.file.FileCollection;
[CtUnresolvedImport]import org.gradle.api.tasks.SourceSet;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import org.gradle.api.artifacts.Configuration;
[CtUnresolvedImport]import org.gradle.api.tasks.Sync;
[CtUnresolvedImport]import org.gradle.api.tasks.SourceSetContainer;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.CheckRestModelTask;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import org.gradle.api.tasks.compile.JavaCompile;
[CtUnresolvedImport]import org.gradle.api.tasks.bundling.Jar;
[CtUnresolvedImport]import org.gradle.api.plugins.JavaPluginConvention;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.ValidateSchemaAnnotationTask;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.gradle.plugins.ide.eclipse.model.EclipseModel;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.CheckIdlTask;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.GenerateDataTemplateTask;
[CtUnresolvedImport]import com.linkedin.pegasus.gradle.tasks.GenerateAvroSchemaTask;
[CtUnresolvedImport]import org.gradle.api.GradleException;
[CtUnresolvedImport]import org.gradle.api.Plugin;
[CtUnresolvedImport]import org.gradle.api.tasks.Copy;
[CtClassImpl][CtJavaDocImpl]/**
 * Pegasus code generation plugin.
 * The supported project layout for this plugin is as follows:
 *
 * <pre>
 *   --- api/
 *   |   --- build.gradle
 *   |   --- src/
 *   |       --- &lt;sourceSet&gt;/
 *   |       |   --- idl/
 *   |       |   |   --- &lt;published idl (.restspec.json) files&gt;
 *   |       |   --- java/
 *   |       |   |   --- &lt;packageName&gt;/
 *   |       |   |       --- &lt;common java files&gt;
 *   |       |   --- pegasus/
 *   |       |       --- &lt;packageName&gt;/
 *   |       |           --- &lt;data schema (.pdsc) files&gt;
 *   |       --- &lt;sourceSet&gt;GeneratedDataTemplate/
 *   |       |   --- java/
 *   |       |       --- &lt;packageName&gt;/
 *   |       |           --- &lt;data template source files generated from data schema (.pdsc) files&gt;
 *   |       --- &lt;sourceSet&gt;GeneratedAvroSchema/
 *   |       |   --- avro/
 *   |       |       --- &lt;packageName&gt;/
 *   |       |           --- &lt;avsc avro schema files (.avsc) generated from pegasus schema files&gt;
 *   |       --- &lt;sourceSet&gt;GeneratedRest/
 *   |           --- java/
 *   |               --- &lt;packageName&gt;/
 *   |                   --- &lt;rest client source (.java) files generated from published idl&gt;
 *   --- impl/
 *   |   --- build.gradle
 *   |   --- src/
 *   |       --- &lt;sourceSet&gt;/
 *   |       |   --- java/
 *   |       |       --- &lt;packageName&gt;/
 *   |       |           --- &lt;resource class source (.java) files&gt;
 *   |       --- &lt;sourceSet&gt;GeneratedRest/
 *   |           --- idl/
 *   |               --- &lt;generated idl (.restspec.json) files&gt;
 *   --- &lt;other projects&gt;/
 * </pre>
 * <ul>
 *   <li>
 *    <i>api</i>: contains all the files which are commonly depended by the server and
 *    client implementation. The common files include the data schema (.pdsc) files,
 *    the idl (.restspec.json) files and potentially Java interface files used by both sides.
 *  </li>
 *  <li>
 *    <i>impl</i>: contains the resource class for server implementation.
 *  </li>
 * </ul>
 * <p>Performs the following functions:</p>
 *
 * <p><b>Generate data model and data template jars for each source set.</b></p>
 *
 * <p><i>Overview:</i></p>
 *
 * <p>
 * In the api project, the plugin generates the data template source (.java) files from the
 * data schema (.pdsc) files, and furthermore compiles the source files and packages them
 * to jar files. Details of jar contents will be explained in following paragraphs.
 * In general, data schema files should exist only in api projects.
 * </p>
 *
 * <p>
 * Configure the server and client implementation projects to depend on the
 * api project's dataTemplate configuration to get access to the generated data templates
 * from within these projects. This allows api classes to be built first so that implementation
 * projects can consume them. We recommend this structure to avoid circular dependencies
 * (directly or indirectly) among implementation projects.
 * </p>
 *
 * <p><i>Detail:</i></p>
 *
 * <p>
 * Generates data template source (.java) files from data schema (.pdsc) files,
 * compiles the data template source (.java) files into class (.class) files,
 * creates a data model jar file and a data template jar file.
 * The data model jar file contains the source data schema (.pdsc) files.
 * The data template jar file contains both the source data schema (.pdsc) files
 * and the generated data template class (.class) files.
 * </p>
 *
 * <p>
 * In the data template generation phase, the plugin creates a new target source set
 * for the generated files. The new target source set's name is the input source set name's
 * suffixed with "GeneratedDataTemplate", e.g. "mainGeneratedDataTemplate".
 * The plugin invokes PegasusDataTemplateGenerator to generate data template source (.java) files
 * for all data schema (.pdsc) files present in the input source set's pegasus
 * directory, e.g. "src/main/pegasus". The generated data template source (.java) files
 * will be in the new target source set's java source directory, e.g.
 * "src/mainGeneratedDataTemplate/java". In addition to
 * the data schema (.pdsc) files in the pegasus directory, the dataModel configuration
 * specifies resolver path for the PegasusDataTemplateGenerator. The resolver path
 * provides the data schemas and previously generated data template classes that
 * may be referenced by the input source set's data schemas. In most cases, the dataModel
 * configuration should contain data template jars.
 * </p>
 *
 * <p>
 * The next phase is the data template compilation phase, the plugin compiles the generated
 * data template source (.java) files into class files. The dataTemplateCompile configuration
 * specifies the pegasus jars needed to compile these classes. The compileClasspath of the
 * target source set is a composite of the dataModel configuration which includes the data template
 * classes that were previously generated and included in the dependent data template jars,
 * and the dataTemplateCompile configuration.
 * This configuration should specify a dependency on the Pegasus data jar.
 * </p>
 *
 * <p>
 * The following phase is creating the the data model jar and the data template jar.
 * This plugin creates the data model jar that includes the contents of the
 * input source set's pegasus directory, and sets the jar file's classification to
 * "data-model". Hence, the resulting jar file's name should end with "-data-model.jar".
 * It adds the data model jar as an artifact to the dataModel configuration.
 * This jar file should only contain data schema (.pdsc) files.
 * </p>
 *
 * <p>
 * This plugin also create the data template jar that includes the contents of the input
 * source set's pegasus directory and the java class output directory of the
 * target source set. It sets the jar file's classification to "data-template".
 * Hence, the resulting jar file's name should end with "-data-template.jar".
 * It adds the data template jar file as an artifact to the dataTemplate configuration.
 * This jar file contains both data schema (.pdsc) files and generated data template
 * class (.class) files.
 * </p>
 *
 * <p>
 * This plugin will ensure that data template source files are generated before
 * compiling the input source set and before the idea and eclipse tasks. It
 * also adds the generated classes to the compileClasspath of the input source set.
 * </p>
 *
 * <p>
 * The configurations that apply to generating the data model and data template jars
 * are as follow:
 * <ul>
 *   <li>
 *     The dataTemplateCompile configuration specifies the classpath for compiling
 *     the generated data template source (.java) files. In most cases,
 *     it should be the Pegasus data jar.
 *     (The default compile configuration is not used for compiling data templates because
 *     it is not desirable to include non data template dependencies in the data template jar.)
 *     The configuration should not directly include data template jars. Data template jars
 *     should be included in the dataModel configuration.
 *   </li>
 *   <li>
 *     The dataModel configuration provides the value of the "generator.resolver.path"
 *     system property that is passed to PegasusDataTemplateGenerator. In most cases,
 *     this configuration should contain only data template jars. The data template jars
 *     contain both data schema (.pdsc) files and generated data template (.class) files.
 *     PegasusDataTemplateGenerator will not generate data template (.java) files for
 *     classes that can be found in the resolver path. This avoids redundant generation
 *     of the same classes, and inclusion of these classes in multiple jars.
 *     The dataModel configuration is also used to publish the data model jar which
 *     contains only data schema (.pdsc) files.
 *   </li>
 *   <li>
 *     The testDataModel configuration is similar to the dataModel configuration
 *     except it is used when generating data templates from test source sets.
 *     It extends from the dataModel configuration. It is also used to publish
 *     the data model jar from test source sets.
 *   </li>
 *   <li>
 *     The dataTemplate configuration is used to publish the data template
 *     jar which contains both data schema (.pdsc) files and the data template class
 *     (.class) files generated from these data schema (.pdsc) files.
 *   </li>
 *   <li>
 *     The testDataTemplate configuration is similar to the dataTemplate configuration
 *     except it is used when publishing the data template jar files generated from
 *     test source sets.
 *   </li>
 * </ul>
 * </p>
 *
 * <p>Performs the following functions:</p>
 *
 * <p><b>Generate avro schema jars for each source set.</b></p>
 *
 * <p><i>Overview:</i></p>
 *
 * <p>
 * In the api project, the task 'generateAvroSchema' generates the avro schema (.avsc)
 * files from pegasus schema (.pdsc) files. In general, data schema files should exist
 * only in api projects.
 * </p>
 *
 * <p>
 * Configure the server and client implementation projects to depend on the
 * api project's avroSchema configuration to get access to the generated avro schemas
 * from within these projects.
 * </p>
 *
 * <p>
 * This plugin also create the avro schema jar that includes the contents of the input
 * source set's avro directory and the avsc schema files.
 * The resulting jar file's name should end with "-avro-schema.jar".
 * </p>
 *
 * <p><b>Generate rest model and rest client jars for each source set.</b></p>
 *
 * <p><i>Overview:</i></p>
 *
 * <p>
 * In the api project, generates rest client source (.java) files from the idl,
 * compiles the rest client source (.java) files to rest client class (.class) files
 * and puts them in jar files. In general, the api project should be only place that
 * contains the publishable idl files. If the published idl changes an existing idl
 * in the api project, the plugin will emit message indicating this has occurred and
 * suggest that the entire project be rebuilt if it is desirable for clients of the
 * idl to pick up the newly published changes.
 * </p>
 *
 * <p>
 * In the impl project, generates the idl (.restspec.json) files from the input
 * source set's resource class files, then compares them against the existing idl
 * files in the api project for compatibility checking. If incompatible changes are
 * found, the build fails (unless certain flag is specified, see below). If the
 * generated idl passes compatibility checks (see compatibility check levels below),
 * publishes the generated idl (.restspec.json) to the api project.
 * </p>
 *
 * <p><i>Detail:</i></p>
 *
 * <p><b>rest client generation phase</b>: in api project</p>
 *
 * <p>
 * In this phase, the rest client source (.java) files are generated from the
 * api project idl (.restspec.json) files using RestRequestBuilderGenerator.
 * The generated rest client source files will be in the new target source set's
 * java source directory, e.g. "src/mainGeneratedRest/java".
 * </p>
 *
 * <p>
 * RestRequestBuilderGenerator requires access to the data schemas referenced
 * by the idl. The dataModel configuration specifies the resolver path needed
 * by RestRequestBuilderGenerator to access the data schemas referenced by
 * the idl that is not in the source set's pegasus directory.
 * This plugin automatically includes the data schema (.pdsc) files in the
 * source set's pegasus directory in the resolver path.
 * In most cases, the dataModel configuration should contain data template jars.
 * The data template jars contains both data schema (.pdsc) files and generated
 * data template class (.class) files. By specifying data template jars instead
 * of data model jars, redundant generation of data template classes is avoided
 * as classes that can be found in the resolver path are not generated.
 * </p>
 *
 * <p><b>rest client compilation phase</b>: in api project</p>
 *
 * <p>
 * In this phase, the plugin compiles the generated rest client source (.java)
 * files into class files. The restClientCompile configuration specifies the
 * pegasus jars needed to compile these classes. The compile classpath is a
 * composite of the dataModel configuration which includes the data template
 * classes that were previously generated and included in the dependent data template
 * jars, and the restClientCompile configuration.
 * This configuration should specify a dependency on the Pegasus restli-client jar.
 * </p>
 *
 * <p>
 * The following stage is creating the the rest model jar and the rest client jar.
 * This plugin creates the rest model jar that includes the
 * generated idl (.restspec.json) files, and sets the jar file's classification to
 * "rest-model". Hence, the resulting jar file's name should end with "-rest-model.jar".
 * It adds the rest model jar as an artifact to the restModel configuration.
 * This jar file should only contain idl (.restspec.json) files.
 * </p>
 *
 * <p>
 * This plugin also create the rest client jar that includes the generated
 * idl (.restspec.json) files and the java class output directory of the
 * target source set. It sets the jar file's classification to "rest-client".
 * Hence, the resulting jar file's name should end with "-rest-client.jar".
 * It adds the rest client jar file as an artifact to the restClient configuration.
 * This jar file contains both idl (.restspec.json) files and generated rest client
 * class (.class) files.
 * </p>
 *
 * <p><b>idl generation phase</b>: in server implementation project</p>
 *
 * <p>
 * Before entering this phase, the plugin will ensure that generating idl will
 * occur after compiling the input source set. It will also ensure that IDEA
 * and Eclipse tasks runs after  rest client source (.java) files are generated.
 * </p>
 *
 * <p>
 * In this phase, the plugin creates a new target source set for the generated files.
 * The new target source set's name is the input source set name's* suffixed with
 * "GeneratedRest", e.g. "mainGeneratedRest". The plugin invokes
 * RestLiResourceModelExporter to generate idl (.restspec.json) files for each
 * IdlItem in the input source set's pegasus IdlOptions. The generated idl files
 * will be in target source set's idl directory, e.g. "src/mainGeneratedRest/idl".
 * For example, the following adds an IdlItem to the source set's pegasus IdlOptions.
 * This line should appear in the impl project's build.gradle. If no IdlItem is added,
 * this source set will be excluded from generating idl and checking idl compatibility,
 * even there are existing idl files.
 * <pre>
 *   pegasus.main.idlOptions.addIdlItem(["com.linkedin.restli.examples.groups.server"])
 * </pre>
 * </p>
 *
 * <p>
 * After the idl generation phase, each included idl file is checked for compatibility against
 * those in the api project. In case the current interface breaks compatibility,
 * by default the build fails and reports all compatibility errors and warnings. Otherwise,
 * the build tasks in the api project later will package the resource classes into jar files.
 * User can change the compatibility requirement between the current and published idl by
 * setting the "rest.model.compatibility" project property, i.e.
 * "gradle -Prest.model.compatibility=<strategy> ..." The following levels are supported:
 * <ul>
 *   <li><b>ignore</b>: idl compatibility check will occur but its result will be ignored.
 *   The result will be aggregated and printed at the end of the build.</li>
 *   <li><b>backwards</b>: build fails if there are backwards incompatible changes in idl.
 *   Build continues if there are only compatible changes.</li>
 *   <li><b>equivalent (default)</b>: build fails if there is any functional changes (compatible or
 *   incompatible) in the current idl. Only docs and comments are allowed to be different.</li>
 * </ul>
 * The plugin needs to know where the api project is. It searches the api project in the
 * following steps. If all searches fail, the build fails.
 * <ol>
 *   <li>
 *     Use the specified project from the impl project build.gradle file. The <i>ext.apiProject</i>
 *     property explicitly assigns the api project. E.g.
 *     <pre>
 *       ext.apiProject = project(':groups:groups-server-api')
 *     </pre>
 *     If multiple such statements exist, the last will be used. Wrong project path causes Gradle
 *     evaluation error.
 *   </li>
 *   <li>
 *     If no <i>ext.apiProject</i> property is defined, the plugin will try to guess the
 *     api project name with the following conventions. The search stops at the first successful match.
 *     <ol>
 *       <li>
 *         If the impl project name ends with the following suffixes, substitute the suffix with "-api".
 *           <ol>
 *             <li>-impl</li>
 *             <li>-service</li>
 *             <li>-server</li>
 *             <li>-server-impl</li>
 *           </ol>
 *         This list can be overridden by inserting the following line to the project build.gradle:
 *         <pre>
 *           ext.apiProjectSubstitutionSuffixes = ['-new-suffix-1', '-new-suffix-2']
 *         </pre>
 *         Alternatively, this setting could be applied globally to all projects by putting it in
 *         the <i>subprojects</i> section of the root build.gradle.</b>
 *       </li>
 *       <li>
 *         Append "-api" to the impl project name.
 *       </li>
 *     </ol>
 *   </li>
 * </ol>
 * The plugin invokes RestLiResourceModelCompatibilityChecker to check compatibility.
 * </p>
 *
 * <p>
 * The idl files in the api project are not generated by the plugin, but rather
 * "published" from the impl project. The publishRestModel task is used to copy the
 * idl files to the api project. This task is invoked automatically if the idls are
 * verified to be "safe". "Safe" is determined by the "rest.model.compatibility"
 * property. Because this task is skipped if the idls are functionally equivalent
 * (not necessarily identical, e.g. differ in doc fields), if the default "equivalent"
 * compatibility level is used, no file will be copied. If such automatic publishing
 * is intended to be skip, set the "rest.model.skipPublish" property to true.
 * Note that all the properties are per-project and can be overridden in each project's
 * build.gradle file.
 * </p>
 *
 * <p>
 * Please always keep in mind that if idl publishing is happened, a subsequent whole-project
 * rebuild is necessary to pick up the changes. Otherwise, the Hudson job will fail and
 * the source code commit will fail.
 * </p>
 *
 * <p>
 * The configurations that apply to generating the rest model and rest client jars
 * are as follow:
 * <ul>
 *   <li>
 *     The restClientCompile configuration specifies the classpath for compiling
 *     the generated rest client source (.java) files. In most cases,
 *     it should be the Pegasus restli-client jar.
 *     (The default compile configuration is not used for compiling rest client because
 *     it is not desirable to include non rest client dependencies, such as
 *     the rest server implementation classes, in the data template jar.)
 *     The configuration should not directly include data template jars. Data template jars
 *     should be included in the dataModel configuration.
 *   </li>
 *   <li>
 *     The dataModel configuration provides the value of the "generator.resolver.path"
 *     system property that is passed to RestRequestBuilderGenerator.
 *     This configuration should contain only data template jars. The data template jars
 *     contain both data schema (.pdsc) files and generated data template (.class) files.
 *     The RestRequestBuilderGenerator will only generate rest client classes.
 *     The dataModel configuration is also included in the compile classpath for the
 *     generated rest client source files. The dataModel configuration does not
 *     include generated data template classes, then the Java compiler may not able to
 *     find the data template classes referenced by the generated rest client.
 *   </li>
 *   <li>
 *     The testDataModel configuration is similar to the dataModel configuration
 *     except it is used when generating rest client source files from
 *     test source sets.
 *   </li>
 *   <li>
 *     The restModel configuration is used to publish the rest model jar
 *     which contains generated idl (.restspec.json) files.
 *   </li>
 *   <li>
 *     The testRestModel configuration is similar to the restModel configuration
 *     except it is used to publish rest model jar files generated from
 *     test source sets.
 *   </li>
 *   <li>
 *     The restClient configuration is used to publish the rest client jar
 *     which contains both generated idl (.restspec.json) files and
 *     the rest client class (.class) files generated from from these
 *     idl (.restspec.json) files.
 *   </li>
 *   <li>
 *     The testRestClient configuration is similar to the restClient configuration
 *     except it is used to publish rest client jar files generated from
 *     test source sets.
 *   </li>
 * </ul>
 * </p>
 *
 * <p>
 * This plugin considers test source sets whose names begin with 'test' or 'integTest' to be
 * test source sets.
 * </p>
 */
public class PegasusPlugin implements [CtTypeReferenceImpl]org.gradle.api.Plugin<[CtTypeReferenceImpl]org.gradle.api.Project> {
    [CtFieldImpl]public static [CtTypeReferenceImpl]boolean debug = [CtLiteralImpl]false;

    [CtFieldImpl][CtCommentImpl]// 
    [CtCommentImpl]// Constants for generating sourceSet names and corresponding directory names
    [CtCommentImpl]// for generated code
    [CtCommentImpl]// 
    private static final [CtTypeReferenceImpl]java.lang.String DATA_TEMPLATE_GEN_TYPE = [CtLiteralImpl]"DataTemplate";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String REST_GEN_TYPE = [CtLiteralImpl]"Rest";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String AVRO_SCHEMA_GEN_TYPE = [CtLiteralImpl]"AvroSchema";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String DATA_TEMPLATE_FILE_SUFFIX = [CtLiteralImpl]".pdsc";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PDL_FILE_SUFFIX = [CtLiteralImpl]".pdl";

    [CtFieldImpl][CtCommentImpl]// gradle property to opt OUT schema annotation validation, by default this feature is enabled.
    private static final [CtTypeReferenceImpl]java.lang.String DISABLE_SCHEMA_ANNOTATION_VALIDATION = [CtLiteralImpl]"schema.annotation.validation.disable";

    [CtFieldImpl][CtCommentImpl]// gradle property to opt in for destroying stale files from the build directory,
    [CtCommentImpl]// by default it is disabled, because it triggers hot-reload (even if it results in a no-op)
    private static final [CtTypeReferenceImpl]java.lang.String DESTROY_STALE_FILES_ENABLE = [CtLiteralImpl]"enableDestroyStaleFiles";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> DATA_TEMPLATE_FILE_SUFFIXES = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String IDL_FILE_SUFFIX = [CtLiteralImpl]".restspec.json";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SNAPSHOT_FILE_SUFFIX = [CtLiteralImpl]".snapshot.json";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SNAPSHOT_COMPAT_REQUIREMENT = [CtLiteralImpl]"rest.model.compatibility";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String IDL_COMPAT_REQUIREMENT = [CtLiteralImpl]"rest.idl.compatibility";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.regex.Pattern TEST_DIR_REGEX = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"^(integ)?[Tt]est");

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SNAPSHOT_NO_PUBLISH = [CtLiteralImpl]"rest.model.noPublish";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String IDL_NO_PUBLISH = [CtLiteralImpl]"rest.idl.noPublish";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SKIP_IDL_CHECK = [CtLiteralImpl]"rest.idl.skipCheck";

    [CtFieldImpl][CtCommentImpl]// gradle property to skip running GenerateRestModel task.
    [CtCommentImpl]// Note it affects GenerateRestModel task only, and does not skip tasks depends on GenerateRestModel.
    private static final [CtTypeReferenceImpl]java.lang.String SKIP_GENERATE_REST_MODEL = [CtLiteralImpl]"rest.model.skipGenerateRestModel";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SUPPRESS_REST_CLIENT_RESTLI_2 = [CtLiteralImpl]"rest.client.restli2.suppress";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SUPPRESS_REST_CLIENT_RESTLI_1 = [CtLiteralImpl]"rest.client.restli1.suppress";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String GENERATOR_CLASSLOADER_NAME = [CtLiteralImpl]"pegasusGeneratorClassLoader";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CONVERT_TO_PDL_REVERSE = [CtLiteralImpl]"convertToPdl.reverse";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CONVERT_TO_PDL_KEEP_ORIGINAL = [CtLiteralImpl]"convertToPdl.keepOriginal";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CONVERT_TO_PDL_SKIP_VERIFICATION = [CtLiteralImpl]"convertToPdl.skipVerification";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CONVERT_TO_PDL_PRESERVE_SOURCE_CMD = [CtLiteralImpl]"convertToPdl.preserveSourceCmd";

    [CtFieldImpl][CtCommentImpl]// Below variables are used to collect data across all pegasus projects (sub-projects) and then print information
    [CtCommentImpl]// to the user at the end after build is finished.
    private static [CtTypeReferenceImpl]java.lang.StringBuffer _restModelCompatMessage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuffer();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> _needCheckinFiles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> _needBuildFolders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> _possibleMissingFilesInEarlierCommit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String RUN_ONCE = [CtLiteralImpl]"runOnce";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.Object STATIC_PROJECT_EVALUATED_LOCK = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Object();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> UNUSED_CONFIGURATIONS = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"dataTemplateGenerator", [CtLiteralImpl]"restTools", [CtLiteralImpl]"avroSchemaGenerator");

    [CtFieldImpl][CtCommentImpl]// Directory in the dataTemplate jar that holds schemas translated from PDL to PDSC.
    private static final [CtTypeReferenceImpl]java.lang.String TRANSLATED_SCHEMAS_DIR = [CtLiteralImpl]"legacyPegasusSchemas";

    [CtFieldImpl][CtCommentImpl]// Enable the use of argFiles for the tasks that support them
    private static final [CtTypeReferenceImpl]java.lang.String ENABLE_ARG_FILE = [CtLiteralImpl]"pegasusPlugin.enableArgFile";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PEGASUS_PLUGIN_CONFIGURATION = [CtLiteralImpl]"pegasusPlugin";

    [CtFieldImpl][CtCommentImpl]// Enable the use of generic pegasus schema compatibility checker
    private static final [CtTypeReferenceImpl]java.lang.String ENABLE_PEGASUS_SCHEMA_COMPATIBILITY_CHECK = [CtLiteralImpl]"pegasusPlugin.enablePegasusSchemaCompatibilityCheck";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PEGASUS_SCHEMA_SNAPSHOT = [CtLiteralImpl]"PegasusSchemaSnapshot";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PEGASUS_EXTENSION_SCHEMA_SNAPSHOT = [CtLiteralImpl]"PegasusExtensionSchemaSnapshot";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PEGASUS_SCHEMA_SNAPSHOT_DIR = [CtLiteralImpl]"pegasusSchemaSnapshot";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PEGASUS_EXTENSION_SCHEMA_SNAPSHOT_DIR = [CtLiteralImpl]"pegasusExtensionSchemaSnapshot";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PEGASUS_SCHEMA_SNAPSHOT_DIR_OVERRIDE = [CtLiteralImpl]"overridePegasusSchemaSnapshotDir";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PEGASUS_EXTENSION_SCHEMA_SNAPSHOT_DIR_OVERRIDE = [CtLiteralImpl]"overridePegasusExtensionSchemaSnapshotDir";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ASSEMBLE = [CtLiteralImpl]"assemble";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SRC = [CtLiteralImpl]"src";

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    private [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.gradle.api.Plugin<[CtTypeReferenceImpl]org.gradle.api.Project>> _thisPluginType = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.gradle.api.Plugin<[CtTypeReferenceImpl]org.gradle.api.Project>>) ([CtInvocationImpl]getClass().asSubclass([CtFieldReadImpl]org.gradle.api.Plugin.class)));

    [CtFieldImpl]private [CtTypeReferenceImpl]org.gradle.api.Task _generateSourcesJarTask;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.gradle.api.tasks.javadoc.Javadoc _generateJavadocTask;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.gradle.api.Task _generateJavadocJarTask;

    [CtMethodImpl]public [CtTypeReferenceImpl]void setPluginType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.gradle.api.Plugin<[CtTypeReferenceImpl]org.gradle.api.Project>> pluginType) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_thisPluginType = [CtVariableReadImpl]pluginType;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setSourcesJarTask([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Task sourcesJarTask) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_generateSourcesJarTask = [CtVariableReadImpl]sourcesJarTask;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setJavadocJarTask([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Task javadocJarTask) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_generateJavadocJarTask = [CtVariableReadImpl]javadocJarTask;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void apply([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPlugins().apply([CtFieldReadImpl]org.gradle.api.plugins.JavaPlugin.class);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPlugins().apply([CtFieldReadImpl]org.gradle.plugins.ide.idea.IdeaPlugin.class);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPlugins().apply([CtFieldReadImpl]org.gradle.plugins.ide.eclipse.EclipsePlugin.class);
        [CtInvocationImpl][CtCommentImpl]// this HashMap will have a PegasusOptions per sourceSet
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().set([CtLiteralImpl]"pegasus", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions>());
        [CtInvocationImpl][CtCommentImpl]// this map will extract PegasusOptions.GenerationMode to project property
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().set([CtLiteralImpl]"PegasusGenerationMode", [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtInvocationImpl][CtTypeAccessImpl]PegasusOptions.GenerationMode.values()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]PegasusOptions.GenerationMode::name, [CtInvocationImpl][CtTypeAccessImpl]java.util.function.Function.identity())));
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.STATIC_PROJECT_EVALUATED_LOCK) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Check if this is the first time the block will run. Pegasus plugin can run multiple times in a build if
            [CtCommentImpl]// multiple sub-projects applied the plugin.
            if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getRootProject().hasProperty([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.RUN_ONCE)) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getRootProject().property([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.RUN_ONCE))))) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getGradle().projectsEvaluated([CtLambdaImpl]([CtParameterImpl] gradle) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]gradle.getRootProject().subprojects([CtLambdaImpl]([CtParameterImpl] subproject) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]UNUSED_CONFIGURATIONS.forEach([CtLambdaImpl]([CtParameterImpl] configurationName) -> [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration conf = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]subproject.getConfigurations().findByName([CtVariableReadImpl]configurationName);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]conf != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]conf.isEmpty())) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]subproject.getLogger().warn([CtBinaryOperatorImpl][CtLiteralImpl]"*** Project {} declares dependency to unused configuration \"{}\". " + [CtLiteralImpl]"This configuration is deprecated and you can safely remove the dependency. ***", [CtInvocationImpl][CtVariableReadImpl]subproject.getPath(), [CtVariableReadImpl]configurationName);
                    }
                })));
                [CtInvocationImpl][CtCommentImpl]// Re-initialize the static variables as they might have stale values from previous run. With Gradle 3.0 and
                [CtCommentImpl]// gradle daemon enabled, the plugin class might not be loaded for every run.
                [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.DATA_TEMPLATE_FILE_SUFFIXES.clear();
                [CtInvocationImpl][CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.DATA_TEMPLATE_FILE_SUFFIXES.add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.DATA_TEMPLATE_FILE_SUFFIX);
                [CtInvocationImpl][CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.DATA_TEMPLATE_FILE_SUFFIXES.add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PDL_FILE_SUFFIX);
                [CtAssignmentImpl][CtFieldWriteImpl]com.linkedin.pegasus.gradle.PegasusPlugin._restModelCompatMessage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuffer();
                [CtInvocationImpl][CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin._needCheckinFiles.clear();
                [CtInvocationImpl][CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin._needBuildFolders.clear();
                [CtInvocationImpl][CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin._possibleMissingFilesInEarlierCommit.clear();
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getGradle().buildFinished([CtLambdaImpl]([CtParameterImpl] result) -> [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder endOfBuildMessage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]com.linkedin.pegasus.gradle.PegasusPlugin._restModelCompatMessage.length() > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]endOfBuildMessage.append([CtFieldReadImpl][CtFieldReferenceImpl]com.linkedin.pegasus.gradle.PegasusPlugin._restModelCompatMessage);
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_needCheckinFiles.isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]endOfBuildMessage.append([CtInvocationImpl]createModifiedFilesMessage([CtFieldReadImpl][CtFieldReferenceImpl]_needCheckinFiles, [CtFieldReadImpl][CtFieldReferenceImpl]_needBuildFolders));
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_possibleMissingFilesInEarlierCommit.isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]endOfBuildMessage.append([CtInvocationImpl]createPossibleMissingFilesMessage([CtFieldReadImpl][CtFieldReferenceImpl]_possibleMissingFilesInEarlierCommit));
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]endOfBuildMessage.length() > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.getGradle().getRootProject().getLogger().quiet([CtInvocationImpl][CtVariableReadImpl]endOfBuildMessage.toString());
                    }
                });
                [CtInvocationImpl][CtCommentImpl]// Set an extra property on the root project to indicate the initialization is complete for the current build.
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getRootProject().getExtensions().getExtraProperties().set([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.RUN_ONCE, [CtLiteralImpl]true);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.ConfigurationContainer configurations = [CtInvocationImpl][CtVariableReadImpl]project.getConfigurations();
        [CtInvocationImpl][CtCommentImpl]// configuration for getting the required classes to make pegasus call main methods
        [CtVariableReadImpl]configurations.maybeCreate([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_PLUGIN_CONFIGURATION);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for compiling generated data templates
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration dataTemplateCompile = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"dataTemplateCompile");
        [CtInvocationImpl][CtVariableReadImpl]dataTemplateCompile.setVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for running rest client generator
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration restClientCompile = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"restClientCompile");
        [CtInvocationImpl][CtVariableReadImpl]restClientCompile.setVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for running data template generator
        [CtCommentImpl]// DEPRECATED! This configuration is no longer used. Please stop using it.
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration dataTemplateGenerator = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"dataTemplateGenerator");
        [CtInvocationImpl][CtVariableReadImpl]dataTemplateGenerator.setVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for running rest client generator
        [CtCommentImpl]// DEPRECATED! This configuration is no longer used. Please stop using it.
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration restTools = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"restTools");
        [CtInvocationImpl][CtVariableReadImpl]restTools.setVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for running Avro schema generator
        [CtCommentImpl]// DEPRECATED! To skip avro schema generation, use PegasusOptions.generationModes
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration avroSchemaGenerator = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"avroSchemaGenerator");
        [CtInvocationImpl][CtVariableReadImpl]avroSchemaGenerator.setVisible([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for depending on data schemas and potentially generated data templates
        [CtCommentImpl]// and for publishing jars containing data schemas to the project artifacts for including in the ivy.xml
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration dataModel = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"dataModel");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration testDataModel = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"testDataModel");
        [CtInvocationImpl][CtVariableReadImpl]testDataModel.extendsFrom([CtVariableReadImpl]dataModel);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for depending on data schemas and potentially generated data templates
        [CtCommentImpl]// and for publishing jars containing data schemas to the project artifacts for including in the ivy.xml
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration avroSchema = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"avroSchema");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration testAvroSchema = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"testAvroSchema");
        [CtInvocationImpl][CtVariableReadImpl]testAvroSchema.extendsFrom([CtVariableReadImpl]avroSchema);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for depending on rest idl and potentially generated client builders
        [CtCommentImpl]// and for publishing jars containing rest idl to the project artifacts for including in the ivy.xml
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration restModel = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"restModel");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration testRestModel = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"testRestModel");
        [CtInvocationImpl][CtVariableReadImpl]testRestModel.extendsFrom([CtVariableReadImpl]restModel);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for publishing jars containing data schemas and generated data templates
        [CtCommentImpl]// to the project artifacts for including in the ivy.xml
        [CtCommentImpl]// 
        [CtCommentImpl]// published data template jars depends on the configurations used to compile the classes
        [CtCommentImpl]// in the jar, this includes the data models/templates used by the data template generator
        [CtCommentImpl]// and the classes used to compile the generated classes.
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration dataTemplate = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"dataTemplate");
        [CtInvocationImpl][CtVariableReadImpl]dataTemplate.extendsFrom([CtVariableReadImpl]dataTemplateCompile, [CtVariableReadImpl]dataModel);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration testDataTemplate = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"testDataTemplate");
        [CtInvocationImpl][CtVariableReadImpl]testDataTemplate.extendsFrom([CtVariableReadImpl]dataTemplate, [CtVariableReadImpl]testDataModel);
        [CtLocalVariableImpl][CtCommentImpl]// configuration for processing and validating schema annotation during build time.
        [CtCommentImpl]// 
        [CtCommentImpl]// The configuration contains dependencies to schema annotation handlers which would process schema annotations
        [CtCommentImpl]// and validate.
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration schemaAnnotationHandler = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"schemaAnnotationHandler");
        [CtLocalVariableImpl][CtCommentImpl]// configuration for publishing jars containing rest idl and generated client builders
        [CtCommentImpl]// to the project artifacts for including in the ivy.xml
        [CtCommentImpl]// 
        [CtCommentImpl]// published client builder jars depends on the configurations used to compile the classes
        [CtCommentImpl]// in the jar, this includes the data models/templates (potentially generated by this
        [CtCommentImpl]// project and) used by the data template generator and the classes used to compile
        [CtCommentImpl]// the generated classes.
        [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration restClient = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"restClient");
        [CtInvocationImpl][CtVariableReadImpl]restClient.extendsFrom([CtVariableReadImpl]restClientCompile, [CtVariableReadImpl]dataTemplate);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration testRestClient = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"testRestClient");
        [CtInvocationImpl][CtVariableReadImpl]testRestClient.extendsFrom([CtVariableReadImpl]restClient, [CtVariableReadImpl]testDataTemplate);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties properties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream inputStream = [CtInvocationImpl][CtInvocationImpl]getClass().getResourceAsStream([CtLiteralImpl]"/pegasus-version.properties");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inputStream != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]properties.load([CtVariableReadImpl]inputStream);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.gradle.api.GradleException([CtLiteralImpl]"Unable to read pegasus-version.properties file.", [CtVariableReadImpl]e);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String version = [CtInvocationImpl][CtVariableReadImpl]properties.getProperty([CtLiteralImpl]"pegasus.version");
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_PLUGIN_CONFIGURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"com.linkedin.pegasus:data:" + [CtVariableReadImpl]version);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_PLUGIN_CONFIGURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"com.linkedin.pegasus:data-avro-generator:" + [CtVariableReadImpl]version);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_PLUGIN_CONFIGURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"com.linkedin.pegasus:generator:" + [CtVariableReadImpl]version);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_PLUGIN_CONFIGURATION, [CtBinaryOperatorImpl][CtLiteralImpl]"com.linkedin.pegasus:restli-tools:" + [CtVariableReadImpl]version);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Unable to add pegasus dependencies to {}. Please be sure that " + [CtLiteralImpl]"'com.linkedin.pegasus:data', 'com.linkedin.pegasus:data-avro-generator', 'com.linkedin.pegasus:generator', 'com.linkedin.pegasus:restli-tools'") + [CtLiteralImpl]" are available on the configuration pegasusPlugin", [CtInvocationImpl][CtVariableReadImpl]project.getPath());
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_PLUGIN_CONFIGURATION, [CtLiteralImpl]"org.slf4j:slf4j-simple:1.7.2");
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_PLUGIN_CONFIGURATION, [CtInvocationImpl][CtVariableReadImpl]project.files([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"java.home") + [CtLiteralImpl]"/../lib/tools.jar"));
        [CtInvocationImpl][CtCommentImpl]// this call has to be here because:
        [CtCommentImpl]// 1) artifact cannot be published once projects has been evaluated, so we need to first
        [CtCommentImpl]// create the tasks and artifact handler, then progressively append sources
        [CtCommentImpl]// 2) in order to append sources progressively, the source and documentation tasks and artifacts must be
        [CtCommentImpl]// configured/created before configuring and creating the code generation tasks.
        configureGeneratedSourcesAndJavadoc([CtVariableReadImpl]project);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.ChangedFileReportTask changedFileReportTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtLiteralImpl]"changedFilesReport", [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.ChangedFileReportTask.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtLiteralImpl]"check").dependsOn([CtVariableReadImpl]changedFileReportTask);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSetContainer sourceSets = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConvention().getPlugin([CtFieldReadImpl]org.gradle.api.plugins.JavaPluginConvention.class).getSourceSets();
        [CtInvocationImpl][CtVariableReadImpl]sourceSets.all([CtLambdaImpl]([CtParameterImpl] sourceSet) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName().toLowerCase([CtVariableReadImpl]Locale.US).contains([CtLiteralImpl]"generated")) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl]checkAvroSchemaExist([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtLocalVariableImpl][CtCommentImpl]// the idl Generator input options will be inside the PegasusOptions class. Users of the
            [CtCommentImpl]// plugin can set the inputOptions in their build.gradle
            [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions> pegasusOptions = [CtInvocationImpl](([CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions>) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().get([CtLiteralImpl]"pegasus")));
            [CtInvocationImpl][CtVariableReadImpl]pegasusOptions.put([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions());
            [CtInvocationImpl][CtCommentImpl]// rest model generation could fail on incompatibility
            [CtCommentImpl]// if it can fail, fail it early
            configureRestModelGeneration([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_PEGASUS_SCHEMA_COMPATIBILITY_CHECK)) [CtBlockImpl]{
                [CtInvocationImpl]configurePegasusSchemaSnapshotGeneration([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            }
            [CtInvocationImpl]configurePegasusExtensionSchemaSnapshotGeneration([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtInvocationImpl]configureConversionUtilities([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.GenerateDataTemplateTask generateDataTemplateTask = [CtInvocationImpl]configureDataTemplateGeneration([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtInvocationImpl]configureAvroSchemaGeneration([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtInvocationImpl]configureRestClientGeneration([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]DISABLE_SCHEMA_ANNOTATION_VALIDATION)) [CtBlockImpl]{
                [CtInvocationImpl]configureSchemaAnnotationValidation([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtVariableReadImpl]generateDataTemplateTask);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task cleanGeneratedDirTask = [CtInvocationImpl][CtVariableReadImpl]project.task([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"clean", [CtLiteralImpl]"GeneratedDir"));
            [CtInvocationImpl][CtVariableReadImpl]cleanGeneratedDirTask.doLast([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl]deleteGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl][CtFieldReferenceImpl]REST_GEN_TYPE);
                [CtInvocationImpl]deleteGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl][CtFieldReferenceImpl]AVRO_SCHEMA_GEN_TYPE);
                [CtInvocationImpl]deleteGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl][CtFieldReferenceImpl]DATA_TEMPLATE_GEN_TYPE);
            }));
            [CtInvocationImpl][CtCommentImpl]// make clean depends on deleting the generated directories
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtLiteralImpl]"clean").dependsOn([CtVariableReadImpl]cleanGeneratedDirTask);
            [CtInvocationImpl][CtCommentImpl]// Set data schema directories as resource roots
            configureDataSchemaResourcesRoot([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
        });
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().set([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.GENERATOR_CLASSLOADER_NAME, [CtInvocationImpl][CtInvocationImpl]getClass().getClassLoader());
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void configureSchemaAnnotationValidation([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.GenerateDataTemplateTask generateDataTemplatesTask) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Task would execute based on the following order.
        [CtCommentImpl]// generateDataTemplatesTask -> validateSchemaAnnotationTask
        [CtCommentImpl]// Create ValidateSchemaAnnotation task
        [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.ValidateSchemaAnnotationTask validateSchemaAnnotationTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"validate", [CtLiteralImpl]"schemaAnnotation"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.ValidateSchemaAnnotationTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtInvocationImpl][CtVariableReadImpl]generateDataTemplatesTask.getInputDir());
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));[CtCommentImpl]// same resolver path as generateDataTemplatesTask

            [CtInvocationImpl][CtVariableReadImpl]task.setClassPath([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"schemaAnnotationHandler").plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION)).plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"runtime")));
            [CtInvocationImpl][CtVariableReadImpl]task.setHandlerJarPath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"schemaAnnotationHandler"));
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
        });
        [CtInvocationImpl][CtCommentImpl]// validateSchemaAnnotationTask depend on generateDataTemplatesTask
        [CtVariableReadImpl]validateSchemaAnnotationTask.dependsOn([CtVariableReadImpl]generateDataTemplatesTask);
        [CtInvocationImpl][CtCommentImpl]// Check depends on validateSchemaAnnotationTask.
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtLiteralImpl]"check").dependsOn([CtVariableReadImpl]validateSchemaAnnotationTask);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    protected [CtTypeReferenceImpl]void configureGeneratedSourcesAndJavadoc([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_generateJavadocTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtLiteralImpl]"generateJavadoc", [CtFieldReadImpl]org.gradle.api.tasks.javadoc.Javadoc.class);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_generateSourcesJarTask == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// 
            [CtCommentImpl]// configuration for publishing jars containing sources for generated classes
            [CtCommentImpl]// to the project artifacts for including in the ivy.xml
            [CtCommentImpl]// 
            [CtTypeReferenceImpl]org.gradle.api.artifacts.ConfigurationContainer configurations = [CtInvocationImpl][CtVariableReadImpl]project.getConfigurations();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration generatedSources = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"generatedSources");
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration testGeneratedSources = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"testGeneratedSources");
            [CtInvocationImpl][CtVariableReadImpl]testGeneratedSources.extendsFrom([CtVariableReadImpl]generatedSources);
            [CtAssignmentImpl][CtFieldWriteImpl]_generateSourcesJarTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtLiteralImpl]"generateSourcesJar", [CtFieldReadImpl]org.gradle.api.tasks.bundling.Jar.class, [CtLambdaImpl]([CtParameterImpl] jarTask) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]jarTask.setGroup([CtVariableReadImpl]JavaBasePlugin.DOCUMENTATION_GROUP);
                [CtInvocationImpl][CtVariableReadImpl]jarTask.setDescription([CtLiteralImpl]"Generates a jar file containing the sources for the generated Java classes.");
                [CtInvocationImpl][CtCommentImpl]// FIXME change to #getArchiveClassifier().set("sources"); breaks backwards-compatibility before 5.1
                [CtVariableReadImpl]jarTask.setClassifier([CtLiteralImpl]"sources");
            });
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"generatedSources", [CtFieldReadImpl]_generateSourcesJarTask);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_generateJavadocJarTask == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// 
            [CtCommentImpl]// configuration for publishing jars containing Javadoc for generated classes
            [CtCommentImpl]// to the project artifacts for including in the ivy.xml
            [CtCommentImpl]// 
            [CtTypeReferenceImpl]org.gradle.api.artifacts.ConfigurationContainer configurations = [CtInvocationImpl][CtVariableReadImpl]project.getConfigurations();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration generatedJavadoc = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"generatedJavadoc");
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration testGeneratedJavadoc = [CtInvocationImpl][CtVariableReadImpl]configurations.maybeCreate([CtLiteralImpl]"testGeneratedJavadoc");
            [CtInvocationImpl][CtVariableReadImpl]testGeneratedJavadoc.extendsFrom([CtVariableReadImpl]generatedJavadoc);
            [CtAssignmentImpl][CtFieldWriteImpl]_generateJavadocJarTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtLiteralImpl]"generateJavadocJar", [CtFieldReadImpl]org.gradle.api.tasks.bundling.Jar.class, [CtLambdaImpl]([CtParameterImpl] jarTask) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]jarTask.dependsOn([CtFieldReadImpl][CtFieldReferenceImpl]_generateJavadocTask);
                [CtInvocationImpl][CtVariableReadImpl]jarTask.setGroup([CtVariableReadImpl]JavaBasePlugin.DOCUMENTATION_GROUP);
                [CtInvocationImpl][CtVariableReadImpl]jarTask.setDescription([CtLiteralImpl]"Generates a jar file containing the Javadoc for the generated Java classes.");
                [CtInvocationImpl][CtCommentImpl]// FIXME change to #getArchiveClassifier().set("sources"); breaks backwards-compatibility before 5.1
                [CtVariableReadImpl]jarTask.setClassifier([CtLiteralImpl]"javadoc");
                [CtInvocationImpl][CtVariableReadImpl]jarTask.from([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_generateJavadocTask.getDestinationDir());
            });
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"generatedJavadoc", [CtFieldReadImpl]_generateJavadocJarTask);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// TODO: Tighten the types so that _generateJavadocJarTask must be of type Jar.
            [CtFieldReadImpl](([CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar) (_generateJavadocJarTask)).from([CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.getDestinationDir());
            [CtInvocationImpl][CtFieldReadImpl]_generateJavadocJarTask.dependsOn([CtFieldReadImpl]_generateJavadocTask);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void deleteGeneratedDir([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dirType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String generatedDirPath = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedDirPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtVariableReadImpl]dirType);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"Delete generated directory {}", [CtVariableReadImpl]generatedDirPath);
        [CtInvocationImpl][CtVariableReadImpl]project.delete([CtVariableReadImpl]generatedDirPath);
    }

    [CtMethodImpl]private static <[CtTypeParameterImpl]E extends [CtTypeReferenceImpl]java.lang.Enum<[CtTypeParameterReferenceImpl]E>> [CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]E> getCompatibilityLevelClass([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader generatorClassLoader = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.ClassLoader) ([CtVariableReadImpl]project.property([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.GENERATOR_CLASSLOADER_NAME)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String className = [CtLiteralImpl]"com.linkedin.restli.tools.idlcheck.CompatibilityLevel";
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            [CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]E> enumClass = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]E>) ([CtInvocationImpl][CtVariableReadImpl]generatorClassLoader.loadClass([CtVariableReadImpl]className).asSubclass([CtFieldReadImpl]java.lang.Enum.class)));
            [CtReturnImpl]return [CtVariableReadImpl]enumClass;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"Could not load class " + [CtVariableReadImpl]className);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void addGeneratedDir([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration> configurations) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPlugins().withType([CtFieldReadImpl]org.gradle.plugins.ide.idea.IdeaPlugin.class, [CtLambdaImpl]([CtParameterImpl] ideaPlugin) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.plugins.ide.idea.model.IdeaModule ideaModule = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ideaPlugin.getModel().getModule();
            [CtIfImpl][CtCommentImpl]// stupid if block needed because of stupid assignment required to update source dirs
            if ([CtInvocationImpl]isTestSourceSet([CtVariableReadImpl]sourceSet)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]java.io.File> sourceDirs = [CtInvocationImpl][CtVariableReadImpl]ideaModule.getTestSourceDirs();
                [CtInvocationImpl][CtVariableReadImpl]sourceDirs.addAll([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getJava().getSrcDirs());
                [CtInvocationImpl][CtCommentImpl]// this is stupid but assignment is required
                [CtVariableReadImpl]ideaModule.setTestSourceDirs([CtVariableReadImpl]sourceDirs);
                [CtIfImpl]if ([CtFieldReadImpl][CtFieldReferenceImpl]com.linkedin.pegasus.gradle.PegasusPlugin.debug) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Added " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getJava().getSrcDirs()) + [CtLiteralImpl]" to IdeaModule testSourceDirs ") + [CtInvocationImpl][CtVariableReadImpl]ideaModule.getTestSourceDirs());
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]java.io.File> sourceDirs = [CtInvocationImpl][CtVariableReadImpl]ideaModule.getSourceDirs();
                [CtInvocationImpl][CtVariableReadImpl]sourceDirs.addAll([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getJava().getSrcDirs());
                [CtInvocationImpl][CtCommentImpl]// this is stupid but assignment is required
                [CtVariableReadImpl]ideaModule.setSourceDirs([CtVariableReadImpl]sourceDirs);
                [CtIfImpl]if ([CtFieldReadImpl][CtFieldReferenceImpl]com.linkedin.pegasus.gradle.PegasusPlugin.debug) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Added " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getJava().getSrcDirs()) + [CtLiteralImpl]" to  IdeaModule sourceDirs ") + [CtInvocationImpl][CtVariableReadImpl]ideaModule.getSourceDirs());
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Collection<[CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration> compilePlus = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ideaModule.getScopes().get([CtLiteralImpl]"COMPILE").get([CtLiteralImpl]"plus");
            [CtInvocationImpl][CtVariableReadImpl]compilePlus.addAll([CtVariableReadImpl]configurations);
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ideaModule.getScopes().get([CtLiteralImpl]"COMPILE").put([CtLiteralImpl]"plus", [CtVariableReadImpl]compilePlus);
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void checkAvroSchemaExist([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sourceDir = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"src" + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File avroSourceDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]sourceDir + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"avro");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]avroSourceDir.exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtLiteralImpl]"{}'s {} has non-empty avro directory. pegasus plugin does not process avro directory", [CtInvocationImpl][CtVariableReadImpl]project.getName(), [CtVariableReadImpl]sourceDir);
        }
    }

    [CtMethodImpl][CtCommentImpl]// Compute the name of the source set that will contain a type of an input generated code.
    [CtCommentImpl]// e.g. genType may be 'DataTemplate' or 'Rest'
    private static [CtTypeReferenceImpl]java.lang.String getGeneratedSourceSetName([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String genType) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"Generated") + [CtVariableReadImpl]genType;
    }

    [CtMethodImpl][CtCommentImpl]// Compute the directory name that will contain a type generated code of an input source set.
    [CtCommentImpl]// e.g. genType may be 'DataTemplate' or 'Rest'
    public static [CtTypeReferenceImpl]java.lang.String getGeneratedDirPath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String genType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getOverridePath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtLiteralImpl]"overrideGeneratedDir");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sourceSetName = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedSourceSetName([CtVariableReadImpl]sourceSet, [CtVariableReadImpl]genType);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String base = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]override == [CtLiteralImpl]null) ? [CtLiteralImpl]"src" : [CtVariableReadImpl]override;
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]base + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtVariableReadImpl]sourceSetName;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getDataSchemaPath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getOverridePath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtLiteralImpl]"overridePegasusDir");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]override == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"src" + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"pegasus";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]override;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getExtensionSchemaPath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getOverridePath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtLiteralImpl]"overrideExtensionSchemaDir");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]override == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"src" + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"extensions";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]override;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getSnapshotPath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getOverridePath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtLiteralImpl]"overrideSnapshotDir");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]override == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"src" + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"snapshot";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]override;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getIdlPath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getOverridePath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtLiteralImpl]"overrideIdlDir");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]override == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"src" + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"idl";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]override;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getPegasusSchemaSnapshotPath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getOverridePath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_SCHEMA_SNAPSHOT_DIR_OVERRIDE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]override == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.SRC + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_SCHEMA_SNAPSHOT_DIR;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]override;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getPegasusExtensionSchemaSnapshotPath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getOverridePath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_EXTENSION_SCHEMA_SNAPSHOT_DIR_OVERRIDE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]override == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.SRC + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_EXTENSION_SCHEMA_SNAPSHOT_DIR;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]override;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getOverridePath([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String overridePropertyName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sourceSetPropertyName = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]'.') + [CtVariableReadImpl]overridePropertyName;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getNonEmptyProperty([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSetPropertyName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]override == [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName().equals([CtLiteralImpl]"main")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]override = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getNonEmptyProperty([CtVariableReadImpl]project, [CtVariableReadImpl]overridePropertyName);
        }
        [CtReturnImpl]return [CtVariableReadImpl]override;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isTestSourceSet([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.TEST_DIR_REGEX.matcher([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()).find();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration getDataModelConfig([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isTestSourceSet([CtVariableReadImpl]sourceSet) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"testDataModel") : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataModel");
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isTaskSuccessful([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Task task) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getState().getExecuted() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getState().getSkipped())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getState().getFailure() == [CtLiteralImpl]null);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void configureRestModelGeneration([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getAllSource().isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"No source files found for sourceSet {}.  Skipping idl generation.", [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName());
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtCommentImpl]// afterEvaluate needed so that api project can be overridden via ext.apiProject
        [CtVariableReadImpl]project.afterEvaluate([CtLambdaImpl]([CtParameterImpl] p) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// find api project here instead of in each project's plugin configuration
            [CtCommentImpl]// this allows api project relation options (ext.api*) to be specified anywhere in the build.gradle file
            [CtCommentImpl]// alternatively, pass closures to task configuration, and evaluate the closures when task is executed
            [CtTypeReferenceImpl]org.gradle.api.Project apiProject = [CtInvocationImpl]getCheckedApiProject([CtVariableReadImpl]project);
            [CtIfImpl][CtCommentImpl]// make sure the api project is evaluated. Important for configure-on-demand mode.
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]apiProject != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]project.evaluationDependsOn([CtInvocationImpl][CtVariableReadImpl]apiProject.getPath());
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]apiProject.getPlugins().hasPlugin([CtFieldReadImpl][CtFieldReferenceImpl]_thisPluginType)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]apiProject = [CtLiteralImpl]null;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]apiProject == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task untypedJarTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().findByName([CtInvocationImpl][CtVariableReadImpl]sourceSet.getJarTaskName());
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]untypedJarTask instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar jarTask = [CtVariableReadImpl](([CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar) (untypedJarTask));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String snapshotCompatPropertyName = [CtInvocationImpl]findProperty([CtVariableReadImpl]FileCompatibilityType.SNAPSHOT);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]project.hasProperty([CtVariableReadImpl]snapshotCompatPropertyName) && [CtInvocationImpl][CtLiteralImpl]"off".equalsIgnoreCase([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]project.property([CtVariableReadImpl]snapshotCompatPropertyName))))) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtLiteralImpl]"Project {} snapshot compatibility level \"OFF\" is deprecated. Default to \"IGNORE\".", [CtInvocationImpl][CtVariableReadImpl]project.getPath());
            }
            [CtLocalVariableImpl][CtCommentImpl]// generate the rest model
            [CtTypeReferenceImpl]org.gradle.api.file.FileCollection restModelCodegenClasspath = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION).plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"runtime")).plus([CtInvocationImpl][CtVariableReadImpl]sourceSet.getRuntimeClasspath());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String destinationDirPrefix = [CtBinaryOperatorImpl][CtInvocationImpl]getGeneratedDirPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl][CtFieldReferenceImpl]REST_GEN_TYPE) + [CtVariableReadImpl]File.separatorChar;
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.file.FileCollection restModelResolverPath = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]apiProject.files([CtInvocationImpl]getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet)).plus([CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]apiProject, [CtVariableReadImpl]sourceSet));
            [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]java.io.File> watchedRestModelInputDirs = [CtInvocationImpl]buildWatchedRestModelInputDirs([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]java.io.File> restModelInputDirs = [CtInvocationImpl]difference([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getAllSource().getSrcDirs(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getResources().getSrcDirs());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task generateRestModelTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"generate", [CtLiteralImpl]"restModel"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.GenerateRestModelTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtInvocationImpl][CtVariableReadImpl]sourceSet.getClassesTaskName()));
                [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtVariableReadImpl]restModelCodegenClasspath);
                [CtInvocationImpl][CtVariableReadImpl]task.setWatchedCodegenClasspath([CtInvocationImpl][CtVariableReadImpl]restModelCodegenClasspath.filter([CtLambdaImpl]([CtParameterImpl] file) -> [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtLiteralImpl]"main".equals([CtInvocationImpl][CtVariableReadImpl]file.getName())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtLiteralImpl]"classes".equals([CtInvocationImpl][CtVariableReadImpl]file.getName()))));
                [CtInvocationImpl][CtVariableReadImpl]task.setInputDirs([CtVariableReadImpl]restModelInputDirs);
                [CtInvocationImpl][CtVariableReadImpl]task.setWatchedInputDirs([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]watchedRestModelInputDirs.isEmpty() ? [CtVariableReadImpl]restModelInputDirs : [CtVariableReadImpl]watchedRestModelInputDirs);
                [CtInvocationImpl][CtCommentImpl]// we need all the artifacts from runtime for any private implementation classes the server code might need.
                [CtVariableReadImpl]task.setSnapshotDestinationDir([CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtVariableReadImpl]destinationDirPrefix + [CtLiteralImpl]"snapshot"));
                [CtInvocationImpl][CtVariableReadImpl]task.setIdlDestinationDir([CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtVariableReadImpl]destinationDirPrefix + [CtLiteralImpl]"idl"));
                [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
                [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions> pegasusOptions = [CtInvocationImpl](([CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions>) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().get([CtLiteralImpl]"pegasus")));
                [CtInvocationImpl][CtVariableReadImpl]task.setIdlOptions([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]pegasusOptions.get([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()).idlOptions);
                [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtVariableReadImpl]restModelResolverPath);
                [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
                }
                [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtUnaryOperatorImpl]![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_GENERATE_REST_MODEL));
                [CtInvocationImpl][CtVariableReadImpl]task.doFirst([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl]deleteGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl][CtFieldReferenceImpl]REST_GEN_TYPE)));
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File apiSnapshotDir = [CtInvocationImpl][CtVariableReadImpl]apiProject.file([CtInvocationImpl]getSnapshotPath([CtVariableReadImpl]apiProject, [CtVariableReadImpl]sourceSet));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File apiIdlDir = [CtInvocationImpl][CtVariableReadImpl]apiProject.file([CtInvocationImpl]getIdlPath([CtVariableReadImpl]apiProject, [CtVariableReadImpl]sourceSet));
            [CtInvocationImpl][CtVariableReadImpl]apiSnapshotDir.mkdirs();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]apiIdlDir.mkdirs();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.CheckRestModelTask checkRestModelTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"check", [CtLiteralImpl]"RestModel"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.CheckRestModelTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]generateRestModelTask);
                [CtInvocationImpl][CtVariableReadImpl]task.setCurrentSnapshotFiles([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSnapshotFiles([CtVariableReadImpl]project, [CtVariableReadImpl]destinationDirPrefix));
                [CtInvocationImpl][CtVariableReadImpl]task.setPreviousSnapshotDirectory([CtVariableReadImpl]apiSnapshotDir);
                [CtInvocationImpl][CtVariableReadImpl]task.setCurrentIdlFiles([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getIdlFiles([CtVariableReadImpl]project, [CtVariableReadImpl]destinationDirPrefix));
                [CtInvocationImpl][CtVariableReadImpl]task.setPreviousIdlDirectory([CtVariableReadImpl]apiIdlDir);
                [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
                [CtInvocationImpl][CtVariableReadImpl]task.setModelCompatLevel([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.PropertyUtil.findCompatLevel([CtVariableReadImpl]project, [CtVariableReadImpl]FileCompatibilityType.SNAPSHOT));
                [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtUnaryOperatorImpl]![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK));
                [CtInvocationImpl][CtVariableReadImpl]task.doLast([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] t) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]task.isEquivalent()) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]com.linkedin.pegasus.gradle.PegasusPlugin._restModelCompatMessage.append([CtInvocationImpl][CtVariableReadImpl]task.getWholeMessage());
                    }
                }));
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.CheckSnapshotTask checkSnapshotTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"check", [CtLiteralImpl]"Snapshot"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.CheckSnapshotTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]generateRestModelTask);
                [CtInvocationImpl][CtVariableReadImpl]task.setCurrentSnapshotFiles([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSnapshotFiles([CtVariableReadImpl]project, [CtVariableReadImpl]destinationDirPrefix));
                [CtInvocationImpl][CtVariableReadImpl]task.setPreviousSnapshotDirectory([CtVariableReadImpl]apiSnapshotDir);
                [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
                [CtInvocationImpl][CtVariableReadImpl]task.setSnapshotCompatLevel([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.PropertyUtil.findCompatLevel([CtVariableReadImpl]project, [CtVariableReadImpl]FileCompatibilityType.SNAPSHOT));
                [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK));
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.CheckIdlTask checkIdlTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"check", [CtLiteralImpl]"Idl"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.CheckIdlTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]generateRestModelTask);
                [CtInvocationImpl][CtVariableReadImpl]task.setCurrentIdlFiles([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getIdlFiles([CtVariableReadImpl]project, [CtVariableReadImpl]destinationDirPrefix));
                [CtInvocationImpl][CtVariableReadImpl]task.setPreviousIdlDirectory([CtVariableReadImpl]apiIdlDir);
                [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtVariableReadImpl]restModelResolverPath);
                [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
                [CtInvocationImpl][CtVariableReadImpl]task.setIdlCompatLevel([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.PropertyUtil.findCompatLevel([CtVariableReadImpl]project, [CtVariableReadImpl]FileCompatibilityType.IDL));
                [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
                }
                [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtLiteralImpl]"OFF".equals([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.PropertyUtil.findCompatLevel([CtVariableReadImpl]project, [CtVariableReadImpl]FileCompatibilityType.IDL))));
            });
            [CtLocalVariableImpl][CtCommentImpl]// rest model publishing involves cross-project reference
            [CtCommentImpl]// configure after all projects have been evaluated
            [CtCommentImpl]// the file copy can be turned off by "rest.model.noPublish" flag
            [CtTypeReferenceImpl]org.gradle.api.Task publishRestliSnapshotTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"publish", [CtLiteralImpl]"RestliSnapshot"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.PublishRestModelTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]checkRestModelTask, [CtVariableReadImpl]checkSnapshotTask, [CtVariableReadImpl]checkIdlTask);
                [CtInvocationImpl][CtVariableReadImpl]task.from([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSnapshotFiles([CtVariableReadImpl]project, [CtVariableReadImpl]destinationDirPrefix));
                [CtInvocationImpl][CtVariableReadImpl]task.into([CtVariableReadImpl]apiSnapshotDir);
                [CtInvocationImpl][CtVariableReadImpl]task.setSuffix([CtFieldReadImpl][CtFieldReferenceImpl]SNAPSHOT_FILE_SUFFIX);
                [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"IDL_NO_PUBLISH: {}\n" + [CtLiteralImpl]"SNAPSHOT_NO_PUBLISH: {}\n") + [CtLiteralImpl]"checkRestModelTask:") + [CtLiteralImpl]" Executed: {}") + [CtLiteralImpl]", Not Skipped: {}") + [CtLiteralImpl]", No Failure: {}") + [CtLiteralImpl]", Is Not Equivalent: {}\n") + [CtLiteralImpl]"checkSnapshotTask:") + [CtLiteralImpl]" Executed: {}") + [CtLiteralImpl]", Not Skipped: {}") + [CtLiteralImpl]", No Failure: {}") + [CtLiteralImpl]", Is Not Equivalent: {}\n", [CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]IDL_NO_PUBLISH), [CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SNAPSHOT_NO_PUBLISH), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.getState().getExecuted(), [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.getState().getSkipped(), [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.getState().getFailure() == [CtLiteralImpl]null, [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.isEquivalent(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.getState().getExecuted(), [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.getState().getSkipped(), [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.getState().getFailure() == [CtLiteralImpl]null, [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.isEquivalent());
                    [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SNAPSHOT_NO_PUBLISH)) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK) && [CtInvocationImpl]isTaskSuccessful([CtVariableReadImpl]checkSnapshotTask)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.isEquivalent())) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK)) && [CtInvocationImpl]isTaskSuccessful([CtVariableReadImpl]checkRestModelTask)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.isEquivalent())));
                });
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task publishRestliIdlTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"publish", [CtLiteralImpl]"RestliIdl"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.PublishRestModelTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]checkRestModelTask, [CtVariableReadImpl]checkIdlTask, [CtVariableReadImpl]checkSnapshotTask);
                [CtInvocationImpl][CtVariableReadImpl]task.from([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getIdlFiles([CtVariableReadImpl]project, [CtVariableReadImpl]destinationDirPrefix));
                [CtInvocationImpl][CtVariableReadImpl]task.into([CtVariableReadImpl]apiIdlDir);
                [CtInvocationImpl][CtVariableReadImpl]task.setSuffix([CtFieldReadImpl][CtFieldReferenceImpl]IDL_FILE_SUFFIX);
                [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SKIP_IDL: {}\n" + [CtLiteralImpl]"IDL_NO_PUBLISH: {}\n") + [CtLiteralImpl]"SNAPSHOT_NO_PUBLISH: {}\n") + [CtLiteralImpl]"checkRestModelTask:") + [CtLiteralImpl]" Executed: {}") + [CtLiteralImpl]", Not Skipped: {}") + [CtLiteralImpl]", No Failure: {}") + [CtLiteralImpl]", Is Not Equivalent: {}\n") + [CtLiteralImpl]"checkIdlTask:") + [CtLiteralImpl]" Executed: {}") + [CtLiteralImpl]", Not Skipped: {}") + [CtLiteralImpl]", No Failure: {}") + [CtLiteralImpl]", Is Not Equivalent: {}\n") + [CtLiteralImpl]"checkSnapshotTask:") + [CtLiteralImpl]" Executed: {}") + [CtLiteralImpl]", Not Skipped: {}") + [CtLiteralImpl]", No Failure: {}") + [CtLiteralImpl]", Is RestSpec Not Equivalent: {}\n", [CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK), [CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]IDL_NO_PUBLISH), [CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SNAPSHOT_NO_PUBLISH), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.getState().getExecuted(), [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.getState().getSkipped(), [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.getState().getFailure() == [CtLiteralImpl]null, [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.isEquivalent(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkIdlTask.getState().getExecuted(), [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkIdlTask.getState().getSkipped(), [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkIdlTask.getState().getFailure() == [CtLiteralImpl]null, [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]checkIdlTask.isEquivalent(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.getState().getExecuted(), [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.getState().getSkipped(), [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.getState().getFailure() == [CtLiteralImpl]null, [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.isEquivalent());
                    [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]IDL_NO_PUBLISH)) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK) && [CtInvocationImpl]isTaskSuccessful([CtVariableReadImpl]checkSnapshotTask)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]checkSnapshotTask.isRestSpecEquivalent())) || [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]SKIP_IDL_CHECK)) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]isTaskSuccessful([CtVariableReadImpl]checkRestModelTask) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]checkRestModelTask.isRestSpecEquivalent())) || [CtBinaryOperatorImpl]([CtInvocationImpl]isTaskSuccessful([CtVariableReadImpl]checkIdlTask) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]checkIdlTask.isEquivalent())))));
                });
            });
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"API project selected for {} is {}", [CtInvocationImpl][CtVariableReadImpl]publishRestliIdlTask.getPath(), [CtInvocationImpl][CtVariableReadImpl]apiProject.getPath());
            [CtInvocationImpl][CtVariableReadImpl]jarTask.from([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getIdlFiles([CtVariableReadImpl]project, [CtVariableReadImpl]destinationDirPrefix));
            [CtInvocationImpl][CtCommentImpl]// add generated .restspec.json files as resources to the jar
            [CtVariableReadImpl]jarTask.dependsOn([CtVariableReadImpl]publishRestliSnapshotTask, [CtVariableReadImpl]publishRestliIdlTask);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.ChangedFileReportTask changedFileReportTask = [CtInvocationImpl](([CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.ChangedFileReportTask) ([CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtLiteralImpl]"changedFilesReport")));
            [CtInvocationImpl][CtCommentImpl]// Use the files from apiDir for generating the changed files report as we need to notify user only when
            [CtCommentImpl]// source system files are modified.
            [CtVariableReadImpl]changedFileReportTask.setIdlFiles([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSuffixedFiles([CtVariableReadImpl]project, [CtVariableReadImpl]apiIdlDir, [CtFieldReadImpl][CtFieldReferenceImpl]IDL_FILE_SUFFIX));
            [CtInvocationImpl][CtVariableReadImpl]changedFileReportTask.setSnapshotFiles([CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSuffixedFiles([CtVariableReadImpl]project, [CtVariableReadImpl]apiSnapshotDir, [CtFieldReadImpl][CtFieldReferenceImpl]SNAPSHOT_FILE_SUFFIX));
            [CtInvocationImpl][CtVariableReadImpl]changedFileReportTask.mustRunAfter([CtVariableReadImpl]publishRestliSnapshotTask, [CtVariableReadImpl]publishRestliIdlTask);
            [CtInvocationImpl][CtVariableReadImpl]changedFileReportTask.doLast([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] t) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]changedFileReportTask.getNeedCheckinFiles().isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"Adding modified files to need checkin list...");
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_needCheckinFiles.addAll([CtInvocationImpl][CtVariableReadImpl]changedFileReportTask.getNeedCheckinFiles());
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]_needBuildFolders.add([CtInvocationImpl][CtInvocationImpl]getCheckedApiProject([CtVariableReadImpl]project).getPath());
                }
            }));
        });
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void configurePegasusSchemaSnapshotGeneration([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File pegasusSchemaDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File publishablePegasusSchemaSnapshotDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getBuildDir().getAbsolutePath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_SCHEMA_SNAPSHOT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task generatePegasusSchemaSnapshot = [CtInvocationImpl]generatePegasusSchemaSnapshot([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_SCHEMA_SNAPSHOT, [CtVariableReadImpl]pegasusSchemaDir, [CtVariableReadImpl]publishablePegasusSchemaSnapshotDir);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task checkSchemaSnapshot = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"check", [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_SCHEMA_SNAPSHOT), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.CheckPegasusSnapshotTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]generatePegasusSchemaSnapshot);
            [CtCommentImpl]// TODO: update CheckPegasusSnapshotTask
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File pegasusSchemaSnapshotDir = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getPegasusSchemaSnapshotPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtInvocationImpl][CtVariableReadImpl]pegasusSchemaSnapshotDir.mkdirs();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task publishPegasusSchemaSnapshot = [CtInvocationImpl]publishPegasusSchemaSnapshot([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_SCHEMA_SNAPSHOT, [CtVariableReadImpl]checkSchemaSnapshot, [CtVariableReadImpl]publishablePegasusSchemaSnapshotDir, [CtVariableReadImpl]pegasusSchemaSnapshotDir);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.ASSEMBLE).dependsOn([CtVariableReadImpl]publishPegasusSchemaSnapshot);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void configurePegasusExtensionSchemaSnapshotGeneration([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File publishablePegasusExtensionSchemaSnapshotDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getBuildDir().getAbsolutePath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_EXTENSION_SCHEMA_SNAPSHOT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File extensionSchemaDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getExtensionSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSuffixedFiles([CtVariableReadImpl]project, [CtVariableReadImpl]extensionSchemaDir, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PDL_FILE_SUFFIX).isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task generatePegasusExtensionSchemaSnapshot = [CtInvocationImpl]generatePegasusSchemaSnapshot([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_EXTENSION_SCHEMA_SNAPSHOT, [CtVariableReadImpl]extensionSchemaDir, [CtVariableReadImpl]publishablePegasusExtensionSchemaSnapshotDir);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File pegasusExtensionSchemaSnapshotDir = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getPegasusExtensionSchemaSnapshotPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
            [CtInvocationImpl][CtVariableReadImpl]pegasusExtensionSchemaSnapshotDir.mkdirs();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task checkPegasusExtensionSchemaSnapshotTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"check", [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_EXTENSION_SCHEMA_SNAPSHOT), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.CheckPegasusSnapshotTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]generatePegasusExtensionSchemaSnapshot);
                [CtCommentImpl]// TODO: update CheckPegasusSnapshotTask
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task publishPegasusExtensionSchemaSnapshot = [CtInvocationImpl]publishPegasusSchemaSnapshot([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PEGASUS_EXTENSION_SCHEMA_SNAPSHOT, [CtVariableReadImpl]checkPegasusExtensionSchemaSnapshotTask, [CtVariableReadImpl]publishablePegasusExtensionSchemaSnapshotDir, [CtVariableReadImpl]pegasusExtensionSchemaSnapshotDir);
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.ASSEMBLE).dependsOn([CtVariableReadImpl]publishPegasusExtensionSchemaSnapshot);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().debug([CtLiteralImpl]"No extension schemas, skip generatePegasusExtensionSchemaSnapshot task");
            [CtReturnImpl]return;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    protected [CtTypeReferenceImpl]void configureAvroSchemaGeneration([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File dataSchemaDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File avroDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedDirPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.AVRO_SCHEMA_GEN_TYPE) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"avro");
        [CtLocalVariableImpl][CtCommentImpl]// generate avro schema files from data schema
        [CtTypeReferenceImpl]org.gradle.api.Task generateAvroSchemaTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"generate", [CtLiteralImpl]"avroSchema"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.GenerateAvroSchemaTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]dataSchemaDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationDir([CtVariableReadImpl]avroDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
            [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
            [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getInputDir().exists()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
                    [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions> pegasusOptions = [CtInvocationImpl](([CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions>) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().get([CtLiteralImpl]"pegasus")));
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pegasusOptions.get([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()).hasGenerationMode([CtVariableReadImpl]PegasusOptions.GenerationMode.AVRO)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    }
                }
                [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"avroSchemaGenerator").isEmpty();
            });
            [CtInvocationImpl][CtVariableReadImpl]task.doFirst([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl]deleteGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl][CtFieldReferenceImpl]AVRO_SCHEMA_GEN_TYPE)));
        });
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtInvocationImpl][CtVariableReadImpl]sourceSet.getCompileJavaTaskName()).dependsOn([CtVariableReadImpl]generateAvroSchemaTask);
        [CtLocalVariableImpl][CtCommentImpl]// create avro schema jar file
        [CtTypeReferenceImpl]org.gradle.api.Task avroSchemaJarTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"AvroSchemaJar", [CtFieldReadImpl]org.gradle.api.tasks.bundling.Jar.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// add path prefix to each file in the data schema directory
            [CtVariableReadImpl]task.from([CtVariableReadImpl]avroDir, [CtLambdaImpl]([CtParameterImpl] copySpec) -> [CtInvocationImpl][CtVariableReadImpl]copySpec.eachFile([CtLambdaImpl]([CtParameterImpl] fileCopyDetails) -> [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.setPath([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"avro" + [CtVariableReadImpl]File.separatorChar) + [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.getPath())));
            [CtInvocationImpl][CtCommentImpl]// FIXME change to #getArchiveAppendix().set(...); breaks backwards-compatibility before 5.1
            [CtVariableReadImpl]task.setAppendix([CtInvocationImpl]getAppendix([CtVariableReadImpl]sourceSet, [CtLiteralImpl]"avro-schema"));
            [CtInvocationImpl][CtVariableReadImpl]task.setDescription([CtLiteralImpl]"Generate an avro schema jar");
        });
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isTestSourceSet([CtVariableReadImpl]sourceSet)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"avroSchema", [CtVariableReadImpl]avroSchemaJarTask);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"testAvroSchema", [CtVariableReadImpl]avroSchemaJarTask);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void configureConversionUtilities([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File dataSchemaDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean reverse = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.CONVERT_TO_PDL_REVERSE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean keepOriginal = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.CONVERT_TO_PDL_KEEP_ORIGINAL);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean skipVerification = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.CONVERT_TO_PDL_SKIP_VERIFICATION);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String preserveSourceCmd = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getNonEmptyProperty([CtVariableReadImpl]project, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.CONVERT_TO_PDL_PRESERVE_SOURCE_CMD);
        [CtInvocationImpl][CtCommentImpl]// Utility task for migrating between PDSC and PDL.
        [CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"convert", [CtLiteralImpl]"ToPdl"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.TranslateSchemasTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]dataSchemaDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationDir([CtVariableReadImpl]dataSchemaDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
            [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
            [CtInvocationImpl][CtVariableReadImpl]task.setPreserveSourceCmd([CtVariableReadImpl]preserveSourceCmd);
            [CtIfImpl]if ([CtVariableReadImpl]reverse) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setSourceFormat([CtVariableReadImpl]SchemaFileType.PDL);
                [CtInvocationImpl][CtVariableReadImpl]task.setDestinationFormat([CtVariableReadImpl]SchemaFileType.PDSC);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setSourceFormat([CtVariableReadImpl]SchemaFileType.PDSC);
                [CtInvocationImpl][CtVariableReadImpl]task.setDestinationFormat([CtVariableReadImpl]SchemaFileType.PDL);
            }
            [CtInvocationImpl][CtVariableReadImpl]task.setKeepOriginal([CtVariableReadImpl]keepOriginal);
            [CtInvocationImpl][CtVariableReadImpl]task.setSkipVerification([CtVariableReadImpl]skipVerification);
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
            [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getInputDir().exists());
            [CtInvocationImpl][CtVariableReadImpl]task.doLast([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] t) -> [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtLiteralImpl]"Pegasus schema conversion complete.");
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"All pegasus schema files in " + [CtVariableReadImpl]dataSchemaDir) + [CtLiteralImpl]" have been converted");
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtLiteralImpl]"You can use '-PconvertToPdl.reverse=true|false' to change the direction of conversion.");
            }));
        });
        [CtInvocationImpl][CtCommentImpl]// Helper task for reformatting existing PDL schemas by generating them again.
        [CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"reformat", [CtLiteralImpl]"Pdl"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.TranslateSchemasTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]dataSchemaDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationDir([CtVariableReadImpl]dataSchemaDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
            [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
            [CtInvocationImpl][CtVariableReadImpl]task.setSourceFormat([CtVariableReadImpl]SchemaFileType.PDL);
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationFormat([CtVariableReadImpl]SchemaFileType.PDL);
            [CtInvocationImpl][CtVariableReadImpl]task.setKeepOriginal([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]task.setSkipVerification([CtLiteralImpl]true);
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
            [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getInputDir().exists());
            [CtInvocationImpl][CtVariableReadImpl]task.doLast([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtLiteralImpl]"PDL reformat complete.")));
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    protected [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.GenerateDataTemplateTask configureDataTemplateGeneration([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File dataSchemaDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File generatedDataTemplateDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedDirPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.DATA_TEMPLATE_GEN_TYPE) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"java");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File publishableSchemasBuildDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getBuildDir().getAbsolutePath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtLiteralImpl]"Schemas");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File publishableLegacySchemasBuildDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getBuildDir().getAbsolutePath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtLiteralImpl]"LegacySchemas");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File publishableExtensionSchemasBuildDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getBuildDir().getAbsolutePath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtLiteralImpl]"ExtensionSchemas");
        [CtLocalVariableImpl][CtCommentImpl]// generate data template source files from data schema
        [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.GenerateDataTemplateTask generateDataTemplatesTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"generate", [CtLiteralImpl]"dataTemplate"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.GenerateDataTemplateTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]dataSchemaDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationDir([CtVariableReadImpl]generatedDataTemplateDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
            [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
            [CtInvocationImpl][CtVariableReadImpl]task.onlyIf([CtLambdaImpl]([CtParameterImpl] t) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getInputDir().exists()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
                    [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions> pegasusOptions = [CtInvocationImpl](([CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions>) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().get([CtLiteralImpl]"pegasus")));
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pegasusOptions.get([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()).hasGenerationMode([CtVariableReadImpl]PegasusOptions.GenerationMode.PEGASUS);
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            });
            [CtInvocationImpl][CtVariableReadImpl]task.doFirst([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.CacheableAction<>([CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl]deleteGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl][CtFieldReferenceImpl]DATA_TEMPLATE_GEN_TYPE)));
        });
        [CtInvocationImpl][CtCommentImpl]// TODO: Tighten the types so that _generateSourcesJarTask must be of type Jar.
        [CtFieldReadImpl](([CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar) (_generateSourcesJarTask)).from([CtInvocationImpl][CtVariableReadImpl]generateDataTemplatesTask.getDestinationDir());
        [CtInvocationImpl][CtFieldReadImpl]_generateSourcesJarTask.dependsOn([CtVariableReadImpl]generateDataTemplatesTask);
        [CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.source([CtInvocationImpl][CtVariableReadImpl]generateDataTemplatesTask.getDestinationDir());
        [CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.setClasspath([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.getClasspath().plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataTemplateCompile")).plus([CtInvocationImpl][CtVariableReadImpl]generateDataTemplatesTask.getResolverPath()));
        [CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.dependsOn([CtVariableReadImpl]generateDataTemplatesTask);
        [CtInvocationImpl][CtCommentImpl]// Add extra dependencies for data model compilation
        [CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtLiteralImpl]"dataTemplateCompile", [CtLiteralImpl]"com.google.code.findbugs:jsr305:3.0.2");
        [CtLocalVariableImpl][CtCommentImpl]// create new source set for generated java source and class files
        [CtTypeReferenceImpl]java.lang.String targetSourceSetName = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedSourceSetName([CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.DATA_TEMPLATE_GEN_TYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSetContainer sourceSets = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConvention().getPlugin([CtFieldReadImpl]org.gradle.api.plugins.JavaPluginConvention.class).getSourceSets();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet targetSourceSet = [CtInvocationImpl][CtVariableReadImpl]sourceSets.create([CtVariableReadImpl]targetSourceSetName, [CtLambdaImpl]([CtParameterImpl] ss) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ss.java([CtLambdaImpl]([CtParameterImpl] sourceDirectorySet) -> [CtInvocationImpl][CtVariableReadImpl]sourceDirectorySet.srcDir([CtVariableReadImpl]generatedDataTemplateDir));
            [CtInvocationImpl][CtVariableReadImpl]ss.setCompileClasspath([CtInvocationImpl][CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet).plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataTemplateCompile")));
        });
        [CtInvocationImpl][CtCommentImpl]// idea plugin needs to know about new generated java source directory and its dependencies
        com.linkedin.pegasus.gradle.PegasusPlugin.addGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]targetSourceSet, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataTemplateCompile")));
        [CtLocalVariableImpl][CtCommentImpl]// make sure that java source files have been generated before compiling them
        [CtTypeReferenceImpl]org.gradle.api.Task compileTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtInvocationImpl][CtVariableReadImpl]targetSourceSet.getCompileJavaTaskName());
        [CtInvocationImpl][CtVariableReadImpl]compileTask.dependsOn([CtVariableReadImpl]generateDataTemplatesTask);
        [CtLocalVariableImpl][CtCommentImpl]// Dummy task to maintain backward compatibility
        [CtCommentImpl]// TODO: Delete this task once use cases have had time to reference the new task
        [CtTypeReferenceImpl]org.gradle.api.Task destroyStaleFiles = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"DestroyStaleFiles", [CtFieldReadImpl]org.gradle.api.tasks.Delete.class);
        [CtInvocationImpl][CtVariableReadImpl]destroyStaleFiles.onlyIf([CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtLiteralImpl]"{} task is a NO-OP task.", [CtInvocationImpl][CtVariableReadImpl]task.getPath());
            [CtReturnImpl]return [CtLiteralImpl]false;
        });
        [CtLocalVariableImpl][CtCommentImpl]// Dummy task to maintain backward compatibility, as this task was replaced by CopySchemas
        [CtCommentImpl]// TODO: Delete this task once use cases have had time to reference the new task
        [CtTypeReferenceImpl]org.gradle.api.Task copyPdscSchemasTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"CopyPdscSchemas", [CtFieldReadImpl]org.gradle.api.tasks.Copy.class);
        [CtInvocationImpl][CtVariableReadImpl]copyPdscSchemasTask.dependsOn([CtVariableReadImpl]destroyStaleFiles);
        [CtInvocationImpl][CtVariableReadImpl]copyPdscSchemasTask.onlyIf([CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().lifecycle([CtLiteralImpl]"{} task is a NO-OP task.", [CtInvocationImpl][CtVariableReadImpl]task.getPath());
            [CtReturnImpl]return [CtLiteralImpl]false;
        });
        [CtLocalVariableImpl][CtCommentImpl]// Prepare schema files for publication by syncing schema folders.
        [CtTypeReferenceImpl]org.gradle.api.Task prepareSchemasForPublishTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"CopySchemas", [CtFieldReadImpl]org.gradle.api.tasks.Sync.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.from([CtVariableReadImpl]dataSchemaDir, [CtLambdaImpl]([CtParameterImpl] syncSpec) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]DATA_TEMPLATE_FILE_SUFFIXES.forEach([CtLambdaImpl]([CtParameterImpl] suffix) -> [CtInvocationImpl][CtVariableReadImpl]syncSpec.include([CtBinaryOperatorImpl][CtLiteralImpl]"**/*" + [CtVariableReadImpl]suffix)));
            [CtInvocationImpl][CtVariableReadImpl]task.into([CtVariableReadImpl]publishableSchemasBuildDir);
        });
        [CtInvocationImpl][CtVariableReadImpl]prepareSchemasForPublishTask.dependsOn([CtVariableReadImpl]copyPdscSchemasTask);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.gradle.api.Task> dataTemplateJarDepends = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]dataTemplateJarDepends.add([CtVariableReadImpl]compileTask);
        [CtInvocationImpl][CtVariableReadImpl]dataTemplateJarDepends.add([CtVariableReadImpl]prepareSchemasForPublishTask);
        [CtLocalVariableImpl][CtCommentImpl]// Convert all PDL files back to PDSC for publication
        [CtCommentImpl]// TODO: Remove this conversion permanently once translated PDSCs are no longer needed.
        [CtTypeReferenceImpl]org.gradle.api.Task prepareLegacySchemasForPublishTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"TranslateSchemas", [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.TranslateSchemasTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]dataSchemaDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationDir([CtVariableReadImpl]publishableLegacySchemasBuildDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
            [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
            [CtInvocationImpl][CtVariableReadImpl]task.setSourceFormat([CtVariableReadImpl]SchemaFileType.PDL);
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationFormat([CtVariableReadImpl]SchemaFileType.PDSC);
            [CtInvocationImpl][CtVariableReadImpl]task.setKeepOriginal([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]task.setSkipVerification([CtLiteralImpl]true);
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]prepareLegacySchemasForPublishTask.dependsOn([CtVariableReadImpl]destroyStaleFiles);
        [CtInvocationImpl][CtVariableReadImpl]dataTemplateJarDepends.add([CtVariableReadImpl]prepareLegacySchemasForPublishTask);
        [CtLocalVariableImpl][CtCommentImpl]// extension schema directory
        [CtTypeReferenceImpl]java.io.File extensionSchemaDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getExtensionSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSuffixedFiles([CtVariableReadImpl]project, [CtVariableReadImpl]extensionSchemaDir, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.PDL_FILE_SUFFIX).isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Validate extension schemas if extension schemas are provided.
            [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.ValidateExtensionSchemaTask validateExtensionSchemaTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"validate", [CtLiteralImpl]"ExtensionSchemas"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.ValidateExtensionSchemaTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]extensionSchemaDir);
                [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl][CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet).plus([CtInvocationImpl][CtVariableReadImpl]project.files([CtInvocationImpl]getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet))));
                [CtInvocationImpl][CtVariableReadImpl]task.setClassPath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
                [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
                }
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Task prepareExtensionSchemasForPublishTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"CopyExtensionSchemas", [CtFieldReadImpl]org.gradle.api.tasks.Sync.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.from([CtVariableReadImpl]extensionSchemaDir, [CtLambdaImpl]([CtParameterImpl] syncSpec) -> [CtInvocationImpl][CtVariableReadImpl]syncSpec.include([CtBinaryOperatorImpl][CtLiteralImpl]"**/*" + [CtFieldReadImpl][CtFieldReferenceImpl]PDL_FILE_SUFFIX));
                [CtInvocationImpl][CtVariableReadImpl]task.into([CtVariableReadImpl]publishableExtensionSchemasBuildDir);
            });
            [CtInvocationImpl][CtVariableReadImpl]prepareExtensionSchemasForPublishTask.dependsOn([CtVariableReadImpl]validateExtensionSchemaTask);
            [CtInvocationImpl][CtVariableReadImpl]prepareExtensionSchemasForPublishTask.dependsOn([CtVariableReadImpl]copyPdscSchemasTask);
            [CtInvocationImpl][CtVariableReadImpl]dataTemplateJarDepends.add([CtVariableReadImpl]prepareExtensionSchemasForPublishTask);
        }
        [CtLocalVariableImpl][CtCommentImpl]// create data template jar file
        [CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar dataTemplateJarTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"DataTemplateJar", [CtFieldReadImpl]org.gradle.api.tasks.bundling.Jar.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]dataTemplateJarDepends);
            [CtInvocationImpl][CtCommentImpl]// Copy all schemas as-is into the root schema directory in the JAR
            [CtVariableReadImpl]task.from([CtVariableReadImpl]publishableSchemasBuildDir, [CtLambdaImpl]([CtParameterImpl] copySpec) -> [CtInvocationImpl][CtVariableReadImpl]copySpec.eachFile([CtLambdaImpl]([CtParameterImpl] fileCopyDetails) -> [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.setPath([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"pegasus" + [CtVariableReadImpl]File.separatorChar) + [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.getPath())));
            [CtInvocationImpl][CtCommentImpl]// Copy the translated PDSC schemas into a separate root directory in the JAR
            [CtCommentImpl]// TODO: Remove this permanently once translated PDSCs are no longer needed.
            [CtVariableReadImpl]task.from([CtVariableReadImpl]publishableLegacySchemasBuildDir, [CtLambdaImpl]([CtParameterImpl] copySpec) -> [CtInvocationImpl][CtVariableReadImpl]copySpec.eachFile([CtLambdaImpl]([CtParameterImpl] fileCopyDetails) -> [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.setPath([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtFieldReferenceImpl]TRANSLATED_SCHEMAS_DIR + [CtVariableReadImpl]File.separatorChar) + [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.getPath())));
            [CtInvocationImpl][CtCommentImpl]// Copy all extension schemas as-is into the root extensions directory in the JAR
            [CtVariableReadImpl]task.from([CtVariableReadImpl]publishableExtensionSchemasBuildDir, [CtLambdaImpl]([CtParameterImpl] copySpec) -> [CtInvocationImpl][CtVariableReadImpl]copySpec.eachFile([CtLambdaImpl]([CtParameterImpl] fileCopyDetails) -> [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.setPath([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"extensions" + [CtVariableReadImpl]File.separatorChar) + [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.getPath())));
            [CtInvocationImpl][CtVariableReadImpl]task.from([CtInvocationImpl][CtVariableReadImpl]targetSourceSet.getOutput());
            [CtInvocationImpl][CtCommentImpl]// FIXME change to #getArchiveAppendix().set(...); breaks backwards-compatibility before 5.1
            [CtVariableReadImpl]task.setAppendix([CtInvocationImpl]getAppendix([CtVariableReadImpl]sourceSet, [CtLiteralImpl]"data-template"));
            [CtInvocationImpl][CtVariableReadImpl]task.setDescription([CtLiteralImpl]"Generate a data template jar");
        });
        [CtIfImpl][CtCommentImpl]// add the data model and date template jars to the list of project artifacts.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isTestSourceSet([CtVariableReadImpl]sourceSet)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"dataTemplate", [CtVariableReadImpl]dataTemplateJarTask);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"testDataTemplate", [CtVariableReadImpl]dataTemplateJarTask);
        }
        [CtLocalVariableImpl][CtCommentImpl]// include additional dependencies into the appropriate configuration used to compile the input source set
        [CtCommentImpl]// must include the generated data template classes and their dependencies the configuration
        [CtTypeReferenceImpl]java.lang.String compileConfigName = [CtConditionalImpl]([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isTestSourceSet([CtVariableReadImpl]sourceSet)) ? [CtLiteralImpl]"testCompile" : [CtLiteralImpl]"compile";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration compileConfig = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().maybeCreate([CtVariableReadImpl]compileConfigName);
        [CtInvocationImpl][CtVariableReadImpl]compileConfig.extendsFrom([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataTemplateCompile"));
        [CtInvocationImpl][CtCommentImpl]// FIXME change to #getArchiveFile(); breaks backwards-compatibility before 5.1
        [CtInvocationImpl][CtVariableReadImpl]project.getDependencies().add([CtVariableReadImpl]compileConfigName, [CtInvocationImpl][CtVariableReadImpl]project.files([CtInvocationImpl][CtVariableReadImpl]dataTemplateJarTask.getArchivePath()));
        [CtIfImpl]if ([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.debug) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"configureDataTemplateGeneration sourceSet " + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName());
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]compileConfigName + [CtLiteralImpl]".allDependencies : ") + [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtVariableReadImpl]compileConfigName).getAllDependencies());
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]compileConfigName + [CtLiteralImpl]".extendsFrom: ") + [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtVariableReadImpl]compileConfigName).getExtendsFrom());
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]compileConfigName + [CtLiteralImpl]".transitive: ") + [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtVariableReadImpl]compileConfigName).isTransitive());
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtInvocationImpl][CtVariableReadImpl]sourceSet.getCompileJavaTaskName()).dependsOn([CtVariableReadImpl]dataTemplateJarTask);
        [CtReturnImpl]return [CtVariableReadImpl]generateDataTemplatesTask;
    }

    [CtMethodImpl][CtCommentImpl]// Generate rest client from idl files generated from java source files in the specified source set.
    [CtCommentImpl]// 
    [CtCommentImpl]// This generates rest client source files from idl file generated from java source files
    [CtCommentImpl]// in the source set. The generated rest client source files will be in a new source set.
    [CtCommentImpl]// It also compiles the rest client source files into classes, and creates both the
    [CtCommentImpl]// rest model and rest client jar files.
    [CtCommentImpl]// 
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    protected [CtTypeReferenceImpl]void configureRestClientGeneration([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// idl directory for api project
        [CtTypeReferenceImpl]java.io.File idlDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getIdlPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet));
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.linkedin.pegasus.gradle.SharedFileUtils.getSuffixedFiles([CtVariableReadImpl]project, [CtVariableReadImpl]idlDir, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.IDL_FILE_SUFFIX).isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File generatedRestClientDir = [CtInvocationImpl][CtVariableReadImpl]project.file([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedDirPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.REST_GEN_TYPE) + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar) + [CtLiteralImpl]"java");
        [CtLocalVariableImpl][CtCommentImpl]// always include imported data template jars in compileClasspath of rest client
        [CtTypeReferenceImpl]org.gradle.api.file.FileCollection dataModelConfig = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
        [CtLocalVariableImpl][CtCommentImpl]// if data templates generated from this source set, add the generated data template jar to compileClasspath
        [CtCommentImpl]// of rest client.
        [CtTypeReferenceImpl]java.lang.String dataTemplateSourceSetName = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedSourceSetName([CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.DATA_TEMPLATE_GEN_TYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar dataTemplateJarTask = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSetContainer sourceSets = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConvention().getPlugin([CtFieldReadImpl]org.gradle.api.plugins.JavaPluginConvention.class).getSourceSets();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.file.FileCollection dataModels;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSets.findByName([CtVariableReadImpl]dataTemplateSourceSetName) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.debug) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"sourceSet " + [CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()) + [CtLiteralImpl]" has generated sourceSet ") + [CtVariableReadImpl]dataTemplateSourceSetName);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]dataTemplateJarTask = [CtInvocationImpl](([CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar) ([CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"DataTemplateJar")));
            [CtAssignmentImpl][CtCommentImpl]// FIXME change to #getArchiveFile(); breaks backwards-compatibility before 5.1
            [CtVariableWriteImpl]dataModels = [CtInvocationImpl][CtVariableReadImpl]dataModelConfig.plus([CtInvocationImpl][CtVariableReadImpl]project.files([CtInvocationImpl][CtVariableReadImpl]dataTemplateJarTask.getArchivePath()));
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataModels = [CtVariableReadImpl]dataModelConfig;
        }
        [CtLocalVariableImpl][CtCommentImpl]// create source set for generated rest model, rest client source and class files.
        [CtTypeReferenceImpl]java.lang.String targetSourceSetName = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getGeneratedSourceSetName([CtVariableReadImpl]sourceSet, [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.REST_GEN_TYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet targetSourceSet = [CtInvocationImpl][CtVariableReadImpl]sourceSets.create([CtVariableReadImpl]targetSourceSetName, [CtLambdaImpl]([CtParameterImpl] ss) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ss.java([CtLambdaImpl]([CtParameterImpl] sourceDirectorySet) -> [CtInvocationImpl][CtVariableReadImpl]sourceDirectorySet.srcDir([CtVariableReadImpl]generatedRestClientDir));
            [CtInvocationImpl][CtVariableReadImpl]ss.setCompileClasspath([CtInvocationImpl][CtVariableReadImpl]dataModels.plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"restClientCompile")));
        });
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPlugins().withType([CtFieldReadImpl]org.gradle.plugins.ide.eclipse.EclipsePlugin.class, [CtLambdaImpl]([CtParameterImpl] eclipsePlugin) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.plugins.ide.eclipse.model.EclipseModel eclipseModel = [CtInvocationImpl](([CtTypeReferenceImpl]org.gradle.plugins.ide.eclipse.model.EclipseModel) ([CtInvocationImpl][CtVariableReadImpl]project.getExtensions().findByName([CtLiteralImpl]"eclipse")));
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eclipseModel.getClasspath().getPlusConfigurations().add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"restClientCompile"));
        });
        [CtInvocationImpl][CtCommentImpl]// idea plugin needs to know about new rest client source directory and its dependencies
        com.linkedin.pegasus.gradle.PegasusPlugin.addGeneratedDir([CtVariableReadImpl]project, [CtVariableReadImpl]targetSourceSet, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"restClientCompile")));
        [CtLocalVariableImpl][CtCommentImpl]// generate the rest client source files
        [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.tasks.GenerateRestClientTask generateRestClientTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]targetSourceSet.getTaskName([CtLiteralImpl]"generate", [CtLiteralImpl]"restClient"), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.GenerateRestClientTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataTemplate"));
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]idlDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtVariableReadImpl]dataModels);
            [CtInvocationImpl][CtVariableReadImpl]task.setRuntimeClasspath([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataModel").plus([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"dataTemplate").getArtifacts().getFiles()));
            [CtInvocationImpl][CtVariableReadImpl]task.setCodegenClasspath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
            [CtInvocationImpl][CtVariableReadImpl]task.setDestinationDir([CtVariableReadImpl]generatedRestClientDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setRestli2FormatSuppressed([CtInvocationImpl][CtVariableReadImpl]project.hasProperty([CtFieldReadImpl][CtFieldReferenceImpl]SUPPRESS_REST_CLIENT_RESTLI_2));
            [CtInvocationImpl][CtVariableReadImpl]task.setRestli1FormatSuppressed([CtInvocationImpl][CtVariableReadImpl]project.hasProperty([CtFieldReadImpl][CtFieldReferenceImpl]SUPPRESS_REST_CLIENT_RESTLI_1));
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
        });
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataTemplateJarTask != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]generateRestClientTask.dependsOn([CtVariableReadImpl]dataTemplateJarTask);
        }
        [CtInvocationImpl][CtCommentImpl]// TODO: Tighten the types so that _generateSourcesJarTask must be of type Jar.
        [CtFieldReadImpl](([CtTypeReferenceImpl]org.gradle.api.tasks.bundling.Jar) (_generateSourcesJarTask)).from([CtInvocationImpl][CtVariableReadImpl]generateRestClientTask.getDestinationDir());
        [CtInvocationImpl][CtFieldReadImpl]_generateSourcesJarTask.dependsOn([CtVariableReadImpl]generateRestClientTask);
        [CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.source([CtInvocationImpl][CtVariableReadImpl]generateRestClientTask.getDestinationDir());
        [CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.setClasspath([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.getClasspath().plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtLiteralImpl]"restClientCompile")).plus([CtInvocationImpl][CtVariableReadImpl]generateRestClientTask.getResolverPath()));
        [CtInvocationImpl][CtFieldReadImpl]_generateJavadocTask.dependsOn([CtVariableReadImpl]generateRestClientTask);
        [CtLocalVariableImpl][CtCommentImpl]// make sure rest client source files have been generated before compiling them
        [CtTypeReferenceImpl]org.gradle.api.tasks.compile.JavaCompile compileGeneratedRestClientTask = [CtInvocationImpl](([CtTypeReferenceImpl]org.gradle.api.tasks.compile.JavaCompile) ([CtInvocationImpl][CtVariableReadImpl]project.getTasks().getByName([CtInvocationImpl][CtVariableReadImpl]targetSourceSet.getCompileJavaTaskName())));
        [CtInvocationImpl][CtVariableReadImpl]compileGeneratedRestClientTask.dependsOn([CtVariableReadImpl]generateRestClientTask);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]compileGeneratedRestClientTask.getOptions().getCompilerArgs().add([CtLiteralImpl]"-Xlint:-deprecation");
        [CtLocalVariableImpl][CtCommentImpl]// create the rest model jar file
        [CtTypeReferenceImpl]org.gradle.api.Task restModelJarTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"RestModelJar", [CtFieldReadImpl]org.gradle.api.tasks.bundling.Jar.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.from([CtVariableReadImpl]idlDir, [CtLambdaImpl]([CtParameterImpl] copySpec) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]copySpec.eachFile([CtLambdaImpl]([CtParameterImpl] fileCopyDetails) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"Add idl file: {}", [CtVariableReadImpl]fileCopyDetails));
                [CtInvocationImpl][CtVariableReadImpl]copySpec.setIncludes([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtBinaryOperatorImpl][CtLiteralImpl]'*' + [CtFieldReadImpl][CtFieldReferenceImpl]IDL_FILE_SUFFIX));
            });
            [CtInvocationImpl][CtCommentImpl]// FIXME change to #getArchiveAppendix().set(...); breaks backwards-compatibility before 5.1
            [CtVariableReadImpl]task.setAppendix([CtInvocationImpl]getAppendix([CtVariableReadImpl]sourceSet, [CtLiteralImpl]"rest-model"));
            [CtInvocationImpl][CtVariableReadImpl]task.setDescription([CtLiteralImpl]"Generate rest model jar");
        });
        [CtLocalVariableImpl][CtCommentImpl]// create the rest client jar file
        [CtTypeReferenceImpl]org.gradle.api.Task restClientJarTask = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]"RestClientJar", [CtFieldReadImpl]org.gradle.api.tasks.bundling.Jar.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]compileGeneratedRestClientTask);
            [CtInvocationImpl][CtVariableReadImpl]task.from([CtVariableReadImpl]idlDir, [CtLambdaImpl]([CtParameterImpl] copySpec) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]copySpec.eachFile([CtLambdaImpl]([CtParameterImpl] fileCopyDetails) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"Add interface file: {}", [CtVariableReadImpl]fileCopyDetails);
                    [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.setPath([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"idl" + [CtVariableReadImpl]File.separatorChar) + [CtInvocationImpl][CtVariableReadImpl]fileCopyDetails.getPath());
                });
                [CtInvocationImpl][CtVariableReadImpl]copySpec.setIncludes([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtBinaryOperatorImpl][CtLiteralImpl]'*' + [CtFieldReadImpl][CtFieldReferenceImpl]IDL_FILE_SUFFIX));
            });
            [CtInvocationImpl][CtVariableReadImpl]task.from([CtInvocationImpl][CtVariableReadImpl]targetSourceSet.getOutput());
            [CtInvocationImpl][CtCommentImpl]// FIXME change to #getArchiveAppendix().set(...); breaks backwards-compatibility before 5.1
            [CtVariableReadImpl]task.setAppendix([CtInvocationImpl]getAppendix([CtVariableReadImpl]sourceSet, [CtLiteralImpl]"rest-client"));
            [CtInvocationImpl][CtVariableReadImpl]task.setDescription([CtLiteralImpl]"Generate rest client jar");
        });
        [CtIfImpl][CtCommentImpl]// add the rest model jar and the rest client jar to the list of project artifacts.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.isTestSourceSet([CtVariableReadImpl]sourceSet)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"restModel", [CtVariableReadImpl]restModelJarTask);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"restClient", [CtVariableReadImpl]restClientJarTask);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"testRestModel", [CtVariableReadImpl]restModelJarTask);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getArtifacts().add([CtLiteralImpl]"testRestClient", [CtVariableReadImpl]restClientJarTask);
        }
    }

    [CtMethodImpl][CtCommentImpl]// Return the appendix for generated jar files.
    [CtCommentImpl]// The source set name is not included for the main source set.
    private static [CtTypeReferenceImpl]java.lang.String getAppendix([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String suffix) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceSet.getName().equals([CtLiteralImpl]"main") ? [CtVariableReadImpl]suffix : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName() + [CtLiteralImpl]'-') + [CtVariableReadImpl]suffix;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.gradle.api.Project getApiProject([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().has([CtLiteralImpl]"apiProject")) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.gradle.api.Project) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().get([CtLiteralImpl]"apiProject")));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> subsSuffixes;
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().has([CtLiteralImpl]"apiProjectSubstitutionSuffixes")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> suffixValue = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().get([CtLiteralImpl]"apiProjectSubstitutionSuffixes")));
            [CtAssignmentImpl][CtVariableWriteImpl]subsSuffixes = [CtVariableReadImpl]suffixValue;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]subsSuffixes = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"-impl", [CtLiteralImpl]"-service", [CtLiteralImpl]"-server", [CtLiteralImpl]"-server-impl");
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String suffix : [CtVariableReadImpl]subsSuffixes) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPath().endsWith([CtVariableReadImpl]suffix)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchPath = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPath().substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getPath().length() - [CtInvocationImpl][CtVariableReadImpl]suffix.length()) + [CtLiteralImpl]"-api";
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Project apiProject = [CtInvocationImpl][CtVariableReadImpl]project.findProject([CtVariableReadImpl]searchPath);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]apiProject != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]apiProject;
                }
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]project.findProject([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]project.getPath() + [CtLiteralImpl]"-api");
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.gradle.api.Project getCheckedApiProject([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.Project apiProject = [CtInvocationImpl]com.linkedin.pegasus.gradle.PegasusPlugin.getApiProject([CtVariableReadImpl]project);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]apiProject == [CtVariableReadImpl]project) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.gradle.api.GradleException([CtLiteralImpl]"The API project of ${project.path} must not be itself.");
        }
        [CtReturnImpl]return [CtVariableReadImpl]apiProject;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * return the property value if the property exists and is not empty (-Pname=value)
     * return null if property does not exist or the property is empty (-Pname)
     *
     * @param project
     * 		the project where to look for the property
     * @param propertyName
     * 		the name of the property
     */
    public static [CtTypeReferenceImpl]java.lang.String getNonEmptyProperty([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String propertyName) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]project.hasProperty([CtVariableReadImpl]propertyName)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String propertyValue = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.property([CtVariableReadImpl]propertyName).toString();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]propertyValue.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtVariableReadImpl]propertyValue;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return true if the given property exists and its value is true
     *
     * @param project
     * 		the project where to look for the property
     * @param propertyName
     * 		the name of the property
     */
    public static [CtTypeReferenceImpl]boolean isPropertyTrue([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String propertyName) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]project.hasProperty([CtVariableReadImpl]propertyName) && [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.property([CtVariableReadImpl]propertyName).toString());
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String createModifiedFilesMessage([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> nonEquivExpectedFiles, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> foldersToBeBuilt) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"\nRemember to checkin the changes to the following new or modified files:\n");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String file : [CtVariableReadImpl]nonEquivExpectedFiles) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"  ");
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtVariableReadImpl]file);
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"\n");
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]foldersToBeBuilt.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtBinaryOperatorImpl][CtLiteralImpl]"\nThe file modifications include service interface changes, you can build the the following projects " + [CtLiteralImpl]"to re-generate the client APIs accordingly:\n");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String folder : [CtVariableReadImpl]foldersToBeBuilt) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"  ");
                [CtInvocationImpl][CtVariableReadImpl]builder.append([CtVariableReadImpl]folder);
                [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"\n");
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.toString();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String createPossibleMissingFilesMessage([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> missingFiles) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"If this is the result of an automated build, then you may have forgotten to check in some snapshot or idl files:\n");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String file : [CtVariableReadImpl]missingFiles) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"  ");
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtVariableReadImpl]file);
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"\n");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.toString();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String findProperty([CtParameterImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.FileCompatibilityType type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String property;
        [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
            [CtCaseImpl]case [CtFieldReadImpl]SNAPSHOT :
                [CtAssignmentImpl][CtVariableWriteImpl]property = [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.SNAPSHOT_COMPAT_REQUIREMENT;
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]IDL :
                [CtAssignmentImpl][CtVariableWriteImpl]property = [CtFieldReadImpl]com.linkedin.pegasus.gradle.PegasusPlugin.IDL_COMPAT_REQUIREMENT;
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.gradle.api.GradleException([CtBinaryOperatorImpl][CtLiteralImpl]"No property defined for compatibility type " + [CtVariableReadImpl]type);
        }
        [CtReturnImpl]return [CtVariableReadImpl]property;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.io.File> buildWatchedRestModelInputDirs([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions> pegasusOptions = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions>) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getExtensions().getExtraProperties().get([CtLiteralImpl]"pegasus")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File rootPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtVariableReadImpl]project.getProjectDir(), [CtInvocationImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]pegasusOptions.get([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()).restModelOptions.getRestResourcesRootPath());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.pegasus.gradle.PegasusOptions.IdlOptions idlOptions = [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]pegasusOptions.get([CtInvocationImpl][CtVariableReadImpl]sourceSet.getName()).idlOptions;
        [CtReturnImpl][CtCommentImpl]// if idlItems exist, only watch the smaller subset
        return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]idlOptions.getIdlItems().stream().flatMap([CtLambdaImpl]([CtParameterImpl] idlItem) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]idlItem.packageNames)).map([CtLambdaImpl]([CtParameterImpl] packageName) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]rootPath, [CtInvocationImpl][CtVariableReadImpl]packageName.replace([CtLiteralImpl]'.', [CtLiteralImpl]'/'))).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.TreeSet::new));
    }

    [CtMethodImpl]private static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]java.util.Set<[CtTypeParameterReferenceImpl]T> difference([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeParameterReferenceImpl]T> left, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeParameterReferenceImpl]T> right) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeParameterReferenceImpl]T> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtVariableReadImpl]left);
        [CtInvocationImpl][CtVariableReadImpl]result.removeAll([CtVariableReadImpl]right);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Configures the given source set so that its data schema directory (usually 'pegasus') is marked as a resource root.
     * The purpose of this is to improve the IDE experience. Makes sure to exclude this directory from being packaged in
     * with the default Jar task.
     */
    private static [CtTypeReferenceImpl]void configureDataSchemaResourcesRoot([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]sourceSet.resources([CtLambdaImpl]([CtParameterImpl] sourceDirectorySet) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String dataSchemaPath = [CtInvocationImpl]getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.File dataSchemaRoot = [CtInvocationImpl][CtVariableReadImpl]project.file([CtVariableReadImpl]dataSchemaPath);
            [CtInvocationImpl][CtVariableReadImpl]sourceDirectorySet.srcDir([CtVariableReadImpl]dataSchemaPath);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"Adding resource root '{}'", [CtVariableReadImpl]dataSchemaPath);
            [CtInvocationImpl][CtCommentImpl]// Exclude the data schema directory from being copied into the default Jar task
            [CtInvocationImpl][CtVariableReadImpl]sourceDirectorySet.getFilter().exclude([CtLambdaImpl]([CtParameterImpl] fileTreeElement) -> [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.File file = [CtInvocationImpl][CtVariableReadImpl]fileTreeElement.getFile();
                [CtLocalVariableImpl][CtCommentImpl]// Traversal starts with the children of a resource root, so checking the direct parent is sufficient
                final [CtTypeReferenceImpl]boolean exclude = [CtInvocationImpl][CtVariableReadImpl]dataSchemaRoot.equals([CtInvocationImpl][CtVariableReadImpl]file.getParentFile());
                [CtIfImpl]if ([CtVariableReadImpl]exclude) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getLogger().info([CtLiteralImpl]"Excluding resource directory '{}'", [CtVariableReadImpl]file);
                }
                [CtReturnImpl]return [CtVariableReadImpl]exclude;
            });
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.gradle.api.Task generatePegasusSchemaSnapshot([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskName, [CtParameterImpl][CtTypeReferenceImpl]java.io.File inputDir, [CtParameterImpl][CtTypeReferenceImpl]java.io.File outputDir) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"generate", [CtVariableReadImpl]taskName), [CtFieldReadImpl]com.linkedin.pegasus.gradle.tasks.GeneratePegasusSnapshotTask.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.setInputDir([CtVariableReadImpl]inputDir);
            [CtInvocationImpl][CtVariableReadImpl]task.setResolverPath([CtInvocationImpl][CtInvocationImpl]getDataModelConfig([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet).plus([CtInvocationImpl][CtVariableReadImpl]project.files([CtInvocationImpl]getDataSchemaPath([CtVariableReadImpl]project, [CtVariableReadImpl]sourceSet))));
            [CtInvocationImpl][CtVariableReadImpl]task.setClassPath([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getConfigurations().getByName([CtFieldReadImpl][CtFieldReferenceImpl]PEGASUS_PLUGIN_CONFIGURATION));
            [CtInvocationImpl][CtVariableReadImpl]task.setPegasusSchemaSnapshotDestinationDir([CtVariableReadImpl]outputDir);
            [CtIfImpl]if ([CtInvocationImpl]isPropertyTrue([CtVariableReadImpl]project, [CtFieldReadImpl][CtFieldReferenceImpl]ENABLE_ARG_FILE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setEnableArgFile([CtLiteralImpl]true);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.gradle.api.Task publishPegasusSchemaSnapshot([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Project project, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.tasks.SourceSet sourceSet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskName, [CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Task DependentTask, [CtParameterImpl][CtTypeReferenceImpl]java.io.File inputDir, [CtParameterImpl][CtTypeReferenceImpl]java.io.File outputDir) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getTasks().create([CtInvocationImpl][CtVariableReadImpl]sourceSet.getTaskName([CtLiteralImpl]"publish", [CtVariableReadImpl]taskName), [CtFieldReadImpl]org.gradle.api.tasks.Copy.class, [CtLambdaImpl]([CtParameterImpl] task) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]task.dependsOn([CtVariableReadImpl]DependentTask);
            [CtInvocationImpl][CtVariableReadImpl]task.from([CtVariableReadImpl]inputDir);
            [CtInvocationImpl][CtVariableReadImpl]task.into([CtVariableReadImpl]outputDir);
        });
    }
}
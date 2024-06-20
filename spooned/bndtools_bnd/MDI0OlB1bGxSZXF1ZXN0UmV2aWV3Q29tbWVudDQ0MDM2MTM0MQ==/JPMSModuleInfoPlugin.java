[CompilationUnitImpl][CtPackageDeclarationImpl]package aQute.bnd.plugin.jpms;
[CtImportImpl]import static java.util.stream.Collectors.groupingBy;
[CtImportImpl]import java.util.function.Predicate;
[CtUnresolvedImport]import aQute.bnd.header.Parameters;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import aQute.bnd.osgi.Constants;
[CtUnresolvedImport]import static aQute.bnd.osgi.Processor.removeDuplicateMarker;
[CtUnresolvedImport]import aQute.bnd.osgi.Descriptors.PackageRef;
[CtUnresolvedImport]import static aQute.bnd.osgi.Processor.isTrue;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import aQute.bnd.classfile.builder.ModuleInfoBuilder;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import aQute.bnd.build.model.EE;
[CtImportImpl]import java.util.Map.Entry;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import static aQute.lib.strings.Strings.splitAsStream;
[CtUnresolvedImport]import aQute.bnd.service.verifier.VerifierPlugin;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import aQute.bnd.osgi.Jar;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import aQute.bnd.osgi.Instructions;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import aQute.bnd.osgi.Analyzer;
[CtUnresolvedImport]import aQute.bnd.osgi.Processor;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import static aQute.bnd.classfile.ModuleAttribute.ACC_OPEN;
[CtUnresolvedImport]import aQute.lib.io.ByteBufferDataOutput;
[CtUnresolvedImport]import aQute.bnd.classfile.ModuleAttribute;
[CtUnresolvedImport]import static org.osgi.namespace.extender.ExtenderNamespace.EXTENDER_NAMESPACE;
[CtUnresolvedImport]import aQute.bnd.header.OSGiHeader;
[CtUnresolvedImport]import static aQute.bnd.classfile.ModuleAttribute.ACC_MANDATED;
[CtUnresolvedImport]import aQute.bnd.osgi.EmbeddedResource;
[CtUnresolvedImport]import aQute.bnd.osgi.Descriptors.TypeRef;
[CtUnresolvedImport]import aQute.bnd.osgi.Packages;
[CtUnresolvedImport]import aQute.bnd.header.Attrs;
[CtUnresolvedImport]import static aQute.bnd.classfile.ModuleAttribute.ACC_SYNTHETIC;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import aQute.bnd.stream.MapStream;
[CtUnresolvedImport]import aQute.lib.strings.Strings;
[CtClassImpl][CtJavaDocImpl]/**
 * A plugin to generate a module-info class from analyzer metadata and bundle
 * annotations.
 */
public class JPMSModuleInfoPlugin implements [CtTypeReferenceImpl]aQute.bnd.service.verifier.VerifierPlugin {
    [CtEnumImpl]enum Access {

        [CtEnumValueImpl]CLOSED([CtLiteralImpl]0),
        [CtEnumValueImpl]OPEN([CtFieldReadImpl]ModuleAttribute.ACC_OPEN),
        [CtEnumValueImpl]SYNTHETIC([CtFieldReadImpl]ModuleAttribute.ACC_SYNTHETIC),
        [CtEnumValueImpl]MANDATED([CtFieldReadImpl]ModuleAttribute.ACC_MANDATED);
        [CtMethodImpl]public static [CtTypeReferenceImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.Access parse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String input) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtVariableReadImpl]input) {
                [CtCaseImpl]case [CtLiteralImpl]"OPEN" :
                [CtCaseImpl]case [CtLiteralImpl]"open" :
                [CtCaseImpl]case [CtLiteralImpl]"0x0020" :
                [CtCaseImpl]case [CtLiteralImpl]"32" :
                    [CtReturnImpl]return [CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.Access.OPEN;
                [CtCaseImpl]case [CtLiteralImpl]"SYNTHETIC" :
                [CtCaseImpl]case [CtLiteralImpl]"synthetic" :
                [CtCaseImpl]case [CtLiteralImpl]"0x1000" :
                [CtCaseImpl]case [CtLiteralImpl]"4096" :
                    [CtReturnImpl]return [CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.Access.SYNTHETIC;
                [CtCaseImpl]case [CtLiteralImpl]"MANDATED" :
                [CtCaseImpl]case [CtLiteralImpl]"mandated" :
                [CtCaseImpl]case [CtLiteralImpl]"0x8000" :
                [CtCaseImpl]case [CtLiteralImpl]"32768" :
                    [CtReturnImpl]return [CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.Access.MANDATED;
                [CtCaseImpl]default :
                    [CtReturnImpl]return [CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.Access.CLOSED;
            }
        }

        [CtConstructorImpl]private Access([CtParameterImpl][CtTypeReferenceImpl]int value) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.value = [CtVariableReadImpl]value;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]int getValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]value;
        }

        [CtFieldImpl]private final [CtTypeReferenceImpl]int value;
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.regex.Pattern mangledModuleName = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"(.*)-\\d.*");

    [CtFieldImpl]private static final [CtTypeReferenceImpl]aQute.bnd.build.model.EE DEFAULT_MODULE_EE = [CtFieldReadImpl]aQute.bnd.build.model.EE.JavaSE_11_0;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERNAL_MODULE_DIRECTIVE = [CtLiteralImpl]"-internal-module:";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String WEB_INF = [CtLiteralImpl]"WEB-INF";

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void verify([CtParameterImpl]final [CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String moduleProperty = [CtInvocationImpl][CtVariableReadImpl]analyzer.getProperty([CtTypeAccessImpl]aQute.bnd.plugin.jpms.JPMS_MODULE_INFO);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]moduleProperty == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters provideCapabilities = [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.header.Parameters([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getJar().getManifest().getMainAttributes().getValue([CtTypeAccessImpl]aQute.bnd.plugin.jpms.PROVIDE_CAPABILITY));
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters requireCapabilities = [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.header.Parameters([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getJar().getManifest().getMainAttributes().getValue([CtTypeAccessImpl]aQute.bnd.plugin.jpms.REQUIRE_CAPABILITY));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]moduleProperty.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]name([CtVariableReadImpl]analyzer);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int access = [CtInvocationImpl]access([CtVariableReadImpl]requireCapabilities);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String version = [CtInvocationImpl][CtVariableReadImpl]analyzer.getVersion();
            [CtAssignmentImpl][CtVariableWriteImpl]moduleProperty = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s;access=%s;version=%s", [CtVariableReadImpl]name, [CtVariableReadImpl]access, [CtVariableReadImpl]version);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters moduleParameters = [CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.header.OSGiHeader.parseHeader([CtVariableReadImpl]moduleProperty);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]moduleParameters.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]moduleParameters.size() > [CtLiteralImpl]1)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Only one -module instruction is allowed:" + [CtVariableReadImpl]moduleParameters);

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]aQute.bnd.header.Attrs> moduleInstructions = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]moduleParameters.stream().findFirst().get();
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters moduleInfoOptions = [CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.header.OSGiHeader.parseHeader([CtInvocationImpl][CtVariableReadImpl]analyzer.getProperty([CtTypeAccessImpl]aQute.bnd.plugin.jpms.JPMS_MODULE_INFO_OPTIONS));
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Packages index = [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.osgi.Packages();
        [CtForEachImpl][CtCommentImpl]// Index the whole class path
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Jar jar : [CtInvocationImpl][CtVariableReadImpl]analyzer.getClasspath()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String moduleName = [CtInvocationImpl]getModuleName([CtVariableReadImpl]analyzer, [CtVariableReadImpl]jar, [CtVariableReadImpl]moduleInfoOptions);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String moduleVersion = [CtInvocationImpl][CtVariableReadImpl]jar.getModuleVersion();
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Attrs attrs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.header.Attrs();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]moduleName != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]attrs.put([CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.INTERNAL_MODULE_DIRECTIVE, [CtVariableReadImpl]moduleName);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]moduleVersion != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]attrs.put([CtTypeAccessImpl]aQute.bnd.plugin.jpms.INTERNAL_MODULE_VERSION_DIRECTIVE, [CtVariableReadImpl]moduleVersion);
            }
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.stream.MapStream.of([CtInvocationImpl][CtVariableReadImpl]jar.getDirectories()).filter([CtLambdaImpl]([CtParameterImpl] k,[CtParameterImpl] v) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]v != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]v.isEmpty())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]k.isEmpty())).keys().map([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]analyzer::getPackageRef).filter([CtLambdaImpl]([CtParameterImpl] ref) -> [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]ref.isMetaData()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ref.getPath().startsWith([CtFieldReadImpl][CtFieldReferenceImpl]WEB_INF))).forEach([CtLambdaImpl]([CtParameterImpl] ref) -> [CtInvocationImpl][CtVariableReadImpl]index.put([CtVariableReadImpl]ref, [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.header.Attrs([CtVariableReadImpl]attrs)));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder = [CtInvocationImpl]nameAccessAndVersion([CtVariableReadImpl]moduleInstructions, [CtVariableReadImpl]requireCapabilities, [CtVariableReadImpl]analyzer);
        [CtInvocationImpl]requires([CtVariableReadImpl]moduleInstructions, [CtVariableReadImpl]analyzer, [CtVariableReadImpl]index, [CtVariableReadImpl]moduleInfoOptions, [CtVariableReadImpl]builder);
        [CtInvocationImpl]exportPackages([CtVariableReadImpl]analyzer, [CtVariableReadImpl]builder);
        [CtInvocationImpl]openPackages([CtVariableReadImpl]analyzer, [CtVariableReadImpl]builder);
        [CtInvocationImpl]serviceLoaderProviders([CtVariableReadImpl]provideCapabilities, [CtVariableReadImpl]analyzer, [CtVariableReadImpl]builder);
        [CtInvocationImpl]serviceLoaderUses([CtVariableReadImpl]requireCapabilities, [CtVariableReadImpl]analyzer, [CtVariableReadImpl]builder);
        [CtInvocationImpl]mainClass([CtVariableReadImpl]analyzer, [CtVariableReadImpl]builder);
        [CtLocalVariableImpl][CtCommentImpl]// TODO use annotations to store other header info???
        [CtCommentImpl]// AnnotationVisitor visitAnnotation = classWriter.visitAnnotation(...,
        [CtCommentImpl]// false);
        [CtTypeReferenceImpl]aQute.lib.io.ByteBufferDataOutput bbout = [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.lib.io.ByteBufferDataOutput();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.build().write([CtVariableReadImpl]bbout);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getJar().putResource([CtTypeAccessImpl]aQute.bnd.plugin.jpms.MODULE_INFO_CLASS, [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.osgi.EmbeddedResource([CtInvocationImpl][CtVariableReadImpl]bbout.toByteBuffer(), [CtInvocationImpl][CtVariableReadImpl]analyzer.lastModified()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getModuleName([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Jar jar, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters moduleInfoOptions) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String moduleName = [CtInvocationImpl][CtVariableReadImpl]jar.getModuleName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]moduleName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]jar.getSource() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jar.getSource().isDirectory()) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtAssignmentImpl][CtVariableWriteImpl]moduleName = [CtInvocationImpl][CtVariableReadImpl]jar.getName();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher matcher = [CtInvocationImpl][CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.mangledModuleName.matcher([CtVariableReadImpl]moduleName);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]matcher.matches()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]moduleName = [CtInvocationImpl][CtVariableReadImpl]matcher.group([CtLiteralImpl]1);
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String name = [CtVariableReadImpl]moduleName;
            [CtAssignmentImpl][CtVariableWriteImpl]moduleName = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]moduleInfoOptions.stream().filterValue([CtLambdaImpl]([CtParameterImpl] attrs) -> [CtInvocationImpl][CtVariableReadImpl]name.equals([CtInvocationImpl][CtVariableReadImpl]attrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.SUBSTITUTE_ATTRIBUTE))).keys().findFirst().orElse([CtVariableReadImpl]moduleName);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.logger.isWarnEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.logger.warn([CtLiteralImpl]"Using module name '{}' for: {}", [CtVariableReadImpl]moduleName, [CtVariableReadImpl]jar);

        }
        [CtReturnImpl]return [CtVariableReadImpl]moduleName;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]int access([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters requireCapabilities) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]requireCapabilities.stream().filterKey([CtLambdaImpl]([CtParameterImpl] key) -> [CtInvocationImpl][CtInvocationImpl]removeDuplicateMarker([CtVariableReadImpl]key).equals([CtTypeAccessImpl]aQute.bnd.plugin.jpms.EXTENDER_NAMESPACE)).mapToInt([CtLambdaImpl]([CtParameterImpl] k,[CtParameterImpl] v) -> [CtFieldReadImpl][CtFieldReferenceImpl]ACC_OPEN).findAny().orElse([CtLiteralImpl]0);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String name([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]analyzer.getProperty([CtTypeAccessImpl]aQute.bnd.plugin.jpms.AUTOMATIC_MODULE_NAME, [CtInvocationImpl][CtVariableReadImpl]analyzer.getBsn());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void exportPackages([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Packages contained = [CtInvocationImpl][CtVariableReadImpl]analyzer.getContained();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getExports().forEach([CtLambdaImpl]([CtParameterImpl] packageRef,[CtParameterImpl] attrs) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]java.lang.String> targets = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptySet();
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Attrs containedAttrs = [CtInvocationImpl][CtVariableReadImpl]contained.get([CtVariableReadImpl]packageRef);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]containedAttrs != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]containedAttrs.containsKey([CtTypeAccessImpl]aQute.bnd.plugin.jpms.INTERNAL_EXPORT_TO_MODULES_DIRECTIVE)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]targets = [CtInvocationImpl][CtInvocationImpl]splitAsStream([CtInvocationImpl][CtVariableReadImpl]containedAttrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.INTERNAL_EXPORT_TO_MODULES_DIRECTIVE)).collect([CtInvocationImpl]toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtTypeReferenceImpl]LinkedHashSet<[CtTypeReferenceImpl]java.lang.String>::new));
            }
            [CtInvocationImpl][CtCommentImpl]// TODO Do we want to handle access? I can't think of a reason.
            [CtCommentImpl]// Allowed: 0 | ACC_SYNTHETIC | ACC_MANDATED
            [CtVariableReadImpl]builder.exports([CtInvocationImpl][CtVariableReadImpl]packageRef.getBinary(), [CtLiteralImpl]0, [CtVariableReadImpl]targets);
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void mainClass([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mainClass = [CtInvocationImpl][CtVariableReadImpl]analyzer.getProperty([CtTypeAccessImpl]aQute.bnd.plugin.jpms.MAIN_CLASS);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mainClass != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Descriptors.TypeRef typeRef = [CtInvocationImpl][CtVariableReadImpl]analyzer.getTypeRefFromFQN([CtVariableReadImpl]mainClass);
            [CtInvocationImpl][CtVariableReadImpl]builder.mainClass([CtInvocationImpl][CtVariableReadImpl]typeRef.getBinary());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder nameAccessAndVersion([CtParameterImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]aQute.bnd.header.Attrs> instruction, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters requireCapability, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Attrs attrs = [CtInvocationImpl][CtVariableReadImpl]instruction.getValue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtVariableReadImpl]instruction.getKey();
        [CtLocalVariableImpl][CtCommentImpl]// Allowed: 0 | ACC_OPEN | ACC_SYNTHETIC | ACC_MANDATED
        [CtTypeReferenceImpl]java.lang.String access = [CtInvocationImpl][CtVariableReadImpl]attrs.computeIfAbsent([CtTypeAccessImpl]aQute.bnd.plugin.jpms.ACCESS_ATTRIBUTE, [CtLambdaImpl]([CtParameterImpl] k) -> [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl]access([CtVariableReadImpl]requireCapability)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String version = [CtInvocationImpl][CtVariableReadImpl]attrs.computeIfAbsent([CtTypeAccessImpl]aQute.bnd.plugin.jpms.VERSION_ATTRIBUTE, [CtLambdaImpl]([CtParameterImpl] k) -> [CtInvocationImpl][CtVariableReadImpl]analyzer.getVersion());
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder().module_name([CtVariableReadImpl]name).module_version([CtVariableReadImpl]version).module_flags([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.Access.parse([CtVariableReadImpl]access).getValue());
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openPackages([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getContained().stream().filterValue([CtLambdaImpl]([CtParameterImpl] attrs) -> [CtInvocationImpl][CtVariableReadImpl]attrs.containsKey([CtTypeAccessImpl]aQute.bnd.plugin.jpms.INTERNAL_OPEN_TO_MODULES_DIRECTIVE)).forEach([CtLambdaImpl]([CtParameterImpl] packageRef,[CtParameterImpl] attrs) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]java.lang.String> targets = [CtInvocationImpl][CtInvocationImpl]splitAsStream([CtInvocationImpl][CtVariableReadImpl]attrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.INTERNAL_OPEN_TO_MODULES_DIRECTIVE)).collect([CtInvocationImpl]toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtTypeReferenceImpl]LinkedHashSet<[CtTypeReferenceImpl]java.lang.String>::new));
            [CtInvocationImpl][CtCommentImpl]// TODO Do we want to handle access? I can't think of a reason.
            [CtCommentImpl]// Allowed: 0 | ACC_SYNTHETIC | ACC_MANDATED
            [CtVariableReadImpl]builder.opens([CtInvocationImpl][CtVariableReadImpl]packageRef.getBinary(), [CtLiteralImpl]0, [CtVariableReadImpl]targets);
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void requires([CtParameterImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]aQute.bnd.header.Attrs> instruction, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Packages index, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters moduleInfoOptions, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String eeAttribute = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]instruction.getValue().get([CtTypeAccessImpl]Constants.EE_ATTRIBUTE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.build.model.EE moduleEE = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]eeAttribute != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]eeAttribute).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]EE::parse).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"unrecognize ee name: " + [CtVariableReadImpl]eeAttribute)) : [CtFieldReadImpl]aQute.bnd.plugin.jpms.JPMSModuleInfoPlugin.DEFAULT_MODULE_EE;
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Packages exports = [CtInvocationImpl][CtVariableReadImpl]analyzer.getExports();
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Packages imports = [CtInvocationImpl][CtVariableReadImpl]analyzer.getImports();
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Packages referred = [CtInvocationImpl][CtVariableReadImpl]analyzer.getReferred();
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Instructions dynamicImportPackages = [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.osgi.Instructions([CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.header.Parameters([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getJar().getManifest().getMainAttributes().getValue([CtTypeAccessImpl]aQute.bnd.plugin.jpms.DYNAMICIMPORT_PACKAGE)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Packages externallyReferred = [CtConstructorCallImpl]new [CtTypeReferenceImpl]aQute.bnd.osgi.Packages([CtVariableReadImpl]referred);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exports.keySet().forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]externallyReferred::remove);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map.Entry<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]aQute.bnd.osgi.Descriptors.PackageRef, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]aQute.bnd.header.Attrs>>> requiresMap = [CtInvocationImpl][CtCommentImpl]// group packages by module/contract
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]externallyReferred.stream().filterKey([CtLambdaImpl]([CtParameterImpl] packageRef) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Attrs attrs = [CtInvocationImpl][CtVariableReadImpl]index.get([CtVariableReadImpl]packageRef);
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Attrs importAttrs = [CtInvocationImpl][CtVariableReadImpl]imports.get([CtVariableReadImpl]packageRef);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]attrs == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]attrs.containsKey([CtFieldReadImpl][CtFieldReferenceImpl]INTERNAL_MODULE_DIRECTIVE))) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String eeModuleName = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]moduleEE.getModules().stream().filterValue([CtLambdaImpl]([CtParameterImpl] a) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.getTyped([CtVariableReadImpl]Attrs.LIST_STRING, [CtTypeAccessImpl]aQute.bnd.plugin.jpms.EXPORTS_ATTRIBUTE).contains([CtInvocationImpl][CtVariableReadImpl]packageRef.getFQN())).keys().findAny().orElse([CtLiteralImpl]null);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]eeModuleName == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.isWarnEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.warn([CtLiteralImpl]"Can't find a module name for imported package: {}", [CtInvocationImpl][CtVariableReadImpl]packageRef.getFQN());

                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]attrs = [CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.header.Attrs.create([CtFieldReadImpl][CtFieldReferenceImpl]INTERNAL_MODULE_DIRECTIVE, [CtVariableReadImpl]eeModuleName);
                [CtInvocationImpl][CtVariableReadImpl]index.put([CtVariableReadImpl]packageRef, [CtVariableReadImpl]attrs);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]importAttrs != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]attrs.mergeWith([CtVariableReadImpl]importAttrs, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }).collect([CtInvocationImpl]java.util.stream.Collectors.groupingBy([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]index.get([CtInvocationImpl][CtVariableReadImpl]entry.getKey()).get([CtFieldReadImpl][CtFieldReferenceImpl]INTERNAL_MODULE_DIRECTIVE)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String manuallyRequiredModules = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]instruction.getValue().get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.MODULES_ATTRIBUTE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]manuallyRequiredModules != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl]splitAsStream([CtVariableReadImpl]manuallyRequiredModules).forEach([CtLambdaImpl]([CtParameterImpl] moduleToAdd) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requiresMap.computeIfAbsent([CtVariableReadImpl]moduleToAdd, [CtLambdaImpl]([CtParameterImpl] key) -> [CtInvocationImpl]emptyList());
            });
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.stream.MapStream.of([CtVariableReadImpl]requiresMap).sortedByKey().mapValue([CtLambdaImpl]([CtParameterImpl] referencedPackages) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.stream.MapStream.of([CtVariableReadImpl]referencedPackages).keys().collect([CtInvocationImpl]toList())).forEach([CtLambdaImpl]([CtParameterImpl] moduleName,[CtParameterImpl] referencedModulePackages) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Attrs moduleMappingAttrs = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]moduleInfoOptions.get([CtVariableReadImpl]moduleName)).orElseGet([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]aQute.bnd.header.Attrs::new);
            [CtIfImpl]if ([CtInvocationImpl]isTrue([CtInvocationImpl][CtVariableReadImpl]moduleMappingAttrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.IGNORE_ATTRIBUTE))) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtCommentImpl]// An import results in `transitive` requires where there is an
            [CtCommentImpl]// `Export-Package` that has a `uses` constraint on the imported
            [CtCommentImpl]// package.
            [CtTypeReferenceImpl]boolean isTransitive = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]moduleMappingAttrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.TRANSITIVE_ATTRIBUTE)).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]aQute.bnd.osgi.Processor::isTrue).orElseGet([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exports.values().stream().map([CtLambdaImpl]([CtParameterImpl] a) -> [CtInvocationImpl][CtVariableReadImpl]a.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.USES_DIRECTIVE)).flatMap([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]aQute.lib.strings.Strings::splitAsStream).map([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]analyzer::getPackageRef).anyMatch([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]referencedModulePackages::contains));
            [CtLocalVariableImpl][CtCommentImpl]// TODO modules can fall under the follow categories:
            [CtCommentImpl]// a) JDK modules (whose packages are not _yet_ imported)
            [CtCommentImpl]// b) all packages referenced are `resolution:=optional` or
            [CtCommentImpl]// `Dynamic-ImportPackage`
            [CtCommentImpl]// c) statically referenced classes (which do not incur an
            [CtCommentImpl]// import like bundle annotations)
            [CtCommentImpl]// b) and c) result in static requires
            [CtTypeReferenceImpl]boolean isStatic = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]moduleMappingAttrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.STATIC_ATTRIBUTE)).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]aQute.bnd.osgi.Processor::isTrue).orElseGet([CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]referencedModulePackages.isEmpty() || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]referencedModulePackages.stream().allMatch([CtLambdaImpl]([CtParameterImpl] p) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.header.Attrs attrs = [CtInvocationImpl][CtVariableReadImpl]index.get([CtVariableReadImpl]p);
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]aQute.bnd.plugin.jpms.OPTIONAL.equals([CtInvocationImpl][CtVariableReadImpl]attrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.RESOLUTION_DIRECTIVE))) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]dynamicImportPackages.isEmpty()) && [CtInvocationImpl][CtVariableReadImpl]dynamicImportPackages.matches([CtInvocationImpl][CtVariableReadImpl]p.getFQN())) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            }));
            [CtLocalVariableImpl][CtCommentImpl]// Allowed: 0 | ACC_TRANSITIVE | ACC_STATIC_PHASE |
            [CtCommentImpl]// ACC_SYNTHETIC | ACC_MANDATED
            [CtTypeReferenceImpl]int access = [CtBinaryOperatorImpl][CtConditionalImpl]([CtVariableReadImpl]isTransitive ? [CtVariableReadImpl]ModuleAttribute.Require.ACC_TRANSITIVE : [CtLiteralImpl]0) | [CtConditionalImpl]([CtVariableReadImpl]isStatic ? [CtVariableReadImpl]ModuleAttribute.Require.ACC_STATIC_PHASE : [CtLiteralImpl]0);
            [CtInvocationImpl][CtCommentImpl]// TODO collect module version. Do we want module version? It is
            [CtCommentImpl]// not checked at runtime.
            [CtVariableReadImpl]builder.requires([CtVariableReadImpl]moduleName, [CtVariableReadImpl]access, [CtLiteralImpl]null);
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void serviceLoaderProviders([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters provideCapabilities, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// We need the `register:` directive to be present for this to work.
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]provideCapabilities.stream().filterKey([CtLambdaImpl]([CtParameterImpl] namespace) -> [CtInvocationImpl][CtInvocationImpl]removeDuplicateMarker([CtVariableReadImpl]namespace).equals([CtTypeAccessImpl]aQute.bnd.plugin.jpms.SERVICELOADER_NAMESPACE)).filterValue([CtLambdaImpl]([CtParameterImpl] attrs) -> [CtInvocationImpl][CtVariableReadImpl]attrs.containsKey([CtTypeAccessImpl]aQute.bnd.plugin.jpms.SERVICELOADER_REGISTER_DIRECTIVE)).values().collect([CtInvocationImpl]java.util.stream.Collectors.groupingBy([CtLambdaImpl]([CtParameterImpl] attrs) -> [CtInvocationImpl][CtVariableReadImpl]analyzer.getTypeRefFromFQN([CtInvocationImpl][CtVariableReadImpl]attrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.SERVICELOADER_NAMESPACE)))).entrySet().forEach([CtLambdaImpl]([CtParameterImpl] entry) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Descriptors.TypeRef typeRef = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]java.lang.String> impls = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().stream().map([CtLambdaImpl]([CtParameterImpl] attrs) -> [CtInvocationImpl][CtVariableReadImpl]attrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.SERVICELOADER_REGISTER_DIRECTIVE)).map([CtLambdaImpl]([CtParameterImpl] impl) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getTypeRefFromFQN([CtVariableReadImpl]impl).getBinary()).collect([CtInvocationImpl]toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtTypeReferenceImpl]LinkedHashSet<[CtTypeReferenceImpl]java.lang.String>::new));
            [CtInvocationImpl][CtVariableReadImpl]builder.provides([CtInvocationImpl][CtVariableReadImpl]typeRef.getBinary(), [CtVariableReadImpl]impls);
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void serviceLoaderUses([CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.header.Parameters requireCapabilities, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Analyzer analyzer, [CtParameterImpl][CtTypeReferenceImpl]aQute.bnd.classfile.builder.ModuleInfoBuilder builder) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]requireCapabilities.stream().filterKey([CtLambdaImpl]([CtParameterImpl] key) -> [CtInvocationImpl][CtInvocationImpl]removeDuplicateMarker([CtVariableReadImpl]key).equals([CtTypeAccessImpl]aQute.bnd.plugin.jpms.SERVICELOADER_NAMESPACE)).values().forEach([CtLambdaImpl]([CtParameterImpl] attrs) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]aQute.bnd.osgi.Descriptors.TypeRef typeRef = [CtInvocationImpl][CtVariableReadImpl]analyzer.getTypeRefFromFQN([CtInvocationImpl][CtVariableReadImpl]attrs.get([CtTypeAccessImpl]aQute.bnd.plugin.jpms.SERVICELOADER_NAMESPACE));
            [CtInvocationImpl][CtVariableReadImpl]builder.uses([CtInvocationImpl][CtVariableReadImpl]typeRef.getBinary());
        });
    }

    [CtMethodImpl]static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeParameterReferenceImpl]T> distinctByKey([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]?> keyExtractor) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Object> seen = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.ConcurrentHashMap.newKeySet();
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl] t) -> [CtInvocationImpl][CtVariableReadImpl]seen.add([CtInvocationImpl][CtVariableReadImpl]keyExtractor.apply([CtVariableReadImpl]t));
    }
}
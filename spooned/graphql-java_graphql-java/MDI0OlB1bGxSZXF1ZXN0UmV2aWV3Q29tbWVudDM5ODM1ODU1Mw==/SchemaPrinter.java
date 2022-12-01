[CompilationUnitImpl][CtPackageDeclarationImpl]package graphql.schema.idl;
[CtImportImpl]import java.util.function.Predicate;
[CtImportImpl]import static java.util.Optional.ofNullable;
[CtUnresolvedImport]import graphql.schema.GraphqlTypeComparatorRegistry;
[CtUnresolvedImport]import graphql.schema.GraphQLInputObjectType;
[CtUnresolvedImport]import static graphql.introspection.Introspection.DirectiveLocation.FIELD_DEFINITION;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import graphql.schema.GraphQLFieldDefinition;
[CtImportImpl]import static java.util.stream.Collectors.toList;
[CtUnresolvedImport]import graphql.schema.GraphQLInterfaceType;
[CtUnresolvedImport]import graphql.language.InputValueDefinition;
[CtUnresolvedImport]import graphql.schema.GraphQLInputObjectField;
[CtUnresolvedImport]import graphql.schema.GraphQLUnionType;
[CtUnresolvedImport]import graphql.schema.GraphQLEnumValueDefinition;
[CtImportImpl]import java.util.LinkedHashMap;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import graphql.language.EnumTypeDefinition;
[CtUnresolvedImport]import graphql.language.Description;
[CtUnresolvedImport]import graphql.language.EnumValueDefinition;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import graphql.schema.DefaultGraphqlTypeComparatorRegistry;
[CtUnresolvedImport]import graphql.schema.GraphQLObjectType;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import graphql.schema.GraphQLDirective;
[CtUnresolvedImport]import graphql.language.ObjectTypeDefinition;
[CtUnresolvedImport]import graphql.schema.GraphQLNamedOutputType;
[CtUnresolvedImport]import graphql.schema.visibility.GraphqlFieldVisibility;
[CtUnresolvedImport]import graphql.language.FieldDefinition;
[CtUnresolvedImport]import graphql.schema.GraphQLInputType;
[CtUnresolvedImport]import graphql.language.TypeDefinition;
[CtUnresolvedImport]import graphql.schema.GraphQLArgument;
[CtUnresolvedImport]import graphql.language.AstPrinter;
[CtUnresolvedImport]import static graphql.introspection.Introspection.DirectiveLocation.ENUM_VALUE;
[CtUnresolvedImport]import graphql.language.InterfaceTypeDefinition;
[CtUnresolvedImport]import graphql.schema.GraphQLEnumType;
[CtUnresolvedImport]import graphql.schema.GraphqlTypeComparatorEnvironment;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import static java.util.stream.Collectors.joining;
[CtUnresolvedImport]import graphql.schema.GraphQLSchemaElement;
[CtUnresolvedImport]import graphql.schema.GraphQLNamedType;
[CtUnresolvedImport]import graphql.schema.GraphQLScalarType;
[CtUnresolvedImport]import graphql.language.InputObjectTypeDefinition;
[CtUnresolvedImport]import graphql.schema.GraphQLType;
[CtUnresolvedImport]import graphql.language.AstValueHelper;
[CtUnresolvedImport]import graphql.language.UnionTypeDefinition;
[CtImportImpl]import java.io.StringWriter;
[CtUnresolvedImport]import static graphql.Directives.DeprecatedDirective;
[CtUnresolvedImport]import graphql.schema.GraphQLSchema;
[CtUnresolvedImport]import graphql.schema.GraphQLTypeUtil;
[CtUnresolvedImport]import graphql.Assert;
[CtUnresolvedImport]import graphql.language.ScalarTypeDefinition;
[CtImportImpl]import java.io.PrintWriter;
[CtUnresolvedImport]import static graphql.schema.visibility.DefaultGraphqlFieldVisibility.DEFAULT_FIELD_VISIBILITY;
[CtUnresolvedImport]import graphql.schema.GraphQLOutputType;
[CtUnresolvedImport]import org.apache.commons.text.StringEscapeUtils;
[CtUnresolvedImport]import graphql.language.Document;
[CtUnresolvedImport]import graphql.PublicApi;
[CtClassImpl][CtJavaDocImpl]/**
 * This can print an in memory GraphQL schema back to a logical schema definition
 */
[CtAnnotationImpl]@graphql.PublicApi
public class SchemaPrinter {
    [CtFieldImpl][CtCommentImpl]// 
    [CtCommentImpl]// we use this so that we get the simple "@deprecated" as text and not a full exploded
    [CtCommentImpl]// text with arguments (but only when we auto add this)
    [CtCommentImpl]// 
    private static final [CtTypeReferenceImpl]graphql.schema.GraphQLDirective DeprecatedDirective4Printing = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphQLDirective.newDirective().name([CtLiteralImpl]"deprecated").validLocations([CtTypeAccessImpl]graphql.introspection.Introspection.DirectiveLocation.FIELD_DEFINITION, [CtTypeAccessImpl]graphql.introspection.Introspection.DirectiveLocation.ENUM_VALUE).build();

    [CtClassImpl][CtJavaDocImpl]/**
     * Options to use when printing a schema
     */
    public static class Options {
        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean includeIntrospectionTypes;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean includeScalars;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean useAstDefinitions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean includeSchemaDefinition;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean descriptionsAsHashComments;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> includeDirective;

        [CtFieldImpl]private final [CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorRegistry comparatorRegistry;

        [CtConstructorImpl]private Options([CtParameterImpl][CtTypeReferenceImpl]boolean includeIntrospectionTypes, [CtParameterImpl][CtTypeReferenceImpl]boolean includeScalars, [CtParameterImpl][CtTypeReferenceImpl]boolean includeSchemaDefinition, [CtParameterImpl][CtTypeReferenceImpl]boolean useAstDefinitions, [CtParameterImpl][CtTypeReferenceImpl]boolean descriptionsAsHashComments, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> includeDirective, [CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorRegistry comparatorRegistry) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeIntrospectionTypes = [CtVariableReadImpl]includeIntrospectionTypes;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeScalars = [CtVariableReadImpl]includeScalars;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeSchemaDefinition = [CtVariableReadImpl]includeSchemaDefinition;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeDirective = [CtVariableReadImpl]includeDirective;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.useAstDefinitions = [CtVariableReadImpl]useAstDefinitions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.descriptionsAsHashComments = [CtVariableReadImpl]descriptionsAsHashComments;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.comparatorRegistry = [CtVariableReadImpl]comparatorRegistry;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isIncludeIntrospectionTypes() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]includeIntrospectionTypes;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isIncludeScalars() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]includeScalars;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isIncludeSchemaDefinition() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]includeSchemaDefinition;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> getIncludeDirective() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]includeDirective;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isDescriptionsAsHashComments() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]descriptionsAsHashComments;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorRegistry getComparatorRegistry() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]comparatorRegistry;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isUseAstDefinitions() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]useAstDefinitions;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options defaultOptions() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtLiteralImpl]false, [CtLiteralImpl]true, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLambdaImpl]([CtParameterImpl] directive) -> [CtLiteralImpl]true, [CtInvocationImpl][CtTypeAccessImpl]graphql.schema.DefaultGraphqlTypeComparatorRegistry.defaultComparators());
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * This will allow you to include introspection types that are contained in a schema
         *
         * @param flag
         * 		whether to include them
         * @return options
         */
        public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options includeIntrospectionTypes([CtParameterImpl][CtTypeReferenceImpl]boolean flag) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtVariableReadImpl]flag, [CtFieldReadImpl][CtThisAccessImpl]this.includeScalars, [CtFieldReadImpl][CtThisAccessImpl]this.includeSchemaDefinition, [CtFieldReadImpl][CtThisAccessImpl]this.useAstDefinitions, [CtFieldReadImpl][CtThisAccessImpl]this.descriptionsAsHashComments, [CtFieldReadImpl][CtThisAccessImpl]this.includeDirective, [CtFieldReadImpl][CtThisAccessImpl]this.comparatorRegistry);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * This will allow you to include scalar types that are contained in a schema
         *
         * @param flag
         * 		whether to include them
         * @return options
         */
        public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options includeScalarTypes([CtParameterImpl][CtTypeReferenceImpl]boolean flag) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtFieldReadImpl][CtThisAccessImpl]this.includeIntrospectionTypes, [CtVariableReadImpl]flag, [CtFieldReadImpl][CtThisAccessImpl]this.includeSchemaDefinition, [CtFieldReadImpl][CtThisAccessImpl]this.useAstDefinitions, [CtFieldReadImpl][CtThisAccessImpl]this.descriptionsAsHashComments, [CtFieldReadImpl][CtThisAccessImpl]this.includeDirective, [CtFieldReadImpl][CtThisAccessImpl]this.comparatorRegistry);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * This will force the printing of the graphql schema definition even if the query, mutation, and/or subscription
         * types use the default names.  Some graphql parsers require this information even if the schema uses the
         * default type names.  The schema definition will always be printed if any of the query, mutation, or subscription
         * types do not use the default names.
         *
         * @param flag
         * 		whether to force include the schema definition
         * @return options
         */
        public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options includeSchemaDefinition([CtParameterImpl][CtTypeReferenceImpl]boolean flag) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtFieldReadImpl][CtThisAccessImpl]this.includeIntrospectionTypes, [CtFieldReadImpl][CtThisAccessImpl]this.includeScalars, [CtVariableReadImpl]flag, [CtFieldReadImpl][CtThisAccessImpl]this.useAstDefinitions, [CtFieldReadImpl][CtThisAccessImpl]this.descriptionsAsHashComments, [CtFieldReadImpl][CtThisAccessImpl]this.includeDirective, [CtFieldReadImpl][CtThisAccessImpl]this.comparatorRegistry);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Allow to print directives. In some situations, auto-generated schemas contain a lot of directives that
         * make the printout noisy and having this flag would allow cleaner printout. On by default.
         *
         * @param flag
         * 		whether to print directives
         * @return new instance of options
         */
        public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options includeDirectives([CtParameterImpl][CtTypeReferenceImpl]boolean flag) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtFieldReadImpl][CtThisAccessImpl]this.includeIntrospectionTypes, [CtFieldReadImpl][CtThisAccessImpl]this.includeScalars, [CtFieldReadImpl][CtThisAccessImpl]this.includeSchemaDefinition, [CtFieldReadImpl][CtThisAccessImpl]this.useAstDefinitions, [CtFieldReadImpl][CtThisAccessImpl]this.descriptionsAsHashComments, [CtLambdaImpl]([CtParameterImpl] directive) -> [CtVariableReadImpl]flag, [CtFieldReadImpl][CtThisAccessImpl]this.comparatorRegistry);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options includeDirectives([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> includeDirective) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtFieldReadImpl][CtThisAccessImpl]this.includeIntrospectionTypes, [CtFieldReadImpl][CtThisAccessImpl]this.includeScalars, [CtFieldReadImpl][CtThisAccessImpl]this.includeSchemaDefinition, [CtFieldReadImpl][CtThisAccessImpl]this.useAstDefinitions, [CtFieldReadImpl][CtThisAccessImpl]this.descriptionsAsHashComments, [CtVariableReadImpl]includeDirective, [CtFieldReadImpl][CtThisAccessImpl]this.comparatorRegistry);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * This flag controls whether schema printer will use the {@link graphql.schema.GraphQLType}'s original Ast {@link graphql.language.TypeDefinition}s when printing the type.  This
         * allows access to any `extend type` declarations that might have been originally made.
         *
         * @param flag
         * 		whether to print via AST type definitions
         * @return new instance of options
         */
        public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options useAstDefinitions([CtParameterImpl][CtTypeReferenceImpl]boolean flag) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtFieldReadImpl][CtThisAccessImpl]this.includeIntrospectionTypes, [CtFieldReadImpl][CtThisAccessImpl]this.includeScalars, [CtFieldReadImpl][CtThisAccessImpl]this.includeSchemaDefinition, [CtVariableReadImpl]flag, [CtFieldReadImpl][CtThisAccessImpl]this.descriptionsAsHashComments, [CtFieldReadImpl][CtThisAccessImpl]this.includeDirective, [CtFieldReadImpl][CtThisAccessImpl]this.comparatorRegistry);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Descriptions are defined as preceding string literals, however an older legacy
         * versions of SDL supported preceding '#' comments as
         * descriptions. Set this to true to enable this deprecated behavior.
         * This option is provided to ease adoption and may be removed in future versions.
         *
         * @param flag
         * 		whether to print description as # comments
         * @return new instance of options
         */
        public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options descriptionsAsHashComments([CtParameterImpl][CtTypeReferenceImpl]boolean flag) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtFieldReadImpl][CtThisAccessImpl]this.includeIntrospectionTypes, [CtFieldReadImpl][CtThisAccessImpl]this.includeScalars, [CtFieldReadImpl][CtThisAccessImpl]this.includeSchemaDefinition, [CtFieldReadImpl][CtThisAccessImpl]this.useAstDefinitions, [CtVariableReadImpl]flag, [CtFieldReadImpl][CtThisAccessImpl]this.includeDirective, [CtFieldReadImpl][CtThisAccessImpl]this.comparatorRegistry);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * The comparator registry controls the printing order for registered {@code GraphQLType}s.
         * <p>
         * The default is to sort elements by name but you can put in your own code to decide on the field order
         *
         * @param comparatorRegistry
         * 		The registry containing the {@code Comparator} and environment scoping rules.
         * @return options
         */
        public [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options setComparators([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorRegistry comparatorRegistry) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options([CtFieldReadImpl][CtThisAccessImpl]this.includeIntrospectionTypes, [CtFieldReadImpl][CtThisAccessImpl]this.includeScalars, [CtFieldReadImpl][CtThisAccessImpl]this.includeSchemaDefinition, [CtFieldReadImpl][CtThisAccessImpl]this.useAstDefinitions, [CtFieldReadImpl][CtThisAccessImpl]this.descriptionsAsHashComments, [CtFieldReadImpl][CtThisAccessImpl]this.includeDirective, [CtVariableReadImpl]comparatorRegistry);
        }
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class, [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtWildcardReferenceImpl]?>> printers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options options;

    [CtConstructorImpl]public SchemaPrinter() [CtBlockImpl]{
        [CtInvocationImpl]this([CtInvocationImpl][CtTypeAccessImpl]graphql.schema.idl.SchemaPrinter.Options.defaultOptions());
    }

    [CtConstructorImpl]public SchemaPrinter([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.Options options) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.options = [CtVariableReadImpl]options;
        [CtInvocationImpl][CtFieldReadImpl]printers.put([CtFieldReadImpl]graphql.schema.GraphQLSchema.class, [CtInvocationImpl]schemaPrinter());
        [CtInvocationImpl][CtFieldReadImpl]printers.put([CtFieldReadImpl]graphql.schema.GraphQLObjectType.class, [CtInvocationImpl]objectPrinter());
        [CtInvocationImpl][CtFieldReadImpl]printers.put([CtFieldReadImpl]graphql.schema.GraphQLEnumType.class, [CtInvocationImpl]enumPrinter());
        [CtInvocationImpl][CtFieldReadImpl]printers.put([CtFieldReadImpl]graphql.schema.GraphQLScalarType.class, [CtInvocationImpl]scalarPrinter());
        [CtInvocationImpl][CtFieldReadImpl]printers.put([CtFieldReadImpl]graphql.schema.GraphQLInterfaceType.class, [CtInvocationImpl]interfacePrinter());
        [CtInvocationImpl][CtFieldReadImpl]printers.put([CtFieldReadImpl]graphql.schema.GraphQLUnionType.class, [CtInvocationImpl]unionPrinter());
        [CtInvocationImpl][CtFieldReadImpl]printers.put([CtFieldReadImpl]graphql.schema.GraphQLInputObjectType.class, [CtInvocationImpl]inputObjectPrinter());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This can print an in memory GraphQL IDL document back to a logical schema definition.
     * If you want to turn a Introspection query result into a Document (and then into a printed
     * schema) then use {@link graphql.introspection.IntrospectionResultToSchema#createSchemaDefinition(java.util.Map)}
     * first to get the {@link graphql.language.Document} and then print that.
     *
     * @param schemaIDL
     * 		the parsed schema IDL
     * @return the logical schema definition
     */
    public [CtTypeReferenceImpl]java.lang.String print([CtParameterImpl][CtTypeReferenceImpl]graphql.language.Document schemaIDL) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.idl.TypeDefinitionRegistry registry = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]graphql.schema.idl.SchemaParser().buildRegistry([CtVariableReadImpl]schemaIDL);
        [CtReturnImpl]return [CtInvocationImpl]print([CtInvocationImpl][CtTypeAccessImpl]graphql.schema.idl.UnExecutableSchemaGenerator.makeUnExecutableSchema([CtVariableReadImpl]registry));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This can print an in memory GraphQL schema back to a logical schema definition
     *
     * @param schema
     * 		the schema in play
     * @return the logical schema definition
     */
    public [CtTypeReferenceImpl]java.lang.String print([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLSchema schema) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintWriter out = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw);
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getCodeRegistry().getFieldVisibility();
        [CtInvocationImpl][CtInvocationImpl]printer([CtInvocationImpl][CtVariableReadImpl]schema.getClass()).print([CtVariableReadImpl]out, [CtVariableReadImpl]schema, [CtVariableReadImpl]visibility);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLType> typesAsList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getAllTypesAsList().stream().sorted([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparing([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]GraphQLNamedType::getName)).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]typesAsList, [CtFieldReadImpl]graphql.schema.GraphQLInterfaceType.class, [CtVariableReadImpl]visibility);
        [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]typesAsList, [CtFieldReadImpl]graphql.schema.GraphQLUnionType.class, [CtVariableReadImpl]visibility);
        [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]typesAsList, [CtFieldReadImpl]graphql.schema.GraphQLObjectType.class, [CtVariableReadImpl]visibility);
        [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]typesAsList, [CtFieldReadImpl]graphql.schema.GraphQLEnumType.class, [CtVariableReadImpl]visibility);
        [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]typesAsList, [CtFieldReadImpl]graphql.schema.GraphQLScalarType.class, [CtVariableReadImpl]visibility);
        [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]typesAsList, [CtFieldReadImpl]graphql.schema.GraphQLInputObjectType.class, [CtVariableReadImpl]visibility);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String result = [CtInvocationImpl][CtVariableReadImpl]sw.toString();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.endsWith([CtLiteralImpl]"\n\n")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]result.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.length() - [CtLiteralImpl]1);
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtInterfaceImpl]private interface TypePrinter<[CtTypeParameterImpl]T> {
        [CtMethodImpl][CtTypeReferenceImpl]void print([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeParameterReferenceImpl]T type, [CtParameterImpl][CtTypeReferenceImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isIntrospectionType([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLNamedType type) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]options.isIncludeIntrospectionTypes()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getName().startsWith([CtLiteralImpl]"__");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]graphql.schema.GraphQLScalarType> scalarPrinter() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]graphql.schema.GraphQLScalarType type,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]options.isIncludeScalars()) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean printScalar;
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]graphql.schema.idl.ScalarInfo.isGraphqlSpecifiedScalar([CtVariableReadImpl]type)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]printScalar = [CtLiteralImpl]false;
                [CtIfImpl][CtCommentImpl]// noinspection RedundantIfStatement
                if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]graphql.schema.idl.ScalarInfo.isGraphqlSpecifiedScalar([CtVariableReadImpl]type)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]printScalar = [CtLiteralImpl]true;
                }
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]printScalar = [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtVariableReadImpl]printScalar) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]shouldPrintAsAst([CtInvocationImpl][CtVariableReadImpl]type.getDefinition())) [CtBlockImpl]{
                    [CtInvocationImpl]printAsAst([CtVariableReadImpl]out, [CtInvocationImpl][CtVariableReadImpl]type.getDefinition(), [CtInvocationImpl][CtVariableReadImpl]type.getExtensionDefinitions());
                } else [CtBlockImpl]{
                    [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtLiteralImpl]"");
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"scalar %s%s\n\n", [CtInvocationImpl][CtVariableReadImpl]type.getName(), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLScalarType.class, [CtInvocationImpl][CtVariableReadImpl]type.getDirectives()));
                }
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]graphql.schema.GraphQLEnumType> enumPrinter() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]graphql.schema.GraphQLEnumType type,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isIntrospectionType([CtVariableReadImpl]type)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLEnumType.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLEnumValueDefinition.class).build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
            [CtIfImpl]if ([CtInvocationImpl]shouldPrintAsAst([CtInvocationImpl][CtVariableReadImpl]type.getDefinition())) [CtBlockImpl]{
                [CtInvocationImpl]printAsAst([CtVariableReadImpl]out, [CtInvocationImpl][CtVariableReadImpl]type.getDefinition(), [CtInvocationImpl][CtVariableReadImpl]type.getExtensionDefinitions());
            } else [CtBlockImpl]{
                [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtLiteralImpl]"");
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"enum %s%s {\n", [CtInvocationImpl][CtVariableReadImpl]type.getName(), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLEnumType.class, [CtInvocationImpl][CtVariableReadImpl]type.getDirectives()));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLEnumValueDefinition> values = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getValues().stream().sorted([CtVariableReadImpl]comparator).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLEnumValueDefinition enumValueDefinition : [CtVariableReadImpl]values) [CtBlockImpl]{
                    [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]enumValueDefinition, [CtLiteralImpl]"  ");
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> enumValueDirectives = [CtInvocationImpl][CtVariableReadImpl]enumValueDefinition.getDirectives();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]enumValueDefinition.isDeprecated()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]enumValueDirectives = [CtInvocationImpl]addDeprecatedDirectiveIfNeeded([CtVariableReadImpl]enumValueDirectives);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"  %s%s\n", [CtInvocationImpl][CtVariableReadImpl]enumValueDefinition.getName(), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLEnumValueDefinition.class, [CtVariableReadImpl]enumValueDirectives));
                }
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"}\n\n");
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void printFieldDefinitions([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLFieldDefinition> fieldDefinitions) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fieldDefinitions.stream().sorted([CtVariableReadImpl]comparator).forEach([CtLambdaImpl]([CtParameterImpl] fd) -> [CtBlockImpl]{
            [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]fd, [CtLiteralImpl]"  ");
            [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> fieldDirectives = [CtInvocationImpl][CtVariableReadImpl]fd.getDirectives();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fd.isDeprecated()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]fieldDirectives = [CtInvocationImpl]addDeprecatedDirectiveIfNeeded([CtVariableReadImpl]fieldDirectives);
            }
            [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"  %s%s: %s%s\n", [CtInvocationImpl][CtVariableReadImpl]fd.getName(), [CtInvocationImpl]argsString([CtFieldReadImpl]graphql.schema.GraphQLFieldDefinition.class, [CtInvocationImpl][CtVariableReadImpl]fd.getArguments()), [CtInvocationImpl]typeString([CtInvocationImpl][CtVariableReadImpl]fd.getType()), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLFieldDefinition.class, [CtVariableReadImpl]fieldDirectives));
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]graphql.schema.GraphQLInterfaceType> interfacePrinter() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]graphql.schema.GraphQLInterfaceType type,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isIntrospectionType([CtVariableReadImpl]type)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLInterfaceType.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLFieldDefinition.class).build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
            [CtIfImpl]if ([CtInvocationImpl]shouldPrintAsAst([CtInvocationImpl][CtVariableReadImpl]type.getDefinition())) [CtBlockImpl]{
                [CtInvocationImpl]printAsAst([CtVariableReadImpl]out, [CtInvocationImpl][CtVariableReadImpl]type.getDefinition(), [CtInvocationImpl][CtVariableReadImpl]type.getExtensionDefinitions());
            } else [CtBlockImpl]{
                [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtLiteralImpl]"");
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"interface %s%s {\n", [CtInvocationImpl][CtVariableReadImpl]type.getName(), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLInterfaceType.class, [CtInvocationImpl][CtVariableReadImpl]type.getDirectives()));
                [CtInvocationImpl]printFieldDefinitions([CtVariableReadImpl]out, [CtVariableReadImpl]comparator, [CtInvocationImpl][CtVariableReadImpl]visibility.getFieldDefinitions([CtVariableReadImpl]type));
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"}\n\n");
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]graphql.schema.GraphQLUnionType> unionPrinter() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]graphql.schema.GraphQLUnionType type,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isIntrospectionType([CtVariableReadImpl]type)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLUnionType.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLOutputType.class).build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
            [CtIfImpl]if ([CtInvocationImpl]shouldPrintAsAst([CtInvocationImpl][CtVariableReadImpl]type.getDefinition())) [CtBlockImpl]{
                [CtInvocationImpl]printAsAst([CtVariableReadImpl]out, [CtInvocationImpl][CtVariableReadImpl]type.getDefinition(), [CtInvocationImpl][CtVariableReadImpl]type.getExtensionDefinitions());
            } else [CtBlockImpl]{
                [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtLiteralImpl]"");
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"union %s%s = ", [CtInvocationImpl][CtVariableReadImpl]type.getName(), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLUnionType.class, [CtInvocationImpl][CtVariableReadImpl]type.getDirectives()));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLNamedOutputType> types = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getTypes().stream().sorted([CtVariableReadImpl]comparator).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]types.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLNamedOutputType objectType = [CtInvocationImpl][CtVariableReadImpl]types.get([CtVariableReadImpl]i);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]" | ");
                    }
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"%s", [CtInvocationImpl][CtVariableReadImpl]objectType.getName());
                }
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"\n\n");
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]graphql.schema.GraphQLObjectType> objectPrinter() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]graphql.schema.GraphQLObjectType type,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isIntrospectionType([CtVariableReadImpl]type)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtInvocationImpl]shouldPrintAsAst([CtInvocationImpl][CtVariableReadImpl]type.getDefinition())) [CtBlockImpl]{
                [CtInvocationImpl]printAsAst([CtVariableReadImpl]out, [CtInvocationImpl][CtVariableReadImpl]type.getDefinition(), [CtInvocationImpl][CtVariableReadImpl]type.getExtensionDefinitions());
            } else [CtBlockImpl]{
                [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtLiteralImpl]"");
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getInterfaces().isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"type %s%s {\n", [CtInvocationImpl][CtVariableReadImpl]type.getName(), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLObjectType.class, [CtInvocationImpl][CtVariableReadImpl]type.getDirectives()));
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLObjectType.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLOutputType.class).build();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> implementsComparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]java.lang.String> interfaceNames = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getInterfaces().stream().sorted([CtVariableReadImpl]implementsComparator).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]GraphQLNamedType::getName);
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"type %s implements %s%s {\n", [CtInvocationImpl][CtVariableReadImpl]type.getName(), [CtInvocationImpl][CtVariableReadImpl]interfaceNames.collect([CtInvocationImpl]java.util.stream.Collectors.joining([CtLiteralImpl]" & ")), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLObjectType.class, [CtInvocationImpl][CtVariableReadImpl]type.getDirectives()));
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLObjectType.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLFieldDefinition.class).build();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
                [CtInvocationImpl]printFieldDefinitions([CtVariableReadImpl]out, [CtVariableReadImpl]comparator, [CtInvocationImpl][CtVariableReadImpl]visibility.getFieldDefinitions([CtVariableReadImpl]type));
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"}\n\n");
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]graphql.schema.GraphQLInputObjectType> inputObjectPrinter() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]graphql.schema.GraphQLInputObjectType type,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isIntrospectionType([CtVariableReadImpl]type)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtInvocationImpl]shouldPrintAsAst([CtInvocationImpl][CtVariableReadImpl]type.getDefinition())) [CtBlockImpl]{
                [CtInvocationImpl]printAsAst([CtVariableReadImpl]out, [CtInvocationImpl][CtVariableReadImpl]type.getDefinition(), [CtInvocationImpl][CtVariableReadImpl]type.getExtensionDefinitions());
            } else [CtBlockImpl]{
                [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtLiteralImpl]"");
                [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLInputObjectType.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLInputObjectField.class).build();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"input %s%s {\n", [CtInvocationImpl][CtVariableReadImpl]type.getName(), [CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLInputObjectType.class, [CtInvocationImpl][CtVariableReadImpl]type.getDirectives()));
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]visibility.getFieldDefinitions([CtVariableReadImpl]type).stream().sorted([CtVariableReadImpl]comparator).forEach([CtLambdaImpl]([CtParameterImpl] fd) -> [CtBlockImpl]{
                    [CtInvocationImpl]printComments([CtVariableReadImpl]out, [CtVariableReadImpl]fd, [CtLiteralImpl]"  ");
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"  %s: %s", [CtInvocationImpl][CtVariableReadImpl]fd.getName(), [CtInvocationImpl]typeString([CtInvocationImpl][CtVariableReadImpl]fd.getType()));
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object defaultValue = [CtInvocationImpl][CtVariableReadImpl]fd.getDefaultValue();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]defaultValue != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String astValue = [CtInvocationImpl]printAst([CtVariableReadImpl]defaultValue, [CtInvocationImpl][CtVariableReadImpl]fd.getType());
                        [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]" = %s", [CtVariableReadImpl]astValue);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtInvocationImpl]directivesString([CtFieldReadImpl]graphql.schema.GraphQLInputObjectField.class, [CtInvocationImpl][CtVariableReadImpl]fd.getDirectives()));
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"\n");
                });
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"}\n\n");
            }
        };
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This will return true if the options say to use the AST and we have an AST element
     *
     * @param definition
     * 		the AST type definition
     * @return true if we should print using AST nodes
     */
    private [CtTypeReferenceImpl]boolean shouldPrintAsAst([CtParameterImpl][CtTypeReferenceImpl]graphql.language.TypeDefinition definition) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]options.isUseAstDefinitions() && [CtBinaryOperatorImpl]([CtVariableReadImpl]definition != [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This will print out a runtime graphql schema element using its contained AST type definition.  This
     * must be guarded by a called to {@link #shouldPrintAsAst(TypeDefinition)}
     *
     * @param out
     * 		the output writer
     * @param definition
     * 		the AST type definition
     * @param extensions
     * 		a list of type definition extensions
     */
    private [CtTypeReferenceImpl]void printAsAst([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]graphql.language.TypeDefinition definition, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]graphql.language.TypeDefinition> extensions) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]out.printf([CtLiteralImpl]"%s\n", [CtInvocationImpl][CtTypeAccessImpl]graphql.language.AstPrinter.printAst([CtVariableReadImpl]definition));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]extensions != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]graphql.language.TypeDefinition extension : [CtVariableReadImpl]extensions) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]out.printf([CtLiteralImpl]"\n%s\n", [CtInvocationImpl][CtTypeAccessImpl]graphql.language.AstPrinter.printAst([CtVariableReadImpl]extension));
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]out.println();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String printAst([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLInputType type) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]graphql.language.AstPrinter.printAst([CtInvocationImpl][CtTypeAccessImpl]graphql.language.AstValueHelper.astFromValue([CtVariableReadImpl]value, [CtVariableReadImpl]type));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]graphql.schema.GraphQLSchema> schemaPrinter() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]graphql.schema.GraphQLSchema schema,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLObjectType queryType = [CtInvocationImpl][CtVariableReadImpl]schema.getQueryType();
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLObjectType mutationType = [CtInvocationImpl][CtVariableReadImpl]schema.getMutationType();
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLObjectType subscriptionType = [CtInvocationImpl][CtVariableReadImpl]schema.getSubscriptionType();
            [CtLocalVariableImpl][CtCommentImpl]// when serializing a GraphQL schema using the type system language, a
            [CtCommentImpl]// schema definition should be omitted if only uses the default root type names.
            [CtTypeReferenceImpl]boolean needsSchemaPrinted = [CtInvocationImpl][CtFieldReadImpl]options.isIncludeSchemaDefinition();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]needsSchemaPrinted) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]queryType != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]queryType.getName().equals([CtLiteralImpl]"Query"))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]needsSchemaPrinted = [CtLiteralImpl]true;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]mutationType != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mutationType.getName().equals([CtLiteralImpl]"Mutation"))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]needsSchemaPrinted = [CtLiteralImpl]true;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]subscriptionType != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]subscriptionType.getName().equals([CtLiteralImpl]"Subscription"))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]needsSchemaPrinted = [CtLiteralImpl]true;
                }
            }
            [CtIfImpl]if ([CtVariableReadImpl]needsSchemaPrinted) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"schema {\n");
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]queryType != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"  query: %s\n", [CtInvocationImpl][CtVariableReadImpl]queryType.getName());
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mutationType != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"  mutation: %s\n", [CtInvocationImpl][CtVariableReadImpl]mutationType.getName());
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]subscriptionType != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"  subscription: %s\n", [CtInvocationImpl][CtVariableReadImpl]subscriptionType.getName());
                }
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"}\n\n");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> directives = [CtInvocationImpl]getSchemaDirectives([CtVariableReadImpl]schema);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]directives.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]out.format([CtLiteralImpl]"%s", [CtInvocationImpl]directiveDefinitions([CtVariableReadImpl]directives));
            }
        };
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> getSchemaDirectives([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLSchema schema) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getDirectives().stream().filter([CtInvocationImpl][CtFieldReadImpl]options.getIncludeDirective()).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String typeString([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLType rawType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphQLTypeUtil.simplePrint([CtVariableReadImpl]rawType);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String argsString([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLArgument> arguments) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]argsString([CtLiteralImpl]null, [CtVariableReadImpl]arguments);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String argsString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> parent, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLArgument> arguments) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasDescriptions = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]arguments.stream().anyMatch([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::hasDescription);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String halfPrefix = [CtConditionalImpl]([CtVariableReadImpl]hasDescriptions) ? [CtLiteralImpl]"  " : [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String prefix = [CtConditionalImpl]([CtVariableReadImpl]hasDescriptions) ? [CtLiteralImpl]"    " : [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtVariableReadImpl]parent).elementType([CtFieldReadImpl]graphql.schema.GraphQLArgument.class).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
        [CtAssignmentImpl][CtVariableWriteImpl]arguments = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]arguments.stream().sorted([CtVariableReadImpl]comparator).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLArgument argument : [CtVariableReadImpl]arguments) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]count == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(");
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]", ");
            }
            [CtIfImpl]if ([CtVariableReadImpl]hasDescriptions) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n");
            }
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl]printComments([CtVariableReadImpl]argument, [CtVariableReadImpl]prefix));
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]prefix).append([CtInvocationImpl][CtVariableReadImpl]argument.getName()).append([CtLiteralImpl]": ").append([CtInvocationImpl]typeString([CtInvocationImpl][CtVariableReadImpl]argument.getType()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object defaultValue = [CtInvocationImpl][CtVariableReadImpl]argument.getDefaultValue();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]defaultValue != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" = ");
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl]graphql.schema.idl.SchemaPrinter.printAst([CtVariableReadImpl]defaultValue, [CtInvocationImpl][CtVariableReadImpl]argument.getType()));
            }
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]argument.getDirectives().stream().map([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::directiveString).filter([CtLambdaImpl]([CtParameterImpl] it) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]it.isEmpty()).forEach([CtLambdaImpl]([CtParameterImpl] directiveString) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ").append([CtVariableReadImpl]directiveString));
            [CtUnaryOperatorImpl][CtVariableWriteImpl]count++;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]count > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]hasDescriptions) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n");
            }
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]halfPrefix).append([CtLiteralImpl]")");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String directivesString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> parent, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> directives) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]directives = [CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// @deprecated is special - we always print it if something is deprecated
        [CtInvocationImpl][CtVariableReadImpl]directives.stream().filter([CtLambdaImpl]([CtParameterImpl] directive) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.getIncludeDirective().test([CtVariableReadImpl]directive) || [CtInvocationImpl]isDeprecatedDirective([CtVariableReadImpl]directive)).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]directives.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]directives.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtVariableReadImpl]parent).elementType([CtFieldReadImpl]graphql.schema.GraphQLDirective.class).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
        [CtAssignmentImpl][CtVariableWriteImpl]directives = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]directives.stream().sorted([CtVariableReadImpl]comparator).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]directives.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLDirective directive = [CtInvocationImpl][CtVariableReadImpl]directives.get([CtVariableReadImpl]i);
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl]directiveString([CtVariableReadImpl]directive));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]directives.size() - [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" ");
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String directiveString([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLDirective directive) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]options.getIncludeDirective().test([CtVariableReadImpl]directive)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// @deprecated is special - we always print it if something is deprecated
            if ([CtUnaryOperatorImpl]![CtInvocationImpl]isDeprecatedDirective([CtVariableReadImpl]directive)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"";
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"@").append([CtInvocationImpl][CtVariableReadImpl]directive.getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLDirective.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLArgument.class).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLArgument> args = [CtInvocationImpl][CtVariableReadImpl]directive.getArguments();
        [CtAssignmentImpl][CtVariableWriteImpl]args = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]args.stream().sorted([CtVariableReadImpl]comparator).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]args.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"(");
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]args.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLArgument arg = [CtInvocationImpl][CtVariableReadImpl]args.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String argValue = [CtLiteralImpl]null;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]arg.getValue() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]argValue = [CtInvocationImpl]graphql.schema.idl.SchemaPrinter.printAst([CtInvocationImpl][CtVariableReadImpl]arg.getValue(), [CtInvocationImpl][CtVariableReadImpl]arg.getType());
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]arg.getDefaultValue() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]argValue = [CtInvocationImpl]graphql.schema.idl.SchemaPrinter.printAst([CtInvocationImpl][CtVariableReadImpl]arg.getDefaultValue(), [CtInvocationImpl][CtVariableReadImpl]arg.getType());
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]graphql.schema.idl.SchemaPrinter.isNullOrEmpty([CtVariableReadImpl]argValue)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtVariableReadImpl]arg.getName());
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" : ");
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]argValue);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]args.size() - [CtLiteralImpl]1)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]", ");
                    }
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]")");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isDeprecatedDirective([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLDirective directive) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]directive.getName().equals([CtInvocationImpl][CtTypeAccessImpl]graphql.Directives.DeprecatedDirective.getName());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean hasDeprecatedDirective([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> directives) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]directives.stream().filter([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::isDeprecatedDirective).count() == [CtLiteralImpl]1;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> addDeprecatedDirectiveIfNeeded([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> directives) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasDeprecatedDirective([CtVariableReadImpl]directives)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]directives = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]directives);
            [CtInvocationImpl][CtVariableReadImpl]directives.add([CtFieldReadImpl]graphql.schema.idl.SchemaPrinter.DeprecatedDirective4Printing);
        }
        [CtReturnImpl]return [CtVariableReadImpl]directives;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String directiveDefinitions([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLDirective> directives) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLDirective directive : [CtVariableReadImpl]directives) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl]directiveDefinition([CtVariableReadImpl]directive));
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n\n");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String directiveDefinition([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLDirective directive) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtInvocationImpl]printComments([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw), [CtVariableReadImpl]directive, [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtVariableReadImpl]sw.toString());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"directive @").append([CtInvocationImpl][CtVariableReadImpl]directive.getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphqlTypeComparatorEnvironment environment = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]graphql.schema.GraphqlTypeComparatorEnvironment.newEnvironment().parentType([CtFieldReadImpl]graphql.schema.GraphQLDirective.class).elementType([CtFieldReadImpl]graphql.schema.GraphQLArgument.class).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]graphql.schema.GraphQLSchemaElement> comparator = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]options.comparatorRegistry.getComparator([CtVariableReadImpl]environment);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLArgument> args = [CtInvocationImpl][CtVariableReadImpl]directive.getArguments();
        [CtAssignmentImpl][CtVariableWriteImpl]args = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]args.stream().sorted([CtVariableReadImpl]comparator).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl]argsString([CtFieldReadImpl]graphql.schema.GraphQLDirective.class, [CtVariableReadImpl]args));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" on ");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String locations = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]directive.validLocations().stream().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Enum::name).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]" | "));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]locations);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    private <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeParameterReferenceImpl]T> printer([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> clazz) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter typePrinter = [CtInvocationImpl][CtFieldReadImpl]printers.get([CtVariableReadImpl]clazz);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]typePrinter == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> superClazz = [CtInvocationImpl][CtVariableReadImpl]clazz.getSuperclass();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]superClazz != [CtFieldReadImpl]java.lang.Object.class) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]typePrinter = [CtInvocationImpl]printer([CtVariableReadImpl]superClazz);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]typePrinter = [CtLambdaImpl]([CtParameterImpl]java.io.PrintWriter out,[CtParameterImpl]java.lang.Object type,[CtParameterImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) -> [CtInvocationImpl][CtVariableReadImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Type not implemented : " + [CtVariableReadImpl]type);
            }
            [CtInvocationImpl][CtFieldReadImpl]printers.put([CtVariableReadImpl]clazz, [CtVariableReadImpl]typePrinter);
        }
        [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeParameterReferenceImpl]T>) (typePrinter));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String print([CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLType type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintWriter out = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw);
        [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtTypeAccessImpl]DefaultGraphqlFieldVisibility.DEFAULT_FIELD_VISIBILITY);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sw.toString();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    private [CtTypeReferenceImpl]void printType([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]graphql.schema.GraphQLType> typesAsList, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class typeClazz, [CtParameterImpl][CtTypeReferenceImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]typesAsList.stream().filter([CtLambdaImpl]([CtParameterImpl] type) -> [CtInvocationImpl][CtVariableReadImpl]typeClazz.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]type.getClass())).forEach([CtLambdaImpl]([CtParameterImpl] type) -> [CtInvocationImpl]printType([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtVariableReadImpl]visibility));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void printType([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]graphql.schema.GraphQLType type, [CtParameterImpl][CtTypeReferenceImpl]graphql.schema.visibility.GraphqlFieldVisibility visibility) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.idl.SchemaPrinter.TypePrinter<[CtTypeReferenceImpl]java.lang.Object> printer = [CtInvocationImpl]printer([CtInvocationImpl][CtVariableReadImpl]type.getClass());
        [CtInvocationImpl][CtVariableReadImpl]printer.print([CtVariableReadImpl]out, [CtVariableReadImpl]type, [CtVariableReadImpl]visibility);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String printComments([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object graphQLType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintWriter pw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw);
        [CtInvocationImpl]printComments([CtVariableReadImpl]pw, [CtVariableReadImpl]graphQLType, [CtVariableReadImpl]prefix);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sw.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void printComments([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object graphQLType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String descriptionText = [CtInvocationImpl]getDescription([CtVariableReadImpl]graphQLType);
        [CtIfImpl]if ([CtInvocationImpl]graphql.schema.idl.SchemaPrinter.isNullOrEmpty([CtVariableReadImpl]descriptionText)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]graphql.schema.idl.SchemaPrinter.isNullOrEmpty([CtVariableReadImpl]descriptionText)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> lines = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]descriptionText.split([CtLiteralImpl]"\n"));
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]options.isDescriptionsAsHashComments()) [CtBlockImpl]{
                [CtInvocationImpl]printMultiLineHashDescription([CtVariableReadImpl]out, [CtVariableReadImpl]prefix, [CtVariableReadImpl]lines);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lines.size() > [CtLiteralImpl]1) [CtBlockImpl]{
                [CtInvocationImpl]printMultiLineDescription([CtVariableReadImpl]out, [CtVariableReadImpl]prefix, [CtVariableReadImpl]lines);
            } else [CtBlockImpl]{
                [CtInvocationImpl]printSingleLineDescription([CtVariableReadImpl]out, [CtVariableReadImpl]prefix, [CtInvocationImpl][CtVariableReadImpl]lines.get([CtLiteralImpl]0));
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void printMultiLineHashDescription([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> lines) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]lines.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String l) -> [CtInvocationImpl][CtVariableReadImpl]out.printf([CtLiteralImpl]"%s#%s\n", [CtVariableReadImpl]prefix, [CtVariableReadImpl]l));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void printMultiLineDescription([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> lines) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]out.printf([CtLiteralImpl]"%s\"\"\"\n", [CtVariableReadImpl]prefix);
        [CtInvocationImpl][CtVariableReadImpl]lines.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String l) -> [CtInvocationImpl][CtVariableReadImpl]out.printf([CtLiteralImpl]"%s%s\n", [CtVariableReadImpl]prefix, [CtVariableReadImpl]l));
        [CtInvocationImpl][CtVariableReadImpl]out.printf([CtLiteralImpl]"%s\"\"\"\n", [CtVariableReadImpl]prefix);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void printSingleLineDescription([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter out, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// See: https://github.com/graphql/graphql-spec/issues/148
        [CtTypeReferenceImpl]java.lang.String comment = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.text.StringEscapeUtils.escapeJson([CtVariableReadImpl]s);
        [CtInvocationImpl][CtVariableReadImpl]out.printf([CtLiteralImpl]"%s\"%s\"\n", [CtVariableReadImpl]prefix, [CtVariableReadImpl]comment);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean hasDescription([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object descriptionHolder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String description = [CtInvocationImpl]getDescription([CtVariableReadImpl]descriptionHolder);
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl]graphql.schema.idl.SchemaPrinter.isNullOrEmpty([CtVariableReadImpl]description);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getDescription([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object descriptionHolder) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLObjectType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLObjectType type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLObjectType) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ObjectTypeDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLEnumType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLEnumType type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLEnumType) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]EnumTypeDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLFieldDefinition) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLFieldDefinition type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLFieldDefinition) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]FieldDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLEnumValueDefinition) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLEnumValueDefinition type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLEnumValueDefinition) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]EnumValueDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLUnionType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLUnionType type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLUnionType) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]UnionTypeDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLInputObjectType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLInputObjectType type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLInputObjectType) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]InputObjectTypeDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLInputObjectField) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLInputObjectField type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLInputObjectField) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]InputValueDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLInterfaceType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLInterfaceType type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLInterfaceType) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]InterfaceTypeDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLScalarType) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLScalarType type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLScalarType) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ScalarTypeDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLArgument) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLArgument type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLArgument) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]type.getDefinition()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]InputValueDefinition::getDescription).orElse([CtLiteralImpl]null));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionHolder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]graphql.schema.GraphQLDirective) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]graphql.schema.GraphQLDirective type = [CtVariableReadImpl](([CtTypeReferenceImpl]graphql.schema.GraphQLDirective) (descriptionHolder));
            [CtReturnImpl]return [CtInvocationImpl]description([CtInvocationImpl][CtVariableReadImpl]type.getDescription(), [CtLiteralImpl]null);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]graphql.Assert.assertShouldNeverHappen();
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String description([CtParameterImpl][CtTypeReferenceImpl]java.lang.String runtimeDescription, [CtParameterImpl][CtTypeReferenceImpl]graphql.language.Description descriptionAst) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// 
        [CtCommentImpl]// 95% of the time if the schema was built from SchemaGenerator then the runtime description is the only description
        [CtCommentImpl]// So the other code here is a really defensive way to get the description
        [CtCommentImpl]// 
        [CtTypeReferenceImpl]java.lang.String descriptionText = [CtVariableReadImpl]runtimeDescription;
        [CtIfImpl]if ([CtInvocationImpl]graphql.schema.idl.SchemaPrinter.isNullOrEmpty([CtVariableReadImpl]descriptionText)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]descriptionAst != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]descriptionText = [CtInvocationImpl][CtVariableReadImpl]descriptionAst.getContent();
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]descriptionText;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isNullOrEmpty([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]s == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]s.isEmpty();
    }
}
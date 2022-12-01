[CompilationUnitImpl][CtPackageDeclarationImpl]package com.google.cloud.bigquery.utils.queryfixer;
[CtUnresolvedImport]import org.apache.calcite.avatica.util.Quoting;
[CtUnresolvedImport]import org.apache.calcite.sql.parser.SqlParserImplFactory;
[CtUnresolvedImport]import org.apache.calcite.sql.parser.SqlParser;
[CtUnresolvedImport]import org.apache.calcite.avatica.util.Casing;
[CtUnresolvedImport]import org.apache.calcite.util.SourceStringReader;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.apache.calcite.sql.validate.SqlConformanceEnum;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.exception.ParserCreationException;
[CtUnresolvedImport]import org.apache.calcite.sql.validate.SqlConformance;
[CtUnresolvedImport]import org.apache.calcite.sql.parser.babel.SqlBabelParserImpl;
[CtImportImpl]import org.apache.commons.lang3.reflect.FieldUtils;
[CtImportImpl]import java.io.Reader;
[CtClassImpl][CtJavaDocImpl]/**
 * A factory to generate parsers. The fault generated parser is Babel Parser with BigQuery dialect.
 */
public class BigQueryParserFactory {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.calcite.avatica.util.Quoting quoting = [CtFieldReadImpl]org.apache.calcite.avatica.util.Quoting.BACK_TICK;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.calcite.avatica.util.Casing quotedCasing = [CtFieldReadImpl]org.apache.calcite.avatica.util.Casing.UNCHANGED;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.calcite.sql.validate.SqlConformance conformance = [CtFieldReadImpl]org.apache.calcite.sql.validate.SqlConformanceEnum.DEFAULT;

    [CtFieldImpl]private final [CtTypeReferenceImpl]SqlParser.Config parserConfig;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.calcite.avatica.util.Casing unquotedCasing = [CtFieldReadImpl]org.apache.calcite.avatica.util.Casing.UNCHANGED;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Default initialization with the Babel Parser factory.
     */
    public BigQueryParserFactory() [CtBlockImpl]{
        [CtInvocationImpl]this([CtTypeAccessImpl]SqlBabelParserImpl.FACTORY);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Initialization with customized factory
     *
     * @param factory
     * 		customized factory
     */
    public BigQueryParserFactory([CtParameterImpl][CtTypeReferenceImpl]org.apache.calcite.sql.parser.SqlParserImplFactory factory) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parserConfig = [CtInvocationImpl]buildConfig([CtVariableReadImpl]factory);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the parser parsing the input query.
     *
     * @param sql
     * 		query to parse
     * @return a parser loaded with the query
     */
    public [CtTypeReferenceImpl]org.apache.calcite.sql.parser.SqlParser getParser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getParser([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.calcite.util.SourceStringReader([CtVariableReadImpl]sql));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the implementation of a parser parsing a query. The implementation provides functions to
     * tokenize the query.
     *
     * @param query
     * 		the query fed to the parser implementation.
     * @return SqlBabelParserImpl the parser implementation
     */
    public [CtTypeReferenceImpl]org.apache.calcite.sql.parser.babel.SqlBabelParserImpl getBabelParserImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String query) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]query, [CtLiteralImpl]"the input query should not be null");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object parserImpl;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]parserImpl = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.reflect.FieldUtils.readField([CtInvocationImpl]getParser([CtVariableReadImpl]query), [CtLiteralImpl]"parser", [CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalAccessException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.exception.ParserCreationException([CtLiteralImpl]"unable to extract the parserImpl from the generated parser");
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]parserImpl instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.parser.babel.SqlBabelParserImpl)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.exception.ParserCreationException([CtLiteralImpl]"the factory does not produce Babel Parser");
        }
        [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.parser.babel.SqlBabelParserImpl) (parserImpl));
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.apache.calcite.sql.parser.SqlParser getParser([CtParameterImpl][CtTypeReferenceImpl]java.io.Reader source) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.calcite.sql.parser.SqlParser.create([CtVariableReadImpl]source, [CtFieldReadImpl]parserConfig);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]SqlParser.Config buildConfig([CtParameterImpl][CtTypeReferenceImpl]org.apache.calcite.sql.parser.SqlParserImplFactory factory) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.calcite.sql.parser.SqlParser.ConfigBuilder configBuilder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.calcite.sql.parser.SqlParser.configBuilder().setParserFactory([CtVariableReadImpl]factory).setQuoting([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.BigQueryParserFactory.quoting).setUnquotedCasing([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.BigQueryParserFactory.unquotedCasing).setQuotedCasing([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.BigQueryParserFactory.quotedCasing).setConformance([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.BigQueryParserFactory.conformance);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]configBuilder.build();
    }
}
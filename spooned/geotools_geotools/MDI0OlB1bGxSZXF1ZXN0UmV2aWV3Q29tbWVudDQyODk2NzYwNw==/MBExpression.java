[CompilationUnitImpl][CtCommentImpl]/* GeoTools - The Open Source Java GIS Toolkit
   http://geotools.org

   (C) 2018, Open Source Geospatial Foundation (OSGeo)

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation;
   version 2.1 of the License.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.
 */
[CtPackageDeclarationImpl]package org.geotools.mbstyle.expression;
[CtUnresolvedImport]import org.geotools.mbstyle.parse.MBObjectParser;
[CtUnresolvedImport]import org.opengis.filter.expression.Expression;
[CtUnresolvedImport]import org.opengis.filter.FilterFactory2;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.geotools.factory.CommonFactoryFinder;
[CtUnresolvedImport]import org.geotools.mbstyle.parse.MBFormatException;
[CtUnresolvedImport]import org.geotools.mbstyle.transform.MBStyleTransformer;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.json.simple.JSONArray;
[CtUnresolvedImport]import org.geotools.filter.FunctionImpl;
[CtClassImpl][CtJavaDocImpl]/**
 * The value for any layout property, paint property, or filter may be specified as an expression.
 * An expression defines a formula for computing the value of the property using the operators
 * described below: -Mathematical operators for performing arithmetic and other operations on
 * numeric values -Logical operators for manipulating boolean values and making conditional
 * decisions -String operators for manipulating strings -Data operators, providing access to the
 * properties of source features -Camera operators, providing access to the parameters defining the
 * current map view
 *
 * <p>Expressions are represented as JSON arrays. The first element of an expression array is a
 * string naming the expression operator, e.g. "*"or "case". Subsequent elements (if any) are the
 * arguments to the expression. Each argument is either a literal value (a string, number, boolean,
 * or null), or another expression array.
 */
public abstract class MBExpression extends [CtTypeReferenceImpl]org.geotools.filter.FunctionImpl {
    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.json.simple.JSONArray json;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]java.lang.String name;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.opengis.filter.FilterFactory2 ff;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBObjectParser parse;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.geotools.mbstyle.transform.MBStyleTransformer transformer;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Please use factory method {@link #create(JSONArray)}
     *
     * @param json
     */
    protected MBExpression([CtParameterImpl][CtTypeReferenceImpl]org.json.simple.JSONArray json) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.json = [CtVariableReadImpl]json;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]json.get([CtLiteralImpl]0)));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ff = [CtInvocationImpl][CtTypeAccessImpl]org.geotools.factory.CommonFactoryFinder.getFilterFactory2();
        [CtAssignmentImpl][CtFieldWriteImpl]parse = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBObjectParser([CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.class);
        [CtAssignmentImpl][CtFieldWriteImpl]transformer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.transform.MBStyleTransformer([CtFieldReadImpl]parse);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]name;
    }

    [CtFieldImpl][CtCommentImpl]/* A list of color expression names */
    public static [CtTypeReferenceImpl]java.util.List colors = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"rgb", [CtLiteralImpl]"rgba", [CtLiteralImpl]"to-rgba");

    [CtFieldImpl][CtCommentImpl]/* A list of decision expression names */
    public static [CtTypeReferenceImpl]java.util.List decisions = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"!", [CtLiteralImpl]"!=", [CtLiteralImpl]"<", [CtLiteralImpl]"<=", [CtLiteralImpl]"==", [CtLiteralImpl]">", [CtLiteralImpl]">=", [CtLiteralImpl]"all", [CtLiteralImpl]"any", [CtLiteralImpl]"case", [CtLiteralImpl]"coalesce", [CtLiteralImpl]"match");

    [CtFieldImpl][CtCommentImpl]/* A list of feature data expression names */
    public static [CtTypeReferenceImpl]java.util.List featureData = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"geometry-type", [CtLiteralImpl]"id", [CtLiteralImpl]"properties");

    [CtFieldImpl][CtCommentImpl]/* A list of heatmap expression names */
    public static [CtTypeReferenceImpl]java.util.List heatMap = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"heatmap-density");

    [CtFieldImpl][CtCommentImpl]/* A list of lookup expression names */
    public static [CtTypeReferenceImpl]java.util.List lookUp = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"at", [CtLiteralImpl]"length", [CtLiteralImpl]"has", [CtLiteralImpl]"get");

    [CtFieldImpl][CtCommentImpl]/* A list of math expression names */
    public static [CtTypeReferenceImpl]java.util.List math = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"-", [CtLiteralImpl]"*", [CtLiteralImpl]"/", [CtLiteralImpl]"%", [CtLiteralImpl]"^", [CtLiteralImpl]"+", [CtLiteralImpl]"acos", [CtLiteralImpl]"asin", [CtLiteralImpl]"atan", [CtLiteralImpl]"cos", [CtLiteralImpl]"e", [CtLiteralImpl]"ln", [CtLiteralImpl]"ln2", [CtLiteralImpl]"log10", [CtLiteralImpl]"log2", [CtLiteralImpl]"max", [CtLiteralImpl]"min", [CtLiteralImpl]"pi", [CtLiteralImpl]"sin", [CtLiteralImpl]"sqrt", [CtLiteralImpl]"tan");

    [CtFieldImpl][CtCommentImpl]/* A list of ramps expression names */
    public static [CtTypeReferenceImpl]java.util.List ramps = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"interpolate", [CtLiteralImpl]"step");

    [CtFieldImpl][CtCommentImpl]/* A list of string expression names */
    public static [CtTypeReferenceImpl]java.util.List string = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"concat", [CtLiteralImpl]"downcase", [CtLiteralImpl]"upcase");

    [CtFieldImpl][CtCommentImpl]/* A list of types expression names */
    public static [CtTypeReferenceImpl]java.util.List types = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"array", [CtLiteralImpl]"boolean", [CtLiteralImpl]"literal", [CtLiteralImpl]"number", [CtLiteralImpl]"object", [CtLiteralImpl]"string", [CtLiteralImpl]"to-boolean", [CtLiteralImpl]"to-color", [CtLiteralImpl]"to-number", [CtLiteralImpl]"to-string", [CtLiteralImpl]"typeof");

    [CtFieldImpl][CtCommentImpl]/* A list of variable bindings expression names */
    public static [CtTypeReferenceImpl]java.util.List variableBindings = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"let", [CtLiteralImpl]"var");

    [CtFieldImpl][CtCommentImpl]/* A list of zoom expression names */
    public static [CtTypeReferenceImpl]java.util.List zoom = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"zoom");

    [CtMethodImpl][CtJavaDocImpl]/**
     * Factory method used to produce the correct MBExpression subclass for the provided JSONArray.
     *
     * @param json
     * 		definition
     * @return MBExpression
     */
    public static [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBExpression create([CtParameterImpl][CtTypeReferenceImpl]org.json.simple.JSONArray json) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]json.get([CtLiteralImpl]0) instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.String) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]name = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]json.get([CtLiteralImpl]0)));
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.colors.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBColor([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.decisions.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBDecision([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.featureData.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBFeatureData([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.heatMap.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBHeatmap([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.lookUp.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBLookup([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.math.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBMath([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.ramps.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBRampsScalesCurves([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.string.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBString([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.types.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBTypes([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.variableBindings.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBVariableBinding([CtVariableReadImpl]json);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.zoom.contains([CtVariableReadImpl]name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.expression.MBZoom([CtVariableReadImpl]json);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBFormatException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Expression \"" + [CtVariableReadImpl]name) + [CtLiteralImpl]"\" not supported.");
            }
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBFormatException([CtLiteralImpl]"Requires a string name of the expression at position 0");
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canCreate([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.colors.contains([CtVariableReadImpl]name) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.decisions.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.featureData.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.heatMap.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.lookUp.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.math.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.ramps.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.string.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.types.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.variableBindings.contains([CtVariableReadImpl]name)) || [CtInvocationImpl][CtFieldReadImpl]org.geotools.mbstyle.expression.MBExpression.zoom.contains([CtVariableReadImpl]name));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determines which expression to use.
     */
    public abstract [CtTypeReferenceImpl]org.opengis.filter.expression.Expression getExpression();

    [CtMethodImpl][CtJavaDocImpl]/**
     * A function to evaluate a given parameter as an expression and use the MBStyleTransformer to
     * transform Mapbox tokens into CQL expressions.
     *
     * @return cq; text expression
     */
    public [CtTypeReferenceImpl]org.opengis.filter.expression.Expression transformLiteral([CtParameterImpl][CtTypeReferenceImpl]org.opengis.filter.expression.Expression ex) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String text = [CtInvocationImpl][CtVariableReadImpl]ex.evaluate([CtLiteralImpl]null, [CtFieldReadImpl]java.lang.String.class);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]text.trim().isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ex = [CtInvocationImpl][CtFieldReadImpl]ff.literal([CtLiteralImpl]" ");
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ex = [CtInvocationImpl][CtFieldReadImpl]transformer.cqlExpressionFromTokens([CtVariableReadImpl]text);
        }
        [CtReturnImpl]return [CtVariableReadImpl]ex;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates an MBExpression and calls the associated function.
     */
    public static [CtTypeReferenceImpl]org.opengis.filter.expression.Expression transformExpression([CtParameterImpl][CtTypeReferenceImpl]org.json.simple.JSONArray json) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]org.geotools.mbstyle.expression.MBExpression.create([CtVariableReadImpl]json).getExpression();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void throwUnexpectedArgumentCount([CtParameterImpl][CtTypeReferenceImpl]java.lang.String expression, [CtParameterImpl][CtTypeReferenceImpl]int argCount) throws [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBFormatException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBFormatException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Expression \"%s\" should have exactly %d argument(s)", [CtVariableReadImpl]expression, [CtVariableReadImpl]argCount));
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void throwInsufficientArgumentCount([CtParameterImpl][CtTypeReferenceImpl]java.lang.String expression, [CtParameterImpl][CtTypeReferenceImpl]int argCount) throws [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBFormatException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.geotools.mbstyle.parse.MBFormatException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Expression \"%s\" should have at least %d argument(s)", [CtVariableReadImpl]expression, [CtVariableReadImpl]argCount));
    }
}
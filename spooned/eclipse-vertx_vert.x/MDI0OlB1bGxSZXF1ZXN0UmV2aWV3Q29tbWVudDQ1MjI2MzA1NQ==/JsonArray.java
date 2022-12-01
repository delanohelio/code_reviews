[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2011-2019 Contributors to the Eclipse Foundation

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
which is available at https://www.apache.org/licenses/LICENSE-2.0.

SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
[CtPackageDeclarationImpl]package io.vertx.core.json;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import io.vertx.core.buffer.Buffer;
[CtImportImpl]import java.time.Instant;
[CtImportImpl]import static java.time.format.DateTimeFormatter.ISO_INSTANT;
[CtUnresolvedImport]import io.vertx.core.shareddata.impl.ClusterSerializable;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import static io.vertx.core.json.impl.JsonUtil.*;
[CtUnresolvedImport]import io.vertx.core.shareddata.Shareable;
[CtClassImpl][CtJavaDocImpl]/**
 * A representation of a <a href="http://json.org/">JSON</a> array in Java.
 *
 * Unlike some other languages Java does not have a native understanding of JSON. To enable JSON to be used easily
 * in Vert.x code we use this class to encapsulate the notion of a JSON array.
 *
 * The implementation adheres to the <a href="http://rfc-editor.org/rfc/rfc7493.txt">RFC-7493</a> to support Temporal
 * data types as well as binary data.
 *
 * Please see the documentation for more information.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class JsonArray implements [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.Object> , [CtTypeReferenceImpl]io.vertx.core.shareddata.impl.ClusterSerializable , [CtTypeReferenceImpl]io.vertx.core.shareddata.Shareable {
    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> list;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create an instance from a String of JSON, this string must be a valid array otherwise an exception will be thrown.
     * <p/>
     * If you are unsure of the value, you should use instead {@link Json#decodeValue(String)} and check the result is
     * a JSON array.
     *
     * @param json
     * 		the string of JSON
     */
    public JsonArray([CtParameterImpl][CtTypeReferenceImpl]java.lang.String json) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]json == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException();
        }
        [CtInvocationImpl]fromJson([CtVariableReadImpl]json);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]list == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DecodeException([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid JSON array: " + [CtVariableReadImpl]json);
        }
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create an empty instance
     */
    public JsonArray() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create an instance from a List. The List is not copied.
     *
     * @param list
     * 		the underlying backing list
     */
    public JsonArray([CtParameterImpl][CtTypeReferenceImpl]java.util.List list) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]list == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException();
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.list = [CtVariableReadImpl]list;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create an instance from a Buffer of JSON.
     *
     * @param buf
     * 		the buffer of JSON.
     */
    public JsonArray([CtParameterImpl][CtTypeReferenceImpl]io.vertx.core.buffer.Buffer buf) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]buf == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NullPointerException();
        }
        [CtInvocationImpl]fromBuffer([CtVariableReadImpl]buf);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]list == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DecodeException([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid JSON array: " + [CtVariableReadImpl]buf);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the String at position {@code pos} in the array,
     *
     * @param pos
     * 		the position in the array
     * @return the String (or String representation), or null if a null value present
     */
    public [CtTypeReferenceImpl]java.lang.String getString([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val = [CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.time.Instant) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]java.time.format.DateTimeFormatter.ISO_INSTANT.format([CtVariableReadImpl](([CtTypeReferenceImpl]java.time.Instant) (val)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtArrayTypeReferenceImpl]byte[]) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]BASE64_ENCODER.encodeToString([CtVariableReadImpl](([CtArrayTypeReferenceImpl]byte[]) (val)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.vertx.core.buffer.Buffer) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]BASE64_ENCODER.encodeToString([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]io.vertx.core.buffer.Buffer) (val)).getBytes());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Enum) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Enum) (val)).name();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]val.toString();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Integer at position {@code pos} in the array,
     *
     * @param pos
     * 		the position in the array
     * @return the Number, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to Number
     */
    public [CtTypeReferenceImpl]java.lang.Number getNumber([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Number) ([CtFieldReadImpl]list.get([CtVariableReadImpl]pos)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Integer at position {@code pos} in the array,
     *
     * @param pos
     * 		the position in the array
     * @return the Integer, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to Integer
     */
    public [CtTypeReferenceImpl]java.lang.Integer getInteger([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Number number = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Number) ([CtFieldReadImpl]list.get([CtVariableReadImpl]pos)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Integer) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Integer) (number));[CtCommentImpl]// Avoids unnecessary unbox/box

        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]number.intValue();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Long at position {@code pos} in the array,
     *
     * @param pos
     * 		the position in the array
     * @return the Long, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to Long
     */
    public [CtTypeReferenceImpl]java.lang.Long getLong([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Number number = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Number) ([CtFieldReadImpl]list.get([CtVariableReadImpl]pos)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Long) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Long) (number));[CtCommentImpl]// Avoids unnecessary unbox/box

        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]number.longValue();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Double at position {@code pos} in the array,
     *
     * @param pos
     * 		the position in the array
     * @return the Double, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to Double
     */
    public [CtTypeReferenceImpl]java.lang.Double getDouble([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Number number = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Number) ([CtFieldReadImpl]list.get([CtVariableReadImpl]pos)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Double) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Double) (number));[CtCommentImpl]// Avoids unnecessary unbox/box

        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]number.doubleValue();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Float at position {@code pos} in the array,
     *
     * @param pos
     * 		the position in the array
     * @return the Float, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to Float
     */
    public [CtTypeReferenceImpl]java.lang.Float getFloat([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Number number = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Number) ([CtFieldReadImpl]list.get([CtVariableReadImpl]pos)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Float) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Float) (number));[CtCommentImpl]// Avoids unnecessary unbox/box

        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]number.floatValue();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Boolean at position {@code pos} in the array,
     *
     * @param pos
     * 		the position in the array
     * @return the Boolean, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to Integer
     */
    public [CtTypeReferenceImpl]java.lang.Boolean getBoolean([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Boolean) ([CtFieldReadImpl]list.get([CtVariableReadImpl]pos)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the JsonObject at position {@code pos} in the array.
     *
     * @param pos
     * 		the position in the array
     * @return the JsonObject, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to JsonObject
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonObject getJsonObject([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val = [CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.Map) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]val = [CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObject([CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Map) (val)));
        }
        [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]JsonObject) (val));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the JsonArray at position {@code pos} in the array.
     *
     * @param pos
     * 		the position in the array
     * @return the Integer, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to JsonArray
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray getJsonArray([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val = [CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.List) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]val = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.vertx.core.json.JsonArray([CtVariableReadImpl](([CtTypeReferenceImpl]java.util.List) (val)));
        }
        [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]io.vertx.core.json.JsonArray) (val));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the byte[] at position {@code pos} in the array.
     *
     * JSON itself has no notion of a binary, so this method assumes there is a String value and
     * it contains a Base64 encoded binary, which it decodes if found and returns.
     *
     * @param pos
     * 		the position in the array
     * @return the byte[], or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to String
     * @throws java.lang.IllegalArgumentException
     * 		if the String value is not a legal Base64 encoded value
     */
    public [CtArrayTypeReferenceImpl]byte[] getBinary([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val = [CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos);
        [CtIfImpl][CtCommentImpl]// no-op
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl][CtCommentImpl]// no-op if value is already an byte[]
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtArrayTypeReferenceImpl]byte[]) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtArrayTypeReferenceImpl]byte[]) (val));
        }
        [CtIfImpl][CtCommentImpl]// unwrap if value is already a Buffer
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.vertx.core.buffer.Buffer) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]io.vertx.core.buffer.Buffer) (val)).getBytes();
        }
        [CtLocalVariableImpl][CtCommentImpl]// assume that the value is in String format as per RFC
        [CtTypeReferenceImpl]java.lang.String encoded = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (val));
        [CtReturnImpl][CtCommentImpl]// parse to proper type
        return [CtInvocationImpl][CtTypeAccessImpl]BASE64_DECODER.decode([CtVariableReadImpl]encoded);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Buffer at position {@code pos} in the array.
     *
     * JSON itself has no notion of a binary, so this method assumes there is a String value and
     * it contains a Base64 encoded binary, which it decodes if found and returns.
     *
     * @param pos
     * 		the position in the array
     * @return the byte[], or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to String
     * @throws java.lang.IllegalArgumentException
     * 		if the String value is not a legal Base64 encoded value
     */
    public [CtTypeReferenceImpl]io.vertx.core.buffer.Buffer getBuffer([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val = [CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos);
        [CtIfImpl][CtCommentImpl]// no-op
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl][CtCommentImpl]// no-op if value is already an Buffer
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.vertx.core.buffer.Buffer) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]io.vertx.core.buffer.Buffer) (val));
        }
        [CtIfImpl][CtCommentImpl]// wrap if value is already a byte[]
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtArrayTypeReferenceImpl]byte[]) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.buffer.Buffer.buffer([CtVariableReadImpl](([CtArrayTypeReferenceImpl]byte[]) (val)));
        }
        [CtLocalVariableImpl][CtCommentImpl]// assume that the value is in String format as per RFC
        [CtTypeReferenceImpl]java.lang.String encoded = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (val));
        [CtReturnImpl][CtCommentImpl]// parse to proper type
        return [CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.buffer.Buffer.buffer([CtInvocationImpl][CtTypeAccessImpl]BASE64_DECODER.decode([CtVariableReadImpl]encoded));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Instant at position {@code pos} in the array.
     *
     * JSON itself has no notion of a temporal types, this extension complies to the RFC-7493, so this method assumes
     * there is a String value and it contains an ISO 8601 encoded date and time format such as "2017-04-03T10:25:41Z",
     * which it decodes if found and returns.
     *
     * @param pos
     * 		the position in the array
     * @return the Instant, or null if a null value present
     * @throws java.lang.ClassCastException
     * 		if the value cannot be converted to String
     * @throws java.time.format.DateTimeParseException
     * 		if the String value is not a legal ISO 8601 encoded value
     */
    public [CtTypeReferenceImpl]java.time.Instant getInstant([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val = [CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos);
        [CtIfImpl][CtCommentImpl]// no-op
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl][CtCommentImpl]// no-op if value is already an Instant
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]val instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.time.Instant) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]java.time.Instant) (val));
        }
        [CtLocalVariableImpl][CtCommentImpl]// assume that the value is in String format as per RFC
        [CtTypeReferenceImpl]java.lang.String encoded = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (val));
        [CtReturnImpl][CtCommentImpl]// parse to proper type
        return [CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.from([CtInvocationImpl][CtFieldReadImpl]java.time.format.DateTimeFormatter.ISO_INSTANT.parse([CtVariableReadImpl]encoded));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the value with the specified key, as an Object with types respecting the limitations of JSON.
     * <ul>
     *   <li>{@code Map} will be wrapped to {@code JsonObject}</li>
     *   <li>{@code List} will be wrapped to {@code JsonArray}</li>
     *   <li>{@code Instant} will be converted to {@code String}</li>
     *   <li>{@code byte[]} will be converted to {@code String}</li>
     *   <li>{@code Buffer} will be converted to {@code String}</li>
     *   <li>{@code Enum} will be converted to {@code String}</li>
     * </ul>
     *
     * @param pos
     * 		the position in the array
     * @return the Integer, or null if a null value present
     */
    public [CtTypeReferenceImpl]java.lang.Object getValue([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]wrapJsonValue([CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Is there a null value at position pos?
     *
     * @param pos
     * 		the position in the array
     * @return true if null value present, false otherwise
     */
    public [CtTypeReferenceImpl]boolean hasNull([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]list.get([CtVariableReadImpl]pos) == [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a null value to the JSON array.
     *
     * @return a reference to this, so the API can be used fluently
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray addNull() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]list.add([CtLiteralImpl]null);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an Object to the JSON array.
     *
     * @param value
     * 		the value
     * @return a reference to this, so the API can be used fluently
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray add([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]list.add([CtVariableReadImpl]value);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an Object to the JSON array at given position {@code pos}.
     *
     * @param pos
     * 		the position
     * @param value
     * 		the value
     * @return a reference to this, so the API can be used fluently
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray add([CtParameterImpl][CtTypeReferenceImpl]int pos, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]list.add([CtVariableReadImpl]pos, [CtVariableReadImpl]value);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Appends all of the elements in the specified array to the end of this JSON array.
     *
     * @param array
     * 		the array
     * @return a reference to this, so the API can be used fluently
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray addAll([CtParameterImpl][CtTypeReferenceImpl]io.vertx.core.json.JsonArray array) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]list.addAll([CtFieldReadImpl][CtVariableReadImpl]array.list);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set a null value to the JSON array at position {@code pos}.
     *
     * @return a reference to this, so the API can be used fluently
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray setNull([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]list.set([CtVariableReadImpl]pos, [CtLiteralImpl]null);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set an Object to the JSON array at position {@code pos}.
     *
     * @param pos
     * 		position in the array
     * @param value
     * 		the value
     * @return a reference to this, so the API can be used fluently
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray set([CtParameterImpl][CtTypeReferenceImpl]int pos, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]list.set([CtVariableReadImpl]pos, [CtVariableReadImpl]value);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Does the JSON array contain the specified value? This method will scan the entire array until it finds a value
     * or reaches the end.
     *
     * @param value
     * 		the value
     * @return true if it contains the value, false if not
     */
    public [CtTypeReferenceImpl]boolean contains([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]list.contains([CtVariableReadImpl]value);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove the specified value from the JSON array. This method will scan the entire array until it finds a value
     * or reaches the end.
     *
     * @param value
     * 		the value to remove
     * @return true if it removed it, false if not found
     */
    public [CtTypeReferenceImpl]boolean remove([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Object wrappedValue = [CtInvocationImpl]wrapJsonValue([CtVariableReadImpl]value);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]list.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// perform comparision on wrapped types
            final [CtTypeReferenceImpl]java.lang.Object otherWrapperValue = [CtInvocationImpl]getValue([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]wrappedValue == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]otherWrapperValue == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]list.remove([CtVariableReadImpl]i);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]wrappedValue.equals([CtVariableReadImpl]otherWrapperValue)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]list.remove([CtVariableReadImpl]i);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove the value at the specified position in the JSON array.
     *
     * @param pos
     * 		the position to remove the value at
     * @return the removed value if removed, null otherwise. If the value is a Map, a {@link JsonObject} is built from
    this Map and returned. It the value is a List, a {@link JsonArray} is built form this List and returned.
     */
    public [CtTypeReferenceImpl]java.lang.Object remove([CtParameterImpl][CtTypeReferenceImpl]int pos) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]wrapJsonValue([CtInvocationImpl][CtFieldReadImpl]list.remove([CtVariableReadImpl]pos));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the number of values in this JSON array
     *
     * @return the number of items
     */
    public [CtTypeReferenceImpl]int size() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]list.size();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Are there zero items in this JSON array?
     *
     * @return true if zero, false otherwise
     */
    public [CtTypeReferenceImpl]boolean isEmpty() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]list.isEmpty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the unerlying List
     *
     * @return the underlying List
     */
    public [CtTypeReferenceImpl]java.util.List getList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]list;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove all entries from the JSON array
     *
     * @return a reference to this, so the API can be used fluently
     */
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray clear() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]list.clear();
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get an Iterator over the values in the JSON array
     *
     * @return an iterator
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.Object> iterator() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.vertx.core.json.JsonArray.Iter([CtInvocationImpl][CtFieldReadImpl]list.iterator());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encode the JSON array to a string
     *
     * @return the string encoding
     */
    public [CtTypeReferenceImpl]java.lang.String encode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]Json.CODEC.toString([CtThisAccessImpl]this, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encode this JSON object as buffer.
     *
     * @return the buffer encoding.
     */
    public [CtTypeReferenceImpl]io.vertx.core.buffer.Buffer toBuffer() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]Json.CODEC.toBuffer([CtThisAccessImpl]this, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encode the JSON array prettily as a string
     *
     * @return the string encoding
     */
    public [CtTypeReferenceImpl]java.lang.String encodePrettily() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]Json.CODEC.toString([CtThisAccessImpl]this, [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Make a copy of the JSON array
     *
     * @return a copy
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.vertx.core.json.JsonArray copy() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> copiedList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]list.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val : [CtFieldReadImpl]list) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]copiedList.add([CtInvocationImpl]checkAndCopy([CtVariableReadImpl]val));
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.vertx.core.json.JsonArray([CtVariableReadImpl]copiedList);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a Stream over the entries in the JSON array
     *
     * @return a Stream
     */
    public [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]java.lang.Object> stream() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]JsonObject.asStream([CtInvocationImpl]iterator());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]encode();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// null check
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]o == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtIfImpl][CtCommentImpl]// self check
        if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl][CtCommentImpl]// type check and cast
        if ([CtBinaryOperatorImpl][CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.core.json.JsonArray other = [CtVariableReadImpl](([CtTypeReferenceImpl]io.vertx.core.json.JsonArray) (o));
        [CtIfImpl][CtCommentImpl]// size check
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtThisAccessImpl]this.size() != [CtInvocationImpl][CtVariableReadImpl]other.size())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtForImpl][CtCommentImpl]// value comparison
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtThisAccessImpl]this.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object thisValue = [CtInvocationImpl][CtThisAccessImpl]this.getValue([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object otherValue = [CtInvocationImpl][CtVariableReadImpl]other.getValue([CtVariableReadImpl]i);
            [CtIfImpl][CtCommentImpl]// identity check
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]thisValue == [CtVariableReadImpl]otherValue) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl][CtCommentImpl]// special case for numbers
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]thisValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Number) && [CtBinaryOperatorImpl]([CtVariableReadImpl]otherValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Number)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]thisValue.getClass() != [CtInvocationImpl][CtVariableReadImpl]otherValue.getClass())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Number n1 = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Number) (thisValue));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Number n2 = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Number) (otherValue));
                [CtIfImpl][CtCommentImpl]// floating point values
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]thisValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Float) || [CtBinaryOperatorImpl]([CtVariableReadImpl]thisValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Double)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]otherValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Float)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]otherValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Double)) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// compare as floating point double
                    if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]n1.doubleValue() == [CtInvocationImpl][CtVariableReadImpl]n2.doubleValue()) [CtBlockImpl]{
                        [CtContinueImpl][CtCommentImpl]// same value check the next entry
                        continue;
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]thisValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Integer) || [CtBinaryOperatorImpl]([CtVariableReadImpl]thisValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Long)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]otherValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Integer)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]otherValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Long)) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// compare as integer long
                    if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]n1.longValue() == [CtInvocationImpl][CtVariableReadImpl]n2.longValue()) [CtBlockImpl]{
                        [CtContinueImpl][CtCommentImpl]// same value check the next entry
                        continue;
                    }
                }
            }
            [CtIfImpl][CtCommentImpl]// special case for char sequences
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]thisValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.CharSequence) && [CtBinaryOperatorImpl]([CtVariableReadImpl]otherValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.CharSequence)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]thisValue.getClass() != [CtInvocationImpl][CtVariableReadImpl]otherValue.getClass())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence s1 = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.CharSequence) (thisValue));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence s2 = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.CharSequence) (otherValue));
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]s1.toString(), [CtInvocationImpl][CtVariableReadImpl]s2.toString())) [CtBlockImpl]{
                    [CtContinueImpl][CtCommentImpl]// same value check the next entry
                    continue;
                }
            }
            [CtIfImpl][CtCommentImpl]// fallback to standard object equals checks
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]thisValue, [CtVariableReadImpl]otherValue)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl][CtCommentImpl]// all checks passed
        return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]list.hashCode();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void writeToBuffer([CtParameterImpl][CtTypeReferenceImpl]io.vertx.core.buffer.Buffer buffer) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String encoded = [CtInvocationImpl]encode();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] bytes = [CtInvocationImpl][CtVariableReadImpl]encoded.getBytes();
        [CtInvocationImpl][CtVariableReadImpl]buffer.appendInt([CtFieldReadImpl][CtVariableReadImpl]bytes.length);
        [CtInvocationImpl][CtVariableReadImpl]buffer.appendBytes([CtVariableReadImpl]bytes);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int readFromBuffer([CtParameterImpl][CtTypeReferenceImpl]int pos, [CtParameterImpl][CtTypeReferenceImpl]io.vertx.core.buffer.Buffer buffer) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int length = [CtInvocationImpl][CtVariableReadImpl]buffer.getInt([CtVariableReadImpl]pos);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int start = [CtBinaryOperatorImpl][CtVariableReadImpl]pos + [CtLiteralImpl]4;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String encoded = [CtInvocationImpl][CtVariableReadImpl]buffer.getString([CtVariableReadImpl]start, [CtBinaryOperatorImpl][CtVariableReadImpl]start + [CtVariableReadImpl]length);
        [CtInvocationImpl]fromJson([CtVariableReadImpl]encoded);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]pos + [CtVariableReadImpl]length) + [CtLiteralImpl]4;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void fromJson([CtParameterImpl][CtTypeReferenceImpl]java.lang.String json) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]list = [CtInvocationImpl][CtTypeAccessImpl]Json.CODEC.fromString([CtVariableReadImpl]json, [CtFieldReadImpl]java.util.List.class);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void fromBuffer([CtParameterImpl][CtTypeReferenceImpl]io.vertx.core.buffer.Buffer buf) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]list = [CtInvocationImpl][CtTypeAccessImpl]Json.CODEC.fromBuffer([CtVariableReadImpl]buf, [CtFieldReadImpl]java.util.List.class);
    }

    [CtClassImpl]private static class Iter implements [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.Object> {
        [CtFieldImpl]final [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.Object> listIter;

        [CtConstructorImpl]Iter([CtParameterImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.Object> listIter) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.listIter = [CtVariableReadImpl]listIter;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean hasNext() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]listIter.hasNext();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.Object next() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]wrapJsonValue([CtInvocationImpl][CtFieldReadImpl]listIter.next());
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void remove() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]listIter.remove();
        }
    }
}
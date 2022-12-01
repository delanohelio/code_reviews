[CompilationUnitImpl][CtCommentImpl]/* Copyright 2014 The Netty Project

The Netty Project licenses this file to you under the Apache License,
version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at:

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package io.netty.handler.codec.json;
[CtUnresolvedImport]import io.netty.buffer.ByteBuf;
[CtUnresolvedImport]import static org.junit.Assert.*;
[CtUnresolvedImport]import io.netty.channel.embedded.EmbeddedChannel;
[CtUnresolvedImport]import io.netty.util.CharsetUtil;
[CtUnresolvedImport]import io.netty.handler.codec.TooLongFrameException;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import io.netty.handler.codec.CorruptedFrameException;
[CtUnresolvedImport]import io.netty.buffer.Unpooled;
[CtClassImpl]public class JsonObjectDecoderTest {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testJsonObjectOverMultipleWrites() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectPart1 = [CtLiteralImpl]"{ \"firstname\": \"John";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectPart2 = [CtLiteralImpl]"\" ,\n \"surname\" :";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectPart3 = [CtLiteralImpl]"\"Doe\", age:22   \n}";
        [CtInvocationImpl][CtCommentImpl]// Test object
        [CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtBinaryOperatorImpl][CtLiteralImpl]"  \n\n  " + [CtVariableReadImpl]objectPart1, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]objectPart2, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtBinaryOperatorImpl][CtVariableReadImpl]objectPart3 + [CtLiteralImpl]"   \n\n  \n", [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]objectPart1 + [CtVariableReadImpl]objectPart2) + [CtVariableReadImpl]objectPart3, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testMultipleJsonObjectsOverMultipleWrites() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectPart1 = [CtLiteralImpl]"{\"name\":\"Jo";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectPart2 = [CtLiteralImpl]"hn\"}{\"name\":\"John\"}{\"name\":\"Jo";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectPart3 = [CtLiteralImpl]"hn\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]objectPart1, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]objectPart2, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]objectPart3, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]3; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
            [CtInvocationImpl]assertEquals([CtLiteralImpl]"{\"name\":\"John\"}", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
            [CtInvocationImpl][CtVariableReadImpl]res.release();
        }
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testJsonArrayOverMultipleWrites() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String arrayPart1 = [CtLiteralImpl]"[{\"test";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String arrayPart2 = [CtLiteralImpl]"case\"  : \"\\\"}]Escaped dou\\\"ble quotes \\\" in JSON str\\\"ing\"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String arrayPart3 = [CtLiteralImpl]"  }\n\n    , ";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String arrayPart4 = [CtLiteralImpl]"{\"testcase\" : \"Streaming string me";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String arrayPart5 = [CtLiteralImpl]"ssage\"} ]";
        [CtInvocationImpl][CtCommentImpl]// Test array
        [CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtBinaryOperatorImpl][CtLiteralImpl]"   " + [CtVariableReadImpl]arrayPart1, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]arrayPart2, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]arrayPart3, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]arrayPart4, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtBinaryOperatorImpl][CtVariableReadImpl]arrayPart5 + [CtLiteralImpl]"      ", [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]arrayPart1 + [CtVariableReadImpl]arrayPart2) + [CtVariableReadImpl]arrayPart3) + [CtVariableReadImpl]arrayPart4) + [CtVariableReadImpl]arrayPart5, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testStreamJsonArrayOverMultipleWrites1() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] array = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"   [{\"test", [CtLiteralImpl]"case\"  : \"\\\"}]Escaped dou\\\"ble quotes \\\" in JSON str\\\"ing\"", [CtLiteralImpl]"  }\n\n    , ", [CtLiteralImpl]"{\"testcase\" : \"Streaming string me", [CtLiteralImpl]"ssage\"} ]      " };
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] result = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"{\"testcase\"  : \"\\\"}]Escaped dou\\\"ble quotes \\\" in JSON str\\\"ing\"  }", [CtLiteralImpl]"{\"testcase\" : \"Streaming string message\"}" };
        [CtInvocationImpl]io.netty.handler.codec.json.JsonObjectDecoderTest.doTestStreamJsonArrayOverMultipleWrites([CtLiteralImpl]2, [CtVariableReadImpl]array, [CtVariableReadImpl]result);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testStreamJsonArrayOverMultipleWrites2() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] array = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"   [{\"test", [CtLiteralImpl]"case\"  : \"\\\"}]Escaped dou\\\"ble quotes \\\" in JSON str\\\"ing\"", [CtLiteralImpl]"  }\n\n    , {\"test", [CtLiteralImpl]"case\" : \"Streaming string me", [CtLiteralImpl]"ssage\"} ]      " };
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] result = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"{\"testcase\"  : \"\\\"}]Escaped dou\\\"ble quotes \\\" in JSON str\\\"ing\"  }", [CtLiteralImpl]"{\"testcase\" : \"Streaming string message\"}" };
        [CtInvocationImpl]io.netty.handler.codec.json.JsonObjectDecoderTest.doTestStreamJsonArrayOverMultipleWrites([CtLiteralImpl]2, [CtVariableReadImpl]array, [CtVariableReadImpl]result);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testStreamJsonArrayOverMultipleWrites3() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] array = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"   [{\"test", [CtLiteralImpl]"case\"  : \"\\\"}]Escaped dou\\\"ble quotes \\\" in JSON str\\\"ing\"", [CtLiteralImpl]"  }\n\n    , [{\"test", [CtLiteralImpl]"case\" : \"Streaming string me", [CtLiteralImpl]"ssage\"}] ]      " };
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] result = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"{\"testcase\"  : \"\\\"}]Escaped dou\\\"ble quotes \\\" in JSON str\\\"ing\"  }", [CtLiteralImpl]"[{\"testcase\" : \"Streaming string message\"}]" };
        [CtInvocationImpl]io.netty.handler.codec.json.JsonObjectDecoderTest.doTestStreamJsonArrayOverMultipleWrites([CtLiteralImpl]2, [CtVariableReadImpl]array, [CtVariableReadImpl]result);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void doTestStreamJsonArrayOverMultipleWrites([CtParameterImpl][CtTypeReferenceImpl]int indexDataAvailable, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] array, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] result) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder([CtLiteralImpl]true));
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean dataAvailable = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String part : [CtVariableReadImpl]array) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataAvailable = [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]part, [CtTypeAccessImpl]CharsetUtil.UTF_8));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]indexDataAvailable > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl]assertFalse([CtVariableReadImpl]dataAvailable);
            } else [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtVariableReadImpl]dataAvailable);
            }
            [CtUnaryOperatorImpl][CtVariableWriteImpl]indexDataAvailable--;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String part : [CtVariableReadImpl]result) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
            [CtInvocationImpl]assertEquals([CtVariableReadImpl]part, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
            [CtInvocationImpl][CtVariableReadImpl]res.release();
        }
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSingleByteStream() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String json = [CtLiteralImpl]"{\"foo\" : {\"bar\" : [{},{}]}}";
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]byte c : [CtInvocationImpl][CtVariableReadImpl]json.getBytes([CtTypeAccessImpl]CharsetUtil.UTF_8)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtNewArrayImpl]new [CtTypeReferenceImpl]byte[]{ [CtVariableReadImpl]c }));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]json, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testBackslashInString1() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtCommentImpl]// {"foo" : "bar\""}
        [CtTypeReferenceImpl]java.lang.String json = [CtLiteralImpl]"{\"foo\" : \"bar\\\"\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]json, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]json, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testBackslashInString2() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtCommentImpl]// {"foo" : "bar\\"}
        [CtTypeReferenceImpl]java.lang.String json = [CtLiteralImpl]"{\"foo\" : \"bar\\\\\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]json, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]json, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testBackslashInString3() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtCommentImpl]// {"foo" : "bar\\\""}
        [CtTypeReferenceImpl]java.lang.String json = [CtLiteralImpl]"{\"foo\" : \"bar\\\\\\\"\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]json, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]json, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testMultipleJsonObjectsInOneWrite() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object1 = [CtLiteralImpl]"{\"key\" : \"value1\"}";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object2 = [CtLiteralImpl]"{\"key\" : \"value2\"}";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object3 = [CtLiteralImpl]"{\"key\" : \"value3\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]object1 + [CtVariableReadImpl]object2) + [CtVariableReadImpl]object3, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object1, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object2, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object3, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]io.netty.handler.codec.CorruptedFrameException.class)
    public [CtTypeReferenceImpl]void testNonJsonContent1() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtLiteralImpl]"  b [1,2,3]", [CtTypeAccessImpl]CharsetUtil.UTF_8));
        } finally [CtBlockImpl]{
            [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
        }
        [CtInvocationImpl]fail();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]io.netty.handler.codec.CorruptedFrameException.class)
    public [CtTypeReferenceImpl]void testNonJsonContent2() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtLiteralImpl]"  [1,2,3]  ", [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"[1,2,3]", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtLiteralImpl]" a {\"key\" : 10}", [CtTypeAccessImpl]CharsetUtil.UTF_8));
        } finally [CtBlockImpl]{
            [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
        }
        [CtInvocationImpl]fail();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]io.netty.handler.codec.TooLongFrameException.class)
    public [CtTypeReferenceImpl]void testMaxObjectLength() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder([CtLiteralImpl]6));
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtLiteralImpl]"[2,4,5]", [CtTypeAccessImpl]CharsetUtil.UTF_8));
        } finally [CtBlockImpl]{
            [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
        }
        [CtInvocationImpl]fail();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testOneJsonObjectPerWrite() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object1 = [CtLiteralImpl]"{\"key\" : \"value1\"}";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object2 = [CtLiteralImpl]"{\"key\" : \"value2\"}";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object3 = [CtLiteralImpl]"{\"key\" : \"value3\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]object1, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]object2, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]object3, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object1, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object2, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object3, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSpecialJsonCharsInString() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object = [CtLiteralImpl]"{ \"key\" : \"[]{}}\\\"}}\'}\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]object, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testStreamArrayElementsSimple() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE, [CtLiteralImpl]true));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String array = [CtBinaryOperatorImpl][CtLiteralImpl]"[  12, \"bla\"  , 13.4   \t  ,{\"key0\" : [1,2], \"key1\" : 12, \"key2\" : {}} , " + [CtLiteralImpl]"true, false, null, [\"bla\", {}, [1,2,3]] ]";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String object = [CtLiteralImpl]"{\"bla\" : \"blub\"}";
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]array, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]object, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"12", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"\"bla\"", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"13.4", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"{\"key0\" : [1,2], \"key1\" : 12, \"key2\" : {}}", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"true", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"false", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"null", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"[\"bla\", {}, [1,2,3]]", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]object, [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testCorruptedFrameException() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String part1 = [CtBinaryOperatorImpl][CtLiteralImpl]"{\"a\":{\"b\":{\"c\":{ \"d\":\"27301\", \"med\":\"d\", \"path\":\"27310\"} }," + [CtLiteralImpl]" \"status\":\"OK\" } }{\"";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String part2 = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"a\":{\"b\":{\"c\":{\"ory\":[{\"competi\":[{\"event\":[{" + [CtLiteralImpl]"\"externalI\":{\"external\"") + [CtLiteralImpl]":[{\"id\":\"O\"} ]";
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel ch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.channel.embedded.EmbeddedChannel([CtConstructorCallImpl]new [CtTypeReferenceImpl]JsonObjectDecoder());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf res;
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]part1, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"{\"a\":{\"b\":{\"c\":{ \"d\":\"27301\", \"med\":\"d\", \"path\":\"27310\"} }, " + [CtLiteralImpl]"\"status\":\"OK\" } }", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtVariableReadImpl]part2, [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertNull([CtVariableReadImpl]res);
        [CtInvocationImpl][CtVariableReadImpl]ch.writeInbound([CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtLiteralImpl]"}}]}]}]}}}}", [CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtAssignmentImpl][CtVariableWriteImpl]res = [CtInvocationImpl][CtVariableReadImpl]ch.readInbound();
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"{\"a\":{\"b\":{\"c\":{\"ory\":[{\"competi\":[{\"event\":[{" + [CtLiteralImpl]"\"externalI\":{") + [CtLiteralImpl]"\"external\":[{\"id\":\"O\"} ]}}]}]}]}}}}", [CtInvocationImpl][CtVariableReadImpl]res.toString([CtTypeAccessImpl]CharsetUtil.UTF_8));
        [CtInvocationImpl][CtVariableReadImpl]res.release();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]ch.finish());
    }
}
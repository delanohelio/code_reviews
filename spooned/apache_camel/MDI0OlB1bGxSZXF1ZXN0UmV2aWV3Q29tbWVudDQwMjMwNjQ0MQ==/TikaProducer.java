[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.camel.component.tika;
[CtImportImpl]import javax.xml.transform.sax.TransformerHandler;
[CtUnresolvedImport]import org.apache.tika.sax.BodyContentHandler;
[CtImportImpl]import java.io.OutputStream;
[CtUnresolvedImport]import org.apache.tika.parser.Parser;
[CtImportImpl]import javax.xml.transform.TransformerConfigurationException;
[CtUnresolvedImport]import org.apache.camel.Exchange;
[CtUnresolvedImport]import org.apache.tika.config.TikaConfig;
[CtImportImpl]import org.xml.sax.SAXException;
[CtUnresolvedImport]import org.apache.tika.mime.MediaType;
[CtImportImpl]import org.xml.sax.ContentHandler;
[CtUnresolvedImport]import org.apache.tika.parser.html.BoilerpipeContentHandler;
[CtUnresolvedImport]import org.apache.tika.metadata.Metadata;
[CtUnresolvedImport]import org.apache.tika.exception.TikaException;
[CtImportImpl]import javax.xml.transform.OutputKeys;
[CtImportImpl]import javax.xml.transform.TransformerFactory;
[CtUnresolvedImport]import org.apache.camel.support.DefaultProducer;
[CtImportImpl]import java.io.UnsupportedEncodingException;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import javax.xml.transform.stream.StreamResult;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.io.ByteArrayOutputStream;
[CtUnresolvedImport]import org.apache.tika.sax.ExpandedTitleContentHandler;
[CtUnresolvedImport]import org.apache.tika.detect.Detector;
[CtUnresolvedImport]import org.apache.tika.parser.AutoDetectParser;
[CtImportImpl]import javax.xml.transform.sax.SAXTransformerFactory;
[CtImportImpl]import javax.xml.XMLConstants;
[CtUnresolvedImport]import org.apache.tika.parser.ParseContext;
[CtImportImpl]import java.io.OutputStreamWriter;
[CtClassImpl]public class TikaProducer extends [CtTypeReferenceImpl]org.apache.camel.support.DefaultProducer {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.camel.component.tika.TikaConfiguration tikaConfiguration;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.tika.parser.Parser parser;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.tika.detect.Detector detector;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String encoding;

    [CtConstructorImpl]public TikaProducer([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.component.tika.TikaEndpoint endpoint) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]endpoint, [CtLiteralImpl]true, [CtLiteralImpl]null);
    }

    [CtConstructorImpl]public TikaProducer([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.component.tika.TikaEndpoint endpoint, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tika.parser.Parser parser) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]endpoint, [CtBinaryOperatorImpl][CtVariableReadImpl]parser == [CtLiteralImpl]null, [CtVariableReadImpl]parser);
    }

    [CtConstructorImpl]private TikaProducer([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.component.tika.TikaEndpoint endpoint, [CtParameterImpl][CtTypeReferenceImpl]boolean autodetectParser, [CtParameterImpl][CtTypeReferenceImpl]org.apache.tika.parser.Parser parser) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]endpoint);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.tikaConfiguration = [CtInvocationImpl][CtVariableReadImpl]endpoint.getTikaConfiguration();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.encoding = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaConfiguration.getTikaParseOutputEncoding();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tika.config.TikaConfig config = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaConfiguration.getTikaConfig();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.detector = [CtInvocationImpl][CtVariableReadImpl]config.getDetector();
        [CtIfImpl]if ([CtVariableReadImpl]autodetectParser) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tika.parser.AutoDetectParser([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaConfiguration.getTikaConfig());
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parser = [CtVariableReadImpl]parser;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void process([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Exchange exchange) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.camel.component.tika.TikaOperation operation = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaConfiguration.getOperation();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object result;
        [CtSwitchImpl]switch ([CtVariableReadImpl]operation) {
            [CtCaseImpl]case [CtFieldReadImpl]detect :
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl]doDetect([CtVariableReadImpl]exchange);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]parse :
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl]doParse([CtVariableReadImpl]exchange);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unknown operation %s", [CtInvocationImpl][CtFieldReadImpl]tikaConfiguration.getOperation()));
        }
        [CtInvocationImpl][CtCommentImpl]// propagate headers
        [CtInvocationImpl][CtVariableReadImpl]exchange.getOut().setHeaders([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exchange.getIn().getHeaders());
        [CtInvocationImpl][CtCommentImpl]// and set result
        [CtInvocationImpl][CtVariableReadImpl]exchange.getOut().setBody([CtVariableReadImpl]result);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object doDetect([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Exchange exchange) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream inputStream = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exchange.getIn().getBody([CtFieldReadImpl]java.io.InputStream.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tika.metadata.Metadata metadata = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tika.metadata.Metadata();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tika.mime.MediaType result = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.detector.detect([CtVariableReadImpl]inputStream, [CtVariableReadImpl]metadata);
        [CtInvocationImpl]convertMetadataToHeaders([CtVariableReadImpl]metadata, [CtVariableReadImpl]exchange);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object doParse([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Exchange exchange) throws [CtTypeReferenceImpl]org.apache.tika.exception.TikaException, [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.xml.sax.SAXException, [CtTypeReferenceImpl]javax.xml.transform.TransformerConfigurationException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream inputStream = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exchange.getIn().getBody([CtFieldReadImpl]java.io.InputStream.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.OutputStream result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayOutputStream();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.xml.sax.ContentHandler contentHandler = [CtInvocationImpl]getContentHandler([CtFieldReadImpl][CtThisAccessImpl]this.tikaConfiguration, [CtVariableReadImpl]result);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tika.parser.ParseContext context = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tika.parser.ParseContext();
        [CtInvocationImpl][CtVariableReadImpl]context.set([CtFieldReadImpl]org.apache.tika.parser.Parser.class, [CtFieldReadImpl][CtThisAccessImpl]this.parser);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tika.metadata.Metadata metadata = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tika.metadata.Metadata();
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.parser.parse([CtVariableReadImpl]inputStream, [CtVariableReadImpl]contentHandler, [CtVariableReadImpl]metadata, [CtVariableReadImpl]context);
        [CtInvocationImpl]convertMetadataToHeaders([CtVariableReadImpl]metadata, [CtVariableReadImpl]exchange);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void convertMetadataToHeaders([CtParameterImpl][CtTypeReferenceImpl]org.apache.tika.metadata.Metadata metadata, [CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Exchange exchange) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]metadata != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String metaname : [CtInvocationImpl][CtVariableReadImpl]metadata.names()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] values = [CtInvocationImpl][CtVariableReadImpl]metadata.getValues([CtVariableReadImpl]metaname);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]values.length == [CtLiteralImpl]1) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exchange.getIn().setHeader([CtVariableReadImpl]metaname, [CtArrayReadImpl][CtVariableReadImpl]values[[CtLiteralImpl]0]);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exchange.getIn().setHeader([CtVariableReadImpl]metaname, [CtVariableReadImpl]values);
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.xml.sax.ContentHandler getContentHandler([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.component.tika.TikaConfiguration configuration, [CtParameterImpl][CtTypeReferenceImpl]java.io.OutputStream outputStream) throws [CtTypeReferenceImpl]javax.xml.transform.TransformerConfigurationException, [CtTypeReferenceImpl]java.io.UnsupportedEncodingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.xml.sax.ContentHandler result = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.camel.component.tika.TikaParseOutputFormat outputFormat = [CtInvocationImpl][CtVariableReadImpl]configuration.getTikaParseOutputFormat();
        [CtSwitchImpl]switch ([CtVariableReadImpl]outputFormat) {
            [CtCaseImpl]case [CtFieldReadImpl]xml :
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl]getTransformerHandler([CtVariableReadImpl]outputStream, [CtLiteralImpl]"xml", [CtLiteralImpl]true);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]text :
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tika.sax.BodyContentHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.OutputStreamWriter([CtVariableReadImpl]outputStream, [CtFieldReadImpl][CtThisAccessImpl]this.encoding));
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]textMain :
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tika.parser.html.BoilerpipeContentHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.OutputStreamWriter([CtVariableReadImpl]outputStream, [CtFieldReadImpl][CtThisAccessImpl]this.encoding));
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]html :
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tika.sax.ExpandedTitleContentHandler([CtInvocationImpl]getTransformerHandler([CtVariableReadImpl]outputStream, [CtLiteralImpl]"html", [CtLiteralImpl]true));
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unknown format %s", [CtInvocationImpl][CtFieldReadImpl]tikaConfiguration.getTikaParseOutputFormat()));
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.xml.transform.sax.TransformerHandler getTransformerHandler([CtParameterImpl][CtTypeReferenceImpl]java.io.OutputStream output, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String method, [CtParameterImpl][CtTypeReferenceImpl]boolean prettyPrint) throws [CtTypeReferenceImpl]javax.xml.transform.TransformerConfigurationException, [CtTypeReferenceImpl]java.io.UnsupportedEncodingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.transform.sax.SAXTransformerFactory factory = [CtInvocationImpl](([CtTypeReferenceImpl]javax.xml.transform.sax.SAXTransformerFactory) ([CtTypeAccessImpl]javax.xml.transform.TransformerFactory.newInstance()));
        [CtInvocationImpl][CtVariableReadImpl]factory.setFeature([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.XMLConstants.[CtFieldReferenceImpl]FEATURE_SECURE_PROCESSING, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.transform.sax.TransformerHandler handler = [CtInvocationImpl][CtVariableReadImpl]factory.newTransformerHandler();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handler.getTransformer().setOutputProperty([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.transform.OutputKeys.[CtFieldReferenceImpl]METHOD, [CtVariableReadImpl]method);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handler.getTransformer().setOutputProperty([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.transform.OutputKeys.[CtFieldReferenceImpl]INDENT, [CtConditionalImpl][CtVariableReadImpl]prettyPrint ? [CtLiteralImpl]"yes" : [CtLiteralImpl]"no");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.encoding != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handler.getTransformer().setOutputProperty([CtFieldReadImpl][CtTypeAccessImpl]javax.xml.transform.OutputKeys.[CtFieldReferenceImpl]ENCODING, [CtFieldReadImpl][CtThisAccessImpl]this.encoding);
        }
        [CtInvocationImpl][CtVariableReadImpl]handler.setResult([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.transform.stream.StreamResult([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.OutputStreamWriter([CtVariableReadImpl]output, [CtFieldReadImpl][CtThisAccessImpl]this.encoding)));
        [CtReturnImpl]return [CtVariableReadImpl]handler;
    }
}
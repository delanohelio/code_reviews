[CompilationUnitImpl][CtJavaDocImpl]/**
 * MegaMek - Copyright (C) 2020 - The MegaMek Team
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
[CtPackageDeclarationImpl]package megamek.client.ui.swing.util;
[CtImportImpl]import java.util.Dictionary;
[CtImportImpl]import java.awt.*;
[CtImportImpl]import java.net.URL;
[CtImportImpl]import java.net.MalformedURLException;
[CtImportImpl]import javax.swing.text.Element;
[CtImportImpl]import java.io.ByteArrayInputStream;
[CtImportImpl]import java.util.Hashtable;
[CtImportImpl]import javax.swing.text.html.ImageView;
[CtImportImpl]import javax.imageio.ImageIO;
[CtImportImpl]import java.util.Base64;
[CtImportImpl]import javax.swing.text.html.HTML;
[CtImportImpl]import java.awt.image.BufferedImage;
[CtClassImpl]public class BASE64ImageView extends [CtTypeReferenceImpl]javax.swing.text.html.ImageView {
    [CtFieldImpl]private [CtTypeReferenceImpl]java.net.URL url;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Returns a unique url for the image. It's created by getting the code location and adding the element to it.
     * This doesn't strictly need to be an actual url, it just needs to be unique and properly formatted.
     *
     * @param elmnt
     * 		the html element containing the base64 src
     */
    public BASE64ImageView([CtParameterImpl][CtTypeReferenceImpl]javax.swing.text.Element elmnt) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]elmnt);
        [CtInvocationImpl]populateImage();
    }

    [CtMethodImpl][CtCommentImpl]// Creates a cache of images for each <img> src,
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    private [CtTypeReferenceImpl]void populateImage() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Dictionary<[CtTypeReferenceImpl]java.net.URL, [CtTypeReferenceImpl]java.awt.Image> cache = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.Dictionary<[CtTypeReferenceImpl]java.net.URL, [CtTypeReferenceImpl]java.awt.Image>) ([CtInvocationImpl]getDocument().getProperty([CtLiteralImpl]"imageCache")));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cache == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Hashtable<>();
            [CtInvocationImpl][CtInvocationImpl]getDocument().putProperty([CtLiteralImpl]"imageCache", [CtVariableReadImpl]cache);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL src = [CtInvocationImpl]getImageURL();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.Image image = [CtInvocationImpl]loadImage();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]image != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]cache.put([CtVariableReadImpl]src, [CtVariableReadImpl]image);
        }
    }

    [CtMethodImpl][CtCommentImpl]// decodes the Base64 string into an image and returns it
    private [CtTypeReferenceImpl]java.awt.Image loadImage() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String b64 = [CtInvocationImpl]getBASE64Image();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]b64 != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.awt.image.BufferedImage newImage = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.ByteArrayInputStream bais = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bais = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Base64.getDecoder().decode([CtInvocationImpl][CtVariableReadImpl]b64.getBytes()));
                [CtAssignmentImpl][CtVariableWriteImpl]newImage = [CtInvocationImpl][CtTypeAccessImpl]javax.imageio.ImageIO.read([CtVariableReadImpl]bais);
                [CtInvocationImpl][CtVariableReadImpl]bais.close();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable ex) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ex.printStackTrace();
            }
            [CtReturnImpl]return [CtVariableReadImpl]newImage;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a unique url for the image. It's created by getting the code location and adding the element to it.
     * This doesn't strictly need to be an actual url, it just needs to be unique and properly formatted.
     *
     * @return the generated url for the image
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.net.URL getImageURL() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String src = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtInvocationImpl]getElement().getAttributes().getAttribute([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.text.html.HTML.Attribute.[CtFieldReferenceImpl]SRC)));
        [CtIfImpl]if ([CtInvocationImpl]isBase64Encoded([CtVariableReadImpl]src)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.url = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtBinaryOperatorImpl][CtLiteralImpl]"file:/" + [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getElement().toString());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.net.MalformedURLException e) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
            }
            [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.url;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getImageURL();
    }

    [CtMethodImpl][CtCommentImpl]// checks if the given src is encoded
    private [CtTypeReferenceImpl]boolean isBase64Encoded([CtParameterImpl][CtTypeReferenceImpl]java.lang.String src) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]src != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]src.contains([CtLiteralImpl]"base64,");
    }

    [CtMethodImpl][CtCommentImpl]// returns the string without the base64 text
    private [CtTypeReferenceImpl]java.lang.String getBASE64Image() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String src = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtInvocationImpl]getElement().getAttributes().getAttribute([CtFieldReadImpl][CtTypeAccessImpl]javax.swing.text.html.HTML.Attribute.[CtFieldReferenceImpl]SRC)));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isBase64Encoded([CtVariableReadImpl]src)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]src.substring([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]src.indexOf([CtLiteralImpl]"base64,") + [CtLiteralImpl]7);
    }
}
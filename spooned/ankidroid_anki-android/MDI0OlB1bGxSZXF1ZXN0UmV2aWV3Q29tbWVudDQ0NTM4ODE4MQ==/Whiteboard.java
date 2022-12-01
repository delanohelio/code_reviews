[CompilationUnitImpl][CtJavaDocImpl]/**
 * **************************************************************************************
 * Copyright (c) 2009 Andrew <andrewdubya@gmail.com>                                    *
 * Copyright (c) 2009 Nicolas Raoul <nicolas.raoul@gmail.com>                           *
 * Copyright (c) 2009 Edu Zamora <edu.zasu@gmail.com>                                   *
 *                                                                                      *
 * This program is free software; you can redistribute it and/or modify it under        *
 * the terms of the GNU General Public License as published by the Free Software        *
 * Foundation; either version 3 of the License, or (at your option) any later           *
 * version.                                                                             *
 *                                                                                      *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY      *
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A      *
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.             *
 *                                                                                      *
 * You should have received a copy of the GNU General Public License along with         *
 * this program.  If not, see <http://www.gnu.org/licenses/>.                           *
 * **************************************************************************************
 */
[CtPackageDeclarationImpl]package com.ichi2.anki;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.graphics.Path;
[CtUnresolvedImport]import android.graphics.PointF;
[CtUnresolvedImport]import android.widget.LinearLayout;
[CtUnresolvedImport]import android.graphics.PathMeasure;
[CtUnresolvedImport]import android.view.WindowManager;
[CtUnresolvedImport]import android.graphics.Point;
[CtImportImpl]import java.util.Stack;
[CtUnresolvedImport]import android.graphics.Color;
[CtImportImpl]import java.lang.ref.WeakReference;
[CtUnresolvedImport]import android.graphics.Bitmap;
[CtUnresolvedImport]import android.view.MotionEvent;
[CtUnresolvedImport]import android.view.Display;
[CtUnresolvedImport]import android.view.View;
[CtUnresolvedImport]import android.widget.Button;
[CtUnresolvedImport]import android.graphics.Canvas;
[CtUnresolvedImport]import android.graphics.Paint;
[CtUnresolvedImport]import androidx.core.content.ContextCompat;
[CtClassImpl][CtJavaDocImpl]/**
 * Whiteboard allowing the user to draw the card's answer on the touchscreen.
 */
public class Whiteboard extends [CtTypeReferenceImpl]android.view.View implements [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]float TOUCH_TOLERANCE = [CtLiteralImpl]4;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.graphics.Paint mPaint;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.ichi2.anki.Whiteboard.UndoStack mUndo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.Whiteboard.UndoStack();

    [CtFieldImpl]private [CtTypeReferenceImpl]android.graphics.Bitmap mBitmap;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.graphics.Canvas mCanvas;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.graphics.Path mPath;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.graphics.Paint mBitmapPaint;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.ref.WeakReference<[CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer> mCardViewer;

    [CtFieldImpl]private [CtTypeReferenceImpl]float mX;

    [CtFieldImpl]private [CtTypeReferenceImpl]float mY;

    [CtFieldImpl]private [CtTypeReferenceImpl]float mSecondFingerX0;

    [CtFieldImpl]private [CtTypeReferenceImpl]float mSecondFingerY0;

    [CtFieldImpl]private [CtTypeReferenceImpl]float mSecondFingerX;

    [CtFieldImpl]private [CtTypeReferenceImpl]float mSecondFingerY;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mSecondFingerPointerId;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mSecondFingerWithinTapTolerance;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mCurrentlyDrawing = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mInvertedColors;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mMonochrome;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mUndoModeActive = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.LinearLayout colorPalette;

    [CtConstructorImpl]public Whiteboard([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer cardViewer, [CtParameterImpl][CtTypeReferenceImpl]boolean inverted, [CtParameterImpl][CtTypeReferenceImpl]boolean monochrome) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]cardViewer, [CtLiteralImpl]null);
        [CtAssignmentImpl][CtFieldWriteImpl]mCardViewer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ref.WeakReference<>([CtVariableReadImpl]cardViewer);
        [CtAssignmentImpl][CtFieldWriteImpl]mInvertedColors = [CtVariableReadImpl]inverted;
        [CtAssignmentImpl][CtFieldWriteImpl]mMonochrome = [CtVariableReadImpl]monochrome;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int foregroundColor;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mInvertedColors) [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl]mMonochrome) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]foregroundColor = [CtFieldReadImpl]android.graphics.Color.BLACK;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]foregroundColor = [CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtVariableReadImpl]cardViewer, [CtTypeAccessImpl]R.color.wb_fg_color);
            }
        } else [CtIfImpl]if ([CtFieldReadImpl]mMonochrome) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]foregroundColor = [CtFieldReadImpl]android.graphics.Color.WHITE;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]foregroundColor = [CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtVariableReadImpl]cardViewer, [CtTypeAccessImpl]R.color.wb_fg_color_inv);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]mPaint = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.Paint();
        [CtInvocationImpl][CtFieldReadImpl]mPaint.setAntiAlias([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]mPaint.setDither([CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// mPaint.setColor(foregroundColor);
        [CtFieldReadImpl]mPaint.setStyle([CtTypeAccessImpl]Paint.Style.STROKE);
        [CtInvocationImpl][CtFieldReadImpl]mPaint.setStrokeJoin([CtTypeAccessImpl]Paint.Join.ROUND);
        [CtInvocationImpl][CtFieldReadImpl]mPaint.setStrokeCap([CtTypeAccessImpl]Paint.Cap.ROUND);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int wbStrokeWidth = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.AnkiDroidApp.getSharedPrefs([CtVariableReadImpl]cardViewer).getInt([CtLiteralImpl]"whiteBoardStrokeWidth", [CtLiteralImpl]6);
        [CtInvocationImpl][CtFieldReadImpl]mPaint.setStrokeWidth([CtVariableReadImpl](([CtTypeReferenceImpl]float) (wbStrokeWidth)));
        [CtInvocationImpl]createBitmap();
        [CtAssignmentImpl][CtFieldWriteImpl]mPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.Path();
        [CtAssignmentImpl][CtFieldWriteImpl]mBitmapPaint = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.Paint([CtFieldReadImpl]android.graphics.Paint.DITHER_FLAG);
        [CtAssignmentImpl][CtCommentImpl]// selecting pen color to draw
        [CtFieldWriteImpl]colorPalette = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.LinearLayout) ([CtVariableReadImpl]cardViewer.findViewById([CtTypeAccessImpl]R.id.whiteboard_pen_color)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button penColorWhite = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Button) ([CtVariableReadImpl]cardViewer.findViewById([CtTypeAccessImpl]R.id.pen_color_white)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button penColorBlack = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Button) ([CtVariableReadImpl]cardViewer.findViewById([CtTypeAccessImpl]R.id.pen_color_black)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button penColorRed = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Button) ([CtVariableReadImpl]cardViewer.findViewById([CtTypeAccessImpl]R.id.pen_color_red)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button penColorGreen = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Button) ([CtVariableReadImpl]cardViewer.findViewById([CtTypeAccessImpl]R.id.pen_color_green)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button penColorBlue = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Button) ([CtVariableReadImpl]cardViewer.findViewById([CtTypeAccessImpl]R.id.pen_color_blue)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button penColorYellow = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Button) ([CtVariableReadImpl]cardViewer.findViewById([CtTypeAccessImpl]R.id.pen_color_yellow)));
        [CtInvocationImpl][CtVariableReadImpl]penColorWhite.setOnClickListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]penColorBlack.setOnClickListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]penColorRed.setOnClickListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]penColorGreen.setOnClickListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]penColorBlue.setOnClickListener([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]penColorYellow.setOnClickListener([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onDraw([CtParameterImpl][CtTypeReferenceImpl]android.graphics.Canvas canvas) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onDraw([CtVariableReadImpl]canvas);
        [CtInvocationImpl][CtVariableReadImpl]canvas.drawColor([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]canvas.drawBitmap([CtFieldReadImpl]mBitmap, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtFieldReadImpl]mBitmapPaint);
        [CtInvocationImpl][CtVariableReadImpl]canvas.drawPath([CtFieldReadImpl]mPath, [CtFieldReadImpl]mPaint);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle motion events to draw using the touch screen or to interact with the flashcard behind
     * the whiteboard by using a second finger.
     *
     * @param event
     * 		The motion event.
     * @return True if the event was handled, false otherwise
     */
    public [CtTypeReferenceImpl]boolean handleTouchEvent([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]handleDrawEvent([CtVariableReadImpl]event) || [CtInvocationImpl]handleMultiTouchEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle motion events to draw using the touch screen. Only simple touch events are processed,
     * a multitouch event aborts to current stroke.
     *
     * @param event
     * 		The motion event.
     * @return True if the event was handled, false otherwise or when drawing was aborted due to
    detection of a multitouch event.
     */
    private [CtTypeReferenceImpl]boolean handleDrawEvent([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]float x = [CtInvocationImpl][CtVariableReadImpl]event.getX();
        [CtLocalVariableImpl][CtTypeReferenceImpl]float y = [CtInvocationImpl][CtVariableReadImpl]event.getY();
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]event.getActionMasked()) {
            [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_DOWN :
                [CtInvocationImpl]drawStart([CtVariableReadImpl]x, [CtVariableReadImpl]y);
                [CtInvocationImpl]invalidate();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_MOVE :
                [CtIfImpl]if ([CtFieldReadImpl]mCurrentlyDrawing) [CtBlockImpl]{
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]event.getHistorySize(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                        [CtInvocationImpl]drawAlong([CtInvocationImpl][CtVariableReadImpl]event.getHistoricalX([CtVariableReadImpl]i), [CtInvocationImpl][CtVariableReadImpl]event.getHistoricalY([CtVariableReadImpl]i));
                    }
                    [CtInvocationImpl]drawAlong([CtVariableReadImpl]x, [CtVariableReadImpl]y);
                    [CtInvocationImpl]invalidate();
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_UP :
                [CtIfImpl]if ([CtFieldReadImpl]mCurrentlyDrawing) [CtBlockImpl]{
                    [CtInvocationImpl]drawFinish();
                    [CtInvocationImpl]invalidate();
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_POINTER_DOWN :
                [CtIfImpl]if ([CtFieldReadImpl]mCurrentlyDrawing) [CtBlockImpl]{
                    [CtInvocationImpl]drawAbort();
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            [CtCaseImpl]default :
                [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtCommentImpl]// Parse multitouch input to scroll the card behind the whiteboard or click on elements
    private [CtTypeReferenceImpl]boolean handleMultiTouchEvent([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getPointerCount() == [CtLiteralImpl]2) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]event.getActionMasked()) {
                [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_POINTER_DOWN :
                    [CtInvocationImpl]reinitializeSecondFinger([CtVariableReadImpl]event);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_MOVE :
                    [CtReturnImpl]return [CtInvocationImpl]trySecondFingerScroll([CtVariableReadImpl]event);
                [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_POINTER_UP :
                    [CtReturnImpl]return [CtInvocationImpl]trySecondFingerClick([CtVariableReadImpl]event);
                [CtCaseImpl]default :
                    [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Clear the whiteboard.
     */
    public [CtTypeReferenceImpl]void clear() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mUndoModeActive = [CtLiteralImpl]false;
        [CtInvocationImpl][CtFieldReadImpl]mBitmap.eraseColor([CtLiteralImpl]0);
        [CtInvocationImpl][CtFieldReadImpl]mUndo.clear();
        [CtInvocationImpl]invalidate();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mCardViewer.get() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCardViewer.get().supportInvalidateOptionsMenu();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Undo the last stroke
     */
    public [CtTypeReferenceImpl]void undo() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mUndo.pop();
        [CtInvocationImpl][CtFieldReadImpl]mUndo.apply();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]undoEmpty() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mCardViewer.get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCardViewer.get().supportInvalidateOptionsMenu();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the number of strokes currently on the undo queue
     */
    public [CtTypeReferenceImpl]int undoSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mUndo.size();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Whether there are strokes to undo
     */
    public [CtTypeReferenceImpl]boolean undoEmpty() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mUndo.empty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return true if the undo queue has had any strokes added to it since the last clear
     */
    public [CtTypeReferenceImpl]boolean isUndoModeActive() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mUndoModeActive;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createBitmap([CtParameterImpl][CtTypeReferenceImpl]int w, [CtParameterImpl][CtTypeReferenceImpl]int h, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.graphics.Bitmap.Config conf) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mBitmap = [CtInvocationImpl][CtTypeAccessImpl]android.graphics.Bitmap.createBitmap([CtVariableReadImpl]w, [CtVariableReadImpl]h, [CtVariableReadImpl]conf);
        [CtAssignmentImpl][CtFieldWriteImpl]mCanvas = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.Canvas([CtFieldReadImpl]mBitmap);
        [CtInvocationImpl]clear();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createBitmap() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// To fix issue #1336, just make the whiteboard big and square.
        final [CtTypeReferenceImpl]android.graphics.Point p = [CtInvocationImpl]com.ichi2.anki.Whiteboard.getDisplayDimenions();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int bitmapSize = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtFieldReadImpl][CtVariableReadImpl]p.x, [CtFieldReadImpl][CtVariableReadImpl]p.y);
        [CtInvocationImpl]createBitmap([CtVariableReadImpl]bitmapSize, [CtVariableReadImpl]bitmapSize, [CtTypeAccessImpl]Bitmap.Config.ARGB_8888);
        [CtCommentImpl]/* if (mMonochrome && !mInvertedColors) {
        createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ALPHA_8);
        } else {
        createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_4444);
        }
         */
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void drawStart([CtParameterImpl][CtTypeReferenceImpl]float x, [CtParameterImpl][CtTypeReferenceImpl]float y) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mCurrentlyDrawing = [CtLiteralImpl]true;
        [CtInvocationImpl][CtFieldReadImpl]mPath.reset();
        [CtInvocationImpl][CtFieldReadImpl]mPath.moveTo([CtVariableReadImpl]x, [CtVariableReadImpl]y);
        [CtAssignmentImpl][CtFieldWriteImpl]mX = [CtVariableReadImpl]x;
        [CtAssignmentImpl][CtFieldWriteImpl]mY = [CtVariableReadImpl]y;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void drawAlong([CtParameterImpl][CtTypeReferenceImpl]float x, [CtParameterImpl][CtTypeReferenceImpl]float y) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]float dx = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtBinaryOperatorImpl][CtVariableReadImpl]x - [CtFieldReadImpl]mX);
        [CtLocalVariableImpl][CtTypeReferenceImpl]float dy = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtBinaryOperatorImpl][CtVariableReadImpl]y - [CtFieldReadImpl]mY);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dx >= [CtFieldReadImpl]com.ichi2.anki.Whiteboard.TOUCH_TOLERANCE) || [CtBinaryOperatorImpl]([CtVariableReadImpl]dy >= [CtFieldReadImpl]com.ichi2.anki.Whiteboard.TOUCH_TOLERANCE)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mPath.quadTo([CtFieldReadImpl]mX, [CtFieldReadImpl]mY, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]x + [CtFieldReadImpl]mX) / [CtLiteralImpl]2, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]y + [CtFieldReadImpl]mY) / [CtLiteralImpl]2);
            [CtAssignmentImpl][CtFieldWriteImpl]mX = [CtVariableReadImpl]x;
            [CtAssignmentImpl][CtFieldWriteImpl]mY = [CtVariableReadImpl]y;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void drawFinish() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mCurrentlyDrawing = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.graphics.PathMeasure pm = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.PathMeasure([CtFieldReadImpl]mPath, [CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]mPath.lineTo([CtFieldReadImpl]mX, [CtFieldReadImpl]mY);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pm.getLength() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCanvas.drawPath([CtFieldReadImpl]mPath, [CtFieldReadImpl]mPaint);
            [CtInvocationImpl][CtFieldReadImpl]mUndo.add([CtFieldReadImpl]mPath);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCanvas.drawPoint([CtFieldReadImpl]mX, [CtFieldReadImpl]mY, [CtFieldReadImpl]mPaint);
            [CtInvocationImpl][CtFieldReadImpl]mUndo.add([CtFieldReadImpl]mX, [CtFieldReadImpl]mY);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]mUndoModeActive = [CtLiteralImpl]true;
        [CtInvocationImpl][CtCommentImpl]// kill the path so we don't double draw
        [CtFieldReadImpl]mPath.reset();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mUndo.size() == [CtLiteralImpl]1) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mCardViewer.get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCardViewer.get().supportInvalidateOptionsMenu();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void drawAbort() [CtBlockImpl]{
        [CtInvocationImpl]drawFinish();
        [CtInvocationImpl]undo();
    }

    [CtMethodImpl][CtCommentImpl]// call this with an ACTION_POINTER_DOWN event to start a new round of detecting drag or tap with
    [CtCommentImpl]// a second finger
    private [CtTypeReferenceImpl]void reinitializeSecondFinger([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerWithinTapTolerance = [CtLiteralImpl]true;
        [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerPointerId = [CtInvocationImpl][CtVariableReadImpl]event.getPointerId([CtInvocationImpl][CtVariableReadImpl]event.getActionIndex());
        [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerX0 = [CtInvocationImpl][CtVariableReadImpl]event.getX([CtInvocationImpl][CtVariableReadImpl]event.findPointerIndex([CtFieldReadImpl]mSecondFingerPointerId));
        [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerY0 = [CtInvocationImpl][CtVariableReadImpl]event.getY([CtInvocationImpl][CtVariableReadImpl]event.findPointerIndex([CtFieldReadImpl]mSecondFingerPointerId));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean updateSecondFinger([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int pointerIndex = [CtInvocationImpl][CtVariableReadImpl]event.findPointerIndex([CtFieldReadImpl]mSecondFingerPointerId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pointerIndex > [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerX = [CtInvocationImpl][CtVariableReadImpl]event.getX([CtVariableReadImpl]pointerIndex);
            [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerY = [CtInvocationImpl][CtVariableReadImpl]event.getY([CtVariableReadImpl]pointerIndex);
            [CtLocalVariableImpl][CtTypeReferenceImpl]float dx = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtBinaryOperatorImpl][CtFieldReadImpl]mSecondFingerX0 - [CtFieldReadImpl]mSecondFingerX);
            [CtLocalVariableImpl][CtTypeReferenceImpl]float dy = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtBinaryOperatorImpl][CtFieldReadImpl]mSecondFingerY0 - [CtFieldReadImpl]mSecondFingerY);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dx >= [CtFieldReadImpl]com.ichi2.anki.Whiteboard.TOUCH_TOLERANCE) || [CtBinaryOperatorImpl]([CtVariableReadImpl]dy >= [CtFieldReadImpl]com.ichi2.anki.Whiteboard.TOUCH_TOLERANCE)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerWithinTapTolerance = [CtLiteralImpl]false;
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtCommentImpl]// call this with an ACTION_POINTER_UP event to check whether it matches a tap of the second finger
    [CtCommentImpl]// if so, forward a click action and return true
    private [CtTypeReferenceImpl]boolean trySecondFingerClick([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mSecondFingerPointerId == [CtInvocationImpl][CtVariableReadImpl]event.getPointerId([CtInvocationImpl][CtVariableReadImpl]event.getActionIndex())) [CtBlockImpl]{
            [CtInvocationImpl]updateSecondFinger([CtVariableReadImpl]event);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer cardViewer = [CtInvocationImpl][CtFieldReadImpl]mCardViewer.get();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mSecondFingerWithinTapTolerance && [CtBinaryOperatorImpl]([CtVariableReadImpl]cardViewer != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cardViewer.tapOnCurrentCard([CtFieldReadImpl](([CtTypeReferenceImpl]int) (mSecondFingerX)), [CtFieldReadImpl](([CtTypeReferenceImpl]int) (mSecondFingerY)));
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtCommentImpl]// call this with an ACTION_MOVE event to check whether it is within the threshold for a tap of the second finger
    [CtCommentImpl]// in this case perform a scroll action
    private [CtTypeReferenceImpl]boolean trySecondFingerScroll([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]updateSecondFinger([CtVariableReadImpl]event) && [CtUnaryOperatorImpl](![CtFieldReadImpl]mSecondFingerWithinTapTolerance)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int dy = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]mSecondFingerY0 - [CtFieldReadImpl]mSecondFingerY));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer cardViewer = [CtInvocationImpl][CtFieldReadImpl]mCardViewer.get();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dy != [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]cardViewer != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]cardViewer.scrollCurrentCardBy([CtVariableReadImpl]dy);
                [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerX0 = [CtFieldReadImpl]mSecondFingerX;
                [CtAssignmentImpl][CtFieldWriteImpl]mSecondFingerY0 = [CtFieldReadImpl]mSecondFingerY;
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]android.graphics.Point getDisplayDimenions() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.Display display = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]android.view.WindowManager) ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.AnkiDroidApp.getInstance().getApplicationContext().getSystemService([CtTypeAccessImpl]Context.WINDOW_SERVICE))).getDefaultDisplay();
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.graphics.Point point = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.Point();
        [CtInvocationImpl][CtVariableReadImpl]display.getSize([CtVariableReadImpl]point);
        [CtReturnImpl]return [CtVariableReadImpl]point;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int redPenColor = [CtInvocationImpl][CtTypeAccessImpl]android.graphics.Color.parseColor([CtLiteralImpl]"#f44336");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int greenPenColor = [CtInvocationImpl][CtTypeAccessImpl]android.graphics.Color.parseColor([CtLiteralImpl]"#4caf50");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int bluePenColor = [CtInvocationImpl][CtTypeAccessImpl]android.graphics.Color.parseColor([CtLiteralImpl]"#2196f3");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int yellowPenColor = [CtInvocationImpl][CtTypeAccessImpl]android.graphics.Color.parseColor([CtLiteralImpl]"#ffeb3b");
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]view.getId()) {
            [CtCaseImpl]case [CtFieldReadImpl]R.id.pen_color_white :
                [CtInvocationImpl][CtFieldReadImpl]mPaint.setColor([CtTypeAccessImpl]Color.WHITE);
                [CtInvocationImpl][CtFieldReadImpl]colorPalette.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]R.id.pen_color_black :
                [CtInvocationImpl][CtFieldReadImpl]mPaint.setColor([CtTypeAccessImpl]Color.BLACK);
                [CtInvocationImpl][CtFieldReadImpl]colorPalette.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]R.id.pen_color_red :
                [CtInvocationImpl][CtFieldReadImpl]mPaint.setColor([CtVariableReadImpl]redPenColor);
                [CtInvocationImpl][CtFieldReadImpl]colorPalette.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]R.id.pen_color_green :
                [CtInvocationImpl][CtFieldReadImpl]mPaint.setColor([CtVariableReadImpl]greenPenColor);
                [CtInvocationImpl][CtFieldReadImpl]colorPalette.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]R.id.pen_color_blue :
                [CtInvocationImpl][CtFieldReadImpl]mPaint.setColor([CtVariableReadImpl]bluePenColor);
                [CtInvocationImpl][CtFieldReadImpl]colorPalette.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]R.id.pen_color_yellow :
                [CtInvocationImpl][CtFieldReadImpl]mPaint.setColor([CtVariableReadImpl]yellowPenColor);
                [CtInvocationImpl][CtFieldReadImpl]colorPalette.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtBreakImpl]break;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Keep a stack of all points and paths so that the last stroke can be undone
     * pop() removes the last stroke from the stack, and apply() redraws it to whiteboard.
     */
    private class UndoStack {
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Stack<[CtTypeReferenceImpl]android.graphics.Path> mPathStack = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Stack<>();

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Stack<[CtTypeReferenceImpl]android.graphics.PointF> mPointStack = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Stack<>();

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Stack<[CtTypeReferenceImpl]java.lang.Integer> mWhichStack = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Stack<>();

        [CtMethodImpl]public [CtTypeReferenceImpl]void add([CtParameterImpl][CtTypeReferenceImpl]android.graphics.Path path) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mPathStack.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.Path([CtVariableReadImpl]path));
            [CtInvocationImpl][CtFieldReadImpl]mWhichStack.add([CtLiteralImpl]0);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void add([CtParameterImpl][CtTypeReferenceImpl]float x, [CtParameterImpl][CtTypeReferenceImpl]float y) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mPointStack.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.PointF([CtVariableReadImpl]x, [CtVariableReadImpl]y));
            [CtInvocationImpl][CtFieldReadImpl]mWhichStack.add([CtLiteralImpl]1);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void clear() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mPathStack.clear();
            [CtInvocationImpl][CtFieldReadImpl]mPointStack.clear();
            [CtInvocationImpl][CtFieldReadImpl]mWhichStack.clear();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]int size() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mWhichStack.size();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void pop() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mWhichStack.size() == [CtLiteralImpl]0)[CtBlockImpl]
                [CtReturnImpl]return;

            [CtSwitchImpl]switch ([CtInvocationImpl][CtFieldReadImpl]mWhichStack.peek()) {
                [CtCaseImpl]case [CtLiteralImpl]0 :
                    [CtInvocationImpl][CtFieldReadImpl]mPathStack.pop();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]1 :
                    [CtInvocationImpl][CtFieldReadImpl]mPointStack.pop();
                    [CtBreakImpl]break;
            }
            [CtInvocationImpl][CtFieldReadImpl]mWhichStack.pop();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void apply() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mBitmap.eraseColor([CtLiteralImpl]0);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]android.graphics.Path path : [CtFieldReadImpl]mPathStack) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mCanvas.drawPath([CtVariableReadImpl]path, [CtFieldReadImpl]mPaint);
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]android.graphics.PointF point : [CtFieldReadImpl]mPointStack) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mCanvas.drawPoint([CtFieldReadImpl][CtVariableReadImpl]point.x, [CtFieldReadImpl][CtVariableReadImpl]point.y, [CtFieldReadImpl]mPaint);
            }
            [CtInvocationImpl]invalidate();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean empty() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mWhichStack.empty();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isCurrentlyDrawing() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mCurrentlyDrawing;
    }
}
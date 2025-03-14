package org.telegram.ui.Components.Paint;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.opengl.GLES20;
import androidx.core.graphics.ColorUtils;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.Utilities;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.Paint.Brush;
import org.telegram.ui.Components.Paint.Painting;
import org.telegram.ui.Components.Size;

/* loaded from: classes4.dex */
public class Painting {
    private Path activePath;
    private Shape activeShape;
    private RectF activeStrokeBounds;
    private Slice backupSlice;
    private Texture bitmapTexture;
    private Bitmap bluredBitmap;
    private Texture bluredTexture;
    private Brush brush;
    private ByteBuffer dataBuffer;
    private PaintingDelegate delegate;
    private float helperAlpha;
    private ValueAnimator helperAnimator;
    private float helperApplyAlpha;
    private ValueAnimator helperApplyAnimator;
    private Shape helperShape;
    private boolean helperShown;
    private int helperTexture;
    private Bitmap imageBitmap;
    private Paint imageBitmapPaint;
    private int imageBitmapRotation;
    private int paintTexture;
    private boolean paused;
    private float[] projection;
    private float[] renderProjection;
    private RenderView renderView;
    private int reusableFramebuffer;
    private Map<String, Shader> shaders;
    private Size size;
    private int suppressChangesCounter;
    private ByteBuffer textureBuffer;
    private ByteBuffer vertexBuffer;
    private HashMap<Integer, Texture> brushTextures = new HashMap<>();
    private int[] buffers = new int[1];
    private RenderState renderState = new RenderState();

    public interface PaintingDelegate {
        void contentChanged();

        DispatchQueue requestDispatchQueue();

        UndoStore requestUndoStore();
    }

    public static class PaintingData {
        public Bitmap bitmap;
        public ByteBuffer data;

        PaintingData(Bitmap bitmap, ByteBuffer byteBuffer) {
            this.bitmap = bitmap;
            this.data = byteBuffer;
        }
    }

    public Painting(Size size, Bitmap bitmap, int i) {
        this.size = size;
        this.imageBitmap = bitmap;
        this.imageBitmapRotation = i;
        this.dataBuffer = ByteBuffer.allocateDirect(((int) size.width) * ((int) size.height) * 4);
        Size size2 = this.size;
        this.projection = GLMatrix.LoadOrtho(0.0f, size2.width, 0.0f, size2.height, -1.0f, 1.0f);
        if (this.vertexBuffer == null) {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(32);
            this.vertexBuffer = allocateDirect;
            allocateDirect.order(ByteOrder.nativeOrder());
        }
        this.vertexBuffer.putFloat(0.0f);
        this.vertexBuffer.putFloat(0.0f);
        this.vertexBuffer.putFloat(this.size.width);
        this.vertexBuffer.putFloat(0.0f);
        this.vertexBuffer.putFloat(0.0f);
        this.vertexBuffer.putFloat(this.size.height);
        this.vertexBuffer.putFloat(this.size.width);
        this.vertexBuffer.putFloat(this.size.height);
        this.vertexBuffer.rewind();
        if (this.textureBuffer == null) {
            ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(32);
            this.textureBuffer = allocateDirect2;
            allocateDirect2.order(ByteOrder.nativeOrder());
            this.textureBuffer.putFloat(0.0f);
            this.textureBuffer.putFloat(0.0f);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.putFloat(0.0f);
            this.textureBuffer.putFloat(0.0f);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.rewind();
        }
    }

    public void setDelegate(PaintingDelegate paintingDelegate) {
        this.delegate = paintingDelegate;
    }

    public void setRenderView(RenderView renderView) {
        this.renderView = renderView;
    }

    public Size getSize() {
        return this.size;
    }

    public RectF getBounds() {
        Size size = this.size;
        return new RectF(0.0f, 0.0f, size.width, size.height);
    }

    private boolean isSuppressingChanges() {
        return this.suppressChangesCounter > 0;
    }

    private void beginSuppressingChanges() {
        this.suppressChangesCounter++;
    }

    private void endSuppressingChanges() {
        this.suppressChangesCounter--;
    }

    public void setBitmap(Bitmap bitmap) {
        if (this.bitmapTexture != null) {
            return;
        }
        this.bitmapTexture = new Texture(bitmap);
    }

    public void setHelperShape(final Shape shape) {
        if (this.helperApplyAnimator != null) {
            return;
        }
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$setHelperShape$2(shape);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setHelperShape$2(Shape shape) {
        if (shape != null && this.helperTexture == 0) {
            this.helperTexture = Texture.generateTexture(this.size);
        }
        if (this.helperShown != (shape != null)) {
            this.helperShown = shape != null;
            ValueAnimator valueAnimator = this.helperAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.helperAnimator = null;
            }
            float[] fArr = new float[2];
            fArr[0] = this.helperAlpha;
            fArr[1] = this.helperShown ? 1.0f : 0.0f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
            this.helperAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    Painting.this.lambda$setHelperShape$1(valueAnimator2);
                }
            });
            this.helperAnimator.addListener(new AnonymousClass1());
            this.helperAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            this.helperAnimator.start();
            this.helperShape = shape;
            PaintingDelegate paintingDelegate = this.delegate;
            if (paintingDelegate != null) {
                paintingDelegate.contentChanged();
            }
            if (this.helperShown) {
                BotWebViewVibrationEffect.SELECTION_CHANGE.vibrate();
                return;
            }
            return;
        }
        if (shape != this.helperShape) {
            this.helperShape = shape;
            PaintingDelegate paintingDelegate2 = this.delegate;
            if (paintingDelegate2 != null) {
                paintingDelegate2.contentChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setHelperShape$1(final ValueAnimator valueAnimator) {
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$setHelperShape$0(valueAnimator);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setHelperShape$0(ValueAnimator valueAnimator) {
        this.helperAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        PaintingDelegate paintingDelegate = this.delegate;
        if (paintingDelegate != null) {
            paintingDelegate.contentChanged();
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.Painting$1, reason: invalid class name */
    class AnonymousClass1 extends AnimatorListenerAdapter {
        AnonymousClass1() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            Painting.this.helperAnimator = null;
            Painting.this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Painting.AnonymousClass1.this.lambda$onAnimationEnd$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0() {
            if (Painting.this.delegate != null) {
                Painting.this.delegate.contentChanged();
            }
        }
    }

    public boolean applyHelperShape() {
        if (this.helperShape == null || !this.helperShown || this.helperTexture == 0) {
            return false;
        }
        ValueAnimator valueAnimator = this.helperApplyAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.helperApplyAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                Painting.this.lambda$applyHelperShape$4(valueAnimator2);
            }
        });
        this.helperApplyAnimator.addListener(new AnonymousClass2());
        this.helperApplyAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.helperApplyAnimator.setDuration(350L);
        this.helperApplyAnimator.start();
        BotWebViewVibrationEffect.IMPACT_RIGID.vibrate();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyHelperShape$4(final ValueAnimator valueAnimator) {
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$applyHelperShape$3(valueAnimator);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyHelperShape$3(ValueAnimator valueAnimator) {
        this.helperApplyAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        PaintingDelegate paintingDelegate = this.delegate;
        if (paintingDelegate != null) {
            paintingDelegate.contentChanged();
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.Painting$2, reason: invalid class name */
    class AnonymousClass2 extends AnimatorListenerAdapter {
        AnonymousClass2() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            Painting.this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Painting.AnonymousClass2.this.lambda$onAnimationEnd$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0() {
            if (Painting.this.helperShape == null) {
                Painting.this.helperApplyAnimator = null;
                return;
            }
            int currentColor = Painting.this.renderView.getCurrentColor();
            Painting painting = Painting.this;
            painting.paintStrokeInternal(painting.activePath, false, false);
            Painting painting2 = Painting.this;
            Slice commitPathInternal = painting2.commitPathInternal(painting2.activePath, currentColor, new RectF(Painting.this.activeStrokeBounds));
            Painting.this.clearStrokeInternal();
            Shape shape = Painting.this.helperShape;
            shape.getBounds(Painting.this.activeStrokeBounds = new RectF());
            Painting.this.restoreSliceInternal(Painting.this.commitShapeInternal(shape, currentColor, new RectF(Painting.this.activeStrokeBounds)), false);
            Painting.this.restoreSliceInternal(commitPathInternal, false);
            Painting.this.commitShapeInternal(shape, currentColor, null);
            Painting.this.helperShape = null;
            Painting.this.helperApplyAlpha = 0.0f;
            Painting.this.helperApplyAnimator = null;
        }
    }

    public void paintShape(final Shape shape, final Runnable runnable) {
        if (shape == null) {
            return;
        }
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$paintShape$5(shape, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$paintShape$5(Shape shape, Runnable runnable) {
        this.activeShape = shape;
        if (this.activeStrokeBounds == null) {
            this.activeStrokeBounds = new RectF();
        }
        this.activeShape.getBounds(this.activeStrokeBounds);
        PaintingDelegate paintingDelegate = this.delegate;
        if (paintingDelegate != null) {
            paintingDelegate.contentChanged();
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public void paintStroke(final Path path, final boolean z, final boolean z2, final Runnable runnable) {
        if (this.helperApplyAnimator != null) {
            return;
        }
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$paintStroke$6(path, z, z2, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$paintStroke$6(Path path, boolean z, boolean z2, Runnable runnable) {
        paintStrokeInternal(path, z, z2);
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void paintStrokeInternal(Path path, boolean z, boolean z2) {
        this.activePath = path;
        if (path == null) {
            return;
        }
        RectF rectF = null;
        GLES20.glBindFramebuffer(36160, getReusableFramebuffer());
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, getPaintTexture(), 0);
        Utils.HasGLError();
        if (GLES20.glCheckFramebufferStatus(36160) == 36053) {
            Size size = this.size;
            GLES20.glViewport(0, 0, (int) size.width, (int) size.height);
            if (z) {
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glClear(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM);
            }
            if (this.shaders == null) {
                return;
            }
            Brush brush = path.getBrush();
            Shader shader = this.shaders.get(brush.getShaderName(2));
            if (shader == null) {
                return;
            }
            GLES20.glUseProgram(shader.program);
            Texture texture = this.brushTextures.get(Integer.valueOf(brush.getStampResId()));
            if (texture == null) {
                texture = new Texture(brush.getStamp());
                this.brushTextures.put(Integer.valueOf(brush.getStampResId()), texture);
            }
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, texture.texture());
            GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.projection));
            GLES20.glUniform1i(shader.getUniform("texture"), 0);
            if (!z2) {
                this.renderState.viewportScale = this.renderView.getScaleX();
            } else {
                this.renderState.viewportScale = 1.0f;
            }
            rectF = Render.RenderPath(path, this.renderState, z2);
        }
        GLES20.glBindFramebuffer(36160, 0);
        PaintingDelegate paintingDelegate = this.delegate;
        if (paintingDelegate != null) {
            paintingDelegate.contentChanged();
        }
        RectF rectF2 = this.activeStrokeBounds;
        if (rectF2 != null) {
            rectF2.union(rectF);
        } else {
            this.activeStrokeBounds = rectF;
        }
    }

    public void commitShape(final Shape shape, final int i) {
        if (shape == null || this.shaders == null) {
            return;
        }
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$commitShape$7(shape, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitShape$7(Shape shape, int i) {
        commitShapeInternal(shape, i, this.activeStrokeBounds);
        this.activeStrokeBounds = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Slice commitShapeInternal(Shape shape, int i, RectF rectF) {
        Slice registerUndo = registerUndo(rectF);
        beginSuppressingChanges();
        GLES20.glBindFramebuffer(36160, getReusableFramebuffer());
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, getTexture(), 0);
        Size size = this.size;
        GLES20.glViewport(0, 0, (int) size.width, (int) size.height);
        Brush brush = shape.brush;
        if (brush == null) {
            brush = this.brush;
        }
        Shader shader = this.shaders.get(brush.getShaderName(1));
        if (shader == null) {
            return null;
        }
        GLES20.glUseProgram(shader.program);
        GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.projection));
        GLES20.glUniform1i(shader.getUniform("texture"), 0);
        GLES20.glUniform1i(shader.getUniform("mask"), 1);
        Shader.SetColorUniform(shader.getUniform("color"), i);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, getTexture());
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, getPaintTexture());
        if ((brush instanceof Brush.Blurer) && this.bluredTexture != null) {
            GLES20.glUniform1i(shader.getUniform("blured"), 2);
            GLES20.glActiveTexture(33986);
            GLES20.glBindTexture(3553, this.bluredTexture.texture());
        }
        if (brush instanceof Brush.Shape) {
            GLES20.glUniform1i(shader.getUniform("type"), shape.getType());
            int uniform = shader.getUniform("resolution");
            Size size2 = this.size;
            GLES20.glUniform2f(uniform, size2.width, size2.height);
            GLES20.glUniform2f(shader.getUniform("center"), shape.centerX, shape.centerY);
            GLES20.glUniform2f(shader.getUniform("radius"), shape.radiusX, shape.radiusY);
            GLES20.glUniform1f(shader.getUniform("thickness"), shape.thickness);
            GLES20.glUniform1f(shader.getUniform("rounding"), shape.rounding);
            GLES20.glUniform2f(shader.getUniform("middle"), shape.middleX, shape.middleY);
            GLES20.glUniform1f(shader.getUniform("rotation"), shape.rotation);
            GLES20.glUniform1i(shader.getUniform("fill"), shape.fill ? 1 : 0);
            GLES20.glUniform1f(shader.getUniform("arrowTriangleLength"), shape.arrowTriangleLength);
            GLES20.glUniform1i(shader.getUniform("composite"), 1);
            GLES20.glUniform1i(shader.getUniform("clear"), 0);
        }
        GLES20.glBlendFunc(1, 0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, (Buffer) this.vertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, (Buffer) this.textureBuffer);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glBindTexture(3553, getTexture());
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glBindFramebuffer(36160, 0);
        if (this.delegate != null && !isSuppressingChanges()) {
            this.delegate.contentChanged();
        }
        endSuppressingChanges();
        this.renderState.reset();
        this.helperApplyAlpha = 0.0f;
        this.helperShown = false;
        this.helperAlpha = 0.0f;
        this.helperShape = null;
        this.activePath = null;
        this.activeShape = null;
        return registerUndo;
    }

    public void commitPath(Path path, int i) {
        commitPath(path, i, true, null);
    }

    public void commitPath(final Path path, final int i, final boolean z, final Runnable runnable) {
        if (this.shaders == null || this.brush == null) {
            return;
        }
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$commitPath$8(path, i, z, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitPath$8(Path path, int i, boolean z, Runnable runnable) {
        commitPathInternal(path, i, z ? this.activeStrokeBounds : null);
        if (z) {
            this.activeStrokeBounds = null;
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Slice commitPathInternal(Path path, int i, RectF rectF) {
        PaintingDelegate paintingDelegate;
        Slice registerUndo = registerUndo(rectF);
        beginSuppressingChanges();
        GLES20.glBindFramebuffer(36160, getReusableFramebuffer());
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, getTexture(), 0);
        Size size = this.size;
        GLES20.glViewport(0, 0, (int) size.width, (int) size.height);
        Brush brush = this.brush;
        if (path != null) {
            brush = path.getBrush();
        }
        Shader shader = this.shaders.get(brush.getShaderName(1));
        if (shader == null) {
            return null;
        }
        GLES20.glUseProgram(shader.program);
        GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.projection));
        GLES20.glUniform1i(shader.getUniform("texture"), 0);
        GLES20.glUniform1i(shader.getUniform("mask"), 1);
        Shader.SetColorUniform(shader.getUniform("color"), ColorUtils.setAlphaComponent(i, (int) (Color.alpha(i) * brush.getOverrideAlpha())));
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, getTexture());
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, getPaintTexture());
        if ((brush instanceof Brush.Blurer) && this.bluredTexture != null) {
            GLES20.glUniform1i(shader.getUniform("blured"), 2);
            GLES20.glActiveTexture(33986);
            GLES20.glBindTexture(3553, this.bluredTexture.texture());
        }
        GLES20.glBlendFunc(1, 0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, (Buffer) this.vertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, (Buffer) this.textureBuffer);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glBindTexture(3553, getTexture());
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glBindFramebuffer(36160, 0);
        if (!isSuppressingChanges() && (paintingDelegate = this.delegate) != null) {
            paintingDelegate.contentChanged();
        }
        endSuppressingChanges();
        this.renderState.reset();
        this.activePath = null;
        this.activeShape = null;
        return registerUndo;
    }

    public void clearStroke() {
        clearStroke(null);
    }

    public void clearStroke(final Runnable runnable) {
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$clearStroke$9(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearStroke$9(Runnable runnable) {
        clearStrokeInternal();
        if (runnable != null) {
            runnable.run();
        }
    }

    public void clearShape() {
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$clearShape$10();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearShape$10() {
        this.activeShape = null;
        PaintingDelegate paintingDelegate = this.delegate;
        if (paintingDelegate != null) {
            paintingDelegate.contentChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearStrokeInternal() {
        GLES20.glBindFramebuffer(36160, getReusableFramebuffer());
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, getPaintTexture(), 0);
        Utils.HasGLError();
        if (GLES20.glCheckFramebufferStatus(36160) == 36053) {
            Size size = this.size;
            GLES20.glViewport(0, 0, (int) size.width, (int) size.height);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glClear(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM);
        }
        GLES20.glBindFramebuffer(36160, 0);
        PaintingDelegate paintingDelegate = this.delegate;
        if (paintingDelegate != null) {
            paintingDelegate.contentChanged();
        }
        this.renderState.reset();
        this.activeStrokeBounds = null;
        this.activePath = null;
        this.helperApplyAlpha = 0.0f;
    }

    private Slice registerUndo(RectF rectF) {
        if (rectF == null || !rectF.setIntersect(rectF, getBounds())) {
            return null;
        }
        final Slice slice = new Slice(getPaintingData(rectF, true).data, rectF, this.delegate.requestDispatchQueue());
        this.delegate.requestUndoStore().registerUndo(UUID.randomUUID(), new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$registerUndo$11(slice);
            }
        });
        return slice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: restoreSlice, reason: merged with bridge method [inline-methods] */
    public void lambda$registerUndo$11(final Slice slice) {
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$restoreSlice$12(slice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreSlice$12(Slice slice) {
        restoreSliceInternal(slice, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreSliceInternal(Slice slice, boolean z) {
        PaintingDelegate paintingDelegate;
        if (slice == null) {
            return;
        }
        ByteBuffer data = slice.getData();
        GLES20.glBindTexture(3553, getTexture());
        GLES20.glTexSubImage2D(3553, 0, slice.getX(), slice.getY(), slice.getWidth(), slice.getHeight(), 6408, 5121, data);
        if (!isSuppressingChanges() && (paintingDelegate = this.delegate) != null) {
            paintingDelegate.contentChanged();
        }
        if (z) {
            slice.cleanResources();
        }
    }

    public void setRenderProjection(float[] fArr) {
        this.renderProjection = fArr;
    }

    public void render() {
        if (this.shaders == null) {
            return;
        }
        if (this.activePath != null) {
            renderBlitPath(getPaintTexture(), this.activePath, (1.0f - (this.helperAlpha * 0.5f)) - (this.helperApplyAlpha * 0.5f));
        } else if (this.activeShape != null) {
            renderBlitShape(getTexture(), getPaintTexture(), this.activeShape, 1.0f);
        } else {
            renderBlit(getTexture(), 1.0f);
        }
        int i = this.helperTexture;
        if (i == 0 || this.helperShape == null || this.helperAlpha <= 0.0f) {
            return;
        }
        renderBlitShape(i, getPaintTexture(), this.helperShape, (this.helperAlpha * 0.5f) + (this.helperApplyAlpha * 0.5f));
    }

    private void renderBlitShape(int i, int i2, Shape shape, float f) {
        Shader shader;
        if (shape == null) {
            return;
        }
        Brush brush = this.brush;
        Brush.Shape shape2 = shape.brush;
        if (shape2 != null && i == this.helperTexture) {
            brush = shape2;
        }
        if (brush == null || this.renderView == null || (shader = this.shaders.get(brush.getShaderName(0))) == null) {
            return;
        }
        GLES20.glUseProgram(shader.program);
        GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.renderProjection));
        GLES20.glUniform1i(shader.getUniform("texture"), 0);
        GLES20.glUniform1i(shader.getUniform("mask"), 1);
        Shader.SetColorUniform(shader.getUniform("color"), ColorUtils.setAlphaComponent(this.renderView.getCurrentColor(), (int) (Color.alpha(r6) * f)));
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, i);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, i2);
        if (brush instanceof Brush.Shape) {
            GLES20.glUniform1i(shader.getUniform("type"), ((Brush.Shape) brush).getShapeShaderType());
            int uniform = shader.getUniform("resolution");
            Size size = this.size;
            GLES20.glUniform2f(uniform, size.width, size.height);
            GLES20.glUniform2f(shader.getUniform("center"), shape.centerX, shape.centerY);
            GLES20.glUniform2f(shader.getUniform("radius"), shape.radiusX, shape.radiusY);
            GLES20.glUniform1f(shader.getUniform("thickness"), shape.thickness);
            GLES20.glUniform1f(shader.getUniform("rounding"), shape.rounding);
            GLES20.glUniform2f(shader.getUniform("middle"), shape.middleX, shape.middleY);
            GLES20.glUniform1f(shader.getUniform("rotation"), shape.rotation);
            GLES20.glUniform1i(shader.getUniform("fill"), shape.fill ? 1 : 0);
            GLES20.glUniform1f(shader.getUniform("arrowTriangleLength"), shape.arrowTriangleLength);
            GLES20.glUniform1i(shader.getUniform("composite"), 0);
            GLES20.glUniform1i(shader.getUniform("clear"), shape == this.helperShape ? 1 : 0);
        }
        GLES20.glBlendFunc(1, 771);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, (Buffer) this.vertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, (Buffer) this.textureBuffer);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glDrawArrays(5, 0, 4);
        Utils.HasGLError();
    }

    private void renderBlitPath(int i, Path path, float f) {
        if (path == null) {
            return;
        }
        Brush brush = path.getBrush();
        if (brush == null) {
            brush = this.brush;
        }
        Shader shader = this.shaders.get(brush.getShaderName(0));
        if (shader == null) {
            return;
        }
        GLES20.glUseProgram(shader.program);
        GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.renderProjection));
        GLES20.glUniform1i(shader.getUniform("texture"), 0);
        GLES20.glUniform1i(shader.getUniform("mask"), 1);
        Shader.SetColorUniform(shader.getUniform("color"), ColorUtils.setAlphaComponent(path.getColor(), (int) (Color.alpha(r4) * brush.getOverrideAlpha() * f)));
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, getTexture());
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, i);
        if ((brush instanceof Brush.Blurer) && this.bluredTexture != null) {
            GLES20.glUniform1i(shader.getUniform("blured"), 2);
            GLES20.glActiveTexture(33986);
            GLES20.glBindTexture(3553, this.bluredTexture.texture());
        }
        GLES20.glBlendFunc(1, 771);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, (Buffer) this.vertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, (Buffer) this.textureBuffer);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glDrawArrays(5, 0, 4);
        Utils.HasGLError();
    }

    private void renderBlit(int i, float f) {
        Shader shader = this.shaders.get("blit");
        if (i == 0 || shader == null) {
            return;
        }
        GLES20.glUseProgram(shader.program);
        GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.renderProjection));
        GLES20.glUniform1i(shader.getUniform("texture"), 0);
        GLES20.glUniform1f(shader.getUniform("alpha"), f);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, i);
        GLES20.glBlendFunc(1, 771);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, (Buffer) this.vertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, (Buffer) this.textureBuffer);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glDrawArrays(5, 0, 4);
        Utils.HasGLError();
    }

    public PaintingData getPaintingData(RectF rectF, boolean z) {
        PaintingData paintingData;
        int i = (int) rectF.left;
        int i2 = (int) rectF.top;
        int width = (int) rectF.width();
        int height = (int) rectF.height();
        GLES20.glGenFramebuffers(1, this.buffers, 0);
        int i3 = this.buffers[0];
        GLES20.glBindFramebuffer(36160, i3);
        GLES20.glGenTextures(1, this.buffers, 0);
        int i4 = this.buffers[0];
        GLES20.glBindTexture(3553, i4);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9728);
        GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, null);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, i4, 0);
        Size size = this.size;
        GLES20.glViewport(0, 0, (int) size.width, (int) size.height);
        Map<String, Shader> map = this.shaders;
        if (map == null) {
            return null;
        }
        Shader shader = map.get(z ? "nonPremultipliedBlit" : "blit");
        if (shader == null) {
            return null;
        }
        GLES20.glUseProgram(shader.program);
        Matrix matrix = new Matrix();
        matrix.preTranslate(-i, -i2);
        GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(GLMatrix.MultiplyMat4f(this.projection, GLMatrix.LoadGraphicsMatrix(matrix))));
        GLES20.glUniform1i(shader.getUniform("texture"), 0);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, getTexture());
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM);
        GLES20.glBlendFunc(1, 0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, (Buffer) this.vertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, (Buffer) this.textureBuffer);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glDrawArrays(5, 0, 4);
        this.dataBuffer.limit(width * height * 4);
        GLES20.glReadPixels(0, 0, width, height, 6408, 5121, this.dataBuffer);
        if (z) {
            paintingData = new PaintingData(null, this.dataBuffer);
        } else {
            Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.dataBuffer);
            paintingData = new PaintingData(createBitmap, null);
        }
        this.dataBuffer.rewind();
        int[] iArr = this.buffers;
        iArr[0] = i3;
        GLES20.glDeleteFramebuffers(1, iArr, 0);
        int[] iArr2 = this.buffers;
        iArr2[0] = i4;
        GLES20.glDeleteTextures(1, iArr2, 0);
        return paintingData;
    }

    public void setBrush(Brush brush) {
        Bitmap bitmap;
        Bitmap resultBitmap;
        this.brush = brush;
        if (!(brush instanceof Brush.Blurer) || (bitmap = this.imageBitmap) == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = this.imageBitmap.getHeight();
        int i = this.imageBitmapRotation;
        if (i == 90 || i == 270 || i == -90) {
            height = width;
            width = height;
        }
        if (this.bluredBitmap == null) {
            this.bluredBitmap = Bitmap.createBitmap((int) (width / 8.0f), (int) (height / 8.0f), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(this.bluredBitmap);
        canvas.save();
        canvas.scale(0.125f, 0.125f);
        if (this.imageBitmapPaint != null) {
            this.imageBitmapPaint = new Paint(1);
        }
        canvas.save();
        canvas.rotate(this.imageBitmapRotation);
        int i2 = this.imageBitmapRotation;
        if (i2 == 90) {
            canvas.translate(0.0f, -width);
        } else if (i2 == 180) {
            canvas.translate(-width, -height);
        } else if (i2 == 270) {
            canvas.translate(-height, 0.0f);
        }
        canvas.drawBitmap(this.imageBitmap, 0.0f, 0.0f, this.imageBitmapPaint);
        canvas.restore();
        RenderView renderView = this.renderView;
        if (renderView != null && (resultBitmap = renderView.getResultBitmap()) != null) {
            canvas.scale(width / resultBitmap.getWidth(), height / resultBitmap.getHeight());
            canvas.drawBitmap(resultBitmap, 0.0f, 0.0f, this.imageBitmapPaint);
            resultBitmap.recycle();
        }
        Utilities.stackBlurBitmap(this.bluredBitmap, (int) 8.0f);
        Texture texture = this.bluredTexture;
        if (texture != null) {
            texture.cleanResources(false);
        }
        this.bluredTexture = new Texture(this.bluredBitmap);
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void onPause(final Runnable runnable) {
        this.renderView.performInContext(new Runnable() { // from class: org.telegram.ui.Components.Paint.Painting$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                Painting.this.lambda$onPause$13(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPause$13(Runnable runnable) {
        this.paused = true;
        this.backupSlice = new Slice(getPaintingData(getBounds(), true).data, getBounds(), this.delegate.requestDispatchQueue());
        cleanResources(false);
        if (runnable != null) {
            runnable.run();
        }
    }

    public void onResume() {
        lambda$registerUndo$11(this.backupSlice);
        this.backupSlice = null;
        this.paused = false;
    }

    public void cleanResources(boolean z) {
        int i = this.reusableFramebuffer;
        if (i != 0) {
            int[] iArr = this.buffers;
            iArr[0] = i;
            GLES20.glDeleteFramebuffers(1, iArr, 0);
            this.reusableFramebuffer = 0;
        }
        this.bitmapTexture.cleanResources(z);
        int i2 = this.paintTexture;
        if (i2 != 0) {
            int[] iArr2 = this.buffers;
            iArr2[0] = i2;
            GLES20.glDeleteTextures(1, iArr2, 0);
            this.paintTexture = 0;
        }
        for (Texture texture : this.brushTextures.values()) {
            if (texture != null) {
                texture.cleanResources(true);
            }
        }
        this.brushTextures.clear();
        int i3 = this.helperTexture;
        if (i3 != 0) {
            int[] iArr3 = this.buffers;
            iArr3[0] = i3;
            GLES20.glDeleteTextures(1, iArr3, 0);
            this.helperTexture = 0;
        }
        Texture texture2 = this.bluredTexture;
        if (texture2 != null) {
            texture2.cleanResources(true);
        }
        Map<String, Shader> map = this.shaders;
        if (map != null) {
            Iterator<Shader> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().cleanResources();
            }
            this.shaders = null;
        }
    }

    private int getReusableFramebuffer() {
        if (this.reusableFramebuffer == 0) {
            int[] iArr = new int[1];
            GLES20.glGenFramebuffers(1, iArr, 0);
            this.reusableFramebuffer = iArr[0];
            Utils.HasGLError();
        }
        return this.reusableFramebuffer;
    }

    private int getTexture() {
        Texture texture = this.bitmapTexture;
        if (texture != null) {
            return texture.texture();
        }
        return 0;
    }

    private int getPaintTexture() {
        if (this.paintTexture == 0) {
            this.paintTexture = Texture.generateTexture(this.size);
        }
        return this.paintTexture;
    }

    public void setupShaders() {
        this.shaders = ShaderSet.setup();
    }
}

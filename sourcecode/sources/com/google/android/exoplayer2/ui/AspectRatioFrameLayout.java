package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class AspectRatioFrameLayout extends FrameLayout {
    private AspectRatioListener aspectRatioListener;
    private final AspectRatioUpdateDispatcher aspectRatioUpdateDispatcher;
    private boolean drawingReady;
    private Matrix matrix;
    private int resizeMode;
    private int rotation;
    private float videoAspectRatio;

    public interface AspectRatioListener {
        void onAspectRatioUpdated(float f, float f2, boolean z);
    }

    public AspectRatioFrameLayout(Context context) {
        super(context);
        this.matrix = new Matrix();
        this.resizeMode = 0;
        this.aspectRatioUpdateDispatcher = new AspectRatioUpdateDispatcher();
    }

    public void setAspectRatio(float f, int i) {
        if (this.videoAspectRatio != f) {
            this.videoAspectRatio = f;
            this.rotation = i;
            requestLayout();
        }
    }

    public void setAspectRatioListener(AspectRatioListener aspectRatioListener) {
        this.aspectRatioListener = aspectRatioListener;
    }

    public int getResizeMode() {
        return this.resizeMode;
    }

    public void setResizeMode(int i) {
        if (this.resizeMode != i) {
            this.resizeMode = i;
            requestLayout();
        }
    }

    public void setDrawingReady(boolean z) {
        if (this.drawingReady == z) {
            return;
        }
        this.drawingReady = z;
    }

    public float getAspectRatio() {
        return this.videoAspectRatio;
    }

    public int getVideoRotation() {
        return this.rotation;
    }

    public boolean isDrawingReady() {
        return this.drawingReady;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        float f;
        float f2;
        super.onMeasure(i, i2);
        if (this.videoAspectRatio <= 0.0f) {
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float f3 = measuredWidth;
        float f4 = measuredHeight;
        float f5 = f3 / f4;
        float f6 = (this.videoAspectRatio / f5) - 1.0f;
        if (Math.abs(f6) <= 0.01f) {
            this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f5, false);
            return;
        }
        int i3 = this.resizeMode;
        if (i3 != 0) {
            if (i3 != 1) {
                if (i3 == 2) {
                    f = this.videoAspectRatio;
                } else if (i3 != 3) {
                    if (i3 == 4) {
                        if (f6 > 0.0f) {
                            f = this.videoAspectRatio;
                        } else {
                            f2 = this.videoAspectRatio;
                        }
                    }
                } else if (f6 <= 0.0f) {
                    f2 = this.videoAspectRatio;
                } else {
                    f = this.videoAspectRatio;
                }
                measuredWidth = (int) (f4 * f);
            } else {
                f2 = this.videoAspectRatio;
            }
            measuredHeight = (int) (f3 / f2);
        } else if (f6 > 0.0f) {
            f2 = this.videoAspectRatio;
            measuredHeight = (int) (f3 / f2);
        } else {
            f = this.videoAspectRatio;
            measuredWidth = (int) (f4 * f);
        }
        this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f5, true);
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt instanceof TextureView) {
                this.matrix.reset();
                float width = getWidth() / 2;
                float height = getHeight() / 2;
                this.matrix.postRotate(this.rotation, width, height);
                int i5 = this.rotation;
                if (i5 == 90 || i5 == 270) {
                    float height2 = getHeight() / getWidth();
                    this.matrix.postScale(1.0f / height2, height2, width, height);
                }
                ((TextureView) childAt).setTransform(this.matrix);
                return;
            }
        }
    }

    private final class AspectRatioUpdateDispatcher implements Runnable {
        private boolean aspectRatioMismatch;
        private boolean isScheduled;
        private float naturalAspectRatio;
        private float targetAspectRatio;

        private AspectRatioUpdateDispatcher() {
        }

        public void scheduleUpdate(float f, float f2, boolean z) {
            this.targetAspectRatio = f;
            this.naturalAspectRatio = f2;
            this.aspectRatioMismatch = z;
            if (this.isScheduled) {
                return;
            }
            this.isScheduled = true;
            AspectRatioFrameLayout.this.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.isScheduled = false;
            if (AspectRatioFrameLayout.this.aspectRatioListener == null) {
                return;
            }
            AspectRatioFrameLayout.this.aspectRatioListener.onAspectRatioUpdated(this.targetAspectRatio, this.naturalAspectRatio, this.aspectRatioMismatch);
        }
    }
}

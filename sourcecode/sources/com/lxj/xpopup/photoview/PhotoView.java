package com.lxj.xpopup.photoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes.dex */
public class PhotoView extends AppCompatImageView {
    public PhotoViewAttacher attacher;
    private ImageView.ScaleType pendingScaleType;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }

    private void init() {
        this.attacher = new PhotoViewAttacher(this);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        ImageView.ScaleType scaleType = this.pendingScaleType;
        if (scaleType != null) {
            setScaleType(scaleType);
            this.pendingScaleType = null;
        }
    }

    public PhotoViewAttacher getAttacher() {
        return this.attacher;
    }

    @Override // android.widget.ImageView
    public ImageView.ScaleType getScaleType() {
        return this.attacher.getScaleType();
    }

    @Override // android.widget.ImageView
    public Matrix getImageMatrix() {
        return this.attacher.getImageMatrix();
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener l) {
        this.attacher.setOnLongClickListener(l);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener l) {
        this.attacher.setOnClickListener(l);
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher == null) {
            this.pendingScaleType = scaleType;
        } else {
            photoViewAttacher.setScaleType(scaleType);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // android.widget.ImageView
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean frame = super.setFrame(l, t, r, b);
        if (frame) {
            this.attacher.update();
        }
        return frame;
    }

    public void setRotationTo(float rotationDegree) {
        this.attacher.setRotationTo(rotationDegree);
    }

    public void setRotationBy(float rotationDegree) {
        this.attacher.setRotationBy(rotationDegree);
    }

    public void setZoomable(boolean zoomable) {
        this.attacher.setZoomable(zoomable);
    }

    public RectF getDisplayRect() {
        return this.attacher.getDisplayRect();
    }

    public float getMinimumScale() {
        return this.attacher.getMinimumScale();
    }

    public float getMediumScale() {
        return this.attacher.getMediumScale();
    }

    public float getMaximumScale() {
        return this.attacher.getMaximumScale();
    }

    public float getScale() {
        return this.attacher.getScale();
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.attacher.setAllowParentInterceptOnEdge(allow);
    }

    public void setMinimumScale(float minimumScale) {
        this.attacher.setMinimumScale(minimumScale);
    }

    public void setMediumScale(float mediumScale) {
        this.attacher.setMediumScale(mediumScale);
    }

    public void setMaximumScale(float maximumScale) {
        this.attacher.setMaximumScale(maximumScale);
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        this.attacher.setOnMatrixChangeListener(listener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.attacher.setOnPhotoTapListener(listener);
    }

    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener listener) {
        this.attacher.setOnOutsidePhotoTapListener(listener);
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.attacher.setOnViewTapListener(listener);
    }

    public void setOnViewDragListener(OnViewDragListener listener) {
        this.attacher.setOnViewDragListener(listener);
    }

    public void setScale(float scale) {
        this.attacher.setScale(scale);
    }

    public void setZoomTransitionDuration(int milliseconds) {
        this.attacher.setZoomTransitionDuration(milliseconds);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.attacher.setOnDoubleTapListener(onDoubleTapListener);
    }

    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangedListener) {
        this.attacher.setOnScaleChangeListener(onScaleChangedListener);
    }

    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        this.attacher.setOnSingleFlingListener(onSingleFlingListener);
    }
}

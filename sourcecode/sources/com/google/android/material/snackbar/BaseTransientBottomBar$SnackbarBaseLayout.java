package com.google.android.material.snackbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes.dex */
public class BaseTransientBottomBar$SnackbarBaseLayout extends FrameLayout {
    private static final View.OnTouchListener consumeAllTouchListener = new View.OnTouchListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar$SnackbarBaseLayout.1
        @Override // android.view.View.OnTouchListener
        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };
    private final float actionTextColorAlpha;
    private int animationMode;
    private final float backgroundOverlayColorAlpha;
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    private BaseTransientBottomBar$OnAttachStateChangeListener onAttachStateChangeListener;
    private BaseTransientBottomBar$OnLayoutChangeListener onLayoutChangeListener;

    protected BaseTransientBottomBar$SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, 0, 0), attributeSet);
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        if (obtainStyledAttributes.hasValue(R$styleable.SnackbarLayout_elevation)) {
            ViewCompat.setElevation(this, obtainStyledAttributes.getDimensionPixelSize(r1, 0));
        }
        this.animationMode = obtainStyledAttributes.getInt(R$styleable.SnackbarLayout_animationMode, 0);
        this.backgroundOverlayColorAlpha = obtainStyledAttributes.getFloat(R$styleable.SnackbarLayout_backgroundOverlayColorAlpha, 1.0f);
        setBackgroundTintList(MaterialResources.getColorStateList(context2, obtainStyledAttributes, R$styleable.SnackbarLayout_backgroundTint));
        setBackgroundTintMode(ViewUtils.parseTintMode(obtainStyledAttributes.getInt(R$styleable.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
        this.actionTextColorAlpha = obtainStyledAttributes.getFloat(R$styleable.SnackbarLayout_actionTextColorAlpha, 1.0f);
        obtainStyledAttributes.recycle();
        setOnTouchListener(consumeAllTouchListener);
        setFocusable(true);
        if (getBackground() == null) {
            ViewCompat.setBackground(this, createThemedBackground());
        }
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable != null && this.backgroundTint != null) {
            drawable = DrawableCompat.wrap(drawable.mutate());
            DrawableCompat.setTintList(drawable, this.backgroundTint);
            DrawableCompat.setTintMode(drawable, this.backgroundTintMode);
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundTintList(ColorStateList colorStateList) {
        this.backgroundTint = colorStateList;
        if (getBackground() != null) {
            Drawable wrap = DrawableCompat.wrap(getBackground().mutate());
            DrawableCompat.setTintList(wrap, colorStateList);
            DrawableCompat.setTintMode(wrap, this.backgroundTintMode);
            if (wrap != getBackground()) {
                super.setBackgroundDrawable(wrap);
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        this.backgroundTintMode = mode;
        if (getBackground() != null) {
            Drawable wrap = DrawableCompat.wrap(getBackground().mutate());
            DrawableCompat.setTintMode(wrap, mode);
            if (wrap != getBackground()) {
                super.setBackgroundDrawable(wrap);
            }
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        setOnTouchListener(onClickListener != null ? null : consumeAllTouchListener);
        super.setOnClickListener(onClickListener);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        BaseTransientBottomBar$OnLayoutChangeListener baseTransientBottomBar$OnLayoutChangeListener = this.onLayoutChangeListener;
        if (baseTransientBottomBar$OnLayoutChangeListener != null) {
            baseTransientBottomBar$OnLayoutChangeListener.onLayoutChange(this, i, i2, i3, i4);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        BaseTransientBottomBar$OnAttachStateChangeListener baseTransientBottomBar$OnAttachStateChangeListener = this.onAttachStateChangeListener;
        if (baseTransientBottomBar$OnAttachStateChangeListener != null) {
            baseTransientBottomBar$OnAttachStateChangeListener.onViewAttachedToWindow(this);
        }
        ViewCompat.requestApplyInsets(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BaseTransientBottomBar$OnAttachStateChangeListener baseTransientBottomBar$OnAttachStateChangeListener = this.onAttachStateChangeListener;
        if (baseTransientBottomBar$OnAttachStateChangeListener != null) {
            baseTransientBottomBar$OnAttachStateChangeListener.onViewDetachedFromWindow(this);
        }
    }

    void setOnLayoutChangeListener(BaseTransientBottomBar$OnLayoutChangeListener baseTransientBottomBar$OnLayoutChangeListener) {
        this.onLayoutChangeListener = baseTransientBottomBar$OnLayoutChangeListener;
    }

    void setOnAttachStateChangeListener(BaseTransientBottomBar$OnAttachStateChangeListener baseTransientBottomBar$OnAttachStateChangeListener) {
        this.onAttachStateChangeListener = baseTransientBottomBar$OnAttachStateChangeListener;
    }

    int getAnimationMode() {
        return this.animationMode;
    }

    void setAnimationMode(int i) {
        this.animationMode = i;
    }

    float getBackgroundOverlayColorAlpha() {
        return this.backgroundOverlayColorAlpha;
    }

    float getActionTextColorAlpha() {
        return this.actionTextColorAlpha;
    }

    private Drawable createThemedBackground() {
        float dimension = getResources().getDimension(R$dimen.mtrl_snackbar_background_corner_radius);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(dimension);
        gradientDrawable.setColor(MaterialColors.layer(this, R$attr.colorSurface, R$attr.colorOnSurface, getBackgroundOverlayColorAlpha()));
        if (this.backgroundTint != null) {
            Drawable wrap = DrawableCompat.wrap(gradientDrawable);
            DrawableCompat.setTintList(wrap, this.backgroundTint);
            return wrap;
        }
        return DrawableCompat.wrap(gradientDrawable);
    }
}

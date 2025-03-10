package com.youth.banner.transformer;

import android.view.View;

/* loaded from: classes.dex */
public class RotateDownPageTransformer extends BasePageTransformer {
    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate;

    public RotateDownPageTransformer() {
        this.mMaxRotate = DEFAULT_MAX_ROTATE;
    }

    public RotateDownPageTransformer(float f) {
        this.mMaxRotate = DEFAULT_MAX_ROTATE;
        this.mMaxRotate = f;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View view, float f) {
        if (f < -1.0f) {
            view.setRotation(this.mMaxRotate * (-1.0f));
            view.setPivotX(view.getWidth());
            view.setPivotY(view.getHeight());
        } else if (f > 1.0f) {
            view.setRotation(this.mMaxRotate);
            view.setPivotX(view.getWidth() * 0);
            view.setPivotY(view.getHeight());
        } else if (f < 0.0f) {
            view.setPivotX(view.getWidth() * (((-f) * 0.5f) + 0.5f));
            view.setPivotY(view.getHeight());
            view.setRotation(this.mMaxRotate * f);
        } else {
            view.setPivotX(view.getWidth() * 0.5f * (1.0f - f));
            view.setPivotY(view.getHeight());
            view.setRotation(this.mMaxRotate * f);
        }
    }
}

package com.youth.banner.transformer;

import android.view.View;

/* loaded from: classes.dex */
public class ScaleInTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private float mMinScale;

    public ScaleInTransformer() {
        this.mMinScale = DEFAULT_MIN_SCALE;
    }

    public ScaleInTransformer(float f) {
        this.mMinScale = DEFAULT_MIN_SCALE;
        this.mMinScale = f;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View view, float f) {
        int width = view.getWidth();
        view.setPivotY(view.getHeight() / 2);
        view.setPivotX(width / 2);
        if (f < -1.0f) {
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
            view.setPivotX(width);
            return;
        }
        if (f > 1.0f) {
            view.setPivotX(0.0f);
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
        } else {
            if (f < 0.0f) {
                float f2 = this.mMinScale;
                float f3 = ((f + 1.0f) * (1.0f - f2)) + f2;
                view.setScaleX(f3);
                view.setScaleY(f3);
                view.setPivotX(width * (((-f) * 0.5f) + 0.5f));
                return;
            }
            float f4 = 1.0f - f;
            float f5 = this.mMinScale;
            float f6 = ((1.0f - f5) * f4) + f5;
            view.setScaleX(f6);
            view.setScaleY(f6);
            view.setPivotX(width * f4 * 0.5f);
        }
    }
}

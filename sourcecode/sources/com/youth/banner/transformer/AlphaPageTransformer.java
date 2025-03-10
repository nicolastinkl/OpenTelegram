package com.youth.banner.transformer;

import android.view.View;

/* loaded from: classes.dex */
public class AlphaPageTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha;

    public AlphaPageTransformer() {
        this.mMinAlpha = 0.5f;
    }

    public AlphaPageTransformer(float f) {
        this.mMinAlpha = 0.5f;
        this.mMinAlpha = f;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View view, float f) {
        view.setScaleX(0.999f);
        if (f < -1.0f) {
            view.setAlpha(this.mMinAlpha);
            return;
        }
        if (f > 1.0f) {
            view.setAlpha(this.mMinAlpha);
        } else if (f < 0.0f) {
            float f2 = this.mMinAlpha;
            view.setAlpha(f2 + ((1.0f - f2) * (f + 1.0f)));
        } else {
            float f3 = this.mMinAlpha;
            view.setAlpha(f3 + ((1.0f - f3) * (1.0f - f)));
        }
    }
}

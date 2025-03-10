package com.lxj.xpopup.animator;

import android.animation.FloatEvaluator;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class BlurAnimator extends PopupAnimator {
    public Bitmap decorBitmap;
    public boolean hasShadowBg;
    public int shadowColor;

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
    }

    public BlurAnimator(View target, int shadowColor) {
        super(target, 0);
        new FloatEvaluator();
        this.hasShadowBg = false;
        this.shadowColor = shadowColor;
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.targetView.getResources(), XPopupUtils.renderScriptBlur(this.targetView.getContext(), this.decorBitmap, 10.0f, true));
        if (this.hasShadowBg) {
            bitmapDrawable.setColorFilter(this.shadowColor, PorterDuff.Mode.SRC_OVER);
        }
        this.targetView.setBackground(bitmapDrawable);
    }
}

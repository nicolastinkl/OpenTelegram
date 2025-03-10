package com.lxj.xpopup.animator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/* loaded from: classes.dex */
public class ShadowBgAnimator extends PopupAnimator {
    public ArgbEvaluator argbEvaluator;
    public boolean isZeroDuration;
    public int shadowColor;
    public int startColor;

    public ShadowBgAnimator(View target, int animationDuration, int shadowColor) {
        super(target, animationDuration);
        this.argbEvaluator = new ArgbEvaluator();
        this.startColor = 0;
        this.isZeroDuration = false;
        this.shadowColor = shadowColor;
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.targetView.setBackgroundColor(this.startColor);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        ValueAnimator ofObject = ValueAnimator.ofObject(this.argbEvaluator, Integer.valueOf(this.startColor), Integer.valueOf(this.shadowColor));
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.animator.ShadowBgAnimator.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ShadowBgAnimator.this.targetView.setBackgroundColor(((Integer) animation.getAnimatedValue()).intValue());
            }
        });
        ofObject.setInterpolator(new FastOutSlowInInterpolator());
        ofObject.setDuration(this.isZeroDuration ? 0L : this.animationDuration).start();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        ValueAnimator ofObject = ValueAnimator.ofObject(this.argbEvaluator, Integer.valueOf(this.shadowColor), Integer.valueOf(this.startColor));
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.animator.ShadowBgAnimator.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ShadowBgAnimator.this.targetView.setBackgroundColor(((Integer) animation.getAnimatedValue()).intValue());
            }
        });
        observerAnimator(ofObject);
        ofObject.setInterpolator(new FastOutSlowInInterpolator());
        ofObject.setDuration(this.isZeroDuration ? 0L : this.animationDuration).start();
    }

    public void applyColorValue(float val) {
        this.targetView.setBackgroundColor(Integer.valueOf(calculateBgColor(val)).intValue());
    }

    public int calculateBgColor(float fraction) {
        return ((Integer) this.argbEvaluator.evaluate(fraction, Integer.valueOf(this.startColor), Integer.valueOf(this.shadowColor))).intValue();
    }
}

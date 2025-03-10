package com.lxj.xpopup.animator;

import android.view.View;

/* loaded from: classes.dex */
public class EmptyAnimator extends PopupAnimator {
    public EmptyAnimator(View target, int animationDuration) {
        super(target, animationDuration);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.targetView.setAlpha(0.0f);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        this.targetView.animate().alpha(1.0f).setDuration(this.animationDuration).withLayer().start();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        observerAnimator(this.targetView.animate().alpha(0.0f).setDuration(this.animationDuration).withLayer()).start();
    }
}

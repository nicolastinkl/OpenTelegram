package com.lxj.xpopup.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.lxj.xpopup.enums.PopupAnimation;

/* loaded from: classes.dex */
public abstract class PopupAnimator {
    protected boolean animating;
    public int animationDuration;
    public boolean hasInit;
    public PopupAnimation popupAnimation;
    public View targetView;

    public abstract void animateDismiss();

    public abstract void animateShow();

    public abstract void initAnimator();

    public PopupAnimator(View target, int animationDuration) {
        this(target, animationDuration, null);
    }

    public PopupAnimator(View target, int animationDuration, PopupAnimation popupAnimation) {
        this.animating = false;
        this.hasInit = false;
        this.animationDuration = 0;
        this.targetView = target;
        this.animationDuration = animationDuration;
        this.popupAnimation = popupAnimation;
    }

    protected ValueAnimator observerAnimator(ValueAnimator animator) {
        animator.removeAllListeners();
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.animator.PopupAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                PopupAnimator.this.animating = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                PopupAnimator.this.animating = false;
            }
        });
        return animator;
    }

    protected ViewPropertyAnimator observerAnimator(ViewPropertyAnimator animator) {
        animator.setListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.animator.PopupAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                PopupAnimator.this.animating = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                PopupAnimator.this.animating = false;
            }
        });
        return animator;
    }
}

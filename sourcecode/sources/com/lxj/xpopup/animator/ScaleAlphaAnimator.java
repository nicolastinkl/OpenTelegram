package com.lxj.xpopup.animator;

import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.lxj.xpopup.enums.PopupAnimation;

/* loaded from: classes.dex */
public class ScaleAlphaAnimator extends PopupAnimator {
    float startScale;

    public ScaleAlphaAnimator(View target, int animationDuration, PopupAnimation popupAnimation) {
        super(target, animationDuration, popupAnimation);
        this.startScale = 0.95f;
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.targetView.setScaleX(this.startScale);
        this.targetView.setScaleY(this.startScale);
        this.targetView.setAlpha(0.0f);
        this.targetView.post(new Runnable() { // from class: com.lxj.xpopup.animator.ScaleAlphaAnimator.1
            @Override // java.lang.Runnable
            public void run() {
                ScaleAlphaAnimator.this.applyPivot();
            }
        });
    }

    /* renamed from: com.lxj.xpopup.animator.ScaleAlphaAnimator$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.ScaleAlphaFromCenter.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightTop.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightBottom.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyPivot() {
        int i = AnonymousClass3.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupAnimation.ordinal()];
        if (i == 1) {
            this.targetView.setPivotX(r0.getMeasuredWidth() / 2.0f);
            this.targetView.setPivotY(r0.getMeasuredHeight() / 2.0f);
            return;
        }
        if (i == 2) {
            this.targetView.setPivotX(0.0f);
            this.targetView.setPivotY(0.0f);
            return;
        }
        if (i == 3) {
            this.targetView.setPivotX(r0.getMeasuredWidth());
            this.targetView.setPivotY(0.0f);
        } else if (i == 4) {
            this.targetView.setPivotX(0.0f);
            this.targetView.setPivotY(r0.getMeasuredHeight());
        } else {
            if (i != 5) {
                return;
            }
            this.targetView.setPivotX(r0.getMeasuredWidth());
            this.targetView.setPivotY(r0.getMeasuredHeight());
        }
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        this.targetView.post(new Runnable() { // from class: com.lxj.xpopup.animator.ScaleAlphaAnimator.2
            @Override // java.lang.Runnable
            public void run() {
                ScaleAlphaAnimator.this.targetView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(ScaleAlphaAnimator.this.animationDuration).setInterpolator(new OvershootInterpolator(1.0f)).start();
            }
        });
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        observerAnimator(this.targetView.animate().scaleX(this.startScale).scaleY(this.startScale).alpha(0.0f).setDuration(this.animationDuration).setInterpolator(new FastOutSlowInInterpolator())).start();
    }
}

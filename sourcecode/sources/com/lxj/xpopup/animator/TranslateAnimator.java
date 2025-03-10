package com.lxj.xpopup.animator;

import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.lxj.xpopup.enums.PopupAnimation;

/* loaded from: classes.dex */
public class TranslateAnimator extends PopupAnimator {
    public float endTranslationX;
    public float endTranslationY;
    public float startTranslationX;
    public float startTranslationY;

    public TranslateAnimator(View target, int animationDuration, PopupAnimation popupAnimation) {
        super(target, animationDuration, popupAnimation);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        if (this.hasInit) {
            return;
        }
        this.endTranslationX = this.targetView.getTranslationX();
        this.endTranslationY = this.targetView.getTranslationY();
        applyTranslation();
        this.startTranslationX = this.targetView.getTranslationX();
        this.startTranslationY = this.targetView.getTranslationY();
    }

    /* renamed from: com.lxj.xpopup.animator.TranslateAnimator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.TranslateFromLeft.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromRight.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void applyTranslation() {
        int i = AnonymousClass1.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupAnimation.ordinal()];
        if (i == 1) {
            this.targetView.setTranslationX((-r0.getRight()) + this.targetView.getTranslationX());
            return;
        }
        if (i == 2) {
            this.targetView.setTranslationY((-r0.getBottom()) + this.targetView.getTranslationY());
        } else if (i == 3) {
            this.targetView.setTranslationX((((View) r0.getParent()).getMeasuredWidth() - this.targetView.getLeft()) + this.targetView.getTranslationX());
        } else {
            if (i != 4) {
                return;
            }
            this.targetView.setTranslationY((((View) r0.getParent()).getMeasuredHeight() - this.targetView.getTop()) + this.targetView.getTranslationY());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0033  */
    @Override // com.lxj.xpopup.animator.PopupAnimator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void animateShow() {
        /*
            r3 = this;
            int[] r0 = com.lxj.xpopup.animator.TranslateAnimator.AnonymousClass1.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation
            com.lxj.xpopup.enums.PopupAnimation r1 = r3.popupAnimation
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 1
            if (r0 == r1) goto L25
            r1 = 2
            if (r0 == r1) goto L18
            r1 = 3
            if (r0 == r1) goto L25
            r1 = 4
            if (r0 == r1) goto L18
            r0 = 0
            goto L31
        L18:
            android.view.View r0 = r3.targetView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            float r1 = r3.endTranslationY
            android.view.ViewPropertyAnimator r0 = r0.translationY(r1)
            goto L31
        L25:
            android.view.View r0 = r3.targetView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            float r1 = r3.endTranslationX
            android.view.ViewPropertyAnimator r0 = r0.translationX(r1)
        L31:
            if (r0 == 0) goto L4a
            androidx.interpolator.view.animation.FastOutSlowInInterpolator r1 = new androidx.interpolator.view.animation.FastOutSlowInInterpolator
            r1.<init>()
            android.view.ViewPropertyAnimator r0 = r0.setInterpolator(r1)
            int r1 = r3.animationDuration
            long r1 = (long) r1
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r1)
            android.view.ViewPropertyAnimator r0 = r0.withLayer()
            r0.start()
        L4a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "start: "
            r0.append(r1)
            android.view.View r1 = r3.targetView
            float r1 = r1.getTranslationY()
            r0.append(r1)
            java.lang.String r1 = "  endy: "
            r0.append(r1)
            float r1 = r3.endTranslationY
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "part"
            android.util.Log.e(r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.animator.TranslateAnimator.animateShow():void");
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = null;
        int i = AnonymousClass1.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupAnimation.ordinal()];
        if (i == 1) {
            this.startTranslationX = -this.targetView.getRight();
            viewPropertyAnimator = this.targetView.animate().translationX(this.startTranslationX);
        } else if (i == 2) {
            this.startTranslationY = -this.targetView.getBottom();
            viewPropertyAnimator = this.targetView.animate().translationY(this.startTranslationY);
        } else if (i == 3) {
            this.startTranslationX = ((View) this.targetView.getParent()).getMeasuredWidth() - this.targetView.getLeft();
            viewPropertyAnimator = this.targetView.animate().translationX(this.startTranslationX);
        } else if (i == 4) {
            this.startTranslationY = ((View) this.targetView.getParent()).getMeasuredHeight() - this.targetView.getTop();
            viewPropertyAnimator = this.targetView.animate().translationY(this.startTranslationY);
        }
        if (viewPropertyAnimator != null) {
            observerAnimator(viewPropertyAnimator.setInterpolator(new FastOutSlowInInterpolator()).setDuration((long) (this.animationDuration * 0.8d)).withLayer()).start();
        }
    }
}

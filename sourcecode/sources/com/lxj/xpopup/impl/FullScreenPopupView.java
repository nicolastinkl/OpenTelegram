package com.lxj.xpopup.impl;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.enums.PopupAnimation;

/* loaded from: classes.dex */
public class FullScreenPopupView extends BasePopupView {
    public ArgbEvaluator argbEvaluator;
    protected View contentView;
    int currColor;
    protected FrameLayout fullPopupContainer;
    private Paint paint;
    protected Rect shadowRect;
    private TranslateAnimator translateAnimator;

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return R$layout._xpopup_fullscreen_popup_view;
    }

    protected void addInnerContent() {
        View inflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.fullPopupContainer, false);
        this.contentView = inflate;
        this.fullPopupContainer.addView(inflate);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.fullPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        getPopupContentView().setTranslationX(this.popupInfo.offsetX);
        getPopupContentView().setTranslationY(this.popupInfo.offsetY);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        this.paint.setColor(this.currColor);
        Rect rect = new Rect(0, 0, getMeasuredWidth(), getStatusBarHeight());
        this.shadowRect = rect;
        canvas.drawRect(rect, this.paint);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doShowAnimation() {
        super.doShowAnimation();
        doStatusBarColorTransform(true);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doDismissAnimation() {
        super.doDismissAnimation();
        doStatusBarColorTransform(false);
    }

    private void doStatusBarColorTransform(boolean isShow) {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        ArgbEvaluator argbEvaluator = this.argbEvaluator;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(isShow ? 0 : getStatusBarBgColor());
        objArr[1] = Integer.valueOf(isShow ? getStatusBarBgColor() : 0);
        ValueAnimator ofObject = ValueAnimator.ofObject(argbEvaluator, objArr);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.impl.FullScreenPopupView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                FullScreenPopupView.this.currColor = ((Integer) animation.getAnimatedValue()).intValue();
                FullScreenPopupView.this.postInvalidate();
            }
        });
        ofObject.setDuration(getAnimationDuration()).start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        if (this.translateAnimator == null) {
            this.translateAnimator = new TranslateAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.TranslateFromBottom);
        }
        return this.translateAnimator;
    }

    @Override // com.lxj.xpopup.core.BasePopupView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        if (this.popupInfo != null && this.translateAnimator != null) {
            getPopupContentView().setTranslationX(this.translateAnimator.startTranslationX);
            getPopupContentView().setTranslationY(this.translateAnimator.startTranslationY);
            this.translateAnimator.hasInit = true;
        }
        super.onDetachedFromWindow();
    }
}

package com.lxj.xpopup.core;

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
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.widget.PopupDrawerLayout;

/* loaded from: classes.dex */
public abstract class DrawerPopupView extends BasePopupView {
    public ArgbEvaluator argbEvaluator;
    int currColor;
    int defaultColor;
    protected FrameLayout drawerContentContainer;
    protected PopupDrawerLayout drawerLayout;
    float mFraction;
    Paint paint;
    Rect shadowRect;

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return null;
    }

    protected void addInnerContent() {
        View inflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.drawerContentContainer, false);
        this.drawerContentContainer.addView(inflate);
        ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
        if (this.popupInfo != null) {
            layoutParams.height = -1;
            if (getPopupWidth() > 0) {
                layoutParams.width = getPopupWidth();
            }
            if (getMaxWidth() > 0) {
                layoutParams.width = Math.min(layoutParams.width, getMaxWidth());
            }
            inflate.setLayoutParams(layoutParams);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doMeasure() {
        super.doMeasure();
        View childAt = this.drawerContentContainer.getChildAt(0);
        if (childAt == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
        if (this.popupInfo != null) {
            layoutParams.height = -1;
            if (getPopupWidth() > 0) {
                layoutParams.width = getPopupWidth();
            }
            if (getMaxWidth() > 0) {
                layoutParams.width = Math.min(layoutParams.width, getMaxWidth());
            }
            childAt.setLayoutParams(layoutParams);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public View getPopupImplView() {
        return this.drawerContentContainer.getChildAt(0);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_drawer_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.drawerContentContainer.getChildCount() == 0) {
            addInnerContent();
        }
        this.drawerLayout.isDismissOnTouchOutside = this.popupInfo.isDismissOnTouchOutside.booleanValue();
        this.drawerLayout.setOnCloseListener(new PopupDrawerLayout.OnCloseListener() { // from class: com.lxj.xpopup.core.DrawerPopupView.1
            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onOpen() {
            }

            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onClose() {
                XPopupCallback xPopupCallback;
                DrawerPopupView.this.beforeDismiss();
                DrawerPopupView drawerPopupView = DrawerPopupView.this;
                PopupInfo popupInfo = drawerPopupView.popupInfo;
                if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
                    xPopupCallback.beforeDismiss(drawerPopupView);
                }
                DrawerPopupView.this.doAfterDismiss();
            }

            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onDrag(int x, float fraction, boolean isToLeft) {
                DrawerPopupView drawerPopupView = DrawerPopupView.this;
                PopupInfo popupInfo = drawerPopupView.popupInfo;
                if (popupInfo == null) {
                    return;
                }
                XPopupCallback xPopupCallback = popupInfo.xPopupCallback;
                if (xPopupCallback != null) {
                    xPopupCallback.onDrag(drawerPopupView, x, fraction, isToLeft);
                }
                DrawerPopupView drawerPopupView2 = DrawerPopupView.this;
                drawerPopupView2.mFraction = fraction;
                if (drawerPopupView2.popupInfo.hasShadowBg.booleanValue()) {
                    DrawerPopupView.this.shadowBgAnimator.applyColorValue(fraction);
                }
                DrawerPopupView.this.postInvalidate();
            }
        });
        getPopupImplView().setTranslationX(this.popupInfo.offsetX);
        getPopupImplView().setTranslationY(this.popupInfo.offsetY);
        PopupDrawerLayout popupDrawerLayout = this.drawerLayout;
        PopupPosition popupPosition = this.popupInfo.popupPosition;
        if (popupPosition == null) {
            popupPosition = PopupPosition.Left;
        }
        popupDrawerLayout.setDrawerPosition(popupPosition);
        PopupDrawerLayout popupDrawerLayout2 = this.drawerLayout;
        popupDrawerLayout2.enableDrag = this.popupInfo.enableDrag;
        popupDrawerLayout2.getChildAt(0).setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.core.DrawerPopupView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DrawerPopupView drawerPopupView = DrawerPopupView.this;
                PopupInfo popupInfo = drawerPopupView.popupInfo;
                if (popupInfo != null) {
                    XPopupCallback xPopupCallback = popupInfo.xPopupCallback;
                    if (xPopupCallback != null) {
                        xPopupCallback.onClickOutside(drawerPopupView);
                    }
                    if (DrawerPopupView.this.popupInfo.isDismissOnTouchOutside.booleanValue()) {
                        DrawerPopupView.this.dismiss();
                    }
                }
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        if (this.shadowRect == null) {
            this.shadowRect = new Rect(0, 0, getMeasuredWidth(), getStatusBarHeight());
        }
        this.paint.setColor(((Integer) this.argbEvaluator.evaluate(this.mFraction, Integer.valueOf(this.defaultColor), Integer.valueOf(getStatusBarBgColor()))).intValue());
        canvas.drawRect(this.shadowRect, this.paint);
    }

    public void doStatusBarColorTransform(boolean isShow) {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        ArgbEvaluator argbEvaluator = this.argbEvaluator;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(isShow ? 0 : getStatusBarBgColor());
        objArr[1] = Integer.valueOf(isShow ? getStatusBarBgColor() : 0);
        ValueAnimator ofObject = ValueAnimator.ofObject(argbEvaluator, objArr);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.core.DrawerPopupView.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                DrawerPopupView.this.currColor = ((Integer) animation.getAnimatedValue()).intValue();
                DrawerPopupView.this.postInvalidate();
            }
        });
        ofObject.setDuration(getAnimationDuration()).start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        this.drawerLayout.open();
        doStatusBarColorTransform(true);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doAfterDismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.autoOpenSoftInput.booleanValue()) {
            KeyboardUtils.hideSoftInput(this);
        }
        this.handler.removeCallbacks(this.doAfterDismissTask);
        this.handler.postDelayed(this.doAfterDismissTask, 0L);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        PopupStatus popupStatus = this.popupStatus;
        PopupStatus popupStatus2 = PopupStatus.Dismissing;
        if (popupStatus == popupStatus2) {
            return;
        }
        this.popupStatus = popupStatus2;
        if (popupInfo.autoOpenSoftInput.booleanValue()) {
            KeyboardUtils.hideSoftInput(this);
        }
        clearFocus();
        doStatusBarColorTransform(false);
        this.drawerLayout.close();
    }
}

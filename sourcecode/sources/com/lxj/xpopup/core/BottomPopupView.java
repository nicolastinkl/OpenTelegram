package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.animator.BlurAnimator;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.SmartDragLayout;

/* loaded from: classes.dex */
public class BottomPopupView extends BasePopupView {
    protected SmartDragLayout bottomPopupContainer;
    private TranslateAnimator translateAnimator;

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return 0;
    }

    public BottomPopupView(Context context) {
        super(context);
        this.bottomPopupContainer = (SmartDragLayout) findViewById(R$id.bottomPopupContainer);
    }

    protected void addInnerContent() {
        this.bottomPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.bottomPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_bottom_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.bottomPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        this.bottomPopupContainer.setDuration(getAnimationDuration());
        this.bottomPopupContainer.enableDrag(this.popupInfo.enableDrag);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo.enableDrag) {
            popupInfo.popupAnimation = null;
            getPopupImplView().setTranslationX(this.popupInfo.offsetX);
            getPopupImplView().setTranslationY(this.popupInfo.offsetY);
        } else {
            getPopupContentView().setTranslationX(this.popupInfo.offsetX);
            getPopupContentView().setTranslationY(this.popupInfo.offsetY);
        }
        this.bottomPopupContainer.dismissOnTouchOutside(this.popupInfo.isDismissOnTouchOutside.booleanValue());
        this.bottomPopupContainer.isThreeDrag(this.popupInfo.isThreeDrag);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), null);
        this.bottomPopupContainer.setOnCloseListener(new SmartDragLayout.OnCloseListener() { // from class: com.lxj.xpopup.core.BottomPopupView.1
            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onOpen() {
            }

            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onClose() {
                XPopupCallback xPopupCallback;
                BottomPopupView.this.beforeDismiss();
                BottomPopupView bottomPopupView = BottomPopupView.this;
                PopupInfo popupInfo2 = bottomPopupView.popupInfo;
                if (popupInfo2 != null && (xPopupCallback = popupInfo2.xPopupCallback) != null) {
                    xPopupCallback.beforeDismiss(bottomPopupView);
                }
                BottomPopupView.this.doAfterDismiss();
            }

            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onDrag(int value, float percent, boolean isScrollUp) {
                BottomPopupView bottomPopupView = BottomPopupView.this;
                PopupInfo popupInfo2 = bottomPopupView.popupInfo;
                if (popupInfo2 == null) {
                    return;
                }
                XPopupCallback xPopupCallback = popupInfo2.xPopupCallback;
                if (xPopupCallback != null) {
                    xPopupCallback.onDrag(bottomPopupView, value, percent, isScrollUp);
                }
                if (!BottomPopupView.this.popupInfo.hasShadowBg.booleanValue() || BottomPopupView.this.popupInfo.hasBlurBg.booleanValue()) {
                    return;
                }
                BottomPopupView bottomPopupView2 = BottomPopupView.this;
                bottomPopupView2.setBackgroundColor(bottomPopupView2.shadowBgAnimator.calculateBgColor(percent));
            }
        });
        this.bottomPopupContainer.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.core.BottomPopupView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BottomPopupView bottomPopupView = BottomPopupView.this;
                PopupInfo popupInfo2 = bottomPopupView.popupInfo;
                if (popupInfo2 != null) {
                    XPopupCallback xPopupCallback = popupInfo2.xPopupCallback;
                    if (xPopupCallback != null) {
                        xPopupCallback.onClickOutside(bottomPopupView);
                    }
                    BottomPopupView bottomPopupView2 = BottomPopupView.this;
                    if (bottomPopupView2.popupInfo.isDismissOnTouchOutside != null) {
                        bottomPopupView2.dismiss();
                    }
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doMeasure() {
        super.doMeasure();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), null);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        BlurAnimator blurAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.enableDrag) {
            if (popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
                blurAnimator.animateShow();
            }
            this.bottomPopupContainer.open();
            return;
        }
        super.doShowAnimation();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
        BlurAnimator blurAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.enableDrag) {
            if (popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
                blurAnimator.animateDismiss();
            }
            this.bottomPopupContainer.close();
            return;
        }
        super.doDismissAnimation();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doAfterDismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.enableDrag) {
            if (popupInfo.autoOpenSoftInput.booleanValue()) {
                KeyboardUtils.hideSoftInput(this);
            }
            this.handler.removeCallbacks(this.doAfterDismissTask);
            this.handler.postDelayed(this.doAfterDismissTask, 0L);
            return;
        }
        super.doAfterDismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        if (this.popupInfo == null) {
            return null;
        }
        if (this.translateAnimator == null) {
            this.translateAnimator = new TranslateAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.TranslateFromBottom);
        }
        if (this.popupInfo.enableDrag) {
            return null;
        }
        return this.translateAnimator;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.enableDrag) {
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
            this.bottomPopupContainer.close();
            return;
        }
        super.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && !popupInfo.enableDrag && this.translateAnimator != null) {
            getPopupContentView().setTranslationX(this.translateAnimator.startTranslationX);
            getPopupContentView().setTranslationY(this.translateAnimator.startTranslationY);
            this.translateAnimator.hasInit = true;
        }
        super.onDetachedFromWindow();
    }
}

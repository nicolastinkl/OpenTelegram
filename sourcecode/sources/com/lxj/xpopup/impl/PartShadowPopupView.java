package com.lxj.xpopup.impl;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.OnClickOutsideListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.PartShadowContainer;

/* loaded from: classes.dex */
public abstract class PartShadowPopupView extends BasePopupView {
    protected PartShadowContainer attachPopupContainer;
    private boolean hasInit;
    public boolean isShowUp;

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_partshadow_popup_view;
    }

    protected void addInnerContent() {
        this.attachPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.attachPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        if (this.attachPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        if (this.popupInfo.hasShadowBg.booleanValue()) {
            this.shadowBgAnimator.targetView = getPopupContentView();
        }
        getPopupImplView().setTranslationX(this.popupInfo.offsetX);
        getPopupImplView().setTranslationY(this.popupInfo.offsetY);
        getPopupImplView().setAlpha(0.0f);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                PartShadowPopupView.this.doAttach();
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doMeasure() {
        super.doMeasure();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.2
            @Override // java.lang.Runnable
            public void run() {
                PartShadowPopupView.this.doAttach();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAndStartAnimation() {
        if (this.hasInit) {
            return;
        }
        this.hasInit = true;
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onDismiss() {
        super.onDismiss();
        this.hasInit = false;
    }

    public void doAttach() {
        if (this.popupInfo.atView == null) {
            throw new IllegalArgumentException("atView() must be called before show()！");
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getPopupContentView().getLayoutParams();
        Rect atViewRect = this.popupInfo.getAtViewRect();
        int height = atViewRect.top + (atViewRect.height() / 2);
        View popupImplView = getPopupImplView();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) popupImplView.getLayoutParams();
        if ((height > getMeasuredHeight() / 2 || this.popupInfo.popupPosition == PopupPosition.Top) && this.popupInfo.popupPosition != PopupPosition.Bottom) {
            marginLayoutParams.height = atViewRect.top;
            this.isShowUp = true;
            layoutParams.gravity = 80;
            if (getMaxHeight() > 0) {
                layoutParams.height = Math.min(popupImplView.getMeasuredHeight(), getMaxHeight());
            }
        } else {
            int measuredHeight = getMeasuredHeight();
            int i = atViewRect.bottom;
            marginLayoutParams.height = measuredHeight - i;
            this.isShowUp = false;
            marginLayoutParams.topMargin = i;
            layoutParams.gravity = 48;
            if (getMaxHeight() > 0) {
                layoutParams.height = Math.min(popupImplView.getMeasuredHeight(), getMaxHeight());
            }
        }
        getPopupContentView().setLayoutParams(marginLayoutParams);
        popupImplView.setLayoutParams(layoutParams);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                PartShadowPopupView.this.initAndStartAnimation();
                PartShadowPopupView.this.getPopupImplView().setAlpha(1.0f);
            }
        });
        PartShadowContainer partShadowContainer = this.attachPopupContainer;
        partShadowContainer.notDismissArea = this.popupInfo.notDismissWhenTouchInArea;
        partShadowContainer.setOnClickOutsideListener(new OnClickOutsideListener() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.4
            @Override // com.lxj.xpopup.interfaces.OnClickOutsideListener
            public void onClickOutside() {
                if (PartShadowPopupView.this.popupInfo.isDismissOnTouchOutside.booleanValue()) {
                    PartShadowPopupView.this.dismiss();
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupImplView(), getAnimationDuration(), this.isShowUp ? PopupAnimation.TranslateFromBottom : PopupAnimation.TranslateFromTop);
    }
}

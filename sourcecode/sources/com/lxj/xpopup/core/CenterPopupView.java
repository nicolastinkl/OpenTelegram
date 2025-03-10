package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lxj.xpopup.R$color;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class CenterPopupView extends BasePopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    protected FrameLayout centerPopupContainer;
    protected View contentView;

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return 0;
    }

    public CenterPopupView(Context context) {
        super(context);
        this.centerPopupContainer = (FrameLayout) findViewById(R$id.centerPopupContainer);
    }

    protected void addInnerContent() {
        View inflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.centerPopupContainer, false);
        this.contentView = inflate;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) inflate.getLayoutParams();
        layoutParams.gravity = 17;
        this.centerPopupContainer.addView(this.contentView, layoutParams);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_center_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.centerPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        getPopupContentView().setTranslationX(this.popupInfo.offsetX);
        getPopupContentView().setTranslationY(this.popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), null);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doMeasure() {
        super.doMeasure();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), null);
    }

    protected void applyTheme() {
        if (this.bindLayoutId == 0) {
            if (this.popupInfo.isDarkTheme) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        this.centerPopupContainer.setBackground(XPopupUtils.createDrawable(getResources().getColor(R$color._xpopup_dark_color), this.popupInfo.borderRadius));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        this.centerPopupContainer.setBackground(XPopupUtils.createDrawable(getResources().getColor(R$color._xpopup_light_color), this.popupInfo.borderRadius));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getMaxWidth() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        int i = popupInfo.maxWidth;
        return i == 0 ? (int) (XPopupUtils.getAppWidth(getContext()) * 0.85f) : i;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScaleAlphaFromCenter);
    }
}

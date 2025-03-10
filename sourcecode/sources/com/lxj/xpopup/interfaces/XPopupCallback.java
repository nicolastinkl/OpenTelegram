package com.lxj.xpopup.interfaces;

import com.lxj.xpopup.core.BasePopupView;

/* loaded from: classes.dex */
public interface XPopupCallback {
    void beforeDismiss(BasePopupView popupView);

    void beforeShow(BasePopupView popupView);

    boolean onBackPressed(BasePopupView popupView);

    void onClickOutside(BasePopupView popupView);

    void onCreated(BasePopupView popupView);

    void onDismiss(BasePopupView popupView);

    void onDrag(BasePopupView popupView, int value, float percent, boolean upOrLeft);

    void onKeyBoardStateChanged(BasePopupView popupView, int height);

    void onShow(BasePopupView popupView);
}

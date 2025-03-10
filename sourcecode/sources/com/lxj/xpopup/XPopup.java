package com.lxj.xpopup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

/* loaded from: classes.dex */
public class XPopup {
    private static int primaryColor = Color.parseColor("#121212");
    private static int animationDuration = 300;
    private static int statusBarBgColor = Color.parseColor("#55000000");
    private static int navigationBarColor = 0;
    private static int shadowBgColor = Color.parseColor("#7F000000");
    public static int isLightStatusBar = 0;
    public static int isLightNavigationBar = 0;
    public static PointF longClickPoint = null;

    public static int getShadowBgColor() {
        return shadowBgColor;
    }

    public static int getStatusBarBgColor() {
        return statusBarBgColor;
    }

    public static int getNavigationBarColor() {
        return navigationBarColor;
    }

    public static int getPrimaryColor() {
        return primaryColor;
    }

    public static int getAnimationDuration() {
        return animationDuration;
    }

    public static class Builder {
        private Context context;
        private final PopupInfo popupInfo = new PopupInfo();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder dismissOnBackPressed(Boolean isDismissOnBackPressed) {
            this.popupInfo.isDismissOnBackPressed = isDismissOnBackPressed;
            return this;
        }

        public Builder hasShadowBg(Boolean hasShadowBg) {
            this.popupInfo.hasShadowBg = hasShadowBg;
            return this;
        }

        public Builder atView(View atView) {
            this.popupInfo.atView = atView;
            return this;
        }

        public Builder watchView(View watchView) {
            watchView.setOnTouchListener(new View.OnTouchListener() { // from class: com.lxj.xpopup.XPopup.Builder.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() != 0) {
                        return false;
                    }
                    Builder.this.popupInfo.touchPoint = new PointF(event.getRawX(), event.getRawY());
                    return false;
                }
            });
            return this;
        }

        public Builder popupAnimation(PopupAnimation popupAnimation) {
            this.popupInfo.popupAnimation = popupAnimation;
            return this;
        }

        public Builder autoOpenSoftInput(Boolean autoOpenSoftInput) {
            this.popupInfo.autoOpenSoftInput = autoOpenSoftInput;
            return this;
        }

        public Builder isLightNavigationBar(boolean isLightNavigationBar) {
            this.popupInfo.isLightNavigationBar = isLightNavigationBar ? 1 : -1;
            return this;
        }

        public Builder offsetY(int offsetY) {
            this.popupInfo.offsetY = offsetY;
            return this;
        }

        public Builder isCenterHorizontal(boolean isCenterHorizontal) {
            this.popupInfo.isCenterHorizontal = isCenterHorizontal;
            return this;
        }

        public CenterListPopupView asCenterList(CharSequence title, String[] data, int[] iconIds, int checkedPosition, OnSelectListener selectListener, int bindLayoutId, int bindItemLayoutId) {
            CenterListPopupView onSelectListener = new CenterListPopupView(this.context, bindLayoutId, bindItemLayoutId).setStringData(title, data, iconIds).setCheckedPosition(checkedPosition).setOnSelectListener(selectListener);
            onSelectListener.popupInfo = this.popupInfo;
            return onSelectListener;
        }

        public CenterListPopupView asCenterList(CharSequence title, String[] data, int[] iconIds, int checkedPosition, OnSelectListener selectListener) {
            return asCenterList(title, data, iconIds, checkedPosition, selectListener, 0, 0);
        }

        public CenterListPopupView asCenterList(CharSequence title, String[] data, OnSelectListener selectListener) {
            return asCenterList(title, data, null, -1, selectListener);
        }

        public LoadingPopupView asLoading(CharSequence title, int bindLayoutId, LoadingPopupView.Style style) {
            LoadingPopupView style2 = new LoadingPopupView(this.context, bindLayoutId).setTitle(title).setStyle(style);
            style2.popupInfo = this.popupInfo;
            return style2;
        }

        public LoadingPopupView asLoading(CharSequence title, LoadingPopupView.Style style) {
            return asLoading(title, 0, style);
        }

        public AttachListPopupView asAttachList(String[] data, int[] iconIds, OnSelectListener selectListener, int bindLayoutId, int bindItemLayoutId, int contentGravity) {
            AttachListPopupView onSelectListener = new AttachListPopupView(this.context, bindLayoutId, bindItemLayoutId).setStringData(data, iconIds).setContentGravity(contentGravity).setOnSelectListener(selectListener);
            onSelectListener.popupInfo = this.popupInfo;
            return onSelectListener;
        }

        public AttachListPopupView asAttachList(String[] data, int[] iconIds, OnSelectListener selectListener) {
            return asAttachList(data, iconIds, selectListener, 0, 0, 17);
        }

        public BasePopupView asCustom(BasePopupView popupView) {
            popupView.popupInfo = this.popupInfo;
            return popupView;
        }
    }
}

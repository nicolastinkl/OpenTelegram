package com.lxj.xpopup.core;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.XPopupCallback;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PopupInfo {
    public int animationDuration;
    public View atView;
    public Boolean autoDismiss;
    public boolean autoFocusEditText;
    public Boolean autoOpenSoftInput;
    public float borderRadius;
    public PopupAnimator customAnimator;
    public boolean enableDrag;
    public boolean enableShowWhenAppBackground;
    public Boolean hasBlurBg;
    public Boolean hasNavigationBar;
    public Boolean hasShadowBg;
    public Boolean hasStatusBar;
    public Boolean hasStatusBarShadow;
    public Lifecycle hostLifecycle;
    public boolean isCenterHorizontal;
    public boolean isClickThrough;
    public Boolean isCoverSoftInput;
    public boolean isDarkTheme;
    public boolean isDestroyOnDismiss;
    public Boolean isDismissOnBackPressed;
    public Boolean isDismissOnTouchOutside;
    public int isLightNavigationBar;
    public int isLightStatusBar;
    public Boolean isMoveUpToKeyboard;
    public boolean isRequestFocus;
    public boolean isThreeDrag;
    public boolean isTouchThrough;
    public boolean isViewMode;
    public boolean keepScreenOn;
    public int maxHeight;
    public int maxWidth;
    public int navigationBarColor;
    public ArrayList<Rect> notDismissWhenTouchInArea;
    public int offsetX;
    public int offsetY;
    public PopupAnimation popupAnimation;
    public int popupHeight;
    public PopupPosition popupPosition;
    public int popupWidth;
    public boolean positionByWindowCenter;
    public int shadowBgColor;
    public int statusBarBgColor;
    public PointF touchPoint;
    public XPopupCallback xPopupCallback;

    public PopupInfo() {
        Boolean bool = Boolean.TRUE;
        this.isDismissOnBackPressed = bool;
        this.isDismissOnTouchOutside = bool;
        this.autoDismiss = bool;
        this.hasShadowBg = bool;
        Boolean bool2 = Boolean.FALSE;
        this.hasBlurBg = bool2;
        this.atView = null;
        this.popupAnimation = null;
        this.customAnimator = null;
        this.touchPoint = null;
        this.borderRadius = 15.0f;
        this.autoOpenSoftInput = bool2;
        this.isMoveUpToKeyboard = bool;
        this.popupPosition = null;
        this.hasStatusBarShadow = bool2;
        this.hasStatusBar = bool;
        this.hasNavigationBar = bool;
        this.navigationBarColor = 0;
        this.isLightNavigationBar = 0;
        this.isLightStatusBar = 0;
        this.enableDrag = true;
        this.isCenterHorizontal = false;
        this.isRequestFocus = true;
        this.autoFocusEditText = true;
        this.isClickThrough = false;
        this.isTouchThrough = false;
        this.isDarkTheme = false;
        this.enableShowWhenAppBackground = false;
        this.isThreeDrag = false;
        this.isDestroyOnDismiss = false;
        this.positionByWindowCenter = false;
        this.isViewMode = false;
        this.keepScreenOn = false;
        this.shadowBgColor = 0;
        this.animationDuration = -1;
        this.statusBarBgColor = 0;
        this.isCoverSoftInput = bool2;
    }

    public Rect getAtViewRect() {
        int[] iArr = new int[2];
        this.atView.getLocationInWindow(iArr);
        return new Rect(iArr[0], iArr[1], iArr[0] + this.atView.getMeasuredWidth(), iArr[1] + this.atView.getMeasuredHeight());
    }
}

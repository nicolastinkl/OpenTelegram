package com.lxj.xpopup.core;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.lxj.xpopup.R$style;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPermission;
import org.telegram.messenger.LiteMode;
import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes.dex */
public class FullScreenDialog extends Dialog {
    BasePopupView contentView;

    public FullScreenDialog(Context context) {
        super(context, R$style._XPopup_TransparentDialog);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        BasePopupView basePopupView;
        PopupInfo popupInfo;
        super.onCreate(savedInstanceState);
        if (getWindow() == null || (basePopupView = this.contentView) == null || (popupInfo = basePopupView.popupInfo) == null) {
            return;
        }
        if (popupInfo.enableShowWhenAppBackground && XPermission.create(getContext(), new String[0]).isGrantedDrawOverlays()) {
            if (Build.VERSION.SDK_INT >= 26) {
                getWindow().setType(2038);
            } else {
                getWindow().setType(2003);
            }
        }
        if (this.contentView.popupInfo.keepScreenOn) {
            getWindow().addFlags(128);
        }
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().getAttributes().format = -2;
        getWindow().setFlags(ConnectionsManager.FileTypePhoto, ConnectionsManager.FileTypePhoto);
        getWindow().setSoftInputMode(16);
        getWindow().setLayout(-1, -1);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setBackgroundDrawable(null);
        int i = Build.VERSION.SDK_INT;
        if (i == 19) {
            getWindow().clearFlags(ConnectionsManager.FileTypeFile);
        } else if (i == 20) {
            setWindowFlag(201326592, true);
        } else if (i >= 21) {
            setWindowFlag(201326592, false);
            getWindow().setStatusBarColor(0);
            int navigationBarColor = getNavigationBarColor();
            if (navigationBarColor != 0) {
                getWindow().setNavigationBarColor(navigationBarColor);
            }
            getWindow().addFlags(LinearLayoutManager.INVALID_OFFSET);
        }
        PopupInfo popupInfo2 = this.contentView.popupInfo;
        if (!popupInfo2.isRequestFocus) {
            setWindowFlag(popupInfo2.isCoverSoftInput.booleanValue() ? 131080 : 8, true);
        } else if (popupInfo2.isCoverSoftInput.booleanValue()) {
            setWindowFlag(131072, true);
        }
        setStatusBarLightMode();
        setNavBarLightMode();
        setContentView(this.contentView);
    }

    private int getNavigationBarColor() {
        int i = this.contentView.popupInfo.navigationBarColor;
        return i == 0 ? XPopup.getNavigationBarColor() : i;
    }

    public void setWindowFlag(final int bits, boolean on) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (on) {
            attributes.flags = bits | attributes.flags;
        } else {
            attributes.flags = (~bits) & attributes.flags;
        }
        getWindow().setAttributes(attributes);
    }

    private void setStatusBarLightMode() {
        if (!this.contentView.popupInfo.hasStatusBar.booleanValue()) {
            getWindow().getDecorView().setSystemUiVisibility(((ViewGroup) getWindow().getDecorView()).getSystemUiVisibility() | 1284);
            return;
        }
        int i = this.contentView.popupInfo.isLightStatusBar;
        if (i == 0) {
            i = XPopup.isLightStatusBar;
        }
        if (Build.VERSION.SDK_INT < 23 || i == 0) {
            return;
        }
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(i > 0 ? systemUiVisibility | LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM : systemUiVisibility & (-8193));
        getWindow().setStatusBarColor(this.contentView.popupInfo.statusBarBgColor);
    }

    public void hideNavigationBar() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            int id = childAt.getId();
            if (id != -1 && "navigationBarBackground".equals(getResNameById(id))) {
                childAt.setVisibility(4);
            }
        }
        viewGroup.setSystemUiVisibility(viewGroup.getSystemUiVisibility() | 4610);
    }

    private String getResNameById(int id) {
        try {
            return getContext().getResources().getResourceEntryName(id);
        } catch (Exception unused) {
            return "";
        }
    }

    public void setNavBarLightMode() {
        if (!this.contentView.popupInfo.hasNavigationBar.booleanValue()) {
            hideNavigationBar();
        }
        int i = this.contentView.popupInfo.isLightNavigationBar;
        if (i == 0) {
            i = XPopup.isLightNavigationBar;
        }
        if (Build.VERSION.SDK_INT < 26 || i == 0) {
            return;
        }
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(i > 0 ? systemUiVisibility | 16 : systemUiVisibility & (-17));
    }

    public FullScreenDialog setContent(BasePopupView view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        this.contentView = view;
        return this;
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        BasePopupView basePopupView;
        super.onWindowFocusChanged(hasFocus);
        setStatusBarLightMode();
        setNavBarLightMode();
        if (hasFocus && (basePopupView = this.contentView) != null && basePopupView.hasMoveUp && basePopupView.popupStatus == PopupStatus.Show) {
            basePopupView.focusAndProcessBackPress();
            KeyboardUtils.showSoftInput(this.contentView);
        }
    }
}

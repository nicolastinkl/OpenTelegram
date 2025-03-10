package com.lxj.xpopup.util;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.lxj.xpopup.core.BasePopupView;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public final class KeyboardUtils {
    private static final SparseArray<ViewTreeObserver.OnGlobalLayoutListener> listenerArray = new SparseArray<>();
    private static int sDecorViewDelta = 0;

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDecorViewInvisibleHeight(final Window window) {
        View decorView = window.getDecorView();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        Log.d("KeyboardUtils", "getDecorViewInvisibleHeight: " + (decorView.getBottom() - rect.bottom));
        int abs = Math.abs(decorView.getBottom() - rect.bottom);
        if (abs <= XPopupUtils.getNavBarHeight(window) + XPopupUtils.getStatusBarHeight(window)) {
            sDecorViewDelta = abs;
            return 0;
        }
        return abs - sDecorViewDelta;
    }

    public static void registerSoftInputChangedListener(final Window window, final BasePopupView popupView, final OnSoftInputChangedListener listener) {
        if (popupView == null) {
            return;
        }
        if ((window.getAttributes().flags & LiteMode.FLAG_CALLS_ANIMATIONS) != 0) {
            window.clearFlags(LiteMode.FLAG_CALLS_ANIMATIONS);
        }
        FrameLayout frameLayout = (FrameLayout) window.findViewById(R.id.content);
        final int[] iArr = {getDecorViewInvisibleHeight(window)};
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.lxj.xpopup.util.KeyboardUtils.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int decorViewInvisibleHeight = KeyboardUtils.getDecorViewInvisibleHeight(window);
                if (iArr[0] != decorViewInvisibleHeight) {
                    listener.onSoftInputChanged(decorViewInvisibleHeight);
                    iArr[0] = decorViewInvisibleHeight;
                }
            }
        };
        frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        listenerArray.append(popupView.getId(), onGlobalLayoutListener);
    }

    public static void removeLayoutChangeListener(Window window, BasePopupView popupView) {
        View findViewById;
        SparseArray<ViewTreeObserver.OnGlobalLayoutListener> sparseArray;
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
        if (popupView == null || (findViewById = window.findViewById(R.id.content)) == null || (onGlobalLayoutListener = (sparseArray = listenerArray).get(popupView.getId())) == null) {
            return;
        }
        findViewById.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        sparseArray.remove(popupView.getId());
    }

    public static void showSoftInput(final View view) {
        InputMethodManager inputMethodManager;
        if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0, new SoftInputReceiver(view.getContext()));
        inputMethodManager.toggleSoftInput(2, 1);
    }

    private static class SoftInputReceiver extends ResultReceiver {
        private Context context;

        public SoftInputReceiver(Context context) {
            super(new Handler());
            this.context = context;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == 1 || resultCode == 3) {
                KeyboardUtils.toggleSoftInput(this.context);
            }
            this.context = null;
        }
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager inputMethodManager;
        if (context == null || (inputMethodManager = (InputMethodManager) context.getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.toggleSoftInput(0, 0);
    }

    public static void hideSoftInput(View view) {
        ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

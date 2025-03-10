package com.hjq.window;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import com.hjq.window.EasyWindow;

/* loaded from: classes.dex */
final class ViewTouchWrapper implements View.OnTouchListener {
    private final EasyWindow.OnTouchListener mListener;
    private final EasyWindow<?> mWindow;

    ViewTouchWrapper(EasyWindow<?> easyWindow, EasyWindow.OnTouchListener onTouchListener) {
        this.mWindow = easyWindow;
        this.mListener = onTouchListener;
    }

    @Override // android.view.View.OnTouchListener
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        EasyWindow.OnTouchListener onTouchListener = this.mListener;
        if (onTouchListener == null) {
            return false;
        }
        return onTouchListener.onTouch(this.mWindow, view, motionEvent);
    }
}

package com.hjq.window;

import android.view.View;
import com.hjq.window.EasyWindow;

/* loaded from: classes.dex */
final class ViewLongClickWrapper implements View.OnLongClickListener {
    private final EasyWindow.OnLongClickListener mListener;
    private final EasyWindow<?> mWindow;

    ViewLongClickWrapper(EasyWindow<?> easyWindow, EasyWindow.OnLongClickListener onLongClickListener) {
        this.mWindow = easyWindow;
        this.mListener = onLongClickListener;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        EasyWindow.OnLongClickListener onLongClickListener = this.mListener;
        if (onLongClickListener == null) {
            return false;
        }
        return onLongClickListener.onLongClick(this.mWindow, view);
    }
}

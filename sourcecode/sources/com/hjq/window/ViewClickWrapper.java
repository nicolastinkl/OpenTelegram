package com.hjq.window;

import android.view.View;
import com.hjq.window.EasyWindow;

/* loaded from: classes.dex */
final class ViewClickWrapper implements View.OnClickListener {
    private final EasyWindow.OnClickListener mListener;
    private final EasyWindow<?> mWindow;

    ViewClickWrapper(EasyWindow<?> easyWindow, EasyWindow.OnClickListener onClickListener) {
        this.mWindow = easyWindow;
        this.mListener = onClickListener;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        EasyWindow.OnClickListener onClickListener = this.mListener;
        if (onClickListener == null) {
            return;
        }
        onClickListener.onClick(this.mWindow, view);
    }
}

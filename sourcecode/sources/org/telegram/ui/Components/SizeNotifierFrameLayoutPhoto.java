package org.telegram.ui.Components;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;

/* loaded from: classes4.dex */
public class SizeNotifierFrameLayoutPhoto extends FrameLayout {
    private SizeNotifierFrameLayoutPhotoDelegate delegate;
    private int keyboardHeight;
    private android.graphics.Rect rect;
    private boolean withoutWindow;

    public interface SizeNotifierFrameLayoutPhotoDelegate {
        void onSizeChanged(int i, boolean z);
    }

    public SizeNotifierFrameLayoutPhoto(Context context, boolean z) {
        super(context);
        this.rect = new android.graphics.Rect();
    }

    public void setDelegate(SizeNotifierFrameLayoutPhotoDelegate sizeNotifierFrameLayoutPhotoDelegate) {
        this.delegate = sizeNotifierFrameLayoutPhotoDelegate;
    }

    public void setWithoutWindow(boolean z) {
        this.withoutWindow = z;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        notifyHeightChanged();
    }

    public int getKeyboardHeight() {
        return this.keyboardHeight;
    }

    public int measureKeyboardHeight() {
        View rootView = getRootView();
        getWindowVisibleDisplayFrame(this.rect);
        if (this.withoutWindow) {
            int height = (rootView.getHeight() - (this.rect.top != 0 ? AndroidUtilities.statusBarHeight : 0)) - AndroidUtilities.getViewInset(rootView);
            android.graphics.Rect rect = this.rect;
            return height - (rect.bottom - rect.top);
        }
        int height2 = (((Activity) rootView.getContext()).getWindow().getDecorView().getHeight() - AndroidUtilities.getViewInset(rootView)) - rootView.getBottom();
        if (height2 <= Math.max(AndroidUtilities.dp(10.0f), AndroidUtilities.statusBarHeight)) {
            return 0;
        }
        return height2;
    }

    public void notifyHeightChanged() {
        if (this.delegate != null) {
            this.keyboardHeight = measureKeyboardHeight();
            android.graphics.Point point = AndroidUtilities.displaySize;
            final boolean z = point.x > point.y;
            post(new Runnable() { // from class: org.telegram.ui.Components.SizeNotifierFrameLayoutPhoto$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SizeNotifierFrameLayoutPhoto.this.lambda$notifyHeightChanged$0(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyHeightChanged$0(boolean z) {
        SizeNotifierFrameLayoutPhotoDelegate sizeNotifierFrameLayoutPhotoDelegate = this.delegate;
        if (sizeNotifierFrameLayoutPhotoDelegate != null) {
            sizeNotifierFrameLayoutPhotoDelegate.onSizeChanged(this.keyboardHeight, z);
        }
    }
}

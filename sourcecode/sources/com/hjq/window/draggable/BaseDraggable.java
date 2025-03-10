package com.hjq.window.draggable;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.hjq.window.EasyWindow;

/* loaded from: classes.dex */
public abstract class BaseDraggable implements View.OnTouchListener {
    private int mCurrentViewOnScreenX;
    private int mCurrentViewOnScreenY;
    private int mCurrentWindowHeight;
    private int mCurrentWindowInvisibleHeight;
    private int mCurrentWindowInvisibleWidth;
    private int mCurrentWindowWidth;
    private View mDecorView;
    private final Rect mTempRect = new Rect();
    private EasyWindow<?> mWindow;

    @SuppressLint({"ClickableViewAccessibility"})
    public void start(EasyWindow<?> easyWindow) {
        this.mWindow = easyWindow;
        View decorView = easyWindow.getDecorView();
        this.mDecorView = decorView;
        decorView.setOnTouchListener(new View.OnTouchListener() { // from class: com.hjq.window.draggable.BaseDraggable$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$start$0;
                lambda$start$0 = BaseDraggable.this.lambda$start$0(view, motionEvent);
                return lambda$start$0;
            }
        });
        this.mDecorView.post(new BaseDraggable$$ExternalSyntheticLambda1(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$start$0(View view, MotionEvent motionEvent) {
        refreshLocationCoordinate();
        return onTouch(view, motionEvent);
    }

    protected EasyWindow<?> getWindow() {
        return this.mWindow;
    }

    protected View getDecorView() {
        return this.mDecorView;
    }

    protected int getWindowWidth() {
        return this.mCurrentWindowWidth;
    }

    protected int getWindowHeight() {
        return this.mCurrentWindowHeight;
    }

    protected int getWindowInvisibleWidth() {
        return this.mCurrentWindowInvisibleWidth;
    }

    protected int getWindowInvisibleHeight() {
        return this.mCurrentWindowInvisibleHeight;
    }

    public void refreshLocationCoordinate() {
        View decorView = getDecorView();
        if (decorView == null) {
            return;
        }
        decorView.getWindowVisibleDisplayFrame(this.mTempRect);
        Rect rect = this.mTempRect;
        this.mCurrentWindowWidth = rect.right - rect.left;
        this.mCurrentWindowHeight = rect.bottom - rect.top;
        int[] iArr = new int[2];
        decorView.getLocationOnScreen(iArr);
        this.mCurrentViewOnScreenX = iArr[0];
        this.mCurrentViewOnScreenY = iArr[1];
        Rect rect2 = this.mTempRect;
        this.mCurrentWindowInvisibleWidth = rect2.left;
        this.mCurrentWindowInvisibleHeight = rect2.top;
    }

    public void onScreenOrientationChange() {
        final float f;
        final int width = getDecorView().getWidth();
        int height = getDecorView().getHeight();
        int i = this.mCurrentViewOnScreenX - this.mCurrentWindowInvisibleWidth;
        int i2 = this.mCurrentViewOnScreenY - this.mCurrentWindowInvisibleHeight;
        float f2 = i;
        final float f3 = 0.0f;
        if (f2 < 1.0f) {
            f = 0.0f;
        } else {
            f = ((float) Math.abs(this.mCurrentWindowWidth - (i + width))) < 1.0f ? 1.0f : (f2 + (width / 2.0f)) / this.mCurrentWindowWidth;
        }
        float f4 = i2;
        if (f4 >= 1.0f) {
            f3 = ((float) Math.abs(this.mCurrentWindowHeight - (i2 + height))) < 1.0f ? 1.0f : (f4 + (height / 2.0f)) / this.mCurrentWindowHeight;
        }
        getWindow().postDelayed(new Runnable() { // from class: com.hjq.window.draggable.BaseDraggable$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BaseDraggable.this.lambda$onScreenOrientationChange$1(f, width, f3);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onScreenOrientationChange$1(float f, int i, float f2) {
        getDecorView().getWindowVisibleDisplayFrame(this.mTempRect);
        Rect rect = this.mTempRect;
        int i2 = rect.right - rect.left;
        int i3 = rect.bottom - rect.top;
        float f3 = i2 * f;
        float f4 = i / 2.0f;
        updateLocation((int) (f3 - f4), (int) ((i3 * f2) - f4));
        getWindow().post(new BaseDraggable$$ExternalSyntheticLambda1(this));
    }

    protected void updateLocation(float f, float f2) {
        updateLocation((int) f, (int) f2);
    }

    protected void updateLocation(int i, int i2) {
        WindowManager.LayoutParams windowParams = this.mWindow.getWindowParams();
        if (windowParams == null) {
            return;
        }
        if (windowParams.gravity == 8388659 && windowParams.x == i && windowParams.y == i2) {
            return;
        }
        windowParams.x = i;
        windowParams.y = i2;
        windowParams.gravity = 8388659;
        this.mWindow.update();
    }

    protected boolean isFingerMove(float f, float f2, float f3, float f4) {
        float minTouchDistance = getMinTouchDistance();
        return Math.abs(f - f2) >= minTouchDistance || Math.abs(f3 - f4) >= minTouchDistance;
    }

    protected float getMinTouchDistance() {
        return TypedValue.applyDimension(1, 1.0f, Resources.getSystem().getDisplayMetrics());
    }
}

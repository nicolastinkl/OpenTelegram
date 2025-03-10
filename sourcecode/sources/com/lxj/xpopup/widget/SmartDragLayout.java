package com.lxj.xpopup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import com.lxj.xpopup.enums.LayoutStatus;

/* loaded from: classes.dex */
public class SmartDragLayout extends LinearLayout implements NestedScrollingParent {
    private View child;
    boolean dismissOnTouchOutside;
    int duration;
    boolean enableDrag;
    boolean isScrollUp;
    boolean isThreeDrag;
    boolean isUserClose;
    int lastHeight;
    private OnCloseListener listener;
    int maxY;
    int minY;
    OverScroller scroller;
    LayoutStatus status;
    float touchX;
    float touchY;
    VelocityTracker tracker;

    public interface OnCloseListener {
        void onClose();

        void onDrag(int y, float percent, boolean isScrollUp);

        void onOpen();
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return 2;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    public SmartDragLayout(Context context) {
        this(context, null);
    }

    public SmartDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.enableDrag = true;
        this.dismissOnTouchOutside = true;
        this.isUserClose = false;
        this.isThreeDrag = false;
        this.status = LayoutStatus.Close;
        this.duration = 400;
        this.scroller = new OverScroller(context);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View c) {
        super.onViewAdded(c);
        this.child = c;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.enableDrag) {
            View view = this.child;
            if (view == null) {
                return;
            }
            this.maxY = view.getMeasuredHeight();
            this.minY = 0;
            int measuredWidth = (getMeasuredWidth() / 2) - (this.child.getMeasuredWidth() / 2);
            this.child.layout(measuredWidth, getMeasuredHeight(), this.child.getMeasuredWidth() + measuredWidth, getMeasuredHeight() + this.maxY);
            if (this.status == LayoutStatus.Open) {
                if (this.isThreeDrag) {
                    scrollTo(getScrollX(), getScrollY() - (this.lastHeight - this.maxY));
                } else {
                    scrollTo(getScrollX(), getScrollY() - (this.lastHeight - this.maxY));
                }
            }
            this.lastHeight = this.maxY;
            return;
        }
        int measuredWidth2 = (getMeasuredWidth() / 2) - (this.child.getMeasuredWidth() / 2);
        this.child.layout(measuredWidth2, getMeasuredHeight() - this.child.getMeasuredHeight(), this.child.getMeasuredWidth() + measuredWidth2, getMeasuredHeight());
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        this.isUserClose = true;
        LayoutStatus layoutStatus = this.status;
        if (layoutStatus == LayoutStatus.Closing || layoutStatus == LayoutStatus.Opening) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0031, code lost:
    
        if (r0 != 3) goto L53;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.SmartDragLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void finishScroll() {
        int scrollY;
        if (this.enableDrag) {
            int scrollY2 = (getScrollY() > (this.isScrollUp ? this.maxY - this.minY : (this.maxY - this.minY) * 2) / 3 ? this.maxY : this.minY) - getScrollY();
            if (this.isThreeDrag) {
                int i = this.maxY / 3;
                float f = i;
                float f2 = 2.5f * f;
                if (getScrollY() > f2) {
                    i = this.maxY;
                    scrollY = getScrollY();
                } else if (getScrollY() <= f2 && getScrollY() > f * 1.5f) {
                    i *= 2;
                    scrollY = getScrollY();
                } else if (getScrollY() > i) {
                    scrollY = getScrollY();
                } else {
                    i = this.minY;
                    scrollY = getScrollY();
                }
                scrollY2 = i - scrollY;
            }
            this.scroller.startScroll(getScrollX(), getScrollY(), 0, scrollY2, this.duration);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.View
    public void scrollTo(int x, int y) {
        int i = this.maxY;
        if (y > i) {
            y = i;
        }
        int i2 = this.minY;
        if (y < i2) {
            y = i2;
        }
        float f = ((y - i2) * 1.0f) / (i - i2);
        this.isScrollUp = y > getScrollY();
        OnCloseListener onCloseListener = this.listener;
        if (onCloseListener != null) {
            if (this.isUserClose && f == 0.0f) {
                LayoutStatus layoutStatus = this.status;
                LayoutStatus layoutStatus2 = LayoutStatus.Close;
                if (layoutStatus != layoutStatus2) {
                    this.status = layoutStatus2;
                    onCloseListener.onClose();
                    this.listener.onDrag(y, f, this.isScrollUp);
                }
            }
            if (f == 1.0f) {
                LayoutStatus layoutStatus3 = this.status;
                LayoutStatus layoutStatus4 = LayoutStatus.Open;
                if (layoutStatus3 != layoutStatus4) {
                    this.status = layoutStatus4;
                    onCloseListener.onOpen();
                }
            }
            this.listener.onDrag(y, f, this.isScrollUp);
        }
        super.scrollTo(x, y);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.scroller.computeScrollOffset()) {
            scrollTo(this.scroller.getCurrX(), this.scroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isScrollUp = false;
        this.isUserClose = false;
        setTranslationY(0.0f);
    }

    public void open() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.1
            @Override // java.lang.Runnable
            public void run() {
                SmartDragLayout smartDragLayout = SmartDragLayout.this;
                int scrollY = smartDragLayout.maxY - smartDragLayout.getScrollY();
                SmartDragLayout smartDragLayout2 = SmartDragLayout.this;
                if (smartDragLayout2.enableDrag && smartDragLayout2.isThreeDrag) {
                    scrollY /= 3;
                }
                smartDragLayout2.smoothScroll(scrollY, true);
                SmartDragLayout.this.status = LayoutStatus.Opening;
            }
        });
    }

    public void close() {
        this.isUserClose = true;
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.2
            @Override // java.lang.Runnable
            public void run() {
                SmartDragLayout.this.scroller.abortAnimation();
                SmartDragLayout smartDragLayout = SmartDragLayout.this;
                smartDragLayout.smoothScroll(smartDragLayout.minY - smartDragLayout.getScrollY(), false);
                SmartDragLayout.this.status = LayoutStatus.Closing;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smoothScroll(final int dy, final boolean isOpen) {
        this.scroller.startScroll(getScrollX(), getScrollY(), 0, dy, (int) (isOpen ? this.duration : this.duration * 0.8f));
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return nestedScrollAxes == 2 && this.enableDrag;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        this.scroller.abortAnimation();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(View target) {
        finishScroll();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        scrollTo(getScrollX(), getScrollY() + dyUnconsumed);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {
            int scrollY = getScrollY() + dy;
            if (scrollY < this.maxY) {
                consumed[1] = dy;
            }
            scrollTo(getScrollX(), scrollY);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if ((getScrollY() > this.minY && getScrollY() < this.maxY) && velocityY < -1500.0f && !this.isThreeDrag) {
            close();
        }
        return false;
    }

    public void isThreeDrag(boolean isThreeDrag) {
        this.isThreeDrag = isThreeDrag;
    }

    public void enableDrag(boolean enableDrag) {
        this.enableDrag = enableDrag;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void dismissOnTouchOutside(boolean dismissOnTouchOutside) {
        this.dismissOnTouchOutside = dismissOnTouchOutside;
    }

    public void setOnCloseListener(OnCloseListener listener) {
        this.listener = listener;
    }
}

package com.lxj.xpopup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.viewpager.widget.ViewPager;
import com.lxj.xpopup.interfaces.OnDragChangeListener;
import com.lxj.xpopup.photoview.PhotoView;
import com.lxj.xpopup.photoview.PhotoViewAttacher;

/* loaded from: classes.dex */
public class PhotoViewContainer extends FrameLayout {
    private static final String TAG = "PhotoViewContainer";
    private int HideTopThreshold;
    ViewDragHelper.Callback cb;
    private OnDragChangeListener dragChangeListener;
    private ViewDragHelper dragHelper;
    public boolean isReleasing;
    boolean isVertical;
    private int maxOffset;
    private float touchX;
    private float touchY;
    public ViewPager viewPager;

    public PhotoViewContainer(Context context) {
        this(context, null);
    }

    public PhotoViewContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.HideTopThreshold = 80;
        this.isReleasing = false;
        this.isVertical = false;
        this.cb = new ViewDragHelper.Callback() { // from class: com.lxj.xpopup.widget.PhotoViewContainer.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View child) {
                return 1;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                return !PhotoViewContainer.this.isReleasing;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View child, int top, int dy) {
                int top2 = PhotoViewContainer.this.viewPager.getTop() + (dy / 2);
                return top2 >= 0 ? Math.min(top2, PhotoViewContainer.this.maxOffset) : -Math.min(-top2, PhotoViewContainer.this.maxOffset);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                ViewPager viewPager = PhotoViewContainer.this.viewPager;
                if (changedView != viewPager) {
                    viewPager.offsetTopAndBottom(dy);
                }
                float abs = (Math.abs(top) * 1.0f) / PhotoViewContainer.this.maxOffset;
                float f = 1.0f - (0.2f * abs);
                PhotoViewContainer.this.viewPager.setScaleX(f);
                PhotoViewContainer.this.viewPager.setScaleY(f);
                changedView.setScaleX(f);
                changedView.setScaleY(f);
                if (PhotoViewContainer.this.dragChangeListener != null) {
                    PhotoViewContainer.this.dragChangeListener.onDragChange(dy, f, abs);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (Math.abs(releasedChild.getTop()) > PhotoViewContainer.this.HideTopThreshold) {
                    if (PhotoViewContainer.this.dragChangeListener != null) {
                        PhotoViewContainer.this.dragChangeListener.onRelease();
                    }
                } else {
                    PhotoViewContainer.this.dragHelper.smoothSlideViewTo(PhotoViewContainer.this.viewPager, 0, 0);
                    PhotoViewContainer.this.dragHelper.smoothSlideViewTo(releasedChild, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(PhotoViewContainer.this);
                }
            }
        };
        init();
    }

    private void init() {
        this.HideTopThreshold = dip2px(this.HideTopThreshold);
        this.dragHelper = ViewDragHelper.create(this, this.cb);
        setBackgroundColor(0);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.viewPager = (ViewPager) getChildAt(0);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.maxOffset = getHeight() / 3;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean z = true;
        if (ev.getPointerCount() > 1) {
            return super.dispatchTouchEvent(ev);
        }
        try {
            int action = ev.getAction();
            if (action == 0) {
                this.touchX = ev.getX();
                this.touchY = ev.getY();
            } else {
                if (action != 1) {
                    if (action == 2) {
                        float x = ev.getX() - this.touchX;
                        float y = ev.getY() - this.touchY;
                        this.viewPager.dispatchTouchEvent(ev);
                        if (Math.abs(y) <= Math.abs(x)) {
                            z = false;
                        }
                        this.isVertical = z;
                        this.touchX = ev.getX();
                        this.touchY = ev.getY();
                    } else if (action != 3) {
                    }
                }
                this.touchX = 0.0f;
                this.touchY = 0.0f;
                this.isVertical = false;
            }
        } catch (Exception unused) {
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isTopOrBottomEnd() {
        View currentImageView = getCurrentImageView();
        if (!(currentImageView instanceof PhotoView)) {
            return false;
        }
        PhotoViewAttacher photoViewAttacher = ((PhotoView) currentImageView).attacher;
        return photoViewAttacher.isTopEnd || photoViewAttacher.isBottomEnd;
    }

    private View getCurrentImageView() {
        ViewPager viewPager = this.viewPager;
        FrameLayout frameLayout = (FrameLayout) viewPager.getChildAt(viewPager.getCurrentItem());
        if (frameLayout == null) {
            return null;
        }
        return frameLayout.getChildAt(0);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldInterceptTouchEvent = this.dragHelper.shouldInterceptTouchEvent(ev);
        if (ev.getPointerCount() > 1 && ev.getAction() == 2) {
            return false;
        }
        if (isTopOrBottomEnd() && this.isVertical) {
            return true;
        }
        return shouldInterceptTouchEvent && this.isVertical;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() > 1) {
            return false;
        }
        try {
            this.dragHelper.processTouchEvent(ev);
        } catch (Exception unused) {
        }
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.dragHelper.continueSettling(false)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public int dip2px(float dpValue) {
        return (int) ((dpValue * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void setOnDragChangeListener(OnDragChangeListener listener) {
        this.dragChangeListener = listener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isReleasing = false;
    }
}

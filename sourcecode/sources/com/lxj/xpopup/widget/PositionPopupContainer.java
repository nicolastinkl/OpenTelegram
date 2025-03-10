package com.lxj.xpopup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import com.lxj.xpopup.enums.DragOrientation;

/* loaded from: classes.dex */
public class PositionPopupContainer extends FrameLayout {
    private static final String TAG = "PositionPopupContainer";
    boolean canIntercept;
    ViewDragHelper.Callback cb;
    public View child;
    private ViewDragHelper dragHelper;
    public DragOrientation dragOrientation;
    public float dragRatio;
    public boolean enableDrag;
    private OnPositionDragListener positionDragListener;
    int touchSlop;
    private float touchX;
    private float touchY;

    public interface OnPositionDragListener {
        void onDismiss();
    }

    public PositionPopupContainer(Context context) {
        this(context, null);
    }

    public PositionPopupContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PositionPopupContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dragRatio = 0.2f;
        this.enableDrag = false;
        this.dragOrientation = DragOrientation.DragToUp;
        this.canIntercept = false;
        this.cb = new ViewDragHelper.Callback() { // from class: com.lxj.xpopup.widget.PositionPopupContainer.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                PositionPopupContainer positionPopupContainer = PositionPopupContainer.this;
                return view == positionPopupContainer.child && positionPopupContainer.enableDrag;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View child) {
                DragOrientation dragOrientation = PositionPopupContainer.this.dragOrientation;
                return (dragOrientation == DragOrientation.DragToUp || dragOrientation == DragOrientation.DragToBottom) ? 1 : 0;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(View child) {
                DragOrientation dragOrientation = PositionPopupContainer.this.dragOrientation;
                return (dragOrientation == DragOrientation.DragToLeft || dragOrientation == DragOrientation.DragToRight) ? 1 : 0;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View child, int top, int dy) {
                DragOrientation dragOrientation = PositionPopupContainer.this.dragOrientation;
                if (dragOrientation == DragOrientation.DragToUp) {
                    if (dy < 0) {
                        return top;
                    }
                    return 0;
                }
                if (dragOrientation != DragOrientation.DragToBottom || dy <= 0) {
                    return 0;
                }
                return top;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                DragOrientation dragOrientation = PositionPopupContainer.this.dragOrientation;
                if (dragOrientation == DragOrientation.DragToLeft) {
                    if (dx < 0) {
                        return left;
                    }
                    return 0;
                }
                if (dragOrientation != DragOrientation.DragToRight || dx <= 0) {
                    return 0;
                }
                return left;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                float measuredWidth = releasedChild.getMeasuredWidth() * PositionPopupContainer.this.dragRatio;
                float measuredHeight = releasedChild.getMeasuredHeight();
                PositionPopupContainer positionPopupContainer = PositionPopupContainer.this;
                float f = measuredHeight * positionPopupContainer.dragRatio;
                if ((positionPopupContainer.dragOrientation != DragOrientation.DragToLeft || releasedChild.getLeft() >= (-measuredWidth)) && ((PositionPopupContainer.this.dragOrientation != DragOrientation.DragToRight || releasedChild.getRight() <= releasedChild.getMeasuredWidth() + measuredWidth) && ((PositionPopupContainer.this.dragOrientation != DragOrientation.DragToUp || releasedChild.getTop() >= (-f)) && (PositionPopupContainer.this.dragOrientation != DragOrientation.DragToBottom || releasedChild.getBottom() <= releasedChild.getMeasuredHeight() + f)))) {
                    PositionPopupContainer.this.dragHelper.smoothSlideViewTo(releasedChild, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(PositionPopupContainer.this);
                } else {
                    PositionPopupContainer.this.positionDragListener.onDismiss();
                }
            }
        };
        init();
    }

    private void init() {
        this.dragHelper = ViewDragHelper.create(this, this.cb);
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.child = getChildAt(0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean z = true;
        if (ev.getPointerCount() > 1 || !this.enableDrag) {
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
                        if (Math.sqrt(Math.pow(ev.getX() - this.touchX, 2.0d) + Math.pow(ev.getY() - this.touchY, 2.0d)) <= this.touchSlop) {
                            z = false;
                        }
                        this.canIntercept = z;
                        this.touchX = ev.getX();
                        this.touchY = ev.getY();
                    } else if (action != 3) {
                    }
                }
                this.touchX = 0.0f;
                this.touchY = 0.0f;
            }
        } catch (Exception unused) {
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.enableDrag) {
            return super.onInterceptTouchEvent(ev);
        }
        this.dragHelper.shouldInterceptTouchEvent(ev);
        return this.dragHelper.shouldInterceptTouchEvent(ev) || this.canIntercept;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() > 1 || !this.enableDrag) {
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

    public void setOnPositionDragChangeListener(OnPositionDragListener positionDragListener) {
        this.positionDragListener = positionDragListener;
    }
}

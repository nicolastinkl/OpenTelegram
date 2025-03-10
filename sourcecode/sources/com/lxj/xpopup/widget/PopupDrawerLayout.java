package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSeekBar;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.lxj.xpopup.enums.LayoutStatus;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class PopupDrawerLayout extends FrameLayout {
    ViewDragHelper.Callback callback;
    boolean canChildScrollLeft;
    float downX;
    float downY;
    ViewDragHelper dragHelper;
    public boolean enableDrag;
    float fraction;
    boolean hasLayout;
    public boolean isDismissOnTouchOutside;
    boolean isIntercept;
    boolean isToLeft;
    private OnCloseListener listener;
    View mChild;
    View placeHolder;
    public PopupPosition position;
    LayoutStatus status;
    float ty;
    float x;
    float y;

    public interface OnCloseListener {
        void onClose();

        void onDrag(int x, float fraction, boolean isToLeft);

        void onOpen();
    }

    public PopupDrawerLayout(Context context) {
        this(context, null);
    }

    public PopupDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.status = null;
        this.position = PopupPosition.Left;
        this.fraction = 0.0f;
        this.enableDrag = true;
        this.hasLayout = false;
        this.isIntercept = false;
        ViewDragHelper.Callback callback = new ViewDragHelper.Callback() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(View child) {
                return 1;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                return (!popupDrawerLayout.enableDrag || popupDrawerLayout.dragHelper.continueSettling(true) || PopupDrawerLayout.this.status == LayoutStatus.Close) ? false : true;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                return child == popupDrawerLayout.placeHolder ? left : popupDrawerLayout.fixLeft(left);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                View view = PopupDrawerLayout.this.placeHolder;
                if (changedView == view) {
                    view.layout(0, 0, view.getMeasuredWidth(), PopupDrawerLayout.this.placeHolder.getMeasuredHeight());
                    PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                    int fixLeft = popupDrawerLayout.fixLeft(popupDrawerLayout.mChild.getLeft() + dx);
                    View view2 = PopupDrawerLayout.this.mChild;
                    view2.layout(fixLeft, view2.getTop(), PopupDrawerLayout.this.mChild.getMeasuredWidth() + fixLeft, PopupDrawerLayout.this.mChild.getBottom());
                    calcFraction(fixLeft, dx);
                    return;
                }
                calcFraction(left, dx);
            }

            private void calcFraction(int left, int dx) {
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                PopupPosition popupPosition = popupDrawerLayout.position;
                if (popupPosition == PopupPosition.Left) {
                    popupDrawerLayout.fraction = ((popupDrawerLayout.mChild.getMeasuredWidth() + left) * 1.0f) / PopupDrawerLayout.this.mChild.getMeasuredWidth();
                    if (left == (-PopupDrawerLayout.this.mChild.getMeasuredWidth()) && PopupDrawerLayout.this.listener != null) {
                        PopupDrawerLayout popupDrawerLayout2 = PopupDrawerLayout.this;
                        LayoutStatus layoutStatus = popupDrawerLayout2.status;
                        LayoutStatus layoutStatus2 = LayoutStatus.Close;
                        if (layoutStatus != layoutStatus2) {
                            popupDrawerLayout2.status = layoutStatus2;
                            popupDrawerLayout2.listener.onClose();
                        }
                    }
                } else if (popupPosition == PopupPosition.Right) {
                    popupDrawerLayout.fraction = ((popupDrawerLayout.getMeasuredWidth() - left) * 1.0f) / PopupDrawerLayout.this.mChild.getMeasuredWidth();
                    if (left == PopupDrawerLayout.this.getMeasuredWidth() && PopupDrawerLayout.this.listener != null) {
                        PopupDrawerLayout popupDrawerLayout3 = PopupDrawerLayout.this;
                        LayoutStatus layoutStatus3 = popupDrawerLayout3.status;
                        LayoutStatus layoutStatus4 = LayoutStatus.Close;
                        if (layoutStatus3 != layoutStatus4) {
                            popupDrawerLayout3.status = layoutStatus4;
                            popupDrawerLayout3.listener.onClose();
                        }
                    }
                }
                if (PopupDrawerLayout.this.listener != null) {
                    PopupDrawerLayout.this.listener.onDrag(left, PopupDrawerLayout.this.fraction, dx < 0);
                    PopupDrawerLayout popupDrawerLayout4 = PopupDrawerLayout.this;
                    if (popupDrawerLayout4.fraction == 1.0f) {
                        LayoutStatus layoutStatus5 = popupDrawerLayout4.status;
                        LayoutStatus layoutStatus6 = LayoutStatus.Open;
                        if (layoutStatus5 != layoutStatus6) {
                            popupDrawerLayout4.status = layoutStatus6;
                            popupDrawerLayout4.listener.onOpen();
                        }
                    }
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int measuredWidth;
                int measuredWidth2;
                super.onViewReleased(releasedChild, xvel, yvel);
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                if (releasedChild == popupDrawerLayout.placeHolder && xvel == 0.0f) {
                    if (popupDrawerLayout.isDismissOnTouchOutside) {
                        popupDrawerLayout.close();
                        return;
                    }
                    return;
                }
                View view = popupDrawerLayout.mChild;
                if (releasedChild == view && popupDrawerLayout.isToLeft && !popupDrawerLayout.canChildScrollLeft && xvel < -500.0f) {
                    popupDrawerLayout.close();
                    return;
                }
                if (popupDrawerLayout.position == PopupPosition.Left) {
                    if (xvel < -1000.0f) {
                        measuredWidth2 = view.getMeasuredWidth();
                    } else {
                        if (PopupDrawerLayout.this.mChild.getLeft() < (-view.getMeasuredWidth()) / 2) {
                            measuredWidth2 = PopupDrawerLayout.this.mChild.getMeasuredWidth();
                        } else {
                            measuredWidth = 0;
                        }
                    }
                    measuredWidth = -measuredWidth2;
                } else if (xvel > 1000.0f) {
                    measuredWidth = popupDrawerLayout.getMeasuredWidth();
                } else {
                    measuredWidth = releasedChild.getLeft() < popupDrawerLayout.getMeasuredWidth() - (PopupDrawerLayout.this.mChild.getMeasuredWidth() / 2) ? PopupDrawerLayout.this.getMeasuredWidth() - PopupDrawerLayout.this.mChild.getMeasuredWidth() : PopupDrawerLayout.this.getMeasuredWidth();
                }
                PopupDrawerLayout popupDrawerLayout2 = PopupDrawerLayout.this;
                popupDrawerLayout2.dragHelper.smoothSlideViewTo(popupDrawerLayout2.mChild, measuredWidth, releasedChild.getTop());
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        };
        this.callback = callback;
        this.isDismissOnTouchOutside = true;
        this.dragHelper = ViewDragHelper.create(this, callback);
    }

    public void setDrawerPosition(PopupPosition position) {
        this.position = position;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.placeHolder = getChildAt(0);
        this.mChild = getChildAt(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.ty = getTranslationY();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.placeHolder.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        if (!this.hasLayout) {
            if (this.position == PopupPosition.Left) {
                View view = this.mChild;
                view.layout(-view.getMeasuredWidth(), 0, 0, getMeasuredHeight());
            } else {
                this.mChild.layout(getMeasuredWidth(), 0, getMeasuredWidth() + this.mChild.getMeasuredWidth(), getMeasuredHeight());
            }
            this.hasLayout = true;
            return;
        }
        View view2 = this.mChild;
        view2.layout(view2.getLeft(), this.mChild.getTop(), this.mChild.getRight(), this.mChild.getMeasuredHeight());
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0042, code lost:
    
        if (r0 != 3) goto L28;
     */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            boolean r0 = r5.enableDrag
            if (r0 != 0) goto L9
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        L9:
            androidx.customview.widget.ViewDragHelper r0 = r5.dragHelper
            r1 = 1
            boolean r0 = r0.continueSettling(r1)
            if (r0 != 0) goto La3
            com.lxj.xpopup.enums.LayoutStatus r0 = r5.status
            com.lxj.xpopup.enums.LayoutStatus r2 = com.lxj.xpopup.enums.LayoutStatus.Close
            if (r0 != r2) goto L1a
            goto La3
        L1a:
            float r0 = r6.getX()
            float r2 = r5.x
            r3 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L27
            r0 = 1
            goto L28
        L27:
            r0 = 0
        L28:
            r5.isToLeft = r0
            float r0 = r6.getX()
            r5.x = r0
            float r0 = r6.getY()
            r5.y = r0
            int r0 = r6.getAction()
            if (r0 == 0) goto L62
            if (r0 == r1) goto L5c
            r2 = 2
            if (r0 == r2) goto L45
            r2 = 3
            if (r0 == r2) goto L5c
            goto L6e
        L45:
            float r0 = r5.x
            float r2 = r5.downX
            float r0 = r0 - r2
            float r0 = java.lang.Math.abs(r0)
            float r2 = r5.y
            float r4 = r5.downY
            float r2 = r2 - r4
            float r2 = java.lang.Math.abs(r2)
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 <= 0) goto L6e
            return r3
        L5c:
            r0 = 0
            r5.x = r0
            r5.y = r0
            goto L6e
        L62:
            float r0 = r6.getX()
            r5.downX = r0
            float r0 = r6.getY()
            r5.downY = r0
        L6e:
            float r0 = r6.getX()
            float r2 = r6.getY()
            boolean r0 = r5.canScroll(r5, r0, r2, r1)
            r5.canChildScrollLeft = r0
            androidx.customview.widget.ViewDragHelper r0 = r5.dragHelper
            boolean r0 = r0.shouldInterceptTouchEvent(r6)
            r5.isIntercept = r0
            boolean r1 = r5.isToLeft
            if (r1 == 0) goto L8d
            boolean r1 = r5.canChildScrollLeft
            if (r1 != 0) goto L8d
            return r0
        L8d:
            float r0 = r6.getX()
            float r1 = r6.getY()
            boolean r0 = r5.canScroll(r5, r0, r1)
            if (r0 != 0) goto L9e
            boolean r6 = r5.isIntercept
            return r6
        L9e:
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        La3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.PopupDrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean canScroll(ViewGroup group, float x, float y, int direction) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View childAt = group.getChildAt(i);
            int[] iArr = new int[2];
            childAt.getLocationInWindow(iArr);
            if (XPopupUtils.isInRect(x, y, new Rect(iArr[0], iArr[1], iArr[0] + childAt.getWidth(), iArr[1] + childAt.getHeight()))) {
                if (childAt instanceof ViewGroup) {
                    if (childAt instanceof ViewPager) {
                        ViewPager viewPager = (ViewPager) childAt;
                        if (direction == 0) {
                            if (!viewPager.canScrollHorizontally(-1)) {
                                viewPager.canScrollHorizontally(1);
                            }
                            return viewPager.canScrollHorizontally(-1) || viewPager.canScrollHorizontally(1);
                        }
                        return viewPager.canScrollHorizontally(direction);
                    }
                    if (childAt instanceof HorizontalScrollView) {
                        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) childAt;
                        if (direction == 0) {
                            return horizontalScrollView.canScrollHorizontally(-1) || horizontalScrollView.canScrollHorizontally(1);
                        }
                        return horizontalScrollView.canScrollHorizontally(direction);
                    }
                    if (childAt instanceof ViewPager2) {
                        RecyclerView recyclerView = (RecyclerView) ((ViewPager2) childAt).getChildAt(0);
                        return recyclerView.canScrollHorizontally(-1) || recyclerView.canScrollHorizontally(1);
                    }
                    return canScroll((ViewGroup) childAt, x, y, direction);
                }
                if ((childAt instanceof AbsSeekBar) && childAt.isEnabled()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canScroll(ViewGroup group, float x, float y) {
        return canScroll(group, x, y, 0);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.enableDrag) {
            return super.onTouchEvent(event);
        }
        if (this.dragHelper.continueSettling(true)) {
            return true;
        }
        this.dragHelper.processTouchEvent(event);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int fixLeft(int left) {
        PopupPosition popupPosition = this.position;
        if (popupPosition == PopupPosition.Left) {
            if (left < (-this.mChild.getMeasuredWidth())) {
                left = -this.mChild.getMeasuredWidth();
            }
            if (left > 0) {
                return 0;
            }
            return left;
        }
        if (popupPosition != PopupPosition.Right) {
            return left;
        }
        if (left < getMeasuredWidth() - this.mChild.getMeasuredWidth()) {
            left = getMeasuredWidth() - this.mChild.getMeasuredWidth();
        }
        return left > getMeasuredWidth() ? getMeasuredWidth() : left;
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void open() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.2
            @Override // java.lang.Runnable
            public void run() {
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                ViewDragHelper viewDragHelper = popupDrawerLayout.dragHelper;
                View view = popupDrawerLayout.mChild;
                viewDragHelper.smoothSlideViewTo(view, popupDrawerLayout.position == PopupPosition.Left ? 0 : view.getLeft() - PopupDrawerLayout.this.mChild.getMeasuredWidth(), 0);
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        });
    }

    public void close() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.3
            @Override // java.lang.Runnable
            public void run() {
                PopupDrawerLayout.this.dragHelper.abort();
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                ViewDragHelper viewDragHelper = popupDrawerLayout.dragHelper;
                View view = popupDrawerLayout.mChild;
                viewDragHelper.smoothSlideViewTo(view, popupDrawerLayout.position == PopupPosition.Left ? -view.getMeasuredWidth() : popupDrawerLayout.getMeasuredWidth(), 0);
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        });
    }

    public void setOnCloseListener(OnCloseListener listener) {
        this.listener = listener;
    }
}

package com.lxj.xpopup.photoview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/* loaded from: classes.dex */
class CustomGestureDetector {
    private int mActivePointerId = -1;
    private int mActivePointerIndex = 0;
    private final ScaleGestureDetector mDetector;
    private boolean mIsDragging;
    private float mLastTouchX;
    private float mLastTouchY;
    private OnGestureListener mListener;
    private final float mMinimumVelocity;
    private final float mTouchSlop;
    private VelocityTracker mVelocityTracker;

    CustomGestureDetector(Context context, OnGestureListener listener) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mListener = listener;
        this.mDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.lxj.xpopup.photoview.CustomGestureDetector.1
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector detector) {
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                    return false;
                }
                if (scaleFactor < 0.0f) {
                    return true;
                }
                CustomGestureDetector.this.mListener.onScale(scaleFactor, detector.getFocusX(), detector.getFocusY());
                return true;
            }
        });
    }

    private float getActiveX(MotionEvent ev) {
        try {
            return ev.getX(this.mActivePointerIndex);
        } catch (Exception unused) {
            return ev.getX();
        }
    }

    private float getActiveY(MotionEvent ev) {
        try {
            return ev.getY(this.mActivePointerIndex);
        } catch (Exception unused) {
            return ev.getY();
        }
    }

    public boolean isScaling() {
        return this.mDetector.isInProgress();
    }

    public boolean isDragging() {
        return this.mIsDragging;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        try {
            if (ev.getPointerCount() > 1) {
                this.mDetector.onTouchEvent(ev);
            }
            return processTouchEvent(ev);
        } catch (IllegalArgumentException unused) {
            return true;
        }
    }

    private boolean processTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action == 0) {
            this.mActivePointerId = ev.getPointerId(0);
            VelocityTracker obtain = VelocityTracker.obtain();
            this.mVelocityTracker = obtain;
            if (obtain != null) {
                obtain.addMovement(ev);
            }
            this.mLastTouchX = getActiveX(ev);
            this.mLastTouchY = getActiveY(ev);
            this.mIsDragging = false;
        } else if (action == 1) {
            this.mActivePointerId = -1;
            if (this.mIsDragging && this.mVelocityTracker != null) {
                this.mLastTouchX = getActiveX(ev);
                this.mLastTouchY = getActiveY(ev);
                this.mVelocityTracker.addMovement(ev);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = this.mVelocityTracker.getXVelocity();
                float yVelocity = this.mVelocityTracker.getYVelocity();
                if (Math.max(Math.abs(xVelocity), Math.abs(yVelocity)) >= this.mMinimumVelocity) {
                    this.mListener.onFling(this.mLastTouchX, this.mLastTouchY, -xVelocity, -yVelocity);
                }
            }
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        } else if (action == 2) {
            float activeX = getActiveX(ev);
            float activeY = getActiveY(ev);
            float f = activeX - this.mLastTouchX;
            float f2 = activeY - this.mLastTouchY;
            if (!this.mIsDragging) {
                this.mIsDragging = Math.sqrt((double) ((f * f) + (f2 * f2))) >= ((double) this.mTouchSlop);
            }
            if (this.mIsDragging) {
                this.mListener.onDrag(f, f2);
                this.mLastTouchX = activeX;
                this.mLastTouchY = activeY;
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.addMovement(ev);
                }
            }
        } else if (action == 3) {
            this.mActivePointerId = -1;
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
                this.mVelocityTracker = null;
            }
        } else if (action == 6) {
            int pointerIndex = Util.getPointerIndex(ev.getAction());
            if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
                int i = pointerIndex == 0 ? 1 : 0;
                this.mActivePointerId = ev.getPointerId(i);
                this.mLastTouchX = ev.getX(i);
                this.mLastTouchY = ev.getY(i);
            }
        }
        int i2 = this.mActivePointerId;
        this.mActivePointerIndex = ev.findPointerIndex(i2 != -1 ? i2 : 0);
        return true;
    }
}

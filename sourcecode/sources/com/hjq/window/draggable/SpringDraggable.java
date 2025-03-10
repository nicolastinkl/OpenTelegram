package com.hjq.window.draggable;

import android.animation.ValueAnimator;

/* loaded from: classes.dex */
public class SpringDraggable extends BaseDraggable {
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private final int mOrientation;
    private boolean mTouchMoving;
    private float mViewDownX;
    private float mViewDownY;

    public SpringDraggable() {
        this(0);
    }

    public SpringDraggable(int i) {
        this.mOrientation = i;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("You cannot pass in directions other than horizontal or vertical");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000f, code lost:
    
        if (r0 != 3) goto L56;
     */
    @Override // android.view.View.OnTouchListener
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r8, android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hjq.window.draggable.SpringDraggable.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    protected void startHorizontalAnimation(float f, float f2, float f3) {
        startHorizontalAnimation(f, f2, f3, calculateAnimationDuration(f, f2));
    }

    protected void startHorizontalAnimation(float f, float f2, final float f3, long j) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        ofFloat.setDuration(j);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.hjq.window.draggable.SpringDraggable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SpringDraggable.this.lambda$startHorizontalAnimation$0(f3, valueAnimator);
            }
        });
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startHorizontalAnimation$0(float f, ValueAnimator valueAnimator) {
        updateLocation(((Float) valueAnimator.getAnimatedValue()).floatValue(), f);
    }

    protected void startVerticalAnimation(float f, float f2, float f3) {
        startVerticalAnimation(f, f2, f3, calculateAnimationDuration(f2, f3));
    }

    protected void startVerticalAnimation(final float f, float f2, float f3, long j) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, f3);
        ofFloat.setDuration(j);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.hjq.window.draggable.SpringDraggable$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SpringDraggable.this.lambda$startVerticalAnimation$1(f, valueAnimator);
            }
        });
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startVerticalAnimation$1(float f, ValueAnimator valueAnimator) {
        updateLocation(f, ((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public long calculateAnimationDuration(float f, float f2) {
        long abs = (long) (Math.abs(f2 - f) / 2.0f);
        if (abs > 800) {
            return 800L;
        }
        return abs;
    }
}

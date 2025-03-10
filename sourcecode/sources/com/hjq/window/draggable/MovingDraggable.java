package com.hjq.window.draggable;

/* loaded from: classes.dex */
public class MovingDraggable extends BaseDraggable {
    private boolean mMoveTouch;
    private float mViewDownX;
    private float mViewDownY;

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:
    
        if (r6 != 3) goto L25;
     */
    @Override // android.view.View.OnTouchListener
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
        /*
            r5 = this;
            int r6 = r7.getAction()
            r0 = 0
            if (r6 == 0) goto L55
            r1 = 1
            if (r6 == r1) goto L52
            r2 = 2
            if (r6 == r2) goto L11
            r7 = 3
            if (r6 == r7) goto L52
            goto L63
        L11:
            float r6 = r7.getRawX()
            int r2 = r5.getWindowInvisibleWidth()
            float r2 = (float) r2
            float r6 = r6 - r2
            float r2 = r7.getRawY()
            int r3 = r5.getWindowInvisibleHeight()
            float r3 = (float) r3
            float r2 = r2 - r3
            float r3 = r5.mViewDownX
            float r6 = r6 - r3
            float r3 = r5.mViewDownY
            float r2 = r2 - r3
            r3 = 0
            int r4 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r4 >= 0) goto L31
            r6 = 0
        L31:
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 >= 0) goto L36
            r2 = 0
        L36:
            r5.updateLocation(r6, r2)
            boolean r6 = r5.mMoveTouch
            if (r6 != 0) goto L63
            float r6 = r5.mViewDownX
            float r2 = r7.getX()
            float r3 = r5.mViewDownY
            float r7 = r7.getY()
            boolean r6 = r5.isFingerMove(r6, r2, r3, r7)
            if (r6 == 0) goto L63
            r5.mMoveTouch = r1
            goto L63
        L52:
            boolean r6 = r5.mMoveTouch
            return r6
        L55:
            float r6 = r7.getX()
            r5.mViewDownX = r6
            float r6 = r7.getY()
            r5.mViewDownY = r6
            r5.mMoveTouch = r0
        L63:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hjq.window.draggable.MovingDraggable.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }
}

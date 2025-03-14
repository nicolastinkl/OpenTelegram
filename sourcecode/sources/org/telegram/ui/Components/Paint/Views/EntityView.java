package org.telegram.ui.Components.Paint.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.Rect;

/* loaded from: classes4.dex */
public class EntityView extends FrameLayout {
    private static final List<Integer> STICKY_ANGLES = Arrays.asList(-90, 0, 90, Integer.valueOf(SubsamplingScaleImageView.ORIENTATION_180));
    private ValueAnimator angleAnimator;
    private boolean announcedSelection;
    private int currentStickyAngle;
    private EntityViewDelegate delegate;
    private ValueAnimator fromStickyAngleAnimator;
    private float fromStickyAnimatedAngle;
    private float fromStickyToAngle;
    private float fromStickyX;
    private float fromStickyY;
    private GestureDetector gestureDetector;
    private boolean hasFromStickyXAnimation;
    private boolean hasFromStickyYAnimation;
    private boolean hasPanned;
    private boolean hasReleased;
    private boolean hasStickyAngle;
    private boolean hasStickyX;
    private boolean hasStickyY;
    private boolean hasTransformed;
    private Point position;
    private float previousLocationX;
    private float previousLocationY;
    private boolean recognizedLongPress;
    protected SelectionView selectionView;
    private float stickyAnimatedAngle;
    private ValueAnimator stickyXAnimator;
    private ValueAnimator stickyYAnimator;
    private UUID uuid;

    public interface EntityViewDelegate {
        boolean allowInteraction(EntityView entityView);

        int[] getCenterLocation(EntityView entityView);

        float getCropRotation();

        float[] getTransformedTouch(float f, float f2);

        boolean onEntityLongClicked(EntityView entityView);

        boolean onEntitySelected(EntityView entityView);
    }

    protected SelectionView createSelectionView() {
        return null;
    }

    public EntityView(Context context, Point point) {
        super(context);
        this.hasPanned = false;
        this.hasReleased = false;
        this.hasTransformed = false;
        this.announcedSelection = false;
        this.recognizedLongPress = false;
        this.hasStickyAngle = true;
        this.currentStickyAngle = 0;
        this.uuid = UUID.randomUUID();
        this.position = point;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: org.telegram.ui.Components.Paint.Views.EntityView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent motionEvent) {
                if (EntityView.this.hasPanned || EntityView.this.hasTransformed || EntityView.this.hasReleased) {
                    return;
                }
                EntityView.this.recognizedLongPress = true;
                if (EntityView.this.delegate != null) {
                    EntityView.this.performHapticFeedback(0);
                    EntityView.this.delegate.onEntityLongClicked(EntityView.this);
                }
            }
        });
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point point) {
        this.position = point;
        updatePosition();
    }

    public float getScale() {
        return getScaleX();
    }

    public void setScale(float f) {
        setScaleX(f);
        setScaleY(f);
    }

    public void setDelegate(EntityViewDelegate entityViewDelegate) {
        this.delegate = entityViewDelegate;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.delegate.allowInteraction(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onTouchMove(float f, float f2) {
        if (getParent() == null) {
            return false;
        }
        float scaleX = ((View) getParent()).getScaleX();
        float f3 = (f - this.previousLocationX) / scaleX;
        float f4 = (f2 - this.previousLocationY) / scaleX;
        if (((float) Math.hypot(f3, f4)) <= (this.hasPanned ? 6.0f : 16.0f)) {
            return false;
        }
        pan(f3, f4);
        this.previousLocationX = f;
        this.previousLocationY = f2;
        this.hasPanned = true;
        if ((getParent() instanceof EntitiesContainerView) && (this.hasStickyX || this.hasStickyY)) {
            ((EntitiesContainerView) getParent()).invalidate();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTouchUp() {
        EntityViewDelegate entityViewDelegate;
        if (!this.recognizedLongPress && !this.hasPanned && !this.hasTransformed && !this.announcedSelection && (entityViewDelegate = this.delegate) != null) {
            entityViewDelegate.onEntitySelected(this);
        }
        this.recognizedLongPress = false;
        this.hasPanned = false;
        this.hasTransformed = false;
        this.hasReleased = true;
        this.announcedSelection = false;
        if (getParent() instanceof EntitiesContainerView) {
            ((EntitiesContainerView) getParent()).invalidate();
        }
    }

    public final boolean hasTouchDown() {
        return !this.hasReleased;
    }

    public void setHasStickyX(boolean z) {
        this.hasStickyX = z;
    }

    public final boolean hasStickyX() {
        return this.hasStickyX;
    }

    public void setHasStickyY(boolean z) {
        this.hasStickyY = z;
    }

    public final boolean hasStickyY() {
        return this.hasStickyY;
    }

    public boolean hasPanned() {
        return this.hasPanned;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0031, code lost:
    
        if (r3 != 6) goto L34;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getPointerCount()
            r1 = 0
            r2 = 1
            if (r0 > r2) goto L7a
            org.telegram.ui.Components.Paint.Views.EntityView$EntityViewDelegate r0 = r5.delegate
            boolean r0 = r0.allowInteraction(r5)
            if (r0 != 0) goto L11
            goto L7a
        L11:
            org.telegram.ui.Components.Paint.Views.EntityView$EntityViewDelegate r0 = r5.delegate
            float r3 = r6.getRawX()
            float r4 = r6.getRawY()
            float[] r0 = r0.getTransformedTouch(r3, r4)
            int r3 = r6.getActionMasked()
            if (r3 == 0) goto L42
            if (r3 == r2) goto L3d
            r4 = 2
            if (r3 == r4) goto L34
            r4 = 3
            if (r3 == r4) goto L3d
            r4 = 5
            if (r3 == r4) goto L42
            r0 = 6
            if (r3 == r0) goto L3d
            goto L75
        L34:
            r1 = r0[r1]
            r0 = r0[r2]
            boolean r1 = r5.onTouchMove(r1, r0)
            goto L75
        L3d:
            r5.onTouchUp()
        L40:
            r1 = 1
            goto L75
        L42:
            boolean r3 = r5.isSelected()
            if (r3 != 0) goto L51
            org.telegram.ui.Components.Paint.Views.EntityView$EntityViewDelegate r3 = r5.delegate
            if (r3 == 0) goto L51
            r3.onEntitySelected(r5)
            r5.announcedSelection = r2
        L51:
            r3 = r0[r1]
            r5.previousLocationX = r3
            r0 = r0[r2]
            r5.previousLocationY = r0
            r5.hasReleased = r1
            android.view.ViewParent r0 = r5.getParent()
            boolean r0 = r0 instanceof org.telegram.ui.Components.Paint.Views.EntitiesContainerView
            if (r0 == 0) goto L40
            boolean r0 = r5.hasStickyX
            if (r0 != 0) goto L6b
            boolean r0 = r5.hasStickyY
            if (r0 == 0) goto L40
        L6b:
            android.view.ViewParent r0 = r5.getParent()
            org.telegram.ui.Components.Paint.Views.EntitiesContainerView r0 = (org.telegram.ui.Components.Paint.Views.EntitiesContainerView) r0
            r0.invalidate()
            goto L40
        L75:
            android.view.GestureDetector r0 = r5.gestureDetector
            r0.onTouchEvent(r6)
        L7a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.Paint.Views.EntityView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void runStickyXAnimator(float... fArr) {
        ValueAnimator duration = ValueAnimator.ofFloat(fArr).setDuration(150L);
        this.stickyXAnimator = duration;
        duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.stickyXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Paint.Views.EntityView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                EntityView.this.lambda$runStickyXAnimator$0(valueAnimator);
            }
        });
        this.stickyXAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Paint.Views.EntityView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator == EntityView.this.stickyXAnimator) {
                    EntityView.this.stickyXAnimator = null;
                    EntityView.this.hasFromStickyXAnimation = false;
                }
            }
        });
        this.stickyXAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runStickyXAnimator$0(ValueAnimator valueAnimator) {
        updatePosition();
    }

    private void runStickyYAnimator(float... fArr) {
        ValueAnimator duration = ValueAnimator.ofFloat(fArr).setDuration(150L);
        this.stickyYAnimator = duration;
        duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.stickyYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Paint.Views.EntityView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                EntityView.this.lambda$runStickyYAnimator$1(valueAnimator);
            }
        });
        this.stickyYAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Paint.Views.EntityView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator == EntityView.this.stickyYAnimator) {
                    EntityView.this.stickyYAnimator = null;
                    EntityView.this.hasFromStickyYAnimation = false;
                }
            }
        });
        this.stickyYAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runStickyYAnimator$1(ValueAnimator valueAnimator) {
        updatePosition();
    }

    public void pan(float f, float f2) {
        Point point = this.position;
        float f3 = point.x + f;
        point.x = f3;
        float f4 = point.y + f2;
        point.y = f4;
        if (this.hasFromStickyXAnimation) {
            this.fromStickyX = f3;
        }
        if (this.hasFromStickyYAnimation) {
            this.fromStickyY = f4;
        }
        if (((View) getParent()) != null) {
            if (this.hasStickyX) {
                if (Math.abs(this.position.x - (r9.getMeasuredWidth() / 2.0f)) > AndroidUtilities.dp(48.0f)) {
                    this.hasStickyX = false;
                    if (getParent() instanceof EntitiesContainerView) {
                        ((EntitiesContainerView) getParent()).invalidate();
                    }
                    ValueAnimator valueAnimator = this.stickyXAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    this.hasFromStickyXAnimation = true;
                    runStickyXAnimator(1.0f, 0.0f);
                }
            } else if (Math.abs(this.position.x - (r9.getMeasuredWidth() / 2.0f)) <= AndroidUtilities.dp(16.0f)) {
                this.hasStickyX = true;
                try {
                    performHapticFeedback(3, 2);
                } catch (Exception unused) {
                }
                if (getParent() instanceof EntitiesContainerView) {
                    ((EntitiesContainerView) getParent()).invalidate();
                }
                ValueAnimator valueAnimator2 = this.stickyXAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.cancel();
                }
                this.fromStickyX = this.position.x;
                this.hasFromStickyXAnimation = false;
                runStickyXAnimator(0.0f, 1.0f);
            }
            if (this.hasStickyY) {
                if (Math.abs(this.position.y - (r9.getMeasuredHeight() / 2.0f)) > AndroidUtilities.dp(48.0f)) {
                    this.hasStickyY = false;
                    if (getParent() instanceof EntitiesContainerView) {
                        ((EntitiesContainerView) getParent()).invalidate();
                    }
                    ValueAnimator valueAnimator3 = this.stickyYAnimator;
                    if (valueAnimator3 != null) {
                        valueAnimator3.cancel();
                    }
                    this.hasFromStickyYAnimation = true;
                    runStickyYAnimator(1.0f, 0.0f);
                }
            } else if (Math.abs(this.position.y - (r9.getMeasuredHeight() / 2.0f)) <= AndroidUtilities.dp(16.0f)) {
                this.hasStickyY = true;
                try {
                    performHapticFeedback(3, 2);
                } catch (Exception unused2) {
                }
                if (getParent() instanceof EntitiesContainerView) {
                    ((EntitiesContainerView) getParent()).invalidate();
                }
                ValueAnimator valueAnimator4 = this.stickyYAnimator;
                if (valueAnimator4 != null) {
                    valueAnimator4.cancel();
                }
                this.fromStickyY = this.position.y;
                this.hasFromStickyYAnimation = false;
                runStickyYAnimator(0.0f, 1.0f);
            }
        }
        updatePosition();
    }

    protected float getPositionX() {
        float f = this.position.x;
        if (getParent() == null) {
            return f;
        }
        View view = (View) getParent();
        if (this.stickyXAnimator != null) {
            return AndroidUtilities.lerp(this.fromStickyX, view.getMeasuredWidth() / 2.0f, ((Float) this.stickyXAnimator.getAnimatedValue()).floatValue());
        }
        return this.hasStickyX ? view.getMeasuredWidth() / 2.0f : f;
    }

    protected float getPositionY() {
        float f = this.position.y;
        if (getParent() == null) {
            return f;
        }
        View view = (View) getParent();
        if (this.stickyYAnimator != null) {
            return AndroidUtilities.lerp(this.fromStickyY, view.getMeasuredHeight() / 2.0f, ((Float) this.stickyYAnimator.getAnimatedValue()).floatValue());
        }
        return this.hasStickyY ? view.getMeasuredHeight() / 2.0f : f;
    }

    protected void updatePosition() {
        setX(getPositionX() - (getMeasuredWidth() / 2.0f));
        setY(getPositionY() - (getMeasuredHeight() / 2.0f));
        updateSelectionView();
    }

    public void scale(float f) {
        setScale(Math.max(getScale() * f, 0.1f));
        updateSelectionView();
    }

    public void rotate(final float f) {
        if (!this.hasStickyAngle) {
            Iterator<Integer> it = STICKY_ANGLES.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                int intValue = it.next().intValue();
                if (Math.abs(intValue - f) < 5.0f) {
                    this.currentStickyAngle = intValue;
                    this.hasStickyAngle = true;
                    try {
                        performHapticFeedback(3, 2);
                    } catch (Exception unused) {
                    }
                    ValueAnimator valueAnimator = this.angleAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    ValueAnimator valueAnimator2 = this.fromStickyAngleAnimator;
                    if (valueAnimator2 != null) {
                        valueAnimator2.cancel();
                    }
                    ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(150L);
                    this.angleAnimator = duration;
                    duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.angleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Paint.Views.EntityView$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                            EntityView.this.lambda$rotate$2(f, valueAnimator3);
                        }
                    });
                    this.angleAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Paint.Views.EntityView.4
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (animator == EntityView.this.angleAnimator) {
                                EntityView.this.angleAnimator = null;
                                EntityView.this.stickyAnimatedAngle = 0.0f;
                            }
                        }
                    });
                    this.angleAnimator.start();
                }
            }
        } else if (Math.abs(this.currentStickyAngle - f) >= 15.0f) {
            ValueAnimator valueAnimator3 = this.angleAnimator;
            if (valueAnimator3 != null) {
                valueAnimator3.cancel();
            }
            ValueAnimator valueAnimator4 = this.fromStickyAngleAnimator;
            if (valueAnimator4 != null) {
                valueAnimator4.cancel();
            }
            this.fromStickyAnimatedAngle = this.currentStickyAngle;
            this.fromStickyToAngle = f;
            ValueAnimator duration2 = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(150L);
            this.fromStickyAngleAnimator = duration2;
            duration2.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.fromStickyAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Paint.Views.EntityView$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator5) {
                    EntityView.this.lambda$rotate$3(valueAnimator5);
                }
            });
            this.fromStickyAngleAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Paint.Views.EntityView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (animator == EntityView.this.fromStickyAngleAnimator) {
                        EntityView.this.fromStickyAngleAnimator = null;
                    }
                }
            });
            this.fromStickyAngleAnimator.start();
            this.hasStickyAngle = false;
        } else if (this.angleAnimator != null) {
            f = this.stickyAnimatedAngle;
        } else {
            f = this.currentStickyAngle;
        }
        ValueAnimator valueAnimator5 = this.fromStickyAngleAnimator;
        if (valueAnimator5 != null) {
            this.fromStickyToAngle = f;
            f = AndroidUtilities.lerpAngle(this.fromStickyAnimatedAngle, f, valueAnimator5.getAnimatedFraction());
        }
        rotateInternal(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rotate$2(float f, ValueAnimator valueAnimator) {
        float lerpAngle = AndroidUtilities.lerpAngle(f, this.currentStickyAngle, valueAnimator.getAnimatedFraction());
        this.stickyAnimatedAngle = lerpAngle;
        rotateInternal(lerpAngle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rotate$3(ValueAnimator valueAnimator) {
        rotateInternal(AndroidUtilities.lerpAngle(this.fromStickyAnimatedAngle, this.fromStickyToAngle, this.fromStickyAngleAnimator.getAnimatedFraction()));
    }

    private void rotateInternal(float f) {
        setRotation(f);
        updateSelectionView();
    }

    protected Rect getSelectionBounds() {
        return new Rect(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // android.view.View
    public boolean isSelected() {
        return this.selectionView != null;
    }

    public void updateSelectionView() {
        SelectionView selectionView = this.selectionView;
        if (selectionView != null) {
            selectionView.updatePosition();
        }
    }

    public void select(ViewGroup viewGroup) {
        SelectionView createSelectionView = createSelectionView();
        createSelectionView.setAlpha(0.0f);
        createSelectionView.setScaleX(0.9f);
        createSelectionView.setScaleY(0.9f);
        createSelectionView.animate().cancel();
        createSelectionView.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(150L).setInterpolator(CubicBezierInterpolator.DEFAULT).setListener(null).start();
        this.selectionView = createSelectionView;
        viewGroup.addView(createSelectionView);
        createSelectionView.updatePosition();
    }

    public void deselect() {
        SelectionView selectionView = this.selectionView;
        if (selectionView == null || selectionView.getParent() == null) {
            return;
        }
        this.selectionView.animate().cancel();
        this.selectionView.animate().alpha(0.0f).scaleX(0.9f).scaleY(0.9f).setDuration(150L).setInterpolator(CubicBezierInterpolator.DEFAULT).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Paint.Views.EntityView.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AndroidUtilities.removeFromParent(EntityView.this.selectionView);
                EntityView.this.selectionView = null;
            }
        }).start();
    }

    public void setSelectionVisibility(boolean z) {
        SelectionView selectionView = this.selectionView;
        if (selectionView == null) {
            return;
        }
        selectionView.setVisibility(z ? 0 : 8);
    }

    public class SelectionView extends FrameLayout {
        private int currentHandle;
        protected Paint dotPaint;
        protected Paint dotStrokePaint;
        protected Paint paint;

        protected int pointInsideHandle(float f, float f2) {
            throw null;
        }

        public SelectionView(Context context) {
            super(context);
            this.paint = new Paint(1);
            this.dotPaint = new Paint(1);
            this.dotStrokePaint = new Paint(1);
            setWillNotDraw(false);
            this.paint.setColor(-1);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeWidth(AndroidUtilities.dp(2.0f));
            this.paint.setStrokeCap(Paint.Cap.ROUND);
            this.paint.setPathEffect(new DashPathEffect(new float[]{AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f)}, 0.5f));
            this.paint.setShadowLayer(AndroidUtilities.dp(0.75f), 0.0f, AndroidUtilities.dp(1.0f), 1879048192);
            this.dotPaint.setColor(-12793105);
            this.dotStrokePaint.setColor(-1);
            this.dotStrokePaint.setStyle(Paint.Style.STROKE);
            this.dotStrokePaint.setStrokeWidth(AndroidUtilities.dp(2.0f));
            this.dotStrokePaint.setShadowLayer(AndroidUtilities.dp(0.75f), 0.0f, AndroidUtilities.dp(1.0f), 1879048192);
        }

        protected void updatePosition() {
            Rect selectionBounds = EntityView.this.getSelectionBounds();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
            layoutParams.leftMargin = (int) selectionBounds.x;
            layoutParams.topMargin = (int) selectionBounds.y;
            layoutParams.width = (int) selectionBounds.width;
            layoutParams.height = (int) selectionBounds.height;
            setLayoutParams(layoutParams);
            setRotation(EntityView.this.getRotation());
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x002c, code lost:
        
            if (r1 != 6) goto L44;
         */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0143  */
        @Override // android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouchEvent(android.view.MotionEvent r18) {
            /*
                Method dump skipped, instructions count: 335
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.Paint.Views.EntityView.SelectionView.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }
}

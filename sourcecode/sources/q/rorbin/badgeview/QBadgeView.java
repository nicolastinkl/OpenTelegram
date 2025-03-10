package q.rorbin.badgeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import q.rorbin.badgeview.Badge;

/* loaded from: classes4.dex */
public class QBadgeView extends View implements Badge {
    protected ViewGroup mActivityRoot;
    protected BadgeAnimator mAnimator;
    protected float mBackgroundBorderWidth;
    protected Paint mBadgeBackgroundBorderPaint;
    protected Paint mBadgeBackgroundPaint;
    protected RectF mBadgeBackgroundRect;
    protected PointF mBadgeCenter;
    protected int mBadgeGravity;
    protected int mBadgeNumber;
    protected float mBadgePadding;
    protected String mBadgeText;
    protected Paint.FontMetrics mBadgeTextFontMetrics;
    protected TextPaint mBadgeTextPaint;
    protected RectF mBadgeTextRect;
    protected float mBadgeTextSize;
    protected Bitmap mBitmapClip;
    protected int mColorBackground;
    protected int mColorBackgroundBorder;
    protected int mColorBadgeText;
    protected PointF mControlPoint;
    protected float mDefalutRadius;
    protected PointF mDragCenter;
    protected boolean mDragOutOfRange;
    protected Path mDragPath;
    protected int mDragQuadrant;
    protected Badge.OnDragStateChangedListener mDragStateChangedListener;
    protected boolean mDraggable;
    protected boolean mDragging;
    protected Drawable mDrawableBackground;
    protected boolean mDrawableBackgroundClip;
    protected boolean mExact;
    protected float mFinalDragDistance;
    protected float mGravityOffsetX;
    protected float mGravityOffsetY;
    protected int mHeight;
    protected List<PointF> mInnertangentPoints;
    protected PointF mRowBadgeCenter;
    protected boolean mShowShadow;
    protected View mTargetView;
    protected int mWidth;

    public QBadgeView(Context context) {
        this(context, null);
    }

    private QBadgeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private QBadgeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setLayerType(1, null);
        this.mBadgeTextRect = new RectF();
        this.mBadgeBackgroundRect = new RectF();
        this.mDragPath = new Path();
        this.mBadgeCenter = new PointF();
        this.mDragCenter = new PointF();
        this.mRowBadgeCenter = new PointF();
        this.mControlPoint = new PointF();
        this.mInnertangentPoints = new ArrayList();
        TextPaint textPaint = new TextPaint();
        this.mBadgeTextPaint = textPaint;
        textPaint.setAntiAlias(true);
        this.mBadgeTextPaint.setSubpixelText(true);
        this.mBadgeTextPaint.setFakeBoldText(true);
        this.mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Paint paint = new Paint();
        this.mBadgeBackgroundPaint = paint;
        paint.setAntiAlias(true);
        this.mBadgeBackgroundPaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.mBadgeBackgroundBorderPaint = paint2;
        paint2.setAntiAlias(true);
        this.mBadgeBackgroundBorderPaint.setStyle(Paint.Style.STROKE);
        this.mColorBackground = -1552832;
        this.mColorBadgeText = -1;
        this.mBadgeTextSize = DisplayUtil.dp2px(getContext(), 11.0f);
        this.mBadgePadding = DisplayUtil.dp2px(getContext(), 5.0f);
        this.mBadgeNumber = 0;
        this.mBadgeGravity = 8388661;
        this.mGravityOffsetX = DisplayUtil.dp2px(getContext(), 1.0f);
        this.mGravityOffsetY = DisplayUtil.dp2px(getContext(), 1.0f);
        this.mFinalDragDistance = DisplayUtil.dp2px(getContext(), 90.0f);
        this.mShowShadow = true;
        this.mDrawableBackgroundClip = false;
        if (Build.VERSION.SDK_INT >= 21) {
            setTranslationZ(1000.0f);
        }
    }

    public Badge bindTarget(View view) {
        if (view == null) {
            throw new IllegalStateException("targetView can not be null");
        }
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        ViewParent parent = view.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            this.mTargetView = view;
            if (parent instanceof BadgeContainer) {
                ((BadgeContainer) parent).addView(this);
            } else {
                ViewGroup viewGroup = (ViewGroup) parent;
                int indexOfChild = viewGroup.indexOfChild(view);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                viewGroup.removeView(view);
                BadgeContainer badgeContainer = new BadgeContainer(this, getContext());
                if (viewGroup instanceof RelativeLayout) {
                    badgeContainer.setId(view.getId());
                }
                viewGroup.addView(badgeContainer, indexOfChild, layoutParams);
                badgeContainer.addView(view);
                badgeContainer.addView(this);
            }
            return this;
        }
        throw new IllegalStateException("targetView must have a parent");
    }

    public View getTargetView() {
        return this.mTargetView;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mActivityRoot == null) {
            findViewRoot(this.mTargetView);
        }
    }

    private void findViewRoot(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mActivityRoot = viewGroup;
        if (viewGroup == null) {
            findActivityRoot(view);
        }
    }

    private void findActivityRoot(View view) {
        if (view.getParent() != null && (view.getParent() instanceof View)) {
            findActivityRoot((View) view.getParent());
        } else if (view instanceof ViewGroup) {
            this.mActivityRoot = (ViewGroup) view;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0014, code lost:
    
        if (r0 != 6) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00ac  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            int r0 = r7.getActionMasked()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L45
            if (r0 == r2) goto L31
            r3 = 2
            if (r0 == r3) goto L18
            r3 = 3
            if (r0 == r3) goto L31
            r3 = 5
            if (r0 == r3) goto L45
            r3 = 6
            if (r0 == r3) goto L31
            goto La8
        L18:
            boolean r0 = r6.mDragging
            if (r0 == 0) goto La8
            android.graphics.PointF r0 = r6.mDragCenter
            float r3 = r7.getRawX()
            r0.x = r3
            android.graphics.PointF r0 = r6.mDragCenter
            float r3 = r7.getRawY()
            r0.y = r3
            r6.invalidate()
            goto La8
        L31:
            int r0 = r7.getActionIndex()
            int r0 = r7.getPointerId(r0)
            if (r0 != 0) goto La8
            boolean r0 = r6.mDragging
            if (r0 == 0) goto La8
            r6.mDragging = r1
            r6.onPointerUp()
            goto La8
        L45:
            float r0 = r7.getX()
            float r3 = r7.getY()
            boolean r4 = r6.mDraggable
            if (r4 == 0) goto La8
            int r4 = r7.getActionIndex()
            int r4 = r7.getPointerId(r4)
            if (r4 != 0) goto La8
            android.graphics.RectF r4 = r6.mBadgeBackgroundRect
            float r5 = r4.left
            int r5 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r5 <= 0) goto La8
            float r5 = r4.right
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto La8
            float r0 = r4.top
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto La8
            float r0 = r4.bottom
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 >= 0) goto La8
            java.lang.String r0 = r6.mBadgeText
            if (r0 == 0) goto La8
            r6.initRowBadgeCenter()
            r6.mDragging = r2
            r6.updataListener(r2)
            android.content.Context r0 = r6.getContext()
            r3 = 1088421888(0x40e00000, float:7.0)
            int r0 = q.rorbin.badgeview.DisplayUtil.dp2px(r0, r3)
            float r0 = (float) r0
            r6.mDefalutRadius = r0
            android.view.ViewParent r0 = r6.getParent()
            r0.requestDisallowInterceptTouchEvent(r2)
            r6.screenFromWindow(r2)
            android.graphics.PointF r0 = r6.mDragCenter
            float r3 = r7.getRawX()
            r0.x = r3
            android.graphics.PointF r0 = r6.mDragCenter
            float r3 = r7.getRawY()
            r0.y = r3
        La8:
            boolean r0 = r6.mDragging
            if (r0 != 0) goto Lb2
            boolean r7 = super.onTouchEvent(r7)
            if (r7 == 0) goto Lb3
        Lb2:
            r1 = 1
        Lb3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: q.rorbin.badgeview.QBadgeView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void onPointerUp() {
        if (this.mDragOutOfRange) {
            animateHide(this.mDragCenter);
            updataListener(5);
        } else {
            reset();
            updataListener(4);
        }
    }

    protected Bitmap createBadgeBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap(((int) this.mBadgeBackgroundRect.width()) + DisplayUtil.dp2px(getContext(), 3.0f), ((int) this.mBadgeBackgroundRect.height()) + DisplayUtil.dp2px(getContext(), 3.0f), Bitmap.Config.ARGB_8888);
        drawBadge(new Canvas(createBitmap), new PointF(r1.getWidth() / 2.0f, r1.getHeight() / 2.0f), getBadgeCircleRadius());
        return createBitmap;
    }

    protected void screenFromWindow(boolean z) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (z) {
            this.mActivityRoot.addView(this, new FrameLayout.LayoutParams(-1, -1));
        } else {
            bindTarget(this.mTargetView);
        }
    }

    private void showShadowImpl(boolean z) {
        int dp2px = DisplayUtil.dp2px(getContext(), 1.0f);
        int dp2px2 = DisplayUtil.dp2px(getContext(), 1.5f);
        int i = this.mDragQuadrant;
        if (i == 1) {
            dp2px = DisplayUtil.dp2px(getContext(), 1.0f);
            dp2px2 = DisplayUtil.dp2px(getContext(), -1.5f);
        } else if (i == 2) {
            dp2px = DisplayUtil.dp2px(getContext(), -1.0f);
            dp2px2 = DisplayUtil.dp2px(getContext(), -1.5f);
        } else if (i == 3) {
            dp2px = DisplayUtil.dp2px(getContext(), -1.0f);
            dp2px2 = DisplayUtil.dp2px(getContext(), 1.5f);
        } else if (i == 4) {
            dp2px = DisplayUtil.dp2px(getContext(), 1.0f);
            dp2px2 = DisplayUtil.dp2px(getContext(), 1.5f);
        }
        this.mBadgeBackgroundPaint.setShadowLayer(z ? DisplayUtil.dp2px(getContext(), 2.0f) : 0.0f, dp2px, dp2px2, AndroidUtilities.DARK_STATUS_BAR_OVERLAY);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mWidth = i;
        this.mHeight = i2;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        BadgeAnimator badgeAnimator = this.mAnimator;
        if (badgeAnimator != null && badgeAnimator.isRunning()) {
            this.mAnimator.draw(canvas);
            return;
        }
        if (this.mBadgeText != null) {
            initPaints();
            float badgeCircleRadius = getBadgeCircleRadius();
            float pointDistance = this.mDefalutRadius * (1.0f - (MathUtil.getPointDistance(this.mRowBadgeCenter, this.mDragCenter) / this.mFinalDragDistance));
            if (this.mDraggable && this.mDragging) {
                this.mDragQuadrant = MathUtil.getQuadrant(this.mDragCenter, this.mRowBadgeCenter);
                showShadowImpl(this.mShowShadow);
                boolean z = pointDistance < ((float) DisplayUtil.dp2px(getContext(), 1.5f));
                this.mDragOutOfRange = z;
                if (z) {
                    updataListener(3);
                    drawBadge(canvas, this.mDragCenter, badgeCircleRadius);
                    return;
                } else {
                    updataListener(2);
                    drawDragging(canvas, pointDistance, badgeCircleRadius);
                    drawBadge(canvas, this.mDragCenter, badgeCircleRadius);
                    return;
                }
            }
            findBadgeCenter();
            drawBadge(canvas, this.mBadgeCenter, badgeCircleRadius);
        }
    }

    private void initPaints() {
        showShadowImpl(this.mShowShadow);
        this.mBadgeBackgroundPaint.setColor(this.mColorBackground);
        this.mBadgeBackgroundBorderPaint.setColor(this.mColorBackgroundBorder);
        this.mBadgeBackgroundBorderPaint.setStrokeWidth(this.mBackgroundBorderWidth);
        this.mBadgeTextPaint.setColor(this.mColorBadgeText);
        this.mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void drawDragging(Canvas canvas, float f, float f2) {
        float f3;
        float f4;
        float f5;
        PointF pointF = this.mDragCenter;
        float f6 = pointF.y;
        PointF pointF2 = this.mRowBadgeCenter;
        float f7 = f6 - pointF2.y;
        float f8 = pointF.x - pointF2.x;
        this.mInnertangentPoints.clear();
        if (f8 != 0.0f) {
            double d = (-1.0d) / (f7 / f8);
            MathUtil.getInnertangentPoints(this.mDragCenter, f2, Double.valueOf(d), this.mInnertangentPoints);
            MathUtil.getInnertangentPoints(this.mRowBadgeCenter, f, Double.valueOf(d), this.mInnertangentPoints);
        } else {
            MathUtil.getInnertangentPoints(this.mDragCenter, f2, Double.valueOf(0.0d), this.mInnertangentPoints);
            MathUtil.getInnertangentPoints(this.mRowBadgeCenter, f, Double.valueOf(0.0d), this.mInnertangentPoints);
        }
        this.mDragPath.reset();
        Path path = this.mDragPath;
        PointF pointF3 = this.mRowBadgeCenter;
        float f9 = pointF3.x;
        float f10 = pointF3.y;
        int i = this.mDragQuadrant;
        path.addCircle(f9, f10, f, (i == 1 || i == 2) ? Path.Direction.CCW : Path.Direction.CW);
        PointF pointF4 = this.mControlPoint;
        PointF pointF5 = this.mRowBadgeCenter;
        float f11 = pointF5.x;
        PointF pointF6 = this.mDragCenter;
        pointF4.x = (f11 + pointF6.x) / 2.0f;
        pointF4.y = (pointF5.y + pointF6.y) / 2.0f;
        this.mDragPath.moveTo(this.mInnertangentPoints.get(2).x, this.mInnertangentPoints.get(2).y);
        Path path2 = this.mDragPath;
        PointF pointF7 = this.mControlPoint;
        path2.quadTo(pointF7.x, pointF7.y, this.mInnertangentPoints.get(0).x, this.mInnertangentPoints.get(0).y);
        this.mDragPath.lineTo(this.mInnertangentPoints.get(1).x, this.mInnertangentPoints.get(1).y);
        Path path3 = this.mDragPath;
        PointF pointF8 = this.mControlPoint;
        path3.quadTo(pointF8.x, pointF8.y, this.mInnertangentPoints.get(3).x, this.mInnertangentPoints.get(3).y);
        this.mDragPath.lineTo(this.mInnertangentPoints.get(2).x, this.mInnertangentPoints.get(2).y);
        this.mDragPath.close();
        canvas.drawPath(this.mDragPath, this.mBadgeBackgroundPaint);
        if (this.mColorBackgroundBorder == 0 || this.mBackgroundBorderWidth <= 0.0f) {
            return;
        }
        this.mDragPath.reset();
        this.mDragPath.moveTo(this.mInnertangentPoints.get(2).x, this.mInnertangentPoints.get(2).y);
        Path path4 = this.mDragPath;
        PointF pointF9 = this.mControlPoint;
        path4.quadTo(pointF9.x, pointF9.y, this.mInnertangentPoints.get(0).x, this.mInnertangentPoints.get(0).y);
        this.mDragPath.moveTo(this.mInnertangentPoints.get(1).x, this.mInnertangentPoints.get(1).y);
        Path path5 = this.mDragPath;
        PointF pointF10 = this.mControlPoint;
        path5.quadTo(pointF10.x, pointF10.y, this.mInnertangentPoints.get(3).x, this.mInnertangentPoints.get(3).y);
        int i2 = this.mDragQuadrant;
        if (i2 == 1 || i2 == 2) {
            float f12 = this.mInnertangentPoints.get(2).x;
            PointF pointF11 = this.mRowBadgeCenter;
            f3 = f12 - pointF11.x;
            f4 = pointF11.y;
            f5 = this.mInnertangentPoints.get(2).y;
        } else {
            float f13 = this.mInnertangentPoints.get(3).x;
            PointF pointF12 = this.mRowBadgeCenter;
            f3 = f13 - pointF12.x;
            f4 = pointF12.y;
            f5 = this.mInnertangentPoints.get(3).y;
        }
        double atan = Math.atan((f4 - f5) / f3);
        int i3 = this.mDragQuadrant;
        float radianToAngle = 360.0f - ((float) MathUtil.radianToAngle(MathUtil.getTanRadian(atan, i3 + (-1) == 0 ? 4 : i3 - 1)));
        if (Build.VERSION.SDK_INT >= 21) {
            Path path6 = this.mDragPath;
            PointF pointF13 = this.mRowBadgeCenter;
            float f14 = pointF13.x;
            float f15 = pointF13.y;
            path6.addArc(f14 - f, f15 - f, f14 + f, f15 + f, radianToAngle, 180.0f);
        } else {
            Path path7 = this.mDragPath;
            PointF pointF14 = this.mRowBadgeCenter;
            float f16 = pointF14.x;
            float f17 = pointF14.y;
            path7.addArc(new RectF(f16 - f, f17 - f, f16 + f, f17 + f), radianToAngle, 180.0f);
        }
        canvas.drawPath(this.mDragPath, this.mBadgeBackgroundBorderPaint);
    }

    private void drawBadge(Canvas canvas, PointF pointF, float f) {
        if (pointF.x == -1000.0f && pointF.y == -1000.0f) {
            return;
        }
        if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
            RectF rectF = this.mBadgeBackgroundRect;
            float f2 = pointF.x;
            float f3 = (int) f;
            rectF.left = f2 - f3;
            float f4 = pointF.y;
            rectF.top = f4 - f3;
            rectF.right = f2 + f3;
            rectF.bottom = f3 + f4;
            if (this.mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawCircle(f2, f4, f, this.mBadgeBackgroundPaint);
                if (this.mColorBackgroundBorder != 0 && this.mBackgroundBorderWidth > 0.0f) {
                    canvas.drawCircle(pointF.x, pointF.y, f, this.mBadgeBackgroundBorderPaint);
                }
            }
        } else {
            this.mBadgeBackgroundRect.left = pointF.x - ((this.mBadgeTextRect.width() / 2.0f) + this.mBadgePadding);
            this.mBadgeBackgroundRect.top = pointF.y - ((this.mBadgeTextRect.height() / 2.0f) + (this.mBadgePadding * 0.5f));
            this.mBadgeBackgroundRect.right = pointF.x + (this.mBadgeTextRect.width() / 2.0f) + this.mBadgePadding;
            this.mBadgeBackgroundRect.bottom = pointF.y + (this.mBadgeTextRect.height() / 2.0f) + (this.mBadgePadding * 0.5f);
            float height = this.mBadgeBackgroundRect.height() / 2.0f;
            if (this.mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawRoundRect(this.mBadgeBackgroundRect, height, height, this.mBadgeBackgroundPaint);
                if (this.mColorBackgroundBorder != 0 && this.mBackgroundBorderWidth > 0.0f) {
                    canvas.drawRoundRect(this.mBadgeBackgroundRect, height, height, this.mBadgeBackgroundBorderPaint);
                }
            }
        }
        if (this.mBadgeText.isEmpty()) {
            return;
        }
        String str = this.mBadgeText;
        float f5 = pointF.x;
        RectF rectF2 = this.mBadgeBackgroundRect;
        float f6 = rectF2.bottom + rectF2.top;
        Paint.FontMetrics fontMetrics = this.mBadgeTextFontMetrics;
        canvas.drawText(str, f5, ((f6 - fontMetrics.bottom) - fontMetrics.top) / 2.0f, this.mBadgeTextPaint);
    }

    private void drawBadgeBackground(Canvas canvas) {
        this.mBadgeBackgroundPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        RectF rectF = this.mBadgeBackgroundRect;
        int i = (int) rectF.left;
        int i2 = (int) rectF.top;
        int i3 = (int) rectF.right;
        int i4 = (int) rectF.bottom;
        if (this.mDrawableBackgroundClip) {
            i3 = i + this.mBitmapClip.getWidth();
            i4 = this.mBitmapClip.getHeight() + i2;
            canvas.saveLayer(i, i2, i3, i4, null, 31);
        }
        this.mDrawableBackground.setBounds(i, i2, i3, i4);
        this.mDrawableBackground.draw(canvas);
        if (this.mDrawableBackgroundClip) {
            this.mBadgeBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(this.mBitmapClip, i, i2, this.mBadgeBackgroundPaint);
            canvas.restore();
            this.mBadgeBackgroundPaint.setXfermode(null);
            if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
                canvas.drawCircle(this.mBadgeBackgroundRect.centerX(), this.mBadgeBackgroundRect.centerY(), this.mBadgeBackgroundRect.width() / 2.0f, this.mBadgeBackgroundBorderPaint);
                return;
            } else {
                RectF rectF2 = this.mBadgeBackgroundRect;
                canvas.drawRoundRect(rectF2, rectF2.height() / 2.0f, this.mBadgeBackgroundRect.height() / 2.0f, this.mBadgeBackgroundBorderPaint);
                return;
            }
        }
        canvas.drawRect(this.mBadgeBackgroundRect, this.mBadgeBackgroundBorderPaint);
    }

    private void createClipLayer() {
        if (this.mBadgeText != null && this.mDrawableBackgroundClip) {
            Bitmap bitmap = this.mBitmapClip;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mBitmapClip.recycle();
            }
            float badgeCircleRadius = getBadgeCircleRadius();
            if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
                int i = ((int) badgeCircleRadius) * 2;
                this.mBitmapClip = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_4444);
                new Canvas(this.mBitmapClip).drawCircle(r0.getWidth() / 2.0f, r0.getHeight() / 2.0f, r0.getWidth() / 2.0f, this.mBadgeBackgroundPaint);
                return;
            }
            this.mBitmapClip = Bitmap.createBitmap((int) (this.mBadgeTextRect.width() + (this.mBadgePadding * 2.0f)), (int) (this.mBadgeTextRect.height() + this.mBadgePadding), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(this.mBitmapClip);
            if (Build.VERSION.SDK_INT >= 21) {
                canvas.drawRoundRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), canvas.getHeight() / 2.0f, canvas.getHeight() / 2.0f, this.mBadgeBackgroundPaint);
            } else {
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight()), canvas.getHeight() / 2.0f, canvas.getHeight() / 2.0f, this.mBadgeBackgroundPaint);
            }
        }
    }

    private float getBadgeCircleRadius() {
        float width;
        float f;
        if (this.mBadgeText.isEmpty()) {
            return this.mBadgePadding;
        }
        if (this.mBadgeText.length() != 1) {
            return this.mBadgeBackgroundRect.height() / 2.0f;
        }
        if (this.mBadgeTextRect.height() > this.mBadgeTextRect.width()) {
            width = this.mBadgeTextRect.height() / 2.0f;
            f = this.mBadgePadding;
        } else {
            width = this.mBadgeTextRect.width() / 2.0f;
            f = this.mBadgePadding;
        }
        return width + (f * 0.5f);
    }

    private void findBadgeCenter() {
        float height = this.mBadgeTextRect.height() > this.mBadgeTextRect.width() ? this.mBadgeTextRect.height() : this.mBadgeTextRect.width();
        switch (this.mBadgeGravity) {
            case 17:
                PointF pointF = this.mBadgeCenter;
                pointF.x = this.mWidth / 2.0f;
                pointF.y = this.mHeight / 2.0f;
                break;
            case 49:
                PointF pointF2 = this.mBadgeCenter;
                pointF2.x = this.mWidth / 2.0f;
                pointF2.y = this.mGravityOffsetY + this.mBadgePadding + (this.mBadgeTextRect.height() / 2.0f);
                break;
            case 81:
                PointF pointF3 = this.mBadgeCenter;
                pointF3.x = this.mWidth / 2.0f;
                pointF3.y = this.mHeight - ((this.mGravityOffsetY + this.mBadgePadding) + (this.mBadgeTextRect.height() / 2.0f));
                break;
            case 8388627:
                PointF pointF4 = this.mBadgeCenter;
                pointF4.x = this.mGravityOffsetX + this.mBadgePadding + (height / 2.0f);
                pointF4.y = this.mHeight / 2.0f;
                break;
            case 8388629:
                PointF pointF5 = this.mBadgeCenter;
                pointF5.x = this.mWidth - ((this.mGravityOffsetX + this.mBadgePadding) + (height / 2.0f));
                pointF5.y = this.mHeight / 2.0f;
                break;
            case 8388659:
                PointF pointF6 = this.mBadgeCenter;
                float f = this.mGravityOffsetX;
                float f2 = this.mBadgePadding;
                pointF6.x = f + f2 + (height / 2.0f);
                pointF6.y = this.mGravityOffsetY + f2 + (this.mBadgeTextRect.height() / 2.0f);
                break;
            case 8388661:
                PointF pointF7 = this.mBadgeCenter;
                float f3 = this.mWidth;
                float f4 = this.mGravityOffsetX;
                float f5 = this.mBadgePadding;
                pointF7.x = f3 - ((f4 + f5) + (height / 2.0f));
                pointF7.y = this.mGravityOffsetY + f5 + (this.mBadgeTextRect.height() / 2.0f);
                break;
            case 8388691:
                PointF pointF8 = this.mBadgeCenter;
                float f6 = this.mGravityOffsetX;
                float f7 = this.mBadgePadding;
                pointF8.x = f6 + f7 + (height / 2.0f);
                pointF8.y = this.mHeight - ((this.mGravityOffsetY + f7) + (this.mBadgeTextRect.height() / 2.0f));
                break;
            case 8388693:
                PointF pointF9 = this.mBadgeCenter;
                float f8 = this.mWidth;
                float f9 = this.mGravityOffsetX;
                float f10 = this.mBadgePadding;
                pointF9.x = f8 - ((f9 + f10) + (height / 2.0f));
                pointF9.y = this.mHeight - ((this.mGravityOffsetY + f10) + (this.mBadgeTextRect.height() / 2.0f));
                break;
        }
        initRowBadgeCenter();
    }

    private void measureText() {
        RectF rectF = this.mBadgeTextRect;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        if (TextUtils.isEmpty(this.mBadgeText)) {
            RectF rectF2 = this.mBadgeTextRect;
            rectF2.right = 0.0f;
            rectF2.bottom = 0.0f;
        } else {
            this.mBadgeTextPaint.setTextSize(this.mBadgeTextSize);
            this.mBadgeTextRect.right = this.mBadgeTextPaint.measureText(this.mBadgeText);
            Paint.FontMetrics fontMetrics = this.mBadgeTextPaint.getFontMetrics();
            this.mBadgeTextFontMetrics = fontMetrics;
            this.mBadgeTextRect.bottom = fontMetrics.descent - fontMetrics.ascent;
        }
        createClipLayer();
    }

    private void initRowBadgeCenter() {
        getLocationOnScreen(new int[2]);
        PointF pointF = this.mRowBadgeCenter;
        PointF pointF2 = this.mBadgeCenter;
        pointF.x = pointF2.x + r0[0];
        pointF.y = pointF2.y + r0[1];
    }

    protected void animateHide(PointF pointF) {
        if (this.mBadgeText == null) {
            return;
        }
        BadgeAnimator badgeAnimator = this.mAnimator;
        if (badgeAnimator == null || !badgeAnimator.isRunning()) {
            screenFromWindow(true);
            BadgeAnimator badgeAnimator2 = new BadgeAnimator(createBadgeBitmap(), pointF, this);
            this.mAnimator = badgeAnimator2;
            badgeAnimator2.start();
            setBadgeNumber(0);
        }
    }

    public void reset() {
        PointF pointF = this.mDragCenter;
        pointF.x = -1000.0f;
        pointF.y = -1000.0f;
        this.mDragQuadrant = 4;
        screenFromWindow(false);
        getParent().requestDisallowInterceptTouchEvent(false);
        invalidate();
    }

    public Badge setBadgeNumber(int i) {
        this.mBadgeNumber = i;
        if (i < 0) {
            this.mBadgeText = "";
        } else if (i > 99) {
            this.mBadgeText = this.mExact ? String.valueOf(i) : "99+";
        } else if (i > 0 && i <= 99) {
            this.mBadgeText = String.valueOf(i);
        } else if (i == 0) {
            this.mBadgeText = null;
        }
        measureText();
        invalidate();
        return this;
    }

    public int getBadgeNumber() {
        return this.mBadgeNumber;
    }

    public String getBadgeText() {
        return this.mBadgeText;
    }

    public int getBadgeBackgroundColor() {
        return this.mColorBackground;
    }

    public Drawable getBadgeBackground() {
        return this.mDrawableBackground;
    }

    public int getBadgeTextColor() {
        return this.mColorBadgeText;
    }

    public int getBadgeGravity() {
        return this.mBadgeGravity;
    }

    private void updataListener(int i) {
        Badge.OnDragStateChangedListener onDragStateChangedListener = this.mDragStateChangedListener;
        if (onDragStateChangedListener != null) {
            onDragStateChangedListener.onDragStateChanged(i, this, this.mTargetView);
        }
    }

    public PointF getDragCenter() {
        if (this.mDraggable && this.mDragging) {
            return this.mDragCenter;
        }
        return null;
    }

    private class BadgeContainer extends ViewGroup {
        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
            if (getParent() instanceof RelativeLayout) {
                return;
            }
            super.dispatchRestoreInstanceState(sparseArray);
        }

        public BadgeContainer(QBadgeView qBadgeView, Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View childAt = getChildAt(i5);
                childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            }
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            View view = null;
            View view2 = null;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof QBadgeView) {
                    view2 = childAt;
                } else {
                    view = childAt;
                }
            }
            if (view == null) {
                super.onMeasure(i, i2);
                return;
            }
            view.measure(i, i2);
            if (view2 != null) {
                view2.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 1073741824));
            }
            setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
        }
    }
}

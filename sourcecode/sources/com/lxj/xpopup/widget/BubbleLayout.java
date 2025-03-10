package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class BubbleLayout extends FrameLayout {
    public int arrowOffset;
    boolean isLookPositionCenter;
    private int mArrowDownLeftRadius;
    private int mArrowDownRightRadius;
    private int mArrowTopLeftRadius;
    private int mArrowTopRightRadius;
    private int mBottom;
    private int mBubbleBgRes;
    private int mBubbleBorderColor;
    private Paint mBubbleBorderPaint;
    private int mBubbleBorderSize;
    private int mBubbleColor;
    private Bitmap mBubbleImageBg;
    private Paint mBubbleImageBgBeforePaint;
    private RectF mBubbleImageBgDstRectF;
    private Paint mBubbleImageBgPaint;
    private Rect mBubbleImageBgSrcRect;
    private int mBubblePadding;
    private int mBubbleRadius;
    private int mHeight;
    private int mLDR;
    private int mLTR;
    private int mLeft;
    private Look mLook;
    public int mLookLength;
    public int mLookPosition;
    public int mLookWidth;
    private Paint mPaint;
    private Path mPath;
    private int mRDR;
    private int mRTR;
    private int mRight;
    private int mShadowColor;
    private int mShadowRadius;
    private int mShadowX;
    private int mShadowY;
    private int mTop;
    private int mWidth;

    public enum Look {
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4);

        int value;

        Look(int v) {
            this.value = v;
        }

        public static Look getType(int value) {
            Look look = BOTTOM;
            if (value == 1) {
                return LEFT;
            }
            if (value != 2) {
                return value != 3 ? look : RIGHT;
            }
            return TOP;
        }
    }

    public BubbleLayout(Context context) {
        this(context, null);
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mLDR = -1;
        this.mBubbleBgRes = -1;
        this.mBubbleImageBg = null;
        this.mBubbleImageBgDstRectF = new RectF();
        this.mBubbleImageBgSrcRect = new Rect();
        this.mBubbleImageBgPaint = new Paint(5);
        this.mBubbleImageBgBeforePaint = new Paint(5);
        this.mBubbleBorderColor = -16777216;
        this.mBubbleBorderSize = 0;
        this.mBubbleBorderPaint = new Paint(5);
        this.arrowOffset = 0;
        setLayerType(1, null);
        setWillNotDraw(false);
        initAttr();
        Paint paint = new Paint(5);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPath = new Path();
        this.mBubbleImageBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    /* renamed from: com.lxj.xpopup.widget.BubbleLayout$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look;

        static {
            int[] iArr = new int[Look.values().length];
            $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look = iArr;
            try {
                iArr[Look.BOTTOM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void initPadding() {
        int i = this.mBubblePadding + this.mShadowRadius;
        int i2 = AnonymousClass1.$SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[this.mLook.ordinal()];
        if (i2 == 1) {
            setPadding(i, i, this.mShadowX + i, this.mLookLength + i + this.mShadowY);
            return;
        }
        if (i2 == 2) {
            setPadding(i, this.mLookLength + i, this.mShadowX + i, this.mShadowY + i);
        } else if (i2 == 3) {
            setPadding(this.mLookLength + i, i, this.mShadowX + i, this.mShadowY + i);
        } else {
            if (i2 != 4) {
                return;
            }
            setPadding(i, i, this.mLookLength + i + this.mShadowX, this.mShadowY + i);
        }
    }

    private void initAttr() {
        this.mLook = Look.BOTTOM;
        this.mLookPosition = 0;
        this.mLookWidth = XPopupUtils.dp2px(getContext(), 10.0f);
        this.mLookLength = XPopupUtils.dp2px(getContext(), 9.0f);
        this.mShadowX = 0;
        this.mShadowY = 0;
        this.mBubbleRadius = XPopupUtils.dp2px(getContext(), 8.0f);
        this.mLTR = -1;
        this.mRTR = -1;
        this.mRDR = -1;
        this.mLDR = -1;
        this.mArrowTopLeftRadius = XPopupUtils.dp2px(getContext(), 1.0f);
        this.mArrowTopRightRadius = XPopupUtils.dp2px(getContext(), 1.0f);
        this.mArrowDownLeftRadius = XPopupUtils.dp2px(getContext(), 1.0f);
        this.mArrowDownRightRadius = XPopupUtils.dp2px(getContext(), 1.0f);
        this.mBubblePadding = XPopupUtils.dp2px(getContext(), 0.0f);
        this.mShadowColor = -12303292;
        this.mBubbleColor = Color.parseColor("#3b3c3d");
        this.mBubbleBorderColor = 0;
        this.mBubbleBorderSize = 0;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        initData();
    }

    @Override // android.view.View
    public void invalidate() {
        initData();
        super.invalidate();
    }

    @Override // android.view.View
    public void postInvalidate() {
        initData();
        super.postInvalidate();
    }

    private void initData() {
        int i;
        int i2;
        initPadding();
        if (this.isLookPositionCenter) {
            Look look = this.mLook;
            if (look == Look.LEFT || look == Look.RIGHT) {
                i = this.mHeight / 2;
                i2 = this.mLookLength;
            } else {
                i = this.mWidth / 2;
                i2 = this.mLookWidth;
            }
            this.mLookPosition = i - (i2 / 2);
        }
        this.mLookPosition += this.arrowOffset;
        this.mPaint.setShadowLayer(this.mShadowRadius, this.mShadowX, this.mShadowY, this.mShadowColor);
        this.mBubbleBorderPaint.setColor(this.mBubbleBorderColor);
        this.mBubbleBorderPaint.setStrokeWidth(this.mBubbleBorderSize);
        this.mBubbleBorderPaint.setStyle(Paint.Style.STROKE);
        int i3 = this.mShadowRadius;
        int i4 = this.mShadowX;
        int i5 = (i4 < 0 ? -i4 : 0) + i3;
        Look look2 = this.mLook;
        this.mLeft = i5 + (look2 == Look.LEFT ? this.mLookLength : 0);
        int i6 = this.mShadowY;
        this.mTop = (i6 < 0 ? -i6 : 0) + i3 + (look2 == Look.TOP ? this.mLookLength : 0);
        this.mRight = ((this.mWidth - i3) + (i4 > 0 ? -i4 : 0)) - (look2 == Look.RIGHT ? this.mLookLength : 0);
        this.mBottom = ((this.mHeight - i3) + (i6 > 0 ? -i6 : 0)) - (look2 == Look.BOTTOM ? this.mLookLength : 0);
        this.mPaint.setColor(this.mBubbleColor);
        this.mPath.reset();
        int i7 = this.mLookPosition;
        int i8 = this.mLookLength + i7;
        int i9 = this.mBottom;
        if (i8 > i9) {
            i7 = i9 - this.mLookWidth;
        }
        int max = Math.max(i7, this.mShadowRadius);
        int i10 = this.mLookPosition;
        int i11 = this.mLookLength + i10;
        int i12 = this.mRight;
        if (i11 > i12) {
            i10 = i12 - this.mLookWidth;
        }
        int max2 = Math.max(i10, this.mShadowRadius);
        int i13 = AnonymousClass1.$SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[this.mLook.ordinal()];
        if (i13 == 1) {
            if (max2 >= getLDR() + this.mArrowDownRightRadius) {
                this.mPath.moveTo(max2 - r1, this.mBottom);
                Path path = this.mPath;
                int i14 = this.mArrowDownRightRadius;
                int i15 = this.mLookWidth;
                int i16 = this.mLookLength;
                path.rCubicTo(i14, 0.0f, ((i15 / 2.0f) - this.mArrowTopRightRadius) + i14, i16, (i15 / 2.0f) + i14, i16);
            } else {
                this.mPath.moveTo(max2 + (this.mLookWidth / 2.0f), this.mBottom + this.mLookLength);
            }
            int i17 = this.mLookWidth + max2;
            int rdr = this.mRight - getRDR();
            int i18 = this.mArrowDownLeftRadius;
            if (i17 < rdr - i18) {
                Path path2 = this.mPath;
                float f = this.mArrowTopLeftRadius;
                int i19 = this.mLookWidth;
                int i20 = this.mLookLength;
                path2.rCubicTo(f, 0.0f, i19 / 2.0f, -i20, (i19 / 2.0f) + i18, -i20);
                this.mPath.lineTo(this.mRight - getRDR(), this.mBottom);
            }
            Path path3 = this.mPath;
            int i21 = this.mRight;
            path3.quadTo(i21, this.mBottom, i21, r4 - getRDR());
            this.mPath.lineTo(this.mRight, this.mTop + getRTR());
            this.mPath.quadTo(this.mRight, this.mTop, r1 - getRTR(), this.mTop);
            this.mPath.lineTo(this.mLeft + getLTR(), this.mTop);
            Path path4 = this.mPath;
            int i22 = this.mLeft;
            path4.quadTo(i22, this.mTop, i22, r4 + getLTR());
            this.mPath.lineTo(this.mLeft, this.mBottom - getLDR());
            if (max2 >= getLDR() + this.mArrowDownRightRadius) {
                this.mPath.quadTo(this.mLeft, this.mBottom, r1 + getLDR(), this.mBottom);
            } else {
                this.mPath.quadTo(this.mLeft, this.mBottom, max2 + (this.mLookWidth / 2.0f), r3 + this.mLookLength);
            }
        } else if (i13 == 2) {
            if (max2 >= getLTR() + this.mArrowDownLeftRadius) {
                this.mPath.moveTo(max2 - r1, this.mTop);
                Path path5 = this.mPath;
                int i23 = this.mArrowDownLeftRadius;
                int i24 = this.mLookWidth;
                int i25 = this.mLookLength;
                path5.rCubicTo(i23, 0.0f, ((i24 / 2.0f) - this.mArrowTopLeftRadius) + i23, -i25, (i24 / 2.0f) + i23, -i25);
            } else {
                this.mPath.moveTo(max2 + (this.mLookWidth / 2.0f), this.mTop - this.mLookLength);
            }
            int i26 = this.mLookWidth + max2;
            int rtr = this.mRight - getRTR();
            int i27 = this.mArrowDownRightRadius;
            if (i26 < rtr - i27) {
                Path path6 = this.mPath;
                float f2 = this.mArrowTopRightRadius;
                int i28 = this.mLookWidth;
                int i29 = this.mLookLength;
                path6.rCubicTo(f2, 0.0f, i28 / 2.0f, i29, (i28 / 2.0f) + i27, i29);
                this.mPath.lineTo(this.mRight - getRTR(), this.mTop);
            }
            Path path7 = this.mPath;
            int i30 = this.mRight;
            path7.quadTo(i30, this.mTop, i30, r4 + getRTR());
            this.mPath.lineTo(this.mRight, this.mBottom - getRDR());
            this.mPath.quadTo(this.mRight, this.mBottom, r1 - getRDR(), this.mBottom);
            this.mPath.lineTo(this.mLeft + getLDR(), this.mBottom);
            Path path8 = this.mPath;
            int i31 = this.mLeft;
            path8.quadTo(i31, this.mBottom, i31, r4 - getLDR());
            this.mPath.lineTo(this.mLeft, this.mTop + getLTR());
            if (max2 >= getLTR() + this.mArrowDownLeftRadius) {
                this.mPath.quadTo(this.mLeft, this.mTop, r1 + getLTR(), this.mTop);
            } else {
                this.mPath.quadTo(this.mLeft, this.mTop, max2 + (this.mLookWidth / 2.0f), r3 - this.mLookLength);
            }
        } else if (i13 == 3) {
            if (max >= getLTR() + this.mArrowDownRightRadius) {
                this.mPath.moveTo(this.mLeft, max - r2);
                Path path9 = this.mPath;
                int i32 = this.mArrowDownRightRadius;
                int i33 = this.mLookLength;
                int i34 = this.mLookWidth;
                path9.rCubicTo(0.0f, i32, -i33, i32 + ((i34 / 2.0f) - this.mArrowTopRightRadius), -i33, (i34 / 2.0f) + i32);
            } else {
                this.mPath.moveTo(this.mLeft - this.mLookLength, max + (this.mLookWidth / 2.0f));
            }
            int i35 = this.mLookWidth + max;
            int ldr = this.mBottom - getLDR();
            int i36 = this.mArrowDownLeftRadius;
            if (i35 < ldr - i36) {
                Path path10 = this.mPath;
                float f3 = this.mArrowTopLeftRadius;
                int i37 = this.mLookLength;
                int i38 = this.mLookWidth;
                path10.rCubicTo(0.0f, f3, i37, i38 / 2.0f, i37, (i38 / 2.0f) + i36);
                this.mPath.lineTo(this.mLeft, this.mBottom - getLDR());
            }
            this.mPath.quadTo(this.mLeft, this.mBottom, r2 + getLDR(), this.mBottom);
            this.mPath.lineTo(this.mRight - getRDR(), this.mBottom);
            Path path11 = this.mPath;
            int i39 = this.mRight;
            path11.quadTo(i39, this.mBottom, i39, r4 - getRDR());
            this.mPath.lineTo(this.mRight, this.mTop + getRTR());
            this.mPath.quadTo(this.mRight, this.mTop, r2 - getRTR(), this.mTop);
            this.mPath.lineTo(this.mLeft + getLTR(), this.mTop);
            if (max >= getLTR() + this.mArrowDownRightRadius) {
                Path path12 = this.mPath;
                int i40 = this.mLeft;
                path12.quadTo(i40, this.mTop, i40, r3 + getLTR());
            } else {
                this.mPath.quadTo(this.mLeft, this.mTop, r2 - this.mLookLength, max + (this.mLookWidth / 2.0f));
            }
        } else if (i13 == 4) {
            if (max >= getRTR() + this.mArrowDownLeftRadius) {
                this.mPath.moveTo(this.mRight, max - r2);
                Path path13 = this.mPath;
                int i41 = this.mArrowDownLeftRadius;
                int i42 = this.mLookLength;
                int i43 = this.mLookWidth;
                path13.rCubicTo(0.0f, i41, i42, i41 + ((i43 / 2.0f) - this.mArrowTopLeftRadius), i42, (i43 / 2.0f) + i41);
            } else {
                this.mPath.moveTo(this.mRight + this.mLookLength, max + (this.mLookWidth / 2.0f));
            }
            int i44 = this.mLookWidth + max;
            int rdr2 = this.mBottom - getRDR();
            int i45 = this.mArrowDownRightRadius;
            if (i44 < rdr2 - i45) {
                Path path14 = this.mPath;
                float f4 = this.mArrowTopRightRadius;
                int i46 = this.mLookLength;
                int i47 = this.mLookWidth;
                path14.rCubicTo(0.0f, f4, -i46, i47 / 2.0f, -i46, (i47 / 2.0f) + i45);
                this.mPath.lineTo(this.mRight, this.mBottom - getRDR());
            }
            this.mPath.quadTo(this.mRight, this.mBottom, r2 - getRDR(), this.mBottom);
            this.mPath.lineTo(this.mLeft + getLDR(), this.mBottom);
            Path path15 = this.mPath;
            int i48 = this.mLeft;
            path15.quadTo(i48, this.mBottom, i48, r4 - getLDR());
            this.mPath.lineTo(this.mLeft, this.mTop + getLTR());
            this.mPath.quadTo(this.mLeft, this.mTop, r2 + getLTR(), this.mTop);
            this.mPath.lineTo(this.mRight - getRTR(), this.mTop);
            if (max >= getRTR() + this.mArrowDownLeftRadius) {
                Path path16 = this.mPath;
                int i49 = this.mRight;
                path16.quadTo(i49, this.mTop, i49, r3 + getRTR());
            } else {
                this.mPath.quadTo(this.mRight, this.mTop, r2 + this.mLookLength, max + (this.mLookWidth / 2.0f));
            }
        }
        this.mPath.close();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.mPath, this.mPaint);
        if (this.mBubbleImageBg != null) {
            this.mPath.computeBounds(this.mBubbleImageBgDstRectF, true);
            int saveLayer = canvas.saveLayer(this.mBubbleImageBgDstRectF, null, 31);
            canvas.drawPath(this.mPath, this.mBubbleImageBgBeforePaint);
            float width = this.mBubbleImageBgDstRectF.width() / this.mBubbleImageBgDstRectF.height();
            if (width > (this.mBubbleImageBg.getWidth() * 1.0f) / this.mBubbleImageBg.getHeight()) {
                int height = (int) ((this.mBubbleImageBg.getHeight() - (this.mBubbleImageBg.getWidth() / width)) / 2.0f);
                this.mBubbleImageBgSrcRect.set(0, height, this.mBubbleImageBg.getWidth(), ((int) (this.mBubbleImageBg.getWidth() / width)) + height);
            } else {
                int width2 = (int) ((this.mBubbleImageBg.getWidth() - (this.mBubbleImageBg.getHeight() * width)) / 2.0f);
                this.mBubbleImageBgSrcRect.set(width2, 0, ((int) (this.mBubbleImageBg.getHeight() * width)) + width2, this.mBubbleImageBg.getHeight());
            }
            canvas.drawBitmap(this.mBubbleImageBg, this.mBubbleImageBgSrcRect, this.mBubbleImageBgDstRectF, this.mBubbleImageBgPaint);
            canvas.restoreToCount(saveLayer);
        }
        if (this.mBubbleBorderSize != 0) {
            canvas.drawPath(this.mPath, this.mBubbleBorderPaint);
        }
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public Path getPath() {
        return this.mPath;
    }

    public Look getLook() {
        return this.mLook;
    }

    public int getLookPosition() {
        return this.mLookPosition;
    }

    public int getLookWidth() {
        return this.mLookWidth;
    }

    public int getLookLength() {
        return this.mLookLength;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public int getShadowRadius() {
        return this.mShadowRadius;
    }

    public int getShadowX() {
        return this.mShadowX;
    }

    public int getShadowY() {
        return this.mShadowY;
    }

    public int getBubbleRadius() {
        return this.mBubbleRadius;
    }

    public int getBubbleColor() {
        return this.mBubbleColor;
    }

    public void setBubbleColor(int mBubbleColor) {
        this.mBubbleColor = mBubbleColor;
    }

    public void setLook(Look mLook) {
        this.mLook = mLook;
        initPadding();
    }

    public void setLookPosition(int mLookPosition) {
        this.mLookPosition = mLookPosition;
    }

    public void setLookPositionCenter(boolean isCenter) {
        this.isLookPositionCenter = isCenter;
    }

    public void setLookWidth(int mLookWidth) {
        this.mLookWidth = mLookWidth;
    }

    public void setLookLength(int mLookLength) {
        this.mLookLength = mLookLength;
        initPadding();
    }

    public void setShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
    }

    public void setShadowRadius(int mShadowRadius) {
        this.mShadowRadius = mShadowRadius;
    }

    public void setShadowX(int mShadowX) {
        this.mShadowX = mShadowX;
    }

    public void setShadowY(int mShadowY) {
        this.mShadowY = mShadowY;
    }

    public void setBubbleRadius(int mBubbleRadius) {
        this.mBubbleRadius = mBubbleRadius;
    }

    public int getLTR() {
        int i = this.mLTR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setLTR(int mLTR) {
        this.mLTR = mLTR;
    }

    public int getRTR() {
        int i = this.mRTR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setRTR(int mRTR) {
        this.mRTR = mRTR;
    }

    public int getRDR() {
        int i = this.mRDR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setRDR(int mRDR) {
        this.mRDR = mRDR;
    }

    public int getLDR() {
        int i = this.mLDR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setLDR(int mLDR) {
        this.mLDR = mLDR;
    }

    public int getArrowTopLeftRadius() {
        return this.mArrowTopLeftRadius;
    }

    public void setArrowTopLeftRadius(int mArrowTopLeftRadius) {
        this.mArrowTopLeftRadius = mArrowTopLeftRadius;
    }

    public int getArrowTopRightRadius() {
        return this.mArrowTopRightRadius;
    }

    public void setArrowTopRightRadius(int mArrowTopRightRadius) {
        this.mArrowTopRightRadius = mArrowTopRightRadius;
    }

    public int getArrowDownLeftRadius() {
        return this.mArrowDownLeftRadius;
    }

    public void setArrowDownLeftRadius(int mArrowDownLeftRadius) {
        this.mArrowDownLeftRadius = mArrowDownLeftRadius;
    }

    public int getArrowDownRightRadius() {
        return this.mArrowDownRightRadius;
    }

    public void setArrowDownRightRadius(int mArrowDownRightRadius) {
        this.mArrowDownRightRadius = mArrowDownRightRadius;
    }

    public void setArrowRadius(int radius) {
        setArrowDownLeftRadius(radius);
        setArrowDownRightRadius(radius);
        setArrowTopLeftRadius(radius);
        setArrowTopRightRadius(radius);
    }

    public void setBubblePadding(int bubblePadding) {
        this.mBubblePadding = bubblePadding;
    }

    public void setBubbleImageBg(Bitmap bitmap) {
        this.mBubbleImageBg = bitmap;
    }

    public void setBubbleImageBgRes(int res) {
        this.mBubbleImageBg = BitmapFactory.decodeResource(getResources(), res);
    }

    public void setBubbleBorderSize(int bubbleBorderSize) {
        this.mBubbleBorderSize = bubbleBorderSize;
    }

    public void setBubbleBorderColor(int bubbleBorderColor) {
        this.mBubbleBorderColor = bubbleBorderColor;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mLookPosition", this.mLookPosition);
        bundle.putInt("mLookWidth", this.mLookWidth);
        bundle.putInt("mLookLength", this.mLookLength);
        bundle.putInt("mShadowColor", this.mShadowColor);
        bundle.putInt("mShadowRadius", this.mShadowRadius);
        bundle.putInt("mShadowX", this.mShadowX);
        bundle.putInt("mShadowY", this.mShadowY);
        bundle.putInt("mBubbleRadius", this.mBubbleRadius);
        bundle.putInt("mLTR", this.mLTR);
        bundle.putInt("mRTR", this.mRTR);
        bundle.putInt("mRDR", this.mRDR);
        bundle.putInt("mLDR", this.mLDR);
        bundle.putInt("mBubblePadding", this.mBubblePadding);
        bundle.putInt("mArrowTopLeftRadius", this.mArrowTopLeftRadius);
        bundle.putInt("mArrowTopRightRadius", this.mArrowTopRightRadius);
        bundle.putInt("mArrowDownLeftRadius", this.mArrowDownLeftRadius);
        bundle.putInt("mArrowDownRightRadius", this.mArrowDownRightRadius);
        bundle.putInt("mWidth", this.mWidth);
        bundle.putInt("mHeight", this.mHeight);
        bundle.putInt("mLeft", this.mLeft);
        bundle.putInt("mTop", this.mTop);
        bundle.putInt("mRight", this.mRight);
        bundle.putInt("mBottom", this.mBottom);
        bundle.putInt("mBubbleBgRes", this.mBubbleBgRes);
        bundle.putInt("mBubbleBorderColor", this.mBubbleBorderColor);
        bundle.putInt("mBubbleBorderSize", this.mBubbleBorderSize);
        return bundle;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mLookPosition = bundle.getInt("mLookPosition");
            this.mLookWidth = bundle.getInt("mLookWidth");
            this.mLookLength = bundle.getInt("mLookLength");
            this.mShadowColor = bundle.getInt("mShadowColor");
            this.mShadowRadius = bundle.getInt("mShadowRadius");
            this.mShadowX = bundle.getInt("mShadowX");
            this.mShadowY = bundle.getInt("mShadowY");
            this.mBubbleRadius = bundle.getInt("mBubbleRadius");
            this.mLTR = bundle.getInt("mLTR");
            this.mRTR = bundle.getInt("mRTR");
            this.mRDR = bundle.getInt("mRDR");
            this.mLDR = bundle.getInt("mLDR");
            this.mBubblePadding = bundle.getInt("mBubblePadding");
            this.mArrowTopLeftRadius = bundle.getInt("mArrowTopLeftRadius");
            this.mArrowTopRightRadius = bundle.getInt("mArrowTopRightRadius");
            this.mArrowDownLeftRadius = bundle.getInt("mArrowDownLeftRadius");
            this.mArrowDownRightRadius = bundle.getInt("mArrowDownRightRadius");
            this.mWidth = bundle.getInt("mWidth");
            this.mHeight = bundle.getInt("mHeight");
            this.mLeft = bundle.getInt("mLeft");
            this.mTop = bundle.getInt("mTop");
            this.mRight = bundle.getInt("mRight");
            this.mBottom = bundle.getInt("mBottom");
            int i = bundle.getInt("mBubbleBgRes");
            this.mBubbleBgRes = i;
            if (i != -1) {
                this.mBubbleImageBg = BitmapFactory.decodeResource(getResources(), this.mBubbleBgRes);
            }
            this.mBubbleBorderSize = bundle.getInt("mBubbleBorderSize");
            this.mBubbleBorderColor = bundle.getInt("mBubbleBorderColor");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}

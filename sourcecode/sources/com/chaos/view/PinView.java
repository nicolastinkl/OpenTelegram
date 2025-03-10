package com.chaos.view;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import java.util.Objects;

/* loaded from: classes.dex */
public class PinView extends AppCompatEditText {
    private boolean drawCursor;
    private boolean isAnimationEnable;
    private boolean isCursorVisible;
    private boolean isPasswordHidden;
    private final TextPaint mAnimatorTextPaint;
    private Blink mBlink;
    private int mCurLineColor;
    private int mCursorColor;
    private float mCursorHeight;
    private int mCursorWidth;
    private ValueAnimator mDefaultAddAnimator;
    private boolean mHideLineWhenFilled;
    private Drawable mItemBackground;
    private int mItemBackgroundResource;
    private final RectF mItemBorderRect;
    private final PointF mItemCenterPoint;
    private final RectF mItemLineRect;
    private ColorStateList mLineColor;
    private int mLineWidth;
    private final Paint mPaint;
    private final Path mPath;
    private int mPinItemCount;
    private int mPinItemHeight;
    private int mPinItemRadius;
    private int mPinItemSpacing;
    private int mPinItemWidth;
    private final Rect mTextRect;
    private String mTransformed;
    private int mViewType;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private static final int[] HIGHLIGHT_STATES = {R.attr.state_selected};

    private static boolean isPasswordInputType(int i) {
        int i2 = i & 4095;
        return i2 == 129 || i2 == 225 || i2 == 18;
    }

    @Override // android.widget.TextView
    public boolean isSuggestionsEnabled() {
        return false;
    }

    public PinView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.pinViewStyle);
    }

    public PinView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TextPaint textPaint = new TextPaint();
        this.mAnimatorTextPaint = textPaint;
        this.mCurLineColor = -16777216;
        this.mTextRect = new Rect();
        this.mItemBorderRect = new RectF();
        this.mItemLineRect = new RectF();
        this.mPath = new Path();
        this.mItemCenterPoint = new PointF();
        this.isAnimationEnable = false;
        Resources resources = getResources();
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        textPaint.set(getPaint());
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.PinView, i, 0);
        this.mViewType = obtainStyledAttributes.getInt(R$styleable.PinView_viewType, 0);
        this.mPinItemCount = obtainStyledAttributes.getInt(R$styleable.PinView_itemCount, 4);
        int i2 = R$styleable.PinView_itemHeight;
        int i3 = R$dimen.pv_pin_view_item_size;
        this.mPinItemHeight = (int) obtainStyledAttributes.getDimension(i2, resources.getDimensionPixelSize(i3));
        this.mPinItemWidth = (int) obtainStyledAttributes.getDimension(R$styleable.PinView_itemWidth, resources.getDimensionPixelSize(i3));
        this.mPinItemSpacing = obtainStyledAttributes.getDimensionPixelSize(R$styleable.PinView_itemSpacing, resources.getDimensionPixelSize(R$dimen.pv_pin_view_item_spacing));
        this.mPinItemRadius = (int) obtainStyledAttributes.getDimension(R$styleable.PinView_itemRadius, 0.0f);
        this.mLineWidth = (int) obtainStyledAttributes.getDimension(R$styleable.PinView_lineWidth, resources.getDimensionPixelSize(R$dimen.pv_pin_view_item_line_width));
        this.mLineColor = obtainStyledAttributes.getColorStateList(R$styleable.PinView_lineColor);
        this.isCursorVisible = obtainStyledAttributes.getBoolean(R$styleable.PinView_android_cursorVisible, true);
        this.mCursorColor = obtainStyledAttributes.getColor(R$styleable.PinView_cursorColor, getCurrentTextColor());
        this.mCursorWidth = obtainStyledAttributes.getDimensionPixelSize(R$styleable.PinView_cursorWidth, resources.getDimensionPixelSize(R$dimen.pv_pin_view_cursor_width));
        this.mItemBackground = obtainStyledAttributes.getDrawable(R$styleable.PinView_android_itemBackground);
        this.mHideLineWhenFilled = obtainStyledAttributes.getBoolean(R$styleable.PinView_hideLineWhenFilled, false);
        obtainStyledAttributes.recycle();
        ColorStateList colorStateList = this.mLineColor;
        if (colorStateList != null) {
            this.mCurLineColor = colorStateList.getDefaultColor();
        }
        updateCursorHeight();
        checkItemRadius();
        setMaxLength(this.mPinItemCount);
        paint.setStrokeWidth(this.mLineWidth);
        setupAnimator();
        setTransformationMethod(null);
        disableSelectionMenu();
        this.isPasswordHidden = isPasswordInputType(getInputType());
    }

    @Override // android.widget.TextView
    public void setInputType(int i) {
        super.setInputType(i);
        this.isPasswordHidden = isPasswordInputType(getInputType());
    }

    public void setPasswordHidden(boolean z) {
        this.isPasswordHidden = z;
        requestLayout();
    }

    @Override // android.widget.TextView
    public void setTypeface(Typeface typeface, int i) {
        super.setTypeface(typeface, i);
    }

    @Override // android.widget.TextView
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        TextPaint textPaint = this.mAnimatorTextPaint;
        if (textPaint != null) {
            textPaint.set(getPaint());
        }
    }

    private void setMaxLength(int i) {
        if (i >= 0) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
        } else {
            setFilters(NO_FILTERS);
        }
    }

    private void setupAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.5f, 1.0f);
        this.mDefaultAddAnimator = ofFloat;
        ofFloat.setDuration(150L);
        this.mDefaultAddAnimator.setInterpolator(new DecelerateInterpolator());
        this.mDefaultAddAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.chaos.view.PinView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                PinView.this.mAnimatorTextPaint.setTextSize(PinView.this.getTextSize() * floatValue);
                PinView.this.mAnimatorTextPaint.setAlpha((int) (255.0f * floatValue));
                PinView.this.postInvalidate();
            }
        });
    }

    private void checkItemRadius() {
        int i = this.mViewType;
        if (i == 1) {
            if (this.mPinItemRadius > this.mLineWidth / 2.0f) {
                throw new IllegalArgumentException("The itemRadius can not be greater than lineWidth when viewType is line");
            }
        } else if (i == 0) {
            if (this.mPinItemRadius > this.mPinItemWidth / 2.0f) {
                throw new IllegalArgumentException("The itemRadius can not be greater than itemWidth");
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i3 = this.mPinItemHeight;
        if (mode != 1073741824) {
            int i4 = this.mPinItemCount;
            size = ViewCompat.getPaddingStart(this) + ((i4 - 1) * this.mPinItemSpacing) + (i4 * this.mPinItemWidth) + ViewCompat.getPaddingEnd(this);
            if (this.mPinItemSpacing == 0) {
                size -= (this.mPinItemCount - 1) * this.mLineWidth;
            }
        }
        if (mode2 != 1073741824) {
            size2 = getPaddingBottom() + i3 + getPaddingTop();
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        ValueAnimator valueAnimator;
        if (i != charSequence.length()) {
            moveSelectionToEnd();
        }
        makeBlink();
        if (this.isAnimationEnable) {
            if ((i3 - i2 > 0) && (valueAnimator = this.mDefaultAddAnimator) != null) {
                valueAnimator.end();
                this.mDefaultAddAnimator.start();
            }
        }
        TransformationMethod transformationMethod = getTransformationMethod();
        if (transformationMethod == null) {
            this.mTransformed = getText().toString();
        } else {
            this.mTransformed = transformationMethod.getTransformation(getText(), this).toString();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z) {
            moveSelectionToEnd();
            makeBlink();
        }
    }

    @Override // android.widget.TextView
    protected void onSelectionChanged(int i, int i2) {
        super.onSelectionChanged(i, i2);
        if (i2 != getText().length()) {
            moveSelectionToEnd();
        }
    }

    private void moveSelectionToEnd() {
        setSelection(getText().length());
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        ColorStateList colorStateList = this.mLineColor;
        if (colorStateList == null || colorStateList.isStateful()) {
            updateColors();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.save();
        updatePaints();
        drawPinView(canvas);
        canvas.restore();
    }

    private void updatePaints() {
        this.mPaint.setColor(this.mCurLineColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.mLineWidth);
        getPaint().setColor(getCurrentTextColor());
    }

    private void drawPinView(Canvas canvas) {
        int length = getText().length();
        int i = 0;
        while (i < this.mPinItemCount) {
            boolean z = isFocused() && length == i;
            this.mPaint.setColor(z ? getLineColorForState(HIGHLIGHT_STATES) : this.mCurLineColor);
            updateItemRectF(i);
            updateCenterPoint();
            canvas.save();
            if (this.mViewType == 0) {
                updatePinBoxPath(i);
                canvas.clipPath(this.mPath);
            }
            drawItemBackground(canvas, z);
            canvas.restore();
            if (z) {
                drawCursor(canvas);
            }
            int i2 = this.mViewType;
            if (i2 == 0) {
                drawPinBox(canvas, i);
            } else if (i2 == 1) {
                drawPinLine(canvas, i);
            }
            if (this.mTransformed.length() > i) {
                if (getTransformationMethod() == null && this.isPasswordHidden) {
                    drawCircle(canvas, i);
                } else {
                    drawText(canvas, i);
                }
            } else if (!TextUtils.isEmpty(getHint()) && getHint().length() == this.mPinItemCount) {
                drawHint(canvas, i);
            }
            i++;
        }
        if (isFocused() && getText().length() != this.mPinItemCount && this.mViewType == 0) {
            int length2 = getText().length();
            updateItemRectF(length2);
            updateCenterPoint();
            updatePinBoxPath(length2);
            this.mPaint.setColor(getLineColorForState(HIGHLIGHT_STATES));
            drawPinBox(canvas, length2);
        }
    }

    private int getLineColorForState(int... iArr) {
        ColorStateList colorStateList = this.mLineColor;
        return colorStateList != null ? colorStateList.getColorForState(iArr, this.mCurLineColor) : this.mCurLineColor;
    }

    private void drawItemBackground(Canvas canvas, boolean z) {
        if (this.mItemBackground == null) {
            return;
        }
        float f = this.mLineWidth / 2.0f;
        this.mItemBackground.setBounds(Math.round(this.mItemBorderRect.left - f), Math.round(this.mItemBorderRect.top - f), Math.round(this.mItemBorderRect.right + f), Math.round(this.mItemBorderRect.bottom + f));
        this.mItemBackground.setState(z ? HIGHLIGHT_STATES : getDrawableState());
        this.mItemBackground.draw(canvas);
    }

    private void updatePinBoxPath(int i) {
        boolean z;
        boolean z2;
        if (this.mPinItemSpacing != 0) {
            z = true;
        } else {
            boolean z3 = i == 0 && i != this.mPinItemCount - 1;
            if (i != this.mPinItemCount - 1 || i == 0) {
                z = z3;
                z2 = false;
                RectF rectF = this.mItemBorderRect;
                int i2 = this.mPinItemRadius;
                updateRoundRectPath(rectF, i2, i2, z, z2);
            }
            z = z3;
        }
        z2 = true;
        RectF rectF2 = this.mItemBorderRect;
        int i22 = this.mPinItemRadius;
        updateRoundRectPath(rectF2, i22, i22, z, z2);
    }

    private void drawPinBox(Canvas canvas, int i) {
        if (!this.mHideLineWhenFilled || i >= getText().length()) {
            canvas.drawPath(this.mPath, this.mPaint);
        }
    }

    private void drawPinLine(Canvas canvas, int i) {
        boolean z;
        boolean z2;
        int i2;
        if (!this.mHideLineWhenFilled || i >= getText().length()) {
            if (this.mPinItemSpacing == 0 && (i2 = this.mPinItemCount) > 1) {
                if (i == 0) {
                    z = true;
                } else if (i == i2 - 1) {
                    z = false;
                } else {
                    z = false;
                }
                z2 = false;
                this.mPaint.setStyle(Paint.Style.FILL);
                this.mPaint.setStrokeWidth(this.mLineWidth / 10.0f);
                float f = this.mLineWidth / 2.0f;
                RectF rectF = this.mItemLineRect;
                RectF rectF2 = this.mItemBorderRect;
                float f2 = rectF2.left - f;
                float f3 = rectF2.bottom;
                rectF.set(f2, f3 - f, rectF2.right + f, f3 + f);
                RectF rectF3 = this.mItemLineRect;
                int i3 = this.mPinItemRadius;
                updateRoundRectPath(rectF3, i3, i3, z, z2);
                canvas.drawPath(this.mPath, this.mPaint);
            }
            z = true;
            z2 = true;
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setStrokeWidth(this.mLineWidth / 10.0f);
            float f4 = this.mLineWidth / 2.0f;
            RectF rectF4 = this.mItemLineRect;
            RectF rectF22 = this.mItemBorderRect;
            float f22 = rectF22.left - f4;
            float f32 = rectF22.bottom;
            rectF4.set(f22, f32 - f4, rectF22.right + f4, f32 + f4);
            RectF rectF32 = this.mItemLineRect;
            int i32 = this.mPinItemRadius;
            updateRoundRectPath(rectF32, i32, i32, z, z2);
            canvas.drawPath(this.mPath, this.mPaint);
        }
    }

    private void drawCursor(Canvas canvas) {
        if (this.drawCursor) {
            PointF pointF = this.mItemCenterPoint;
            float f = pointF.x;
            float f2 = pointF.y - (this.mCursorHeight / 2.0f);
            int color = this.mPaint.getColor();
            float strokeWidth = this.mPaint.getStrokeWidth();
            this.mPaint.setColor(this.mCursorColor);
            this.mPaint.setStrokeWidth(this.mCursorWidth);
            canvas.drawLine(f, f2, f, f2 + this.mCursorHeight, this.mPaint);
            this.mPaint.setColor(color);
            this.mPaint.setStrokeWidth(strokeWidth);
        }
    }

    private void updateRoundRectPath(RectF rectF, float f, float f2, boolean z, boolean z2) {
        updateRoundRectPath(rectF, f, f2, z, z2, z2, z);
    }

    private void updateRoundRectPath(RectF rectF, float f, float f2, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mPath.reset();
        float f3 = rectF.left;
        float f4 = rectF.top;
        float f5 = (rectF.right - f3) - (f * 2.0f);
        float f6 = (rectF.bottom - f4) - (2.0f * f2);
        this.mPath.moveTo(f3, f4 + f2);
        if (z) {
            float f7 = -f2;
            this.mPath.rQuadTo(0.0f, f7, f, f7);
        } else {
            this.mPath.rLineTo(0.0f, -f2);
            this.mPath.rLineTo(f, 0.0f);
        }
        this.mPath.rLineTo(f5, 0.0f);
        if (z2) {
            this.mPath.rQuadTo(f, 0.0f, f, f2);
        } else {
            this.mPath.rLineTo(f, 0.0f);
            this.mPath.rLineTo(0.0f, f2);
        }
        this.mPath.rLineTo(0.0f, f6);
        if (z3) {
            this.mPath.rQuadTo(0.0f, f2, -f, f2);
        } else {
            this.mPath.rLineTo(0.0f, f2);
            this.mPath.rLineTo(-f, 0.0f);
        }
        this.mPath.rLineTo(-f5, 0.0f);
        if (z4) {
            float f8 = -f;
            this.mPath.rQuadTo(f8, 0.0f, f8, -f2);
        } else {
            this.mPath.rLineTo(-f, 0.0f);
            this.mPath.rLineTo(0.0f, -f2);
        }
        this.mPath.rLineTo(0.0f, -f6);
        this.mPath.close();
    }

    private void updateItemRectF(int i) {
        float f = this.mLineWidth / 2.0f;
        int scrollX = getScrollX() + ViewCompat.getPaddingStart(this);
        int i2 = this.mPinItemSpacing;
        int i3 = this.mPinItemWidth;
        float f2 = scrollX + ((i2 + i3) * i) + f;
        if (i2 == 0 && i > 0) {
            f2 -= this.mLineWidth * i;
        }
        float scrollY = getScrollY() + getPaddingTop() + f;
        this.mItemBorderRect.set(f2, scrollY, (i3 + f2) - this.mLineWidth, (this.mPinItemHeight + scrollY) - this.mLineWidth);
    }

    private void drawText(Canvas canvas, int i) {
        drawTextAtBox(canvas, getPaintByIndex(i), this.mTransformed, i);
    }

    private void drawHint(Canvas canvas, int i) {
        Paint paintByIndex = getPaintByIndex(i);
        paintByIndex.setColor(getCurrentHintTextColor());
        drawTextAtBox(canvas, paintByIndex, getHint(), i);
    }

    private void drawTextAtBox(Canvas canvas, Paint paint, CharSequence charSequence, int i) {
        int i2 = i + 1;
        paint.getTextBounds(charSequence.toString(), i, i2, this.mTextRect);
        PointF pointF = this.mItemCenterPoint;
        float f = pointF.x;
        float f2 = pointF.y;
        float abs = f - (Math.abs(this.mTextRect.width()) / 2.0f);
        Rect rect = this.mTextRect;
        canvas.drawText(charSequence, i, i2, abs - rect.left, (f2 + (Math.abs(rect.height()) / 2.0f)) - this.mTextRect.bottom, paint);
    }

    private void drawCircle(Canvas canvas, int i) {
        Paint paintByIndex = getPaintByIndex(i);
        PointF pointF = this.mItemCenterPoint;
        canvas.drawCircle(pointF.x, pointF.y, paintByIndex.getTextSize() / 2.0f, paintByIndex);
    }

    private Paint getPaintByIndex(int i) {
        if (this.isAnimationEnable && i == getText().length() - 1) {
            this.mAnimatorTextPaint.setColor(getPaint().getColor());
            return this.mAnimatorTextPaint;
        }
        return getPaint();
    }

    private void updateColors() {
        int currentTextColor;
        ColorStateList colorStateList = this.mLineColor;
        boolean z = false;
        if (colorStateList != null) {
            currentTextColor = colorStateList.getColorForState(getDrawableState(), 0);
        } else {
            currentTextColor = getCurrentTextColor();
        }
        if (currentTextColor != this.mCurLineColor) {
            this.mCurLineColor = currentTextColor;
            z = true;
        }
        if (z) {
            invalidate();
        }
    }

    private void updateCenterPoint() {
        RectF rectF = this.mItemBorderRect;
        float abs = rectF.left + (Math.abs(rectF.width()) / 2.0f);
        RectF rectF2 = this.mItemBorderRect;
        this.mItemCenterPoint.set(abs, rectF2.top + (Math.abs(rectF2.height()) / 2.0f));
    }

    @Override // android.widget.EditText, android.widget.TextView
    protected MovementMethod getDefaultMovementMethod() {
        return DefaultMovementMethod.getInstance();
    }

    public void setLineColor(int i) {
        this.mLineColor = ColorStateList.valueOf(i);
        updateColors();
    }

    public void setLineColor(ColorStateList colorStateList) {
        Objects.requireNonNull(colorStateList);
        this.mLineColor = colorStateList;
        updateColors();
    }

    public ColorStateList getLineColors() {
        return this.mLineColor;
    }

    public int getCurrentLineColor() {
        return this.mCurLineColor;
    }

    public void setLineWidth(int i) {
        this.mLineWidth = i;
        checkItemRadius();
        requestLayout();
    }

    public int getLineWidth() {
        return this.mLineWidth;
    }

    public void setItemCount(int i) {
        this.mPinItemCount = i;
        setMaxLength(i);
        requestLayout();
    }

    public int getItemCount() {
        return this.mPinItemCount;
    }

    public void setItemRadius(int i) {
        this.mPinItemRadius = i;
        checkItemRadius();
        requestLayout();
    }

    public int getItemRadius() {
        return this.mPinItemRadius;
    }

    public void setItemSpacing(int i) {
        this.mPinItemSpacing = i;
        requestLayout();
    }

    public int getItemSpacing() {
        return this.mPinItemSpacing;
    }

    public void setItemHeight(int i) {
        this.mPinItemHeight = i;
        updateCursorHeight();
        requestLayout();
    }

    public int getItemHeight() {
        return this.mPinItemHeight;
    }

    public void setItemWidth(int i) {
        this.mPinItemWidth = i;
        checkItemRadius();
        requestLayout();
    }

    public int getItemWidth() {
        return this.mPinItemWidth;
    }

    public void setAnimationEnable(boolean z) {
        this.isAnimationEnable = z;
    }

    public void setHideLineWhenFilled(boolean z) {
        this.mHideLineWhenFilled = z;
    }

    @Override // android.widget.TextView
    public void setTextSize(float f) {
        super.setTextSize(f);
        updateCursorHeight();
    }

    @Override // android.widget.TextView
    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        updateCursorHeight();
    }

    public void setItemBackgroundResources(int i) {
        if (i == 0 || this.mItemBackgroundResource == i) {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), i, getContext().getTheme());
            this.mItemBackground = drawable;
            setItemBackground(drawable);
            this.mItemBackgroundResource = i;
        }
    }

    public void setItemBackgroundColor(int i) {
        Drawable drawable = this.mItemBackground;
        if (drawable instanceof ColorDrawable) {
            ((ColorDrawable) drawable.mutate()).setColor(i);
            this.mItemBackgroundResource = 0;
        } else {
            setItemBackground(new ColorDrawable(i));
        }
    }

    public void setItemBackground(Drawable drawable) {
        this.mItemBackgroundResource = 0;
        this.mItemBackground = drawable;
        invalidate();
    }

    public void setCursorWidth(int i) {
        this.mCursorWidth = i;
        if (isCursorVisible()) {
            invalidateCursor(true);
        }
    }

    public int getCursorWidth() {
        return this.mCursorWidth;
    }

    public void setCursorColor(int i) {
        this.mCursorColor = i;
        if (isCursorVisible()) {
            invalidateCursor(true);
        }
    }

    public int getCursorColor() {
        return this.mCursorColor;
    }

    @Override // android.widget.TextView
    public void setCursorVisible(boolean z) {
        if (this.isCursorVisible != z) {
            this.isCursorVisible = z;
            invalidateCursor(z);
            makeBlink();
        }
    }

    @Override // android.widget.TextView
    public boolean isCursorVisible() {
        return this.isCursorVisible;
    }

    @Override // android.widget.TextView, android.view.View
    public void onScreenStateChanged(int i) {
        super.onScreenStateChanged(i);
        if (i == 0) {
            suspendBlink();
        } else {
            if (i != 1) {
                return;
            }
            resumeBlink();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        resumeBlink();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        suspendBlink();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldBlink() {
        return isCursorVisible() && isFocused();
    }

    private void makeBlink() {
        if (shouldBlink()) {
            if (this.mBlink == null) {
                this.mBlink = new Blink();
            }
            removeCallbacks(this.mBlink);
            this.drawCursor = false;
            postDelayed(this.mBlink, 500L);
            return;
        }
        Blink blink = this.mBlink;
        if (blink != null) {
            removeCallbacks(blink);
        }
    }

    private void suspendBlink() {
        Blink blink = this.mBlink;
        if (blink != null) {
            blink.cancel();
            invalidateCursor(false);
        }
    }

    private void resumeBlink() {
        Blink blink = this.mBlink;
        if (blink != null) {
            blink.uncancel();
            makeBlink();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateCursor(boolean z) {
        if (this.drawCursor != z) {
            this.drawCursor = z;
            invalidate();
        }
    }

    private void updateCursorHeight() {
        float dpToPx = dpToPx(2.0f) * 2;
        this.mCursorHeight = ((float) this.mPinItemHeight) - getTextSize() > dpToPx ? getTextSize() + dpToPx : getTextSize();
    }

    private class Blink implements Runnable {
        private boolean mCancelled;

        private Blink() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mCancelled) {
                return;
            }
            PinView.this.removeCallbacks(this);
            if (PinView.this.shouldBlink()) {
                PinView.this.invalidateCursor(!r0.drawCursor);
                PinView.this.postDelayed(this, 500L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancel() {
            if (this.mCancelled) {
                return;
            }
            PinView.this.removeCallbacks(this);
            this.mCancelled = true;
        }

        void uncancel() {
            this.mCancelled = false;
        }
    }

    private void disableSelectionMenu() {
        setCustomSelectionActionModeCallback(new DefaultActionModeCallback());
        if (Build.VERSION.SDK_INT >= 26) {
            setCustomInsertionActionModeCallback(new DefaultActionModeCallback(this) { // from class: com.chaos.view.PinView.2
                @Override // com.chaos.view.PinView.DefaultActionModeCallback, android.view.ActionMode.Callback
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    menu.removeItem(R.id.autofill);
                    return true;
                }
            });
        }
    }

    private int dpToPx(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }

    private static class DefaultActionModeCallback implements ActionMode.Callback {
        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        private DefaultActionModeCallback() {
        }
    }
}

package com.youth.banner.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class RoundLinesIndicator extends BaseIndicator {
    public RoundLinesIndicator(Context context) {
        this(context, null);
    }

    public RoundLinesIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundLinesIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int indicatorSize = this.config.getIndicatorSize();
        if (indicatorSize <= 1) {
            return;
        }
        setMeasuredDimension(this.config.getSelectedWidth() * indicatorSize, this.config.getHeight());
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.config.getIndicatorSize() <= 1) {
            return;
        }
        this.mPaint.setColor(this.config.getNormalColor());
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, canvas.getWidth(), this.config.getHeight()), this.config.getRadius(), this.config.getRadius(), this.mPaint);
        this.mPaint.setColor(this.config.getSelectedColor());
        canvas.drawRoundRect(new RectF(this.config.getCurrentPosition() * this.config.getSelectedWidth(), 0.0f, r0 + this.config.getSelectedWidth(), this.config.getHeight()), this.config.getRadius(), this.config.getRadius(), this.mPaint);
    }
}

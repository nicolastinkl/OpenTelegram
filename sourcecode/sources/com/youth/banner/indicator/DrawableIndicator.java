package com.youth.banner.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import com.youth.banner.R;

/* loaded from: classes.dex */
public class DrawableIndicator extends BaseIndicator {
    private Bitmap normalBitmap;
    private Bitmap selectedBitmap;

    public DrawableIndicator(Context context, int i, int i2) {
        super(context);
        this.normalBitmap = BitmapFactory.decodeResource(getResources(), i);
        this.selectedBitmap = BitmapFactory.decodeResource(getResources(), i2);
    }

    public DrawableIndicator(Context context) {
        this(context, null);
    }

    public DrawableIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DrawableIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DrawableIndicator);
        if (obtainStyledAttributes != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) obtainStyledAttributes.getDrawable(R.styleable.DrawableIndicator_normal_drawable);
            BitmapDrawable bitmapDrawable2 = (BitmapDrawable) obtainStyledAttributes.getDrawable(R.styleable.DrawableIndicator_selected_drawable);
            this.normalBitmap = bitmapDrawable.getBitmap();
            this.selectedBitmap = bitmapDrawable2.getBitmap();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int indicatorSize = this.config.getIndicatorSize();
        if (indicatorSize <= 1) {
            return;
        }
        int i3 = indicatorSize - 1;
        setMeasuredDimension((this.selectedBitmap.getWidth() * i3) + this.selectedBitmap.getWidth() + (this.config.getIndicatorSpace() * i3), Math.max(this.normalBitmap.getHeight(), this.selectedBitmap.getHeight()));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int indicatorSize = this.config.getIndicatorSize();
        if (indicatorSize <= 1 || this.normalBitmap == null || this.selectedBitmap == null) {
            return;
        }
        int i = 0;
        float f = 0.0f;
        while (i < indicatorSize) {
            canvas.drawBitmap(this.config.getCurrentPosition() == i ? this.selectedBitmap : this.normalBitmap, f, 0.0f, this.mPaint);
            f += this.normalBitmap.getWidth() + this.config.getIndicatorSpace();
            i++;
        }
    }
}

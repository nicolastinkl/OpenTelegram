package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import org.telegram.messenger.AndroidUtilities;

/* loaded from: classes4.dex */
public class CircularProgressDrawable extends Drawable {
    private static final FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();
    private float angleOffset;
    private final RectF bounds;
    private final Paint paint;
    private float[] segment;
    private float size;
    private long start;
    private float thickness;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public CircularProgressDrawable(int i) {
        this.size = AndroidUtilities.dp(18.0f);
        this.thickness = AndroidUtilities.dp(2.25f);
        this.start = -1L;
        this.segment = new float[2];
        Paint paint = new Paint();
        this.paint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.bounds = new RectF();
        setColor(i);
    }

    public CircularProgressDrawable(float f, float f2, int i) {
        this.size = AndroidUtilities.dp(18.0f);
        this.thickness = AndroidUtilities.dp(2.25f);
        this.start = -1L;
        this.segment = new float[2];
        Paint paint = new Paint();
        this.paint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.bounds = new RectF();
        this.size = f;
        this.thickness = f2;
        setColor(i);
    }

    private void updateSegment() {
        getSegments((SystemClock.elapsedRealtime() - this.start) % 5400, this.segment);
    }

    public static void getSegments(float f, float[] fArr) {
        float f2 = (1520.0f * f) / 5400.0f;
        fArr[0] = Math.max(0.0f, f2 - 20.0f);
        fArr[1] = f2;
        for (int i = 0; i < 4; i++) {
            float f3 = fArr[1];
            FastOutSlowInInterpolator fastOutSlowInInterpolator = interpolator;
            fArr[1] = f3 + (fastOutSlowInInterpolator.getInterpolation((f - (i * 1350)) / 667.0f) * 250.0f);
            fArr[0] = fArr[0] + (fastOutSlowInInterpolator.getInterpolation((f - (r5 + 667)) / 667.0f) * 250.0f);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.start < 0) {
            this.start = SystemClock.elapsedRealtime();
        }
        updateSegment();
        RectF rectF = this.bounds;
        float f = this.angleOffset;
        float[] fArr = this.segment;
        canvas.drawArc(rectF, fArr[0] + f, fArr[1] - fArr[0], false, this.paint);
        invalidateSelf();
    }

    public void reset() {
        this.start = -1L;
    }

    public void setAngleOffset(float f) {
        this.angleOffset = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        RectF rectF = this.bounds;
        float f = i;
        float f2 = i3 - i;
        float f3 = this.thickness;
        float f4 = this.size;
        float f5 = i2;
        float f6 = i4 - i2;
        rectF.set((((f2 - (f3 / 2.0f)) - f4) / 2.0f) + f, (((f6 - (f3 / 2.0f)) - f4) / 2.0f) + f5, f + (((f2 + (f3 / 2.0f)) + f4) / 2.0f), f5 + (((f6 + (f3 / 2.0f)) + f4) / 2.0f));
        super.setBounds(i, i2, i3, i4);
        this.paint.setStrokeWidth(this.thickness);
    }

    public void setColor(int i) {
        this.paint.setColor(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.paint.setAlpha(i);
    }
}

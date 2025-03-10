package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes.dex */
public class BlankView extends View {
    public int color;
    private Paint paint;
    public int radius;
    private RectF rect;
    public int strokeColor;

    public BlankView(Context context) {
        super(context);
        this.paint = new Paint();
        this.rect = null;
        this.radius = 0;
        this.color = -1;
        this.strokeColor = Color.parseColor("#DDDDDD");
    }

    public BlankView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        this.rect = null;
        this.radius = 0;
        this.color = -1;
        this.strokeColor = Color.parseColor("#DDDDDD");
        init();
    }

    public BlankView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.paint = new Paint();
        this.rect = null;
        this.radius = 0;
        this.color = -1;
        this.strokeColor = Color.parseColor("#DDDDDD");
        init();
    }

    private void init() {
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(1.0f);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.rect = new RectF(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(this.color);
        RectF rectF = this.rect;
        int i = this.radius;
        canvas.drawRoundRect(rectF, i, i, this.paint);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.strokeColor);
        RectF rectF2 = this.rect;
        int i2 = this.radius;
        canvas.drawRoundRect(rectF2, i2, i2, this.paint);
        this.paint.setStyle(Paint.Style.FILL);
    }
}

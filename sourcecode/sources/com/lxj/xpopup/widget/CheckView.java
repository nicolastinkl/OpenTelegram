package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class CheckView extends View {
    int color;
    Paint paint;
    Path path;

    public CheckView(Context context) {
        this(context, null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.color = 0;
        this.path = new Path();
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setStrokeWidth(XPopupUtils.dp2px(context, 2.0f));
        this.paint.setStyle(Paint.Style.STROKE);
    }

    public void setColor(int color) {
        this.color = color;
        this.paint.setColor(color);
        postInvalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.color == 0) {
            return;
        }
        this.path.moveTo(getMeasuredWidth() / 4.0f, getMeasuredHeight() / 2.0f);
        this.path.lineTo(getMeasuredWidth() / 2.0f, (getMeasuredHeight() * 3) / 4.0f);
        this.path.lineTo(getMeasuredWidth(), getMeasuredHeight() / 4.0f);
        canvas.drawPath(this.path, this.paint);
    }
}

package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

/* loaded from: classes4.dex */
public class LoadingSpan extends ReplacementSpan {
    private LoadingDrawable drawable;
    private int size;
    private View view;
    public int yOffset;

    public LoadingSpan(View view, int i) {
        this(view, i, AndroidUtilities.dp(2.0f));
    }

    public LoadingSpan(View view, int i, int i2) {
        this.view = view;
        this.size = i;
        this.yOffset = i2;
        LoadingDrawable loadingDrawable = new LoadingDrawable(null);
        this.drawable = loadingDrawable;
        loadingDrawable.setRadiiDp(4.0f);
    }

    public void setColors(int i, int i2) {
        this.drawable.color1 = Integer.valueOf(i);
        this.drawable.color2 = Integer.valueOf(i2);
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        if (paint != null) {
            this.drawable.setColors(Theme.multAlpha(paint.getColor(), 0.1f), Theme.multAlpha(paint.getColor(), 0.25f));
            this.drawable.setAlpha(paint.getAlpha());
        }
        return this.size;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        int i6 = (int) f;
        this.drawable.setBounds(i6, i3 + this.yOffset, this.size + i6, (i5 - AndroidUtilities.dp(2.0f)) + this.yOffset);
        this.drawable.draw(canvas);
        View view = this.view;
        if (view != null) {
            view.invalidate();
        }
    }
}

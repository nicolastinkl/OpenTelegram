package com.youth.banner.indicator;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.youth.banner.config.IndicatorConfig;

/* loaded from: classes.dex */
public class BaseIndicator extends View implements Indicator {
    protected IndicatorConfig config;
    protected Paint mPaint;
    protected float offset;

    @Override // com.youth.banner.listener.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    public BaseIndicator(Context context) {
        this(context, null);
    }

    public BaseIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.config = new IndicatorConfig();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setColor(0);
        this.mPaint.setColor(this.config.getNormalColor());
    }

    @Override // com.youth.banner.indicator.Indicator
    public View getIndicatorView() {
        if (this.config.isAttachToBanner()) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            int gravity = this.config.getGravity();
            if (gravity == 0) {
                layoutParams.gravity = 8388691;
            } else if (gravity == 1) {
                layoutParams.gravity = 81;
            } else if (gravity == 2) {
                layoutParams.gravity = 8388693;
            }
            layoutParams.leftMargin = this.config.getMargins().leftMargin;
            layoutParams.rightMargin = this.config.getMargins().rightMargin;
            layoutParams.topMargin = this.config.getMargins().topMargin;
            layoutParams.bottomMargin = this.config.getMargins().bottomMargin;
            setLayoutParams(layoutParams);
        }
        return this;
    }

    @Override // com.youth.banner.indicator.Indicator
    public IndicatorConfig getIndicatorConfig() {
        return this.config;
    }

    @Override // com.youth.banner.indicator.Indicator
    public void onPageChanged(int i, int i2) {
        this.config.setIndicatorSize(i);
        this.config.setCurrentPosition(i2);
        requestLayout();
    }

    @Override // com.youth.banner.listener.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        this.offset = f;
        invalidate();
    }

    @Override // com.youth.banner.listener.OnPageChangeListener
    public void onPageSelected(int i) {
        this.config.setCurrentPosition(i);
        invalidate();
    }
}

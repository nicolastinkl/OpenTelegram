package com.youth.banner.indicator;

import android.view.View;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.listener.OnPageChangeListener;

/* loaded from: classes.dex */
public interface Indicator extends OnPageChangeListener {
    IndicatorConfig getIndicatorConfig();

    View getIndicatorView();

    void onPageChanged(int i, int i2);
}

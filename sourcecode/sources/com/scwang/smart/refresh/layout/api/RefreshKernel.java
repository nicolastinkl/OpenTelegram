package com.scwang.smart.refresh.layout.api;

import android.animation.ValueAnimator;
import com.scwang.smart.refresh.layout.constant.RefreshState;

/* loaded from: classes.dex */
public interface RefreshKernel {
    ValueAnimator animSpinner(int i);

    RefreshKernel finishTwoLevel();

    RefreshLayout getRefreshLayout();

    RefreshKernel moveSpinner(int i, boolean z);

    RefreshKernel requestDrawBackgroundFor(RefreshComponent refreshComponent, int i);

    RefreshKernel setState(RefreshState refreshState);
}

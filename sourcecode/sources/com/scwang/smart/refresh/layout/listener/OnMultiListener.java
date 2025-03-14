package com.scwang.smart.refresh.layout.listener;

import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;

/* loaded from: classes.dex */
public interface OnMultiListener extends OnRefreshListener, OnLoadMoreListener, OnStateChangedListener {
    void onFooterFinish(RefreshFooter refreshFooter, boolean z);

    void onFooterMoving(RefreshFooter refreshFooter, boolean z, float f, int i, int i2, int i3);

    void onFooterReleased(RefreshFooter refreshFooter, int i, int i2);

    void onFooterStartAnimator(RefreshFooter refreshFooter, int i, int i2);

    void onHeaderFinish(RefreshHeader refreshHeader, boolean z);

    void onHeaderMoving(RefreshHeader refreshHeader, boolean z, float f, int i, int i2, int i3);

    void onHeaderReleased(RefreshHeader refreshHeader, int i, int i2);

    void onHeaderStartAnimator(RefreshHeader refreshHeader, int i, int i2);
}

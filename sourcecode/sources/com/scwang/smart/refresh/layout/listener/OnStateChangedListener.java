package com.scwang.smart.refresh.layout.listener;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

/* loaded from: classes.dex */
public interface OnStateChangedListener {
    void onStateChanged(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2);
}

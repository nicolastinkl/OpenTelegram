package com.scwang.smart.refresh.layout.api;

import android.view.ViewGroup;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/* loaded from: classes.dex */
public interface RefreshLayout {
    RefreshLayout finishLoadMore();

    RefreshLayout finishRefresh();

    ViewGroup getLayout();

    RefreshLayout setEnableNestedScroll(boolean z);

    RefreshLayout setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener);

    RefreshLayout setOnRefreshListener(OnRefreshListener onRefreshListener);

    RefreshLayout setRefreshFooter(RefreshFooter refreshFooter);

    RefreshLayout setRefreshHeader(RefreshHeader refreshHeader);
}

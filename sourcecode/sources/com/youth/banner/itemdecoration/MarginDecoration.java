package com.youth.banner.itemdecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int mMarginPx;

    public MarginDecoration(int i) {
        this.mMarginPx = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (requireLinearLayoutManager(recyclerView).getOrientation() == 1) {
            int i = this.mMarginPx;
            rect.top = i;
            rect.bottom = i;
        } else {
            int i2 = this.mMarginPx;
            rect.left = i2;
            rect.right = i2;
        }
    }

    private LinearLayoutManager requireLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) layoutManager;
        }
        throw new IllegalStateException("The layoutManager must be LinearLayoutManager");
    }
}

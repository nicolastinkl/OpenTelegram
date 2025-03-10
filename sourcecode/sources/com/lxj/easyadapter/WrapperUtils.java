package com.lxj.easyadapter;

import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WrapperUtils.kt */
/* loaded from: classes.dex */
public final class WrapperUtils {
    public static final WrapperUtils INSTANCE = new WrapperUtils();

    private WrapperUtils() {
    }

    public final void onAttachedToRecyclerView(RecyclerView recyclerView, final Function3<? super GridLayoutManager, ? super GridLayoutManager.SpanSizeLookup, ? super Integer, Integer> fn) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(fn, "fn");
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.lxj.easyadapter.WrapperUtils$onAttachedToRecyclerView$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    Function3<GridLayoutManager, GridLayoutManager.SpanSizeLookup, Integer, Integer> function3 = fn;
                    RecyclerView.LayoutManager layoutManager2 = layoutManager;
                    GridLayoutManager.SpanSizeLookup spanSizeLookup2 = spanSizeLookup;
                    Intrinsics.checkNotNullExpressionValue(spanSizeLookup2, "spanSizeLookup");
                    return ((Number) function3.invoke(layoutManager2, spanSizeLookup2, Integer.valueOf(i))).intValue();
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    public final void setFullSpan(RecyclerView.ViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams == null || !(layoutParams instanceof StaggeredGridLayoutManager$LayoutParams)) {
            return;
        }
        ((StaggeredGridLayoutManager$LayoutParams) layoutParams).setFullSpan(true);
    }
}

package com.lxj.easyadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.ViewHolder;
import java.util.List;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MultiItemTypeAdapter.kt */
/* loaded from: classes.dex */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private List<? extends T> data;
    private final SparseArray<View> mFootViews;
    private final SparseArray<View> mHeaderViews;
    private ItemDelegateManager<T> mItemDelegateManager;
    private OnItemClickListener mOnItemClickListener;

    /* compiled from: MultiItemTypeAdapter.kt */
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i);
    }

    /* compiled from: MultiItemTypeAdapter.kt */
    public static class SimpleOnItemClickListener implements OnItemClickListener {
        @Override // com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int i) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(holder, "holder");
            return false;
        }
    }

    static {
        new Companion(null);
    }

    protected final boolean isEnabled(int i) {
        return true;
    }

    public final void onViewHolderCreated(ViewHolder holder, View itemView) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(itemView, "itemView");
    }

    public MultiItemTypeAdapter(List<? extends T> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        this.mHeaderViews = new SparseArray<>();
        this.mFootViews = new SparseArray<>();
        this.mItemDelegateManager = new ItemDelegateManager<>();
    }

    public final List<T> getData() {
        return this.data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i, List list) {
        onBindViewHolder2(viewHolder, i, (List<? extends Object>) list);
    }

    private final int getRealItemCount() {
        return (getItemCount() - getHeadersCount()) - getFootersCount();
    }

    public final int getHeadersCount() {
        return this.mHeaderViews.size();
    }

    public final int getFootersCount() {
        return this.mFootViews.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (isHeaderViewPos(i)) {
            return this.mHeaderViews.keyAt(i);
        }
        if (isFooterViewPos(i)) {
            return this.mFootViews.keyAt((i - getHeadersCount()) - getRealItemCount());
        }
        return !useItemDelegateManager() ? super.getItemViewType(i) : this.mItemDelegateManager.getItemViewType(this.data.get(i - getHeadersCount()), i - getHeadersCount());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (this.mHeaderViews.get(i) != null) {
            ViewHolder.Companion companion = ViewHolder.Companion;
            View view = this.mHeaderViews.get(i);
            Intrinsics.checkNotNull(view);
            return companion.createViewHolder(view);
        }
        if (this.mFootViews.get(i) != null) {
            ViewHolder.Companion companion2 = ViewHolder.Companion;
            View view2 = this.mFootViews.get(i);
            Intrinsics.checkNotNull(view2);
            return companion2.createViewHolder(view2);
        }
        int layoutId = this.mItemDelegateManager.getItemViewDelegate(i).getLayoutId();
        ViewHolder.Companion companion3 = ViewHolder.Companion;
        Context context = parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "parent.context");
        ViewHolder createViewHolder = companion3.createViewHolder(context, parent, layoutId);
        onViewHolderCreated(createViewHolder, createViewHolder.getConvertView());
        setListener(parent, createViewHolder, i);
        return createViewHolder;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void convert$default(MultiItemTypeAdapter multiItemTypeAdapter, ViewHolder viewHolder, Object obj, List list, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: convert");
        }
        if ((i & 4) != 0) {
            list = null;
        }
        multiItemTypeAdapter.convert(viewHolder, obj, list);
    }

    public final void convert(ViewHolder holder, T t, List<? extends Object> list) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        this.mItemDelegateManager.convert(holder, t, holder.getAdapterPosition() - getHeadersCount(), list);
    }

    protected final void setListener(ViewGroup parent, final ViewHolder viewHolder, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        if (isEnabled(i)) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.lxj.easyadapter.MultiItemTypeAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MultiItemTypeAdapter.m151setListener$lambda0(MultiItemTypeAdapter.this, viewHolder, view);
                }
            });
            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.lxj.easyadapter.MultiItemTypeAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean m152setListener$lambda1;
                    m152setListener$lambda1 = MultiItemTypeAdapter.m152setListener$lambda1(MultiItemTypeAdapter.this, viewHolder, view);
                    return m152setListener$lambda1;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setListener$lambda-0, reason: not valid java name */
    public static final void m151setListener$lambda0(MultiItemTypeAdapter this$0, ViewHolder viewHolder, View v) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        if (this$0.mOnItemClickListener != null) {
            int adapterPosition = viewHolder.getAdapterPosition() - this$0.getHeadersCount();
            OnItemClickListener onItemClickListener = this$0.mOnItemClickListener;
            Intrinsics.checkNotNull(onItemClickListener);
            Intrinsics.checkNotNullExpressionValue(v, "v");
            onItemClickListener.onItemClick(v, viewHolder, adapterPosition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setListener$lambda-1, reason: not valid java name */
    public static final boolean m152setListener$lambda1(MultiItemTypeAdapter this$0, ViewHolder viewHolder, View v) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        if (this$0.mOnItemClickListener == null) {
            return false;
        }
        int adapterPosition = viewHolder.getAdapterPosition() - this$0.getHeadersCount();
        OnItemClickListener onItemClickListener = this$0.mOnItemClickListener;
        Intrinsics.checkNotNull(onItemClickListener);
        Intrinsics.checkNotNullExpressionValue(v, "v");
        return onItemClickListener.onItemLongClick(v, viewHolder, adapterPosition);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (isHeaderViewPos(i) || isFooterViewPos(i)) {
            return;
        }
        convert$default(this, holder, this.data.get(i - getHeadersCount()), null, 4, null);
    }

    /* renamed from: onBindViewHolder, reason: avoid collision after fix types in other method */
    public void onBindViewHolder2(ViewHolder holder, int i, List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        if (isHeaderViewPos(i) || isFooterViewPos(i)) {
            return;
        }
        convert(holder, this.data.get(i - getHeadersCount()), payloads);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        WrapperUtils.INSTANCE.onAttachedToRecyclerView(recyclerView, new Function3<GridLayoutManager, GridLayoutManager.SpanSizeLookup, Integer, Integer>(this) { // from class: com.lxj.easyadapter.MultiItemTypeAdapter$onAttachedToRecyclerView$1
            final /* synthetic */ MultiItemTypeAdapter<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Integer invoke(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, Integer num) {
                return invoke(gridLayoutManager, spanSizeLookup, num.intValue());
            }

            public final Integer invoke(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int i) {
                SparseArray sparseArray;
                SparseArray sparseArray2;
                int spanCount;
                Intrinsics.checkNotNullParameter(layoutManager, "layoutManager");
                Intrinsics.checkNotNullParameter(oldLookup, "oldLookup");
                int itemViewType = this.this$0.getItemViewType(i);
                sparseArray = ((MultiItemTypeAdapter) this.this$0).mHeaderViews;
                if (sparseArray.get(itemViewType) != null) {
                    spanCount = layoutManager.getSpanCount();
                } else {
                    sparseArray2 = ((MultiItemTypeAdapter) this.this$0).mFootViews;
                    spanCount = sparseArray2.get(itemViewType) != null ? layoutManager.getSpanCount() : oldLookup.getSpanSize(i);
                }
                return Integer.valueOf(spanCount);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(ViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewAttachedToWindow((MultiItemTypeAdapter<T>) holder);
        int layoutPosition = holder.getLayoutPosition();
        if (isHeaderViewPos(layoutPosition) || isFooterViewPos(layoutPosition)) {
            WrapperUtils.INSTANCE.setFullSpan(holder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + this.data.size();
    }

    private final boolean isHeaderViewPos(int i) {
        return i < getHeadersCount();
    }

    private final boolean isFooterViewPos(int i) {
        return i >= getHeadersCount() + getRealItemCount();
    }

    public final MultiItemTypeAdapter<T> addItemDelegate(ItemDelegate<T> itemViewDelegate) {
        Intrinsics.checkNotNullParameter(itemViewDelegate, "itemViewDelegate");
        this.mItemDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    protected final boolean useItemDelegateManager() {
        return this.mItemDelegateManager.getItemViewDelegateCount() > 0;
    }

    public final void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        Intrinsics.checkNotNullParameter(onItemClickListener, "onItemClickListener");
        this.mOnItemClickListener = onItemClickListener;
    }

    /* compiled from: MultiItemTypeAdapter.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

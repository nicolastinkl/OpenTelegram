package com.lxj.easyadapter;

import android.util.SparseArray;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ItemDelegateManager.kt */
/* loaded from: classes.dex */
public final class ItemDelegateManager<T> {
    private SparseArray<ItemDelegate<T>> delegates = new SparseArray<>();

    public final int getItemViewDelegateCount() {
        return this.delegates.size();
    }

    public final ItemDelegateManager<T> addDelegate(ItemDelegate<T> delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegates.put(this.delegates.size(), delegate);
        return this;
    }

    public final int getItemViewType(T t, int i) {
        int size = this.delegates.size() - 1;
        if (size < 0) {
            return 0;
        }
        while (true) {
            int i2 = size - 1;
            if (this.delegates.valueAt(size).isThisType(t, i)) {
                return this.delegates.keyAt(size);
            }
            if (i2 < 0) {
                return 0;
            }
            size = i2;
        }
    }

    public final void convert(ViewHolder holder, T t, int i, List<? extends Object> list) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        int size = this.delegates.size();
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2 + 1;
            ItemDelegate<T> valueAt = this.delegates.valueAt(i2);
            if (valueAt.isThisType(t, i)) {
                if (list == null || list.isEmpty()) {
                    valueAt.bind(holder, t, i);
                    return;
                } else {
                    valueAt.bindWithPayloads(holder, t, i, list);
                    return;
                }
            }
            i2 = i3;
        }
    }

    public final ItemDelegate<T> getItemViewDelegate(int i) {
        ItemDelegate<T> itemDelegate = this.delegates.get(i);
        Intrinsics.checkNotNull(itemDelegate);
        return itemDelegate;
    }
}

package com.lxj.easyadapter;

import java.util.List;

/* compiled from: ItemDelegate.kt */
/* loaded from: classes.dex */
public interface ItemDelegate<T> {
    void bind(ViewHolder viewHolder, T t, int i);

    void bindWithPayloads(ViewHolder viewHolder, T t, int i, List<? extends Object> list);

    int getLayoutId();

    boolean isThisType(T t, int i);
}

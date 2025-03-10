package com.lxj.easyadapter;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EasyAdapter.kt */
/* loaded from: classes.dex */
public abstract class EasyAdapter<T> extends MultiItemTypeAdapter<T> {
    private int mLayoutId;

    protected abstract void bind(ViewHolder viewHolder, T t, int i);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasyAdapter(List<? extends T> data, int i) {
        super(data);
        Intrinsics.checkNotNullParameter(data, "data");
        this.mLayoutId = i;
        addItemDelegate(new ItemDelegate<T>(this) { // from class: com.lxj.easyadapter.EasyAdapter.1
            final /* synthetic */ EasyAdapter<T> this$0;

            @Override // com.lxj.easyadapter.ItemDelegate
            public boolean isThisType(T t, int i2) {
                return true;
            }

            {
                this.this$0 = this;
            }

            @Override // com.lxj.easyadapter.ItemDelegate
            public void bind(ViewHolder holder, T t, int i2) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                this.this$0.bind(holder, t, i2);
            }

            @Override // com.lxj.easyadapter.ItemDelegate
            public void bindWithPayloads(ViewHolder holder, T t, int i2, List<? extends Object> payloads) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(payloads, "payloads");
                this.this$0.bindWithPayloads(holder, t, i2, payloads);
            }

            @Override // com.lxj.easyadapter.ItemDelegate
            public int getLayoutId() {
                return this.this$0.getMLayoutId();
            }
        });
    }

    protected final int getMLayoutId() {
        return this.mLayoutId;
    }

    protected void bindWithPayloads(ViewHolder holder, T t, int i, List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        bind(holder, t, i);
    }
}

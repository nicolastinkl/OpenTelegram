package com.tencent.beacon.base.net.adapter;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/* compiled from: OkHttpAdapter.java */
/* loaded from: classes.dex */
class d implements Callback {
    final /* synthetic */ com.tencent.beacon.base.net.call.Callback a;
    final /* synthetic */ String b;
    final /* synthetic */ OkHttpAdapter c;

    d(OkHttpAdapter okHttpAdapter, com.tencent.beacon.base.net.call.Callback callback, String str) {
        this.c = okHttpAdapter;
        this.a = callback;
        this.b = str;
    }

    @Override // okhttp3.Callback
    public abstract /* synthetic */ void onFailure(Call call, IOException iOException);

    @Override // okhttp3.Callback
    public abstract /* synthetic */ void onResponse(Call call, Response response) throws IOException;
}

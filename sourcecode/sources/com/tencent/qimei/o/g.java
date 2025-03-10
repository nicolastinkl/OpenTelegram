package com.tencent.qimei.o;

import com.tencent.qimei.sdk.IAsyncQimeiListener;
import com.tencent.qimei.sdk.Qimei;

/* compiled from: HidBuilder.java */
/* loaded from: classes.dex */
public class g implements IAsyncQimeiListener {
    public final /* synthetic */ j a;

    public g(j jVar) {
        this.a = jVar;
    }

    @Override // com.tencent.qimei.sdk.IAsyncQimeiListener
    public void onQimeiDispatch(Qimei qimei) {
        this.a.a();
    }
}

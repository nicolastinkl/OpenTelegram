package com.tencent.qimei.v;

import android.content.Context;

/* compiled from: Strategy.java */
/* loaded from: classes.dex */
public class c implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ Context b;
    public final /* synthetic */ k c;

    public c(String str, Context context, k kVar) {
        this.a = str;
        this.b = context;
        this.c = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        f a = f.a(this.a);
        k kVar = this.c;
        j jVar = a.d;
        jVar.d = kVar;
        jVar.a();
        com.tencent.qimei.k.a.b("QM", "开始执行Strategy请求任务(appKey: %s)", a.c);
        if (com.tencent.qimei.a.a.a()) {
            a.b();
        } else {
            com.tencent.qimei.k.a.b("QM", "没有网络，取消Strategy请求(appKey: %s)", a.c);
        }
        com.tencent.qimei.g.b.a().a(a.c, new e(a));
        com.tencent.qimei.k.a.b("SDK_INIT ｜ 策略", " 初始化完成 ", new Object[0]);
    }
}

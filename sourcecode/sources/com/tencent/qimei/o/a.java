package com.tencent.qimei.o;

import android.content.Context;

/* compiled from: AsyInitTask.java */
/* loaded from: classes.dex */
public class a implements Runnable {
    public final /* synthetic */ d a;

    public a(d dVar) {
        this.a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        Context context2;
        String str;
        com.tencent.qimei.g.c cVar;
        String str2;
        Context context3;
        Context context4;
        String str3;
        com.tencent.qimei.x.b bVar;
        String str4;
        com.tencent.qimei.j.f fVar;
        e a = e.a();
        context = this.a.b;
        a.a(context);
        com.tencent.qimei.c.a.h();
        com.tencent.qimei.g.b a2 = com.tencent.qimei.g.b.a();
        context2 = this.a.b;
        a2.a(context2);
        com.tencent.qimei.g.b a3 = com.tencent.qimei.g.b.a();
        str = this.a.c;
        cVar = this.a.e;
        a3.a(str, cVar);
        str2 = this.a.c;
        j a4 = j.a(str2);
        context3 = this.a.b;
        a4.i = context3;
        context4 = this.a.b;
        str3 = this.a.c;
        bVar = this.a.d;
        d dVar = this.a;
        com.tencent.qimei.v.d.a(str3, bVar);
        com.tencent.qimei.b.a.a().a(new com.tencent.qimei.v.c(str3, context4, dVar));
        str4 = this.a.c;
        l a5 = l.a(str4);
        if (!a5.h) {
            a5.a();
            a5.h = true;
        }
        com.tencent.qimei.k.a.b(d.a, "basicTask init finished,notify another task (oaid and collection)", new Object[0]);
        fVar = this.a.i;
        fVar.a();
        this.a.a();
    }
}

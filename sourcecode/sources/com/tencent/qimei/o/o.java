package com.tencent.qimei.o;

/* compiled from: QimeiQueryTask.java */
/* loaded from: classes.dex */
public class o implements com.tencent.qimei.d.c {
    public final /* synthetic */ String a;
    public final /* synthetic */ r b;

    public o(r rVar, String str) {
        this.b = rVar;
        this.a = str;
    }

    @Override // com.tencent.qimei.d.c
    public void a(String str, String... strArr) {
        r.b(this.b);
        this.b.a(str, this.a);
    }

    @Override // com.tencent.qimei.d.c
    public void a(String str, int i, String str2) {
        this.b.a(str, i, str2);
    }
}

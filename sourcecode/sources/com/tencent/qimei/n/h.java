package com.tencent.qimei.n;

/* compiled from: Reporter.java */
/* loaded from: classes.dex */
public class h implements com.tencent.qimei.d.c {
    public h(i iVar) {
    }

    @Override // com.tencent.qimei.d.c
    public void a(String str, int i, String str2) {
        com.tencent.qimei.k.a.b("上报", "Report failed. error: %s,%d,%s", str, Integer.valueOf(i), str2);
    }

    @Override // com.tencent.qimei.d.c
    public void a(String str, String... strArr) {
    }
}

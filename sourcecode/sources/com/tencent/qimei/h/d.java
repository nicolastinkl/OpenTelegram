package com.tencent.qimei.h;

/* compiled from: StringParser.java */
/* loaded from: classes.dex */
public class d extends a<String> {
    public d(com.tencent.qimei.f.a<?>... aVarArr) {
        super(aVarArr);
    }

    @Override // com.tencent.qimei.h.a
    public String a(com.tencent.qimei.f.a<String> aVar, String str) {
        String a = a(str, a(aVar));
        return (a == null || a.isEmpty()) ? aVar.a() : a;
    }
}

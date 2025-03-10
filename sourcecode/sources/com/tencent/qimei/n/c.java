package com.tencent.qimei.n;

import java.util.HashMap;
import java.util.Map;

/* compiled from: ReportParamBuilder.java */
/* loaded from: classes.dex */
public class c {
    public String a = "";
    public Map<String, Object> b = new HashMap();
    public String c = "";

    public c a(String str, Object obj) {
        this.b.put(str, obj);
        return this;
    }

    public void a(String str) {
        i.a().b(this.c, this.b, str, this.a);
    }
}

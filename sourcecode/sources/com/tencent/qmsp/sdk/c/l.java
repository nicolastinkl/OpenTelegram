package com.tencent.qmsp.sdk.c;

import java.util.HashMap;

/* loaded from: classes.dex */
public class l {
    private HashMap<String, e> a = new HashMap<>();

    public void a(e eVar) {
        String a = eVar.a();
        if (a == null || this.a.containsKey(a)) {
            return;
        }
        this.a.put(a, eVar);
    }
}

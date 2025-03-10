package io.openinstall.sdk;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class eg implements ay<Map<String, String>> {
    protected final bc a;
    protected final aw b = aw.a();

    protected eg(bc bcVar) {
        this.a = bcVar;
    }

    protected abstract Map<String, String> a();

    @Override // io.openinstall.sdk.ay
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public Map<String, String> a_() {
        HashMap hashMap = new HashMap();
        hashMap.put("jpaw", this.a.d());
        hashMap.put("opof", this.a.c());
        hashMap.put("kjfe", this.a.e());
        hashMap.put("hwef", String.valueOf(this.a.f()));
        hashMap.put("vsna", this.a.g());
        hashMap.putAll(a());
        return hashMap;
    }
}

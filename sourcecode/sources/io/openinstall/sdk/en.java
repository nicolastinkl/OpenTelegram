package io.openinstall.sdk;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class en extends eo {
    private String a;

    public en(boolean z, String str) {
        super(z, str);
    }

    public void a(String str) {
        this.a = str;
    }

    @Override // io.openinstall.sdk.eo, io.openinstall.sdk.el
    public byte[] d() {
        String str = this.a;
        if (str == null || str.length() <= 0) {
            return null;
        }
        return this.a.getBytes(ds.c);
    }

    @Override // io.openinstall.sdk.eo, io.openinstall.sdk.el
    public Map<String, String> e() {
        if (d() == null || d().length == 0) {
            return super.e();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("content-type", "text/plain;charset=utf-8");
        hashMap.put("content-length", String.valueOf(d().length));
        return hashMap;
    }
}

package io.openinstall.sdk;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class em implements el {
    private static final String b = fv.a("VXNlci1BZ2VudA");
    private static final String c = fv.a("T3Blbkluc3RhbGxTREs");
    public final String a;

    public em(String str) {
        this.a = str;
    }

    @Override // io.openinstall.sdk.el
    public ek a() {
        return ek.GET;
    }

    @Override // io.openinstall.sdk.el
    public String b() {
        return this.a;
    }

    @Override // io.openinstall.sdk.el
    public String c() {
        return "t=" + System.currentTimeMillis();
    }

    @Override // io.openinstall.sdk.el
    public byte[] d() {
        return null;
    }

    @Override // io.openinstall.sdk.el
    public Map<String, String> e() {
        HashMap hashMap = new HashMap();
        hashMap.put(b, c);
        return hashMap;
    }
}

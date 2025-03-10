package io.openinstall.sdk;

/* loaded from: classes.dex */
public class f implements ef {
    private static final String[] a = {"openinstall.io", "deepinstall.com"};
    private static int b = 0;

    @Override // io.openinstall.sdk.ef
    public void a() {
        b = (b + 1) % a.length;
    }

    @Override // io.openinstall.sdk.ef
    public String b() {
        return "api2." + a[b];
    }

    @Override // io.openinstall.sdk.ef
    public String c() {
        return "stat2." + a[b];
    }
}

package io.openinstall.sdk;

/* renamed from: io.openinstall.sdk.do, reason: invalid class name */
/* loaded from: classes.dex */
public class Cdo {
    private static Cdo b;
    private static final Object c = new Object();
    private final dp a;

    private Cdo(dq dqVar) {
        this.a = dqVar.a_();
    }

    public static Cdo a(dq dqVar) {
        synchronized (c) {
            if (b == null) {
                b = new Cdo(dqVar);
            }
        }
        return b;
    }

    public String a(String str) {
        return this.a.a(str);
    }

    public void a(String str, String str2) {
        this.a.a(str, str2);
    }

    public void b(String str, String str2) {
        String a = a(str);
        if (a == null || !a.equals(str2)) {
            a(str, str2);
        }
    }
}

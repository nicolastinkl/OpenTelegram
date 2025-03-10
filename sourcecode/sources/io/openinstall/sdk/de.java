package io.openinstall.sdk;

import java.io.Serializable;
import java.util.Map;

/* loaded from: classes.dex */
public class de implements Serializable {
    private boolean a;
    private final int b;
    private final String c;
    private final Long d;
    private final Long e;
    private final Map<String, String> f;

    private de(int i, String str, Long l, Long l2) {
        this(i, str, l, l2, null);
    }

    private de(int i, String str, Long l, Long l2, Map<String, String> map) {
        this.a = false;
        this.b = i;
        this.c = str;
        this.d = l;
        this.e = l2;
        this.f = map;
    }

    public static de a() {
        return new de(0, "$register", Long.valueOf(System.currentTimeMillis()), 1L);
    }

    public static de a(long j) {
        return new de(1, null, Long.valueOf(System.currentTimeMillis()), Long.valueOf(j));
    }

    public static de a(String str, long j, Map<String, String> map) {
        return new de(2, str, Long.valueOf(System.currentTimeMillis()), Long.valueOf(j), map);
    }

    public void a(boolean z) {
        this.a = z;
    }

    public int b() {
        return this.b;
    }

    public boolean c() {
        return this.a;
    }

    public String d() {
        return this.c;
    }

    public Long e() {
        return this.d;
    }

    public Long f() {
        return this.e;
    }

    public Map<String, String> g() {
        return this.f;
    }
}

package com.tencent.beacon.e;

import java.util.Map;
import java.util.Set;

/* compiled from: ModuleStrategyBean.java */
/* loaded from: classes.dex */
public class e {
    private final int a;
    public String b = "";
    private boolean c = false;
    private Map<String, String> d = null;
    private Set<String> e = null;
    private Set<String> f = null;

    public e(int i) {
        this.a = i;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public int b() {
        return this.a;
    }

    public boolean c() {
        return this.c;
    }

    public Map<String, String> a() {
        return this.d;
    }

    public void b(Set<String> set) {
        this.f = set;
    }

    public void a(Map<String, String> map) {
        this.d = map;
    }

    public void a(Set<String> set) {
        this.e = set;
    }
}

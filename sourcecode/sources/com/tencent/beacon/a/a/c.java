package com.tencent.beacon.a.a;

import java.util.Map;

/* compiled from: BusEvent.java */
/* loaded from: classes.dex */
public final class c {
    public int a;
    public Map<String, Object> b;

    public c(int i, Map<String, Object> map) {
        this.a = i;
        this.b = map;
    }

    public String toString() {
        return "BusEvent{channel=" + this.a + ", params=" + this.b + '}';
    }

    public c(int i) {
        this.a = i;
    }
}

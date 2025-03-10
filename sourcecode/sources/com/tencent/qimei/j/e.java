package com.tencent.qimei.j;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: StrictMode.java */
/* loaded from: classes.dex */
public class e {
    public static AtomicBoolean a = new AtomicBoolean(false);

    public static void a(String str) {
        if (a.get()) {
            throw new IllegalStateException("[strict] " + str);
        }
    }
}

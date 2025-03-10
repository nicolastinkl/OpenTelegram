package com.tencent.qmsp.sdk.a;

import android.content.Context;
import com.tencent.qmsp.sdk.f.h;
import java.io.File;

/* loaded from: classes.dex */
public class b {
    private static final byte[] a = {52, 125, -96, 80};
    private static final byte[] b = {107, 124, -70, 66, 61};
    private static final byte[] c = {107, 67, -107, 117, 97};

    public static String a() {
        Context context;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        return context.getDir(com.tencent.qmsp.sdk.c.b.a + h.a(a), 0).toString();
    }

    public static String b() {
        return a() + File.separator + h.a(b);
    }

    public static String c() {
        return a() + File.separator + h.a(c);
    }
}

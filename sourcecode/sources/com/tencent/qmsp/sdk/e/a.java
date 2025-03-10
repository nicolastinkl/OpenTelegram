package com.tencent.qmsp.sdk.e;

import com.tencent.qmsp.sdk.c.f;

/* loaded from: classes.dex */
public class a {

    /* renamed from: com.tencent.qmsp.sdk.e.a$a, reason: collision with other inner class name */
    static class RunnableC0035a implements Runnable {
        RunnableC0035a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                f.i().b().a(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void a() {
        f.i().c().post(new RunnableC0035a());
    }
}

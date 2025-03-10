package com.tencent.qmsp.sdk.e;

import android.content.Context;
import com.tencent.qmsp.sdk.c.f;
import com.tencent.qmsp.sdk.c.h;
import com.tencent.qmsp.sdk.c.i;
import com.tencent.qmsp.sdk.f.g;

/* loaded from: classes.dex */
public class b {

    static class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Context context;
            Context context2;
            try {
                context = com.tencent.qmsp.sdk.app.a.getContext();
                if (context != null) {
                    context2 = com.tencent.qmsp.sdk.app.a.getContext();
                    h.a(context2);
                } else {
                    g.d("qmsp.cbid", 2, "Context is null!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void a() {
        f.i().c().postDelayed(new a(), i.f);
    }
}

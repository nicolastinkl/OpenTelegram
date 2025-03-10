package com.shubao.xinstall.a.a;

import com.shubao.xinstall.a.f.k;
import com.xinstall.model.XAppError;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class i implements Runnable {
    com.shubao.xinstall.a.d.b a;
    long b = 10;
    private Callable c;
    private b d;

    class a implements Runnable {
        private com.shubao.xinstall.a.b.d b;

        a(com.shubao.xinstall.a.b.d dVar) {
            this.b = dVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            i.this.a.a(this.b);
        }
    }

    public i(Callable callable, com.shubao.xinstall.a.d.b bVar, b bVar2) {
        this.d = null;
        this.d = bVar2;
        this.c = callable;
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.shubao.xinstall.a.b.d dVar;
        Future submit = this.d.h.submit(this.c);
        try {
            dVar = (com.shubao.xinstall.a.b.d) submit.get(this.b + 1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            submit.cancel(true);
            com.shubao.xinstall.a.b.d dVar2 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, XAppError.TIMEOUT);
            dVar2.d = "请求超时:" + e.getMessage();
            dVar = dVar2;
        } catch (Exception e2) {
            dVar = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, XAppError.REQUEST_FAIL);
            dVar.d = "请求失败:" + e2.getMessage();
        }
        k.b().execute(new a(dVar));
    }
}

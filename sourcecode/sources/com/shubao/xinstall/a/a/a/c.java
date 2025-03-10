package com.shubao.xinstall.a.a.a;

import com.xinstall.model.XAppError;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class c implements Callable {
    private com.shubao.xinstall.a.a.b a;
    private long b;

    public c(com.shubao.xinstall.a.a.b bVar, long j) {
        this.a = bVar;
        this.b = j;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ Object call() {
        boolean a = this.a.c().a(this.b);
        com.shubao.xinstall.a.a.g f = this.a.f();
        if (a) {
            String a2 = f.a();
            com.shubao.xinstall.a.b.d dVar = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.SUCCESS, "0000");
            dVar.c = a2;
            this.a.d(dVar.e);
            return dVar;
        }
        if (this.a.c().a == com.shubao.xinstall.a.a.c.d) {
            String d = f.d();
            com.shubao.xinstall.a.b.d dVar2 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, XAppError.INIT_FAIL);
            dVar2.d = "初始化失败：".concat(String.valueOf(d));
            return dVar2;
        }
        String d2 = f.d();
        com.shubao.xinstall.a.b.d dVar3 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, XAppError.TIMEOUT);
        dVar3.d = "获取安装参数超时：".concat(String.valueOf(d2));
        return dVar3;
    }
}

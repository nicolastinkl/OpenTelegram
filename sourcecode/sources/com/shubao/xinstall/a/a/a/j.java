package com.shubao.xinstall.a.a.a;

import android.net.Uri;
import com.shubao.xinstall.a.f.o;
import com.xinstall.model.XAppError;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class j implements Callable {
    private Uri a;
    private boolean b;
    private com.shubao.xinstall.a.a.b c;

    public j(Uri uri, boolean z, com.shubao.xinstall.a.a.b bVar) {
        this.a = uri;
        this.b = z;
        this.c = bVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ Object call() {
        com.shubao.xinstall.a.b.d dVar;
        String str;
        com.shubao.xinstall.a.b.d dVar2;
        com.shubao.xinstall.a.b.d a;
        if (!this.c.c().a(10L)) {
            String d = this.c.f().d();
            com.shubao.xinstall.a.b.d dVar3 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, XAppError.INIT_FAIL);
            dVar3.d = "初始化时错误：".concat(String.valueOf(d));
            return dVar3;
        }
        if (this.c.d().a().booleanValue()) {
            if (this.b) {
                o.a("发起");
                this.c.h().a();
                IdentityHashMap identityHashMap = new IdentityHashMap();
                com.shubao.xinstall.a.f.i g = this.c.g();
                identityHashMap.put("model", g.b);
                identityHashMap.put("buildId", g.c);
                identityHashMap.put("buildDisplay", g.d);
                identityHashMap.put("brand", g.e);
                if (this.b) {
                    identityHashMap.put("yyb", "1");
                } else {
                    identityHashMap.put("yyb", "2");
                }
                identityHashMap.put("needXin", "1");
                identityHashMap.putAll(g.g);
                com.shubao.xinstall.a.e.a.a(true);
                a = com.shubao.xinstall.a.e.a.a(this.c.a("wakeupyyb2"), this.c.a(), identityHashMap);
            } else {
                Uri uri = this.a;
                if (uri != null) {
                    String str2 = uri.getPath().contains("tolink/xx/") ? "xx" : this.a.getPath().contains("tolink/yy/") ? "yy" : "";
                    if (str2.equalsIgnoreCase("")) {
                        dVar2 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, "-1");
                    } else {
                        String[] split = this.a.getPath().split("tolink/" + str2 + "/");
                        if (str2.equalsIgnoreCase("xx")) {
                            if (split.length >= 2) {
                                String b = com.shubao.xinstall.a.f.g.b(split[split.length - 1]);
                                com.shubao.xinstall.a.b.d dVar4 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.SUCCESS, "0000");
                                dVar4.c = b;
                                if (b != null && !b.isEmpty()) {
                                    this.c.a(this.a);
                                }
                                return dVar4;
                            }
                            dVar2 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, "-1");
                        } else if (!str2.equalsIgnoreCase("yy")) {
                            dVar2 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, "-1");
                        } else if (split.length >= 2) {
                            String b2 = com.shubao.xinstall.a.f.g.b(split[split.length - 1]);
                            HashMap hashMap = new HashMap();
                            hashMap.put("ak", b2);
                            com.shubao.xinstall.a.e.a.a(true);
                            a = com.shubao.xinstall.a.e.a.a(this.c.a("wakeup21"), this.c.a(), hashMap);
                        } else {
                            dVar2 = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, "-1");
                        }
                    }
                    dVar2.d = "没有唤醒传参";
                    return dVar2;
                }
                dVar = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, XAppError.ERROR_SCHEME_NO_URL);
                str = "scheme URL 为空";
            }
            this.c.d(a.e);
            return a;
        }
        dVar = new com.shubao.xinstall.a.b.d(com.shubao.xinstall.a.e.b.FAIL, XAppError.NO_PERMISSION);
        str = "没有获取唤醒参数的能力";
        dVar.d = str;
        o.a(str);
        return dVar;
    }
}

package com.shubao.xinstall.a.a;

import java.util.Calendar;

/* loaded from: classes.dex */
public class e {
    private static e d;
    protected boolean a;
    protected com.shubao.xinstall.a.b.a b;
    private g c;

    private e(g gVar, com.shubao.xinstall.a.b.a aVar) {
        this.c = gVar;
        this.b = aVar;
        this.a = gVar.f();
    }

    public static e a(g gVar, com.shubao.xinstall.a.b.a aVar) {
        e eVar;
        synchronized (e.class) {
            if (d == null) {
                synchronized (e.class) {
                    d = new e(gVar, aVar);
                }
            }
            eVar = d;
        }
        return eVar;
    }

    public final void a() {
        this.c.e();
        this.b.d = Boolean.TRUE;
        this.a = true;
    }

    public final void a(com.shubao.xinstall.a.b.a aVar) {
        this.b = aVar;
        if (this.c.f()) {
            return;
        }
        Boolean c = aVar.c();
        if (c == null || !c.booleanValue()) {
            this.c.a(0);
            this.c.a(0L);
            return;
        }
        int c2 = this.c.c() + 1;
        this.c.a(c2);
        if (c2 >= 3) {
            this.a = true;
            this.c.e();
        } else if (this.c.g() == 0) {
            long longValue = aVar.e.longValue();
            Calendar calendar = Calendar.getInstance();
            calendar.add(13, (int) longValue);
            this.c.a(calendar.getTime().getTime());
        }
    }
}

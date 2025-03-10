package com.tencent.beacon.event;

import java.util.Iterator;
import java.util.Set;

/* compiled from: EventReportCallback.java */
/* loaded from: classes.dex */
class e implements Runnable {
    final /* synthetic */ long a;
    final /* synthetic */ f b;

    e(f fVar, long j) {
        this.b = fVar;
        this.a = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        Set set;
        com.tencent.beacon.event.a.a aVar;
        String str;
        String str2;
        g gVar;
        Set<Long> set2;
        g gVar2;
        StringBuilder sb = new StringBuilder();
        set = this.b.a;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            sb.append((Long) it.next());
            sb.append(",");
        }
        String substring = sb.substring(0, sb.lastIndexOf(","));
        aVar = this.b.g;
        str = this.b.f;
        boolean a = aVar.a(str, substring);
        str2 = this.b.b;
        com.tencent.beacon.base.util.c.a(str2, 4, "delete: %s", Boolean.valueOf(a));
        gVar = this.b.e;
        set2 = this.b.a;
        gVar.a(set2);
        gVar2 = this.b.e;
        gVar2.a(this.a);
    }
}

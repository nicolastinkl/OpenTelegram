package com.shubao.xinstall.a.a.a;

import com.shubao.xinstall.a.f.l;
import java.io.File;
import java.util.List;

/* loaded from: classes.dex */
public final class k implements Runnable {
    private File a;
    private List<com.shubao.xinstall.a.b.c> b;
    private com.shubao.xinstall.a.d.c c;

    public k(File file, List<com.shubao.xinstall.a.b.c> list, com.shubao.xinstall.a.d.c cVar) {
        this.a = file;
        this.b = list;
        this.c = cVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.c.a(1, false);
        boolean z = false;
        for (com.shubao.xinstall.a.b.c cVar : this.b) {
            l.a(this.a, cVar);
            z = cVar.a;
        }
        this.c.a(0, z);
    }
}

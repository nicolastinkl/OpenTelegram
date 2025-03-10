package com.tencent.beacon.a.a;

import android.util.SparseArray;
import com.tencent.beacon.a.b.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: BeaconBus.java */
/* loaded from: classes.dex */
public final class b {
    private static volatile b a;
    private final Object c = new Object();
    private AtomicBoolean f = new AtomicBoolean(false);
    private final SparseArray<List<d>> b = new SparseArray<>();
    private final SparseArray<List<c>> d = new SparseArray<>();
    private final SparseArray<Object> e = new SparseArray<>();

    private b() {
    }

    public static b a() {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    private void c(c cVar) {
        d(cVar);
        synchronized (b(cVar.a)) {
            List<d> c = c(cVar.a);
            if (c == null) {
                return;
            }
            Iterator<d> it = c.iterator();
            while (it.hasNext()) {
                try {
                    it.next().a(cVar);
                } catch (Throwable th) {
                    com.tencent.beacon.base.util.c.a(th);
                    if (this.f.compareAndSet(false, true)) {
                        g.e().a("512", "dispatchEvent error", th);
                    }
                }
            }
        }
    }

    private void d(c cVar) {
    }

    public void b(c cVar) {
        synchronized (b(cVar.a)) {
            c cVar2 = new c(cVar.a, cVar.b);
            List<c> list = this.d.get(cVar2.a);
            if (list == null) {
                list = new ArrayList<>();
                this.d.put(cVar2.a, list);
            }
            list.add(cVar2);
            c(cVar);
        }
    }

    public void a(int i, d dVar) {
        synchronized (b(i)) {
            List<d> list = this.b.get(i);
            if (list == null) {
                list = new ArrayList<>();
                this.b.put(i, list);
            }
            if (list.contains(dVar)) {
                return;
            }
            list.add(dVar);
            List<c> list2 = this.d.get(i);
            if (list2 != null) {
                Iterator<c> it = list2.iterator();
                while (it.hasNext()) {
                    try {
                        dVar.a(it.next());
                    } catch (Throwable th) {
                        com.tencent.beacon.base.util.c.a(th);
                    }
                }
                if (i == 6 || i == 12) {
                    a(i);
                }
            }
        }
    }

    private Object b(int i) {
        Object obj;
        synchronized (this.c) {
            obj = this.e.get(i);
            if (obj == null) {
                obj = new Object();
                this.e.put(i, obj);
            }
        }
        return obj;
    }

    private List<d> c(int i) {
        List<d> list = this.b.get(i);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public void a(c cVar) {
        com.tencent.beacon.a.b.a.a().a(new a(this, cVar));
    }

    public void a(int i) {
        synchronized (b(i)) {
            this.d.remove(i);
        }
    }
}

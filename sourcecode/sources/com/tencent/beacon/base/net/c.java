package com.tencent.beacon.base.net;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.a.b.g;
import com.tencent.beacon.base.net.adapter.AbstractNetAdapter;
import com.tencent.beacon.base.net.adapter.f;
import com.tencent.beacon.base.net.b.e;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.net.call.j;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BeaconNet.java */
/* loaded from: classes.dex */
public final class c implements e, e.a, Closeable {
    private static volatile c a;
    private final AtomicBoolean b = new AtomicBoolean(false);
    private final AtomicInteger c = new AtomicInteger();
    public com.tencent.beacon.base.net.a.a d;
    public com.tencent.beacon.base.net.a.b e;
    private Context f;
    private AbstractNetAdapter g;
    private AbstractNetAdapter h;

    private c() {
    }

    public static synchronized c c() {
        c cVar;
        synchronized (c.class) {
            if (a == null) {
                a = new c();
            }
            cVar = a;
        }
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "current net connected num: %d", Integer.valueOf(this.c.decrementAndGet()));
    }

    private void g() {
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "current net connected num: %d", Integer.valueOf(this.c.incrementAndGet()));
    }

    public j b(JceRequestEntity jceRequestEntity) {
        return new j(jceRequestEntity);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.b.set(true);
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "network can't use. close BeaconNet.", new Object[0]);
    }

    public boolean d() {
        return this.c.get() >= 5;
    }

    public void e() {
        this.b.set(false);
    }

    public void a(Context context, AbstractNetAdapter abstractNetAdapter) {
        this.f = context;
        if (abstractNetAdapter == null) {
            abstractNetAdapter = new com.tencent.beacon.base.net.adapter.b();
        }
        this.g = f.a();
        this.h = abstractNetAdapter;
        this.d = com.tencent.beacon.base.net.a.a.a();
        this.e = com.tencent.beacon.base.net.a.b.a();
        com.tencent.beacon.base.net.b.e.a(context, this);
    }

    @Override // com.tencent.beacon.base.net.b.e.a
    public void b() {
        this.b.set(true);
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "network can't use. close BeaconNet.", new Object[0]);
    }

    public void a(JceRequestEntity jceRequestEntity, Callback<byte[]> callback) {
        if (this.b.get()) {
            callback.onFailure(new d(jceRequestEntity.getType().name(), null, 0, "BeaconNet close."));
            return;
        }
        AbstractNetAdapter a2 = a(jceRequestEntity);
        g();
        a2.request(jceRequestEntity, new a(this, jceRequestEntity, a2 == this.g, callback));
    }

    public void a(com.tencent.beacon.base.net.call.e eVar, Callback<BResponse> callback) {
        if (this.b.get()) {
            callback.onFailure(new d(eVar.h(), null, 0, "BeaconNet close."));
        } else {
            g();
            this.h.request(eVar, new b(this, eVar, callback));
        }
    }

    public com.tencent.beacon.base.net.call.c a(com.tencent.beacon.base.net.call.e eVar) {
        return new com.tencent.beacon.base.net.call.c(eVar);
    }

    public AbstractNetAdapter a(JceRequestEntity jceRequestEntity) {
        if (jceRequestEntity.getType() == RequestType.EVENT) {
            return com.tencent.beacon.e.b.a().m() ? this.g : this.h;
        }
        return this.g;
    }

    public void a(d dVar) {
        if (dVar.a.equals("atta") || TextUtils.isEmpty(dVar.b)) {
            return;
        }
        g.e().a(dVar.b, dVar.toString(), dVar.e);
    }

    @Override // com.tencent.beacon.base.net.b.e.a
    public void a() {
        this.b.set(false);
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "network recovery. open BeaconNet.", new Object[0]);
    }
}

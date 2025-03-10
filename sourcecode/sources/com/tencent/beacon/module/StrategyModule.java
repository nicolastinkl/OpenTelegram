package com.tencent.beacon.module;

import android.content.Context;
import com.tencent.beacon.base.net.b.e;
import com.tencent.beacon.base.util.c;
import com.tencent.beacon.e.a;
import com.tencent.beacon.e.b;
import com.tencent.beacon.e.h;
import com.tencent.beacon.e.i;

/* loaded from: classes.dex */
public class StrategyModule implements BeaconModule {
    private static final Object a = new Object();
    private i c;
    private boolean e = false;
    private b d = b.a();
    private a b = a.a();

    public StrategyModule() {
        h.b().a(this.b);
        this.c = new i(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void d() {
        if (!this.c.a()) {
            com.tencent.beacon.a.b.a.a().a(this.c);
        }
    }

    public boolean c() {
        boolean z;
        synchronized (a) {
            z = this.e;
        }
        return z;
    }

    @Override // com.tencent.beacon.module.BeaconModule
    public void a(Context context) {
        c.a("[module] strategy module > TRUE", new Object[0]);
        this.c.b();
        d();
        e.a(context, new e.a() { // from class: com.tencent.beacon.module.StrategyModule.1
            @Override // com.tencent.beacon.base.net.b.e.a
            public void a() {
                synchronized (StrategyModule.this) {
                    if (!StrategyModule.this.c() && !StrategyModule.this.c.a()) {
                        StrategyModule.this.d();
                    }
                }
            }

            @Override // com.tencent.beacon.base.net.b.e.a
            public void b() {
            }
        });
    }

    public b b() {
        return this.d;
    }

    public void a(boolean z) {
        synchronized (a) {
            this.e = z;
        }
    }

    public a a() {
        return this.b;
    }
}

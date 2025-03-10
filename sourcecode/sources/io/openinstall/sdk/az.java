package io.openinstall.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/* loaded from: classes.dex */
public abstract class az {
    private final Context a;
    private final ej b;
    private final ax c;
    private final au d;
    private final ba e;
    private final Handler f = new Handler(Looper.getMainLooper());
    private final dq g;

    protected az() {
        Context c = aw.a().c();
        this.a = c;
        this.d = new au();
        this.e = new ba();
        this.c = new ax(new bb().a(c, "FM_config", null));
        this.b = ej.a(this);
        this.g = a();
    }

    protected abstract dq a();

    public Handler b() {
        return this.f;
    }

    public ej c() {
        return this.b;
    }

    public ax d() {
        return this.c;
    }

    public au e() {
        return this.d;
    }

    public ba f() {
        return this.e;
    }

    public bc g() {
        return bc.a(this.a, this.c);
    }

    public dt h() {
        return dt.a(this.a);
    }

    public Cdo i() {
        return Cdo.a(this.g);
    }
}

package io.openinstall.sdk;

/* loaded from: classes.dex */
public class fg extends ff {
    private final boolean c;
    private int d;

    public fg(az azVar, boolean z, fb fbVar) {
        super(azVar, fbVar);
        this.c = z;
    }

    public void a(int i) {
        this.d = i;
    }

    @Override // io.openinstall.sdk.et
    protected String k() {
        return "install";
    }

    @Override // io.openinstall.sdk.ff
    protected void o() {
        if (this.c) {
            h().b(k());
        } else {
            h().a(k());
        }
    }

    @Override // io.openinstall.sdk.ff
    protected ew q() {
        return ew.a(c().a());
    }

    @Override // io.openinstall.sdk.ff
    protected int r() {
        int i = this.d;
        if (i > 0) {
            return i;
        }
        return 10;
    }
}

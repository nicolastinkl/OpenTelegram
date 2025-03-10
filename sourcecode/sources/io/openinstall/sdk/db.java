package io.openinstall.sdk;

/* loaded from: classes.dex */
public class db extends bq {
    private byte[] e;

    @Override // io.openinstall.sdk.bq
    protected void a(bh bhVar) {
        this.e = bhVar.i();
    }

    @Override // io.openinstall.sdk.bq
    protected void a(bi biVar, be beVar, boolean z) {
        biVar.a(this.e);
    }

    @Override // io.openinstall.sdk.bq
    protected String b() {
        return bq.a(this.e);
    }
}

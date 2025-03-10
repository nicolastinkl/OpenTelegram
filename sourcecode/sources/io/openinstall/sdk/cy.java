package io.openinstall.sdk;

import java.io.IOException;

/* loaded from: classes.dex */
abstract class cy extends bq {
    protected bn e;

    protected cy() {
    }

    @Override // io.openinstall.sdk.bq
    protected void a(bh bhVar) throws IOException {
        this.e = new bn(bhVar);
    }

    @Override // io.openinstall.sdk.bq
    protected abstract void a(bi biVar, be beVar, boolean z);

    @Override // io.openinstall.sdk.bq
    protected String b() {
        return this.e.toString();
    }
}

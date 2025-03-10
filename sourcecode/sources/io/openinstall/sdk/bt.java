package io.openinstall.sdk;

import java.util.concurrent.BlockingQueue;

/* loaded from: classes.dex */
class bt implements cg {
    final /* synthetic */ BlockingQueue a;
    final /* synthetic */ int b;
    final /* synthetic */ bs c;

    bt(bs bsVar, BlockingQueue blockingQueue, int i) {
        this.c = bsVar;
        this.a = blockingQueue;
        this.b = i;
    }

    @Override // io.openinstall.sdk.cg
    public void a(Throwable th) {
        this.a.offer(th);
    }

    @Override // io.openinstall.sdk.cg
    public void a(byte[] bArr) {
        bl a;
        if (bArr.length < 12) {
            this.a.offer(new cf("invalid DNS header - too short"));
            return;
        }
        int i = ((bArr[0] & 255) << 8) + (bArr[1] & 255);
        if (i == this.b) {
            try {
                a = this.c.a(bArr);
                this.a.offer(a);
                return;
            } catch (cf e) {
                this.a.offer(e);
                return;
            }
        }
        this.a.offer(new cf("invalid message id: expected " + this.b + "; got id " + i));
    }
}

package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;

/* renamed from: j$.util.stream.l2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0192l2 extends AbstractC0204n2 implements r.b {
    C0192l2(B1 b1) {
        super(b1);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.k(this, consumer);
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.c(this, consumer);
    }
}

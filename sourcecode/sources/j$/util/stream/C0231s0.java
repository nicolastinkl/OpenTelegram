package j$.util.stream;

import j$.util.function.Consumer;

/* renamed from: j$.util.stream.s0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0231s0 extends AbstractC0237t0 {
    final Consumer b;

    C0231s0(Consumer consumer, boolean z) {
        super(z);
        this.b = consumer;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        this.b.accept(obj);
    }
}

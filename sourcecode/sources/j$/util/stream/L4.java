package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class L4 extends M4 implements r.c, j$.util.function.s {
    long e;

    L4(r.c cVar, long j, long j2) {
        super(cVar, j, j2);
    }

    L4(r.c cVar, L4 l4) {
        super(cVar, l4);
    }

    @Override // j$.util.function.s
    public void accept(long j) {
        this.e = j;
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.l(this, consumer);
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.d(this, consumer);
    }

    @Override // j$.util.stream.O4
    protected j$.util.r q(j$.util.r rVar) {
        return new L4((r.c) rVar, this);
    }

    @Override // j$.util.stream.M4
    protected void s(Object obj) {
        ((j$.util.function.s) obj).accept(this.e);
    }

    @Override // j$.util.stream.M4
    protected AbstractC0212o4 t(int i) {
        return new C0206n4(i);
    }
}

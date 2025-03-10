package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class K4 extends M4 implements r.b, j$.util.function.m {
    int e;

    K4(r.b bVar, long j, long j2) {
        super(bVar, j, j2);
    }

    K4(r.b bVar, K4 k4) {
        super(bVar, k4);
    }

    @Override // j$.util.function.m
    public void accept(int i) {
        this.e = i;
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.k(this, consumer);
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.c(this, consumer);
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }

    @Override // j$.util.stream.O4
    protected j$.util.r q(j$.util.r rVar) {
        return new K4((r.b) rVar, this);
    }

    @Override // j$.util.stream.M4
    protected void s(Object obj) {
        ((j$.util.function.m) obj).accept(this.e);
    }

    @Override // j$.util.stream.M4
    protected AbstractC0212o4 t(int i) {
        return new C0200m4(i);
    }
}

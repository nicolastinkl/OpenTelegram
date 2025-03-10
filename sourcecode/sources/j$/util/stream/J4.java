package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class J4 extends M4 implements r.a, j$.util.function.f {
    double e;

    J4(r.a aVar, long j, long j2) {
        super(aVar, j, j2);
    }

    J4(r.a aVar, J4 j4) {
        super(aVar, j4);
    }

    @Override // j$.util.function.f
    public void accept(double d) {
        this.e = d;
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.j(this, consumer);
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.b(this, consumer);
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new j$.util.function.e(this, fVar);
    }

    @Override // j$.util.stream.O4
    protected j$.util.r q(j$.util.r rVar) {
        return new J4((r.a) rVar, this);
    }

    @Override // j$.util.stream.M4
    protected void s(Object obj) {
        ((j$.util.function.f) obj).accept(this.e);
    }

    @Override // j$.util.stream.M4
    protected AbstractC0212o4 t(int i) {
        return new C0194l4(i);
    }
}

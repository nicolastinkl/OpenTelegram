package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class z4 extends E4 implements r.a {
    z4(r.a aVar, long j, long j2) {
        super(aVar, j, j2);
    }

    z4(r.a aVar, long j, long j2, long j3, long j4) {
        super(aVar, j, j2, j3, j4, null);
    }

    @Override // j$.util.stream.I4
    protected j$.util.r a(j$.util.r rVar, long j, long j2, long j3, long j4) {
        return new z4((r.a) rVar, j, j2, j3, j4);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.j(this, consumer);
    }

    @Override // j$.util.stream.E4
    protected /* bridge */ /* synthetic */ Object f() {
        return new j$.util.function.f() { // from class: j$.util.stream.y4
            @Override // j$.util.function.f
            public final void accept(double d) {
            }

            @Override // j$.util.function.f
            public j$.util.function.f j(j$.util.function.f fVar) {
                Objects.requireNonNull(fVar);
                return new j$.util.function.e(this, fVar);
            }
        };
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.b(this, consumer);
    }
}

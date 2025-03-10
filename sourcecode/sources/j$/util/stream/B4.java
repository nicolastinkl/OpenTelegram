package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class B4 extends E4 implements r.b {
    B4(r.b bVar, long j, long j2) {
        super(bVar, j, j2);
    }

    B4(r.b bVar, long j, long j2, long j3, long j4) {
        super(bVar, j, j2, j3, j4, null);
    }

    @Override // j$.util.stream.I4
    protected j$.util.r a(j$.util.r rVar, long j, long j2, long j3, long j4) {
        return new B4((r.b) rVar, j, j2, j3, j4);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.k(this, consumer);
    }

    @Override // j$.util.stream.E4
    protected /* bridge */ /* synthetic */ Object f() {
        return new j$.util.function.m() { // from class: j$.util.stream.A4
            @Override // j$.util.function.m
            public final void accept(int i) {
            }

            @Override // j$.util.function.m
            public j$.util.function.m l(j$.util.function.m mVar) {
                Objects.requireNonNull(mVar);
                return new j$.util.function.l(this, mVar);
            }
        };
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.c(this, consumer);
    }
}

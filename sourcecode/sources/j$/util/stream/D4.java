package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class D4 extends E4 implements r.c {
    D4(r.c cVar, long j, long j2) {
        super(cVar, j, j2);
    }

    D4(r.c cVar, long j, long j2, long j3, long j4) {
        super(cVar, j, j2, j3, j4, null);
    }

    @Override // j$.util.stream.I4
    protected j$.util.r a(j$.util.r rVar, long j, long j2, long j3, long j4) {
        return new D4((r.c) rVar, j, j2, j3, j4);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.l(this, consumer);
    }

    @Override // j$.util.stream.E4
    protected /* bridge */ /* synthetic */ Object f() {
        return new j$.util.function.s() { // from class: j$.util.stream.C4
            @Override // j$.util.function.s
            public final void accept(long j) {
            }

            @Override // j$.util.function.s
            public j$.util.function.s f(j$.util.function.s sVar) {
                Objects.requireNonNull(sVar);
                return new j$.util.function.r(this, sVar);
            }
        };
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.d(this, consumer);
    }
}

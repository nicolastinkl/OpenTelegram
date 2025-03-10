package j$.util;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class z extends D implements r.a {
    z() {
    }

    @Override // j$.util.r.a, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.j(this, consumer);
    }

    @Override // j$.util.r.a
    public void e(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
    }

    @Override // j$.util.r.a, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.b(this, consumer);
    }

    @Override // j$.util.r
    public java.util.Comparator getComparator() {
        throw new IllegalStateException();
    }

    @Override // j$.util.r
    public /* synthetic */ long getExactSizeIfKnown() {
        return AbstractC0112a.e(this);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return AbstractC0112a.f(this, i);
    }

    @Override // j$.util.r.a
    public boolean k(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return false;
    }

    @Override // j$.util.D, j$.util.r.b, j$.util.s, j$.util.r
    public /* bridge */ /* synthetic */ r.a trySplit() {
        return null;
    }

    @Override // j$.util.D, j$.util.r.b, j$.util.s, j$.util.r
    public /* bridge */ /* synthetic */ s trySplit() {
        return null;
    }
}

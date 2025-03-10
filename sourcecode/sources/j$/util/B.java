package j$.util;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class B extends D implements r.c {
    B() {
    }

    @Override // j$.util.r.c, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.l(this, consumer);
    }

    @Override // j$.util.r.c
    /* renamed from: d */
    public void e(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
    }

    @Override // j$.util.r.c, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.d(this, consumer);
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

    @Override // j$.util.r.c
    /* renamed from: i */
    public boolean k(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return false;
    }

    @Override // j$.util.D, j$.util.r.b, j$.util.s, j$.util.r
    public /* bridge */ /* synthetic */ r.c trySplit() {
        return null;
    }

    @Override // j$.util.D, j$.util.r.b, j$.util.s, j$.util.r
    public /* bridge */ /* synthetic */ s trySplit() {
        return null;
    }
}

package j$.util;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class A extends D implements r.b {
    A() {
    }

    @Override // j$.util.r.b, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.k(this, consumer);
    }

    @Override // j$.util.r.b
    /* renamed from: c */
    public void e(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
    }

    @Override // j$.util.r.b, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.c(this, consumer);
    }

    @Override // j$.util.r.b
    /* renamed from: g */
    public boolean k(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return false;
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

    @Override // j$.util.D, j$.util.r.b, j$.util.s, j$.util.r
    public /* bridge */ /* synthetic */ r.b trySplit() {
        return null;
    }

    @Override // j$.util.D, j$.util.r.b, j$.util.s, j$.util.r
    public /* bridge */ /* synthetic */ s trySplit() {
        return null;
    }
}

package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.r;

/* renamed from: j$.util.stream.a2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0126a2 extends Z3 implements InterfaceC0271z1, InterfaceC0244u1 {
    C0126a2() {
    }

    @Override // j$.util.stream.Z3
    /* renamed from: B */
    public r.a spliterator() {
        return super.spliterator();
    }

    @Override // j$.util.function.Consumer
    /* renamed from: C, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Double d) {
        AbstractC0238t1.a(this, d);
    }

    @Override // j$.util.stream.F1
    /* renamed from: D, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Double[] dArr, int i) {
        AbstractC0238t1.h(this, dArr, i);
    }

    @Override // j$.util.stream.F1
    /* renamed from: E, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ InterfaceC0271z1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.n(this, j, j2, nVar);
    }

    @Override // j$.util.stream.InterfaceC0261x1
    public F1 a() {
        return this;
    }

    @Override // j$.util.stream.InterfaceC0244u1, j$.util.stream.InterfaceC0261x1
    public InterfaceC0271z1 a() {
        return this;
    }

    @Override // j$.util.stream.Z3, j$.util.function.f
    public void accept(double d) {
        super.accept(d);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void accept(int i) {
        AbstractC0238t1.d(this);
        throw null;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public /* synthetic */ void accept(long j) {
        AbstractC0238t1.e(this);
        throw null;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.stream.E1, j$.util.stream.F1
    public E1 b(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.AbstractC0152e4, j$.util.stream.E1
    public void d(Object obj, int i) {
        super.d((double[]) obj, i);
    }

    @Override // j$.util.stream.AbstractC0152e4, j$.util.stream.E1
    public Object e() {
        return (double[]) super.e();
    }

    @Override // j$.util.stream.AbstractC0152e4, j$.util.stream.E1
    public void g(Object obj) {
        super.g((j$.util.function.f) obj);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void m() {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        clear();
        x(j);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ boolean o() {
        return false;
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ int p() {
        return 0;
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ Object[] q(j$.util.function.n nVar) {
        return AbstractC0238t1.g(this, nVar);
    }

    @Override // j$.util.stream.Z3, j$.util.stream.AbstractC0152e4, java.lang.Iterable, j$.lang.e
    public j$.util.s spliterator() {
        return super.spliterator();
    }

    @Override // j$.util.stream.F1
    public /* bridge */ /* synthetic */ F1 b(int i) {
        b(i);
        throw null;
    }

    @Override // j$.util.stream.Z3, j$.util.stream.AbstractC0152e4, java.lang.Iterable, j$.lang.e
    public j$.util.r spliterator() {
        return super.spliterator();
    }
}

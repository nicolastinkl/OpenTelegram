package j$.util.stream;

import j$.util.function.Consumer;

/* renamed from: j$.util.stream.y2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0267y2 extends C0158f4 implements F1, InterfaceC0261x1 {
    C0267y2() {
    }

    @Override // j$.util.stream.InterfaceC0261x1
    public F1 a() {
        return this;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public /* synthetic */ void accept(double d) {
        AbstractC0238t1.f(this);
        throw null;
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

    @Override // j$.util.stream.C0158f4, j$.util.function.Consumer
    public void accept(Object obj) {
        super.accept(obj);
    }

    @Override // j$.util.stream.F1
    public F1 b(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.C0158f4, j$.lang.e
    public void forEach(Consumer consumer) {
        super.forEach(consumer);
    }

    @Override // j$.util.stream.C0158f4, j$.util.stream.F1
    public void i(Object[] objArr, int i) {
        super.i(objArr, i);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void m() {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        clear();
        u(j);
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
    public Object[] q(j$.util.function.n nVar) {
        long count = count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) nVar.apply((int) count);
        i(objArr, 0);
        return objArr;
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ F1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.q(this, j, j2, nVar);
    }

    @Override // j$.util.stream.C0158f4, java.lang.Iterable, j$.lang.e
    public j$.util.r spliterator() {
        return super.spliterator();
    }
}

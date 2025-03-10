package j$.util.stream;

/* loaded from: classes2.dex */
abstract class W1 extends H1 implements E1 {
    W1(E1 e1, E1 e12) {
        super(e1, e12);
    }

    @Override // j$.util.stream.E1
    public void d(Object obj, int i) {
        ((E1) this.a).d(obj, i);
        ((E1) this.b).d(obj, i + ((int) ((E1) this.a).count()));
    }

    @Override // j$.util.stream.E1
    public Object e() {
        long count = count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object c = c((int) count);
        d(c, 0);
        return c;
    }

    @Override // j$.util.stream.E1
    public void g(Object obj) {
        ((E1) this.a).g(obj);
        ((E1) this.b).g(obj);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ Object[] q(j$.util.function.n nVar) {
        return AbstractC0238t1.g(this, nVar);
    }

    public String toString() {
        return count() < 32 ? String.format("%s[%s.%s]", getClass().getName(), this.a, this.b) : String.format("%s[size=%d]", getClass().getName(), Long.valueOf(count()));
    }
}

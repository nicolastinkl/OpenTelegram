package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.function.Supplier;
import java.util.Objects;

/* loaded from: classes2.dex */
final class Q4 extends AbstractC0188k4 {
    Q4(D2 d2, Supplier supplier, boolean z) {
        super(d2, supplier, z);
    }

    Q4(D2 d2, j$.util.r rVar, boolean z) {
        super(d2, rVar, z);
    }

    @Override // j$.util.r
    public boolean b(Consumer consumer) {
        Object obj;
        Objects.requireNonNull(consumer);
        boolean a = a();
        if (a) {
            C0158f4 c0158f4 = (C0158f4) this.h;
            long j = this.g;
            if (c0158f4.c != 0) {
                if (j >= c0158f4.count()) {
                    throw new IndexOutOfBoundsException(Long.toString(j));
                }
                for (int i = 0; i <= c0158f4.c; i++) {
                    long[] jArr = c0158f4.d;
                    long j2 = jArr[i];
                    Object[][] objArr = c0158f4.f;
                    if (j < j2 + objArr[i].length) {
                        obj = objArr[i][(int) (j - jArr[i])];
                    }
                }
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
            if (j >= c0158f4.b) {
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
            obj = c0158f4.e[(int) j];
            consumer.accept(obj);
        }
        return a;
    }

    @Override // j$.util.r
    public void forEachRemaining(Consumer consumer) {
        if (this.h != null || this.i) {
            while (b(consumer)) {
            }
            return;
        }
        Objects.requireNonNull(consumer);
        h();
        this.b.r0(new P4(consumer), this.d);
        this.i = true;
    }

    @Override // j$.util.stream.AbstractC0188k4
    void j() {
        C0158f4 c0158f4 = new C0158f4();
        this.h = c0158f4;
        this.e = this.b.s0(new P4(c0158f4));
        this.f = new C0129b(this);
    }

    @Override // j$.util.stream.AbstractC0188k4
    AbstractC0188k4 l(j$.util.r rVar) {
        return new Q4(this.b, rVar, this.a);
    }
}

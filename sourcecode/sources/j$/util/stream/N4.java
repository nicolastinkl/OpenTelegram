package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes2.dex */
final class N4 extends O4 implements j$.util.r, Consumer {
    Object e;

    N4(j$.util.r rVar, long j, long j2) {
        super(rVar, j, j2);
    }

    N4(j$.util.r rVar, N4 n4) {
        super(rVar, n4);
    }

    @Override // j$.util.function.Consumer
    public final void accept(Object obj) {
        this.e = obj;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.r
    public boolean b(Consumer consumer) {
        Objects.requireNonNull(consumer);
        while (r() != 1 && this.a.b(this)) {
            if (p(1L) == 1) {
                consumer.accept(this.e);
                this.e = null;
                return true;
            }
        }
        return false;
    }

    @Override // j$.util.r
    public void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        C0218p4 c0218p4 = null;
        while (true) {
            int r = r();
            if (r == 1) {
                return;
            }
            if (r != 2) {
                this.a.forEachRemaining(consumer);
                return;
            }
            if (c0218p4 == null) {
                c0218p4 = new C0218p4(128);
            } else {
                c0218p4.a = 0;
            }
            long j = 0;
            while (this.a.b(c0218p4)) {
                j++;
                if (j >= 128) {
                    break;
                }
            }
            if (j == 0) {
                return;
            }
            long p = p(j);
            for (int i = 0; i < p; i++) {
                consumer.accept(c0218p4.b[i]);
            }
        }
    }

    @Override // j$.util.r
    public Comparator getComparator() {
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

    @Override // j$.util.stream.O4
    protected j$.util.r q(j$.util.r rVar) {
        return new N4(rVar, this);
    }
}

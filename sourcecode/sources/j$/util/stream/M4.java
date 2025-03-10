package j$.util.stream;

import j$.util.AbstractC0112a;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes2.dex */
abstract class M4 extends O4 implements j$.util.s {
    M4(j$.util.s sVar, long j, long j2) {
        super(sVar, j, j2);
    }

    M4(j$.util.s sVar, M4 m4) {
        super(sVar, m4);
    }

    @Override // j$.util.s
    /* renamed from: forEachRemaining, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void e(Object obj) {
        Objects.requireNonNull(obj);
        AbstractC0212o4 abstractC0212o4 = null;
        while (true) {
            int r = r();
            if (r == 1) {
                return;
            }
            if (r != 2) {
                ((j$.util.s) this.a).e(obj);
                return;
            }
            if (abstractC0212o4 == null) {
                abstractC0212o4 = t(128);
            } else {
                abstractC0212o4.b = 0;
            }
            long j = 0;
            while (((j$.util.s) this.a).k(abstractC0212o4)) {
                j++;
                if (j >= 128) {
                    break;
                }
            }
            if (j == 0) {
                return;
            } else {
                abstractC0212o4.b(obj, p(j));
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

    protected abstract void s(Object obj);

    protected abstract AbstractC0212o4 t(int i);

    @Override // j$.util.s
    /* renamed from: tryAdvance, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean k(Object obj) {
        Objects.requireNonNull(obj);
        while (r() != 1 && ((j$.util.s) this.a).k(this)) {
            if (p(1L) == 1) {
                s(obj);
                return true;
            }
        }
        return false;
    }
}

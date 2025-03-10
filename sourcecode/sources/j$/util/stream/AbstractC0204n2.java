package j$.util.stream;

import java.util.Deque;

/* renamed from: j$.util.stream.n2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0204n2 extends AbstractC0216p2 implements j$.util.s {
    AbstractC0204n2(E1 e1) {
        super(e1);
    }

    @Override // j$.util.s
    /* renamed from: forEachRemaining, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void e(Object obj) {
        if (this.a == null) {
            return;
        }
        if (this.d != null) {
            while (k(obj)) {
            }
            return;
        }
        j$.util.r rVar = this.c;
        if (rVar != null) {
            ((j$.util.s) rVar).e(obj);
            return;
        }
        Deque f = f();
        while (true) {
            E1 e1 = (E1) a(f);
            if (e1 == null) {
                this.a = null;
                return;
            }
            e1.g(obj);
        }
    }

    @Override // j$.util.s
    /* renamed from: tryAdvance, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean k(Object obj) {
        E1 e1;
        if (!h()) {
            return false;
        }
        boolean k = ((j$.util.s) this.d).k(obj);
        if (!k) {
            if (this.c == null && (e1 = (E1) a(this.e)) != null) {
                j$.util.s spliterator = e1.spliterator();
                this.d = spliterator;
                return spliterator.k(obj);
            }
            this.a = null;
        }
        return k;
    }
}

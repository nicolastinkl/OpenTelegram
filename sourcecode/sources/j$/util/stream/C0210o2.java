package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Deque;

/* renamed from: j$.util.stream.o2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0210o2 extends AbstractC0216p2 {
    C0210o2(F1 f1) {
        super(f1);
    }

    @Override // j$.util.r
    public boolean b(Consumer consumer) {
        F1 a;
        if (!h()) {
            return false;
        }
        boolean b = this.d.b(consumer);
        if (!b) {
            if (this.c == null && (a = a(this.e)) != null) {
                j$.util.r spliterator = a.spliterator();
                this.d = spliterator;
                return spliterator.b(consumer);
            }
            this.a = null;
        }
        return b;
    }

    @Override // j$.util.r
    public void forEachRemaining(Consumer consumer) {
        if (this.a == null) {
            return;
        }
        if (this.d != null) {
            while (b(consumer)) {
            }
            return;
        }
        j$.util.r rVar = this.c;
        if (rVar != null) {
            rVar.forEachRemaining(consumer);
            return;
        }
        Deque f = f();
        while (true) {
            F1 a = a(f);
            if (a == null) {
                this.a = null;
                return;
            }
            a.forEach(consumer);
        }
    }
}

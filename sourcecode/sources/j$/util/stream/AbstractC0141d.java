package j$.util.stream;

import java.util.concurrent.atomic.AtomicReference;

/* renamed from: j$.util.stream.d, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0141d extends AbstractC0153f {
    protected final AtomicReference h;
    protected volatile boolean i;

    protected AbstractC0141d(D2 d2, j$.util.r rVar) {
        super(d2, rVar);
        this.h = new AtomicReference(null);
    }

    protected AbstractC0141d(AbstractC0141d abstractC0141d, j$.util.r rVar) {
        super(abstractC0141d, rVar);
        this.h = abstractC0141d.h;
    }

    @Override // j$.util.stream.AbstractC0153f
    public Object b() {
        if (!e()) {
            return super.b();
        }
        Object obj = this.h.get();
        return obj == null ? k() : obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x006b, code lost:
    
        r8 = r7.a();
     */
    @Override // j$.util.stream.AbstractC0153f, java.util.concurrent.CountedCompleter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void compute() {
        /*
            r10 = this;
            j$.util.r r0 = r10.b
            long r1 = r0.estimateSize()
            long r3 = r10.c
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto Lf
            goto L15
        Lf:
            long r3 = j$.util.stream.AbstractC0153f.h(r1)
            r10.c = r3
        L15:
            r5 = 0
            java.util.concurrent.atomic.AtomicReference r6 = r10.h
            r7 = r10
        L19:
            java.lang.Object r8 = r6.get()
            if (r8 != 0) goto L6f
            boolean r8 = r7.i
            if (r8 != 0) goto L34
            j$.util.stream.f r9 = r7.c()
        L27:
            j$.util.stream.d r9 = (j$.util.stream.AbstractC0141d) r9
            if (r8 != 0) goto L34
            if (r9 == 0) goto L34
            boolean r8 = r9.i
            j$.util.stream.f r9 = r9.c()
            goto L27
        L34:
            if (r8 == 0) goto L3b
            java.lang.Object r8 = r7.k()
            goto L6f
        L3b:
            int r8 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r8 <= 0) goto L6b
            j$.util.r r1 = r0.trySplit()
            if (r1 != 0) goto L46
            goto L6b
        L46:
            j$.util.stream.f r2 = r7.f(r1)
            j$.util.stream.d r2 = (j$.util.stream.AbstractC0141d) r2
            r7.d = r2
            j$.util.stream.f r8 = r7.f(r0)
            j$.util.stream.d r8 = (j$.util.stream.AbstractC0141d) r8
            r7.e = r8
            r9 = 1
            r7.setPendingCount(r9)
            if (r5 == 0) goto L60
            r0 = r1
            r7 = r2
            r2 = r8
            goto L61
        L60:
            r7 = r8
        L61:
            r5 = r5 ^ 1
            r2.fork()
            long r1 = r0.estimateSize()
            goto L19
        L6b:
            java.lang.Object r8 = r7.a()
        L6f:
            r7.g(r8)
            r7.tryComplete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.AbstractC0141d.compute():void");
    }

    @Override // j$.util.stream.AbstractC0153f
    protected void g(Object obj) {
        if (!e()) {
            super.g(obj);
        } else if (obj != null) {
            this.h.compareAndSet(null, obj);
        }
    }

    @Override // j$.util.stream.AbstractC0153f, java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public Object getRawResult() {
        return b();
    }

    protected void i() {
        this.i = true;
    }

    protected void j() {
        AbstractC0141d abstractC0141d = this;
        for (AbstractC0141d abstractC0141d2 = (AbstractC0141d) c(); abstractC0141d2 != null; abstractC0141d2 = (AbstractC0141d) abstractC0141d2.c()) {
            if (abstractC0141d2.d == abstractC0141d) {
                AbstractC0141d abstractC0141d3 = (AbstractC0141d) abstractC0141d2.e;
                if (!abstractC0141d3.i) {
                    abstractC0141d3.i();
                }
            }
            abstractC0141d = abstractC0141d2;
        }
    }

    protected abstract Object k();

    protected void l(Object obj) {
        if (obj != null) {
            this.h.compareAndSet(null, obj);
        }
    }
}

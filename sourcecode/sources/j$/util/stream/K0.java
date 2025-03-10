package j$.util.stream;

import j$.util.function.IntUnaryOperator;

/* loaded from: classes2.dex */
class K0 extends AbstractC0193l3 {
    public final /* synthetic */ int b = 0;
    final /* synthetic */ Object c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(P p, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = p;
    }

    @Override // j$.util.stream.InterfaceC0217p3, j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        switch (this.b) {
            case 0:
                this.a.accept(i);
                return;
            case 1:
                ((j$.util.function.m) ((S) this.c).m).accept(i);
                this.a.accept(i);
                return;
            case 2:
                this.a.accept(i);
                return;
            case 3:
                this.a.accept(((IntUnaryOperator) ((S) this.c).m).applyAsInt(i));
                return;
            case 4:
                this.a.accept((InterfaceC0228r3) ((j$.util.function.n) ((Q) this.c).m).apply(i));
                return;
            case 5:
                this.a.accept(((j$.util.function.o) ((T) this.c).m).applyAsLong(i));
                return;
            case 6:
                this.a.accept(((j$.wrappers.U) ((P) this.c).m).a(i));
                return;
            case 7:
                IntStream intStream = (IntStream) ((j$.util.function.n) ((S) this.c).m).apply(i);
                if (intStream != null) {
                    try {
                        intStream.sequential().S(new G0(this));
                    } catch (Throwable th) {
                        try {
                            intStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (intStream != null) {
                    intStream.close();
                    return;
                }
                return;
            default:
                if (((j$.wrappers.S) ((S) this.c).m).b(i)) {
                    this.a.accept(i);
                    return;
                }
                return;
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        switch (this.b) {
            case 7:
                this.a.n(-1L);
                break;
            case 8:
                this.a.n(-1L);
                break;
            default:
                this.a.n(j);
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(Q q2, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = q2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(S s, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(S s, InterfaceC0228r3 interfaceC0228r3, j$.lang.a aVar) {
        super(interfaceC0228r3);
        this.c = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(S s, InterfaceC0228r3 interfaceC0228r3, j$.lang.b bVar) {
        super(interfaceC0228r3);
        this.c = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(S s, InterfaceC0228r3 interfaceC0228r3, j$.lang.c cVar) {
        super(interfaceC0228r3);
        this.c = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(T t, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = t;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(U u, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = u;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(L0 l0, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = l0;
    }
}

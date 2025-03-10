package j$.util.stream;

import j$.wrappers.C0287g0;
import j$.wrappers.C0291i0;
import j$.wrappers.C0295k0;

/* renamed from: j$.util.stream.e1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0149e1 extends AbstractC0199m3 {
    public final /* synthetic */ int b = 4;
    final /* synthetic */ Object c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(P p, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = p;
    }

    @Override // j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public void accept(long j) {
        switch (this.b) {
            case 0:
                this.a.accept(j);
                return;
            case 1:
                this.a.accept(((j$.util.function.v) ((T) this.c).m).applyAsLong(j));
                return;
            case 2:
                this.a.accept((InterfaceC0228r3) ((j$.util.function.t) ((Q) this.c).m).apply(j));
                return;
            case 3:
                this.a.accept(((C0295k0) ((S) this.c).m).a(j));
                return;
            case 4:
                this.a.accept(((C0291i0) ((P) this.c).m).a(j));
                return;
            case 5:
                InterfaceC0179j1 interfaceC0179j1 = (InterfaceC0179j1) ((j$.util.function.t) ((T) this.c).m).apply(j);
                if (interfaceC0179j1 != null) {
                    try {
                        interfaceC0179j1.sequential().d(new C0131b1(this));
                    } catch (Throwable th) {
                        try {
                            interfaceC0179j1.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (interfaceC0179j1 != null) {
                    interfaceC0179j1.close();
                    return;
                }
                return;
            case 6:
                if (((C0287g0) ((T) this.c).m).b(j)) {
                    this.a.accept(j);
                    return;
                }
                return;
            default:
                ((j$.util.function.s) ((T) this.c).m).accept(j);
                this.a.accept(j);
                return;
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        switch (this.b) {
            case 5:
                this.a.n(-1L);
                break;
            case 6:
                this.a.n(-1L);
                break;
            default:
                this.a.n(j);
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(Q q2, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = q2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(S s, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(T t, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = t;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(T t, InterfaceC0228r3 interfaceC0228r3, j$.lang.a aVar) {
        super(interfaceC0228r3);
        this.c = t;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(T t, InterfaceC0228r3 interfaceC0228r3, j$.lang.b bVar) {
        super(interfaceC0228r3);
        this.c = t;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(T t, InterfaceC0228r3 interfaceC0228r3, j$.lang.c cVar) {
        super(interfaceC0228r3);
        this.c = t;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0149e1(U u, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = u;
    }
}

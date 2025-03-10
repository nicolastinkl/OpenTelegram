package j$.util.stream;

/* loaded from: classes2.dex */
class O extends AbstractC0187k3 {
    public final /* synthetic */ int b = 0;
    final /* synthetic */ Object c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O(P p, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = p;
    }

    @Override // j$.util.stream.InterfaceC0211o3, j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public void accept(double d) {
        switch (this.b) {
            case 0:
                this.a.accept(((j$.wrappers.I) ((P) this.c).m).a(d));
                return;
            case 1:
                this.a.accept((InterfaceC0228r3) ((j$.util.function.g) ((Q) this.c).m).apply(d));
                return;
            case 2:
                this.a.accept(((j$.wrappers.E) ((S) this.c).m).a(d));
                return;
            case 3:
                this.a.accept(((j$.util.function.h) ((T) this.c).m).applyAsLong(d));
                return;
            case 4:
                Z z = (Z) ((j$.util.function.g) ((P) this.c).m).apply(d);
                if (z != null) {
                    try {
                        z.sequential().j(new K(this));
                    } catch (Throwable th) {
                        try {
                            z.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (z != null) {
                    z.close();
                    return;
                }
                return;
            case 5:
                if (((j$.wrappers.C) ((P) this.c).m).b(d)) {
                    this.a.accept(d);
                    return;
                }
                return;
            default:
                ((j$.util.function.f) ((P) this.c).m).accept(d);
                this.a.accept(d);
                return;
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        switch (this.b) {
            case 4:
                this.a.n(-1L);
                break;
            case 5:
                this.a.n(-1L);
                break;
            default:
                this.a.n(j);
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O(P p, InterfaceC0228r3 interfaceC0228r3, j$.lang.a aVar) {
        super(interfaceC0228r3);
        this.c = p;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O(P p, InterfaceC0228r3 interfaceC0228r3, j$.lang.b bVar) {
        super(interfaceC0228r3);
        this.c = p;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O(P p, InterfaceC0228r3 interfaceC0228r3, j$.lang.c cVar) {
        super(interfaceC0228r3);
        this.c = p;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O(Q q2, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = q2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O(S s, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O(T t, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = t;
    }
}

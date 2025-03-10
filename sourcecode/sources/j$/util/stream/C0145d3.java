package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.function.Predicate;
import j$.util.function.ToDoubleFunction;
import j$.util.function.ToIntFunction;
import j$.util.function.ToLongFunction;

/* renamed from: j$.util.stream.d3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0145d3 extends AbstractC0205n3 {
    public final /* synthetic */ int b = 5;
    final /* synthetic */ Object c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0145d3(P p, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = p;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        switch (this.b) {
            case 0:
                ((Consumer) ((Q) this.c).m).accept(obj);
                this.a.accept((InterfaceC0228r3) obj);
                return;
            case 1:
                if (((Predicate) ((Q) this.c).m).test(obj)) {
                    this.a.accept((InterfaceC0228r3) obj);
                    return;
                }
                return;
            case 2:
                this.a.accept((InterfaceC0228r3) ((C0157f3) this.c).m.apply(obj));
                return;
            case 3:
                this.a.accept(((ToIntFunction) ((S) this.c).m).applyAsInt(obj));
                return;
            case 4:
                this.a.accept(((ToLongFunction) ((T) this.c).m).applyAsLong(obj));
                return;
            case 5:
                this.a.accept(((ToDoubleFunction) ((P) this.c).m).applyAsDouble(obj));
                return;
            default:
                Stream stream = (Stream) ((C0157f3) this.c).m.apply(obj);
                if (stream != null) {
                    try {
                        ((Stream) stream.sequential()).forEach(this.a);
                    } catch (Throwable th) {
                        try {
                            stream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (stream != null) {
                    stream.close();
                    return;
                }
                return;
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        switch (this.b) {
            case 1:
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
    public C0145d3(Q q2, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = q2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0145d3(Q q2, InterfaceC0228r3 interfaceC0228r3, j$.lang.a aVar) {
        super(interfaceC0228r3);
        this.c = q2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0145d3(S s, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0145d3(T t, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = t;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0145d3(C0157f3 c0157f3, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.c = c0157f3;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0145d3(C0157f3 c0157f3, InterfaceC0228r3 interfaceC0228r3, j$.lang.a aVar) {
        super(interfaceC0228r3);
        this.c = c0157f3;
    }
}

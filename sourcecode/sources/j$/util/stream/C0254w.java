package j$.util.stream;

import j$.util.function.Function;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* renamed from: j$.util.stream.w, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0254w extends AbstractC0205n3 {
    public final /* synthetic */ int b = 3;
    Object c;
    final /* synthetic */ Object d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0254w(C0259x c0259x, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.d = c0259x;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        switch (this.b) {
            case 0:
                if (((Set) this.c).contains(obj)) {
                    return;
                }
                ((Set) this.c).add(obj);
                this.a.accept((InterfaceC0228r3) obj);
                return;
            case 1:
                InterfaceC0179j1 interfaceC0179j1 = (InterfaceC0179j1) ((Function) ((T) this.d).m).apply(obj);
                if (interfaceC0179j1 != null) {
                    try {
                        interfaceC0179j1.sequential().d((j$.util.function.s) this.c);
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
            case 2:
                IntStream intStream = (IntStream) ((Function) ((S) this.d).m).apply(obj);
                if (intStream != null) {
                    try {
                        intStream.sequential().S((j$.util.function.m) this.c);
                    } catch (Throwable th3) {
                        try {
                            intStream.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                }
                if (intStream != null) {
                    intStream.close();
                    return;
                }
                return;
            default:
                Z z = (Z) ((Function) ((P) this.d).m).apply(obj);
                if (z != null) {
                    try {
                        z.sequential().j((j$.util.function.f) this.c);
                    } catch (Throwable th5) {
                        try {
                            z.close();
                        } catch (Throwable th6) {
                            th5.addSuppressed(th6);
                        }
                        throw th5;
                    }
                }
                if (z != null) {
                    z.close();
                    return;
                }
                return;
        }
    }

    @Override // j$.util.stream.AbstractC0205n3, j$.util.stream.InterfaceC0228r3
    public void m() {
        switch (this.b) {
            case 0:
                this.c = null;
                this.a.m();
                break;
            default:
                this.a.m();
                break;
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        switch (this.b) {
            case 0:
                this.c = new HashSet();
                this.a.n(-1L);
                break;
            case 1:
                this.a.n(-1L);
                break;
            case 2:
                this.a.n(-1L);
                break;
            default:
                this.a.n(-1L);
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0254w(P p, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.d = p;
        InterfaceC0228r3 interfaceC0228r32 = this.a;
        Objects.requireNonNull(interfaceC0228r32);
        this.c = new K(interfaceC0228r32);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0254w(S s, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.d = s;
        InterfaceC0228r3 interfaceC0228r32 = this.a;
        Objects.requireNonNull(interfaceC0228r32);
        this.c = new G0(interfaceC0228r32);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0254w(T t, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.d = t;
        InterfaceC0228r3 interfaceC0228r32 = this.a;
        Objects.requireNonNull(interfaceC0228r32);
        this.c = new C0131b1(interfaceC0228r32);
    }
}

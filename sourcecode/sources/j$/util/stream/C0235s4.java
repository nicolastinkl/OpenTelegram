package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;

/* renamed from: j$.util.stream.s4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0235s4 implements InterfaceC0211o3 {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0235s4(j$.util.function.f fVar) {
        this.b = fVar;
    }

    @Override // j$.util.stream.InterfaceC0211o3, j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public final void accept(double d) {
        switch (this.a) {
            case 0:
                ((j$.util.function.f) this.b).accept(d);
                break;
            default:
                ((Z3) this.b).accept(d);
                break;
        }
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
        }
        return Consumer.CC.$default$andThen(this, consumer);
    }

    public /* synthetic */ void b(Double d) {
        switch (this.a) {
            case 0:
                AbstractC0238t1.a(this, d);
                break;
            default:
                AbstractC0238t1.a(this, d);
                break;
        }
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        switch (this.a) {
            case 0:
                Objects.requireNonNull(fVar);
                break;
            default:
                Objects.requireNonNull(fVar);
                break;
        }
        return new j$.util.function.e(this, fVar);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void m() {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void n(long j) {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ boolean o() {
        return false;
    }

    public /* synthetic */ C0235s4(Z3 z3) {
        this.b = z3;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void accept(int i) {
        switch (this.a) {
            case 0:
                AbstractC0238t1.d(this);
                throw null;
            default:
                AbstractC0238t1.d(this);
                throw null;
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public /* synthetic */ void accept(long j) {
        switch (this.a) {
            case 0:
                AbstractC0238t1.e(this);
                throw null;
            default:
                AbstractC0238t1.e(this);
                throw null;
        }
    }

    @Override // j$.util.function.Consumer
    public /* bridge */ /* synthetic */ void accept(Object obj) {
        switch (this.a) {
            case 0:
                b((Double) obj);
                break;
            default:
                b((Double) obj);
                break;
        }
    }
}

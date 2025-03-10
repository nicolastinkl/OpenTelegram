package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class w4 implements InterfaceC0223q3 {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ Object b;

    public /* synthetic */ w4(j$.util.function.s sVar) {
        this.b = sVar;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public /* synthetic */ void accept(double d) {
        switch (this.a) {
            case 0:
                AbstractC0238t1.f(this);
                throw null;
            default:
                AbstractC0238t1.f(this);
                throw null;
        }
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
        }
        return Consumer.CC.$default$andThen(this, consumer);
    }

    public /* synthetic */ void b(Long l) {
        switch (this.a) {
            case 0:
                AbstractC0238t1.c(this, l);
                break;
            default:
                AbstractC0238t1.c(this, l);
                break;
        }
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        switch (this.a) {
            case 0:
                Objects.requireNonNull(sVar);
                break;
            default:
                Objects.requireNonNull(sVar);
                break;
        }
        return new j$.util.function.r(this, sVar);
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

    public /* synthetic */ w4(C0146d4 c0146d4) {
        this.b = c0146d4;
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

    @Override // j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public final void accept(long j) {
        switch (this.a) {
            case 0:
                ((j$.util.function.s) this.b).accept(j);
                break;
            default:
                ((C0146d4) this.b).accept(j);
                break;
        }
    }

    @Override // j$.util.function.Consumer
    public /* bridge */ /* synthetic */ void accept(Object obj) {
        switch (this.a) {
            case 0:
                b((Long) obj);
                break;
            default:
                b((Long) obj);
                break;
        }
    }
}

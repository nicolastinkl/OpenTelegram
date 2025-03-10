package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;

/* renamed from: j$.util.stream.u4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0247u4 implements InterfaceC0217p3 {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0247u4(j$.util.function.m mVar) {
        this.b = mVar;
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

    public /* synthetic */ void b(Integer num) {
        switch (this.a) {
            case 0:
                AbstractC0238t1.b(this, num);
                break;
            default:
                AbstractC0238t1.b(this, num);
                break;
        }
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        switch (this.a) {
            case 0:
                Objects.requireNonNull(mVar);
                break;
            default:
                Objects.requireNonNull(mVar);
                break;
        }
        return new j$.util.function.l(this, mVar);
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

    public /* synthetic */ C0247u4(C0134b4 c0134b4) {
        this.b = c0134b4;
    }

    @Override // j$.util.stream.InterfaceC0217p3, j$.util.stream.InterfaceC0228r3
    public final void accept(int i) {
        switch (this.a) {
            case 0:
                ((j$.util.function.m) this.b).accept(i);
                break;
            default:
                ((C0134b4) this.b).accept(i);
                break;
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
                b((Integer) obj);
                break;
            default:
                b((Integer) obj);
                break;
        }
    }
}

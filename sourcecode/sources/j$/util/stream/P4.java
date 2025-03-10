package j$.util.stream;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class P4 implements InterfaceC0228r3 {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ Object b;

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
    public final void accept(Object obj) {
        switch (this.a) {
            case 0:
                ((Consumer) this.b).accept(obj);
                break;
            default:
                ((C0158f4) this.b).accept(obj);
                break;
        }
    }
}

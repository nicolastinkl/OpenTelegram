package j$.util.stream;

/* renamed from: j$.util.stream.v, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0248v extends AbstractC0205n3 {
    boolean b;
    Object c;

    C0248v(C0259x c0259x, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        if (obj == null) {
            if (this.b) {
                return;
            }
            this.b = true;
            InterfaceC0228r3 interfaceC0228r3 = this.a;
            this.c = null;
            interfaceC0228r3.accept((InterfaceC0228r3) null);
            return;
        }
        Object obj2 = this.c;
        if (obj2 == null || !obj.equals(obj2)) {
            InterfaceC0228r3 interfaceC0228r32 = this.a;
            this.c = obj;
            interfaceC0228r32.accept((InterfaceC0228r3) obj);
        }
    }

    @Override // j$.util.stream.AbstractC0205n3, j$.util.stream.InterfaceC0228r3
    public void m() {
        this.b = false;
        this.c = null;
        this.a.m();
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        this.b = false;
        this.c = null;
        this.a.n(-1L);
    }
}

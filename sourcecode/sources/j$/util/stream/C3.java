package j$.util.stream;

/* loaded from: classes2.dex */
class C3 extends AbstractC0187k3 {
    long b;
    long c;
    final /* synthetic */ D3 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3(D3 d3, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.d = d3;
        this.b = d3.l;
        long j = d3.m;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // j$.util.stream.InterfaceC0211o3, j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public void accept(double d) {
        long j = this.b;
        if (j != 0) {
            this.b = j - 1;
            return;
        }
        long j2 = this.c;
        if (j2 > 0) {
            this.c = j2 - 1;
            this.a.accept(d);
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        this.a.n(G3.c(j, this.d.l, this.c));
    }

    @Override // j$.util.stream.AbstractC0187k3, j$.util.stream.InterfaceC0228r3
    public boolean o() {
        return this.c == 0 || this.a.o();
    }
}

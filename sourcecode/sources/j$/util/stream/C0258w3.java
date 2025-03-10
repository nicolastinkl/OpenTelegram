package j$.util.stream;

/* renamed from: j$.util.stream.w3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0258w3 extends AbstractC0193l3 {
    long b;
    long c;
    final /* synthetic */ C0263x3 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0258w3(C0263x3 c0263x3, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.d = c0263x3;
        this.b = c0263x3.l;
        long j = c0263x3.m;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // j$.util.stream.InterfaceC0217p3, j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        long j = this.b;
        if (j != 0) {
            this.b = j - 1;
            return;
        }
        long j2 = this.c;
        if (j2 > 0) {
            this.c = j2 - 1;
            this.a.accept(i);
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        this.a.n(G3.c(j, this.d.l, this.c));
    }

    @Override // j$.util.stream.AbstractC0193l3, j$.util.stream.InterfaceC0228r3
    public boolean o() {
        return this.c == 0 || this.a.o();
    }
}

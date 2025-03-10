package j$.util.stream;

/* renamed from: j$.util.stream.z3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0273z3 extends AbstractC0199m3 {
    long b;
    long c;
    final /* synthetic */ A3 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0273z3(A3 a3, InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
        this.d = a3;
        this.b = a3.l;
        long j = a3.m;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public void accept(long j) {
        long j2 = this.b;
        if (j2 != 0) {
            this.b = j2 - 1;
            return;
        }
        long j3 = this.c;
        if (j3 > 0) {
            this.c = j3 - 1;
            this.a.accept(j);
        }
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        this.a.n(G3.c(j, this.d.l, this.c));
    }

    @Override // j$.util.stream.AbstractC0199m3, j$.util.stream.InterfaceC0228r3
    public boolean o() {
        return this.c == 0 || this.a.o();
    }
}

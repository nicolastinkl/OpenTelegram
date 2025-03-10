package j$.util.stream;

import java.util.Arrays;

/* loaded from: classes2.dex */
final class N3 extends J3 {
    private C0146d4 c;

    N3(InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
    }

    @Override // j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public void accept(long j) {
        this.c.accept(j);
    }

    @Override // j$.util.stream.AbstractC0199m3, j$.util.stream.InterfaceC0228r3
    public void m() {
        long[] jArr = (long[]) this.c.e();
        Arrays.sort(jArr);
        this.a.n(jArr.length);
        int i = 0;
        if (this.b) {
            int length = jArr.length;
            while (i < length) {
                long j = jArr[i];
                if (this.a.o()) {
                    break;
                }
                this.a.accept(j);
                i++;
            }
        } else {
            int length2 = jArr.length;
            while (i < length2) {
                this.a.accept(jArr[i]);
                i++;
            }
        }
        this.a.m();
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.c = j > 0 ? new C0146d4((int) j) : new C0146d4();
    }
}

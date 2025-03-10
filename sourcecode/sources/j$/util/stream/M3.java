package j$.util.stream;

import java.util.Arrays;

/* loaded from: classes2.dex */
final class M3 extends I3 {
    private C0134b4 c;

    M3(InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
    }

    @Override // j$.util.stream.InterfaceC0217p3, j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        this.c.accept(i);
    }

    @Override // j$.util.stream.AbstractC0193l3, j$.util.stream.InterfaceC0228r3
    public void m() {
        int[] iArr = (int[]) this.c.e();
        Arrays.sort(iArr);
        this.a.n(iArr.length);
        int i = 0;
        if (this.b) {
            int length = iArr.length;
            while (i < length) {
                int i2 = iArr[i];
                if (this.a.o()) {
                    break;
                }
                this.a.accept(i2);
                i++;
            }
        } else {
            int length2 = iArr.length;
            while (i < length2) {
                this.a.accept(iArr[i]);
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
        this.c = j > 0 ? new C0134b4((int) j) : new C0134b4();
    }
}

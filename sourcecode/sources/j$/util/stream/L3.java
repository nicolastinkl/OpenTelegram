package j$.util.stream;

import java.util.Arrays;

/* loaded from: classes2.dex */
final class L3 extends H3 {
    private Z3 c;

    L3(InterfaceC0228r3 interfaceC0228r3) {
        super(interfaceC0228r3);
    }

    @Override // j$.util.stream.InterfaceC0211o3, j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public void accept(double d) {
        this.c.accept(d);
    }

    @Override // j$.util.stream.AbstractC0187k3, j$.util.stream.InterfaceC0228r3
    public void m() {
        double[] dArr = (double[]) this.c.e();
        Arrays.sort(dArr);
        this.a.n(dArr.length);
        int i = 0;
        if (this.b) {
            int length = dArr.length;
            while (i < length) {
                double d = dArr[i];
                if (this.a.o()) {
                    break;
                }
                this.a.accept(d);
                i++;
            }
        } else {
            int length2 = dArr.length;
            while (i < length2) {
                this.a.accept(dArr[i]);
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
        this.c = j > 0 ? new Z3((int) j) : new Z3();
    }
}

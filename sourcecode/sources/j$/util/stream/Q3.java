package j$.util.stream;

import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
final class Q3 extends AbstractC0161g1 {
    Q3(AbstractC0135c abstractC0135c) {
        super(abstractC0135c, EnumC0182j4.LONG_VALUE, EnumC0176i4.f27q | EnumC0176i4.o);
    }

    @Override // j$.util.stream.AbstractC0135c
    public F1 B0(D2 d2, j$.util.r rVar, j$.util.function.n nVar) {
        if (EnumC0176i4.SORTED.d(d2.p0())) {
            return d2.m0(rVar, false, nVar);
        }
        long[] jArr = (long[]) ((D1) d2.m0(rVar, true, nVar)).e();
        Arrays.sort(jArr);
        return new C0222q2(jArr);
    }

    @Override // j$.util.stream.AbstractC0135c
    public InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        Objects.requireNonNull(interfaceC0228r3);
        return EnumC0176i4.SORTED.d(i) ? interfaceC0228r3 : EnumC0176i4.SIZED.d(i) ? new V3(interfaceC0228r3) : new N3(interfaceC0228r3);
    }
}

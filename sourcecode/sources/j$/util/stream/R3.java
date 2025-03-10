package j$.util.stream;

import j$.util.Comparator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes2.dex */
final class R3 extends AbstractC0169h3 {
    private final boolean l;
    private final Comparator m;

    R3(AbstractC0135c abstractC0135c) {
        super(abstractC0135c, EnumC0182j4.REFERENCE, EnumC0176i4.f27q | EnumC0176i4.o);
        this.l = true;
        this.m = Comparator.CC.a();
    }

    R3(AbstractC0135c abstractC0135c, java.util.Comparator comparator) {
        super(abstractC0135c, EnumC0182j4.REFERENCE, EnumC0176i4.f27q | EnumC0176i4.p);
        this.l = false;
        Objects.requireNonNull(comparator);
        this.m = comparator;
    }

    @Override // j$.util.stream.AbstractC0135c
    public F1 B0(D2 d2, j$.util.r rVar, j$.util.function.n nVar) {
        if (EnumC0176i4.SORTED.d(d2.p0()) && this.l) {
            return d2.m0(rVar, false, nVar);
        }
        Object[] q2 = d2.m0(rVar, true, nVar).q(nVar);
        Arrays.sort(q2, this.m);
        return new I1(q2);
    }

    @Override // j$.util.stream.AbstractC0135c
    public InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        Objects.requireNonNull(interfaceC0228r3);
        return (EnumC0176i4.SORTED.d(i) && this.l) ? interfaceC0228r3 : EnumC0176i4.SIZED.d(i) ? new W3(interfaceC0228r3, this.m) : new S3(interfaceC0228r3, this.m);
    }
}

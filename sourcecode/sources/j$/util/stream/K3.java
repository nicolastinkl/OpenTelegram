package j$.util.stream;

import java.util.Comparator;

/* loaded from: classes2.dex */
abstract class K3 extends AbstractC0205n3 {
    protected final Comparator b;
    protected boolean c;

    K3(InterfaceC0228r3 interfaceC0228r3, Comparator comparator) {
        super(interfaceC0228r3);
        this.b = comparator;
    }

    @Override // j$.util.stream.AbstractC0205n3, j$.util.stream.InterfaceC0228r3
    public final boolean o() {
        this.c = true;
        return false;
    }
}

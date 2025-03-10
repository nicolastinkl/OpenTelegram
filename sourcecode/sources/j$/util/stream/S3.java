package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.Collection$EL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
final class S3 extends K3 {
    private ArrayList d;

    S3(InterfaceC0228r3 interfaceC0228r3, Comparator comparator) {
        super(interfaceC0228r3, comparator);
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        this.d.add(obj);
    }

    @Override // j$.util.stream.AbstractC0205n3, j$.util.stream.InterfaceC0228r3
    public void m() {
        AbstractC0112a.v(this.d, this.b);
        this.a.n(this.d.size());
        if (this.c) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (this.a.o()) {
                    break;
                } else {
                    this.a.accept((InterfaceC0228r3) next);
                }
            }
        } else {
            ArrayList arrayList = this.d;
            InterfaceC0228r3 interfaceC0228r3 = this.a;
            Objects.requireNonNull(interfaceC0228r3);
            Collection$EL.a(arrayList, new C0129b(interfaceC0228r3));
        }
        this.a.m();
        this.d = null;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.d = j >= 0 ? new ArrayList((int) j) : new ArrayList();
    }
}

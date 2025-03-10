package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.function.Supplier;
import java.util.List;

/* renamed from: j$.util.stream.b, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0129b implements Supplier, j$.util.function.t, Consumer, j$.util.function.c {
    public final /* synthetic */ int a = 2;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0129b(j$.util.r rVar) {
        this.b = rVar;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        switch (this.a) {
            case 3:
                ((InterfaceC0228r3) this.b).accept((InterfaceC0228r3) obj);
                break;
            default:
                ((List) this.b).add(obj);
                break;
        }
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
        }
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.function.t
    public Object apply(long j) {
        j$.util.function.n nVar = (j$.util.function.n) this.b;
        int i = M1.k;
        return C2.d(j, nVar);
    }

    @Override // j$.util.function.Supplier
    public Object get() {
        switch (this.a) {
            case 0:
                return (j$.util.r) this.b;
            default:
                return ((AbstractC0135c) this.b).A0();
        }
    }

    public /* synthetic */ C0129b(AbstractC0135c abstractC0135c) {
        this.b = abstractC0135c;
    }
}

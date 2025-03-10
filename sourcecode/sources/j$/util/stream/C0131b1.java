package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.b1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0131b1 implements j$.util.function.s {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ Object b;

    @Override // j$.util.function.s
    public final void accept(long j) {
        switch (this.a) {
            case 0:
                ((InterfaceC0228r3) this.b).accept(j);
                break;
            default:
                ((C0149e1) this.b).a.accept(j);
                break;
        }
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        switch (this.a) {
            case 0:
                Objects.requireNonNull(sVar);
                break;
            default:
                Objects.requireNonNull(sVar);
                break;
        }
        return new j$.util.function.r(this, sVar);
    }
}

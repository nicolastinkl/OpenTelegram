package j$.util.stream;

import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class G0 implements j$.util.function.m {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ Object b;

    @Override // j$.util.function.m
    public final void accept(int i) {
        switch (this.a) {
            case 0:
                ((InterfaceC0228r3) this.b).accept(i);
                break;
            default:
                ((K0) this.b).a.accept(i);
                break;
        }
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        switch (this.a) {
            case 0:
                Objects.requireNonNull(mVar);
                break;
            default:
                Objects.requireNonNull(mVar);
                break;
        }
        return new j$.util.function.l(this, mVar);
    }
}

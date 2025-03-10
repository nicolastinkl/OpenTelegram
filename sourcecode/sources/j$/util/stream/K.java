package j$.util.stream;

import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class K implements j$.util.function.f {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ Object b;

    @Override // j$.util.function.f
    public final void accept(double d) {
        switch (this.a) {
            case 0:
                ((InterfaceC0228r3) this.b).accept(d);
                break;
            default:
                ((O) this.b).a.accept(d);
                break;
        }
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        switch (this.a) {
            case 0:
                Objects.requireNonNull(fVar);
                break;
            default:
                Objects.requireNonNull(fVar);
                break;
        }
        return new j$.util.function.e(this, fVar);
    }
}

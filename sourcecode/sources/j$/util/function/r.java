package j$.util.function;

import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class r implements s {
    public final /* synthetic */ s a;
    public final /* synthetic */ s b;

    public /* synthetic */ r(s sVar, s sVar2) {
        this.a = sVar;
        this.b = sVar2;
    }

    @Override // j$.util.function.s
    public final void accept(long j) {
        s sVar = this.a;
        s sVar2 = this.b;
        sVar.accept(j);
        sVar2.accept(j);
    }

    @Override // j$.util.function.s
    public s f(s sVar) {
        Objects.requireNonNull(sVar);
        return new r(this, sVar);
    }
}

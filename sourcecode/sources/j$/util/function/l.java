package j$.util.function;

import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class l implements m {
    public final /* synthetic */ m a;
    public final /* synthetic */ m b;

    public /* synthetic */ l(m mVar, m mVar2) {
        this.a = mVar;
        this.b = mVar2;
    }

    @Override // j$.util.function.m
    public final void accept(int i) {
        m mVar = this.a;
        m mVar2 = this.b;
        mVar.accept(i);
        mVar2.accept(i);
    }

    @Override // j$.util.function.m
    public m l(m mVar) {
        Objects.requireNonNull(mVar);
        return new l(this, mVar);
    }
}

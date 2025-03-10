package j$.util;

import j$.util.function.Consumer;
import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class o implements j$.util.function.s {
    public final /* synthetic */ Consumer a;

    @Override // j$.util.function.s
    public final void accept(long j) {
        this.a.accept(Long.valueOf(j));
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }
}

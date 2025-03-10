package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.n4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0206n4 extends AbstractC0212o4 implements j$.util.function.s {
    final long[] c;

    C0206n4(int i) {
        this.c = new long[i];
    }

    @Override // j$.util.function.s
    public void accept(long j) {
        long[] jArr = this.c;
        int i = this.b;
        this.b = i + 1;
        jArr[i] = j;
    }

    @Override // j$.util.stream.AbstractC0212o4
    public void b(Object obj, long j) {
        j$.util.function.s sVar = (j$.util.function.s) obj;
        for (int i = 0; i < j; i++) {
            sVar.accept(this.c[i]);
        }
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }
}

package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.m4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0200m4 extends AbstractC0212o4 implements j$.util.function.m {
    final int[] c;

    C0200m4(int i) {
        this.c = new int[i];
    }

    @Override // j$.util.function.m
    public void accept(int i) {
        int[] iArr = this.c;
        int i2 = this.b;
        this.b = i2 + 1;
        iArr[i2] = i;
    }

    @Override // j$.util.stream.AbstractC0212o4
    public void b(Object obj, long j) {
        j$.util.function.m mVar = (j$.util.function.m) obj;
        for (int i = 0; i < j; i++) {
            mVar.accept(this.c[i]);
        }
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }
}

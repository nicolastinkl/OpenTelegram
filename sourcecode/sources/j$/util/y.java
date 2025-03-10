package j$.util;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;
import org.telegram.messenger.LiteMode;

/* loaded from: classes2.dex */
final class y implements r.a {
    private final double[] a;
    private int b;
    private final int c;
    private final int d;

    public y(double[] dArr, int i, int i2, int i3) {
        this.a = dArr;
        this.b = i;
        this.c = i2;
        this.d = i3 | 64 | LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM;
    }

    @Override // j$.util.r.a, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.j(this, consumer);
    }

    @Override // j$.util.r
    public int characteristics() {
        return this.d;
    }

    @Override // j$.util.s
    public void e(j$.util.function.f fVar) {
        int i;
        Objects.requireNonNull(fVar);
        double[] dArr = this.a;
        int length = dArr.length;
        int i2 = this.c;
        if (length < i2 || (i = this.b) < 0) {
            return;
        }
        this.b = i2;
        if (i < i2) {
            do {
                fVar.accept(dArr[i]);
                i++;
            } while (i < i2);
        }
    }

    @Override // j$.util.r
    public long estimateSize() {
        return this.c - this.b;
    }

    @Override // j$.util.r.a, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.b(this, consumer);
    }

    @Override // j$.util.r
    public java.util.Comparator getComparator() {
        if (AbstractC0112a.f(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }

    @Override // j$.util.r
    public /* synthetic */ long getExactSizeIfKnown() {
        return AbstractC0112a.e(this);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return AbstractC0112a.f(this, i);
    }

    @Override // j$.util.s
    public boolean k(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        int i = this.b;
        if (i < 0 || i >= this.c) {
            return false;
        }
        double[] dArr = this.a;
        this.b = i + 1;
        fVar.accept(dArr[i]);
        return true;
    }

    @Override // j$.util.s, j$.util.r
    public r.a trySplit() {
        int i = this.b;
        int i2 = (this.c + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        double[] dArr = this.a;
        this.b = i2;
        return new y(dArr, i, i2, this.d);
    }
}

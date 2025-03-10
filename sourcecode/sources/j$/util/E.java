package j$.util;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Objects;
import org.telegram.messenger.LiteMode;

/* loaded from: classes2.dex */
final class E implements r.b {
    private final int[] a;
    private int b;
    private final int c;
    private final int d;

    public E(int[] iArr, int i, int i2, int i3) {
        this.a = iArr;
        this.b = i;
        this.c = i2;
        this.d = i3 | 64 | LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM;
    }

    @Override // j$.util.r.b, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.k(this, consumer);
    }

    @Override // j$.util.s
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public void e(j$.util.function.m mVar) {
        int i;
        Objects.requireNonNull(mVar);
        int[] iArr = this.a;
        int length = iArr.length;
        int i2 = this.c;
        if (length < i2 || (i = this.b) < 0) {
            return;
        }
        this.b = i2;
        if (i < i2) {
            do {
                mVar.accept(iArr[i]);
                i++;
            } while (i < i2);
        }
    }

    @Override // j$.util.r
    public int characteristics() {
        return this.d;
    }

    @Override // j$.util.r
    public long estimateSize() {
        return this.c - this.b;
    }

    @Override // j$.util.r.b, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.c(this, consumer);
    }

    @Override // j$.util.s
    /* renamed from: g, reason: merged with bridge method [inline-methods] */
    public boolean k(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        int i = this.b;
        if (i < 0 || i >= this.c) {
            return false;
        }
        int[] iArr = this.a;
        this.b = i + 1;
        mVar.accept(iArr[i]);
        return true;
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

    @Override // j$.util.s, j$.util.r
    public r.b trySplit() {
        int i = this.b;
        int i2 = (this.c + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        int[] iArr = this.a;
        this.b = i2;
        return new E(iArr, i, i2, this.d);
    }
}

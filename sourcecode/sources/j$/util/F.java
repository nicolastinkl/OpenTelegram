package j$.util;

import j$.util.Iterator;
import j$.util.function.Consumer;
import java.util.Collection;
import java.util.Objects;
import org.telegram.messenger.LiteMode;
import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes2.dex */
class F implements r {
    private final Collection a;
    private java.util.Iterator b = null;
    private final int c;
    private long d;
    private int e;

    public F(Collection collection, int i) {
        this.a = collection;
        this.c = (i & LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM) == 0 ? i | 64 | LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM : i;
    }

    @Override // j$.util.r
    public boolean b(Consumer consumer) {
        Objects.requireNonNull(consumer);
        if (this.b == null) {
            this.b = this.a.iterator();
            this.d = this.a.size();
        }
        if (!this.b.hasNext()) {
            return false;
        }
        consumer.accept(this.b.next());
        return true;
    }

    @Override // j$.util.r
    public int characteristics() {
        return this.c;
    }

    @Override // j$.util.r
    public long estimateSize() {
        if (this.b != null) {
            return this.d;
        }
        this.b = this.a.iterator();
        long size = this.a.size();
        this.d = size;
        return size;
    }

    @Override // j$.util.r
    public void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        java.util.Iterator it = this.b;
        if (it == null) {
            it = this.a.iterator();
            this.b = it;
            this.d = this.a.size();
        }
        if (it instanceof Iterator) {
            ((Iterator) it).forEachRemaining(consumer);
        } else {
            Iterator.CC.$default$forEachRemaining(it, consumer);
        }
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

    @Override // j$.util.r
    public r trySplit() {
        long j;
        java.util.Iterator it = this.b;
        if (it == null) {
            it = this.a.iterator();
            this.b = it;
            j = this.a.size();
            this.d = j;
        } else {
            j = this.d;
        }
        if (j <= 1 || !it.hasNext()) {
            return null;
        }
        int i = this.e + 1024;
        if (i > j) {
            i = (int) j;
        }
        if (i > 33554432) {
            i = ConnectionsManager.FileTypeVideo;
        }
        Object[] objArr = new Object[i];
        int i2 = 0;
        do {
            objArr[i2] = it.next();
            i2++;
            if (i2 >= i) {
                break;
            }
        } while (it.hasNext());
        this.e = i2;
        long j2 = this.d;
        if (j2 != Long.MAX_VALUE) {
            this.d = j2 - i2;
        }
        return new x(objArr, 0, i2, this.c);
    }
}

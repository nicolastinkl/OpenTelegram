package j$.util;

import j$.util.Iterator;
import j$.util.function.Consumer;
import j$.util.n;
import j$.util.r;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: classes2.dex */
class u implements n.a, j$.util.function.m, Iterator {
    boolean a = false;
    int b;
    final /* synthetic */ r.b c;

    u(r.b bVar) {
        this.c = bVar;
    }

    @Override // j$.util.function.m
    public void accept(int i) {
        this.a = true;
        this.b = i;
    }

    @Override // j$.util.n
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public void forEachRemaining(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        while (hasNext()) {
            mVar.accept(nextInt());
        }
    }

    @Override // j$.util.n.a, j$.util.Iterator
    public void forEachRemaining(Consumer consumer) {
        if (consumer instanceof j$.util.function.m) {
            forEachRemaining((j$.util.function.m) consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        if (K.a) {
            K.a(u.class, "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)");
            throw null;
        }
        while (hasNext()) {
            consumer.accept(Integer.valueOf(nextInt()));
        }
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public boolean hasNext() {
        if (!this.a) {
            this.c.tryAdvance(this);
        }
        return this.a;
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public Integer next() {
        if (!K.a) {
            return Integer.valueOf(nextInt());
        }
        K.a(u.class, "{0} calling PrimitiveIterator.OfInt.nextInt()");
        throw null;
    }

    @Override // j$.util.n.a
    public int nextInt() {
        if (!this.a && !hasNext()) {
            throw new NoSuchElementException();
        }
        this.a = false;
        return this.b;
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public /* synthetic */ void remove() {
        Iterator.CC.a(this);
        throw null;
    }
}

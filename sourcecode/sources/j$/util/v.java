package j$.util;

import j$.util.Iterator;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: classes2.dex */
class v implements p, j$.util.function.s, Iterator {
    boolean a = false;
    long b;
    final /* synthetic */ r.c c;

    v(r.c cVar) {
        this.c = cVar;
    }

    @Override // j$.util.function.s
    public void accept(long j) {
        this.a = true;
        this.b = j;
    }

    @Override // j$.util.n
    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    public void forEachRemaining(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        while (hasNext()) {
            sVar.accept(nextLong());
        }
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }

    @Override // j$.util.p, j$.util.Iterator
    public void forEachRemaining(Consumer consumer) {
        if (consumer instanceof j$.util.function.s) {
            forEachRemaining((j$.util.function.s) consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        if (K.a) {
            K.a(v.class, "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)");
            throw null;
        }
        while (hasNext()) {
            consumer.accept(Long.valueOf(nextLong()));
        }
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public boolean hasNext() {
        if (!this.a) {
            this.c.tryAdvance(this);
        }
        return this.a;
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public Long next() {
        if (!K.a) {
            return Long.valueOf(nextLong());
        }
        K.a(v.class, "{0} calling PrimitiveIterator.OfLong.nextLong()");
        throw null;
    }

    @Override // j$.util.p
    public long nextLong() {
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

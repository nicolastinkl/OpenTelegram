package j$.util;

import j$.util.Iterator;
import j$.util.function.Consumer;
import j$.util.r;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: classes2.dex */
class w implements l, j$.util.function.f, Iterator {
    boolean a = false;
    double b;
    final /* synthetic */ r.a c;

    w(r.a aVar) {
        this.c = aVar;
    }

    @Override // j$.util.function.f
    public void accept(double d) {
        this.a = true;
        this.b = d;
    }

    @Override // j$.util.n
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public void forEachRemaining(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        while (hasNext()) {
            fVar.accept(nextDouble());
        }
    }

    @Override // j$.util.l, j$.util.Iterator
    public void forEachRemaining(Consumer consumer) {
        if (consumer instanceof j$.util.function.f) {
            forEachRemaining((j$.util.function.f) consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        if (K.a) {
            K.a(w.class, "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)");
            throw null;
        }
        while (hasNext()) {
            consumer.accept(Double.valueOf(nextDouble()));
        }
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public boolean hasNext() {
        if (!this.a) {
            this.c.tryAdvance(this);
        }
        return this.a;
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new j$.util.function.e(this, fVar);
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public Double next() {
        if (!K.a) {
            return Double.valueOf(nextDouble());
        }
        K.a(w.class, "{0} calling PrimitiveIterator.OfDouble.nextLong()");
        throw null;
    }

    @Override // j$.util.l
    public double nextDouble() {
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

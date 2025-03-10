package kotlin.sequences;

import j$.util.Iterator;
import j$.util.function.Consumer;
import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: Sequences.kt */
/* loaded from: classes.dex */
public final class DropSequence$iterator$1<T> implements Iterator<T>, KMappedMarker, j$.util.Iterator {
    private final Iterator<T> iterator;
    private int left;

    @Override // j$.util.Iterator
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.CC.$default$forEachRemaining(this, consumer);
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    DropSequence$iterator$1(DropSequence<T> dropSequence) {
        Sequence sequence;
        int i;
        sequence = ((DropSequence) dropSequence).sequence;
        this.iterator = sequence.iterator();
        i = ((DropSequence) dropSequence).count;
        this.left = i;
    }

    private final void drop() {
        while (this.left > 0 && this.iterator.hasNext()) {
            this.iterator.next();
            this.left--;
        }
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public T next() {
        drop();
        return this.iterator.next();
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public boolean hasNext() {
        drop();
        return this.iterator.hasNext();
    }
}

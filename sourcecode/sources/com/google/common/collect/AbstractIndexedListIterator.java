package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
abstract class AbstractIndexedListIterator<E> extends UnmodifiableListIterator<E> {
    private int position;
    private final int size;

    protected abstract E get(int i);

    protected AbstractIndexedListIterator(int i, int i2) {
        Preconditions.checkPositionIndex(i2, i);
        this.size = i;
        this.position = i2;
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public final boolean hasNext() {
        return this.position < this.size;
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public final E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.position;
        this.position = i + 1;
        return get(i);
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.position;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.position > 0;
    }

    @Override // java.util.ListIterator
    public final E previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i = this.position - 1;
        this.position = i;
        return get(i);
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.position - 1;
    }
}

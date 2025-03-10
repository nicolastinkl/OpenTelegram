package com.google.android.gms.internal.mlkit_common;

import java.util.ListIterator;

/* JADX WARN: Unexpected interfaces in signature: [j$.util.Iterator] */
/* compiled from: com.google.mlkit:common@@17.0.0 */
/* loaded from: classes.dex */
public abstract class zzaj<E> extends zzak<E> implements ListIterator<E> {
    protected zzaj() {
    }

    @Override // java.util.ListIterator
    @Deprecated
    public final void add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    public final void set(E e) {
        throw new UnsupportedOperationException();
    }
}

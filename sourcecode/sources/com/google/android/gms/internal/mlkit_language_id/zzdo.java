package com.google.android.gms.internal.mlkit_language_id;

import j$.util.Iterator;
import j$.util.function.Consumer;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
abstract class zzdo implements zzds, Iterator {
    zzdo() {
    }

    @Override // j$.util.Iterator
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.CC.$default$forEachRemaining(this, consumer);
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, j$.util.Iterator
    public /* synthetic */ Object next() {
        return Byte.valueOf(zza());
    }
}

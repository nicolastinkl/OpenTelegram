package j$.lang;

import j$.util.AbstractC0112a;
import j$.util.DesugarCollections;
import j$.util.function.Consumer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: j$.lang.Iterable$-EL, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Iterable$EL {
    public static void forEach(Iterable iterable, Consumer consumer) {
        if (iterable instanceof e) {
            ((e) iterable).forEach(consumer);
            return;
        }
        if (iterable instanceof Collection) {
            AbstractC0112a.a((Collection) iterable, consumer);
            return;
        }
        if (DesugarCollections.a.isInstance(iterable)) {
            DesugarCollections.c(iterable, consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }
}

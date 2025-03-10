package j$.util;

import j$.util.function.Consumer;
import j$.util.function.Predicate;
import j$.util.stream.Stream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

/* renamed from: j$.util.Collection$-EL, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Collection$EL {
    public static /* synthetic */ void a(Collection collection, Consumer consumer) {
        if (collection instanceof InterfaceC0113b) {
            ((InterfaceC0113b) collection).forEach(consumer);
        } else {
            AbstractC0112a.a(collection, consumer);
        }
    }

    public static r b(Collection collection) {
        if (collection instanceof InterfaceC0113b) {
            return ((InterfaceC0113b) collection).spliterator();
        }
        if (collection instanceof LinkedHashSet) {
            LinkedHashSet linkedHashSet = (LinkedHashSet) collection;
            Objects.requireNonNull(linkedHashSet);
            return new F(linkedHashSet, 17);
        }
        if (collection instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet) collection;
            return new q(sortedSet, sortedSet, 21);
        }
        if (collection instanceof Set) {
            Set set = (Set) collection;
            Objects.requireNonNull(set);
            return new F(set, 1);
        }
        if (!(collection instanceof List)) {
            Objects.requireNonNull(collection);
            return new F(collection, 0);
        }
        List list = (List) collection;
        Objects.requireNonNull(list);
        return new F(list, 16);
    }

    public static /* synthetic */ boolean removeIf(Collection collection, Predicate predicate) {
        return collection instanceof InterfaceC0113b ? ((InterfaceC0113b) collection).k(predicate) : AbstractC0112a.h(collection, predicate);
    }

    public static /* synthetic */ Stream stream(Collection collection) {
        return collection instanceof InterfaceC0113b ? ((InterfaceC0113b) collection).stream() : AbstractC0112a.i(collection);
    }
}

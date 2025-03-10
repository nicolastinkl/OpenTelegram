package j$.util;

import j$.util.Comparator;
import j$.util.function.Consumer;
import j$.util.function.Predicate;
import j$.util.r;
import j$.util.stream.AbstractC0238t1;
import j$.util.stream.Stream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalLong;

/* renamed from: j$.util.a, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC0112a {
    public static void a(Collection collection, Consumer consumer) {
        Objects.requireNonNull(consumer);
        java.util.Iterator it = collection.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    public static void b(r.a aVar, Consumer consumer) {
        if (consumer instanceof j$.util.function.f) {
            aVar.e((j$.util.function.f) consumer);
        } else {
            if (K.a) {
                K.a(aVar.getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            aVar.e(new C0122k(consumer));
        }
    }

    public static void c(r.b bVar, Consumer consumer) {
        if (consumer instanceof j$.util.function.m) {
            bVar.e((j$.util.function.m) consumer);
        } else {
            if (K.a) {
                K.a(bVar.getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            bVar.e(new m(consumer));
        }
    }

    public static void d(r.c cVar, Consumer consumer) {
        if (consumer instanceof j$.util.function.s) {
            cVar.e((j$.util.function.s) consumer);
        } else {
            if (K.a) {
                K.a(cVar.getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            cVar.e(new o(consumer));
        }
    }

    public static long e(r rVar) {
        if ((rVar.characteristics() & 64) == 0) {
            return -1L;
        }
        return rVar.estimateSize();
    }

    public static boolean f(r rVar, int i) {
        return (rVar.characteristics() & i) == i;
    }

    public static Stream g(Collection collection) {
        return AbstractC0238t1.y(Collection$EL.b(collection), true);
    }

    public static boolean h(Collection collection, Predicate predicate) {
        if (DesugarCollections.a.isInstance(collection)) {
            return DesugarCollections.d(collection, predicate);
        }
        Objects.requireNonNull(predicate);
        boolean z = false;
        java.util.Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    public static Stream i(Collection collection) {
        return AbstractC0238t1.y(Collection$EL.b(collection), false);
    }

    public static boolean j(r.a aVar, Consumer consumer) {
        if (consumer instanceof j$.util.function.f) {
            return aVar.k((j$.util.function.f) consumer);
        }
        if (K.a) {
            K.a(aVar.getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return aVar.k(new C0122k(consumer));
    }

    public static boolean k(r.b bVar, Consumer consumer) {
        if (consumer instanceof j$.util.function.m) {
            return bVar.k((j$.util.function.m) consumer);
        }
        if (K.a) {
            K.a(bVar.getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return bVar.k(new m(consumer));
    }

    public static boolean l(r.c cVar, Consumer consumer) {
        if (consumer instanceof j$.util.function.s) {
            return cVar.k((j$.util.function.s) consumer);
        }
        if (K.a) {
            K.a(cVar.getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return cVar.k(new o(consumer));
    }

    public static Optional m(java.util.Optional optional) {
        if (optional == null) {
            return null;
        }
        return optional.isPresent() ? Optional.of(optional.get()) : Optional.empty();
    }

    public static C0120i n(OptionalDouble optionalDouble) {
        if (optionalDouble == null) {
            return null;
        }
        return optionalDouble.isPresent() ? C0120i.d(optionalDouble.getAsDouble()) : C0120i.a();
    }

    public static OptionalInt o(java.util.OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        return optionalInt.isPresent() ? OptionalInt.of(optionalInt.getAsInt()) : OptionalInt.empty();
    }

    public static C0121j p(OptionalLong optionalLong) {
        if (optionalLong == null) {
            return null;
        }
        return optionalLong.isPresent() ? C0121j.d(optionalLong.getAsLong()) : C0121j.a();
    }

    public static java.util.Optional q(Optional optional) {
        if (optional == null) {
            return null;
        }
        return optional.isPresent() ? java.util.Optional.of(optional.get()) : java.util.Optional.empty();
    }

    public static OptionalDouble r(C0120i c0120i) {
        if (c0120i == null) {
            return null;
        }
        return c0120i.c() ? OptionalDouble.of(c0120i.b()) : OptionalDouble.empty();
    }

    public static java.util.OptionalInt s(OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        return optionalInt.isPresent() ? java.util.OptionalInt.of(optionalInt.getAsInt()) : java.util.OptionalInt.empty();
    }

    public static OptionalLong t(C0121j c0121j) {
        if (c0121j == null) {
            return null;
        }
        return c0121j.c() ? OptionalLong.of(c0121j.b()) : OptionalLong.empty();
    }

    public static boolean u(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static void v(List list, java.util.Comparator comparator) {
        if (DesugarCollections.b.isInstance(list)) {
            DesugarCollections.e(list, comparator);
            return;
        }
        Object[] array = list.toArray();
        Arrays.sort(array, comparator);
        ListIterator listIterator = list.listIterator();
        for (Object obj : array) {
            listIterator.next();
            listIterator.set(obj);
        }
    }

    public static /* synthetic */ java.util.Comparator w(java.util.Comparator comparator, java.util.Comparator comparator2) {
        return comparator instanceof Comparator ? ((Comparator) comparator).thenComparing(comparator2) : Comparator.CC.$default$thenComparing(comparator, comparator2);
    }
}

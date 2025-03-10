package j$.util.stream;

import j$.util.concurrent.ConcurrentHashMap;
import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: j$.util.stream.x, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0259x extends AbstractC0169h3 {
    C0259x(AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i) {
        super(abstractC0135c, enumC0182j4, i);
    }

    @Override // j$.util.stream.AbstractC0135c
    F1 B0(D2 d2, j$.util.r rVar, j$.util.function.n nVar) {
        if (EnumC0176i4.DISTINCT.d(d2.p0())) {
            return d2.m0(rVar, false, nVar);
        }
        if (EnumC0176i4.ORDERED.d(d2.p0())) {
            return I0(d2, rVar);
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        new C0231s0(new C0236t(atomicBoolean, concurrentHashMap), false).c(d2, rVar);
        Collection keySet = concurrentHashMap.keySet();
        if (atomicBoolean.get()) {
            HashSet hashSet = new HashSet(keySet);
            hashSet.add(null);
            keySet = hashSet;
        }
        return new J1(keySet);
    }

    @Override // j$.util.stream.AbstractC0135c
    j$.util.r C0(D2 d2, j$.util.r rVar) {
        return EnumC0176i4.DISTINCT.d(d2.p0()) ? d2.t0(rVar) : EnumC0176i4.ORDERED.d(d2.p0()) ? ((J1) I0(d2, rVar)).spliterator() : new C0229r4(d2.t0(rVar));
    }

    @Override // j$.util.stream.AbstractC0135c
    InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        Objects.requireNonNull(interfaceC0228r3);
        return EnumC0176i4.DISTINCT.d(i) ? interfaceC0228r3 : EnumC0176i4.SORTED.d(i) ? new C0248v(this, interfaceC0228r3) : new C0254w(this, interfaceC0228r3);
    }

    F1 I0(D2 d2, j$.util.r rVar) {
        C0242u c0242u = new Supplier() { // from class: j$.util.stream.u
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new LinkedHashSet();
            }
        };
        r rVar2 = new BiConsumer() { // from class: j$.util.stream.r
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((LinkedHashSet) obj).add(obj2);
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        };
        return new J1((Collection) new E2(EnumC0182j4.REFERENCE, new BiConsumer() { // from class: j$.util.stream.s
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((LinkedHashSet) obj).addAll((LinkedHashSet) obj2);
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        }, rVar2, c0242u).c(d2, rVar));
    }
}

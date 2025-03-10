package j$.wrappers;

import j$.util.function.Consumer;
import java.util.PrimitiveIterator;

/* renamed from: j$.wrappers.a, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0274a implements j$.util.l {
    final /* synthetic */ PrimitiveIterator.OfDouble a;

    private /* synthetic */ C0274a(PrimitiveIterator.OfDouble ofDouble) {
        this.a = ofDouble;
    }

    public static /* synthetic */ j$.util.l a(PrimitiveIterator.OfDouble ofDouble) {
        if (ofDouble == null) {
            return null;
        }
        return ofDouble instanceof C0276b ? ((C0276b) ofDouble).a : new C0274a(ofDouble);
    }

    @Override // j$.util.l
    /* renamed from: e */
    public /* synthetic */ void forEachRemaining(j$.util.function.f fVar) {
        this.a.forEachRemaining(C0311z.a(fVar));
    }

    @Override // j$.util.l, j$.util.Iterator
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.n
    public /* synthetic */ void forEachRemaining(Object obj) {
        this.a.forEachRemaining((PrimitiveIterator.OfDouble) obj);
    }

    @Override // java.util.Iterator
    public /* synthetic */ boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // j$.util.l, java.util.Iterator
    public /* synthetic */ Double next() {
        return this.a.next();
    }

    @Override // java.util.Iterator
    public /* synthetic */ Object next() {
        return this.a.next();
    }

    @Override // j$.util.l
    public /* synthetic */ double nextDouble() {
        return this.a.nextDouble();
    }

    @Override // java.util.Iterator
    public /* synthetic */ void remove() {
        this.a.remove();
    }
}

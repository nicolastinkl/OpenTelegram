package j$.wrappers;

import j$.util.function.Consumer;
import java.util.PrimitiveIterator;

/* renamed from: j$.wrappers.e, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0282e implements j$.util.p {
    final /* synthetic */ PrimitiveIterator.OfLong a;

    private /* synthetic */ C0282e(PrimitiveIterator.OfLong ofLong) {
        this.a = ofLong;
    }

    public static /* synthetic */ j$.util.p a(PrimitiveIterator.OfLong ofLong) {
        if (ofLong == null) {
            return null;
        }
        return ofLong instanceof C0284f ? ((C0284f) ofLong).a : new C0282e(ofLong);
    }

    @Override // j$.util.p
    /* renamed from: d */
    public /* synthetic */ void forEachRemaining(j$.util.function.s sVar) {
        this.a.forEachRemaining(C0281d0.a(sVar));
    }

    @Override // j$.util.p, j$.util.Iterator
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.n
    public /* synthetic */ void forEachRemaining(Object obj) {
        this.a.forEachRemaining((PrimitiveIterator.OfLong) obj);
    }

    @Override // java.util.Iterator
    public /* synthetic */ boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // j$.util.p, java.util.Iterator
    public /* synthetic */ Long next() {
        return this.a.next();
    }

    @Override // java.util.Iterator
    public /* synthetic */ Object next() {
        return this.a.next();
    }

    @Override // j$.util.p
    public /* synthetic */ long nextLong() {
        return this.a.nextLong();
    }

    @Override // java.util.Iterator
    public /* synthetic */ void remove() {
        this.a.remove();
    }
}

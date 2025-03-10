package j$.wrappers;

import j$.util.function.Consumer;
import j$.util.n;
import java.util.PrimitiveIterator;

/* renamed from: j$.wrappers.c, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0278c implements n.a {
    final /* synthetic */ PrimitiveIterator.OfInt a;

    private /* synthetic */ C0278c(PrimitiveIterator.OfInt ofInt) {
        this.a = ofInt;
    }

    public static /* synthetic */ n.a a(PrimitiveIterator.OfInt ofInt) {
        if (ofInt == null) {
            return null;
        }
        return ofInt instanceof C0280d ? ((C0280d) ofInt).a : new C0278c(ofInt);
    }

    @Override // j$.util.n.a
    /* renamed from: c */
    public /* synthetic */ void forEachRemaining(j$.util.function.m mVar) {
        this.a.forEachRemaining(O.a(mVar));
    }

    @Override // j$.util.n.a, j$.util.Iterator
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.n
    public /* synthetic */ void forEachRemaining(Object obj) {
        this.a.forEachRemaining((PrimitiveIterator.OfInt) obj);
    }

    @Override // java.util.Iterator
    public /* synthetic */ boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // j$.util.n.a, java.util.Iterator
    public /* synthetic */ Integer next() {
        return this.a.next();
    }

    @Override // java.util.Iterator
    public /* synthetic */ Object next() {
        return this.a.next();
    }

    @Override // j$.util.n.a
    public /* synthetic */ int nextInt() {
        return this.a.nextInt();
    }

    @Override // java.util.Iterator
    public /* synthetic */ void remove() {
        this.a.remove();
    }
}

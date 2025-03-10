package j$.wrappers;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Comparator;
import java.util.Spliterator;

/* renamed from: j$.wrappers.i, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0290i implements r.a {
    final /* synthetic */ Spliterator.OfDouble a;

    private /* synthetic */ C0290i(Spliterator.OfDouble ofDouble) {
        this.a = ofDouble;
    }

    public static /* synthetic */ r.a a(Spliterator.OfDouble ofDouble) {
        if (ofDouble == null) {
            return null;
        }
        return ofDouble instanceof C0292j ? ((C0292j) ofDouble).a : new C0290i(ofDouble);
    }

    @Override // j$.util.r.a, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return this.a.tryAdvance(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.r
    public /* synthetic */ int characteristics() {
        return this.a.characteristics();
    }

    @Override // j$.util.r.a
    public /* synthetic */ void e(j$.util.function.f fVar) {
        this.a.forEachRemaining(C0311z.a(fVar));
    }

    @Override // j$.util.r
    public /* synthetic */ long estimateSize() {
        return this.a.estimateSize();
    }

    @Override // j$.util.r.a, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.s
    /* renamed from: forEachRemaining */
    public /* synthetic */ void e(Object obj) {
        this.a.forEachRemaining((Spliterator.OfDouble) obj);
    }

    @Override // j$.util.r
    public /* synthetic */ Comparator getComparator() {
        return this.a.getComparator();
    }

    @Override // j$.util.r
    public /* synthetic */ long getExactSizeIfKnown() {
        return this.a.getExactSizeIfKnown();
    }

    @Override // j$.util.r
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return this.a.hasCharacteristics(i);
    }

    @Override // j$.util.r.a
    public /* synthetic */ boolean k(j$.util.function.f fVar) {
        return this.a.tryAdvance(C0311z.a(fVar));
    }

    @Override // j$.util.s
    /* renamed from: tryAdvance */
    public /* synthetic */ boolean k(Object obj) {
        return this.a.tryAdvance((Spliterator.OfDouble) obj);
    }

    @Override // j$.util.r.a, j$.util.s, j$.util.r
    public /* synthetic */ r.a trySplit() {
        return a(this.a.trySplit());
    }

    @Override // j$.util.r
    public /* synthetic */ j$.util.r trySplit() {
        return C0286g.a(this.a.trySplit());
    }

    @Override // j$.util.s, j$.util.r
    public /* synthetic */ j$.util.s trySplit() {
        return C0301o.a(this.a.trySplit());
    }
}

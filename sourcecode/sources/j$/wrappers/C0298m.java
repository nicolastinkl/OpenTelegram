package j$.wrappers;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Comparator;
import java.util.Spliterator;

/* renamed from: j$.wrappers.m, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0298m implements r.c {
    final /* synthetic */ Spliterator.OfLong a;

    private /* synthetic */ C0298m(Spliterator.OfLong ofLong) {
        this.a = ofLong;
    }

    public static /* synthetic */ r.c a(Spliterator.OfLong ofLong) {
        if (ofLong == null) {
            return null;
        }
        return ofLong instanceof C0300n ? ((C0300n) ofLong).a : new C0298m(ofLong);
    }

    @Override // j$.util.r.c, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return this.a.tryAdvance(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.r
    public /* synthetic */ int characteristics() {
        return this.a.characteristics();
    }

    @Override // j$.util.r.c
    /* renamed from: d */
    public /* synthetic */ void e(j$.util.function.s sVar) {
        this.a.forEachRemaining(C0281d0.a(sVar));
    }

    @Override // j$.util.r
    public /* synthetic */ long estimateSize() {
        return this.a.estimateSize();
    }

    @Override // j$.util.r.c, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.s
    /* renamed from: forEachRemaining */
    public /* synthetic */ void e(Object obj) {
        this.a.forEachRemaining((Spliterator.OfLong) obj);
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

    @Override // j$.util.r.c
    /* renamed from: i */
    public /* synthetic */ boolean k(j$.util.function.s sVar) {
        return this.a.tryAdvance(C0281d0.a(sVar));
    }

    @Override // j$.util.s
    /* renamed from: tryAdvance */
    public /* synthetic */ boolean k(Object obj) {
        return this.a.tryAdvance((Spliterator.OfLong) obj);
    }

    @Override // j$.util.r.c, j$.util.s, j$.util.r
    public /* synthetic */ r.c trySplit() {
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

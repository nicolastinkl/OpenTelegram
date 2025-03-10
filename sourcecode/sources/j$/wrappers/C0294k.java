package j$.wrappers;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Comparator;
import java.util.Spliterator;

/* renamed from: j$.wrappers.k, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0294k implements r.b {
    final /* synthetic */ Spliterator.OfInt a;

    private /* synthetic */ C0294k(Spliterator.OfInt ofInt) {
        this.a = ofInt;
    }

    public static /* synthetic */ r.b a(Spliterator.OfInt ofInt) {
        if (ofInt == null) {
            return null;
        }
        return ofInt instanceof C0296l ? ((C0296l) ofInt).a : new C0294k(ofInt);
    }

    @Override // j$.util.r.b, j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return this.a.tryAdvance(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.r.b
    /* renamed from: c */
    public /* synthetic */ void e(j$.util.function.m mVar) {
        this.a.forEachRemaining(O.a(mVar));
    }

    @Override // j$.util.r
    public /* synthetic */ int characteristics() {
        return this.a.characteristics();
    }

    @Override // j$.util.r
    public /* synthetic */ long estimateSize() {
        return this.a.estimateSize();
    }

    @Override // j$.util.r.b, j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.s
    /* renamed from: forEachRemaining */
    public /* synthetic */ void e(Object obj) {
        this.a.forEachRemaining((Spliterator.OfInt) obj);
    }

    @Override // j$.util.r.b
    /* renamed from: g */
    public /* synthetic */ boolean k(j$.util.function.m mVar) {
        return this.a.tryAdvance(O.a(mVar));
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

    @Override // j$.util.s
    /* renamed from: tryAdvance */
    public /* synthetic */ boolean k(Object obj) {
        return this.a.tryAdvance((Spliterator.OfInt) obj);
    }

    @Override // j$.util.r.b, j$.util.s, j$.util.r
    public /* synthetic */ r.b trySplit() {
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

package j$.util.stream;

import j$.util.r;
import org.telegram.messenger.LiteMode;

/* loaded from: classes2.dex */
class A3 extends AbstractC0161g1 {
    final /* synthetic */ long l;
    final /* synthetic */ long m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    A3(AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, long j, long j2) {
        super(abstractC0135c, enumC0182j4, i);
        this.l = j;
        this.m = j2;
    }

    @Override // j$.util.stream.AbstractC0135c
    F1 B0(D2 d2, j$.util.r rVar, j$.util.function.n nVar) {
        long n0 = d2.n0(rVar);
        if (n0 > 0 && rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            return C2.h(d2, G3.b(d2.o0(), rVar, this.l, this.m), true);
        }
        return !EnumC0176i4.ORDERED.d(d2.p0()) ? C2.h(this, K0((r.c) d2.t0(rVar), this.l, this.m, n0), true) : (F1) new F3(this, d2, rVar, nVar, this.l, this.m).invoke();
    }

    @Override // j$.util.stream.AbstractC0135c
    j$.util.r C0(D2 d2, j$.util.r rVar) {
        long d;
        long n0 = d2.n0(rVar);
        if (n0 > 0 && rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            r.c cVar = (r.c) d2.t0(rVar);
            long j = this.l;
            d = G3.d(j, this.m);
            return new D4(cVar, j, d);
        }
        return !EnumC0176i4.ORDERED.d(d2.p0()) ? K0((r.c) d2.t0(rVar), this.l, this.m, n0) : ((F1) new F3(this, d2, rVar, new j$.util.function.n() { // from class: j$.util.stream.y3
            @Override // j$.util.function.n
            public final Object apply(int i) {
                return new Long[i];
            }
        }, this.l, this.m).invoke()).spliterator();
    }

    @Override // j$.util.stream.AbstractC0135c
    InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        return new C0273z3(this, interfaceC0228r3);
    }

    r.c K0(r.c cVar, long j, long j2, long j3) {
        long j4;
        long j5;
        if (j <= j3) {
            long j6 = j3 - j;
            j5 = j2 >= 0 ? Math.min(j2, j6) : j6;
            j4 = 0;
        } else {
            j4 = j;
            j5 = j2;
        }
        return new L4(cVar, j4, j5);
    }
}

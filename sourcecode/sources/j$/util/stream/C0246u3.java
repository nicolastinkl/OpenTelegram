package j$.util.stream;

import org.telegram.messenger.LiteMode;

/* renamed from: j$.util.stream.u3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0246u3 extends AbstractC0169h3 {
    final /* synthetic */ long l;
    final /* synthetic */ long m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0246u3(AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, long j, long j2) {
        super(abstractC0135c, enumC0182j4, i);
        this.l = j;
        this.m = j2;
    }

    @Override // j$.util.stream.AbstractC0135c
    F1 B0(D2 d2, j$.util.r rVar, j$.util.function.n nVar) {
        long n0 = d2.n0(rVar);
        if (n0 > 0 && rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            return C2.e(d2, G3.b(d2.o0(), rVar, this.l, this.m), true, nVar);
        }
        return !EnumC0176i4.ORDERED.d(d2.p0()) ? C2.e(this, I0(d2.t0(rVar), this.l, this.m, n0), true, nVar) : (F1) new F3(this, d2, rVar, nVar, this.l, this.m).invoke();
    }

    @Override // j$.util.stream.AbstractC0135c
    j$.util.r C0(D2 d2, j$.util.r rVar) {
        long d;
        long n0 = d2.n0(rVar);
        if (n0 > 0 && rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            j$.util.r t0 = d2.t0(rVar);
            long j = this.l;
            d = G3.d(j, this.m);
            return new H4(t0, j, d);
        }
        return !EnumC0176i4.ORDERED.d(d2.p0()) ? I0(d2.t0(rVar), this.l, this.m, n0) : ((F1) new F3(this, d2, rVar, new j$.util.function.n() { // from class: j$.util.stream.s3
            @Override // j$.util.function.n
            public final Object apply(int i) {
                return new Object[i];
            }
        }, this.l, this.m).invoke()).spliterator();
    }

    @Override // j$.util.stream.AbstractC0135c
    InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        return new C0240t3(this, interfaceC0228r3);
    }

    j$.util.r I0(j$.util.r rVar, long j, long j2, long j3) {
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
        return new N4(rVar, j4, j5);
    }
}

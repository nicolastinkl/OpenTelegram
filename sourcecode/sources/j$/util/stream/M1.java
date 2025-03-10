package j$.util.stream;

import j$.util.function.BiFunction;
import j$.util.function.Function;

/* loaded from: classes2.dex */
final class M1 extends S1 {
    public static final /* synthetic */ int k = 0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public M1(D2 d2, j$.util.r rVar, int i) {
        super(d2, rVar, new j$.util.function.t() { // from class: j$.util.stream.L1
            @Override // j$.util.function.t
            public final Object apply(long j) {
                return C2.j(j);
            }
        }, new j$.util.function.b() { // from class: j$.util.stream.K1
            @Override // j$.util.function.BiFunction
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction.CC.$default$andThen(this, function);
            }

            @Override // j$.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return new T1((InterfaceC0271z1) obj, (InterfaceC0271z1) obj2);
            }
        });
        if (i == 1) {
            super(d2, rVar, new j$.util.function.t() { // from class: j$.util.stream.O1
                @Override // j$.util.function.t
                public final Object apply(long j) {
                    return C2.p(j);
                }
            }, new j$.util.function.b() { // from class: j$.util.stream.N1
                @Override // j$.util.function.BiFunction
                public /* synthetic */ BiFunction andThen(Function function) {
                    return BiFunction.CC.$default$andThen(this, function);
                }

                @Override // j$.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return new U1((B1) obj, (B1) obj2);
                }
            });
        } else if (i != 2) {
        } else {
            super(d2, rVar, new j$.util.function.t() { // from class: j$.util.stream.Q1
                @Override // j$.util.function.t
                public final Object apply(long j) {
                    return C2.q(j);
                }
            }, new j$.util.function.b() { // from class: j$.util.stream.P1
                @Override // j$.util.function.BiFunction
                public /* synthetic */ BiFunction andThen(Function function) {
                    return BiFunction.CC.$default$andThen(this, function);
                }

                @Override // j$.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return new V1((D1) obj, (D1) obj2);
                }
            });
        }
    }

    public M1(D2 d2, j$.util.function.n nVar, j$.util.r rVar) {
        super(d2, rVar, new C0129b(nVar), new j$.util.function.b() { // from class: j$.util.stream.R1
            @Override // j$.util.function.BiFunction
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction.CC.$default$andThen(this, function);
            }

            @Override // j$.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return new X1((F1) obj, (F1) obj2);
            }
        });
    }
}

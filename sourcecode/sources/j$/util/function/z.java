package j$.util.function;

import j$.util.function.Predicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class z implements Predicate {
    public final /* synthetic */ int a;
    public final /* synthetic */ Predicate b;
    public final /* synthetic */ Predicate c;

    public /* synthetic */ z(Predicate predicate, Predicate predicate2, int i) {
        this.a = i;
        if (i != 1) {
            this.b = predicate;
            this.c = predicate2;
        } else {
            this.b = predicate;
            this.c = predicate2;
        }
    }

    @Override // j$.util.function.Predicate
    public /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.a) {
        }
        return Predicate.CC.$default$and(this, predicate);
    }

    @Override // j$.util.function.Predicate
    public /* synthetic */ Predicate negate() {
        switch (this.a) {
        }
        return Predicate.CC.$default$negate(this);
    }

    @Override // j$.util.function.Predicate
    public /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.a) {
        }
        return Predicate.CC.$default$or(this, predicate);
    }

    @Override // j$.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.a) {
            case 0:
                Predicate predicate = this.b;
                Predicate predicate2 = this.c;
                if (!predicate.test(obj) || !predicate2.test(obj)) {
                }
                break;
            default:
                Predicate predicate3 = this.b;
                Predicate predicate4 = this.c;
                if (predicate3.test(obj) || predicate4.test(obj)) {
                }
                break;
        }
        return false;
    }
}

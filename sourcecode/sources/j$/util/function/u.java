package j$.util.function;

import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class u implements v {
    public final /* synthetic */ int a;
    public final /* synthetic */ v b;
    public final /* synthetic */ v c;

    public /* synthetic */ u(v vVar, v vVar2, int i) {
        this.a = i;
        if (i != 1) {
            this.b = vVar;
            this.c = vVar2;
        } else {
            this.b = vVar;
            this.c = vVar2;
        }
    }

    @Override // j$.util.function.v
    public v a(v vVar) {
        int i = 0;
        switch (this.a) {
            case 0:
                Objects.requireNonNull(vVar);
                break;
            default:
                Objects.requireNonNull(vVar);
                break;
        }
        return new u(this, vVar, i);
    }

    @Override // j$.util.function.v
    public final long applyAsLong(long j) {
        switch (this.a) {
            case 0:
                return this.c.applyAsLong(this.b.applyAsLong(j));
            default:
                return this.b.applyAsLong(this.c.applyAsLong(j));
        }
    }

    @Override // j$.util.function.v
    public v b(v vVar) {
        int i = 1;
        switch (this.a) {
            case 0:
                Objects.requireNonNull(vVar);
                break;
            default:
                Objects.requireNonNull(vVar);
                break;
        }
        return new u(this, vVar, i);
    }
}

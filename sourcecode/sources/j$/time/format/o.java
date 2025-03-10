package j$.time.format;

/* loaded from: classes2.dex */
final class o extends l {
    static final j$.time.e i = j$.time.e.w(2000, 1, 1);
    private final int g;
    private final j$.time.chrono.b h;

    o(j$.time.temporal.l lVar, int i2, int i3, int i4, j$.time.chrono.b bVar) {
        this(lVar, i2, i3, i4, bVar, 0);
        if (i2 < 1 || i2 > 10) {
            throw new IllegalArgumentException("The minWidth must be from 1 to 10 inclusive but was " + i2);
        }
        if (i3 < 1 || i3 > 10) {
            throw new IllegalArgumentException("The maxWidth must be from 1 to 10 inclusive but was " + i2);
        }
        if (i3 >= i2) {
            return;
        }
        throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " + i3 + " < " + i2);
    }

    private o(j$.time.temporal.l lVar, int i2, int i3, int i4, j$.time.chrono.b bVar, int i5) {
        super(lVar, i2, i3, A.NOT_NEGATIVE, i5);
        this.g = i4;
        this.h = bVar;
    }

    /* synthetic */ o(j$.time.temporal.l lVar, int i2, int i3, int i4, j$.time.chrono.b bVar, int i5, c cVar) {
        this(lVar, i2, i3, i4, bVar, i5);
    }

    @Override // j$.time.format.l
    long c(v vVar, long j) {
        long j2;
        long abs = Math.abs(j);
        int i2 = this.g;
        if (this.h != null) {
            j$.time.chrono.d.b(vVar.d());
            i2 = j$.time.e.n(this.h).c(this.a);
        }
        long j3 = i2;
        if (j >= j3) {
            long[] jArr = l.f;
            int i3 = this.b;
            if (j < j3 + jArr[i3]) {
                j2 = jArr[i3];
                return abs % j2;
            }
        }
        j2 = l.f[this.c];
        return abs % j2;
    }

    @Override // j$.time.format.l
    l d() {
        return this.e == -1 ? this : new o(this.a, this.b, this.c, this.g, this.h, -1);
    }

    @Override // j$.time.format.l
    l e(int i2) {
        return new o(this.a, this.b, this.c, this.g, this.h, this.e + i2);
    }

    @Override // j$.time.format.l
    public String toString() {
        StringBuilder a = j$.time.a.a("ReducedValue(");
        a.append(this.a);
        a.append(",");
        a.append(this.b);
        a.append(",");
        a.append(this.c);
        a.append(",");
        Object obj = this.h;
        if (obj == null) {
            obj = Integer.valueOf(this.g);
        }
        a.append(obj);
        a.append(")");
        return a.toString();
    }
}

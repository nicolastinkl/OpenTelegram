package j$.time.format;

/* loaded from: classes2.dex */
class l implements h {
    static final long[] f = {0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L};
    final j$.time.temporal.l a;
    final int b;
    final int c;
    private final A d;
    final int e;

    l(j$.time.temporal.l lVar, int i, int i2, A a) {
        this.a = lVar;
        this.b = i;
        this.c = i2;
        this.d = a;
        this.e = 0;
    }

    protected l(j$.time.temporal.l lVar, int i, int i2, A a, int i3) {
        this.a = lVar;
        this.b = i;
        this.c = i2;
        this.d = a;
        this.e = i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x009b A[LOOP:0: B:18:0x0092->B:20:0x009b, LOOP_END] */
    @Override // j$.time.format.h
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(j$.time.format.v r12, java.lang.StringBuilder r13) {
        /*
            r11 = this;
            j$.time.temporal.l r0 = r11.a
            java.lang.Long r0 = r12.e(r0)
            r1 = 0
            if (r0 != 0) goto La
            return r1
        La:
            long r2 = r0.longValue()
            long r2 = r11.c(r12, r2)
            j$.time.format.y r12 = r12.b()
            r4 = -9223372036854775808
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L1f
            java.lang.String r0 = "9223372036854775808"
            goto L27
        L1f:
            long r4 = java.lang.Math.abs(r2)
            java.lang.String r0 = java.lang.Long.toString(r4)
        L27:
            int r4 = r0.length()
            int r5 = r11.c
            java.lang.String r6 = " cannot be printed as the value "
            java.lang.String r7 = "Field "
            if (r4 > r5) goto La9
            r12.a(r0)
            r4 = 0
            r8 = 2
            r9 = 1
            int r10 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            int[] r4 = j$.time.format.e.a
            j$.time.format.A r5 = r11.d
            int r5 = r5.ordinal()
            if (r10 < 0) goto L60
            r4 = r4[r5]
            if (r4 == r9) goto L4d
            if (r4 == r8) goto L5b
            goto L92
        L4d:
            int r4 = r11.b
            r5 = 19
            if (r4 >= r5) goto L92
            long[] r5 = j$.time.format.l.f
            r4 = r5[r4]
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 < 0) goto L92
        L5b:
            char r2 = r12.d()
            goto L8f
        L60:
            r4 = r4[r5]
            if (r4 == r9) goto L8b
            if (r4 == r8) goto L8b
            r5 = 3
            if (r4 == r5) goto L8b
            r5 = 4
            if (r4 == r5) goto L6d
            goto L92
        L6d:
            j$.time.b r12 = new j$.time.b
            java.lang.StringBuilder r13 = j$.time.a.a(r7)
            j$.time.temporal.l r0 = r11.a
            r13.append(r0)
            r13.append(r6)
            r13.append(r2)
            java.lang.String r0 = " cannot be negative according to the SignStyle"
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L8b:
            char r2 = r12.c()
        L8f:
            r13.append(r2)
        L92:
            int r2 = r11.b
            int r3 = r0.length()
            int r2 = r2 - r3
            if (r1 >= r2) goto La5
            char r2 = r12.e()
            r13.append(r2)
            int r1 = r1 + 1
            goto L92
        La5:
            r13.append(r0)
            return r9
        La9:
            j$.time.b r12 = new j$.time.b
            java.lang.StringBuilder r13 = j$.time.a.a(r7)
            j$.time.temporal.l r0 = r11.a
            r13.append(r0)
            r13.append(r6)
            r13.append(r2)
            java.lang.String r0 = " exceeds the maximum print width of "
            r13.append(r0)
            int r0 = r11.c
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.l.a(j$.time.format.v, java.lang.StringBuilder):boolean");
    }

    long c(v vVar, long j) {
        return j;
    }

    l d() {
        return this.e == -1 ? this : new l(this.a, this.b, this.c, this.d, -1);
    }

    l e(int i) {
        return new l(this.a, this.b, this.c, this.d, this.e + i);
    }

    public String toString() {
        StringBuilder a;
        Object obj;
        int i = this.b;
        if (i == 1 && this.c == 19 && this.d == A.NORMAL) {
            a = j$.time.a.a("Value(");
            obj = this.a;
        } else {
            if (i == this.c && this.d == A.NOT_NEGATIVE) {
                a = j$.time.a.a("Value(");
                a.append(this.a);
                a.append(",");
                a.append(this.b);
                a.append(")");
                return a.toString();
            }
            a = j$.time.a.a("Value(");
            a.append(this.a);
            a.append(",");
            a.append(this.b);
            a.append(",");
            a.append(this.c);
            a.append(",");
            obj = this.d;
        }
        a.append(obj);
        a.append(")");
        return a.toString();
    }
}

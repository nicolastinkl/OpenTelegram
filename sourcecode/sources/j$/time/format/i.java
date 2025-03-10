package j$.time.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/* loaded from: classes2.dex */
final class i implements h {
    private final j$.time.temporal.l a;
    private final int b;
    private final int c;
    private final boolean d;

    i(j$.time.temporal.l lVar, int i, int i2, boolean z) {
        Objects.requireNonNull(lVar, "field");
        if (!lVar.c().f()) {
            throw new IllegalArgumentException("Field must have a fixed set of values: " + lVar);
        }
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("Minimum width must be from 0 to 9 inclusive but was " + i);
        }
        if (i2 < 1 || i2 > 9) {
            throw new IllegalArgumentException("Maximum width must be from 1 to 9 inclusive but was " + i2);
        }
        if (i2 >= i) {
            this.a = lVar;
            this.b = i;
            this.c = i2;
            this.d = z;
            return;
        }
        throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " + i2 + " < " + i);
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        Long e = vVar.e(this.a);
        if (e == null) {
            return false;
        }
        y b = vVar.b();
        long longValue = e.longValue();
        j$.time.temporal.x c = this.a.c();
        c.b(longValue, this.a);
        BigDecimal valueOf = BigDecimal.valueOf(c.e());
        BigDecimal divide = BigDecimal.valueOf(longValue).subtract(valueOf).divide(BigDecimal.valueOf(c.d()).subtract(valueOf).add(BigDecimal.ONE), 9, RoundingMode.FLOOR);
        BigDecimal stripTrailingZeros = divide.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : divide.stripTrailingZeros();
        if (stripTrailingZeros.scale() != 0) {
            String a = b.a(stripTrailingZeros.setScale(Math.min(Math.max(stripTrailingZeros.scale(), this.b), this.c), RoundingMode.FLOOR).toPlainString().substring(2));
            if (this.d) {
                sb.append(b.b());
            }
            sb.append(a);
            return true;
        }
        if (this.b <= 0) {
            return true;
        }
        if (this.d) {
            sb.append(b.b());
        }
        for (int i = 0; i < this.b; i++) {
            sb.append(b.e());
        }
        return true;
    }

    public String toString() {
        String str = this.d ? ",DecimalPoint" : "";
        StringBuilder a = j$.time.a.a("Fraction(");
        a.append(this.a);
        a.append(",");
        a.append(this.b);
        a.append(",");
        a.append(this.c);
        a.append(str);
        a.append(")");
        return a.toString();
    }
}

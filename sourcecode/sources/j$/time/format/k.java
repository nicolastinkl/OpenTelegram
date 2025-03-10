package j$.time.format;

/* loaded from: classes2.dex */
final class k implements h {
    public final /* synthetic */ int a = 0;
    private final Object b;

    public k(B b) {
        this.b = b;
    }

    private static StringBuilder b(StringBuilder sb, int i) {
        sb.append((char) ((i / 10) + 48));
        sb.append((char) ((i % 10) + 48));
        return sb;
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        switch (this.a) {
            case 0:
                Long e = vVar.e(j$.time.temporal.a.OFFSET_SECONDS);
                if (e == null) {
                    return false;
                }
                sb.append("GMT");
                long longValue = e.longValue();
                int i = (int) longValue;
                if (longValue != i) {
                    throw new ArithmeticException();
                }
                if (i == 0) {
                    return true;
                }
                int abs = Math.abs((i / 3600) % 100);
                int abs2 = Math.abs((i / 60) % 60);
                int abs3 = Math.abs(i % 60);
                sb.append(i < 0 ? "-" : "+");
                if (((B) this.b) == B.FULL) {
                    b(sb, abs);
                    sb.append(':');
                    b(sb, abs2);
                    if (abs3 == 0) {
                        return true;
                    }
                } else {
                    if (abs >= 10) {
                        sb.append((char) ((abs / 10) + 48));
                    }
                    sb.append((char) ((abs % 10) + 48));
                    if (abs2 == 0 && abs3 == 0) {
                        return true;
                    }
                    sb.append(':');
                    b(sb, abs2);
                    if (abs3 == 0) {
                        return true;
                    }
                }
                sb.append(':');
                b(sb, abs3);
                return true;
            default:
                sb.append((String) this.b);
                return true;
        }
    }

    public String toString() {
        switch (this.a) {
            case 0:
                return "LocalizedOffset(" + ((B) this.b) + ")";
            default:
                return "'" + ((String) this.b).replace("'", "''") + "'";
        }
    }

    public k(String str) {
        this.b = str;
    }
}

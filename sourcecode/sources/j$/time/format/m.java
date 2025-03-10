package j$.time.format;

import java.util.Objects;

/* loaded from: classes2.dex */
final class m implements h {
    static final String[] c = {"+HH", "+HHmm", "+HH:mm", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS", "+HH:MM:SS"};
    static final m d = new m("+HH:MM:ss", "Z");
    private final String a;
    private final int b;

    static {
        new m("+HH:MM:ss", "0");
    }

    m(String str, String str2) {
        Objects.requireNonNull(str, "pattern");
        Objects.requireNonNull(str2, "noOffsetText");
        int i = 0;
        while (true) {
            String[] strArr = c;
            if (i >= strArr.length) {
                throw new IllegalArgumentException("Invalid zone offset pattern: " + str);
            }
            if (strArr[i].equals(str)) {
                this.b = i;
                this.a = str2;
                return;
            }
            i++;
        }
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        Long e = vVar.e(j$.time.temporal.a.OFFSET_SECONDS);
        if (e == null) {
            return false;
        }
        long longValue = e.longValue();
        int i = (int) longValue;
        if (longValue != i) {
            throw new ArithmeticException();
        }
        if (i != 0) {
            int abs = Math.abs((i / 3600) % 100);
            int abs2 = Math.abs((i / 60) % 60);
            int abs3 = Math.abs(i % 60);
            int length = sb.length();
            sb.append(i < 0 ? "-" : "+");
            sb.append((char) ((abs / 10) + 48));
            sb.append((char) ((abs % 10) + 48));
            int i2 = this.b;
            if (i2 >= 3 || (i2 >= 1 && abs2 > 0)) {
                sb.append(i2 % 2 == 0 ? ":" : "");
                sb.append((char) ((abs2 / 10) + 48));
                sb.append((char) ((abs2 % 10) + 48));
                abs += abs2;
                int i3 = this.b;
                if (i3 >= 7 || (i3 >= 5 && abs3 > 0)) {
                    sb.append(i3 % 2 != 0 ? "" : ":");
                    sb.append((char) ((abs3 / 10) + 48));
                    sb.append((char) ((abs3 % 10) + 48));
                    abs += abs3;
                }
            }
            if (abs == 0) {
                sb.setLength(length);
            }
            return true;
        }
        sb.append(this.a);
        return true;
    }

    public String toString() {
        String replace = this.a.replace("'", "''");
        StringBuilder a = j$.time.a.a("Offset(");
        a.append(c[this.b]);
        a.append(",'");
        a.append(replace);
        a.append("')");
        return a.toString();
    }
}

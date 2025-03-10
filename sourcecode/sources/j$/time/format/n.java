package j$.time.format;

/* loaded from: classes2.dex */
final class n implements h {
    private final h a;
    private final int b;
    private final char c;

    n(h hVar, int i, char c) {
        this.a = hVar;
        this.b = i;
        this.c = c;
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        int length = sb.length();
        if (!this.a.a(vVar, sb)) {
            return false;
        }
        int length2 = sb.length() - length;
        if (length2 <= this.b) {
            for (int i = 0; i < this.b - length2; i++) {
                sb.insert(length, this.c);
            }
            return true;
        }
        throw new j$.time.b("Cannot print as output of " + length2 + " characters exceeds pad width of " + this.b);
    }

    public String toString() {
        String sb;
        StringBuilder a = j$.time.a.a("Pad(");
        a.append(this.a);
        a.append(",");
        a.append(this.b);
        if (this.c == ' ') {
            sb = ")";
        } else {
            StringBuilder a2 = j$.time.a.a(",'");
            a2.append(this.c);
            a2.append("')");
            sb = a2.toString();
        }
        a.append(sb);
        return a.toString();
    }
}

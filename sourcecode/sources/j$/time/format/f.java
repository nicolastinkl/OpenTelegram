package j$.time.format;

/* loaded from: classes2.dex */
final class f implements h {
    private final char a;

    f(char c) {
        this.a = c;
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        sb.append(this.a);
        return true;
    }

    public String toString() {
        if (this.a == '\'') {
            return "''";
        }
        StringBuilder a = j$.time.a.a("'");
        a.append(this.a);
        a.append("'");
        return a.toString();
    }
}

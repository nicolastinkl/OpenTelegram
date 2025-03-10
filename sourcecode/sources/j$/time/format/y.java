package j$.time.format;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class y {
    public static final y a = new y('0', '+', '-', '.');

    static {
        new ConcurrentHashMap(16, 0.75f, 2);
    }

    private y(char c, char c2, char c3, char c4) {
    }

    String a(String str) {
        return str;
    }

    public char b() {
        return '.';
    }

    public char c() {
        return '-';
    }

    public char d() {
        return '+';
    }

    public char e() {
        return '0';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof y)) {
            return false;
        }
        Objects.requireNonNull((y) obj);
        return true;
    }

    public int hashCode() {
        return 182;
    }

    public String toString() {
        return "DecimalStyle[0+-.]";
    }
}

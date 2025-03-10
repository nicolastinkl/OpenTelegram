package com.shubao.xinstall.a.c;

/* loaded from: classes.dex */
public final class f<A, B> {
    final A a;
    final B b;

    private f(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> f<A, B> a(A a, B b) {
        return new f<>(a, b);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || f.class != obj.getClass()) {
            return false;
        }
        f fVar = (f) obj;
        A a = this.a;
        if (a == null) {
            if (fVar.a != null) {
                return false;
            }
        } else if (!a.equals(fVar.a)) {
            return false;
        }
        B b = this.b;
        B b2 = fVar.b;
        if (b == null) {
            if (b2 != null) {
                return false;
            }
        } else if (!b.equals(b2)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        A a = this.a;
        int hashCode = ((a == null ? 0 : a.hashCode()) + 31) * 31;
        B b = this.b;
        return hashCode + (b != null ? b.hashCode() : 0);
    }

    public final String toString() {
        return "first = " + this.a + " , second = " + this.b;
    }
}

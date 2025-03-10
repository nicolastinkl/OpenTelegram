package j$.util;

import java.util.NoSuchElementException;

/* renamed from: j$.util.i, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final class C0120i {
    private static final C0120i c = new C0120i();
    private final boolean a;
    private final double b;

    private C0120i() {
        this.a = false;
        this.b = Double.NaN;
    }

    private C0120i(double d) {
        this.a = true;
        this.b = d;
    }

    public static C0120i a() {
        return c;
    }

    public static C0120i d(double d) {
        return new C0120i(d);
    }

    public double b() {
        if (this.a) {
            return this.b;
        }
        throw new NoSuchElementException("No value present");
    }

    public boolean c() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0120i)) {
            return false;
        }
        C0120i c0120i = (C0120i) obj;
        boolean z = this.a;
        if (z && c0120i.a) {
            if (Double.compare(this.b, c0120i.b) == 0) {
                return true;
            }
        } else if (z == c0120i.a) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (!this.a) {
            return 0;
        }
        long doubleToLongBits = Double.doubleToLongBits(this.b);
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }

    public String toString() {
        return this.a ? String.format("OptionalDouble[%s]", Double.valueOf(this.b)) : "OptionalDouble.empty";
    }
}

package j$.util;

import java.util.NoSuchElementException;

/* renamed from: j$.util.j, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final class C0121j {
    private static final C0121j c = new C0121j();
    private final boolean a;
    private final long b;

    private C0121j() {
        this.a = false;
        this.b = 0L;
    }

    private C0121j(long j) {
        this.a = true;
        this.b = j;
    }

    public static C0121j a() {
        return c;
    }

    public static C0121j d(long j) {
        return new C0121j(j);
    }

    public long b() {
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
        if (!(obj instanceof C0121j)) {
            return false;
        }
        C0121j c0121j = (C0121j) obj;
        boolean z = this.a;
        if (z && c0121j.a) {
            if (this.b == c0121j.b) {
                return true;
            }
        } else if (z == c0121j.a) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (!this.a) {
            return 0;
        }
        long j = this.b;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        return this.a ? String.format("OptionalLong[%s]", Long.valueOf(this.b)) : "OptionalLong.empty";
    }
}

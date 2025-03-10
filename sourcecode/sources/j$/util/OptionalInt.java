package j$.util;

import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public final class OptionalInt {
    private static final OptionalInt c = new OptionalInt();
    private final boolean a;
    private final int b;

    private OptionalInt() {
        this.a = false;
        this.b = 0;
    }

    private OptionalInt(int i) {
        this.a = true;
        this.b = i;
    }

    public static OptionalInt empty() {
        return c;
    }

    public static OptionalInt of(int i) {
        return new OptionalInt(i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OptionalInt)) {
            return false;
        }
        OptionalInt optionalInt = (OptionalInt) obj;
        boolean z = this.a;
        if (z && optionalInt.a) {
            if (this.b == optionalInt.b) {
                return true;
            }
        } else if (z == optionalInt.a) {
            return true;
        }
        return false;
    }

    public int getAsInt() {
        if (this.a) {
            return this.b;
        }
        throw new NoSuchElementException("No value present");
    }

    public int hashCode() {
        if (this.a) {
            return this.b;
        }
        return 0;
    }

    public boolean isPresent() {
        return this.a;
    }

    public String toString() {
        return this.a ? String.format("OptionalInt[%s]", Integer.valueOf(this.b)) : "OptionalInt.empty";
    }
}

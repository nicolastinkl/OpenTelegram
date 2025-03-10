package j$.util;

import java.util.Objects;

/* loaded from: classes2.dex */
public final class I {
    private final String a;
    private final String b;
    private final String c;
    private StringBuilder d;
    private String e;

    public I(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        Objects.requireNonNull(charSequence2, "The prefix must not be null");
        Objects.requireNonNull(charSequence, "The delimiter must not be null");
        Objects.requireNonNull(charSequence3, "The suffix must not be null");
        String charSequence4 = charSequence2.toString();
        this.a = charSequence4;
        this.b = charSequence.toString();
        String charSequence5 = charSequence3.toString();
        this.c = charSequence5;
        this.e = charSequence4 + charSequence5;
    }

    private StringBuilder c() {
        StringBuilder sb = this.d;
        if (sb != null) {
            sb.append(this.b);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.a);
            this.d = sb2;
        }
        return this.d;
    }

    public I a(CharSequence charSequence) {
        c().append(charSequence);
        return this;
    }

    public I b(I i) {
        Objects.requireNonNull(i);
        StringBuilder sb = i.d;
        if (sb != null) {
            c().append((CharSequence) i.d, i.a.length(), sb.length());
        }
        return this;
    }

    public String toString() {
        if (this.d == null) {
            return this.e;
        }
        if (this.c.equals("")) {
            return this.d.toString();
        }
        int length = this.d.length();
        StringBuilder sb = this.d;
        sb.append(this.c);
        String sb2 = sb.toString();
        this.d.setLength(length);
        return sb2;
    }
}

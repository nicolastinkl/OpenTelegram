package kotlin.text;

import java.nio.charset.Charset;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Charsets.kt */
/* loaded from: classes.dex */
public final class Charsets {
    public static final Charsets INSTANCE = new Charsets();
    public static final Charset UTF_8;
    private static Charset utf_32be;
    private static Charset utf_32le;

    private Charsets() {
    }

    static {
        Charset forName = Charset.forName("UTF-8");
        Intrinsics.checkNotNullExpressionValue(forName, "forName(\"UTF-8\")");
        UTF_8 = forName;
        Intrinsics.checkNotNullExpressionValue(Charset.forName("UTF-16"), "forName(\"UTF-16\")");
        Intrinsics.checkNotNullExpressionValue(Charset.forName("UTF-16BE"), "forName(\"UTF-16BE\")");
        Intrinsics.checkNotNullExpressionValue(Charset.forName("UTF-16LE"), "forName(\"UTF-16LE\")");
        Intrinsics.checkNotNullExpressionValue(Charset.forName("US-ASCII"), "forName(\"US-ASCII\")");
        Intrinsics.checkNotNullExpressionValue(Charset.forName("ISO-8859-1"), "forName(\"ISO-8859-1\")");
    }

    public final Charset UTF32_LE() {
        Charset charset = utf_32le;
        if (charset != null) {
            return charset;
        }
        Charset forName = Charset.forName("UTF-32LE");
        Intrinsics.checkNotNullExpressionValue(forName, "forName(\"UTF-32LE\")");
        utf_32le = forName;
        return forName;
    }

    public final Charset UTF32_BE() {
        Charset charset = utf_32be;
        if (charset != null) {
            return charset;
        }
        Charset forName = Charset.forName("UTF-32BE");
        Intrinsics.checkNotNullExpressionValue(forName, "forName(\"UTF-32BE\")");
        utf_32be = forName;
        return forName;
    }
}

package okio.internal;

import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;

/* compiled from: -ByteString.kt */
/* loaded from: classes3.dex */
public final class _ByteStringKt {
    private static final char[] HEX_DIGIT_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final char[] getHEX_DIGIT_CHARS() {
        return HEX_DIGIT_CHARS;
    }

    public static final void commonWrite(ByteString byteString, Buffer buffer, int i, int i2) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        buffer.write(byteString.getData$okio(), i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int decodeHexDigit(char c) {
        if ('0' <= c && c < ':') {
            return c - '0';
        }
        char c2 = 'a';
        if (!('a' <= c && c < 'g')) {
            c2 = 'A';
            if (!('A' <= c && c < 'G')) {
                throw new IllegalArgumentException("Unexpected hex digit: " + c);
            }
        }
        return (c - c2) + 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0201 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0044 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x007c A[EDGE_INSN: B:267:0x007c->B:268:0x007c BREAK  A[LOOP:1: B:249:0x004d->B:276:0x0082, LOOP_LABEL: LOOP:0: B:2:0x0008->B:43:0x0008], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x015e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int codePointIndexToCharIndex(byte[] r19, int r20) {
        /*
            Method dump skipped, instructions count: 531
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal._ByteStringKt.codePointIndexToCharIndex(byte[], int):int");
    }
}

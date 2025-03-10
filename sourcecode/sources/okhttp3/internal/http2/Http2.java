package okhttp3.internal.http2;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.internal.Util;
import okio.ByteString;

/* compiled from: Http2.kt */
/* loaded from: classes3.dex */
public final class Http2 {
    private static final String[] BINARY;
    public static final Http2 INSTANCE = new Http2();
    public static final ByteString CONNECTION_PREFACE = ByteString.Companion.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    private static final String[] FRAME_NAMES = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
    private static final String[] FLAGS = new String[64];

    private Http2() {
    }

    static {
        String replace$default;
        String[] strArr = new String[256];
        int i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            String binaryString = Integer.toBinaryString(i2);
            Intrinsics.checkNotNullExpressionValue(binaryString, "toBinaryString(it)");
            replace$default = StringsKt__StringsJVMKt.replace$default(Util.format("%8s", binaryString), ' ', '0', false, 4, (Object) null);
            strArr[i2] = replace$default;
        }
        BINARY = strArr;
        String[] strArr2 = FLAGS;
        strArr2[0] = "";
        strArr2[1] = "END_STREAM";
        int[] iArr = {1};
        strArr2[8] = "PADDED";
        int i3 = 0;
        while (i3 < 1) {
            int i4 = iArr[i3];
            i3++;
            String[] strArr3 = FLAGS;
            strArr3[i4 | 8] = Intrinsics.stringPlus(strArr3[i4], "|PADDED");
        }
        String[] strArr4 = FLAGS;
        strArr4[4] = "END_HEADERS";
        strArr4[32] = "PRIORITY";
        strArr4[36] = "END_HEADERS|PRIORITY";
        int[] iArr2 = {4, 32, 36};
        int i5 = 0;
        while (i5 < 3) {
            int i6 = iArr2[i5];
            i5++;
            int i7 = 0;
            while (i7 < 1) {
                int i8 = iArr[i7];
                i7++;
                String[] strArr5 = FLAGS;
                int i9 = i8 | i6;
                StringBuilder sb = new StringBuilder();
                sb.append((Object) strArr5[i8]);
                sb.append('|');
                sb.append((Object) strArr5[i6]);
                strArr5[i9] = sb.toString();
                strArr5[i9 | 8] = ((Object) strArr5[i8]) + '|' + ((Object) strArr5[i6]) + "|PADDED";
            }
        }
        int length = FLAGS.length;
        while (i < length) {
            int i10 = i + 1;
            String[] strArr6 = FLAGS;
            if (strArr6[i] == null) {
                strArr6[i] = BINARY[i];
            }
            i = i10;
        }
    }

    public final String frameLog(boolean z, int i, int i2, int i3, int i4) {
        return Util.format("%s 0x%08x %5d %-13s %s", z ? "<<" : ">>", Integer.valueOf(i), Integer.valueOf(i2), formattedType$okhttp(i3), formatFlags(i3, i4));
    }

    public final String formattedType$okhttp(int i) {
        String[] strArr = FRAME_NAMES;
        return i < strArr.length ? strArr[i] : Util.format("0x%02x", Integer.valueOf(i));
    }

    public final String formatFlags(int i, int i2) {
        String str;
        String replace$default;
        String replace$default2;
        if (i2 == 0) {
            return "";
        }
        if (i != 2 && i != 3) {
            if (i == 4 || i == 6) {
                return i2 == 1 ? "ACK" : BINARY[i2];
            }
            if (i != 7 && i != 8) {
                String[] strArr = FLAGS;
                if (i2 < strArr.length) {
                    str = strArr[i2];
                    Intrinsics.checkNotNull(str);
                } else {
                    str = BINARY[i2];
                }
                String str2 = str;
                if (i == 5 && (i2 & 4) != 0) {
                    replace$default2 = StringsKt__StringsJVMKt.replace$default(str2, "HEADERS", "PUSH_PROMISE", false, 4, (Object) null);
                    return replace$default2;
                }
                if (i != 0 || (i2 & 32) == 0) {
                    return str2;
                }
                replace$default = StringsKt__StringsJVMKt.replace$default(str2, "PRIORITY", "COMPRESSED", false, 4, (Object) null);
                return replace$default;
            }
        }
        return BINARY[i2];
    }
}

package io.openinstall.sdk;

import com.tencent.beacon.pack.AbstractJceStruct;
import java.nio.charset.Charset;
import java.util.Arrays;

/* loaded from: classes.dex */
public class dr {

    public static class a {
        static final a a;
        static final a b;
        static final a c;
        private static final int[] f;
        private static final int[] g;
        private final boolean d;
        private final boolean e;

        static {
            int[] iArr = new int[256];
            f = iArr;
            Arrays.fill(iArr, -1);
            for (int i = 0; i < b.h.length; i++) {
                f[b.h[i]] = i;
            }
            f[61] = -2;
            int[] iArr2 = new int[256];
            g = iArr2;
            Arrays.fill(iArr2, -1);
            for (int i2 = 0; i2 < b.i.length; i2++) {
                g[b.i[i2]] = i2;
            }
            g[61] = -2;
            a = new a(false, false);
            b = new a(true, false);
            c = new a(false, true);
        }

        private a(boolean z, boolean z2) {
            this.d = z;
            this.e = z2;
        }

        private int a(byte[] bArr, int i, int i2) {
            int i3;
            int[] iArr = this.d ? g : f;
            int i4 = i2 - i;
            int i5 = 0;
            if (i4 == 0) {
                return 0;
            }
            if (i4 < 2) {
                if (this.e && iArr[0] == -1) {
                    return 0;
                }
                throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
            }
            if (this.e) {
                int i6 = 0;
                while (true) {
                    if (i >= i2) {
                        break;
                    }
                    int i7 = i + 1;
                    int i8 = bArr[i] & 255;
                    if (i8 == 61) {
                        i4 -= (i2 - i7) + 1;
                        break;
                    }
                    if (iArr[i8] == -1) {
                        i6++;
                    }
                    i = i7;
                }
                i4 -= i6;
            } else if (bArr[i2 - 1] == 61) {
                i5 = bArr[i2 - 2] == 61 ? 2 : 1;
            }
            if (i5 == 0 && (i3 = i4 & 3) != 0) {
                i5 = 4 - i3;
            }
            return (((i4 + 3) / 4) * 3) - i5;
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x002c, code lost:
        
            if (r11[r8] == 61) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0030, code lost:
        
            if (r4 != 18) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x007f, code lost:
        
            if (r4 != 6) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0081, code lost:
        
            r14[r5] = (byte) (r3 >> 16);
            r5 = r5 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x009f, code lost:
        
            if (r12 >= r13) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x00a3, code lost:
        
            if (r10.e == false) goto L57;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00a5, code lost:
        
            r14 = r12 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x00ab, code lost:
        
            if (r0[r11[r12]] >= 0) goto L58;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00ad, code lost:
        
            r12 = r14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x00af, code lost:
        
            r12 = r14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x00c6, code lost:
        
            throw new java.lang.IllegalArgumentException("Input byte array has incorrect ending byte at " + r12);
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00c7, code lost:
        
            return r5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x008a, code lost:
        
            if (r4 != 0) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x008c, code lost:
        
            r1 = r5 + 1;
            r14[r5] = (byte) (r3 >> 16);
            r5 = r1 + 1;
            r14[r1] = (byte) (r3 >> 8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x009d, code lost:
        
            if (r4 == 12) goto L49;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00cf, code lost:
        
            throw new java.lang.IllegalArgumentException("Last unit does not have enough valid bits");
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private int a(byte[] r11, int r12, int r13, byte[] r14) {
            /*
                Method dump skipped, instructions count: 208
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.dr.a.a(byte[], int, int, byte[]):int");
        }

        public byte[] a(String str) {
            return a(str.getBytes(Charset.defaultCharset()));
        }

        public byte[] a(byte[] bArr) {
            int a2 = a(bArr, 0, bArr.length);
            byte[] bArr2 = new byte[a2];
            int a3 = a(bArr, 0, bArr.length, bArr2);
            return a3 != a2 ? Arrays.copyOf(bArr2, a3) : bArr2;
        }
    }

    public static class b {
        static final b a;
        static final b b;
        static final b c;
        private static final char[] h = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        private static final char[] i = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        private static final byte[] j;
        private final byte[] d;
        private final int e;
        private final boolean f;
        private final boolean g;

        static {
            byte[] bArr = {AbstractJceStruct.SIMPLE_LIST, 10};
            j = bArr;
            a = new b(false, null, -1, true);
            b = new b(true, null, -1, true);
            c = new b(false, bArr, 76, true);
        }

        private b(boolean z, byte[] bArr, int i2, boolean z2) {
            this.f = z;
            this.d = bArr;
            this.e = i2;
            this.g = z2;
        }

        private final int a(int i2) {
            int i3;
            if (this.g) {
                i3 = ((i2 + 2) / 3) * 4;
            } else {
                int i4 = i2 % 3;
                i3 = ((i2 / 3) * 4) + (i4 == 0 ? 0 : i4 + 1);
            }
            int i5 = this.e;
            return i5 > 0 ? i3 + (((i3 - 1) / i5) * this.d.length) : i3;
        }

        private int a(byte[] bArr, int i2, int i3, byte[] bArr2) {
            char[] cArr = this.f ? i : h;
            int i4 = ((i3 - i2) / 3) * 3;
            int i5 = i2 + i4;
            int i6 = this.e;
            if (i6 > 0 && i4 > (i6 / 4) * 3) {
                i4 = (i6 / 4) * 3;
            }
            int i7 = 0;
            while (i2 < i5) {
                int min = Math.min(i2 + i4, i5);
                int i8 = i2;
                int i9 = i7;
                while (i8 < min) {
                    int i10 = i8 + 1;
                    int i11 = i10 + 1;
                    int i12 = ((bArr[i8] & 255) << 16) | ((bArr[i10] & 255) << 8);
                    int i13 = i11 + 1;
                    int i14 = i12 | (bArr[i11] & 255);
                    int i15 = i9 + 1;
                    bArr2[i9] = (byte) cArr[(i14 >>> 18) & 63];
                    int i16 = i15 + 1;
                    bArr2[i15] = (byte) cArr[(i14 >>> 12) & 63];
                    int i17 = i16 + 1;
                    bArr2[i16] = (byte) cArr[(i14 >>> 6) & 63];
                    i9 = i17 + 1;
                    bArr2[i17] = (byte) cArr[i14 & 63];
                    i8 = i13;
                }
                int i18 = ((min - i2) / 3) * 4;
                i7 += i18;
                if (i18 == this.e && min < i3) {
                    byte[] bArr3 = this.d;
                    int length = bArr3.length;
                    int i19 = 0;
                    while (i19 < length) {
                        bArr2[i7] = bArr3[i19];
                        i19++;
                        i7++;
                    }
                }
                i2 = min;
            }
            if (i2 >= i3) {
                return i7;
            }
            int i20 = i2 + 1;
            int i21 = bArr[i2] & 255;
            int i22 = i7 + 1;
            bArr2[i7] = (byte) cArr[i21 >> 2];
            if (i20 == i3) {
                int i23 = i22 + 1;
                bArr2[i22] = (byte) cArr[(i21 << 4) & 63];
                if (!this.g) {
                    return i23;
                }
                int i24 = i23 + 1;
                bArr2[i23] = 61;
                int i25 = i24 + 1;
                bArr2[i24] = 61;
                return i25;
            }
            int i26 = bArr[i20] & 255;
            int i27 = i22 + 1;
            bArr2[i22] = (byte) cArr[((i21 << 4) & 63) | (i26 >> 4)];
            int i28 = i27 + 1;
            bArr2[i27] = (byte) cArr[(i26 << 2) & 63];
            if (!this.g) {
                return i28;
            }
            int i29 = i28 + 1;
            bArr2[i28] = 61;
            return i29;
        }

        public b a() {
            return !this.g ? this : new b(this.f, this.d, this.e, false);
        }

        public byte[] a(byte[] bArr) {
            int a2 = a(bArr.length);
            byte[] bArr2 = new byte[a2];
            int a3 = a(bArr, 0, bArr.length, bArr2);
            return a3 != a2 ? Arrays.copyOf(bArr2, a3) : bArr2;
        }

        public String b(byte[] bArr) {
            byte[] a2 = a(bArr);
            return new String(a2, 0, 0, a2.length);
        }
    }

    public static b a() {
        return b.a;
    }

    public static b b() {
        return b.b;
    }

    public static a c() {
        return a.a;
    }

    public static a d() {
        return a.b;
    }
}

package com.tencent.qmsp.sdk.f;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

/* loaded from: classes.dex */
class c {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private int e;
    private int f;
    private int g;
    private byte[] h;
    private int j;
    private boolean i = true;
    private Random k = new Random();

    c() {
    }

    private void a() {
        this.f = 0;
        while (true) {
            int i = this.f;
            if (i >= 8) {
                break;
            }
            if (this.i) {
                byte[] bArr = this.a;
                bArr[i] = (byte) (bArr[i] ^ this.b[i]);
            } else {
                byte[] bArr2 = this.a;
                bArr2[i] = (byte) (bArr2[i] ^ this.c[this.e + i]);
            }
            this.f = i + 1;
        }
        System.arraycopy(b(this.a), 0, this.c, this.d, 8);
        this.f = 0;
        while (true) {
            int i2 = this.f;
            if (i2 >= 8) {
                System.arraycopy(this.a, 0, this.b, 0, 8);
                int i3 = this.d;
                this.e = i3;
                this.d = i3 + 8;
                this.f = 0;
                this.i = false;
                return;
            }
            byte[] bArr3 = this.c;
            int i4 = this.d + i2;
            bArr3[i4] = (byte) (bArr3[i4] ^ this.b[i2]);
            this.f = i2 + 1;
        }
    }

    private boolean a(byte[] bArr, int i, int i2) {
        this.f = 0;
        while (true) {
            int i3 = this.f;
            if (i3 >= 8) {
                byte[] a = a(this.b);
                this.b = a;
                if (a == null) {
                    return false;
                }
                this.j += 8;
                this.d += 8;
                this.f = 0;
                return true;
            }
            if (this.j + i3 >= i2) {
                return true;
            }
            byte[] bArr2 = this.b;
            bArr2[i3] = (byte) (bArr2[i3] ^ bArr[(this.d + i) + i3]);
            this.f = i3 + 1;
        }
    }

    private byte[] a(byte[] bArr) {
        return a(bArr, 0);
    }

    private byte[] a(byte[] bArr, int i) {
        try {
            long b = b(bArr, i, 4);
            long b2 = b(bArr, i + 4, 4);
            long b3 = b(this.h, 0, 4);
            long b4 = b(this.h, 4, 4);
            long b5 = b(this.h, 8, 4);
            long b6 = b(this.h, 12, 4);
            long j = 3816266640L;
            int i2 = 16;
            while (true) {
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8);
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeInt((int) b);
                    dataOutputStream.writeInt((int) b2);
                    dataOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                b2 = (b2 - ((((b << 4) + b5) ^ (b + j)) ^ ((b >>> 5) + b6))) & 4294967295L;
                b = (b - ((((b2 << 4) + b3) ^ (b2 + j)) ^ ((b2 >>> 5) + b4))) & 4294967295L;
                j = (j - 2654435769L) & 4294967295L;
                i2 = i3;
            }
        } catch (IOException unused) {
            return null;
        }
    }

    private int b() {
        return this.k.nextInt();
    }

    private static long b(byte[] bArr, int i, int i2) {
        long j = 0;
        int i3 = i2 > 8 ? i + 8 : i2 + i;
        while (i < i3) {
            j = (j << 8) | (bArr[i] & 255);
            i++;
        }
        return (4294967295L & j) | (j >>> 32);
    }

    private byte[] b(byte[] bArr) {
        try {
            long b = b(bArr, 0, 4);
            long b2 = b(bArr, 4, 4);
            long b3 = b(this.h, 0, 4);
            long b4 = b(this.h, 4, 4);
            long b5 = b(this.h, 8, 4);
            long b6 = b(this.h, 12, 4);
            long j = 0;
            int i = 16;
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8);
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeInt((int) b);
                    dataOutputStream.writeInt((int) b2);
                    dataOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                j = (j + 2654435769L) & 4294967295L;
                b = (b + ((((b2 << 4) + b3) ^ (b2 + j)) ^ ((b2 >>> 5) + b4))) & 4294967295L;
                b2 = (b2 + ((((b << 4) + b5) ^ (b + j)) ^ ((b >>> 5) + b6))) & 4294967295L;
                i = i2;
            }
        } catch (IOException unused) {
            return null;
        }
    }

    private byte[] b(byte[] bArr, int i, int i2, byte[] bArr2) {
        int i3;
        byte[] bArr3 = new byte[8];
        this.a = bArr3;
        this.b = new byte[8];
        this.f = 1;
        this.g = 0;
        this.e = 0;
        this.d = 0;
        this.h = bArr2;
        this.i = true;
        int i4 = (i2 + 10) % 8;
        this.f = i4;
        if (i4 != 0) {
            this.f = 8 - i4;
        }
        this.c = new byte[this.f + i2 + 10];
        bArr3[0] = (byte) ((b() & 248) | this.f);
        int i5 = 1;
        while (true) {
            i3 = this.f;
            if (i5 > i3) {
                break;
            }
            this.a[i5] = (byte) (b() & 255);
            i5++;
        }
        this.f = i3 + 1;
        for (int i6 = 0; i6 < 8; i6++) {
            this.b[i6] = 0;
        }
        this.g = 1;
        while (this.g <= 2) {
            int i7 = this.f;
            if (i7 < 8) {
                byte[] bArr4 = this.a;
                this.f = i7 + 1;
                bArr4[i7] = (byte) (b() & 255);
                this.g++;
            }
            if (this.f == 8) {
                a();
            }
        }
        while (i2 > 0) {
            int i8 = this.f;
            if (i8 < 8) {
                byte[] bArr5 = this.a;
                this.f = i8 + 1;
                bArr5[i8] = bArr[i];
                i2--;
                i++;
            }
            if (this.f == 8) {
                a();
            }
        }
        this.g = 1;
        while (true) {
            int i9 = this.g;
            if (i9 > 7) {
                return this.c;
            }
            int i10 = this.f;
            if (i10 < 8) {
                byte[] bArr6 = this.a;
                this.f = i10 + 1;
                bArr6[i10] = 0;
                this.g = i9 + 1;
            }
            if (this.f == 8) {
                a();
            }
        }
    }

    protected byte[] a(byte[] bArr, int i, int i2, byte[] bArr2) {
        int i3 = 0;
        this.e = 0;
        this.d = 0;
        this.h = bArr2;
        int i4 = i + 8;
        byte[] bArr3 = new byte[i4];
        if (i2 % 8 != 0 || i2 < 16) {
            return null;
        }
        byte[] a = a(bArr, i);
        this.b = a;
        int i5 = a[0] & 7;
        this.f = i5;
        int i6 = (i2 - i5) - 10;
        if (i6 < 0) {
            return null;
        }
        for (int i7 = i; i7 < i4; i7++) {
            bArr3[i7] = 0;
        }
        this.c = new byte[i6];
        this.e = 0;
        this.d = 8;
        this.j = 8;
        this.f++;
        this.g = 1;
        while (true) {
            int i8 = this.g;
            if (i8 > 2) {
                while (i6 != 0) {
                    int i9 = this.f;
                    if (i9 < 8) {
                        this.c[i3] = (byte) (bArr3[(this.e + i) + i9] ^ this.b[i9]);
                        i3++;
                        i6--;
                        this.f = i9 + 1;
                    }
                    if (this.f == 8) {
                        this.e = this.d - 8;
                        if (!a(bArr, i, i2)) {
                            return null;
                        }
                        bArr3 = bArr;
                    }
                }
                this.g = 1;
                while (this.g < 8) {
                    int i10 = this.f;
                    if (i10 < 8) {
                        if ((bArr3[(this.e + i) + i10] ^ this.b[i10]) != 0) {
                            return null;
                        }
                        this.f = i10 + 1;
                    }
                    if (this.f == 8) {
                        this.e = this.d;
                        if (!a(bArr, i, i2)) {
                            return null;
                        }
                        bArr3 = bArr;
                    }
                    this.g++;
                }
                return this.c;
            }
            int i11 = this.f;
            if (i11 < 8) {
                this.f = i11 + 1;
                this.g = i8 + 1;
            }
            if (this.f == 8) {
                if (!a(bArr, i, i2)) {
                    return null;
                }
                bArr3 = bArr;
            }
        }
    }

    protected byte[] a(byte[] bArr, byte[] bArr2) {
        return b(bArr, 0, bArr.length, bArr2);
    }
}

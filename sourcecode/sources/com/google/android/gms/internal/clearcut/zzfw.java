package com.google.android.gms.internal.clearcut;

/* loaded from: classes.dex */
public final class zzfw implements Cloneable {
    private int mSize;
    private int[] zzrn;
    private zzfx[] zzro;

    static {
        new zzfx();
    }

    zzfw() {
        this(10);
    }

    private zzfw(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        int i5 = i2 / 4;
        this.zzrn = new int[i5];
        this.zzro = new zzfx[i5];
        this.mSize = 0;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzfw zzfwVar = new zzfw(i);
        System.arraycopy(this.zzrn, 0, zzfwVar.zzrn, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            zzfx[] zzfxVarArr = this.zzro;
            if (zzfxVarArr[i2] != null) {
                zzfwVar.zzro[i2] = (zzfx) zzfxVarArr[i2].clone();
            }
        }
        zzfwVar.mSize = i;
        return zzfwVar;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfw)) {
            return false;
        }
        zzfw zzfwVar = (zzfw) obj;
        int i = this.mSize;
        if (i != zzfwVar.mSize) {
            return false;
        }
        int[] iArr = this.zzrn;
        int[] iArr2 = zzfwVar.zzrn;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            }
            if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            }
            i2++;
        }
        if (z) {
            zzfx[] zzfxVarArr = this.zzro;
            zzfx[] zzfxVarArr2 = zzfwVar.zzro;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                }
                if (!zzfxVarArr[i4].equals(zzfxVarArr2[i4])) {
                    z2 = false;
                    break;
                }
                i4++;
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzrn[i2]) * 31) + this.zzro[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final zzfx zzaq(int i) {
        return this.zzro[i];
    }
}

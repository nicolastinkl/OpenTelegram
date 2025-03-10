package com.google.android.gms.internal.clearcut;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
final class zzfx implements Cloneable {
    private Object value;
    private zzfv<?, ?> zzrp;
    private List<Object> zzrq = new ArrayList();

    zzfx() {
    }

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzen()];
        zza(zzfs.zzg(bArr));
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: zzeq, reason: merged with bridge method [inline-methods] */
    public final zzfx clone() {
        Object clone;
        zzfx zzfxVar = new zzfx();
        try {
            List<Object> list = this.zzrq;
            if (list == null) {
                zzfxVar.zzrq = null;
            } else {
                zzfxVar.zzrq.addAll(list);
            }
            Object obj = this.value;
            if (obj != null) {
                if (obj instanceof zzfz) {
                    clone = (zzfz) ((zzfz) obj).clone();
                } else if (obj instanceof byte[]) {
                    clone = ((byte[]) obj).clone();
                } else {
                    int i = 0;
                    if (obj instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) obj;
                        byte[][] bArr2 = new byte[bArr.length][];
                        zzfxVar.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (obj instanceof boolean[]) {
                        clone = ((boolean[]) obj).clone();
                    } else if (obj instanceof int[]) {
                        clone = ((int[]) obj).clone();
                    } else if (obj instanceof long[]) {
                        clone = ((long[]) obj).clone();
                    } else if (obj instanceof float[]) {
                        clone = ((float[]) obj).clone();
                    } else if (obj instanceof double[]) {
                        clone = ((double[]) obj).clone();
                    } else if (obj instanceof zzfz[]) {
                        zzfz[] zzfzVarArr = (zzfz[]) obj;
                        zzfz[] zzfzVarArr2 = new zzfz[zzfzVarArr.length];
                        zzfxVar.value = zzfzVarArr2;
                        while (i < zzfzVarArr.length) {
                            zzfzVarArr2[i] = (zzfz) zzfzVarArr[i].clone();
                            i++;
                        }
                    }
                }
                zzfxVar.value = clone;
            }
            return zzfxVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        List<Object> list;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfx)) {
            return false;
        }
        zzfx zzfxVar = (zzfx) obj;
        if (this.value != null && zzfxVar.value != null) {
            if (this.zzrp != zzfxVar.zzrp) {
                return false;
            }
            throw null;
        }
        List<Object> list2 = this.zzrq;
        if (list2 != null && (list = zzfxVar.zzrq) != null) {
            return list2.equals(list);
        }
        try {
            return Arrays.equals(toByteArray(), zzfxVar.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    final void zza(zzfs zzfsVar) throws IOException {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        Iterator<Object> it = this.zzrq.iterator();
        if (it.hasNext()) {
            it.next();
            throw new NoSuchMethodError();
        }
    }

    final int zzen() {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        Iterator<Object> it = this.zzrq.iterator();
        if (!it.hasNext()) {
            return 0;
        }
        it.next();
        throw new NoSuchMethodError();
    }
}

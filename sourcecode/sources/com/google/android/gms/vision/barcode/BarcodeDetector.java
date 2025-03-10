package com.google.android.gms.vision.barcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.util.SparseArray;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.vision.zzs;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import java.nio.ByteBuffer;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
/* loaded from: classes.dex */
public final class BarcodeDetector extends Detector<Barcode> {
    private final com.google.android.gms.internal.vision.zzm zza;

    private BarcodeDetector(com.google.android.gms.internal.vision.zzm zzmVar) {
        this.zza = zzmVar;
    }

    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class Builder {
        private Context zza;
        private com.google.android.gms.internal.vision.zzk zzb = new com.google.android.gms.internal.vision.zzk();

        public Builder(@RecentlyNonNull Context context) {
            this.zza = context;
        }

        @RecentlyNonNull
        public Builder setBarcodeFormats(int i) {
            this.zzb.zza = i;
            return this;
        }

        @RecentlyNonNull
        public BarcodeDetector build() {
            return new BarcodeDetector(new com.google.android.gms.internal.vision.zzm(this.zza, this.zzb));
        }
    }

    @Override // com.google.android.gms.vision.Detector
    public final void release() {
        super.release();
        this.zza.zzc();
    }

    @RecentlyNonNull
    public final SparseArray<Barcode> detect(@RecentlyNonNull Frame frame) {
        Barcode[] zza;
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        zzs zza2 = zzs.zza(frame);
        if (frame.getBitmap() != null) {
            zza = this.zza.zza((Bitmap) Preconditions.checkNotNull(frame.getBitmap()), zza2);
            if (zza == null) {
                throw new IllegalArgumentException("Internal barcode detector error; check logcat output.");
            }
        } else if (Build.VERSION.SDK_INT >= 19 && frame.getPlanes() != null) {
            zza = this.zza.zza((ByteBuffer) Preconditions.checkNotNull(((Image.Plane[]) Preconditions.checkNotNull(frame.getPlanes()))[0].getBuffer()), new zzs(((Image.Plane[]) Preconditions.checkNotNull(frame.getPlanes()))[0].getRowStride(), zza2.zzb, zza2.zzc, zza2.zzd, zza2.zze));
        } else {
            zza = this.zza.zza((ByteBuffer) Preconditions.checkNotNull(frame.getGrayscaleImageData()), zza2);
        }
        SparseArray<Barcode> sparseArray = new SparseArray<>(zza.length);
        for (Barcode barcode : zza) {
            sparseArray.append(barcode.rawValue.hashCode(), barcode);
        }
        return sparseArray;
    }

    public final boolean isOperational() {
        return this.zza.zzb();
    }
}

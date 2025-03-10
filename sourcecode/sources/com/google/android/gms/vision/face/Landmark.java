package com.google.android.gms.vision.face;

import android.graphics.PointF;
import androidx.annotation.RecentlyNonNull;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
/* loaded from: classes.dex */
public final class Landmark {
    private final PointF zza;
    private final int zzb;

    @RecentlyNonNull
    public final PointF getPosition() {
        return this.zza;
    }

    public final int getType() {
        return this.zzb;
    }

    public Landmark(@RecentlyNonNull PointF pointF, int i) {
        this.zza = pointF;
        this.zzb = i;
    }
}
